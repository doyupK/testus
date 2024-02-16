package com.testus.testus.controller;

import com.testus.testus.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dev")
@Slf4j
public class DevController {

    @PostMapping("/login")
    public String test(@RequestBody User.LoginDto dto){
        log.info(dto.getUserEmail());
        log.info(dto.getPassword());
        return null;
    }
}
