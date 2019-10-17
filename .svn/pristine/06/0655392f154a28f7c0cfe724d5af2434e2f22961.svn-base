package com.htmitech.proxy.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.htmitech.MyView.EmptyLayout;
import com.htmitech.api.BookInit;
import com.htmitech.commonx.base.app.DialogFactory;
import com.htmitech.emportal.HtmitechApplication;
import com.htmitech.emportal.R;
import com.htmitech.emportal.entity.OAConText;
import htmitech.com.componentlibrary.listener.ObserverCallBackType;
import com.htmitech.proxy.adapter.DeviceUserAdapter;
import com.htmitech.proxy.adapter.ListPopupWindowAdapter;
import com.htmitech.proxy.doman.DeviceSafeConfigRequest;
import com.htmitech.proxy.doman.DeviceSafeConfigResponse;
import com.htmitech.proxy.doman.DeviceSafeConfigResult;
import com.htmitech.proxy.doman.DeviceSaveRequest;
import com.htmitech.proxy.doman.DeviceUserListRequest;
import com.htmitech.proxy.doman.DeviceUserListResponse;
import com.htmitech.proxy.doman.DeviceUserListResultItem;
import com.htmitech.proxy.interfaces.CallBackSchedulePosition;
import com.htmitech.proxy.pop.ListPopupWindow;

import java.util.ArrayList;

import cn.feng.skin.manager.base.BaseFragmentActivity;
import htmitech.com.componentlibrary.dialog.LoadingDialog;
import htmitech.com.componentlibrary.unit.ServerUrlConstant;

import com.htmitech.thread.AnsynHttpRequest;
import com.htmitech.thread.CHTTP;
import com.htmitech.thread.GetFinalRefreshTokenAfterRequestValue;
import com.htmitech.thread.Network;

public class DeviceSafeMainActivity extends BaseFragmentActivity implements View.OnClickListener, CallBackSchedulePosition, ObserverCallBackType {

