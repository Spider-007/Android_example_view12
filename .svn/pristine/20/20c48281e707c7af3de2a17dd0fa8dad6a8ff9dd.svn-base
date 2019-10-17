package com.htmitech.commonx.util;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

/**
 * 对话框、提示等界面的工具类
 * 
 */
public class UIUtil {
    public static void showToast(Context context, int stringId) {
        showToast(context, context.getString(stringId));
    }

    public static void showToast(Context context, String text) {
        showToast(context, text, false);
    }

    public static void showToast(Context context, String text, boolean isLong) {
        if (context == null) {
            return;
        }

        Toast toast = Toast.makeText(context, text, isLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
        toast.show();
    }

    public static void showToast(Context context, int stringId, boolean isLong) {
        if (context == null) {
            return;
        }

        Toast toast =
                Toast.makeText(context, context.getString(stringId), isLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
        toast.show();
    }

    /**
     * 完全自定义Toast
     * 
     * @param context
     * @param layout 自定义Toast的VIEW
     * @param gravity 屏幕显示位置
     * @param xOffset x坐标
     * @param yOffset y坐标
     * @param isLong 是否长显示
     */
    public static void showToast(Context context, View layout, int gravity, int xOffset, int yOffset, boolean isLong) {
        if (context == null || layout == null) {
            return;
        }
        Toast toast = new Toast(context.getApplicationContext());
        toast.setGravity(gravity, xOffset, yOffset);
        toast.setDuration(isLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
}
