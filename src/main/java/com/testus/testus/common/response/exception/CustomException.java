package com.testus.testus.common.response.exception;

import jakarta.annotation.Nullable;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{
    private final Code code;
    @Nullable
    private final Object data;

    public CustomException(Code code) {
        super(code.getMessage());
        this.code = code;
        this.data = null;
    }

    public CustomException(Code code, @Nullable Object data) {
        super(code.getMessage());
        this.code = code;
        this.data = data;
    }
}
