package com.htmitech.ztcustom.zt.chinarailway;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.adapter.FileManagerFragmentPagerAdapter;
import com.htmitech.ztcustom.zt.app.ZTCustomInit;
import com.htmitech.ztcustom.zt.base.BaseFragmentActivity;
import com.htmitech.ztcustom.zt.constant.ContantValues;
import com.htmitech.ztcustom.zt.domain.QualityObjectionBigImageRequest;
import com.htmitech.ztcustom.zt.domain.QualityObjectionBigImageResult;
import com.htmitech.ztcustom.zt.fragment.qualityobjectiondetailfragment.QualityObjectionDetailCLQKFragment;
import com.htmitech.ztcustom.zt.fragment.qualityobjectiondetailfragment.QualityObjectionDetailDQLCFragment;
import com.htmitech.ztcustom.zt.fragment.qualityobjectiondetailfragment.QualityObjectionDetailJBXXFragment;
import com.htmitech.ztcustom.zt.interfaces.ObserverCallBack;
import com.htmitech.ztcustom.zt.interfaces.OnClickBigImageListener;
import com.htmitech.ztcustom.zt.thread.AnsynHttpRequest;
import com.htmitech.ztcustom.zt.unit.CHTTP;
import com.htmitech.ztcustom.zt.util.FastJsonUtils;
import com.htmitech.ztcustom.zt.util.GlideUtil;
import com.htmitech.ztcustom.zt.view.MainViewPager;
import com.htmitech.ztcustom.zt.view.photoview.PhotoView;

import java.util.ArrayList;
import java.util.List;

/**
 * 质量异议
 */
public class QualityObjectionDetailsActivity extends BaseFragmentActivity implements View.OnClickListener, OnClickBigImageListener {


    private ImageButton ibBack;
    private String id;
    private RelativeLayout rlJBXX;
    private RelativeLayout rlCLQK;
    private RelativeLayout rlDQLC;
    private MainViewPager viewPager;
    private List<Fragment> fragmentList;
    private FileManagerFragmentPagerAdapter pagerAdapter;
    private PhotoView bigImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quality_objection_details);
        initView();
        initData();
        initControl();
    }

    private void initView() {
        ibBack = (ImageButton) findViewById(R.id.ibn_quality_objection_detail_back);
        ibBack.setOnClickListener(this);
        rlJBXX = (RelativeLayout) findViewById(R.id.rl_quality_objection_detail_jbxx);
        rlJBXX.setOnClickListener(this);
        rlCLQK = (RelativeLayout) findViewById(R.id.rl_quality_objection_detail_clqk);
        rlCLQK.setOnClickListener(this);
        rlDQLC = (RelativeLayout) findViewById(R.id.rl_quality_objection_detail_dqlc);
        rlDQLC.setOnClickListener(this);
        viewPager = (MainViewPager) findViewById(R.id.vp_welding_base);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setNoScroll(true);
        bigImageView = (PhotoView) findViewById(R.id.iv_quality_objection_detail_big_pic_all);
        bigImageView.setOnClickListener(this);
        bigImageView.setVisibility(View.GONE);
    }

    private void initData() {
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        fragmentList = new ArrayList<Fragment>();
        QualityObjectionDetailJBXXFragment jbxxFragment = new QualityObjectionDetailJBXXFragment();
        Bundle bundlejbxx = new Bundle();
        bundlejbxx.putString("id", id);
        jbxxFragment.setArguments(bundlejbxx);
        QualityObjectionDetailCLQKFragment clqkFragment = new QualityObjectionDetailCLQKFragment();
        Bundle bundleclqk = new Bundle();
        bundleclqk.putString("id", id);
        clqkFragment.setArguments(bundleclqk);
        QualityObjectionDetailDQLCFragment dqlcFragment = new QualityObjectionDetailDQLCFragment();
        Bundle bundledqlc = new Bundle();
        bundledqlc.putString("id", id);
        dqlcFragment.setArguments(bundledqlc);
        fragmentList.add(jbxxFragment);
        fragmentList.add(clqkFragment);
        fragmentList.add(dqlcFragment);
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
                    rlJBXX.getChildAt(1).setVisibility(View.VISIBLE);
                } else if (position == 1) {
                    rlCLQK.getChildAt(1).setVisibility(View.VISIBLE);
                } else if (position == 2) {
                    rlDQLC.getChildAt(1).setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId() ==R.id.ibn_quality_objection_detail_back ){
            this.finish();
        }else if(v.getId() ==R.id.rl_quality_objection_detail_jbxx){
            viewPager.setCurrentItem(0);
        }else if(v.getId() ==R.id.rl_quality_objection_detail_clqk){
            viewPager.setCurrentItem(1);
        }else if(v.getId() ==R.id.rl_quality_objection_detail_dqlc){
            viewPager.setCurrentItem(2);
        }

    }

    private void initInVisable() {
        rlJBXX.getChildAt(1).setVisibility(View.INVISIBLE);
        rlCLQK.getChildAt(1).setVisibility(View.INVISIBLE);
        rlDQLC.getChildAt(1).setVisibility(View.INVISIBLE);
    }


    @Override
    public void onBackPressed() {
        if (bigImageView.getVisibility() == View.VISIBLE) {
            bigImageView.setVisibility(View.GONE);
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void clickImageView(String id, String fileId) {
        getBigImage(id, fileId);
    }

    private void getBigImage(String id, String fileId) {

        showProgressDialog(this);
        final QualityObjectionBigImageRequest request = new QualityObjectionBigImageRequest();
        request.userid =  ZTCustomInit.get().getmCache().getCisAccountDetail().cisAccountId;
        request.id = id;
        request.fileid = fileId;

        AnsynHttpRequest.request(this, request, ContantValues.ZLYYFILEDOWNLOAD, CHTTP.POST, new ObserverCallBack() {

            @Override
            public void success(String successMessage) {
                dimssDialog();
                if (successMessage != null && !successMessage.equals("")) {
                    QualityObjectionBigImageResult result = FastJsonUtils.getPerson(successMessage, QualityObjectionBigImageResult.class);
                    if (result == null) {
                        Toast.makeText(QualityObjectionDetailsActivity.this, "获取图片地址失败", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (result.success && result.photo != null && result.photo.size() != 0) {
                        bigImageView.setVisibility(View.VISIBLE);
                        GlideUtil.loadGild(QualityObjectionDetailsActivity.this, result.photo.get(0), R.drawable.icon_zlyy_loading, R.drawable.img_zlyy_list_thumbnail, bigImageView);
                    } else if (result.success) {
                        Toast.makeText(QualityObjectionDetailsActivity.this, "图片还未上传", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(QualityObjectionDetailsActivity.this, "获取图片地址失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void notNetwork() {
                // TODO Auto-generated method stub
                dimssDialog();
            }

            @Override
            public void fail(String exceptionMessage) {
                // TODO Auto-generated method stub
                dimssDialog();
                Toast.makeText(QualityObjectionDetailsActivity.this, "获取图片地址失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
