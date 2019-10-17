package com.htmitech.ztcustom.zt.chinarailway;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.adapter.MyFragmentPagerAdapter;
import com.htmitech.ztcustom.zt.app.ZTCustomInit;
import com.htmitech.ztcustom.zt.base.BaseFragment;
import com.htmitech.ztcustom.zt.base.BaseFragmentActivity;
import com.htmitech.ztcustom.zt.dialog.ActionSheetDialog;
import com.htmitech.ztcustom.zt.domain.DetectionValue;
import com.htmitech.ztcustom.zt.domain.Dictitemlist;
import com.htmitech.ztcustom.zt.domain.DicttypeResult;
import com.htmitech.ztcustom.zt.domain.GetDefectStatBy;
import com.htmitech.ztcustom.zt.domain.GetDefectStatByList;
import com.htmitech.ztcustom.zt.domain.GetDefectStatByOrg;
import com.htmitech.ztcustom.zt.domain.GetDefectStatSuccess;
import com.htmitech.ztcustom.zt.fragment.DWdefectFragment;
import com.htmitech.ztcustom.zt.fragment.DetectionFragment;
import com.htmitech.ztcustom.zt.fragment.XLdefectFragment;
import com.htmitech.ztcustom.zt.interfaces.CallBackDefectStatOrg;
import com.htmitech.ztcustom.zt.interfaces.CallBackDetectionOnClick;
import com.htmitech.ztcustom.zt.interfaces.CallBackScreening;
import com.htmitech.ztcustom.zt.interfaces.ObserverCallBack;
import com.htmitech.ztcustom.zt.pop.FunctionPopupWindow;
import com.htmitech.ztcustom.zt.thread.AnsynHttpRequest;
import com.htmitech.ztcustom.zt.unit.CHTTP;
import com.htmitech.ztcustom.zt.unit.ShareUnit;
import com.htmitech.ztcustom.zt.util.DensityUtil;
import com.htmitech.ztcustom.zt.util.FastJsonUtils;
import com.htmitech.ztcustom.zt.util.ZTActivityUnit;
import com.htmitech.ztcustom.zt.view.CircleChartView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import htmitech.com.componentlibrary.unit.ToastUtil;

/**
 *
 *
 * 探伤监控  主界面
 *
 * 现存重伤分时统计
 * Tony on 2016/9/27.
 */
public class DefectStatByActivity extends BaseFragmentActivity implements CallBackDetectionOnClick,View.OnClickListener,CallBackScreening,CallBackDefectStatOrg {

    private CircleChartView chartView;

    private LinearLayout layout_name_value;

    private List<DetectionValue> numList;

    private LayoutInflater inflater;


    private TextView tv_number;

    private LinearLayout ll_popupLayout;

    private TextView iv_shaixuan;

    private ImageView function;

    public FunctionPopupWindow menuWindow;

    public GetDefectStatByOrg mGetDefectStatByOrg;

    public GetDefectStatBy mGetDefectStatBy;


    public ImageButton ibn_fn5_back;


    public TextView tv_orgname;

    private ViewPager mPager;

    private ArrayList<BaseFragment> fragmentList;

