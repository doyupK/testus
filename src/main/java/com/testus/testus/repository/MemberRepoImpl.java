package com.testus.testus.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.testus.testus.domain.Member;
import lombok.RequiredArgsConstructor;

import static com.testus.testus.domain.QMember.member;

@RequiredArgsConstructor
public class MemberRepoImpl implements MemberRepoCustom {

    private final JPAQueryFactory queryFactory;



    @Override
    public void updateInfo(Member.MemberInfoUpdateDto memberInfoUpdateDto, int userSeq, String encodePassword) {
        if (encodePassword == null){
            queryFactory.update(member)
                    .set(member.userName, memberInfoUpdateDto.getUserName())
                    .set(member.userEmail, memberInfoUpdateDto.getUserEmail())
                    .set(member.status, 'Y')
                    .set(member.phoneNumber, memberInfoUpdateDto.getPhoneNumber())
                    .set(member.marketingYn, memberInfoUpdateDto.getMarketingYn())
                    .set(member.birthDay, memberInfoUpdateDto.getBirthDay())
                    .where(member.userSeq.eq(userSeq))
                    .execute();
        } else {
            queryFactory.update(member)
                    .set(member.userPassword, encodePassword)
                    .set(member.userName, memberInfoUpdateDto.getUserName())
                    .set(member.userEmail, memberInfoUpdateDto.getUserEmail())
                    .set(member.status, 'Y')
                    .set(member.phoneNumber, memberInfoUpdateDto.getPhoneNumber())
                    .set(member.marketingYn, memberInfoUpdateDto.getMarketingYn())
                    .set(member.birthDay, memberInfoUpdateDto.getBirthDay())
                    .where(member.userSeq.eq(userSeq))
                    .execute();
        }

    }
}
