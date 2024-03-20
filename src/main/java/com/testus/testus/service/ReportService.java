package com.testus.testus.service;

import com.testus.testus.domain.User;
import com.testus.testus.dto.review.ReportListDto;
import com.testus.testus.repository.ReportRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepo reportRepo;

    @Transactional(readOnly = true)
    public Page<ReportListDto> getMyTestReport(User user, Pageable pageable) {
        return reportRepo.getReportByUserTest(user, pageable);
    }

    @Transactional(readOnly = true)
    public Long getNoAnswerReportFromUserTest(User user) {
        return reportRepo.getNoAnswerReviewFromUserTest(user);
    }

    @Transactional(readOnly = true)
    public Page<ReportListDto> getUserCreateReportList(User user, PageRequest pageRequest) {
        return reportRepo.getUserCreateReportList(user, pageRequest);
    }
}
