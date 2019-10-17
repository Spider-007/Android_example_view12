package com.htmitech.htcommonformplugin.entity;

import java.io.Serializable;

/**
 * Created by Think on 2017/4/19.
 */

public class Editfields implements Serializable{

    public String key;
    public String value;


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
