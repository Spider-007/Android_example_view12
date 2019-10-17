package com.htmitech.proxy.myenum;

/**
 * 底部   2016/11/14.
 */
public enum ButtomEnum {


    DB("待办"),

    TXL("通讯录"),

    GT("app_messages"), //沟通

    GZQ("app_worksocial"),//工作圈

    CJ("插件"),
//    CT("磁铁"),
//
//    WG("网格"),
//
//    FZDH("分组导航");
    ZY("主页");



    public String s;
    ButtomEnum(String s){
        this.s = s;
    }

}
