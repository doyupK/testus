package com.testus.testus.service;

import com.testus.testus.domain.User;
import com.testus.testus.repository.ReviewRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepo reviewRepo;
//    public Object getMyTestReview(User user) {
//
//    }
}
