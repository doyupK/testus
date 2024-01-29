package com.testus.testus.common.oauth.userInfo;



public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(String provider, String code, String redirectUrl) {
        return switch (provider) {
            case "KAKAO" -> new KakaoUserInfo(code, redirectUrl);
            case "GOOGLE" -> new GoogleUserInfo(code, redirectUrl);
            default -> throw new IllegalArgumentException();
        };
    }
}