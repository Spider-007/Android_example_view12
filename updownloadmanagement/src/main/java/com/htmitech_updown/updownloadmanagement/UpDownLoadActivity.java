package com.htmitech_updown.updownloadmanagement;

import android.Manifest;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

;import com.example.updownloadmanagement.R;
import com.htmitech.thread.AnsynHttpRequest;
import com.htmitech.thread.CHTTP;
import com.htmitech.thread.GetFinalRefreshTokenAfterRequestValue;
import com.htmitech.utils.FastJsonUtils;
import com.htmitech_updown.updownloadmanagement.UploadBigFileFactory;
import com.htmitech_updown.updownloadmanagement.adapter.FileManagerFragmentPagerAdapter;
import com.htmitech_updown.updownloadmanagement.db.DbUtil;
import com.htmitech_updown.updownloadmanagement.fragment.UpLoadFileFinishFragment;
import com.htmitech_updown.updownloadmanagement.fragment.UpLoadFileFragment;
import com.htmitech_updown.updownloadmanagement.uploadbean.ChunkInfo;
import com.htmitech_updown.updownloadmanagement.uploadbean.GetUploadBatchResultBean;
import com.htmitech_updown.updownloadmanagement.uploadfile.Md5Utils;
import com.htmitech_updown.updownloadmanagement.uploadfile.UploadManager;
import com.htmitech_updown.updownloadmanagement.view.NoScrollViewPager;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import htmitech.com.componentlibrary.entity.bigfileentity.SaveBigFileExtFields;
import htmitech.com.componentlibrary.listener.ObserverCallBackType;
import htmitech.com.componentlibrary.unit.ServerUrlConstant;

public class UpDownLoadActivity extends cn.feng.skin.manager.base.BaseFragmentActivity implements View.OnClickListener, ObserverCallBackType {

