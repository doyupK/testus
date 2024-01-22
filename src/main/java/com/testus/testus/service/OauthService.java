package com.testus.testus.service;

import com.testus.testus.util.CookieUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import static com.testus.testus.util.HttpCookieOAuth2AuthorizationRequestRepository.*;

@Service
@RequiredArgsConstructor
public class OauthService {


//    public void signUpOauth(HttpServletRequest request, HttpServletResponse response, String provider, String redirectUrl) {
//        CookieUtils.addCookie(response,
//                OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME,
//                CookieUtils.serialize(authorizationRequest),
//                COOKIE_EXPIRE_SECONDS);
//
//        String redirectUriAfterLogin = request.getParameter(REDIRECT_URI_PARAM_COOKIE_NAME);
//        if (StringUtils.hasText(redirectUriAfterLogin)) {
//            CookieUtils.addCookie(response,
//                    REDIRECT_URI_PARAM_COOKIE_NAME,
//                    redirectUriAfterLogin,
//                    COOKIE_EXPIRE_SECONDS);
//        }
//
//        String mode = request.getParameter(MODE_PARAM_COOKIE_NAME);
//        if (StringUtils.hasText(mode)) {
//            CookieUtils.addCookie(response,
//                    MODE_PARAM_COOKIE_NAME,
//                    mode,
//                    COOKIE_EXPIRE_SECONDS);
//        }
//
//    }
}
