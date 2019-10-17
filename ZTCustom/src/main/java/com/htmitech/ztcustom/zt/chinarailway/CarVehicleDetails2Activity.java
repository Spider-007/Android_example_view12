package com.htmitech.ztcustom.zt.chinarailway;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.adapter.CarVehicleDetailsAdapter;
import com.htmitech.ztcustom.zt.app.ZTCustomInit;
import com.htmitech.ztcustom.zt.base.BaseFragmentActivity;
import com.htmitech.ztcustom.zt.constant.CarVehicleDetailsBean;
import com.htmitech.ztcustom.zt.constant.CarVehicleDetailsRequest;
import com.htmitech.ztcustom.zt.constant.ContantValues;
import com.htmitech.ztcustom.zt.interfaces.ObserverCallBack;
import com.htmitech.ztcustom.zt.thread.AnsynHttpRequest;
import com.htmitech.ztcustom.zt.unit.CHTTP;
import com.htmitech.ztcustom.zt.util.FastJsonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarVehicleDetails2Activity extends BaseFragmentActivity {

    private String carnums;
    private String userID;
    private ImageButton imgBack;
    private TextView tvCarnum;
    private TextView tvStartstation;
    private TextView tvCurrentstation;
    private TextView tvArrivestation;
    private TextView tvStarttime;
    private TextView tvCurrenttime;
    private TextView tvArrivetime;
    private ImageView imgLine1;
    private ImageView imgLine2;
    private ListView lvCarDetail;
    private ImageView ivClear;
    private List<Map<String, Object>> data;
    private ArrayList<CarVehicleDetailsBean.ResultsBean.RuninfoBean> list = new ArrayList<CarVehicleDetailsBean.ResultsBean.RuninfoBean>();
    private CarVehicleDetailsAdapter carVehicleDetailsAdapter;
    private CarVehicleDetailsBean.ResultsBean.RuntrackBean runtrack;
    private RelativeLayout rlAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_vehicle_details2);
        Intent intent = getIntent();
        carnums = intent.getStringExtra("carnums");
//        userID = ZTCustomInit.get().getmCache().getmListDetails().AccountId;
        userID = ZTCustomInit.get().getmCache().getCisAccountDetail().cisAccountId;
        Log.e("YJH", "onCreate->CarVehicleDetails2Activity:"+userID);
        initView();
    }


    private void initView() {
        imgBack = (ImageButton) findViewById(R.id.ib_car_vehicle_back);
        tvCarnum = (TextView) findViewById(R.id.tv_carnum);
        tvStartstation = (TextView) findViewById(R.id.tv_startstation);
        tvCurrentstation = (TextView) findViewById(R.id.tv_currentstation);
        tvArrivestation = (TextView) findViewById(R.id.tv_arrivestation);
        tvStarttime = (TextView) findViewById(R.id.tv_starttime);
        tvCurrenttime = (TextView) findViewById(R.id.tv_currenttime);
        tvArrivetime = (TextView) findViewById(R.id.tv_arrivetime);
        imgLine1 = (ImageView) findViewById(R.id.img_line1);
        imgLine2 = (ImageView) findViewById(R.id.img_line2);
        lvCarDetail = (ListView) findViewById(R.id.lv_car_detail);
        ivClear = (ImageView) findViewById(R.id.iv_icon_clear);
        rlAll = (RelativeLayout) findViewById(R.id.rl_all);
        rlAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                try{
                    CarVehicleDetails2Activity.this.overridePendingTransition(R.anim.fade_out,R.anim.fade_out1);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });


        data = initData();
        carVehicleDetailsAdapter = new CarVehicleDetailsAdapter(this,data);
        lvCarDetail.setAdapter(carVehicleDetailsAdapter);
        carVehicleDetailsAdapter.notifyDataSetChanged();

        lvCarDetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                finish();
