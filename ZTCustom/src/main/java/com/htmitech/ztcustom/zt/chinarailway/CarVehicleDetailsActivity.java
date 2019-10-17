package com.htmitech.ztcustom.zt.chinarailway;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
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

/**
 * 车辆追踪详情
 */
public class CarVehicleDetailsActivity extends BaseFragmentActivity implements View.OnClickListener {

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
    private List<Map<String, Object>> data;
    private SimpleAdapter simpleAdapter;
    private String userID;
    private String carnum = "";
    private CarVehicleDetailsBean.ResultsBean.RuntrackBean runtrack;
    private ArrayList<CarVehicleDetailsBean.ResultsBean.RuninfoBean> list = new ArrayList<CarVehicleDetailsBean.ResultsBean.RuninfoBean>();
    private CarVehicleDetailsAdapter carVehicleDetailsAdapter;
    private EditText etSearch;
    private ImageView ivClear;
    private TextView tvSearch;
    private Animation upAnimation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_vehicle_details);
//        userID = ZTCustomInit.get().getmCache().getmListDetails().AccountId;
        userID =  ZTCustomInit.get().getmCache().getCisAccountDetail().cisAccountId;
        Log.e("YJH", "onCreate->CarVehicleDetailsActivity: "+ userID );
        Intent intent = getIntent();
        String carnum1 = intent.getStringExtra("carnum");
        carnum = carnum1;
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
        etSearch = (EditText) findViewById(R.id.et_search);
        ivClear = (ImageView) findViewById(R.id.iv_icon_clear);
        tvSearch = (TextView) findViewById(R.id.tv_search);
        ivClear.setOnClickListener(this);
        tvSearch.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(CarVehicleDetailsActivity.this.getCurrentFocus()
                                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//                    searchCarNum();
                    Intent intent = new Intent(CarVehicleDetailsActivity.this, CarVehicleActivity.class);
                    intent.putExtra("searchNum", etSearch.getText().toString());
                    startActivity(intent);
                }
                return false;
            }
        });
        data = initData();
       // int resource = R.layout.item_carvehicle_search_detail;
        // from表示数组中存储数据源中的Map中的各个Key
       // String[] from = {"key", "value"};
        // to表示数据源中的Map中的各个Key对应的Value应该显示到哪些控件
       // int[] to = {R.id.tv_carvehicle_numkey, R.id.tv_carvehicle_numvalue};
//        simpleAdapter = new SimpleAdapter(this, data, resource, from, to);
//        lvCarDetail.setAdapter(simpleAdapter);
//        simpleAdapter.notifyDataSetChanged();
          carVehicleDetailsAdapter = new CarVehicleDetailsAdapter(this,data);
          lvCarDetail.setAdapter(carVehicleDetailsAdapter);
          carVehicleDetailsAdapter.notifyDataSetChanged();

    }

    private List<Map<String, Object>> initData() {
        showProgressDialog(this);
        CarVehicleDetailsRequest request = new CarVehicleDetailsRequest();
        request.userid = userID;
        request.vehicleno = carnum;
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
                        tvCarnum.setText(carnum);
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
                    carVehicleDetailsAdapter.notifyDataSetChanged();
                    dimssDialog();
                }

                @Override
                public void fail(String exceptionMessage) {
                    Toast.makeText(CarVehicleDetailsActivity.this,"数据请求失败!",Toast.LENGTH_SHORT).show();
                    dimssDialog();
                }

                @Override
                public void notNetwork() {
                    Toast.makeText(CarVehicleDetailsActivity.this,"网络异常!",Toast.LENGTH_SHORT).show();
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
    public void onClick(View view) {
        if(view.getId() ==R.id.ib_car_vehicle_back ){
            this.finish();
        }else if(view.getId() ==R.id.iv_icon_clear){
            etSearch.setText("");
        }else if(view.getId() ==R.id.tv_search){
            Intent intent = new Intent(CarVehicleDetailsActivity.this, CarVehicleActivity.class);
            intent.putExtra("searchNum", etSearch.getText().toString());
            startActivity(intent);
            finish();
        }
    }

    public void searchCarNum(){
          String carnum2 = etSearch.getText().toString().trim();
          Intent intent = new Intent(CarVehicleDetailsActivity.this, CarVehicleDetails2Activity.class);
          intent.putExtra("carnums",carnum2);
          startActivity(intent);
        try{
          this.overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        }catch (Exception e){
            e.printStackTrace();
        }

//        if(data != null){
//            data.clear();
//            Log.e("wsf", "success: "+data.size());
//        }
//        if(carnum != null && !"".equals(carnum)){
//            Log.e("carnum", "initView: "+carnum);
//            data = initData();
//            carVehicleDetailsAdapter = new CarVehicleDetailsAdapter(this,data);
//            lvCarDetail.setAdapter(carVehicleDetailsAdapter);
//            carVehicleDetailsAdapter.notifyDataSetChanged();
//        }else{
//            Toast.makeText(this,"请输入车号信息",Toast.LENGTH_SHORT).show();
//        }


    }

}