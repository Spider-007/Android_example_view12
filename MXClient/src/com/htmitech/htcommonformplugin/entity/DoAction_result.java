package com.htmitech.htcommonformplugin.entity;

import java.io.Serializable;

/**
 * Created by Think on 2017/4/24.
 */

public class DoAction_result implements Serializable{
    public boolean is_excuted = false; //执行时否成功
    public String result_code; //执行的返回结果编码
    public String result_info ; //执行的返回结果说明
    public String error_msg ; //出错时的错误信息
    //行为扩展
    public boolean need_close_view = false; //执行成功后，是否关闭当前页面

    public boolean is_excuted() {
        return is_excuted;
    }

    public void setIs_excuted(boolean is_excuted) {
        this.is_excuted = is_excuted;
    }

    public String getResult_code() {
        return result_code;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }

    public String getResult_info() {
        return result_info;
    }

    public void setResult_info(String result_info) {
        this.result_info = result_info;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public boolean isNeed_close_view() {
        return need_close_view;
    }

    public void setNeed_close_view(boolean need_close_view) {
        this.need_close_view = need_close_view;
    }

    //**删除**public List<RouteInfo> ListRouteInfo = null; //需要选择的路由列表
    //**删除**public List<AuthorInfo> ListAuthorInfo = null; //需要选择的人员列表
    //**删除**public RouteInfo HasSelectedRoute = null; //已经确定的路由信息 //add by gulbel 2015-08-20
    //**删除**public bool IsMultiSelectUser = false; //是否多选人员
    //**删除**public bool IsMultiSelectRoute = false;  //是否多选路由
    //**删除**public bool IsFreeSelectUser = false; //是否自由选择人员

}
