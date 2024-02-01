package com.testus.testus.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.testus.testus.common.oauth.userInfo.OAuth2UserInfo;
import com.testus.testus.common.oauth.userInfo.OAuth2UserInfoFactory;
import com.testus.testus.common.response.ResponseDto;
import com.testus.testus.common.response.exception.Code;
import com.testus.testus.common.response.exception.CustomException;
import com.testus.testus.domain.Member;
import com.testus.testus.repository.MemberRepo;
import com.testus.testus.util.JwtTokenUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepo memberRepo;
    private final MemberService memberService;
    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final RedisService redisService;
    private final ObjectMapper objectMapper;



    @Transactional
    public ResponseDto<Member.MemberInfoDto> oauthLogin(String provider, String code, String redirectUrl, HttpServletResponse response) {

        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(provider, code, redirectUrl);
        Optional<Member> findUser = memberRepo.findByProviderSubject(userInfo.getId());

        Member member;

        if (findUser.isPresent()) { // 기존 회원
            member = findUser.get();
            jwtTokenUtil.addJwtTokenInHeader(member.getUserSeq(), response);
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
        return memberService.checkMemberStatusAndReturn(member);
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
            jwtTokenUtil.addJwtTokenInHeader(member.getUserSeq(), response);
            return memberService.checkMemberStatusAndReturn(member);
        } else {
            throw new CustomException(Code.PASSWORD_UNMATCHED);
        }
    }

    @Transactional(readOnly = true)
    public ResponseDto<Code> checkPassword(Member.PasswordCheckDto dto, Member member) {
        if (passwordEncoder.matches(dto.getPassword(),member.getUserPassword())){
            return new ResponseDto<>(Code.SUCCESS);
        } else {
            throw new CustomException(Code.PASSWORD_UNMATCHED);
        }
    }


    @Transactional
    public ResponseDto<Code> reverseAlarm(String category, Member member) {

        memberRepo.reverseAlarmSetup(category, member);
        return new ResponseDto<>(Code.SUCCESS);
    }
}
