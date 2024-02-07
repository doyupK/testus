package com.testus.testus.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.testus.testus.common.oauth.userInfo.OAuth2UserInfo;
import com.testus.testus.common.oauth.userInfo.OAuth2UserInfoFactory;
import com.testus.testus.common.response.ResponseDto;
import com.testus.testus.common.response.exception.Code;
import com.testus.testus.common.response.exception.CustomException;
import com.testus.testus.domain.User;
import com.testus.testus.repository.UserRepo;
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
    private final UserRepo userRepo;
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final RedisService redisService;
    private final ObjectMapper objectMapper;



    @Transactional
    public ResponseDto<User.MemberInfoDto> oauthLogin(String provider, String code, String redirectUrl, HttpServletResponse response) {

        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(provider, code, redirectUrl);
        Optional<User> findUser = userRepo.findByProviderSubject(userInfo.getId());

        User user;

        if (findUser.isPresent()) { // 기존 회원
            user = findUser.get();
            jwtTokenUtil.addJwtTokenInHeader(user.getUserSeq(), response);
        } else { // 신규 회원
            user = User.builder()
                    .userName(userInfo.getName())
                    .userEmail(userInfo.getEmail())
                    .userPassword(null)
                    .providerType(provider)
                    .providerSubject(userInfo.getId())
                    .role("ROLE_USER")
                    .status('D')
                    .build();
            user = userRepo.save(user);
        }
        return userService.checkMemberStatusAndReturn(user);
    }


    @Transactional
    public ResponseDto<Code> signup(User.MemberInfoUpdateOrSignupDto dto) {
        Optional<User> oneByUserEmail = userRepo.findOneByUserEmail(dto.getUserEmail());
        if (oneByUserEmail.isPresent()) {
            throw new CustomException(Code.ALREADY_MEMBER);
        } else {
            User user = User.builder()
                    .providerType("TESTUS")
                    .userPassword(passwordEncoder.encode(dto.getPassword()))
                    .userName(dto.getUserName())
                    .userEmail(dto.getUserEmail())
                    .status('Y')
                    .role("ROLE_USER")
                    .marketingYn(dto.getMarketingYn())
                    .build();
            userRepo.save(user);
            return new ResponseDto<>(Code.SUCCESS);
        }
    }

    @Transactional
    public ResponseDto<User.MemberInfoDto> login(User.LoginDto dto, HttpServletResponse response) {
        User user = userRepo.findOneByUserEmail(dto.getUserEmail()).orElseThrow(
                () -> new CustomException(Code.NOT_FOUND_USER)
        );
        if (passwordEncoder.matches(dto.getPassword(), user.getUserPassword())) {
            jwtTokenUtil.addJwtTokenInHeader(user.getUserSeq(), response);
            return userService.checkMemberStatusAndReturn(user);
        } else {
            throw new CustomException(Code.PASSWORD_UNMATCHED);
        }
    }

    @Transactional(readOnly = true)
    public ResponseDto<Code> checkPassword(User.PasswordCheckDto dto, User user) {
        if (passwordEncoder.matches(dto.getPassword(), user.getUserPassword())){
            return new ResponseDto<>(Code.SUCCESS);
        } else {
            throw new CustomException(Code.PASSWORD_UNMATCHED);
        }
    }


    @Transactional
    public ResponseDto<Code> reverseAlarm(String category, User user) {

        userRepo.reverseAlarmSetup(category, user);
        return new ResponseDto<>(Code.SUCCESS);
    }
}
