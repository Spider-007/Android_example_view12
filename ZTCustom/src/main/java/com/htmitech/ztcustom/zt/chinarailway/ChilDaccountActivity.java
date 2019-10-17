package com.htmitech.ztcustom.zt.chinarailway;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.app.Cache;
import com.htmitech.ztcustom.zt.app.ZTCustomInit;
import com.htmitech.ztcustom.zt.base.BaseFragmentActivity;
import com.htmitech.ztcustom.zt.bean.GetCisAccountByAppResult;
import com.htmitech.ztcustom.zt.bean.GetCisAccountByAppResultRoot;
import com.htmitech.ztcustom.zt.domain.longin.GetChildAccount;
import com.htmitech.ztcustom.zt.domain.longin.ListDetails;
import com.htmitech.ztcustom.zt.util.ZTActivityUnit;

import java.util.HashMap;
import java.util.Map;

/**
 * 子账号激活
 *
 * @author htrf-pc
 */
public class ChilDaccountActivity extends BaseFragmentActivity implements
        OnClickListener {
    private RelativeLayout relativelayout_01, relativelayout_02;
    private TextView tv_up_name, tv_down_name;
    private ImageView ibn_fn5_back;
    private TextView xt_name, pt_name;
    private String cis_xt_name, cis_ps_name;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zt_activity_childaccount);
        initView();
        initData();
    }

    public void initView() {
        relativelayout_01 = (RelativeLayout) findViewById(R.id.relativelayout_01);
        relativelayout_02 = (RelativeLayout) findViewById(R.id.relativelayout_02);
        tv_up_name = (TextView) findViewById(R.id.tv_up_name);
        tv_down_name = (TextView) findViewById(R.id.tv_down_name);
        ibn_fn5_back = (ImageView) findViewById(R.id.ibn_fn5_back);
        xt_name = (TextView) findViewById(R.id.tv_cis_xt_name);
        pt_name = (TextView) findViewById(R.id.tv_cis_name_pt);
    }

    private String upName, downName;
    //    private ListDetails mListDetails0, mListDetails1;
    private GetCisAccountByAppResult mListDetails0, mListDetails1;

    public void initData() {

        //    <--------------------Administrator -> 2019-8-16:16:46: 新建一个子账号类 --------------------->
     /*   GetChildAccount mGetChildAccount = ZTCustomInit.get().getmCache()
                .getmGetChildAccount();*/
        GetCisAccountByAppResultRoot mGetChildAccount = ZTCustomInit.get().getmCache().getCisAccountByAppResultRoot();
        mListDetails0 = mGetChildAccount.result;
        mListDetails1 = mGetChildAccount.result;
        String status0 = "", status1 = "";
        if (mListDetails1.cisDeptCode.equals("2")
                || mListDetails1.cisDeptCode.equals("3")) {
            status1 = "（未激活）";
        } else {
            status1 = "（已激活）";
        }
        //    <--------------------Administrator -> 2019-8-16:17:25:状态判断 字段？？--------------------->
        if (mListDetails0.cisDeptCode.equals("2")) {
            status0 = "（未激活）";
        } else {
            status0 = "（已激活）";
        }
        upName = mListDetails0.cisDeptName + status0 + mListDetails0.cisAccountName;
        downName = mListDetails1.cisDeptName + status1 + mListDetails0.cisAccountName;
        tv_up_name.setText("" + upName);
        tv_down_name.setText("" + downName);
        relativelayout_02.setOnClickListener(this);
        relativelayout_01.setOnClickListener(this);
        ibn_fn5_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        Map<String, Object> params = new HashMap<String, Object>();
        if (arg0.getId() == R.id.relativelayout_01) {
            params.put("titleName", upName);
            params.put("ListDetails", mListDetails0);
            ZTActivityUnit.switchTo(this, ChilDaccountYZActivity.class, params);
        } else if (arg0.getId() == R.id.relativelayout_02) {
            params = new HashMap<String, Object>();
            params.put("titleName", downName);
            params.put("ListDetails", mListDetails1);
            ZTActivityUnit.switchTo(this, ChilDaccountYZActivity.class, params);
        } else if (arg0.getId() == R.id.ibn_fn5_back) {
            this.finish();
        }

    }
}