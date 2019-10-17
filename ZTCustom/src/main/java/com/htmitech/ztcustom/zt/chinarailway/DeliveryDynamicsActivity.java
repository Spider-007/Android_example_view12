package com.htmitech.ztcustom.zt.chinarailway;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.ztcustom.R;
import com.htmitech.utils.OAConText;
import com.htmitech.ztcustom.zt.app.PlannedCashRateConfig;
import com.htmitech.ztcustom.zt.base.BaseFragmentActivity;
import com.htmitech.ztcustom.zt.constant.ContantValues;
import com.htmitech.ztcustom.zt.constant.DeliveryDynamicsRequest;
import com.htmitech.ztcustom.zt.constant.DeliveryDynamicsTwoFix;
import com.htmitech.ztcustom.zt.constant.DeliveryDynamicsTwoNoFixThreeFix;
import com.htmitech.ztcustom.zt.constant.DeliveryDynamicsTwoNoFixThreeNoFixFourFix;
import com.htmitech.ztcustom.zt.constant.GetUserDetailInformationRequest;
import com.htmitech.ztcustom.zt.constant.GetUserDetailInformationResult;
import com.htmitech.ztcustom.zt.interfaces.ObserverCallBack;
import com.htmitech.ztcustom.zt.thread.AnsynHttpRequest;
import com.htmitech.ztcustom.zt.unit.CHTTP;
import com.htmitech.ztcustom.zt.util.FastJsonUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import htmitech.com.componentlibrary.unit.Utils;
import mobilereport.com.chatkit.domain.HTMRDataTable;
import mobilereport.com.chatkit.domain.Table;
import mobilereport.com.chatkit.listener.IOnItemClickListener;
import mobilereport.com.chatkit.myView.FromLayout;

/**
 * 发货动态
 *
 * @author heyang
 * @date 2018-5-10
 */
public class DeliveryDynamicsActivity extends BaseFragmentActivity implements OnClickListener {

    private LayoutInflater inflater;

    private TextView tv_delivery_date;// 上面的日期
    private ImageView iv_date_cut;// 日期减少
    private ImageView iv_date_add;// 日期增加
    private TextView tv_unit_one;
    private FromLayout formLayout_one;
    private FromLayout formLayout_twofix;
    private FromLayout formLayout_twonofixorthreefix;
    private FromLayout formLayout_generallast;
    private ImageButton ib_back;
    Calendar c = Calendar.getInstance();
    int year = c.get(Calendar.YEAR);
    int month = c.get(Calendar.MONTH) + 1;
    int monthNow = month;
    int yearNow = year;
    private HTMRDataTable mHTMRDataTable;
    private String userID;
    private DeliveryDynamicsRequest request;
    private DeliveryDynamicsTwoFix requestTwoFix;
    private DeliveryDynamicsTwoNoFixThreeFix requestTwoNoFixThreeFix;
    private DeliveryDynamicsTwoNoFixThreeNoFixFourFix requestTwoNoFixThreeNoFixFourFix;
    private String nowLevel = "one";
    String usedircode;
    String unitcode;
    private String lastLevel = "";
    private GetUserDetailInformationRequest getUserDetailInformationRequest;
    private GetUserDetailInformationResult getUserDetailInformationResult;
    private String orgName = "";
    private Map<String, String> unitMap;
    private String projectID = "";
    private TextView tvSearch;


    @SuppressLint("NewApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_deliverydynamic);
        tvSearch = (TextView) findViewById(R.id.tv_planned_rate_search);
        tvSearch.setOnClickListener(this);
        userID = OAConText.getInstance(this).UserID;
        unitMap = new HashMap<String, String>();
        initView();
        initData();
        initControl();
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();

    }

