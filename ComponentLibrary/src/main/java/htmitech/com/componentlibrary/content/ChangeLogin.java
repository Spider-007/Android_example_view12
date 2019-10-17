package htmitech.com.componentlibrary.content;

import android.content.Context;
import android.content.Intent;

import htmitech.com.componentlibrary.listener.ICallLogin;

/**
 * Created by Administrator on 2018/4/26.
 */

public class ChangeLogin  {
    private ICallLogin mICallLogin;

    public void login(Context context, String usernameString, String passwordString){
        mICallLogin.login(context,usernameString,passwordString);
    }

    public void setClent(Context context){
        Intent intent = new Intent();
        intent.setClassName(context, "com.minxing.client.ClientTabActivity");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }
}
