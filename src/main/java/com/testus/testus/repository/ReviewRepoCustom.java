package com.testus.testus.repository;

import com.testus.testus.domain.User;
import com.testus.testus.dto.review.ReviewListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewRepoCustom {
    Page<ReviewListDto> getReviewByUserTest(User user, Pageable pageable);

    Long getNoAnswerReviewFromUserTest(User user);

}
