package com.testus.testus.service;

import com.testus.testus.domain.ExperienceRecruitment;
import com.testus.testus.domain.User;
import com.testus.testus.repository.TestJoinMapRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TestJoinMapService {
    private final TestJoinMapRepo testJoinMapRepo;


    @Transactional(readOnly = true)
    public Page<ExperienceRecruitment.MyPostDataResponse> getMyJoinTest(User user, PageRequest pageRequest) {
        Page<ExperienceRecruitment.MyPostDataResponse> userJoinTestList = testJoinMapRepo.getUserJoinTestList(user, pageRequest);
        return userJoinTestList;

    }
}
