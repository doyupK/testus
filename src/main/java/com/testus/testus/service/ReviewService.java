package com.testus.testus.service;

import com.testus.testus.domain.User;
import com.testus.testus.dto.review.ReviewListDto;
import com.testus.testus.repository.ReviewRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepo reviewRepo;

    @Transactional(readOnly = true)
    public Page<ReviewListDto> getMyTestReview(User user, Pageable pageable) {
        return reviewRepo.getReviewByUserTest(user, pageable);
    }

    @Transactional(readOnly = true)
    public Long getNoAnswerReviewFromUserTest(User user) {
        return reviewRepo.getNoAnswerReviewFromUserTest(user);
    }
//    public Object getMyTestReview(User user) {
//
//    }
}
