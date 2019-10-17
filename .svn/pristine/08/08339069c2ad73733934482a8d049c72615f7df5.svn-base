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
 * Created by htrf-pc on 2016/11/9.
 */
public class PrivateDealLeftButtom implements CallBackLeftDidebar {
    private Context context;

    private CallBackLeftJC mCallBackLeftJCs;

    private ApplicationAllEnum mApplicationAllEnum;


    private CallBackButtom mCallBackButtoms;

    private HomePageStyleEnum mHomePageStyleEnum;
    public PrivateDealLeftButtom(Context context, ApplicationAllEnum leftEnum, CallBackLeftJC mCallBackLeftJCs){
        this.context = context;
        this.mCallBackLeftJCs = mCallBackLeftJCs;
        this.mApplicationAllEnum = leftEnum;
    }


    public PrivateDealLeftButtom(Context context, ApplicationAllEnum buttomEnum,CallBackButtom mCallBackButtoms){
        this.context = context;
        this.mApplicationAllEnum = buttomEnum;
        this.mCallBackButtoms = mCallBackButtoms;
    }

    public PrivateDealLeftButtom(Context context, HomePageStyleEnum mHomePageStyleEnum,CallBackButtom mCallBackButtoms){
        this.context = context;
        this.mHomePageStyleEnum = mHomePageStyleEnum;
        this.mCallBackButtoms = mCallBackButtoms;
    }

    @Override
    public ResideMenuItem leftShowIntent() {
        ResideMenunItemFactory mResideMenunItemFactory = ResideMenunItemFactory.getInstance();

        return mResideMenunItemFactory.getResideMenuItem(mApplicationAllEnum, context, mCallBackLeftJCs);
    }

    @Override
    public MenuTabItem ButtomShowIntent() {
        MenuTabItemFactory mMenuTabItemFactory = MenuTabItemFactory.getInstance();
        return  mMenuTabItemFactory.getMenuTabItem(context,mApplicationAllEnum,mCallBackButtoms);
    }

    @Override
    public ArrayList<MenuTabItem> getStyleMenuList() {
        MenuTabItemFactory mMenuTabItemFactory = MenuTabItemFactory.getInstance();
        return mMenuTabItemFactory.getStyleEnum(context,mHomePageStyleEnum,mCallBackButtoms);
    }
}
