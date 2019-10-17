package com.htmitech.htworkflowformpluginnew.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.htmitech.base.BaseFragmentActivity;
import com.htmitech.emportal.R;
import com.htmitech.emportal.ui.widget.ToastInfo;
import com.htmitech.htworkflowformpluginnew.constant.ActivityResultConstant;
import com.htmitech.htworkflowformpluginnew.entity.Others;
import com.htmitech.htworkflowformpluginnew.fragment.WebViewFormDetailFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import htmitech.com.componentlibrary.entity.DocResultInfo;
import htmitech.com.componentlibrary.entity.EditField;
import htmitech.com.componentlibrary.entity.EditFieldList;
import htmitech.com.componentlibrary.entity.FieldItem;
import htmitech.com.componentlibrary.entity.InfoRegion;
import htmitech.com.componentlibrary.entity.InfoTab;

public class WebSearchActivity extends BaseFragmentActivity implements View.OnClickListener {
    private ImageButton leftButton;
    private ImageButton leftButtonX;
    private TextView tv_title;
    private TextView tv_right_reset;
    private TextView tv_right_complete;
    private String titleName;
    private LinearLayout ll_content;
    private WebViewFormDetailFragment mWebViewFormDetailFragment;
    public String app_id;
    public String app_version_id;
    public String mobile_condition;
    public String url;
    public InfoTab tab;
    public int isWaterSecurity; //是不是支持水印
    public int com_workflow_mobileconfig_IM_enabled;//是否支持敏行聊天跳转
    public Map<String, Object> formMap;
    public DocResultInfo mDocResultInfo;
    public String comeShare = "-1";
    public  ArrayList<EditField> EditFields;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_search);
        titleName = getIntent().getStringExtra("appName");
        app_id = getIntent().getStringExtra("app_id");
        app_version_id = getIntent().getStringExtra("app_version_id");
        mobile_condition = getIntent().getStringExtra("mobile_condition");
        url = getIntent().getStringExtra("url");
        titleName = "筛选";
        formMap = new HashMap<String, Object>();
        mDocResultInfo = new DocResultInfo();
        initView();
        initData();
        EditFields = new ArrayList<EditField>();

    }



    public void initView() {
        leftButton = (ImageButton) findViewById(R.id.title_left_button);
        leftButtonX = (ImageButton) findViewById(R.id.title_left_button_x);
        ll_content = (LinearLayout) findViewById(R.id.ll_content);
        leftButtonX.setOnClickListener(this);
        leftButtonX.setVisibility(View.VISIBLE);
        leftButton.setVisibility(View.GONE);
        tv_title = (TextView) findViewById(R.id.title_name);
        tv_right_reset = (TextView) findViewById(R.id.tv_right_reset);
        tv_right_complete = (TextView) findViewById(R.id.tv_right_complete);
        tv_right_reset.setVisibility(View.VISIBLE);
        tv_right_complete.setVisibility(View.VISIBLE);
        tv_right_complete.setOnClickListener(this);
        tv_right_reset.setOnClickListener(this);
    }
    private void initData() {
        tv_title.setText(titleName);
        new Thread(new Runnable() {
            @Override
            public void run() {
                String jsonStr = mobile_condition;
                tab = new InfoTab();
                tab = JSON.parseObject(jsonStr, InfoTab.class);

                for(Others mOthers: ActivityResultConstant.otherses){
                    for(InfoRegion mInfoRegion:tab.regions){
                        for(FieldItem mFieldItem:mInfoRegion.getFieldItems()){
                            if(mOthers.fieldName.equals(mFieldItem.getKey())){

                                mFieldItem.setValue(mOthers.compareValue);
                            }
                        }

                    }
                }


                final InfoTab finalTab = tab;
                WebSearchActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(finalTab != null){
                            mWebViewFormDetailFragment = new WebViewFormDetailFragment(finalTab, app_id, 0);
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.add(R.id.ll_content, mWebViewFormDetailFragment);
                            transaction.show(mWebViewFormDetailFragment);
                            //提交事务
                            transaction.commit();
                        }else{
                            ToastInfo mToastInfo = ToastInfo.getInstance(WebSearchActivity.this);
                            mToastInfo.setText("请添加配置项");
                            mToastInfo.show(Toast.LENGTH_SHORT);
                        }

                    }
                });

            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_left_button_x:
                ActivityResultConstant.isBack = true;
                finish();
                break;
            case R.id.tv_right_complete:
                ArrayList<Others> otherses = new ArrayList<Others>();
                Others others = null;
                ActivityResultConstant.otherses.clear();
                for(EditField mEditField : mDocResultInfo.getResult().getEditFields()){
                    if(!TextUtils.isEmpty(mEditField.getKey()) && !TextUtils.isEmpty(mEditField.getValue())){
                        if(!EditFields.contains(mEditField)){
                            EditFields.add(mEditField);
                            others = new Others();
                            others.fieldName = mEditField.getKey();
                            others.compareValue = mEditField.getValue();
                            others.compareType = "=";
                            otherses.add(others);
                        }

                    }
                }
                ActivityResultConstant.otherses = otherses;
                ActivityResultConstant.isBack = false;
                finish();
                break;

            case R.id.tv_right_reset:
                mDocResultInfo = new DocResultInfo();
                EditFields = new ArrayList<EditField>();
                ActivityResultConstant.otherses.clear();
                String jsonStr = mobile_condition;
                tab = JSON.parseObject(jsonStr, InfoTab.class);
//                mWebViewFormDetailFragment.refash(tab.regions);

                mWebViewFormDetailFragment = new WebViewFormDetailFragment(tab, app_id, 0);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.ll_content, mWebViewFormDetailFragment);
                transaction.show(mWebViewFormDetailFragment);
                //提交事务
                transaction.commit();

                break;
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        ActivityResultConstant.isBack = true;
        return super.onKeyDown(keyCode, event);
    }
}


