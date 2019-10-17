package com.htmitech.htexceptionmanage.entity;

import java.io.Serializable;

/**
 * 异常已读未读详情
 * @author  joe
 * @date 2017-9-27 15:18:51
 */

public class AlertCountInfo implements Serializable{
    private String noDealCount;
    private String realCount;
    private String dealCount;
    private String allDealCount;
    private String allCount;

    public String getNoDealCount() {
        return noDealCount;
    }

    public void setNoDealCount(String noDealCount) {
        this.noDealCount = noDealCount;
    }

    public String getRealCount() {
        return realCount;
    }

    public void setRealCount(String realCount) {
        this.realCount = realCount;
    }

    public String getDealCount() {
        return dealCount;
    }

    public void setDealCount(String dealCount) {
        this.dealCount = dealCount;
    }

    public String getAllDealCount() {
        return allDealCount;
    }

    public void setAllDealCount(String allDealCount) {
        this.allDealCount = allDealCount;
    }

    public String getAllCount() {
        return allCount;
    }

    public void setAllCount(String allCount) {
        this.allCount = allCount;
    }
}
