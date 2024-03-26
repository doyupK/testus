package com.testus.testus.repository;

import com.testus.testus.domain.ExperienceRecruitment;
import com.testus.testus.domain.User;
import com.testus.testus.dto.post.ExperienceRecruitmentThumbnailDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ExperienceRecruitmentRepoCustom {
    List<ExperienceRecruitmentThumbnailDto> getLatestPost(String category);

    List<ExperienceRecruitmentThumbnailDto> getPopularPost(String category);

    Page<ExperienceRecruitment.MyPostDataResponse> getMyTest(User user, Pageable pageable);


    ExperienceRecruitment.TestListResponse getTestList(int lastId, String category, String tag, String sortBy);
    ExperienceRecruitment.TestListResponse getTestList(int lastId, String category, String tag, String sortBy, User user);

}
