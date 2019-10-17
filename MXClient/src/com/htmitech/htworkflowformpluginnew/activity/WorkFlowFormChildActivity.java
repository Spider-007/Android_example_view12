package com.htmitech.htworkflowformpluginnew.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.htmitech.app.Constant;
import com.htmitech.base.BaseFragmentActivity;
import com.htmitech.emportal.R;
import com.htmitech.htworkflowformpluginnew.fragment.WorkFlowHaveDoneListFragment;
import com.htmitech.proxy.pop.ToRightPopMenum;
import com.minxing.client.ClientTabActivity;

/**
 * 工作流表单 插件入口
 *
 * @author joe
 * @date 2017-06-30 12:08:38
 */
public class WorkFlowFormChildActivity extends BaseFragmentActivity {
    private LinearLayout ll_workflow_from_child;
    private TextView report_title;
    private boolean flag = false;
    private ImageView btn_back;
    private ToRightPopMenum functionPopMenu;
    private ImageView imageview_daiban_more;
    private long tab_item_id;
    private int isShare;
    private int actionButtonStyle;
    private int isWaterSecurity;
    private int config_IM_enabled;
    private ImageView btn_back_home;
    private String com_workflow_mobileconfig_importance_workas_toreadflag;
    private String starTime;
    private String endTime;
    private String com_workflow_mobileconfig_flowhistory_display_order;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workflow_from_child);
        initView();
        initData();
    }

    public void initView() {
        ll_workflow_from_child = (LinearLayout) findViewById(R.id.ll_workflow_from_child);
        report_title = (TextView) findViewById(R.id.report_title);
        btn_back = (ImageView) findViewById(R.id.btn_back);
        btn_back_home = (ImageView) findViewById(R.id.btn_back_home);
        imageview_daiban_more = (ImageView) findViewById(R.id.imageview_daiban_more);
    }


    public void initData() {
        WorkFlowHaveDoneListFragment mDynamicFragment = new WorkFlowHaveDoneListFragment();
        String appName = getIntent().getStringExtra("appName");
        flag = getIntent().getBooleanExtra("isHome", false);
        String todoFlag = getIntent().getStringExtra("com_workflow_plugin_selector_paramter_TodoFlag");
        String com_workflow_mobileconfig_IM_enabled = getIntent().getStringExtra("com_workflow_mobileconfig_IM_enabled");
        String com_workflow_mobileconfig_include_security = getIntent().getStringExtra("com_workflow_mobileconfig_include_security");
        String com_workflow_mobileconfig_include_share = getIntent().getStringExtra("com_workflow_mobileconfig_include_share");
        String com_workflow_mobileconfig_actionbutton_style = getIntent().getStringExtra("com_workflow_mobileconfig_actionbutton_style");
        String com_workflow_mobileconfig_include_options_str = getIntent().getStringExtra("com_workflow_mobileconfig_include_options");
        starTime = getIntent().getStringExtra("com_workflow_plugin_selector_paramter_startTime");
        endTime = getIntent().getStringExtra("com_workflow_plugin_selector_paramter_endTime");
        com_workflow_mobileconfig_flowhistory_display_order = getIntent().getStringExtra("com_workflow_mobileconfig_flowhistory_display_order");
        com_workflow_mobileconfig_flowhistory_display_order = TextUtils.isEmpty(com_workflow_mobileconfig_flowhistory_display_order) ? "0" : com_workflow_mobileconfig_flowhistory_display_order;
        Constant.com_workflow_mobileconfig_flowhistory_display_order = com_workflow_mobileconfig_flowhistory_display_order;
        com_workflow_mobileconfig_include_security = com_workflow_mobileconfig_include_security == null ? "0" : com_workflow_mobileconfig_include_security;
        com_workflow_mobileconfig_include_share = com_workflow_mobileconfig_include_share == null ? "0" : com_workflow_mobileconfig_include_share;
        com_workflow_mobileconfig_IM_enabled = com_workflow_mobileconfig_IM_enabled == null ? "0" : com_workflow_mobileconfig_IM_enabled;
        com_workflow_mobileconfig_actionbutton_style = com_workflow_mobileconfig_actionbutton_style == null ? "0" : com_workflow_mobileconfig_actionbutton_style;
        com_workflow_mobileconfig_include_options_str = com_workflow_mobileconfig_include_options_str == null ? "0" : com_workflow_mobileconfig_include_options_str;
        com_workflow_mobileconfig_importance_workas_toreadflag = getIntent().getStringExtra("com_workflow_plugin_selector_paramter_importance_workas_toreadflag");
        String com_workflow_plugin_selector_paramter_docListTypeCode = getIntent().getStringExtra("com_workflow_plugin_selector_paramter_docListTypeCode");
        String com_workflow_mobileconfig_todoflag = getIntent().getStringExtra("com_workflow_plugin_selector_paramter_todoflag");
        if(TextUtils.isEmpty(com_workflow_mobileconfig_importance_workas_toreadflag)){
            com_workflow_mobileconfig_importance_workas_toreadflag = "";
        }
        if(!TextUtils.isEmpty(com_workflow_mobileconfig_todoflag)){
            com_workflow_mobileconfig_importance_workas_toreadflag = "";
        }
        isShare = Integer.parseInt(com_workflow_mobileconfig_include_share.equals("") ? "0" : com_workflow_mobileconfig_include_share);
        actionButtonStyle = Integer.parseInt(com_workflow_mobileconfig_actionbutton_style.equals("") ? "0" : com_workflow_mobileconfig_actionbutton_style);
        isWaterSecurity = Integer.parseInt(com_workflow_mobileconfig_include_security.equals("") ? "0" : com_workflow_mobileconfig_include_security);
        config_IM_enabled = Integer.parseInt(com_workflow_mobileconfig_IM_enabled.equals("") ? "0" : com_workflow_mobileconfig_IM_enabled);
        int com_workflow_mobileconfig_include_options = Integer.parseInt(com_workflow_mobileconfig_include_options_str.equals("") ? "0" : com_workflow_mobileconfig_include_options_str);
        tab_item_id = getIntent().getLongExtra("tab_item_id", 0);


        Bundle mBundle = new Bundle();
        mBundle.putString("com_workflow_plugin_selector_paramter_TodoFlag", todoFlag);
        mBundle.putInt("com_workflow_mobileconfig_IM_enabled", config_IM_enabled);
        mBundle.putInt("com_workflow_mobileconfig_include_security", isWaterSecurity);
        mBundle.putInt("com_workflow_mobileconfig_include_share", isShare);
        mBundle.putInt("com_workflow_mobileconfig_actionbutton_style", actionButtonStyle);
        mBundle.putString("com_workflow_mobileconfig_importance_workas_toreadflag", com_workflow_mobileconfig_importance_workas_toreadflag);
        mBundle.putString("com_workflow_plugin_selector_paramter_docListTypeCode", com_workflow_plugin_selector_paramter_docListTypeCode);
        mBundle.putString("startTime", starTime);
        mBundle.putString("endTime", endTime);
        mBundle.putInt("com_workflow_mobileconfig_include_options", com_workflow_mobileconfig_include_options);
        mDynamicFragment.setArguments(mBundle);


        if (flag) {
            btn_back.setVisibility(View.GONE);
            btn_back_home.setVisibility(View.VISIBLE);
        }
        report_title.setText(appName);
//        mDynamicFragment.setArguments(mBundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.ll_workflow_from_child, mDynamicFragment);
        transaction.show(mDynamicFragment);
        functionPopMenu = new ToRightPopMenum(
                this);
        functionPopMenu.setView(tab_item_id, imageview_daiban_more);

        //提交事务
        transaction.commit();

    }

    public void onClickBack(View v) {
        if (!flag) {
            this.finish();
        } else {
            ((ClientTabActivity) getParent()).callUserMeesageMain();
        }
    }

    public void onClickRight(View v) {
        if (!functionPopMenu.isShowing()) {
            functionPopMenu.showAsDropDown(v);
        }
    }

}
