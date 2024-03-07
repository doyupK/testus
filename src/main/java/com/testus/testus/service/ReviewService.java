package com.testus.testus.service;

import com.testus.testus.domain.User;
import com.testus.testus.dto.review.ReviewListDto;
import com.testus.testus.repository.ReviewRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepo reviewRepo;

    public Object getMyTestReview(User user) {
        PageRequest pageRequest = PageRequest.of(0, 3);
        return reviewRepo.getReviewByUserTest(user, pageRequest);

    }
//    public Object getMyTestReview(User user) {
//
//    }
}
