package com.htmitech.photoselector.model;

import java.io.Serializable;

/**
 * Created by Think on 2017/6/7.
 * 扩展字段
 */

public class FormExtensionFiles implements Serializable{
    /**数据库存储的*/
    public int ID;
    public String data_id;
    public String form_id;
    public String user_id;
    public String app_id;
    public String subsystemid;
    public String other_id;
    public String field_id;
    public String ext_field_type;
    public String file_id;
    public String file_path;
    public int status_flag;
    /**表单下发字段*/
    public String file_type;
    public String file_size;
    public String file_creater;
    public String file_time;
    public String file_url;
    public long file_long;
    public String file_summ_url;
    public boolean isNet;


    public boolean isNet() {
        return isNet;
    }

    public void setNet(boolean net) {
        isNet = net;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getData_id() {
        return data_id;
    }

    public void setData_id(String data_id) {
        this.data_id = data_id;
    }

    public String getForm_id() {
        return form_id;
    }

    public void setForm_id(String form_id) {
        this.form_id = form_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getSubsystemid() {
        return subsystemid;
    }

    public void setSubsystemid(String subsystemid) {
        this.subsystemid = subsystemid;
    }

    public String getOther_id() {
        return other_id;
    }

    public void setOther_id(String other_id) {
        this.other_id = other_id;
    }

    public String getField_id() {
        return field_id;
    }

    public void setField_id(String field_id) {
        this.field_id = field_id;
    }

    public String getExt_field_type() {
        return ext_field_type;
    }

    public void setExt_field_type(String ext_field_type) {
        this.ext_field_type = ext_field_type;
    }

    public String getFile_id() {
        return file_id;
    }

    public void setFile_id(String file_id) {
        this.file_id = file_id;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public int getStatus_flag() {
        return status_flag;
    }

    public void setStatus_flag(int status_flag) {
        this.status_flag = status_flag;
    }

    public String getFile_type() {
        return file_type;
    }

    public void setFile_type(String file_type) {
        this.file_type = file_type;
    }

    public String getFile_size() {
        return file_size;
    }

    public void setFile_size(String file_size) {
        this.file_size = file_size;
    }

    public String getFile_creater() {
        return file_creater;
    }

    public void setFile_creater(String file_creater) {
        this.file_creater = file_creater;
    }

    public String getFile_time() {
        return file_time;
    }

    public void setFile_time(String file_time) {
        this.file_time = file_time;
    }

    public String getFile_url() {
        return file_url;
    }

    public void setFile_url(String file_url) {
        this.file_url = file_url;
    }

    public long getFile_long() {
        return file_long;
    }

    public void setFile_long(long file_long) {
        this.file_long = file_long;
    }

    public String getFile_summ_url() {
        return file_summ_url;
    }

    public void setFile_summ_url(String file_summ_url) {
        this.file_summ_url = file_summ_url;
    }
}
