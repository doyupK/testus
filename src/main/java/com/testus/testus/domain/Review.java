package com.testus.testus.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.testus.testus.enums.ReportReviewCategory;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Getter @Setter @Builder
@AllArgsConstructor @NoArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reviewSeq;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq")
    private User createUser;
    private String contents;
    private char answerYn;
    @Enumerated(EnumType.STRING)
    private ReportReviewCategory reportReviewCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_seq")
    private ExperienceRecruitment test;
    private int star;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate createDate;

}
