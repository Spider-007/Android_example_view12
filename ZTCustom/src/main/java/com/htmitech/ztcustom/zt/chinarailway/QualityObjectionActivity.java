package com.htmitech.ztcustom.zt.chinarailway;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.adapter.QualityObjectionFragmentPagerAdapter;
import com.htmitech.ztcustom.zt.app.ZTCustomInit;
import com.htmitech.ztcustom.zt.base.BaseFragment;
import com.htmitech.ztcustom.zt.base.BaseFragmentActivity;
import com.htmitech.ztcustom.zt.constant.ContantValues;
import com.htmitech.ztcustom.zt.constant.GetUserDetailInformationRequest;
import com.htmitech.ztcustom.zt.constant.GetUserDetailInformationResult;
import com.htmitech.ztcustom.zt.domain.GetTabCountRequest;
import com.htmitech.ztcustom.zt.domain.GetTabCountReslutList;
import com.htmitech.ztcustom.zt.domain.GetTabCountResult;
import com.htmitech.ztcustom.zt.fragment.qualityobjectionfragment.QualityObjectionCLJGFragment;
import com.htmitech.ztcustom.zt.fragment.qualityobjectionfragment.QualityObjectionCLQKFragment;
import com.htmitech.ztcustom.zt.fragment.qualityobjectionfragment.QualityObjectionDWJFragment;
import com.htmitech.ztcustom.zt.fragment.qualityobjectionfragment.QualityObjectionFGSSLFragment;
import com.htmitech.ztcustom.zt.fragment.qualityobjectionfragment.QualityObjectionFXYYFragment;
import com.htmitech.ztcustom.zt.fragment.qualityobjectionfragment.QualityObjectionGCSLFragment;
import com.htmitech.ztcustom.zt.fragment.qualityobjectionfragment.QualityObjectionTBZSFragment;
import com.htmitech.ztcustom.zt.fragment.qualityobjectionfragment.QualityObjectionYWJFragment;
import com.htmitech.ztcustom.zt.interfaces.ObserverCallBack;
import com.htmitech.ztcustom.zt.interfaces.OnQualityObjectionRefreshDataCallBack;
import com.htmitech.ztcustom.zt.thread.AnsynHttpRequest;
import com.htmitech.ztcustom.zt.unit.CHTTP;
import com.htmitech.ztcustom.zt.util.FastJsonUtils;
import com.htmitech.ztcustom.zt.view.MainViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * 质量异议
 */
public class QualityObjectionActivity extends BaseFragmentActivity implements View.OnClickListener, OnQualityObjectionRefreshDataCallBack {