//                try {
//                    CarVehicleDetails2Activity.this.overridePendingTransition(R.anim.fade_out, R.anim.fade_out1);
//                }catch (Exception e){
//                       e.printStackTrace();
//                }
            }
        });

    }

    private List<Map<String, Object>> initData() {
        showProgressDialog(this);
        CarVehicleDetailsRequest request = new CarVehicleDetailsRequest();
        request.userid = userID;
        request.vehicleno = (carnums == null ? "" : carnums);
        final List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        AnsynHttpRequest.request(this, request, ContantValues.VEHICLEDYNAMICS,
                CHTTP.POST, new ObserverCallBack() {
                    @Override
                    public void success(String successMessage) {
                        CarVehicleDetailsBean carVehicleDetailsBean = FastJsonUtils.getPerson(successMessage, CarVehicleDetailsBean.class);
                        if (carVehicleDetailsBean != null && carVehicleDetailsBean.getResults() != null && carVehicleDetailsBean.getResults().getRuntrack() != null) {
                            runtrack = carVehicleDetailsBean.getResults().getRuntrack();
                            setDatainUi();
                        } else{
                            tvCarnum.setText(carnums);
                            tvStartstation.setText("");
                            tvCurrentstation.setText("");
                            tvArrivestation.setText("");
                            tvStarttime.setText("");
                            tvArrivetime.setText("");
                            tvCurrenttime.setText("");
                            dimssDialog();
                            return;
                        }
                        if(list != null){
                            list.clear();
                        }
                        if(carVehicleDetailsBean.getResults().getRuninfo() != null){
                            list.addAll(carVehicleDetailsBean.getResults().getRuninfo());
                        }
                        Map<String, Object> item;
                        Log.e("tag", "" + successMessage);

                        for (int i = 0; i < list.size(); i++) {
                            item = new HashMap<String, Object>();
                            item.put("key", list.get(i).getKey());
                            item.put("value", list.get(i).getValue());
                            data.add(item);
                        }
                        Log.e("YJH", "success-> data---->>"+data.toString() );
                        carVehicleDetailsAdapter.notifyDataSetChanged();
                        dimssDialog();
                    }

                    @Override
                    public void fail(String exceptionMessage) {
                        Toast.makeText(CarVehicleDetails2Activity.this,"数据请求失败!",Toast.LENGTH_SHORT).show();
                        dimssDialog();
                    }

                    @Override
                    public void notNetwork() {
                        Toast.makeText(CarVehicleDetails2Activity.this,"网络异常!",Toast.LENGTH_SHORT).show();
                        dimssDialog();
                    }
                });
        return data;
    }



    private void setDatainUi() {
        tvCarnum.setText(runtrack.getVehicleno());
        tvStartstation.setText(runtrack.getStartstation());
        tvCurrentstation.setText(runtrack.getCurrentstation());
        tvArrivestation.setText(runtrack.getArrivestation());
        tvStarttime.setText(runtrack.getStarttime());
        tvArrivetime.setText(runtrack.getArrivetime());
        tvCurrenttime.setText(runtrack.getCurrenttime());
        if(runtrack.getCurrenttime() != null && runtrack.getArrivetime() != null){
            imgLine1.setImageResource(R.drawable.carvehicle_redline_shape);
            imgLine2.setImageResource(R.drawable.carvehicle_redline_shape);
        }else if(runtrack.getCurrenttime() != null && runtrack.getArrivetime() == null ){
            imgLine1.setImageResource(R.drawable.carvehicle_redline_shape);
            imgLine2.setImageResource(R.drawable.carvehicle_dashed_shape);
        }else{
            imgLine1.setImageResource(R.drawable.carvehicle_dashed_shape);
            imgLine2.setImageResource(R.drawable.carvehicle_dashed_shape);
        }
    }

    @Override
    public void finish() {
        super.finish();
        try {
        CarVehicleDetails2Activity.this.overridePendingTransition(R.anim.fade_out, R.anim.fade_out1);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
