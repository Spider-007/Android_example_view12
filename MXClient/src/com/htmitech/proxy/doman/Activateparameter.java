package com.htmitech.proxy.doman;

import java.io.Serializable;

/**
 * Created by Think on 2017/5/4.
 */

public class Activateparameter implements Serializable {

    public String app_id;
    public String cis_id;
    public String user_id;
    public String login_name;
    public String password;


    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getCis_id() {
        return cis_id;
    }

    public void setCis_id(String cis_id) {
        this.cis_id = cis_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getLogin_name() {
        return login_name;
    }

    public void setLogin_name(String login_name) {
        this.login_name = login_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
