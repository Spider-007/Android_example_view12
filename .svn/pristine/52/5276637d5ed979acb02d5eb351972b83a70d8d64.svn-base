package com.htmitech.ztcustom.zt.chinarailway;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;


import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.app.ZTCustomInit;
import com.htmitech.ztcustom.zt.base.BaseFragmentActivity;
import com.htmitech.ztcustom.zt.constant.ContantValues;
import com.htmitech.ztcustom.zt.db.QualityObjectionDao;
import com.htmitech.ztcustom.zt.dialog.AlertDialog;
import com.htmitech.ztcustom.zt.dialog.QualityObjectionItemClickAlertDialog;
import com.htmitech.ztcustom.zt.domain.QualittObjectionDicVauleList;
import com.htmitech.ztcustom.zt.domain.QualityObjectionDicRequest;
import com.htmitech.ztcustom.zt.domain.QualityObjectionDicResult;
import com.htmitech.ztcustom.zt.domain.QualityObjectionDicResultList;
import com.htmitech.ztcustom.zt.domain.QualityObjectionSubmitDataResult;
import com.htmitech.ztcustom.zt.domain.QualityObjectionSubmitDataRquest;
import com.htmitech.ztcustom.zt.interfaces.ObserverCallBack;
import com.htmitech.ztcustom.zt.interfaces.OnQualityObjectionItemClickListener;
import com.htmitech.ztcustom.zt.interfaces.UploadFileCallBackListener;
import com.htmitech.ztcustom.zt.thread.AnsynHttpRequest;
import com.htmitech.ztcustom.zt.unit.CHTTP;
import com.htmitech.ztcustom.zt.util.FastJsonUtils;
import com.htmitech.ztcustom.zt.util.GlideUtil;
import com.htmitech.ztcustom.zt.util.ImageUtils;
import com.htmitech.ztcustom.zt.util.UpLoadFileUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import htmitech.com.componentlibrary.unit.ToastUtil;
import htmitech.com.componentlibrary.unit.Utils;

public class QualityObjectionSubmitActivity extends BaseFragmentActivity implements View.OnClickListener, View.OnLongClickListener {

