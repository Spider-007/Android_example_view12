package com.htmitech.emportal.ui.applicationcenter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.htmitech.api.BookInit;
import com.htmitech.app.widget.XCRoundRectImageView;
import com.htmitech.base.BaseFragmentActivity;
import com.htmitech.commonx.base.BitmapUtils;
import com.htmitech.commonx.base.bitmap.BitmapDisplayConfig;
import com.htmitech.commonx.base.bitmap.callback.BitmapLoadCallBack;
import com.htmitech.commonx.base.bitmap.callback.BitmapLoadFrom;
import com.htmitech.emportal.R;
import com.htmitech.emportal.entity.OAConText;
import com.htmitech.emportal.ui.login.data.logindata.EmpApiLoginOutEntity;
import com.htmitech.proxy.dao.AppliationCenterDao;
import com.htmitech.proxy.doman.RemoveAppss;
import com.htmitech.proxy.myenum.LogManagerEnum;
import com.htmitech.thread.GetFinalRefreshTokenAfterRequestValue;
import com.minxing.client.util.FastJsonUtils;

import org.apache.http.NameValuePair;

import java.util.ArrayList;

import htmitech.com.componentlibrary.listener.ObserverCallBackType;
import htmitech.com.componentlibrary.unit.ServerUrlConstant;


/**
 * 应用中心添加详情
 */
public class AppCenterAddMessageActivity extends BaseFragmentActivity implements View.OnClickListener,ObserverCallBackType {
    private RemoveAppss appInfo;
    private TextView add_app_name;
    private TextView exitlogin_btn;
    private AppliationCenterDao mAppliationCenterDao;
    private ArrayList<NameValuePair> nameValuePairList;
    private String addAppUrl = "";
    private ImageView iv_back;
    private XCRoundRectImageView xc_pic_logo;
    private static final String HTTPTYPE = "Message";
    private TextView daibantopTabIndicator_bbslist;
    private TextView add_app_desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appcenter_add_mess);
        initView();
        initData();
    }

    public void initView(){
        add_app_desc = (TextView) findViewById(R.id.add_app_desc);
        add_app_name = (TextView) findViewById(R.id.add_app_name);
        exitlogin_btn = (TextView) findViewById(R.id.exitlogin_btn);
        daibantopTabIndicator_bbslist = (TextView) findViewById(R.id.daibantopTabIndicator_bbslist);
        iv_back = (ImageView) findViewById(R.id.iv_back1);
        xc_pic_logo = (XCRoundRectImageView) findViewById(R.id.xc_pic_logo);
    }

    public void initData(){
        iv_back.setOnClickListener(this);
        mAppliationCenterDao = new AppliationCenterDao(this);
        addAppUrl = ServerUrlConstant.SERVER_EMPAPI_URL() + ServerUrlConstant.ADD_REMOVE + "/"+ BookInit.getInstance().getPortalId()+"/"+ OAConText.getInstance(this).UserID;;
        appInfo = (RemoveAppss) getIntent().getSerializableExtra("appInfo");
        exitlogin_btn.setOnClickListener(this);
        add_app_desc.setText(appInfo.appDesc + "");
        add_app_name.setText(appInfo.appName+"");
        daibantopTabIndicator_bbslist.setText(appInfo.appShortname+"");
        if(appInfo.getPicPath()!= null&&!appInfo.getPicPath().endsWith("null")){
            xc_pic_logo.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            BitmapUtils.instance().display(xc_pic_logo,
                    appInfo .getPicPath(),
                    new BitmapLoadCallBack<ImageView>() {
                        @SuppressLint("NewApi")
                        @Override
                        public void onLoadCompleted(
                                ImageView container, String uri,
                                Bitmap bitmap,
                                BitmapDisplayConfig config,
                                BitmapLoadFrom from) {
                            container.setImageBitmap(bitmap);
                        }

                        @Override
                        public void onLoadFailed(ImageView container,
                                                 String uri, Drawable drawable) {
                            // TODO Auto-generated method stub
                        }
                    });
        }else{
            xc_pic_logo.setImageResource(R.drawable.icon_app_centre_normal);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.exitlogin_btn:
//                showDialog();
//                setDialogValue("正在添加应用");
//                nameValuePairList = new ArrayList<NameValuePair>();
//                nameValuePairList.add(new BasicNameValuePair("appids",appInfo.appId+""));
//                AnsynHttpRequest.requestByPostWithToken(this, nameValuePairList, addAppUrl, CHTTP.POSTWITHTOKEN, this,HTTPTYPE, LogManagerEnum.APP_CENTER_ADD.functionCode);
//                mAppliationCenterDao.addRemoveApp(appInfo.appId + "");
                appInfo.setCheck(true);
                Intent intent = new Intent();
                intent.putExtra("result", "true");
                setResult(1000, intent);
                this.finish();
                break;
            case R.id.iv_back1:
                this.finish();
                break;
        }
    }

    @Override
    public void success(String requestValue, int type,String requestName) {

        requestValue= GetFinalRefreshTokenAfterRequestValue.getFinalRequestValue(this,requestValue, type, addAppUrl, nameValuePairList, this, requestName, LogManagerEnum.APP_CENTER_ADD.functionCode);
        if (requestValue != null && !requestValue.equals("")) {
            EmpApiLoginOutEntity entity = FastJsonUtils.getPerson(requestValue, EmpApiLoginOutEntity.class);
            dismissDialog();
            if (entity.code == 200) {//EmpApi退出登入成功
                mAppliationCenterDao.addRemoveApp(appInfo.appId + "");
                Toast.makeText(this, appInfo.appName + "已添加到应用列表中！", Toast.LENGTH_SHORT).show();
//                HTActivityUnit.switchTo(AppCenterAddMessageActivity.this, ApplicationCenterAddActivity.class, null); 2017年3月22日09:39:50
                this.finish();
            }else{
                Toast.makeText(this, entity.message, Toast.LENGTH_SHORT).show();
            }
        }else{
//            Toast.makeText(this, "获取失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void fail(String exceptionMessage, int type,String requestName) {

    }

    @Override
    public void notNetwork() {

    }

    @Override
    public void callbackMainUI(String successMessage) {

    }
}
