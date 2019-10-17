package com.htmitech.htcommonformplugin.entity;

/**
 * Created by htrf-pc on 2017/4/19.
 */
public class Attentioniteminfo {

    /// <summary>
    /// 显示在列表上的图标的服务器端完整路径。如：http://htrf.dscloud.me:8020/image/1.pgn
    /// </summary>
    public String iconurl;
    /// <summary>
    /// 标题（展示在列表上的标题）
    /// </summary>
    public String title;
    /// <summary>
    /// 表示要显示的时间，如待办时表示的当前的收到的时间，已办时表示最后已办时间
    /// </summary>
    public String time;

    /// <summary>
    /// 要显示的其他信息，可以是“模块名称”，“来文单位”，或者业务系统中多个字段的组合（展示在图标下方，时间上方，屏幕居左的位置）
    /// </summary>
    public String otherinfo1;
    /// <summary>
    /// 要显示的其他信息，可以是“发送人”，“关注人”等，或者业务系统中多个字段的组合（展示在图标下方，时间上方，屏幕居右的位置）
    /// </summary>
    public String otherinfo2;

    /// <summary>
    /// 待办已办，标记（0，表示待办；1，表示已办）
    /// </summary>
    public String todoflag;

    /// <summary>
    /// 是否要在图标上，增加‘new'标记
    /// </summary>
    public String newtag;
    /// <summary>
    /// 隐藏的数据：表单模板ID，表单模板唯一标识了一个表单的后台定义
    /// </summary>
    public String form_id;
    /// <summary>
    /// 隐藏的数据：表单数据主键ID，表单数据id唯一标识了一个表单承载的一条记录在数据库记录里的数据记录ID
    /// </summary>
    public String data_id;
    /// <summary>
    /// 主键ID，（扩展备用）
    /// </summary>
    public String id;
    /// <summary>
    /// 隐藏的数据：多系统集成时，列表数据来自不同的系统，被集成到一个待办列表中，该字段用来表明是什么系统的。
    /// </summary>
    public String subsystemid;

    public String app_id;

    public String attention_flag;

    public String allow_push;

    public String user_id;

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getAttention_flag() {
        return attention_flag;
    }

    public void setAttention_flag(String attention_flag) {
        this.attention_flag = attention_flag;
    }

    public String getAllow_push() {
        return allow_push;
    }

    public void setAllow_push(String allow_push) {
        this.allow_push = allow_push;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getIconurl() {
        return iconurl;
    }

    public void setIconurl(String iconurl) {
        this.iconurl = iconurl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getOtherinfo1() {
        return otherinfo1;
    }

    public void setOtherinfo1(String otherinfo1) {
        this.otherinfo1 = otherinfo1;
    }

    public String getOtherinfo2() {
        return otherinfo2;
    }

    public void setOtherinfo2(String otherinfo2) {
        this.otherinfo2 = otherinfo2;
    }

    public String getTodoflag() {
        return todoflag;
    }

    public void setTodoflag(String todoflag) {
        this.todoflag = todoflag;
    }

    public String getNewtag() {
        return newtag;
    }

    public void setNewtag(String newtag) {
        this.newtag = newtag;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubsystemid() {
        return subsystemid;
    }

    public void setSubsystemid(String subsystemid) {
        this.subsystemid = subsystemid;
    }
}
