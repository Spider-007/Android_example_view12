package htmitech.com.componentlibrary.listener;

import android.app.Activity;
import android.content.Context;

import com.minxing.kit.api.bean.ChatMessage;

/**
 * Created by Administrator on 2018/4/26.
 */

public interface ICallMXInit {
    void onKitMasterClear();

//
    void onChatNotify(Context context, ChatMessage message, boolean flag);

    void dismissNotification(Context context, int chatID);

    void onMessageRevoked(Context context, ChatMessage revokedMessage);

    void onActivityStart(Activity activity);

    void onActivityPause(Activity activity);

    void onStartActivityForResult(Activity activity);

    void onActivityResumed(Activity activity);


}
