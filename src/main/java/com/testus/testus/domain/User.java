package com.testus.testus.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import java.time.LocalDate;

@Getter
@Builder @AllArgsConstructor @NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userSeq;

    @Column
    private String userName;

    @Column(nullable = false)
    private String providerType;

    @Column
    private String providerSubject;

    @Column
    private String userEmail;

    @Column
    private String phoneNumber;

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate birthDay;

    @JsonIgnore
    @Column
    private String userPassword;

    @Column
    private char status;

    @Column
    private char marketingYn;
    @Column
    private char joinTestAlarm;
    @Column
    private char communityMyPostAlarm;

    @Column(nullable = false)
    private String role;

    @PrePersist
    public void prePersist(){
        this.marketingYn = 'N';
        this.joinTestAlarm = 'Y';
        this.communityMyPostAlarm = 'Y';
    }


    public MemberInfoDto convertMemberInfoResponse() {
        return MemberInfoDto.builder()
                .userSeq(this.userSeq)
                .userName(this.userName)
                .providerType(this.providerType)
                .userEmail(this.userEmail)
                .marketingYn(this.marketingYn)
                .joinTestAlarm(this.joinTestAlarm)
                .communityMyPostAlarm(this.communityMyPostAlarm)
                .status(this.status)
                .build();
    }


    @Getter @Setter
    @Builder
    public static class MemberInfoDto {
        private int userSeq;
        private String userName;
        private String providerType;
        private String userEmail;
        private char marketingYn;
        private char joinTestAlarm;
        private char communityMyPostAlarm;
        private char status;
    }

    @Getter @Setter
    @Builder
    public static class MemberInfoUpdateOrSignupDto {
        private String userName;
        private String phoneNumber;
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate birthDay;
        private String gender;
        private String userEmail;
        @Nullable
        private String password;
        private char marketingYn;
    }
    @Getter @Setter
    @Builder
    public static class LoginDto {
        private String userEmail;
        private String password;
    }

    @Getter @Setter
    @Builder
    public static class FindIdRequestDto {
        private String userName;
        private String phoneNumber;
    }
    @Getter @Setter
    @Builder
    public static class FindIdResponseDto {
        private String userEmail;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FindPwRequestDto {
        private String userEmail;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResetPwRequestDto {
        private String userEmail;
        private String uuid;
        private String password;
    }

    @Getter @Setter
    @Builder
    public static class PasswordCheckDto {
        private String password;
    }
}
