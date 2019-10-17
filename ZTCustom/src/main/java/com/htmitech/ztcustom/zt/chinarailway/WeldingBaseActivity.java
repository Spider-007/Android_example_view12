package com.htmitech.ztcustom.zt.chinarailway;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;


import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.adapter.FileManagerFragmentPagerAdapter;
import com.htmitech.ztcustom.zt.base.BaseFragmentActivity;
import com.htmitech.ztcustom.zt.fragment.weldingbasefragment.ProductWeldingFragment;
import com.htmitech.ztcustom.zt.fragment.weldingbasefragment.WaittingWeldingFragment;
import com.htmitech.ztcustom.zt.view.MainViewPager;

import java.util.ArrayList;
import java.util.List;

public class WeldingBaseActivity extends BaseFragmentActivity implements View.OnClickListener {


    private ImageButton imageButtonBack;
    private RelativeLayout rlDHG;
    private RelativeLayout rlCPG;
    private MainViewPager viewPager;
    private List<Fragment> fragmentList;
    private FileManagerFragmentPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welding_base);
        initView();
        initData();
        initControl();
    }

    private void initView() {
        imageButtonBack = (ImageButton) findViewById(R.id.ib_welding_base_back);
        imageButtonBack.setOnClickListener(this);
        rlDHG = (RelativeLayout) findViewById(R.id.rl_welding_base_dhg);
        rlDHG.setOnClickListener(this);
        rlCPG = (RelativeLayout) findViewById(R.id.rl_welding_base_cpg);
        rlCPG.setOnClickListener(this);
        viewPager = (MainViewPager) findViewById(R.id.vp_welding_base);
        viewPager.setNoScroll(true);
    }

    private void initData() {
        fragmentList = new ArrayList<Fragment>();
        WaittingWeldingFragment waittingWeldingFragment = new WaittingWeldingFragment();
        ProductWeldingFragment productWeldingFragment = new ProductWeldingFragment();
        fragmentList.add(waittingWeldingFragment);
        fragmentList.add(productWeldingFragment);
        pagerAdapter = new FileManagerFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(pagerAdapter);
    }

    private void initControl() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                initInVisable();
                if (position == 0) {
                    rlDHG.getChildAt(1).setVisibility(View.VISIBLE);
                } else if (position == 1) {
                    rlCPG.getChildAt(1).setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId() ==R.id.ib_welding_base_back ){
            this.finish();
        }else if(v.getId() ==R.id.rl_welding_base_dhg){
            viewPager.setCurrentItem(0);
        }else if(v.getId() ==R.id.rl_welding_base_cpg){
            viewPager.setCurrentItem(1);
        }
    }

    private void initInVisable() {
        rlDHG.getChildAt(1).setVisibility(View.INVISIBLE);
        rlCPG.getChildAt(1).setVisibility(View.INVISIBLE);
    }
}
