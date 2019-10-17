package com.htmitech.htcommonformplugin.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.htmitech.base.BaseFragmentActivity;
import com.htmitech.emportal.R;
import com.htmitech.emportal.common.lib.residemenu.ResideMenu;
import com.htmitech.htcommonformplugin.fragment.HaveDoneListFragment;
import com.htmitech.proxy.doman.AppInfo;
import com.htmitech.proxy.myenum.ApplicationAllEnum;
import com.htmitech.proxy.pop.ToRightPopMenum;
import com.minxing.client.ClientTabActivity;

/**
 * 通用表单的代办已办列表
 */
public class GeneralFormChildActivity extends BaseFragmentActivity {
    private LinearLayout ll_general_from_child;
    private TextView report_title;
    private boolean flag = false;
    private ImageView btn_back;
    private ToRightPopMenum functionPopMenu;
    private ImageView imageview_daiban_more;
    private long tab_item_id;
    private int  isShare;
    private int  actionButtonStyle;
    private int  isWaterSecurity ;
    private int  config_IM_enabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gengral_from_child);
        initView();
        initData();
    }

    public void initView() {
        ll_general_from_child = (LinearLayout) findViewById(R.id.ll_general_from_child);
        report_title = (TextView) findViewById(R.id.report_title);
        btn_back = (ImageView) findViewById(R.id.btn_back);
        imageview_daiban_more = (ImageView) findViewById(R.id.imageview_daiban_more);
    }


    public void initData() {
        HaveDoneListFragment mDynamicFragment = new HaveDoneListFragment();
        String appName = getIntent().getStringExtra("appName");
        flag = getIntent().getBooleanExtra("isHome", false);
        String todoFlag = getIntent().getStringExtra("com_commonform_plugin_selector_paramter_todoflag");
        String com_commonform_mobileconfig_IM_enabled = getIntent().getStringExtra("com_commonform_mobileconfig_IM_enabled");
        String com_commonform_mobileconfig_include_security = getIntent().getStringExtra("com_commonform_mobileconfig_include_security");
        String com_commonform_mobileconfig_include_share = getIntent().getStringExtra("com_commonform_mobileconfig_include_share");
        String com_commonform_mobileconfig_actionbutton_style = getIntent().getStringExtra("com_commonform_mobileconfig_actionbutton_style");

        com_commonform_mobileconfig_include_security = com_commonform_mobileconfig_include_security == null ? "0":com_commonform_mobileconfig_include_security;
        com_commonform_mobileconfig_include_share = com_commonform_mobileconfig_include_share == null ? "0":com_commonform_mobileconfig_include_share;
        com_commonform_mobileconfig_IM_enabled = com_commonform_mobileconfig_IM_enabled == null ? "0":com_commonform_mobileconfig_IM_enabled;
        com_commonform_mobileconfig_actionbutton_style = com_commonform_mobileconfig_actionbutton_style == null ? "0" : com_commonform_mobileconfig_actionbutton_style;

        isShare = Integer.parseInt(com_commonform_mobileconfig_include_share.equals("") ? "0" : com_commonform_mobileconfig_include_share);
        actionButtonStyle = Integer.parseInt(com_commonform_mobileconfig_actionbutton_style.equals("") ? "0" : com_commonform_mobileconfig_actionbutton_style);
        isWaterSecurity = Integer.parseInt(com_commonform_mobileconfig_include_security.equals("") ? "0" : com_commonform_mobileconfig_include_security);
        config_IM_enabled = Integer.parseInt(com_commonform_mobileconfig_IM_enabled.equals("") ? "0" : com_commonform_mobileconfig_IM_enabled);
        tab_item_id = getIntent().getLongExtra("tab_item_id", 0);






        Bundle mBundle = new Bundle();
        mBundle.putString("com_commonform_plugin_selector_paramter_todoflag", todoFlag);
        mBundle.putInt("com_commonform_mobileconfig_IM_enabled", config_IM_enabled);
        mBundle.putInt("com_commonform_mobileconfig_include_security", isWaterSecurity);
        mBundle.putInt("com_commonform_mobileconfig_include_share", isShare);
        mBundle.putInt("com_commonform_mobileconfig_actionbutton_style", actionButtonStyle);
        mDynamicFragment.setArguments(mBundle);


        if(flag){
            btn_back.setImageResource(R.drawable.nav_profile);
        }
        report_title.setText(appName);
//        mDynamicFragment.setArguments(mBundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.ll_general_from_child, mDynamicFragment);
        transaction.show(mDynamicFragment);
        functionPopMenu = new ToRightPopMenum(
                this);
        functionPopMenu.setView(tab_item_id, imageview_daiban_more);

        //提交事务
        transaction.commit();

    }

    public void onClickBack(View v){
        if(!flag) {
            this.finish();
        } else {
            ((ClientTabActivity) getParent()).callUserMeesageMain();
        }
    }

    public void onClickRight(View v){
        if (!functionPopMenu.isShowing()) {
            functionPopMenu.showAsDropDown(v);
        }
    }

}
