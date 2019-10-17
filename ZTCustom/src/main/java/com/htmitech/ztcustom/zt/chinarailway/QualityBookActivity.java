package com.htmitech.ztcustom.zt.chinarailway;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.app.ZTCustomInit;
import com.htmitech.ztcustom.zt.base.BaseFragmentActivity;
import com.htmitech.ztcustom.zt.constant.ContantValues;
import com.htmitech.ztcustom.zt.constant.QualityBookfirstBean;
import com.htmitech.ztcustom.zt.constant.QualityBookfirstRequest;
import com.htmitech.ztcustom.zt.interfaces.ObserverCallBack;
import com.htmitech.ztcustom.zt.thread.AnsynHttpRequest;
import com.htmitech.ztcustom.zt.unit.CHTTP;
import com.htmitech.ztcustom.zt.util.FastJsonUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import mobilereport.com.chatkit.domain.HTMRDataTable;
import mobilereport.com.chatkit.domain.Table;
import mobilereport.com.chatkit.listener.IOnItemClickListener;
import mobilereport.com.chatkit.myView.FromLayout;

/**
 * 质保书首页
 */
public class QualityBookActivity extends BaseFragmentActivity implements View.OnClickListener {

    public ImageButton imageButtonBack;
    private ImageView imgSearch;
    private QualityBookfirstBean.ResultsBean qualityBookfirstresultsBean;
    private TextView tvMonthNum;
    private TextView tvMonthAllNum;
    private FromLayout fromlayoutQualitybook;
    private HTMRDataTable mHTMRDataTable;
    private List<QualityBookfirstBean.ResultsBean.MainlistBean> qualityBookfirstmainlist;
    private ImageView iv_date_cut;
    private ImageView iv_date_add;
    private int REQUESTCODE = 1;
    Calendar c = Calendar.getInstance();
    int year1 = c.get(Calendar.YEAR);
    int month1 = c.get(Calendar.MONTH) + 1;
    int monthNow = month1;
    int yearNow = year1;
    private String year;
    private String month;
    private TextView tvDate;

