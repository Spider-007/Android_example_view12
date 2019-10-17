package com.htmitech.ztcustom.zt.chinarailway;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.base.BaseFragmentActivity;
import com.htmitech.ztcustom.zt.constant.ContantValues;
import com.htmitech.ztcustom.zt.constant.QualityBookDetailBean;
import com.htmitech.ztcustom.zt.constant.QualityBookDetailRequest;
import com.htmitech.ztcustom.zt.interfaces.ObserverCallBack;
import com.htmitech.ztcustom.zt.thread.AnsynHttpRequest;
import com.htmitech.ztcustom.zt.unit.CHTTP;
import com.htmitech.ztcustom.zt.util.Base64;
import com.htmitech.ztcustom.zt.util.FastJsonUtils;

import java.io.File;
import java.io.IOException;

/**
 * 质保书详情
 */
public class QualityBookDetailActivity extends BaseFragmentActivity implements View.OnClickListener {

    private ImageButton imgBack;
    private RelativeLayout llQualitybookDetail;
    private String userid;
    private String zbsid;
    private String uploadid;
    private String cftype;
    private WebView qualitybookWebview;
    private ImageView imgQualitybook;
    private int isvisibilitys = 1;
    private QualityBookDetailBean.ResultsBean qualityBookDetailResultsBean;
    private String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "fanxin/web_temp.html";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quality_book_detail);
        initView();
        Intent intent = getIntent();
        userid = intent.getStringExtra("userid");
        zbsid = intent.getStringExtra("zbsid");
        uploadid = intent.getStringExtra("uploadid");
        cftype = intent.getStringExtra("cftype");
        getData();
    }

    private void initView() {
        imgBack = (ImageButton) findViewById(R.id.ib_quality_book_back);
        llQualitybookDetail = (RelativeLayout) findViewById(R.id.ll_qualitybook_detail);
        imgQualitybook = (ImageView) findViewById(R.id.img_qualitybook);
        qualitybookWebview = (WebView) findViewById(R.id.qualitybook_webview);
        //支持JS
        WebSettings settings = qualitybookWebview.getSettings();
        settings.setJavaScriptEnabled(true);
        //支持屏幕缩放
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        imgQualitybook.setImageResource(R.drawable.btn_angle_spread);
        llQualitybookDetail.setOnClickListener(this);
        imgBack.setOnClickListener(this);
    }

    private void getData() {
        showProgressDialog(this);
        QualityBookDetailRequest request = new QualityBookDetailRequest();
        request.userid = userid;
        request.zbsid = (zbsid == null ? "" : zbsid);
        request.uploadid = (uploadid == null ? "" : uploadid);
        request.cftype = (cftype == null ? "" : cftype);
        Log.e("quuu", "getData: " + userid + "_" + zbsid + "_" + uploadid + "_" + cftype + "_");
        AnsynHttpRequest.request(this, request, ContantValues.QUALITYBOOKDETAIL,
                CHTTP.POST, new ObserverCallBack() {
                    @Override
                    public void success(String successMessage) {
                        QualityBookDetailBean qualityBookDetailBean = FastJsonUtils.getPerson(successMessage, QualityBookDetailBean.class);
                        Log.e("QualityBookDetailBean", "success: " + qualityBookDetailBean.getResults().getHtmlurl().toString());
                        if (qualityBookDetailBean != null) {
                            qualityBookDetailResultsBean = qualityBookDetailBean.getResults();
                            try {
                                Base64.decodeToFile(qualityBookDetailResultsBean.getHtmlbase64(), filePath);
                                qualitybookWebview.loadUrl("file://" + filePath);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        dimssDialog();
                    }

                    @Override
                    public void fail(String exceptionMessage) {
                        Toast.makeText(QualityBookDetailActivity.this, "数据请求失败!", Toast.LENGTH_SHORT).show();
                        dimssDialog();
                    }

                    @Override
                    public void notNetwork() {
                        Toast.makeText(QualityBookDetailActivity.this, "网络异常!", Toast.LENGTH_SHORT).show();
                        dimssDialog();
                    }
                });
    }


    @Override
    public void onClick(View view) {
        if(view.getId() ==R.id.ll_qualitybook_detail ){
            if (isvisibilitys == 0) {
                imgQualitybook.setImageResource(R.drawable.btn_angle_spread);
                qualitybookWebview.setVisibility(View.VISIBLE);
                isvisibilitys = 1;
            } else if (isvisibilitys == 1) {
                imgQualitybook.setImageResource(R.drawable.btn_angle_retract);
                qualitybookWebview.setVisibility(View.GONE);
                isvisibilitys = 0;
            }
        }else if(view.getId() ==R.id.ib_quality_book_back){
            exit();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        exit();
    }

    private void exit() {
        File desFile = new File(filePath);
        if (desFile.exists()) {
            desFile.delete();
        }
        finish();
    }


}
