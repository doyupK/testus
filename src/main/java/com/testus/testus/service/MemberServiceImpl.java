package com.testus.testus.service;

import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService{
    @Override
    public String signUp() {
        return "ab";
    }
}
