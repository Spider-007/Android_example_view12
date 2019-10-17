package htmitech.com.componentlibrary.content;

import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.ComponentName;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.minxing.kit.api.MXAPI;
import com.minxing.kit.api.bean.MXCurrentUser;
import com.minxing.kit.api.bean.MXNetwork;

import java.util.List;

import htmitech.com.componentlibrary.R;

/**
 * Created by Administrator on 2018/4/26.
 */

public class MXUtil {


    @SuppressWarnings("deprecation")
    public static boolean isApplicationForeground(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        KeyguardManager kgm = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);

        boolean isForeground = false;
        boolean isScreenActive = true;
        if (kgm.inKeyguardRestrictedInputMode()) {
            isScreenActive = false;
        }

        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (topActivity.getPackageName().equals(context.getPackageName())) {
                isForeground = true;
            }
        }
        return isForeground && isScreenActive;
    }


    public static void refreshHandlerLayout(Context context,View handlerView,boolean checkUpgradeMark,int id,
                                      MXCurrentUser currentUser) {
        if (handlerView != null) {
            TextView unreadNum = (TextView) handlerView
                    .findViewById(R.id.sms_num);
            ImageView unreadIcon = (ImageView) handlerView
                    .findViewById(id);

            List<MXNetwork> networks = currentUser.getNetworks();
            boolean isCircleUnread = false;
            for (MXNetwork network : networks) {
                if (currentUser.getNetworkID() != network.getId()) {
                    if (!isCircleUnread) {
                        isCircleUnread = MXAPI.getInstance(context)
                                .checkNetworkCircleUnread(network.getId());
                    }
                }
            }

            int unReadChatMessage = MXAPI.getInstance(context)
                    .queryNonCurrentNetworkChatUnread(
                            currentUser.getNetworkID());

            if (unReadChatMessage > 0 || isCircleUnread) {
                if (unReadChatMessage > 0) {
                    unreadNum.setVisibility(View.GONE);
                    unreadIcon.setVisibility(View.GONE);
                    String unReadCount = unReadChatMessage <= 99 ? String
                            .valueOf(unReadChatMessage) : "...";
                    unreadNum.setText(unReadCount);
                } else {
                    unreadNum.setVisibility(View.GONE);
                    unreadIcon.setVisibility(View.GONE);
                    unreadNum.setText("");
                }
            } else {
                unreadNum.setVisibility(View.GONE);
                unreadIcon.setVisibility(View.GONE);
                unreadNum.setText("");
            }

            if (unReadChatMessage == 0 && !isCircleUnread) {
                if (checkUpgradeMark) {
                    unreadIcon.setVisibility(View.GONE);
                } else {
                    unreadIcon.setVisibility(View.GONE);
                }
            }
        }
    }
}
