package com.testus.testus.dto.review;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter @Setter @Builder
public class MyPageReportListResDto {
    private Long count;
    private Page<ReportListDto> reportList;
}
