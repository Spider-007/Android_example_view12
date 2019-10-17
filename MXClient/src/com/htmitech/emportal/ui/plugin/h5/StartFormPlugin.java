package com.htmitech.emportal.ui.plugin.h5;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.htmitech.api.BookInit;
import com.htmitech.datepicker.PopChooseTimeHelper;
import com.htmitech.domain.SYS_Department;
import com.htmitech.domain.SYS_User;
import com.htmitech.emportal.entity.AuthorInfo;
import com.htmitech.emportal.ui.formConfig.CallBackDoAction;
import com.htmitech.listener.CallCheckAllUserListener;
import com.htmitech.myEnum.ChooseSystemBook;
import com.htmitech.myEnum.ChooseTreeHierarchy;
import com.htmitech.myEnum.ChooseWay;
import com.htmitech.myEnum.ChooseWayEnum;
import com.htmitech.myEnum.TimeEnum;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Think on 2016/11/23.
 */

public class StartFormPlugin extends CordovaPlugin implements PopChooseTimeHelper.OnClickOkListener,Runnable{

    public StartFormPlugin() {
    }

    private static final String ACTION_SHOW_EVENT = "show";
    private static final String ACTION_CHECK_EVENT = "check";
    private static final String ACTION_SUBMIT_EVENT = "submit";

    private PopChooseTimeHelper mPopBirthHelper;
    private String CheckDate;
    private String workID;
    public static String data;
    private int input;
    public boolean  flag = false;
    private CallbackContext callbackContext;
    private String submitData;
    private CallBackDoAction mCallBackDoAction;

