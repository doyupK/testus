package com.testus.testus.controller;

import com.testus.testus.common.response.ResponseDto;
import com.testus.testus.common.response.exception.Code;
import com.testus.testus.config.security.UserDetailsImpl;
import com.testus.testus.domain.Member;
import com.testus.testus.service.MemberServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@Tag(name = "01. 인증")
public class MemberController {

    private final MemberServiceImpl memberService;

    @PostMapping("/signup")
    @Operation(summary = "일반 회원가입", description = "일반 회원가입 API")
    public ResponseEntity<ResponseDto<Code>> signUp(@RequestBody Member.MemberInfoUpdateOrSignupDto dto){
        return ResponseEntity
                .ok()
                .body(memberService.signup(dto));
    }

    @PostMapping("/login")
    @Operation(summary = "일반 로그인", description = "일반 로그인 API")
    public ResponseEntity<ResponseDto<Member.MemberInfoDto>> login(@RequestBody Member.LoginDto dto,
                                                                   HttpServletResponse response){
        return ResponseEntity
                .ok()
                .body(memberService.login(dto, response));
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
                .body(memberService.oauthLogin(provider.toUpperCase(), code, redirectUrl, response));
    }


    @GetMapping("/member/status/check")
    @Operation(summary = "회원 정보 조회 ( 상태값 조회 )", description = "회원 정보 조회 ( 상태 값 조회 API )")
    public ResponseEntity<ResponseDto<Member.MemberInfoDto>> statusCheck(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity
                .ok()
                .body(memberService.checkMemberStatusAndReturn(userDetails.getMember()));
    }

    @PutMapping("/member/info")
    @Operation(summary = "회원정보 업데이트", description = "회원정보 업데이트용 API")
    public ResponseEntity<ResponseDto<Member.MemberInfoDto>> memberInfoUpdate(@RequestBody Member.MemberInfoUpdateOrSignupDto memberInfoUpdateOrSignupDto,
                                                                              @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity
                .ok()
                .body(memberService.updateInfo(memberInfoUpdateOrSignupDto, userDetails.getMember()));
    }

    @PutMapping("/member/find/id")
    @Operation(summary = "아이디 찾기", description = "아이디 찾기 API")
    public ResponseEntity<ResponseDto<Member.FindIdResponseDto>> findMemberID(@RequestBody Member.FindIdRequestDto dto){
        return ResponseEntity
                .ok()
                .body(memberService.findId(dto));
    }

}
