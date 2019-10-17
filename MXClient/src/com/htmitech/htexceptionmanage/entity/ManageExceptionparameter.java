package com.htmitech.htexceptionmanage.entity;

import java.io.Serializable;

/**
 * 异常列表请求体
 *
 * @author joe
 * @date 2017-9-26 09:43:20
 */
public class ManageExceptionparameter implements Serializable {

    private String pageSize = "";
    private String pageNum = "";
    private String userId = "";
    private String sourceType = "";
    private String filterDays = "";
    private String keyWord = "";

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getPageNum() {
        return pageNum;
    }

    public void setPageNum(String pageNum) {
        this.pageNum = pageNum;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getFilterDays() {
        return filterDays;
    }

    public void setFilterDays(String filterDays) {
        this.filterDays = filterDays;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }
}
