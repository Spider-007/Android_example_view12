package com.htmitech.ztcustom.zt.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.interfaces.QualityObjectionOverDialogCallBack;


public class QualityObjectionOverAlertDialog {
    private Context context;
    private Dialog dialog;
    private RelativeLayout lLayout_bg;
    private TextView tv_title;
    private EditText editText;
    private Button btn_neg;
    private Button btn_pos;
    private Display display;


    public QualityObjectionOverAlertDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public QualityObjectionOverAlertDialog builder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(R.layout.quality_objection_over_dialog_layout, null);

        // 获取自定义Dialog布局中的控件
        lLayout_bg = (RelativeLayout) view.findViewById(R.id.ll_quality_objection_over_dialog_background);
        tv_title = (TextView) view.findViewById(R.id.tv_quality_objection_over_dialog_title);
        editText = (EditText) view.findViewById(R.id.et_quality_objection_over);
        btn_neg = (Button) view.findViewById(R.id.btn_create_new_task_neg);
        btn_pos = (Button) view.findViewById(R.id.btn_create_new_task_pos);
        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.AlertDialogStyle);
        dialog.setContentView(view);
        // 调整dialog背景大小
        lLayout_bg.setLayoutParams(new FrameLayout.LayoutParams((int) (display.getWidth() * 0.65), (int) (display.getHeight() * 0.4)));
        return this;
    }


    public QualityObjectionOverAlertDialog setTitle(String title) {
        if ("".equals(title)) {
            tv_title.setText("标题");
        } else {
            tv_title.setText(title);
        }
        return this;
    }


    public QualityObjectionOverAlertDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }


    public QualityObjectionOverAlertDialog setPositiveButton(String text, final QualityObjectionOverDialogCallBack callBack) {
        if ("".equals(text)) {
            btn_pos.setText("确定");
        } else {
            btn_pos.setText(text);
        }
        btn_pos.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText != null && !"".equals(editText.getText().toString())) {
                    callBack.getEditText(editText.getText().toString());
                    dialog.dismiss();
                }
            }
        });
        return this;
    }

    public QualityObjectionOverAlertDialog setNegativeButton(String text,
                                                             final OnClickListener listener) {
        if ("".equals(text)) {
            btn_neg.setText("取消");
        } else {
            btn_neg.setText(text);
        }
        btn_neg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }

    public void show() {
        dialog.show();
    }


}
