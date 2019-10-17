package com.htmitech.proxy.activity;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.htmitech.api.BookInit;
import com.htmitech.app.Constant;
import com.htmitech.base.BaseFragmentActivity;
import com.htmitech.emportal.HtmitechApplication;
import com.htmitech.emportal.R;
import com.htmitech.emportal.common.CommonSettings;
import com.htmitech.emportal.entity.OAConText;
import com.htmitech.emportal.ui.appcenter.adapter.HomePageGridAdapter;
import com.htmitech.others.FileUtil;
import com.htmitech.proxy.adapter.ClearCacheAdapter;
import com.htmitech.proxy.dao.AppliationCenterDao;
import com.htmitech.proxy.doman.AppInfo;
import com.htmitech.proxy.doman.ClearCacheDoman;
import com.htmitech.proxy.doman.EmpPortal;
import com.htmitech.proxy.util.FileSizeUtil;

import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;

import htmitech.com.componentlibrary.unit.RefreshTotal;

/**********************************************************
 *
 * 清除缓存功能
 *
 *********************************************************
 */

public class ClearCacheActivity extends BaseFragmentActivity implements View.OnClickListener {

    private TextView clearCache;
    private ImageView ivBack;
    private TextView titleName;
    private TextView tv_size;
    private ListView lv_clear_list;

    private ClearCacheAdapter clearCacheAdapter;
    private RelativeLayout layout_titlebar;
    private LinearLayout llTop;
    private RelativeLayout rl_clear_back;
    private double numberSize;
    private ArrayList<ClearCacheDoman> clearCacheDomans;

    private ArrayList<ClearCacheDoman> cacheclearCacheDomans;
    private TextView tv_no_clear;

    private double yiqingliSize = 0;
    private  View headerView;

