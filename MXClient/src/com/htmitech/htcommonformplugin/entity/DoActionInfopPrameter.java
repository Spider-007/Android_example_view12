package com.htmitech.htcommonformplugin.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Think on 2017/4/24.
 */

public class DoActionInfopPrameter implements Serializable{
    /// <summary>
    /// 平台应用ID
    /// </summary>
    public String app_id;
    /// <summary>
    /// 平台用户ID
    /// </summary>
    public String user_id;

    /// <summary>
    /// 主键ID，（扩展备用）
    /// </summary>
    public String id;
    /// <summary>
    /// 表单ID
    /// </summary>
    public String form_id;
    /// <summary>
    /// 数据ID
    /// </summary>
    public String data_id;
    public String action_id;
    public String action_key;
    /// <summary>
    /// 多系统集成时，列表数据来自不同的系统，被集成到一个待办列表中，该字段用来表明是什么系统的。
    /// </summary>
    public String subsystemid = "";
    /// <summary>
    /// 提交变更的字段
    /// </summary>
    public List<Editfields> editfields ;

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getForm_id() {
        return form_id;
    }

    public void setForm_id(String form_id) {
        this.form_id = form_id;
    }

    public String getData_id() {
        return data_id;
    }

    public void setData_id(String data_id) {
        this.data_id = data_id;
    }

    public String getAction_id() {
        return action_id;
    }

    public void setAction_id(String action_id) {
        this.action_id = action_id;
    }

    public String getAction_key() {
        return action_key;
    }

    public void setAction_key(String action_key) {
        this.action_key = action_key;
    }

    public String getSubsystemid() {
        return subsystemid;
    }

    public void setSubsystemid(String subsystemid) {
        this.subsystemid = subsystemid;
    }

    public List<Editfields> getEditfields() {
        return editfields;
    }

    public void setEditfields(List<Editfields> editfields) {
        this.editfields = editfields;
    }
}
