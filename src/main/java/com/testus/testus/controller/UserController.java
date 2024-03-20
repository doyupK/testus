package com.testus.testus.controller;

import com.testus.testus.common.response.ResponseDto;
import com.testus.testus.common.response.exception.Code;
import com.testus.testus.config.security.UserDetailsImpl;
import com.testus.testus.domain.ExperienceRecruitment;
import com.testus.testus.domain.User;
import com.testus.testus.dto.review.MyPageReportListResDto;
import com.testus.testus.dto.review.ReportListDto;
import com.testus.testus.service.AuthService;
import com.testus.testus.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ResponseDto<Page<ExperienceRecruitment.MyPostDataResponse>>> getMyCreateTest(@RequestParam int size,
                                                                                                       @RequestParam int pageNo,
                                                                                                       @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity
                .ok()
                .body(userService.getMyCreateTest(userDetails.getUser(), size, pageNo));
    }

    @GetMapping("/member/my/create/post/report")
    @Operation(summary = "내가 만든 TEST의 레포트 조회")
    public ResponseEntity<ResponseDto<MyPageReportListResDto>> getMyTestReview(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                               @RequestParam int size,
                                                                               @RequestParam int page
    ) {
        return ResponseEntity
                .ok()
                .body(userService.getMyTestReport(userDetails.getUser(), size, page));
    }

    @GetMapping("/member/my/join/post")
    @Operation(summary = "내가 참여한 TEST 조회")
    public ResponseEntity<ResponseDto<Page<ExperienceRecruitment.MyPostDataResponse>>> getMyJoinTest(@RequestParam int size,
                                           @RequestParam int pageNo,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity
                .ok()
                .body(userService.getMyJoinTest(userDetails.getUser(), size, pageNo));
    }

    @GetMapping("/member/my/create/report")
    @Operation(summary = "내가 작성한 리포트 조회")
    public ResponseEntity<ResponseDto<Page<ReportListDto>>> getMyCreateReport(@RequestParam int size,
                                                                              @RequestParam int pageNo,
                                                                              @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity
                .ok()
                .body(userService.getMyCreateReport(userDetails.getUser(), size, pageNo));
    }
}
