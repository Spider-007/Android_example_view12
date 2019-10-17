package activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.example.schedulemanagerlibrary.R;
import com.htmitech.api.BookInit;
import com.htmitech.myEnum.LogManagerEnum;
import com.htmitech.thread.AnsynHttpRequest;
import com.htmitech.thread.CHTTP;
import com.htmitech.utils.OAConText;
import com.minxing.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;

import Interface.SelectPoisition;
import adapter.SchedulePoiAdapter;
import cn.feng.skin.manager.base.BaseFragmentActivity;
import htmitech.com.componentlibrary.listener.ObserverCallBackType;
import htmitech.com.componentlibrary.unit.ServerUrlConstant;
import schedulebean.ScheduleCpId;
import schedulebean.ScheduleOftenPoiRequestBean;
import schedulebean.ScheduleOftenResultBean;

public class SchedulePoiSearchActivity extends BaseFragmentActivity implements LocationSource, AMapLocationListener,
        AMap.OnCameraChangeListener, PoiSearch.OnPoiSearchListener, View.OnClickListener, ObserverCallBackType {

    MapView mapView;
    EditText etSearch;
    LinearLayout llSearchRoot;
    PullToRefreshListView lvScheduleList;
    TextView tvCurrentValue;
    LinearLayout llCurrentRoot;
    TextView tvOftenLocation;
    TextView tvCurrentKey;
    ImageButton titleLeftButton;
    TextView scheduleTitleNameBack;
    TextView titleName;
    private AMap aMap;
    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private PoiSearch.Query query;// Poi查询条件类
    private PoiSearch poiSearch;
    private ArrayList<PoiItem> poiList = new ArrayList<PoiItem>();
    private String city = "北京";
    public SchedulePoiAdapter mSchedulePoiAdapter;
    public String currentLocation;
    public SelectPoisition mSelectPoisition;
    public ScheduleCpId mScheduleCpId = new ScheduleCpId();
    private String app_id;
    private ScheduleOftenResultBean scheduleOftenResultBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_poi_search);
         mapView = (MapView)findViewById(R.id.mapView);
         etSearch = (EditText)findViewById(R.id.et_search);
         llSearchRoot = (LinearLayout)findViewById(R.id.ll_search_root);
         lvScheduleList = (PullToRefreshListView)findViewById(R.id.lv_schedule_list);
         tvCurrentValue = (TextView)findViewById(R.id.tv_current_value);
         llCurrentRoot = (LinearLayout)findViewById(R.id.ll_current_root);
         tvOftenLocation = (TextView)findViewById(R.id.tv_often_location);
         tvCurrentKey = (TextView)findViewById(R.id.tv_current_key);
         titleLeftButton = (ImageButton)findViewById(R.id.title_left_button);
         scheduleTitleNameBack = (TextView)findViewById(R.id.schedule_title_name_back);
         titleName = (TextView)findViewById(R.id.title_name);
//        getDataFronServer();
        initLocation();
        titleName.setText("定位");
        Intent intent = getIntent();
        app_id = intent.getStringExtra("app_id");
        aMap = mapView.getMap();
        setUpMap();
        aMap.moveCamera(CameraUpdateFactory.zoomTo(18));
        aMap.setOnCameraChangeListener(this);
//        etSearch.addTextChangedListener(this);
        initAdapter();
        getDataFronServer();

