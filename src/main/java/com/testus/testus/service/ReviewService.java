package com.testus.testus.service;

import com.testus.testus.domain.User;
import com.testus.testus.dto.review.ReportListDto;
import com.testus.testus.repository.ReviewRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepo reviewRepo;




//    public Object getMyTestReview(User user) {
//
//    }
}
