package com.htmitech.htexceptionmanage.entity;

import java.io.Serializable;

/**
 * 异常已读未读详情
 * @author  joe
 * @date 2017-9-27 15:18:51
 */

public class AlertCountResult implements Serializable{
   private AlertCountInfo alertCount;

    public AlertCountInfo getAlertCount() {
        return alertCount;
    }

    public void setAlertCount(AlertCountInfo alertCount) {
        this.alertCount = alertCount;
    }
}
