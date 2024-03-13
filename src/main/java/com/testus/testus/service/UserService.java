package com.testus.testus.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.testus.testus.common.response.ResponseDto;
import com.testus.testus.common.response.exception.Code;
import com.testus.testus.common.response.exception.CustomException;
import com.testus.testus.domain.ExperienceRecruitment;
import com.testus.testus.domain.User;
import com.testus.testus.dto.member.PwResetUuidDto;
import com.testus.testus.dto.review.MyPageReviewListResDto;
import com.testus.testus.dto.review.ReviewListDto;
import com.testus.testus.repository.UserRepo;
import com.testus.testus.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;
    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final RedisService redisService;
    private final ObjectMapper objectMapper;
    private final ExperienceRecruitmentService experienceRecruitmentService;
    private final ReviewService reviewService;

    @Transactional
    public ResponseDto<User.MemberInfoDto> updateInfo(User.MemberInfoUpdateDto memberInfoUpdateOrSignupDto, User user) {
        if (memberInfoUpdateOrSignupDto.getPassword() == null) {
            userRepo.updateInfo(memberInfoUpdateOrSignupDto, user.getUserSeq(), null);
        } else {
            userRepo.updateInfo(memberInfoUpdateOrSignupDto, user.getUserSeq(), passwordEncoder.encode(memberInfoUpdateOrSignupDto.getPassword()));
        }
        return new ResponseDto<>(Code.SUCCESS);
    }

    @Transactional(readOnly = true)
    public ResponseDto<User.MemberInfoDto> checkMemberStatusAndReturn(User user) {
        if (user.getStatus() == 'D') { // 회원 정보 업데이트 필요
            return new ResponseDto<>(Code.REQUIRED_UPDATE_MEMBER_INFO, user.convertMemberInfoResponse());
        } else {
            return new ResponseDto<>(Code.SUCCESS, user.convertMemberInfoResponse());
        }
    }


    @Transactional(readOnly = true)
    public ResponseDto<User.FindIdResponseDto> findId(User.FindIdRequestDto dto) {
        User user = userRepo.findOneByUserNameAndPhoneNumber(dto.getUserName(), dto.getPhoneNumber())
                .orElseThrow(
                        () -> new CustomException(Code.NOT_FOUND_USER)
                );

        User.FindIdResponseDto result =
                User.FindIdResponseDto.builder().userEmail(user.getUserEmail()).provider(user.getProviderType())
                        .build();
        return new ResponseDto<>(Code.SUCCESS, result);
    }

    @Transactional(readOnly = true)
    public ResponseDto<Code> resetPwMailSend(User.FindPwRequestDto dto) throws Exception {
        UUID uuid = UUID.randomUUID();

        User user = userRepo.findOneByUserEmail(dto.getUserEmail()).orElseThrow(
                () -> new CustomException(Code.NOT_FOUND_USER)
        );
        emailService.sendSimpleMessage(user.getUserEmail(), uuid);
        PwResetUuidDto pwResetUuidDto = PwResetUuidDto.builder()
                .userSeq(user.getUserSeq())
                .userEmail(user.getUserEmail())
                .build();
        redisService.setValues("RESET::"+uuid, pwResetUuidDto, Duration.ofMinutes(10));
        return new ResponseDto<>(Code.SUCCESS);
    }

    @Transactional
    public ResponseDto<Code> resetPw(User.ResetPwRequestDto dto) {
        Object values = redisService.getValues("RESET::" + dto.getUuid());
        PwResetUuidDto pwResetUuidDto = objectMapper.convertValue(values, PwResetUuidDto.class);
        log.info("password : {}", pwResetUuidDto.getUserEmail() == null ? "null" : pwResetUuidDto.getUserEmail());
        if (dto.getUserEmail().equals(pwResetUuidDto.getUserEmail())){
            User target = userRepo.findById(pwResetUuidDto.getUserSeq()).orElseThrow(
                    () -> new CustomException(Code.BAD_REQUEST)
            );
            userRepo.changePassword(target.getUserSeq(), passwordEncoder.encode(dto.getPassword()));
            redisService.deleteValues("RESET::" + dto.getUuid());
        } else {
            throw new CustomException(Code.BAD_REQUEST);
        }
        return new ResponseDto<>(Code.SUCCESS);
    }

    @Transactional(readOnly = true)
    public ResponseDto<Code> checkEmail(String email) {
        if (userRepo.findOneByUserEmail(email).isPresent()){
            throw new CustomException(Code.EMAIL_DUPLICATE);
        } else {
            return new ResponseDto<>(Code.SUCCESS);
        }

    }

    @Transactional(readOnly = true)
    public ResponseDto<Page<ExperienceRecruitment.MyPostDataResponse>> getMyCreateTest(User user, int size, int pageNo) {
        PageRequest pageRequest = PageRequest.of(pageNo, size);
        return new ResponseDto<>(Code.SUCCESS, experienceRecruitmentService.getMyTest(user, pageRequest));

    }

    @Transactional(readOnly = true)
    public Object getMyTestReview(User user, int size, int page) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return new ResponseDto<>(
                Code.SUCCESS,
                MyPageReviewListResDto.builder()
                        .reviewList(reviewService.getMyTestReview(user, pageRequest))
                        .count(reviewService.getNoAnswerReviewFromUserTest(user)));
    }

    public Object getMyJoinTest(User user, int size, int pageNo) {
        PageRequest pageRequest = PageRequest.of(pageNo, size);

        experienceRecruitmentService.getMyJoinTest(user, pageRequest);
        return null;
    }
}
