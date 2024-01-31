package com.testus.testus.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.testus.testus.common.oauth.userInfo.OAuth2UserInfo;
import com.testus.testus.common.oauth.userInfo.OAuth2UserInfoFactory;
import com.testus.testus.common.response.ResponseDto;
import com.testus.testus.common.response.exception.Code;
import com.testus.testus.common.response.exception.CustomException;
import com.testus.testus.domain.Member;
import com.testus.testus.dto.member.PwResetUuidDto;
import com.testus.testus.enums.SocialType;
import com.testus.testus.repository.MemberRepo;
import com.testus.testus.util.JwtTokenUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepo memberRepo;
    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final RedisService redisService;
    private final ObjectMapper objectMapper;

    @Override
    public Member oauthSignUp(String name, String nickname, String email, String subId, SocialType type) {
        Member member = Member.builder()
                .providerType(type.getTypeName())
                .userPassword("asdfasdf")
                .userName(nickname)
                .userEmail("test@gmail.com")
                .providerSubject(subId)
                .status('D')
                .marketingYn('N')
                .build();

        return memberRepo.save(member);
    }

    @Override
    public Optional<Member> findMember(String id) {
        return memberRepo.findByProviderSubject(id);
    }

    @Override
    @Transactional
    public ResponseDto<Member.MemberInfoDto> updateInfo(Member.MemberInfoUpdateOrSignupDto memberInfoUpdateOrSignupDto, Member member) {
        if (memberInfoUpdateOrSignupDto.getPassword() == null) {
            memberRepo.updateInfo(memberInfoUpdateOrSignupDto, member.getUserSeq(), null);
        } else {
            memberRepo.updateInfo(memberInfoUpdateOrSignupDto, member.getUserSeq(), passwordEncoder.encode(memberInfoUpdateOrSignupDto.getPassword()));
        }
        return new ResponseDto<>(Code.SUCCESS);
    }


    @Transactional
    public ResponseDto<Member.MemberInfoDto> oauthLogin(String provider, String code, String redirectUrl, HttpServletResponse response) {

        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(provider, code, redirectUrl);
        Optional<Member> findUser = memberRepo.findByProviderSubject(userInfo.getId());

        Member member;

        if (findUser.isPresent()) { // 기존 회원
            member = findUser.get();
            jwtTokenUtil.addJwtTokenInHeader(member, response);
        } else { // 신규 회원
            member = Member.builder()
                    .userName(userInfo.getName())
                    .userEmail(userInfo.getEmail())
                    .userPassword(null)
                    .providerType(provider)
                    .providerSubject(userInfo.getId())
                    .role("ROLE_USER")
                    .status('D')
                    .build();
            member = memberRepo.save(member);
        }
        return checkMemberStatusAndReturn(member);
    }

    @Transactional(readOnly = true)
    public ResponseDto<Member.MemberInfoDto> checkMemberStatusAndReturn(Member member) {
        if (member.getStatus() == 'D') { // 회원 정보 업데이트 필요
            return new ResponseDto<>(Code.REQUIRED_UPDATE_MEMBER_INFO, member.convertMemberInfoResponse());
        } else {
            return new ResponseDto<>(Code.SUCCESS, member.convertMemberInfoResponse());
        }
    }

    @Transactional
    public ResponseDto<Code> signup(Member.MemberInfoUpdateOrSignupDto dto) {
        Optional<Member> oneByUserEmail = memberRepo.findOneByUserEmail(dto.getUserEmail());
        if (oneByUserEmail.isPresent()) {
            throw new CustomException(Code.ALREADY_MEMBER);
        } else {
            Member member = Member.builder()
                    .providerType("TESTUS")
                    .userPassword(passwordEncoder.encode(dto.getPassword()))
                    .userName(dto.getUserName())
                    .userEmail(dto.getUserEmail())
                    .status('Y')
                    .role("ROLE_USER")
                    .marketingYn(dto.getMarketingYn())
                    .build();
            memberRepo.save(member);
            return new ResponseDto<>(Code.SUCCESS);
        }
    }

    @Transactional
    public ResponseDto<Member.MemberInfoDto> login(Member.LoginDto dto, HttpServletResponse response) {
        Member member = memberRepo.findOneByUserEmail(dto.getUserEmail()).orElseThrow(
                () -> new CustomException(Code.NOT_FOUND_USER)
        );
        if (passwordEncoder.matches(dto.getPassword(), member.getUserPassword())) {
            jwtTokenUtil.addJwtTokenInHeader(member, response);
            return checkMemberStatusAndReturn(member);
        } else {
            throw new CustomException(Code.PASSWORD_UNMATCHED);
        }
    }

    @Transactional(readOnly = true)
    public ResponseDto<Member.FindIdResponseDto> findId(Member.FindIdRequestDto dto) {
        Member member = memberRepo.findOneByUserEmailAndPhoneNumber(dto.getUserName(), dto.getPhoneNumber())
                .orElseThrow(
                        () -> new CustomException(Code.NOT_FOUND_USER)
                );

        Member.FindIdResponseDto result =
                Member.FindIdResponseDto.builder().userEmail(member.getUserEmail())
                        .build();
        return new ResponseDto<>(Code.SUCCESS, result);
    }

    @Transactional(readOnly = true)
    public ResponseDto<Code> resetPwMailSend(Member.FindPwRequestDto dto) throws Exception {
        UUID uuid = UUID.randomUUID();

        Member member = memberRepo.findOneByUserEmail(dto.getUserEmail()).orElseThrow(
                () -> new CustomException(Code.NOT_FOUND_USER)
        );
        emailService.sendSimpleMessage(member.getUserEmail(), uuid);
        PwResetUuidDto pwResetUuidDto = PwResetUuidDto.builder()
                .userSeq(member.getUserSeq())
                .userEmail(member.getUserEmail())
                .build();
        redisService.setValues("RESET::"+uuid, pwResetUuidDto, Duration.ofMinutes(10));
        return new ResponseDto<>(Code.SUCCESS);
    }

    @Transactional
    public ResponseDto<Code> resetPw(Member.ResetPwRequestDto dto) {
        Object values = redisService.getValues("RESET::" + dto.getUuid());
        PwResetUuidDto pwResetUuidDto = objectMapper.convertValue(values, PwResetUuidDto.class);
        if (dto.getUserEmail().equals(pwResetUuidDto.getUserEmail())){
            Member target = memberRepo.findById(pwResetUuidDto.getUserSeq()).orElseThrow(
                    () -> new CustomException(Code.BAD_REQUEST)
            );
            memberRepo.changePassword(target.getUserSeq(), passwordEncoder.encode(dto.getPassword()));
            redisService.deleteValues("RESET::" + dto.getUuid());
        } else {
            throw new CustomException(Code.BAD_REQUEST);
        }
        return new ResponseDto<>(Code.SUCCESS);
    }
}
