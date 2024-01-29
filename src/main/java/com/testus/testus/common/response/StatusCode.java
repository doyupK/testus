package com.testus.testus.common.response;

import com.testus.testus.common.response.exception.Code;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "스테이터스 클래스")
public class StatusCode { // http 본문에 담아서 보내줄 형태

    @Schema(description = "Status Code")
    private String code;
    @Schema(description = "Status Message")
    private String message;


    public StatusCode(Code code){
        this.code = code.getCode();
        this.message = code.getMessage();
    }
    public StatusCode(Code code, String message){
        this.code = code.getCode();
        this.message = message;
    }

}