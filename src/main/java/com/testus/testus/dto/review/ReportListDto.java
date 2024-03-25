package com.testus.testus.dto.review;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.testus.testus.enums.ReportCategory;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
public class ReportListDto {
    private int seq;
    private boolean hasAnswer;
    private String contents;
    private String category;
    private String writer;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate createDate;

    public ReportListDto(int seq, boolean hasAnswer, String contents, ReportCategory category, String writer, LocalDate createDate) {
        this.seq = seq;
        this.hasAnswer = hasAnswer;
        this.contents = contents;
        this.category = category.getTypeName();
        this.writer = writer;
        this.createDate = createDate;
    }
}
