package com.htmitech.proxy.activity;

import android.os.Bundle;

import com.htmitech.emportal.R;

import org.apache.cordova.CordovaActivity;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CordovaWebViewImpl;
import org.apache.cordova.engine.mx.SystemWebView;
import org.apache.cordova.engine.mx.SystemWebViewEngine;

/**
 * Created by Think on 2016/12/9.
 */

public abstract class H5BaseActivity extends CordovaActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.init();
        setContentView(getLayoutId());
        initViews();
    }

    /**
     * 获取布局id，用于setContentView。
     *
     * @return id
     */
    protected abstract int getLayoutId();

    /**
     * 初始化View。
     */
    protected abstract void initViews();

    @Override
    protected  CordovaWebView makeWebView() {
        SystemWebView webView = (SystemWebView)findViewById(R.id.cordovaWebView);
        return new CordovaWebViewImpl(new SystemWebViewEngine(webView));
    }

}
