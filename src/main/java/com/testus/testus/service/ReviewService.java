package com.testus.testus.service;

import com.testus.testus.domain.User;
import com.testus.testus.dto.review.ReviewListDto;
import com.testus.testus.repository.ReviewRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepo reviewRepo;

    public List<ReviewListDto> getMyTestReview(User user) {

        return reviewRepo.getReviewByUserTest(user);

    }

    public Long getNoAnswerReviewFromUserTest(User user) {
        return reviewRepo.getNoAnswerReviewFromUserTest(user);

    }
//    public Object getMyTestReview(User user) {
//
//    }
}