    private String jsdwid = "";
    private String shdwid = "";
    private String dzid = "";
    private String zbsid = "";
    private String vehicleno = "";
    private String userID;
    private String showMontch = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quality_book);
        initView();
        //    <--------------------Administrator -> 2019-8-16:16:37:ZTCustomInit.get().getmCache().getmListDetails().AccountId--------------------->
        userID = ZTCustomInit.get().getmCache().getCisAccountDetail().cisAccountId;
        getData();
        fromlayoutQualitybook.setOnItemClickListener(new IOnItemClickListener() {
            @Override
            public void onItemClick(ArrayList<Table> arrayList) {
                String gcid = arrayList.get(0).value;
                Intent intent1 = new Intent(QualityBookActivity.this, QualityBookSituationActivity.class);
                intent1.putExtra("year", year);
                intent1.putExtra("month", month);
                intent1.putExtra("jsdwid", jsdwid);
                intent1.putExtra("shdwid", shdwid);
                intent1.putExtra("dzid", dzid);
                intent1.putExtra("gcid", gcid);
                intent1.putExtra("zbsid", zbsid);
                intent1.putExtra("vehicleno", vehicleno);
                intent1.putExtra("userid", userID);
                startActivity(intent1);
            }
        });
    }

    private void getData() {
        showProgressDialog(this);
        QualityBookfirstRequest request = new QualityBookfirstRequest();
        request.userid = userID;
        request.datebegin = year + month;
        request.dzid = (dzid == null ? "" : dzid);
        request.jsdwid = (jsdwid == null ? "" : jsdwid);
        request.shdwid = (shdwid == null ? "" : shdwid);
        request.vehicleno = (vehicleno == null ? "" : vehicleno);
        request.zbsid = (zbsid == null ? "" : zbsid);

        AnsynHttpRequest.request(this, request, ContantValues.QUALITYBOOKFIRSTZONG,
                CHTTP.POST, new ObserverCallBack() {
                    @Override
                    public void success(final String successMessage) {
                        QualityBookfirstBean qualityBookfirstBean = FastJsonUtils.getPerson(successMessage, QualityBookfirstBean.class);
                        if (qualityBookfirstBean != null) {
                            qualityBookfirstresultsBean = qualityBookfirstBean.getResults();
                            if (null != qualityBookfirstresultsBean)
                                qualityBookfirstmainlist = qualityBookfirstresultsBean.getMainlist();
                            if (null != qualityBookfirstmainlist)
                                addDataInUI();
                        }
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    initHTMRDataTable(readRawFile(R.raw.qualitybookfirst), qualityBookfirstmainlist);
                                    QualityBookActivity.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                long upMillis = System.currentTimeMillis();
                                                if (mHTMRDataTable != null) {
                                                    fromlayoutQualitybook.initFromLayout(mHTMRDataTable);
                                                }
                                                long currentTimeMillis = System.currentTimeMillis();
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
                    public void fail(String exceptionMessage) {
                        Toast.makeText(QualityBookActivity.this, "数据请求失败!", Toast.LENGTH_SHORT).show();
                        dimssDialog();
                    }

                    @Override
                    public void notNetwork() {
                        Toast.makeText(QualityBookActivity.this, "网络无连接!", Toast.LENGTH_SHORT).show();
                        dimssDialog();
                    }
                });
    }

    private void addDataInUI() {
        tvMonthNum.setText(qualityBookfirstresultsBean.getMainsum().getCurrentmonthnum());
        tvMonthAllNum.setText(qualityBookfirstresultsBean.getMainsum().getCurrentmonthallnum());
    }


    @SuppressLint("SetTextI18n")
    private void initView() {
        imageButtonBack = (ImageButton) findViewById(R.id.ib_quality_book_back);
        imgSearch = (ImageView) findViewById(R.id.bt_quality_book_right_top);
        tvMonthNum = (TextView) findViewById(R.id.tv_card_first_bottom);
        tvMonthAllNum = (TextView) findViewById(R.id.tv_card_second_bottom);
        imgSearch = (ImageView) findViewById(R.id.bt_quality_book_right_top);
        iv_date_cut = (ImageView) findViewById(R.id.iv_tanshangcompletesum_cut);
        iv_date_add = (ImageView) findViewById(R.id.iv_tanshangcompletesum_add);
        fromlayoutQualitybook = (FromLayout) findViewById(R.id.fromlayoutqualitybook);
        tvDate = (TextView) findViewById(R.id.tv_qualitybook_title_data);
        imageButtonBack.setOnClickListener(this);
        imgSearch.setOnClickListener(this);
        iv_date_add.setOnClickListener(this);
        iv_date_cut.setOnClickListener(this);
        year = String.valueOf(year1);
        if (month1 < 10) {
            month = "0" + month1;
        } else {
            month = String.valueOf(month1);
        }
        if (month1 != 10 && month1 < 10) {
            if (month.contains("0")) {
                showMontch = month.replace("0", "");
            }
        } else {
            showMontch = month;
        }
        tvDate.setText(year1 + "年" + showMontch + "月");

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ib_quality_book_back) {
            this.finish();
        } else if (v.getId() == R.id.bt_quality_book_right_top) {
            Intent intent = new Intent(QualityBookActivity.this, QualityBookSearchNewsActivity.class);
            intent.putExtra("year", year);
            intent.putExtra("month", month);
            startActivityForResult(intent, REQUESTCODE);
        } else if (v.getId() == R.id.iv_tanshangcompletesum_cut) {
            c.add(Calendar.MONTH, -1);
            year1 = c.get(Calendar.YEAR);
            month1 = c.get(Calendar.MONTH) + 1;
            // initData();
            if (month1 < 10) {
                month = "0" + month1;
            } else {
                month = String.valueOf(month1);
            }
            if (month1 != 10 && month1 < 10) {
                if (month.contains("0")) {
                    showMontch = month.replace("0", "");
                }
            } else {
                showMontch = month;
            }
            tvDate.setText(year1 + "年" + showMontch + "月");
            year = String.valueOf(year1);
            getData();

        } else if (v.getId() == R.id.iv_tanshangcompletesum_add) {
            if (year1 < yearNow || month1 < monthNow) {
                c.add(Calendar.MONTH, 1);
                year1 = c.get(Calendar.YEAR);
                month1 = c.get(Calendar.MONTH) + 1;
                year = String.valueOf(year1);
                if (month1 < 10) {
                    month = "0" + month1;
                } else {
                    month = String.valueOf(month1);
                }
                if (month1 != 10 && month1 < 10) {
                    if (month.contains("0")) {
                        showMontch = month.replace("0", "");
                    }
                } else {
                    showMontch = month;
                }
                tvDate.setText(year1 + "年" + showMontch + "月");
                getData();
            }
        }

    }

    /**
     * 初始化定义
     */
    public void initHTMRDataTable(String titleJson, List<QualityBookfirstBean.ResultsBean.MainlistBean> list) {
        if (TextUtils.isEmpty(titleJson) || null == list || list.size() == 0) {
            return;
        }
        try {
            // JSONArray data;
            // JSONObject jsonObjectBody = JSON.parseObject(bodyJson);
            JSONObject jsonObject = JSON.parseObject(titleJson);
            // data = jsonObjectBody.getJSONArray("results");
            jsonObject.put("data", list);
            String jsonAll = jsonObject.toJSONString();
            mHTMRDataTable = JSON.parseObject(jsonAll, HTMRDataTable.class);
        } catch (Exception e) {

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 4) {
            String jsdwid1 = data.getStringExtra("jsdwid");
            String shdwid1 = data.getStringExtra("shdwid");
            String dzid1 = data.getStringExtra("dzid");
            String zbsid1 = data.getStringExtra("bianhao");
            String vehicleno1 = data.getStringExtra("carnum");
            jsdwid = jsdwid1;
            shdwid = shdwid1;
            dzid = dzid1;
            zbsid = zbsid1;
            vehicleno = vehicleno1;
            getData();
        }
    }
}
