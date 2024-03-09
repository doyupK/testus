package com.testus.testus.repository;

import com.testus.testus.domain.ExperienceRecruitment;
import com.testus.testus.domain.User;
import com.testus.testus.dto.post.ExperienceRecruitmentThumbnailDto;

import java.util.List;

public interface ExperienceRecruitmentRepoCustom {
    List<ExperienceRecruitmentThumbnailDto> getLatestPost(String category);

    List<ExperienceRecruitmentThumbnailDto> getPopularPost(String category);

    List<ExperienceRecruitment.MyPostDataResponse> getMyTest(User user);

}
