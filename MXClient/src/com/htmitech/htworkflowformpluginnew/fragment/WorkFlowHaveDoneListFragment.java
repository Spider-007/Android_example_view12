package com.htmitech.htworkflowformpluginnew.fragment;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.htmitech.MyView.EmptyLayout;
import com.htmitech.api.BookInit;
import com.htmitech.app.Constant;
import com.htmitech.commonx.pulltorefresh.library.ILoadingLayout;
import com.htmitech.commonx.pulltorefresh.library.PullToRefreshBase;
import com.htmitech.commonx.pulltorefresh.library.PullToRefreshBase.Mode;
import com.htmitech.commonx.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.htmitech.commonx.pulltorefresh.library.PullToRefreshListView;
import com.htmitech.emportal.HtmitechApplication;
import com.htmitech.emportal.R;
import com.htmitech.emportal.entity.OAConText;
import com.htmitech.emportal.ui.main.IBottomItemSelectCallBack;
import com.htmitech.htworkflowformpluginnew.activity.InitWorkFlowFormActivity;
import com.htmitech.htworkflowformpluginnew.activity.WorkFlowFormDetalActivity;
import com.htmitech.htworkflowformpluginnew.adapter.WorkFlowFormAdapter;
import com.htmitech.htworkflowformpluginnew.entity.Doc;
import com.htmitech.htworkflowformpluginnew.entity.DocSearchParameters;
import com.htmitech.htworkflowformpluginnew.entity.GetDocListEntity;
import com.htmitech.htworkflowformpluginnew.entity.MineFaQiResultBean;
import com.htmitech.htworkflowformpluginnew.entity.MyFQResult;
import com.htmitech.htworkflowformpluginnew.entity.Others;
import com.htmitech.htworkflowformpluginnew.entity.PrevenanceResult;
import com.htmitech.htworkflowformpluginnew.entity.PrevenanceResultBean;
import com.htmitech.htworkflowformpluginnew.listener.IWorkFlowCheckCountBack;
import com.htmitech.htworkflowformpluginnew.listener.IWorkFlowScrollBack;
import com.htmitech.htworkflowformpluginnew.myenum.RequestTypeEnum;
import com.htmitech.htworkflowformpluginnew.util.AngleWorkFlowUntil;
import com.htmitech.htworkflowformpluginnew.util.AnimatorUtil;

import htmitech.com.componentlibrary.base.MyBaseFragment;
import htmitech.com.componentlibrary.listener.ObserverCallBackType;
import com.htmitech.myEnum.ChooseWay;
import com.htmitech.myEnum.LogManagerEnum;
import com.htmitech.pop.ActionSheetDialog;
import com.htmitech.proxy.doman.AppInfo;
import com.htmitech.proxy.interfaces.CallBackOpen;
import com.htmitech.proxy.interfaces.INetWorkManager;

import com.htmitech.proxy.util.HTActivityUnit;
import com.htmitech.proxy.util.LogManagerProxy;
import com.htmitech.proxy.util.NetWorkManager;
import com.htmitech.thread.AnsynHttpRequest;
import com.htmitech.thread.CHTTP;
import com.htmitech.thread.GetFinalRefreshTokenAfterRequestValue;
import com.htmitech.thread.Network;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.minxing.client.util.FastJsonUtils;
import com.minxing.client.util.Utils;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import htmitech.com.componentlibrary.unit.ServerUrlConstant;

/***
 * 工作流 所有列表展示
 *
 * @author heyang
 * @date 2017-01-24
 */
public class WorkFlowHaveDoneListFragment extends MyBaseFragment implements View.OnClickListener, IBottomItemSelectCallBack, SearchView.OnQueryTextListener, ObserverCallBackType {
    private final static String TAG = "WorkFlowHaveDone";
    private PullToRefreshListView mPullToRefreshListView;
    private WorkFlowFormAdapter docAdapter;
    /**
     * 是否已办理
     */
    private boolean isHaveRead = true;

    /***
     * 列表实体对象
     */
    private Vector<Doc> docListEntity;
    private Vector<Doc> vectorDoc;

    /***
     * 页码
     **/
    private int pageNum = 0;
    /***
     * 每页要读取的记录数量
     **/
    private int countPerPage = 15;

    private boolean isHasMore = true;
    private boolean flag = true;
    private SearchView sv_search;
    private RelativeLayout rv_serach;
    private ImageView iv_serach;
    private TextView tv_serach;
    private EmptyLayout layout_search_no;
    private boolean isSoso = false;
    private String todoFlag = "0";//代办已办标记0，代表待办；1，代表已办。空代表不限定
    private String com_workflow_plugin_selector_paramter_IsMyFav = "";//是否要查询我关注的
    private String com_workflow_plugin_selector_paramter_IsMyStart = "";//是否要查询我发起的
    private String com_workflow_plugin_selector_paramter_ModelName = "";//模块名称   过滤流程名称。
    private String com_workflow_plugin_selector_paramter_Title = "";//检索标题 根据关键字过滤
    private String com_workflow_plugin_selector_paramter_TodoFlag = "";//待办已办标记
    private int com_workflow_mobileconfig_include_options;//意见审批是否显示常用意见
    private String com_workflow_mobileconfig_importance_workas_toreadflag;//待阅已阅
    private String startTime;
    private String endTime;
    private String app_id = "";
    private DocSearchParameters mDocSearchParameters;
    private int actionButtonStyle;
    private int customerShortcuts;
    private int com_workflow_mobileconfig_IM_enabled;
    private int isFavValue;
    private int isStartValue;//是否支持我的发起
    private int isWaterSecurity;
    private int isShare;
    public int isTextUrl;//是否正文展示HTML
    private TextView tv_top;
    private boolean hideSearch = false;
    private CallBackOpen mCallBackOpen;
    private RequestTypeEnum requestType = RequestTypeEnum.DBYB; //表示是否是关注跳进来的
    private boolean isMine = false;
    private com.htmitech.pop.LoadingPopupWindow LoadingPopupWindow;

