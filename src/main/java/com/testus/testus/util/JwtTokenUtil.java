package com.testus.testus.util;

import com.testus.testus.config.security.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenUtil {

    @Value("${jwt.secretKey}")
    private String secretKey;
    @Value("${jwt.accessToken.expireTime}")
    private long accessTokenExpireSeconds;
    @Value("${jwt.refreshToken.expireTime}")
    private long refreshTokenExpireSeconds;

    // JWT Token 발급
    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String ACCESS_TOKEN_HEADER = "Authorization";
    private static final String REFRESH_TOKEN_HEADER = "refreshToken";
    private final UserDetailsServiceImpl userDetailsService;

    public String getTokenFromHeader(HttpServletRequest request) {
        return request.getHeader(ACCESS_TOKEN_HEADER);
    }
    public String getRefreshTokenFromHeader(HttpServletRequest request) {
        return request.getHeader(REFRESH_TOKEN_HEADER);
    }
    public String getAccessTokenHeader() {
        return ACCESS_TOKEN_HEADER;
    }
    public String getRefreshTokenHeader() {
        return REFRESH_TOKEN_HEADER;
    }
    public Long getTokenExpireSeconds() {
        return accessTokenExpireSeconds;
    }
    public Long getRefreshTokenExpireSeconds() {
        return refreshTokenExpireSeconds;
    }
    public String createToken(String userId) {
        Date now = new Date();
        Claims claims = Jwts.claims()
                .setSubject("access_token")
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessTokenExpireSeconds));

        claims.put("userSeq", userId);

        return TOKEN_PREFIX + Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256,  secretKey)
                .compact();
    }
    public String createRefreshToken(String userId) {
        Date now = new Date();
        Claims claims = Jwts.claims()
                .setSubject("refresh_token")
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshTokenExpireSeconds));

//        claims.put("uuid", UUID.randomUUID().toString());
        claims.put("userSeq", userId);

//        return TOKEN_PREFIX + Jwts.builder()
//                .setHeaderParam("typ", "JWT")
//                .setClaims(claims)
//                .signWith(SignatureAlgorithm.HS256,  secretKey)
//                .compact();
        return TOKEN_PREFIX + Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256,  secretKey)
                .compact();
    }

    public Claims validToken(String token){

        if(!token.startsWith(TOKEN_PREFIX)){
            throw new IllegalArgumentException();
        }
        token = token.substring(TOKEN_PREFIX.length());
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    public Authentication getAuthenticationFromToken(String token) {
        Claims claims = validToken(token);
        token = token.substring(TOKEN_PREFIX.length());
        String userId = (String) claims.get("userSeq");
        UserDetails userDetails = userDetailsService.loadUserByUsername(userId);
        return new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());
    }

    /**
     * 헤더에 JWT Access Token, Refresh Token 추가
     * @param memberSeq 회원 시퀀스
     * @param response 응답 객체
     */
    public void addJwtTokenInHeader(int  memberSeq, HttpServletResponse response) {
        response.addHeader("Authorization",createToken(String.valueOf(memberSeq)));
        response.addHeader("Refreshtoken",createRefreshToken(String.valueOf(memberSeq)));
    }
}