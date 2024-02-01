package com.testus.testus.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
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

    @Override
    public void changePassword(int seq, String encode) {
        queryFactory.update(member)
                .set(member.userPassword, encode)
                .where(member.userSeq.eq(seq))
                .execute();
    }

    @Override
    public void updateJoinAlarm(char joinTestAlarm, int userSeq) {
        queryFactory.update(member)
                .set(member.joinTestAlarm, joinTestAlarm == 'Y' ? 'N' : 'Y')
                .where(member.userSeq.eq(userSeq))
                .execute();
    }

    @Override
    public void reverseAlarmSetup(String field, Member targetMember) {

        JPAUpdateClause where = queryFactory.update(member)
                .where(member.userSeq.eq(targetMember.getUserSeq()));

        switch (field ) {
            case "community" -> where.set(member.communityMyPostAlarm, targetMember.getCommunityMyPostAlarm() == 'Y' ? 'N' : 'Y');
            case "join" -> where.set(member.joinTestAlarm, targetMember.getJoinTestAlarm() == 'Y' ? 'N' : 'Y');
            case "marketing" -> where.set(member.marketingYn, targetMember.getMarketingYn() == 'Y' ? 'N' : 'Y');
        }
        where.execute();


    }

}
