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
import com.htmitech.ztcustom.zt.fragment.filemanagerfragment.FileManagerFinishedFragment;
import com.htmitech.ztcustom.zt.fragment.filemanagerfragment.FileManagerUploadFragment;
import com.htmitech.ztcustom.zt.view.MainViewPager;

import java.util.ArrayList;
import java.util.List;

public class FileManagerActivity extends BaseFragmentActivity implements View.OnClickListener {

    private ImageButton ibBack;
    private MainViewPager viewPager;
    private RelativeLayout rlUpload;
    private RelativeLayout rlFinished;
    private List<Fragment> fragmentList;
    private FileManagerFragmentPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_manager);
        initView();
        initData();
        initControl();
    }

    private void initView() {
        ibBack = (ImageButton) findViewById(R.id.ib_file_manager_back);
        ibBack.setOnClickListener(this);
        rlUpload = (RelativeLayout) findViewById(R.id.rl_file_manager_upload);
        rlUpload.setOnClickListener(this);
        rlFinished = (RelativeLayout) findViewById(R.id.rl_file_manager_finish);
        rlFinished.setOnClickListener(this);
        viewPager = (MainViewPager) findViewById(R.id.vp_file_manager);
    }

    private void initData() {
        fragmentList = new ArrayList<Fragment>();
        FileManagerUploadFragment uploadFragment = new FileManagerUploadFragment();
        FileManagerFinishedFragment finishedFragment = new FileManagerFinishedFragment();
        fragmentList.add(uploadFragment);
        fragmentList.add(finishedFragment);
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
                if (0 == position) {
                    rlUpload.getChildAt(1).setVisibility(View.VISIBLE);
                } else if (1 == position) {
                    rlFinished.getChildAt(1).setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId() ==R.id.ib_file_manager_back ){
            this.finish();
        }else if(v.getId() ==R.id.rl_file_manager_upload){
            viewPager.setCurrentItem(0);
        }else if(v.getId() ==R.id.rl_file_manager_finish){
            viewPager.setCurrentItem(1);
        }

    }

    private void initInVisable() {
        rlUpload.getChildAt(1).setVisibility(View.INVISIBLE);
        rlFinished.getChildAt(1).setVisibility(View.INVISIBLE);
    }
}
