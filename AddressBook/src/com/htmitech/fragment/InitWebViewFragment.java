package com.htmitech.fragment;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.htmitech.addressbook.R;
import com.htmitech.base.BaseFragment;
import com.htmitech.base.BaseFragmentActivity;

/**
 * 网页所有的入口
 * <p>
 * Created by tony on
 */
public class InitWebViewFragment extends BaseFragment {
    private ImageView btn_daiban_person;
    private TextView daibantopTabIndicator_bbslist;
    private WebView webView;
    private String url;
    private ProgressBar bar;
    private String title;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater
                .inflate(R.layout.activity_webview, container, false);
    }
    @Override
    public boolean onBackPressed() {
        return false;
    }
    @Override
    protected void initView() {
        bar = (ProgressBar) getView().findViewById(R.id.myProgressBar);
        webView = (WebView) getView().findViewById(R.id.myWebView);
        daibantopTabIndicator_bbslist = (TextView) getView().findViewById(R.id.daibantopTabIndicator_bbslist);
        btn_daiban_person = (ImageView) getView().findViewById(R.id.btn_daiban_person);
    }
    @Override
    protected void initData() {
        Bundle mBundle = getArguments();
        url = mBundle.getString("url");
        title = mBundle.getString("title");
        daibantopTabIndicator_bbslist.setText(title);
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
                getActivity().finish();
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
        };
        //支持javascript
        webView.getSettings().setJavaScriptEnabled(true);
        // 设置可以支持缩放
        webView.getSettings().setSupportZoom(true);
        // 设置出现缩放工具
        webView.getSettings().setBuiltInZoomControls(true);
        //扩大比例的缩放
        webView.getSettings().setUseWideViewPort(true);
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
        return false;
//        return super.onKeyDown(keyCode, event);
    }
}
