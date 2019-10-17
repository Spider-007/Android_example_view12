package com.htmitech.emportal.ui.detail;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.htmitech.emportal.HtmitechApplication;
import com.htmitech.emportal.R;
import com.htmitech.emportal.base.IBaseCallback;
import com.htmitech.emportal.base.SlidingBackAcitivity;
import com.htmitech.emportal.common.AppPreferenceHelper;
import com.htmitech.emportal.common.PreferenceKeys;
import com.htmitech.emportal.entity.OAConText;
import com.htmitech.emportal.ui.commonoptions.OptionEditActivity;
import com.htmitech.emportal.ui.commonoptions.OptionNewActivity;
import com.htmitech.emportal.ui.commonoptions.OptionSelectAdapter;
import com.htmitech.emportal.ui.commonoptions.data.AddUserOptionsResult;
import com.htmitech.emportal.ui.commonoptions.data.DelUserOptionsEntity;
import com.htmitech.emportal.ui.commonoptions.data.OptionsParameters;
import com.htmitech.emportal.ui.commonoptions.data.Optionsdel;
import com.htmitech.emportal.ui.commonoptions.task.AddUserOptionsModel;
import com.htmitech.emportal.ui.login.data.logindata.UserOptionListEntity;
import com.htmitech.emportal.ui.login.model.task.LoginModel;

/**
 * 意见输入界面
 */
