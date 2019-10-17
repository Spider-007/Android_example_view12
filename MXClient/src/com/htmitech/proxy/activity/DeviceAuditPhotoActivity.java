package com.htmitech.proxy.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.htmitech.api.BookInit;
import com.htmitech.emportal.R;
import com.htmitech.emportal.entity.OAConText;
import htmitech.com.componentlibrary.listener.ObserverCallBackType;
import com.htmitech.proxy.doman.DeviceApplyInfo;
import com.htmitech.proxy.doman.DeviceApplyparameter;
import com.htmitech.proxy.doman.DeviceUserListResultItem;
import com.htmitech.proxy.doman.GetPictureResult;
import com.htmitech.proxy.doman.PictureInfo;
import com.htmitech.proxy.myenum.DeviceStateEnum;
import com.htmitech.proxy.myenum.LogManagerEnum;
import com.htmitech.proxy.util.FileSizeUtil;
import com.htmitech.proxy.util.ImageCompressUtil;
import com.htmitech.proxy.util.ImageUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.htmitech.base.BaseFragmentActivity;

import com.htmitech.thread.AnsynHttpRequest;
import com.htmitech.thread.CHTTP;
import com.htmitech.thread.GetFinalRefreshTokenAfterRequestValue;

import htmitech.com.componentlibrary.unit.ServerUrlConstant;

/**
 * Created by Think on 2017/7/31.
 */

public class DeviceAuditPhotoActivity extends BaseFragmentActivity implements View.OnClickListener, ObserverCallBackType {

    private static final String TAG = "DeviceAuditPhotoActivit";
    private TextView tv_device_description;
    private LinearLayout ll_add_photo;
    private ImageView iv_photo_detail;

    private String imageFilePath;//临时拍照路径
    private String app_id = "";
    private String deviceMessage = "Lenovo K30-T";

    private TextView title_name;
    private TextView title_left_text_button;
    private TextView title_right_text_button;
    private ImageButton title_left_button;


    private File mFileTemp;

