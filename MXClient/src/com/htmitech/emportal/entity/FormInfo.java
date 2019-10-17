package com.htmitech.emportal.entity;

import java.io.Serializable;

/**
 * Created by Think on 2016/11/30.
 */

public class FormInfo implements Serializable {

    private String key;
    private String input;
    private String formKey;
    private String value;
    private String mode = "1";

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getFormKey() {
        return formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
