package com.htmitech.htworkflowformpluginnew.entity;

/**
 * Created by sunxl on 2018-1-26.
 */
public class OptionsParameters {
    public String userId;
    public UserOption option;
    public String appId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public UserOption getOption() {
        return option;
    }

    public void setOption(UserOption option) {
        this.option = option;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}
