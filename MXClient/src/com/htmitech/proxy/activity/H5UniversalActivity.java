package com.htmitech.proxy.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.htmitech.emportal.R;
import com.htmitech.emportal.common.CommonSettings;
import com.htmitech.proxy.util.ZTActivityUnit;

import org.apache.cordova.Config;
import org.apache.cordova.CordovaActivity;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CordovaWebViewImpl;
import org.apache.cordova.engine.mx.SystemWebChromeClient;
import org.apache.cordova.engine.mx.SystemWebView;
import org.apache.cordova.engine.mx.SystemWebViewEngine;

import java.io.File;
import java.util.concurrent.ExecutorService;

/**
 * Created by Think on 2016/12/8.
 * 通用的展示h5插件
 */

public class H5UniversalActivity extends CordovaActivity implements View.OnClickListener {
    private ImageButton leftButton;
    private TextView tv_title;
    private ProgressBar bar;
    private String url;
    private String app_id;
    private String localFile = "";
    private String appName;
    private String com_active_navigation_show;
    private RelativeLayout system_title;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_web_plugin);
        init();
        ZTActivityUnit.setActivity(this);
        Intent intent = getIntent();
        app_id = intent.getStringExtra("app_id");
        localFile = intent.getStringExtra("localFile");
        appName = intent.getStringExtra("appName");
        com_active_navigation_show = intent.getStringExtra("com_active_navigation_show");
        localFile = localFile == null ? "" : localFile;
        if(localFile.equals("")){
            launchUrl = "file://" + CommonSettings.DEFAULT_DOCFILE_CACHE_FOLDER + File.separator + app_id + "/www/index.html";
        }else{
            launchUrl = "file://" + localFile;
        }

//        String url = "http://www.cnblogs.com/hubcarl/p/4202784.html";
        tv_title.setText(appName);
        appView.loadUrl(launchUrl);

        if(TextUtils.isEmpty(com_active_navigation_show)){
            com_active_navigation_show = "0";
        }
        system_title.setVisibility(com_active_navigation_show.equals("0") ? View.GONE : View.VISIBLE);
    }


    @Override
    protected CordovaWebView makeWebView() {
        SystemWebView webView = (SystemWebView) findViewById(R.id.webcordovawebview);
//        WebSettings settings = webView.getSettings();
//        //支持javascript
//        settings.setJavaScriptEnabled(true);
//        //自适应屏幕
//        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//        settings.setLoadWithOverviewMode(true);
//        //设定支持缩放
//        settings.setBuiltInZoomControls(true);
//        settings.setSupportZoom(true);
//        settings.setDisplayZoomControls(false);
//        //扩大比例的缩放
//        settings.setUseWideViewPort(true);
//        //设置 缓存模式
//        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        SystemWebViewEngine engine = new SystemWebViewEngine(webView);
        webView.setWebChromeClient(new SystemWebChromeClient(engine) {
            @Override
            public void onProgressChanged(WebView webView, int i) {
                if (i == 100) {
                    bar.setVisibility(View.INVISIBLE);
                } else {
                    if (View.INVISIBLE == bar.getVisibility()) {
                        bar.setVisibility(View.VISIBLE);
                    }
                    bar.setProgress(i);
                }
                super.onProgressChanged(webView, i);
            }
        });
        return new CordovaWebViewImpl(engine);
    }

    @Override
    protected void createViews() {

        initView();
        if (preferences.contains("BackgroundColor")) {
            int backgroundColor = preferences.getInteger("BackgroundColor", Color.BLACK);
            // Background of activity:
            appView.getView().setBackgroundColor(backgroundColor);
        }

        appView.getView().requestFocusFromTouch();
    }

    private void initView() {
        leftButton = (ImageButton) findViewById(R.id.title_left_button);
        leftButton.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.title_name);
        bar = (ProgressBar) findViewById(R.id.webpluginProgressBar);
        system_title = (RelativeLayout) findViewById(R.id.system_title);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_left_button:
                finish();
                break;
        }
    }
}


