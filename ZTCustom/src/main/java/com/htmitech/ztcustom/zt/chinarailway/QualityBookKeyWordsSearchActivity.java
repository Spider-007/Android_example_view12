package com.htmitech.ztcustom.zt.chinarailway;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.adapter.QualityBookSearchNewsReceivegoodsAdapter;
import com.htmitech.ztcustom.zt.adapter.QualityBookSearchNewsgetBuildersAdapter;
import com.htmitech.ztcustom.zt.adapter.QualityBookSearchNewsgetStationListAdapter;
import com.htmitech.ztcustom.zt.app.ZTCustomInit;
import com.htmitech.ztcustom.zt.base.BaseFragmentActivity;
import com.htmitech.ztcustom.zt.constant.ContantValues;
import com.htmitech.ztcustom.zt.constant.EventBusMessage;
import com.htmitech.ztcustom.zt.constant.PlannedRateListRequestBean;
import com.htmitech.ztcustom.zt.constant.QualityBookSearchNewsReceivegoodsBean;
import com.htmitech.ztcustom.zt.constant.QualityBookSearchNewsReceivegoodsRequest;
import com.htmitech.ztcustom.zt.constant.QualityBookSearchNewsgetBuilderBean;
import com.htmitech.ztcustom.zt.constant.QualityBookSearchNewsgetBuilderRequest;
import com.htmitech.ztcustom.zt.constant.QualityBookSearchNewsgetStationListBean;
import com.htmitech.ztcustom.zt.constant.QualityBookSearchNewsgetStationListRequest;
import com.htmitech.ztcustom.zt.interfaces.ObserverCallBack;
import com.htmitech.ztcustom.zt.thread.AnsynHttpRequest;
import com.htmitech.ztcustom.zt.unit.CHTTP;
import com.htmitech.ztcustom.zt.util.CharacterParser;
import com.htmitech.ztcustom.zt.util.DimenUtil;
import com.htmitech.ztcustom.zt.util.FastJsonUtils;
import com.htmitech.ztcustom.zt.view.zgtw.Sidebar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import static com.htmitech.ztcustom.zt.app.PlannedCashRateConfig.LISTREQUESTCODE;
import static com.htmitech.ztcustom.zt.app.PlannedCashRateConfig.LISTRESULTCODE;

/**
 * 质保书关键字搜索
 */
public class QualityBookKeyWordsSearchActivity extends BaseFragmentActivity implements View.OnClickListener {

    private ImageButton imgbtnBack;
    private Button btnSure;
    private EditText etSearch;
    private String userID = null;
    private List<QualityBookSearchNewsgetBuilderBean.ResultsBean> getBuilderList = new ArrayList<QualityBookSearchNewsgetBuilderBean.ResultsBean>();
    private List<QualityBookSearchNewsgetBuilderBean.ResultsBean> getBuilderListss = new ArrayList<QualityBookSearchNewsgetBuilderBean.ResultsBean>();
    private List<QualityBookSearchNewsReceivegoodsBean.ResultsBean> receiveGoodsList = new ArrayList<QualityBookSearchNewsReceivegoodsBean.ResultsBean>();
    private List<QualityBookSearchNewsReceivegoodsBean.ResultsBean> receiveGoodsListss = new ArrayList<QualityBookSearchNewsReceivegoodsBean.ResultsBean>();
    private List<QualityBookSearchNewsgetStationListBean.ResultsBean> getStationList = new ArrayList<QualityBookSearchNewsgetStationListBean.ResultsBean>();
    private List<QualityBookSearchNewsgetStationListBean.ResultsBean> getStationListss = new ArrayList<QualityBookSearchNewsgetStationListBean.ResultsBean>();

