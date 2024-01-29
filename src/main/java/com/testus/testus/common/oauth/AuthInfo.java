package com.testus.testus.common.oauth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


public class AuthInfo {
    @Component
    public static class Kakao{
        public static String clientId;
        public static String tokenUrl;
        public static String userinfoUrl;
        public static String clientSecret;

        @Value("${oauth2.kakao.client-id}")
        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        @Value("${oauth2.kakao.token-url}")
        public void setTokenUrl(String tokenUrl) {
            this.tokenUrl = tokenUrl;
        }

        @Value("${oauth2.kakao.userinfo-url}")
        public void setUserinfoUrl(String userinfoUrl) {
            this.userinfoUrl = userinfoUrl;
        }
        @Value("${oauth2.kakao.client-secret}")
        public void setClientSecret(String clientSecret) {
            this.clientSecret = clientSecret;
        }
    }
    @Component
    public static class Google{
        public static String clientId;
        public static String clientSecret;
        public static String tokenUrl;
        public static String userinfoUrl;

        @Value("${oauth2.google.client-id}")
        public void setClientId(String clientId) {
            this.clientId = clientId;
        }
        @Value("${oauth2.google.client-secret}")
        public void setClientSecret(String clientSecret) {
            this.clientSecret = clientSecret;
        }
        @Value("${oauth2.google.token-url}")
        public void setTokenUrl(String tokenUrl) {
            this.tokenUrl = tokenUrl;
        }

        @Value("${oauth2.google.userinfo-url}")
        public void setUserinfoUrl(String userinfoUrl) {
            this.userinfoUrl = userinfoUrl;
        }
    }
}