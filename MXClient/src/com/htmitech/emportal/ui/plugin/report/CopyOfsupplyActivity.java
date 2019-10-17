package com.htmitech.emportal.ui.plugin.report;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SlidingDrawer;
import android.widget.SlidingDrawer.OnDrawerOpenListener;
import android.widget.TextView;
import android.widget.Toast;


import com.htmitech.emportal.R;
import com.htmitech.emportal.base.IBaseCallback;
import com.htmitech.emportal.entity.ReportResult;
import com.htmitech.emportal.entity.ReportResultInfo;
import com.htmitech.emportal.ui.chart.view.RingView;
import com.htmitech.emportal.ui.plugin.model.task.ZtChartModel;
import com.htmitech.emportal.ui.plugin.widget.NumericWheelAdapter;
import com.htmitech.emportal.ui.plugin.widget.OnWheelChangedListener;
import com.htmitech.emportal.ui.plugin.widget.WheelView;
import com.htmitech.emportal.ui.plugin.zt.entity.BuildResultInfo;
import com.htmitech.emportal.ui.plugin.zt.entity.JSItem;
import com.htmitech.emportal.ui.plugin.zt.entity.PJItem;
import com.htmitech.emportal.ui.plugin.zt.entity.ProjectNameResultInfo;
import com.htmitech.emportal.ui.plugin.zt.entity.TableChartResult1;
import com.htmitech.emportal.ui.plugin.zt.entity.TableChartResultInfo1;
import com.htmitech.ztcustom.zt.util.DensityUtil;
import com.minxing.client.widget.SystemMainTopRightPopMenu;
import com.radio.SegmentedGroup;


import java.io.Serializable;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import htmitech.com.componentlibrary.unit.PreferenceUtils;

/***
 *
 * @author Administrator 供应动态
 *
 */

public class CopyOfsupplyActivity extends Activity implements IBaseCallback,
        OnClickListener {

    // 标记 表示是否是第一次请求，如果是第一次请求则填充点亚数据
    public boolean ONE_POST = true;

    public final static int TON = 0;
    public final static int ROOT = 1;
    public final static int KM = 2;

    // 全局的 Tab 信号量
    private int currentSelect = TON;

    // 时间切换标记
    public final static int MONTH = 0;
    public final static int YEAR = 2;

    // 前后时间切换标记
    private static final int BEFORE_TIME = 0;
    private static final int CURRENT_TIME = 1;
    private static final int BEHIND_TIME = 2;

    // 前后时间按钮
    private ImageButton beforeButton = null;
    private ImageButton behindButton = null;

    private ImageButton leftBackButton;
    private ImageButton rightMenuButton;

    private TextView timeTextView;
    private SlidingDrawer mDrawer;

    private TextView chartText_1 = null;
    private TextView chartText_2 = null;
    private TextView chartText_3 = null;
    private TextView chartText_4 = null;

    private FrameLayout chart_1 = null;
    private FrameLayout chart_2 = null;
    private FrameLayout chart_3 = null;
    private FrameLayout chart_4 = null;

    // 自动匹配项
    private AutoCompleteTextView mProjectText;
    private AutoCompleteTextView mBuildText;

    private ArrayAdapter<String> projectAdapter;
    private ArrayAdapter<String> buildAdapter;

    private ArrayList<String> projectList;
    private ArrayList<String> buildList;

    // 项目名称的数据集
    private static PJItem[] projectNameItems = null;
    private static JSItem[] buildNameItems = null;
    String projectID = "";
    String buildID = "";

    // 时间处理工具
    Calendar mCalendar = null;
    SimpleDateFormat df = null;
    SimpleDateFormat dfValue = null;
    SimpleDateFormat dfYear = null;

    boolean neddUpdateDatetimerListner = true;
    WheelView year;
    WheelView month;

    // 记录当前选中的时间
    Date currentDate = null;

    // tab 容器
    private RadioGroup rgTab;

    private int timeFlag = MONTH;

    // 全局维护的 请求参数， 请求第一次数据的结构
    private PostEntity DataReportParameter;

    private TableChartResult1 mChartListData;

    // 下拉图
    private SystemMainTopRightPopMenu functionPopMenu;

    //
    private String reportGuid = "291d419a-c88f-464c-a6ac-ab1e5262ba20"; // 真实

    // private String reportGuid = "73af5f93-123b-4674-b98a-1f9ea9c4a269"; //模拟

    private ZtChartModel chartModel = new ZtChartModel(this, this);
    private ZtChartModel chartModel2 = null;
    private ZtChartModel chartModel3 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supply_tab_cp);

        // 启动帮助页

