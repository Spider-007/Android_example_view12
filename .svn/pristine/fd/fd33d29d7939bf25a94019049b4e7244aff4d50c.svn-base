package com.htmitech.ztcustom.zt.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.adapter.QualityObjectionItemClickDialogAdapter;
import com.htmitech.ztcustom.zt.domain.QualittObjectionDicVauleList;
import com.htmitech.ztcustom.zt.interfaces.OnQualityObjectionItemClickListener;

import java.util.ArrayList;


public class QualityObjectionItemClickAlertDialog {
    private Context context;
    private Dialog dialog;
    private RelativeLayout lLayout_bg;
    private TextView tv_title;
    private ListView listView;
    private Button btn_neg;
    private Button btn_pos;
    private Display display;
    private String chooseString = "";
    private String code = "";


    public QualityObjectionItemClickAlertDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public QualityObjectionItemClickAlertDialog builder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(R.layout.quality_objection_item_click_dialog_layout, null);

        // 获取自定义Dialog布局中的控件
        lLayout_bg = (RelativeLayout) view.findViewById(R.id.ll_quality_objection_item_click_dialog_background);
        tv_title = (TextView) view.findViewById(R.id.tv_quality_objection_item_click_dialog_title);
        listView = (ListView) view.findViewById(R.id.lv_quality_objection_item_click_dialog);
        btn_neg = (Button) view.findViewById(R.id.btn_create_new_task_neg);
        btn_pos = (Button) view.findViewById(R.id.btn_create_new_task_pos);
        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.AlertDialogStyle);
        dialog.setContentView(view);
        // 调整dialog背景大小
        lLayout_bg.setLayoutParams(new FrameLayout.LayoutParams((int) (display.getWidth() * 0.65), (int) (display.getHeight() * 0.35)));
        return this;
    }


    public QualityObjectionItemClickAlertDialog setTitle(String title) {
        if ("".equals(title)) {
            tv_title.setText("标题");
        } else {
            tv_title.setText(title);
        }
        return this;
    }


    public QualityObjectionItemClickAlertDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public QualityObjectionItemClickAlertDialog setData(final ArrayList<QualittObjectionDicVauleList> list) {
        final QualityObjectionItemClickDialogAdapter adapter = new QualityObjectionItemClickDialogAdapter(context, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                chooseString = list.get(position).value;
                code = list.get(position).code;
                adapter.setClickNumber(position);
                adapter.notifyDataSetChanged();
            }
        });
        return this;
    }


    public QualityObjectionItemClickAlertDialog setPositiveButton(String text, final OnQualityObjectionItemClickListener callBack) {
        if ("".equals(text)) {
            btn_pos.setText("确定");
        } else {
            btn_pos.setText(text);
        }
        btn_pos.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(chooseString)) {
                    callBack.callBack(chooseString, code);
                    dialog.dismiss();
                }
            }
        });
        return this;
    }

    public QualityObjectionItemClickAlertDialog setNegativeButton(String text, final OnClickListener listener) {
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