    //新增代办已办列表日志 2017-07-18 09:23:47
    private INetWorkManager netWorkManager;
    private String app_version_id;
    private boolean isHome = false;
    public boolean isPullMore = false;

    private static final String WORK_FLOW_GETDOCLIST = "getDocList";
    private static final String WORK_FLOW_MYSTART = "myStart";
    private static final String WORK_FLOW_MYATTENTION = "myAttention";
    private static final String GETDOCLIST_PATH = ServerUrlConstant.SERVER_JAVA_URL() + ServerUrlConstant.OA_GETDOCLIST_METHOD_JAVA;
    private static final String MY_START_PATH = ServerUrlConstant.SERVER_JAVA_URL() + ServerUrlConstant.GET_MY_SEND_FLOWLIST_JAVA;
    private static final String MY_ATTENTION_PATH = ServerUrlConstant.SERVER_JAVA_URL() + ServerUrlConstant.GET_MY_ATTENTION_FLOWLIST_JAVA;
    private String com_workflow_plugin_selector_paramter_docListTypeCode;
    private IWorkFlowCheckCountBack mIWorkFlowCheckCountBack;
    private String com_workflow_mobileconfig_others;
    private IWorkFlowScrollBack mIWorkFlowScrollBack;
    private String appShortName;
    public boolean isRefresh = true;
    private boolean IS_GYL;
    private int com_workflow_mobileconfig_opinion_style;

    public IWorkFlowScrollBack getmIWorkFlowScrollBack() {
        return mIWorkFlowScrollBack;
    }

    public void setmIWorkFlowScrollBack(IWorkFlowScrollBack mIWorkFlowScrollBack) {
        this.mIWorkFlowScrollBack = mIWorkFlowScrollBack;
    }

    private ArrayList<Doc> checkDoc = new ArrayList<Doc>();

    public WorkFlowHaveDoneListFragment() {
    }

    public IWorkFlowCheckCountBack getmIWorkFlowCheckCountBack() {
        return mIWorkFlowCheckCountBack;
    }

    public void setmIWorkFlowCheckCountBack(IWorkFlowCheckCountBack mIWorkFlowCheckCountBack) {
        this.mIWorkFlowCheckCountBack = mIWorkFlowCheckCountBack;
    }

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

//        showDialog();
        Bundle mBunlde = getArguments();
        if (mBunlde != null) {
            hideSearch = mBunlde.getBoolean("hideSearch", false);
        }
        if (!hideSearch) {
            hideSearch = getActivity().getIntent().getBooleanExtra("hideSearch", false);
        }
        isMine = getActivity().getIntent().getBooleanExtra("isMine", false);
        if (mBunlde != null) {
            todoFlag = mBunlde.getString("com_workflow_plugin_selector_paramter_TodoFlag");
            com_workflow_plugin_selector_paramter_docListTypeCode = mBunlde.getString("com_workflow_plugin_selector_paramter_docListTypeCode");
            if (isMine) {
                com_workflow_plugin_selector_paramter_IsMyFav = mBunlde.getString("com_workflow_plugin_selector_paramter_IsMyFav");
                com_workflow_plugin_selector_paramter_IsMyStart = mBunlde.getString("com_workflow_plugin_selector_paramter_IsMyStart");
            }
        } else {
            todoFlag = getActivity().getIntent().getStringExtra("com_commonform_plugin_selector_paramter_todoflag");
        }
        if (!isMine) {
            com_workflow_plugin_selector_paramter_IsMyFav = mBunlde.getString("com_workflow_plugin_selector_paramter_IsMyFav");
            com_workflow_plugin_selector_paramter_IsMyStart = mBunlde.getString("com_workflow_plugin_selector_paramter_IsMyStart");
        }
        com_workflow_plugin_selector_paramter_IsMyFav = getActivity().getIntent().getStringExtra("com_workflow_plugin_selector_paramter_IsMyFav");
        com_workflow_plugin_selector_paramter_IsMyStart = getActivity().getIntent().getStringExtra("com_workflow_plugin_selector_paramter_IsMyStart");
        com_workflow_mobileconfig_importance_workas_toreadflag = mBunlde.getString("com_workflow_mobileconfig_importance_workas_toreadflag");
        com_workflow_plugin_selector_paramter_ModelName = mBunlde.getString("com_workflow_plugin_selector_paramter_ModelName");
        com_workflow_plugin_selector_paramter_Title = mBunlde.getString("com_workflow_plugin_selector_paramter_Title");

        if (TextUtils.isEmpty(com_workflow_plugin_selector_paramter_ModelName))

