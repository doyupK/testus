package com.testus.testus.dto.post;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExperienceRecruitmentThumbnailDto {
    private Long id;
    private String title;
    private String imgUrl;
    private Integer star;
    private Integer remainCnt;
    private Integer remainDay;
    private Integer reviewCnt;

    public ExperienceRecruitmentThumbnailDto(Long id, String title, String imgUrl) {
        this.id = id;
        this.title = title;
        this.imgUrl = imgUrl;
    }
    public ExperienceRecruitmentThumbnailDto(Long id, String title, String imgUrl,
                                             LocalDate endDate,
                                             int currentJoinCount, int joinLimit,
                                             int star, int remainCnt) {
        this.id = id;
        this.title = title;
        this.imgUrl = imgUrl;
        this.star = star;
        this.remainCnt = joinLimit - currentJoinCount;
        this.remainDay = Period.between(LocalDate.now(), endDate).getDays();
        this.reviewCnt = remainCnt;
    }
}
