package com.testus.testus.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.testus.testus.enums.ReportCategory;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Getter @Setter @Builder
@AllArgsConstructor @NoArgsConstructor
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reportSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq")
    private User createUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "experience_recruitment_seq")
    private ExperienceRecruitment experienceRecruitment;

    @Enumerated(EnumType.STRING)
    private ReportCategory reportCategory;

    private char answerStatus;

    private String inquiryContents;
    private String answer;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate createDate;


}
