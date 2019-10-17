package com.htmitech.proxy.doman;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *版本信息
 */
public class AppVersion implements Serializable{
    private long app_version_id;
    private long app_id;
    private long version_number;
    private String version_name;
    private int version_type;
    private String version_desc;
    private int mustupdated;
    private long app_filesize;
    private String hash_code;
    private String file_location;
    private String package_name;
    private String package_main;
    private int status_flag;
    private ArrayList<AppVersionConfig> appVersionConfigArrayList;
    private String suffix;//文件后缀名
    private String localName;//文件名称及各式

    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public ArrayList<AppVersionConfig> getAppVersionConfigArrayList() {
        if(appVersionConfigArrayList == null){
            appVersionConfigArrayList = new ArrayList<AppVersionConfig>();
        }
        return appVersionConfigArrayList;
    }

    public void setAppVersionConfigArrayList(ArrayList<AppVersionConfig> appVersionConfigArrayList) {
        this.appVersionConfigArrayList = appVersionConfigArrayList;
    }

    public int getMustupdated() {
        return mustupdated;
    }

    public int getStatus_flag() {
        return status_flag;
    }

    public int getVersion_type() {
        return version_type;
    }

    public String getApp_filesize() {
        return app_filesize + "kb";
    }

    public long getApp_id() {
        return app_id;
    }

    public long getApp_version_id() {
        return app_version_id;
    }

    public long getVersion_number() {
        return version_number;
    }

    public String getFile_location() {
        return file_location;
    }

    public String getHash_code() {
        return hash_code;
    }

    public String getPackage_main() {
        return package_main;
    }

    public String getPackage_name() {
        return package_name;
    }

    public String getVersion_desc() {
        return version_desc;
    }

    public String getVersion_name() {
        return version_name;
    }

    public void setApp_filesize(long app_filesize) {
        this.app_filesize = app_filesize;
    }

    public void setApp_id(long app_id) {
        this.app_id = app_id;
    }

    public void setApp_version_id(long app_version_id) {
        this.app_version_id = app_version_id;
    }

    public void setFile_location(String file_location) {
        this.file_location = file_location;
    }

    public void setHash_code(String hash_code) {
        this.hash_code = hash_code;
    }

    public void setMustupdated(int mustupdated) {
        this.mustupdated = mustupdated;
    }

    public void setPackage_main(String package_main) {
        this.package_main = package_main;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    public void setStatus_flag(int status_flag) {
        this.status_flag = status_flag;
    }

    public void setVersion_desc(String version_desc) {
        this.version_desc = version_desc;
    }

    public void setVersion_name(String version_name) {
        this.version_name = version_name;
    }

    public void setVersion_number(long version_number) {
        this.version_number = version_number;
    }

    public void setVersion_type(int version_type) {
        this.version_type = version_type;
    }
}
