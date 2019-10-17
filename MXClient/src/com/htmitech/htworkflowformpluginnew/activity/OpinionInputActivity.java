package com.htmitech.htworkflowformpluginnew.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
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
import com.htmitech.htworkflowformpluginnew.adapter.OptionSelectAdapter;
import com.htmitech.htworkflowformpluginnew.entity.GetUserOpintionParameter;
import com.htmitech.htworkflowformpluginnew.entity.OpintionDelectOrEditResult;
import com.htmitech.htworkflowformpluginnew.entity.OptionsParameters;
import com.htmitech.htworkflowformpluginnew.entity.UserOption;
import com.htmitech.htworkflowformpluginnew.entity.UserOptionListEntity;
import com.htmitech.htworkflowformpluginnew.entity.UserOptionsResult;
import htmitech.com.componentlibrary.listener.ObserverCallBackType;
import com.htmitech.proxy.interfaces.INetWorkManager;
import com.htmitech.proxy.myenum.LogManagerEnum;
import com.htmitech.proxy.util.LogManagerProxy;
import com.htmitech.proxy.util.NetWorkManager;
import com.minxing.client.util.FastJsonUtils;

import java.util.ArrayList;
import java.util.List;

import com.htmitech.thread.AnsynHttpRequest;
import com.htmitech.thread.CHTTP;
import com.htmitech.thread.GetFinalRefreshTokenAfterRequestValue;

import htmitech.com.componentlibrary.unit.ServerUrlConstant;


/**
 * 意见输入界面
 */
