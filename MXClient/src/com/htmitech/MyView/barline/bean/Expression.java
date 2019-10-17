package com.htmitech.MyView.barline.bean;

public class Expression {
    private String compareType;
    private String compareValue;
    private String field;

    public String getField() {
        return this.field;
    }

    public void setField(String str) {
        this.field = str;
    }

    public String getCompareType() {
        return this.compareType;
    }

    public void setCompareType(String str) {
        this.compareType = str;
    }

    public String getCompareValue() {
        return this.compareValue;
    }

    public void setCompareValue(String str) {
        this.compareValue = str;
    }
}