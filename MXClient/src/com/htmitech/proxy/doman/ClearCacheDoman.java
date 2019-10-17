package com.htmitech.proxy.doman;

/**
 * Created by htrf-pc on 2017/5/26.
 */
public class ClearCacheDoman {

    public AppInfo appInfo;
    public double size;
    public String showTextSize;
    private String filePath;
    private String danwei;
    private boolean isDelete = true;
    public ClearCacheDoman(AppInfo appInfo, double size,String filePath,String showTextSize,String danwei) {
        this.appInfo = appInfo;
        this.size = size;
        this.filePath = filePath;
        this.showTextSize = showTextSize;
        this.danwei = danwei;
    }

    public String getDanwei() {
        return danwei;
    }

    public void setDanwei(String danwei) {
        this.danwei = danwei;
    }

    public String getShowTextSize() {
        return showTextSize;
    }

    public void setShowTextSize(String showTextSize) {
        this.showTextSize = showTextSize;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public AppInfo getAppInfo() {
        return appInfo;
    }

    public void setAppInfo(AppInfo appInfo) {
        this.appInfo = appInfo;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setIsDelete(boolean isDelete) {
        this.isDelete = isDelete;
    }
}
