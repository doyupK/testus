package com.testus.testus.service;

import com.testus.testus.domain.Member;
import com.testus.testus.enums.SocialType;
import com.testus.testus.repository.MemberRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
    private final MemberRepo memberRepo;
    @Override
    public void oauthSignUp(String name, String nickname, String email, String accessToken, SocialType type) {
        Member member = Member.builder()
                .providerType(type.getTypeName())
                .userPassword("asdfasdf")
                .userName(nickname)
                .userEmail("test@gmail.com")
                .providerSubject(accessToken)
                .status('D')
                .marketingYn('N')
                .build();
        memberRepo.save(member);
    }
}
