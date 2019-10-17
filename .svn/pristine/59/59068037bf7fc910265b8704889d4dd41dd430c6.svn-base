package com.htmitech.ztcustom.zt.chinarailway;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.adapter.GeneralInfomationFragmentPagerAdapter;
import com.htmitech.ztcustom.zt.base.BaseFragmentActivity;
import com.htmitech.ztcustom.zt.fragment.DecetionCarFragment;
import com.htmitech.ztcustom.zt.fragment.DecetionTaskFragment;
import com.htmitech.ztcustom.zt.fragment.InjuryDisposeFragment;
import com.htmitech.ztcustom.zt.fragment.NewFindInjuryFragment;
import com.htmitech.ztcustom.zt.view.MainViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * 综合信息查询
 */
public class GeneralInformationActivity extends BaseFragmentActivity implements View.OnClickListener {

    private MainViewPager viewPager;

    private RelativeLayout rlNewFindInjury;
    private RelativeLayout rlInjuryDispose;
    private RelativeLayout rlDecetionTask;
    private RelativeLayout rlDecetionCar;
    private GeneralInfomationFragmentPagerAdapter fragmentPagerAdapter;
    private List<Fragment> fragmentList;
    private ImageButton imageButtonBack;
    private LinearLayout linearLayoutTopItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_information);
        initView();
        initData();
        initControl();
    }

    private void initView() {
        viewPager = (MainViewPager) findViewById(R.id.viewpager_tanshanggeneralinformation);
        viewPager.setNoScroll(true);
        viewPager.setOffscreenPageLimit(1);
        imageButtonBack = (ImageButton) findViewById(R.id.ib_tanshanggeneralinformation_back);
        imageButtonBack.setOnClickListener(this);
        rlNewFindInjury = (RelativeLayout) findViewById(R.id.rl_generalinfomation_xinfazhongshang);
        rlNewFindInjury.setOnClickListener(this);
        rlInjuryDispose = (RelativeLayout) findViewById(R.id.rl_generalinfomation_zhongshangchuzhi);
        rlInjuryDispose.setOnClickListener(this);
        rlDecetionTask = (RelativeLayout) findViewById(R.id.rl_generalinfomation_tanshangrenwu);
        rlDecetionTask.setOnClickListener(this);
        rlDecetionCar = (RelativeLayout) findViewById(R.id.rl_generalinfomation_tanshangche);
        rlDecetionCar.setOnClickListener(this);
        linearLayoutTopItem = (LinearLayout) findViewById(R.id.ll_generalinfomation_top_item);
        setTopGone();
        rlNewFindInjury.getChildAt(1).setVisibility(View.VISIBLE);
    }

    private void initData() {
        //将数据与ViewPager通过适配器连接在一起
        fragmentList = new ArrayList<Fragment>();
        NewFindInjuryFragment newFindInjuryFragment = new NewFindInjuryFragment();//新发重伤
        InjuryDisposeFragment injuryDisposeFragment = new InjuryDisposeFragment();//重伤处置
        DecetionTaskFragment decetionTaskFragment = new DecetionTaskFragment();//探伤任务
        DecetionCarFragment decetionCarFragment = new DecetionCarFragment();//探伤车
        fragmentList.add(newFindInjuryFragment);
        fragmentList.add(injuryDisposeFragment);
        fragmentList.add(decetionTaskFragment);
        fragmentList.add(decetionCarFragment);
        fragmentPagerAdapter = new GeneralInfomationFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(fragmentPagerAdapter);
    }


    private void initControl() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setTopGone();
                ((RelativeLayout) linearLayoutTopItem.getChildAt(position)).getChildAt(1).setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    public void onClick(View v) {
        if(v.getId() ==R.id.ib_tanshanggeneralinformation_back ){
            this.finish();
        }else if(v.getId() ==R.id.rl_generalinfomation_xinfazhongshang){
            viewPager.setCurrentItem(0);
            setTopGone();
            rlNewFindInjury.getChildAt(1).setVisibility(View.VISIBLE);
        }else if(v.getId() ==R.id.rl_generalinfomation_zhongshangchuzhi){
            viewPager.setCurrentItem(1);
            setTopGone();
            rlInjuryDispose.getChildAt(1).setVisibility(View.VISIBLE);
        }else if(v.getId() ==R.id.rl_generalinfomation_tanshangrenwu){
            viewPager.setCurrentItem(2);
            setTopGone();
            rlDecetionTask.getChildAt(1).setVisibility(View.VISIBLE);
        }else if(v.getId() ==R.id.rl_generalinfomation_tanshangche){
            viewPager.setCurrentItem(3);
            setTopGone();
            rlDecetionCar.getChildAt(1).setVisibility(View.VISIBLE);
        }
    }

    private void setTopGone() {
        rlNewFindInjury.getChildAt(1).setVisibility(View.GONE);
        rlInjuryDispose.getChildAt(1).setVisibility(View.GONE);
        rlDecetionTask.getChildAt(1).setVisibility(View.GONE);
        rlDecetionCar.getChildAt(1).setVisibility(View.GONE);
    }
}