    private static final String HTTPTYPEPICTUTR = "getDicPicture";
    private static final String HTTPAPPLYPIC = "getApplyPicture";
    private DeviceUserListResultItem mDeviceUserListResultItem;
    private String getPictureUrl = "";
    private String getApplylyUrl = "";
    private GetPictureResult mGetPictureResult;
    private Gson mGson = new Gson();
    private PictureInfo mPictureInfo;
    private String deviceDescription;
    private DeviceApplyparameter mDeviceApplyparameter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_audit_photo);
        initView();
        initData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10005) {
            if (resultCode == Activity.RESULT_OK) {
                ll_add_photo.setVisibility(View.GONE);
                iv_photo_detail.setVisibility(View.VISIBLE);
                showDialog();
                setDialogValue("图片加载中...");
                setCanceledOnTouchOutside(false);
                final Date date = new Date(System.currentTimeMillis());
                final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                mFileTemp = new File(imageFilePath);
                ImageCompressUtil.with(DeviceAuditPhotoActivity.this).load(mFileTemp).setCompressListener(new ImageCompressUtil.onCompressFileCallBack() {
                    @Override
                    public void onCompressStart() {

                    }

                    @Override
                    public void onCompressFileResult(File file) {
                        Bitmap bm = BitmapFactory.decodeFile(file.getPath()).copy(Bitmap.Config.ARGB_8888, true);
                        String text = dateFormat.format(date) + "|" + deviceMessage + "|" + OAConText.getInstance(DeviceAuditPhotoActivity.this).login_name + " " + OAConText.getInstance(DeviceAuditPhotoActivity.this).UserName;
                        Bitmap watermark = ImageUtil.drawTextToRightBottom(DeviceAuditPhotoActivity.this, bm, text, 40, Color.parseColor("#DBB011"), 20, 10);
                        mFileTemp = new File(imageFilePath);
                        ImageUtil.saveBitmap(mFileTemp, watermark);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dismissDialog();
                                Glide.with(DeviceAuditPhotoActivity.this).load(mFileTemp).
                                        placeholder(com.htmitech.addressbook.R.drawable.pictures_no).
                                        error(com.htmitech.addressbook.R.drawable.pictures_no).into(iv_photo_detail);
                            }
                        });
                    }

                    @Override
                    public void onCompressError(Exception e) {

                    }
                }).launch();


            }
        }
    }

    private void initView() {
        tv_device_description = (TextView) findViewById(R.id.tv_device_description);
        title_name = (TextView) findViewById(R.id.title_name);
        title_left_text_button = (TextView) findViewById(R.id.title_left_text_button);
        title_right_text_button = (TextView) findViewById(R.id.title_right_text_button);
        title_left_button = (ImageButton) findViewById(R.id.title_left_button);
        title_left_button.setVisibility(View.GONE);
        ll_add_photo = (LinearLayout) findViewById(R.id.ll_add_photo);
        iv_photo_detail = (ImageView) findViewById(R.id.iv_photo_detail);
        ll_add_photo.setOnClickListener(this);
        iv_photo_detail.setOnClickListener(this);
        title_left_text_button.setVisibility(View.GONE);
        title_right_text_button.setVisibility(View.VISIBLE);
        title_left_text_button.setOnClickListener(this);
        title_right_text_button.setOnClickListener(this);

    }

    private void initData() {
        title_name.setText("上传照片");
        Intent intent = getIntent();
        mDeviceUserListResultItem = (DeviceUserListResultItem) intent.getSerializableExtra("deviceUserListResultItem");
        if (mDeviceUserListResultItem != null) {
            deviceMessage = mDeviceUserListResultItem.deviceName;
        }
        app_id = intent.getStringExtra("app_id");
        if (mDeviceUserListResultItem != null) {
            deviceDescription = "您的设备" + DeviceStateEnum.getTypeName(Integer.parseInt(mDeviceUserListResultItem.applyType)) + "申请已经成功发送，需要管理员审核批准后才可生效。\n" +
                    "· 为了更快的让管理员确认您的身份，您可以选择上传一张自拍照片\n" +
                    "· 您的照片只用于" + DeviceStateEnum.getTypeName(Integer.parseInt(mDeviceUserListResultItem.applyType)) + "审核，不会被用作其他用途。\n" +
                    "· 此操作非必选项，您也可以直接点击完成。";
        }
        tv_device_description.setText(deviceDescription);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.ll_add_photo:
                toPhoto();
                break;
            case R.id.iv_photo_detail:
                toPhoto();
                break;
            case R.id.title_left_text_button:
                finish();
                break;
            case R.id.title_right_text_button:
                if (mFileTemp == null) {
                    finish();
                    return;
                }
                showDialog();
                setDialogValue("图片保存...");
                setCanceledOnTouchOutside(false);
                getPictureUrl = ServerUrlConstant.SERVER_EMPAPI_URL() + ServerUrlConstant.FilecontrollerPicture;
                AnsynHttpRequest.requestByPostWithToken(this, mFileTemp, getPictureUrl, CHTTP.POSTWITHTOKEN, this, HTTPTYPEPICTUTR, LogManagerEnum.GGENERAL.getFunctionCode());

                break;
        }

    }

    public void toPhoto() {
        imageFilePath = FileSizeUtil.getTempPhotoFileName(OAConText.getInstance(DeviceAuditPhotoActivity.this).UserID, BookInit.getInstance().getPortalId(), app_id);
        File temp = new File(imageFilePath);
        Uri imageFileUri = Uri.fromFile(temp);//获取文件的Uri
        Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//跳转到相机Activity
        it.putExtra("android.intent.extras.CAMERA_FACING", 1); // 调用前置摄像头
        it.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);//告诉相机拍摄完毕输出图片到指定的Uri
        startActivityForResult(it, 10005);
    }

    @Override
    public void success(String requestValue, int type, String requestName) {
        if (requestName.equals(HTTPTYPEPICTUTR)) {
            requestValue = GetFinalRefreshTokenAfterRequestValue.getFinalRequestValue(this, requestValue, type, getPictureUrl, mFileTemp, this, HTTPTYPEPICTUTR, LogManagerEnum.GGENERAL.getFunctionCode());
            if (requestValue != null && !requestValue.equals("")) {
                mGetPictureResult = mGson.fromJson(requestValue.toString(), GetPictureResult.class);
                mPictureInfo = mGetPictureResult.getResult();
                Log.d(TAG, "success: " + requestValue);
                setDialogValue("图片上传中...");
                getApplylyUrl = ServerUrlConstant.SERVER_EMPAPI_URL() + ServerUrlConstant.GetDeviceSaveApplyPic;
                mDeviceApplyparameter = new DeviceApplyparameter();
                mDeviceApplyparameter.setApplyId(mDeviceUserListResultItem.applyId);
                mDeviceApplyparameter.setPictureId(mPictureInfo.getPictureId() + "");
                AnsynHttpRequest.requestByPostWithToken(this, mDeviceApplyparameter, getApplylyUrl, CHTTP.POSTWITHTOKEN, this, HTTPAPPLYPIC, LogManagerEnum.GGENERAL.getFunctionCode());

            } else {
                Log.d(TAG, "success: " + requestValue);
                return;
            }

        } else if (requestName.equals(HTTPAPPLYPIC)) {
            requestValue = GetFinalRefreshTokenAfterRequestValue.getFinalRequestValue(this, requestValue, type, getApplylyUrl, mDeviceApplyparameter, this, HTTPAPPLYPIC, LogManagerEnum.GGENERAL.getFunctionCode());
            if (requestValue != null && !requestValue.equals("")) {
                dismissDialog();
                DeviceApplyInfo mDeviceApplyInfo = mGson.fromJson(requestValue, DeviceApplyInfo.class);
                if (mDeviceApplyInfo.getCode() == 200) {
                    Toast.makeText(DeviceAuditPhotoActivity.this, mDeviceApplyInfo.getMessage(), Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(DeviceAuditPhotoActivity.this, mDeviceApplyInfo.getMessage(), Toast.LENGTH_SHORT).show();
                }
            } else {
                Log.d(TAG, "success: " + requestValue);
                return;
            }
        }

    }

    @Override
    public void fail(String exceptionMessage, int type, String requestName) {
        if (requestName.equals(HTTPTYPEPICTUTR)) {
            Toast.makeText(DeviceAuditPhotoActivity.this, "图片保存失败：" + exceptionMessage, Toast.LENGTH_SHORT).show();
        } else if (requestName.equals(HTTPAPPLYPIC)) {
            Toast.makeText(DeviceAuditPhotoActivity.this, "图片上传失败：" + exceptionMessage, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void notNetwork() {

    }

    @Override
    public void callbackMainUI(String successMessage) {

    }

}
