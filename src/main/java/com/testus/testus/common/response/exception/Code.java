package com.testus.testus.common.response.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum Code {

    //2xx
    SUCCESS                     (HttpStatus.OK, "E20000","Success"),
    LOGIN_SUCCESS               (HttpStatus.OK,"E20002","Access Token 발행"),
    TOKEN_REFRESH               (HttpStatus.OK,"E20003","Access, Refresh Token 재발행"),
    REQUIRED_UPDATE_MEMBER_INFO (HttpStatus.OK,"E20004","유저 정보 추가 필요"),

    // 3xx Redirection

    // 400 Bad Request
    BAD_REQUEST                 (HttpStatus.OK, "E40000","올바른 요청이 아닙니다."),
    ALREADY_MEMBER              (HttpStatus.OK, "E40001", "중복된 이메일이 있습니다."),
    NULL_CONTENT                (HttpStatus.OK, "E40002","내용을 입력해 주세요"),
    EMAIL_DUPLICATE             (HttpStatus.OK, "E40003", "이메일 중복"),
    EXIST_USER                  (HttpStatus.OK, "E40004", "이미 존재하는 회원입니다."),
    VALIDATION_ERROR            (HttpStatus.OK, "E40005", "올바른 형식을 입력해주세요."),
    PASSWORD_UNMATCHED          (HttpStatus.OK, "E40006", "아이디/비밀번호 불일치\n 확인 후 다시 로그인하세요."),
    REQUIRED_TERMS_CONDITIONS   (HttpStatus.OK, "E40016", "필수 약관은 동의 후 가입 가능합니다."),

    //401 Authentication
    EXPIRED_ACCESS_JWT          (HttpStatus.OK,"E40101","JWT ACCESS 시간이 만료되었습니다."),
    AUTHENTICATION_FAILURE_JWT  (HttpStatus.OK,"E40102","올바른 JWT 정보가 아닙니다."),
    EXPIRED_REFRESH_JWT         (HttpStatus.OK,"E40103","JWT REFRESH 시간이 만료되었습니다."),
    //402 Payment Required (결제 시스템에 사용하기 위해 만들어졌지만 지금은 사용되고 있지 않다.)

    //403 Authorization
    PENALTY_USER                (HttpStatus.OK,"E40301","제제된 사용자입니다."),
    NO_AUTHORIZATION            (HttpStatus.OK, "E40302", "권한이 없습니다"),
    NO_AUTHENTICATION           (HttpStatus.OK, "E40303", "수정 권한이 없습니다."),
    SECURITY_FILTER_ERROR       (HttpStatus.OK, "E40304", "JWT 에러" ),

    // 404 Not Found
    NOT_FOUND_USER              (HttpStatus.OK, "E40404", "유저를 찾지 못했습니다."),
    NOT_FOUND_API               (HttpStatus.OK,"E40406","없는 API 입니다."),

    // 415 Media file
    INTERNAL_SERVER_ERROR       (HttpStatus.INTERNAL_SERVER_ERROR,"E50000", "통신중 예기치않은 오류가 발생하였습니다.\n 관리자에게 문의 부탁드립니다."),



    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
