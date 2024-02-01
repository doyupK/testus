package com.testus.testus.repository;

import com.testus.testus.domain.Member;

public interface MemberRepoCustom {

    void updateInfo(Member.MemberInfoUpdateOrSignupDto memberInfoUpdateOrSignupDto, int userSeq, String encodePassword);

    void changePassword(int seq, String encode);

    void updateJoinAlarm(char joinTestAlarm, int userSeq);

    void reverseAlarmSetup(String field, Member member);

}
