package com.htmitech.emportal.ui.login.data.logindata;

/**
 * Created by heyang on 2016-12-8.
 */
public class EmpApiLoginResult {
    public String accessToken;
    public String refreshToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
