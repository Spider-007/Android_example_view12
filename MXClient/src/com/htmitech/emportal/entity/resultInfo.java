package com.htmitech.emportal.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Think on 2016/11/29.
 */

public class resultInfo implements Serializable {

    private String formId;
    private  List<FormInfo> formInfo;

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public List<FormInfo> getFormInfo() {
        return formInfo;
    }

    public void setFormInfo(List<FormInfo> formInfo) {
        this.formInfo = formInfo;
    }
}
