package com.testus.testus.common.oauth.userInfo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.testus.testus.common.oauth.AuthInfo;
import com.testus.testus.common.response.exception.Code;
import com.testus.testus.common.response.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
@Slf4j
public class KakaoUserInfo extends OAuth2UserInfo{
    private final Map<String, Object> kakaoAccount;
    private final Map<String, Object> kakaoProfile;


    public KakaoUserInfo(String code, String redirectUrl) {
        String accessToken = getAccessToken(code, redirectUrl);

        this.attributes = getUserInfo(accessToken);

        kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");
    }
    @Override
    public String getId() {
        return String.valueOf(attributes.get("id"));
    }
    @Override
    public String getProvider() {
        return  "KAKAO";
    }

    @Override
    public String getName() {
        return (String) kakaoProfile.get("nickname");
    }

    @Override
    public String getEmail() {
        return (String) kakaoAccount.get("email");
    }

    @Override
    public String getImageUrl() {
        return (String) kakaoProfile.get("profile_image_url");
    }

    private String getAccessToken(String code, String redirectUrl){

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);

            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("grant_type", "authorization_code");
            body.add("client_id", AuthInfo.Kakao.clientId);
            body.add("client_secret", AuthInfo.Kakao.clientSecret);
            body.add("redirect_uri", redirectUrl);
            body.add("code", code);

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

            String responseBody = new RestTemplate().postForEntity(
                    AuthInfo.Kakao.tokenUrl,
                    request,
                    String.class
            ).getBody();

            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> map = objectMapper.readValue(responseBody, new TypeReference<>() { });

            return map.get("access_token");
        } catch (JsonProcessingException e) {
            throw new CustomException(Code.BAD_REQUEST);
        }
    }

    private Map<String, Object> getUserInfo(String accessToken) {

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + accessToken);
            headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);
            String responseBody = new RestTemplate().exchange(
                    AuthInfo.Kakao.userinfoUrl,
                    HttpMethod.POST,
                    request,
                    String.class
            ).getBody();

            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(responseBody, new TypeReference<>() { });

        } catch (JsonProcessingException e) {
            throw new CustomException(Code.BAD_REQUEST);
        }
    }
}
