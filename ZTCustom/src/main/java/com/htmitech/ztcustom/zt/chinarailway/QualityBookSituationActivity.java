package com.htmitech.ztcustom.zt.chinarailway;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;


import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.adapter.QualitybookSituationAdapter;
import com.htmitech.ztcustom.zt.base.BaseFragmentActivity;
import com.htmitech.ztcustom.zt.constant.ContantValues;
import com.htmitech.ztcustom.zt.constant.QualityBookSituationBean;
import com.htmitech.ztcustom.zt.constant.QualityBookSituationRequest;
import com.htmitech.ztcustom.zt.interfaces.ObserverCallBack;
import com.htmitech.ztcustom.zt.thread.AnsynHttpRequest;
import com.htmitech.ztcustom.zt.unit.CHTTP;
import com.htmitech.ztcustom.zt.util.FastJsonUtils;

import java.util.List;

/**
 * 质保书二级页面
 */
public class QualityBookSituationActivity extends BaseFragmentActivity implements View.OnClickListener {

    private ImageButton imgBack;
    private ImageView imgSearch;
    private ListView lvQualitybookSituation;
    private List<QualityBookSituationBean.ResultsBean> qualityBookSituationList;
    private String year;
    private String month;
    private String jsdwid;
    private String shdwid;
    private String dzid;
    private String gcid;
    private String userid;
    private String vehicleno;
    private String zbsid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quality_book_situation);
        initView();
        Intent intent = getIntent();
        year = intent.getStringExtra("year");
        month = intent.getStringExtra("month");
        jsdwid = intent.getStringExtra("jsdwid");
        shdwid = intent.getStringExtra("shdwid");
        dzid = intent.getStringExtra("dzid");
        gcid = intent.getStringExtra("gcid");
        userid = intent.getStringExtra("userid");
        vehicleno = intent.getStringExtra("vehicleno");
        zbsid = intent.getStringExtra("zbsid");
        imgBack.setOnClickListener(this);
        imgSearch.setOnClickListener(this);
        initData();
        lvQualitybookSituation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String zbsid = qualityBookSituationList.get(i).getZbsid();
                String uploadid = qualityBookSituationList.get(i).getUploadid();
                String cftype = qualityBookSituationList.get(i).getCftype();
                Intent intent1 = new Intent(QualityBookSituationActivity.this,QualityBookDetailActivity.class);
                intent1.putExtra("userid",userid);
                intent1.putExtra("zbsid",zbsid);
                intent1.putExtra("uploadid",uploadid);
                intent1.putExtra("cftype",cftype);
                startActivity(intent1);
            }
        });
    }


    private void initView() {
        imgBack = (ImageButton) findViewById(R.id.ib_quality_book_back);
        imgSearch = (ImageView) findViewById(R.id.bt_quality_book_right_top);
        lvQualitybookSituation = (ListView) findViewById(R.id.lv_qualitybook_situation);
    }

    private void initData() {
        showProgressDialog(this);
        QualityBookSituationRequest request = new QualityBookSituationRequest();
        request.userid = userid;
        request.jsdwid = (jsdwid == null ? "" : jsdwid);
        request.shdwid = (shdwid == null ? "" : shdwid);
        request.datebegin =year+month;
        request.dzid = (dzid == null ? "" : dzid);
        request.gcid = (gcid == null ? "" : gcid);
        request.vehicleno = (vehicleno == null ? "" : vehicleno);
        request.zbsid = (zbsid == null ? "" : zbsid);
        AnsynHttpRequest.request(this, request, ContantValues.QUALITYBOOKSITUATION,
        CHTTP.POST, new ObserverCallBack() {
            @Override
            public void success(String successMessage) {
                QualityBookSituationBean qualityBookSituationBean = FastJsonUtils.getPerson(successMessage, QualityBookSituationBean.class);
                if(qualityBookSituationBean != null){
                    qualityBookSituationList = qualityBookSituationBean.getResults();
                    lvgetqualitybookSituationData();
                }
                dimssDialog();
            }

            @Override
            public void fail(String exceptionMessage) {
                Toast.makeText(QualityBookSituationActivity.this,"数据请求失败!",Toast.LENGTH_SHORT).show();
                dimssDialog();
            }

            @Override
            public void notNetwork() {
                Toast.makeText(QualityBookSituationActivity.this,"网络异常!",Toast.LENGTH_SHORT).show();
                dimssDialog();
            }
        });
    }

    private void lvgetqualitybookSituationData() {
        QualitybookSituationAdapter qualitybookSituationAdapter = new QualitybookSituationAdapter(QualityBookSituationActivity.this,qualityBookSituationList);
        lvQualitybookSituation.setAdapter(qualitybookSituationAdapter);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() ==R.id.ib_quality_book_back ){
            this.finish();
        }else if(view.getId() ==R.id.bt_quality_book_right_top){

        }

    }
}