    private TextView tbr;//填报人 自动带出
    private TextView tbrq;//填报日期 自动带出
    private EditText lxr;//手动录入
    private EditText lxfs;//手动录入
    private TextView tbdw;//自动带出
    private TextView xmmc;//下拉 非必填
    private EditText zlyydd;//手动录入
    private TextView gx;//下拉
    private TextView gg;//下拉
    private TextView cd;//手动录入
    private TextView gc;//下拉
    private EditText lh;//手动录入
    private EditText jyms;//手动录入
    private EditText ds;//手动录入
    private EditText gs;//手动录入
    private TextView ss;//下拉
    private TextView cz;//下拉
    private TextView rcl;//下拉
    private ImageView imageViewOne;
    private ImageView imageViewTwo;
    private ImageView imageViewThree;
    private Button submit;
    private ImageButton imBack;
    private ImageButton imFile;
    private PopupWindow mPopWindow;
    private static final int REQUEST_BLUETOOTH_PERMISSION = 10;
    public static final int CAMERA_REQUEST_CODE = 100;
    public static final int IMAGE_REQUEST_CODE = 101;
    public int whichImage = 0;
    public HashMap<Integer, String> imageHasMap;//图片URL路径集合
    public String pzUrl = "";
    private ArrayList<QualittObjectionDicVauleList> xmmcList;
    private ArrayList<QualittObjectionDicVauleList> gxList;
    private ArrayList<QualittObjectionDicVauleList> ggList;
    private ArrayList<QualittObjectionDicVauleList> gcList;
    private ArrayList<QualittObjectionDicVauleList> cdList;
    private ArrayList<QualittObjectionDicVauleList> sldwList;
    private ArrayList<QualittObjectionDicVauleList> ssList;
    private ArrayList<QualittObjectionDicVauleList> czList;
    private ArrayList<QualittObjectionDicVauleList> rclList;
    private ArrayList<String> picThumbList;
    private ArrayList<String> picList;
    private String id;
    private String xmmcCode = "";
    private String gxCode = "";
    private String ggCode = "";
    private String gcCode = "";
    private String cdCode = "";
    private String sldwCode = "";
    private String orgCode = "";
    private String ssCode = "";
    private String czCode = "";
    private String rclCode = "";
    private String orgName = "";
    private String userName = "";
    int num = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quality_objection_submit);
        initView();
        initData();
    }

    private void initView() {
        lxr = (EditText) findViewById(R.id.quality_objection_submit_lxr);
        lxfs = (EditText) findViewById(R.id.quality_objection_submit_lxfs);
        tbdw = (TextView) findViewById(R.id.quality_objection_submit_tbdw);
        tbr = (TextView) findViewById(R.id.quality_objection_submit_tbr);
        tbrq = (TextView) findViewById(R.id.quality_objection_submit_tbrq);
        xmmc = (TextView) findViewById(R.id.quality_objection_submit_xmmc);
        zlyydd = (EditText) findViewById(R.id.quality_objection_submit_zlyydd);
        gx = (TextView) findViewById(R.id.quality_objection_submit_gx);
        gg = (TextView) findViewById(R.id.quality_objection_submit_gg);
        cd = (TextView) findViewById(R.id.quality_objection_submit_cd);
        gc = (TextView) findViewById(R.id.quality_objection_submit_gc);
        lh = (EditText) findViewById(R.id.quality_objection_submit_lh);
        jyms = (EditText) findViewById(R.id.quality_objection_submit_jyms);
        ds = (EditText) findViewById(R.id.quality_objection_submit_ds);
        gs = (EditText) findViewById(R.id.quality_objection_submit_gs);
        ss = (TextView) findViewById(R.id.quality_objection_submit_ss);
        cz = (TextView) findViewById(R.id.quality_objection_submit_cz);
        rcl = (TextView) findViewById(R.id.quality_objection_submit_rcl);
        imageViewOne = (ImageView) findViewById(R.id.quality_objection_submit_img1);
        imageViewOne.setOnClickListener(this);
        imageViewOne.setOnLongClickListener(this);
        imageViewTwo = (ImageView) findViewById(R.id.quality_objection_submit_img2);
        imageViewTwo.setOnClickListener(this);
        imageViewTwo.setOnLongClickListener(this);
        imageViewThree = (ImageView) findViewById(R.id.quality_objection_submit_img3);
        imageViewThree.setOnClickListener(this);
        imageViewThree.setOnLongClickListener(this);
        submit = (Button) findViewById(R.id.bt_quality_objection_submit);
        submit.setOnClickListener(this);
        imBack = (ImageButton) findViewById(R.id.ib_quality_objection_submit_back);
        imBack.setOnClickListener(this);
        imFile = (ImageButton) findViewById(R.id.ib_quality_objection_submit_file);
        imFile.setOnClickListener(this);
    }

    private void initData() {
        picThumbList = new ArrayList<String>();
        picList = new ArrayList<String>();
        xmmcList = new ArrayList<QualittObjectionDicVauleList>();
        gxList = new ArrayList<QualittObjectionDicVauleList>();
        ggList = new ArrayList<QualittObjectionDicVauleList>();
        gcList = new ArrayList<QualittObjectionDicVauleList>();
        cdList = new ArrayList<QualittObjectionDicVauleList>();
        sldwList = new ArrayList<QualittObjectionDicVauleList>();
        ssList = new ArrayList<QualittObjectionDicVauleList>();
        czList = new ArrayList<QualittObjectionDicVauleList>();
        rclList = new ArrayList<QualittObjectionDicVauleList>();
        getDicData();
        orgName = getIntent().getStringExtra("orgname");
        userName = getIntent().getStringExtra("username");
        orgCode = getIntent().getStringExtra("orgid");
        if (orgCode != null && orgName != null && !orgName.equals("")) {
            tbdw.setText(orgName);
        }
        if (userName != null && !userName.equals("")) {
            tbr.setText(userName);
        }
        Date day = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        tbrq.setText(df.format(day));
        imageHasMap = new HashMap<Integer, String>();
        xmmc.setOnClickListener(this);
        gx.setOnClickListener(this);
        gg.setOnClickListener(this);
        gc.setOnClickListener(this);
        cd.setOnClickListener(this);
        ss.setOnClickListener(this);
        cz.setOnClickListener(this);
        rcl.setOnClickListener(this);
    }

    @Override
    public void onClick(final View v) {
        if (v.getId() == R.id.ib_quality_objection_submit_back) {
            this.finish();
        } else if (v.getId() == R.id.bt_quality_objection_submit) {
            submit();
        } else if (v.getId() == R.id.quality_objection_submit_xmmc || v.getId() == R.id.quality_objection_submit_gx || v.getId() == R.id.quality_objection_submit_gg || v.getId() == R.id.quality_objection_submit_gc || v.getId() == R.id.quality_objection_submit_cd || v.getId() == R.id.quality_objection_submit_ss || v.getId() == R.id.quality_objection_submit_cz || v.getId() == R.id.quality_objection_submit_rcl) {
            String title = "";
            ArrayList<QualittObjectionDicVauleList> list = null;
            if (v.getId() == R.id.quality_objection_submit_xmmc) {
                title = "请选择项目名称";
                list = new ArrayList<QualittObjectionDicVauleList>();
                list.addAll(xmmcList);
            } else if (v.getId() == R.id.quality_objection_submit_gx) {
                title = "请选择轨型";
                list = new ArrayList<QualittObjectionDicVauleList>();
                list.addAll(gxList);
            } else if (v.getId() == R.id.quality_objection_submit_gg) {
                title = "请选择规格";
                list = new ArrayList<QualittObjectionDicVauleList>();
                list.addAll(ggList);
            } else if (v.getId() == R.id.quality_objection_submit_gc) {
                title = "请选择钢厂";
                list = new ArrayList<QualittObjectionDicVauleList>();
                list.addAll(gcList);
            } else if (v.getId() == R.id.quality_objection_submit_cd) {
                title = "请选择长度";
                list = new ArrayList<QualittObjectionDicVauleList>();
                list.addAll(cdList);
            } else if (v.getId() == R.id.quality_objection_submit_ss) {
                title = "请选择时速";
                list = new ArrayList<QualittObjectionDicVauleList>();
                list.addAll(ssList);
            } else if (v.getId() == R.id.quality_objection_submit_cz) {
                title = "请选择材质";
                list = new ArrayList<QualittObjectionDicVauleList>();
                list.addAll(czList);
            } else if (v.getId() == R.id.quality_objection_submit_rcl) {
                title = "请选择热处理";
                list = new ArrayList<QualittObjectionDicVauleList>();
                list.addAll(rclList);
            }
            new QualityObjectionItemClickAlertDialog(this).builder().
                    setTitle(title).setData(list).
                    setPositiveButton("", new OnQualityObjectionItemClickListener() {
                        @Override
                        public void callBack(String chooseString, String code) {
                            Log.e("chooseString", chooseString);
                            ((TextView) v).setText(chooseString);
                            if (v.getId() == R.id.quality_objection_submit_xmmc) {
                                xmmcCode = code;
                            } else if (v.getId() == R.id.quality_objection_submit_gx) {
                                gxCode = code;
                            } else if (v.getId() == R.id.quality_objection_submit_gg) {
                                ggCode = code;
                            } else if (v.getId() == R.id.quality_objection_submit_gc) {
                                gcCode = code;
                            } else if (v.getId() == R.id.quality_objection_submit_cd) {
                                cdCode = code;
                            } else if (v.getId() == R.id.quality_objection_submit_ss) {
                                ssCode = code;
                            } else if (v.getId() == R.id.quality_objection_submit_cz) {
                                czCode = code;
                            } else if (v.getId() == R.id.quality_objection_submit_rcl) {
                                rclCode = code;
                            }
                        }
                    }).
                    setNegativeButton("", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    }).setCancelable(false).show();
        } else if (v.getId() == R.id.quality_objection_submit_img1||v.getId() == R.id.quality_objection_submit_img2||v.getId() == R.id.quality_objection_submit_img3) {
            if (v.getId() == R.id.quality_objection_submit_img1) {
                if (null != imageHasMap.get(1)) {
                    return;
                }
                whichImage = 1;
            } else if (v.getId() == R.id.quality_objection_submit_img2) {
                if (null != imageHasMap.get(2)) {
                    return;
                }
                whichImage = 2;
            } else if (v.getId() == R.id.quality_objection_submit_img3) {
                if (null != imageHasMap.get(3)) {
                    return;
                }
                whichImage = 3;
            }
            showPopupWindow();
        } else if (v.getId() == R.id.ib_quality_objection_submit_file) {
            Intent intent = new Intent(this, FileManagerActivity.class);
            startActivity(intent);
        }

    }

    @Override
    public boolean onLongClick(View v) {
        if(v.getId() ==R.id.quality_objection_submit_img1||v.getId() ==R.id.quality_objection_submit_img2||v.getId() ==R.id.quality_objection_submit_img3 ){
            if (v.getId() == R.id.quality_objection_submit_img1) {
                if (null == imageHasMap.get(1)) {
                    return true;
                }
                imageHasMap.remove(1);
                imageViewOne.setImageResource(R.drawable.btn_addphoto);
            } else if (v.getId() == R.id.quality_objection_submit_img2) {
                if (null == imageHasMap.get(2)) {
                    return true;
                }
                imageHasMap.remove(2);
                imageViewTwo.setImageResource(R.drawable.btn_addphoto);
            } else if (v.getId() == R.id.quality_objection_submit_img3) {
                if (null == imageHasMap.get(3)) {
                    return true;
                }
                imageHasMap.remove(3);
                imageViewThree.setImageResource(R.drawable.btn_addphoto);
            }
        }
        return true;
    }


    private void submit() {
        //检验是否有必填项未填写
        if ("".equals(lxr.getText().toString())) {
            Toast.makeText(this, "联系人未填写", Toast.LENGTH_LONG).show();
            return;
        }
        if ("".equals(lxfs.getText().toString())) {
            Toast.makeText(this, "联系方式未填写", Toast.LENGTH_LONG).show();
            return;
        }
        if ("".equals(xmmcCode)) {
            Toast.makeText(this, "项目名称未填写", Toast.LENGTH_LONG).show();
            return;
        }
        if ("".equals(gcCode)) {
            Toast.makeText(this, "钢厂未填写", Toast.LENGTH_LONG).show();
            return;
        }
        if ("".equals(jyms.getText().toString())) {
            Toast.makeText(this, "简要描述未填写", Toast.LENGTH_LONG).show();
            return;
        }
        if (imageHasMap != null && imageHasMap.get(1) == null && imageHasMap.get(2) == null && imageHasMap.get(3) == null) {

            new AlertDialog(QualityObjectionSubmitActivity.this).builder().
                    setTitle("提示").setMsg("建议上传质量问题照片").
                    setNegativeButton("直接提报", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {//不上传直接提报
                            selectSLDW();
                        }
                    }).
                    setPositiveButton("上传照片", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {//上传照片
                            return;
                        }
                    }).setCancelable(false).show();
        } else {
            cutPic();
            selectSLDW();
        }
    }


    private void cutPic() {
        //创建缩略图的base64并且把图片路径放到list集合中 这样保证有几个图片集合就多大，而且是有序的，方便后面命名
        //每次点击提报按钮都要清空，防止在选择受理单位时取消然后再点击提报重复的像list中添加数据
        if (picList != null) {
            picList.clear();
        }
        if (picThumbList != null) {
            picThumbList.clear();
        }
        if (imageHasMap.get(1) != null) {
            picList.add(imageHasMap.get(1));
            Bitmap bitmap = ImageUtils.getImageThumbnail(imageHasMap.get(1), 432, 288);
            String base64String = ImageUtils.bitmapToBase64(bitmap);
            ImageUtils.gcBitmap(bitmap);
            picThumbList.add(base64String);
        }
        if (imageHasMap.get(2) != null) {
            picList.add(imageHasMap.get(2));
            Bitmap bitmap = ImageUtils.getImageThumbnail(imageHasMap.get(2), 432, 288);
            String base64String = ImageUtils.bitmapToBase64(bitmap);
            ImageUtils.gcBitmap(bitmap);
            picThumbList.add(base64String);
        }
        if (imageHasMap.get(3) != null) {
            picList.add(imageHasMap.get(3));
            Bitmap bitmap = ImageUtils.getImageThumbnail(imageHasMap.get(3), 432, 288);
            String base64String = ImageUtils.bitmapToBase64(bitmap);
            ImageUtils.gcBitmap(bitmap);
            picThumbList.add(base64String);
        }

    }

    private void selectSLDW() {

        //选择受理单位
        new QualityObjectionItemClickAlertDialog(this).builder().
                setTitle("请选择受理单位").setData(sldwList).
                setPositiveButton("", new OnQualityObjectionItemClickListener() {
                    @Override
                    public void callBack(String chooseString, String code) {
                        Log.e("chooseString", chooseString);
                        sldwCode = code;
                        //开始提报网络请求
                        submitDataHttp();
                    }
                }).
                setNegativeButton("", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        return;
                    }
                }).setCancelable(false).show();
    }


    private void submitDataHttp() {
        showProgressDialog(this);
        QualityObjectionSubmitDataRquest request = new QualityObjectionSubmitDataRquest();
        request.userid = ZTCustomInit.get().getmCache().getCisAccountDetail().cisAccountId;
        request.lianxiren = lxr.getText().toString();
        request.lianxifangshi = lxfs.getText().toString();
        request.tianbaodanwei = orgCode;
        request.didian = zlyydd.getText().toString();
        request.guixing = gxCode;
        request.guige = ggCode;
        request.changdu = cdCode;
        request.xiangmumingcheng = xmmcCode;
        request.dunshu = ds.getText().toString();
        request.genshu = gs.getText().toString();
        request.shisu = ssCode;
        request.caizhi = czCode;
        request.rechuli = rclCode;
        request.gangchang = gcCode;
        request.luhao = lh.getText().toString();
        request.miaoshu = jyms.getText().toString();
        request.shoulidanwei = sldwCode;
        request.suoluetu.addAll(picThumbList);

        AnsynHttpRequest.request(this, request, ContantValues.SUBMITZLYY, CHTTP.POST, new ObserverCallBack() {

            @Override
            public void success(String successMessage) {
                // TODO Auto-generated method stub

                if (successMessage == null) {
                    dimssDialog();
                    Utils.toast(QualityObjectionSubmitActivity.this, "服务器异常", Toast.LENGTH_SHORT);
                    return;
                }
                if (successMessage != null && !successMessage.equals("")) {
                    QualityObjectionSubmitDataResult result = new QualityObjectionSubmitDataResult();
                    result = FastJsonUtils.getPerson(successMessage, QualityObjectionSubmitDataResult.class);
                    if (result.success) {
                        id = result.id;
                        dimssDialog();
                        //弹出对话框是否要立刻上报图片 如果存在照片弹出提示框
                        if (picList != null && picList.size() > 0) {
                            new AlertDialog(QualityObjectionSubmitActivity.this).builder().
                                    setTitle("提示").setMsg("是否立即上传图片").
                                    setNegativeButton("", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {//不立即上报

                                            new Thread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    for (int i = 0; i < picList.size(); i++) {
                                                        String picID = id + "-0" + (i + 1);
                                                        try {
                                                            new QualityObjectionDao(QualityObjectionSubmitActivity.this).insertIntoFile(picID, getFileName(picList.get(i)), picList.get(i), "zlyy", "0", ZTCustomInit.get().getmCache().getCisAccountDetail().cisAccountId);
                                                            setResult(2, new Intent());//为了回掉能够刷新页签数量
                                                            QualityObjectionSubmitActivity.this.finish();
                                                        } catch (Exception e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                }
                                            }).start();
                                        }
                                    }).
                                    setPositiveButton("", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {//立即上报
                                            showProgressDialog(QualityObjectionSubmitActivity.this);
                                            new Thread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    for (int i = 0; i < picList.size(); i++) {
                                                        new UpLoadFileUtils().uploadFile(picList.get(i), ZTCustomInit.get().getmCache().getCisAccountDetail().cisAccountId, id, id + "-0" + (i + 1), QualityObjectionSubmitActivity.this, false, 0, new UploadFileCallBackListener() {
                                                            @Override
                                                            public void fileCallBack(String result) {
                                                                Log.e("UPLOADFILE", result);
                                                                num++;
                                                                if (num == picList.size()) {
                                                                    QualityObjectionSubmitActivity.this.runOnUiThread(new Runnable() {
                                                                        @Override
                                                                        public void run() {
                                                                            dimssDialog();
                                                                            Toast.makeText(QualityObjectionSubmitActivity.this, "文件上报完成", Toast.LENGTH_SHORT).show();
                                                                            setResult(2, new Intent());//为了回掉能够刷新页签数量
                                                                            QualityObjectionSubmitActivity.this.finish();
                                                                        }
                                                                    });
                                                                }
                                                            }
                                                        });
                                                    }
                                                }
                                            }).start();

                                        }
                                    }).setCancelable(false).show();
                        } else {
                            Toast.makeText(QualityObjectionSubmitActivity.this, "质量异议提报成功", Toast.LENGTH_SHORT).show();
                            setResult(2, new Intent());//为了回掉能够刷新页签数量
                            QualityObjectionSubmitActivity.this.finish();
                        }
                    } else {
                        dimssDialog();
                        Utils.toast(QualityObjectionSubmitActivity.this, "服务器异常" + result.msg, Toast.LENGTH_SHORT);
                    }
                } else {
                    dimssDialog();
                    Utils.toast(QualityObjectionSubmitActivity.this, "服务器异常,返回为空", Toast.LENGTH_SHORT);
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
                Toast.makeText(QualityObjectionSubmitActivity.this, "服务器异常", Toast.LENGTH_SHORT).show();
            }
        });

    }


    /**
     * 选择相机/相册的提示对话框
     */
    private void showPopupWindow() {
        //设置contentView
        View contentView = LayoutInflater.from(this).inflate(R.layout.dialog_photo, null);
        mPopWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        mPopWindow.setContentView(contentView);
        //设置各个控件的点击响应
        contentView.findViewById(R.id.btn_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String state = Environment.getExternalStorageState(); //拿到sdcard是否可用的状态码
                if (state.equals(Environment.MEDIA_MOUNTED)) {   //如果可用
                    letCamera();
                } else {
                    Toast.makeText(QualityObjectionSubmitActivity.this, "sdcard不存在", Toast.LENGTH_SHORT).show();
                }
            }
        });
        contentView.findViewById(R.id.btn_picture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toPicture();
            }
        });
        contentView.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopWindow.dismiss();
            }
        });
        //显示PopupWindow
        View rootview = LayoutInflater.from(this).inflate(R.layout.activity_quality_objection_submit, null);
        mPopWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
    }


    /**
     * 跳转相机
     */

    protected void letCamera() {
        // TODO Auto-generated method stub
        requestWESPermission(); // 安卓6.0以上需要申请权限
        mPopWindow.dismiss();
        Intent imageCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String strImgPath = Environment.getExternalStorageDirectory().toString() + "/zt/";// 存放照片的文件夹
        String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".jpg";// 照片命名
        File out = new File(strImgPath);
        if (!out.exists()) {
            out.mkdirs();
        }
        out = new File(strImgPath, fileName);
        strImgPath = strImgPath + fileName;// 该照片的绝对路径
        pzUrl = strImgPath;
        Uri uri = Uri.fromFile(out);
        imageCaptureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        imageCaptureIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, CAMERA_REQUEST_CODE);
        startActivityForResult(imageCaptureIntent, CAMERA_REQUEST_CODE);
    }

    /**
     * 跳转相册
     */
    private void toPicture() {
        mPopWindow.dismiss();
        // 激活系统图库，选择一张图片
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_GALLERY
        startActivityForResult(intent, IMAGE_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case IMAGE_REQUEST_CODE: // 相册数据
                if (data == null) {
                    return;
                }
                String url = getRealPathFromUri(this, data.getData());
                if (whichImage != 0) {
                    if (whichImage == 1) {
                        GlideUtil.loadGild(this, url, R.drawable.btn_addphoto, R.drawable.btn_addphoto, imageViewOne);
                        imageHasMap.put(1, url);
                    } else if (2 == whichImage) {
                        GlideUtil.loadGild(this, url, R.drawable.btn_addphoto, R.drawable.btn_addphoto, imageViewTwo);
                        imageHasMap.put(2, url);
                    } else if (3 == whichImage) {
                        GlideUtil.loadGild(this, url, R.drawable.btn_addphoto, R.drawable.btn_addphoto, imageViewThree);
                        imageHasMap.put(3, url);
                    }
                }
                break;
            case CAMERA_REQUEST_CODE: // 相机数据
                if (pzUrl != null && !pzUrl.equals("")) {
                    if (whichImage != 0) {
                        if (whichImage == 1) {
                            GlideUtil.loadGild(this, pzUrl, R.drawable.btn_addphoto, R.drawable.btn_addphoto, imageViewOne);
                            imageHasMap.put(1, pzUrl);
                        } else if (2 == whichImage) {
                            GlideUtil.loadGild(this, pzUrl, R.drawable.btn_addphoto, R.drawable.btn_addphoto, imageViewTwo);
                            imageHasMap.put(2, pzUrl);
                        } else if (3 == whichImage) {
                            GlideUtil.loadGild(this, pzUrl, R.drawable.btn_addphoto, R.drawable.btn_addphoto, imageViewThree);
                            imageHasMap.put(3, pzUrl);
                        }
                    }
                    pzUrl = "";
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    /**
     * 动态申请权限
     */
    private void requestWESPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                // 判断是否需要 向用户解释，为什么要申请该权限
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE))
                    Toast.makeText(this, "Need write external storage permission", Toast.LENGTH_LONG);
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_BLUETOOTH_PERMISSION);
                return;
            } else {
            }
        } else {
        }
    }

    /**
     * 授权回调处理
     */
    @Override
    public void onRequestPermissionsResult(int permsRequestCode, String[] permissions, int[] grantResults) {
        switch (permsRequestCode) {
            case REQUEST_BLUETOOTH_PERMISSION:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 授权成功
                } else {
                    // 授权拒绝
                }
                break;
        }
    }

    /**
     * 拿到真实路径
     *
     * @param context
     * @param contentUri
     * @return
     */
    public static String getRealPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public void getDicData() {

        showProgressDialog(this);
        final QualityObjectionDicRequest request = new QualityObjectionDicRequest();
        request.userid = ZTCustomInit.get().getmCache().getCisAccountDetail().cisAccountId;

        AnsynHttpRequest.request(this, request, ContantValues.GETZLYYDIC, CHTTP.POST, new ObserverCallBack() {

            @Override
            public void success(String successMessage) {
                // TODO Auto-generated method stub

                if (successMessage != null && !successMessage.equals("")) {
                    QualityObjectionDicResult result = new QualityObjectionDicResult();
                    result = FastJsonUtils.getPerson(successMessage, QualityObjectionDicResult.class);
                    if (result.success) {
                        List<QualityObjectionDicResultList> resultListList = result.results;
                        for (int i = 0; i < resultListList.size(); i++) {
                            if (resultListList.get(i).key.equals("")) {
                                ToastUtil.show(getApplicationContext(),"Result返回为null",2000);
                            } else {
                                if (resultListList.get(i).key.equals("guixing")) {
                                    if (gxList != null  && resultListList.get(i).value != null) {
                                        gxList.clear();
                                        gxList.addAll(resultListList.get(i).value);
                                    }else{
                                        Log.e("YJH", "success-> resultListList.get(i).value == null" );
                                    }
                                } else if (resultListList.get(i).key.equals("guige")) {
                                    if (ggList != null  && resultListList.get(i).value != null) {
                                        ggList.clear();
                                        ggList.addAll(resultListList.get(i).value);
                                    }else{
                                        Log.e("YJH", "success-> resultListList.get(i).value == null" );
                                    }
                                } else if (resultListList.get(i).key.equals("gangchang")) {
                                    if (gcList != null  && resultListList.get(i).value != null) {
                                        gcList.clear();
                                        gcList.addAll(resultListList.get(i).value);
                                    }else{
                                        Log.e("YJH", "success-> resultListList.get(i).value == null" );
                                    }
                                } else if (resultListList.get(i).key.equals("shoulidanwei")) {
                                    if (sldwList != null  && resultListList.get(i).value != null) {
                                        sldwList.clear();
                                        sldwList.addAll(resultListList.get(i).value);
                                    }else{
                                        Log.e("YJH", "success-> resultListList.get(i).value == null" );
                                    }
                                } else if (resultListList.get(i).key.equals("xiangmu")) {
                                    if (xmmcList != null && resultListList.get(i).value != null) {
                                        xmmcList.clear();
                                        xmmcList.addAll(resultListList.get(i).value);
                                    }else{
                                        Log.e("YJH", "success-> resultListList.get(i).value == null" );
                                    }
                                } else if (resultListList.get(i).key.equals("changdu")) {
                                    if (cdList != null  && resultListList.get(i).value != null) {
                                        cdList.clear();
                                        cdList.addAll(resultListList.get(i).value);
                                    }else{
                                        Log.e("YJH", "success-> resultListList.get(i).value == null" );
                                    }
                                } else if (resultListList.get(i).key.equals("shisu")) {
                                    if (ssList != null  && resultListList.get(i).value != null) {
                                        ssList.clear();
                                        ssList.addAll(resultListList.get(i).value);
                                    }else{
                                        Log.e("YJH", "success-> resultListList.get(i).value == null" );
                                    }
                                } else if (resultListList.get(i).key.equals("caizhi")) {
                                    if (czList != null  && resultListList.get(i).value != null) {
                                        czList.clear();
                                        czList.addAll(resultListList.get(i).value);
                                    }else{
                                        Log.e("YJH", "success-> resultListList.get(i).value == null" );
                                    }
                                } else if (resultListList.get(i).key.equals("rechuli")) {
                                    if (rclList != null  && resultListList.get(i).value != null) {
                                        rclList.clear();
                                        rclList.addAll(resultListList.get(i).value);
                                    }else{
                                        Log.e("YJH", "success-> resultListList.get(i).value == null" );
                                    }
                                }
                            }
                        }
                        dimssDialog();
                    } else {
                        dimssDialog();
                        Utils.toast(QualityObjectionSubmitActivity.this, "服务器异常" + result.msg, Toast.LENGTH_SHORT);
                    }
                } else {
                    dimssDialog();
                    Utils.toast(QualityObjectionSubmitActivity.this, "服务器异常,返回为空", Toast.LENGTH_SHORT);
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
                Utils.toast(QualityObjectionSubmitActivity.this, "服务器异常", Toast.LENGTH_SHORT);
            }
        });
    }

    /**
     * 根据路径获取文件名
     *
     * @param path 路径参数
     * @return 文件名字符串
     */
    private String getFileName(String path) throws Exception {
        // 判空操作必须要有 , 处理方式不唯一 , 根据实际情况可选其一 。
        if (path == null) {
            throw new Exception("路径不能为null"); // 处理方法一
        }
        int start = path.lastIndexOf("/");
        int end = path.lastIndexOf(".");
        if (start != -1 && end != -1) {
            return path.substring(start + 1, end);//包含头不包含尾 , 故:头 + 1
        } else {
            return "";
        }
    }

}
