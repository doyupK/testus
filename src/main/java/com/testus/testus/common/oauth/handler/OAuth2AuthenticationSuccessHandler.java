package com.testus.testus.common.oauth.handler;

import com.testus.testus.common.oauth.*;
import com.testus.testus.config.security.OAuth2UserPrincipal;
import com.testus.testus.enums.SocialType;
import com.testus.testus.service.MemberService;
import com.testus.testus.util.CookieUtils;
import com.testus.testus.util.HttpCookieOAuth2AuthorizationRequestRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.log.LogMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Optional;

import static com.testus.testus.util.HttpCookieOAuth2AuthorizationRequestRepository.MODE_PARAM_COOKIE_NAME;
import static com.testus.testus.util.HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;
    private final OAuth2UserUnlinkManager oAuth2UserUnlinkManager;
    private final MemberService memberService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        String targetUrl;

        targetUrl = determineTargetUrl(request, response, authentication);

        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }

        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) {

        Optional<String> redirectUri = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);

        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());
        log.info("target URL : {}", targetUrl);

        String mode = CookieUtils.getCookie(request, MODE_PARAM_COOKIE_NAME)
                .map(Cookie::getValue)
                .orElse("");

        OAuth2UserPrincipal principal = getOAuth2UserPrincipal(authentication);

        if (principal == null) {
            return UriComponentsBuilder.fromUriString(targetUrl)
                    .queryParam("error", "Login failed")
                    .build().toUriString();
        }

        if ("login".equalsIgnoreCase(mode)) {
            log.info("email={}, name={}, nickname={}, accessToken={}", principal.getUserInfo().getEmail(),
                    principal.getUserInfo().getName(),
                    principal.getUserInfo().getNickname(),
                    principal.getUserInfo().getAccessToken()
            );
            SocialType socialType = null;
            if (principal.getUserInfo() instanceof GoogleOAuth2UserInfo){
                log.info("google");
                socialType = SocialType.GOOGLE;
            } else if(principal.getUserInfo() instanceof KakaoOAuth2UserInfo) {
                log.info("kakao");
                socialType = SocialType.KAKAO;
            } else if (principal.getUserInfo() instanceof NaverOAuth2UserInfo) {
                socialType = SocialType.NAVER;
            }
            log.info("path : {}", request.getContextPath());
            memberService.oauthSignUp(
                    principal.getName(),
                    principal.getNickName(),
                    principal.getEmail(),
                    principal.getUserInfo().getAccessToken(),
                    socialType
                    );

            return UriComponentsBuilder.fromUriString(targetUrl)
//                    .queryParam("access_token", accessToken)
//                    .queryParam("refresh_token", refreshToken)
                    .build().toUriString();

        } else if ("unlink".equalsIgnoreCase(mode)) {

            String accessToken = principal.getUserInfo().getAccessToken();
            OAuth2Provider provider = principal.getUserInfo().getProvider();

            // TODO: DB 삭제
            // TODO: 리프레시 토큰 삭제
            oAuth2UserUnlinkManager.unlink(provider, accessToken);

            return UriComponentsBuilder.fromUriString(targetUrl)
                    .build().toUriString();
        }

        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("error", "Login failed")
                .build().toUriString();
    }

    private OAuth2UserPrincipal getOAuth2UserPrincipal(Authentication authentication) {
        Object principal = authentication.getPrincipal();

        if (principal instanceof OAuth2UserPrincipal) {
            return (OAuth2UserPrincipal) principal;
        }
        return null;
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }
}
