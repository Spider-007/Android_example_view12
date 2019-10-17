package com.htmitech.htcommonformplugin.fragment;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.htmitech.MyView.EmptyLayout;
import com.htmitech.commonx.pulltorefresh.library.ILoadingLayout;
import com.htmitech.commonx.pulltorefresh.library.PullToRefreshBase;
import com.htmitech.commonx.pulltorefresh.library.PullToRefreshBase.Mode;
import com.htmitech.commonx.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.htmitech.commonx.pulltorefresh.library.PullToRefreshListView;
import com.htmitech.emportal.R;
import com.htmitech.emportal.base.IBaseCallback;
import htmitech.com.componentlibrary.base.MyBaseFragment;
import com.htmitech.emportal.entity.Doc;
import com.htmitech.emportal.entity.OAConText;
import com.htmitech.emportal.ui.daiban.model.task.GetDocListModel;
import com.htmitech.emportal.ui.main.IBottomItemSelectCallBack;
import com.htmitech.htcommonformplugin.adapter.GeneralFormAdapter;
import com.htmitech.htcommonformplugin.entity.Conidtionfiled;
import com.htmitech.htcommonformplugin.entity.GeneralFormListResult;
import com.htmitech.htcommonformplugin.entity.Listiteminfo;
import com.htmitech.htcommonformplugin.entity.Searchcondition;
import com.htmitech.pop.LoadingPopupWindow;
import com.htmitech.proxy.doman.AppInfo;
import com.htmitech.proxy.interfaces.CallBackOpen;
import com.htmitech.proxy.util.AngleUntil;
import com.minxing.client.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import htmitech.com.componentlibrary.unit.PreferenceUtils;

/***
 * 代办已办列表
 *
 * @author joe
 * @date 2017/04/17
 */
