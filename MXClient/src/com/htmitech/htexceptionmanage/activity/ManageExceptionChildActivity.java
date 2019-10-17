package com.htmitech.htexceptionmanage.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.htmitech.base.BaseFragmentActivity;
import com.htmitech.emportal.R;
import com.htmitech.htexceptionmanage.fragment.ManageExceptionHaveDoneListFragment;
import com.htmitech.proxy.pop.ToRightPopMenum;
import com.minxing.client.ClientTabActivity;

/**
 * 提醒中心 插件入口
 * @author joe
 * @date 2017-9-25 11:17:07
 */
public class ManageExceptionChildActivity extends BaseFragmentActivity {
    private LinearLayout ll_workflow_from_child;
    private TextView report_title;
    private boolean flag = false;
    private ImageView btn_back;
    private ToRightPopMenum functionPopMenu;
    private ImageView imageview_daiban_more;
    private long tab_item_id;
    private ImageView btn_back_home;
    private int isWaterSecurity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exception_child);
        initView();
        initData();
    }

    public void initView() {
        ll_workflow_from_child = (LinearLayout) findViewById(R.id.ll_workflow_from_child);
        report_title = (TextView) findViewById(R.id.report_title);
        btn_back = (ImageView) findViewById(R.id.btn_back);
        btn_back_home = (ImageView)findViewById(R.id.btn_back_home);
        imageview_daiban_more = (ImageView) findViewById(R.id.imageview_daiban_more);
    }


    public void initData() {
        ManageExceptionHaveDoneListFragment mDynamicFragment = new ManageExceptionHaveDoneListFragment();
        String appName = getIntent().getStringExtra("appName");
        flag = getIntent().getBooleanExtra("isHome", false);
        tab_item_id = getIntent().getLongExtra("tab_item_id", 0);
        String com_alert_mobileconfig_include_security = getIntent().getStringExtra("com_alert_mobileconfig_include_security");//是否支持水印
        String com_alert_plugin_selector_paramter_days = getIntent().getStringExtra("com_alert_plugin_selector_paramter_days");
        String com_alert_plugin_selector_paramter_flag = getIntent().getStringExtra("com_alert_plugin_selector_paramter_flag");
        String com_alert_plugin_selector_paramter_sourcetype = getIntent().getStringExtra("com_alert_plugin_selector_paramter_sourcetype");
        String com_alert_plugin_selector_paramter_title_keyword = getIntent().getStringExtra("com_alert_plugin_selector_paramter_title_keyword");
        com_alert_mobileconfig_include_security = com_alert_mobileconfig_include_security == null ? "0" : com_alert_mobileconfig_include_security;
        isWaterSecurity = Integer.parseInt(com_alert_mobileconfig_include_security.equals("") ? "0" : com_alert_mobileconfig_include_security);

        Bundle mBundle = new Bundle();
        mBundle.putString("com_alert_plugin_selector_paramter_days", com_alert_plugin_selector_paramter_days);
        mBundle.putString("com_alert_plugin_selector_paramter_flag", com_alert_plugin_selector_paramter_flag);
        mBundle.putString("com_alert_plugin_selector_paramter_sourcetype", com_alert_plugin_selector_paramter_sourcetype);
        mBundle.putString("com_alert_plugin_selector_paramter_title_keyword", com_alert_plugin_selector_paramter_title_keyword);
        mBundle.putInt("com_alert_mobileconfig_include_security",isWaterSecurity);
        mDynamicFragment.setArguments(mBundle);


        if(flag){
            btn_back.setVisibility(View.GONE);
            btn_back_home.setVisibility(View.VISIBLE);
        }
        functionPopMenu = new ToRightPopMenum(
                this);
        functionPopMenu.setView(tab_item_id, imageview_daiban_more);
        report_title.setText(appName);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.ll_workflow_from_child, mDynamicFragment);
        transaction.show(mDynamicFragment);
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