//        etSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (null != poiList && poiList.size() > 0)
//                    poiList.clear();
//                mSchedulePoiAdapter.notifyDataSetChanged();
//            }
//        });
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    titleLeftButton.setTag("search");
                    if (null != poiList && poiList.size() > 0)
                        poiList.clear();
                    mSchedulePoiAdapter.notifyDataSetChanged();
                    doSearchQuery(v.getText().toString());
                    return true;
                }
                return false;
            }
        });
        etSearch.setOnClickListener(this);
        titleLeftButton.setOnClickListener(this);
        tvCurrentValue.setOnClickListener(this);

    }

    /*
    * 实时监测位置信息变化
    * */
    private void initLocation() {
        //初始化client
        AMapLocationClient locationClient = new AMapLocationClient(getApplicationContext());
        //设置定位参数
        locationClient.setLocationOption(getDefaultOption());
        // 设置定位监听
        locationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation loc) {

                if (null != loc) {
                    if (loc.getErrorCode() == 0) {
                        currentLocation = loc.getAddress();
//                                ScheduleUtils.currentLocation = loc;
                        mScheduleCpId.cpTitle = loc.getAddress();
                        mScheduleCpId.cpLatitude = loc.getLatitude() + "";
                        mScheduleCpId.cpLongitude = loc.getLongitude() + "";
                        tvCurrentValue.setText(currentLocation == null ? "" : currentLocation);
                    } else {
                        Toast.makeText(SchedulePoiSearchActivity.this, loc.getErrorInfo(), Toast.LENGTH_SHORT).show();
                        Log.e("location", loc.getErrorInfo());
                    }
                } else {
                    Toast.makeText(SchedulePoiSearchActivity.this, "定位失败", Toast.LENGTH_SHORT).show();
                    Log.e("location", "定位失败");
                }
            }
        });
        locationClient.startLocation();

    }

    /*
  * 设置定位参数
  * */
    private AMapLocationClientOption getDefaultOption() {
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(2000);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(true);//可选，设置是否单次定位。默认是false
        return mOption;
    }

    private void initAdapter() {
        mSchedulePoiAdapter = new SchedulePoiAdapter(SchedulePoiSearchActivity.this, poiList);
        lvScheduleList.setAdapter(mSchedulePoiAdapter);
        lvScheduleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PoiItem poiItem = poiList.get((position - 1) >= 0 ? position - 1 : 0);
                mScheduleCpId.cpTitle = poiItem.getTitle();
                mScheduleCpId.cpLatitude = poiItem.getLatLonPoint().getLatitude() + "";
                mScheduleCpId.cpLongitude = poiItem.getLatLonPoint().getLongitude() + "";
                SelectPoisition.getInstances().mSelectPoisitionInterface.OnLocationChange(mScheduleCpId);
                finish();
            }
        });
    }

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        // 自定义系统定位小蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
//        myLocationStyle.myLocationIcon(BitmapDescriptorFactory
//                .fromResource(R.drawable.location_marker));// 设置小蓝点的图标
        myLocationStyle.strokeColor(Color.BLACK);// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(100, 0, 0, 180));// 设置圆形的填充颜色
        // myLocationStyle.anchor(int,int)//设置小蓝点的锚点
        myLocationStyle.strokeWidth(1.0f);// 设置圆形的边框粗细
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        // aMap.setMyLocationType()
    }

    public void doSearchQuery(String keywords) {
        query = new PoiSearch.Query(keywords, "", city);// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query.setPageSize(50);// 设置每页最多返回多少条poiitem
        query.setPageNum(0);// 设置查第一页
        query.setCityLimit(true);

        poiSearch = new PoiSearch(this, query);
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.searchPOIAsyn();
    }

    /*
     * 定位激活与关闭监听
     * */
    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        city = aMapLocation.getCity();
        if (mListener != null && aMapLocation != null) {
            if (aMapLocation != null
                    && aMapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(aMapLocation);// 显示系统小蓝点
            } else {
                String errText = "定位失败," + aMapLocation.getErrorCode() + ": " + aMapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
            }
        }
    }

    /*
      * 获取中心点
      * */
    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {

    }

    /*
    * 搜索后返回的数据
    * */
    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {

        if (null == poiResult) {
            if (null != poiList)
                poiList.clear();
            mSchedulePoiAdapter.setData(poiList);
        }
        poiList = poiResult.getPois();
        mSchedulePoiAdapter.setData(poiList);
        mSchedulePoiAdapter.notifyDataSetChanged();

    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.et_search) {
            llCurrentRoot.setVisibility(View.GONE);
            tvOftenLocation.setVisibility(View.GONE);
            tvCurrentKey.setVisibility(View.GONE);

        } else if (i == R.id.title_left_button) {
            if (null != titleLeftButton && null != titleLeftButton.getTag() && titleLeftButton.getTag().equals("search")) {
                if (null != poiList && poiList.size() > 0)
                    poiList.clear();
                mSchedulePoiAdapter.notifyDataSetChanged();
                getDataFronServer();
                llCurrentRoot.setVisibility(View.VISIBLE);
                tvOftenLocation.setVisibility(View.VISIBLE);
                tvCurrentKey.setVisibility(View.VISIBLE);
                titleLeftButton.setTag("");
                etSearch.setText("");
            } else {
                finish();
            }

        } else if (i == R.id.tv_current_value) {
            tvCurrentValue.setText(currentLocation == null ? "" : currentLocation);
            SelectPoisition.getInstances().mSelectPoisitionInterface.OnLocationChange(mScheduleCpId);
            finish();

        }
    }

    public void getDataFronServer() {
        try {
//            String url = "http://htrf.dscloud.me:8083/data-crab/schplace/getcommonplacelist";
            String url = ServerUrlConstant.SERVER_EMPAPI_URL() + ServerUrlConstant.GET_COMMON_PLACE;
            ScheduleOftenPoiRequestBean mScheduleOftenPoiRequestBean = new ScheduleOftenPoiRequestBean();
            mScheduleOftenPoiRequestBean.appId = app_id;
            mScheduleOftenPoiRequestBean.corpId = BookInit.getInstance().getCorp_id();
            mScheduleOftenPoiRequestBean.groupCorpId = OAConText.getInstance(this).group_corp_id;
            mScheduleOftenPoiRequestBean.pageNum = "1";
            mScheduleOftenPoiRequestBean.pageSize = "10";
            mScheduleOftenPoiRequestBean.userId = OAConText.getInstance(this).UserID;
            AnsynHttpRequest.requestByPostWithToken(this, mScheduleOftenPoiRequestBean, url, CHTTP.POSTWITHTOKEN, this, "oftenPosition", LogManagerEnum.GGENERAL.getFunctionCode());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void success(String requestValue, int type, String requestName) {
        try {
            if (!TextUtils.isEmpty(requestName) && requestName.equals("oftenPosition")) {
                if (!TextUtils.isEmpty(requestValue)) {
                    scheduleOftenResultBean = JSONObject.parseObject(requestValue, ScheduleOftenResultBean.class);
                    for (int i = 0; i < scheduleOftenResultBean.result.list.size(); i++) {
                        if (!TextUtils.isEmpty(scheduleOftenResultBean.result.list.get(i).cpLongitude) && !TextUtils.isEmpty(scheduleOftenResultBean.result.list.get(i).cpLatitude)) {
                            LatLonPoint latLonPoint = new LatLonPoint(Double.parseDouble(scheduleOftenResultBean.result.list.get(i).cpLongitude),
                                    Double.parseDouble(scheduleOftenResultBean.result.list.get(i).cpLatitude));

                            PoiItem poiItem = new PoiItem(scheduleOftenResultBean.result.list.get(i).cpTitle,
                                    latLonPoint, scheduleOftenResultBean.result.list.get(i).cpTitle, scheduleOftenResultBean.result.list.get(i).cpTitle);
                            poiList.add(poiItem);
                        }

                    }
                    mSchedulePoiAdapter.notifyDataSetChanged();
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void fail(String exceptionMessage, int type, String requestName) {
        Toast.makeText(SchedulePoiSearchActivity.this, "数据异常请联系后台管理员！", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void notNetwork() {

    }

    @Override
    public void callbackMainUI(String successMessage) {

    }
}
