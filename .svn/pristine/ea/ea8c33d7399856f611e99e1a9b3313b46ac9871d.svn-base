package com.htmitech.ztcustom.zt.chinarailway;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.adapter.RecyclingPagerAdapter;
import com.htmitech.ztcustom.zt.adapter.TanShangCompleteSumTableAdapter;
import com.htmitech.ztcustom.zt.app.ZTCustomInit;
import com.htmitech.ztcustom.zt.base.BaseFragmentActivity;
import com.htmitech.ztcustom.zt.constant.CompleteSumCardContant;
import com.htmitech.ztcustom.zt.constant.ContantValues;
import com.htmitech.ztcustom.zt.constant.GetOrgListParmasBean;
import com.htmitech.ztcustom.zt.constant.GetOrgListResultBean;
import com.htmitech.ztcustom.zt.constant.TanShangCompleteResultBean;
import com.htmitech.ztcustom.zt.constant.TanShangCompleteSumRequestBean;
import com.htmitech.ztcustom.zt.constant.TanShangCompleteSumRootBean;
import com.htmitech.ztcustom.zt.domain.permissions.LineList;
import com.htmitech.ztcustom.zt.domain.permissions.Order;
import com.htmitech.ztcustom.zt.fragment.CompleteSumDrawLayoutFragment;
import com.htmitech.ztcustom.zt.interfaces.CallBackCompleteFragmentDataListener;
import com.htmitech.ztcustom.zt.interfaces.ObserverCallBack;
import com.htmitech.ztcustom.zt.pop.FunctionPopupWindow;
import com.htmitech.ztcustom.zt.thread.AnsynHttpRequest;
import com.htmitech.ztcustom.zt.unit.CHTTP;
import com.htmitech.ztcustom.zt.unit.ShareUnit;
import com.htmitech.ztcustom.zt.util.FastJsonUtils;
import com.htmitech.ztcustom.zt.view.kapian.ScalePageTransformer;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 探伤完成情况汇总Activity
 *
 * @author heyang
 */
public class TanShangCompleteSumActivity extends BaseFragmentActivity implements
        OnClickListener, CallBackCompleteFragmentDataListener, OnTouchListener {

    View mHead;
    private ListView lv_table;
    private ViewPager mViewPager;
    private TextView tv_date;
    private ImageView iv_dateadd;
    private ImageView iv_datecut;
    private ImageButton ib_back;
    private ImageView iv_i;
    private TextView tv_drawlayout;// 上下拉的按钮（不用了）
    private LinearLayout ll_drawlayout_up;// 上下拉的linearlayout
    private LinearLayout ll_completesum_drawlayout;// 上下拉 装fragment的
    private RelativeLayout rl_completesum_drawlayout;
    Calendar c = Calendar.getInstance();
    Calendar calendar = Calendar.getInstance();
    int year = c.get(Calendar.YEAR);
    int month = c.get(Calendar.MONTH) + 1;
    int monthnow = month;
    int yearnow = year;
    // 获取组织列表的list集合
    private List<GetOrgListResultBean> orgList = new ArrayList<GetOrgListResultBean>();
    // 放请求到的linesumlist 下面表格里面的内容
    private List<TanShangCompleteResultBean> com_list = new ArrayList<TanShangCompleteResultBean>();
    // 放请求到的卡片的内容
    private List<CompleteSumCardContant> comcard_list = new ArrayList<CompleteSumCardContant>();
    // 放所有的下面的表格中的数据
    private List<List<TanShangCompleteResultBean>> allcom_list = new ArrayList<List<TanShangCompleteResultBean>>();
    private String userid;
    private String beginmonth = year + "" + month;
    private String endmonth = year + "" + month;
    private String orgid;
    private String orgtype;
    private String parentId;
    private String getOrg_orgtype = "";
    private int WhichSelect = 2;
    private FunctionPopupWindow functionPopWindow;
    private GestureDetector mgGestureDetector;
    private LinearLayout zt_layout_sum_item;
    private TextView tv_complete_xianming;
    private String apiUrlTemp;

    private boolean isShare;
    private String shareParmas;

    private int vpCurrentItem = 1;
    private int vpPosition = 0;
    private int startMonth = 0;
    private String stat_day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tan_shang_complete_sum);
        Intent intent = getIntent();
        isShare = intent.getBooleanExtra("flag_share", false);
        shareParmas = intent.getStringExtra("share");

