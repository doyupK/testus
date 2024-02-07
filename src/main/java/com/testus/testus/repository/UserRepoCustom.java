package com.testus.testus.repository;

import com.testus.testus.domain.User;

public interface UserRepoCustom {

    void updateInfo(User.MemberInfoUpdateOrSignupDto memberInfoUpdateOrSignupDto, int userSeq, String encodePassword);

    void changePassword(int seq, String encode);

    void updateJoinAlarm(char joinTestAlarm, int userSeq);

    void reverseAlarmSetup(String field, User user);

}