public class OpinionInputActivity extends SlidingBackAcitivity implements
        OnClickListener, IBaseCallback, OptionSelectAdapter.Callback {

    private EditText mEditText_input;
    private Spinner mSpinner;
    private TextView mTextViewOk;
    private ImageView mImage_mute;
    private RelativeLayout rl_opinion;
    private RelativeLayout rl_opintion_always;
    private ArrayList<String> mItem = null;

    private ArrayAdapter<String> adapter = null;
    private String returnedData = null;
    private Intent intent;
    private String app_id;
    private String parent_app_id;
    boolean is2004 = false;
    String textValue = "";

    //常用意见
    private ListView options_listview;
    private TextView tv_newoptions;
    private List<AddUserOptionsResult> list;
    private OptionSelectAdapter optionSelectAdapter;
    private boolean isDel = false;
    private int viewId;
    private String datas;
    private boolean isSuccess = false;
    private View mEmptyView;
    private ImageView iv_no_messages;
    private TextView text_no_messages;
    private int com_workflow_mobileconfig_include_options;


    protected int getLayoutById() {
        return R.layout.activity_optioninput;
    }

    /**
     * 初始化UI
     */
    protected void initView() {

        String userOpinion = AppPreferenceHelper.getInstance(this).getString(
                PreferenceKeys.KEY_OPINION_SAVE, "同意|拟同意");
        String[] mItemArray = org.apache.commons.lang3.StringUtils.split(
                userOpinion, "|");

        mItem = new ArrayList<String>();
        for (int i = 0; i < mItemArray.length; i++) {
            mItem.add(mItemArray[i]);
        }

        findViewById(R.id.imgview_titlebar_back).setOnClickListener(this);
        rl_opinion = (RelativeLayout) findViewById(R.id.rl_opinion);
        rl_opinion.setOnClickListener(this);
        rl_opintion_always = (RelativeLayout) findViewById(R.id.rl_option_always);
        mTextViewOk = (TextView) findViewById(R.id.textview_titlebar_more);
        mTextViewOk.setText("确定");
        mTextViewOk.setVisibility(View.VISIBLE);
        String appShortName = getIntent().getStringExtra("appShortName");
        if (TextUtils.isEmpty(appShortName)) {
            appShortName = "审批意见";
        }
        ((TextView) findViewById(R.id.textview_titlebar_title)).setText(appShortName);
        mTextViewOk.setOnClickListener(this);
        mEditText_input = (EditText) findViewById(R.id.edittext_optioninput_input);
        mSpinner = (Spinner) findViewById(R.id.spinner_optioninput_input);
        /*
         * String userOpinion = AppPreferenceHelper.getInstance(this)
		 * .getString(PreferenceKeys.KEY_OPINION_SAVE, "同意|拟同意"); mItems =
		 * org.apache.commons.lang3.StringUtils.split(userOpinion, "|");
		 */
        adapter = new ArrayAdapter<String>(this, R.layout.layout_spinner_item,
                mItem);
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                mEditText_input.setText(mItem.get(position));
                // ArrayList<String> a = new ArrayList<String>();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        mImage_mute = (ImageView) findViewById(R.id.image_mute);
        mImage_mute.setOnClickListener(this);

        Intent intent = getIntent();
        app_id = getIntent().getStringExtra("app_id");
        textValue = getIntent().getStringExtra("textValue");
        parent_app_id = getIntent().getStringExtra("parent_app_id");
        com_workflow_mobileconfig_include_options = getIntent().getIntExtra("com_workflow_mobileconfig_include_options", 0);
        if (com_workflow_mobileconfig_include_options == 1) {
            rl_opintion_always.setVisibility(View.GONE);
        } else if (com_workflow_mobileconfig_include_options == 0) {
            rl_opintion_always.setVisibility(View.VISIBLE);
        }
        if (!TextUtils.isEmpty(parent_app_id)) {

            app_id = parent_app_id;
        }
        is2004 = getIntent().getBooleanExtra("is2004", false);
        datas = intent.getStringExtra("extra_datas");
        if (datas != null && datas.equals("ClientTabActivity")) {
            mEditText_input.setVisibility(View.GONE);
            mTextViewOk.setVisibility(View.GONE);
        }
        mEditText_input.setText(textValue);
        if (is2004) {
            rl_opinion.setVisibility(View.GONE);
        }

        if (com_workflow_mobileconfig_include_options == 1) {

        }

        //常用意见
        list = new ArrayList<AddUserOptionsResult>();
        // if (list.size()==0) {
        // mEmptyView = View.inflate(OptionSelectActivity.this,
        // R.layout.activity_empty, null);
        // }
        options_listview = (ListView) findViewById(R.id.options_listview);
        tv_newoptions = (TextView) findViewById(R.id.tv_newoptions);
        iv_no_messages = (ImageView) findViewById(R.id.iv_no_messages);
        text_no_messages = (TextView) findViewById(R.id.text_no_messages);
        tv_newoptions.setOnClickListener(this);
        if (list != null) {
            iv_no_messages.setVisibility(View.GONE);
            text_no_messages.setVisibility(View.GONE);
        }
        optionSelectAdapter = new OptionSelectAdapter(OpinionInputActivity.this, list,
                OpinionInputActivity.this);
        options_listview.setAdapter(optionSelectAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        LoginModel loginModel = new LoginModel(OpinionInputActivity.this);
        loginModel
                .getDataFromServerByType(
                        LoginModel.TYPE_GetUserOptions,
                        OAConText.getInstance(HtmitechApplication.getInstance()).UserID + "&app_id=" + app_id);
        showDialog();
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        // TODO Auto-generated method stub
//        super.onActivityResult(requestCode, resultCode, data);
//
//        switch (requestCode) {
//            case 1:
//                if (resultCode == RESULT_OK) {
//
//                    returnedData = data.getStringExtra("data1_return");
//                    if (returnedData != null)
//                        mEditText_input.setText(returnedData);
//                }
//                break;
//            default:
//        }
//
//    }

    @Override
    public void onSuccess(int requestTypeId, Object result) {
        // TODO Auto-generated method stub

        if (requestTypeId == LoginModel.TYPE_GetUserOptions) {

            if (result != null && result instanceof UserOptionListEntity) {
                UserOptionListEntity UserOptionListResult = (UserOptionListEntity) result;
                if (UserOptionListResult.getResult() != null
                        && UserOptionListResult.getResult().getItems() != null) {
                    isSuccess = true;
                    dismissDialog();
                    List<AddUserOptionsResult> mdata = new ArrayList<AddUserOptionsResult>();
                    for (int i = 0; i < UserOptionListResult.getResult()
                            .getItems().size(); i++) {
                        AddUserOptionsResult addUserOptionsResult = new AddUserOptionsResult();
                        addUserOptionsResult.setId(UserOptionListResult
                                .getResult().getItems().get(i).getId());
                        addUserOptionsResult.setValue(UserOptionListResult
                                .getResult().getItems().get(i).getValue());
                        mdata.add(addUserOptionsResult);

                    }

                    if (list != null) {
                        list.clear();
                    }
                    list.addAll(mdata);
                    if (list != null && list.size() != 0) {
                        iv_no_messages.setVisibility(View.GONE);
                        text_no_messages.setVisibility(View.GONE);
                    } else {
                        iv_no_messages.setVisibility(View.VISIBLE);
                        text_no_messages.setVisibility(View.VISIBLE);
                    }

                    if (datas != null && datas.equals("ClientTabActivity")) {
                        optionSelectAdapter.setOptionCheckVisiable(false);
                    }

                    optionSelectAdapter.notifyDataSetChanged();

                }
            }

        }
        if (requestTypeId == AddUserOptionsModel.TYPE_GET_DEL_OPTIONS) {

            if (result != null && result instanceof DelUserOptionsEntity) {
                DelUserOptionsEntity mDelUserOptionsEntity = (DelUserOptionsEntity) result;
                if (mDelUserOptionsEntity.getResult() != null) {
                    isDel = mDelUserOptionsEntity.getResult();
                    dismissDialog();
                    if (isDel) {
                        optionSelectAdapter.removeItem(viewId);
                    }
                    if (list != null && list.size() != 0) {
                        iv_no_messages.setVisibility(View.GONE);
                        text_no_messages.setVisibility(View.GONE);
                    } else {
                        iv_no_messages.setVisibility(View.VISIBLE);
                        text_no_messages.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    }

    @Override
    public void onFail(int requestTypeId, int statusCode, String errorMsg,
                       Object result) {
        // TODO Auto-generated method stub
        Toast.makeText(OpinionInputActivity.this, errorMsg + "请您稍后重试", Toast.LENGTH_SHORT)
                .show();
        if (requestTypeId == AddUserOptionsModel.TYPE_GET_DEL_OPTIONS) {

           dismissDialog();
        } else {
            dismissDialog();

        }
        if (list != null && list.size() != 0) {
            iv_no_messages.setVisibility(View.GONE);
            text_no_messages.setVisibility(View.GONE);
        } else {
            iv_no_messages.setVisibility(View.VISIBLE);
            text_no_messages.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.imgview_titlebar_back:
                finish();
                break;
            case R.id.textview_titlebar_more:
                if (mEditText_input.getText().toString().length() <= 0) {
                    Toast.makeText(this, "请输入审批意见", Toast.LENGTH_SHORT).show();
                    break;
                }
                setResult(RESULT_OK, new Intent().putExtra("option",
                        mEditText_input.getText().toString()));
                finish();
                break;

//            case R.id.rl_opinion:
//                intent = new Intent(OpinionInputActivity.this,
//                        OptionSelectActivity.class);
//                intent.putExtra("app_id", app_id);
//                startActivityForResult(intent, 1);
//
//                break;
//            case R.id.image_mute:
//                intent = new Intent(OpinionInputActivity.this,
//                        OptionSelectActivity.class);
//                intent.putExtra("app_id", app_id);
//                startActivityForResult(intent, 1);
//                break;
            case R.id.tv_newoptions:
                Intent intent = new Intent(OpinionInputActivity.this,
                        OptionNewActivity.class);
                intent.putExtra("app_id", app_id);
                startActivity(intent);
            default:
                break;
        }
    }

    @Override
    public void click(final View v) {
        switch (v.getId()) {
            case R.id.iv_check_select:
                String commonOptions = list.get((Integer) v.getTag()).getValue();
                if (commonOptions != null) {
//                    Intent intent = new Intent();
//                    intent.putExtra("data1_return", commonOptions);
//                    setResult(RESULT_OK, intent);
                    setResult(RESULT_OK, new Intent().putExtra("option", commonOptions));
                }
                finish();
                break;
            case R.id.tv_delselect:



                new com.htmitech.pop.AlertDialog(OpinionInputActivity.this).builder().setTitle("删除").setMsg("确认删除吗？").setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View vs) {
                          // 删除意见调用
                        OptionsParameters mOptionsParameters = new OptionsParameters();
                        mOptionsParameters.context = OAConText
                                .getInstance(OpinionInputActivity.this);
                        mOptionsParameters.app_id = app_id;
                        mOptionsParameters.option = Optionsdel.getInstance(OpinionInputActivity.this,
                                list.get((Integer) v.getTag()).getId());
                        AddUserOptionsModel addUserOptionsModel = new AddUserOptionsModel(OpinionInputActivity.this);
                        addUserOptionsModel.getDataFromServerByType(AddUserOptionsModel.TYPE_GET_DEL_OPTIONS, mOptionsParameters);
                        viewId = (Integer) v.getTag();
                        showDialog();
                    }
                }).setNegativeButton("取消", new OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).show();

                break;
            case R.id.tv_editselect:
                String data = list.get((Integer) v.getTag()).getId() + "|"
                        + list.get((Integer) v.getTag()).getValue();
                Intent intent = new Intent(OpinionInputActivity.this,
                        OptionEditActivity.class);
                intent.putExtra("extra_data", data);
                intent.putExtra("app_id", app_id);
                startActivity(intent);

                break;

            default:
                break;
        }
    }
}
