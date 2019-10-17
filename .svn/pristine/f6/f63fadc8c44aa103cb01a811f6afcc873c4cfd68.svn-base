package com.htmitech.proxy.interfaces;

import com.minxing.client.tab.MenuTabItem;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/9/4.
 * 针对首页，应用中心在没有数据的时候，也就是页面为空的时候
 * 不能展示底部导航栏对应菜单（应用中心，首页）的红点。
 */

public class ConcerCountShow {
    private static ConcerCountShow mConcerCountShow;
    private ArrayList<MenuTabItem> mMenuTabItem;

    public static ConcerCountShow getInstances() {
        if (null == mConcerCountShow) {
            mConcerCountShow = new ConcerCountShow();
        }
        return mConcerCountShow;
    }

    public void setMenuItemInfo(ArrayList<MenuTabItem> mMenuTabItem){
        this.mMenuTabItem = mMenuTabItem;
    }

    public ArrayList<MenuTabItem> getMenuTabItem(){
        return mMenuTabItem;
    }

}