    private MainViewPager viewPager;
    private RelativeLayout rltibaozongshu;
    private RelativeLayout rlfengongsishouli;
    private RelativeLayout rlgangchangshouli;
    private RelativeLayout rlchuliqingkuang;
    private RelativeLayout rlfenxiyuanyin;
    private RelativeLayout rlchulijieguo;
    private RelativeLayout rldaiwanjie;
    private RelativeLayout rlyiwanjie;
    private QualityObjectionFragmentPagerAdapter fragmentPagerAdapter;
    private List<BaseFragment> fragmentList;
    private ImageButton imageButtonBack;
    private LinearLayout linearLayoutTopItem;
    private Button topRightButton;
    private String topRightType = "";
    private GetUserDetailInformationRequest getUserDetailInformationRequest;
    private GetUserDetailInformationResult getUserDetailInformationResult;
    private GetTabCountRequest getTabCountRequest;
    private GetTabCountResult getTabCountResult;
    private String type = "";
    private String orgid = "";
    private String orgName = "";
    private String username = "";
    private String currentYqbm = "tbzs";
    private String currentYqbmName = "提报总数";
    private String currentState = "";
    private final static int REQUESTCODE = 1; // 返回的结果码
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quality_objection);
        initView();
        initData();
        initControl();
    }

    private void initView() {
        viewPager = (MainViewPager) findViewById(R.id.viewpager_quality_objection);
        viewPager.setNoScroll(true);
        viewPager.setOffscreenPageLimit(7);
        imageButtonBack = (ImageButton) findViewById(R.id.ib_quality_objection_back);
        imageButtonBack.setOnClickListener(this);
        rltibaozongshu = (RelativeLayout) findViewById(R.id.rl_quality_objection_tibaozongshu);
        rltibaozongshu.setOnClickListener(this);
        rlfengongsishouli = (RelativeLayout) findViewById(R.id.rl_quality_objection_fengongsishouli);
        rlfengongsishouli.setOnClickListener(this);
        rlgangchangshouli = (RelativeLayout) findViewById(R.id.rl_quality_objection_gangchangshouli);
        rlgangchangshouli.setOnClickListener(this);
        rlchuliqingkuang = (RelativeLayout) findViewById(R.id.rl_quality_objection_chuliqingkuang);
        rlchuliqingkuang.setOnClickListener(this);
        rlfenxiyuanyin = (RelativeLayout) findViewById(R.id.rl_quality_objection_fenxiyuanyin);
        rlfenxiyuanyin.setOnClickListener(this);
        rlchulijieguo = (RelativeLayout) findViewById(R.id.rl_quality_objection_chulijieguo);
        rlchulijieguo.setOnClickListener(this);
        rldaiwanjie = (RelativeLayout) findViewById(R.id.rl_quality_objection_daiwanjie);
        rldaiwanjie.setOnClickListener(this);
        rlyiwanjie = (RelativeLayout) findViewById(R.id.rl_quality_objection_yiwanjie);
        rlyiwanjie.setOnClickListener(this);
        linearLayoutTopItem = (LinearLayout) findViewById(R.id.ll_quality_objection_top_item);
        topRightButton = (Button) findViewById(R.id.bt_quality_objection_right_top);
        topRightButton.setOnClickListener(this);
        setTopGone();
        rltibaozongshu.getChildAt(1).setVisibility(View.VISIBLE);
    }

    private void initData() {
        getUserDetailInformationRequest = new GetUserDetailInformationRequest();
         //    <--------------------Administrator -> 2019-8-16:16:37:ZTCustomInit.get().getmCache().getmListDetails().AccountId--------------------->
        getUserDetailInformationRequest.userid = ZTCustomInit.get().getmCache().getCisAccountDetail().cisAccountId;
        getTabCountRequest = new GetTabCountRequest();
        getTabCountRequest.userid =  ZTCustomInit.get().getmCache().getCisAccountDetail().cisAccountId;
        getUserDeatil();
        getTabCount();
        //将数据与ViewPager通过适配器连接在一起
        fragmentList = new ArrayList<BaseFragment>();
        QualityObjectionTBZSFragment tbzsFragment = new QualityObjectionTBZSFragment();
        QualityObjectionFGSSLFragment fgsslFragment = new QualityObjectionFGSSLFragment();
        QualityObjectionGCSLFragment gcslFragment = new QualityObjectionGCSLFragment();
        QualityObjectionCLQKFragment clqkFragment = new QualityObjectionCLQKFragment();
        QualityObjectionFXYYFragment fxyyFragment = new QualityObjectionFXYYFragment();
        QualityObjectionCLJGFragment cljgFragment = new QualityObjectionCLJGFragment();
        QualityObjectionDWJFragment dwjFragment = new QualityObjectionDWJFragment();
        QualityObjectionYWJFragment ywjFragment = new QualityObjectionYWJFragment();
        fragmentList.add(tbzsFragment);
        fragmentList.add(fgsslFragment);
        fragmentList.add(gcslFragment);
        fragmentList.add(clqkFragment);
        fragmentList.add(fxyyFragment);
        fragmentList.add(cljgFragment);
        fragmentList.add(dwjFragment);
        fragmentList.add(ywjFragment);
        fragmentPagerAdapter = new QualityObjectionFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(fragmentPagerAdapter);
    }


    private void initControl() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                QualityObjectionActivity.this.position = position;
                setTopGone();
                ((RelativeLayout) linearLayoutTopItem.getChildAt(position)).getChildAt(1).setVisibility(View.VISIBLE);
                if (position == 0) {
                    currentYqbm = "tbzs";
                    currentYqbmName = "提报总数";
                } else if (position == 1) {
                    currentYqbm = "fgssl";
                    currentYqbmName = "分公司受理";
                    currentState = "2";
                } else if (position == 2) {
                    currentYqbm = "gcsl";
                    currentYqbmName = "钢厂受理";
                    currentState = "3";
                } else if (position == 3) {
                    currentYqbm = "clqk";
                    currentYqbmName = "处理情况";
                    currentState = "4";
                } else if (position == 4) {
                    currentYqbm = "fxyy";
                    currentYqbmName = "分析原因";
                    currentState = "5";
                } else if (position == 5) {
                    currentYqbm = "cljg";
                    currentYqbmName = "处理结果";
                    currentState = "8";
                } else if (position == 6) {
                    currentYqbm = "dwj";
                    currentYqbmName = "待完结";
                    currentState = "9";
                }
                if (position == 0) {
                    //还需要判断权限，是否显示 提报按钮
                    if (type.equals("3") || type.equals("2")) {
                        topRightButton.setVisibility(View.VISIBLE);
                        topRightButton.setText("提报");
                        topRightType = "TB";
                    } else {
                        topRightButton.setVisibility(View.GONE);
                    }
                } else if (position == 1 || position == 2 || position == 3 || position == 4 || position == 5) {
                    //还需要判断权限，是否显示 提报按钮
                    if (type.equals("2")) {
                        topRightButton.setVisibility(View.VISIBLE);
                        topRightButton.setText("处理");
                        topRightType = "CL";
                    } else {
                        topRightButton.setVisibility(View.GONE);
                    }
                } else if (position == 6) {
                    //还需要判断权限，是否显示 提报按钮
                    if (type.equals("2")) {
                        topRightButton.setVisibility(View.VISIBLE);
                        topRightButton.setText("完结");
                        topRightType = "WJ";
                    } else {
                        topRightButton.setVisibility(View.GONE);
                    }
                } else {
                    topRightButton.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    public void onClick(View v) {
        if(v.getId() ==R.id.ib_quality_objection_back ){
            this.finish();
        }else if(v.getId() ==R.id.rl_quality_objection_tibaozongshu){
            viewPager.setCurrentItem(0);
            setTopGone();
            rltibaozongshu.getChildAt(1).setVisibility(View.VISIBLE);
        }else if(v.getId() ==R.id.rl_quality_objection_fengongsishouli){
            viewPager.setCurrentItem(1);
            setTopGone();
            rlfengongsishouli.getChildAt(1).setVisibility(View.VISIBLE);
        }else if(v.getId() ==R.id.rl_quality_objection_gangchangshouli){
            viewPager.setCurrentItem(2);
            setTopGone();
            rlgangchangshouli.getChildAt(1).setVisibility(View.VISIBLE);
        }else if(v.getId() ==R.id.rl_quality_objection_chuliqingkuang){
            viewPager.setCurrentItem(3);
            setTopGone();
            rlchuliqingkuang.getChildAt(1).setVisibility(View.VISIBLE);
        }else if(v.getId() ==R.id.rl_quality_objection_fenxiyuanyin){
            viewPager.setCurrentItem(4);
            setTopGone();
            rlfenxiyuanyin.getChildAt(1).setVisibility(View.VISIBLE);
        }else if(v.getId() ==R.id.rl_quality_objection_chulijieguo){
            viewPager.setCurrentItem(5);
            setTopGone();
            rlchulijieguo.getChildAt(1).setVisibility(View.VISIBLE);
        }else if(v.getId() ==R.id.rl_quality_objection_daiwanjie){
            viewPager.setCurrentItem(6);
            setTopGone();
            rldaiwanjie.getChildAt(1).setVisibility(View.VISIBLE);
        }else if(v.getId() ==R.id.rl_quality_objection_yiwanjie){
            viewPager.setCurrentItem(7);
            setTopGone();
            rlyiwanjie.getChildAt(1).setVisibility(View.VISIBLE);
        }else if(v.getId() ==R.id.bt_quality_objection_right_top){
            if ("TB".equals(topRightType)) {
                Log.e("QualityObjection", "TB");
                //提报点击事件，跳到提报的Activity
                Intent intent = new Intent(this, QualityObjectionSubmitActivity.class);
                intent.putExtra("orgid", orgid);
                intent.putExtra("orgname", orgName);
                intent.putExtra("username", username);
                startActivityForResult(intent, REQUESTCODE);
            } else if ("CL".equals(topRightType)) {
                Log.e("QualityObjection", "CL");
                //处理的点击事件，跳到受理的activity
                Intent intent = new Intent(this, QualityObjectionDealOverActivity.class);
                intent.putExtra("type", "CL");
                intent.putExtra("orgid", orgid);
                intent.putExtra("orgname", orgName);
                intent.putExtra("username", username);
                intent.putExtra("yqbm", currentYqbm);
                intent.putExtra("currentYqbmName", currentYqbmName);
                intent.putExtra("currentState", currentState);
                startActivityForResult(intent, REQUESTCODE);
            } else if ("WJ".equals(topRightType)) {
                Log.e("QualityObjection", "WJ");
                //处理的点击事件，跳到受理的activity
                Intent intent = new Intent(this, QualityObjectionDealOverActivity.class);
                intent.putExtra("type", "WJ");
                intent.putExtra("orgid", orgid);
                intent.putExtra("orgname", orgName);
                intent.putExtra("username", username);
                intent.putExtra("yqbm", currentYqbm);
                intent.putExtra("currentYqbmName", currentYqbmName);
                intent.putExtra("currentState", currentState);
                startActivityForResult(intent, REQUESTCODE);
            }
            //只要点击右上角按钮，返回后都刷新数据，先做成暴力刷新，之后再根据是否有数据被提交，或者数据被处理完结去刷新
            for (int i = 0; i < fragmentList.size(); i++) {
                fragmentList.get(i).setRefresh();
            }
        }
    }

    private void setTopGone() {
        rltibaozongshu.getChildAt(1).setVisibility(View.GONE);
        rlfengongsishouli.getChildAt(1).setVisibility(View.GONE);
        rlgangchangshouli.getChildAt(1).setVisibility(View.GONE);
        rlchuliqingkuang.getChildAt(1).setVisibility(View.GONE);
        rlfenxiyuanyin.getChildAt(1).setVisibility(View.GONE);
        rlchulijieguo.getChildAt(1).setVisibility(View.GONE);
        rldaiwanjie.getChildAt(1).setVisibility(View.GONE);
        rlyiwanjie.getChildAt(1).setVisibility(View.GONE);
    }

    /**
     * 获取用户信息
     */
    private void getUserDeatil() {
        AnsynHttpRequest.request(this, getUserDetailInformationRequest, ContantValues.GETUSERDETAILINFORMATION, CHTTP.POST, new ObserverCallBack() {

            @Override
            public void success(String successMessage) {
                if (successMessage != null && !successMessage.equals("")) {
                    getUserDetailInformationResult = FastJsonUtils.getPerson(successMessage, GetUserDetailInformationResult.class);
                    if (getUserDetailInformationResult.success) {
                        type = getUserDetailInformationResult.type.trim();
                        orgid = getUserDetailInformationResult.orgid;
                        orgName = getUserDetailInformationResult.orgname;
                        username = getUserDetailInformationResult.username;
                        //还需要判断权限，是否显示 提报按钮
                        if (type.equals("3") || type.equals("2")) {
                            topRightButton.setVisibility(View.VISIBLE);
                            topRightButton.setText("提报");
                            topRightType = "TB";
                        } else {
                            topRightButton.setVisibility(View.GONE);
                        }
                    }
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
    }


    /**
     * 获取页签数量
     */
    private void getTabCount() {

        AnsynHttpRequest.request(this, getTabCountRequest, ContantValues.GETZLYYTABCOUNT, CHTTP.POST, new ObserverCallBack() {

            @Override
            public void success(String successMessage) {
                if (successMessage != null && !successMessage.equals("")) {
                    getTabCountResult = FastJsonUtils.getPerson(successMessage, GetTabCountResult.class);
                    if (getTabCountResult.success) {
                        if (getTabCountResult.results != null && getTabCountResult.results.size() != 0) {
                            for (int i = 0; i < getTabCountResult.results.size(); i++) {
                                GetTabCountReslutList reslutList = getTabCountResult.results.get(i);
                                if (reslutList.key.equals("tbzs")) {
                                    ((TextView) rltibaozongshu.getChildAt(0)).setText("提报总数(" + reslutList.value + ")");
                                } else if (reslutList.key.equals("fgssl")) {
                                    ((TextView) rlfengongsishouli.getChildAt(0)).setText("分公司受理(" + reslutList.value + ")");
                                } else if (reslutList.key.equals("gcsl")) {
                                    ((TextView) rlgangchangshouli.getChildAt(0)).setText("钢厂受理(" + reslutList.value + ")");
                                } else if (reslutList.key.equals("clqk")) {
                                    ((TextView) rlchuliqingkuang.getChildAt(0)).setText("处理情况(" + reslutList.value + ")");
                                } else if (reslutList.key.equals("fxyy")) {
                                    ((TextView) rlfenxiyuanyin.getChildAt(0)).setText("分析原因(" + reslutList.value + ")");
                                } else if (reslutList.key.equals("cljg")) {
                                    ((TextView) rlchulijieguo.getChildAt(0)).setText("处理结果(" + reslutList.value + ")");
                                } else if (reslutList.key.equals("dwj")) {
                                    ((TextView) rldaiwanjie.getChildAt(0)).setText("待完结(" + reslutList.value + ")");
                                } else if (reslutList.key.equals("ywj")) {
                                    ((TextView) rlyiwanjie.getChildAt(0)).setText("已完结(" + reslutList.value + ")");
                                }
                            }
                        }
                    }
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
    }

    /**
     * 提报或者处理后的回掉，为了刷新数据
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //刷新页签
        getTabCount();
        //刷新当前页签的列表内容
        fragmentList.get(position).lazyLoad();
    }

    @Override
    public void onRefreshData() {
        Log.e("onRefreshData", "执行了");
        getTabCount();
    }
}
