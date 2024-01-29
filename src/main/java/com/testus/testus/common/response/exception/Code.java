package com.testus.testus.common.response.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum Code {

    //2xx
    SUCCESS                     (HttpStatus.OK, "E20000","Success"),
    CREATED                     (HttpStatus.CREATED, "E20001","Create Success"),
    LOGIN_SUCCESS               (HttpStatus.OK,"E20002","Access Token 발행"),
    TOKEN_REFRESH               (HttpStatus.OK,"E20003","Access, Refresh Token 재발행"),
    REQUIRED_UPDATE_MEMBER_INFO (HttpStatus.OK,"E20004","유저 정보 추가 필요"),

    // 3xx Redirection

    // 400 Bad Request
    BAD_REQUEST                 (HttpStatus.BAD_REQUEST, "E40000","올바른 요청이 아닙니다."),
    ALREADY_MEMBER              (HttpStatus.BAD_REQUEST, "E40001", "중복된 이메일이 있습니다."),
    NULL_CONTENT                (HttpStatus.BAD_REQUEST, "E40002","내용을 입력해 주세요"),
    EXIST_USER                  (HttpStatus.BAD_REQUEST, "E40004", "이미 존재하는 회원입니다."),
    VALIDATION_ERROR            (HttpStatus.BAD_REQUEST, "E40005", "올바른 형식을 입력해주세요."),
    PASSWORD_UNMATCHED          (HttpStatus.BAD_REQUEST, "E40006", "아이디/비밀번호 불일치\n 확인 후 다시 로그인하세요."),
    NOT_LOGIN                   (HttpStatus.BAD_REQUEST, "E40007", "로그인이 필요한 서비스입니다."),
    PASSWORD_VALID_ERROR        (HttpStatus.BAD_REQUEST, "E40009", "비밀번호와 비밀번호 확인이 맞지 않습니다."),
    REQUIRED_TERMS_CONDITIONS   (HttpStatus.BAD_REQUEST, "E40016", "필수 약관은 동의 후 가입 가능합니다."),
    PASSWORD_FIVE_UNMATCH       (HttpStatus.BAD_REQUEST, "E40017", "아이디/비밀번호 불일치\n 로그인 5회 실패하여 접속이 차단됩니다. 실패 횟수 : (5/5)"),
    DUPLICATE_RESTRICTION_DATE  (HttpStatus.BAD_REQUEST, "E40018", "이미 등록/대기중인 제재가 있습니다."),
    DUPLICATE_CATEGORY          (HttpStatus.BAD_REQUEST, "E40019", "중복된 카테고리 입니다."),
    EXIST_CATEGORY_PRODUCT      (HttpStatus.BAD_REQUEST, "E40020", "카테고리에 연결된 상품이 있습니다."),
    EXIST_DATA      (HttpStatus.BAD_REQUEST, "E40021", "중복된 데이터 입니다."),

    //401 Authentication
    EXPIRED_ACCESS_JWT          (HttpStatus.BAD_REQUEST,"E40101","JWT ACCESS 시간이 만료되었습니다."),
    AUTHENTICATION_FAILURE_JWT  (HttpStatus.BAD_REQUEST,"E40102","올바른 JWT 정보가 아닙니다."),
    EXPIRED_REFRESH_JWT         (HttpStatus.BAD_REQUEST,"E40103","JWT REFRESH 시간이 만료되었습니다."),
    INVALID_INTERNAL_TOKEN      (HttpStatus.BAD_REQUEST, "E40104", "내부 통신용 토큰이 맞지 않습니다."),


    DUPLICATE_LOGIN_JWT         (HttpStatus.BAD_REQUEST,"E40105","다른 기기에서 로그인 되었습니다."),

    //402 Payment Required (결제 시스템에 사용하기 위해 만들어졌지만 지금은 사용되고 있지 않다.)

    //403 Authorization
    PENALTY_USER                (HttpStatus.BAD_REQUEST,"E40301","제제된 사용자입니다."),
    NO_AUTHORIZATION            (HttpStatus.BAD_REQUEST, "E40302", "권한이 없습니다"),
    NO_AUTHENTICATION           (HttpStatus.BAD_REQUEST, "E40303", "수정 권한이 없습니다."),
    BLOCKED_USER                (HttpStatus.BAD_REQUEST,"E40304","차단된 유저입니다."),
    REVOKE_USER                 (HttpStatus.BAD_REQUEST,"E40305","참여 신청 취소된 유저입니다."),
    CHANGED_INFORMATION         (HttpStatus.BAD_REQUEST,"E40306","변경된 정보가 있습니다. 재 로그인 해주세요"),
    PASSWORD_BLOCKED_USER       (HttpStatus.BAD_REQUEST,"E40307","로그인 5회 실패하여 접속이 차단된 계정입니다. \n 다른 관리자에게 문의하세요"),

    // 404 Not Found
    NOT_FOUND_USER              (HttpStatus.BAD_REQUEST, "E40404", "유저를 찾지 못했습니다."),

    NOT_FOUND_API               (HttpStatus.BAD_REQUEST,"E40406","없는 API 입니다."),
    // 415 Media file
    INTERNAL_SERVER_ERROR       (HttpStatus.INTERNAL_SERVER_ERROR,"E50000", "통신중 예기치않은 오류가 발생하였습니다.\n 관리자에게 문의 부탁드립니다."),


    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
