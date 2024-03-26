package com.testus.testus.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.testus.testus.enums.ExperienceRecruitmentCategory;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter @Setter @Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExperienceRecruitmentImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "experience_recruitment_seq")
    private ExperienceRecruitment experienceRecruitment;

    @Column
    private String thumbnailUrl;


    @Getter
    @Setter
    @Builder
    public static class PostDataResponse {
        private String imgSrc;
        private String url;
    }


}