public class HaveDoneListFragment extends MyBaseFragment implements IBaseCallback,
        IBottomItemSelectCallBack, SearchView.OnQueryTextListener {
    private final static String TAG = "YiBanListFragment";

    private PullToRefreshListView mPullToRefreshListView;
    private GeneralFormAdapter docAdapter;
    /**
     * 是否已办理
     */
    private boolean isHaveRead = true;

    /***
     * 列表实体对象
     */
    private Vector<Listiteminfo> docListEntity;
    private Vector<Listiteminfo> vectorDoc;

    /***
     * 页码
     **/
    private int pageNum = 0;
    /***
     * 每页要读取的记录数量
     **/
    private int countPerPage = 10;

    private SimpleAdapter adapter;

    private static final int REFRESH_DATA = 1;
    private static final int PULLDOWNTOREGRESH = 2;

    private boolean isHasMore = true;
    private boolean flag = true;
    private SearchView sv_search;
    private RelativeLayout rv_serach;
    private ImageView iv_serach;
    private TextView tv_serach;
    private EmptyLayout layout_search_no;
    private boolean isSoso = false;
    private String modelName = "";
    private String todoFlag = "0";//代办已办标记0，代表待办；1，代表已办。空代表不限定

    private String com_commonform_plugin_selector_paramter_starttime = "";//查询的开始时间
    private String com_commonform_plugin_selector_paramter_endtime = "";//查询的结束时间
    private String com_commonform_plugin_selector_paramter_modulename = "";//支持模糊搜索，如“公文呈批件”，“公文收文”模块，输入“公文”来检索出所有流程名称中含“公文”的流程待办或已办项目
    private String com_commonform_plugin_selector_paramter_myfavflag = "";//我关注的标记：0，不限；1只查询我关注的；
    private String com_commonform_plugin_selector_paramter_otherfavflag = "";//其他人关注的标记：0，不限；1只查询我关注的；
    private String com_commonform_plugin_selector_paramter_mystartflag = "";//我关注的标记：0，不限；1只查询我关注的；
    private String com_commonform_plugin_selector_paramter_otherconditions = "";
    private String com_commonform_plugin_selector_paramter_flag = "";
    private String app_id = "";
    private Searchcondition mSearchcondition;

    private int actionButtonStyle;
    private int customerShortcuts;
    private int com_workflow_mobileconfig_IM_enabled;
    private int isFavValue;
    private int isOtherFavValue;
    private int isWaterSecurity;
    private int isShare;


    int days = 0;
    private TextView tv_top;
    private boolean hideSearch = false;
    private CallBackOpen mCallBackOpen;
    private boolean isMine = false; //表示是否是分享跳进来的
    private String com_commonform_mobileconfig_actionbutton_style;//0，直接在底部显示；1，浮动，点击后弹出。
    private String com_commonform_mobileconfig_customer_shortcuts;//
    private LoadingPopupWindow LoadingPopupWindow;


    private boolean isHome = false;


    @Override
    protected int getLayoutId() {
        // TODO Auto-generated method stub
        return R.layout.fragment_yiban_list;
    }

    @SuppressLint("HandlerLeak")
    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    protected void initViews() {
        // TODO Auto-generated method stub

//		LoadingPopupWindow = new LoadingPopupWindow(getActivity());
//		LoadingPopupWindow.show(getRootView());
//        showDialog();
        Bundle mBunlde = getArguments();
        if (mBunlde != null) {
            hideSearch = mBunlde.getBoolean("hideSearch", false);

        }
        if (!hideSearch) {
            hideSearch = getActivity().getIntent().getBooleanExtra("hideSearch", false);
        }
        isMine = getActivity().getIntent().getBooleanExtra("isMine", false);
//		if (!isMine) {
//			todoFlag = getActivity().getIntent().getStringExtra("com_commonform_plugin_selector_paramter_todoflag");
//
//		}else{
//
//
//
//		}
        if (mBunlde != null) {
            todoFlag = mBunlde.getString("com_commonform_plugin_selector_paramter_todoflag");
            if (isMine) {
                com_commonform_plugin_selector_paramter_myfavflag = mBunlde.getString("com_commonform_plugin_selector_paramter_myfavflag");
                com_commonform_plugin_selector_paramter_otherfavflag = mBunlde.getString("com_commonform_plugin_selector_paramter_otherfavflag");
            }
        } else {
            todoFlag = getActivity().getIntent().getStringExtra("com_commonform_plugin_selector_paramter_todoflag");
        }
        com_commonform_plugin_selector_paramter_starttime = getActivity().getIntent().getStringExtra("com_commonform_plugin_selector_paramter_starttime");
        com_commonform_plugin_selector_paramter_endtime = getActivity().getIntent().getStringExtra("com_commonform_plugin_selector_paramter_endtime");
        com_commonform_plugin_selector_paramter_modulename = getActivity().getIntent().getStringExtra("com_commonform_plugin_selector_paramter_modulename");
        if (!isMine) {
            com_commonform_plugin_selector_paramter_myfavflag = getActivity().getIntent().getStringExtra("com_commonform_plugin_selector_paramter_myfavflag");
            com_commonform_plugin_selector_paramter_otherfavflag = getActivity().getIntent().getStringExtra("com_commonform_plugin_selector_paramter_otherfavflag");
        }
        com_commonform_plugin_selector_paramter_mystartflag = getActivity().getIntent().getStringExtra("com_commonform_plugin_selector_paramter_mystartflag");
        com_commonform_plugin_selector_paramter_otherconditions = getActivity().getIntent().getStringExtra("com_commonform_plugin_selector_paramter_otherconditions");
        com_commonform_plugin_selector_paramter_flag = getActivity().getIntent().getStringExtra("com_commonform_plugin_selector_paramter_flag");
        String daysStrin = getActivity().getIntent().getStringExtra("com_commonform_plugin_selector_paramter_days");

        actionButtonStyle = mBunlde.getInt("com_commonform_mobileconfig_actionbutton_style", actionButtonStyle);
        customerShortcuts = mBunlde.getInt("com_commonform_mobileconfig_customer_shortcuts", customerShortcuts);
        com_workflow_mobileconfig_IM_enabled = mBunlde.getInt("com_commonform_mobileconfig_IM_enabled", com_workflow_mobileconfig_IM_enabled);
        isFavValue = mBunlde.getInt("com_commonform_mobileconfig_include_myfav", isFavValue);
        isOtherFavValue = mBunlde.getInt("com_commonform_mobileconfig_include_otherfav", isOtherFavValue);
        isWaterSecurity = mBunlde.getInt("com_commonform_mobileconfig_include_security", isWaterSecurity);
        isShare = mBunlde.getInt("com_commonform_mobileconfig_include_share", isShare);
        isHome = getActivity().getIntent().getBooleanExtra("isHome", false);

        if (daysStrin != null)
            days = daysStrin.equals("") ? 0 : Integer.parseInt(daysStrin);
        app_id = getActivity().getIntent().getStringExtra("app_id");
        // 已经办理列表
//		if(((InitFormFragmentActivity)getActivity()).todoFlag == 1){
//			modelName = ((InitFormFragmentActivity)getActivity()).modelName;
//		}

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
        rv_serach.setVisibility(View.VISIBLE);
        rv_serach.clearFocus();//清除焦
        //设置提示文字的颜色,这里走了点奇招,用Html类方法
        sv_search.setQueryHint(Html.fromHtml("<font color = #999999>" + "请输入标题关键字" + "</font>"));
//		if(!com_commonform_plugin_selector_paramter_modulename.equals("")){
//			iv_serach.setVisibility(View.GONE);
//			tv_serach.setVisibility(View.GONE);
//			sv_search.setVisibility(View.VISIBLE);
//			sv_search.setQuery(com_commonform_plugin_selector_paramter_modulename,false);
//		}
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
                pullDownRefresh();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase refreshView) {
                // TODO Auto-generated method stub 上拉加载更多
                pullUpLoadMore();
            }
        });
        // listView.setOnScrollListener(onscrollListener);
        mPullToRefreshListView
                .setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1,
                                            int position, long id) {
//                        iv_serach.setVisibility(View.GONE);
//                        tv_serach.setVisibility(View.GONE);
                        Listiteminfo doc = null;
                        if (flag) {// item按键响应
                            if (null != docListEntity) {
                                doc = docListEntity.get(position - 1);
                                Log.i(TAG, "commonFormResult.getData_id():" + doc.getForm_id());
                            }


                            HashMap map = new HashMap<String, String>();
                            map.put("user_id", OAConText.getInstance(getActivity()).UserID);
                            map.put("app_id", app_id);
                            map.put("data_id", doc.data_id);
                            map.put("id", doc.id);
                            map.put("form_id", doc.form_id);
                            map.put("subsystemid", doc.subsystemid); //2015-08-11
                            map.put("doc_title", doc.title);
                            map.put("iconurl", doc.iconurl);
                            map.put("newtag", doc.newtag);
                            map.put("time", doc.time);
                            map.put("todoflag", doc.todoflag);
                            map.put("otherinfo1", doc.otherinfo1);
                            map.put("otherinfo2", doc.otherinfo2);
                            map.put("com_commonform_mobileconfig_actionbutton_style", actionButtonStyle);
                            map.put("com_commonform_mobileconfig_customer_shortcuts", customerShortcuts);
                            map.put("com_commonform_mobileconfig_IM_enabled", com_workflow_mobileconfig_IM_enabled);
                            map.put("com_commonform_mobileconfig_include_myfav", isFavValue);
                            map.put("com_commonform_mobileconfig_include_otherfav", isOtherFavValue);
                            map.put("com_commonform_mobileconfig_include_security", isWaterSecurity);
                            map.put("com_commonform_mobileconfig_include_share", isShare);
//                            HTActivityUnit.switchTo(getActivity(), GeneralFormDetalActivity.class, map);
                        }
                    }
                });

        docAdapter = new GeneralFormAdapter(getActivity(), isHaveRead);

        mPullToRefreshListView.setAdapter(docAdapter);


        if (hideSearch) {
            tv_top.setVisibility(View.GONE);
            sv_search.setVisibility(View.GONE);
            rv_serach.setVisibility(View.GONE);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            layoutParams.setMargins(20, 0, 20, 0);
            mPullToRefreshListView.setLayoutParams(layoutParams);

            FrameLayout.LayoutParams layoutParamsLayout = new FrameLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            layoutParamsLayout.setMargins(0, 0, 0, 0);
            layout_search_no.setLayoutParams(layoutParamsLayout);
        }
    }

    public void httpData() {
        getSearchcondition();
        GetDocListModel getdocListModel = new GetDocListModel(this);
        getdocListModel.getDataFromServerByType(
                GetDocListModel.TYPE_GET_GENERAL_FORM_LIST, mSearchcondition);
        Log.d(TAG, "发起获取已办列表请求");
    }

    public void getSearchcondition() {
        mSearchcondition = new Searchcondition();
        mSearchcondition.app_id = app_id + "";
        mSearchcondition.user_id = PreferenceUtils.getEMPUserID(getActivity());
        mSearchcondition.starttime = com_commonform_plugin_selector_paramter_starttime;
        mSearchcondition.endtime = com_commonform_plugin_selector_paramter_endtime;
        mSearchcondition.days = days;
        mSearchcondition.myfavflag = com_commonform_plugin_selector_paramter_myfavflag;
        mSearchcondition.mystartflag = com_commonform_plugin_selector_paramter_mystartflag;
        mSearchcondition.todoflag = todoFlag;
        mSearchcondition.modulename = com_commonform_plugin_selector_paramter_modulename;
        mSearchcondition.otherfavflag = com_commonform_plugin_selector_paramter_otherfavflag;
        mSearchcondition.flag = com_commonform_plugin_selector_paramter_flag;
        mSearchcondition.page_num = 0;
        mSearchcondition.page_size = countPerPage;
        List<Conidtionfiled> conidtionfileds = null;
        if (com_commonform_plugin_selector_paramter_otherconditions != null && !com_commonform_plugin_selector_paramter_otherconditions.equals(""))
            conidtionfileds = JSON.parseArray(com_commonform_plugin_selector_paramter_otherconditions, Conidtionfiled.class);
        mSearchcondition.otherconditions = conidtionfileds;
    }

    public void pullDownRefresh() {
        // 刷新
        pageNum = 0;
        getRefreshData(GetDocListModel.TYPE_GET_GENERAL_FORM_LIST);
    }

    private void pullUpLoadMore() {
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
        pageNum = pageNum + 1;
        getMoreData(pageNum, GetDocListModel.TYPE_GET_GENERAL_FORM_LISTDATA);

    }

    /**
     * 刷新列表
     **/
    public void getRefreshData(int interfaceId) {
        mSearchcondition.page_num = 0;
        mSearchcondition.page_size = countPerPage;
        GetDocListModel getdocListModel = new GetDocListModel(this);
        getdocListModel.getDataFromServerByType(interfaceId,
                mSearchcondition);
        Log.d(TAG, "发起获取待办列表请求:getRefreshData");
    }

    /****
     * 获取更多
     */
    public void getMoreData(int pageNum, int interfaceId) {
        String orderString = "";
        /*
         * AuthWS.getInstance().goLink(MenuItem_mobileReceiverListData.this,
		 * false, MenuItem_mobileReceiverListData.this, interfaceId,
		 * getActivity(), orderString, countPerPage * pageNum, countPerPage *
		 * (pageNum + 1) - 1, method);
		 */
        mSearchcondition.page_size = countPerPage;
        mSearchcondition.page_num = pageNum;
        GetDocListModel getdocListModel = new GetDocListModel(this);
        getdocListModel.getDataFromServerByType(interfaceId,
                mSearchcondition);
        Log.d(TAG, "发起获取待办列表请求:getMoreData");
    }

    @Override
    public void onSuccess(int requestTypeId, Object result) {
        // TODO Auto-generated method stub
        Log.d(TAG, "返回获取待办列表请求");
        mPullToRefreshListView.onRefreshComplete();
        if (requestTypeId == GetDocListModel.TYPE_GET_GENERAL_FORM_LIST) { // 初次获取所有已办
            if (result != null) {
                GeneralFormListResult entity = (GeneralFormListResult) result;
                Log.d(TAG, "取得了" + entity.getResult().size() + "条已办信息");

                if (docListEntity == null) {
                    docListEntity = new Vector<Listiteminfo>();
                }
                docListEntity.removeAllElements();
                for (int i = 0; i < entity.getResult().size(); i++) {
                    docListEntity.add(entity.getResult().get(i));
                }
                if (entity.getResult().size() == 0) {
//					layout_search_no.setVisibility(View.VISIBLE);
                    if (isSoso) {
                        layout_search_no.setShowEmptyButton(false);
                        layout_search_no.showEmpty();
                    } else {
                        /**
                         * 点击按钮刷新
                         */
                        layout_search_no.setEmptyButtonClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pullDownRefresh();
                            }
                        });
                        layout_search_no.setShowEmptyButton(true);
                        layout_search_no.showEmpty();
                        layout_search_no.setShowEmptyButton(true);
                    }
                } else {
                    layout_search_no.hide();
//					layout_search_no.setVisibility(View.GONE);
                }
            } else {
                docListEntity = new Vector<Listiteminfo>();
            }
            if (docListEntity.size() < countPerPage) {
                isHasMore = false;
            }
            setData();
            if (mCallBackOpen != null)
                mCallBackOpen.callBackOpen();
        } else if (requestTypeId == GetDocListModel.TYPE_GET_GENERAL_FORM_LISTDATA) {
            if (result != null) {
                GeneralFormListResult entity = (GeneralFormListResult) result;
                Log.d(TAG, "取得了更多：" + entity.getResult().size() + "条待办信息");

                for (int i = 0; i < entity.getResult().size(); i++) {
                    docListEntity.add(entity.getResult().get(i));
                }
                if (entity.getResult().size() < (countPerPage)) {
                    isHasMore = false;
                }
                setData();
            }
            if (mCallBackOpen != null)
                mCallBackOpen.callBackOpen();

        }
        if (LoadingPopupWindow != null && LoadingPopupWindow.isShowing()) {
            LoadingPopupWindow.dismiss();
        }
