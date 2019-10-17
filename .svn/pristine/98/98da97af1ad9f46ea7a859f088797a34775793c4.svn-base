package com.htmitech.emportal.ui.commonoptions;

import android.app.ProgressDialog;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.htmitech.emportal.R;
import com.htmitech.emportal.base.IBaseCallback;
import com.htmitech.emportal.base.SlidingBackAcitivity;
import com.htmitech.emportal.entity.OAConText;
import com.htmitech.emportal.ui.commonoptions.data.EditUserOptionsEntity;
import com.htmitech.emportal.ui.commonoptions.data.Options;
import com.htmitech.emportal.ui.commonoptions.data.OptionsParametersEdit;
import com.htmitech.emportal.ui.commonoptions.task.AddUserOptionsModel;

public class OptionEditActivity extends SlidingBackAcitivity implements
        OnClickListener, IBaseCallback {

    private TextView btnOptionSave;
    private EditText etOptions;
    private String id;
    private String value;
    private boolean isEdit = false;
    private String oldOptions;
    ProgressDialog progressDialog;
    String app_id;

    protected int getLayoutById() {
        return R.layout.activity_optionnew;
    }

    protected void initView() {

        findViewById(R.id.imgview_titlebar_back).setOnClickListener(this);
        ((TextView) findViewById(R.id.textview_titlebar_title))
                .setText("编辑常用意见");
        btnOptionSave = (TextView) findViewById(R.id.btn_option_save);
        etOptions = (EditText) findViewById(R.id.et_Options);
        btnOptionSave.setOnClickListener(this);

        Intent intent = getIntent();
        String data = intent.getStringExtra("extra_data");
        app_id = getIntent().getStringExtra("app_id");
        String[] mItemArray = org.apache.commons.lang3.StringUtils.split(data,
                "|");
        id = mItemArray[0];
        value = mItemArray[1];
        etOptions.setText(value);
        oldOptions = value;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_option_save:
                if (etOptions.getText().toString().trim().equals("")) {
                    Toast.makeText(OptionEditActivity.this, "您输入的内容为空",
                            Toast.LENGTH_SHORT).show();
                } else if (etOptions.getText().toString().equals(oldOptions)) {
                    Toast.makeText(OptionEditActivity.this, "您的意见尚未修改，请编辑您的意见",
                            Toast.LENGTH_SHORT).show();

                } else {

                    // 修改意见调用
                    OptionsParametersEdit mOptionsParameters = new OptionsParametersEdit();
                    mOptionsParameters.context = OAConText
                            .getInstance(OptionEditActivity.this);
                    mOptionsParameters.app_id = app_id;
                    mOptionsParameters.option = Options.getInstance(
                            OptionEditActivity.this, id, etOptions.getText()
                                    .toString());
                    AddUserOptionsModel addUserOptionsModel = new AddUserOptionsModel(
                            OptionEditActivity.this);
                    addUserOptionsModel.getDataFromServerByType(
                            AddUserOptionsModel.TYPE_GET_EDIT_OPTIONS,
                            mOptionsParameters);
                    progressDialog = new ProgressDialog
                            (OptionEditActivity.this);
                    progressDialog.setTitle("常用意见");
                    progressDialog.setMessage("保存中...");
                    progressDialog.setCancelable(true);//通过back键不消失
                    progressDialog.show();

//				new Handler().postDelayed(new Runnable() {
//					@Override
//					public void run() {
//						if(!isEdit){
//							Toast.makeText(OptionEditActivity.this, "网络连接失败" + "请您稍后重试", 0)
//							.show();
//						}
//						
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
        if (requestTypeId == AddUserOptionsModel.TYPE_GET_EDIT_OPTIONS) {
            if (result != null && result instanceof EditUserOptionsEntity) {
                EditUserOptionsEntity mEditUserOptionsEntity = (EditUserOptionsEntity) result;
                if (mEditUserOptionsEntity.getResult() != null) {
                    isEdit = mEditUserOptionsEntity.getResult();
                    progressDialog.dismiss();
                    if (isEdit) {
                        OptionEditActivity.this.finish();
                    }

                }

            }
        }

    }

    @Override
    public void onFail(int requestTypeId, int statusCode, String errorMsg,
                       Object result) {
        // TODO Auto-generated method stub
        Toast.makeText(OptionEditActivity.this, errorMsg + "请您稍后重试", Toast.LENGTH_SHORT)
                .show();
        progressDialog.dismiss();
    }
}
