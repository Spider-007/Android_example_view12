package com.htmitech.htcommonformplugin.entity;

import java.io.Serializable;

/***
 * 待办已办列表请求参数 实体
 * @date 2017/04/17
 * @author joe
 */
public class CommonFormRequiredParameter implements Serializable{

    private static final long serialVersionUID = -8592118312872488358L;

    public String title_keyword ="";
    public String starttime="";
    public String days="";
    public String app_id="";
    public String otherfavflag="";
    public String todoflag="";
    public String page_num="";
    public String modulename="";
    public String page_size="";
    public String endtime="";
    public String mystartflag="";
    public String user_id="";
    public String myfavflag="";
    public String flag="";

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getTitle_keyword() {
        return title_keyword;
    }

    public void setTitle_keyword(String title_keyword) {
        this.title_keyword = title_keyword;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getOtherfavflag() {
        return otherfavflag;
    }

    public void setOtherfavflag(String otherfavflag) {
        this.otherfavflag = otherfavflag;
    }

    public String getTodoflag() {
        return todoflag;
    }

    public void setTodoflag(String todoflag) {
        this.todoflag = todoflag;
    }

    public String getPage_num() {
        return page_num;
    }

    public void setPage_num(String page_num) {
        this.page_num = page_num;
    }

    public String getModulename() {
        return modulename;
    }

    public void setModulename(String modulename) {
        this.modulename = modulename;
    }

    public String getPage_size() {
        return page_size;
    }

    public void setPage_size(String page_size) {
        this.page_size = page_size;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getMystartflag() {
        return mystartflag;
    }

    public void setMystartflag(String mystartflag) {
        this.mystartflag = mystartflag;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getMyfavflag() {
        return myfavflag;
    }

    public void setMyfavflag(String myfavflag) {
        this.myfavflag = myfavflag;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
