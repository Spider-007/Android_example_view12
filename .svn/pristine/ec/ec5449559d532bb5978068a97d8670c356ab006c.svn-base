package com.htmitech.htexceptionmanage.fragment;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.htmitech.MyView.EmptyLayout;
import com.htmitech.commonx.pulltorefresh.library.ILoadingLayout;
import com.htmitech.commonx.pulltorefresh.library.PullToRefreshBase;
import com.htmitech.commonx.pulltorefresh.library.PullToRefreshBase.Mode;
import com.htmitech.commonx.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.htmitech.commonx.pulltorefresh.library.PullToRefreshListView;
import com.htmitech.emportal.HtmitechApplication;
import com.htmitech.emportal.R;
import com.htmitech.emportal.entity.OAConText;
import com.htmitech.emportal.ui.main.IBottomItemSelectCallBack;
import com.htmitech.htexceptionmanage.activity.ManageExceptionDetalActivity;
import com.htmitech.htexceptionmanage.adapter.ManageExceptionAdapter;
import com.htmitech.htexceptionmanage.entity.ManageExcInfo;
import com.htmitech.htexceptionmanage.entity.ManageExceptionEntity;
import com.htmitech.htexceptionmanage.entity.ManageExceptionparameter;

import htmitech.com.componentlibrary.base.MyBaseFragment;
import htmitech.com.componentlibrary.listener.ObserverCallBackType;
import com.htmitech.proxy.doman.AppInfo;
import com.htmitech.proxy.doman.AppVersionConfig;
import com.htmitech.proxy.interfaces.CallBackOpen;
import com.htmitech.proxy.myenum.LogManagerEnum;
import com.htmitech.proxy.util.HTActivityUnit;
import com.minxing.client.util.Utils;

import java.util.HashMap;
import java.util.List;

import com.htmitech.thread.AnsynHttpRequest;
import com.htmitech.thread.CHTTP;
import com.htmitech.thread.GetFinalRefreshTokenAfterRequestValue;
import com.htmitech.thread.Network;

import htmitech.com.componentlibrary.unit.ServerUrlConstant;

/***
 * 消息提醒 所有列表展示
 *
 * @author joe
 * @date 2017-9-25 10:09:30
 */
