package com.testus.testus.repository;

import com.testus.testus.domain.User;
import com.testus.testus.dto.review.ReviewListDto;

import java.util.List;

public interface ReviewRepoCustom {
    List<ReviewListDto> getReviewByUserTest(User user);

    Long getNoAnswerReviewFromUserTest(User user);

}