//        if (PreferenceUtils.isLoginSupply(this)) {
//            PreferenceUtils.setLoginSupply(this, false);
//            final Dialog dialog = new Dialog(CopyOfsupplyActivity.this,
//                    R.style.Dialog_Fullscreen);
//            dialog.setContentView(R.layout.layout_help_supply);
//            ImageView iv1 = (ImageView) dialog
//                    .findViewById(R.id.ivNavigater_supplytop1);
//            ImageView iv2 = (ImageView) dialog
//                    .findViewById(R.id.ivNavigater_supplytop2);
//            iv1.setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    v.setVisibility(View.GONE);
//                    ((ImageView) dialog
//                            .findViewById(R.id.ivNavigater_supplytop2))
//                            .setVisibility(View.VISIBLE);
//                }
//            });
//
//            iv2.setOnClickListener(new OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//                    dialog.dismiss();
//                }
//            });
//            dialog.show();
//        }

        initView();

        // // 请求填充首页视图数据的结构
        // ZtChartModel chartMode3 = new ZtChartModel(CopyOfsupplyActivity.this,
        // CopyOfsupplyActivity.this);

        chartModel.getDataFromServerByType(
                ZtChartModel.TYPE_GET_CHARTPARAMETER, null, reportGuid);

        // 只初始一次
        if (projectNameItems == null) {
            // 获取自动匹配列表
            chartModel2 = new ZtChartModel(this, this);
            chartModel2.getDataFromServerByType(
                    ZtChartModel.TYPE_GETPROJECTNAME, "");
        }
        if (buildList == null) {

            chartModel3 = new ZtChartModel(this, this);
            chartModel3.getDataFromServerByType(ZtChartModel.TYPE_GETBUILDNAME,
                    "");
        }
    }

    private void PostMan() {
        if (DataReportParameter == null)
            return;
        DataReportParameter.parameterList[0]
                .setValues(new String[]{PreferenceUtils.getLoginName(this)});
        DataReportParameter.parameterList[1]
                .setValues(new String[]{projectID});
        DataReportParameter.parameterList[4]
                .setValues(new String[]{buildID});
        if (timeFlag == MONTH) {
            DataReportParameter.parameterList[2]
                    .setValues(new String[]{dfValue.format(currentDate)});
            DataReportParameter.parameterList[3]
                    .setValues(new String[]{dfValue.format(currentDate)});
        } else if (timeFlag == YEAR) {
            DataReportParameter.parameterList[2]
                    .setValues(new String[]{dfYear.format(currentDate) + "01"});
            DataReportParameter.parameterList[3]
                    .setValues(new String[]{dfYear.format(currentDate) + "12"});
        }

        // TODO Auto-generated method stub
        if (projectID.equalsIgnoreCase("")) {
            if (mProjectText.getText().toString().length() > 0) {
                Toast.makeText(
                        CopyOfsupplyActivity.this,
                        "没有包含“" + mProjectText.getText().toString()
                                + "”关键字的项目或没有选择,请输入关键字后从列表中选择。",
                        Toast.LENGTH_LONG).show();
                mProjectText.setText("");
                return;
            }
        }

        if (buildID.equalsIgnoreCase("")) {
            if (mBuildText.getText().toString().length() > 0) {
                Toast.makeText(
                        CopyOfsupplyActivity.this,
                        "没有包含“" + mBuildText.getText().toString()
                                + "”关键字的建设单位或没有选择,请输入关键字后从列表中选择。",
                        Toast.LENGTH_LONG).show();
                mBuildText.setText("");
                return;
            }
        }
        chartModel.abort();
        // ZtChartModel chartMode = new ZtChartModel(this, this);
        chartModel.getDataFromServerByType(ZtChartModel.TYPE_GET_CHARTLIST,
                DataReportParameter);
    }

    private void initView() {
        initTitle();
        initTimeBar();
        initSlidingDrawerAndContent();
        initChartFrame();
        initDateChangeBar();
        initAutoCompleteBar();

    }

    private void initAutoCompleteBar() {
        mProjectText = (AutoCompleteTextView) findViewById(
                R.id.framelayout_supplt)
                .findViewById(R.id.slidingDrawer_content_ll)
                .findViewById(R.id.handle_ll_text)
                .findViewById(R.id.project_ll)
                .findViewById(R.id.project_autocompleteTextView);
        mBuildText = (AutoCompleteTextView) findViewById(
                R.id.framelayout_supplt)
                .findViewById(R.id.slidingDrawer_content_ll)
                .findViewById(R.id.handle_ll_text).findViewById(R.id.build_ll)
                .findViewById(R.id.build_autocompleteTextView);

        mProjectText.setText("");
        mBuildText.setText("");

        if (projectNameItems != null) {
            projectList = new ArrayList<String>();
            for (int i = 0; i < projectNameItems.length; i++) {
                projectList.add(projectNameItems[i].getProjectname());
            }
            projectAdapter = new ArrayAdapter<String>(this,
                    R.layout.activity_main2, projectList);
            mProjectText.setAdapter(projectAdapter);
        }

        if (buildNameItems != null) {
            buildList = new ArrayList<String>();
            for (int i = 0; i < buildNameItems.length; i++) {
                buildList.add(buildNameItems[i].getJSname());
            }
            buildAdapter = new ArrayAdapter<String>(this,
                    R.layout.activity_main2, buildList);
            mBuildText.setAdapter(buildAdapter);
        }

        mProjectText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equalsIgnoreCase("")) {
                    projectID = "";
                    return;
                } else {
                    projectID = "";
                    if (projectNameItems != null) {
                        for (int i = 0; i < projectNameItems.length; i++) {
                            if (s.toString().equals(
                                    projectNameItems[i].getProjectname())) {
                                projectID = projectNameItems[i].getProjectid();
                                break;
                            }
                        }
                    }
                }

            }
        });

        mBuildText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                if (s.toString().equalsIgnoreCase("")) {
                    buildID = "";
                    return;
                } else {
                    buildID = "";

                    if (buildNameItems != null) {//修改建设单位空指针bug
                        for (int i = 0; i < buildNameItems.length; i++) {
                            if (s.toString().equals(
                                    buildNameItems[i].getJSname())) {
                                buildID = buildNameItems[i].getJSid();
                                break;
                            }

                        }
                    }
                }
            }


        });
    }

    private void initDateChangeBar() {

        year = (WheelView) this.findViewById(R.id.year);
        year.setLabel("年");
        month = (WheelView) this.findViewById(R.id.month);
        month.setLabel("月");

        Calendar c = Calendar.getInstance();
        int curMonth = c.get(Calendar.MONTH);
        int curyear = c.get(Calendar.YEAR);

        NumericWheelAdapter year_adapter = new NumericWheelAdapter(1990, 2080);
        NumericWheelAdapter month_adapter = new NumericWheelAdapter(1, 12);

        year.setAdapter(year_adapter);
        year.setCurrentItem(curyear - 1990 + 1);
        month.setAdapter(month_adapter);
        month.setCurrentItem(curMonth);

        year.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                // TODO Auto-generated method stub
                Log.i("WheelView", "新的年值是：" + (1990 + newValue));

                // if (neddUpdateDatetimerListner)
                // return;

                int newYear = 1990 + newValue;

                Calendar mc = Calendar.getInstance();
                mc.clear();
                mc.setTime(currentDate);
                int curYear = mc.get(Calendar.YEAR); // 当前选择的时间-年份
                int curMonth = mc.get(Calendar.MONTH) + 1; // 当前选择的时间-月份

                mc.setTime(new Date());
                int intYear = mc.get(Calendar.YEAR);
                int intMonth = mc.get(Calendar.MONTH);

                if (newYear == intYear) {
                    // NumericWheelAdapter month_adapter = new
                    // NumericWheelAdapter(1, intMonth+1);
                    // month.setAdapter(month_adapter);
                    // if (curMonth > (intMonth+1)){
                    // month.setCurrentItem(intMonth);
                    // }
                } else {
                    NumericWheelAdapter month_adapter = new NumericWheelAdapter(
                            1, 12);
                    month.setAdapter(month_adapter);
                }

                // 设定年
                String strDateDesc = "";
                if (newYear > curYear) {
                    strDateDesc = changeYear(BEHIND_TIME, newYear - curYear,
                            false, false);
                } else if (newYear < curYear) {
                    strDateDesc = changeYear(BEFORE_TIME, curYear - newYear,
                            false, false);
                }

                if (timeFlag == MONTH) {
                    // 自然日期的本月
                    Date systemTime = new Date();

                    String thisMonth = getThisMonth(systemTime);
                    mCalendar.clear();
                    mCalendar.setTime(systemTime);
                    // mCalendar.setTime(mCalendar.getTime());//
                    mCalendar.add(Calendar.MONTH, -1);
                    // 自然日期的上月
                    String preMonth = getThisMonth(mCalendar.getTime());

                    mCalendar.clear();
                    mCalendar.setTime(currentDate);
                    behindButton.setVisibility(View.VISIBLE);

                    String currentMonth = getThisMonth(mCalendar.getTime());
                    // 判断是否是上月
                    if (currentMonth.equalsIgnoreCase(preMonth)) {// 判断是否是上月
                        currentMonth += "(上月)";
                    } else if (currentMonth.equalsIgnoreCase(thisMonth)) {
                        currentMonth += "(本月)";
                        // behindButton.setVisibility(View.INVISIBLE);
                    }
                    timeTextView.setText(currentMonth);
                } else {
                    timeTextView.setText(strDateDesc);
                }

            }
        });

        month.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                // TODO Auto-generated method stub
                Log.i("WheelView", "新的月值是：" + (1 + newValue));

                // if (neddUpdateDatetimerListner)
                // return;

                int newMonth = (1 + newValue);

                Calendar mc = Calendar.getInstance();
                mc.clear();
                mc.setTime(currentDate);
                int curMonth = mc.get(Calendar.MONTH) + 1; // 当前选择的时间-月份

                // 设定月
                String strDateDesc = "";
                if (newMonth > curMonth) {
                    strDateDesc = changeMonth(BEHIND_TIME, newMonth - curMonth,
                            false);
                } else if (newMonth < curMonth) {
                    strDateDesc = changeMonth(BEFORE_TIME, curMonth - newMonth,
                            false);
                }

                if (timeFlag == MONTH)
                    timeTextView.setText(strDateDesc);

            }
        });

        SegmentedGroup timeRadioGroup = (SegmentedGroup) findViewById(
                R.id.framelayout_supplt)
                .findViewById(R.id.slidingDrawer_content_ll)
                .findViewById(R.id.handle_ll_time)
                .findViewById(R.id.radio_group);
        RadioButton yueRb = (RadioButton) timeRadioGroup
                .findViewById(R.id.rb_yue);
        RadioButton yearRb = (RadioButton) timeRadioGroup
                .findViewById(R.id.rb_year);

        yueRb.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                timeFlag = MONTH;
                String date = null;
                if (timeFlag == MONTH) {
                    date = changeMonth(CURRENT_TIME, 1, true);
                } else if (timeFlag == YEAR) {
                    date = changeYear(CURRENT_TIME, 1, true, true);
                }
                if (date != null) {
                    timeTextView.setText(date);
                }

                year.setVisibility(View.VISIBLE);
                month.setVisibility(View.VISIBLE);

                // PostMan();
            }
        });
        yearRb.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                timeFlag = YEAR;
                String date = null;
                if (timeFlag == MONTH) {
                    date = changeMonth(CURRENT_TIME, 1, true);
                } else if (timeFlag == YEAR) {
                    date = changeYear(CURRENT_TIME, 1, true, true);
                }
                if (date != null) {
                    timeTextView.setText(date);
                }

                year.setVisibility(View.VISIBLE);
                month.setVisibility(View.GONE);

                // PostMan();
            }
        });

    }

    private void initChartFrame() {
        chart_1 = (FrameLayout) mDrawer.getContent()
                .findViewById(R.id.main_content).findViewById(R.id.row_ll_one)
                .findViewById(R.id.view_ll_1).findViewById(R.id.view_frame_1);
        chartText_1 = (TextView) mDrawer.getContent()
                .findViewById(R.id.main_content).findViewById(R.id.row_ll_one)
                .findViewById(R.id.view_ll_1).findViewById(R.id.view_text_1);

        chart_2 = (FrameLayout) mDrawer.getContent()
                .findViewById(R.id.main_content).findViewById(R.id.row_ll_one)
                .findViewById(R.id.view_ll_2).findViewById(R.id.view_frame_2);
        chartText_2 = (TextView) mDrawer.getContent()
                .findViewById(R.id.main_content).findViewById(R.id.row_ll_one)
                .findViewById(R.id.view_ll_2).findViewById(R.id.view_text_2);

        chart_3 = (FrameLayout) mDrawer.getContent()
                .findViewById(R.id.main_content).findViewById(R.id.row_ll_two)
                .findViewById(R.id.view_ll_3).findViewById(R.id.view_frame_3);
        chartText_3 = (TextView) mDrawer.getContent()
                .findViewById(R.id.main_content).findViewById(R.id.row_ll_two)
                .findViewById(R.id.view_ll_3).findViewById(R.id.view_text_3);

        chart_4 = (FrameLayout) mDrawer.getContent()
                .findViewById(R.id.main_content).findViewById(R.id.row_ll_two)
                .findViewById(R.id.view_ll_4).findViewById(R.id.view_frame_4);
        chartText_4 = (TextView) mDrawer.getContent()
                .findViewById(R.id.main_content).findViewById(R.id.row_ll_two)
                .findViewById(R.id.view_ll_4).findViewById(R.id.view_text_4);

        chartText_1.setText("需求量");
        chartText_2.setText("配置量");
        chartText_3.setText("订货量");
        chartText_4.setText("发货量");

        chart_1.setOnClickListener(this);
        chart_2.setOnClickListener(this);
        chart_3.setOnClickListener(this);
        chart_4.setOnClickListener(this);

    }

    private void changeChartList() {
        if (mChartListData == null || mChartListData.getListDataSet() == null || mChartListData.getListDataSet().getTable1() == null)
            return;

        double count = 0;
        double a = 0, b = 0, c = 0, d = 0;
        DecimalFormat df = new DecimalFormat();
        df.applyPattern("##0.000");
        df.setRoundingMode(RoundingMode.HALF_UP);
//		BigDecimal b1 = new BigDecimal(v1);
//		BigDecimal b2 = new BigDecimal(v2); 
//		return b1.add(b2).doubleValue();


        if (currentSelect == TON) {

            a = mChartListData.getListDataSet().getTable1().getJSONObject(0)
                    .getDoubleValue("吨");
            b = mChartListData.getListDataSet().getTable1().getJSONObject(1)
                    .getDoubleValue("吨");
            c = mChartListData.getListDataSet().getTable1().getJSONObject(2)
                    .getDoubleValue("吨");
            d = mChartListData.getListDataSet().getTable1().getJSONObject(3)
                    .getDoubleValue("吨");

        } else if (currentSelect == ROOT) {

            df = new DecimalFormat("###");

            a = mChartListData.getListDataSet().getTable1().getJSONObject(0)
                    .getDoubleValue("根");
            b = mChartListData.getListDataSet().getTable1().getJSONObject(1)
                    .getDoubleValue("根");
            c = mChartListData.getListDataSet().getTable1().getJSONObject(2)
                    .getDoubleValue("根");
            d = mChartListData.getListDataSet().getTable1().getJSONObject(3)
                    .getDoubleValue("根");

        } else if (currentSelect == KM) {
            a = mChartListData.getListDataSet().getTable1().getJSONObject(0)
                    .getDoubleValue("公里");
            b = mChartListData.getListDataSet().getTable1().getJSONObject(1)
                    .getDoubleValue("公里");
            c = mChartListData.getListDataSet().getTable1().getJSONObject(2)
                    .getDoubleValue("公里");
            d = mChartListData.getListDataSet().getTable1().getJSONObject(3)
                    .getDoubleValue("公里");
        }

        count = (a + b + c + d);

        RingView rv1 = new RingView(this, Color.parseColor("#3373B7"),
                Color.parseColor("#92D62D"));
        rv1.setAngle((float) (a / count * 100f));

        System.out.println("答应出来原始数据为=====" + a);
        rv1.setText(df.format(a));

        System.out.println("答应出来格式化数据为=====" + df.format(a));
        rv1.setTextSize(DensityUtil.dip2px(this, 16));
        chart_1.removeAllViews();
        chart_1.addView(rv1);

        RingView rv2 = new RingView(this, Color.parseColor("#3373B7"),
                Color.parseColor("#FB1A60"));//
        rv2.setAngle((float) (b / count * 100));
        rv2.setText(df.format(b));
        rv2.setTextSize(DensityUtil.dip2px(this, 16));
        chart_2.removeAllViews();
        chart_2.addView(rv2);

        RingView rv3 = new RingView(this, Color.parseColor("#3373B7"),
                Color.parseColor("#FCAF32"));
        rv3.setAngle((float) (c / count * 100));
        rv3.setText(df.format(c));
        rv3.setTextSize(DensityUtil.dip2px(this, 16));
        chart_3.removeAllViews();
        chart_3.addView(rv3);

        RingView rv4 = new RingView(this, Color.parseColor("#3373B7"),
                Color.parseColor("#4E519D"));
        rv4.setAngle((float) (d / count * 100));
        rv4.setText(df.format(d));
        rv4.setTextSize(DensityUtil.dip2px(this, 16));
        chart_4.removeAllViews();
        chart_4.addView(rv4);
    }

    private void initTimeBar() {
        timeTextView = (TextView) (findViewById(R.id.layout_supply_timebar)
                .findViewById(R.id.supply_time_textview));
        beforeButton = (ImageButton) (findViewById(R.id.layout_supply_timebar)
                .findViewById(R.id.before_button));
        behindButton = (ImageButton) (findViewById(R.id.layout_supply_timebar)
                .findViewById(R.id.behind_button));

        df = new SimpleDateFormat("yyyy年MM月", Locale.CHINA);
        dfYear = new SimpleDateFormat("yyyy", Locale.CHINA);
        dfValue = new SimpleDateFormat("yyyyMM", Locale.CHINA);

        mCalendar = Calendar.getInstance();

        // 给定一个日期
        currentDate = new Date();

        timeTextView.setText(getThisMonth(currentDate) + " (本月)");
        // this.behindButton.setVisibility(View.INVISIBLE);
        beforeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = null;
                if (timeFlag == MONTH) {
                    date = changeMonth(BEFORE_TIME, 1, true);
                } else if (timeFlag == YEAR) {
                    date = changeYear(BEFORE_TIME, 1, true, true);
                }
                if (date != null) {
                    timeTextView.setText(date);
                }
                PostMan();
            }
        });

        behindButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = null;
                if (timeFlag == MONTH) {
                    date = changeMonth(BEHIND_TIME, 1, true);
                } else if (timeFlag == YEAR) {
                    date = changeYear(BEHIND_TIME, 1, true, true);
                }
                if (date != null) {
                    timeTextView.setText(date);
                }
                PostMan();
            }
        });

        // timeTextView.addTextChangedListener(new TextWatcher() {
        //
        // @Override
        // public void onTextChanged(CharSequence s, int start, int before,
        // int count) {
        //
        // }
        //
        // @Override
        // public void beforeTextChanged(CharSequence s, int start, int count,
        // int after) {
        // }
        //
        // @Override
        // public void afterTextChanged(Editable s) {
        // // 当时间改变根据时间条件来拼接查询条件来请求服务器重新绘制报表
        // }
        // });

    }

    private void initSlidingDrawerAndContent() {
        mDrawer = (SlidingDrawer) (findViewById(R.id.framelayout_supplt)
                .findViewById(R.id.slidingDrawer_supplt));
        rgTab = (RadioGroup) mDrawer.getContent().findViewById(R.id.tab_menu);
        mDrawer.toggle(); // 默认抽开
        mDrawer.setOnDrawerOpenListener(new OnDrawerOpenListener() {

            @Override
            public void onDrawerOpened() {
                PostMan();
            }
        });

        Drawable drawable = CopyOfsupplyActivity.this.getResources()
                .getDrawable(R.drawable.btn_ton_pre);
        ((RadioButton) rgTab.findViewById(R.id.rb_ton)).setTextColor(Color
                .parseColor("#21BFF4"));
        ((RadioButton) rgTab.findViewById(R.id.rb_ton))
                .setCompoundDrawablesWithIntrinsicBounds(null, drawable, null,
                        null);

        rgTab.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_ton:
                        // 设置当前选中 - 切换图片
                        Drawable drawable = CopyOfsupplyActivity.this
                                .getResources().getDrawable(R.drawable.btn_ton_pre);
                        ((RadioButton) group.findViewById(R.id.rb_ton))
                                .setTextColor(Color.parseColor("#21BFF4"));
                        ((RadioButton) group.findViewById(R.id.rb_ton))
                                .setCompoundDrawablesWithIntrinsicBounds(null,
                                        drawable, null, null);

                        // 设置其他两项未选中图片
                        Drawable drawable1 = CopyOfsupplyActivity.this
                                .getResources().getDrawable(R.drawable.btn_root);
                        ((RadioButton) group.findViewById(R.id.rb_root))
                                .setTextColor(Color.parseColor("#707070"));
                        ((RadioButton) group.findViewById(R.id.rb_root))
                                .setCompoundDrawablesWithIntrinsicBounds(null,
                                        drawable1, null, null);

                        // 设置其他两项未选中图片
                        Drawable drawable2 = CopyOfsupplyActivity.this
                                .getResources().getDrawable(R.drawable.btn_km);
                        ((RadioButton) group.findViewById(R.id.rb_km))
                                .setTextColor(Color.parseColor("#707070"));
                        ((RadioButton) group.findViewById(R.id.rb_km))
                                .setCompoundDrawablesWithIntrinsicBounds(null,
                                        drawable2, null, null);

                        currentSelect = TON;
                        changeChartList();

                        break;
                    case R.id.rb_root:
                        // 设置当前选中 - 切换图片
                        Drawable drawable3 = CopyOfsupplyActivity.this
                                .getResources()
                                .getDrawable(R.drawable.btn_root_pre);
                        ((RadioButton) group.findViewById(R.id.rb_root))
                                .setTextColor(Color.parseColor("#21BFF4"));
                        ((RadioButton) group.findViewById(R.id.rb_root))
                                .setCompoundDrawablesWithIntrinsicBounds(null,
                                        drawable3, null, null);

                        // 设置其他两项未选中图片
                        Drawable drawable4 = CopyOfsupplyActivity.this
                                .getResources().getDrawable(R.drawable.btn_ton);
                        ((RadioButton) group.findViewById(R.id.rb_ton))
                                .setTextColor(Color.parseColor("#707070"));
                        ((RadioButton) group.findViewById(R.id.rb_ton))
                                .setCompoundDrawablesWithIntrinsicBounds(null,
                                        drawable4, null, null);

                        // 设置其他两项未选中图片
                        Drawable drawable5 = CopyOfsupplyActivity.this
                                .getResources().getDrawable(R.drawable.btn_km);
                        ((RadioButton) group.findViewById(R.id.rb_km))
                                .setTextColor(Color.parseColor("#707070"));
                        ((RadioButton) group.findViewById(R.id.rb_km))
                                .setCompoundDrawablesWithIntrinsicBounds(null,
                                        drawable5, null, null);

                        currentSelect = ROOT;
                        changeChartList();
                        break;
                    case R.id.rb_km:
                        // 设置当前选中 - 切换图片
                        Drawable drawable6 = CopyOfsupplyActivity.this
                                .getResources().getDrawable(R.drawable.btn_km_pre);
                        ((RadioButton) group.findViewById(R.id.rb_km))
                                .setTextColor(Color.parseColor("#21BFF4"));
                        ((RadioButton) group.findViewById(R.id.rb_km))
                                .setCompoundDrawablesWithIntrinsicBounds(null,
                                        drawable6, null, null);

                        // 设置其他两项未选中图片
                        Drawable drawable7 = CopyOfsupplyActivity.this
                                .getResources().getDrawable(R.drawable.btn_ton);
                        ((RadioButton) group.findViewById(R.id.rb_ton))
                                .setTextColor(Color.parseColor("#707070"));
                        ((RadioButton) group.findViewById(R.id.rb_ton))
                                .setCompoundDrawablesWithIntrinsicBounds(null,
                                        drawable7, null, null);

                        // 设置其他两项未选中图片
                        Drawable drawable8 = CopyOfsupplyActivity.this
                                .getResources().getDrawable(R.drawable.btn_root);
                        ((RadioButton) group.findViewById(R.id.rb_root))
                                .setTextColor(Color.parseColor("#707070"));
                        ((RadioButton) group.findViewById(R.id.rb_root))
                                .setCompoundDrawablesWithIntrinsicBounds(null,
                                        drawable8, null, null);

                        currentSelect = KM;
                        changeChartList();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    // 获取当前日期字符串
    private String getThisMonth(Date date) {
        String str = "";
        str += df.format(date);
        // 设置要传递的值，实际使用值: 开始时间
        // parameterValueDate = dfValue.format(mCalendar.getTime());
        return str;
    }

    private String getThisYear(Date date) {
        String str = "";
        str += dfYear.format(date);
        return str;
    }

    public class PostEntity implements Serializable {
        public String reportGuid;
        public ReportResult[] parameterList;
    }

    // updateDatetimer 为true时，表明是在title按下了上下翻按钮
    private String changeYear(int beforeOrBehind, int step,
                              boolean updateDatetimer, boolean needAdd) {
        mCalendar.clear();
        String description = "";
        // 判断去年和今年
        mCalendar.setTime(new Date());
        String currentYear = dfYear.format(mCalendar.getTime());
        mCalendar.add(Calendar.YEAR, -1);
        String preYear = dfYear.format(mCalendar.getTime());

        // 当前存住的日期
        mCalendar.clear();
        mCalendar.setTime(currentDate);
        String current = dfYear.format(mCalendar.getTime());

        if (beforeOrBehind == BEFORE_TIME) {
            mCalendar.add(Calendar.YEAR, 0 - step);
        } else if (beforeOrBehind == BEHIND_TIME) {
            // if (!current.equalsIgnoreCase(currentYear))
            mCalendar.add(Calendar.YEAR, step);

        }
        currentDate = mCalendar.getTime();
        current = dfYear.format(mCalendar.getTime());

        this.behindButton.setVisibility(View.VISIBLE);
        if (current.equalsIgnoreCase(currentYear) && needAdd) {
            description = " (本年)";
            // this.behindButton.setVisibility(View.INVISIBLE);
        } else if (current.equalsIgnoreCase(preYear) && needAdd) {
            description = " (去年)";
        }

        if (updateDatetimer) {
            // 设置时间控件的选择
            int curMonth = mCalendar.get(Calendar.MONTH);
            int curyear = mCalendar.get(Calendar.YEAR);
            year.setCurrentItem(curyear - 1990);
            month.setCurrentItem(curMonth);

        }
        neddUpdateDatetimerListner = updateDatetimer;
        return getThisYear(currentDate) + "年" + description;
    }

    // 更改时间方法
    private String changeMonth(int beforeOrBehind, int step,
                               boolean updateDatetimer) {
        // 自然日期的本月
        Date systemTime = new Date();

        String thisMonth = getThisMonth(systemTime);
        mCalendar.clear();
        mCalendar.setTime(systemTime);
        // mCalendar.setTime(mCalendar.getTime());//
        mCalendar.add(Calendar.MONTH, -1);
        // 自然日期的上月
        String preMonth = getThisMonth(mCalendar.getTime());

        mCalendar.clear();
        mCalendar.setTime(currentDate);
        this.behindButton.setVisibility(View.VISIBLE);

        String currentMonth = "";
        if (beforeOrBehind == 0) {
            mCalendar.add(Calendar.MONTH, 0 - step);
            currentDate = mCalendar.getTime();
            currentMonth = getThisMonth(mCalendar.getTime());
            // 判断是否是上月
            if (currentMonth.equalsIgnoreCase(preMonth)) {
                currentMonth += "(上月)";
            }
            // 判断是否
            else if (currentMonth.equalsIgnoreCase(thisMonth)) {
                currentMonth += "(本月)";
                // this.behindButton.setVisibility(View.INVISIBLE);
            }
        } else if (beforeOrBehind == 2) {
            // currentMonth = getThisMonth(mCalendar.getTime());
            // // 如果已经是本月 不做操作
            // if (currentMonth.equalsIgnoreCase(thisMonth))
            // return null;

            mCalendar.add(Calendar.MONTH, step);
            currentDate = mCalendar.getTime();
            currentMonth = getThisMonth(mCalendar.getTime());

            // 判断是否是上周
            if (currentMonth.equalsIgnoreCase(preMonth)) {
                currentMonth += "(上月)";
            }
            // 判断是否是上周
            else if (currentMonth.equalsIgnoreCase(thisMonth)) {
                currentMonth += "(本月)";
                // this.behindButton.setVisibility(View.INVISIBLE);
            }
        } else {
            currentMonth = getThisMonth(mCalendar.getTime());
            currentDate = mCalendar.getTime();
            currentMonth += "(本月)";

            // this.behindButton.setVisibility(View.INVISIBLE);

        }

        if (updateDatetimer) {
            // 设置时间控件的选择
            int curMonth = mCalendar.get(Calendar.MONTH);
            int curyear = mCalendar.get(Calendar.YEAR);
            year.setCurrentItem(curyear - 1990);
            month.setCurrentItem(curMonth);
        }

        return currentMonth;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finishWithAnimation();
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void finishWithAnimation() {
        chartModel.abort();
        if (chartModel3 != null)
            chartModel3.abort();
        if (chartModel2 != null)
            chartModel2.abort();
        super.finish();
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);

    }

    private void initTitle() {
        // title
        ((TextView) findViewById(R.id.title_name)).setText("供应动态");

        // 返回按钮
        leftBackButton = (ImageButton) findViewById(R.id.title_left_button);
        leftBackButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finishWithAnimation();
            }
        });

        // 右侧按钮
        // 下拉菜单按钮
        rightMenuButton = (ImageButton) findViewById(R.id.title_right_image_button);
        functionPopMenu = new SystemMainTopRightPopMenu(this);
        functionPopMenu.setForTodo(); // 设计下拉菜单项
        rightMenuButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!functionPopMenu.isShowing()) {
                    functionPopMenu.showAsDropDown(v);
                }
            }
        });
        rightMenuButton.setImageResource(R.drawable.btn_selector_more);
        rightMenuButton.setVisibility(View.GONE);
    }

    @Override
    public void onSuccess(int requestTypeId, Object result) {
        // 如果是从获取项目名字返回就 适配 查询匹配数据
        if (requestTypeId == ZtChartModel.TYPE_GETPROJECTNAME) {
            projectNameItems = ((ProjectNameResultInfo) result).getResult()
                    .getItem();
            projectList = new ArrayList<String>();
            for (int i = 0; i < projectNameItems.length; i++) {
                projectList.add(projectNameItems[i].getProjectname());
            }
            projectAdapter = new ArrayAdapter<String>(this,
                    R.layout.activity_main2, projectList);
            mProjectText.setAdapter(projectAdapter);

        } else if (requestTypeId == ZtChartModel.TYPE_GETBUILDNAME) {
            buildNameItems = ((BuildResultInfo) result).getResult().getItem();
            buildList = new ArrayList<String>();
            for (int i = 0; i < buildNameItems.length; i++) {
                buildList.add(buildNameItems[i].getJSname());
            }
            buildAdapter = new ArrayAdapter<String>(this,
                    R.layout.activity_main2, buildList);
            mBuildText.setAdapter(buildAdapter);

        } else if (requestTypeId == ZtChartModel.TYPE_GET_CHARTPARAMETER) {
            DataReportParameter = new PostEntity();
            DataReportParameter.parameterList = ((ReportResultInfo) result)
                    .getResult();
            DataReportParameter.reportGuid = reportGuid;
            PostMan();
        } else if (requestTypeId == ZtChartModel.TYPE_GET_CHARTLIST) {
            mChartListData = ((TableChartResultInfo1) result).getResult();

            changeChartList();

        }
    }

    @Override
    public void onFail(int requestTypeId, int statusCode, String errorMsg,
                       Object result) {
//		Toast.makeText(CopyOfsupplyActivity.this, errorMsg, Toast.LENGTH_LONG)
//				.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                Intent i = new Intent(this, PieChartActivity.class);
                String beginTime = "";
                String endTime = "";
                if (timeFlag == MONTH) {
                    beginTime = dfValue.format(currentDate);
                    endTime = beginTime;
                } else {
                    beginTime = dfYear.format(currentDate) + "01";
                    endTime = dfYear.format(currentDate) + "12";
                }
                i.putExtra(PieChartActivity.PIE_BEGIN_TIME, beginTime);
                i.putExtra(PieChartActivity.PIE_END_TIME, endTime);

                if (v.getId() == R.id.view_frame_1) { // 需求量
                    if (currentSelect == TON) {
                        i.putExtra(PieChartActivity.PIE_REPORT_ID,
                                "97867af5-1a12-43b1-b710-dc782be7ce46"); // 真实
                        // i.putExtra(PieChartActivity.PIE_REPORT_ID,
                        // "26535645-946a-4c5a-a9e5-b74cbd076929"); //模拟

                    } else if (currentSelect == ROOT) {
                        i.putExtra(PieChartActivity.PIE_REPORT_ID,
                                "84dfbbba-afa7-4f5d-a867-faf3f256c199"); // 真实
                        // i.putExtra(PieChartActivity.PIE_REPORT_ID,
                        // "e2f3d89c-158f-4ed9-be93-3795c85c0ac6"); //模拟
                    } else if (currentSelect == KM) {
                        i.putExtra(PieChartActivity.PIE_REPORT_ID,
                                "ac0686fa-45ab-4b7b-9439-b3d5677c266b");
                    }
                    i.putExtra("title", "需求量");

                } else if (v.getId() == R.id.view_frame_2) {// 配置量
                    if (currentSelect == TON) {
                        i.putExtra(PieChartActivity.PIE_REPORT_ID,
                                "7687ccd7-7126-4c7a-96c0-8dd37d4e1ea4");
                        // i.putExtra(PieChartActivity.PIE_REPORT_ID,
                        // "26535645-946a-4c5a-a9e5-b74cbd076929"); //模拟
                    } else if (currentSelect == ROOT) {
                        i.putExtra(PieChartActivity.PIE_REPORT_ID,
                                "91888a30-fcd7-46c6-a46d-4756ccd9a339");
                        // i.putExtra(PieChartActivity.PIE_REPORT_ID,
                        // "e2f3d89c-158f-4ed9-be93-3795c85c0ac6"); //模拟
                    } else if (currentSelect == KM) {
                        i.putExtra(PieChartActivity.PIE_REPORT_ID,
                                "22418b03-ce90-4203-8599-ec4ed8c83c13");
                    }
                    i.putExtra("title", "配置量");
                } else if (v.getId() == R.id.view_frame_3) { // 订货量
                    if (currentSelect == TON) {
                        i.putExtra(PieChartActivity.PIE_REPORT_ID,
                                "9ef58558-bcb2-4529-8331-87755fea52c0");
                    } else if (currentSelect == ROOT) {
                        i.putExtra(PieChartActivity.PIE_REPORT_ID,
                                "73e3a1a9-cace-45ea-bba3-843861fdf380");
                    } else if (currentSelect == KM) {
                        i.putExtra(PieChartActivity.PIE_REPORT_ID,
                                "4d67c633-1ea4-44ff-9d58-a4d5f42b9408");
                    }
                    i.putExtra("title", "订货量");
                } else if (v.getId() == R.id.view_frame_4) { // 发货量
                    if (currentSelect == TON) {
                        i.putExtra(PieChartActivity.PIE_REPORT_ID,
                                "4bdf960a-bd84-41e6-88bb-3852bf1d0e88");
                    } else if (currentSelect == ROOT) {
                        i.putExtra(PieChartActivity.PIE_REPORT_ID,
                                "658d1fb6-03ba-42b4-9108-5b3d3a50bcdd");
                    } else if (currentSelect == KM) {
                        i.putExtra(PieChartActivity.PIE_REPORT_ID,
                                "e5fac344-2cc9-4b4a-be5d-1a36a3d3ab19");
                    }
                    i.putExtra("title", "发货量");
                }
                i.putExtra(PieChartActivity.SELECT_TYPE, currentSelect);
                i.putExtra(PieChartActivity.BUILD_ID, buildID);
                i.putExtra(PieChartActivity.PROJECT_ID, projectID);

                i.putExtra(PieChartActivity.DIMENSIONALITY_TYPE, "");
                i.putExtra(PieChartActivity.DIMENSIONALITY_VALUE, "");

                i.putExtra("datestr", timeTextView.getText().toString());
                startActivity(i);
                break;
        }
    }

}
