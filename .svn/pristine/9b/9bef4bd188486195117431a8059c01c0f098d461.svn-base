package com.htmitech.emportal.ui.applicationcenter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.htmitech.api.BookInit;
import com.htmitech.app.Constant;
import com.htmitech.base.BaseFragmentActivity;
import com.htmitech.commonx.pulltorefresh.library.PullToRefreshBase;
import com.htmitech.commonx.pulltorefresh.library.PullToRefreshListView;
import com.htmitech.emportal.R;
import com.htmitech.emportal.entity.OAConText;
import com.htmitech.emportal.ui.login.data.logindata.EmpApiLoginOutEntity;
import com.htmitech.proxy.dao.AppliationCenterDao;
import com.htmitech.proxy.doman.GetRemoveApps;
import com.htmitech.proxy.doman.RemoveAppss;
import com.htmitech.proxy.myenum.LogManagerEnum;
import com.htmitech.proxy.util.HTActivityUnit;
import com.htmitech.thread.AnsynHttpRequest;
import com.htmitech.thread.CHTTP;
import com.htmitech.thread.GetFinalRefreshTokenAfterRequestValue;
import com.minxing.client.util.FastJsonUtils;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import htmitech.com.componentlibrary.listener.ObserverCallBackType;
import htmitech.com.componentlibrary.unit.RefreshTotal;
import htmitech.com.componentlibrary.unit.ServerUrlConstant;


/**
 * tony
 * 应用中心添加页面
 */
