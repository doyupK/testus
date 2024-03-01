package com.testus.testus.dto.post;

import lombok.*;

@Getter @Setter @Builder
@AllArgsConstructor @NoArgsConstructor
public class PostThumbnailDto {
    private Long id;
    private String title;
    private String imgUrl;
}
