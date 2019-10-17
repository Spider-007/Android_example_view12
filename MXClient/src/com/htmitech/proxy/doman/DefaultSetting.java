package com.htmitech.proxy.doman;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by htrf-pc on 2017/5/25.
 */
public class DefaultSetting implements Serializable {

    public float textSize = -1;
    public String currentProtal = "";

    private HashMap<String,String> hashMap = new HashMap<String, String>();//k表示当前门户 v表示当前主题


    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public String getCurrentProtal() {
        return currentProtal;
    }

    public void setCurrentProtal(String currentProtal) {
        this.currentProtal = currentProtal;
    }

    public HashMap<String, String> getHashMap() {
        return hashMap;
    }

    public void setHashMap(HashMap<String, String> hashMap) {
        this.hashMap = hashMap;
    }
}
