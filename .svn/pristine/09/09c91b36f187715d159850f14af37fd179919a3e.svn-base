package com.htmitech.domain;

import android.text.TextUtils;

/**
 * apc_userdefine_portal
 */
public class ApcUserdefinePortal {
    public String user_id;
    public String portal_id;
    public String corp_id;
    public int is_using = 1;
    public int using_home_style;
    public int using_color_style;
    public int using_layout_style;
    public int using_apc_style;
    public int using_font_style;
    public long group_corp_id;
    public int status_flag = 1;
    public int mx_app_id;
    public String portal_name;
    public String getCorp_id() {
        return corp_id;
    }

    public void setCorp_id(String corp_id) {
        this.corp_id = corp_id;
    }

    public int getMx_app_id() {
        return mx_app_id;
    }

    public void setMx_app_id(int mx_app_id) {
        this.mx_app_id = mx_app_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPortal_id() {
        return TextUtils.isEmpty(portal_id) ? "" : portal_id;
    }

    public void setPortal_id(String portal_id) {
        this.portal_id = portal_id;
    }

    public int getIs_using() {
        return is_using;
    }

    public void setIs_using(int is_using) {
        this.is_using = is_using;
    }

    public int getUsing_home_style() {
        return using_home_style;
    }

    public void setUsing_home_style(int using_home_style) {
        this.using_home_style = using_home_style;
    }

    public int getUsing_color_style() {
        return using_color_style;
    }

    public void setUsing_color_style(int using_color_style) {
        this.using_color_style = using_color_style;
    }

    public int getUsing_layout_style() {
        return using_layout_style;
    }

    public void setUsing_layout_style(int using_layout_style) {
        this.using_layout_style = using_layout_style;
    }

    public int getUsing_apc_style() {
        return using_apc_style;
    }

    public void setUsing_apc_style(int using_apc_style) {
        this.using_apc_style = using_apc_style;
    }

    public int getUsing_font_style() {
        if(using_font_style == -2){
            using_font_style = -1;
        }
        return using_font_style;
    }

    public void setUsing_font_style(int using_font_style) {
        this.using_font_style = using_font_style;
    }

    public long getGroup_corp_id() {
        return group_corp_id;
    }

    public void setGroup_corp_id(long group_corp_id) {
        this.group_corp_id = group_corp_id;
    }

    public int getStatus_flag() {
        return status_flag;
    }

    public void setStatus_flag(int status_flag) {
        this.status_flag = status_flag;
    }

    @Override
    public String toString() {
        return "首页："+using_home_style+" 门户："+portal_id+"  门户名称："+portal_name;
    }
}
