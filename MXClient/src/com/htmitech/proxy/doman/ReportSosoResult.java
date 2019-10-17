package com.htmitech.proxy.doman;

import java.io.Serializable;

/**
 * Created by htrf-pc on 2017/4/13.
 */
public class ReportSosoResult implements Serializable {
    public String id;
    public String name;
    public String value;
    public String key;
    public int postion;
    public int isSelectd ;

    public int getIsSelectd() {
        return isSelectd;
    }

    public void setIsSelectd(int isSelectd) {
        this.isSelectd = isSelectd;
    }

    public int getPostion() {
        return postion;
    }

    public void setPostion(int postion) {
        this.postion = postion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
