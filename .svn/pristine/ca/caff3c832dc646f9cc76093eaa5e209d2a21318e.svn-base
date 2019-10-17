package com.htmitech.ztcustom.zt.chinarailway;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.adapter.CarNumAdapter;
import com.htmitech.ztcustom.zt.app.ZTCustomInit;
import com.htmitech.ztcustom.zt.base.BaseFragmentActivity;
import com.htmitech.ztcustom.zt.constant.CarNumFuzzyQueryBean;
import com.htmitech.ztcustom.zt.constant.CarNumFuzzyQueryRequest;
import com.htmitech.ztcustom.zt.constant.ContantValues;
import com.htmitech.ztcustom.zt.interfaces.ObserverCallBack;
import com.htmitech.ztcustom.zt.thread.AnsynHttpRequest;
import com.htmitech.ztcustom.zt.unit.CHTTP;
import com.htmitech.ztcustom.zt.util.FastJsonUtils;
import com.minxing.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 车辆追踪首页
 */
public class CarVehicleActivity extends BaseFragmentActivity implements View.OnClickListener {

    public ImageButton imageButtonBack;
    private EditText etSearch;
    private TextView tvSearch;
    private String userID;
    private List<CarNumFuzzyQueryBean.ResultsBean> carNumFuzzyQueryBeanAllList = new ArrayList<CarNumFuzzyQueryBean.ResultsBean>();
    private ListView lvcarNum;
    private String carnum;
    private ImageView imgClear,img_empty_car;
    private String num = "";
    private String vehiclenosize = "1000";
    private String searchNumFormDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.overridePendingTransition(R.anim.no_slide,R.anim.no_slide);
        setContentView(R.layout.activity_car_vehicle);
        searchNumFormDetail = getIntent().getStringExtra("searchNum");
//        userID = ZTCustomInit.get().getmCache().getmListDetails().AccountId;
        userID = ZTCustomInit.get().getmCache().getCisAccountDetail().cisAccountId;
        initView();
        if (searchNumFormDetail != null)
        searchCarNum();
        Log.e("car", "initView: "+carNumFuzzyQueryBeanAllList.size());
        lvcarNum.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(CarVehicleActivity.this,CarVehicleDetailsActivity.class);
                intent.putExtra("carnum",carNumFuzzyQueryBeanAllList.get(i).getVehicleno());
                startActivity(intent);
                finish();
            }
        });

    }


    private void initView() {
        imageButtonBack = (ImageButton) findViewById(R.id.ib_car_vehicle_back);
        etSearch = (EditText) findViewById(R.id.et_search);
        etSearch.setText(searchNumFormDetail);
        tvSearch = (TextView) findViewById(R.id.tv_search);
        lvcarNum = (ListView) findViewById(R.id.lv_car_num);
        imgClear = (ImageView) findViewById(R.id.iv_icon_clear);
        img_empty_car = (ImageView) findViewById(R.id.empty_car);
        imageButtonBack.setOnClickListener(this);
        tvSearch.setOnClickListener(this);
        imgClear.setOnClickListener(this);
        img_empty_car.setVisibility(View.VISIBLE);
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(CarVehicleActivity.this.getCurrentFocus()
                                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    searchCarNum();
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId() ==R.id.ib_car_vehicle_back ){
            this.finish();
        }else if(v.getId() ==R.id.tv_search){
            searchCarNum();
        }else if(v.getId() ==R.id.iv_icon_clear){
            etSearch.setText("");
        }
    }

    private void searchCarNum(){
        carnum = etSearch.getText().toString().trim();
        if(carnum != null && !"".equals(carnum)){
            Log.e("carnum", "initView: "+carnum);
            initData();
        }else{
            Toast.makeText(this,"请输入车号信息",Toast.LENGTH_SHORT).show();
        }
        if(carNumFuzzyQueryBeanAllList != null){
            carNumFuzzyQueryBeanAllList.clear();
            Log.e("wsf", "success: "+carNumFuzzyQueryBeanAllList.size());
        }
    }

    private void initData() {
        showProgressDialog(this);
        final CarNumFuzzyQueryRequest carNumFuzzyQueryRequest = new CarNumFuzzyQueryRequest();
        carNumFuzzyQueryRequest.lastvehicleno = num;
        carNumFuzzyQueryRequest.vehiclenosize = vehiclenosize;
        carNumFuzzyQueryRequest.userid = userID;
        carNumFuzzyQueryRequest.vehicleno = carnum;
        AnsynHttpRequest.request(this, carNumFuzzyQueryRequest, ContantValues.CARNUMFUZZYQUERY,
        CHTTP.POST, new ObserverCallBack() {
            @Override
            public void success(String successMessage) {
                img_empty_car.setVisibility(View.GONE);
                CarNumFuzzyQueryBean carNumFuzzyQueryBean = FastJsonUtils.getPerson(successMessage, CarNumFuzzyQueryBean.class);
                List<CarNumFuzzyQueryBean.ResultsBean> carNumFuzzyQueryBeenList = carNumFuzzyQueryBean.getResults();
                if(carNumFuzzyQueryBeenList != null && carNumFuzzyQueryBeenList.size() != 0){
                    carNumFuzzyQueryBeanAllList.addAll(carNumFuzzyQueryBeenList);
                    CarNumAdapter carNumAdapter = new CarNumAdapter(CarVehicleActivity.this,carNumFuzzyQueryBeanAllList, carnum);
                    lvcarNum.setAdapter(carNumAdapter);
                    //carNumAdapter.notifyDataSetChanged();
                }
                dimssDialog();
            }
            @Override
            public void fail(String exceptionMessage) {
                Toast.makeText(CarVehicleActivity.this,"数据请求失败!",Toast.LENGTH_SHORT).show();
//                if(carNumFuzzyQueryBeanAllList != null){
//                    carNumFuzzyQueryBeanAllList.clear();
//                    Log.e("wsf", "success: "+carNumFuzzyQueryBeanAllList.size());
//                }
                dimssDialog();
            }

            @Override
            public void notNetwork() {
                Toast.makeText(CarVehicleActivity.this,"网络异常!",Toast.LENGTH_SHORT).show();
//                if(carNumFuzzyQueryBeanAllList != null){
//                    carNumFuzzyQueryBeanAllList.clear();
//                    Log.e("wsf", "success: "+carNumFuzzyQueryBeanAllList.size());
//                }
                dimssDialog();
            }
        });
    }


}
