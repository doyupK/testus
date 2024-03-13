package com.testus.testus.dto.review;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter @Setter @Builder
public class MyPageReviewListResDto {
    private Long count;
    private Page<ReviewListDto> reviewList;
}
