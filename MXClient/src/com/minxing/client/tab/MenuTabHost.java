package com.minxing.client.tab;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TabHost;

import com.htmitech.proxy.util.LogMesgUpUtil;

import htmitech.com.componentlibrary.api.ComponentInit;

public class MenuTabHost extends TabHost {
    public final static String TAB_TAG_CHAT = "chat";
    public final static String TAB_TAG_CONTACTS = "contacts";
    public final static String TAB_TAG_APP_CENTER = "img_oa_appcenter03";
    public final static String TAB_TAG_CIRCLES = "circle";
    public final static String TAB_TAG_TODO = "todo";
    public final static String TAB_TAG_TOREAD = "toread";
    public final static String TAB_TAG_HOME = "home";    //home
    public final static String TAB_CJ = "chajian";//插件
    private List<MenuTabItem> menuTabItemList = new ArrayList<MenuTabItem>();

    public MenuTabHost(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MenuTabHost(Context context) {
        super(context);
    }

    @Override
    public void setCurrentTab(int index) {
        try {
            MenuTabItem menuTabItem = getMenuTabItemByIndex(index); //查到在Tab中的选项卡
            //设置当前选项卡被重复单击的行为
            if (index == getCurrentTab()) {    //如果传入下标 等于当前选项卡下标
                if (null != menuTabItem.getOnReClickListener()) {    //如果当前选项卡的重复单击事件监听不为空
                    menuTabItem.getOnReClickListener().onReClick(menuTabItem); //如果重复单击将可调用重复单击事件。 传入 当前item
                }
            } else {    //要切换的选项卡不是当下的选项卡	//处理一下该选显卡切换之前的一切行为(如果不为空的话)
                if (null != menuTabItem.getBeforeTabChangeListener()) {
                    menuTabItem.getBeforeTabChangeListener().beforeTabChange(menuTabItem);
                }
                super.setCurrentTab(index);  //调用父类正常切换选项卡的逻辑
                ComponentInit.getInstance().getmILogUpdateCallListener().logMessage(LogMesgUpUtil.getLogMsg(menuTabItem.getButtomEnum()));
                //弹出引导页逻辑
                if (null != menuTabItem.getOnClickListener()) {
                    menuTabItem.getOnClickListener().onClick();
                }
            }
        } catch (Exception dx) {
        }
    }

    /**
     * 添加底部菜单
     *
     * @param id       底部菜单唯一标示.
     * @param intent   底部菜单对应的intent.
     * @param drawable 底部菜单对应图标.
     * @param name     底部菜单名称.
     */
    public void addMenuItem(MenuTabItem tabItem) {
//		menuTabItemList.add(tabItem);
//		//TabHost相当于浏览器中浏览器分布的集合，而Tabspec则相当于浏览器中的每一个分页面。
//		TabHost.TabSpec tSpec = newTabSpec(tabItem.getTag());
//		tSpec.setContent(tabItem.getIntent()); //设置此分页的内容， 内容设置成Intent表示 包含的是页面而不是其他组件或是布局
//		tSpec.setIndicator(tabItem.getView()); //设置此分页的显示视图
//		addTab(tSpec);	//每一个配置好的选项卡实例

        // 设置mCurrentTab为非-1,addtab时候不会进入setCurrentTab()
        try {

            Field idcurrent = this.getClass().getSuperclass().getDeclaredField("mCurrentTab");
            idcurrent.setAccessible(true);
            idcurrent.setInt(this, 10);
        } catch (Exception e) {
            e.printStackTrace();
        }
        menuTabItemList.add(tabItem);
        //TabHost相当于浏览器中浏览器分布的集合，而Tabspec则相当于浏览器中的每一个分页面。
        TabHost.TabSpec tSpec = newTabSpec(tabItem.getTag());
        tSpec.setContent(tabItem.getIntent()); //设置此分页的内容， 内容设置成Intent表示 包含的是页面而不是其他组件或是布局
        tSpec.setIndicator(tabItem.getView()); //设置此分页的显示视图
        addTab(tSpec);    //每一个配置好的选项卡实例
        try {
            Field idcurrent = getClass().getSuperclass().getDeclaredField("mCurrentTab");
            idcurrent.setAccessible(true);
            idcurrent.setInt(this, -1);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //在列表中获取某一个选项卡项
    public MenuTabItem getMenuTabItemByIndex(int index) {
        return menuTabItemList.get(index);
    }

    public List<MenuTabItem> getMenuTabItem() {
        return menuTabItemList;
    }
}