    private ImageButton imageButtonBack;
    private TextView textViewTitle;
    private RelativeLayout rlSCLB;
    private RelativeLayout rlYWCLB;
    private NoScrollViewPager viewPager;
    private List<Fragment> fragmentList;
    private FileManagerFragmentPagerAdapter pagerAdapter;
//    private static final String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE};
//    private static final int requestCode = 0x0001;
    UploadBigFileFactory fileFactory;
//    private String getFileBatchPath = ServerUrlConstant.SERVER_JAVA_URL() + ServerUrlConstant.GET_BIGFILE_BATCH;
//    private String GET_FILE_BATCH = "getFileBatchPath";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updownload_prefixactivity_main_arch);
//        UploadManager.init(this);
//        DbUtil.setContext(this);
//        fileFactory = UploadBigFileFactory.get().setSaveBigFileExtField(new SaveBigFileExtFields());
//        fileFactory.setContext(this);
//        fileFactory.createUploadTask("/mnt/sdcard/SVID_20170814_160318.mp4", "工作流-附件", "123", "123");
        initView();
        initData();
        initControl();
    }

    private void initView() {
        imageButtonBack = (ImageButton) findViewById(R.id.title_left_button);
        imageButtonBack.setOnClickListener(this);
        textViewTitle = (TextView) findViewById(R.id.title_name);
        textViewTitle.setText("文件传输管理");
        rlSCLB = (RelativeLayout) findViewById(R.id.rl_updownload_list);
        rlSCLB.setOnClickListener(this);
        rlYWCLB = (RelativeLayout) findViewById(R.id.rl_tv_updownload_finish_list);
        rlYWCLB.setOnClickListener(this);
        viewPager = (NoScrollViewPager) findViewById(R.id.vp_updownlaod);
        viewPager.setNoScroll(true);
    }


    private void initData() {
//        requestPermissions(this, requestCode, permissions);
        fragmentList = new ArrayList<>();
        UpLoadFileFragment upLoadFileFragment = new UpLoadFileFragment();
        UpLoadFileFinishFragment upLoadFileFinishFragment = new UpLoadFileFinishFragment();
        fragmentList.add(upLoadFileFragment);
        fragmentList.add(upLoadFileFinishFragment);
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
                    rlSCLB.getChildAt(1).setVisibility(View.VISIBLE);
                } else if (position == 1) {
                    rlYWCLB.getChildAt(1).setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.title_left_button) {
            try {
                InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
                // 隐藏软键盘
                imm.hideSoftInputFromWindow(this.getWindow().getDecorView().getWindowToken(), 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
            finish();
        } else if (id == R.id.rl_updownload_list) {
            viewPager.setCurrentItem(0);
        } else if (id == R.id.rl_tv_updownload_finish_list) {
            viewPager.setCurrentItem(1);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (fragmentList != null) {
            if (fragmentList.get(1) != null) {
                if (fragmentList.get(1) instanceof UpLoadFileFinishFragment) {
                    ((UpLoadFileFinishFragment) fragmentList.get(1)).uploadActivityData();
                }
            }
        }

//        fileFactory.createUploadTask(this, "/mnt/sdcard/demo.apk", "工作流-附件");
    }

    private void initInVisable() {
        rlSCLB.getChildAt(1).setVisibility(View.INVISIBLE);
        rlYWCLB.getChildAt(1).setVisibility(View.INVISIBLE);
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == this.requestCode && grantResults.length > 0) {
//            for (int result : grantResults) {
//                if (result != PackageManager.PERMISSION_GRANTED) {
//                    Toast.makeText(this, "请求权限失败！", Toast.LENGTH_LONG).show();
//                    return;
//                }
//            }
//        }
//
//    }

//    public static void requestPermissions(Object object, int requestCode, String... permissions) {
//        ArrayList<String> arrayList = new ArrayList<>();
//        for (String permission : permissions) {
//            arrayList.add(permission);
//        }
//        if (arrayList.size() > 0) {
//            if (object instanceof Activity) {
//                Activity activity = (Activity) object;
//                Activity activity1 = activity.getParent() != null && activity.getParent() instanceof TabActivity ? activity.getParent() : activity;
//                ActivityCompat.requestPermissions(activity1, arrayList.toArray(new String[]{}), requestCode);
//            } else if (object instanceof Fragment) {
//                Fragment fragment = (Fragment) object;
//                Fragment fragment1 = fragment.getParentFragment() != null ? fragment.getParentFragment() : fragment;
//                fragment1.requestPermissions(arrayList.toArray(new String[]{}), requestCode);
//            } else {
//                throw new RuntimeException("the object must be Activity or Fragment");
//            }
//        }
//    }


//    public void createUploadTask(String path, String taskName) {
//        File file = new File(path);
//        if (path.equals("")) {
//            Toast.makeText(this, "请选择文件", Toast.LENGTH_LONG).show();
//        } else if (!file.exists()) {
//            Toast.makeText(this, "文件不存在", Toast.LENGTH_LONG).show();
//        } else {
//            //获取文件号
//            AnsynHttpRequest.requestByPostWithToken(this, null, getFileBatchPath, CHTTP.POSTWITHTOKEN, this, GET_FILE_BATCH, null);
//        }
//    }

    @Override
    public void success(String requestValue, int type, String requestName) {
//        if (GET_FILE_BATCH.equals(requestName)) {
//            requestValue = GetFinalRefreshTokenAfterRequestValue.getFinalRequestValue(this, requestValue, type, getFileBatchPath, null, this, requestName, null);
//            if (null != requestValue) {
//                GetUploadBatchResultBean batchResultBean = FastJsonUtils.getPerson(requestValue, GetUploadBatchResultBean.class);
//                if (batchResultBean.code == 200) {
//                    if (batchResultBean.result != null && !batchResultBean.result.equals("")) {
//                        File file = new File(path);
//                        ChunkInfo fileInfo = new ChunkInfo();
//                        fileInfo.setFilePath(path);
//                        fileInfo.setFileLength(file.length());
//                        fileInfo.setIsChunk(true);
//                        fileInfo.setUploadStatus(0);
//                        fileInfo.setBatchNumber(batchResultBean.result);
//                        fileInfo.setFileName(path.substring(path.lastIndexOf("/") + 1));
//                        long time1 = System.currentTimeMillis();
//                        String md5 = Md5Utils.getFileMd5(file) + time1;//文件的MD5加上一个当前时间戳
//                        fileInfo.setMd5(md5);
//                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
//                        String createTime = df.format(new Date());// new Date()为获取当前系统时间
//                        fileInfo.setCreateTime(createTime);
//                        fileInfo.setTaskName("工作流-附件");
//                        DbUtil.addUpDownState(fileInfo);
//                        UploadManager.getInstance().addUploadTask(fileInfo);
//                    }
//                }
//            }
//        }
    }

    @Override
    public void fail(String exceptionMessage, int type, String requestName) {

    }

    @Override
    public void notNetwork() {

    }

    @Override
    public void callbackMainUI(String successMessage) {

    }
}