    private String appName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clear_cache);
        inintView();
        initData();
    }


    private void initData() {
        appName = getIntent().getStringExtra("appName");
        titleName.setText(appName);
        AppliationCenterDao mAppliationCenterDao = new AppliationCenterDao(this);
        //获取应用中心中的应用来进行
        ArrayList<AppInfo> appInfos = mAppliationCenterDao.getPortalAppInfoAll();
//        ArrayList<AppInfo> appInfosLeft = mAppliationCenterDao.getLeftAppInfo();
//        appInfos.addAll(appInfosLeft);
        String portal_id = BookInit.getInstance().getPortalId() + "";
        String userId = OAConText.getInstance(HtmitechApplication.instance()).UserID;
        lv_clear_list.addHeaderView(headerView);
        clearCacheDomans = new ArrayList<ClearCacheDoman>();
        cacheclearCacheDomans = new ArrayList<ClearCacheDoman>();
        ArrayList<AppInfo> classifyAppInfos = new ArrayList<AppInfo>();
        for(int i = 0 ; i < appInfos.size() ; i ++){
            if(appInfos.get(i).getApp_type() == 7 && appInfos.get(i).getClassifyAppInfo() != null){
                classifyAppInfos.addAll(appInfos.get(i).getClassifyAppInfo());
                appInfos.remove(i);
                i--;
            }
        }

        appInfos.addAll(classifyAppInfos);
        //将所有的应用中心都建立文件夹
        for(AppInfo mAppInfo : appInfos){
            String filePath =  CommonSettings.DEFAULT_CACHE_FOLDER
                    + File.separator + userId + File.separator + portal_id + File.separator + mAppInfo.getApp_id();
            double size = FileSizeUtil.getFileOrFilesSize(filePath,3);
            String[] showTextSize = FileSizeUtil.getAutoFileOrFilesSize(filePath);
            numberSize += size;
            if(size == 0){
                continue;
            }
            clearCacheDomans.add(new ClearCacheDoman(mAppInfo, size, filePath, showTextSize[0], showTextSize[1]));
            cacheclearCacheDomans.add(new ClearCacheDoman(mAppInfo, size, filePath, showTextSize[0], showTextSize[1]));
        }

        setBack();

        DecimalFormat df = new DecimalFormat("#####0.00");
        String numberStr = df.format(numberSize);
        tv_size.setText(numberStr+"");
        clearCache.setText("一键清理已选(" + numberStr + ")M");
        clearCacheAdapter = new ClearCacheAdapter(clearCacheDomans,this);

        lv_clear_list.setAdapter(clearCacheAdapter);

        lv_clear_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    return;
                }
                ClearCacheDoman mClearCacheDoman = (ClearCacheDoman) parent.getItemAtPosition(position);

                if (!mClearCacheDoman.isDelete()) {
                    numberSize += mClearCacheDoman.getSize();
                } else {
                    numberSize -= mClearCacheDoman.getSize();
                }
                String numberStr = "0.00";
                if (numberSize != 0) {
                    DecimalFormat df = new DecimalFormat("#####0.00");
                    numberStr = df.format(numberSize);
                }
                RefreshTotal.addReshActivity();
                mClearCacheDoman.setIsDelete(!mClearCacheDoman.isDelete());
                cacheclearCacheDomans.get(position - 1).setIsDelete(mClearCacheDoman.isDelete());
                clearCache.setText("一键清理已选(" + numberStr + ")M");
                tv_size.setText("" + numberStr);
                clearCacheAdapter.notifyDataSetChanged();

            }
        });
        layout_titlebar.setBackgroundColor(getResources().getColor(R.color.transparent));


    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setBack(){
        if(numberSize == 0){
            lv_clear_list.setVisibility(View.GONE);
            clearCache.setVisibility(View.GONE);
            tv_no_clear.setVisibility(View.VISIBLE);
            int colorValue = getResources().getColor(R.color.ht_hred_title);
            int colorBulue = getResources().getColor(R.color.ht_hred_title_duibi);
            int colorRed  = getResources().getColor(R.color.ht_red_dangzheng);

            Drawable drawable = layout_titlebar.getBackground();
            ColorDrawable dra = (ColorDrawable) drawable;
            int colors[] = new int[2];
            colorValue = dra.getColor();
            if(colorValue == colorBulue){
                colors[0] = colorBulue;
                colors[1] = 0xff36BC99;
            }else if(colorValue == colorRed){
                colors[0] = colorRed;
                colors[1] = 0xffFAB3B6;
            }else{
                drawable = llTop.getBackground();
                dra = (ColorDrawable) drawable;
                colorValue = dra.getColor();
                if(colorValue == colorBulue){
                    colors[0] = colorBulue;
                    colors[1] = 0xff36BC99;
                }else if(colorValue == colorRed){
                    colors[0] = colorRed;
                    colors[1] = 0xffFAB3B6;
                }else{
                    colors[0] = colorBulue;
                    colors[1] = 0xff36BC99;
                    titleName.setTextColor(Color.parseColor("#ffffff"));
                    ivBack.setBackgroundResource(R.drawable.mx_btn_back);
                }
            }

            GradientDrawable bg = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors);
            int sdk = android.os.Build.VERSION.SDK_INT;
            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                rl_clear_back.setBackgroundDrawable(bg);
            } else {
                rl_clear_back.setBackground(bg);
            }
        }
    }

    private void inintView() {
        headerView = LayoutInflater.from(this).inflate(R.layout.activity_clear_head_top, null);
        clearCache = (TextView) findViewById(R.id.tv_clear_cache);
        layout_titlebar = (RelativeLayout) findViewById(R.id.layout_titlebar);
        llTop = (LinearLayout) headerView.findViewById(R.id.ll_top);
        rl_clear_back = (RelativeLayout) findViewById(R.id.rl_clear_back);
        tv_size = (TextView) headerView.findViewById(R.id.tv_size);
        clearCache.setOnClickListener(this);
        ivBack= (ImageView) findViewById(R.id.btn_daiban_person);
        titleName= (TextView) findViewById(R.id.daibantopTabIndicator_bbslist);
        tv_no_clear= (TextView) findViewById(R.id.tv_no_clear);
        lv_clear_list= (ListView) findViewById(R.id.lv_clear_list);
        ivBack.setOnClickListener(this);

        Drawable drawable = llTop.getBackground();
        ColorDrawable dra = (ColorDrawable) drawable;
        int colorValue = dra.getColor();
        int colorBulue = getResources().getColor(R.color.ht_hred_title_duibi);
        int colorRed  = getResources().getColor(R.color.ht_red_dangzheng);
        if(colorValue != colorBulue && colorValue != colorRed){
            llTop.setBackgroundColor(getResources().getColor(R.color.ht_hred_title_duibi));
            titleName.setTextColor(Color.parseColor("#ffffff"));
            ivBack.setBackgroundResource(R.drawable.mx_btn_back);
        }
        if(colorValue == colorRed){
            clearCache.setBackgroundColor(getResources().getColor(R.color.ht_red_dangzheng));
        }
    }

    Handler myHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            switch (msg.what) {
                case 0:
                    int postion = msg.arg1;
                    clearCacheAdapter.remove(postion);
                    break;
                case 1:
                    dismissDialog();
                    DecimalFormat   df   =   new DecimalFormat("#####0.00");
                    String numberStr =df.format(yiqingliSize);
                    Toast.makeText(ClearCacheActivity.this, "已清理("+numberStr+"M)", Toast.LENGTH_SHORT).show();

                    cacheclearCacheDomans.clear();
                    cacheclearCacheDomans.addAll(clearCacheDomans);
                    if(clearCacheDomans.size() == 0){
                        numberSize = 0;
                        setBack();
                        Drawable image = getResources().getDrawable(R.drawable.img_trophy_finish);
                        tv_no_clear.setCompoundDrawablesWithIntrinsicBounds(null, image, null, null);
                        tv_no_clear.setText("清理完成");
                    }else{
                        clearCache.setText("一键清理已选(0.00M)");
                        tv_size.setText("0.00");
                    }
                    RefreshTotal.addReshActivity();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_clear_cache:
                showDialog();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int postion = 0;
                        int delete = 0;
                        for(ClearCacheDoman clearCacheDoman : cacheclearCacheDomans){
                            if(clearCacheDoman.isDelete()){
                                File file = new File(clearCacheDoman.getFilePath());
                                deleteAllFiles(file);
                                numberSize -= clearCacheDoman.getSize();
                                yiqingliSize += clearCacheDoman.getSize();
                                clearCacheDoman.setSize(0);
                                Message msg = myHandler.obtainMessage();
                                msg.what = 0;
                                msg.arg1 = postion - delete;
                                myHandler.sendMessage(msg);
                                delete ++;
                            }
                            postion++;
                        }

                        Message msg = myHandler.obtainMessage();
                        msg.what = 1;
                        myHandler.sendMessage(msg);

                    }
                }).start();


                break;
            case R.id.btn_daiban_person:
                finish();
                break;
        }
    }

    private void deleteAllFiles(File root) {
        File files[] = root.listFiles();
        if (files != null)
            for (File f : files) {
                if (f.isDirectory()) { // 判断是否为文件夹
                    deleteAllFiles(f);
                    try {
                        f.delete();
                    } catch (Exception e) {
                    }
                } else {
                    if (f.exists()) { // 判断是否存在
                        deleteAllFiles(f);
                        try {
                            f.delete();
                        } catch (Exception e) {
                        }
                    }
                }
            }
    }

}
