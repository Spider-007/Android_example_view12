package com.htmitech.emportal.base;

import com.htmitech.commonx.base.app.BaseFragmentActivity;
import com.htmitech.unit.BackgroundDetector;
import com.umeng.analytics.MobclickAgent;

import htmitech.com.componentlibrary.api.ComponentInit;

public class MyBaseFragmentActivity extends BaseFragmentActivity {
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        BackgroundDetector.getInstance().onAppPause(this);
        super.onPause();
    }

    @Override
    protected void onResume() {
        MobclickAgent.onResume(this);
        super.onResume();
    }
}
