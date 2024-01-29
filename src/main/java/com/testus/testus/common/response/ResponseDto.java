package com.testus.testus.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.testus.testus.common.response.exception.Code;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto<T> {
    @Schema(description = "Status")
    StatusCode status;
    @Nullable
    @Schema(description = "본문 데이터")
    T data;

    /**
     * 표준 응답 객체 생성자
     * @param code Code Enum.class
     * @param data data
     */
    public ResponseDto(Code code, @Nullable T data) {
        this.status = new StatusCode(code);
        this.data = data;
    }
    /**
     * 표준 응답 객체 생성자 ( 데이터가 없는 경우 )
     * @param code Code Enum.class
     */
    public ResponseDto(Code code){
        this.status = new StatusCode(code);
    }

    /**
     * 응답 state.message Custom 생성자
     * @param code Code Enum.class
     * @param message Custom Message
     * @param data data
     */
    public ResponseDto(Code code, String message, @Nullable T data) {
        this.status = new StatusCode(code, message);
        this.data = data;
    }

}
