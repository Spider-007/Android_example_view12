package com.htmitech.ztcustom.zt.chinarailway;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ztcustom.R;
import com.htmitech.api.BookInit;
import com.htmitech.utils.OAConText;
import com.htmitech.ztcustom.zt.adapter.ContainerPagerAdapter;
import com.htmitech.ztcustom.zt.app.ZTCustomInit;
import com.htmitech.ztcustom.zt.base.BaseFragmentActivity;
import com.htmitech.ztcustom.zt.constant.ContantValues;
import com.htmitech.ztcustom.zt.constant.LineDataItems;
import com.htmitech.ztcustom.zt.constant.LineValueItems;
import com.htmitech.ztcustom.zt.constant.ParameterList;
import com.htmitech.ztcustom.zt.constant.ProduceCircleProgressResult;
import com.htmitech.ztcustom.zt.constant.ProduceLineProgressResult;
import com.htmitech.ztcustom.zt.constant.ProduceProgressParmas;
import com.htmitech.ztcustom.zt.constant.ProduceProgressResult;
import com.htmitech.ztcustom.zt.constant.XiangQingBean;
import com.htmitech.ztcustom.zt.fragment.ProductProgressShaiXuanFragment;
import com.htmitech.ztcustom.zt.interfaces.ObserverCallBack;
import com.htmitech.ztcustom.zt.interfaces.ProductProgressCallBackListener;
import com.htmitech.ztcustom.zt.pop.FunctionPopupWindow;
import com.htmitech.ztcustom.zt.thread.AnsynHttpRequest;
import com.htmitech.ztcustom.zt.unit.CHTTP;
import com.htmitech.ztcustom.zt.unit.ShareUnit;
import com.htmitech.ztcustom.zt.util.FastJsonUtils;
import com.htmitech.ztcustom.zt.util.ZTUnit;
import com.htmitech.ztcustom.zt.view.barchart.BarChart;
import com.htmitech.ztcustom.zt.view.barchart.BarData;
import com.htmitech.ztcustom.zt.view.barchart.IBarData;
import com.htmitech.ztcustom.zt.view.circledemo.CircleBar;
import com.htmitech.ztcustom.zt.view.linechart.LineChartView;
import com.minxing.kit.mail.widget.SlidingMenu;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 探伤生产进度动态
 *
 * @author heyang
 * @date 2016-4-10
 */
