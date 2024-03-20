package com.testus.testus.repository;

import com.testus.testus.domain.ExperienceRecruitment;
import com.testus.testus.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public interface TestJoinMapRepoCustom {
    Page<ExperienceRecruitment.MyPostDataResponse> getUserJoinTestList(User user, Pageable pageable);

}
