package com.testus.testus.dto.member;

import lombok.*;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PwResetUuidDto {
    private String userEmail;
    private int userSeq;
}