    private String year = "";
    private String month = "";
    private String jsdwid;
    private String jsdwname;
    private int BUILDERRESULTCODE = 11;
    private int RECEIVEGOODSESULTCODE = 22;
    private int STATIONRESULTCODE = 33;
    private Intent intent1;
    private int builder;
    private int receivegoods;
    private int station;
    private String shdwid;
    private String shdwname;
    private Intent intent2;
    private String dzid;
    private String dzname;
    private Intent intent3;
    private RecyclerView lvSearch;
    private ImageView mImgvDelete;
    private QualityBookSearchNewsgetBuildersAdapter getBuildersadapter;
    private LinearLayoutManager builderlinearLayoutManager;
    private LinearLayoutManager receivegoodslinearLayoutManager;
    private QualityBookSearchNewsReceivegoodsAdapter receivegoodsAdapter;
    private LinearLayoutManager stationlinearLayoutManager;
    private QualityBookSearchNewsgetStationListAdapter getStationListAdapter;
    HashMap<String, Integer> letters = new HashMap<String, Integer>();
    private SearchView sv_search;
    private RelativeLayout rv_serach;
    private ImageView iv_serach;
    private TextView tv_serach;
    private String name;
    private String id;
    private ArrayList<QualityBookSearchNewsgetBuilderBean.ResultsBean> getBuilderLists;
    private CharacterParser characterParser;
    private ArrayList<QualityBookSearchNewsReceivegoodsBean.ResultsBean> receiveGoodsLists;
    private ArrayList<QualityBookSearchNewsgetStationListBean.ResultsBean> getStationLists;
    private TextView mDialog;
    private boolean mShouldScroll;
    private int mToPosition;
    private Sidebar sidebar;
    private String jsdwids;
    private String shdwids;
    private String from;
    private TextView tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quality_book_key_words_search);
        EventBus.getDefault().register(QualityBookKeyWordsSearchActivity.this);
        initView();
        //    <--------------------Administrator -> 2019-8-16:16:37:ZTCustomInit.get().getmCache().getmListDetails().AccountId--------------------->
        userID = ZTCustomInit.get().getmCache().getCisAccountDetail().cisAccountId;
        //   Log.e("wsf", "onCreate: "+ userID);
        Intent intent = getIntent();
        builder = intent.getExtras().getInt("builder");
        String year1 = intent.getStringExtra("year");
        String month1 = intent.getStringExtra("month");
        from = intent.getStringExtra("from");
        year = year1;
        month = month1;
        receivegoods = intent.getExtras().getInt("receivegoods");
        jsdwids = intent.getStringExtra("jsdwid");
        station = intent.getExtras().getInt("station");
        shdwids = intent.getStringExtra("shdwid");
        getBuilderLists = new ArrayList<QualityBookSearchNewsgetBuilderBean.ResultsBean>();
        receiveGoodsLists = new ArrayList<QualityBookSearchNewsReceivegoodsBean.ResultsBean>();
        getStationLists = new ArrayList<QualityBookSearchNewsgetStationListBean.ResultsBean>();
        characterParser = new CharacterParser();
        if (builder == 1) {
            showProgressDialog(this);
            if (from != null && from.equals("plannedCashRate")) {
                // 从计划兑现跳过来的，懒寄生与之前的也挺好。
                if (intent.getStringExtra("userid") != null) {
                    getPlannedCashRateList(intent.getStringExtra("userid"));
                }
            } else {
                getBuildeData();
            }
            sidebar.setTextView(mDialog);

            sidebar.setOnTouchingLetterChangedListener(new Sidebar.OnTouchingLetterChangedListener() {
                @Override
                public void onTouchingLetterChanged(String s) {
                    // TODO Auto-generated method stub
                    int position = 0;
                    // 该字母首次出现的位置
                    if (getBuildersadapter != null) {
                        position = getBuildersadapter.getPositionForSection(s.charAt(0));
                    }
                    if (position != -1) {
                        smoothMoveToPosition(lvSearch, position);
                    }
                }
            });

            // 为该SearchView组件设置事件监听器
            sv_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    sv_search.clearFocus();
                    if (getBuilderList != null) {
                        s = s.toLowerCase();
                        searchBuilderKeyWord(s, getBuilderList);
                    }
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    if (!s.equals("")) {
                        if (getBuilderList != null) {
                            s = s.toLowerCase();
                            searchBuilderKeyWord(s, getBuilderList);
                        }
                    }else{
                        if (getBuilderList != null) {
                            getBuilderList.clear();
                            Comparator<QualityBookSearchNewsgetBuilderBean.ResultsBean> comparator = new Comparator<QualityBookSearchNewsgetBuilderBean.ResultsBean>() {
                                @Override
                                public int compare(QualityBookSearchNewsgetBuilderBean.ResultsBean resultsBean, QualityBookSearchNewsgetBuilderBean.ResultsBean t1) {
                                    String pinyin0 = CharacterParser.getInstance().getSelling(resultsBean.getJsdwname());
                                    String pinyin1 = CharacterParser.getInstance().getSelling(t1.getJsdwname());
                                    return pinyin0.compareTo(pinyin1);
                                }
                            };
                            Collections.sort(getBuilderListss, comparator);

                            getBuilderList.addAll(getBuilderListss);
                            getBuildersadapter.notifyDataSetChanged();
                        }
                    }
                    return false;
                }
            });
            sv_search.setIconifiedByDefault(false);
        } else if (receivegoods == 2) {
            showProgressDialog(this);
            getReceiveGoodsData();
            sidebar.setTextView(mDialog);
            sidebar.setOnTouchingLetterChangedListener(new Sidebar.OnTouchingLetterChangedListener() {
                @Override
                public void onTouchingLetterChanged(String s) {
                    // TODO Auto-generated method stub
                    int position = 0;
                    // 该字母首次出现的位置
                    if (receivegoodsAdapter != null) {
                        position = receivegoodsAdapter.getPositionForSection(s.charAt(0));
                    }
                    if (position != -1) {
                        smoothMoveToPosition(lvSearch, position);
                    }
                }
            });

            // 为该SearchView组件设置事件监听器
            sv_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    sv_search.clearFocus();
                    if (receiveGoodsList != null) {
                        s = s.toLowerCase();
                        searchReceiveGoodsKeyWord(s, receiveGoodsList);
                    }
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    if (s.equals("")) {
                        if (receiveGoodsListss != null) {
                            receiveGoodsList.clear();
                            receiveGoodsList.addAll(receiveGoodsListss);
                            receivegoodsAdapter.notifyDataSetChanged();
                            // receiveGoodsAdapter.notifyDataSetChanged();
                        }
                    }
                    return false;
                }
            });
            sv_search.setIconifiedByDefault(false);
        } else if (station == 3) {
            showProgressDialog(this);
            getStationData();
            sidebar.setTextView(mDialog);
            sidebar.setOnTouchingLetterChangedListener(new Sidebar.OnTouchingLetterChangedListener() {
                @Override
                public void onTouchingLetterChanged(String s) {
                    // TODO Auto-generated method stub
                    int position = 0;
                    // 该字母首次出现的位置
                    if (getStationListAdapter != null) {
                        position = getStationListAdapter.getPositionForSection(s.charAt(0));
                        Log.e("hhh", "onTouchingLetterChanged: " + position);
                    }
                    if (position != -1) {
                        Log.e("hhh", "onTouchingLetterChanged: " + position);
                        smoothMoveToPosition(lvSearch, position);
                    }
                }
            });

            // 为该SearchView组件设置事件监听器
            sv_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    sv_search.clearFocus();
                    if (getStationList != null) {
                        s = s.toLowerCase();
                        searchgetStationKeyWord(s, getStationList);
                    }
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    if (s.equals("")) {
                        if (getStationList != null) {
                            getStationList.clear();
                            getStationList.addAll(getStationListss);
                            getStationListAdapter.notifyDataSetChanged();
                        }
                    }
                    return false;
                }
            });
            sv_search.setIconifiedByDefault(false);
        }

        //    <--------------------Administrator -> 2019-8-7:1:57: 搜索页面复用 所以加此判断--------------------->
        String title = intent.getStringExtra("title");
        if (title != null && !title.equals("")) {
            tv_title.setText(title);
        }
    }


    /**
     * 滑动到指定位置
     */
    private void smoothMoveToPosition(RecyclerView mRecyclerView, final int position) {
        Log.e("position", "smoothMoveToPosition: " + position);
        // 第一个可见位置
        int firstItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(0));
        // 最后一个可见位置
        int lastItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(mRecyclerView.getChildCount() - 1));
        if (position < firstItem) {
            // 第一种可能:跳转位置在第一个可见位置之前
            mRecyclerView.smoothScrollToPosition(position);
        } else if (position <= lastItem) {
            // 第二种可能:跳转位置在第一个可见位置之后
            int movePosition = position - firstItem;
            if (movePosition >= 0 && movePosition < mRecyclerView.getChildCount()) {
                int top = mRecyclerView.getChildAt(movePosition).getTop();
                mRecyclerView.smoothScrollBy(0, top);
            }
        } else {
            // 第三种可能:跳转位置在最后可见项之后
            mRecyclerView.smoothScrollToPosition(position);
            mToPosition = position;
            mShouldScroll = true;
        }
    }


    private void searchBuilderKeyWord(final String s, final List<QualityBookSearchNewsgetBuilderBean.ResultsBean> getBuilderList) {
        getBuilderLists.clear();
        getBuilderList.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {
                getBuilderList.addAll(getBuilderListss);
                for (int i = 0; i < getBuilderList.size(); i++) {
                    String jsdwname = getBuilderList.get(i).getJsdwname();
                    if (jsdwname.indexOf(s.toString()) != -1 || characterParser.getSelling(jsdwname).contains(s.toString())) {
                        getBuilderLists.add(getBuilderList.get(i));
                    }
                }
                if (getBuilderLists.size() > 0) {
                    getBuilderList.clear();
                    getBuilderList.addAll(getBuilderLists);
                }

                Comparator<QualityBookSearchNewsgetBuilderBean.ResultsBean> comparator = new Comparator<QualityBookSearchNewsgetBuilderBean.ResultsBean>() {
                    @Override
                    public int compare(QualityBookSearchNewsgetBuilderBean.ResultsBean resultsBean, QualityBookSearchNewsgetBuilderBean.ResultsBean t1) {
                        String pinyin0 = CharacterParser.getInstance().getSelling(resultsBean.getJsdwname());
                        String pinyin1 = CharacterParser.getInstance().getSelling(t1.getJsdwname());
                        return pinyin0.compareTo(pinyin1);
                    }
                };
                Collections.sort(getBuilderList, comparator);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getBuildersadapter.initMap(getBuilderList);
                        getBuildersadapter.notifyDataSetChanged();
                    }
                });

            }
        }).start();
    }

    private void searchReceiveGoodsKeyWord(final String s, final List<QualityBookSearchNewsReceivegoodsBean.ResultsBean> receiveGoodsList) {
        receiveGoodsLists.clear();
        receiveGoodsList.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {
                receiveGoodsList.addAll(receiveGoodsListss);
                for (int i = 0; i < receiveGoodsList.size(); i++) {
                    String shdwname = receiveGoodsList.get(i).getShdwname();
                    if (shdwname.indexOf(s.toString()) != -1 || characterParser.getSelling(shdwname).contains(s.toString())) {
                        receiveGoodsLists.add(receiveGoodsList.get(i));
                    }
                }
                if (receiveGoodsLists.size() > 0) {
                    receiveGoodsList.clear();
                    receiveGoodsList.addAll(receiveGoodsLists);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        receivegoodsAdapter.initMap(receiveGoodsList);
                        receivegoodsAdapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }

    private void searchgetStationKeyWord(final String s, final List<QualityBookSearchNewsgetStationListBean.ResultsBean> getStationList) {
        getStationLists.clear();
        getStationList.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {
                getStationList.addAll(getStationListss);
                for (int i = 0; i < getStationList.size(); i++) {
                    String dzname = getStationList.get(i).getDzname();
                    if (dzname.indexOf(s.toString()) != -1 || characterParser.getSelling(dzname).contains(s.toString())) {
                        getStationLists.add(getStationList.get(i));
                    }
                }
                if (getStationLists.size() > 0) {
                    getStationList.clear();
                    getStationList.addAll(getStationLists);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getStationListAdapter.initMap(getStationList);
                        getStationListAdapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }


    private void initView() {
        tv_title = (TextView) findViewById(R.id.tv_quality_book_title_name);
        imgbtnBack = (ImageButton) findViewById(R.id.ib_quality_book_back);
        btnSure = (Button) findViewById(R.id.bt_quality_book_right_top);
        sv_search = (SearchView) findViewById(R.id.upload_finish_sv_search);
        btnSure = (Button) findViewById(R.id.bt_quality_book_right_top);
        lvSearch = (RecyclerView) findViewById(R.id.lv_search);
        mImgvDelete = (ImageView) findViewById(R.id.iv_icon_clear);
        iv_serach = (ImageView) findViewById(R.id.upload_finish_iv_serach);
        tv_serach = (TextView) findViewById(R.id.upload_finish_tv_serach);
        rv_serach = (RelativeLayout) findViewById(R.id.upload_finish_rv_serach);
        sidebar = (com.htmitech.ztcustom.zt.view.zgtw.Sidebar) findViewById(R.id.sidebar);
        mDialog = (TextView) findViewById(R.id.floating_header);
        btnSure.setOnClickListener(this);
        imgbtnBack.setOnClickListener(this);

        int search_mag_icon_id = sv_search.getContext().getResources().getIdentifier("android:id/search_mag_icon", null, null);
        ImageView search_mag_icon = (ImageView) sv_search.findViewById(search_mag_icon_id);// 获取搜索图标
        search_mag_icon.setImageResource(R.drawable.mx_search_bar_icon_normal);// 图标都是用src的
        // 设置提示文字的颜色,这里走了点奇招,用Html类方法
        sv_search.setQueryHint(Html.fromHtml("<font color = #999999>" + "请输入关键字搜索" + "</font>"));
        int id = sv_search.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView textView = (TextView) sv_search.findViewById(id);
        textView.setTextSize(DimenUtil.sp2px(this, 5));
        rv_serach.setOnClickListener(new View.OnClickListener() {

            @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                iv_serach.setVisibility(View.GONE);
                tv_serach.setVisibility(View.GONE);
                sv_search.setVisibility(View.VISIBLE);
                sv_search.onActionViewExpanded();
                InputMethodManager inputManager = (InputMethodManager) sv_search.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(sv_search, 0);
            }
        });
        sv_search.setOnCloseListener(new SearchView.OnCloseListener() {

            @Override
            public boolean onClose() {
                return true;
            }
        });
        if (sv_search != null) {
            try {
                //--拿到字节码
                Class<?> argClass = sv_search.getClass();
                //--指定某个私有属性,mSearchPlate是搜索框父布局的名字
                Field ownField = argClass.getDeclaredField("mSearchPlate");
                //--暴力反射,只有暴力反射才能拿到私有属性
                ownField.setAccessible(true);
                View mView = (View) ownField.get(sv_search);
                //--设置背景
                mView.setBackgroundColor(Color.TRANSPARENT);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    private void getBuildeData() {
        QualityBookSearchNewsgetBuilderRequest request = new QualityBookSearchNewsgetBuilderRequest();
        request.userid = userID;
        request.updatetime = year + month;
        Log.e("buil", "getBuildeData: " + "userID:" + userID + "yearmonth:" + year + month);
        AnsynHttpRequest.request(QualityBookKeyWordsSearchActivity.this, request, ContantValues.GETBUILDER, CHTTP.POST, new ObserverCallBack() {
            @Override
            public void success(String successMessage) {
                QualityBookSearchNewsgetBuilderBean getBuilderBean = FastJsonUtils.getPerson(successMessage, QualityBookSearchNewsgetBuilderBean.class);
                Log.e("getBuilderBean", "success: " + getBuilderBean.getResults().toString());
                if (getBuilderBean != null) {
                    List<QualityBookSearchNewsgetBuilderBean.ResultsBean> results = getBuilderBean.getResults();
                    getBuilderList.addAll(results);
                    Log.e("builder22", "onQueryTextSubmit: " + getBuilderList.size());
                    getBuilderListss.addAll(getBuilderList);

                    Comparator<QualityBookSearchNewsgetBuilderBean.ResultsBean> comparator = new Comparator<QualityBookSearchNewsgetBuilderBean.ResultsBean>() {
                        @Override
                        public int compare(QualityBookSearchNewsgetBuilderBean.ResultsBean resultsBean, QualityBookSearchNewsgetBuilderBean.ResultsBean t1) {
                            String pinyin0 = CharacterParser.getInstance().getSelling(resultsBean.getJsdwname());
                            String pinyin1 = CharacterParser.getInstance().getSelling(t1.getJsdwname());
                            return pinyin0.compareTo(pinyin1);
                        }
                    };
                    Collections.sort(getBuilderList, comparator);
                    Log.e("comparator", "QualityBookSearchNewsgetBuildersAdapter: " + comparator.toString());

                    lvgetBuildeData();
                }
                dimssDialog();
            }

            @Override
            public void fail(String exceptionMessage) {
                Toast.makeText(QualityBookKeyWordsSearchActivity.this, "数据请求失败!", Toast.LENGTH_SHORT).show();
                dimssDialog();
            }

            @Override
            public void notNetwork() {
                Toast.makeText(QualityBookKeyWordsSearchActivity.this, "网络异常!", Toast.LENGTH_SHORT).show();
                dimssDialog();
            }
        });
    }

    private void getPlannedCashRateList(String userId) {
        PlannedRateListRequestBean request = new PlannedRateListRequestBean();
        request.userid = userId;
        AnsynHttpRequest.request(QualityBookKeyWordsSearchActivity.this, request, CHTTP.PLANED_CASH_RATE_GET_PROJECT, CHTTP.POST, new ObserverCallBack() {
            @Override
            public void success(String successMessage) {
                QualityBookSearchNewsgetBuilderBean getBuilderBean = FastJsonUtils.getPerson(successMessage, QualityBookSearchNewsgetBuilderBean.class);
                if (getBuilderBean != null) {
                    if (getBuilderBean.getCode() == 200) {
                        getBuilderBean.moveDataToResultsBean();
                    } else {
                        Toast.makeText(QualityBookKeyWordsSearchActivity.this, "数据请求失败!", Toast.LENGTH_SHORT).show();
                        dimssDialog();
                        return;
                    }
                    List<QualityBookSearchNewsgetBuilderBean.ResultsBean> results = getBuilderBean.getResults();
                    getBuilderList.addAll(results);
                    Log.e("builder22", "onQueryTextSubmit: " + getBuilderList.size());
                    getBuilderListss.addAll(getBuilderList);

                    Comparator<QualityBookSearchNewsgetBuilderBean.ResultsBean> comparator = new Comparator<QualityBookSearchNewsgetBuilderBean.ResultsBean>() {
                        @Override
                        public int compare(QualityBookSearchNewsgetBuilderBean.ResultsBean resultsBean, QualityBookSearchNewsgetBuilderBean.ResultsBean t1) {
                            String pinyin0 = CharacterParser.getInstance().getSelling(resultsBean.getJsdwname());
                            String pinyin1 = CharacterParser.getInstance().getSelling(t1.getJsdwname());
                            return pinyin0.compareTo(pinyin1);
                        }
                    };
                    Collections.sort(getBuilderList, comparator);
                    Log.e("comparator", "QualityBookSearchNewsgetBuildersAdapter: " + comparator.toString());

                    lvgetBuildeData();
                }
                dimssDialog();
            }

            @Override
            public void fail(String exceptionMessage) {
                Toast.makeText(QualityBookKeyWordsSearchActivity.this, "数据请求失败!", Toast.LENGTH_SHORT).show();
                dimssDialog();
            }

            @Override
            public void notNetwork() {
                Toast.makeText(QualityBookKeyWordsSearchActivity.this, "网络异常!", Toast.LENGTH_SHORT).show();
                dimssDialog();
            }
        });
    }


    private void getReceiveGoodsData() {
        QualityBookSearchNewsReceivegoodsRequest request = new QualityBookSearchNewsReceivegoodsRequest();
        request.userid = userID;
        request.updatetime = year + month;
        request.jsdwid = (jsdwids == null ? "" : jsdwids);
        Log.e("builderchuandi22222", "onClick: " + "jsdwids:" + jsdwids + "userID:" + userID + "year + month:" + year + month);
        AnsynHttpRequest.request(this, request, ContantValues.GETRECREIVEGOODS,
                CHTTP.POST, new ObserverCallBack() {

                    @Override
                    public void success(String successMessage) {
                        QualityBookSearchNewsReceivegoodsBean receivegoodsBean = FastJsonUtils.getPerson(successMessage, QualityBookSearchNewsReceivegoodsBean.class);
                        for (int i = 0; i < receivegoodsBean.getResults().size(); i++) {
                            if (receivegoodsBean.getResults().get(i).getShdwname() == "安康工务段") {
                                Log.e("receivegoodsBean11111", "success: " + receivegoodsBean.getResults().get(i).getUpdatetime());
                            }
                        }
                        Log.e("receivegoodsBean", "success: " + receivegoodsBean.getResults().toString());
                        if (receivegoodsBean != null) {
                            List<QualityBookSearchNewsReceivegoodsBean.ResultsBean> results = receivegoodsBean.getResults();
                            receiveGoodsList.addAll(results);
                            receiveGoodsListss.addAll(receiveGoodsList);
                            Comparator<QualityBookSearchNewsReceivegoodsBean.ResultsBean> comparator = new Comparator<QualityBookSearchNewsReceivegoodsBean.ResultsBean>() {
                                @Override
                                public int compare(QualityBookSearchNewsReceivegoodsBean.ResultsBean resultsBean, QualityBookSearchNewsReceivegoodsBean.ResultsBean t1) {
                                    String pinyin0 = CharacterParser.getInstance().getSelling(resultsBean.getShdwname());
                                    String pinyin1 = CharacterParser.getInstance().getSelling(t1.getShdwname());
                                    return pinyin0.compareTo(pinyin1);
                                }
                            };
                            Collections.sort(receiveGoodsList, comparator);
                            Log.e("comparator", "QualityBookSearchNewsgetBuildersAdapter: " + comparator.toString());
                            lvgetReceiveGoodsData();
                        }
                        dimssDialog();
                    }

                    @Override
                    public void fail(String exceptionMessage) {
                        Toast.makeText(QualityBookKeyWordsSearchActivity.this, "数据请求失败!", Toast.LENGTH_SHORT).show();
                        dimssDialog();
                    }

                    @Override
                    public void notNetwork() {
                        Toast.makeText(QualityBookKeyWordsSearchActivity.this, "网络异常!", Toast.LENGTH_SHORT).show();
                        dimssDialog();
                    }
                });
    }


    private void getStationData() {
        QualityBookSearchNewsgetStationListRequest request = new QualityBookSearchNewsgetStationListRequest();
        request.userid = userID;
        request.updatetime = year + month;
        request.shdwid = (shdwids == null ? "" : shdwids);
        Log.e("receivrchuandi333333", "onClick: " + "shdwids:" + shdwids + "userID:" + userID + "year + month:" + year + month);
        AnsynHttpRequest.request(this, request, ContantValues.GETSTATION,
                CHTTP.POST, new ObserverCallBack() {

                    @Override
                    public void success(String successMessage) {
                        QualityBookSearchNewsgetStationListBean getStationListBean = FastJsonUtils.getPerson(successMessage, QualityBookSearchNewsgetStationListBean.class);
                        Log.e("getStationListBean", "success: " + getStationListBean.getResults().toString());
                        if (getStationListBean != null) {
                            List<QualityBookSearchNewsgetStationListBean.ResultsBean> results = getStationListBean.getResults();
                            getStationList.addAll(results);
                            getStationListss.addAll(getStationList);
                            Comparator<QualityBookSearchNewsgetStationListBean.ResultsBean> comparator = new Comparator<QualityBookSearchNewsgetStationListBean.ResultsBean>() {
                                @Override
                                public int compare(QualityBookSearchNewsgetStationListBean.ResultsBean resultsBean, QualityBookSearchNewsgetStationListBean.ResultsBean t1) {
                                    String pinyin0 = CharacterParser.getInstance().getSelling(resultsBean.getDzname());
                                    String pinyin1 = CharacterParser.getInstance().getSelling(t1.getDzname());
                                    return pinyin0.compareTo(pinyin1);
                                }
                            };
                            Collections.sort(getStationList, comparator);
//                            List<QualityBookSearchNewsgetStationListBean.ResultsBean> getStationListTemp = new ArrayList<QualityBookSearchNewsgetStationListBean.ResultsBean>();
//                            getStationListTemp.addAll(getStationList);
//                            getStationList.clear();
//                            for (int i = 0; i < getStationListTemp.size(); i++) {
//                                if (!getStationListTemp.get(i).getDzname().equals("")) {
//                                    getStationList.add(getStationListTemp.get(i));
//                                }
//                            }
                            Iterator it = getStationList.iterator();
                            QualityBookSearchNewsgetStationListBean.ResultsBean bean = new QualityBookSearchNewsgetStationListBean.ResultsBean();
                            while (it.hasNext()) {
                                bean = (QualityBookSearchNewsgetStationListBean.ResultsBean) it.next();
                                if (bean.getDzname().equals("")) {
                                    it.remove();
                                }
                            }
                            Log.e("comparator", "QualityBookSearchNewsgetBuildersAdapter: " + comparator.toString());
                            lvgetStationData();
                        }
                        dimssDialog();
                    }

                    @Override
                    public void fail(String exceptionMessage) {
                        Toast.makeText(QualityBookKeyWordsSearchActivity.this, "数据请求失败!", Toast.LENGTH_SHORT).show();
                        dimssDialog();
                    }

                    @Override
                    public void notNetwork() {
                        Toast.makeText(QualityBookKeyWordsSearchActivity.this, "网络无连接!", Toast.LENGTH_SHORT).show();
                        dimssDialog();
                    }
                });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEventBusMessage(EventBusMessage eventBusMessage) {
        if (builder == 1) {
            jsdwname = eventBusMessage.getName();
            jsdwid = eventBusMessage.getId();
            Log.e("eventBus", "getEventBusMessage: " + jsdwid + "___" + jsdwname);
        } else if (receivegoods == 2) {
            shdwname = eventBusMessage.getName();
            shdwid = eventBusMessage.getId();
            Log.e("eventBus", "getEventBusMessage: " + shdwname + "___" + shdwid);
        } else if (station == 3) {
            dzname = eventBusMessage.getName();
            dzid = eventBusMessage.getId();
            Log.e("eventBus", "getEventBusMessage: " + dzname + "___" + dzid);
        }

    }


    public void lvgetBuildeData() {
        builderlinearLayoutManager = new LinearLayoutManager(QualityBookKeyWordsSearchActivity.this, LinearLayoutManager.VERTICAL, false);
        lvSearch.setLayoutManager(builderlinearLayoutManager);
        getBuildersadapter = new QualityBookSearchNewsgetBuildersAdapter(QualityBookKeyWordsSearchActivity.this, getBuilderList);
        lvSearch.setAdapter(getBuildersadapter);

    }

    public void lvgetReceiveGoodsData() {
        receivegoodslinearLayoutManager = new LinearLayoutManager(QualityBookKeyWordsSearchActivity.this, LinearLayoutManager.VERTICAL, false);
        lvSearch.setLayoutManager(receivegoodslinearLayoutManager);
        receivegoodsAdapter = new QualityBookSearchNewsReceivegoodsAdapter(QualityBookKeyWordsSearchActivity.this, receiveGoodsList);
        lvSearch.setAdapter(receivegoodsAdapter);

    }

    public void lvgetStationData() {
        stationlinearLayoutManager = new LinearLayoutManager(QualityBookKeyWordsSearchActivity.this, LinearLayoutManager.VERTICAL, false);
        lvSearch.setLayoutManager(stationlinearLayoutManager);
        getStationListAdapter = new QualityBookSearchNewsgetStationListAdapter(QualityBookKeyWordsSearchActivity.this, getStationList);
        lvSearch.setAdapter(getStationListAdapter);

    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.bt_quality_book_right_top) {
            if (builder == 1 && getBuilderList != null) {
                intent1 = new Intent();
                if (from.equals("plannedCashRate")) {
                    intent1.putExtra("pntprojectname", jsdwname);
                    intent1.putExtra("id", jsdwid);
                    setResult(LISTRESULTCODE, intent1);
                } else {
                    intent1.putExtra("jsdwname", jsdwname);
                    intent1.putExtra("jsdwid", jsdwid);
                    setResult(BUILDERRESULTCODE, intent1);
                }
                Log.e("builderchuandi", "onClick: " + jsdwid + "______" + jsdwname);
                finish();
            } else if (receivegoods == 2 && receiveGoodsList != null) {
                intent2 = new Intent();
                intent2.putExtra("shdwname", shdwname);
                intent2.putExtra("shdwid", shdwid);
                setResult(RECEIVEGOODSESULTCODE, intent2);
                finish();
            } else if (station == 3 && getStationList != null) {
                intent3 = new Intent();
                intent3.putExtra("dzname", dzname);
                intent3.putExtra("dzid", dzid);
                setResult(STATIONRESULTCODE, intent3);
                finish();
            }
        } else if (view.getId() == R.id.ib_quality_book_back) {
            finish();
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(QualityBookKeyWordsSearchActivity.this);
    }
}