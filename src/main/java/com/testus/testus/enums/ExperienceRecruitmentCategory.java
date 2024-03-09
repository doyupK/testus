package com.testus.testus.enums;

public enum ExperienceRecruitmentCategory {

    APP("APP"),
    GAME("GAME"),
    SERVICE("SERVICE")
    ;

    private final String typeName;

    ExperienceRecruitmentCategory(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() { return typeName; }

}