    private int ViewPagerNumber = 2;  //滑动个数
    private int currentIndex;
    private TextView tv_dw,tv_xl;
    private TextView image;
    private String codeTime = "";
    /**
     * 屏幕的宽度
     */
    private int screenWidth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zt_activity_tanshang);
        initView();
        init();
        initData();
        initTabLineWidth();
        setViewPager();
    }

    public void initView(){
        ibn_fn5_back = (ImageButton) findViewById(R.id.ibn_fn5_back);
        chartView = (CircleChartView) findViewById(R.id.circle_view);
        layout_name_value  = (LinearLayout) findViewById(R.id.layout_name_value);
        ll_popupLayout = (LinearLayout) findViewById(R.id.ll_popupLayout);
        inflater = LayoutInflater.from(this);
        tv_number = (TextView) findViewById(R.id.tv_number);
        iv_shaixuan = (TextView) findViewById(R.id.iv_shaixuan);
        function = (ImageView) findViewById(R.id.function);
        tv_orgname = (TextView) findViewById(R.id.tv_orgname);
        tv_dw = (TextView) findViewById(R.id.tv_dw);
        tv_xl = (TextView) findViewById(R.id.tv_xl);
        mPager = (ViewPager) findViewById(R.id.viewpager);
        image = (TextView) findViewById(R.id.cursor);
    }

    public void initData(){

        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels; // 屏幕宽度（像素）
        int height = metric.heightPixels; // 屏幕高度（像素）
        float density = metric.density;
        int densityDpi = metric.densityDpi;
        Log.d("initData","width --->"+width+"  height -- > "+height+"  density -- > "+ density+"  densityDpi --->"+densityDpi);
        ibn_fn5_back.setOnClickListener(this);
        menuWindow = new FunctionPopupWindow(this, this);
        iv_shaixuan.setOnClickListener(this);
        function.setOnClickListener(this);
        ll_popupLayout.getBackground().setAlpha(220);
        DetectionFragment mDetectionFragment = new DetectionFragment();
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        transaction.add(R.id.content, mDetectionFragment);
        transaction.commit();
    }

    /**
     * 设置滑动条的宽度为屏幕的1/3(根据Tab的个数而定)
     */
    private void initTabLineWidth() {
        DisplayMetrics dpMetrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay()
                .getMetrics(dpMetrics);
        screenWidth = DensityUtil.dip2px(this,160);
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) image
                .getLayoutParams();
        lp.width = screenWidth / ViewPagerNumber;
        image.setLayoutParams(lp);
    }

    /**
     * 重置颜色
     */
    private void resetTextView() {
        tv_dw.setTextColor(Color.WHITE);
        tv_xl.setTextColor(Color.WHITE);
    }
    DWdefectFragment mDWdefectFragment;
    XLdefectFragment mXLdefectFragment;
    public void setViewPager(){
        // 给ViewPager设置适配器
        tv_dw.setOnClickListener(this);
        tv_xl.setOnClickListener(this);
        fragmentList = new ArrayList<BaseFragment>();
        mDWdefectFragment = new DWdefectFragment();
        mDWdefectFragment.setmGetDefectStatByOrg(mGetDefectStatByOrg);

        mXLdefectFragment = new XLdefectFragment();
        mXLdefectFragment.setmGetDefectStatByOrg(mGetDefectStatByOrg);



        fragmentList.add(mDWdefectFragment);
        fragmentList.add(mXLdefectFragment);

        mPager.setAdapter(new MyFragmentPagerAdapter(
                getSupportFragmentManager(), fragmentList));
        mPager.setCurrentItem(0);// 设置当前显示标签页为第一页
        mPager.setOffscreenPageLimit(ViewPagerNumber);
        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            /**
             * state滑动中的状态 有三种状态（0，1，2） 1：正在滑动 2：滑动完毕 0：什么都没做。
             */
            @Override
            public void onPageScrollStateChanged(int state) {

            }

            /**
             * position :当前页面，及你点击滑动的页面 offset:当前页面偏移的百分比
             * offsetPixels:当前页面偏移的像素位置
             */
            @Override
            public void onPageScrolled(int position, float offset,
                                       int offsetPixels) {
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) image
                        .getLayoutParams();
                /**
                 * 利用currentIndex(当前所在页面)和position(下一个页面)以及offset来
                 * 设置mTabLineIv的左边距 滑动场景： 记3个页面, 从左到右分别为0,1,2 0->1; 1->2; 2->1;
                 * 1->0
                 */

                if (currentIndex == 0 && position == 0)// 0->1
                {
                    lp.leftMargin = (int) (offset * (screenWidth * 1.0 / ViewPagerNumber) + currentIndex
                            * (screenWidth / ViewPagerNumber));

                } else if (currentIndex == 1 && position == 0) // 1->0
                {
                    lp.leftMargin = (int) (-(1 - offset)
                            * (screenWidth * 1.0 / ViewPagerNumber) + currentIndex
                            * (screenWidth / ViewPagerNumber));

                } else if (currentIndex == 1 && position == 1) // 1->2
                {
                    lp.leftMargin = (int) (offset * (screenWidth * 1.0 / ViewPagerNumber) + currentIndex
                            * (screenWidth / ViewPagerNumber));
                } else if (currentIndex == 2 && position == 1) // 2->1
                {
                    lp.leftMargin = (int) (-(1 - offset)
                            * (screenWidth * 1.0 / ViewPagerNumber) + currentIndex
                            * (screenWidth / ViewPagerNumber));
                }
                image.setLayoutParams(lp);
            }

            @Override
            public void onPageSelected(int position) {
                resetTextView();

                switch (position) {

                    case 0:
                        tv_dw.setTextColor(getResources().getColor(R.color.white));
                        break;
                    case 1:
                        tv_xl.setTextColor(getResources().getColor(R.color.white));
                        break;
                }

                currentIndex = position;
            }
        });// 页面变化时的监听器
    }

    /**
     * 初始化数组重新刷新扇形图
     */
    private void init() {
        numList = new ArrayList<DetectionValue>();
        mGetDefectStatBy = new GetDefectStatBy();

        DicttypeResult mDicttypeResult_SSCD = ZTCustomInit.get().getmCache().getmDicttypeResult().get("SSCD");
        DicttypeResult mDicttypeResult_SBLX = ZTCustomInit.get().getmCache().getmDicttypeResult().get("SBLX");
        DicttypeResult mDicttypeResult_CZZT = ZTCustomInit.get().getmCache().getmDicttypeResult().get("CZZT");

        mGetDefectStatBy.userid = "123";
        mGetDefectStatBy.org_id = "20";
        mGetDefectStatBy.org_type = "tz";
        mGetDefectStatBy.defect_type = "1";
//        for(Dictitemlist mDictitemlist : mDicttypeResult_SSCD.getDictitemlist()){
            mGetDefectStatBy.sscd_list.add("ZS");
//        }

        for(Dictitemlist mDictitemlist : mDicttypeResult_SBLX.getDictitemlist()){
            mGetDefectStatBy.sblx_list.add(mDictitemlist.getCode());
        }

        for(Dictitemlist mDictitemlist : mDicttypeResult_CZZT.getDictitemlist()){
            mGetDefectStatBy.czzt_list.add(mDictitemlist.getCode());
        }
        ZTCustomInit.get().getmCache().setSscdList(mGetDefectStatBy.sscd_list);
        ZTCustomInit.get().getmCache().setSblxList(mGetDefectStatBy.sblx_list);
        ZTCustomInit.get().getmCache().setCzztList(mGetDefectStatBy.czzt_list);
        mGetDefectStatBy.stat_type = "ORG";
        requestGetDefectStatFan();

        mGetDefectStatByOrg = new GetDefectStatByOrg();
        mGetDefectStatByOrg.userid = "123";
        mGetDefectStatByOrg.org_id = "20";
        mGetDefectStatByOrg.org_type = "tz";
        mGetDefectStatByOrg.defect_type = "1";
        mGetDefectStatByOrg.sscd_list.add("ZS");

        for(Dictitemlist mDictitemlist : mDicttypeResult_SBLX.getDictitemlist()){
            mGetDefectStatByOrg.sblx_list.add(mDictitemlist.getCode());
        }

        for(Dictitemlist mDictitemlist : mDicttypeResult_CZZT.getDictitemlist()){
            mGetDefectStatByOrg.czzt_list.add(mDictitemlist.getCode());
        }

        mGetDefectStatByOrg.stat_type = "ORG";
        mGetDefectStatByOrg.end_stat_date ="";
        mGetDefectStatByOrg.begin_stat_date = "";
        upbegin_stat_date = "";
        upend_stat_date = "";

        orgTypeList.add(mGetDefectStatBy.org_type);
        cacheGetDefectStatBy.put(mGetDefectStatBy.org_type, copyDetDefectStatBy(mGetDefectStatBy));
        cacheGetDefectStatByOrg.put(mGetDefectStatByOrg.org_type,copyGetDefectStatByOrg(mGetDefectStatByOrg));
    }

    public GetDefectStatBy copyDetDefectStatBy(GetDefectStatBy mGetDefectStatBy){
        GetDefectStatBy tempGetDefectStatBy = new GetDefectStatBy();
        tempGetDefectStatBy.org_type = mGetDefectStatBy.org_type;
        tempGetDefectStatBy.userid = mGetDefectStatBy.userid;
        tempGetDefectStatBy.org_id = mGetDefectStatBy.org_id;
        tempGetDefectStatBy.czzt_list = mGetDefectStatBy.czzt_list;
        tempGetDefectStatBy.defect_type = mGetDefectStatBy.defect_type;
        tempGetDefectStatBy.sblx_list = mGetDefectStatBy.sblx_list;
        tempGetDefectStatBy.sscd_list = mGetDefectStatBy.sscd_list;
        tempGetDefectStatBy.stat_type = mGetDefectStatBy.stat_type;
        return tempGetDefectStatBy;
    }

    public GetDefectStatByOrg copyGetDefectStatByOrg(GetDefectStatByOrg mGetDefectStatByOrg){
        if(codeTime == null){
            codeTime = "";
        }
        GetDefectStatByOrg tempGetDefectStatByOrg = new GetDefectStatByOrg();
        tempGetDefectStatByOrg.begin_stat_date = codeTime.equals("") ? "": mGetDefectStatByOrg.begin_stat_date;
        tempGetDefectStatByOrg.org_type = mGetDefectStatByOrg.org_type;
        tempGetDefectStatByOrg.end_stat_date = codeTime.equals("") ? "": mGetDefectStatByOrg.end_stat_date;
        tempGetDefectStatByOrg.org_id = mGetDefectStatByOrg.org_id;
        tempGetDefectStatByOrg.czzt_list = mGetDefectStatByOrg.czzt_list;
        tempGetDefectStatByOrg.defect_type = mGetDefectStatByOrg.defect_type;
        tempGetDefectStatByOrg.sblx_list = mGetDefectStatByOrg.sblx_list;
        tempGetDefectStatByOrg.sscd_list = mGetDefectStatByOrg.sscd_list;
        tempGetDefectStatByOrg.stat_type = mGetDefectStatByOrg.stat_type;
        tempGetDefectStatByOrg.userid = mGetDefectStatByOrg.userid;
        return tempGetDefectStatByOrg;
    }

    private void requestAll(){
        requestGetDefectStatFan();
        mDWdefectFragment.requestGetDefectStat(mGetDefectStatByOrg,code);
        mXLdefectFragment.requestGetDefectStat(mGetDefectStatByOrg,code);
    }

    private void requestGetDefectStatFan() {
        showProgressDialog(this);
        AnsynHttpRequest.request(this, mGetDefectStatBy, CHTTP.GETDEFECTSTATBYTIME,
                CHTTP.POST, new ObserverCallBack() {

                    @Override
                    public void success(String successMessage) {
                        // TODO Auto-generated method stub
                        GetDefectStatSuccess mGetDefectStatSuccess = FastJsonUtils
                                .getPerson(successMessage, GetDefectStatSuccess.class);
                        numList.clear();
                        if (mGetDefectStatSuccess.success) {
                            for (GetDefectStatByList mGetDefectStatByList : mGetDefectStatSuccess.time_value_list) {
                                numList.add(new DetectionValue(mGetDefectStatByList.color, mGetDefectStatByList.value, mGetDefectStatByList.name, mGetDefectStatByList.code,mGetDefectStatByList.begin_stat_date,mGetDefectStatByList.end_stat_date));
                            }
                        }
                        tv_orgname.setText(mGetDefectStatSuccess.org_name);
                        initDetectionLeft();
                        chartView.setCallBackDetectionOnClick(DefectStatByActivity.this);
                        chartView.start();
                        chartView.setNumbers(numList);
                        chartView.setOntouch(codeTime);
                        dimssDialog();
                    }

                    @Override
                    public void notNetwork() {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void fail(String exceptionMessage) {
                        // TODO Auto-generated method stub
                        ToastUtil.showShort(DefectStatByActivity.this,"暂无数据");
//                        ToastInfo toast = ToastInfo
//                                .getInstance();
//                        toast.setText("暂无数据");
//                        toast.show(Toast.LENGTH_SHORT);
                        dimssDialog();
                    }
                });
    }


    /**
     * 回调刷新
     */
    private void initDetectionLeft(){
        layout_name_value.removeAllViews();
        for(DetectionValue mDetectionValue : numList){
            View v = inflater.inflate(R.layout.zt_activity_zsjk_value,null);
            v.setTag(mDetectionValue.code+"");
            TextView tv_tanshang_value = (TextView) v.findViewById(R.id.tv_tanshang_value);

            TextView tv_tanshang_color = (TextView) v.findViewById(R.id.tv_tanshang_color);

            tv_tanshang_color.setBackgroundColor(Color.parseColor("#"+mDetectionValue.color));



            tv_tanshang_value.setText(mDetectionValue.name + "：" + ((int) mDetectionValue.value));
            v.setOnClickListener(new LeftDetecotionOnclick(mDetectionValue));
            layout_name_value.addView(v);
        }
    }



    /**
     * 回调点击刷新
     * 点击扇形
     * @param flag
     */
    private String upbegin_stat_date = "";
    private String upend_stat_date = "";
    private String code = "";
    @Override
    public void onClick(boolean flag, DetectionValue mDetectionValue) {
        code = flag ? mDetectionValue.code :"";
        codeTime = flag ? mDetectionValue.code :"";

        mGetDefectStatByOrg.begin_stat_date = flag ?  mDetectionValue.begin_stat_date : "";
        mGetDefectStatByOrg.end_stat_date = flag ? mDetectionValue.end_stat_date : "";
        mDWdefectFragment.requestGetDefectStat(mGetDefectStatByOrg,code);
        mXLdefectFragment.requestGetDefectStat(mGetDefectStatByOrg,code);

        cacheGetDefectStatByOrg.get(mGetDefectStatByOrg.org_type).begin_stat_date = mGetDefectStatByOrg.begin_stat_date;
        cacheGetDefectStatByOrg.get(mGetDefectStatByOrg.org_type).end_stat_date = mGetDefectStatByOrg.end_stat_date;
    }

    /**
     * 回调总计
     * @param total
     */
    @Override
    public void onTotal(double total) {
        tv_number.setText(""+(int)total);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() ==R.id.iv_shaixuan ){
            ll_popupLayout.setAnimation(AnimationUtils.makeInAnimation(this,
                    false));
            ll_popupLayout.setVisibility(View.VISIBLE);

            if (menuWindow.isShowing()) {
                function.setBackgroundResource(R.drawable.zt_htmitech_shezhi_1);
                menuWindow.dismiss();
            }
        }else if(v.getId() ==R.id.function){
            if (!menuWindow.isShowing()) {
                menuWindow = new FunctionPopupWindow(this, this);
                menuWindow.setLayoutTLVisibility();
                function.setBackgroundResource(R.drawable.zt_htmitech_shezhi);
                int popupWidth = menuWindow.mMenuView.getMeasuredWidth();
                int popupHeight = menuWindow.mMenuView.getMeasuredHeight();

                int[] location = new int[2];
                function.getLocationOnScreen(location);
                // 显示窗口
                menuWindow.showAtLocation(function, Gravity.NO_GRAVITY,
                        (location[0] + function.getWidth() / 2) - popupWidth
                                / 2, location[1] - popupHeight); // 设置layout在PopupWindow中显示的位置
                menuWindow.update();
            } else {
                function.setBackgroundResource(R.drawable.zt_htmitech_shezhi_1);
                menuWindow.dismiss();
            }
        }else if(v.getId() ==R.id.iv_tl){
            ArrayList<String> itemName = new ArrayList<String>();
            ArrayList<String> itemColor = new ArrayList<String>();

            for(DetectionValue mDetectionValue : numList){
                itemName.add(mDetectionValue.name+"："+(int)mDetectionValue.value);
                itemColor.add(mDetectionValue.color);
            }

            new ActionSheetDialog(this)
                    .builder()
                    .setTitle("查看图例详情")
                    .setCancelable(false)
                    .setCanceledOnTouchOutside(false)
                    .addSheetItem(itemName,itemColor)
                    .setOnSheetItemClickListener(
                            new ActionSheetDialog.OnSheetItemClickListener() {

                                @Override
                                public void onClick(String code) {
                                    if(code == null || code.equals("")){
                                        Toast.makeText(DefectStatByActivity.this,"请选图例详情！", Toast.LENGTH_SHORT)
                                                .show();
                                        return;
                                    }else{
                                        try{

                                            DetectionValue mDetectionValue = numList.get(Integer.parseInt(code) - 1);
                                            chartView.setOntouch(mDetectionValue.code);

                                            boolean flag = chartView.getCircleShow();

                                            mGetDefectStatByOrg.begin_stat_date = flag ?  mDetectionValue.begin_stat_date : upbegin_stat_date;
                                            mGetDefectStatByOrg.end_stat_date = flag ? mDetectionValue.end_stat_date : upend_stat_date;
                                            mDWdefectFragment.requestGetDefectStat(mGetDefectStatByOrg,code);
                                            mXLdefectFragment.requestGetDefectStat(mGetDefectStatByOrg,code);
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }).show();
        }else if(v.getId() ==R.id.iv_fun_detail_data){
            GetDefectStatByOrg mGetDefectStatByOrgs = copyGetDefectStatByOrg(this.mGetDefectStatByOrg);
            int currentItem = mPager.getCurrentItem();
            mGetDefectStatByOrgs.stat_type = (currentItem == 0) ? "ORG" : "LINE";
            HashMap<String,Object> map = new HashMap<String,Object>();
            map.put("mGetDefectStatByOrg",mGetDefectStatByOrgs);


            ZTActivityUnit.switchTo(this, DefectStatByDataActivity.class,
                    map);
        }else if(v.getId() ==R.id.iv_share){
            String shareString = "".toString();
//                shareString = shareString.substring(0, shareString.length() - 1);
            ShareUnit
                    .ShareListener(
                            this,
                            "分享"  + "详细数据",
                            "http://114.112.89.94:8081/ZTCloudAPI/MetroImage/sharereport.png",
                            shareString, "BB");

        }else if(v.getId() ==R.id.ibn_fn5_back){
            if(orgTypeList.size() > 1){
                chartView.stop();
                String orgType = orgTypeList.get(orgTypeList.size() - 2);
                String orgTypeLast = orgTypeList.get(orgTypeList.size() - 1);
                mGetDefectStatBy = copyDetDefectStatBy(cacheGetDefectStatBy.get(orgType));
                mGetDefectStatByOrg = copyGetDefectStatByOrg(cacheGetDefectStatByOrg.get(orgType));
                requestAll();
                orgTypeList.remove(orgTypeLast);
                cacheGetDefectStatBy.remove(orgTypeLast);
                cacheGetDefectStatByOrg.remove(orgTypeLast);
            }else{
                finish();
            }

        }else if(v.getId() ==R.id.tv_dw){
            mPager.setCurrentItem(0);
        }else if(v.getId() ==R.id.tv_xl){
            mPager.setCurrentItem(1);
        }
    }

    @Override
    public void screeningValue(ArrayList<String> sscdList, ArrayList<String> sblxList,ArrayList<String> czztList, String pmfs, String pmsl) {
        ll_popupLayout
                .setAnimation(AnimationUtils.makeOutAnimation(this, true));
        ll_popupLayout.setVisibility(View.GONE);
        chartView.stop();
        mGetDefectStatBy.sscd_list.clear();
        mGetDefectStatBy.sscd_list.addAll(sscdList);
        mGetDefectStatBy.sblx_list.clear();
        mGetDefectStatBy.sblx_list.addAll(sblxList);
        mGetDefectStatBy.czzt_list.clear();
        mGetDefectStatBy.czzt_list.addAll(czztList);
        requestGetDefectStatFan();


        mGetDefectStatByOrg.sscd_list.clear();
        mGetDefectStatByOrg.sscd_list.addAll(sscdList);
        mGetDefectStatByOrg.sblx_list.clear();
        mGetDefectStatByOrg.sblx_list.addAll(sblxList);
        mGetDefectStatByOrg.czzt_list.clear();
        mGetDefectStatByOrg.czzt_list.addAll(czztList);
        mDWdefectFragment.requestGetDefectStat(mGetDefectStatByOrg,code);
        mXLdefectFragment.requestGetDefectStat(mGetDefectStatByOrg,code);
        ZTCustomInit.get().getmCache().setSscdList(mGetDefectStatBy.sscd_list);
        ZTCustomInit.get().getmCache().setSblxList(mGetDefectStatBy.sblx_list);
        ZTCustomInit.get().getmCache().setCzztList(mGetDefectStatBy.sblx_list);

    }

    @Override
    public void clean() {
        ll_popupLayout
                .setAnimation(AnimationUtils.makeOutAnimation(this, true));
        ll_popupLayout.setVisibility(View.GONE);
    }
    private HashMap<String,GetDefectStatBy> cacheGetDefectStatBy = new HashMap<String,GetDefectStatBy>();

    private HashMap<String,GetDefectStatByOrg> cacheGetDefectStatByOrg = new HashMap<String,GetDefectStatByOrg>();

    public ArrayList<String> orgTypeList = new ArrayList<String>();


    @Override
    public void callBackDefect(String orgId, String org_type, String codeTime) {
        if(org_type.equalsIgnoreCase("XL"))
            return;
        this.codeTime = codeTime;
        mGetDefectStatBy.org_id = orgId;
        mGetDefectStatBy.org_type = org_type;
        chartView.stop();
        orgTypeList.add(org_type);
        upbegin_stat_date = mGetDefectStatByOrg.begin_stat_date;
        upend_stat_date = mGetDefectStatByOrg.end_stat_date;

        cacheGetDefectStatBy.put(org_type, copyDetDefectStatBy(mGetDefectStatBy));
        requestGetDefectStatFan();

        mGetDefectStatByOrg.org_id = orgId;
        mGetDefectStatByOrg.org_type = org_type;

        cacheGetDefectStatByOrg.put(org_type, copyGetDefectStatByOrg(mGetDefectStatByOrg));
        mDWdefectFragment.requestGetDefectStat(mGetDefectStatByOrg,code);
        mXLdefectFragment.requestGetDefectStat(mGetDefectStatByOrg,code);
    }



    public class LeftDetecotionOnclick implements View.OnClickListener{
        private DetectionValue mDetectionValue;
        public LeftDetecotionOnclick(DetectionValue mDetectionValue){
            this.mDetectionValue = mDetectionValue;
        }
        @Override
        public void onClick(View v) {
            chartView.setOntouch(mDetectionValue.code);

            int count = layout_name_value.getChildCount();
//            for(int i = 0;i < count ;i ++){
//                View childView = layout_name_value.getChildAt(i);
//                if(childView.getTag().toString().equals(mDetectionValue.id+"")){
//
//
//                    TextView tv_tanshang_color = (TextView) v.findViewById(R.id.tv_tanshang_color);
//                    LinearLayout.LayoutParams para = (LinearLayout.LayoutParams) tv_tanshang_color.getLayoutParams();
//
//                    para.width = chartView.getCircleShow() ? DensityUtil.dip2px(TestDemoActivity.this,22) : DensityUtil.dip2px(TestDemoActivity.this,18);
//                    para.height = chartView.getCircleShow() ? DensityUtil.dip2px(TestDemoActivity.this,14) : DensityUtil.dip2px(TestDemoActivity.this,10);
//                    tv_tanshang_color.setLayoutParams(para);
//
//                }else{
//
//                    TextView tv_tanshang_color = (TextView) childView.findViewById(R.id.tv_tanshang_color);
//
//                    LinearLayout.LayoutParams para = (LinearLayout.LayoutParams) tv_tanshang_color.getLayoutParams();
//
//                    para.width = DensityUtil.dip2px(TestDemoActivity.this,18);
//                    para.height = DensityUtil.dip2px(TestDemoActivity.this,10);
//                    tv_tanshang_color.setLayoutParams(para);
//                }
//            }

        }
    }


}
