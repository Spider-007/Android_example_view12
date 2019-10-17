package com.htmitech.emportal.ui.login.data.logindata;

import java.io.Serializable;

/**
 * Created by yanxin on 2017-8-29.
 */
public class CisAccountList implements Serializable {
    public String cisId;
    public String cisName;
    public String applyType;
    public String cisAccountId;
    public String cisAccountName;
    public String cisDeptId;
    public String cisDeptName;

    public String getCisId() {
        return cisId;
    }

    public void setCisId(String cisId) {
        this.cisId = cisId;
    }

    public String getCisName() {
        return cisName;
    }

    public void setCisName(String cisName) {
        this.cisName = cisName;
    }

    public String getApplyType() {
        return applyType;
    }

    public void setApplyType(String applyType) {
        this.applyType = applyType;
    }

    public String getCisAccountId() {
        return cisAccountId;
    }

    public void setCisAccountId(String cisAccountId) {
        this.cisAccountId = cisAccountId;
    }

    public String getCisAccountName() {
        return cisAccountName;
    }

    public void setCisAccountName(String cisAccountName) {
        this.cisAccountName = cisAccountName;
    }

    public String getCisDeptId() {
        return cisDeptId;
    }

    public void setCisDeptId(String cisDeptId) {
        this.cisDeptId = cisDeptId;
    }

    public String getCisDeptName() {
        return cisDeptName;
    }

    public void setCisDeptName(String cisDeptName) {
        this.cisDeptName = cisDeptName;
    }
}
