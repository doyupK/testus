package com.testus.testus.common.oauth.userInfo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public abstract class OAuth2UserInfo {
    protected Map<String, Object> attributes;
    public OAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }
    public abstract String getId();

    public abstract String getName();
    public abstract String getProvider();

    public abstract String getEmail();

    public abstract String getImageUrl();
}