package com.testus.testus.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.testus.testus.common.response.ResponseDto;
import com.testus.testus.common.response.exception.Code;
import com.testus.testus.config.security.UserDetailsImpl;
import com.testus.testus.domain.ExperienceRecruitment;
import com.testus.testus.dto.post.ExperienceRecruitmentThumbnailDto;
import com.testus.testus.service.ExperienceRecruitmentService;
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
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/test")
@Tag(name = "03. 테스트 관련")
public class ExperienceRecruitmentController {
    private final ExperienceRecruitmentService experienceRecruitmentService;
    private final UserService userService;


    @GetMapping("/latest")
    @Operation(summary = "최신 테스트 게시글 조회(6개)")
    public ResponseEntity<ResponseDto<List<ExperienceRecruitmentThumbnailDto>>> getLatestPost(
            @Parameter(example = "APP or SERVICE or GAME") @RequestParam String category) {
        return ResponseEntity
                .ok()
                .body(experienceRecruitmentService.getLatestPost(category));
    }

    @GetMapping("/popular")
    @Operation(summary = "인기 테스트 게시글 조회(6개)")
    public ResponseEntity<ResponseDto<List<ExperienceRecruitmentThumbnailDto>>> getPopularPost(
            @Parameter(example = "APP or SERVICE or GAME") @RequestParam String category) {

        return ResponseEntity
                .ok()
                .body(experienceRecruitmentService.getPopularPost(category));

    }

    @PostMapping("/")
    @Operation(summary = "테스트 생성")
    public ResponseEntity<?> createTest(@RequestBody ExperienceRecruitment.CreatePostDto dto,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) throws JsonProcessingException {

        return ResponseEntity
                .ok()
                .body(experienceRecruitmentService.createRecruitment(dto, userDetails.getUser()));

    }

    @PostMapping("/add/tester")
    @Operation(summary = "테스터 추가")
    public ResponseEntity<ResponseDto<Code>> createTest(@RequestBody ExperienceRecruitment.AddTesterDto dto,
                                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return ResponseEntity
                .ok()
                .body(experienceRecruitmentService.addTester(dto, userDetails.getUser()));
    }

}
