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
import com.htmitech.utils.OAConText;
import com.htmitech.ztcustom.zt.adapter.DeliveryAdapter;
import com.htmitech.ztcustom.zt.app.ZTCustomInit;
import com.htmitech.ztcustom.zt.constant.ContantValues;
import com.htmitech.ztcustom.zt.constant.DeliveryDynamicsListDetailKeyValue;
import com.htmitech.ztcustom.zt.constant.DeliveryDynamicsListDetailRequest;
import com.htmitech.ztcustom.zt.constant.DeliveryDynamicsListDetailResult;
import com.htmitech.ztcustom.zt.interfaces.ObserverCallBack;
import com.htmitech.ztcustom.zt.thread.AnsynHttpRequest;
import com.htmitech.ztcustom.zt.unit.CHTTP;
import com.htmitech.ztcustom.zt.util.FastJsonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 伤损实况通知播报
 */
public class DeliveryDynamicsListDetailActivity extends Activity {

    private ListView listview;
    private List<Map<String, Object>> data;
    private ArrayList<DeliveryDynamicsListDetailKeyValue> list;
    private SimpleAdapter simpleAdapter;
    private ImageButton ibnBack;
    private TextView tv_fn4_title_name;
    private DeliveryAdapter deliveryAdapter;

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
        // simpleAdapter = new SimpleAdapter(this, data, resource, from, to);
        deliveryAdapter = new DeliveryAdapter(this, data);
        listview.setAdapter(deliveryAdapter);
        deliveryAdapter.notifyDataSetChanged();

        ibnBack = (ImageButton) findViewById(R.id.ibn_injury_dispose_detail_back);
        ibnBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                finish();
            }
        });
    }

    private DeliveryDynamicsListDetailRequest updateData() {
        Intent intent = getIntent();
        tv_fn4_title_name.setText(intent.getStringExtra("title") != null ? intent.getStringExtra("title") : "发货动态");
        String transmitbillid = intent.getStringExtra("transmitbillid");
        DeliveryDynamicsListDetailRequest detailRequest = new DeliveryDynamicsListDetailRequest();
//        detailRequest.userid = "liyi";
        detailRequest.userid = OAConText.getInstance(this).UserID;
        detailRequest.transmitbillid = transmitbillid;
        return detailRequest;
    }

    private List<Map<String, Object>> getData() {
        //    <--------------------Administrator -> 2019-8-6:21:08:get All Data--------------------->
        final List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        AnsynHttpRequest.request(this, updateData(), ContantValues.DELIVERYDYNAMICSDETAIL, CHTTP.POST, new ObserverCallBack() {

            @Override
            public void success(String successMessage) {

                DeliveryDynamicsListDetailResult result = FastJsonUtils.getPerson(successMessage, DeliveryDynamicsListDetailResult.class);
                if (result == null) {
                    return;
                }
                if (result.code == 200) {
                    list = new ArrayList<DeliveryDynamicsListDetailKeyValue>();
                    list.clear();
                    list.addAll(result.result.datas);
                    Map<String, Object> item;
                    Log.e("tag", "" + successMessage);

                    for (int i = 0; i < list.size(); i++) {
                        item = new HashMap<String, Object>();
                        item.put("key", list.get(i).getKey());
                        item.put("value", list.get(i).getValue());
                        data.add(item);
                    }
                    deliveryAdapter.notifyDataSetChanged();
                }else{
                    Log.e("YJH", "failed->result->null");
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

        return data;
    }

}
