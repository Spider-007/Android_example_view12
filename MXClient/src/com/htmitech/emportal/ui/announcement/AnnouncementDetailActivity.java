package com.htmitech.emportal.ui.announcement;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.htmitech.MyView.EmptyLayout;
import com.htmitech.commonx.util.DeviceUtils;
import com.htmitech.emportal.R;
import com.htmitech.emportal.entity.ActionInfo;
import com.htmitech.emportal.ui.announcement.entity.GetNoticeListByConditionResult;
import com.htmitech.emportal.ui.detail.CallBackLayout;
import com.htmitech.emportal.ui.detail.DetailActivityLayout;
import com.htmitech.emportal.ui.pop.FunctionPopupWindow;
import com.htmitech.emportal.ui.widget.flatingactionButton.AddFloatingActionButton;
import com.htmitech.emportal.ui.widget.flatingactionButton.FloatingActionButton;
import com.htmitech.emportal.utils.OpenFiles;
import com.minxing.kit.api.MXAPI;
import com.minxing.kit.api.bean.ShareLink;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import cn.feng.skin.manager.base.BaseActivity;

public class AnnouncementDetailActivity extends BaseActivity implements View.OnClickListener, DownloadListener,
        CallBackLayout, View.OnTouchListener {

    private GetNoticeListByConditionResult resultData;
    private TextView mTitle;
    private ImageView mBack;
    private WebView mWebView;
    private EmptyLayout mEmptyLayout;
    private int myfavValue;
    private int shareValue;
    private int showimgValue;
    private int showwaysValue;
    private int signreadValue;
    private DetailActivityLayout mDetailActivityLayout;
    private FunctionPopupWindow mFunctionPopupWindow;
    private AddFloatingActionButton menuMultipleActions;
    private ArrayList<ActionInfo> mDataList;
    private ShareLink shareLink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement_detail);
        mDetailActivityLayout = (DetailActivityLayout) LayoutInflater.from(this).inflate(
                R.layout.activity_announcement_detail, null);
        mEmptyLayout = (EmptyLayout) mDetailActivityLayout.findViewById(R.id.emptyLayout);
        mEmptyLayout.setErrorButtonClickListener(this);
        mDetailActivityLayout.setValue(this);
        mDataList = new ArrayList<ActionInfo>();
        int shareValue = getIntent().getIntExtra("shareValue", 0);
        ActionInfo mActionInfo = new ActionInfo();
        mActionInfo.setActionID("Share");
        mActionInfo.setActionName("分享");
        if (shareValue == 1) {
            mDataList.add(mActionInfo);
        }