    private EmptyLayout contentRoot;
    private LinearLayout llBindCount;
    private TextView tvBindCount;
    private TextView tvBindCountValue;
    private LinearLayout llOnlyBind;
    private TextView tvOnlyBind;
    private Switch tvOnlyBindValue;
    private LinearLayout llBindHistory;
    private TextView tvHasBind;
    private TextView tvHasBindValue;
    private ListView lvDeviceList;
    private ImageButton ivback;
    private ImageButton ivBackHome;
    private TextView tvTitle;
    private TextView rightContent;
    public ArrayList<DeviceUserListResultItem> dataList = new ArrayList<DeviceUserListResultItem>();  //保存用户设备列表信息
    //    public NetWorkManager netWorkManager = LogManagerProxy.getProxyInstance(NetWorkManager.class);
    public boolean isHome;   //是否在底部配置
    private DeviceUserAdapter mDeviceUserAdapter;
    public DeviceSafeConfigResult deviceSafeConfig = null;
    private View view = null;
    public int position = -1;
    private String bindListUrl;
    public int num;
    private DeviceUserListRequest mDeviceUserListRequest;
    public boolean firstComing;
    public String app_id = "";
    public LinearLayout rootView;       //保存上次点击的view对象
    String deviceId = ((TelephonyManager) HtmitechApplication.getApplication().getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
    private String pathUrl;
    private DeviceSafeConfigRequest mDeviceSafeConfigRequest;
    private LoadingDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = LayoutInflater.from(this).inflate(R.layout.activity_device_safe_main, null);
        setContentView(view);

        //-----------------------------获取上层传来的参数-------------------------------------------
        Intent intent = getIntent();
        isHome = intent.getBooleanExtra("isHome", false);
        app_id = intent.getStringExtra("app_id");
        deviceSafeConfig = (DeviceSafeConfigResult) intent.getSerializableExtra("result");
        if (deviceSafeConfig != null)
            position = (deviceSafeConfig.userBindingDeviceLimit == 0 ? -1 : deviceSafeConfig.userBindingDeviceLimit);
        //-----------------------------布局定义-----------------------------------------------------
        contentRoot = (EmptyLayout) findViewById(R.id.content_root);
        llBindCount = (LinearLayout) findViewById(R.id.ll_bind_count);       //允许绑定数量根布局
        tvBindCount = (TextView) findViewById(R.id.tv_bind_count);           //
        tvBindCountValue = (TextView) findViewById(R.id.tv_bind_count_value); //允许绑定数量值
        llOnlyBind = (LinearLayout) findViewById(R.id.ll_only_bind);          //只允许绑定设备登录根布局
        tvOnlyBind = (TextView) findViewById(R.id.tv_only_bind);             //
        tvOnlyBindValue = (Switch) findViewById(R.id.tv_only_bind_value);  //只允许绑定设备登录值
        llBindHistory = (LinearLayout) findViewById(R.id.ll_bind_history);  //已绑定设备根布局
        tvHasBind = (TextView) findViewById(R.id.tv_has_bind);              //
        tvHasBindValue = (TextView) findViewById(R.id.tv_has_bind_value);  //已绑定设备值
        lvDeviceList = (ListView) findViewById(R.id.lv_device_list);       //用户列表
        ivback = (ImageButton) findViewById(R.id.title_left_button);        //返回按钮
        //主页返回
        ivBackHome = (ImageButton) findViewById(R.id.title_left_button_home);
        tvTitle = (TextView) findViewById(R.id.title_name);               //标题
        tvTitle.setText("设备安全");
        rightContent = (TextView) findViewById(R.id.title_right_text_button);//标题右侧内容
        rightContent.setText("我的黑名单");
        if (isHome) {
            ivback.setVisibility(View.GONE);
            ivBackHome.setVisibility(View.VISIBLE);
        } else {
            ivback.setVisibility(View.VISIBLE);
            ivBackHome.setVisibility(View.GONE);
        }
        ivback.setOnClickListener(this);
        ivBackHome.setOnClickListener(this);
        llBindCount.setOnClickListener(this);
        tvOnlyBindValue.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!Network.checkNetWork(DeviceSafeMainActivity.this)) {
                    Toast.makeText(DeviceSafeMainActivity.this, "网络异常，请检查！", Toast.LENGTH_SHORT).show();
                    tvOnlyBindValue.setChecked(!isChecked);
                    return;
                }
                if (firstComing != true) {
                    saveDeviceConfig(deviceSafeConfig.userBindingDeviceLimit);
                }
                firstComing = false;
            }
        });
        initUI();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!Network.checkNetWork(DeviceSafeMainActivity.this)) {
            contentRoot.setNoWifiButtonClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Network.checkNetWork(DeviceSafeMainActivity.this)) {
                        contentRoot.hide();
                        recreate();
                    }
                }
            });
            contentRoot.showNowifi();
            return;
        }
        //-----------------------------请求用户设备绑定列表-----------------------------------------
        getDeviceListFromServer();

    }

    public void showDialog() {
        progressDialog = new LoadingDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    /*
    * 修改配置项后刷新配置信息
    * */
    public void requestDeviceSafeConfig() {
        mDeviceSafeConfigRequest = new DeviceSafeConfigRequest();
        mDeviceSafeConfigRequest.userId = OAConText.getInstance(DeviceSafeMainActivity.this).UserID;
        mDeviceSafeConfigRequest.groupCorpId = OAConText.getInstance(DeviceSafeMainActivity.this).group_corp_id;
        pathUrl = ServerUrlConstant.SERVER_EMPAPI_URL() + ServerUrlConstant.GET_DCSAFE_CONFIG;
        AnsynHttpRequest.requestByPostWithToken(DeviceSafeMainActivity.this, mDeviceSafeConfigRequest, pathUrl, CHTTP.POSTWITHTOKEN
                , DeviceSafeMainActivity.this, "safeConfig", "0");
    }


    public void saveDeviceConfig(int limit) {
        showDialog();
        String saveUrl = ServerUrlConstant.SERVER_EMPAPI_URL() + ServerUrlConstant.SAVE_DCSAFE_CONFIG;
        DeviceSaveRequest mDeviceSaveRequest = new DeviceSaveRequest();
        mDeviceSaveRequest.userId = OAConText.getInstance(DeviceSafeMainActivity.this).UserID;
        mDeviceSaveRequest.groupCorpId = OAConText.getInstance(DeviceSafeMainActivity.this).group_corp_id;
        mDeviceSaveRequest.loginVerifyDevice = tvOnlyBindValue.isChecked() ? 2 : 0;
        if (deviceSafeConfig != null) {
            mDeviceSaveRequest.appVerifyAddBinding = deviceSafeConfig.appVerifyAddBinding;
            mDeviceSaveRequest.appVerifyLossReport = deviceSafeConfig.appVerifyLossReport;
            mDeviceSaveRequest.appVerifyPullBlack = deviceSafeConfig.appVerifyPullBlack;
            mDeviceSaveRequest.appVerifyRemoveBinding = deviceSafeConfig.appVerifyRemoveBinding;
            mDeviceSaveRequest.auditAddBinging = deviceSafeConfig.auditAddBinging;
            mDeviceSaveRequest.auditLossReport = deviceSafeConfig.auditLossReport;
            mDeviceSaveRequest.skipAuditList = deviceSafeConfig.skipAuditList;
            mDeviceSaveRequest.auditPullBlack = deviceSafeConfig.auditPullBlack;
            mDeviceSaveRequest.autoBindingDevice = deviceSafeConfig.autoBindingDevice;
            mDeviceSaveRequest.configType = deviceSafeConfig.configType;
            try {
                mDeviceSaveRequest.corpId = Integer.parseInt(deviceSafeConfig.corpId);
            } catch (Exception e) {
                e.printStackTrace();
            }
            mDeviceSaveRequest.userBindingDeviceLimit = limit == -1 ? deviceSafeConfig.userBindingDeviceLimit : limit;
            mDeviceSaveRequest.defineLevel = deviceSafeConfig.defineLevel;
            mDeviceSaveRequest.auditRemoveBinding = deviceSafeConfig.auditRemoveBinding;
        }
        AnsynHttpRequest.requestByPostWithToken(DeviceSafeMainActivity.this, mDeviceSaveRequest, saveUrl, CHTTP.POSTWITHTOKEN
                , DeviceSafeMainActivity.this, "save", "0");
    }

    public void getDeviceListFromServer() {
        bindListUrl = ServerUrlConstant.SERVER_EMPAPI_URL() + ServerUrlConstant.GET_DEVICE_LIST;
        mDeviceUserListRequest = new DeviceUserListRequest();
        mDeviceUserListRequest.userId = OAConText.getInstance(DeviceSafeMainActivity.this).UserID;
        mDeviceUserListRequest.groupCorpId = OAConText.getInstance(DeviceSafeMainActivity.this).group_corp_id;
        AnsynHttpRequest.requestByPostWithToken(DeviceSafeMainActivity.this, mDeviceUserListRequest, bindListUrl, CHTTP.POSTWITHTOKEN
                , DeviceSafeMainActivity.this, "bindList", "0");
    }

    /*
        * 根据安全配置值确定UI控件的状态
        * */
    public void initUI() {
        if (null != deviceSafeConfig) {          //自定义配置不为空
            if (deviceSafeConfig.loginVerifyDevice == 2) {
                firstComing = true;
                tvOnlyBindValue.setChecked(true);
            }
            //自定义级别不为空
            //自定义级别不为空
            if (deviceSafeConfig.defineLevel == 1 || deviceSafeConfig.defineLevel == 2 ||
                    (deviceSafeConfig.defineLevel == 3 && deviceSafeConfig.vipFlag != 1)) {
                tvOnlyBindValue.setClickable(false);//禁止开关按钮
                llBindCount.setClickable(false);    //禁止自定义绑定的数量
                tvBindCountValue.setCompoundDrawables(null, null, null, null);
            }
            if (deviceSafeConfig.userBindingDeviceLimit == 999) {
                tvBindCountValue.setText("无限制");
            } else if (deviceSafeConfig.userBindingDeviceLimit == 0) {
                tvBindCountValue.setText("禁止绑定");
            } else {
                tvBindCountValue.setText(String.valueOf(deviceSafeConfig.userBindingDeviceLimit) + "台");
            }
        }
    }

    public void initData() {
        mDeviceUserAdapter = new DeviceUserAdapter(this, dataList, view, num, deviceSafeConfig);
        lvDeviceList.setAdapter(mDeviceUserAdapter);
        lvDeviceList.setOnItemClickListener(new MyItemClickListener(dataList));

    }

    @Override
    public void getPosition(int position, int index) {
        this.position = position;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void success(String requestValue, int type, String requestName) {
        if (requestName.equals("bindList")) {
            requestValue = GetFinalRefreshTokenAfterRequestValue.getFinalRequestValue(this, requestValue, type, bindListUrl, mDeviceUserListRequest, this, requestName, "0");
            Gson gson = new Gson();
            DeviceUserListResponse mDeviceUserListResponse = gson.fromJson(requestValue, DeviceUserListResponse.class);
            if (null != mDeviceUserListResponse && null != dataList) {
                dataList.clear();
                dataList = mDeviceUserListResponse.result.dcUserDeviceBindList;
                if (dataList.size() <= 0) {
                    contentRoot.setEmptyButtonClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (Network.checkNetWork(DeviceSafeMainActivity.this)) {
                                getDeviceListFromServer();
                            }
                        }
                    });
                    contentRoot.showEmpty();
                }
                num = 0;
                for (int i = 0; i < dataList.size(); i++) {
                    if (dataList.get(i).bindStatus == 1 && dataList.get(i).statusFlag != 2)
                        num++;
                    tvHasBindValue.setText(String.valueOf(num) + "台");
                }
                initData();
            }
        } else if (requestName.equals("save")) {
            Dialog dialog = DialogFactory.creatDialog(DeviceSafeMainActivity.this, "", "配置修改成功!");
            dialog.setCancelable(true);
            dialog.show();
            requestDeviceSafeConfig();
        } else if (requestName.equals("safeConfig")) {
            DeviceSafeConfigResponse mDeviceSafeConfigResponse = null;
            requestValue = GetFinalRefreshTokenAfterRequestValue.getFinalRequestValue(this, requestValue, type, pathUrl, mDeviceSafeConfigRequest, this, requestName, "0");
            try {
                if (requestValue != null && !"".equals(requestValue)) {
                    Gson gson = new Gson();
                    mDeviceSafeConfigResponse = gson.fromJson(requestValue, DeviceSafeConfigResponse.class);
                    if (null != mDeviceSafeConfigResponse && null != mDeviceSafeConfigResponse.result) {
                        deviceSafeConfig = mDeviceSafeConfigResponse.result;
                        initData();
                    } else if (mDeviceSafeConfigResponse.code != 900) {
                        Toast.makeText(DeviceSafeMainActivity.this, "设备配置信息未返回，继续使用将会有异常！", Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("DeviceConfig", e.getMessage());
            } finally {
                if (null != progressDialog) {
                    progressDialog.dismiss();
                }
            }

        }

    }

    @Override
    public void fail(String exceptionMessage, int type, String requestName) {
        if (requestName.equals("save")) {
            requestDeviceSafeConfig();
            Dialog dialog = DialogFactory.creatDialog(DeviceSafeMainActivity.this, "", "配置修改失败！");
            dialog.setCancelable(true);
            dialog.show();
        } else if (requestName.equals("safeConfig")) {
            if (null != progressDialog) {
                progressDialog.dismiss();
            }
        }
    }

    @Override
    public void notNetwork() {
        if (null != progressDialog) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void callbackMainUI(String successMessage) {

    }

    class MyItemClickListener implements AdapterView.OnItemClickListener {
        public ArrayList<DeviceUserListResultItem> dataList;

        public MyItemClickListener(ArrayList<DeviceUserListResultItem> dataList) {
            this.dataList = dataList;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (null != dataList && dataList.size() > 0) {
//                if (rootView != null)
//                    rootView.setVisibility(View.GONE);
                if (null != deviceSafeConfig && deviceSafeConfig.userBindingDeviceLimit == 0 &&
                        deviceId.equals((dataList.get(position).deviceSn))) {
//                    LinearLayout llStateRoot = (LinearLayout) view.findViewById(R.id.bottom_item);
//                    llStateRoot.setVisibility(llStateRoot.isShown() ? View.GONE : View.VISIBLE);
//                    rootView = llStateRoot;
                    mDeviceUserAdapter.notifyDataSetChanged();
                    return;
//                    dataList.get(position).isExtra = true;

                }
                setExtra(position);
                mDeviceUserAdapter.notifyDataSetChanged();
            }
        }
    }


    public void setExtra(int position) {
        for (int i = 0; i < dataList.size(); i++) {
            if (i == position) {
                dataList.get(position).isExtra = !dataList.get(position).isExtra;
            } else {
                dataList.get(i).isExtra = false;
            }

        }
//        initData();

        mDeviceUserAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_left_button:
//                DeviceUnbindPopWindow mDeviceUnbindPopWindow = new DeviceUnbindPopWindow(DeviceSafeMainActivity.this);
//                mDeviceUnbindPopWindow.show(view);
                finish();
                break;
            case R.id.title_left_button_home:
                BookInit.getInstance().getmCallbackMX().callUserMeesageMain();
                break;
            case R.id.ll_bind_count:
                if (!Network.checkNetWork(DeviceSafeMainActivity.this)) {
                    Toast.makeText(DeviceSafeMainActivity.this, "网络异常，请检查！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (deviceSafeConfig.defineLevel == 1 || deviceSafeConfig.defineLevel == 2 ||
                        (deviceSafeConfig.defineLevel == 3 && deviceSafeConfig.vipFlag != 1)) {
                    Toast.makeText(DeviceSafeMainActivity.this, "当前不允许修改绑定数量!", Toast.LENGTH_SHORT).show();
                    return;
                }
                ArrayList<Integer> datalist = new ArrayList<Integer>();
                datalist.add(999);    //无限制
                if (deviceSafeConfig != null)
                    for (int i = 1; i <= 3; i++) {
                        datalist.add(i);
                    }
                position = position;
                ListPopupWindowAdapter listPopupWindowAdapter = new ListPopupWindowAdapter(DeviceSafeMainActivity.this, datalist);
                ListPopupWindow listPopupWindow = new ListPopupWindow(DeviceSafeMainActivity.this
                        , listPopupWindowAdapter, tvBindCountValue, position, 0, this);
                if (null != listPopupWindow)
                    listPopupWindow.showAtLocation(v, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
        }

    }
}
