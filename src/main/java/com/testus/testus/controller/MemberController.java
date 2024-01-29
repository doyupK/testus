package com.testus.testus.controller;

import com.testus.testus.common.response.ResponseDto;
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

    @PostMapping("/oauth/login/{provider}")
    @Operation(summary = "OAuth 로그인", description = "OAuth 로그인 API")
    public ResponseEntity<ResponseDto<Member.MemberInfoDto>> oauthLogin(@PathVariable String provider,
                                                                        @RequestParam String code,
                                                                        @RequestParam String redirect_url,
                                                                        HttpServletResponse response
    ){
        return ResponseEntity
                .ok()
                .body(memberService.oauthLogin(provider.toUpperCase(), code, redirect_url, response));
    }


    @GetMapping("/member/status/check")
    public ResponseEntity<ResponseDto<Member.MemberInfoDto>> statusCheck(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity
                .ok()
                .body(memberService.checkMemberStatus(userDetails.getMember()));
    }

    @PostMapping("/member/info")
    @Operation(summary = "회원정보 업데이트", description = "회원정보 업데이트용 API")
    public ResponseEntity<ResponseDto<Member.MemberInfoDto>> memberInfoUpdate(@RequestBody Member.MemberInfoUpdateDto memberInfoUpdateDto,
                                                                              @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity
                .ok()
                .body(memberService.updateInfo(memberInfoUpdateDto, userDetails.getMember()));
    }
}