//        menuMultipleActions.setVisibility(View.VISIBLE);
        mFunctionPopupWindow = new FunctionPopupWindow(this,
                new MenuOnClickListener(), mDataList.size());
        mFunctionPopupWindow.initArcMenu(mDataList);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels - 50;
        Intent intent = getIntent();
        resultData = (GetNoticeListByConditionResult) intent.getSerializableExtra("data");
        myfavValue = intent.getIntExtra("myfavValue", -1);
        this.shareValue = intent.getIntExtra("shareValue", -1);
        showimgValue = intent.getIntExtra("showimgValue", -1);
        showwaysValue = intent.getIntExtra("showwaysValue", -1);
        signreadValue = intent.getIntExtra("signreadValue", -1);
        initView();


    }

    public void initView() {
        mTitle = (TextView) findViewById(R.id.tv_anndetail_title);
        mBack = (ImageView) findViewById(R.id.iv_anndetail_back);
        mWebView = (WebView) findViewById(R.id.wv_anndetail);
        menuMultipleActions = (AddFloatingActionButton) findViewById(R.id.function);
        if (shareValue == 1) {
            menuMultipleActions.setVisibility(View.VISIBLE);
        } else {
            menuMultipleActions.setVisibility(View.GONE);
        }
        menuMultipleActions.setOnClickListener(this);
        menuMultipleActions.setOnTouchListener(this);
        menuMultipleActions.setSize(FloatingActionButton.SIZE_NORMAL);
        WebSettings settings = mWebView.getSettings();
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        //支持JS
        settings.setJavaScriptEnabled(true);
        mBack.setOnClickListener(this);
        if (resultData != null) {
            mTitle.setText(resultData.title);
            mWebView.loadUrl(resultData.url);
            //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
            mWebView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    // TODO Auto-generated method stub
                    //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                    view.loadUrl(url);
                    return true;
                }
            });
            mWebView.setDownloadListener(this);
        }

    }

    private class DownloadTask extends AsyncTask<String, Void, Void> {
        // 传递两个参数：URL 和 目标路径
        private String url;
        private String destPath;

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Void doInBackground(String... params) {
            url = params[0];
            destPath = params[1];
            OutputStream out = null;
            HttpURLConnection urlConnection = null;
            try {
                URL url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setConnectTimeout(15000);
                urlConnection.setReadTimeout(15000);
                InputStream in = null;
                try {
                    in = urlConnection.getInputStream();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                out = new FileOutputStream(params[1]);
                byte[] buffer = new byte[10 * 1024];
                int len;
                while ((len = in.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                }
                in.close();
            } catch (IOException e) {

            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {

                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Intent handlerIntent = new Intent(Intent.ACTION_VIEW);
            String mimeType = getMIMEType(url);
            Uri uri = Uri.fromFile(new File(destPath));
            handlerIntent.setDataAndType(uri, mimeType);
            startActivity(handlerIntent);
        }
    }

    private String getMIMEType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }

    int popupHeight;
    int popupWidth;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_anndetail_back:
                finish();
                break;
            case R.id.function:
                if (!isTuoZhuai) {
                    if (mFunctionPopupWindow == null) {
                        return;
                    }
                    if (!mFunctionPopupWindow.isShowing()) {
//					menuMultipleActions.startAnimation(animation);
                        menuMultipleActions.setImageDrawable(getResources().getDrawable(R.drawable.btn_operation_homelabel_on));
                        mFunctionPopupWindow = new FunctionPopupWindow(this,
                                new MenuOnClickListener(), mDataList.size());
                        mFunctionPopupWindow.initArcMenu(mDataList);
                        popupWidth = mFunctionPopupWindow.mMenuView.getMeasuredWidth();
                        popupWidth = DeviceUtils.dip2px(this, 55) + popupWidth;

                        popupHeight = mFunctionPopupWindow.getHeight();
                        int[] location = new int[2];
                        v.getLocationOnScreen(location);
                        mFunctionPopupWindow.showAtLocation(v, Gravity.NO_GRAVITY,
                                (location[0] - popupWidth), location[1]
                                        - popupHeight);
                        mFunctionPopupWindow.update();
                    } else {
                        menuMultipleActions.setImageDrawable(getResources().getDrawable(R.drawable.btn_operation_homelabel_off));
                        mFunctionPopupWindow.dismiss();
                    }
                }
                break;
        }
    }

    public class MenuOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            ShareListener();
        }

    }

    //分享
    public int curItem = 0;
    public String apiUrl = null;

    private void ShareListener() {
        // 弹选择窗
        //设置分享参数
        shareLink = new ShareLink();
        shareLink.setTitle("分享网页");
//        shareLink.setDesc(docTitle);
//        shareLink.setThumbnail(iconId);
        shareLink.setUrl(resultData.url);

        AlertDialog.Builder builder = new AlertDialog.Builder(
                AnnouncementDetailActivity.this);
        builder.setTitle("请选择分享位置");
        final String[] pos = {"分享给同事", "分享到工作组"};
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (curItem == 0) {
                    apiUrl = "ee" + resultData.url;
                    shareLink.setAppUrl(apiUrl);
                    shareLink.setDesc(resultData.title);
                    MXAPI.getInstance(AnnouncementDetailActivity.this).shareToChat(AnnouncementDetailActivity.this, shareLink);
                } else {
                    apiUrl = "ee" + resultData.url;
                    shareLink.setAppUrl(apiUrl);
                    shareLink.setTitle("分享网页");
                    shareLink.setDesc(resultData.title);
                    MXAPI.getInstance(AnnouncementDetailActivity.this).shareToCircle(AnnouncementDetailActivity.this, shareLink);
                }
                curItem = 0;
            }
        });
        builder.setSingleChoiceItems(pos, 0,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        curItem = which;
                    }
                });
        builder.show();
    }

    @Override
    public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {


        String fileName = URLUtil.guessFileName(url, contentDisposition, mimetype);
        String destPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                .getAbsolutePath() + File.separator + fileName;
        File file = new File(destPath);
        if (file.exists()) {
            Intent handlerIntent = new Intent(Intent.ACTION_VIEW);
            String mimeType = getMIMEType(url);
            Uri uri = Uri.fromFile(new File(destPath));
            handlerIntent.setDataAndType(uri, mimeType);
            startActivity(handlerIntent);
        } else {
            new DownloadTask().execute(url, destPath);
        }
        if (null!=resultData&&null!=resultData.url) {
            mWebView.loadUrl(resultData.url);
        }

    }

    int screenWidth;
    int screenHeight;
    int lastX;
    int lastY;
    private boolean isTuoZhuai = false;
    private float x, y;
    private float ex, ey;
    private int left, top, right, bottom;
    private boolean action_move = false;
    private long startTime = 0;
    private long endTime = 0;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        ex = 0;
        ey = 0;
        switch (action) {
            case MotionEvent.ACTION_DOWN:

                startTime = System.currentTimeMillis();
                x = event.getX();
                y = event.getY();
                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();
                isTuoZhuai = false;
                break;
            /**
             * layout(l,t,r,b) l Left position, relative to parent t Top position,
             * relative to parent r Right position, relative to parent b Bottom
             * position, relative to parent
             * */
            case MotionEvent.ACTION_MOVE:

                action_move = true;
                isTuoZhuai = true;
                int dx = (int) event.getRawX() - lastX;
                int dy = (int) event.getRawY() - lastY;
                left = v.getLeft() + dx;
                top = v.getTop() + dy;
                right = v.getRight() + dx;
                bottom = v.getBottom() + dy;
                if (left < 0) {
                    left = 0;
                    right = left + v.getWidth();
                }
                if (right > screenWidth) {
                    right = screenWidth;
                    left = right - v.getWidth();
                }
                if (top - (mFunctionPopupWindow.isShowing() ? popupHeight : 0) < 0) {
                    top = mFunctionPopupWindow.isShowing() ? popupHeight : 0;
                    bottom = top + v.getHeight();
                }
                if (bottom > screenHeight) {
                    bottom = screenHeight;
                    top = bottom - v.getHeight();
                }

                v.layout(left, top, right, bottom);
                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();
                int[] location = new int[2];
                v.getLocationOnScreen(location);
                mFunctionPopupWindow.update(location[0] - popupWidth, location[1]
                        - popupHeight, -1, -1);
                break;
            case MotionEvent.ACTION_UP:

                endTime = System.currentTimeMillis();
                ex = event.getX() - x;
                ey = event.getY() - y;
                //当从点击到弹起小于半秒的时候,则判断为点击,如果超过则不响应点击事件
                //以前是根据点击挪动距离来进行判断，有些手机敏感性强导致无法弹出等问题
                if ((endTime - startTime) > 0.1 * 2000L) {
                    isTuoZhuai = true;
                } else {
                    isTuoZhuai = false;
                }
                break;
        }
        return false;
    }

    @Override
    public void callBackLayout() {
        if (action_move)
            menuMultipleActions.layout(left, top, right, bottom);
    }
}
