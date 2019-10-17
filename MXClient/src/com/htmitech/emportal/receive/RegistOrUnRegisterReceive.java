package com.htmitech.emportal.receive;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * Created by heyang on 2017-1-18.
 */
public class RegistOrUnRegisterReceive {

    private static HomeKeyEventReceive receive = null;

    public static void registerHomeKeyEventReceive(Context context) {
        if (receive == null)
            receive = new HomeKeyEventReceive();
        IntentFilter filter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        context.registerReceiver(receive, filter);
    }

    public static void unRegisterHomeKeyEventReceive(Context context) {
        if (null != receive) {
            context.unregisterReceiver(receive);
        }
    }
}