public class ManageExceptionHaveDoneListFragment extends MyBaseFragment implements
        IBottomItemSelectCallBack, SearchView.OnQueryTextListener, ObserverCallBackType {
    private final static String TAG = ManageExceptionHaveDoneListFragment.class.getSimpleName();
    private PullToRefreshListView mPullToRefreshListView;
    private ManageExceptionAdapter adapter;
    /***
     * 页码
     **/
    private int pageNum = 1;
    /***
     * 每页要读取的记录数量
     **/
    private int pageSize = 10;

    private boolean isHasMore = true;
    private boolean flag = true;
    private SearchView sv_search;
    private RelativeLayout rv_serach;
    private ImageView iv_serach;
    private TextView tv_serach;
    private EmptyLayout layout_search_no;
    private boolean isSoso = false;


    private TextView tv_top;
    private boolean hideSearch = false;
    private CallBackOpen mCallBackOpen;

    private boolean isHome = false;
    private String app_id = "";
    private int isWaterSecurity;

    private ManageExceptionparameter manageExceptionparameter;
    private String manegeExceptionListUrl;
    private static final String EXCEPTIONLIST = "exceptionlist";
    private Gson mGson = new Gson();
    private ManageExceptionEntity manageExceptionEntity;
    private List<ManageExcInfo> manageExcInfoList;

    private String filterDays;
    private String sourceType;
    private String keyWord;

    @Override
    protected int getLayoutId() {
        // TODO Auto-generated method stub
        return R.layout.fragment_exception_havedown_list;
    }

    @SuppressLint("HandlerLeak")
    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    protected void initViews() {
        // TODO Auto-generated method stub
        Bundle mBunlde = getArguments();
        app_id = mBunlde.getString("app_id");
        isWaterSecurity = mBunlde.getInt("com_alert_mobileconfig_include_security");
        filterDays = mBunlde.getString("com_alert_plugin_selector_paramter_days") == null ? "0" : mBunlde.getString("com_alert_plugin_selector_paramter_days");
        sourceType = mBunlde.getString("com_alert_plugin_selector_paramter_sourcetype") == null ? "" : mBunlde.getString("com_alert_plugin_selector_paramter_sourcetype");
        keyWord = mBunlde.getString("com_alert_plugin_selector_paramter_title_keyword") == null ? "" : mBunlde.getString("com_alert_plugin_selector_paramter_title_keyword");
        isHome = getActivity().getIntent().getBooleanExtra("isHome", false);
        mPullToRefreshListView = (PullToRefreshListView) findViewById(R.id.listview_hasdo);
        sv_search = (SearchView) findViewById(R.id.sv_search);
        rv_serach = (RelativeLayout) findViewById(R.id.rv_serach);
        iv_serach = (ImageView) findViewById(R.id.iv_serach);
        tv_serach = (TextView) findViewById(R.id.tv_serach);
        tv_top = (TextView) findViewById(R.id.tv_top);
        layout_search_no = (EmptyLayout) findViewById(R.id.layout_search_no);
        int search_mag_icon_id = sv_search.getContext().getResources().getIdentifier("android:id/search_mag_icon", null, null);
        ImageView search_mag_icon = (ImageView) sv_search.findViewById(search_mag_icon_id);//获取搜索图标
        search_mag_icon.setImageResource(R.drawable.mx_search_bar_icon_normal);//图标都是用src的
        int id = sv_search.getContext()
                .getResources()
                .getIdentifier("android:id/search_src_text", null, null);
        TextView textView = (TextView) sv_search.findViewById(id);
        textView.setTextSize(14);
        rv_serach.clearFocus();//清除焦
        //设置提示文字的颜色,这里走了点奇招,用Html类方法
        sv_search.setQueryHint(Html.fromHtml("<font color = #999999>" + "请输入标题关键字" + "</font>"));
        rv_serach.setOnClickListener(new View.OnClickListener() {

            @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                isSoso = true;
                iv_serach.setVisibility(View.GONE);
                tv_serach.setVisibility(View.GONE);
                sv_search.setVisibility(View.VISIBLE);
                sv_search.onActionViewExpanded();
            }
        });
        sv_search.setOnQueryTextListener(this);
        sv_search.setOnCloseListener(new SearchView.OnCloseListener() {

            @Override
            public boolean onClose() {
                return true;
            }
        });
        sv_search.setIconifiedByDefault(false);
        mPullToRefreshListView.setMode(Mode.BOTH);
        ILoadingLayout startLabels = mPullToRefreshListView
                .getLoadingLayoutProxy(true, false);
        startLabels.setPullLabel("下拉刷新");// 刚下拉时，显示的提示
        startLabels.setRefreshingLabel("正在刷新...");// 刷新时
        startLabels.setReleaseLabel("释放开始刷新");// 下来达到一定距离时，显示的提示
        ILoadingLayout endLabels = mPullToRefreshListView.getLoadingLayoutProxy(
                false, true);
        endLabels.setPullLabel("上拉加载更多");// 刚下拉时，显示的提示
        endLabels.setRefreshingLabel("正在加载");// 刷新时
        endLabels.setReleaseLabel("释放开始加载");// 下来达到一定距离时，显示的提示
        mPullToRefreshListView.setOnRefreshListener(new OnRefreshListener2() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase refreshView) {
                // TODO Auto-generated method stub
                // 下拉刷新
                getRefreshData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase refreshView) {
                // TODO Auto-generated method stub 上拉加载更多
                getMoreData();
            }
        });

        mPullToRefreshListView
                .setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1,
                                            int position, long id) {
                        ManageExcInfo mManageExcInfo = null;
                        if (flag) {// item按键响应
                            mManageExcInfo = manageExcInfoList.get(position - 1);
                            HashMap map = new HashMap<String, String>();
                            map.put("app_id", app_id);
                            map.put("alertId", mManageExcInfo.getAlertId());
                            map.put("alertTitle", mManageExcInfo.getAlertTitle());
                            map.put("com_alert_mobileconfig_include_security", isWaterSecurity);
                            HTActivityUnit.switchTo(getActivity(), ManageExceptionDetalActivity.class, map);
                        }
                    }
                });

        if (hideSearch) {
            tv_top.setVisibility(View.GONE);
            sv_search.setVisibility(View.GONE);
            rv_serach.setVisibility(View.GONE);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            layoutParams.setMargins(20, 0, 20, 0);
            mPullToRefreshListView.setLayoutParams(layoutParams);
        }
    }

    public void httpData() {
        //TODO 写日志专用

        getRefreshData();
    }

    /**
     * 刷新列表
     **/
    public void getRefreshData() {
        if (manageExcInfoList != null) {
            manageExcInfoList.clear();
        }
        isHasMore = true;
        pageNum = 1;
        manageExceptionparameter = new ManageExceptionparameter();
        manageExceptionparameter.setPageNum(pageNum + "");
        manageExceptionparameter.setPageSize(pageSize + "");
        manageExceptionparameter.setUserId(OAConText.getInstance(HtmitechApplication.instance()).UserID);
        manageExceptionparameter.setFilterDays(filterDays);
        manageExceptionparameter.setSourceType(sourceType);
        manageExceptionparameter.setKeyWord(keyWord);
        getNeedParse();
    }

    public void getNeedParse() {
        manegeExceptionListUrl = ServerUrlConstant.SERVER_EMPAPI_URL() + ServerUrlConstant.MANAGE_EXCEPTION_LIST;
        AnsynHttpRequest.requestByPostWithToken(getActivity(), manageExceptionparameter, manegeExceptionListUrl, CHTTP.POSTWITHTOKEN, this, EXCEPTIONLIST, LogManagerEnum.GGENERAL.functionCode);
    }

    /**
     * 获取更多
     */
    public void getMoreData() {
        if (!isHasMore) {
            Utils.toast(getActivity(), "已经是最后一页了！", Toast.LENGTH_SHORT);
            mPullToRefreshListView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    mPullToRefreshListView.onRefreshComplete();
                }
            }, 100);
            return;
        }
        manageExceptionparameter = new ManageExceptionparameter();
        manageExceptionparameter.setPageNum((++pageNum) + "");
        manageExceptionparameter.setPageSize(pageSize + "");
        manageExceptionparameter.setUserId(OAConText.getInstance(getActivity()).UserID);
        manageExceptionparameter.setFilterDays(filterDays);
        manageExceptionparameter.setSourceType(sourceType);
        manageExceptionparameter.setKeyWord(keyWord);
        getNeedParse();
    }

    @Override
    public void onFragmentTabClick(int position) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean onQueryTextChange(String arg0) {
        // TODO Auto-generated method stub
        if (arg0.equals("")) {

        }
        isSoso = false;
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (sv_search != null)
            sv_search.clearFocus();
        httpData();

    }

    /**
     * @param arg0 关键字搜索
     */
    public void searchQuery(String arg0) {
        hideSearch = false;
        if (manageExceptionparameter == null) {
            return;
        }

        keyWord = arg0;
        getRefreshData();
    }

    /**
     * @param appInfo 插件搜索
     */
    public void searchQuery(AppInfo appInfo) {
        hideSearch = false;
        if (manageExceptionparameter == null) {
            return;
        }

        if (appInfo != null && appInfo.getmAppVersion() != null && appInfo.getmAppVersion().getAppVersionConfigArrayList() != null) {
            for (AppVersionConfig appVersionConfig : appInfo.getmAppVersion().getAppVersionConfigArrayList()) {
                if (appVersionConfig.getConfig_code().equals("com_alert_plugin_selector_paramter_days")) {
                    filterDays = appVersionConfig.getConfig_value();
                }
                if (appVersionConfig.getConfig_code().equals("com_alert_plugin_selector_paramter_sourcetype")) {
                    sourceType = appVersionConfig.getConfig_value();
                }
                if (appVersionConfig.getConfig_code().equals("com_alert_plugin_selector_paramter_title_keyword")) {
                    keyWord = appVersionConfig.getConfig_value();
                }
            }
        }
        getRefreshData();
    }

    /**
     * 收索全部
     */
    public void searchQueryAll() {

        hideSearch = false;
        keyWord = "";
        sourceType = "";
        filterDays = "0";
        getRefreshData();
    }

    public void hideSearch(boolean hideSearch) {
        this.hideSearch = hideSearch;
    }

    public void setCallBackOpen(CallBackOpen mCallBackOpen) {
        this.mCallBackOpen = mCallBackOpen;
    }

    @Override
    public void success(String requestValue, int type, String requestName) {
        if (EXCEPTIONLIST.equals(requestName)) {
            requestValue = GetFinalRefreshTokenAfterRequestValue.getFinalRequestValue(getActivity(), requestValue, type, manegeExceptionListUrl, manageExceptionparameter, this, requestName, LogManagerEnum.GGENERAL.functionCode);
            if (requestValue != null && !requestValue.equals("")) {
                layout_search_no.hide();
                mPullToRefreshListView.onRefreshComplete();
                manageExceptionEntity = mGson.fromJson(requestValue.toString(), ManageExceptionEntity.class);
                if (manageExceptionEntity.getResult() == null || manageExceptionEntity.getResult().size() == 0) {
                    layout_search_no.showEmpty();
                }
                if (manageExceptionEntity.getResult().size() < 10) {
                    isHasMore = false;
                }
                if (manageExcInfoList != null && manageExcInfoList.size() > 0) {
                    manageExcInfoList.addAll(manageExceptionEntity.getResult());
                } else {
                    manageExcInfoList = manageExceptionEntity.getResult();
                }
                adapter = new ManageExceptionAdapter(manageExcInfoList, getActivity());
                mPullToRefreshListView.setAdapter(adapter);
                if (mCallBackOpen != null)
                    mCallBackOpen.callBackOpen();

            }
        }

    }

    @Override
    public void fail(String exceptionMessage, int type, String requestName) {
        if (!Network.checkNetWork(HtmitechApplication.instance().getApplicationContext())) {
            layout_search_no.setNoWifiButtonClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog();
                    getRefreshData();
                }
            });
            layout_search_no.showNowifi();
        } else {
            if (!isHome) {
                layout_search_no.setErrorButtonClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ManageExceptionHaveDoneListFragment.this.getActivity().finish();
                    }
                });
            }
            layout_search_no.showError();
        }
    }

    @Override
    public void notNetwork() {

    }

    @Override
    public void callbackMainUI(String successMessage) {

    }
}
