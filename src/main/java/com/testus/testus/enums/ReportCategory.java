package com.testus.testus.enums;

public enum ReportCategory {
    BUG("BUG"),
    IDEA("IDEA"),
    UI_UX("UI_UX")
    ;

    private final String typeName;

    ReportCategory(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() { return typeName; }

}
