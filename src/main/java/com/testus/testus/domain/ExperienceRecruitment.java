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
public class ExperienceRecruitment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq")
    private User createUser;
    @Column
    @Enumerated(EnumType.STRING)
    private ExperienceRecruitmentCategory category;
    @Column
    private int joinLimit;
    @Column
    private int currentJoinCount;
    @Column
    private String title;
    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate startDate;
    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate endDate;
    @Column
    private int star;
    @Column
    private int reviewCnt;
    @Column
    private int ageLimit;
    @Column
    private char genderType;
    @Column
    private char exposureType;
    @Column
    private String contents;
    @Column
    private String serviceUrl;
    @Column
    private String thumbnailUrl;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate createDate;



    @Getter
    @Setter
    @Builder
    public static class PostDataResponse {
        private String imgSrc;
        private String url;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyPostDataResponse {
        private int seq;
        private String thumbnailUrl;
        private String title;
        private int currentJoinCount;
        private long remainingDay;

        public MyPostDataResponse(int seq, String thumbnailUrl, String title, int currentJoinCount, LocalDate endDate) {
            this.seq = seq;
            this.thumbnailUrl = thumbnailUrl;
            this.title = title;
            this.currentJoinCount = currentJoinCount;
            this.remainingDay = Duration.between(LocalDate.now(), endDate).toDays();
        }
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreatePostDto {
        private boolean exposureType;
        private ExperienceRecruitmentCategory experienceRecruitmentCategory;
        private String title;
        private List<String> thumbnailUrl;
        private LocalDate startDate;
        private LocalDate endDate;
        private char genderType;
        private int age;
        private int joinLimit;
        private String testUrl;
        private char reportChannel;
        private String testContents;

    }

}
