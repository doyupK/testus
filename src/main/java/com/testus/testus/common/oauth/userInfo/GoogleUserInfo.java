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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Map;

@Slf4j
public class GoogleUserInfo extends OAuth2UserInfo{

    public GoogleUserInfo(String code, String redirectUrl) {
        String accessToken = getAccessToken(code, redirectUrl);

        this.attributes = getUserInfo(accessToken);

    }
    @Override
    public String getId() {
        return String.valueOf(attributes.get("id"));
    }

    @Override
    public String getProvider() {
        return "GOOGLE";
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getImageUrl() {
        return (String) attributes.get("picture");
    }

    private String getAccessToken(String code, String redirectUrl){

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("grant_type", "authorization_code");
            body.add("client_id", AuthInfo.Google.clientId);
//            body.add("code_verifier", null);
            body.add("code_challenge_method", "S256");
            body.add("client_secret", AuthInfo.Google.clientSecret);
            body.add("redirect_uri", redirectUrl);
            body.add("code", code);
            log.info(AuthInfo.Google.clientId);
            HttpEntity<MultiValueMap<String, String>> request =
                    new HttpEntity<>(body, headers);
            String responseBody = new RestTemplate().exchange(
                    AuthInfo.Google.tokenUrl,
                    HttpMethod.POST,
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
            HttpEntity<MultiValueMap<String, String>> request =
                    new HttpEntity<>(headers);
            String responseBody = new RestTemplate().exchange(
                    AuthInfo.Google.userinfoUrl + "?access_token=" + accessToken,
                    HttpMethod.GET,
                    request,
                    String.class
            ).getBody();

            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(responseBody, new TypeReference<>() { });

        } catch ( IOException e) {
            throw new CustomException(Code.BAD_REQUEST);
        }
    }
}
