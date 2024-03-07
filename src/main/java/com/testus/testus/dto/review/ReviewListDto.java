package com.testus.testus.dto.review;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter @Setter @Builder
@NoArgsConstructor
public class ReviewListDto {
    private int seq;
    private boolean hasAnswer;
    private String contents;
    private String category;
    private String writer;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate createDate;

    public ReviewListDto(int seq, boolean hasAnswer, String contents, String category, String writer, LocalDate createDate) {
        this.seq = seq;
        this.hasAnswer = hasAnswer;
        this.contents = contents;
        this.category = category;
        this.writer = writer;
        this.createDate = createDate;
    }
}
