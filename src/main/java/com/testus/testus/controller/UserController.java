package com.testus.testus.controller;

import com.testus.testus.common.response.ResponseDto;
import com.testus.testus.common.response.exception.Code;
import com.testus.testus.config.security.UserDetailsImpl;
import com.testus.testus.domain.Post;
import com.testus.testus.domain.User;
import com.testus.testus.service.AuthService;
import com.testus.testus.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@Tag(name = "02. 회원 관련")
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    @GetMapping("/member/status/check")
    @Operation(summary = "회원 정보 조회 ( 상태값 조회 )")
    public ResponseEntity<ResponseDto<User.MemberInfoDto>> statusCheck(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity
                .ok()
                .body(userService.checkMemberStatusAndReturn(userDetails.getUser()));
    }

    @PutMapping("/member/info")
    @Operation(summary = "회원정보 업데이트")
    public ResponseEntity<ResponseDto<User.MemberInfoDto>> memberInfoUpdate(@RequestBody User.MemberInfoUpdateDto memberInfoUpdateOrSignupDto,
                                                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity
                .ok()
                .body(userService.updateInfo(memberInfoUpdateOrSignupDto, userDetails.getUser()));
    }

    @PostMapping("/member/check/pw")
    @Operation(summary = "비밀번호 확인")
    public ResponseEntity<ResponseDto<Code>> passwordCheck(@RequestBody User.PasswordCheckDto dto,
                                                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity
                .ok()
                .body(authService.checkPassword(dto, userDetails.getUser()));
    }


    @PutMapping("/member/alarm/{category}")
    @Operation(summary = "알림 수정")
    public ResponseEntity<ResponseDto<Code>> modifyMemberCommunityAlarm(@Parameter(example = "community, join, marketing")
                                                                        @PathVariable String category,
                                                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity
                .ok()
                .body(authService.reverseAlarm(category, userDetails.getUser()));
    }

    @GetMapping("/member/my/create/post")
    @Operation(summary = "내가 만든 TEST 조회")
    public ResponseEntity<ResponseDto<List<Post.MyPostDataResponse>>> getMyTest(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity
                .ok()
                .body(userService.getMyTest(userDetails.getUser()));
    }

    @GetMapping("/member/my/create/post/report")
    @Operation(summary = "내가 만든 TEST의 레포트 조회")
    public ResponseEntity<?> getMyTestReview(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                                      @RequestParam Boolean hasAnswer,
                                                                                      @RequestParam int size,
                                                                                      @RequestParam int page
    ) {
        return ResponseEntity
                .ok()
                .body(userService.getMyTestReview(userDetails.getUser()));
    }
}
