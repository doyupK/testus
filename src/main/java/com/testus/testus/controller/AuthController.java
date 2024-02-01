package com.testus.testus.controller;

import com.testus.testus.common.response.ResponseDto;
import com.testus.testus.common.response.exception.Code;
import com.testus.testus.domain.Member;
import com.testus.testus.service.AuthService;
import com.testus.testus.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@Tag(name = "01. 인증 관련")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    @Operation(summary = "일반 회원가입", description = "일반 회원가입 API")
    public ResponseEntity<ResponseDto<Code>> signUp(@RequestBody Member.MemberInfoUpdateOrSignupDto dto){
        return ResponseEntity
                .ok()
                .body(authService.signup(dto));
    }
    @PostMapping("/login")
    @Operation(summary = "일반 로그인", description = "일반 로그인 API")
    public ResponseEntity<ResponseDto<Member.MemberInfoDto>> login(@RequestBody Member.LoginDto dto,
                                                                   HttpServletResponse response){
        return ResponseEntity
                .ok()
                .body(authService.login(dto, response));
    }

    @PostMapping("/oauth/login/{provider}")
    @Operation(summary = "OAuth 로그인", description = "OAuth 로그인 API")
    public ResponseEntity<ResponseDto<Member.MemberInfoDto>> oauthLogin(@PathVariable String provider,
                                                                        @RequestParam String code,
                                                                        @RequestParam String redirectUrl,
                                                                        HttpServletResponse response
    ){
        return ResponseEntity
                .ok()
                .body(authService.oauthLogin(provider.toUpperCase(), code, redirectUrl, response));
    }




}
