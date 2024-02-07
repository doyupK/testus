package com.testus.testus.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import com.testus.testus.domain.User;
import lombok.RequiredArgsConstructor;

import static com.testus.testus.domain.QUser.user;

@RequiredArgsConstructor
public class UserRepoImpl implements UserRepoCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public void updateInfo(User.MemberInfoUpdateOrSignupDto memberInfoUpdateOrSignupDto, int userSeq, String encodePassword) {
        if (encodePassword == null){
            queryFactory.update(user)
                    .set(user.userName, memberInfoUpdateOrSignupDto.getUserName())
                    .set(user.userEmail, memberInfoUpdateOrSignupDto.getUserEmail())
                    .set(user.status, 'Y')
                    .set(user.phoneNumber, memberInfoUpdateOrSignupDto.getPhoneNumber())
                    .set(user.marketingYn, memberInfoUpdateOrSignupDto.getMarketingYn())
                    .set(user.birthDay, memberInfoUpdateOrSignupDto.getBirthDay())
                    .where(user.userSeq.eq(userSeq))
                    .execute();
        } else {
            queryFactory.update(user)
                    .set(user.userPassword, encodePassword)
                    .set(user.userName, memberInfoUpdateOrSignupDto.getUserName())
                    .set(user.userEmail, memberInfoUpdateOrSignupDto.getUserEmail())
                    .set(user.status, 'Y')
                    .set(user.phoneNumber, memberInfoUpdateOrSignupDto.getPhoneNumber())
                    .set(user.marketingYn, memberInfoUpdateOrSignupDto.getMarketingYn())
                    .set(user.birthDay, memberInfoUpdateOrSignupDto.getBirthDay())
                    .where(user.userSeq.eq(userSeq))
                    .execute();
        }

    }

    @Override
    public void changePassword(int seq, String encode) {
        queryFactory.update(user)
                .set(user.userPassword, encode)
                .where(user.userSeq.eq(seq))
                .execute();
    }

    @Override
    public void updateJoinAlarm(char joinTestAlarm, int userSeq) {
        queryFactory.update(user)
                .set(user.joinTestAlarm, joinTestAlarm == 'Y' ? 'N' : 'Y')
                .where(user.userSeq.eq(userSeq))
                .execute();
    }

    @Override
    public void reverseAlarmSetup(String field, User targetUser) {

        JPAUpdateClause where = queryFactory.update(user)
                .where(user.userSeq.eq(targetUser.getUserSeq()));

        switch (field ) {
            case "community" -> where.set(user.communityMyPostAlarm, targetUser.getCommunityMyPostAlarm() == 'Y' ? 'N' : 'Y');
            case "join" -> where.set(user.joinTestAlarm, targetUser.getJoinTestAlarm() == 'Y' ? 'N' : 'Y');
            case "marketing" -> where.set(user.marketingYn, targetUser.getMarketingYn() == 'Y' ? 'N' : 'Y');
        }
        where.execute();


    }

}
