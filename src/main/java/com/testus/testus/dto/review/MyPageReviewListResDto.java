package com.testus.testus.dto.review;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter @Builder
public class MyPageReviewListResDto {
    private Long count;
    private List<ReviewListDto> reviewList;
}