public class ApplicationCenterAddActivity extends BaseFragmentActivity implements View.OnClickListener, ObserverCallBackType {
    private PullToRefreshListView mPullToRefreshListView;
    private LinearLayout ll_emptoy;
    private ImageView btn_daiban_person;
    public ImageView iv_menu;
    public TextView complete;
    private String url = "";
    private ProgressBar progress_;
    private ApplicationCenterAddAdapter mApplicationCenterAddAdapter;
    private AppliationCenterDao mAppliationCenterDao;
    private static final String HTTPTYPE = "add";
    private static final String MHTTPTYPE = "message";
    private ArrayList<RemoveAppss> appInfoList;
    RemoveAppss mAppInfo;
    private String addAppUrl = "";
    private ArrayList<NameValuePair> nameValuePairList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_add);
        initView();
        initData();
    }

    public void initView() {
        mPullToRefreshListView = (PullToRefreshListView) findViewById(R.id.lv_app_add);
        btn_daiban_person = (ImageView) findViewById(R.id.btn_daiban_person);
        progress_ = (ProgressBar) findViewById(R.id.progress_);
        iv_menu = (ImageView) findViewById(R.id.iv_menu);
        complete = (TextView) findViewById(R.id.complete);
        iv_menu.setVisibility(View.GONE);
        ll_emptoy = (LinearLayout) findViewById(R.id.layout_search_no);
    }


    public void initData() {

        complete.setOnClickListener(this);
        iv_menu.setOnClickListener(this);
        appInfoList = new ArrayList<RemoveAppss>();
        mPullToRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        btn_daiban_person.setOnClickListener(this);
        // 下拉刷新时的提示文本设置
        mPullToRefreshListView.getLoadingLayoutProxy(true, false)
                .setLastUpdatedLabel("");
        mPullToRefreshListView.getLoadingLayoutProxy(true, false).setPullLabel(
                "下拉刷新");
        mPullToRefreshListView.getLoadingLayoutProxy(true, false)
                .setRefreshingLabel("正在刷新");
        mPullToRefreshListView.getLoadingLayoutProxy(true, false)
                .setReleaseLabel("放开刷新");
        mAppliationCenterDao = new AppliationCenterDao(this);
        String portal_id =  BookInit.getInstance().getPortalId();
        url = ServerUrlConstant.SERVER_EMPAPI_URL() + ServerUrlConstant.USER_GETREMOVE + "/" + portal_id + "/" + OAConText.getInstance(this).UserID;
//        url = "http://192.168.88.63:8050/data-crab/" + ServerUrlConstant.USER_GETREMOVE + "/" + mEmpPortal.portal_id + "/" + OAConText.getInstance(this).UserID;
        addAppUrl = ServerUrlConstant.SERVER_EMPAPI_URL() + ServerUrlConstant.ADD_REMOVE + "/" + portal_id + "/" + OAConText.getInstance(this).UserID;
        ;
        //暂时模拟给一个token
//
//        PreferenceUtils.saveAccessToken("ADA6C4AF14B0F14A85B55E0853C09C94");
//        PreferenceUtils.saveRefreshToken("1bb7b0057f8484ffcde7ebe5097e2775");

        AnsynHttpRequest.requestByPostWithToken(this, null, url, CHTTP.POSTWITHTOKEN, this, HTTPTYPE, LogManagerEnum.APP_CENTER_ADD.functionCode);

        mApplicationCenterAddAdapter = new ApplicationCenterAddAdapter(this, null);
        mPullToRefreshListView.setAdapter(mApplicationCenterAddAdapter);

        mPullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mAppInfo = (RemoveAppss) parent.getItemAtPosition(position);
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("appInfo", mAppInfo);
                HTActivityUnit.switchTo(ApplicationCenterAddActivity.this, AppCenterAddMessageActivity.class, map, 1000);
//                ApplicationCenterAddActivity.this.finish();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && data != null) {
            String result_value = data.getStringExtra("result");
            if (result_value.equals("true")) {
                mAppInfo.setCheck(true);
                ;
            }
            boolean hascheck = false;
            for (int i = 0; i < appInfoList.size(); i++) {
                if (appInfoList.get(i).isCheck()) {
                    hascheck = true;
                }
                if (hascheck) {
                    complete.setVisibility(View.VISIBLE);
                } else {
                    complete.setVisibility(View.GONE);
                }
            }
            mApplicationCenterAddAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_daiban_person:
//                HTActivityUnit.switchTo(this, ApplicationCenterActivity.class, null); 2017年3月22日09:38:20
                this.finish();
                break;
            case R.id.iv_menu:
            case R.id.complete:
                if (appInfoList.size() != 0) {
                    showDialog();
                    setDialogValue("正在添加应用");
                    StringBuilder sb = new StringBuilder();
                    int count = 0;
                    for (int i = 0; i < appInfoList.size(); i++) {
                        if (appInfoList.get(i).isCheck()) {
                            sb.append(appInfoList.get(i).appId + ",");
                            mAppliationCenterDao.addRemoveApp(appInfoList.get(i).appId + "");
                            count++;
                        }
                    }
                    if (count == 0) {
                        Toast.makeText(ApplicationCenterAddActivity.this, "您还没有选择要添加的应用！", Toast.LENGTH_SHORT).show();
                        dismissDialog();
                        return;
                    }
                    nameValuePairList = new ArrayList<NameValuePair>();
                    nameValuePairList.add(new BasicNameValuePair("appids", sb.toString()));
                    AnsynHttpRequest.requestByPostWithToken(this, nameValuePairList, addAppUrl, CHTTP.POSTWITHTOKEN, this, MHTTPTYPE, LogManagerEnum.APP_CENTER_ADD.functionCode);
                } else {
                    Toast.makeText(ApplicationCenterAddActivity.this, "您没有可以添加的应用！", Toast.LENGTH_SHORT).show();
                }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        HTActivityUnit.switchTo(this, ApplicationCenterActivity.class, null); 2017年3月22日09:38:16
        this.finish();
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void success(String requestValue, int type, String requestName) {
        if (requestName.equals(HTTPTYPE)) {
            requestValue = GetFinalRefreshTokenAfterRequestValue.getFinalRequestValue(this,requestValue, type, url, null, this, requestName, LogManagerEnum.APP_CENTER_ADD.functionCode);
            if (requestValue != null && !requestValue.equals("")) {
                GetRemoveApps entity = FastJsonUtils.getPerson(requestValue, GetRemoveApps.class);
                progress_.setVisibility(View.GONE);
                if (entity.code == 200) {
                    for (RemoveAppss mRemoveAppss : entity.result) {
                        appInfoList.add(mRemoveAppss);
                    }
                    mApplicationCenterAddAdapter.setData(appInfoList);
                    if (appInfoList.size() == 0) {
                        ll_emptoy.setVisibility(View.VISIBLE);
                        iv_menu.setVisibility(View.GONE);
                    } else {
                        ll_emptoy.setVisibility(View.GONE);
                        iv_menu.setVisibility(View.GONE);
                    }
                } else {
                    Toast.makeText(this, "获取成功", Toast.LENGTH_SHORT).show();
                }
            } else {


            }
        } else if (requestName.equals(MHTTPTYPE)) {
            requestValue = GetFinalRefreshTokenAfterRequestValue.getFinalRequestValue(this,requestValue, type, addAppUrl, nameValuePairList, this, requestName, LogManagerEnum.APP_CENTER_ADD.functionCode);
            if (requestValue != null && !requestValue.equals("")) {
                Log.d("requestValue--->", requestValue);
                EmpApiLoginOutEntity entity = FastJsonUtils.getPerson(requestValue, EmpApiLoginOutEntity.class);
                dismissDialog();
                if (entity != null && entity.code == 200) {//EmpApi退出登入成功
                    StringBuilder sb = new StringBuilder();
                    int cont = 0;
                    for (int i = 0; i < appInfoList.size(); i++) {
                        if (appInfoList.get(i).isCheck()) {
                            sb.append(appInfoList.get(i).appName + ",");
                            cont += 1;
                            mAppliationCenterDao.addRemoveApp(appInfoList.get(i).appId + "");
                        }

                    }
//                    if(appInfoList.size()==cont){
//                        ll_emptoy.setVisibility(View.VISIBLE);
//                    }else {
//                        ll_emptoy.setVisibility(View.GONE);
//                    }
                    RefreshTotal.addReshActivity();
                    Toast.makeText(this, sb.toString() + "已添加到应用列表中！", Toast.LENGTH_SHORT).show();
//                    HTActivityUnit.switchTo(this, ApplicationCenterActivity.class, null);  2017年3月22日09:37:00
                    this.finish();
                } else {
                    Toast.makeText(this, entity == null ? "请求出错" : entity.message, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "获取失败", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void fail(String exceptionMessage, int type, String requestName) {
        progress_.setVisibility(View.GONE);
        ll_emptoy.setVisibility(View.VISIBLE);
    }

    @Override
    public void notNetwork() {
        progress_.setVisibility(View.GONE);
        ll_emptoy.setVisibility(View.VISIBLE);
    }

    @Override
    public void callbackMainUI(String successMessage) {

    }
}
