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
@Tag(name = "10. 공통으로 사용되는 API")
public class CommonController {

    private final MemberService memberService;
    private final AuthService authService;


    @PostMapping("/common/email/check")
    @Operation(summary = "이메일 중복체크", description = "이메일 중복체크 API")
    public ResponseEntity<ResponseDto<Code>> checkEmailDuplicate(@RequestParam String email) {
        return ResponseEntity
                .ok()
                .body(memberService.checkEmail(email));
    }

    @PostMapping("/common/member/id")
    @Operation(summary = "아이디 찾기", description = "아이디 찾기 API")
    public ResponseEntity<ResponseDto<Member.FindIdResponseDto>> findMemberID(@RequestBody Member.FindIdRequestDto dto){
        return ResponseEntity
                .ok()
                .body(memberService.findId(dto));
    }

    @PostMapping("/common/member/pw/mail")
    @Operation(summary = "비밀번호 재설정 메일발송", description = "비밀번호 메일발송 API")
    public ResponseEntity<ResponseDto<Code>> findMemberID(@RequestBody Member.FindPwRequestDto dto) throws Exception {
        return ResponseEntity
                .ok()
                .body(memberService.resetPwMailSend(dto));
    }

    @PostMapping("/common/member/pw")
    @Operation(summary = "비밀번호 재설정", description = "비밀번호 재설정 API")
    public ResponseEntity<ResponseDto<Code>> findMemberID(@RequestBody Member.ResetPwRequestDto dto) {
        return ResponseEntity
                .ok()
                .body(memberService.resetPw(dto));
    }


}