package com.htmitech.proxy.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.htmitech.app.Constant;
import com.htmitech.base.BaseFragmentActivity;
import com.htmitech.emportal.R;
import com.htmitech.others.LoadUserAvatar;

import java.io.File;

import htmitech.com.componentlibrary.SuperFileView;
import com.htmitech.app.widget.PhotoView;

/**
 * 通用的打开File的方式，本地打开，如PDF，Word，TXT
 *
 *
 * 图片可以穿URL链接 也可以穿本地文件路径
 *
 * 其他的如本地的PDF WORD TXT等文件必须传递本地的文件
 *
 * tony
 *
 */

public class GeneralFileViewActivity extends BaseFragmentActivity {

    private String filePath = "/storage/emulated/0/Android_0101.docx";

    private TextView tvName;

    private SuperFileView mSuperFileView;

    private PhotoView mPhotoView;

    private LoadUserAvatar avatarLoader;

    private ImageButton title_left_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_fileview);

        avatarLoader = new LoadUserAvatar(GeneralFileViewActivity.this, Constant.SDCARD_PATH);
        Intent intent = this.getIntent();
        String path = (String) intent.getSerializableExtra("path");
        if (!TextUtils.isEmpty(path)) {
            setFilePath(path);
        }

        initView();
        initData();
    }

    private void getFilePathAndShowFile(SuperFileView mSuperFileView) {


        if (getFilePath().contains("http")) {//网络地址要先下载

            String fileType = getFileType(getFilePath()).trim().toLowerCase();
            tvName.setText("图片");
            if (!fileType.matches("^image/(jpg|png|jpeg)$")){
                mPhotoView.setVisibility(View.VISIBLE);
                Bitmap bitmap = avatarLoader.loadImage(mPhotoView,
                        getFilePath(), new LoadUserAvatar.ImageDownloadedCallBack() {

                            @Override
                            public void onImageDownloaded(
                                    ImageView imageView, Bitmap bitmap) {
                                if (imageView.getTag() == getFilePath()) {
                                    imageView.setImageBitmap(bitmap);
                                }
                            }

                        });

                if (bitmap != null) {
                    mPhotoView.setImageBitmap(bitmap);
                }
            }

        } else {
            mSuperFileView.setVisibility(View.VISIBLE);
            File file = new File(getFilePath());
            tvName.setText(getFileName(file.getName()));
            mSuperFileView.displayFile(file);
        }

        title_left_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ChangeLogin mChangeLogin = new ChangeLogin();
//                mChangeLogin.setClent(SettingActivity.this);
                GeneralFileViewActivity.this.finish();
            }
        });
    }

    private void initView(){
        tvName = (TextView) findViewById(R.id.title_name);
        mSuperFileView = (SuperFileView) findViewById(R.id.mSuperFileView);
        mPhotoView = (PhotoView) findViewById(R.id.iv_photo);
        title_left_button = (ImageButton) findViewById(R.id.title_left_button);
    }

    private void initData(){
        mSuperFileView.setOnGetFilePathListener(new SuperFileView.OnGetFilePathListener() {
            @Override
            public void onGetFilePath(SuperFileView mSuperFileView2) {
                getFilePathAndShowFile(mSuperFileView2);
            }
        });

        mSuperFileView.show();
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }


    private String getFileName(String fileName){
        int i = fileName.lastIndexOf('.');
        if(i == -1){
            i = fileName.length() - 1;
        }
        return fileName.substring(0,i);
    }


    /***
     * 获取文件类型
     *
     * @param paramString
     * @return
     */
    private String getFileType(String paramString) {
        String str = "";

        if (TextUtils.isEmpty(paramString)) {
            return str;
        }
        int i = paramString.lastIndexOf('.');
        if (i <= -1) {
            return str;
        }


        str = paramString.substring(i + 1);
        return str;
    }

    @Override
    protected void onDestroy() {
        if (mSuperFileView != null) {
            mSuperFileView.onStopDisplay();
        }
        super.onDestroy();
    }
}
