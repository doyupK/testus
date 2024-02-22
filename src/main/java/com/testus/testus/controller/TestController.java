package com.testus.testus.controller;

import com.testus.testus.common.response.ResponseDto;
import com.testus.testus.common.response.exception.Code;
import com.testus.testus.domain.Test;
import com.testus.testus.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final TestService testService;



    @GetMapping("/latestPost")
    public ResponseEntity<ResponseDto<?>> getLatestPost() {
        List<Test.PostDataResponse> res = new ArrayList<>();
        for(int i=0; i<4; i++){
            res.add(Test.PostDataResponse.builder()
                    .imgSrc("/tempPost.png")
                    .url("https://naver.com")
                    .build());
        }

        return ResponseEntity
                .ok()
                .body(new ResponseDto<>(Code.SUCCESS, res));

    }
}
