package com.htmitech.htexceptionmanage.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.htmitech.app.Constant;
import com.htmitech.emportal.R;
import com.htmitech.emportal.base.MyBaseFragmentActivity;
import com.htmitech.htexceptionmanage.fragment.ManageExceptionTodoListFragment;
import com.htmitech.proxy.myenum.ApplicationAllEnum;
import com.htmitech.proxy.pop.ToRightPopMenum;
import com.minxing.client.ClientTabActivity;

/***
 * 消息提醒构件入口
 *
 * @author joe
 * @date 2017/04/17
 */
public class InitManageExceptionActivity extends MyBaseFragmentActivity implements View.OnClickListener {

    private ToRightPopMenum functionPopMenu;
    private ImageView functionButton;
    private TextView tv_exception_title;
    private boolean flag = false; //是否是首页 还是新开页面
    private int isWaterSecurity;//是否支持水印
    public String app_id;//appid 新加参数
    private ManageExceptionTodoListFragment manageExceptionTodoListFragment;
    @Override
    protected int getLayoutById() {
        return R.layout.activity_exception;
    }

    /**
     *
     */
    @Override
    protected void initView() {
        ImageView btn_exception_person = (ImageView) findViewById(R.id.btn_exception_person);
        ImageView btn_exception_person_home = (ImageView)findViewById(R.id.btn_exception_person_home);
        btn_exception_person.setOnClickListener(this);
        btn_exception_person_home.setOnClickListener(this);
        functionButton = (ImageView) findViewById(R.id.imageview_exception_more);
        functionPopMenu = new ToRightPopMenum(
                this);
        functionPopMenu.setView(ApplicationAllEnum.DB.tab_item_id, functionButton);
        tv_exception_title = (TextView) findViewById(R.id.tv_exception_title);
        functionButton.setOnClickListener(this);
        //接收的参数
        String com_alert_mobileconfig_include_security = getIntent().getStringExtra("com_alert_mobileconfig_include_security");//是否支持水印
        com_alert_mobileconfig_include_security = com_alert_mobileconfig_include_security == null ? "0" : com_alert_mobileconfig_include_security;
        isWaterSecurity = Integer.parseInt(com_alert_mobileconfig_include_security.equals("") ? "0" : com_alert_mobileconfig_include_security);
        flag = getIntent().getBooleanExtra("Type", false);
        app_id = getIntent().getStringExtra("app_id");
        Constant.addGeneralChannel = !flag ? "notHome":"home";
        if(!flag){
            btn_exception_person_home.setVisibility(View.VISIBLE);
            btn_exception_person.setVisibility(View.GONE);
        }
        //添加Fragment
        Bundle mBundle = new Bundle();
        mBundle.putString("app_id", app_id);
        mBundle.putInt("com_alert_mobileconfig_include_security", isWaterSecurity);
        manageExceptionTodoListFragment = new ManageExceptionTodoListFragment();
        manageExceptionTodoListFragment.setArguments(mBundle);
        FragmentTransaction transaction_task = this.getSupportFragmentManager()
                .beginTransaction();
        transaction_task.add(R.id.ll_exception, manageExceptionTodoListFragment);
        transaction_task.show(manageExceptionTodoListFragment);
        transaction_task.commit();
    }
    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.btn_exception_person_home:
            case R.id.btn_exception_person:
                if (flag) {
                    this.finish();
                } else {
                    ((ClientTabActivity) getParent()).callUserMeesageMain();
                }
                break;
            case R.id.imageview_exception_more:
                if (!functionPopMenu.isShowing()) {
                    functionPopMenu.showAsDropDown(arg0);
                }
                break;
            default:
                break;
        }
    }


}
