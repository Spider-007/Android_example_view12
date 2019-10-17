package com.htmitech.proxy.doman;

import java.util.ArrayList;

/**
 * 请求搜索对象的实体
 */
public class ReportValue {
    public String app_id;
    public String user_id;
    public String app_version_id;
    public ArrayList<Conditions> conditions ;
    public Order order;

    public ArrayList<Conditions> getConditions() {
        return conditions;
    }

    public void setConditions(ArrayList<Conditions> conditions) {
        this.conditions = conditions;
    }

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getApp_version_id() {
        return app_version_id;
    }

    public void setApp_version_id(String app_version_id) {
        this.app_version_id = app_version_id;
    }



    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