    private void initData() {
        if (request == null) {
            request = new DeliveryDynamicsRequest();
            request.userid = userID;
        }
        if (requestTwoFix == null) {
            requestTwoFix = new DeliveryDynamicsTwoFix();
            requestTwoFix.userid = userID;
        }
        if (requestTwoNoFixThreeFix == null) {
            requestTwoNoFixThreeFix = new DeliveryDynamicsTwoNoFixThreeFix();
            requestTwoNoFixThreeFix.userid = userID;
        }
        if (requestTwoNoFixThreeNoFixFourFix == null) {
            requestTwoNoFixThreeNoFixFourFix = new DeliveryDynamicsTwoNoFixThreeNoFixFourFix();
            requestTwoNoFixThreeNoFixFourFix.userid = userID;
        }
        request.querydate = year + "" + month;
        requestTwoFix.querydate = year + "" + month;
        requestTwoNoFixThreeFix.querydate = year + "" + month;
        requestTwoNoFixThreeNoFixFourFix.querydate = year + "0" + month;
        if (nowLevel.equals("one")) {
            formLayout_one.setVisibility(View.VISIBLE);
            formLayout_twonofixorthreefix.setVisibility(View.GONE);
            formLayout_generallast.setVisibility(View.GONE);
            formLayout_twofix.setVisibility(View.GONE);
            getDeliveryOneData();
        } else if (nowLevel.equals("twofix")) {
            Log.e("YJH", "initData: ->" + "twofix");
            formLayout_one.setVisibility(View.GONE);
            formLayout_twonofixorthreefix.setVisibility(View.GONE);
            formLayout_generallast.setVisibility(View.GONE);
            formLayout_twofix.setVisibility(View.VISIBLE);
            getDeliveryTwoFIXData();
        } else if (nowLevel.equals("twonofix")) {
            formLayout_one.setVisibility(View.GONE);
            formLayout_twonofixorthreefix.setVisibility(View.VISIBLE);
            formLayout_generallast.setVisibility(View.GONE);
            formLayout_twofix.setVisibility(View.GONE);
            getDeliveryTwoNoFixOrThreeFixData();
        } else if (nowLevel.equals("general")) {
            formLayout_one.setVisibility(View.GONE);
            formLayout_twonofixorthreefix.setVisibility(View.GONE);
            formLayout_generallast.setVisibility(View.VISIBLE);
            formLayout_twofix.setVisibility(View.GONE);
            getDeliveryLastData();
        }
    }

    @SuppressLint("NewApi")
    private void initView() {
        inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        tv_delivery_date = (TextView) findViewById(R.id.tv_delivery_title_data);
        tv_delivery_date.setText(year + "年" + month + "月");
        iv_date_add = (ImageView) findViewById(R.id.iv_delivery_add);
        iv_date_cut = (ImageView) findViewById(R.id.iv_delivery_cut);
        ib_back = (ImageButton) findViewById(R.id.ib_delivery_back);
        ib_back.setOnClickListener(this);
        iv_date_add.setOnClickListener(this);
        iv_date_cut.setOnClickListener(this);
        tv_unit_one = (TextView) findViewById(R.id.tv_delivery_unit_one);
        formLayout_one = (FromLayout) findViewById(R.id.fromlayout_delivery_unit_one);
        formLayout_twofix = (FromLayout) findViewById(R.id.fromlayout_delivery_unit_twofix);
        formLayout_twofix.setVisibility(View.GONE);
        formLayout_twonofixorthreefix = (FromLayout) findViewById(R.id.fromlayout_delivery_unit_twonofixorthreefix);
        formLayout_twonofixorthreefix.setVisibility(View.GONE);
        formLayout_generallast = (FromLayout) findViewById(R.id.fromlayout_delivery_unit_generalfix);
        formLayout_generallast.setVisibility(View.GONE);
        getUserDeatil();
    }

    //    <--------------------Administrator -> 2019-8-20:17:28:第一个界面点击->字段 赋值有问题 ，第三个跳转到第四个界面的时候出现问题了->usedircode--------------------->
    private void initControl() {
        formLayout_one.setOnItemClickListener(new IOnItemClickListener() {
            @Override
            public void onItemClick(final ArrayList<Table> arrayList) {

                DeliveryDynamicsActivity.this.runOnUiThread(new Runnable() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void run() {
                        if (nowLevel.equals("one")) {
                            lastLevel = "one";
                            nowLevel = "twofix";
                            requestTwoFix.usedircode = arrayList.get(0).value;
                            requestTwoNoFixThreeFix.usedircode = arrayList.get(0).value;
                            requestTwoNoFixThreeNoFixFourFix.usedircode = arrayList.get(0).value;
                            unitMap.put(nowLevel, arrayList.get(1).value);
                            initData();
                        }
                    }
                });
            }
        });

