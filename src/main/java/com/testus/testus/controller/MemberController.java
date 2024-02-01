package com.testus.testus.controller;

import com.testus.testus.common.response.ResponseDto;
import com.testus.testus.common.response.exception.Code;
import com.testus.testus.config.security.UserDetailsImpl;
import com.testus.testus.domain.Member;
import com.testus.testus.service.AuthService;
import com.testus.testus.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
@Tag(name = "02. 회원 관련")
public class MemberController {

    private final MemberService memberService;
    private final AuthService authService;

    @GetMapping("/member/status/check")
    @Operation(summary = "회원 정보 조회 ( 상태값 조회 )", description = "회원 정보 조회 ( 상태 값 조회 API )")
    public ResponseEntity<ResponseDto<Member.MemberInfoDto>> statusCheck(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity
                .ok()
                .body(memberService.checkMemberStatusAndReturn(userDetails.getMember()));
    }

    @PutMapping("/member/info")
    @Operation(summary = "회원정보 업데이트", description = "회원정보 업데이트용 API")
    public ResponseEntity<ResponseDto<Member.MemberInfoDto>> memberInfoUpdate(@RequestBody Member.MemberInfoUpdateOrSignupDto memberInfoUpdateOrSignupDto,
                                                                              @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity
                .ok()
                .body(memberService.updateInfo(memberInfoUpdateOrSignupDto, userDetails.getMember()));
    }

    @PostMapping("/member/check/pw")
    @Operation(summary = "비밀번호 확인", description = "비밀번호 확인 API")
    public ResponseEntity<ResponseDto<Code>> passwordCheck(@RequestBody Member.PasswordCheckDto dto,
                                                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity
                .ok()
                .body(authService.checkPassword(dto, userDetails.getMember()));
    }


    @PutMapping("/member/alarm/{category}")
    @Operation(summary = "알림 수정", description = "알림 수정 API")
    public ResponseEntity<ResponseDto<Code>> modifyMemberCommunityAlarm(@Parameter(example = "community, join, marketing")
                                                                        @PathVariable String category,
                                                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity
                .ok()
                .body(authService.reverseAlarm(category, userDetails.getMember()));
    }


}
