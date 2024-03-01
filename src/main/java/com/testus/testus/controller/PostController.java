package com.testus.testus.controller;

import com.testus.testus.common.response.ResponseDto;
import com.testus.testus.dto.post.PostThumbnailDto;
import com.testus.testus.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/post")
public class PostController {
    private final PostService postService;


    @GetMapping("/latest")
    @Operation(summary = "최신 테스트 게시글 조회(6개)")
    public ResponseEntity<ResponseDto<List<PostThumbnailDto>>> getLatestPost(@RequestParam String category) {
        return ResponseEntity
                .ok()
                .body(postService.getLatestPost(category));
    }

    @GetMapping("/popular")
    @Operation(summary = "인기 테스트 게시글 조회(6개)")
    public ResponseEntity<ResponseDto<List<PostThumbnailDto>>> getPopularPost(@RequestParam String category) {

        return ResponseEntity
                .ok()
                .body(postService.getPopularPost(category));

    }

}