//        userid =  ZTCustomInit.get().getmCache().getmListDetails().AccountId;
        userid =  ZTCustomInit.get().getmCache().getCisAccountDetail().cisAccountId;
        orgid =   ZTCustomInit.get().getmCache().getCisAccountDetail().cisDeptId;
        orgtype =  ZTCustomInit.get().getmCache().getCisAccountDetail().cisDeptType;
        parentId = orgid;
        if(null != ZTCustomInit.get().getmCache().getmGetStatParam()){
            stat_day =  ZTCustomInit.get().getmCache().getmGetStatParam().stat_day;
        }
        if (stat_day != null && !stat_day.equals("") && Integer.parseInt(stat_day) > 14 && c.get(Calendar.DAY_OF_MONTH) > Integer.parseInt(stat_day)) {
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
        if (month < 10) {
            endmonth = year + "0" + month;
            beginmonth = year + "0" + month;
        } else if (month >= 10) {
            endmonth = year + "" + month;
            beginmonth = year + "" + month;
        }
        initView();
        initData();
        initContorl();
    }

    private void initContorl() {
        // TODO Auto-generated method stub
        mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(final int arg0) {
                // TODO Auto-generated method stub
                vpCurrentItem = arg0;
                vpPosition = arg0;
                if (arg0 >= 1 && arg0 == (comcard_list.size())) {
                    String orgid_now = orgList.get(arg0 - 1).getOrgid();
                    getCompleteData(userid, beginmonth, endmonth, orgid_now,
                            TanShangCompleteSumActivity.this.orgtype);
                } else {
                    // for (int i = 0; i < allcom_list.size(); i++) {
                    // Log.e("LINENAME", allcom_list.get(i).get(0)
                    // .getLine_name());
                    // }
                    // com_list.addAll(allcom_list.get(arg0));
                    new Thread(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            array = new ArrayList<LineList>();
                            String lineName = "";
                            LineList mLineList = null;
                            ArrayList<Order> order = new ArrayList<Order>();
                            if (arg0 >= allcom_list.size()) {
                                return;
                            }
                            if (allcom_list.get(arg0) != null
                                    && allcom_list.get(arg0).size() > 0) {
                                for (TanShangCompleteResultBean mTanShangCompleteResultBean : allcom_list
                                        .get(arg0)) {
                                    if (lineName.equals(""
                                            + mTanShangCompleteResultBean
                                            .getLine_name())) {
                                        Order mOrder = new Order();
                                        mOrder.setCsgh_sum(mTanShangCompleteResultBean
                                                .getCsgh_sum());
                                        mOrder.setGg_sum(mTanShangCompleteResultBean
                                                .getGg_sum());
                                        mOrder.setGmgc_sum(mTanShangCompleteResultBean
                                                .getGmgc_sum());
                                        mOrder.setHjgc_sum(mTanShangCompleteResultBean
                                                .getHjgc_sum());
                                        mOrder.setJghf_sum(mTanShangCompleteResultBean
                                                .getJghf_sum());
                                        mOrder.setKdxc_sum(mTanShangCompleteResultBean
                                                .getKdxc_sum());
                                        mOrder.setLine_id(mTanShangCompleteResultBean
                                                .getLine_id());
                                        mOrder.setLine_typename(mTanShangCompleteResultBean
                                                .getLine_typename());
                                        mOrder.setLrh_sum(mTanShangCompleteResultBean
                                                .getLrh_sum());
                                        mOrder.setQyh_sum(mTanShangCompleteResultBean
                                                .getQyh_sum());
                                        mOrder.setXsgh_sum(mTanShangCompleteResultBean
                                                .getXsgh_sum());
                                        mLineList.order.add(mOrder);
                                    } else {
                                        lineName = mTanShangCompleteResultBean
                                                .getLine_name();
                                        mLineList = new LineList();
                                        mLineList.line_name = lineName;
                                        order = new ArrayList<Order>();
                                        Order mOrder = new Order();
                                        mOrder.setCsgh_sum(mTanShangCompleteResultBean
                                                .getCsgh_sum());
                                        mOrder.setGg_sum(mTanShangCompleteResultBean
                                                .getGg_sum());
                                        mOrder.setGmgc_sum(mTanShangCompleteResultBean
                                                .getGmgc_sum());
                                        mOrder.setHjgc_sum(mTanShangCompleteResultBean
                                                .getHjgc_sum());
                                        mOrder.setJghf_sum(mTanShangCompleteResultBean
                                                .getJghf_sum());
                                        mOrder.setKdxc_sum(mTanShangCompleteResultBean
                                                .getKdxc_sum());
                                        mOrder.setLine_id(mTanShangCompleteResultBean
                                                .getLine_id());
                                        mOrder.setLine_typename(mTanShangCompleteResultBean
                                                .getLine_typename());
                                        mOrder.setLrh_sum(mTanShangCompleteResultBean
                                                .getLrh_sum());
                                        mOrder.setQyh_sum(mTanShangCompleteResultBean
                                                .getQyh_sum());
                                        mOrder.setXsgh_sum(mTanShangCompleteResultBean
                                                .getXsgh_sum());
                                        mLineList.order = order;
                                        mLineList.order.add(mOrder);
                                        array.add(mLineList);
                                    }
                                }
                            } else {
                                if (array != null && array.size() != 0)
                                    array.clear();
                            }
                            TanShangCompleteSumActivity.this
                                    .runOnUiThread(new Runnable() {

                                        @Override
                                        public void run() {
                                            // TODO Auto-generated method stub
                                            mTanShangCompleteSumTableAdapter
                                                    .setData(array);
                                        }
                                    });
                        }
                    }).start();

                }

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
        mgGestureDetector = new GestureDetector(this, onGestureListener);
    }

    private void initView() {
        tv_date = (TextView) findViewById(R.id.tv_tanshangcompletesum_title_data);
        iv_dateadd = (ImageView) findViewById(R.id.iv_tanshangcompletesum_add);
        iv_datecut = (ImageView) findViewById(R.id.iv_tanshangcompletesum_cut);
        ib_back = (ImageButton) findViewById(R.id.ib_tanshangcompletesum_back);
        tv_complete_xianming = (TextView) findViewById(R.id.tv_complete_xianming);
        iv_i = (ImageView) findViewById(R.id.iv_completesum_i);
        zt_layout_sum_item = (LinearLayout) findViewById(R.id.zt_layout_sum_item);
        tv_drawlayout = (TextView) findViewById(R.id.tv_completesum_drawlayout);
        ll_drawlayout_up = (LinearLayout) findViewById(R.id.ll_completesum_drawlayout_up);
        ll_completesum_drawlayout = (LinearLayout) findViewById(R.id.ll_completesum_drawlayout);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        lv_table = (ListView) findViewById(R.id.lv_complete_table);
        lv_table.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());
        rl_completesum_drawlayout = (RelativeLayout) findViewById(R.id.rl_completesum_drawlayout);
        mHead = LayoutInflater.from(this).inflate(
                R.layout.zt_z_tanshang_huizong, null);
        mHead.setFocusable(true);
        mHead.setClickable(true);
        mHead.setBackgroundColor(Color.parseColor("#0080FC"));
        tv_complete_xianming.setBackgroundColor(Color.parseColor("#0080FC"));
        mHead.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());
        zt_layout_sum_item.addView(mHead);
    }

    TanShangCompleteSumTableAdapter mTanShangCompleteSumTableAdapter;

    private void initData() {
        mViewPager.setPageTransformer(true, new ScalePageTransformer());
        functionPopWindow = new FunctionPopupWindow(this, this);
        if (month >= 10) {
            tv_date.setText(year + "年" + month + "月");
        } else if (month < 10) {
            tv_date.setText(year + "年0" + month + "月");
        }
        iv_dateadd.setOnClickListener(this);
        iv_datecut.setOnClickListener(this);
        ib_back.setOnClickListener(this);
        iv_i.setOnClickListener(this);
        mTanShangCompleteSumTableAdapter = new TanShangCompleteSumTableAdapter(
                array, TanShangCompleteSumActivity.this, mHead);
        lv_table.setAdapter(mTanShangCompleteSumTableAdapter);
        // tv_drawlayout.setOnClickListener(this);
        // tv_drawlayout.setOnTouchListener(this);
        ll_drawlayout_up.setOnTouchListener(this);
        ll_drawlayout_up.setOnClickListener(this);
        getOrgId(parentId, getOrg_orgtype);
    }

    /**
     * @author heyang viewpager的适配器
     */
    public class TubatuAdapter extends RecyclingPagerAdapter {
        private DecimalFormat df = new DecimalFormat("#0.000");
        private DecimalFormat dfint = new DecimalFormat("#0");

        private final List<CompleteSumCardContant> mList;
        private final Context mContext;

        public TubatuAdapter(Context context) {
            mList = new ArrayList<CompleteSumCardContant>();
            mContext = context;
        }

        public void addAll(List<CompleteSumCardContant> list) {
            if (mList.size() != 0) {
                mList.clear();
            }
            mList.addAll(list);
//			Log.e("MLISTSIZE", mList.size() + "+++++++");
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
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            // 设置上面的卡片名称
            viewHolder.tv_card_first_top.setText("钢轨探伤完成");
            viewHolder.tv_card_second_top.setText("道岔探伤完成");
            viewHolder.tv_card_three_top.setText("焊缝探伤完成");
            viewHolder.tv_card_head_meter.setVisibility(View.GONE);
            if (position < mList.size()) {
                viewHolder.tv_card_head_address.setText(mList.get(position)
                        .getOrgname());
                viewHolder.tv_card_first_bottom.setText(df.format(mList.get(
                        position).getGgts_sum())
                        + "公里");
                viewHolder.tv_card_second_bottom.setText(dfint.format(mList
                        .get(position).getDcts_sum()) + "组");
                viewHolder.tv_card_three_bottom.setText(dfint.format(mList.get(
                        position).getHfts_sum())
                        + "个");
            } else {
                viewHolder.tv_card_head_address.setText("");
                viewHolder.tv_card_first_bottom.setText("");
                viewHolder.tv_card_second_bottom.setText("");
                viewHolder.tv_card_three_bottom.setText("");
            }

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
            // 因为是每次都要重新创建适配器，每次list集合大小都不同，
            //当mList与orgList相同时，mList后面只能在装一个数据
            //当mList大于orgList时返回的就是Mlist的大小
            if (mList.size() <= orgList.size()) {
                return mList.size() + 1;
            } else {
                return mList.size();
            }

        }
    }

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        if(arg0.getId() ==R.id.ib_tanshangcompletesum_back ){
            this.finish();
        }else if(arg0.getId() ==R.id.iv_tanshangcompletesum_add){
            if (WhichSelect == 2) {// 年月都有的
                c.add(Calendar.MONTH, 1);
                int monthl = c.get(Calendar.MONTH) + 1;
                int yearl = c.get(Calendar.YEAR);
                if (monthl <= monthnow || yearl < yearnow) {
                    year = c.get(Calendar.YEAR);
                    month = c.get(Calendar.MONTH) + 1;
                    if (month < 10) {
                        tv_date.setText(year + "年" + "0" + month + "月");
                        beginmonth = year + "0" + month;
                        endmonth = year + "0" + month;
                    } else {
                        tv_date.setText(year + "年" + month + "月");
                        beginmonth = year + "" + month;
                        endmonth = year + "" + month;
                    }
                    // 清空之前所有数据
                    if (com_list != null) {
                        com_list.clear();
                    }
                    if (comcard_list != null) {
                        comcard_list.clear();
                    }
                    if (orgList != null) {
                        orgList.clear();
                    }
                    if (allcom_list != null) {
                        allcom_list.clear();
                    }
                    getOrgId(parentId, getOrg_orgtype);
                } else {
                    c = Calendar.getInstance();
                }
            } else if (WhichSelect == 0) {// 按照年的
                c.add(Calendar.YEAR, 1);
                int yearl = c.get(Calendar.YEAR);
                if (yearl <= yearnow) {
                    year = c.get(Calendar.YEAR);
                    if (year < yearnow) {
                        beginmonth = year + "0" + 1;
                        endmonth = year + "" + 12;
                    } else if (year == yearnow) {
                        beginmonth = year + "0" + 1;
                        if (monthnow < 10) {
                            endmonth = year + "0" + monthnow;
                        } else if (month >= 10) {
                            endmonth = year + "" + monthnow;
                        }
                    }
                    tv_date.setText(year + "年");
                    // 清空之前所有数据
                    if (com_list != null) {
                        com_list.clear();
                    }
                    if (comcard_list != null) {
                        comcard_list.clear();
                    }
                    if (orgList != null) {
                        orgList.clear();
                    }
                    if (allcom_list != null) {
                        allcom_list.clear();
                    }

                    getOrgId(parentId, getOrg_orgtype);
                } else {
                    c = Calendar.getInstance();
                }
            } else if (WhichSelect == 1) {// 按照季度的
                if (Integer.valueOf(endmonth) < Integer.valueOf(year + "12")) {// 到最后一个季度不许在加
                    // if (!endmonth.equals("12")) {
                    endmonth = (Integer.valueOf(endmonth) + 3) + "";
                    beginmonth = (Integer.valueOf(beginmonth) + 3) + "";
                    // }
                    if (endmonth.equals(year + "03")) {
                        tv_date.setText(year + "年一季度");
                    }
                    if (endmonth.equals(year + "06")) {
                        tv_date.setText(year + "年二季度");
                    }
                    if (endmonth.equals(year + "09")) {
                        tv_date.setText(year + "年三季度");
                    }
                    if (endmonth.equals(year + "12")) {
                        tv_date.setText(year + "年四季度");
                    }

                    // 清空之前所有数据
                    if (com_list != null) {
                        com_list.clear();
                    }
                    if (comcard_list != null) {
                        comcard_list.clear();
                    }
                    if (orgList != null) {
                        orgList.clear();
                    }
                    if (allcom_list != null) {
                        allcom_list.clear();
                    }
                    getOrgId(parentId, getOrg_orgtype);
                }
            }
        }else if(arg0.getId() ==R.id.iv_tanshangcompletesum_cut){
            if (WhichSelect == 2) {// 按照年月都有的
                c.add(Calendar.MONTH, -1);
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH) + 1;
                if (month < 10) {
                    tv_date.setText(year + "年" + "0" + month + "月");
                    beginmonth = year + "0" + month;
                    endmonth = year + "0" + month;
                } else {
                    tv_date.setText(year + "年" + month + "月");
                    beginmonth = year + "" + month;
                    endmonth = year + "" + month;
                }
                // 清空之前所有数据
                if (com_list != null) {
                    com_list.clear();
                }
                if (comcard_list != null) {
                    comcard_list.clear();
                }
                if (orgList != null) {
                    orgList.clear();
                }
                if (allcom_list != null) {
                    allcom_list.clear();
                }
                getOrgId(parentId, getOrg_orgtype);
            } else if (WhichSelect == 0) {// 按照年的
                c.add(Calendar.YEAR, -1);
                year = c.get(Calendar.YEAR);
                beginmonth = year + "01";
                endmonth = year + "12";
                tv_date.setText(year + "年");

                // 清空之前所有数据
                if (com_list != null) {
                    com_list.clear();
                }
                if (comcard_list != null) {
                    comcard_list.clear();
                }
                if (orgList != null) {
                    orgList.clear();
                }
                if (allcom_list != null) {
                    allcom_list.clear();
                }

                getOrgId(parentId, getOrg_orgtype);
            } else if (WhichSelect == 1) {// 按照季度的
                if (Integer.valueOf(beginmonth) > Integer.valueOf(year + "01")) {
                    // if (!beginmonth.equals("1")) {
                    beginmonth = (Integer.valueOf(beginmonth) - 3) + "";
                    endmonth = (Integer.valueOf(endmonth) - 3) + "";
//					Log.e("BEGIN", beginmonth);
                    // }
                    if (endmonth.equals(year + "03")) {
                        tv_date.setText(year + "年一季度");
                    }
                    if (endmonth.equals(year + "06")) {
                        tv_date.setText(year + "年二季度");
                    }
                    if (endmonth.equals(year + "09")) {
                        tv_date.setText(year + "年三季度");
                    }
                    if (endmonth.equals(year + "12")) {
                        tv_date.setText(year + "年四季度");
                    }

                    // 清空之前所有数据
                    if (com_list != null) {
                        com_list.clear();
                    }
                    if (comcard_list != null) {
                        comcard_list.clear();
                    }
                    if (orgList != null) {
                        orgList.clear();
                    }
                    if (allcom_list != null) {
                        allcom_list.clear();
                    }
                    getOrgId(parentId, getOrg_orgtype);
                }
            }
        }else if(arg0.getId() ==R.id.iv_completesum_i){
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
        }

    }

    ArrayList<LineList> array;

    /**
     * 网络请求，获取数据
     *
     * @param userid
     * @param beginmonth
     * @param endmonth
     * @param orgid
     * @param orgtype
     */
    private void getCompleteData(String userid, String beginmonth,
                                 String endmonth, String orgid, String orgtype) {
        showProgressDialog(this);
        TanShangCompleteSumRequestBean bean = new TanShangCompleteSumRequestBean();
        bean.setBeginmonth(beginmonth);
        bean.setUserid(userid);
        bean.setEndmonth(endmonth);
        bean.setOrgtype(orgtype);
        bean.setOrgid(orgid);

        AnsynHttpRequest.request(this, bean,
                ContantValues.TANSHANGQINGKUANGHUIZONG, CHTTP.POST,
                new ObserverCallBack() {

                    @Override
                    public void success(final String successMessage) {
                        // TODO Auto-generated method stub
//						Log.e("SuccessMessage", successMessage);
                        new Thread(new Runnable() {

                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                TanShangCompleteSumRootBean result = FastJsonUtils
                                        .getPerson(
                                                successMessage,
                                                TanShangCompleteSumRootBean.class);
                                // 向上面卡片的list里面添加数据
                                array = new ArrayList<LineList>();
                                if (result != null) {
                                    CompleteSumCardContant card_contant = new CompleteSumCardContant();
                                    card_contant.setOrgname(result.getOrgname());
                                    card_contant.setDcts_sum(result
                                            .getDcts_sum());
                                    card_contant.setGgts_sum(result
                                            .getGgts_sum());
                                    card_contant.setHfts_sum(result
                                            .getHfts_sum());
                                    comcard_list.add(card_contant);
                                    if (result.getLinesumlist() != null
                                            && result.getLinesumlist().size() != 0) {
                                        if (com_list != null) {
                                            com_list.clear();
                                        }
                                        com_list.addAll(result.getLinesumlist());
//										Log.e(":COMLIST", com_list.get(0)
//												.getLine_id() + "");
                                        // 向下面的表格中添加数据
                                        allcom_list.add(result.getLinesumlist());
                                        // // 向表格里加入数据

                                        String lineName = "";
                                        LineList mLineList = null;
                                        ArrayList<Order> order = new ArrayList<Order>();
                                        for (TanShangCompleteResultBean mTanShangCompleteResultBean : com_list) {
                                            if (lineName.equals(""
                                                    + mTanShangCompleteResultBean
                                                    .getLine_name())) {
                                                Order mOrder = new Order();
                                                mOrder.setCsgh_sum(mTanShangCompleteResultBean
                                                        .getCsgh_sum());
                                                mOrder.setGg_sum(mTanShangCompleteResultBean
                                                        .getGg_sum());
                                                mOrder.setGmgc_sum(mTanShangCompleteResultBean
                                                        .getGmgc_sum());
                                                mOrder.setHjgc_sum(mTanShangCompleteResultBean
                                                        .getHjgc_sum());
                                                mOrder.setJghf_sum(mTanShangCompleteResultBean
                                                        .getJghf_sum());
                                                mOrder.setKdxc_sum(mTanShangCompleteResultBean
                                                        .getKdxc_sum());
                                                mOrder.setLine_id(mTanShangCompleteResultBean
                                                        .getLine_id());
                                                mOrder.setLine_typename(mTanShangCompleteResultBean
                                                        .getLine_typename());
                                                mOrder.setLrh_sum(mTanShangCompleteResultBean
                                                        .getLrh_sum());
                                                mOrder.setQyh_sum(mTanShangCompleteResultBean
                                                        .getQyh_sum());
                                                mOrder.setXsgh_sum(mTanShangCompleteResultBean
                                                        .getXsgh_sum());
                                                mLineList.order.add(mOrder);
                                            } else {
                                                lineName = mTanShangCompleteResultBean
                                                        .getLine_name();
                                                mLineList = new LineList();
                                                mLineList.line_name = lineName;
                                                order = new ArrayList<Order>();
                                                Order mOrder = new Order();
                                                mOrder.setCsgh_sum(mTanShangCompleteResultBean
                                                        .getCsgh_sum());
                                                mOrder.setGg_sum(mTanShangCompleteResultBean
                                                        .getGg_sum());
                                                mOrder.setGmgc_sum(mTanShangCompleteResultBean
                                                        .getGmgc_sum());
                                                mOrder.setHjgc_sum(mTanShangCompleteResultBean
                                                        .getHjgc_sum());
                                                mOrder.setJghf_sum(mTanShangCompleteResultBean
                                                        .getJghf_sum());
                                                mOrder.setKdxc_sum(mTanShangCompleteResultBean
                                                        .getKdxc_sum());
                                                mOrder.setLine_id(mTanShangCompleteResultBean
                                                        .getLine_id());
                                                mOrder.setLine_typename(mTanShangCompleteResultBean
                                                        .getLine_typename());
                                                mOrder.setLrh_sum(mTanShangCompleteResultBean
                                                        .getLrh_sum());
                                                mOrder.setQyh_sum(mTanShangCompleteResultBean
                                                        .getQyh_sum());
                                                mOrder.setXsgh_sum(mTanShangCompleteResultBean
                                                        .getXsgh_sum());
                                                mLineList.order = order;
                                                mLineList.order.add(mOrder);
                                                array.add(mLineList);
                                            }
                                        }

                                    } else {
                                        allcom_list
                                                .add(new ArrayList<TanShangCompleteResultBean>());
                                    }
                                } else {
                                    comcard_list.add(new CompleteSumCardContant());
                                    allcom_list.add(new ArrayList<TanShangCompleteResultBean>());
                                }

                                TanShangCompleteSumActivity.this
                                        .runOnUiThread(new Runnable() {

                                            @Override
                                            public void run() {
                                                // TODO Auto-generated
                                                // method stub
                                                // lv_table.invalidate() ;
                                                // mTanShangCompleteSumTableAdapter
                                                // = new
                                                // TanShangCompleteSumTableAdapter(
                                                // array,
                                                // TanShangCompleteSumActivity.this,
                                                // mHead);
                                                // lv_table.setAdapter(mTanShangCompleteSumTableAdapter);
                                                // // 每次创建一个adapter
                                                mTanShangCompleteSumTableAdapter
                                                        .setData(array);
                                                TubatuAdapter adapter = new TubatuAdapter(
                                                        TanShangCompleteSumActivity.this);
                                                mViewPager
                                                        .setOffscreenPageLimit(2);
                                                adapter.addAll(comcard_list);
                                                mViewPager.setAdapter(adapter);
                                                // if (comcard_list.size() <=
                                                // orgList.size()){
                                                mViewPager
                                                        .setCurrentItem(comcard_list
                                                                .size() - 1);
                                                // }
                                                dimssDialog();
                                            }
                                        });
                            }
                        }).start();

                    }

                    @Override
                    public void notNetwork() {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void fail(String exceptionMessage) {
                        // TODO Auto-generated method stub

                    }
                });

    }

    /**
     * 获取组织机构的网络请求
     *
     * @param parentId
     * @param orgtype
     */
    private void getOrgId(String parentId, String orgtype) {
        GetOrgListParmasBean bean = new GetOrgListParmasBean();

        if (!isShare) {
            bean.setParent_id(parentId);
            bean.setOrgtype(orgtype);
        } else if (isShare) {
//			shareParmas = shareParmas.substring(2);
            String[] ss = shareParmas.split("\\|");
            bean.setParent_id(ss[3]);
            bean.setOrgtype(ss[4]);
        }
        AnsynHttpRequest.request(this, bean, ContantValues.GETZUZHIJIGOU,
                CHTTP.POST, new ObserverCallBack() {

                    @Override
                    public void success(String successMessage) {
                        // TODO Auto-generated method stub
//                        Log.e("ORG", successMessage);
                        orgList = FastJsonUtils.getPersonList(successMessage,
                                GetOrgListResultBean.class);
                        // // 循环获取所有数据
                        // for (int i = 0; i < orgList.size(); i++) {
                        // getCompleteData(userid, beginmonth, endmonth,
                        // orgList.get(i).getOrgid(),
                        // TanShangCompleteSumActivity.this.orgtype);
                        // }

                        if (!isShare) {
                            getCompleteData(userid, beginmonth, endmonth,
                                    orgid,
                                    TanShangCompleteSumActivity.this.orgtype);
                        } else if (isShare) {
                            String[] ss = shareParmas.split("\\|");
                            userid = ss[0];
                            beginmonth = ss[1];
                            endmonth = ss[2];
                            orgid = ss[3];
                            TanShangCompleteSumActivity.this.orgtype = ss[4];
                            tv_date.setText(ss[5]);
                            getCompleteData(userid, beginmonth, endmonth,
                                    orgid,
                                    TanShangCompleteSumActivity.this.orgtype);
                            shareViewGone();
                        }

                    }

                    @Override
                    public void notNetwork() {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void fail(String exceptionMessage) {
                        // TODO Auto-generated method stub
                    }
                });

    }

    private void shareViewGone() {
        iv_dateadd.setVisibility(View.GONE);
        iv_datecut.setVisibility(View.GONE);
        iv_i.setVisibility(View.GONE);
        ll_drawlayout_up.setVisibility(View.GONE);
        ll_drawlayout_up.setFocusable(false);
//		mViewPager.setFocusable(false);
//		mViewPager.setEnabled(false);
        mViewPager.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                // TODO Auto-generated method stub
                return true;
            }
        });
    }

    class ListViewAndHeadViewTouchLinstener implements OnTouchListener {

        @Override
        public boolean onTouch(View arg0, MotionEvent arg1) {
            // 当在列头 和 listView控件上touch时，将这个touch的事件分发给 ScrollView
            HorizontalScrollView headSrcrollView = (HorizontalScrollView) mHead
                    .findViewById(R.id.complete_horizontalScrollView1);
            headSrcrollView.onTouchEvent(arg1);
            return false;
        }
    }

    // 接口回调返回的值
    @Override
    public void getFragmentData(int Year, int startMonth, int endMonth,
                                int whichSelect) {
        // TODO Auto-generated method stub
        // Log.e("TIME", Year + " -" + startMonth + " -" + endMonth);
        iv_dateadd.setClickable(true);
        iv_datecut.setClickable(true);
        iv_i.setVisibility(View.VISIBLE);
        Animation mGoneAction = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f);
        mGoneAction.setDuration(1000);
        rl_completesum_drawlayout.setVisibility(View.VISIBLE);
        rl_completesum_drawlayout.setAnimation(mGoneAction);
        mGoneAction.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation arg0) {
                // TODO Auto-generated method stub
                ll_completesum_drawlayout.setVisibility(View.GONE);
            }
        });
        this.year = Year;
        if (startMonth < 10) {
            this.beginmonth = year + "0" + startMonth;
        } else if (startMonth >= 10) {
            this.beginmonth = year + "" + startMonth;
        }
        if (endMonth < 10) {
            this.endmonth = year + "0" + endMonth;
        } else if (endMonth >= 10) {
            this.endmonth = year + "" + endMonth;
        }
        WhichSelect = whichSelect;
        if (WhichSelect == 0) {
            tv_date.setText(Year + "年");
        } else if (WhichSelect == 1) {
            if (endMonth == 3) {
                tv_date.setText(Year + "年一季度");
            }
            if (endMonth == 6) {
                tv_date.setText(Year + "年二季度");
            }
            if (endMonth == 9) {
                tv_date.setText(Year + "年三季度");
            }
            if (endMonth == 12) {
                tv_date.setText(Year + "年四季度");
            }

        } else if (WhichSelect == 2) {
            if (endMonth < 10) {
                tv_date.setText(Year + "年0" + endMonth + "月");
            } else if (endMonth >= 10) {
                tv_date.setText(Year + "年" + endMonth + "月");
            }
        }
        if (com_list != null) {
            com_list.clear();
        }
        if (comcard_list != null) {
            comcard_list.clear();
        }
        if (orgList != null) {
            orgList.clear();
        }
        if (allcom_list != null) {
            allcom_list.clear();
        }
        getOrgId(parentId, getOrg_orgtype);

    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        TanShangCompleteSumActivity.this.finish();
    }

    @Override
    public boolean onTouch(View arg0, MotionEvent arg1) {
        // TODO Auto-generated method stub
        return mgGestureDetector.onTouchEvent(arg1);
    }

    // 手势触摸上拉fragmengt
    private GestureDetector.OnGestureListener onGestureListener = new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {
            float x = e2.getX() - e1.getX();
            float y = e2.getY() - e1.getY();
            if (y > 50) {
                iv_dateadd.setClickable(false);
                iv_datecut.setClickable(false);
                iv_i.setVisibility(View.GONE);// 下拉的时候隐藏i
                CompleteSumDrawLayoutFragment fragment = new CompleteSumDrawLayoutFragment();
                fragment.startMonth = startMonth;
                fragment.setYear(year);
                fragment.setEndmonth(month);
                Bundle bundle = new Bundle();
                bundle.putInt("whichSelected", WhichSelect);
                fragment.setArguments(bundle);
                FragmentTransaction transaction = getSupportFragmentManager()
                        .beginTransaction();
                transaction.replace(R.id.ll_completesum_drawlayout, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
                // rl_completesum_drawlayout.setAnimation(AnimationUtils.loadAnimation(this,
                // R.anim.complete_drawlayout_animation_in));// 进入
                Animation mShowAction = new TranslateAnimation(
                        Animation.RELATIVE_TO_SELF, 0.0f,
                        Animation.RELATIVE_TO_SELF, 0.0f,
                        Animation.RELATIVE_TO_SELF, 0.0f,
                        Animation.RELATIVE_TO_SELF, 1.0f);
                mShowAction.setDuration(1000);
                ll_completesum_drawlayout.setVisibility(View.VISIBLE);
                rl_completesum_drawlayout.setAnimation(mShowAction);
                mShowAction.setAnimationListener(new AnimationListener() {

                    @Override
                    public void onAnimationStart(Animation arg0) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onAnimationRepeat(Animation arg0) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void onAnimationEnd(Animation arg0) {
                        // TODO Auto-generated method stub
                        rl_completesum_drawlayout.setVisibility(View.GONE);
                    }
                });
            }
            return false;
        }

    };

    private void shareListener() {
        if (vpCurrentItem == 1) {
            apiUrlTemp = userid + "|" + beginmonth + "|" + endmonth + "|"
                    + orgid + "|" + orgtype + "|"
                    + tv_date.getText().toString();
        } else {
            apiUrlTemp = userid + "|" + beginmonth + "|" + endmonth + "|"
                    + orgList.get(vpCurrentItem - 1).getOrgid() + "|" + orgtype
                    + "|" + tv_date.getText().toString();
        }
        ShareUnit.ShareListener(this, "探伤完成情况汇总", "http://114.112.89.94:8081/ZTCloudAPI/MetroImage/sharereport.png", apiUrlTemp, "ZZ");
    }
}