//        dismissDialog();
//		layout_search_no.hide();
    }

    // 修改
    public ArrayList<Doc> getData(Vector<Doc> docListEntity) {
        if (docListEntity == null) {
            return new ArrayList<Doc>();
        } else {
            return new ArrayList<Doc>(docListEntity);
        }
    }

    public void setData() {
        docAdapter.setData(true, docListEntity);
    }

    @Override
    public void onFail(int requestTypeId, int statusCode, String errorMsg,
                       Object result) {
        // TODO Auto-generated method stub
        Log.d(TAG, "返回获取已办列表请求：错误");
//        dismissDialog();
        if (!Utils.isNetworkAvailable()) {
            layout_search_no.setNoWifiButtonClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pullDownRefresh();
                }
            });
            layout_search_no.showNowifi();
        } else {
            if (!isHome) {
                layout_search_no.setErrorButtonClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HaveDoneListFragment.this.getActivity().finish();
                    }
                });
            }
            layout_search_no.showError();
        }
        if (mCallBackOpen != null)
            mCallBackOpen.callBackOpen();
    }

    @Override
    public void onFragmentTabClick(int position) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean onQueryTextChange(String arg0) {
        // TODO Auto-generated method stub
        if (arg0.equals("")) {
//            showDialog();
            mSearchcondition.page_num = 0;
            mSearchcondition.page_size = countPerPage;
            mSearchcondition.title_keyword = "" + arg0;
            GetDocListModel getdocListModel = new GetDocListModel(this);
            getdocListModel.getDataFromServerByType(
                    GetDocListModel.TYPE_GET_GENERAL_FORM_LIST, mSearchcondition);
            sv_search.clearFocus();
        }
        isSoso = false;
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String arg0) {
        // TODO Auto-generated method stub
//        showDialog();
        mSearchcondition.page_num = 0;
        mSearchcondition.page_size = countPerPage;
        mSearchcondition.title_keyword = "" + arg0;
        GetDocListModel getdocListModel = new GetDocListModel(this);
        getdocListModel.getDataFromServerByType(
                GetDocListModel.TYPE_GET_GENERAL_FORM_LIST, mSearchcondition);
        sv_search.clearFocus();
        if (arg0.toString().equals("")) {
            isSoso = false;
        } else {
            isSoso = true;
        }
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
     * @param arg0
     */
    public void searchQuery(String arg0) {
        hideSearch = false;
        if (mSearchcondition == null) {
            return;
        }
//        showDialog();
        mSearchcondition.page_num = 0;
        mSearchcondition.page_size = countPerPage;
        mSearchcondition.title_keyword = "" + arg0;
        GetDocListModel getdocListModel = new GetDocListModel(this);
        getdocListModel.getDataFromServerByType(
                GetDocListModel.TYPE_GET_GENERAL_FORM_LIST, mSearchcondition);
    }

    public void searchQuery(AppInfo appInfo) {
        hideSearch = false;
        if (mSearchcondition == null) {
            return;
        }
//        showDialog();

        mSearchcondition = AngleUntil.getSearchcondition(getActivity(), appInfo);
        mSearchcondition.page_num = 0;
        mSearchcondition.page_size = countPerPage;
        GetDocListModel getdocListModel = new GetDocListModel(this);
        getdocListModel.getDataFromServerByType(
                GetDocListModel.TYPE_GET_GENERAL_FORM_LIST, mSearchcondition);
    }

    public void searchQueryAll() {
        hideSearch = false;
        getSearchcondition();
        searchQuery("");
    }

    public void hideSearch(boolean hideSearch) {
        this.hideSearch = hideSearch;
    }

    public void setCallBackOpen(CallBackOpen mCallBackOpen) {
        this.mCallBackOpen = mCallBackOpen;
    }

    public void setTodoFlag(String todoFlag) {
        this.todoFlag = todoFlag;
    }

    public void setIsMine(boolean isMine) {
        this.isMine = isMine;
    }
}