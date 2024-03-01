package com.testus.testus.controller;

import com.testus.testus.config.security.UserDetailsImpl;
import com.testus.testus.domain.User;
import com.testus.testus.dto.dev.Dto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/dev")
@Slf4j
public class DevController {

    @PostMapping("/login")
    public String test(@RequestBody User.LoginDto dto, @AuthenticationPrincipal UserDetailsImpl details){
        log.info(details.getUser().getUserName());
        log.info(dto.getUserEmail());
        log.info(dto.getPassword());
        return null;
    }

    @GetMapping("/ssr")
    public Dto testSsr(){
        log.info("test api ");
        Dto dto = new Dto();
        dto.setSsr("test123444444");
        return dto;

    }
}
