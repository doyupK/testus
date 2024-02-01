package com.testus.testus.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.testus.testus.common.response.ResponseDto;
import com.testus.testus.common.response.exception.Code;
import com.testus.testus.common.response.exception.CustomException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            filterChain.doFilter(request, response);
        }catch (JwtException e){
            logger.info(e.getMessage());
            setErrorResponse(response, Code.SECURITY_FILTER_ERROR);
        }catch (CustomException e){
            setErrorResponse(response, e.getCode());
        }
    }

    public void setErrorResponse(HttpServletResponse response, Code code) throws IOException {
        response.setStatus(HttpStatus.OK.value());
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(new ResponseDto<>(code)));
    }
}
