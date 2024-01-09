package com.testus.testus.controller;

import com.testus.testus.service.MemberServiceImpl;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberServiceImpl memberService;

    @GetMapping("")
    public String test(){
        String s = memberService.signUp();
        return s;
    }
}
