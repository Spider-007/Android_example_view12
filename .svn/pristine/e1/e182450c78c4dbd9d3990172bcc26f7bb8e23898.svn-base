package com.htmitech.addressbook;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.htmitech.api.BookInit;
import com.htmitech.base.BaseFragmentActivity;
import com.htmitech.domain.OrgUser;
import com.htmitech.domain.PeopleMessage;
import com.htmitech.domain.SYS_User;
import com.htmitech.listener.CallBackRequestListener;
import com.minxing.kit.ui.chat.vh.send.SendFileViewHolder;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by htrf-pc on 2016/6/3.
 */
public class PeopleMessageEditActivity extends BaseFragmentActivity implements View.OnClickListener, CallBackRequestListener {
    private EditText et_value;
    private TextView tv_title;
    private TextView tv_save;
    private ImageView btn_daiban_person;
    private OrgUser orgUser;
    private String title;
    private String fieldName;
    private ProgressBar progress_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ht_activity_people_message_edit);

        initView();
        initDate();
    }

    public void initView() {
        btn_daiban_person = (ImageView) findViewById(R.id.btn_daiban_person);
        et_value = (EditText) findViewById(R.id.et_value);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_save = (TextView) findViewById(R.id.tv_save);
        progress_ = (ProgressBar) findViewById(R.id.progress_);
    }

    public void initDate() {
        Intent mIntent = getIntent();
        title = mIntent.getStringExtra("title");
        fieldName = mIntent.getStringExtra("fieldName");
        String value = mIntent.getStringExtra("value");
        orgUser = (OrgUser) mIntent.getSerializableExtra("orgUser");
        et_value.setText(value + "");
        tv_title.setText("" + title);
        btn_daiban_person.setOnClickListener(this);
        tv_save.setOnClickListener(this);
        if (fieldName.equals("birthday")) {
            et_value.setFocusable(false);
            et_value.setTag(fieldName);
            et_value.setFocusableInTouchMode(false);
            et_value.setOnClickListener(this);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    PeopleMessageEditActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            BookInit.getInstance().getmCallbackMX().settingData(PeopleMessageEditActivity.this, et_value);
                        }
                    });
                }
            }).start();

        } else if (fieldName.equals("gender")) {
            et_value.setFocusable(false);
            et_value.setTag(fieldName);
            et_value.setFocusableInTouchMode(false);
            et_value.setOnClickListener(this);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    PeopleMessageEditActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            BookInit.getInstance().getmCallbackMX().settingGender(PeopleMessageEditActivity.this, et_value);
                        }
                    });
                }
            }).start();
        }else if(fieldName.equals("mobile_phone") || fieldName.equals("office_phone") || fieldName.equals("home_phone")){
            et_value.setInputType(InputType.TYPE_CLASS_PHONE);
            et_value.setKeyListener(DigitsKeyListener.getInstance("1234567890-"));
        }else if(fieldName.equals("postal_code")){
            et_value.setInputType(InputType.TYPE_CLASS_PHONE);
            et_value.setKeyListener(DigitsKeyListener.getInstance("1234567890-"));
        }
        BookInit.getInstance().setCallBackRequestListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_daiban_person) {
            this.finish();
        } else if (v.getId() == R.id.tv_save) {
            String value = et_value.getText().toString();
            try {
                Field f = orgUser.getClass().getDeclaredField(fieldName); //修改对应属性的值

                f.setAccessible(true);

                if (fieldName.equals("email")) {
                    if (!isEmail(value)) {
                        Toast.makeText(PeopleMessageEditActivity.this, "输入格式错误，请重新输入", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }if(fieldName.equals("mobile_phone") || fieldName.equals("office_phone") || fieldName.equals("home_phone")){
                    if (!isPhoneNumberValid(value) && !isHomeNumberValid(value)) {
                        Toast.makeText(PeopleMessageEditActivity.this, "输入格式错误，请重新输入", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }else{
                    if(value.contains("/>")){
                        Toast.makeText(PeopleMessageEditActivity.this, "输入不合法，请重新输入", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                if (fieldName.equals("gender")) {
                    if (value.equals("女")) {
                        value = "2";
                    } else if (value.equals("男")) {
                        value = "1";
                    } else {
                        value = "0";
                    }
                    f.set(orgUser, Integer.parseInt(value));
                } else {
                    f.set(orgUser, value);
                }


            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            orgUser.setEfs1("");
            progress_.setVisibility(View.VISIBLE);
            BookInit.getInstance().getmCallbackMX().callSavePeopleMessage(orgUser, 1,"");  //回调主页给主函数进行网络操作
        } else if (v.getTag().toString().equals("birthday")) {
            BookInit.getInstance().getmCallbackMX().settingData(PeopleMessageEditActivity.this, et_value);
        } else if (v.getTag().toString().equals("gender")) {
            BookInit.getInstance().getmCallbackMX().settingGender(PeopleMessageEditActivity.this, et_value);
        }
    }

    //判断email格式是否正确
    public boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);

        return m.matches();
    }

    public static boolean isHomeNumberValid(String homeNumber){
        boolean isValid = false;
        String homeStr = "^(0[0-9]{2,3}/-)?([2-9][0-9]{6,7})+(/-[0-9]{1,4})?$";
        CharSequence inputStr = homeNumber;

        Pattern pattern = Pattern.compile(homeStr);

        Matcher matcher = pattern.matcher(inputStr);

        if (matcher.matches() ) {
            isValid = true;
        }

        return isValid;
    }

    public static boolean isPhoneNumberValid(String phoneNumber) {
        boolean isValid = false;
        String expression = "((^(13|15|18|14|17)[0-9]{9}$)|(^0[1,2]{1}\\d{1}-?\\d{8}$)|(^0[3-9] {1}\\d{2}-?\\d{7,8}$)|(^0[1,2]{1}\\d{1}-?\\d{8}-(\\d{1,4})$)|(^0[3-9]{1}\\d{2}-? \\d{7,8}-(\\d{1,4})$))";
        CharSequence inputStr = phoneNumber;

        Pattern pattern = Pattern.compile(expression);

        Matcher matcher = pattern.matcher(inputStr);

        if (matcher.matches() ) {
            isValid = true;
        }

        return isValid;

    }

    @Override
    public void classBackRequstPeopleOnSuccess() {
        BookInit.getInstance().setOrgUser(orgUser);
        progress_.setVisibility(View.GONE);
        Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
        this.finish();
    }

    @Override
    public void classBackRequstPeopleOnFail() {
//        BookInit.getInstance().setRequestUser(null);
        BookInit.getInstance().setBitmap(null);
        progress_.setVisibility(View.GONE);
        Toast.makeText(this, "修改失败", Toast.LENGTH_SHORT).show();
        this.finish();
    }


}
