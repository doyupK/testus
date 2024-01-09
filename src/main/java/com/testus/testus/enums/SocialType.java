package com.testus.testus.enums;

public enum SocialType {

    KAKAO("카카오"),
    GOOGLE("구글")
    ;

    private final String typeName;

    SocialType(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() { return typeName; }
}
