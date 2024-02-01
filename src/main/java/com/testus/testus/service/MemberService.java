package com.testus.testus.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.testus.testus.common.response.ResponseDto;
import com.testus.testus.common.response.exception.Code;
import com.testus.testus.common.response.exception.CustomException;
import com.testus.testus.domain.Member;
import com.testus.testus.dto.member.PwResetUuidDto;
import com.testus.testus.repository.MemberRepo;
import com.testus.testus.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepo memberRepo;
    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final RedisService redisService;
    private final ObjectMapper objectMapper;

    @Transactional
    public ResponseDto<Member.MemberInfoDto> updateInfo(Member.MemberInfoUpdateOrSignupDto memberInfoUpdateOrSignupDto, Member member) {
        if (memberInfoUpdateOrSignupDto.getPassword() == null) {
            memberRepo.updateInfo(memberInfoUpdateOrSignupDto, member.getUserSeq(), null);
        } else {
            memberRepo.updateInfo(memberInfoUpdateOrSignupDto, member.getUserSeq(), passwordEncoder.encode(memberInfoUpdateOrSignupDto.getPassword()));
        }
        return new ResponseDto<>(Code.SUCCESS);
    }

    @Transactional(readOnly = true)
    public ResponseDto<Member.MemberInfoDto> checkMemberStatusAndReturn(Member member) {
        if (member.getStatus() == 'D') { // 회원 정보 업데이트 필요
            return new ResponseDto<>(Code.REQUIRED_UPDATE_MEMBER_INFO, member.convertMemberInfoResponse());
        } else {
            return new ResponseDto<>(Code.SUCCESS, member.convertMemberInfoResponse());
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
        log.info("password : {}", pwResetUuidDto.getUserEmail() == null ? "null" : pwResetUuidDto.getUserEmail());
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

    @Transactional(readOnly = true)
    public ResponseDto<Code> checkEmail(String email) {
        if (memberRepo.findOneByUserEmail(email).isPresent()){
            throw new CustomException(Code.EMAIL_DUPLICATE);
        } else {
            return new ResponseDto<>(Code.SUCCESS);
        }

    }
}
