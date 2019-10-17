package com.htmitech.ztcustom.zt.chinarailway;

/**
 * 探伤工作日报主界面
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.adapter.DanWeiDayReportTableAdapter;
import com.htmitech.ztcustom.zt.adapter.RecyclingPagerAdapter;
import com.htmitech.ztcustom.zt.app.ZTCustomInit;
import com.htmitech.ztcustom.zt.base.BaseFragmentActivity;
import com.htmitech.ztcustom.zt.constant.ContantValues;
import com.htmitech.ztcustom.zt.constant.DanWeiDayReportParmeters;
import com.htmitech.ztcustom.zt.constant.DanWeiDayReportResultBean;
import com.htmitech.ztcustom.zt.domain.Dictitemlist;
import com.htmitech.ztcustom.zt.domain.DicttypeResult;
import com.htmitech.ztcustom.zt.fragment.DamageSummaryFragment;
import com.htmitech.ztcustom.zt.fragment.DayReportShaiXuanFragment;
import com.htmitech.ztcustom.zt.interfaces.CallBackDataListener;
import com.htmitech.ztcustom.zt.interfaces.DayReportCallBackListener;
import com.htmitech.ztcustom.zt.interfaces.ObserverCallBack;
import com.htmitech.ztcustom.zt.pop.FunctionPopupWindow;
import com.htmitech.ztcustom.zt.thread.AnsynHttpRequest;
import com.htmitech.ztcustom.zt.unit.CHTTP;
import com.htmitech.ztcustom.zt.unit.ShareUnit;
import com.htmitech.ztcustom.zt.util.FastJsonUtils;
import com.htmitech.ztcustom.zt.view.kapian.ScalePageTransformer;

import net.simonvt.datepicker.DatePicker;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import htmitech.com.componentlibrary.unit.Utils;

public class TanShangDayReportActivity extends BaseFragmentActivity implements
        OnClickListener, DayReportCallBackListener, CallBackDataListener {

    private ViewPager mViewPager;
    private TubatuAdapter mPagerAdapter;
    private TubatuAdapter mPagerAdapter_0;// 点击第一个listview的条目创建一个新的adapter
    private ListView lv_DayReport;
    Calendar c = Calendar.getInstance();
    Calendar calendar = Calendar.getInstance();
    int year = c.get(Calendar.YEAR);
    int month = c.get(Calendar.MONTH) + 1;
    int day = c.get(Calendar.DAY_OF_MONTH);
    int daynow = day;
    private TextView tv_date;
    private TextView tv_title;
    private ImageView iv_dateadd;
    private ImageView iv_datecut;
    private ImageButton ib_back;
    private ImageView iv_i;
    private TextView ib_search;
    private LinearLayout ll_search;
    private LinearLayout ll_shaixuan_wai;
    private FunctionPopupWindow functionPopWindow;
    // 单位日报解析数据总体list
    private List<DanWeiDayReportResultBean> danWeiList = new ArrayList<DanWeiDayReportResultBean>();
    private List<DanWeiDayReportResultBean> danWeiListTZ = new ArrayList<DanWeiDayReportResultBean>();
    private List<DanWeiDayReportResultBean> danWeiListTLJ = new ArrayList<DanWeiDayReportResultBean>();
    private List<DanWeiDayReportResultBean> danWeiListGWD = new ArrayList<DanWeiDayReportResultBean>();
    private List<DanWeiDayReportResultBean> danWeiListCJ = new ArrayList<DanWeiDayReportResultBean>();
    private List<DanWeiDayReportResultBean> danWeiListGQ = new ArrayList<DanWeiDayReportResultBean>();
    private List<DanWeiDayReportResultBean> CardList_cutadd = new ArrayList<DanWeiDayReportResultBean>();
    private TextView tv_1;
    private TextView tv_2;
    private TextView tv_3;
    private TextView tv_4;
    // 做标记
    boolean flag = true;
    // 临时存放
    final List<DanWeiDayReportResultBean> listlinshi = new ArrayList<DanWeiDayReportResultBean>();
    // 卡片的list
    List<DanWeiDayReportResultBean> listCard = new ArrayList<DanWeiDayReportResultBean>();
    // 表格listview的list
    List<DanWeiDayReportResultBean> listTable = new ArrayList<DanWeiDayReportResultBean>();
    // 存放单位探伤日报的list
    private List<List<DanWeiDayReportResultBean>> listQueue = new ArrayList<List<DanWeiDayReportResultBean>>();
    // 存放单位的名字
    private ArrayList<String> listname = new ArrayList<String>();
    private String tzname;
    private String tljname;
    private String gwdname;
    private String cjname;
    int arg0now = -1;
    int positionBefore = -1;
    int positionnow = -1;
    // 标记第flagFirst+1个界面
    int flagFirst = 0;
    // 模拟orgID
    private String orgID;
    public static String Sblx = "GG";
    private ArrayList<String> list_wz = new ArrayList<String>();
    private ArrayList<String> list_lx = new ArrayList<String>();
    private String Jhh = "";
    private String userID;
    private String orgType;
    private static DecimalFormat df;
    private boolean flag_frist_fragment = true;// 判断是否第一次传值过去

    // 分享相关变量
    public String apiUrlTmp = null; // 用于拼接分享参数
    // public String apiUrl = null;
    // public String docTitle = null;
    // public String iconId = null;

    private boolean isShare = false;// 判断是否是分享的界面
    private String shareParams;// 分享后的参数
    private String orgidBefore;// 上一个界面的orgid；
    private boolean isCutOrAdd = true;
    private int addcut_position;
    private LinearLayout ll_Popup;
    private Animation upAnimation, downAnimation;
    private LinearLayout layout_iv_up, layout_iv_down;
    private GestureDetector mGestureDetectorDown, mGestureDetectorUp;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tan_shang_day_report);
        Intent intent = getIntent();
        isShare = intent.getBooleanExtra("flag_share", false);
        shareParams = intent.getStringExtra("share");
//        userID =  ZTCustomInit.get().getmCache().getmListDetails().AccountId;
        userID = ZTCustomInit.get().getmCache().getCisAccountDetail().cisAccountId;
        Log.e("YJH", "onCreate->cisAccountId"+userID);
        orgID =  ZTCustomInit.get().getmCache().getCisAccountDetail().cisDeptId;
        orgType =  ZTCustomInit.get().getmCache().getCisAccountDetail().cisDeptType;
        orgidBefore = orgID;
        Log.e("YJH", "orgidBefore: "+orgID + "------orgidBefore----" + orgidBefore );
        initView();
        changeViewState();
        initData();
        mViewPager.setPageTransformer(true, new ScalePageTransformer());
        // mViewPager.setAdapter(mPagerAdapter);
        initControl();
    }

    private void initControl() {

        // viewpager的滑动事件
        // TODO Auto-generated method stub
        mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                // TODO Auto-generated method stub
                // 修改listview里的内容
                arg0now = arg0;
                addcut_position = arg0;
                if (!isCutOrAdd) {
                    String orgid = listlinshi.get(arg0).getOrgid();
                    orgID = orgid;
                    getDanWeiData(orgID, Jhh, list_wz, Sblx, list_lx);
                }
                isCutOrAdd = false;
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

        // listview的点击事件
        lv_DayReport.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                // Intent intent = new Intent(TanShangDayReportActivity.this,
                // CheJianDayReportActivity.class);
                addcut_position = position;
                Intent intent = new Intent(TanShangDayReportActivity.this,
                        BanZuDayReportActivity.class);
                // 防止点击同一个position的listview后viewpager出现问题，先给临时设置到其他position
                if (position == positionBefore) {
                    if (position != listlinshi.size() - 1) {
                        positionnow = position + 1;
                        intent.putExtra("position", position);
                        mViewPager.setCurrentItem(position + 1);
                    } else {
                        positionnow = position - 1;
                        intent.putExtra("position", position);
                        mViewPager.setCurrentItem(position - 1);
                    }
                } else {
                    intent.putExtra("position", positionBefore);
                }
                // 下挖到哪一层，现在是设置到铁路局，到铁路局就就直接跳转到详情界面。
                if (danWeiList.get(0).getOrgtype().equals("CJ")) {
                    if (listname != null && listname.size() != 0) {
                        listname.clear();
                    }
                    cjname = danWeiList.get(position + 1).getOrgname();
                    listname.add(tzname);
                    listname.add(tljname);
                    listname.add(gwdname);
                    listname.add(cjname);
                    intent.putExtra("orgid", danWeiList.get(position + 1)
                            .getOrgid());
                    intent.putExtra("year", year);
                    intent.putExtra("month", month);
                    intent.putExtra("day", day);
                    intent.putExtra("Sblx", Sblx);
                    intent.putStringArrayListExtra("listname", listname);
                    intent.putStringArrayListExtra("list_wz",
                            (ArrayList<String>) list_wz);
                    intent.putStringArrayListExtra("list_lx",
                            (ArrayList<String>) list_lx);
                    startActivityForResult(intent, 200);
                } else if (!danWeiList.get(0).getOrgtype().equals("CJ")) {
                    flagFirst++;
                    TubatuAdapter adapter = new TubatuAdapter(
                            TanShangDayReportActivity.this);
                    if (listCard != null && listCard.size() != 0) {
                        listCard.clear();
                    }
                    // 添加到list队列内，用于点击上面返回按钮后可以从队列里面拿数据
                    listlinshi.clear();
                    listlinshi.addAll(danWeiList);
                    if (danWeiList.get(0).getOrgtype().equals("TZ")) {
                        danWeiListTZ.addAll(danWeiList);
                        listQueue.add(danWeiListTZ);
                    } else if (danWeiList.get(0).getOrgtype().equals("TLJ")) {
                        danWeiListTLJ.addAll(danWeiList);
                        listQueue.add(danWeiListTLJ);
                    } else if (danWeiList.get(0).getOrgtype().equals("GWD")) {
                        danWeiListGWD.addAll(danWeiList);
                        listQueue.add(danWeiListGWD);
                    } else if (danWeiList.get(0).getOrgtype().equals("CJ")) {
                        danWeiListCJ.addAll(danWeiList);
                        listQueue.add(danWeiListCJ);
                    }
                    orgidBefore = listlinshi.get(0).getOrgid();
                    listlinshi.remove(0);
                    listCard.addAll(listlinshi);
                    mViewPager.setOffscreenPageLimit(listlinshi.size());
                    adapter.addAll(listCard);
                    mViewPager.setAdapter(adapter);
                    if (position == 0) {
                        // 当点击第一个条目网络请求
                        String orgid = listlinshi.get(position).getOrgid();
                        orgID = orgid;
                        getDanWeiData(orgID, Jhh, list_wz, Sblx, list_lx);
                    } else {
                        // 当点击其他条目网络请求
                        mViewPager.setCurrentItem(position);
                    }

                }
            }
            // positionBefore = position;
        });
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        FrameLayout fl = (FrameLayout) findViewById(R.id.fl_dayreport);
        lv_DayReport = (ListView) fl.findViewById(R.id.lv_dayreport_table);
        tv_1 = (TextView) findViewById(R.id.tv_dayreport_1);
        tv_2 = (TextView) findViewById(R.id.tv_dayreport_2);
        tv_3 = (TextView) findViewById(R.id.tv_dayreport_3);
        tv_4 = (TextView) findViewById(R.id.tv_dayreport_4);
        ll_Popup = (LinearLayout) findViewById(R.id.ll_popupLayout);
        layout_iv_up = (LinearLayout) findViewById(R.id.layout_iv_up);
        layout_iv_down = (LinearLayout) findViewById(R.id.layout_iv_down);
        tv_date = (TextView) findViewById(R.id.tv_dayreport_title_data);
        tv_date.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_dayreport_title_name);
        iv_dateadd = (ImageView) findViewById(R.id.iv_dayreport_add);
        iv_datecut = (ImageView) findViewById(R.id.iv_dayreport_cut);
        iv_dateadd.setOnClickListener(this);
        iv_datecut.setOnClickListener(this);
        ib_back = (ImageButton) findViewById(R.id.ib_dayreport_back);
        ib_back.setOnClickListener(this);
        iv_i = (ImageView) findViewById(R.id.iv_dayreport_i);
        iv_i.setOnClickListener(this);
        ib_search = (TextView) findViewById(R.id.ib_dayreport_shaixuan);
        ib_search.setOnClickListener(this);
        ll_search = (LinearLayout) findViewById(R.id.ll_dayreport_shaixuan);
        ll_shaixuan_wai = (LinearLayout) findViewById(R.id.ll_dayreport_shaixuan_wai);
    }

    private void initData() {
        layout_iv_up.setOnClickListener(this);
        layout_iv_down.setOnClickListener(this);
        upAnimation = AnimationUtils.loadAnimation(this,
                R.anim.zt_score_business_query_enter);
        DicttypeResult mDicttypeResult_XB =  ZTCustomInit.get().getmCache().getmDicttypeResult().get("XB1");
        for (Dictitemlist mDictitemlist : mDicttypeResult_XB.getDictitemlist()) {
            list_wz.add(mDictitemlist.getCode());
        }
        upAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub
                layout_iv_up.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub
                layout_iv_down.setVisibility(View.VISIBLE);
            }
        });

        downAnimation = AnimationUtils.loadAnimation(this,
                R.anim.zt_score_business_query_exit);

        downAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub
                layout_iv_down.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub
                layout_iv_up.setVisibility(View.VISIBLE);
            }
        });

        mGestureDetectorUp = new GestureDetector(this,
                new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onFling(MotionEvent e1, MotionEvent e2,
                                           float velocityX, float velocityY) {
                        // if (Math.abs(e1.getRawX() - e2.getRawX()) > 250) {
                        // // System.out.println("水平方向移动距离过大");
                        // return true;
                        // }
                        if (Math.abs(velocityY) < 100) {
                            // System.out.println("手指移动的太慢了");
                            return true;
                        }

                        // 手势向下 down
                        if ((e2.getRawY() - e1.getRawY()) > 100) {
                            ll_Popup.setVisibility(View.VISIBLE); // 显示布局
                            ll_Popup.startAnimation(upAnimation);
                            return true;
                        }
                        // 手势向上 up
                        if ((e1.getRawY() - e2.getRawY()) > 100) {
                            return true;
                        }
                        return super.onFling(e1, e2, velocityX, velocityY);
                    }
                });

        mGestureDetectorDown = new GestureDetector(this,
                new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onFling(MotionEvent e1, MotionEvent e2,
                                           float velocityX, float velocityY) {
                        // if (Math.abs(e1.getRawX() - e2.getRawX()) > 250) {
                        // // System.out.println("水平方向移动距离过大");
                        // return true;
                        // }
                        if (Math.abs(velocityY) < 100) {
                            // System.out.println("手指移动的太慢了");
                            return true;
                        }

                        // 手势向下 down
                        if ((e2.getRawY() - e1.getRawY()) > 100) {
                            return true;
                        }
                        // 手势向上 up
                        if ((e1.getRawY() - e2.getRawY()) > 100) {
                            layoutDown();
                            return true;
                        }
                        return super.onFling(e1, e2, velocityX, velocityY);
                    }
                });
        layout_iv_down.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View arg0, MotionEvent event) {
                // TODO Auto-generated method stub
                mGestureDetectorDown.onTouchEvent(event);
                return false;
            }
        });

        layout_iv_up.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View arg0, MotionEvent event) {
                // TODO Auto-generated method stub
                mGestureDetectorUp.onTouchEvent(event);
                return false;
            }
        });

        functionPopWindow = new FunctionPopupWindow(this, this);
        // mPagerAdapter = new TubatuAdapter(this);
        // mPagerAdapter_0 = new TubatuAdapter(this);
        if (day >= 10) {
            tv_date.setText(year + "年" + month + "月" + day + "日");
        } else if (day < 10) {
            tv_date.setText(year + "年" + month + "月0" + day + "日");
        }
        isCutOrAdd = false;
        showProgressDialog(this);
        getDanWeiData(orgID, Jhh, list_wz, Sblx, list_lx);
        ll_Popup.getBackground().setAlpha(220);
    }

    /**
     * @author heyang viewpager的适配器
     */
    public static class TubatuAdapter extends RecyclingPagerAdapter {

        private final List<DanWeiDayReportResultBean> mList;
        private final Context mContext;

        public TubatuAdapter(Context context) {
            mList = new ArrayList<DanWeiDayReportResultBean>();
            mContext = context;
        }

        public void addAll(List<DanWeiDayReportResultBean> list) {
            if (mList.size() != 0) {
                mList.clear();
            }
            mList.addAll(list);
            notifyDataSetChanged();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup container) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(
                        R.layout.card_item, null, false);
                viewHolder.tv_card_first_bottom = (TextView) convertView
                        .findViewById(R.id.tv_card_first_bottom);
                viewHolder.tv_card_head_address = (TextView) convertView
                        .findViewById(R.id.tv_card_head_address);
                viewHolder.tv_card_head_meter = (TextView) convertView
                        .findViewById(R.id.tv_card_head_meter);
                viewHolder.tv_card_first_top = (TextView) convertView
                        .findViewById(R.id.tv_card_first_top);
                viewHolder.tv_card_second_top = (TextView) convertView
                        .findViewById(R.id.tv_card_second_top);
                viewHolder.tv_card_second_bottom = (TextView) convertView
                        .findViewById(R.id.tv_card_second_bottom);
                viewHolder.tv_card_three_top = (TextView) convertView
                        .findViewById(R.id.tv_card_three_top);
                viewHolder.tv_card_three_bottom = (TextView) convertView
                        .findViewById(R.id.tv_card_three_bottom);
                convertView.setTag(R.id.tag_viewhoder, viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag(R.id.tag_viewhoder);
            }
            if (Sblx.equals("GG")) {
                viewHolder.tv_card_head_meter.setText("单位：公里");
                df = new DecimalFormat("#0.000");
            } else if (Sblx.equals("DC")) {
                df = new DecimalFormat("#0");
                viewHolder.tv_card_head_meter.setText("单位：组");
            } else if (Sblx.equals("HF")) {
                df = new DecimalFormat("#0");
                viewHolder.tv_card_head_meter.setText("单位：个");
            }
            viewHolder.tv_card_head_address.setText(mList.get(position)
                    .getOrgname());
            viewHolder.tv_card_first_top.setText("年初计划数");
            viewHolder.tv_card_first_bottom.setText(df.format(mList.get(
                    position).getTs_jhs()));
            viewHolder.tv_card_second_top.setText("实施计划数");
            viewHolder.tv_card_second_bottom.setText(df.format(mList.get(
                    position).getTs_aps()));
            viewHolder.tv_card_three_top.setText("实际完成数");
            viewHolder.tv_card_three_bottom.setText(df.format(mList.get(
                    position).getTs_wcs()));
            return convertView;
        }

        class ViewHolder {
            TextView tv_card_head_address;
            TextView tv_card_head_meter;
            TextView tv_card_first_top;
            TextView tv_card_first_bottom;
            TextView tv_card_second_top;
            TextView tv_card_second_bottom;
            TextView tv_card_three_top;
            TextView tv_card_three_bottom;
        }

        @Override
        public int getCount() {
            return mList.size();
        }
    }

    /**
     * 获取单位日报的数据
     */
    private void getDanWeiData(String orgID, String Jhh, List<String> wzlx,
                               String Sblx, List<String> Sblxmx) {
        showProgressDialog(this);
        DanWeiDayReportParmeters entity = new DanWeiDayReportParmeters();
        if (!isShare) {
            if (Jhh != "") {
                entity.setJhh(Jhh);
            } else {
                entity.setJhh("");
            }
            entity.setUserid(userID);
            entity.setWorkday(year + "-" + month + "-" + day);
            entity.setOrgid(orgID);
            entity.setOrgtype(orgType);
            if (wzlx != null) {
                if (wzlx.size() != 0) {
                    entity.setWzlx(wzlx);
                } else if (wzlx.size() == 0) {
                    wzlx.add("");
                    entity.setWzlx(wzlx);
                }
            }
            entity.setSblx(Sblx);
            if (Sblxmx != null) {
                if (Sblxmx.size() != 0) {
                    entity.setSblxmx(Sblxmx);
                } else if (Sblxmx.size() == 0) {
                    Sblxmx.add("");
                    entity.setSblxmx(Sblxmx);
                }
            }
        } else if (isShare) {
            Log.e("ShareParmas", shareParams);
            wzlx.clear();
            Sblxmx.clear();
            String[] ss = shareParams.split("\\|");
            entity.setUserid(ss[0]);
            entity.setWorkday(ss[1]);
            entity.setOrgid(ss[2]);
            entity.setOrgtype(ss[3]);
            if (ss[4].equals("share")) {
                wzlx.add("");
                entity.setWzlx(wzlx);
            } else {
                String[] wzlx_ss = ss[4].split(",");
                for (int i = 0; i < wzlx_ss.length; i++) {
                    wzlx.add(wzlx_ss[i]);
                }
                entity.setWzlx(wzlx);
            }
            entity.setSblx(ss[5]);
            if (ss[6].equalsIgnoreCase("share")) {
                Sblxmx.add("");
                entity.setSblxmx(Sblxmx);
            } else {
                String[] sblxmx_ss = ss[6].split(",");
                for (int i = 0; i < sblxmx_ss.length; i++) {
                    Sblxmx.add(sblxmx_ss[i]);
                }
                entity.setSblxmx(Sblxmx);
            }
            if (ss[7].equals("share")) {
                entity.setJhh("");
            } else {
                entity.setJhh(ss[7]);
            }
            tv_date.setText(ss[8]);
            setViewShareShow();
        }

        AnsynHttpRequest.request(this, entity, ContantValues.DANWEIDAYREPORT,
                CHTTP.POST, new ObserverCallBack() {

                    @Override
                    public void success(String requestValue) {
                        // TODO Auto-generated method stub
                        if (danWeiList != null && danWeiList.size() != 0) {
                            danWeiList.clear();
                        }
                        danWeiList = FastJsonUtils.getPersonList(requestValue,
                                DanWeiDayReportResultBean.class);
                        dimssDialog();
                        if (danWeiList != null && danWeiList.size() != 0) {
                            // 向卡片的list里面加入数据,如果是第一次进入页面请求数据flag=ture执行下面这段话
                            if (flag == true) {
                                if (listCard != null && listCard.size() != 0) {
                                    listCard.clear();
                                }
                                // 把第一次网络请求的结果放到队列里面，以后不再网络请求里面放入
                                // 这个只在最开始执行只能放在这里
                                // listQueue.add(danWeiList);
                                TubatuAdapter adapter = new TubatuAdapter(
                                        TanShangDayReportActivity.this);
                                listCard.add(danWeiList.get(0));
                                // 设置OffscreenPageLimit
                                mViewPager.setOffscreenPageLimit(listCard
                                        .size());
                                adapter.addAll(listCard);
                                mViewPager.setAdapter(adapter);
                                flag = false;
                            }
                            // 得到各个段的名字
                            DanWeiDayReportResultBean bean = danWeiList.get(0);
                            String orgtype_Name = bean.getOrgtype();
                            if (orgtype_Name.equals("TZ")) {
                                tzname = bean.getOrgname();
                            } else if (orgtype_Name.equals("TLJ")) {
                                tljname = bean.getOrgname();
                            } else if (orgtype_Name.equals("GWD")) {
                                gwdname = bean.getOrgname();
                            }

                            // 设置下面表格的内容
                            if (listTable != null && listTable.size() != 0) {
                                listTable.clear();
                            }
                            List<DanWeiDayReportResultBean> listlinshi = new ArrayList<DanWeiDayReportResultBean>();
                            listlinshi.addAll(danWeiList);
                            listlinshi.remove(0);
                            listTable.addAll(listlinshi);
                            lv_DayReport
                                    .setAdapter(new DanWeiDayReportTableAdapter(
                                            listTable,
                                            TanShangDayReportActivity.this, df));

                            // TubatuAdapter adapter = new TubatuAdapter(
                            // TanShangDayReportActivity.this);
                            // listCard.addAll(listlinshi);
                            // mViewPager.setOffscreenPageLimit(listlinshi.size());
                            // adapter.addAll(listCard);
                            // mViewPager.setAdapter(adapter);
                        }
                        dimssDialog();
                    }

                    @Override
                    public void notNetwork() {
                        // TODO Auto-generated method stub
                        dimssDialog();
                    }

                    @Override
                    public void fail(String exceptionMessage) {
                        // TODO Auto-generated method stub
                        dimssDialog();
                        Utils.toast(TanShangDayReportActivity.this, "请求错误:" + exceptionMessage, Toast.LENGTH_SHORT);
                    }

                });
    }

    // 分享下有一些按钮不显示
    private void setViewShareShow() {
        // TODO Auto-generated method stub
        iv_dateadd.setVisibility(View.GONE);
        iv_datecut.setVisibility(View.GONE);
        iv_i.setVisibility(View.GONE);
        ib_search.setVisibility(View.GONE);
        layout_iv_down.setVisibility(View.GONE);
        layout_iv_up.setVisibility(View.GONE);
        tv_date.setClickable(false);
//		lv_DayReport.setFocusable(false);
//		lv_DayReport.setClickable(false);
        lv_DayReport.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                // TODO Auto-generated method stub
                return true;
            }
        });
    }

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        if(arg0.getId() ==R.id.layout_iv_up ){
            layoutUp();
        }else if(arg0.getId() ==R.id.layout_iv_down){
            layoutDown();
        }else if(arg0.getId() ==R.id.tv_dayreport_title_data){
            if (ll_Popup.getVisibility() == View.GONE) {
                layoutUp();
            } else {
                layoutDown();
            }
        }else if(arg0.getId() ==R.id.iv_dayreport_add){
            c.set(year, month - 1, day);

            c.add(Calendar.DATE, 1);
            int dayl = c.get(Calendar.DAY_OF_MONTH);
//			if (dayl <= daynow) {
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);
            if (day >= 10) {
                tv_date.setText(year + "年" + month + "月" + day + "日");
            } else if (day < 10) {
                tv_date.setText(year + "年" + month + "月0" + day + "日");
            }
            // if (flagFirst == 0) {
            isCutOrAdd = true;
            mDatePicker.updateDate(year, month, day);
            getDanWeiData(orgID, Jhh, list_wz, Sblx, list_lx);
            getCardData(orgidBefore, Jhh, list_wz, Sblx, list_lx);
            // } else {
            // String orgidAdd = listlinshi.get(arg0now).getOrgid();
            // orgID = orgidAdd;
            // getDanWeiData(orgID, Jhh, list_wz, Sblx, list_lx);
            // }
