package com.htmitech.ztcustom.zt.chinarailway;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;


import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.adapter.FileManagerFragmentPagerAdapter;
import com.htmitech.ztcustom.zt.base.BaseFragmentActivity;
import com.htmitech.ztcustom.zt.fragment.weldingbasefragment.WeldingBaseGCFragment;
import com.htmitech.ztcustom.zt.fragment.weldingbasefragment.WeldingBaseXMFragment;
import com.htmitech.ztcustom.zt.view.MainViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * 焊轨基地
 */
public class WeldingBaseDetailActivity extends BaseFragmentActivity implements View.OnClickListener {

    private ImageButton imageButtonBack;
    private RelativeLayout rlxm;
    private RelativeLayout rlgc;
    private MainViewPager viewPager;
    private List<Fragment> fragmentList;
    private FileManagerFragmentPagerAdapter pagerAdapter;
    private String type;
    private String id;
    private String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welding_base_detail);
        initView();
        initData();
        initControl();
    }


    private void initView() {
        imageButtonBack = (ImageButton) findViewById(R.id.ib_welding_base_detail_back);
        imageButtonBack.setOnClickListener(this);
        rlxm = (RelativeLayout) findViewById(R.id.rl_welding_base_detail_xm);
        rlxm.setOnClickListener(this);
        rlgc = (RelativeLayout) findViewById(R.id.rl_welding_base_detail_gc);
        rlgc.setOnClickListener(this);
        viewPager = (MainViewPager) findViewById(R.id.vp_welding_base_detail);
    }

    private void initData() {
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        id = intent.getStringExtra("hanguijidiid");
        name = intent.getStringExtra("hanguijidi");
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        bundle.putString("id", id);
        bundle.putString("hanguijidi", name);
        fragmentList = new ArrayList<Fragment>();
        WeldingBaseXMFragment weldingBaseXMFragment = new WeldingBaseXMFragment();
        weldingBaseXMFragment.setArguments(bundle);
        WeldingBaseGCFragment weldingBaseGCFragment = new WeldingBaseGCFragment();
        weldingBaseGCFragment.setArguments(bundle);
        fragmentList.add(weldingBaseXMFragment);
        fragmentList.add(weldingBaseGCFragment);
        pagerAdapter = new FileManagerFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setNoScroll(true);
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
                    rlxm.getChildAt(1).setVisibility(View.VISIBLE);
                } else if (position == 1) {
                    rlgc.getChildAt(1).setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId() ==R.id.ib_welding_base_detail_back ){
            this.finish();
        }else if(v.getId() ==R.id.rl_welding_base_detail_xm){
            viewPager.setCurrentItem(0);
        }else if(v.getId() ==R.id.rl_welding_base_detail_gc){
            viewPager.setCurrentItem(1);
        }
    }

    private void initInVisable() {
        rlxm.getChildAt(1).setVisibility(View.INVISIBLE);
        rlgc.getChildAt(1).setVisibility(View.INVISIBLE);
    }
}
