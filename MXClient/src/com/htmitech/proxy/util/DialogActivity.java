package com.htmitech.proxy.util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.htmitech.emportal.R;
import com.htmitech.emportal.service.UpdateService;
import com.htmitech.emportal.utils.CacheDeleteUtils;
import com.htmitech.proxy.doman.AppclientVersion;

import java.io.File;

import cn.feng.skin.manager.base.BaseFragmentActivity;
import htmitech.com.componentlibrary.unit.PreferenceUtils;
import htmitech.com.componentlibrary.unit.SecuritySharedPreference;

public class DialogActivity extends BaseFragmentActivity implements View.OnClickListener {

    private TextView dialogTitle;
    private TextView dialog_message;
    private TextView cancel;
    private TextView confirm;
    private AppclientVersion mUpdateInfo;
    String path="";
    private View line;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        Intent intent = getIntent();
        mUpdateInfo = (AppclientVersion)intent.getSerializableExtra("result");
//        DialogActivity.this.setFinishOnTouchOutside(false);
        dialogTitle = (TextView)findViewById(R.id.dialog_title);
        dialog_message = (TextView)findViewById(R.id.dialog_message);
        cancel = (TextView)findViewById(R.id.cancel);
        confirm = (TextView)findViewById(R.id.confirm);
        line = (View)findViewById(R.id.line);

        cancel.setOnClickListener(this);
        confirm.setOnClickListener(this);
        if(null != mUpdateInfo){
            dialogTitle.setText(mUpdateInfo.getVersion_name()+"版本");
            dialog_message.setText(mUpdateInfo.getUpdate_desc());
        }
        if(mUpdateInfo != null && mUpdateInfo.getMustupdated() == 2){
            cancel.setVisibility(View.GONE);
            DialogActivity.this.setFinishOnTouchOutside(false);
            line.setVisibility(View.GONE);
        }
    }
    @Override
    public boolean onKeyDown(int keyCode,KeyEvent event){
        if(mUpdateInfo != null && mUpdateInfo.getMustupdated() == 2){
            if(keyCode==KeyEvent.KEYCODE_BACK)
                return true;//不执行父类点击事件
        }
        return super.onKeyDown(keyCode, event);//继续执行父类其他点击事件
    }
    @Override
    public void onClick(View v) {
        SecuritySharedPreference securitySharedPreference = new SecuritySharedPreference(getApplicationContext(), PreferenceUtils.PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        SecuritySharedPreference.SecurityEditor securityEditor = securitySharedPreference.edit();
        securityEditor.putBoolean("ischeckupgrade", false);
        securityEditor.apply();
        switch (v.getId()){
            case R.id.cancel:
                finish();
                break;
            case R.id.confirm:
                if(mUpdateInfo != null && mUpdateInfo.resetClient == 1){
                    if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                        path = Environment.getExternalStorageDirectory().getPath()+ File.separator+"htmitech";
                    }
                     if(!"".equals(path)){
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                CacheDeleteUtils.clearFiles(path);
                            }
                        }).start();
                    }
                }
                Intent updateIntents = new Intent(DialogActivity.this,
                        UpdateService.class);
                updateIntents.putExtra("url", mUpdateInfo.getDownload_url());
                updateIntents.putExtra("app_name",
                        DialogActivity.this.getPackageName());
                startService(updateIntents);
                finish();
                break;
        }
    }
}