//			} else {
//				c = Calendar.getInstance();
//			}
        }else if(arg0.getId() ==R.id.iv_dayreport_cut){
            c.set(year, month - 1, day);
            c.add(Calendar.DATE, -1);
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);
            if (day >= 10) {
                tv_date.setText(year + "年" + month + "月" + day + "日");
            } else if (day < 10) {
                tv_date.setText(year + "年" + month + "月0" + day + "日");
            }
            // if (flagFirst == 0) {
            isCutOrAdd = true;
            mDatePicker.updateDate(year, month, day);
            getDanWeiData(orgID, Jhh, list_wz, Sblx, list_lx);
            getCardData(orgidBefore, Jhh, list_wz, Sblx, list_lx);
            // } else {
            // String orgidCut = listlinshi.get(arg0now).getOrgid();
            // orgID = orgidCut;
            // getDanWeiData(orgID, Jhh, list_wz, Sblx, list_lx);
            // }
            // getDanWeiData("TZ");
        }else if(arg0.getId() ==R.id.ib_dayreport_back){
            flagFirst--;
            Log.e("Flag", flagFirst + "");
            TubatuAdapter adapter = new TubatuAdapter(
                    TanShangDayReportActivity.this);
            int size = listQueue.size();
            if (size > 1) {
                // 修改上面卡片的内容
                if (listCard != null && listCard.size() != 0) {
                    listCard.clear();
                }
                listlinshi.clear();
                listlinshi.addAll(listQueue.get(size - 2));
                orgidBefore = listlinshi.get(0).getOrgid();
                listlinshi.remove(0);
                listCard.addAll(listlinshi);
                mViewPager.setOffscreenPageLimit(listlinshi.size());
                adapter.addAll(listCard);
                mViewPager.setAdapter(adapter);
                addcut_position = 0;
                mViewPager.setCurrentItem(0);
                String orgid = listlinshi.get(0).getOrgid();
                orgID = orgid;
                getDanWeiData(orgID, Jhh, list_wz, Sblx, list_lx);
                listQueue.remove(size - 1);
            } else if (size == 1) {
                if (positionBefore <= 2) {
                    positionnow = 3;
                    mViewPager.setCurrentItem(3);
                }
                positionnow = -1;
                if (listCard != null && listCard.size() != 0) {
                    listCard.clear();
                }
                listlinshi.clear();
                listlinshi.addAll(listQueue.get(0));
                listCard.add(listlinshi.get(0));
                // 设置OffscreenPageLimit
                mViewPager.setOffscreenPageLimit(listCard.size());
                adapter.addAll(listCard);
                mViewPager.setAdapter(adapter);
                String orgid = listlinshi.get(0).getOrgid();
                orgID = orgid;
                orgidBefore = orgID;
                getDanWeiData(orgID, Jhh, list_wz, Sblx, list_lx);
                listQueue.remove(size - 1);
            } else {
                finish();
            }
        }else if(arg0.getId() ==R.id.iv_dayreport_i){
            if (!functionPopWindow.isShowing()) {
                functionPopWindow = new FunctionPopupWindow(this, this);
                functionPopWindow.setLinearVisibility();
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
        }else if(arg0.getId() ==R.id.iv_share){
            shareListener();
        }else if(arg0.getId() ==R.id.ib_dayreport_shaixuan){
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
            DayReportShaiXuanFragment fragment = new DayReportShaiXuanFragment();
            fragment.setArguments(bundle);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                    .beginTransaction();
            ll_search.setVisibility(View.VISIBLE);
            fragmentTransaction.replace(R.id.ll_dayreport_shaixuan, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            // ll_search.setAnimation(AnimationUtils.makeOutAnimation(this,
            // true));// 推出
            ll_search.setAnimation(AnimationUtils.makeInAnimation(this, false));// 进入
            flag_frist_fragment = false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        int position = data.getExtras().getInt("position");
        mViewPager.setCurrentItem(position);
    }

    // 筛选后返回的接口回调
    @Override
    public void getData(List<String> list_wz, List<String> list_lx, String jhh,
                        String Sblx) {
        // TODO Auto-generated method stub
        // Toast.makeText(this, jhh + "+" + Sblx, 0).show();
        if (this.list_lx != null) {
            this.list_lx.clear();
        }
        if (this.list_wz != null) {
            this.list_wz.clear();
        }
        this.list_wz.addAll(list_wz);
        this.list_lx.addAll(list_lx);
        this.Jhh = jhh;
        this.Sblx = Sblx;
        flag = true;
        if (listlinshi != null) {
            listlinshi.clear();
        }
        if (listCard != null) {
            listCard.clear();
        }
        if (listname != null) {
            listname.clear();
        }
        if (listTable != null) {
            listTable.clear();
        }
        if (listQueue != null) {
            listQueue.clear();
        }
        orgID =  ZTCustomInit.get().getmCache().getCisAccountDetail().cisDeptId;
        getDanWeiData(orgID, this.Jhh, this.list_wz, this.Sblx, this.list_lx);
        ll_search.setVisibility(View.GONE);
        ll_shaixuan_wai.setVisibility(View.GONE);
    }

    @Override
    public void cancle() {
        // TODO Auto-generated method stub
        ll_search.setAnimation(AnimationUtils.makeOutAnimation(this, true));// 推出
        ll_search.setVisibility(View.GONE);
        ll_shaixuan_wai.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        TanShangDayReportActivity.this.finish();
    }

    /**
     * 分享
     */

    private void shareListener() {
        // 弹选择窗
        // 设置分享参数
        String workday = year + "-" + month + "-" + day;
        String listwz = "";
        String listlx = "";
        if (Jhh == "") {
            Jhh = "share";
        }
        if (list_wz.size() == 0 && list_lx.size() == 0) {
            apiUrlTmp = userID + "|" + workday + "|" + orgID + "|" + orgType
                    + "|" + "share" + "|" + Sblx + "|" + "share" + "|" + Jhh
                    + "|" + tv_date.getText();
        }
        if (list_wz.size() != 0 && list_lx.size() == 0) {
            for (int i = 0; i < list_wz.size(); i++) {
                if (i != list_wz.size() - 1) {
                    listwz += list_wz.get(i) + ",";
                } else {
                    listwz += list_wz.get(i);
                }
            }
            apiUrlTmp = userID + "|" + workday + "|" + orgID + "|" + orgType
                    + "|" + listwz + "|" + Sblx + "|" + "share" + "|" + Jhh
                    + "|" + tv_date.getText();
        }
        if (list_wz.size() == 0 && list_lx.size() != 0) {
            for (int i = 0; i < list_lx.size(); i++) {
                if (i != list_lx.size() - 1) {
                    listlx += list_lx.get(i) + ",";
                } else {
                    listlx += list_lx.get(i);
                }
            }
            apiUrlTmp = userID + "|" + workday + "|" + orgID + "|" + orgType
                    + "|" + "share" + "|" + Sblx + "|" + listlx + "|" + Jhh
                    + "|" + tv_date.getText();
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
            apiUrlTmp = userID + "|" + workday + "|" + orgID + "|" + orgType
                    + "|" + listwz + "|" + Sblx + "|" + listlx + "|" + Jhh
                    + "|" + tv_date.getText();
        }

        ShareUnit
                .ShareListener(
                        this,
                        tv_title.getText().toString(),
                        "http://114.112.89.94:8081/ZTCloudAPI/MetroImage/sharereport.png",
                        apiUrlTmp, "CC");
    }

    // 当日期加减的时候获取上面卡片的数据
    private void getCardData(String orgID, String Jhh, List<String> wzlx,
                             String Sblx, List<String> Sblxmx) {
        showProgressDialog(this);
        DanWeiDayReportParmeters entity = new DanWeiDayReportParmeters();
        if (Jhh != "") {
            entity.setJhh(Jhh);
        } else {
            entity.setJhh("");
        }
        entity.setUserid(userID);
        entity.setWorkday(year + "-" + month + "-" + day);
        entity.setOrgid(orgID);
        entity.setOrgtype(orgType);
        if (wzlx != null) {
            if (wzlx.size() != 0) {
                entity.setWzlx(wzlx);
            } else if (wzlx.size() == 0) {
                wzlx.add("");
                entity.setWzlx(wzlx);
            }
        }
        entity.setSblx(Sblx);
        if (Sblxmx != null) {
            if (Sblxmx.size() != 0) {
                entity.setSblxmx(Sblxmx);
            } else if (Sblxmx.size() == 0) {
                Sblxmx.add("");
                entity.setSblxmx(Sblxmx);
            }
        }

        AnsynHttpRequest.request(this, entity, ContantValues.DANWEIDAYREPORT,
                CHTTP.POST, new ObserverCallBack() {

                    @Override
                    public void success(String requestValue) {
                        // TODO Auto-generated method stub
                        if (CardList_cutadd != null
                                && CardList_cutadd.size() != 0) {
                            CardList_cutadd.clear();
                        }
                        CardList_cutadd = FastJsonUtils.getPersonList(
                                requestValue, DanWeiDayReportResultBean.class);
                        dimssDialog();
                        if (CardList_cutadd != null
                                && CardList_cutadd.size() != 0) {
                            if (flagFirst != 0) {// 如果不是第一个几面就走这部分
                                if (listCard != null && listCard.size() != 0) {
                                    listCard.clear();
                                }
                                TubatuAdapter adapter = new TubatuAdapter(
                                        TanShangDayReportActivity.this);
                                CardList_cutadd.remove(0);
                                listCard.addAll(CardList_cutadd);
                                // 设置OffscreenPageLimit
                                mViewPager.setOffscreenPageLimit(listCard
                                        .size());
                                adapter.addAll(listCard);
                                mViewPager.setAdapter(adapter);
                                mViewPager.setCurrentItem(addcut_position);
                            } else if (flagFirst == 0) {// 如果是第一个界面 比如铁总的走这部分
                                if (listCard != null && listCard.size() != 0) {
                                    listCard.clear();
                                }
                                TubatuAdapter adapter = new TubatuAdapter(
                                        TanShangDayReportActivity.this);
                                listCard.add(CardList_cutadd.get(0));
                                // 设置OffscreenPageLimit
                                mViewPager.setOffscreenPageLimit(listCard
                                        .size());
                                adapter.addAll(listCard);
                                mViewPager.setAdapter(adapter);
                            }
                        }
                        dimssDialog();
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

    public void changeViewState() {
        FragmentManager manager = this.getSupportFragmentManager();
        // 2.根据FragmentManager对象的findFragmentById方法来获取指定的fragment
        DamageSummaryFragment fragment2 = (DamageSummaryFragment) manager.findFragmentById(R.id.fragment_summary);
        // 3.获取Fragment中的布局文件
        View v = fragment2.getView();
        fragment2.type = 2;
        LinearLayout layout_title = (LinearLayout) v.findViewById(R.id.layout_title);
        mDatePicker = (DatePicker) v.findViewById(R.id.datePicker);
        layout_title.setVisibility(View.GONE);
        mDatePicker.basisDay();
    }

    DatePicker mDatePicker;

    public void layoutUp() {
        ll_Popup.setVisibility(View.VISIBLE); // 显示布局
        ll_Popup.startAnimation(upAnimation);
    }

    public void layoutDown() {
        ll_Popup.setVisibility(View.GONE); // 取出布局
        ll_Popup.startAnimation(downAnimation); // 开始退出动画
        getDanWeiData(orgID, Jhh, list_wz, Sblx, list_lx);
        getCardData(orgidBefore, Jhh, list_wz, Sblx, list_lx);
    }

    @Override
    public void onClickChild(int year, int monthOfYear, int dayOfMonth,
                             Type mType) {
        // TODO Auto-generated method stub
        this.year = year;
        month = monthOfYear;
        day = dayOfMonth;
        if (day >= 10) {
            tv_date.setText(year + "年" + month + "月" + day + "日");
        } else if (day < 10) {
            tv_date.setText(year + "年" + month + "月0" + day + "日");
        }

    }

    @Override
    public void onClickChildOne(int year, int monthOfYear, int dayOfMonth) {
        // TODO Auto-generated method stub
        this.year = year;
        month = monthOfYear;
        day = dayOfMonth;
        if (day >= 10) {
            tv_date.setText(year + "年" + month + "月" + day + "日");
        } else if (day < 10) {
            tv_date.setText(year + "年" + month + "月0" + day + "日");
        }
    }
}
