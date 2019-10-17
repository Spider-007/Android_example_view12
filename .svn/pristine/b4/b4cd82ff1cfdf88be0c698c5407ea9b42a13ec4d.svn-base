package com.htmitech.ztcustom.zt.chinarailway;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.app.ZTCustomInit;
import com.htmitech.ztcustom.zt.constant.ContantValues;
import com.htmitech.ztcustom.zt.domain.ZBBroadDown;
import com.htmitech.ztcustom.zt.domain.generalinfomation.InjuryDisposeListDetailRequest;
import com.htmitech.ztcustom.zt.domain.generalinfomation.InjuryDisposeListDetailResult;
import com.htmitech.ztcustom.zt.interfaces.ObserverCallBack;
import com.htmitech.ztcustom.zt.thread.AnsynHttpRequest;
import com.htmitech.ztcustom.zt.unit.CHTTP;
import com.htmitech.ztcustom.zt.util.FastJsonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InjuryDisposeListDetailActivity extends Activity {

    private ListView listview;
    private List<Map<String, Object>> data;
    private ArrayList<ZBBroadDown> list;
    private SimpleAdapter simpleAdapter;
    private ImageButton ibnBack;
    private TextView tv_fn4_title_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_injury_dispose_list_detail);
        listview = (ListView) findViewById(R.id.injury_dispose_detail_listview);
        tv_fn4_title_name = (TextView) findViewById(R.id.tv_injury_dispose_detail_title_name);
        data = getData();
        int resource = R.layout.zb_broad_detal_item;
        // from表示数组中存储数据源中的Map中的各个Key
        String[] from = {"key", "value"};
        // to表示数据源中的Map中的各个Key对应的Value应该显示到哪些控件
        int[] to = {R.id.tv_key, R.id.tv_value};
        simpleAdapter = new SimpleAdapter(this, data, resource, from, to);
        listview.setAdapter(simpleAdapter);
        simpleAdapter.notifyDataSetChanged();

        ibnBack = (ImageButton) findViewById(R.id.ibn_injury_dispose_detail_back);
        ibnBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                finish();
            }
        });
    }

    //    <--------------------Administrator -> 2019-8-16:16:36:ZTCustomInit.get().getmCache().getmListDetails().AccountId--------------------->
    private InjuryDisposeListDetailRequest updateData() {
        Intent intent = getIntent();
        tv_fn4_title_name.setText(intent.getStringExtra("title") != null ? intent.getStringExtra("title") : "伤损实况通知播报");
        String defect_id = intent.getStringExtra("defect_id");
        InjuryDisposeListDetailRequest br = new InjuryDisposeListDetailRequest();
        br.userid = ZTCustomInit.get().getmCache().getCisAccountDetail().cisAccountId;
        br.defect_id = defect_id;
        return br;
    }

    private List<Map<String, Object>> getData() {

        final List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();


        AnsynHttpRequest.request(this, updateData(),
                ContantValues.INJURYDISPOSELISTDETAIL, CHTTP.POST, new ObserverCallBack() {

                    @Override
                    public void success(String successMessage) {

                        InjuryDisposeListDetailResult result = FastJsonUtils.getPerson(successMessage, InjuryDisposeListDetailResult.class);
                        if (result == null) {
                            return;
                        }
                        list = new ArrayList<ZBBroadDown>();
                        list.clear();
                        list.addAll(result.defect_detail_list);
                        Map<String, Object> item;
                        Log.e("tag", "" + successMessage);

                        for (int i = 0; i < list.size(); i++) {
                            item = new HashMap<String, Object>();
                            item.put("key", list.get(i).getLabel());
                            item.put("value", list.get(i).getValue());
                            data.add(item);
                        }
                        simpleAdapter.notifyDataSetChanged();


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

        return data;
    }

}
