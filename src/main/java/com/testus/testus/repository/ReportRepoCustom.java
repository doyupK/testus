package com.testus.testus.repository;

import com.testus.testus.domain.Report;
import com.testus.testus.domain.User;
import com.testus.testus.dto.review.ReportListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepoCustom {
    Page<ReportListDto> getUserCreateReportList(User user, Pageable pageable);
    Page<ReportListDto> getReportByUserTest(User user, Pageable pageable);

    Long getNoAnswerReviewFromUserTest(User user);

}
