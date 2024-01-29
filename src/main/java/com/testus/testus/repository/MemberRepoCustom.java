package com.testus.testus.repository;

import com.testus.testus.domain.Member;

public interface MemberRepoCustom {

    void updateInfo(Member.MemberInfoUpdateDto memberInfoUpdateDto, int userSeq, String encodePassword);

}
