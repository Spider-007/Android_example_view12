package com.htmitech.ztcustom.zt.chinarailway;

import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.base.BaseFragmentActivity;
import com.htmitech.ztcustom.zt.constant.ContantValues;
import com.htmitech.ztcustom.zt.constant.RegisteRequest;
import com.htmitech.ztcustom.zt.constant.RegisterBean;
import com.htmitech.ztcustom.zt.interfaces.ObserverCallBack;
import com.htmitech.ztcustom.zt.thread.AnsynHttpRequest;
import com.htmitech.ztcustom.zt.unit.CHTTP;
import com.htmitech.ztcustom.zt.util.FastJsonUtils;


/**
 * 注册
 */

public class RegisteActivity extends BaseFragmentActivity {

    private ImageButton imgBack;
    private Button btnCommit;
    private EditText etUsername;
    private EditText etMobile;
    private EditText etCorpname;
    private EditText etDeptname;
    private EditText etTitle;
    private EditText etOfficephone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registe);
        initView();
    }


    private void initView() {
        imgBack = (ImageButton) findViewById(R.id.ib_user_registe_back);
        etUsername = (EditText) findViewById(R.id.et_username);
        etMobile = (EditText) findViewById(R.id.et_mobile);
        etCorpname = (EditText) findViewById(R.id.et_corpname);
        etDeptname = (EditText) findViewById(R.id.et_deptname);
        etTitle = (EditText) findViewById(R.id.et_title);
        etOfficephone = (EditText) findViewById(R.id.et_officephone);
        btnCommit = (Button) findViewById(R.id.btn_commit);
        btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        etOfficephone.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(RegisteActivity.this.getCurrentFocus()
                                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    getData();
            }
                return false;
            }
        });
    }

    private void getData() {
        String userName = etUsername.getText().toString().trim();
        String mobile = etMobile.getText().toString().trim();
        String corpname = etCorpname.getText().toString().trim();
        String deptname = etDeptname.getText().toString().trim();
        String title = etTitle.getText().toString().trim();
        String officephone = etOfficephone.getText().toString().trim();
        if(TextUtils.isEmpty(userName)){
             Toast.makeText(this,"请输入姓名",Toast.LENGTH_SHORT).show();
             return;
        }else if(TextUtils.isEmpty(mobile)){
            Toast.makeText(this,"请输入手机号",Toast.LENGTH_SHORT).show();
            return;
        }else if(TextUtils.isEmpty(corpname)){
            Toast.makeText(this,"请输入所属单位",Toast.LENGTH_SHORT).show();
            return;
        }else if(TextUtils.isEmpty(deptname)){
            Toast.makeText(this,"请输入部门",Toast.LENGTH_SHORT).show();
            return;
        }

//        else if(!isTelPhoneNumber(mobile)){
//            Toast.makeText(this,"手机号格式错误",Toast.LENGTH_SHORT).show();
//            return;
//        }

        showProgressDialog(this);
        RegisteRequest request = new RegisteRequest();
        request.userName = userName;
        request.mobilePhone = mobile;
        request.corpName = corpname;
        request.deptName = deptname;
        request.positionName = title;
        request.officePhone = officephone;
        request.sourceType = "1";//'来源类型.1:Android移动端,2:iOS移动端,3:EMPM端,4:第三方系统导入,9:其它'
        request.sourceInfo =  ((TelephonyManager) this.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
        AnsynHttpRequest.request(this, request, ContantValues.USERREGISTE,
                CHTTP.POST, new ObserverCallBack() {

                    @Override
                    public void success(String successMessage) {
                        dimssDialog();
                        RegisterBean person = FastJsonUtils.getPerson(successMessage, RegisterBean.class);
                        if(person != null){
                            if(person.code == 200){
                                dimssDialog();
                                Toast.makeText(RegisteActivity.this,person.message,Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                        finish();

                    }

                    @Override
                    public void fail(String exceptionMessage) {
                        Toast.makeText(RegisteActivity.this,"数据请求失败!",Toast.LENGTH_SHORT).show();
                        dimssDialog();
                        finish();
                    }

                    @Override
                    public void notNetwork() {
                        Toast.makeText(RegisteActivity.this,"网络异常!",Toast.LENGTH_SHORT).show();
                        dimssDialog();
                        finish();
                    }
                });
    }


//    public static boolean isTelPhoneNumber(String value) {
//        if (value != null && value.length() == 11) {
//            Pattern pattern = Pattern.compile("^1[3|4|5|6|7|8][0-9]\\d{8}$");
//            Matcher matcher = pattern.matcher(value);
//            return matcher.matches();
//        }else{
//            return false;
//        }
//
//    }



}
