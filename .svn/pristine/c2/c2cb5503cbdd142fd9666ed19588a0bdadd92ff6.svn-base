package com.htmitech.proxy.activity;

import android.graphics.Color;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.htmitech.emportal.R;

import org.apache.cordova.CordovaActivity;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CordovaWebViewImpl;
import org.apache.cordova.engine.mx.SystemWebChromeClient;
import org.apache.cordova.engine.mx.SystemWebView;
import org.apache.cordova.engine.mx.SystemWebViewClient;
import org.apache.cordova.engine.mx.SystemWebViewEngine;

public class WebPluginActivity extends CordovaActivity implements View.OnClickListener {
    private ImageButton leftButton;
    private TextView tv_title;
    private ProgressBar bar;
    private String url;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_web_plugin);
        init();
        url = getIntent().getStringExtra("url");
        String titleName = getIntent().getStringExtra("appName");
        tv_title.setText(titleName);

//        String url = "http://www.cnblogs.com/hubcarl/p/4202784.html";
        
        appView.loadUrl(url);
    }


    @Override
    protected CordovaWebView makeWebView() {
        SystemWebView webView = (SystemWebView) findViewById(R.id.webcordovawebview);
        WebSettings settings = webView.getSettings();
        //支持javascript
        settings.setJavaScriptEnabled(true);
        //自适应屏幕
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setLoadWithOverviewMode(true);
        //设定支持缩放
        settings.setBuiltInZoomControls(true);
        settings.setSupportZoom(true);
        settings.setDisplayZoomControls(false);
        //扩大比例的缩放
        settings.setUseWideViewPort(true);
        //设置 缓存模式
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);

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

        webView.setWebViewClient(new SystemWebViewClient(engine) {
            @Override
            public void onReceivedSslError(WebView view, final SslErrorHandler handler, SslError error) {

                // *** NEVER DO THIS!!! ***
                // super.onReceivedSslError(view, handler, error);

                // let's ignore ssl error
                new com.htmitech.pop.AlertDialog(WebPluginActivity.this).builder().setTitle("提示").setMsg("是否继续访问").setPositiveButton("continue", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        handler.proceed();
                    }
                }).setNegativeButton("cancel", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        handler.cancel();
                    }
                }).show();
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


