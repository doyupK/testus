package com.testus.testus.config.security;

import com.testus.testus.common.response.exception.Code;
import com.testus.testus.common.response.exception.CustomException;
import com.testus.testus.service.RedisService;
import com.testus.testus.util.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenUtil jwtUtils;

    private final RedisService redisService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, AuthenticationException {
        //get jwt token from header
        String jwtToken = jwtUtils.getTokenFromHeader(request);
        if (jwtToken != null) {
            try {
                Authentication authentication = jwtUtils.getAuthenticationFromToken(jwtToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (ExpiredJwtException e) {
                String refreshToken = jwtUtils.getRefreshTokenFromHeader(request);
                if (refreshToken == null) {
                    throw new CustomException(Code.EXPIRED_ACCESS_JWT);
                } else {
//                    int userSeq = (int) e.getClaims().get("seq");

//                    String findRefreshToken = (String) redisService.getValues(String.valueOf(userSeq));
//                    if (findRefreshToken.equals(refreshToken)) {
                    Claims claims;
                    try {
                        claims = jwtUtils.validToken(refreshToken);
                    } catch (ExpiredJwtException jwtException) {
                        throw new CustomException(Code.EXPIRED_REFRESH_JWT);
                    }
                    String userId = (String) claims.get("userSeq");
                    jwtUtils.addJwtTokenInHeader(Integer.parseInt(userId), response);
                    throw new CustomException(Code.TOKEN_REFRESH);
//                    }
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
