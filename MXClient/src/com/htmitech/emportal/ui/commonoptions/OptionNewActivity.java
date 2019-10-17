package com.htmitech.emportal.ui.commonoptions;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.htmitech.api.BookInit;
import com.htmitech.emportal.R;
import com.htmitech.emportal.base.IBaseCallback;
import com.htmitech.emportal.base.SlidingBackAcitivity;
import com.htmitech.emportal.entity.OAConText;
import com.htmitech.emportal.ui.commonoptions.data.AddUserOptionsEntity;
import com.htmitech.emportal.ui.commonoptions.data.OptionsAdd;
import com.htmitech.emportal.ui.commonoptions.data.OptionsParametersAdd;
import com.htmitech.emportal.ui.commonoptions.task.AddUserOptionsModel;

public class OptionNewActivity extends SlidingBackAcitivity implements
        OnClickListener, IBaseCallback {

    private TextView btnOptionSave;
    private EditText etOptions;
    private boolean isSuccess = false;
    ProgressDialog progressDialog;
    String app_id;

    protected int getLayoutById() {
        return R.layout.activity_optionnew;
    }

    protected void initView() {

        findViewById(R.id.imgview_titlebar_back).setOnClickListener(this);
        ((TextView) findViewById(R.id.textview_titlebar_title))
                .setText("新增常用意见");
        btnOptionSave = (TextView) findViewById(R.id.btn_option_save);
        try {
            if(null != BookInit.getInstance().getmApcUserdefinePortal() && BookInit.getInstance().getmApcUserdefinePortal().getUsing_color_style() == 3){
                btnOptionSave.setBackgroundResource(R.drawable.option_save_shape_red);
            }else{
                btnOptionSave.setBackgroundResource(R.drawable.option_save_shape);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        etOptions = (EditText) findViewById(R.id.et_Options);
        btnOptionSave.setOnClickListener(this);
        Intent intent = getIntent();
        app_id = getIntent().getStringExtra("app_id");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_option_save:
                if (etOptions.getText().toString().trim().equals("")) {
                    Toast.makeText(OptionNewActivity.this, "您输入的内容为空",
                            Toast.LENGTH_SHORT).show();
                } else {

                    // 新增意见调用
                    OptionsParametersAdd mOptionsParameters = new OptionsParametersAdd();
                    mOptionsParameters.context = OAConText
                            .getInstance(OptionNewActivity.this);
                    mOptionsParameters.app_id = app_id;
                    mOptionsParameters.option = OptionsAdd.getInstance(
                            OptionNewActivity.this, etOptions.getText().toString());
                    AddUserOptionsModel addUserOptionsModel = new AddUserOptionsModel(
                            OptionNewActivity.this);
                    addUserOptionsModel.getDataFromServerByType(
                            AddUserOptionsModel.TYPE_GET_NEW_OPTIONS,
                            mOptionsParameters);

//                    progressDialog = new ProgressDialog
//                            (OptionNewActivity.this);
//                    progressDialog.setTitle("常用意见");
//                    progressDialog.setMessage("保存中...");
//                    progressDialog.setCancelable(true);//通过back键不消失
//                    progressDialog.show();

                    showDialog();

//				new Handler().postDelayed(new Runnable() {
//					@Override
//					public void run() {
//						if (!isSuccess) {
//							Toast.makeText(OptionNewActivity.this,
//									"网络连接失败" + "请您稍后重试", 0).show();
//						}
//
//					}
//				}, 3000);

                }
                break;
            case R.id.imgview_titlebar_back:

                finish();
                break;

            default:
                break;
        }

    }

    @Override
    public void onSuccess(int requestTypeId, Object result) {
        if (requestTypeId == AddUserOptionsModel.TYPE_GET_NEW_OPTIONS) {
            if (result != null && result instanceof AddUserOptionsEntity) {
                AddUserOptionsEntity mAddUserOptionsEntity = (AddUserOptionsEntity) result;
//                progressDialog.dismiss();
                dismissDialog();
                if (mAddUserOptionsEntity.getResult() != null
                        && mAddUserOptionsEntity.getResult().getValue() != null) {
                    isSuccess = true;
                    OptionNewActivity.this.finish();
                }
            }
        }

    }

    @Override
    public void onFail(int requestTypeId, int statusCode, String errorMsg,
                       Object result) {
        // TODO Auto-generated method stub
        Toast.makeText(OptionNewActivity.this, errorMsg + "请您稍后重试", Toast.LENGTH_SHORT).show();
//        progressDialog.dismiss();
        dismissDialog();

    }
}
