package com.htmitech.proxy.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.htmitech.MyView.barline.bean.YData;
import com.htmitech.commonx.pulltorefresh.library.internal.Utils;
import com.htmitech.commonx.util.UIUtil;
import com.htmitech.emportal.R;
import com.htmitech.emportal.utils.UIUtils;
import com.htmitech.proxy.doman.Datas;

import java.util.ArrayList;
import java.util.List;

public class ReportDetailActivity extends Activity {

    private LinearLayout linearLayoutFristCloum;
    private LinearLayout linearLayoutLastCloum;
    private int screenWidth;//屏幕的宽度
    private String from;
    private String datas;
    private TextView title_name;
    public ImageButton title_left_button;
    public int cellHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_detail);
        initView();
        initData();
    }

    private void initView() {
        title_left_button = (ImageButton) findViewById(R.id.title_left_button);
        linearLayoutFristCloum = (LinearLayout) findViewById(R.id.report_detail_fristcloum);
        linearLayoutLastCloum = (LinearLayout) findViewById(R.id.report_detail_lastcloum);
        title_name = (TextView) findViewById(R.id.title_name);
    }

    private void initData() {
        Intent intent = getIntent();
        from = intent.getStringExtra("from");
        datas = intent.getStringExtra("datas");
        String titleName = intent.getStringExtra("titleName");
        title_name.setText(titleName);
        getScreenWidth();
        cellHeight = 40;
        getBarLineData();
        title_left_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReportDetailActivity.this.finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void getBarLineData() {
        if ("BarLine".equals(from)) {
            List<YData> yDataList = new ArrayList<YData>();
            yDataList = JSON.parseArray(datas, YData.class);
            if (yDataList != null)
                for (int i = 0; i < yDataList.size(); i++) {
                    //固定列的子linearlayout
                    LinearLayout linearLayoutFristCell = new LinearLayout(this);
                    linearLayoutFristCell.setOrientation(LinearLayout.VERTICAL);
                    linearLayoutFristCell.setGravity(Gravity.CENTER);
                    //固定列的子textview
                    TextView textViewFrist = createTextView();
                    textViewFrist.setText(yDataList.get(i).belong.name);
                    linearLayoutFristCell.addView(textViewFrist, LinearLayout.LayoutParams.WRAP_CONTENT, UIUtils.dip2px(cellHeight));
                    //固定列的横线
                    TextView textViewfristOne = new TextView(this);
                    textViewfristOne.setBackgroundColor(Color.BLACK);
                    textViewfristOne.setWidth(screenWidth / 3);
                    textViewfristOne.setHeight(UIUtils.dip2px(1));
                    linearLayoutFristCell.addView(textViewfristOne);

                    linearLayoutFristCloum.addView(linearLayoutFristCell);
                    //滑动部分的子linearlayout
                    LinearLayout linearLayoutCell = new LinearLayout(this);
                    linearLayoutCell.setOrientation(LinearLayout.HORIZONTAL);
                    linearLayoutCell.setGravity(Gravity.CENTER);

                    for (int k = 0; k < yDataList.get(i).data.size(); k++) {
                        //滑动部分的子textview
                        TextView textViewCell = createTextView();
                        textViewCell.setText(yDataList.get(i).data.get(k) + "");
                        linearLayoutCell.addView(textViewCell);
                        //滑动部分的竖线
                        TextView textView = new TextView(this);
                        textView.setBackgroundColor(Color.BLACK);
                        textView.setWidth(UIUtils.dip2px(1));
                        textView.setHeight(UIUtils.dip2px(cellHeight));
                        linearLayoutCell.addView(textView);
                    }

                    linearLayoutLastCloum.addView(linearLayoutCell);
                    //滑动部分的横线
                    TextView textView = new TextView(this);
                    textView.setBackgroundColor(Color.BLACK);
                    textView.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
                    textView.setHeight(UIUtils.dip2px(1));

                    linearLayoutLastCloum.addView(textView);
                }
        } else if ("Pie".equals(from)) {
            List<Datas> dataList = new ArrayList<Datas>();
            dataList = JSON.parseArray(datas, Datas.class);
            if (dataList != null) {
                double sum = 0;
                for (int i = 0; i < dataList.size(); i++) {
                    sum += Double.parseDouble(dataList.get(i).value.equals("") ? "0" : dataList.get(i).value);
                }
                for (int i = 0; i < dataList.size(); i++) {
                    //固定列的子linearlayout
                    LinearLayout linearLayoutFristCell = new LinearLayout(this);
                    linearLayoutFristCell.setOrientation(LinearLayout.VERTICAL);
                    linearLayoutFristCell.setGravity(Gravity.CENTER);
                    //固定列的子textview
                    TextView textViewFrist = createTextView();
                    textViewFrist.setText(dataList.get(i).name);
                    linearLayoutFristCell.addView(textViewFrist, LinearLayout.LayoutParams.WRAP_CONTENT, UIUtils.dip2px(cellHeight));
                    //固定列的横线
                    TextView textViewfristOne = new TextView(this);
                    textViewfristOne.setBackgroundColor(Color.BLACK);
                    textViewfristOne.setWidth(screenWidth / 3);
                    textViewfristOne.setHeight(UIUtils.dip2px(1));
                    linearLayoutFristCell.addView(textViewfristOne);

                    linearLayoutFristCloum.addView(linearLayoutFristCell);

                    LinearLayout linearLayoutCell = new LinearLayout(this);
                    linearLayoutCell.setOrientation(LinearLayout.HORIZONTAL);
                    linearLayoutCell.setGravity(Gravity.CENTER);


                    TextView textViewCellOne = createTextView();
                    textViewCellOne.setText(dataList.get(i).value + "");
                    linearLayoutCell.addView(textViewCellOne);

                    TextView textView = new TextView(this);
                    textView.setBackgroundColor(Color.BLACK);
                    textView.setWidth(UIUtils.dip2px(1));
                    textView.setHeight(UIUtils.dip2px(cellHeight));
                    linearLayoutCell.addView(textView);

                    TextView textViewCellTwo = createTextView();
                    textViewCellTwo.setText(String.format("%.2f", Double.parseDouble(dataList.get(i).value.equals("") ? "0" : dataList.get(i).value) / sum) + "%");
                    linearLayoutCell.addView(textViewCellTwo);

                    linearLayoutLastCloum.addView(linearLayoutCell);

                    TextView textViewPie = new TextView(this);
                    textViewPie.setBackgroundColor(Color.BLACK);
                    textViewPie.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
                    textViewPie.setHeight(UIUtils.dip2px(1));

                    linearLayoutLastCloum.addView(textViewPie);
                }
            }
        }

    }

    private TextView createTextView() {
        TextView textView = new TextView(this);
        textView.setTextSize(20);
        textView.setWidth(screenWidth / 3);
        textView.setHeight(UIUtils.dip2px(cellHeight));
        textView.setMinHeight(UIUtils.dip2px(cellHeight));
        textView.setGravity(Gravity.CENTER);
        textView.setEllipsize(TextUtils.TruncateAt.END);
        textView.setSingleLine(true);
        return textView;
    }

    private void getScreenWidth() {
        WindowManager wm = (WindowManager) getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE);
        screenWidth = wm.getDefaultDisplay().getWidth();
    }
}
