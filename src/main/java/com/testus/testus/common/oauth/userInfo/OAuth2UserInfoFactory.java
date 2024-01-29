package com.testus.testus.common.oauth.userInfo;



public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(String provider, String code, String redirectUrl) {
        switch (provider) {
            case "KAKAO":
                return new KakaoUserInfo(code, redirectUrl);
            case "GOOGLE":
                return new GoogleUserInfo(code, redirectUrl);
            default: throw new IllegalArgumentException();
        }
    }
}