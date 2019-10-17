package com.htmitech.emportal.ui.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.htmitech.emportal.R;

public class DaiBanTopTabIndicator extends FrameLayout implements
        ViewPager.OnPageChangeListener, View.OnClickListener {

    private MainViewPager mViewPager;
    private Button mDaiBanTab;
    private Button mYiBanTab;
    private Button mPrevenans;
    public static int currentPage = 0;
    private Context context;
    private int isStartValue = -1;//是否支持我发起我关注的 1 支持 0 不支持 -1 是默认
    private Button mYiBanTabRight;
    private int com_workflow_mobileconfig_tabbutton_style;
    public DaiBanTopTabIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        View.inflate(context, R.layout.daibantop_tab_indicator_layout, this);
        mDaiBanTab = (Button) this.findViewById(R.id.tab_daiban);
        mYiBanTab = (Button) this.findViewById(R.id.tab_yiban);
        mYiBanTabRight = (Button)this.findViewById(R.id.tab_yiban_right);
        mPrevenans = (Button) this.findViewById(R.id.tab_prevenans);
        mDaiBanTab.setOnClickListener(this);
        mYiBanTab.setOnClickListener(this);
        mPrevenans.setOnClickListener(this);
        mYiBanTabRight.setOnClickListener(this);
        mDaiBanTab.setSelected(true);
        mYiBanTabRight.setSelected(false);
        mYiBanTab.setSelected(false);
        mPrevenans.setSelected(false);

    }

    public void setViewPager(MainViewPager vp) {
        if (vp != null) {
            this.mViewPager = vp;
            mViewPager.setOnPageChangeListener(this);
            refreshTab();
        }
    }

    // begin OnPageChangeListener --
    @Override
    public void onPageScrolled(int position, float positionOffset,
                               int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        refreshTab();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tab_yiban:
                mYiBanTab.setSelected(true);
                mYiBanTabRight.setSelected(false);
                mDaiBanTab.setSelected(false);
                mPrevenans.setSelected(false);
                if(4 == com_workflow_mobileconfig_tabbutton_style || 6 == com_workflow_mobileconfig_tabbutton_style){
                    mViewPager.setCurrentItem(0, false);
                }else{
                    mViewPager.setCurrentItem(1, false);
                }

                break;
            case R.id.tab_yiban_right:
                mYiBanTabRight.setSelected(true);
                mYiBanTab.setSelected(false);
                mDaiBanTab.setSelected(false);
                mPrevenans.setSelected(false);
                if(4 == com_workflow_mobileconfig_tabbutton_style || 6 == com_workflow_mobileconfig_tabbutton_style){
                    mViewPager.setCurrentItem(0, false);
                }else{
                    mViewPager.setCurrentItem(1, false);
                }
                break;
            case R.id.tab_daiban:
                mDaiBanTab.setSelected(true);
                mYiBanTabRight.setSelected(false);
                mYiBanTab.setSelected(false);
                mPrevenans.setSelected(false);
                mViewPager.setCurrentItem(0, false);
                break;
            case R.id.tab_prevenans:
                mYiBanTabRight.setSelected(false);
                mDaiBanTab.setSelected(false);
                mYiBanTab.setSelected(false);
                mPrevenans.setSelected(true);
                mViewPager.setCurrentItem(mViewPager.getChildCount() - 1, false);
                break;
            default:
                break;
        }
        refreshTab();
    }

    private void refreshTab() {
        switch (mViewPager.getCurrentItem()) {
            case 1:
                if(isStartValue == 1){
                    mYiBanTab.setVisibility(VISIBLE);
                    mYiBanTabRight.setVisibility(View.GONE);
                }else{
                    mYiBanTab.setVisibility(GONE);
                    mYiBanTabRight.setVisibility(View.VISIBLE);
                }
                break;
            case 0:
                if(isStartValue == 1){
                    mYiBanTab.setVisibility(View.VISIBLE);
                    mYiBanTabRight.setVisibility(View.GONE);
                }else{
                    mYiBanTab.setVisibility(View.GONE);
                    mYiBanTabRight.setVisibility(View.VISIBLE);
                }
                break;
            case 2:
                mYiBanTabRight.setVisibility(View.GONE);
                mYiBanTab.setVisibility(View.VISIBLE);
                break;
        }
        setCom_workflow_mobileconfig_tabbutton_style(com_workflow_mobileconfig_tabbutton_style,false);
        currentPage = mViewPager.getCurrentItem();
    }

    public void setIsStartValue(int isStartValue){
        this.isStartValue = isStartValue;
        if(isStartValue == 1 && mYiBanTab != null){
            mYiBanTab.setVisibility(View.VISIBLE);
            mYiBanTabRight.setVisibility(View.GONE);
            mPrevenans.setVisibility(VISIBLE);
        }
    }

    public int getCom_workflow_mobileconfig_tabbutton_style() {
        return com_workflow_mobileconfig_tabbutton_style;
    }

    public void setCom_workflow_mobileconfig_tabbutton_style(int com_workflow_mobileconfig_tabbutton_style,boolean flag) {
        this.com_workflow_mobileconfig_tabbutton_style = com_workflow_mobileconfig_tabbutton_style;
        if(com_workflow_mobileconfig_tabbutton_style == 1){
            mDaiBanTab.setText("待办");
            mYiBanTab.setText("已办");
        }else if(com_workflow_mobileconfig_tabbutton_style == 2){
            mDaiBanTab.setText("待阅");
            mYiBanTab.setText("已阅");
            mYiBanTabRight.setText("已阅");
        }else if(com_workflow_mobileconfig_tabbutton_style == 3){
            mYiBanTab.setVisibility(View.GONE);
            mYiBanTabRight.setVisibility(View.GONE);
        }else if(com_workflow_mobileconfig_tabbutton_style == 4){
//            mYiBanTab.setVisibility(View.GONE);
//            mYiBanTabRight.setVisibility(View.VISIBLE);
            if(flag){
                mYiBanTab.setSelected(true);
                mYiBanTabRight.setSelected(true);
            }

            mDaiBanTab.setVisibility(GONE);
        }else if(com_workflow_mobileconfig_tabbutton_style == 5){
//            mYiBanTab.setVisibility(View.GONE);
//            mYiBanTabRight.setVisibility(View.VISIBLE);
            mDaiBanTab.setText("待阅");

            mYiBanTab.setVisibility(View.GONE);
            mYiBanTabRight.setVisibility(View.GONE);
        }
        else if(com_workflow_mobileconfig_tabbutton_style == 6){
//            mYiBanTab.setVisibility(View.GONE);
//            mYiBanTabRight.setVisibility(View.VISIBLE);
            mYiBanTab.setText("已阅");
            mYiBanTabRight.setText("已阅");
            if(flag){
                mYiBanTab.setSelected(true);
                mYiBanTabRight.setSelected(true);
            }
            mDaiBanTab.setVisibility(GONE);
        }
    }
}
