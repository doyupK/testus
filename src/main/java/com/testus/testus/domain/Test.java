package com.testus.testus.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.testus.testus.enums.TestCategory;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Getter @Setter @Builder
@AllArgsConstructor
@NoArgsConstructor
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq")
    private User createUser;
    @Column
    private TestCategory category;
    @Column
    private int joinLimit;
    @Column
    private String title;
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
    private String contents;
    @Column
    private String serviceUrl;
    @Column
    private String thumbnailUrl;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate createDate;


}