        {
            com_workflow_plugin_selector_paramter_ModelName = getActivity().getIntent().getStringExtra("com_workflow_plugin_selector_paramter_ModelName");
        }
        com_workflow_mobileconfig_others = mBunlde.getString("com_workflow_mobileconfig_others");
        appShortName = mBunlde.getString("appShortName");
        mDocSearchParameters = (DocSearchParameters) mBunlde.getSerializable("mDocSearchParameters");
        startTime = mBunlde.getString("startTime");
        endTime = mBunlde.getString("endTime");
        IS_GYL = mBunlde.getBoolean("IS_GYL",false);
        countPerPage = mBunlde.getInt("number",15);
        if(todoFlag.equals("1")){
            countPerPage = 15;
        }
        actionButtonStyle = mBunlde.getInt("com_workflow_mobileconfig_actionbutton_style", actionButtonStyle);
        customerShortcuts = mBunlde.getInt("com_workflow_mobileconfig_customer_shortcuts", customerShortcuts);
        com_workflow_mobileconfig_IM_enabled = mBunlde.getInt("com_workflow_mobileconfig_IM_enabled", com_workflow_mobileconfig_IM_enabled);
        com_workflow_mobileconfig_opinion_style = mBunlde.getInt("com_workflow_mobileconfig_opinion_style", 0);
        isFavValue = mBunlde.getInt("com_workflow_mobileconfig_include_myfav", isFavValue);
        isStartValue = mBunlde.getInt("com_workflow_mobileconfig_include_mystart", isStartValue);
        isWaterSecurity = mBunlde.getInt("com_workflow_mobileconfig_include_security", isWaterSecurity);
        isShare = mBunlde.getInt("com_workflow_mobileconfig_include_share", isShare);
        isTextUrl = mBunlde.getInt("com_workflow_mobileconfig_docview_style", isTextUrl);
        com_workflow_mobileconfig_include_options = mBunlde.getInt("com_workflow_mobileconfig_include_options", com_workflow_mobileconfig_include_options);
        isHome = getActivity().getIntent().getBooleanExtra("isHome", false);
        app_id = getActivity().getIntent().getStringExtra("app_id");
        app_version_id = getActivity().getIntent().getStringExtra("app_version_id");

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
        int id = sv_search.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView textView = (TextView) sv_search.findViewById(id);
        textView.setTextSize(14);
        rv_serach.setVisibility(View.VISIBLE);
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
        ILoadingLayout startLabels = mPullToRefreshListView.getLoadingLayoutProxy(true, false);
        startLabels.setPullLabel("下拉刷新");// 刚下拉时，显示的提示
        startLabels.setRefreshingLabel("正在刷新...");// 刷新时
        startLabels.setReleaseLabel("释放开始刷新");// 下来达到一定距离时，显示的提示
        ILoadingLayout endLabels = mPullToRefreshListView.getLoadingLayoutProxy(false, true);
        endLabels.setPullLabel("上拉加载更多");// 刚下拉时，显示的提示
        endLabels.setRefreshingLabel("正在加载");// 刷新时
        endLabels.setReleaseLabel("释放开始加载");// 下来达到一定距离时，显示的提示
        netWorkManager = LogManagerProxy.getProxyInstance(NetWorkManager.class);
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

        mPullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long id) {
                Doc doc = null;
                if (flag) {// item按键响应
                    if (null != docListEntity && docListEntity.size() > 0) {
                        doc = docListEntity.get(position - 1);
                    } else {
                        return;
                    }
                    HashMap map = new HashMap<String, String>();
                    map.put("app_id", app_id);
                    map.put("UserID", mDocSearchParameters.userId);
                    map.put("app_version_id", app_version_id);
                    map.put("DocId", doc.getDocId());
                    map.put("DocType", doc.getFlowName());
                    map.put("DocTitle", doc.getDocTitle());
                    map.put("Kind", doc.getSystemCode()); //2015-08-11
                    map.put("TodoFlag", doc.getTodoFlag());
                    map.put("sendFrom", doc.getSendFrom());
                    map.put("flowId", doc.getFlowId());
                    map.put("sendDate", doc.getSendDate());
                    map.put("com_workflow_mobileconfig_actionbutton_style", actionButtonStyle);
                    map.put("com_workflow_mobileconfig_customer_shortcuts", customerShortcuts);
                    map.put("com_workflow_mobileconfig_IM_enabled", com_workflow_mobileconfig_IM_enabled);
                    map.put("com_workflow_mobileconfig_opinion_style", com_workflow_mobileconfig_opinion_style);
                    map.put("com_workflow_mobileconfig_include_myfav", isFavValue);
                    map.put("com_workflow_mobileconfig_include_otherfav", isStartValue);
                    map.put("appShortName", appShortName);
                    map.put("com_workflow_mobileconfig_include_security", isWaterSecurity);
                    map.put("com_workflow_mobileconfig_include_share", isShare);
                    map.put("com_workflow_mobileconfig_docview_style", isTextUrl);
                    map.put("com_workflow_mobileconfig_include_options", com_workflow_mobileconfig_include_options);

                    if(getActivity() != null && getActivity() instanceof InitWorkFlowFormActivity){
                        map.put("com_workflow_mobileconfig_confirmdialog",((InitWorkFlowFormActivity)getActivity()).com_workflow_mobileconfig_confirmdialog);

                        map.put("com_workflow_mobileconfig_downloadType",((InitWorkFlowFormActivity)getActivity()).com_workflow_mobileconfig_downloadType);
                    }
                    if (doc.getIconUrl() == null || "".equals(doc.getIconUrl()) || !(doc.getIconUrl().endsWith(".png") || doc.getIconUrl().endsWith(".jpg"))) {
                        map.put("IconId", "");
                    } else {
                        map.put("IconId", doc.getIconUrl());
                    }
                    HTActivityUnit.switchTo(getActivity(), WorkFlowFormDetalActivity.class, map);
                }
            }
        });

        docAdapter = new WorkFlowFormAdapter(getActivity(), isHaveRead);
        docAdapter.IS_GYL = IS_GYL;
        docAdapter.setmWorkFlowAdapterCheck(new WorkFlowAdapterCheck() {
            @Override
            public void selectAdapterCheck(Doc doc) {
                if (doc.isCheck()) {
                    if (!checkDoc.contains(doc)) {
                        checkDoc.add(doc);
                    }
                } else {
                    if (checkDoc.contains(doc)) {
                        checkDoc.remove(doc);
                    }
                }

                if (mIWorkFlowCheckCountBack != null) {
                    mIWorkFlowCheckCountBack.workFlowCheckCount(checkDoc, checkDoc.size() == docListEntity.size());
                }
            }
        });
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
        mAnimatorUtil = new AnimatorUtil();
        mPullToRefreshListView.setOnScrollListener(new AbsListView.OnScrollListener() {

            int lastposition = 0; // 上一次在屏幕里可见的第一个item在整个listview的位置

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // TODO Auto-generated method stub
                switch (scrollState) {
                    // 当不滚动时
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:// 是当屏幕停止滚动时

                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:// 滚动时
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING:// 是当用户由于之前划动屏幕并抬起手指，屏幕产生惯性滑动时
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                View firstView = view.getChildAt(firstVisibleItem);
                /**
                 *  1，firstVisibleItem == 0：当前屏幕第一个可见item是整体listview的第一个item时
                 *  2，firstView == null：列表为空时；或者列表不为空时第一个item距离顶部的距离为0，说明已到达顶部
                 */
                if (mIWorkFlowScrollBack == null) {
                    return;
                }
                if (firstVisibleItem == 0 && (firstView == null || firstView.getTop() == 0)) {
                    mIWorkFlowScrollBack.animateBack();
                }

                if (firstVisibleItem > 0) {
                    // 即向上滑动列表
                    if (firstVisibleItem > lastposition) {
                        mIWorkFlowScrollBack.animateHide();
                    }
                    // 即向下滑动列表
                    if (firstVisibleItem < lastposition) {
                        mIWorkFlowScrollBack.animateBack();
                    }
                }
                lastposition = firstVisibleItem;
            }


        });

    }

    AnimatorUtil mAnimatorUtil;

    public void httpData() {
        if (docListEntity == null) {
            docListEntity = new Vector<Doc>();
        }
        getSearchcondition();
        LogManagerEnum.WORKFLOWGETDOCLIST.app_id = app_id;
        LogManagerEnum.WORKFLOWGETDOCLIST.appVersionId = app_version_id;

        LogManagerEnum.WORKFLOWMYSTART.app_id = app_id;
        LogManagerEnum.WORKFLOWMYSTART.appVersionId = app_version_id;

        LogManagerEnum.WORKFLOWMYATTENTION.app_id = app_id;
        LogManagerEnum.WORKFLOWMYATTENTION.appVersionId = app_version_id;
        switch (requestType) {
            case DBYB:
                netWorkManager.logFunactionStart(getActivity(), this, LogManagerEnum.WORKFLOWGETDOCLIST);
                break;
            case MYFQ:
                netWorkManager.logFunactionStart(getActivity(), this, LogManagerEnum.WORKFLOWMYSTART);
                break;
            case MYGZ:
                netWorkManager.logFunactionStart(getActivity(), this, LogManagerEnum.WORKFLOWMYATTENTION);
                break;
            default:
                netWorkManager.logFunactionStart(getActivity(), this, LogManagerEnum.WORKFLOWGETDOCLIST);
        }

    }

    public void AttentionFunction(Doc result) {
        String path = ServerUrlConstant.SERVER_BASE_URL() + ServerUrlConstant.SET_ATTENTION_YESORNO;
        AsyncHttpClient client = new AsyncHttpClient();
        OAConText instance = OAConText.getInstance(getActivity());
        client.addHeader("Content-Type", "application/json");
        try {
            JSONObject jsonObject = new JSONObject();
            JSONObject jsonObjectAll = new JSONObject();
            jsonObject.put("UserID", instance.UserID);
            jsonObject.put("UserName", instance.OA_UserName);
            jsonObject.put("OA_UserId", instance.OA_UserId);
            jsonObject.put("OA_DeptId", instance.ThirdDepartmentId);
            jsonObject.put("OA_DeptName", instance.ThirdDepartmentName);
            jsonObject.put("OA_UserName", instance.OA_UserName);
            jsonObject.put("OA_UnitId", instance.OA_UnitId);
            jsonObject.put("MRS_UserId", "");
            jsonObject.put("ThirdDepartmentId", instance.ThirdDepartmentId);
            jsonObject.put("ThirdDepartmentName", instance.ThirdDepartmentName);
            jsonObject.put("NetworkName", instance.NetworkName);
            jsonObjectAll.put("DocId", result.getDocId());
            jsonObjectAll.put("AttentionFlag", result.AttentionFlag);
            jsonObjectAll.put("AllowPush", result.AllowPush);
            jsonObjectAll.put("DocTitle", result.getDocTitle());
            jsonObjectAll.put("DocType", result.getFlowName());
            jsonObjectAll.put("SendFrom", result.getSendFrom());
            jsonObjectAll.put("SendDate", result.getSendDate());
            jsonObjectAll.put("iconId", result.getIconUrl());
            jsonObjectAll.put("Kind", result.getSystemCode());
            jsonObjectAll.put("context", jsonObject);

            StringEntity stringEntity = new StringEntity(jsonObjectAll.toString(), "utf-8");
            RequestHandle post = client.post(getActivity(), path,
                    stringEntity, null, new JsonHttpResponseHandler() {

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            super.onSuccess(statusCode, headers, response);
                            pullDownRefresh();
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            super.onFailure(statusCode, headers, throwable, errorResponse);
                            throwable.printStackTrace();
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void getSearchcondition() {
        String moudleName = "";
        List<Others> others = null;


        if (mDocSearchParameters != null) {
            moudleName = mDocSearchParameters.modelName;
            others = mDocSearchParameters.others;
        } else {
            moudleName = com_workflow_plugin_selector_paramter_ModelName;
            others = FastJsonUtils.getPersonList(com_workflow_mobileconfig_others, Others.class);
            ;
        }

        if (mDocSearchParameters == null) {
            mDocSearchParameters = new DocSearchParameters();
            mDocSearchParameters.modelName = moudleName;
            mDocSearchParameters.title = com_workflow_plugin_selector_paramter_Title;
        }


        mDocSearchParameters.userId = OAConText.getInstance(getActivity()).UserID;
        if (!TextUtils.isEmpty(com_workflow_mobileconfig_importance_workas_toreadflag)) {
            mDocSearchParameters.importance = com_workflow_mobileconfig_importance_workas_toreadflag;
            mDocSearchParameters.todoFlag = "";
        }else{
            mDocSearchParameters.todoFlag = todoFlag;
            mDocSearchParameters.importance = "";
        }
        mDocSearchParameters.appId = app_id + "";
        mDocSearchParameters.recordStartIndex = 0;
        mDocSearchParameters.startTime = startTime;
        mDocSearchParameters.endTime = endTime;

        mDocSearchParameters.others = others;
        mDocSearchParameters.recordEndIndex = countPerPage - 1;
//        mDocSearchParameters.modelName = com_workflow_plugin_selector_paramter_ModelName;

    }

    public void pullDownRefresh() {
        // 刷新
        isHasMore = true;
        pageNum = 0;
        getRefreshData();
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
        pageNum += countPerPage;
        getMoreData(pageNum);
    }

    /**
     * 刷新列表
     **/
    public void getRefreshData() {
        if (docListEntity == null) {
            docListEntity = new Vector<Doc>();
        }
        docListEntity.removeAllElements();
        mDocSearchParameters.recordStartIndex = 0;
        mDocSearchParameters.recordEndIndex = countPerPage - 1;
        switch (requestType) {
            case DBYB:
                netWorkManager.logFunactionStart(getActivity(), this, LogManagerEnum.WORKFLOWGETDOCLIST);
                break;
            case MYFQ:
                netWorkManager.logFunactionStart(getActivity(), this, LogManagerEnum.WORKFLOWMYSTART);
                break;
            case MYGZ:
                netWorkManager.logFunactionStart(getActivity(), this, LogManagerEnum.WORKFLOWMYATTENTION);
                break;
            default:
                netWorkManager.logFunactionStart(getActivity(), this, LogManagerEnum.WORKFLOWGETDOCLIST);
        }
    }


    /****
     * 获取更多
     */
    public void getMoreData(int pageNum) {

        isPullMore = true;
        mDocSearchParameters.recordStartIndex = pageNum;
        mDocSearchParameters.recordEndIndex = pageNum + countPerPage - 1;
        switch (requestType) {
            case DBYB:
                netWorkManager.logFunactionStart(getActivity(), this, LogManagerEnum.WORKFLOWGETDOCLIST);
                break;
            case MYFQ:
                netWorkManager.logFunactionStart(getActivity(), this, LogManagerEnum.WORKFLOWMYSTART);
                break;
            case MYGZ:
                netWorkManager.logFunactionStart(getActivity(), this, LogManagerEnum.WORKFLOWMYATTENTION);
                break;
            default:
                netWorkManager.logFunactionStart(getActivity(), this, LogManagerEnum.WORKFLOWGETDOCLIST);
        }
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
        try{
            for (int i = 0; i < checkDoc.size(); i++) {
                boolean isExit = false;
                for (Doc dDoc : docListEntity) {
                    if (checkDoc.get(i).getDocId().equals(dDoc.getDocId())) {
                        dDoc.setCheck(checkDoc.get(i).isCheck());
                        isExit = true;
                        break;
                    }
                }
                if (!isExit) {
                    checkDoc.remove(i);
                    i--;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        if (mIWorkFlowCheckCountBack != null) {
            mIWorkFlowCheckCountBack.workFlowCheckCount(checkDoc, checkDoc.size() == docListEntity.size());
        }

        docAdapter.setData(true, docListEntity);
    }

    @Override
    public void onFragmentTabClick(int position) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean onQueryTextChange(String arg0) {
        // TODO Auto-generated method stub
        if (arg0.equals("")) {
            if (mDocSearchParameters != null) {
                mDocSearchParameters.recordStartIndex = 0;
                mDocSearchParameters.recordEndIndex = countPerPage - 1;
                mDocSearchParameters.title = "" + arg0;
                getRefreshData();
                sv_search.clearFocus();
            }
        }
        isSoso = false;
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String arg0) {
        // TODO Auto-generated method stub
//        showDialog();
        mDocSearchParameters.recordStartIndex = 0;
        mDocSearchParameters.recordEndIndex = countPerPage - 1;
        mDocSearchParameters.title = "" + arg0;
        getRefreshData();
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

        if(isRefresh){
            httpData();
        }else{
            isRefresh = true;
        }
    }

    /**
     * @param arg0
     */
    public void searchQuery(String com_workflow_mobileconfig_others, String arg0) {
        hideSearch = false;
        if (mDocSearchParameters == null) {
            return;
        }
//        showDialog();
        mDocSearchParameters.todoFlag = todoFlag;
        mDocSearchParameters.setOthers(com_workflow_mobileconfig_others);
        mDocSearchParameters.recordStartIndex = 0;
        mDocSearchParameters.recordEndIndex = countPerPage - 1;
        mDocSearchParameters.title = "" + arg0;
        getRefreshData();
    }

    public void searchQueryShow(AppInfo appInfo, String com_workflow_mobileconfig_others) {
        showDialog();
        searchQuery(appInfo, com_workflow_mobileconfig_others);
    }

    public void searchQueryShow(String modelName, String com_workflow_mobileconfig_others) {
        showDialog();
        if (mDocSearchParameters != null) {
            mDocSearchParameters.modelName = modelName;
        }
        searchQuery(new AppInfo(), com_workflow_mobileconfig_others);
    }

    public void searchQuery(AppInfo appInfo, String com_workflow_mobileconfig_others) {
        hideSearch = false;
        if (mDocSearchParameters == null) {
            return;
        }
//        showDialog();
        if (appInfo != null && appInfo.getmAppVersion() != null) {
            mDocSearchParameters = AngleWorkFlowUntil.getSearchcondition(getActivity(), appInfo);
        }
        mDocSearchParameters.setOthers(com_workflow_mobileconfig_others);
        mDocSearchParameters.appId = app_id;
        mDocSearchParameters.recordStartIndex = 0;
        mDocSearchParameters.todoFlag = todoFlag;
        mDocSearchParameters.recordEndIndex = countPerPage - 1;
        getRefreshData();
    }

    public void initSearch(AppInfo appInfo, String com_workflow_mobileconfig_others) {
        mDocSearchParameters = AngleWorkFlowUntil.getSearchcondition(getActivity(), appInfo);
        if(mDocSearchParameters == null){
            mDocSearchParameters = new DocSearchParameters();
        }
        mDocSearchParameters.setOthers(com_workflow_mobileconfig_others);
    }

    public void initSearch(String modelName, String com_workflow_mobileconfig_others) {
        mDocSearchParameters = new DocSearchParameters();
        mDocSearchParameters.userId = BookInit.getInstance().getCrrentUserId();
        if (!TextUtils.isEmpty(com_workflow_mobileconfig_importance_workas_toreadflag)) {
            mDocSearchParameters.importance = com_workflow_mobileconfig_importance_workas_toreadflag;
        }else{
            mDocSearchParameters.todoFlag = todoFlag;
        }
        mDocSearchParameters.appId = app_id + "";

        mDocSearchParameters.recordStartIndex = 0;
        mDocSearchParameters.startTime = startTime;
        mDocSearchParameters.endTime = endTime;
        mDocSearchParameters.modelName = modelName;
        mDocSearchParameters.setOthers(com_workflow_mobileconfig_others);
        mDocSearchParameters.recordEndIndex = countPerPage - 1;
//        mDocSearchParameters.modelName = com_workflow_plugin_selector_paramter_ModelName;
        mDocSearchParameters.title = com_workflow_plugin_selector_paramter_Title;
    }


    public void searchsearchQueryAllShow(String com_workflow_mobileconfig_others) {
        showDialog();
        searchQueryAll(com_workflow_mobileconfig_others);
    }

    public void searchQueryAll(String com_workflow_mobileconfig_others) {
        hideSearch = false;
        getSearchcondition();
        mDocSearchParameters.modelName = "";
        searchQuery(com_workflow_mobileconfig_others, "");
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

    public void setRequestType(RequestTypeEnum requestType) {
        this.requestType = requestType;
    }

    @Override
    public void success(String requestValue, int type, String requestName) {
        dismissDialog();
        GetDocListEntity entity = new GetDocListEntity();
        if (requestName.equals(LogManagerEnum.WORKFLOWGETDOCLIST.functionCode)) {//获取待办已办列表日志
            AnsynHttpRequest.requestByPostWithToken(getActivity(), mDocSearchParameters, GETDOCLIST_PATH, CHTTP.POSTWITHTOKEN, this, WORK_FLOW_GETDOCLIST, LogManagerEnum.WORKFLOWGETDOCLIST.functionCode);
        } else if (requestName.equals(LogManagerEnum.WORKFLOWMYSTART.functionCode)) {//我的发起日志
            AnsynHttpRequest.requestByPostWithToken(getActivity(), mDocSearchParameters, MY_START_PATH, CHTTP.POSTWITHTOKEN, this, WORK_FLOW_MYSTART, LogManagerEnum.WORKFLOWMYSTART.functionCode);
        } else if (requestName.equals(LogManagerEnum.WORKFLOWMYATTENTION.functionCode)) {//我的关注日志
            AnsynHttpRequest.requestByPostWithToken(getActivity(), mDocSearchParameters, MY_ATTENTION_PATH, CHTTP.POSTWITHTOKEN, this, WORK_FLOW_MYATTENTION, LogManagerEnum.WORKFLOWMYATTENTION.functionCode);
        } else if (!requestName.equals("FunctionFinish")) {

            mPullToRefreshListView.onRefreshComplete();
            if (requestName.equals(WORK_FLOW_GETDOCLIST)) {
                requestValue = GetFinalRefreshTokenAfterRequestValue.getFinalRequestValue(getActivity(),requestValue, type, GETDOCLIST_PATH, mDocSearchParameters, this, requestName, LogManagerEnum.WORKFLOWGETDOCLIST.functionCode);
                if (!TextUtils.isEmpty(requestValue)) {
                    entity = FastJsonUtils.getPerson(requestValue, GetDocListEntity.class);
                }
            } else if (requestName.equals(WORK_FLOW_MYSTART)) {
                requestValue = GetFinalRefreshTokenAfterRequestValue.getFinalRequestValue(getActivity(),requestValue, type, MY_START_PATH, mDocSearchParameters, this, requestName, LogManagerEnum.WORKFLOWGETDOCLIST.functionCode);
                MineFaQiResultBean entityFQ = null;
                if (!TextUtils.isEmpty(requestValue)) {
                    entityFQ = FastJsonUtils.getPerson(requestValue, MineFaQiResultBean.class);
                    List<Doc> docListFQ = new ArrayList<Doc>();
                    if (entityFQ != null && entityFQ.Result != null) {
                        for (MyFQResult mResult : entityFQ.Result) {
                            Doc doc = new Doc();
                            doc.setDocId(mResult.docId);
                            doc.setDocTitle(mResult.docTitle);
                            doc.setFlowName(mResult.flowName);
                            doc.setSystemCode(mResult.systemCode);
                            doc.setSendDate(mResult.sendDate);
                            doc.setSendFrom(mResult.sendFrom);
                            doc.setIconUrl(mResult.iconUrl);
                            doc.setTodoFlag(mResult.todoFlag);
                            docListFQ.add(doc);
                        }
                        Doc[] docsFQ = (Doc[]) docListFQ.toArray(new Doc[docListFQ.size()]);
                        entity.setResult(docsFQ);
                    }
                }
            } else if (requestName.equals(WORK_FLOW_MYATTENTION)) {
                requestValue = GetFinalRefreshTokenAfterRequestValue.getFinalRequestValue(getActivity(),requestValue, type, MY_ATTENTION_PATH, mDocSearchParameters, this, requestName, LogManagerEnum.WORKFLOWGETDOCLIST.functionCode);
                PrevenanceResultBean entityGZ = null;
                if (!TextUtils.isEmpty(requestValue)) {
                    entityGZ = FastJsonUtils.getPerson(requestValue, PrevenanceResultBean.class);
                    List<Doc> docListGZ = new ArrayList<Doc>();
                    if (entityGZ != null && entityGZ.result != null) {
                        for (PrevenanceResult mResult : entityGZ.result) {
                            Doc doc = new Doc();
                            doc.setDocId(mResult.docId);
                            doc.setDocTitle(mResult.docTitle);
                            doc.setFlowName(mResult.flowName);
                            doc.setSystemCode(mResult.systemCode);
                            doc.setSendDate(mResult.sendDate);
                            doc.setSendFrom(mResult.sendFrom);
                            doc.setIconUrl(mResult.iconUrl);
                            doc.AllowPush = mResult.AllowPush;
                            doc.AttentionFlag = mResult.AttentionFlag;
                            doc.slideView = mResult.slideView;
                            docListGZ.add(doc);
                        }
                        Doc[] docsGZ = (Doc[]) docListGZ.toArray(new Doc[docListGZ.size()]);
                        entity.setResult(docsGZ);
                    }
                }
            }
            if (null != entity && entity.getResult() != null) {
                if (docListEntity == null) {
                    docListEntity = new Vector<Doc>();
                }

                if (!isPullMore) {
                    docListEntity.removeAllElements();
                }

                for (int i = 0; i < entity.getResult().length; i++) {
                    docListEntity.add(entity.getResult()[i]);
                }
                if (entity.getResult().length == 0 && !isPullMore) {
                    isHasMore = false;
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
                }
                if (requestName.equals(WORK_FLOW_GETDOCLIST)) {
                    netWorkManager.logFunactionFinsh(getActivity(), WorkFlowHaveDoneListFragment.this, "FunctionFinish", LogManagerEnum.WORKFLOWGETDOCLIST.functionCode, entity.message, INetWorkManager.State.SUCCESS);
                } else if (requestName.equals(WORK_FLOW_MYSTART)) {
                    netWorkManager.logFunactionFinsh(getActivity(), WorkFlowHaveDoneListFragment.this, "FunctionFinish", LogManagerEnum.WORKFLOWMYSTART.functionCode, entity.message, INetWorkManager.State.SUCCESS);
                } else if (requestName.equals(WORK_FLOW_MYATTENTION)) {
                    netWorkManager.logFunactionFinsh(getActivity(), WorkFlowHaveDoneListFragment.this, "FunctionFinish", LogManagerEnum.WORKFLOWMYATTENTION.functionCode, entity.message, INetWorkManager.State.SUCCESS);
                }
                if (entity.getResult().length < countPerPage) {
                    isHasMore = false;
                }
                setData();
                if (mCallBackOpen != null)
                    mCallBackOpen.callBackOpen();
            } else {
                if (requestName.equals(WORK_FLOW_GETDOCLIST)) {
                    netWorkManager.logFunactionFinsh(getActivity(), WorkFlowHaveDoneListFragment.this, "FunctionFinish", LogManagerEnum.WORKFLOWGETDOCLIST.functionCode, entity.message, INetWorkManager.State.FAIL);
                } else if (requestName.equals(WORK_FLOW_MYSTART)) {
                    netWorkManager.logFunactionFinsh(getActivity(), WorkFlowHaveDoneListFragment.this, "FunctionFinish", LogManagerEnum.WORKFLOWMYSTART.functionCode, entity.message, INetWorkManager.State.FAIL);
                } else if (requestName.equals(WORK_FLOW_MYATTENTION)) {
                    netWorkManager.logFunactionFinsh(getActivity(), WorkFlowHaveDoneListFragment.this, "FunctionFinish", LogManagerEnum.WORKFLOWMYATTENTION.functionCode, entity.message, INetWorkManager.State.FAIL);
                }

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
            dismissDialog();
            isPullMore = false;
        }
    }

    @Override
    public void fail(String exceptionMessage, int type, String requestName) {
        dismissDialog();
        if (requestName.equals(LogManagerEnum.WORKFLOWGETDOCLIST.functionCode)) {//获取待办已办列表
            AnsynHttpRequest.requestByPostWithToken(getActivity(), mDocSearchParameters, GETDOCLIST_PATH, CHTTP.POSTWITHTOKEN, this, WORK_FLOW_GETDOCLIST, LogManagerEnum.WORKFLOWGETDOCLIST.functionCode);
        } else if (requestName.equals(LogManagerEnum.WORKFLOWMYSTART.functionCode)) {//我的发起
            AnsynHttpRequest.requestByPostWithToken(getActivity(), mDocSearchParameters, MY_START_PATH, CHTTP.POSTWITHTOKEN, this, WORK_FLOW_MYSTART, LogManagerEnum.WORKFLOWMYSTART.functionCode);
        } else if (requestName.equals(LogManagerEnum.WORKFLOWMYATTENTION.functionCode)) {//我的关注
            AnsynHttpRequest.requestByPostWithToken(getActivity(), mDocSearchParameters, MY_ATTENTION_PATH, CHTTP.POSTWITHTOKEN, this, WORK_FLOW_MYATTENTION, LogManagerEnum.WORKFLOWMYATTENTION.functionCode);
        } else if (requestName.equals(WORK_FLOW_GETDOCLIST) || requestName.equals(WORK_FLOW_MYSTART) || requestName.equals(WORK_FLOW_MYATTENTION)) {//正式网络请求失败之后要出来空页面
            mPullToRefreshListView.onRefreshComplete();
            dismissDialog();
            if (!Network.checkNetWork(HtmitechApplication.instance().getApplicationContext())) {
                layout_search_no.setNoWifiButtonClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialog();
                        pullDownRefresh();
                    }
                });
                layout_search_no.showNowifi();
            } else {
                if (!isHome) {
                    layout_search_no.setErrorButtonClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            WorkFlowHaveDoneListFragment.this.getActivity().finish();
                        }
                    });
                }
                layout_search_no.showError();
            }
            if (mCallBackOpen != null) {
                mCallBackOpen.callBackOpen();
            }
            if (requestName.equals(WORK_FLOW_GETDOCLIST)) {
                netWorkManager.logFunactionFinsh(getActivity(), WorkFlowHaveDoneListFragment.this, "FunctionFinish", LogManagerEnum.WORKFLOWGETDOCLIST.functionCode, exceptionMessage, INetWorkManager.State.FAIL);
            } else if (requestName.equals(WORK_FLOW_MYSTART)) {
                netWorkManager.logFunactionFinsh(getActivity(), WorkFlowHaveDoneListFragment.this, "FunctionFinish", LogManagerEnum.WORKFLOWMYSTART.functionCode, exceptionMessage, INetWorkManager.State.FAIL);
            } else if (requestName.equals(WORK_FLOW_MYATTENTION)) {
                netWorkManager.logFunactionFinsh(getActivity(), WorkFlowHaveDoneListFragment.this, "FunctionFinish", LogManagerEnum.WORKFLOWMYATTENTION.functionCode, exceptionMessage, INetWorkManager.State.FAIL);
            }
        }
    }

    @Override
    public void notNetwork() {

    }

    @Override
    public void callbackMainUI(String successMessage) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }


    public interface WorkFlowAdapterCheck {
        void selectAdapterCheck(Doc doc);
    }


    public void setRefreshData(boolean isCheck) {
        for (com.htmitech.htworkflowformpluginnew.entity.Doc mDoc : docListEntity) {
            mDoc.setCheck(isCheck);
        }

        checkDoc = new ArrayList<com.htmitech.htworkflowformpluginnew.entity.Doc>(docListEntity);

        docAdapter.setData(true, docListEntity);
    }

    public ArrayList<Doc> getCheckDoc() {
        return checkDoc;
    }

    public void setCheckDoc(ArrayList<Doc> checkDoc) {
        this.checkDoc = checkDoc;
    }

    public Vector<Doc> getDocListEntity() {
        return docListEntity;
    }
}
