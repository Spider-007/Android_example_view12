package com.htmitech.htexceptionmanage.entity;

import java.io.Serializable;

/**
 *异常详情请求体
 * @author  joe
 * @date 2017-9-26 09:43:20
 */
public class ExceptionDetailparameter implements Serializable {

    public String alertId;

    public String getAlertId() {
        return alertId;
    }

    public void setAlertId(String alertId) {
        this.alertId = alertId;
    }
}
