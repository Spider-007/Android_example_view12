package com.htmitech.broadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.htmitech.api.BookInit;
import com.htmitech.app.Constant;
import com.htmitech.fragment.AddressFragment;
import com.htmitech.fragment.UpdateUserDetailsMessageFragment;

import htmitech.com.componentlibrary.api.ComponentInit;
import htmitech.com.componentlibrary.content.ConcreteLogin;

/**
 * Created by htrf-pc on 2016/3/28.
 */
public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        int value = intent.getIntExtra("value", -1);
        String from = intent.getStringExtra("from") != null ? intent.getStringExtra("from") : "";
        boolean isSuccess = true;
        if (from != null && from.equals("login")) {
            isSuccess = intent.getBooleanExtra("isSuccess", true);
        }
        if (action.equals(Constant.ACTION_NAME)) {
            AddressFragment mAddressFragment = null;
            if (BookInit.getInstance().getMyMap() != null) {
                mAddressFragment = BookInit.getInstance().getMyMap().get(BookInit.getInstance().getBookType());
            }

            if (value == 1) {
                BookInit.getInstance().setBoradCast(true);
                if (ComponentInit.getInstance().getSuccess() != null) {
                    if (from != null && from.equals("login")) {
                        ComponentInit.getInstance().getSuccess().sysUserSuccess(isSuccess);
                    } else {
                        ComponentInit.getInstance().getSuccess().sysUserSuccess(true);
                    }
                }
                if (mAddressFragment != null)
                    mAddressFragment.callBoradcastReceiver();
            } else if (value == 2) {  //添加成功
                UpdateUserDetailsMessageFragment mUserDetailsMessageFragment = BookInit.getInstance().getmUserDetailsMessageFragment();
                mUserDetailsMessageFragment.addUser();
                ConcreteLogin mConcreteLogin = ConcreteLogin.getInstance();
                mConcreteLogin.addOrDeleteContact(context,true,mUserDetailsMessageFragment.getmSYS_User().getUserId(),null);
            } else if (value == 3) {// 删除成功
                String userId = intent.getStringExtra("CUserId");
                mAddressFragment.deleteUserLocal(userId);
                ConcreteLogin mConcreteLogin = ConcreteLogin.getInstance();
                mConcreteLogin.addOrDeleteContact(context,false,userId,null);
            } else if (value == 4) {
                mAddressFragment.getContactList();
            } else if (value == 5) {
                BookInit.getInstance().getCallBackRequestListener().classBackRequstPeopleOnSuccess();
            } else if (value == 6) {
                BookInit.getInstance().getCallBackRequestListener().classBackRequstPeopleOnFail();
            } else if (value == 11) {
                if (from != null && from.equals("login")) {
                    ComponentInit.getInstance().getSuccess().sysUserSuccess(false);
                }
            }
        }
    }
}
