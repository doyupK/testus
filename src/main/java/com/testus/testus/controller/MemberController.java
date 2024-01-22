package com.testus.testus.controller;

import com.testus.testus.service.MemberServiceImpl;
import com.testus.testus.service.OauthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MemberController {

    private final MemberServiceImpl memberService;
    private final OauthService oauthService;

    @GetMapping("")
    public String test(){
//        memberService.signUp(principal.getUserInfo().getName(), principal.getUserInfo().getNickname(), principal.getUserInfo().getAccessToken());
        return "s";
    }

    @GetMapping("/oauth2/authorization/{provider}")
    public ResponseEntity<?> oauthSignUp(HttpServletRequest request, HttpServletResponse response,  @PathVariable String provider, @RequestParam String redirect_url){
//        oauthService.signUpOauth(request, response, provider, redirectUrl);
        log.info("redirect URL : {} ", redirect_url);
        return null;
    }
}
