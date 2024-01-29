package com.testus.testus.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.testus.testus.domain.Member;
import lombok.RequiredArgsConstructor;

import static com.testus.testus.domain.QMember.member;

@RequiredArgsConstructor
public class MemberRepoImpl implements MemberRepoCustom {

    private final JPAQueryFactory queryFactory;



    @Override
    public void updateInfo(Member.MemberInfoUpdateOrSignupDto memberInfoUpdateOrSignupDto, int userSeq, String encodePassword) {
        if (encodePassword == null){
            queryFactory.update(member)
                    .set(member.userName, memberInfoUpdateOrSignupDto.getUserName())
                    .set(member.userEmail, memberInfoUpdateOrSignupDto.getUserEmail())
                    .set(member.status, 'Y')
                    .set(member.phoneNumber, memberInfoUpdateOrSignupDto.getPhoneNumber())
                    .set(member.marketingYn, memberInfoUpdateOrSignupDto.getMarketingYn())
                    .set(member.birthDay, memberInfoUpdateOrSignupDto.getBirthDay())
                    .where(member.userSeq.eq(userSeq))
                    .execute();
        } else {
            queryFactory.update(member)
                    .set(member.userPassword, encodePassword)
                    .set(member.userName, memberInfoUpdateOrSignupDto.getUserName())
                    .set(member.userEmail, memberInfoUpdateOrSignupDto.getUserEmail())
                    .set(member.status, 'Y')
                    .set(member.phoneNumber, memberInfoUpdateOrSignupDto.getPhoneNumber())
                    .set(member.marketingYn, memberInfoUpdateOrSignupDto.getMarketingYn())
                    .set(member.birthDay, memberInfoUpdateOrSignupDto.getBirthDay())
                    .where(member.userSeq.eq(userSeq))
                    .execute();
        }

    }
}