    @Override
    public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
        this.callbackContext = callbackContext;
      if (ACTION_SHOW_EVENT.equals(action)) {

          callbackContext.success(data);
          return true;


      } else if (ACTION_CHECK_EVENT.equals(action))  {

              workID = args.getString(0);
              final String iputType = args.getString(1);
              input = Integer.parseInt(iputType);
              switch (input) {
                  case 300:
                  case 301:
                      cordova.getActivity().runOnUiThread(this);
                      break;
                  case 601:
                  case 602:
                  case 603:
                      BookInit.getInstance().setCallCheckUserListener(cordova.getActivity(), ChooseWayEnum.PEOPLECHOOSE, ChooseWay.SINGLE_CHOOSE, ChooseTreeHierarchy
                              .HIERARCHY, ChooseSystemBook.ADDRESSBOOK, "选择人员", true, null, new CallCheckAllUserListener() {
                          @Override
                          public void checkAll(ArrayList<SYS_User> checkAllUser, ArrayList<SYS_Department> checkAllDepartment) {
                              if (checkAllUser != null && checkAllUser.size() != 0) {
//                                  ((TextView) v).setText("");
                                  ArrayList<AuthorInfo> mAuthorInfoList = new ArrayList<AuthorInfo>();
                                  for (SYS_User mSYS_User : checkAllUser) {
                                      AuthorInfo mAuthorInfo = new AuthorInfo();
                                      mAuthorInfo.setUserID(mSYS_User.getUserId());
                                      mAuthorInfo.setUserName(mSYS_User.getFullName());
                                      mAuthorInfoList.add(mAuthorInfo);
                                  }
//                                  mCallBackDoAction.callHandle_doAction_hasAuthor(mAuthorInfoList,currentEditTextView);
                                  callbackContext.success(new String("{\"id\":\"" + workID + "\",\"value\":\"" + mAuthorInfoList.get(0).getUserName() + "\",\"name\":\"" + mAuthorInfoList.get(0).getUserName() + "\"}"));
                                  flag = true;
                              } else if (checkAllDepartment != null && checkAllDepartment.size() != 0) {
//                                  ((TextView) v).setText("");
                                  ArrayList<AuthorInfo> mAuthorInfoList = new ArrayList<AuthorInfo>();
                                  for (SYS_Department mSYS_Department : checkAllDepartment) {
                                      AuthorInfo mAuthorInfo = new AuthorInfo();
                                      mAuthorInfo.setUserID(mSYS_Department.getDepartmentCode());
                                      mAuthorInfo.setUserName(mSYS_Department.getFullName());
                                      mAuthorInfoList.add(mAuthorInfo);
                                  }
//                                  mCallBackDoAction.callHandle_doAction_hasAuthor(mAuthorInfoList,currentEditTextView);
                                  callbackContext.success(new String("{\"id\":\"" + workID + "\",\"value\":\"" + mAuthorInfoList.get(0).getUserName() + "\",\"name\":\"" + mAuthorInfoList.get(0).getUserName() + "\"}"));
                                  flag = true;
                              }

                          }
                      });
                      break;
                  case 611:
                  case 612:
                  case 613:
                      BookInit.getInstance().setCallCheckUserListener(cordova.getActivity(), ChooseWayEnum.DEPARTMENTCHOOSE, ChooseWay.SINGLE_CHOOSE, ChooseTreeHierarchy
                              .HIERARCHY, ChooseSystemBook.ADDRESSBOOK, "选择人员", true, null, new CallCheckAllUserListener() {
                          @Override
                          public void checkAll(ArrayList<SYS_User> checkAllUser, ArrayList<SYS_Department> checkAllDepartment) {
                              if (checkAllUser != null && checkAllUser.size() != 0) {
//                                  ((TextView) v).setText("");
                                  ArrayList<AuthorInfo> mAuthorInfoList = new ArrayList<AuthorInfo>();
                                  for (SYS_User mSYS_User : checkAllUser) {
                                      AuthorInfo mAuthorInfo = new AuthorInfo();
                                      mAuthorInfo.setUserID(mSYS_User.getUserId());
                                      mAuthorInfo.setUserName(mSYS_User.getFullName());
                                      mAuthorInfoList.add(mAuthorInfo);
                                  }
//                                  mCallBackDoAction.callHandle_doAction_hasAuthor(mAuthorInfoList,currentEditTextView);
                                  callbackContext.success(new String("{\"id\":\"" + workID + "\",\"value\":\"" + mAuthorInfoList.get(0).getUserName() + "\",\"name\":\"" + mAuthorInfoList.get(0).getUserName() + "\"}"));
                                  flag = true;
                              } else if (checkAllDepartment != null && checkAllDepartment.size() != 0) {
//                                  ((TextView) v).setText("");
                                  ArrayList<AuthorInfo> mAuthorInfoList = new ArrayList<AuthorInfo>();
                                  for (SYS_Department mSYS_Department : checkAllDepartment) {
                                      AuthorInfo mAuthorInfo = new AuthorInfo();
                                      mAuthorInfo.setUserID(mSYS_Department.getDepartmentCode());
                                      mAuthorInfo.setUserName(mSYS_Department.getFullName());
                                      mAuthorInfoList.add(mAuthorInfo);
                                  }
//                                  mCallBackDoAction.callHandle_doAction_hasAuthor(mAuthorInfoList,currentEditTextView);
                                  callbackContext.success(new String("{\"id\":\"" + workID + "\",\"value\":\"" + mAuthorInfoList.get(0).getUserName() + "\",\"name\":\"" + mAuthorInfoList.get(0).getUserName() + "\"}"));
                                  flag = true;
                              }

                          }
                      });
                      break;

              }

          return t();
          }else if (ACTION_SUBMIT_EVENT.equals(action))  {

            submitData = args.getString(0);
            parseJSONWithJSONObject(submitData);
            return true;

         } else {
              callbackContext.error("Form." + action + " 没有被找到");
              return false;
          }
    }

    private synchronized  boolean t(){
        Boolean message = BookInit.getInstance().getFlag();
        while (!flag && !BookInit.getInstance().getFlag()){
        }
        flag = false;
        BookInit.getInstance().setFlag(false);
        return true;
    }


    @Override
    public void onClickOk(String birthday) {
        if(!birthday.equals(""))
        CheckDate = birthday;
        callbackContext.success(new String("{\"id\":\"" + workID + "\",\"value\":\"" + CheckDate + "\",\"name\":\"" + CheckDate + "\"}"));//name显示 value上传的值
        flag = true;
    }

    @Override
    public void run() {
        if (mPopBirthHelper != null) {
            mPopBirthHelper.dismiss();
        }
        mPopBirthHelper = new PopChooseTimeHelper(cordova.getActivity()); 

        //设置监听
        mPopBirthHelper.getPop().setOutsideTouchable(false);
        mPopBirthHelper.getPop().setFocusable(false);

        if (input == 300) {
            mPopBirthHelper.setTimeEnums(TimeEnum.YEAR, TimeEnum.MONTH, TimeEnum.DAY);
        } else {
            mPopBirthHelper.setTimeEnums(TimeEnum.YEAR, TimeEnum.MONTH, TimeEnum.DAY, TimeEnum.HOUR, TimeEnum.SEX);
        }
        mPopBirthHelper.show(webView.getView());

        mPopBirthHelper.setOnClickOkListener(this);
    }

    public static class Receiver extends BroadcastReceiver {
       @Override
        public void onReceive(Context context, Intent intent) {
           data = intent.getExtras().getString("data");
            Log.i("Recevier1", "接收到:"+data);
        }

    }
    private void parseJSONWithJSONObject(String jsonData) {
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            int result  = Integer.parseInt(jsonObject.getString("result"));;
            String resultMsg = jsonObject.getString("resultMsg");
            String resultInfo = jsonObject.getString("resultInfo");
            if(result ==1){
                Toast.makeText(cordova.getActivity(),resultMsg,Toast.LENGTH_SHORT).show();

            }else{
//              Toast.makeText(cordova.getActivity(),resultInfo,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setAction("com.tijiao");
                intent.putExtra("submitData",resultInfo);
                cordova.getActivity().sendBroadcast(intent);
                Log.e("MXCommon","111111111"+resultInfo);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
