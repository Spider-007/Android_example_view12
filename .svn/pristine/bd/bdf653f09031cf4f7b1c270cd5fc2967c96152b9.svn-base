package htmitech.com.componentlibrary.unit;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.HashMap;

import htmitech.com.componentlibrary.R;

/**
 * Created by Administrator on 2018-7-18.
 */

public class ClickToast {

    private Toast mToast;
    private LinearLayout ll_toast_layout;
    private OnClickLinstener mOnClickLinstener;

    private static ClickToast mClickToast;

    private ClickToast() {

    }

    public ClickToast setmOnClickLinstener(OnClickLinstener mOnClickLinstener) {
        this.mOnClickLinstener = mOnClickLinstener;
        return mClickToast;
    }

    public static ClickToast get() {
        if (mClickToast == null) {
            mClickToast = new ClickToast();
        }
        return mClickToast;
    }

    public void showToast(final Context context, int duration) {

        if (mToast == null) {
            LayoutInflater inflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //自定义布局
            View view = inflater.inflate(R.layout.toast_form, null);
            view.getBackground().setAlpha(180);
            ll_toast_layout = (LinearLayout) view.findViewById(R.id.ll_toast_layout);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnClickLinstener != null) {
                        mOnClickLinstener.onClicks();
                    }

                    try {
                        HTActivityUnit.switchTo((Activity) context, "com.htmitech_updown.updownloadmanagement.UpDownLoadActivity", new HashMap<String, Object>());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    mToast.cancel();

                }
            });
            mToast = Toast.makeText(context.getApplicationContext(), "", duration);
            //这里可以指定显示位置
            //            mToast.setGravity(Gravity.BOTTOM, 0, 0);

            mToast.setView(view);
        }

        try {
            Object mTN;
            mTN = getField(mToast, "mTN");
            if (mTN != null) {
                Object mParams = getField(mTN, "mParams");
                if (mParams != null
                        && mParams instanceof WindowManager.LayoutParams) {
                    WindowManager.LayoutParams params = (WindowManager.LayoutParams) mParams;
                    //显示与隐藏动画
                    params.windowAnimations = R.style.ClickToast;
                    //Toast可点击
                    params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                            | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;

                    //设置viewgroup宽高
                    params.width = WindowManager.LayoutParams.MATCH_PARENT; //设置Toast宽度为屏幕宽度
                    params.height = WindowManager.LayoutParams.WRAP_CONTENT; //设置高度
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        mToast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.TOP, 0, 0);
        mToast.show();
    }

    /**
     * 反射字段
     *
     * @param object    要反射的对象
     * @param fieldName 要反射的字段名称
     */
    private static Object getField(Object object, String fieldName)
            throws NoSuchFieldException, IllegalAccessException {
        Field field = object.getClass().getDeclaredField(fieldName);
        if (field != null) {
            field.setAccessible(true);
            return field.get(object);
        }
        return null;
    }

    public interface OnClickLinstener {
        void onClicks();
    }

}