        formLayout_twofix.setOnItemClickListener(new IOnItemClickListener() {
            @Override
            public void onItemClick(final ArrayList<Table> arrayList) {

                DeliveryDynamicsActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (nowLevel.equals("twofix")) {
                            lastLevel = "twofix";
                            nowLevel = "twonofix";
                            requestTwoNoFixThreeFix.unitcode = arrayList.get(0).value;
                            tv_unit_one.setText("单位名称：" + arrayList.get(1).value);
                            unitMap.put(nowLevel, arrayList.get(1).value);
                            unitMap.put(unitcode, arrayList.get(0).value);
                            initData();
                        }
                    }
                });
            }
        });

        formLayout_twonofixorthreefix.setOnItemClickListener(new IOnItemClickListener() {
            @Override
            public void onItemClick(final ArrayList<Table> arrayList) {

                DeliveryDynamicsActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (nowLevel.equals("twonofix")) {
                            nowLevel = "general";
                            requestTwoNoFixThreeNoFixFourFix.projectname = arrayList.get(0).value;
                            requestTwoNoFixThreeNoFixFourFix.unitcode = unitMap.get(unitcode);
                            tv_unit_one.setText("单位名称：" + arrayList.get(0).value);
                            unitMap.put(nowLevel, arrayList.get(0).value);
                            initData();
                        }
                    }
                });
            }
        });

        formLayout_generallast.setOnItemClickListener(new IOnItemClickListener() {
            @Override
            public void onItemClick(final ArrayList<Table> arrayList) {

                DeliveryDynamicsActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (nowLevel.equals("general")) {//跳到详细页面
                            Intent intent = new Intent(DeliveryDynamicsActivity.this, DeliveryDynamicsListDetailActivity.class);
                            intent.putExtra("transmitbillid", arrayList.get(0).value);
                            intent.putExtra("title", "发货动态");
                            startActivity(intent);
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PlannedCashRateConfig.LISTREQUESTCODE && resultCode == PlannedCashRateConfig.LISTRESULTCODE) {
            if (data != null) {
                String projectName = data.getStringExtra("pntprojectname");
                projectID = data.getStringExtra("id");
                getDeliveryOneData();
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ib_delivery_back) {
            if (nowLevel.equals("general")) {
                nowLevel = "twonofix";
                setUnitOne();
                initData();
            } else if (nowLevel.equals("twonofix")) {
                if (lastLevel.equals("one")) {
                    nowLevel = "one";
                    setUnitOne();
                    initData();
                } else if (lastLevel.equals("twofix")) {
                    nowLevel = "twofix";
                    setUnitOne();
                    initData();
                }
            } else if (nowLevel.equals("twofix")) {
                nowLevel = "one";
                setUnitOne();
                initData();
            } else {
                finish();
            }
        } else if (view.getId() == R.id.iv_delivery_add) {
            if (year < yearNow || month < monthNow) {
                c.add(Calendar.MONTH, 1);
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH) + 1;
                tv_delivery_date.setText(year + "年" + month + "月");
                initData();
            }
        } else if (view.getId() == R.id.iv_delivery_cut) {
            c.add(Calendar.MONTH, -1);
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH) + 1;
            tv_delivery_date.setText(year + "年" + month + "月");
            initData();
        } else if (view.getId() == R.id.tv_planned_rate_search) {
            Intent intent1 = new Intent(this, QualityBookKeyWordsSearchActivity.class);
            intent1.putExtra("builder", 1);
            intent1.putExtra("from", "plannedCashRate");
            intent1.putExtra("title", "发运动态");
            intent1.putExtra("userid",  OAConText.getInstance(this).UserID);
            startActivityForResult(intent1, PlannedCashRateConfig.LISTREQUESTCODE);
        }
    }


    //一级界面
    public void getDeliveryOneData() {
        showProgressDialog(this);
        request.projectid = projectID;
        AnsynHttpRequest.request(this, request, ContantValues.DELIVERYDYNAMICSONE, CHTTP.POST, new ObserverCallBack() {

            @Override
            public void success(final String requestValue) {
                // TODO Auto-generated method stub
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            initHTMRDataTable(readRawFile(R.raw.fhdtone_811), requestValue);
                            DeliveryDynamicsActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Log.d("WorkReportSitFragment", "WorkReportSitFragment------------------->");
                                        long upMillis = System.currentTimeMillis();
                                        if (mHTMRDataTable != null) {
                                            formLayout_one.initFromLayout(mHTMRDataTable);
                                        }
                                        long currentTimeMillis = System.currentTimeMillis();
                                        Log.d("WorkReportSitFragment", "WorkReportSitFragment------------------->" + (currentTimeMillis - upMillis));
                                        dimssDialog();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        dimssDialog();
                                    }
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                            dimssDialog();
                        }
                    }
                }).start();

            }

            @Override
            public void notNetwork() {
                // TODO Auto-generated method stub
                Utils.toast(DeliveryDynamicsActivity.this, "网络连接异常", Toast.LENGTH_SHORT);
                dimssDialog();
            }

            @Override
            public void fail(String exceptionMessage) {
                // TODO Auto-generated method stub
                dimssDialog();
                Utils.toast(DeliveryDynamicsActivity.this, "请求错误:" + exceptionMessage, Toast.LENGTH_SHORT);
            }

        });
    }

    //发货动态二级界面大维修
    public void getDeliveryTwoFIXData() {
        showProgressDialog(this);
        AnsynHttpRequest.request(this, requestTwoFix, ContantValues.DELIVERYDYNAMICSTWOFIX, CHTTP.POST, new ObserverCallBack() {

            @Override
            public void success(final String requestValue) {
                // TODO Auto-generated method stub
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            initHTMRDataTable(readRawFile(R.raw.fhdttwo_811), requestValue);
                            DeliveryDynamicsActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Log.d("WorkReportSitFragment", "WorkReportSitFragment------------------->");
                                        long upMillis = System.currentTimeMillis();
                                        if (mHTMRDataTable != null) {
                                            formLayout_twofix.initFromLayout(mHTMRDataTable);
                                        }
                                        long currentTimeMillis = System.currentTimeMillis();
                                        Log.d("WorkReportSitFragment", "WorkReportSitFragment------------------->" + (currentTimeMillis - upMillis));
                                        dimssDialog();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        dimssDialog();
                                    }
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                            dimssDialog();
                        }
                    }
                }).start();

            }

            @Override
            public void notNetwork() {
                // TODO Auto-generated method stub
                Utils.toast(DeliveryDynamicsActivity.this, "网络连接异常", Toast.LENGTH_SHORT);
                dimssDialog();
            }

            @Override
            public void fail(String exceptionMessage) {
                // TODO Auto-generated method stub
                dimssDialog();
                Utils.toast(DeliveryDynamicsActivity.this, "请求错误:" + exceptionMessage, Toast.LENGTH_SHORT);
            }

        });
    }

    //发货动态非大维修二级界面及大维修三级界面
    public void getDeliveryTwoNoFixOrThreeFixData() {
        showProgressDialog(this);
        AnsynHttpRequest.request(this, requestTwoNoFixThreeFix, ContantValues.DELIVERYDYNAMICSTWONOFIXORTHREEFIX, CHTTP.POST, new ObserverCallBack() {

            @Override
            public void success(final String requestValue) {
                // TODO Auto-generated method stub
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            initHTMRDataTable(readRawFile(R.raw.fhdtthree_811), requestValue);
                            DeliveryDynamicsActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Log.d("WorkReportSitFragment", "WorkReportSitFragment------------------->");
                                        long upMillis = System.currentTimeMillis();
                                        if (mHTMRDataTable != null) {
                                            formLayout_twonofixorthreefix.initFromLayout(mHTMRDataTable);
                                        }
                                        long currentTimeMillis = System.currentTimeMillis();
                                        Log.d("WorkReportSitFragment", "WorkReportSitFragment------------------->" + (currentTimeMillis - upMillis));
                                        dimssDialog();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        dimssDialog();
                                    }
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                            dimssDialog();
                        }
                    }
                }).start();

            }

            @Override
            public void notNetwork() {
                // TODO Auto-generated method stub
                Utils.toast(DeliveryDynamicsActivity.this, "网络连接异常", Toast.LENGTH_SHORT);
                dimssDialog();
            }

            @Override
            public void fail(String exceptionMessage) {
                // TODO Auto-generated method stub
                dimssDialog();
                Utils.toast(DeliveryDynamicsActivity.this, "请求错误:" + exceptionMessage, Toast.LENGTH_SHORT);
            }

        });
    }

    //发货动态最后一级界面界面
    public void getDeliveryLastData() {
        showProgressDialog(this);
        AnsynHttpRequest.request(this, requestTwoNoFixThreeNoFixFourFix, ContantValues.DELIVERYDYNAMICSTGENERAL, CHTTP.POST, new ObserverCallBack() {

            @Override
            public void success(final String requestValue) {
                // TODO Auto-generated method stub
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            initHTMRDataTable(readRawFile(R.raw.fhdtfour_811), requestValue);
                            DeliveryDynamicsActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Log.d("WorkReportSitFragment", "WorkReportSitFragment------------------->");
                                        long upMillis = System.currentTimeMillis();
                                        if (mHTMRDataTable != null) {
                                            formLayout_generallast.initFromLayout(mHTMRDataTable);
                                        }
                                        long currentTimeMillis = System.currentTimeMillis();
                                        Log.d("WorkReportSitFragment", "WorkReportSitFragment------------------->" + (currentTimeMillis - upMillis));
                                        dimssDialog();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        dimssDialog();
                                    }
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                            dimssDialog();
                        }
                    }
                }).start();

            }

            @Override
            public void notNetwork() {
                // TODO Auto-generated method stub
                Utils.toast(DeliveryDynamicsActivity.this, "网络连接异常", Toast.LENGTH_SHORT);
                dimssDialog();
            }

            @Override
            public void fail(String exceptionMessage) {
                // TODO Auto-generated method stub
                dimssDialog();
                Utils.toast(DeliveryDynamicsActivity.this, "请求错误:" + exceptionMessage, Toast.LENGTH_SHORT);
            }

        });
    }


    protected float pxTodp(float value) {
        DisplayMetrics metrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay()
                .getMetrics(metrics);
        float valueDP = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                value, metrics);
        return valueDP;
    }

    /**
     * 初始化定义
     */
    public void initHTMRDataTable(String titleJson, String bodyJson) {
        if (TextUtils.isEmpty(titleJson) || TextUtils.isEmpty(bodyJson)) {
            return;
        }
        try {
            JSONArray data;
            JSONObject jsonObjectBody = JSON.parseObject(bodyJson);
            //bodyJson ->
            JSONObject resultObject = jsonObjectBody.getJSONObject("result");
            JSONObject jsonObject = JSON.parseObject(titleJson);
            data = resultObject.getJSONArray("datas");
            jsonObject.put("data", data);
            String jsonAll = jsonObject.toJSONString();
            mHTMRDataTable = JSON.parseObject(jsonAll, HTMRDataTable.class);
        } catch (Exception e) {
            Log.e("YJH", "initHTMRDataTable->EXCEPTION: " + e.toString() + "->messahe:" + e.getMessage());
        }

    }

    private String readRawFile(int rawID) {
        String content;
        Resources resources = this.getResources();
        InputStream is = null;
        try {
            is = resources.openRawResource(rawID);
            byte buffer[] = new byte[is.available()];
            is.read(buffer);
            content = new String(buffer);
            return content;
        } catch (IOException e) {
            Log.e("write file", e.toString());
            return null;
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    Log.e("close file", e.toString());
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (nowLevel.equals("general")) {
            nowLevel = "twonofix";
            setUnitOne();
            initData();
        } else if (nowLevel.equals("twonofix")) {
            if (lastLevel.equals("one")) {
                nowLevel = "one";
                setUnitOne();
                initData();
            } else if (lastLevel.equals("twofix")) {
                nowLevel = "twofix";
                setUnitOne();
                initData();
            }
        } else if (nowLevel.equals("twofix")) {
            nowLevel = "one";
            setUnitOne();
            initData();
        } else {
            finish();
        }
    }

    private void setUnitOne() {
        if (unitMap != null && unitMap.get(nowLevel) != null && !"".equals(unitMap.get(nowLevel))) {
            tv_unit_one.setText("项目类型：" + unitMap.get(nowLevel));
            if (nowLevel.equals("general")) {
                tv_unit_one.setText("项目名称：" + unitMap.get(nowLevel));
            }
        } else {
            tv_unit_one.setText("项目类型：" + orgName);
        }
        if (nowLevel.equals("one")) {
            tv_unit_one.setText("");
        }
    }

    private void getUserDeatil() {
        getUserDetailInformationRequest = new GetUserDetailInformationRequest();
        getUserDetailInformationRequest.userid = OAConText.getInstance(this).UserID;
        AnsynHttpRequest.request(this, getUserDetailInformationRequest, ContantValues.GETUSERDETAILINFORMATION, CHTTP.POST, new ObserverCallBack() {

            @Override
            public void success(String successMessage) {
                if (successMessage != null && !successMessage.equals("")) {
                    getUserDetailInformationResult = FastJsonUtils.getPerson(successMessage, GetUserDetailInformationResult.class);
                    if (getUserDetailInformationResult.success) {
                        orgName = getUserDetailInformationResult.orgname;
                        tv_unit_one.setText("");
                        unitMap.put(nowLevel, orgName);
                    }
                }
            }

            @Override
            public void notNetwork() {
                // TODO Auto-generated method stub
            }

            @Override
            public void fail(String exceptionMessage) {
                // TODO Auto-generated method stub
            }
        });
    }

}