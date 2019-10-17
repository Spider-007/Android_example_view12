package com.htmitech.addressbook;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.http.SslError;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.htmitech.app.Constant;
import com.htmitech.base.BaseFragmentActivity;

import java.io.File;

/**
 *
 * 网页所有的入口
 *
 * Created by tony on
 */
public class InitWebView  extends BaseFragmentActivity {
    private ImageView btn_daiban_person;
    private TextView daibantopTabIndicator_bbslist;
    private WebView webView;
    private String url;
    private ProgressBar bar;
    private RelativeLayout layout_daiban_titlebar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        url = getIntent().getStringExtra("url");
        bar = (ProgressBar)findViewById(R.id.myProgressBar);
        webView = (WebView)findViewById(R.id.myWebView);
        layout_daiban_titlebar = (RelativeLayout)findViewById(R.id.layout_daiban_titlebar);
        daibantopTabIndicator_bbslist = (TextView) findViewById(R.id.daibantopTabIndicator_bbslist);
        btn_daiban_person = (ImageView) findViewById(R.id.btn_daiban_person);
        String com_active_navigation_show = getIntent().getStringExtra("com_active_navigation_show");
        if(TextUtils.isEmpty(com_active_navigation_show) ||"0".equals(com_active_navigation_show)){
            layout_daiban_titlebar.setVisibility(View.GONE);
        }else{
            layout_daiban_titlebar.setVisibility(View.VISIBLE);
        }
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    bar.setVisibility(View.INVISIBLE);
                } else {
                    if (View.INVISIBLE == bar.getVisibility()) {
                        bar.setVisibility(View.VISIBLE);
                    }
                    bar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                daibantopTabIndicator_bbslist.setText(title);
            }



        });
        btn_daiban_person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != webView && webView.canGoBack()) {
                    webView.goBack(); // goBack()表示返回WebView的上一页面
                }else if(null != webView && !webView.canGoBack()){
                    finish();
                }else{
                    finish();
                }
            }
        });
        // 创建WebViewClient对象
        WebViewClient wvc = new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 使用自己的WebView组件来响应Url加载事件，而不是使用默认浏览器器加载页面
                webView.loadUrl(url);
                // 消耗掉这个事件。Android中返回True的即到此为止吧,事件就会不会冒泡传递了，我们称之为消耗掉
                return true;
            }

            @Override
            public void onReceivedSslError(WebView view, final SslErrorHandler handler, SslError error) {

                new com.htmitech.pop.AlertDialog(InitWebView.this).builder().setTitle("提示").setMsg("是否继续访问").setPositiveButton("continue", new View.OnClickListener() {
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

        };
        //支持javascript
        webView.getSettings().setJavaScriptEnabled(true);
        // 设置可以支持缩放
            webView.getSettings().setSupportZoom(true);
         // 设置出现缩放工具
        webView.getSettings().setBuiltInZoomControls(true);
        //扩大比例的缩放
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setTextSize(WebSettings.TextSize.NORMAL);
        //自适应屏幕
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setLoadWithOverviewMode(true);
        //设置 缓存模式
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        // 开启 DOM storage API 功能
        webView.getSettings().setDomStorageEnabled(true);
        webView.setWebViewClient(wvc);
        webView.loadUrl(url);
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack(); // goBack()表示返回WebView的上一页面
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }
}
