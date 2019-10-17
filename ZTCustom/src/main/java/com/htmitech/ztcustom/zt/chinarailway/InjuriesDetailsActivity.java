package com.htmitech.ztcustom.zt.chinarailway;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.adapter.MyFragmentPagerAdapter;
import com.htmitech.ztcustom.zt.base.BaseFragment;
import com.htmitech.ztcustom.zt.base.BaseFragmentActivity;
import com.htmitech.ztcustom.zt.fragment.HistoricalMessageFragment;
import com.htmitech.ztcustom.zt.fragment.ShangsxqFragment;

import java.util.ArrayList;

/**
 * 现存伤损详情 Activity
 */
public class InjuriesDetailsActivity extends BaseFragmentActivity implements View.OnClickListener {
    private ViewPager mPager;
    private ArrayList<BaseFragment> fragmentList;
    private int ViewPagerNumber = 2;  //滑动个数
    private int currentIndex;
    private TextView tv_people_shangsxq;
    private TextView tv_lisxx;
    private ImageView back;
    /**
     * 屏幕的宽度
     */
    private int screenWidth;
    private ImageView image;
    private String userid;
    private String defect_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zt_activity_injuries_details);

        initView();
        initTabLineWidth();
        initData();
    }

    private void initData() {
        userid = getIntent().getStringExtra("userid");
        defect_id = getIntent().getStringExtra("defect_id");
        fragmentList = new ArrayList<BaseFragment>();
        HistoricalMessageFragment mHistoricalMessageFragment = new HistoricalMessageFragment();
        ShangsxqFragment mShangsxqFragment = new ShangsxqFragment();
        mShangsxqFragment.setUserid(userid);
        mShangsxqFragment.setDefect_id(defect_id);
        mHistoricalMessageFragment.setUserid(userid);
        mHistoricalMessageFragment.setDefect_id(defect_id);
        fragmentList.add(mShangsxqFragment);

        fragmentList.add(mHistoricalMessageFragment);
        // 给ViewPager设置适配器
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
                        tv_people_shangsxq.setTextColor(getResources().getColor(R.color.lightblue_3));
                        break;
                    case 1:
                        tv_lisxx.setTextColor(getResources().getColor(R.color.lightblue_3));
                        break;
                }

                currentIndex = position;
            }
        });// 页面变化时的监听器
        tv_people_shangsxq.setOnClickListener(this);
        tv_lisxx.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    private void initView() {
        mPager = (ViewPager) findViewById(R.id.viewpager);
        image = (ImageView) findViewById(R.id.cursor);
        tv_people_shangsxq = (TextView) findViewById(R.id.tv_people_shangsxq);
        tv_lisxx = (TextView) findViewById(R.id.tv_lisxx);
        back = (ImageView) findViewById(R.id.back);
    }
    /**
     * 设置滑动条的宽度为屏幕的1/3(根据Tab的个数而定)
     */
    private void initTabLineWidth() {
        DisplayMetrics dpMetrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay()
                .getMetrics(dpMetrics);
        screenWidth = dpMetrics.widthPixels;
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) image
                .getLayoutParams();
        lp.width = screenWidth / ViewPagerNumber;
        image.setLayoutParams(lp);
    }

    /**
     * 重置颜色
     */
    private void resetTextView() {
        tv_people_shangsxq.setTextColor(Color.WHITE);
        tv_lisxx.setTextColor(Color.WHITE);
    }
    @Override
    public void onClick(View v) {
        if(v.getId() ==R.id.tv_people_shangsxq ){
            mPager.setCurrentItem(0);
        }else if(v.getId() ==R.id.tv_lisxx){
            mPager.setCurrentItem(1);
        }else if(v.getId() ==R.id.back){
            finish();
        }

    }
}
