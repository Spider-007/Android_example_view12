package com.htmitech.htworkflowformpluginnew.listener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.htmitech.api.BookInit;
import com.htmitech.domain.SYS_Department;
import com.htmitech.domain.SYS_User;
import com.htmitech.emportal.entity.OAConText;

import com.htmitech.htworkflowformpluginnew.activity.OpinionInputActivity;
import com.htmitech.htworkflowformpluginnew.activity.WorkFlowFormDetalActivity;
import com.htmitech.htworkflowformpluginnew.fragment.WorkFlowInitUrlFragment;
import com.htmitech.listener.CallCheckAllUserListener;
import com.htmitech.myEnum.ChooseSystemBook;
import com.htmitech.myEnum.ChooseTreeHierarchy;
import com.htmitech.myEnum.ChooseWay;
import com.htmitech.myEnum.ChooseWayEnum;

import java.util.ArrayList;
import java.util.List;

import htmitech.com.componentlibrary.datepicker.PopChooseTimeHelper;
import htmitech.com.componentlibrary.entity.EditField;
import htmitech.com.componentlibrary.myEnum.TimeEnum;
import htmitech.formConfig.CheckDate300_301;


/**
 * Created by Administrator on 2018/6/27.
 * JS调用本地方法
 */

public class JavaScriptObject {
    public Context context;
    public WorkFlowInitUrlFragment mWorkFlowInitUrlFragment;
    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (null != msg) {
                switch (msg.what) {
                    case 2002:        //意见字段2002将数据传入到JS方法中
                        if (null != mWorkFlowInitUrlFragment && null != mWorkFlowInitUrlFragment.webView) {
                            mWorkFlowInitUrlFragment.webView.loadUrl("javascript:getSignInfoZTwo(" + msg.obj + ")");
                        }
                        break;
                    case 2003:      //意见字段2003将数据传入到JS方法中

                        break;
                    case 2004:     //意见字段2004将数据传入到JS方法中
                        if (null != mWorkFlowInitUrlFragment && null != mWorkFlowInitUrlFragment.webView) {
                            mWorkFlowInitUrlFragment.webView.loadUrl("javascript:getSignInfoZFour(" + msg.obj + ")");
                        }

                    case 200:
                        ((WorkFlowFormDetalActivity) context).functionDetail();
                        break;
                    case 201:
                        if (null != mWorkFlowInitUrlFragment && null != mWorkFlowInitUrlFragment.webView) {
                            mWorkFlowInitUrlFragment.webView.loadUrl("javascript:getDateInfo(" + msg.obj+ ")");
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    };

    /*
    * @param contex上下文
    * @param mWorkFlowInitUrlFragment网页签批fragment的引用，方便调用页面中的方与变量
    * */
    public JavaScriptObject(Context context, WorkFlowInitUrlFragment mWorkFlowInitUrlFragment) {
        this.context = context;
        this.mWorkFlowInitUrlFragment = mWorkFlowInitUrlFragment;

    }

    /*
    * 人员部门选择
    * */
    @JavascriptInterface
    public void backSelectUserInfoMobile(final String input, String key) {
        final String keys = key;
        ChooseWayEnum peoplechoose = null;
        ChooseWay chooseWay = null;
        if (!TextUtils.isEmpty(input)) {
            if (input.equals("601") || input.equals("602") || input.equals("603")) {     //人员单选
                peoplechoose = ChooseWayEnum.PEOPLECHOOSE;
                chooseWay = ChooseWay.SINGLE_CHOOSE;
            } else if (input.equals("611") || input.equals("612") || input.equals("613")) {//人员多选
                peoplechoose = ChooseWayEnum.PEOPLECHOOSE;
                chooseWay = ChooseWay.MORE_CHOOSE;
            } else if (input.equals("901") || input.equals("902")) {//部门单选
                peoplechoose = ChooseWayEnum.DEPARTMENTCHOOSE;
                chooseWay = ChooseWay.SINGLE_CHOOSE;
            } else if (input.equals("911") || input.equals("912")) {//部门多选
                peoplechoose = ChooseWayEnum.DEPARTMENTCHOOSE;
                chooseWay = ChooseWay.MORE_CHOOSE;
            } else if (input.equals("1001")) {//自由单选
                peoplechoose = ChooseWayEnum.FREECHOOSE;
                chooseWay = ChooseWay.SINGLE_CHOOSE;
            }
        }
        if (null != peoplechoose && null != chooseWay) {

            BookInit.getInstance().setCallCheckUserListener(context, peoplechoose, chooseWay, ChooseTreeHierarchy
                    .HIERARCHY, ChooseSystemBook.ADDRESSBOOK, "选择人员", true, null, new CallCheckAllUserListener() {
                @Override
                public void checkAll(ArrayList<SYS_User> checkAllUser, ArrayList<SYS_Department> checkAllDepartment) {
                    if (checkAllUser != null && checkAllUser.size() != 0) {
                        //拼装选择人员后的信息交给JS
                        JSONArray jsonArray = new JSONArray();
                        JSONObject root = new JSONObject();
                        for (int i = 0; i < checkAllUser.size(); i++) {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("userID", checkAllUser.get(i).UserId);
                            jsonObject.put("userName", checkAllUser.get(i).getFullName());
                            jsonObject.put("login_name", checkAllUser.get(i).login_name);
                            jsonArray.add(i, jsonObject);
                        }
                        root.put("key", keys);
                        root.put("dataList", jsonArray);
                        root.put("input", input);
                        if (null != mWorkFlowInitUrlFragment && null != mWorkFlowInitUrlFragment.webView) {
                            mWorkFlowInitUrlFragment.webView.loadUrl("javascript:backSelectUserInfo(" + root.toJSONString() + ")");
                        }

                    } else if (checkAllDepartment != null && checkAllDepartment.size() != 0) {
                        //拼装选择部门后的信息交给JS
                        JSONArray jsonArray = new JSONArray();
                        JSONObject root = new JSONObject();
                        for (int i = 0; i < checkAllDepartment.size(); i++) {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("userID", checkAllDepartment.get(i).getDepartmentCode());
                            jsonObject.put("userName", checkAllDepartment.get(i).getFullName());
                            jsonArray.add(i, jsonObject);
                        }
                        root.put("key", keys);
                        root.put("dataList", jsonArray);
                        root.put("input", input);
                        if (null != mWorkFlowInitUrlFragment && null != mWorkFlowInitUrlFragment.webView) {
                            mWorkFlowInitUrlFragment.webView.loadUrl("javascript:backSelectUserInfo(" + root.toJSONString() + ")");
                        }
                    }
                }
            });

        }

    }

    /*
    * 意见,签名字段处理
    * */
    @JavascriptInterface
    public void getSignInfoMobile(String inputType, String key) {
        if (!TextUtils.isEmpty(inputType)) {
            if (inputType.equals("2001")) {
                Intent intent = new Intent(context, OpinionInputActivity.class);
                intent.putExtra("app_id", mWorkFlowInitUrlFragment.app_id);
                mWorkFlowInitUrlFragment.startActivityForResult(intent, 1);
                mWorkFlowInitUrlFragment.key = key;
            } else if (inputType.equals("2002")) {
                final JSONObject jsonObject = new JSONObject();
                jsonObject.put("userName", OAConText.getInstance(context).OA_UserName);
                jsonObject.put("key", key);
                Message message = new Message();
                message.what = 2002;
                message.obj = jsonObject.toJSONString();
                mHandler.sendMessage(message);
            } else if (inputType.equals("2003")) {

            } else if (inputType.equals("2004")) {
                Intent intent = new Intent(context, OpinionInputActivity.class);
                intent.putExtra("app_id", mWorkFlowInitUrlFragment.app_id);
            } else {
                Toast.makeText(context, "该类型不是意见类型", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @JavascriptInterface
    public void submitDataMobile(String submitData) {
        try {
            if (null != context && context instanceof WorkFlowFormDetalActivity) {
                //重置提交数据
                if (!TextUtils.isEmpty(submitData)) {
                    List<EditField> editFields = JSONArray.parseArray(submitData, EditField.class);
                    if (null != ((WorkFlowFormDetalActivity) context).mDocResultInfo && null != ((WorkFlowFormDetalActivity) context).mDocResultInfo.getResult()) {
                        ((WorkFlowFormDetalActivity) context).mDocResultInfo.getResult().setEditFields(editFields);
                    }
                }
                Message message = new Message();
                message.what = 200;
                mHandler.sendMessage(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    String selectDateValue;
    @JavascriptInterface
    public void getDateInfoMobile(final String inputType, final String key,final String date){
        ((Activity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                CheckDate300_301 checkDate300_301 = new CheckDate300_301(context);
                selectDateValue = checkDate300_301.getName(Integer.parseInt(inputType), date);
                PopChooseTimeHelper mPopBirthHelper = null;
                if (mPopBirthHelper != null) {
                    mPopBirthHelper.dismiss();
                }
                mPopBirthHelper = new PopChooseTimeHelper((Activity)context);
                if (Integer.parseInt(inputType) == 300) {
//                    mPopBirthHelper.setTimeEnums(TimeEnum.YEAR, TimeEnum.MONTH, TimeEnum.DAY);
                    mPopBirthHelper.setTimeEnums(TimeEnum.YEAR, TimeEnum.MONTH, TimeEnum.DAY, TimeEnum.AMPM);
                } else if (Integer.parseInt(inputType) == 302) {
                    mPopBirthHelper.setTimeEnums(TimeEnum.YEAR);
                } else if (Integer.parseInt(inputType) == 303) {
                    mPopBirthHelper.setTimeEnums(TimeEnum.YEAR, TimeEnum.MONTH);
                } else if (Integer.parseInt(inputType) == 304) {
                    mPopBirthHelper.setTimeEnums(TimeEnum.YEAR, TimeEnum.Y_WEEK);
                } else {
                    mPopBirthHelper.setTimeEnums(TimeEnum.YEAR, TimeEnum.MONTH, TimeEnum.DAY, TimeEnum.HOUR, TimeEnum.SEX);
                }
                mPopBirthHelper.setOnClickOkListener(new PopChooseTimeHelper.OnClickOkListener() {
                    @Override
                    public void onClickOk(String birthday) {
                        // TODO Auto-generated method stub
                        if (birthday != null && !birthday.equals("")){
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("dateInfo",birthday);
                            jsonObject.put("key",key);
                            jsonObject.put("input",inputType);
                            Message message = new Message();
                            message.what = 201;
                            message.obj = jsonObject.toJSONString();
                            mHandler.sendMessage(message);
                        }

                    }
                });
                mPopBirthHelper.show(mWorkFlowInitUrlFragment.webView);
            }
        });

    }

}
