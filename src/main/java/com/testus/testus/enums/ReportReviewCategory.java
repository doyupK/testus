package com.testus.testus.enums;

public enum ReportReviewCategory {
    BUG("BUG"),
    IDEA("IDEA"),
    UI_UX("UI_UX")
    ;

    private final String typeName;

    ReportReviewCategory(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() { return typeName; }

}
