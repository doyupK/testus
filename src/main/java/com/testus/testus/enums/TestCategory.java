package com.testus.testus.enums;

public enum TestCategory {

    APP("APP"),
    GAME("GAME"),
    SERVICE("SERVICE")
    ;

    private final String typeName;

    TestCategory(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() { return typeName; }

}