public class OpinionInputActivity extends SlidingBackAcitivity implements
        View.OnClickListener, IBaseCallback, OptionSelectAdapter.Callback, ObserverCallBackType {

    private EditText mEditText_input;
    private Spinner mSpinner;
    private TextView mTextViewOk;
    private ImageView mImage_mute;
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
    private List<UserOptionsResult> list;
    private OptionSelectAdapter optionSelectAdapter;
    private boolean isDel = false;
    private int viewId;
    private String datas;
    private boolean isSuccess = false;
    private View mEmptyView;
    private ImageView iv_no_messages;
    private TextView text_no_messages;
    private int com_workflow_mobileconfig_include_options;
    private GetUserOpintionParameter getUserOpintionParameter;
    private OptionsParameters mOptionsParameters;
    private INetWorkManager netWorkManager;
    public String GET_USEROPINTIONS_PATH = ServerUrlConstant.SERVER_JAVA_URL() + ServerUrlConstant.USERINFO_GETUSEROPTIONS_METHOD_JAVA;
    public String DELECT_USEROPINTIONS_PATH = ServerUrlConstant.SERVER_JAVA_URL() + ServerUrlConstant.USERINFO_DELUSEROPTIONS_METHOD_JAVA;
    public static final String GET_USEROPINTION = "getUserOpintion";
    public static final String DELECT_USEROPINTION = "delectUserOpintion";


    protected int getLayoutById() {
        return R.layout.activity_optioninput;
    }

    /**
     * 初始化UI
     */
    protected void initView() {

        String userOpinion = AppPreferenceHelper.getInstance(this).getString(PreferenceKeys.KEY_OPINION_SAVE, "同意|拟同意");
        String[] mItemArray = org.apache.commons.lang3.StringUtils.split(userOpinion, "|");

        mItem = new ArrayList<String>();
        for (int i = 0; i < mItemArray.length; i++) {
            mItem.add(mItemArray[i]);
        }

        findViewById(R.id.imgview_titlebar_back).setOnClickListener(this);
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
        adapter = new ArrayAdapter<String>(this, R.layout.layout_spinner_item, mItem);
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
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
        if (com_workflow_mobileconfig_include_options == 1) {

        }

        //常用意见
        list = new ArrayList<UserOptionsResult>();
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
        optionSelectAdapter = new OptionSelectAdapter(OpinionInputActivity.this, list, OpinionInputActivity.this);
        options_listview.setAdapter(optionSelectAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        netWorkManager = LogManagerProxy.getProxyInstance(NetWorkManager.class);
        getUserOpintionParameter = new GetUserOpintionParameter();
        getUserOpintionParameter.appId = app_id;
        getUserOpintionParameter.userId = OAConText.getInstance(HtmitechApplication.getInstance()).UserID;
        netWorkManager.logFunactionStart(OpinionInputActivity.this, OpinionInputActivity.this, "getOpintionfunctionStart", LogManagerEnum.GETUSEROPINTION.functionCode);
//        LoginModel loginModel = new LoginModel(OpinionInputActivity.this);
//        loginModel.getDataFromServerByType(LoginModel.TYPE_GetUserOptions, OAConText.getInstance(HtmitechApplication.getInstance()).UserID + "&app_id=" + app_id);
        showDialog();
    }

    @Override
    public void onSuccess(int requestTypeId, Object result) {
        // TODO Auto-generated method stub

//        if (requestTypeId == LoginModel.TYPE_GetUserOptions) {
//
//            if (result != null && result instanceof UserOptionListEntity) {
//                UserOptionListEntity UserOptionListResult = (UserOptionListEntity) result;
//                if (UserOptionListResult.getResult() != null
//                        && UserOptionListResult.getResult().getItems() != null) {
//                    isSuccess = true;
//                    progressDialog1.dismiss();
//                    List<AddUserOptionsResult> mdata = new ArrayList<AddUserOptionsResult>();
//                    for (int i = 0; i < UserOptionListResult.getResult()
//                            .getItems().size(); i++) {
//                        AddUserOptionsResult addUserOptionsResult = new AddUserOptionsResult();
//                        addUserOptionsResult.setId(UserOptionListResult
//                                .getResult().getItems().get(i).getId());
//                        addUserOptionsResult.setValue(UserOptionListResult
//                                .getResult().getItems().get(i).getValue());
//                        mdata.add(addUserOptionsResult);
//
//                    }
//
//                    if (list != null) {
//                        list.clear();
//                    }
//                    list.addAll(mdata);
//                    if (list != null && list.size() != 0) {
//                        iv_no_messages.setVisibility(View.GONE);
//                        text_no_messages.setVisibility(View.GONE);
//                    } else {
//                        iv_no_messages.setVisibility(View.VISIBLE);
//                        text_no_messages.setVisibility(View.VISIBLE);
//                    }
//
//                    if (datas != null && datas.equals("ClientTabActivity")) {
//                        optionSelectAdapter.setOptionCheckVisiable(false);
//                    }
//
//                    optionSelectAdapter.notifyDataSetChanged();
//
//                }
//            }
//
//        }
//        if (requestTypeId == AddUserOptionsModel.TYPE_GET_DEL_OPTIONS) {
//
//            if (result != null && result instanceof DelUserOptionsEntity) {
//                DelUserOptionsEntity mDelUserOptionsEntity = (DelUserOptionsEntity) result;
//                if (mDelUserOptionsEntity.getResult() != null) {
//                    isDel = mDelUserOptionsEntity.getResult();
//                    progressDialog.dismiss();
//                    if (isDel) {
//                        optionSelectAdapter.removeItem(viewId);
//                    }
//                    if (list != null && list.size() != 0) {
//                        iv_no_messages.setVisibility(View.GONE);
//                        text_no_messages.setVisibility(View.GONE);
//                    } else {
//                        iv_no_messages.setVisibility(View.VISIBLE);
//                        text_no_messages.setVisibility(View.VISIBLE);
//                    }
//                }
//            }
//        }
    }

    @Override
    public void onFail(int requestTypeId, int statusCode, String errorMsg,
                       Object result) {
        // TODO Auto-generated method stub
//        Toast.makeText(OpinionInputActivity.this, errorMsg + "请您稍后重试", Toast.LENGTH_SHORT).show();
//        if (requestTypeId == AddUserOptionsModel.TYPE_GET_DEL_OPTIONS) {
//
//            progressDialog.dismiss();
//        } else {
//            progressDialog1.dismiss();
//
//        }
//        if (list != null && list.size() != 0) {
//            iv_no_messages.setVisibility(View.GONE);
//            text_no_messages.setVisibility(View.GONE);
//        } else {
//            iv_no_messages.setVisibility(View.VISIBLE);
//            text_no_messages.setVisibility(View.VISIBLE);
//        }
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
                setResult(RESULT_OK, new Intent().putExtra("option", mEditText_input.getText().toString()));
                finish();
                break;
            case R.id.tv_newoptions:
                Intent intent = new Intent(OpinionInputActivity.this, OptionNewActivity.class);
                intent.putExtra("app_id", app_id);
                startActivity(intent);
            default:
                break;
        }
    }

    @Override
    public void click(final View v, final String optionText, final String optionId) {
        switch (v.getId()) {
            case R.id.iv_check_select:
                String commonOptions = list.get((Integer) v.getTag()).getOpinionText();
                if (commonOptions != null) {
                    setResult(RESULT_OK, new Intent().putExtra("option", commonOptions));
                }
                finish();
                break;
            case R.id.tv_delselect:
//                Dialog dialog = new AlertDialog.Builder(OpinionInputActivity.this)
//                        .setTitle("删除对话框")
//                        .setIcon(R.drawable.ic_launcher)
//                        .setMessage("确认删除吗？")
//                        // 相当于点击确认按钮
//                        .setPositiveButton("确认",
//                                new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        // 删除意见调用
//                                        mOptionsParameters = new OptionsParameters();
//                                        mOptionsParameters.appId = app_id;
//                                        mOptionsParameters.userId = OAConText.getInstance(HtmitechApplication.getApplication()).UserID;
//                                        UserOption userOption = new UserOption();
//                                        userOption.opinionText = optionText;
//                                        userOption.opinionId = optionId;
//                                        mOptionsParameters.setOption(userOption);
//                                        netWorkManager.logFunactionStart(OpinionInputActivity.this, OpinionInputActivity.this, "delectOpintionfunctionStart", "");
//
//                                        viewId = (Integer) v.getTag();
//                                        progressDialog = new ProgressDialog(OpinionInputActivity.this);
//                                        progressDialog.setTitle("常用意见");
//                                        progressDialog.setMessage("删除中...");
//                                        progressDialog.setCancelable(true);//通过back键不消失
//                                        progressDialog.show();
//                                    }
//                                })
//                        // 相当于点击取消按钮
//                        .setNegativeButton("取消",
//                                new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog,
//                                                        int which) {
//                                        // TODO Auto-generated method
//                                        // stub
//
//                                    }
//                                }).create();
//                dialog.show();


                new com.htmitech.pop.AlertDialog(OpinionInputActivity.this).builder().setTitle("删除").setMsg("确认删除吗？").setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View vs) {
                        // 删除意见调用
                        mOptionsParameters = new OptionsParameters();
                        mOptionsParameters.appId = app_id;
                        mOptionsParameters.userId = OAConText.getInstance(HtmitechApplication.getApplication()).UserID;
                        UserOption userOption = new UserOption();
                        userOption.opinionText = optionText;
                        userOption.opinionId = optionId;
                        mOptionsParameters.setOption(userOption);
                        netWorkManager.logFunactionStart(OpinionInputActivity.this, OpinionInputActivity.this, "delectOpintionfunctionStart", "");

                        viewId = (Integer) v.getTag();
                        showDialog();
                    }
                }).setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).show();

                break;
            case R.id.tv_editselect:
                String data = list.get((Integer) v.getTag()).getOpinionId() + "|" + list.get((Integer) v.getTag()).getOpinionText();
                Intent intent = new Intent(OpinionInputActivity.this, OptionEditActivity.class);
                intent.putExtra("extra_data", data);
                intent.putExtra("app_id", app_id);
                startActivity(intent);
                break;

            default:
                break;
        }
    }

    @Override
    public void success(String requestValue, int type, String requestName) {
        if ("getOpintionfunctionStart".equals(requestName)) {
            AnsynHttpRequest.requestByPostWithToken(this, getUserOpintionParameter, GET_USEROPINTIONS_PATH, CHTTP.POSTWITHTOKEN, this, GET_USEROPINTION, LogManagerEnum.GETUSEROPINTION.functionCode);
        } else if ("delectOpintionfunctionStart".equals(requestName)) {
            AnsynHttpRequest.requestByPostWithToken(this, mOptionsParameters, DELECT_USEROPINTIONS_PATH, CHTTP.POSTWITHTOKEN, this, DELECT_USEROPINTION, LogManagerEnum.DELECTUSEROPINTION.functionCode);
        } else if (GET_USEROPINTION.equals(requestName)) {
            requestValue = GetFinalRefreshTokenAfterRequestValue.getFinalRequestValue(this, requestValue, type, GET_USEROPINTIONS_PATH, getUserOpintionParameter, this, requestName, LogManagerEnum.GETUSEROPINTION.functionCode);
            dismissDialog();
            if (!TextUtils.isEmpty(requestValue)) {
                UserOptionListEntity UserOptionListResult = FastJsonUtils.getPerson(requestValue, UserOptionListEntity.class);
                if (UserOptionListResult != null) {
                    isSuccess = true;
                    if (list != null) {
                        list.clear();
                    }
                    list.addAll(UserOptionListResult.Result);
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
                    netWorkManager.logFunactionFinsh(OpinionInputActivity.this, OpinionInputActivity.this, "getOpintionfunctionStartFinish", LogManagerEnum.GETUSEROPINTION.functionCode, UserOptionListResult.getMessage(), INetWorkManager.State.SUCCESS);
                }
                netWorkManager.logFunactionFinsh(OpinionInputActivity.this, OpinionInputActivity.this, "getOpintionfunctionFinish", LogManagerEnum.GETUSEROPINTION.functionCode, "返回实体为空", INetWorkManager.State.FAIL);
            }
            netWorkManager.logFunactionFinsh(OpinionInputActivity.this, OpinionInputActivity.this, "getOpintionfunctionFinish", LogManagerEnum.GETUSEROPINTION.functionCode, "返回实体为空", INetWorkManager.State.FAIL);
        } else if (DELECT_USEROPINTION.equals(requestName)) {
            requestValue = GetFinalRefreshTokenAfterRequestValue.getFinalRequestValue(this, requestValue, type, DELECT_USEROPINTIONS_PATH, mOptionsParameters, this, requestName, LogManagerEnum.DELECTUSEROPINTION.functionCode);
            dismissDialog();
            if (!TextUtils.isEmpty(requestValue)) {
                OpintionDelectOrEditResult mDelUserOptionsEntity = FastJsonUtils.getPerson(requestValue, OpintionDelectOrEditResult.class);
                if (mDelUserOptionsEntity != null) {
                    isDel = mDelUserOptionsEntity.Result;
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
                    netWorkManager.logFunactionFinsh(OpinionInputActivity.this, OpinionInputActivity.this, "delectOpintionfunctionFinish", LogManagerEnum.DELECTUSEROPINTION.functionCode, mDelUserOptionsEntity.message, INetWorkManager.State.SUCCESS);
                }
                netWorkManager.logFunactionFinsh(OpinionInputActivity.this, OpinionInputActivity.this, "delectOpintionfunctionFinish", LogManagerEnum.DELECTUSEROPINTION.functionCode, "返回实体为空", INetWorkManager.State.FAIL);
            }
            netWorkManager.logFunactionFinsh(OpinionInputActivity.this, OpinionInputActivity.this, "delectOpintionfunctionFinish", LogManagerEnum.DELECTUSEROPINTION.functionCode, "返回实体为空", INetWorkManager.State.FAIL);
        }
    }

    @Override
    public void fail(String exceptionMessage, int type, String requestName) {
        if ("getOpintionfunctionStart".equals(requestName)) {
            AnsynHttpRequest.requestByPostWithToken(this, getUserOpintionParameter, GET_USEROPINTIONS_PATH, CHTTP.POSTWITHTOKEN, this, GET_USEROPINTION, LogManagerEnum.GETUSEROPINTION.functionCode);
        } else if ("delectOpintionfunctionStart".equals(requestName)) {
            AnsynHttpRequest.requestByPostWithToken(this, mOptionsParameters, DELECT_USEROPINTIONS_PATH, CHTTP.POSTWITHTOKEN, this, DELECT_USEROPINTION, LogManagerEnum.DELECTUSEROPINTION.functionCode);
        } else {
            if (GET_USEROPINTION.equals(requestName)) {
                dismissDialog();
                netWorkManager.logFunactionFinsh(OpinionInputActivity.this, OpinionInputActivity.this, "getOpintionfunctionFinish", LogManagerEnum.GETUSEROPINTION.functionCode, exceptionMessage, INetWorkManager.State.FAIL);
            } else if (DELECT_USEROPINTION.equals(requestName)) {
                dismissDialog();
                netWorkManager.logFunactionFinsh(OpinionInputActivity.this, OpinionInputActivity.this, "delectOpintionfunctionFinish", LogManagerEnum.DELECTUSEROPINTION.functionCode, exceptionMessage, INetWorkManager.State.FAIL);
            }
            Toast.makeText(OpinionInputActivity.this, exceptionMessage + "请您稍后重试", Toast.LENGTH_SHORT).show();
            if (list != null && list.size() != 0) {
                iv_no_messages.setVisibility(View.GONE);
                text_no_messages.setVisibility(View.GONE);
            } else {
                iv_no_messages.setVisibility(View.VISIBLE);
                text_no_messages.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void notNetwork() {

    }

    @Override
    public void callbackMainUI(String successMessage) {

    }
}