public class ProduceProgressActivity extends BaseFragmentActivity implements
        OnClickListener, ProductProgressCallBackListener {
    private ProgressBar progress1;
    private ProgressBar progress2;
    private ProgressBar progress3;
    private TextView tv_progress1_persent;
    private TextView tv_progress2_persent;
    private TextView tv_progress3_persent;
    private TextView tv_progress1_end;
    private TextView tv_progress2_end;
    private TextView tv_progress3_end;
    private TextView tv_progress2_top;
    private TextView tv_progress3_top;
    private TextView arror;
    private ImageView iv_arror, iv_arror_pre;
    private ImageView iv_point1;//环形图下面的点
    private ImageView iv_point2;//环形图下面的点
    private ImageView iv_vppoint1;//折线图下面的点
    private ImageView iv_vppoint2;//折线图下面的点
    private LinearLayout ll_point;//折线线面点的linearlayout
    private ImageView[] pointArray;//环形图下面的点的数组
    private ImageView[] vppointArray;//折线图下面的点的数组
    private int tempChoice = 0; // button点击切换初始化
    private ViewPager viewpager;
    private ArrayList<View> lists;
    private CircleBar circleBar1;
    private CircleBar circleBar2;
    private LineChartView wc_linearpicture, ap_linearpicture;
    private BarChart wc_barChart, ap_barChart;
    private ArrayList<IBarData> wc_mDataList = new ArrayList<IBarData>();
    private ArrayList<IBarData> ap_mDataList = new ArrayList<IBarData>();
    private TextView tvNum; // 折线图数据显示
    private LayoutInflater inflater;
    private FrameLayout flContainerLine; // 添加折线图帧布局
    private FrameLayout flContainerBar;//添加柱状图的真布局
    private ViewPager vpContainer;//折线图和柱状图切换的viewpager
    private ContainerPagerAdapter containerPagerAdapter;//折线和柱状图的适配器
    private View view1, view2;
    private int Y_value_span = 100; // y轴要显示坐标的数值间隔
    private float Ymax = 1000; // y轴的最大值
    private float Ymin = 0; // y轴的最小值
    public static SlidingMenu slidingMenu;
    private Button bt1, bt2;
    private TextView tv1_bt1_bottom, tv2_bt2_bottom;
    private ImageButton ib_back;// 上面返回按钮
    // private ImageButton ib_shaixuan;// 上面的筛选按钮
    private TextView tv_shaixuan;
    private LinearLayout ll_progress_shaixuan;
    private LinearLayout ll_shaixuan_wai;
    private TextView tv_produce_date;// 上面的日期
    private ImageView iv_date_cut;// 日期减少
    private ImageView iv_date_add;// 日期增加
    Calendar c = Calendar.getInstance();
    Calendar calendar = Calendar.getInstance();
    int year = c.get(Calendar.YEAR);
    int month = c.get(Calendar.MONTH) + 1;
    int monthnow = month;
    int yearnow = year;
    // 取到的全部参数列表
    private ArrayList<ParameterList> list_progress = new ArrayList<ParameterList>();
    private ArrayList<ParameterList> list_circle = new ArrayList<ParameterList>();
    private ArrayList<ParameterList> list_line = new ArrayList<ParameterList>();
    private String progressReportID = "ee312bd4-5e0c-4c6c-90de-f4ce3b1d8f56";// 上面进度条的reportID
    private String circleReportID = "c44edb83-8548-479a-be82-fb5a8c0241fb";// 饼状图的ReportID;
    private String lineReportID = "d7a95a82-77b5-4437-8383-da1034d511cd";// 折线图的ReportID；
    private String[] X_values = new String[]{"1月", "2月", "3月", "4月", "5月",
            "6月", "7月", "8月", "9月", "10月", "11月", "12月"};
    private float[] values_ap;
    private float[] values_wc;
    private float[] values_jh;
    private ImageView iv_i;
    private FunctionPopupWindow functionPopWindow;
    private int popWindowShow;
    private ArrayList<String> list_wz = new ArrayList<String>();
    private ArrayList<String> list_lx = new ArrayList<String>();
    private String Sblx = "GG";
    private String userID;
    private boolean flag = true;
    private int maxe = 1000;
    // 分享拼接的字符串
    private String apiUrlTemp;
    // 判断是否为分享过来的
    private boolean isShare;
    // 得到分享的内容
    private String shareParmas;
    String date = "";
    private boolean flag_frist_fragment = true;// 判断是否第一次传值过去
    public int startMonth = 0;
    private boolean flagCircleFinish = false;
    private boolean flagLineFinish = false;
    private boolean flagProgressFinish = false;
    private List<FrameLayout> listFramLayout = new ArrayList<FrameLayout>();//填装柱状图和折线图的framlayout的list
    int vpContainerCurPos = 0;//viewpager当前的位置

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            Bundle bundle = new Bundle();
            bundle = msg.getData();
            double i = bundle.getDouble("i") * maxe;
            // progress1.setProgress(i, true);
            progress1.setProgress((int) i);
            // progressTextViewGo(12, msg.what, tv_progress1_persent);
        }

        ;
    };
    @SuppressLint("HandlerLeak")
    private Handler handler2 = new Handler() {
        public void handleMessage(Message msg) {
            Bundle bundle = new Bundle();
            bundle = msg.getData();
            double i = bundle.getDouble("i");
            progress2.setProgress((int) i);
            // progress2.setProgress(i, true);
            // progressTextViewGo(10000, msg.what, tv_progress2_persent);
        }

        ;
    };
    @SuppressLint("HandlerLeak")
    private Handler handler3 = new Handler() {
        public void handleMessage(Message msg) {
            Bundle bundle = new Bundle();
            bundle = msg.getData();
            double i = bundle.getDouble("i");
            progress3.setProgress((int) i);
            // progress3.setProgress(i, true);
            // progressTextViewGo(20000, msg.what, tv_progress3_persent);
        }

        ;
    };
    @SuppressLint("HandlerLeak")
    private Handler handlerViewGone = new Handler() {
        public void handleMessage(Message msg) {
//            ll_point.setVisibility(View.GONE); 现在先不用以后放开
            vpContainer.setVisibility(View.GONE);
            arror.setClickable(true);
        }

        ;
    };
    private Handler wc_BarChart_Gone = new Handler() {
        public void handleMessage(Message msg) {
            wc_barChart.setVisibility(View.GONE);
            bt2.setClickable(true);
        }

        ;
    };
    private Handler ap_BarChart_Gone = new Handler() {
        public void handleMessage(Message msg) {
            ap_barChart.setVisibility(View.GONE);
            bt1.setClickable(true);
        }

        ;
    };
    private Handler wc_LinePicture_Gone = new Handler() {
        public void handleMessage(Message msg) {
            wc_linearpicture.setVisibility(View.GONE);
            bt2.setClickable(true);
        }

        ;
    };
    private Handler ap_LinePicture_Gone = new Handler() {
        public void handleMessage(Message msg) {
            ap_linearpicture.setVisibility(View.GONE);
            bt1.setClickable(true);
        }

        ;
    };

    @SuppressLint("NewApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_produceprogress);
        monthnow = month;
        String stat_day = "";
        try {
            stat_day = ZTCustomInit.get().getmCache().getmGetStatParam().stat_day;
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (stat_day != null && !stat_day.equals("") && Integer.parseInt(stat_day) > 14
                && c.get(Calendar.DAY_OF_MONTH) > Integer.parseInt(stat_day)) {
            startMonth = 1;
            if (month == 12) {
                year++;
                yearnow = year;
                month = startMonth;
                monthnow = month;
            } else {
                month = month + startMonth;
                monthnow = month;
            }
        }
        Intent intent = getIntent();
        isShare = intent.getBooleanExtra("flag_share", false);
        shareParmas = intent.getStringExtra("share");
        //ZTCustomInit.get().getmCache().getmListDetails().AccountId
        userID = ZTCustomInit.get().getmCache().getCisAccountDetail().cisAccountId;
        //    <--------------------Administrator -> 2019-8-6:15:11: 2010--------------------->
        for (int i = 0; i < ZTCustomInit.get().getmCache().getmDicitemList().size(); i++) {
            list_wz.add(ZTCustomInit.get().getmCache().getmDicitemList().get(i).getCode());
        }
        for (int j = 0; j < ZTCustomInit.get().getmCache().getLxDicitemList().size(); j++) {
            list_lx.add(ZTCustomInit.get().getmCache().getLxDicitemList().get(j).getCode());
        }
        initView();
        initData();
        // 初始化值
        initAdapter();
        // initGridViewOne();
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        if (isShare) {
            setViewGoneWhileShare();
        }
    }

    private void initData() {

        // if (flag) {
        // // getWZShuJuZiDian("XB");
        // list_wz.add("ZX");
        // list_lx.add("CSGH");
        // flag = false;
        // }
        // if (!flag) {

        if (isShare) {
//			shareParmas = shareParmas.substring(2);
        }
        showProgressDialog(this);
        // 获取条形状图的参数
        getContentValuesprogress(progressReportID);

        // 获取饼状环形图
        getContentValuescircle(circleReportID);

        // 获取折线图的数据
        getContentValuesline(lineReportID);
        // }

    }

    private void initAdapter() {
        // 填充ViewPager的数据适配器
        PagerAdapter mPagerAdapter = new PagerAdapter() {

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public int getCount() {
                return lists.size();
            }

            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
                container.removeView(lists.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(lists.get(position));
                return lists.get(position);
            }

        };
        viewpager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                for (int i = 0; i < pointArray.length; i++) {
                    pointArray[i].setBackgroundResource(R.drawable.table_point);
                }
                pointArray[arg0]
                        .setBackgroundResource(R.drawable.table_point_pre);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });
        viewpager.setAdapter(mPagerAdapter);

        vpContainer.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int arg0) {
                for (int i = 0; i < vppointArray.length; i++) {
                    vppointArray[i].setBackgroundResource(R.drawable.table_point);
                }
                vppointArray[arg0]
                        .setBackgroundResource(R.drawable.table_point_pre);
                setBarOrLineGoneOrVis();
                tvNum.setText("");
                vpContainerCurPos = arg0;
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }

    @SuppressLint("NewApi")
    private void initView() {

        inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        // viewpager中的view
        view1 = inflater.inflate(R.layout.activity_circle1, null);
        view2 = inflater.inflate(R.layout.activity_circle2, null);
        View view = findViewById(R.id.ll_bottom);
        // sideMenu = inflater.inflate(R.layout.activity_right_menu, null);

        flContainerLine = new FrameLayout(this);
        flContainerBar = new FrameLayout(this);
        arror = (TextView) findViewById(R.id.tv_arror);
        iv_arror = (ImageView) findViewById(R.id.iv_arror);
        iv_arror_pre = (ImageView) findViewById(R.id.iv_arror_pre);
        // 上面进度条对象
        progress1 = (ProgressBar) findViewById(R.id.iv_progressbar1);
        progress2 = (ProgressBar) findViewById(R.id.iv_progressbar2);
        progress3 = (ProgressBar) findViewById(R.id.iv_progressbar3);
        // 上面进度条的百分比
        tv_progress1_persent = (TextView) findViewById(R.id.tv_progress1_persent);
        tv_progress2_persent = (TextView) findViewById(R.id.tv_progress2_persent);
        tv_progress3_persent = (TextView) findViewById(R.id.tv_progress3_persent);
        // 上面进度条后面的总量
        tv_progress1_end = (TextView) findViewById(R.id.tv_progressbar1_end);
        tv_progress2_end = (TextView) findViewById(R.id.tv_progressbar2_end);
        tv_progress3_end = (TextView) findViewById(R.id.tv_progressbar3_end);
        tv_progress2_top = (TextView) findViewById(R.id.tv_data2);
        tv_progress3_top = (TextView) findViewById(R.id.tv_data3);
        viewpager = (ViewPager) findViewById(R.id.vp_circle);
        bt1 = (Button) findViewById(R.id.bt_1);
        bt2 = (Button) findViewById(R.id.bt_2);
        tv1_bt1_bottom = (TextView) findViewById(R.id.tv_bt1_bottom);
        tv2_bt2_bottom = (TextView) findViewById(R.id.tv_bt2_bottom);
        // llSideMenu = (LinearLayout) sideMenu.findViewById(R.id.ll_side_menu);

        tvNum = (TextView) findViewById(R.id.tv_cont);
        ib_back = (ImageButton) findViewById(R.id.ib_produce_back);
        tv_shaixuan = (TextView) findViewById(R.id.tv_produce_shaixuan);
        tv_shaixuan.setOnClickListener(this);
        ll_progress_shaixuan = (LinearLayout) findViewById(R.id.ll_product_shaixuan);
        ll_shaixuan_wai = (LinearLayout) findViewById(R.id.ll_product_shaixuan_wai);
        tv_produce_date = (TextView) findViewById(R.id.tv_produce_title_data);
        if (month < 10) {
            tv_produce_date.setText(year + "年" + "0" + month + "月");
        } else {
            tv_produce_date.setText(year + "年" + month + "月");
        }
        iv_date_add = (ImageView) findViewById(R.id.iv_produce_add);
        iv_date_cut = (ImageView) findViewById(R.id.iv_produce_cut);
        ib_back.setOnClickListener(this);
        iv_date_add.setOnClickListener(this);
        iv_date_cut.setOnClickListener(this);

        // 获得环形对象
        circleBar1 = (CircleBar) view1.findViewById(R.id.circle);
        circleBar2 = (CircleBar) view2.findViewById(R.id.circle);
        circleBar1.setDanWei("公里");
        circleBar2.setDanWei("公里");
        lists = new ArrayList<View>();
        arror.setOnClickListener(ProduceProgressActivity.this);
        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);

        // 设置进度条前背景
        // progress1.setFgDrawableRes(R.drawable.progressbars);
        // progress1.setBgDrawableRes(R.drawable.progressbar);
        // progress2.setFgDrawableRes(R.drawable.progressbars);
        // progress2.setBgDrawableRes(R.drawable.progressbar);
        // progress3.setFgDrawableRes(R.drawable.progressbars);
        // progress3.setBgDrawableRes(R.drawable.progressbar);
        lists.add(view1);
        lists.add(view2);

        // 下面的环形图的数据来源
        circleBar1.setString("月度计划安排率", "探伤月计划安排量");
        circleBar2.setString("月度计划完成率", "探伤月完成工作量");
        // 获取i对象
        iv_i = (ImageView) findViewById(R.id.iv_productprogress_i);
        iv_i.setOnClickListener(this);
        functionPopWindow = new FunctionPopupWindow(this, this);
        iv_point1 = (ImageView) findViewById(R.id.iv_progressproduce_point1);
        iv_point2 = (ImageView) findViewById(R.id.iv_progressproduce_point2);
        iv_vppoint1 = (ImageView) findViewById(R.id.iv_progressproduce_vppoint1);
        iv_vppoint2 = (ImageView) findViewById(R.id.iv_progressproduce_vppoint2);
        iv_point1.setBackgroundResource(R.drawable.table_point_pre);
        iv_point2.setBackgroundResource(R.drawable.table_point);
        iv_vppoint1.setBackgroundResource(R.drawable.table_point_pre);
        iv_vppoint2.setBackgroundResource(R.drawable.table_point);
        pointArray = new ImageView[2];
        pointArray[0] = iv_point1;
        pointArray[1] = iv_point2;
        vppointArray = new ImageView[2];
        vppointArray[0] = iv_vppoint1;
        vppointArray[1] = iv_vppoint2;
        vpContainer = (ViewPager) findViewById(R.id.vp_container);
        ll_point = (LinearLayout) findViewById(R.id.ll_point);
    }

    // 加载上面条形的数据
    private void loading(final double month, final double ap, final double wc) {
        // 第一条线
        Thread t = new Thread() {
            @Override
            public void run() {
                double i = 0;
                // 一共需要走2000毫秒，每次间隔10毫秒，一共要走11个，平均每次走的距离。
                double arv = month / (2000 / 10);
                while (i <= month) {
                    try {
                        Bundle bundle = new Bundle();
                        bundle.putDouble("i", i);
                        Message message = Message.obtain();
                        message.setData(bundle);
                        handler.sendMessage(message);
                        i += arv;
                        // Log.e("I", i+"");
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                super.run();
            }
        };
        t.start();
        // 第二条线
        new Thread(new Runnable() {

            @Override
            public void run() {
                double i = 0;
                // 一共需要走2000毫秒，每次间隔10毫秒，一共要走10000个，平均每次走的距离。
                double arv = ap / (2000 / 10);
                while (i <= ap) {
                    try {
                        Bundle bundle = new Bundle();
                        bundle.putDouble("i", i);
                        Message message = Message.obtain();
                        message.setData(bundle);
                        handler2.sendMessage(message);
                        i += arv;
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        // 第三条线
        new Thread(new Runnable() {

            @Override
            public void run() {
                double i = 0;
                // 一共需要走2000毫秒，每次间隔50毫秒，一共要走11个，平均每次走的距离。
                double arv = wc / (2000 / 10);
                while (i <= wc) {
                    try {
                        Bundle bundle = new Bundle();
                        bundle.putDouble("i", i);
                        Message message = Message.obtain();
                        message.setData(bundle);
                        handler3.sendMessage(message);
                        i += arv;
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tv_arror) {
            if (tempChoice % 2 == 0) {
                Animation mShowAction = new TranslateAnimation(
                        Animation.RELATIVE_TO_SELF, 0.0f,
                        Animation.RELATIVE_TO_SELF, 0.0f,
                        Animation.RELATIVE_TO_SELF, -0.86f,
                        Animation.RELATIVE_TO_SELF, 0.0f);
                mShowAction.setDuration(700);
                viewpager.setAnimation(mShowAction);

                vpContainer.setVisibility(View.VISIBLE);

                wc_linearpicture.setVisibility(View.GONE);
                ap_linearpicture.setVisibility(View.VISIBLE);
                wc_barChart.setVisibility(View.GONE);
                ap_barChart.setVisibility(View.VISIBLE);
                bt1.setVisibility(View.VISIBLE);
                bt2.setVisibility(View.VISIBLE);
                tv1_bt1_bottom.setVisibility(View.VISIBLE);
                iv_arror.setVisibility(View.GONE);
                iv_arror_pre.setVisibility(View.VISIBLE);
//                    ll_point.setVisibility(View.VISIBLE);  现在先不用以后再放开
            } else {

                Animation mHiddenAction = new TranslateAnimation(
                        Animation.RELATIVE_TO_SELF, 0.0f,
                        Animation.RELATIVE_TO_SELF, 0.0f,
                        Animation.RELATIVE_TO_SELF, 0.0f,
                        Animation.RELATIVE_TO_SELF, -0.86f);
                mHiddenAction.setDuration(700);
                viewpager.setAnimation(mHiddenAction);

                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            arror.setClickable(false);
                            Thread.sleep(700);
                            Message msg = Message.obtain();
                            handlerViewGone.sendMessage(msg);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }).start();

                ap_linearpicture.setVisibility(View.VISIBLE);
                wc_linearpicture.setVisibility(View.VISIBLE);
                ap_barChart.setVisibility(View.VISIBLE);
                wc_barChart.setVisibility(View.VISIBLE);
                bt1.setVisibility(View.INVISIBLE);
                bt2.setVisibility(View.INVISIBLE);
                tv1_bt1_bottom.setVisibility(View.INVISIBLE);
                tv2_bt2_bottom.setVisibility(View.INVISIBLE);
                iv_arror.setVisibility(View.VISIBLE);
                iv_arror_pre.setVisibility(View.GONE);
                tvNum.setVisibility(View.GONE);
            }
            tempChoice++;
        } else if (view.getId() == R.id.bt_1) {
            if (tv1_bt1_bottom.getVisibility() == View.INVISIBLE) {

                Animation aplinePicture_Translate_bt1 = new TranslateAnimation(
                        Animation.RELATIVE_TO_SELF, -1.0f,
                        Animation.RELATIVE_TO_SELF, 0.0f,
                        Animation.RELATIVE_TO_SELF, 0.0f,
                        Animation.RELATIVE_TO_SELF, 0.0f);
                aplinePicture_Translate_bt1.setDuration(700);

                Animation wclinePicture_Translate_bt1 = new TranslateAnimation(
                        Animation.RELATIVE_TO_SELF, 0.0f,
                        Animation.RELATIVE_TO_SELF, 1.0f,
                        Animation.RELATIVE_TO_SELF, 0.0f,
                        Animation.RELATIVE_TO_SELF, 0.0f);
                wclinePicture_Translate_bt1.setDuration(700);
                tv1_bt1_bottom.setVisibility(View.VISIBLE);
                tv2_bt2_bottom.setVisibility(View.INVISIBLE);
                tvNum.setText("");
                if (vpContainerCurPos == 0) {//根据当前viewpager的position判断哪个界面不走动画，防止动画混乱
                    new Thread(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            try {
                                bt2.setClickable(false);
                                Thread.sleep(700);
                                Message msg = Message.obtain();
                                wc_LinePicture_Gone.sendMessage(msg);
                            } catch (InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }).start();

                    // wc_linearpicture.setVisibility(View.GONE);
                    ap_linearpicture.setVisibility(View.VISIBLE);
                    wc_linearpicture.setAnimation(wclinePicture_Translate_bt1);
                    ap_linearpicture.setAnimation(aplinePicture_Translate_bt1);
                } else {
                    ap_linearpicture.setVisibility(View.VISIBLE);
                    wc_linearpicture.setVisibility(View.GONE);
                }

                if (vpContainerCurPos == 1) {//根据当前viewpager的position判断哪个界面不走动画，防止动画混乱
                    new Thread(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            try {
                                bt2.setClickable(false);
                                Thread.sleep(700);
                                Message msg = Message.obtain();
                                wc_BarChart_Gone.sendMessage(msg);
                            } catch (InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }).start();
                    ap_barChart.setVisibility(View.VISIBLE);
                    wc_barChart.setAnimation(wclinePicture_Translate_bt1);
                    ap_barChart.setAnimation(aplinePicture_Translate_bt1);
                } else {
                    ap_barChart.setVisibility(View.VISIBLE);
                    wc_barChart.setVisibility(View.GONE);
                }
            }
        } else if (view.getId() == R.id.bt_2) {
            if (tv2_bt2_bottom.getVisibility() == View.INVISIBLE) {
                Animation aplinePicture_Translate_bt2 = new TranslateAnimation(
                        Animation.RELATIVE_TO_SELF, 0.0f,
                        Animation.RELATIVE_TO_SELF, -1.0f,
                        Animation.RELATIVE_TO_SELF, 0.0f,
                        Animation.RELATIVE_TO_SELF, 0.0f);
                aplinePicture_Translate_bt2.setDuration(700);

                Animation wclinePicture_Translate_bt2 = new TranslateAnimation(
                        Animation.RELATIVE_TO_SELF, 1.0f,
                        Animation.RELATIVE_TO_SELF, 0.0f,
                        Animation.RELATIVE_TO_SELF, 0.0f,
                        Animation.RELATIVE_TO_SELF, 0.0f);
                wclinePicture_Translate_bt2.setDuration(700);

                tv1_bt1_bottom.setVisibility(View.INVISIBLE);
                tv2_bt2_bottom.setVisibility(View.VISIBLE);
                tvNum.setText("");
                if (vpContainerCurPos == 0) {//根据当前viewpager的position判断哪个界面不走动画，防止动画混乱

                    new Thread(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            try {
                                bt1.setClickable(false);
                                Thread.sleep(700);
                                Message msg = Message.obtain();
                                ap_LinePicture_Gone.sendMessage(msg);
                            } catch (InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }).start();

                    wc_linearpicture.setAnimation(wclinePicture_Translate_bt2);
                    ap_linearpicture.setAnimation(aplinePicture_Translate_bt2);
                    wc_linearpicture.setVisibility(View.VISIBLE);
                } else {
                    ap_linearpicture.setVisibility(View.GONE);
                    wc_linearpicture.setVisibility(View.VISIBLE);
                }
                if (vpContainerCurPos == 1) {//根据当前viewpager的position判断哪个界面不走动画，防止动画混乱
                    new Thread(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            try {
                                bt1.setClickable(false);
                                Thread.sleep(700);
                                Message msg = Message.obtain();
                                ap_BarChart_Gone.sendMessage(msg);
                            } catch (InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }).start();
                    wc_barChart.setAnimation(wclinePicture_Translate_bt2);
                    ap_barChart.setAnimation(aplinePicture_Translate_bt2);
                    wc_barChart.setVisibility(View.VISIBLE);
                } else {
                    wc_barChart.setVisibility(View.VISIBLE);
                    ap_barChart.setVisibility(View.GONE);
                }
            }
        } else if (view.getId() == R.id.ib_produce_back) {
            finish();
        } else if (view.getId() == R.id.iv_produce_add) {
            c.add(Calendar.MONTH, 1);
            int monthl = c.get(Calendar.MONTH) + 1;
            int yearl = c.get(Calendar.YEAR);
            if (monthl <= monthnow || yearl < yearnow) {
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH) + 1;
                if (month < 10) {
                    tv_produce_date.setText(year + "年" + "0" + month + "月");
                } else {
                    tv_produce_date.setText(year + "年" + month + "月");
                }
                initData();
            } else {
                c = Calendar.getInstance();
            }
        } else if (view.getId() == R.id.iv_produce_cut) {
            c.add(Calendar.MONTH, -1);
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH) + 1;
            if (month < 10) {
                tv_produce_date.setText(year + "年" + "0" + month + "月");
            } else {
                tv_produce_date.setText(year + "年" + month + "月");
            }
            initData();
        } else if (view.getId() == R.id.iv_productprogress_i) {
            if (!functionPopWindow.isShowing()) {
                functionPopWindow = new FunctionPopupWindow(this, this);
                int popupWidth = functionPopWindow.mMenuView.getMeasuredWidth();
                int popupHeight = functionPopWindow.mMenuView
                        .getMeasuredHeight();
                iv_i.setBackgroundResource(R.drawable.htmitech_shezhi);
                int[] location = new int[2];
                iv_i.getLocationOnScreen(location);
                // 显示窗口
                functionPopWindow.showAtLocation(iv_i, Gravity.NO_GRAVITY,
                        (location[0] + iv_i.getWidth() / 2) - popupWidth / 2,
                        location[1] - popupHeight); // 设置layout在PopupWindow中显示的位置
                functionPopWindow.update();
            } else {
                iv_i.setBackgroundResource(R.drawable.htmitech_shezhi_1);
                functionPopWindow.dismiss();
            }
        } else if (view.getId() == R.id.iv_fun_detail_data) {
            Intent intent = new Intent(ProduceProgressActivity.this,
                    TanShangProduceDetailsActivity.class);
            // 后续传请求数据的条件值，现在写死
            if (month >= 10) {
                date = year + "" + month;
            } else if (month < 10) {
                date = year + "0" + month;
            }
            intent.putExtra("date", date);
            intent.putExtra("userID", userID);
            intent.putExtra("SBLX", Sblx);
            intent.putStringArrayListExtra("list_wz", list_wz);
            intent.putStringArrayListExtra("list_lx", list_lx);
            startActivity(intent);
        } else if (view.getId() == R.id.tv_produce_shaixuan) {
            Bundle bundle = new Bundle();
            bundle.putString("SBLX", Sblx);
            bundle.putStringArrayList("list_wz", list_wz);
            bundle.putStringArrayList("list_lx", list_lx);
            bundle.putBoolean("flag_frist", flag_frist_fragment);
            if (functionPopWindow != null && functionPopWindow.isShowing()) {
                iv_i.setBackgroundResource(R.drawable.htmitech_shezhi_1);
                functionPopWindow.dismiss();
            }
            ll_shaixuan_wai.setVisibility(View.VISIBLE);
            ProductProgressShaiXuanFragment fragment = new ProductProgressShaiXuanFragment();
            fragment.setArguments(bundle);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                    .beginTransaction();
            ll_progress_shaixuan.setVisibility(View.VISIBLE);
            fragmentTransaction.replace(R.id.ll_product_shaixuan, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            // ll_search.setAnimation(AnimationUtils.makeOutAnimation(this,
            // true));// 推出
            ll_progress_shaixuan.setAnimation(AnimationUtils.makeInAnimation(
                    this, false));// 进入
            flag_frist_fragment = false;
        }

    }

    // 得到上面的条形柱状图数据
    private void getProgressData(List<String> list_wz, List<String> list_lx,
                                 String Sblx) {
        ProduceProgressParmas entity = new ProduceProgressParmas();
        entity.setReportGuid(progressReportID);
        if (!isShare) {// 如果不是分享的
            list_wz = ZTUnit.getSumitemValue((ArrayList<String>) list_wz);
            list_lx = ZTUnit.getSumitemValue((ArrayList<String>) list_lx);
            for (int i = 0; i < list_progress.size(); i++) {
                // for (ParameterList parameterList : list_progress) {
                if (list_progress.get(i).getName().equals("sblx_sub_list")) {
                    list_progress.get(i).setValues((ArrayList<String>) list_lx);
                }
                if (list_progress.get(i).getName().equals("line_list")) {
                    list_progress.get(i).setValues((ArrayList<String>) list_wz);
                }
                if (list_progress.get(i).getName().equals("userid")) {
                    ArrayList<String> userid_list = new ArrayList<String>();
                    userid_list.add(userID);
                    list_progress.get(i).setValues(userid_list);
                }
                if (list_progress.get(i).getName().equals("month")) {
                    ArrayList<String> month_list = new ArrayList<String>();
                    if (month >= 10) {
                        month_list.add(year + "" + month);
                        list_progress.get(i).setValues(month_list);
                    } else if (month < 10) {
                        month_list.add(year + "0" + month);
                        list_progress.get(i).setValues(month_list);
                    }
                }
                if (list_progress.get(i).getName().equals("sblx")) {
                    ArrayList<String> sblx_list = new ArrayList<String>();
                    sblx_list.add(Sblx);
                    list_progress.get(i).setValues(sblx_list);
                }
            }
        } else if (isShare) {// 如果是分享过来的
            list_wz.clear();
            list_lx.clear();
//            Log.e("ShareParmas", shareParmas);
            String[] ss = shareParmas.split("\\|");
            if (ss[0].equals("share")) {
                list_lx.add("");
            } else {
                String[] listlx_ss = ss[0].split(",");
                for (int i = 0; i < listlx_ss.length; i++) {
                    list_lx.add(listlx_ss[i]);
                }
            }
            if (ss[1].equals("share")) {
                list_wz.add("");
            } else {
                String[] listwz_ss = ss[1].split(",");
                for (int i = 0; i < listwz_ss.length; i++) {
                    list_wz.add(listwz_ss[i]);
                }
            }
            list_wz = ZTUnit.getSumitemValue((ArrayList<String>) list_wz);
            list_lx = ZTUnit.getSumitemValue((ArrayList<String>) list_lx);
            userID = ss[2];
            Sblx = ss[4];
            tv_produce_date.setText(ss[5]);
            for (int i = 0; i < list_progress.size(); i++) {
                // for (ParameterList parameterList : list_progress) {
                if (list_progress.get(i).getName().equals("sblx_sub_list")) {
                    list_progress.get(i).setValues((ArrayList<String>) list_lx);
                }
                if (list_progress.get(i).getName().equals("line_list")) {
                    list_progress.get(i).setValues((ArrayList<String>) list_wz);
                }
                if (list_progress.get(i).getName().equals("userid")) {
                    ArrayList<String> userid_list = new ArrayList<String>();
                    userid_list.add(userID);
                    list_progress.get(i).setValues(userid_list);
                }
                if (list_progress.get(i).getName().equals("month")) {
                    ArrayList<String> month_list = new ArrayList<String>();
                    month_list.add(ss[3]);
                    String shareMonth = ss[3].substring(ss[3].length() - 2);
                    if (shareMonth.startsWith("0")) {
                        month = Integer.parseInt(shareMonth.charAt(1) + "");
                    } else {
                        month = Integer.parseInt(shareMonth);
                    }
                    list_progress.get(i).setValues(month_list);
                }
                if (list_progress.get(i).getName().equals("sblx")) {
                    ArrayList<String> sblx_list = new ArrayList<String>();
                    sblx_list.add(Sblx);
                    list_progress.get(i).setValues(sblx_list);
                }
            }

        }
        entity.setParameterList(list_progress);
        AnsynHttpRequest.request(ProduceProgressActivity.this, entity,
                ContantValues.TANSHANG_PRODUCE_YEAR, CHTTP.POST,
                new ObserverCallBack() {

                    @Override
                    public void success(String successMessage) {
                        // TODO Auto-generated method stub
                        try {
                            ProduceProgressResult produceProgressResult = FastJsonUtils
                                    .getPerson(successMessage,
                                            ProduceProgressResult.class);
//                        Log.e("PRODUCE", successMessage);
                            flagProgressFinish = true;
                            if (flagProgressFinish && flagCircleFinish && flagLineFinish) {
                                dimssDialog();
                            }
                            if (produceProgressResult.getResult() != null) {
                                progress1.setMax(12 * maxe);

                                progress2
                                        .setMax(produceProgressResult.getResult()
                                                .getListDataSet().getTable1()
                                                .size() != 0 ? produceProgressResult
                                                .getResult().getListDataSet()
                                                .getTable1().get(0).getJh()
                                                : 0);
                                progress3
                                        .setMax(produceProgressResult.getResult()
                                                .getListDataSet().getTable1()
                                                .size() != 0 ? produceProgressResult
                                                .getResult().getListDataSet()
                                                .getTable1().get(0).getJh()
                                                : 0);
                                // progress1.setAllprogress(12);
                                // progress2.setAllprogress(produceProgressResult
                                // .getResult().getListDataSet().getTable1()
                                // .get(0).getJh());
                                // progress3.setAllprogress(produceProgressResult
                                // .getResult().getListDataSet().getTable1()
                                // .get(0).getJh());
                                DecimalFormat fnum_end = new DecimalFormat(
                                        "##0.000");
                                tv_progress1_end.setText("12月");
                                if (ProduceProgressActivity.this.Sblx.equals("GG")) {
                                    tv_progress2_end.setText(fnum_end
                                            .format(produceProgressResult
                                                    .getResult().getListDataSet()
                                                    .getTable1().get(0).getJh()));
                                    tv_progress3_end.setText(fnum_end
                                            .format(produceProgressResult
                                                    .getResult().getListDataSet()
                                                    .getTable1().get(0).getJh()));
                                } else {
                                    tv_progress2_end.setText(produceProgressResult
                                            .getResult().getListDataSet()
                                            .getTable1().get(0).getJh()
                                            + "");
                                    tv_progress3_end.setText(produceProgressResult
                                            .getResult().getListDataSet()
                                            .getTable1().get(0).getJh()
                                            + "");
                                }
                                // Log.e("AP",
                                // listdataset.getTable1().get(0).getAp()+"");
                                // Log.e("AP",
                                // listdataset.getTable1().get(0).getWc()+"");
                                loading(month, produceProgressResult.getResult()
                                        .getListDataSet().getTable1().get(0)
                                        .getAp(), produceProgressResult.getResult()
                                        .getListDataSet().getTable1().get(0)
                                        .getWc());
                                DecimalFormat fnum = new DecimalFormat("##0");
                                tv_progress1_persent.setText(fnum
                                        .format((float) (month / 12.0 * 100))
                                        + "%"
                                        + "(" + month + "月)");
                                tv_progress2_persent.setText(fnum
                                        .format(produceProgressResult.getResult()
                                                .getListDataSet().getTable1()
                                                .get(0).getApl() * 100)
                                        + "%("
                                        + produceProgressResult.getResult()
                                        .getListDataSet().getTable1()
                                        .get(0).getAp() + ")");
                                tv_progress3_persent.setText(

                                        fnum.format(produceProgressResult.getResult()
                                                .getListDataSet().getTable1().get(0)
                                                .getWcl() * 100)
                                                + "%("
                                                + produceProgressResult.getResult()
                                                .getListDataSet().getTable1()
                                                .get(0).getWc() + ")");

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void notNetwork() {
                        // TODO Auto-generated method stub
                        dimssDialog();
                    }

                    @Override
                    public void fail(String exceptionMessage) {
                        // TODO Auto-generated method stub

                    }
                });
    }

    // 得到下面饼图的数据
    private void getCircleData(List<String> list_wz, List<String> list_lx,
                               String Sblx) {
        ProduceProgressParmas entity = new ProduceProgressParmas();
        entity.setReportGuid(circleReportID);
        if (!isShare) {
            list_wz = ZTUnit.getSumitemValue((ArrayList<String>) list_wz);
            list_lx = ZTUnit.getSumitemValue((ArrayList<String>) list_lx);

            for (ParameterList parameterList : list_circle) {
                if (parameterList.getName().equals("sblx_sub_list")) {
                    parameterList.setValues((ArrayList<String>) list_lx);
                } else if (parameterList.getName().equals("line_list")) {
                    parameterList.setValues((ArrayList<String>) list_wz);
                } else if (parameterList.getName().equals("userid")) {
                    ArrayList<String> userid_list = new ArrayList<String>();
                    userid_list.add(userID);
                    parameterList.setValues(userid_list);
                } else if (parameterList.getName().equals("month")) {

                    ArrayList<String> month_list = new ArrayList<String>();
                    if (month >= 10) {
                        month_list.add(year + "" + month);
                        parameterList.setValues(month_list);
                    } else if (month < 10) {
                        month_list.add(year + "0" + month);
                        parameterList.setValues(month_list);
                    }
                } else if (parameterList.getName().equals("sblx")) {
                    ArrayList<String> sblx_list = new ArrayList<String>();
                    sblx_list.add(Sblx);
                    parameterList.setValues(sblx_list);
                }
            }
        } else if (isShare) {
            list_wz.clear();
            list_lx.clear();
            String[] ss = shareParmas.split("\\|");
            if (ss[0].equals("share")) {
                list_lx.add("");
            } else {
                String[] listlx_ss = ss[0].split(",");
                for (int i = 0; i < listlx_ss.length; i++) {
                    list_lx.add(listlx_ss[i]);
                }
            }
            if (ss[1].equals("share")) {
                list_wz.add("");
            } else {
                String[] listwz_ss = ss[1].split(",");
                for (int i = 0; i < listwz_ss.length; i++) {
                    list_wz.add(listwz_ss[i]);
                }
            }
            list_wz = ZTUnit.getSumitemValue((ArrayList<String>) list_wz);
            list_lx = ZTUnit.getSumitemValue((ArrayList<String>) list_lx);
            userID = ss[2];
            Sblx = ss[4];

            for (ParameterList parameterList : list_circle) {
                if (parameterList.getName().equals("sblx_sub_list")) {
                    parameterList.setValues((ArrayList<String>) list_lx);
                } else if (parameterList.getName().equals("line_list")) {
                    parameterList.setValues((ArrayList<String>) list_wz);
                } else if (parameterList.getName().equals("userid")) {
                    ArrayList<String> userid_list = new ArrayList<String>();
                    userid_list.add(userID);
                    parameterList.setValues(userid_list);
                } else if (parameterList.getName().equals("month")) {

                    ArrayList<String> month_list = new ArrayList<String>();
                    month_list.add(ss[3]);
                    parameterList.setValues(month_list);

                } else if (parameterList.getName().equals("sblx")) {
                    ArrayList<String> sblx_list = new ArrayList<String>();
                    sblx_list.add(Sblx);
                    parameterList.setValues(sblx_list);
                }
            }
        }

        entity.setParameterList(list_circle);
        AnsynHttpRequest.request(this, entity,
                ContantValues.TANSHANG_PRODUCE_YEAR, CHTTP.POST,
                new ObserverCallBack() {

                    @Override
                    public void success(String requestValue) {
                        // TODO Auto-generated method stub
                        ProduceCircleProgressResult pcpr = FastJsonUtils
                                .getPerson(requestValue,
                                        ProduceCircleProgressResult.class);
                        flagCircleFinish = true;
                        if (flagProgressFinish && flagCircleFinish && flagLineFinish) {
                            dimssDialog();
                        }
                        if (pcpr.getResult() != null && null != pcpr.getResult().getListDataSet()
                                && null != pcpr.getResult().getListDataSet().getTable1() && pcpr.getResult().getListDataSet().getTable1().size() > 0) {
                            double ydjh = pcpr.getResult().getListDataSet()
                                    .getTable1().get(0).getYdjh();
                            double ydap = pcpr.getResult().getListDataSet()
                                    .getTable1().get(0).getYdap();
                            double ydwc = pcpr.getResult().getListDataSet()
                                    .getTable1().get(0).getYdwc();
                            double ydapl = pcpr.getResult().getListDataSet()
                                    .getTable1().get(0).getYdapl();
                            double yuwcl = pcpr.getResult().getListDataSet()
                                    .getTable1().get(0).getYdwcl();

                            circleBar1.setMaxstepnumber(ydjh);
                            circleBar1.update(ydap, 1000);
                            circleBar1.setBottomNum(ydap);
                            circleBar2.setMaxstepnumber(ydap);
                            circleBar2.update(ydwc, 1000);
                            circleBar2.setBottomNum(ydwc);
                        }
                    }

                    @Override
                    public void notNetwork() {
                        // TODO Auto-generated method stub
                        dimssDialog();
                    }

                    @Override
                    public void fail(String exceptionMessage) {
                        // TODO Auto-generated method stub

                    }

                });
    }

    // 得到折线图的数据
    private void getLinData(List<String> list_wz, List<String> list_lx,
                            String Sblx) {
        ProduceProgressParmas entity = new ProduceProgressParmas();
        entity.setReportGuid(lineReportID);
        if (!isShare) {
            list_wz = ZTUnit.getSumitemValue((ArrayList<String>) list_wz);
            list_lx = ZTUnit.getSumitemValue((ArrayList<String>) list_lx);
            for (ParameterList parameterList : list_line) {
                if (parameterList.getName().equals("sblx_sub_list")) {
                    parameterList.setValues((ArrayList<String>) list_lx);
                } else if (parameterList.getName().equals("line_list")) {
                    parameterList.setValues((ArrayList<String>) list_wz);
                } else if (parameterList.getName().equals("userid")) {
                    ArrayList<String> userid_list = new ArrayList<String>();
                    userid_list.add(userID);
                    parameterList.setValues(userid_list);
                } else if (parameterList.getName().equals("month")) {

                    ArrayList<String> month_list = new ArrayList<String>();
                    if (month >= 10) {
                        month_list.add(year + "" + month);
                        parameterList.setValues(month_list);
                    } else if (month < 10) {
                        month_list.add(year + "0" + month);
                        parameterList.setValues(month_list);
                    }
                } else if (parameterList.getName().equals("sblx")) {
                    ArrayList<String> sblx_list = new ArrayList<String>();
                    sblx_list.add(Sblx);
                    parameterList.setValues(sblx_list);
                }
            }
        } else if (isShare) {
            list_wz.clear();
            list_lx.clear();
            String[] ss = shareParmas.split("\\|");
            if (ss[0].equals("share")) {
                list_lx.add("");
            } else {
                String[] listlx_ss = ss[0].split(",");
                for (int i = 0; i < listlx_ss.length; i++) {
                    list_lx.add(listlx_ss[i]);
                }
            }
            if (ss[1].equals("share")) {
                list_wz.add("");
            } else {
                String[] listwz_ss = ss[1].split(",");
                for (int i = 0; i < listwz_ss.length; i++) {
                    list_wz.add(listwz_ss[i]);
                }
            }
            list_wz = ZTUnit.getSumitemValue((ArrayList<String>) list_wz);
            list_lx = ZTUnit.getSumitemValue((ArrayList<String>) list_lx);
            userID = ss[2];
            Sblx = ss[4];
            for (ParameterList parameterList : list_line) {
                if (parameterList.getName().equals("sblx_sub_list")) {
                    parameterList.setValues((ArrayList<String>) list_lx);
                } else if (parameterList.getName().equals("line_list")) {
                    parameterList.setValues((ArrayList<String>) list_wz);
                } else if (parameterList.getName().equals("userid")) {
                    ArrayList<String> userid_list = new ArrayList<String>();
                    userid_list.add(userID);
                    parameterList.setValues(userid_list);
                } else if (parameterList.getName().equals("month")) {

                    ArrayList<String> month_list = new ArrayList<String>();
                    month_list.add(ss[3]);
                    parameterList.setValues(month_list);
                } else if (parameterList.getName().equals("sblx")) {
                    ArrayList<String> sblx_list = new ArrayList<String>();
                    sblx_list.add(Sblx);
                    parameterList.setValues(sblx_list);
                }
            }
        }
        entity.setParameterList(list_line);
        AnsynHttpRequest.request(this, entity, ContantValues.TANSHANG_ZHEXIAN,
                CHTTP.POST, new ObserverCallBack() {

                    @Override
                    public void success(String successMessage) {
                        // TODO Auto-generated method stub
                        ProduceLineProgressResult pcpr = FastJsonUtils
                                .getPerson(successMessage,
                                        ProduceLineProgressResult.class);
                        flagLineFinish = true;
                        if (flagProgressFinish && flagCircleFinish && flagLineFinish) {
                            dimssDialog();
                        }
                        if (pcpr.getResult() != null) {
                            Ymax = pcpr.getResult().getYobj().getMaxValue();
                            Ymin = pcpr.getResult().getYobj().getMinValue();
                            List<LineDataItems> list_LineDataItems = pcpr
                                    .getResult().getDataItems();
                            for (int i = 0; i < list_LineDataItems.size(); i++) {
                                List<LineValueItems> list_LineValueItems = list_LineDataItems
                                        .get(i).getValueItems();
                                if (list_LineDataItems.get(i).getDes()
                                        .equals("年余月计划")) {
                                    List<LineValueItems> list_LineValueItems_ls = new ArrayList<LineValueItems>();

                                    for (int j = 0; j < list_LineValueItems
                                            .size(); j++) {
                                        if (list_LineValueItems.get(j)
                                                .getIsValid()) {
                                            list_LineValueItems_ls
                                                    .add(list_LineValueItems
                                                            .get(j));
                                        }
                                    }
                                    values_jh = new float[list_LineValueItems_ls
                                            .size()];
                                    for (int j = 0; j < list_LineValueItems_ls
                                            .size(); j++) {
                                        values_jh[j] = list_LineValueItems.get(
                                                j).getValue();
                                    }
                                } else if (list_LineDataItems.get(i).getDes()
                                        .equals("年余月安排")) {
                                    List<LineValueItems> list_LineValueItems_ls = new ArrayList<LineValueItems>();
                                    for (int j = 0; j < list_LineValueItems
                                            .size(); j++) {
                                        if (list_LineValueItems.get(j)
                                                .getIsValid()) {
                                            list_LineValueItems_ls
                                                    .add(list_LineValueItems
                                                            .get(j));
                                        }
                                    }
                                    values_ap = new float[list_LineValueItems_ls
                                            .size()];
                                    for (int j = 0; j < list_LineValueItems_ls
                                            .size(); j++) {
                                        values_ap[j] = list_LineValueItems.get(
                                                j).getValue();
                                    }
                                } else if (list_LineDataItems.get(i).getDes()
                                        .equals("年余月完成")) {
                                    List<LineValueItems> list_LineValueItems_ls = new ArrayList<LineValueItems>();
                                    for (int j = 0; j < list_LineValueItems
                                            .size(); j++) {
                                        if (list_LineValueItems.get(j)
                                                .getIsValid()) {
                                            list_LineValueItems_ls
                                                    .add(list_LineValueItems
                                                            .get(j));
                                        }
                                    }
                                    values_wc = new float[list_LineValueItems_ls
                                            .size()];
                                    for (int j = 0; j < list_LineValueItems_ls
                                            .size(); j++) {
                                        values_wc[j] = list_LineValueItems.get(
                                                j).getValue();
                                    }
                                }
                            }
                            float jh_min = 0;
                            float jh_max = 0;
                            for (int i = 0; i < values_jh.length; i++) {
                                if (values_jh[i] < jh_min) {
                                    jh_min = values_jh[i];
                                }
                                if (values_jh[i] > jh_max) {
                                    jh_max = values_jh[i];
                                }
                            }
                            float ap_min = 0;
                            float ap_max = 0;
                            for (int i = 0; i < values_ap.length; i++) {
                                if (values_ap[i] < ap_min) {
                                    ap_min = values_ap[i];
                                }
                                if (values_ap[i] > ap_max) {
                                    ap_max = values_ap[i];
                                }
                            }
                            float wc_min = 0;
                            float wc_max = 0;
                            for (int i = 0; i < values_wc.length; i++) {
                                if (values_wc[i] < wc_min) {
                                    wc_min = values_wc[i];
                                }
                                if (values_wc[i] > wc_max) {
                                    wc_max = values_wc[i];
                                }
                            }
                            int ap_jg = 0;
                            ap_max = (int) ap_max;
                            if (ap_max < 10 && ap_max >= 0) {
                                ap_jg = 1;
                            } else if (ap_max >= 10 && ap_max < 100) {
                                ap_jg = (((int) (ap_max / 10)) + 1);
                            } else if (ap_max >= 100 && ap_max < 1000) {
                                ap_jg = (((int) (ap_max / 100)) + 1) * 10;
                            } else if (ap_max >= 1000) {
                                ap_jg = (((int) (ap_max / 1000)) + 1) * 100;
                            }
                            ap_max = ap_jg * 10;
                            float ap_ls = 0;
                            while (ap_ls < Math.abs(ap_min)) {
                                ap_ls += ap_jg;
                            }
                            if (ap_min < 0) {
                                ap_min = -(int) ap_ls;
                            }
                            int wc_jg = 0;
                            wc_max = (int) wc_max;
                            if (wc_max < 10 && wc_max >= 0) {
                                wc_jg = 1;
                            } else if (wc_max >= 10 && wc_max < 100) {
                                wc_jg = (((int) (wc_max / 10)) + 1);
                            } else if (wc_max >= 100 && wc_max < 1000) {
                                wc_jg = (((int) (wc_max / 100)) + 1) * 10;
                            } else if (wc_max >= 1000) {
                                wc_jg = (((int) (wc_max / 1000)) + 1) * 100;
                            }
                            wc_max = wc_jg * 10;
                            float wc_ls = 0;
                            while (wc_ls < Math.abs(wc_min)) {
                                wc_ls += wc_jg;
                            }
                            if (wc_min < 0) {
                                wc_min = -(int) wc_ls;
                            }
                            int jh_jg = 0;
                            jh_max = (int) jh_max;
                            if (jh_max < 10 && jh_max >= 0) {
                                jh_jg = 1;
                            } else if (jh_max >= 10 && jh_max < 100) {
                                jh_jg = (((int) (jh_max / 10)) + 1);
                            } else if (jh_max >= 100 && jh_max < 1000) {
                                jh_jg = (((int) (jh_max / 100)) + 1) * 10;
                            } else if (jh_max >= 1000) {
                                jh_jg = (((int) (jh_max / 1000)) + 1) * 100;
                            }
                            jh_max = jh_jg * 10;
                            float jh_ls = 0;
                            while (jh_ls < Math.abs(jh_min)) {
                                jh_ls += jh_jg;
                            }
                            if (jh_min < 0) {
                                jh_min = -(int) jh_ls;
                            }
                            int YInterval_ap = (int) ((ap_max - ap_min) / 10);
                            int YInterval_wc = (int) ((wc_max - wc_min) / 10);
                            // 构造函数传参
                            ap_linearpicture = new LineChartView(
                                    ProduceProgressActivity.this, values_ap,
                                    X_values, ap_max, ap_min, ap_jg, values_jh);
                            wc_linearpicture = new LineChartView(
                                    ProduceProgressActivity.this, values_wc,
                                    X_values, wc_max, wc_min, wc_jg, values_jh);

                            //柱状图对象
                            wc_barChart = new BarChart(ProduceProgressActivity.this);
                            ap_barChart = new BarChart(ProduceProgressActivity.this);
                            wc_barChart.setPadding(80, 0, 0, 0);
                            ap_barChart.setPadding(80, 0, 0, 0);
                            List<Float> datas_wc = new ArrayList<Float>();
                            List<Float> datas_ap = new ArrayList<Float>();
                            for (int wc_valuesize = 0; wc_valuesize < values_wc.length; wc_valuesize++) {
                                datas_wc.add(values_wc[wc_valuesize]);
                            }
                            for (int ap_valuesize = 0; ap_valuesize < values_ap.length; ap_valuesize++) {
                                datas_ap.add(values_ap[ap_valuesize]);
                            }
                            //初始化数据一类的
                            if (wc_mDataList != null)
                                wc_mDataList.clear();
                            if (ap_mDataList != null)
                                ap_mDataList.clear();
                            initBarData(wc_mDataList, datas_wc);
                            initBarData(ap_mDataList, datas_ap);
                            setBarChart(wc_barChart, wc_mDataList, YInterval_wc);
                            setBarChart(ap_barChart, ap_mDataList, YInterval_ap);
                            flContainerLine.removeAllViews();
                            flContainerLine.addView(wc_linearpicture);
                            flContainerLine.addView(ap_linearpicture);
                            flContainerBar.removeAllViews();
                            flContainerBar.addView(wc_barChart);
                            flContainerBar.addView(ap_barChart);
                            //把framlayout加到list集合中
                            if (listFramLayout != null && listFramLayout.size() != 0)
                                listFramLayout.clear();
                            listFramLayout.add(flContainerLine);
//                            listFramLayout.add(flContainerBar);    ****现在不要viewpager滑动 先只是把折线图放进去，以后再放开添加柱状图****
                            containerPagerAdapter = new ContainerPagerAdapter(listFramLayout);
                            vpContainer.setAdapter(containerPagerAdapter);
                            containerPagerAdapter.notifyDataSetChanged();
                            vpContainer.setCurrentItem(vpContainerCurPos);
                            //update view
                            setBarOrLineGoneOrVis();
                            // 添加折点的点击事件
                            wc_linearpicture
                                    .setValueOnClickListener(new OnClickListener() {

                                        @Override
                                        public void onClick(View v) {

                                            // TODO Auto-generated method stub
                                            if (wc_linearpicture
                                                    .getValueIndex() != -1
                                                    && values_wc != null) {
                                                tvNum.setVisibility(View.VISIBLE);
                                                tvNum.setText(""
                                                        + values_wc[wc_linearpicture
                                                        .getValueIndex()]);
                                                Log.e("INDEX",
                                                        wc_linearpicture
                                                                .getValueIndex()
                                                                + "");
                                            }
                                        }
                                    });
                            ap_linearpicture
                                    .setValueOnClickListener(new OnClickListener() {

                                        @Override
                                        public void onClick(View v) {

                                            // TODO Auto-generated method stub
                                            if (ap_linearpicture
                                                    .getValueIndex() != -1
                                                    && values_ap != null) {
                                                tvNum.setText(""
                                                        + values_ap[ap_linearpicture
                                                        .getValueIndex()]);
                                                tvNum.setVisibility(View.VISIBLE);
                                                Log.e("INDEX", tvNum.getText()
                                                        .toString());
                                            }
                                        }
                                    });
                        }
                    }

                    @Override
                    public void notNetwork() {
                        // TODO Auto-generated method stub
                        dimssDialog();
                    }

                    @Override
                    public void fail(String exceptionMessage) {
                        // TODO Auto-generated method stub

                    }
                });
    }

    /**
     * 给柱状图设置属性值
     *
     * @param barChart
     * @param mDataList
     * @param YInterval
     */
    private void setBarChart(BarChart barChart, ArrayList<IBarData> mDataList, int YInterval) {
        barChart.setDataList(mDataList);
        barChart.setXAxisUnit("月");
        barChart.setYAxisUnit("公里");
        //设置纵向的数据间隔
        barChart.setYInterval(YInterval);
        //设置横向的数据间隔
        barChart.setXInterval(1);
        //x y轴的颜色
        barChart.setAxisColor(0xff145BAD);
        barChart.isAnimated = false;
        barChart.setBarWidth(25);
    }

    /**
     * 给柱状图赋值
     *
     * @param mDataList
     */
    private void initBarData(ArrayList<IBarData> mDataList, List<Float> datas) {

        ArrayList<PointF> mPointArrayList = new ArrayList<PointF>();
        BarData mBarData = new BarData();
        for (int i = 0; i < datas.size(); i++) {
            mPointArrayList.add(new PointF(i + 1, datas.get(i)));
        }
        mBarData.setValue(mPointArrayList);
        mBarData.setColor(0xEEB9F57F);
        mBarData.setPaintWidth(pxTodp(10));
        mBarData.setTextSize(pxTodp(8));
        mDataList.add(mBarData);
    }

    protected float pxTodp(float value) {
        DisplayMetrics metrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay()
                .getMetrics(metrics);
        float valueDP = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                value, metrics);
        return valueDP;
    }

    /**
     * update view
     */
    private void setBarOrLineGoneOrVis() {

        if (tv1_bt1_bottom.getVisibility() == View.VISIBLE) {
            ap_linearpicture.setVisibility(View.VISIBLE);
            wc_linearpicture.setVisibility(View.GONE);
            wc_barChart.setVisibility(View.GONE);
            ap_barChart.setVisibility(View.VISIBLE);
        } else if (tv2_bt2_bottom.getVisibility() == View.VISIBLE) {
            wc_linearpicture.setVisibility(View.VISIBLE);
            ap_linearpicture.setVisibility(View.GONE);
            wc_barChart.setVisibility(View.VISIBLE);
            ap_barChart.setVisibility(View.GONE);
        }
    }

    // 获取详细参数
    private void getContentValuesprogress(String reportID) {

        AnsynHttpRequest.request(this, null,
                ContantValues.GETVALUES + reportID, CHTTP.GET,
                new ObserverCallBack() {

                    @Override
                    public void success(String successMessage) {
                        // TODO Auto-generated method stub
                        list_progress.clear();
                        XiangQingBean bean = FastJsonUtils.getPerson(
                                successMessage, XiangQingBean.class);
                        if (bean.getResult() != null) {
                            list_progress.addAll(bean.getResult());
                            getProgressData(list_wz, list_lx, Sblx);
                        }

                    }

                    @Override
                    public void notNetwork() {
                        // TODO Auto-generated method stub
                        dimssDialog();
                    }

                    @Override
                    public void fail(String exceptionMessage) {
                        // TODO Auto-generated method stub

                    }
                });

    }

    private void getContentValuescircle(String reportID) {

        AnsynHttpRequest.request(this, null,
                ContantValues.GETVALUES + reportID, CHTTP.GET,
                new ObserverCallBack() {

                    @Override
                    public void success(String successMessage) {
                        // TODO Auto-generated method stub

                        list_circle.clear();
                        XiangQingBean bean = FastJsonUtils.getPerson(
                                successMessage, XiangQingBean.class);
                        if (bean.getResult() != null) {
                            list_circle.addAll(bean.getResult());
                            getCircleData(list_wz, list_lx, Sblx);
                        }

                    }

                    @Override
                    public void notNetwork() {
                        // TODO Auto-generated method stub
                        dimssDialog();
                    }

                    @Override
                    public void fail(String exceptionMessage) {
                        // TODO Auto-generated method stub

                    }
                });

    }

    private void getContentValuesline(String reportID) {

        AnsynHttpRequest.request(this, null,
                ContantValues.GETVALUES + reportID, CHTTP.GET,
                new ObserverCallBack() {

                    @Override
                    public void success(String successMessage) {
                        // TODO Auto-generated method stub

                        list_line.clear();
                        XiangQingBean bean = FastJsonUtils.getPerson(
                                successMessage, XiangQingBean.class);
                        if (bean.getResult() != null) {
                            list_line.addAll(bean.getResult());
                            getLinData(list_wz, list_lx, Sblx);
                        }

                    }

                    @Override
                    public void notNetwork() {
                        // TODO Auto-generated method stub
                        dimssDialog();
                    }

                    @Override
                    public void fail(String exceptionMessage) {
                        // TODO Auto-generated method stub

                    }
                });

    }

    /**
     * 从productprogressfragment中接口回调回来的值
     */
    @Override
    public void getData(List<String> list_wz, List<String> list_lx,
                        String rb_text) {
        // TODO Auto-generated method stub
        if (this.list_wz != null) {
            this.list_wz.clear();
        }
        if (this.list_lx != null) {
            this.list_lx.clear();
        }
        this.list_wz.addAll((ArrayList<String>) list_wz);
        this.list_lx.addAll((ArrayList<String>) list_lx);
        this.Sblx = rb_text;
        if (Sblx.equals("GG")) {
            tv_progress2_top.setText("全年实施计划进度（公里）");
            tv_progress3_top.setText("全年累计完成进度（公里）");
            circleBar1.setDanWei("公里");
            circleBar2.setDanWei("公里");
        } else if (Sblx.equals("DC")) {
            tv_progress2_top.setText("全年实施计划进度（组）");
            tv_progress3_top.setText("全年累计完成进度（组）");
            circleBar1.setDanWei("组");
            circleBar2.setDanWei("组");
        } else if (Sblx.equals("HF")) {
            tv_progress2_top.setText("全年实施计划进度（个）");
            tv_progress3_top.setText("全年累计完成进度（个）");
            circleBar1.setDanWei("个");
            circleBar2.setDanWei("个");
        }
        initData();
        ll_progress_shaixuan.setVisibility(View.GONE);
        ll_shaixuan_wai.setVisibility(View.GONE);
    }

    @Override
    public void cancle() {
        // TODO Auto-generated method stub
        ll_progress_shaixuan.setAnimation(AnimationUtils.makeOutAnimation(this,
                true));// 推出
        ll_progress_shaixuan.setVisibility(View.GONE);
        ll_shaixuan_wai.setVisibility(View.GONE);
    }

    /**
     * 因为有fragment的问题 导致筛选之后点击物理返回键无法直接关闭，重写方法强制关闭activity
     */
    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        ProduceProgressActivity.this.finish();
    }

    private void shareListener() {

        String listwz = "";
        String listlx = "";
        if (month >= 10) {
            date = year + "" + month;
        } else if (month < 10) {
            date = year + "0" + month;
        }

        if (list_wz.size() == 0 && list_lx.size() == 0) {
            apiUrlTemp = "share" + "|" + "share" + "|" + userID + "|" + date
                    + "|" + Sblx + "|" + tv_produce_date.getText().toString();
        }
        if (list_wz.size() != 0 && list_lx.size() == 0) {
            for (int i = 0; i < list_wz.size(); i++) {
                if (i != list_wz.size() - 1) {
                    listwz += list_wz.get(i) + ",";
                } else {
                    listwz += list_wz.get(i);
                }
            }
            apiUrlTemp = "share" + "|" + listwz + "|" + userID + "|" + date
                    + "|" + Sblx + "|" + tv_produce_date.getText().toString();
        }
        if (list_wz.size() == 0 && list_lx.size() != 0) {
            for (int i = 0; i < list_lx.size(); i++) {
                if (i != list_lx.size() - 1) {
                    listlx += list_lx.get(i) + ",";
                } else {
                    listlx += list_lx.get(i);
                }
            }
            apiUrlTemp = listlx + "|" + "share" + "|" + userID + "|" + date
                    + "|" + Sblx + "|" + tv_produce_date.getText().toString();
        }
        if (list_wz.size() != 0 && list_lx.size() != 0) {
            for (int i = 0; i < list_wz.size(); i++) {
                if (i != list_wz.size() - 1) {
                    listwz += list_wz.get(i) + ",";
                } else {
                    listwz += list_wz.get(i);
                }
            }
            for (int i = 0; i < list_lx.size(); i++) {
                if (i != list_lx.size() - 1) {
                    listlx += list_lx.get(i) + ",";
                } else {
                    listlx += list_lx.get(i);
                }
            }
            apiUrlTemp = listlx + "|" + listwz + "|" + userID + "|" + date
                    + "|" + Sblx + "|" + tv_produce_date.getText().toString();
        }

        ShareUnit
                .ShareListener(
                        this,
                        "探伤生产进度动态",
                        "http://114.112.89.94:8081/ZTCloudAPI/MetroImage/sharereport.png",
                        apiUrlTemp, "XX");
    }

    private void setViewGoneWhileShare() {
        iv_date_add.setVisibility(View.GONE);
        iv_date_cut.setVisibility(View.GONE);
        iv_i.setVisibility(View.GONE);
        tv_shaixuan.setVisibility(View.GONE);
    }

}
