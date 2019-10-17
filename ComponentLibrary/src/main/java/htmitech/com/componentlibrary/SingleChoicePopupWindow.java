package htmitech.com.componentlibrary;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import htmitech.com.componentlibrary.datepicker.wheelview.OnWheelScrollListener;
import htmitech.com.componentlibrary.datepicker.wheelview.WheelView;
import htmitech.com.componentlibrary.datepicker.wheelview.adapter.ArrayWheelAdapter;
import htmitech.com.componentlibrary.datepicker.wheelview.adapter.NumericWheelAdapter;


/**
 * tony
 */
public class SingleChoicePopupWindow {

    private Context context;
    private PopupWindow pop;
    private View view;
    private OnClickOkListener onClickOkListener;

    private WheelView singleChoice;

    private TextView tv_show_time;

    private String[] singleChoiceArray;

    private Button btnOK, btnCancel;

    public void setCheckForm(String[] singleChoiceArray) {
        this.singleChoiceArray = singleChoiceArray;
        initData();
    }

    public SingleChoicePopupWindow(Context context) {
        this.context = context;
        view = LayoutInflater.from(context).inflate(R.layout.single_choice_popwindow, null);
        pop = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        initView();
        initPop();
    }

    public PopupWindow getPop() {
        return this.pop;
    }

    public void initView() {
        btnOK = (Button) view.findViewById(R.id.btnOK);
        btnCancel = (Button) view.findViewById(R.id.btnCancel);
    }

    private void initPop() {
        pop.setAnimationStyle(R.style.AnimBottom);
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        ColorDrawable dw = new ColorDrawable(0x00000000);
        dw.setAlpha(100);
        pop.setBackgroundDrawable(dw);
        pop.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        WindowManager.LayoutParams lp = ((Activity)context).getWindow()
                .getAttributes();
        lp.alpha = 0.7f;
        ((Activity)context).getWindow().setAttributes(lp);
    }

    public void initData() {

        singleChoice = (WheelView) view.findViewById(R.id.single_choice);
        ArrayWheelAdapter<String> arrayWheelAdapter = new ArrayWheelAdapter<String>(context, singleChoiceArray);
        singleChoice.setViewAdapter(arrayWheelAdapter);
        singleChoice.setCyclic(false);//是否可循环滑
        singleChoice.addScrollingListener(scrollListener);
        singleChoice.setVisibleItems(6 );//显示的行数
        singleChoice.setCurrentItem(0);//当前显示第几行

        tv_show_time = (TextView) view.findViewById(R.id.tv_show_time);
        tv_show_time.setText(setText(singleChoiceArray[0]));
        btnOK.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if (onClickOkListener != null) {
                    onClickOkListener.onClickOk("" + tv_show_time.getText().toString());
                }
                pop.dismiss();
                WindowManager.LayoutParams lp = ( (Activity)context).getWindow().getAttributes();
                lp.alpha = 1f;
                ((Activity)context).getWindow().setAttributes(lp);
            }
        });
        btnCancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if (onClickOkListener != null) {
                    onClickOkListener.onClickOk("");
                }
                pop.dismiss();
                WindowManager.LayoutParams lp = ( (Activity)context).getWindow().getAttributes();
                lp.alpha = 1f;
                ((Activity)context).getWindow().setAttributes(lp);
            }
        });
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp =( (Activity)context).getWindow().getAttributes();
                lp.alpha = 1f;
                ( (Activity)context).getWindow().setAttributes(lp);
            }
        });

    }

    OnWheelScrollListener scrollListener = new OnWheelScrollListener() {
        @Override
        public void onScrollingStarted(WheelView wheel) {
        }

        @Override
        public void onScrollingFinished(WheelView wheel) {
            if (!tv_show_time.isShown()) {
                if (wheel.getViewAdapter() instanceof ArrayWheelAdapter) {
                    tv_show_time.setText("" + ((ArrayWheelAdapter) wheel.getViewAdapter()).getItemText(wheel.getCurrentItem()));
                } else {
                    tv_show_time.setText("" + ((NumericWheelAdapter) wheel.getViewAdapter()).getItemText(wheel.getCurrentItem()));
                }
                return;
            }
            String mSingleChoice = singleChoiceArray[singleChoice.getCurrentItem()];
            String showText = setText(mSingleChoice);
            tv_show_time.setText("" + showText);

        }
    };

    /**
     * 设置显示选项
     * @return
     */
    public String setText(String singleChoice) {
        StringBuffer sb = new StringBuffer();
        if (singleChoice == null) {
            return "";
        }else {
            sb.append(singleChoice);
        }

        return sb.toString();
    }

    /**
     * 显示
     *
     * @param view
     */
    public void show(View view) {
        pop.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

    public boolean isShowing() {
        return pop.isShowing();
    }

    public void dismiss() {
        if (pop != null && isShowing())
            pop.dismiss();
    }

    /**
     * 隐藏监听
     *
     * @param onDismissListener
     */
    public void setOnDismissListener(PopupWindow.OnDismissListener onDismissListener) {
        pop.setOnDismissListener(onDismissListener);
    }

    public void setOnClickOkListener(OnClickOkListener onClickOkListener) {
        this.onClickOkListener = onClickOkListener;
    }

    public interface OnClickOkListener {
        public void onClickOk(String birthday);
    }

    public interface OnClickCancelListener {
        public void onClickCancel();
    }

}
