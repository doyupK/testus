package com.testus.testus.enums;

public enum SocialType {

    KAKAO("KAKAO"),
    NAVER("NAVER"),
    GOOGLE("GOOGLE")
    ;

    private final String typeName;

    SocialType(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() { return typeName; }
}
