package com.htmitech.proxy.doman;

import java.io.Serializable;

/**
 * Created by htrf-pc on 2016/12/8.
 */
public class AppVersionConfig implements Serializable{
    private long app_version_id;
    private long app_id;
    private String config_name;
    private String config_value = "";
    private String config_remark;
    private int status_flag;
    private String config_code = "";

    public long getApp_version_id() {
        return app_version_id;
    }

    public int getStatus_flag() {
        return status_flag;
    }

    public void setApp_version_id(long app_version_id) {
        this.app_version_id = app_version_id;
    }

    public void setApp_id(long app_id) {
        this.app_id = app_id;
    }

    public void setConfig_code(String config_code) {
        this.config_code = config_code;
    }

    public void setConfig_remark(String config_remark) {
        this.config_remark = config_remark;
    }

    public void setConfig_name(String config_name) {
        this.config_name = config_name;
    }

    public void setConfig_value(String config_value) {
        this.config_value = config_value;
    }

    public void setStatus_flag(int status_flag) {
        this.status_flag = status_flag;
    }

    public long getApp_id() {
        return app_id;
    }

    public String getConfig_code() {
        return config_code;
    }

    public String getConfig_name() {
        return config_name;
    }

    public String getConfig_remark() {
        return config_remark;
    }

    public String getConfig_value() {
        return config_value;
    }

}
