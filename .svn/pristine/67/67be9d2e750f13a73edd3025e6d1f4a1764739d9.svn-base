package com.htmitech.ztcustom.zt.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.interfaces.OnDialogInjuryDisposeItemClickListener;


public class InjuryDisposeItemClickAlertDialog implements OnClickListener {
    private Context context;
    private Dialog dialog;
    private RelativeLayout lLayout_bg;
    private TextView tv_title;
    private TextView tv_org;
    private TextView tv_gg;
    private TextView tv_dc;
    private TextView tv_hf;
    private Button btn_neg;
    private Button btn_pos;
    private Display display;
    private String chooseString = "";


    public InjuryDisposeItemClickAlertDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public InjuryDisposeItemClickAlertDialog builder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(R.layout.injury_dispose_item_click_dialog_layout, null);

        // 获取自定义Dialog布局中的控件
        lLayout_bg = (RelativeLayout) view.findViewById(R.id.ll_injury_dispose_item_click_dialog_background);
        tv_title = (TextView) view.findViewById(R.id.tv_injury_dispose_item_click_dialog_title);
        tv_org = (TextView) view.findViewById(R.id.tv_injury_dispose_item_click_dialog_org);
        tv_org.setOnClickListener(this);
        tv_gg = (TextView) view.findViewById(R.id.tv_injury_dispose_item_click_dialog_gg);
        tv_gg.setOnClickListener(this);
        tv_dc = (TextView) view.findViewById(R.id.tv_injury_dispose_item_click_dialog_dc);
        tv_dc.setOnClickListener(this);
        tv_hf = (TextView) view.findViewById(R.id.tv_injury_dispose_item_click_dialog_hf);
        tv_hf.setOnClickListener(this);

        btn_neg = (Button) view.findViewById(R.id.btn_create_new_task_neg);
        btn_pos = (Button) view.findViewById(R.id.btn_create_new_task_pos);
        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.AlertDialogStyle);
        dialog.setContentView(view);
        // 调整dialog背景大小
        lLayout_bg.setLayoutParams(new FrameLayout.LayoutParams((int) (display.getWidth() * 0.65), (int) (display.getHeight() * 0.4)));
        return this;
    }

    public InjuryDisposeItemClickAlertDialog setOrgName(String orgName) {
        tv_org.setText(orgName);
        return this;
    }


    public InjuryDisposeItemClickAlertDialog setTitle(String title) {
        if ("".equals(title)) {
            tv_title.setText("标题");
        } else {
            tv_title.setText(title);
        }
        return this;
    }


    public InjuryDisposeItemClickAlertDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }


    public InjuryDisposeItemClickAlertDialog setPositiveButton(String text, final OnDialogInjuryDisposeItemClickListener callBack) {
        if ("".equals(text)) {
            btn_pos.setText("确定");
        } else {
            btn_pos.setText(text);
        }
        btn_pos.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(chooseString)) {
                    callBack.callBack(chooseString);
                    dialog.dismiss();
                }
            }
        });
        return this;
    }

    public InjuryDisposeItemClickAlertDialog setNegativeButton(String text,
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

    @Override
    public void onClick(View v) {
        int id = v.getId();
        tv_org.setTextColor(context.getResources().getColor(R.color.mx_text_gray));
        tv_gg.setTextColor(context.getResources().getColor(R.color.mx_text_gray));
        tv_dc.setTextColor(context.getResources().getColor(R.color.mx_text_gray));
        tv_hf.setTextColor(context.getResources().getColor(R.color.mx_text_gray));
        if (v.getId() == R.id.tv_injury_dispose_item_click_dialog_org) {
            tv_org.setTextColor(Color.parseColor("#3A9BFC"));
            chooseString = "org";
        } else if (v.getId() == R.id.tv_injury_dispose_item_click_dialog_gg) {
            tv_gg.setTextColor(Color.parseColor("#3A9BFC"));
            chooseString = "gg";
        } else if (v.getId() == R.id.tv_injury_dispose_item_click_dialog_dc) {
            tv_dc.setTextColor(Color.parseColor("#3A9BFC"));
            chooseString = "dc";
        } else if (v.getId() == R.id.tv_injury_dispose_item_click_dialog_hf) {
            tv_hf.setTextColor(Color.parseColor("#3A9BFC"));
            chooseString = "hf";
        }

    }

}
