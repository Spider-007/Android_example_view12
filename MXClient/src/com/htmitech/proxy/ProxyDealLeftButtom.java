package com.htmitech.proxy;

import android.content.Context;

import com.htmitech.emportal.common.lib.residemenu.ResideMenuItem;
import com.htmitech.proxy.interfaces.CallBackButtom;
import com.htmitech.proxy.interfaces.CallBackLeftJC;
import com.htmitech.proxy.myenum.ApplicationAllEnum;
import com.htmitech.proxy.myenum.HomePageStyleEnum;
import com.minxing.client.tab.MenuTabItem;

import java.util.ArrayList;

/**
 * 代理类来间接实现
 *
 */
public class ProxyDealLeftButtom implements CallBackLeftDidebar {
    private CallBackLeftDidebar mPrivateDealLeft;

    private Context context;

    private CallBackLeftJC mCallBackLeftJCs;


    private ApplicationAllEnum mApplicationAllEnum; //底部

    private CallBackButtom mCallBackButtoms;

    private HomePageStyleEnum mHomePageStyleEnum;
    public ProxyDealLeftButtom(Context context, CallBackLeftJC mCallBackLeftJCs, ApplicationAllEnum leftEnum){
        this.context = context;
        this.mCallBackLeftJCs = mCallBackLeftJCs;
        this.mApplicationAllEnum = leftEnum;
    }

    public ProxyDealLeftButtom(Context context, ApplicationAllEnum buttomEnum,CallBackButtom mCallBackButtoms){
        this.context = context;
        this.mApplicationAllEnum = buttomEnum;
        this.mCallBackButtoms = mCallBackButtoms;
    }

    public ProxyDealLeftButtom(Context context,ApplicationAllEnum buttomEnum, HomePageStyleEnum mHomePageStyleEnum,CallBackButtom mCallBackButtoms){
        this.context = context;
        this.mApplicationAllEnum = buttomEnum;
        this.mHomePageStyleEnum = mHomePageStyleEnum;
        this.mCallBackButtoms = mCallBackButtoms;
    }

    @Override
    public ResideMenuItem leftShowIntent() {
       mPrivateDealLeft = new PrivateDealLeftButtom(context,mApplicationAllEnum,mCallBackLeftJCs);

       return  mPrivateDealLeft.leftShowIntent();
    }

    @Override
    public MenuTabItem ButtomShowIntent() {
        mPrivateDealLeft = new PrivateDealLeftButtom(context,mApplicationAllEnum,mCallBackButtoms);

        return  mPrivateDealLeft.ButtomShowIntent();
    }

    @Override
    public ArrayList<MenuTabItem> getStyleMenuList() {
        mPrivateDealLeft = new PrivateDealLeftButtom(context,mHomePageStyleEnum,mCallBackButtoms);
        return mPrivateDealLeft.getStyleMenuList();
    }
}
