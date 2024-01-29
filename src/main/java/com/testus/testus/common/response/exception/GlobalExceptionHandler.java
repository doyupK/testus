package com.testus.testus.common.response.exception;

import com.testus.testus.common.response.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<?> handleApiRequestException(Exception ex) {
        ResponseDto<?> responseDto = new ResponseDto<>(Code.INTERNAL_SERVER_ERROR, null);
        responseDto.getStatus().setMessage(ex.getMessage());

        return ResponseEntity.badRequest().body(responseDto);
    }

    @ExceptionHandler(value = CustomException.class)
    public ResponseEntity<?> handleCustomException(CustomException e) {
        if (e.getData() == null) {
            ResponseDto<Object> responseDto = new ResponseDto<>(e.getCode(), null);

            return ResponseEntity
                    .status(e.getCode().getHttpStatus())
                    .body(responseDto);
        } else {
            ResponseDto<Object> responseDto = new ResponseDto<>(e.getCode(), e.getData());

            return ResponseEntity
                    .status(e.getCode().getHttpStatus())
                    .body(responseDto);
        }
    }

}