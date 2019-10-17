package com.htmitech.proxy.myenum;

import htmitech.com.componentlibrary.unit.PreferenceUtils;

/**
 * Created by htrf-pc on 2016/11/16.
 */
public enum  HomePageStyleEnum {

    CTFG("磁铁",1,"metropage") {
        @Override
        public void saveHomePage() {
            PreferenceUtils.saveCurrentPage(this.code);
        }
    },
    WGFG("规则网格",2,"gridpage") {
        @Override
        public void saveHomePage() {
            PreferenceUtils.saveCurrentPage(this.code);
        }
    },
    FZDHFG("功能导航",3,"grouppage") {
        @Override
        public void saveHomePage() {
            PreferenceUtils.saveCurrentPage(this.code);
        }
    },
    DBLBFG("代办列表风格",5,"group_fz_page") {
        @Override
        public void saveHomePage() {
            PreferenceUtils.saveCurrentPage(this.code);
        }
    },
    DBLBFGS("代办列表风格",4,"group_fz_page") {
        @Override
        public void saveHomePage() {
            PreferenceUtils.saveCurrentPage(this.code);
        }
    };
    public String name;
    public int homeStyle;

    public String code;
    HomePageStyleEnum(String name){
        this.name = name;
    }

    HomePageStyleEnum(String name,int homeStyle,String code){
        this.name = name;
        this.homeStyle = homeStyle;
        this.code = code;
    }

    public static HomePageStyleEnum getHomePageStyle(int homeStyle){
        for (HomePageStyleEnum c : HomePageStyleEnum.values()) {
            if (c.homeStyle == homeStyle) {
                return c;
            }
        }
        return WGFG;
    }


    public static HomePageStyleEnum getCodeHomePageStyle(String  code){
        for (HomePageStyleEnum c : HomePageStyleEnum.values()) {
            if (c.code == code) {
                return c;
            }
        }
        return WGFG;
    }

    public static HomePageStyleEnum getHomePageStyle(String name){
        for (HomePageStyleEnum c : HomePageStyleEnum.values()) {
            if (c.name.equals(name)) {
                return c;
            }
        }
        return null;
    }


    public static void saveHomePages(int homeStyle){
        for (HomePageStyleEnum c : HomePageStyleEnum.values()) {
            if (c.homeStyle == homeStyle) {
                c.saveHomePage();
            }
        }
    }
    public abstract void saveHomePage();
}
