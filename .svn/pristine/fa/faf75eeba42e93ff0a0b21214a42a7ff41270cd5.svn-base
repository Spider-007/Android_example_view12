package com.htmitech.htworkflowformpluginnew.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.htmitech.api.BookInit;
import com.htmitech.app.Constant;
import com.htmitech.commonx.pulltorefresh.library.PullToRefreshBase;
import com.htmitech.commonx.pulltorefresh.library.PullToRefreshBase.Mode;
import com.htmitech.commonx.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.htmitech.commonx.pulltorefresh.library.PullToRefreshListView;
import com.htmitech.emportal.HtmitechApplication;
import com.htmitech.emportal.R;
import com.htmitech.emportal.base.IBaseCallback;
import com.htmitech.emportal.common.AppPreferenceHelper;
import com.htmitech.emportal.entity.OAConText;
import com.htmitech.emportal.ui.widget.LoadingView;
import com.htmitech.emportal.ui.widget.ToastInfo;
import com.htmitech.htworkflowformpluginnew.activity.WorkFlowFormDetalActivity;
import com.htmitech.htworkflowformpluginnew.entity.DocInfoParameters;
import com.htmitech.htworkflowformpluginnew.entity.FlowProcessInfo;
import com.htmitech.htworkflowformpluginnew.entity.Stepdes;
import com.htmitech.htworkflowformpluginnew.weight.PhoneClickText;

import htmitech.com.componentlibrary.base.MyBaseFragment;
import htmitech.com.componentlibrary.listener.ObserverCallBackType;

import com.htmitech.proxy.dao.AppliationCenterDao;
import com.htmitech.proxy.interfaces.INetWorkManager;
import com.htmitech.proxy.myenum.LogManagerEnum;
import com.htmitech.proxy.util.LogManagerProxy;
import com.htmitech.proxy.util.NetWorkManager;
import com.htmitech.thread.AnsynHttpRequest;
import com.htmitech.thread.CHTTP;
import com.htmitech.thread.GetFinalRefreshTokenAfterRequestValue;
import com.minxing.client.util.FastJsonUtils;
import com.minxing.kit.api.MXAPI;
import com.minxing.kit.api.bean.MXError;
import com.minxing.kit.api.callback.MXApiCallback;

import java.util.ArrayList;
import java.util.Collections;

import htmitech.com.componentlibrary.entity.DocResultInfo;
import htmitech.com.componentlibrary.unit.PreferenceUtils;
import htmitech.com.componentlibrary.unit.ServerUrlConstant;

/**
 * 工作流表单 流程详情
 *
 * @author joe
 * @date 2017-06-30 12:05:55
 */
public class WorkFlowFlowFragment extends MyBaseFragment implements IBaseCallback, ObserverCallBackType {
    private PullToRefreshListView mPullToRefreshListView;
    private LoadingView mLoadingView = null;
    private FlowAdapter mAdapter;
    private DialogFragment mNewFragment;

    private static final int PULLDOWN_TOREFRESH = 0;
    private static final int PULLUP_TOLOADMORE = 1;
    protected static final String FlowAdapter = null;

    private int mPageNum = 1;
    private boolean has_more = false;

    private LayoutInflater mInflater;
    private View mEmptyView;
    private FlowProcessInfo mFlowProcessInfo;

    private INetWorkManager netWorkManager;

    private static final String GET_WORK_FLOW_PATH = ServerUrlConstant.SERVER_JAVA_URL() + ServerUrlConstant.OA_GETDOC_FLOW_METHOD_JAVA;
    private static final String GETFLOW = "getFlowMethod";
    private DocInfoParameters mDocInfoParameters;
    private String flowId;

    public WorkFlowFlowFragment() {

    }

    public WorkFlowFlowFragment(String flowId) {
        this.flowId = flowId;
    }

    protected int getLayoutId() {
        return R.layout.fragment_flow;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.mInflater = inflater;
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     * 初始化View。
     */
    protected void initViews() {
        initValues();
    }

    private void initValues() {
        mPullToRefreshListView = (PullToRefreshListView) findViewById(R.id.listview_flow);
        if (getActivity() != null) {
            mEmptyView = View.inflate(getActivity(),
                    R.layout.layout_empty_view, null);
        }
        mPullToRefreshListView.setEmptyView(mEmptyView);
        setCommon();
        ListView mListview = mPullToRefreshListView.getRefreshableView();
        mLoadingView = (LoadingView) findViewById(R.id.loadingview_flow_listview);

        mAdapter = new FlowAdapter();
        Stepdes item = new Stepdes();

        // SwingBottomInAnimationAdapter swingBottomInAnimationAdapter = new
        // SwingBottomInAnimationAdapter(mAdapter);
        // swingBottomInAnimationAdapter.setListView(mListview);
        // mListview.setAdapter(swingBottomInAnimationAdapter);
        mListview.setAdapter(mAdapter);
        netWorkManager = LogManagerProxy.getProxyInstance(NetWorkManager.class);
        netWorkManager.logFunactionStart(getActivity(), this, "functionFlow", LogManagerEnum.APP_DOC_FLOW.functionCode);
        showLoadingView();

    }

    private void setCommon() {
        mPullToRefreshListView.setMode(Mode.DISABLED);
        // 下拉刷新时的提示文本设置
        mPullToRefreshListView.getLoadingLayoutProxy(true, false)
                .setLastUpdatedLabel("");
        mPullToRefreshListView.getLoadingLayoutProxy(true, false).setPullLabel(
                "下拉刷新");
        mPullToRefreshListView.getLoadingLayoutProxy(true, false)
                .setRefreshingLabel("正在刷新");
        mPullToRefreshListView.getLoadingLayoutProxy(true, false)
                .setReleaseLabel("放开刷新");
        // 上拉加载更多时的提示文本设置
        mPullToRefreshListView.getLoadingLayoutProxy(false, true)
                .setLastUpdatedLabel("");
        mPullToRefreshListView.getLoadingLayoutProxy(false, true).setPullLabel(
                "上拉加载");
        mPullToRefreshListView.getLoadingLayoutProxy(false, true)
                .setRefreshingLabel("正在加载");
        mPullToRefreshListView.getLoadingLayoutProxy(false, true)
                .setReleaseLabel("放开加载");

        mPullToRefreshListView
                .setOnRefreshListener(new OnRefreshListener2<ListView>() {
                    @Override
                    public void onPullDownToRefresh(
                            PullToRefreshBase<ListView> refreshView) {
                        // TODO Auto-generated method stub
                        pullDownToRefresh();
                    }

                    @Override
                    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                    }
                });

    }

    private void pullDownToRefresh() {
        long time = AppPreferenceHelper.getInstance(HtmitechApplication.instance()).getLong(this.getClass().getName(), System.currentTimeMillis());
        String label = "上次更新:";
        try {
            label = "上次更新:" + DateUtils.formatDateTime(getActivity(), time, DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_ABBREV_ALL);
        } catch (Exception e) {

        }
        mPullToRefreshListView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
        if (getActivity() != null && ((WorkFlowFormDetalActivity) getActivity()).mDocResultInfo == null) {
            return;
        }
        DocResultInfo mDocResultInfo = ((WorkFlowFormDetalActivity) getActivity()).mDocResultInfo;
        if (mDocResultInfo != null && mDocResultInfo.getResult() != null) {
            // 发起网络请求，获取所有待办列表
            mDocInfoParameters = new DocInfoParameters();
            mDocInfoParameters.userId = OAConText.getInstance(HtmitechApplication.instance()).UserID;
            mDocInfoParameters.docId = mDocResultInfo.getResult().getDocID(); //"HZ286f024cf9144b014d22ae386f495e";
            mDocInfoParameters.systemCode = ((WorkFlowFormDetalActivity) getActivity()).getDocKind(); //2015-08-11
            mDocInfoParameters.appId = ((WorkFlowFormDetalActivity) getActivity()).app_id;
            mDocInfoParameters.flowId = flowId;
            mDocInfoParameters.appVersionId = ((WorkFlowFormDetalActivity) getActivity()).app_version_id;
//            mDocInfoModel.getDataFromServerByType(DocInfoModel.TYPE_GET_FLOW, mDocInfoParameters);
            AnsynHttpRequest.requestByPostWithToken(getActivity(), mDocInfoParameters, GET_WORK_FLOW_PATH, CHTTP.POSTWITHTOKEN, this, GETFLOW, LogManagerEnum.APP_DOC_FLOW.functionCode);
        }
    }

    public void onResume() {
        super.onResume();
        pullDownToRefresh();
    }

    private void hideLoadingShowError() {
        mLoadingView.setVisibility(View.GONE);
        mLoadingView.stopLoading();
    }

    private void hideLoadingView() {
        mLoadingView.setVisibility(View.GONE);
        mLoadingView.stopLoading();
        judgeShowEmptyView();
    }

    private void judgeShowEmptyView() {
        if (mAdapter != null && mAdapter.getCount() == 0) {
            mPullToRefreshListView.setEmptyView(mEmptyView);
        }
    }

    private void showLoadingView() {
        mLoadingView.setVisibility(View.VISIBLE);
        mLoadingView.startLoading();
    }

    @Override
    public void success(String requestValue, int type, String requestName) {
        if (requestName.equals("functionFlow")) {
            pullDownToRefresh();
        } else if (GETFLOW.equals(requestName)) {
            requestValue = GetFinalRefreshTokenAfterRequestValue.getFinalRequestValue(getActivity(), requestValue, type, GET_WORK_FLOW_PATH, mDocInfoParameters, this, requestName, LogManagerEnum.APP_DOC_FLOW.functionCode);
            hideLoadingView();
            if (mPullToRefreshListView != null)
                mPullToRefreshListView.onRefreshComplete();
            if (!TextUtils.isEmpty(requestValue)) {
                mFlowProcessInfo = FastJsonUtils.getPerson(requestValue, FlowProcessInfo.class);
                if (mFlowProcessInfo != null) {
                    if (mFlowProcessInfo.getResult() != null) {
                        mAdapter.setData(mFlowProcessInfo.getResult(), PULLDOWN_TOREFRESH);

                        mAdapter.notifyDataSetChanged();
                    }
                    if (getActivity() != null) {
                        Stepdes lastItem = new Stepdes();
                        if (!Constant.IS_DZKF) {
                            if (mAdapter.arrayList != null && mAdapter.arrayList.size() > 0) {
                                if (!TextUtils.equals(((WorkFlowFormDetalActivity) getActivity()).mDocResultInfo.getResult().getCurrentNodeName(), mAdapter.arrayList.get(mAdapter.arrayList.size() - 1).getStepName())) {
                                    lastItem.UserID = ((WorkFlowFormDetalActivity) getActivity()).mDocResultInfo.getResult().getCurrentUserId();
                                    lastItem.Actiontime = "当前";
                                    lastItem.StepName = "当前："+((WorkFlowFormDetalActivity) getActivity()).mDocResultInfo.getResult().getCurrentNodeName();
                                    lastItem.userName = ((WorkFlowFormDetalActivity) getActivity()).mDocResultInfo.getResult().getCurrentUsername();
                                    lastItem.Action = "";
                                    lastItem.LoginName = ((WorkFlowFormDetalActivity) getActivity()).mDocResultInfo.getResult().getCurrentUserId();
                                    mAdapter.arrayList.add(lastItem);
                                }
                            }
                        }

                        if ("1".equals(Constant.com_workflow_mobileconfig_flowhistory_display_order)) {
                            Collections.reverse(mAdapter.arrayList);
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                    netWorkManager.logFunactionFinsh(getActivity(), this, "functionFlowFail", LogManagerEnum.APP_DOC_FLOW.functionCode, mFlowProcessInfo.getMessage(), INetWorkManager.State.SUCCESS);
                }
            } else {
                netWorkManager.logFunactionFinsh(getActivity(), this, "functionFlowFail", LogManagerEnum.APP_DOC_FLOW.functionCode, "返回值为空", INetWorkManager.State.FAIL);
                ToastInfo toast = ToastInfo.getInstance(getActivity());
                toast.setView(mInflater, R.drawable.prompt_error, "服务器异常");
                toast.show(Toast.LENGTH_SHORT);
                if (mAdapter == null || mAdapter.getCount() == 0) {
                    hideLoadingShowError();
                }
            }
        }
    }

    @Override
    public void fail(String exceptionMessage, int type, String requestName) {
        if (requestName.equals("functionFlow")) {
            pullDownToRefresh();
        } else if (GETFLOW.equals(requestName)) {
            hideLoadingView();
            netWorkManager.logFunactionFinsh(getActivity(), this, "functionFlowFail", LogManagerEnum.APP_DOC_FLOW.functionCode, exceptionMessage, INetWorkManager.State.FAIL);
            ToastInfo toast = ToastInfo.getInstance(getActivity());
            toast.setView(mInflater, R.drawable.prompt_error, "获取流程失败");
            toast.show(Toast.LENGTH_SHORT);
            if (mAdapter == null || mAdapter.getCount() == 0) {
                hideLoadingShowError();
            }
            if (mPullToRefreshListView != null)
                mPullToRefreshListView.onRefreshComplete();
        }
    }

    @Override
    public void notNetwork() {

    }

    @Override
    public void callbackMainUI(String successMessage) {

    }


    class FlowAdapter extends BaseAdapter {
        ArrayList<Stepdes> arrayList = new ArrayList<Stepdes>();

        public void setData(java.util.List<Stepdes> temp, int pullStyle) {
            if (pullStyle == PULLDOWN_TOREFRESH) {
                arrayList.clear();
                arrayList.addAll(temp);
            } else if (pullStyle == PULLUP_TOLOADMORE) {
                arrayList.addAll(temp);
            }

            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            if (position < arrayList.size()) {
                return arrayList.get(position);
            }
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {


            ViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(
                        HtmitechApplication.instance()).inflate(
                        R.layout.layout_flowlist_item, null);
                holder = new ViewHolder();
                holder.tv_timeStamp = (TextView) convertView
                        .findViewById(R.id.textview_flowlist_timestamp);
                holder.iv_buttom_line = (ImageView) convertView.findViewById(R.id.iv_buttom_line);
                holder.tv_stepname = (TextView) convertView
                        .findViewById(R.id.textview_flowlist_content_stepname);
                holder.tv_content_userName = (TextView) convertView
                        .findViewById(R.id.textview_flowlist_content_username);

                holder.img_people = (ImageView) convertView
                        .findViewById(R.id.imageview_flowlist_content_people);
                holder.tv_actionname = (TextView) convertView
                        .findViewById(R.id.textview_flowlist_content_actionname);

                holder.tv_comments = (TextView) convertView
                        .findViewById(R.id.textview_flowlist_content_comments);

                holder.img_tip = (ImageView) convertView
                        .findViewById(R.id.imageview_flowlist_content_tip);

                holder.tv_line_up = (ImageView) convertView
                        .findViewById(R.id.line_up);

                holder.tv_line_buttom = (ImageView) convertView
                        .findViewById(R.id.line_buttom);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            Stepdes entity = arrayList.get(position);

            holder.tv_timeStamp
                    .setText(TextUtils.isEmpty(entity.Actiontime) ? ""
                            : entity.Actiontime.replace("T", " "));
            String stepName = TextUtils.isEmpty(entity.StepName) ? ""
                    : entity.StepName;
            String action = TextUtils.isEmpty(entity.Action) ? ""
                    : entity.Action;
            String content = stepName + action;
            if (Constant.IS_DZKF) {
                holder.img_people.setVisibility(View.VISIBLE);
                holder.img_tip.setVisibility(View.GONE);
//                if(position == arrayList.size() - 1){
//                    holder.img_people.setImageResource(R.drawable.sign_oa_flow_line_current_dz);
//                }else{
//                    holder.img_people.setImageResource(R.drawable.sign_oa_flow_line_previous_dz);
//                }
                holder.tv_stepname.setTextColor(Color.parseColor("#BFBFBF"));
                holder.tv_comments.setTextColor(Color.parseColor("#BFBFBF"));
                holder.tv_actionname.setTextColor(Color.parseColor("#BFBFBF"));
                holder.tv_timeStamp.setTextColor(Color.parseColor("#BFBFBF"));
                holder.tv_content_userName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
                holder.tv_stepname.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                holder.tv_comments.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                holder.tv_actionname.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                holder.tv_timeStamp.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            }
            if (stepName.equals("结束")) {
                if (entity.getAction().equals("暂存") || entity.getAction().equals("已阅") || entity.getAction().equals("保存")) {
                    holder.tv_stepname.setText("结束");
                    holder.tv_content_userName.setText("");
                    holder.tv_actionname.setText(entity.getAction());
                    holder.tv_comments.setText("");
                    if (entity.getUserName() != null && !entity.getUserName().contains(";") && !entity.getUserName().contains(",")) {
                        holder.tv_content_userName.setText("");
                        SpannableString spannableString = new SpannableString(entity.getUserName());
//                        ClickableSpan clickttt = new PhoneClickText(getActivity(), entity.getLoginName(), "Flow", new FlowPeopleOnclick());
                        ClickableSpan clickttt = new PhoneClickText(getActivity(), new AppliationCenterDao(getActivity()).getLoginName(entity.getUserID()), "Flow", new FlowPeopleOnclick(entity.getUserID()), entity.getUserID());
                        spannableString.setSpan(clickttt, 0, entity.getUserName().length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                        holder.tv_content_userName.append(spannableString);
                        holder.tv_content_userName.setMovementMethod(LinkMovementMethod.getInstance());
                    } else if (entity.getUserName() != null && entity.getUserName().contains(";")) {
                        String[] arrayName = entity.getUserName().split(";");
                        String[] arrayUserId = entity.getUserID().split(";");
                        String nameString = "";
                        holder.tv_content_userName.setText("");
                        for (int i = 0; i < arrayName.length; i++) {
                            SpannableString spannableString = new SpannableString(arrayName[i] + (i != arrayName.length - 1 ? "," : ""));
//                            ClickableSpan clickttt = new PhoneClickText(getActivity(), arrayUserId[i], "Flow", new FlowPeopleOnclick());
                            ClickableSpan clickttt = new PhoneClickText(getActivity(), new AppliationCenterDao(getActivity()).getLoginName(arrayUserId[i]), "Flow", new FlowPeopleOnclick(arrayUserId[i]), arrayUserId[i]);
                            spannableString.setSpan(clickttt, 0, arrayName[i].length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                            holder.tv_content_userName.append(spannableString);
                            holder.tv_content_userName.setMovementMethod(LinkMovementMethod.getInstance());
                        }
                    } else if (entity.getUserName() != null && entity.getUserName().contains(",")) {
                        String[] arrayName = entity.getUserName().split(",");
                        String[] arrayUserId = entity.getUserID().split(",");
                        String nameString = "";
                        holder.tv_content_userName.setText("");
                        for (int i = 0; i < arrayName.length; i++) {
                            SpannableString spannableString = new SpannableString(arrayName[i] + (i != arrayName.length - 1 ? "," : ""));
//                            ClickableSpan clickttt = new PhoneClickText(getActivity(), arrayUserId[i], "Flow", new FlowPeopleOnclick());
                            ClickableSpan clickttt = new PhoneClickText(getActivity(), new AppliationCenterDao(getActivity()).getLoginName(arrayUserId[i]), "Flow", new FlowPeopleOnclick(arrayUserId[i]), arrayUserId[i]);
                            spannableString.setSpan(clickttt, 0, arrayName[i].length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                            holder.tv_content_userName.append(spannableString);
                            holder.tv_content_userName.setMovementMethod(LinkMovementMethod.getInstance());
                        }
                    }
                } else {
                    holder.tv_stepname.setText("结束");
                    holder.tv_content_userName.setText("");
                    holder.tv_actionname.setText("");
                    holder.tv_comments.setText("");
                    if (entity.getUserName() != null && !entity.getUserName().contains(";") && !entity.getUserName().contains(",")) {
                        holder.tv_content_userName.setText("");
                        SpannableString spannableString = new SpannableString(entity.getUserName());
//                        ClickableSpan clickttt = new PhoneClickText(getActivity(), entity.getLoginName(), "Flow", new FlowPeopleOnclick());
                        ClickableSpan clickttt = new PhoneClickText(getActivity(), new AppliationCenterDao(getActivity()).getLoginName(entity.getUserID()), "Flow", new FlowPeopleOnclick(entity.getUserID()), entity.getUserID());
                        spannableString.setSpan(clickttt, 0, entity.getUserName().length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                        holder.tv_content_userName.append(spannableString);
                        holder.tv_content_userName.setMovementMethod(LinkMovementMethod.getInstance());
                    } else if (entity.getUserName() != null && entity.getUserName().contains(";")) {
                        String[] arrayName = entity.getUserName().split(";");
                        String[] arrayUserId = entity.getUserID().split(";");
                        String nameString = "";
                        holder.tv_content_userName.setText("");
                        for (int i = 0; i < arrayName.length; i++) {
                            SpannableString spannableString = new SpannableString(arrayName[i] + (i != arrayName.length - 1 ? "," : ""));
//                            ClickableSpan clickttt = new PhoneClickText(getActivity(), arrayUserId[i], "Flow", new FlowPeopleOnclick());
                            ClickableSpan clickttt = new PhoneClickText(getActivity(), new AppliationCenterDao(getActivity()).getLoginName(arrayUserId[i]), "Flow", new FlowPeopleOnclick(arrayUserId[i]), arrayUserId[i]);

                            spannableString.setSpan(clickttt, 0, arrayName[i].length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                            holder.tv_content_userName.append(spannableString);
                            holder.tv_content_userName.setMovementMethod(LinkMovementMethod.getInstance());
                        }
                    } else if (entity.getUserName() != null && entity.getUserName().contains(",")) {
                        String[] arrayName = entity.getUserName().split(",");
                        String[] arrayUserId = entity.getUserID().split(",");
                        String nameString = "";
                        holder.tv_content_userName.setText("");
                        SpannableStringBuilder ssb = new SpannableStringBuilder();
                        for (int i = 0; i < arrayName.length; i++) {
                            SpannableString spannableString = new SpannableString(arrayName[i] + (i != arrayName.length - 1 ? "," : ""));
//                            ClickableSpan clickttt = new PhoneClickText(getActivity(), arrayUserId[i], "Flow", new FlowPeopleOnclick());
                            ClickableSpan clickttt = new PhoneClickText(getActivity(), new AppliationCenterDao(getActivity()).getLoginName(arrayUserId[i]), "Flow", new FlowPeopleOnclick(arrayUserId[i]), arrayUserId[i]);

                            spannableString.setSpan(clickttt, 0, arrayName[i].length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                            holder.tv_content_userName.append(spannableString);
                            holder.tv_content_userName.setMovementMethod(LinkMovementMethod.getInstance());
                        }
                    }
                }
            } else {
                if (entity.getUserName() != null && !entity.getUserName().contains(";") && !entity.getUserName().contains(",")) {
                    holder.tv_stepname.setText(stepName);
                    holder.tv_content_userName.setText("");
                    SpannableString spannableString = new SpannableString(entity.getUserName());
                    ClickableSpan clickttt = new PhoneClickText(getActivity(), new AppliationCenterDao(getActivity()).getLoginName(entity.getUserID()), "Flow", new FlowPeopleOnclick(entity.getUserID()), entity.getUserID());
                    spannableString.setSpan(clickttt, 0, entity.getUserName().length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    holder.tv_content_userName.append(spannableString);
                    holder.tv_content_userName.setMovementMethod(LinkMovementMethod.getInstance());

//                    holder.tv_content_userName.setText(entity.getOAUserName());
                    holder.tv_actionname.setText(entity.getAction() + (TextUtils.isEmpty(entity.getComments()) ? "" : ":"));
                    holder.tv_comments.setText(entity.getComments());

                } else if (entity.getUserName() != null && entity.getUserName().contains(";")) {
                    holder.tv_stepname.setText(stepName);
                    String[] arrayName = null;
                    String[] arrayUserId = null;
                    if (entity != null && entity.getUserName() != null) {
                        arrayName = entity.getUserName().split(";");
                    }
                    arrayUserId = new String[arrayName.length];
                    if (entity != null && entity.getUserID() != null) {

                        arrayUserId = entity.getUserID().split(";");
                    }


                    String nameString = "";
                    holder.tv_content_userName.setText("");
                    if (arrayName != null) {
                        if (arrayName.length == arrayUserId.length) {
                            for (int i = 0; i < arrayName.length; i++) {
                                SpannableString spannableString = new SpannableString(arrayName[i] + (i != arrayName.length - 1 ? "," : ""));
                                if (arrayName != null && arrayName.length > i) {
//                                ClickableSpan clickttt = new PhoneClickText(getActivity(), arrayUserId[i], "Flow", new FlowPeopleOnclick());
                                    ClickableSpan clickttt = new PhoneClickText(getActivity(), new AppliationCenterDao(getActivity()).getLoginName(arrayUserId[i]), "Flow", new FlowPeopleOnclick(arrayUserId[i]), arrayUserId[i]);
                                    spannableString.setSpan(clickttt, 0, arrayName[i].length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                                    holder.tv_content_userName.append(spannableString);
                                    holder.tv_content_userName.setMovementMethod(LinkMovementMethod.getInstance());
                                }
                            }
                        }
                    }
                    holder.tv_actionname.setText(entity.getAction() + (TextUtils.isEmpty(entity.getComments()) ? "" : ":"));
                    holder.tv_comments.setText(entity.getComments());
                } else if (entity.getUserName() != null && entity.getUserName().contains(",")) {
                    holder.tv_stepname.setText(stepName);
                    String[] arrayName = null;
                    String[] arrayUserId = null;
                    if (entity != null && entity.getUserID() != null) {
                        arrayName = entity.getUserName().split(",");
                    }
                    if (entity != null && entity.getUserID() != null) {
                        arrayName = entity.getUserName().split(",");
                        arrayUserId = entity.getUserID().split(",");
                    }
                    String nameString = "";
                    holder.tv_content_userName.setText("");
                    if (arrayName != null) {
                        for (int i = 0; i < arrayName.length; i++) {
                            SpannableString spannableString = new SpannableString(arrayName[i] + (i != arrayName.length - 1 ? "," : ""));
                            if (arrayUserId != null && arrayUserId.length > i) {
//                                ClickableSpan clickttt = new PhoneClickText(getActivity(), arrayUserId[i], "Flow", new FlowPeopleOnclick());
                                ClickableSpan clickttt = new PhoneClickText(getActivity(), new AppliationCenterDao(getActivity()).getLoginName(arrayUserId[i]), "Flow", new FlowPeopleOnclick(arrayUserId[i]), arrayUserId[i]);
                                spannableString.setSpan(clickttt, 0, arrayName[i].length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                                holder.tv_content_userName.append(spannableString);
                                holder.tv_content_userName.setMovementMethod(LinkMovementMethod.getInstance());
                            }
                        }
                    }
                    holder.tv_actionname.setText(entity.getAction() + (TextUtils.isEmpty(entity.getComments()) ? "" : ":"));
                    holder.tv_comments.setText(entity.getComments());
                }

//                holder.tv_content_userName.setOnClickListener(new FlowPeopleOnclick());
            }
            holder.img_tip.setImageResource(R.drawable.sign_oa_flow_line_previous_point);

            if (!stepName.equals("结束")) {
                if (!TextUtils.isEmpty(entity.Actiontime) && entity.Actiontime.equals("当前")) {
                    holder.img_tip.setImageResource(R.drawable.sign_oa_flow_line_current);
                    holder.tv_actionname.setText(entity.getAction());
                    holder.tv_timeStamp.setVisibility(View.GONE);
                } else {
                    holder.img_tip.setImageResource(R.drawable.sign_oa_flow_line_previous_point);
                }

            } else if (stepName.equals("结束")) {
                holder.img_tip.setImageResource(R.drawable.sign_oa_flow_line_end);
            }
            if (position == getCount() - 1) {
                //获取最后一条记录
                holder.iv_buttom_line.setVisibility(View.GONE);
                holder.tv_line_buttom.setVisibility(View.INVISIBLE);
            } else {
                holder.tv_line_up.setVisibility(View.VISIBLE);
                if (position == 0) {
                    holder.tv_line_up.setVisibility(View.INVISIBLE);
                }
                holder.iv_buttom_line.setVisibility(View.VISIBLE);
                holder.tv_line_buttom.setVisibility(View.VISIBLE);
            }
            if(null != entity && TextUtils.isEmpty(entity.getAction())){
                holder.tv_actionname.setVisibility(View.GONE);
            }else{
                holder.tv_actionname.setVisibility(View.VISIBLE);
            }
            if(null != entity && TextUtils.isEmpty(entity.getComments())){
                holder.tv_comments.setVisibility(View.GONE);
            }else{
                holder.tv_comments.setVisibility(View.VISIBLE);
            }
            return convertView;
        }

        public class FlowPeopleOnclick implements OnClickListener {
            String user_id;

            public FlowPeopleOnclick(String user_id) {
                this.user_id = user_id;
            }

            @Override
            public void onClick(View v) {
                if (((WorkFlowFormDetalActivity) getActivity()).com_workflow_mobileconfig_IM_enabled == 0 || !(new AppliationCenterDao(getActivity()).isEmiUser(user_id))) {
                    return;
                }
                String UserID = v.getTag().toString().split(":")[1];
                if (UserID.equalsIgnoreCase("admin")) {
//                    Toast.makeText(getActivity(), "不能发起对admin的聊天",
//                            Toast.LENGTH_SHORT).show();
                    return;
                } else if (UserID.equalsIgnoreCase(PreferenceUtils.getLogin_name(getActivity()))) {//BookInit.getInstance().getCurrentUser().getLogin_name())
//                    Toast.makeText(getActivity(), "不能发起对自己的聊天",
//                            Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Log.d("FlowFragment", "发起聊天，对：" + UserID);
                    String[] loginNames = new String[]{UserID};
                    // TODO Auto-generated method stub
                    MXAPI.getInstance(getActivity()).chat(loginNames, new MXApiCallback() {
                        @Override
                        public void onLoading() {
                            // TODO Auto-generated method stub
                            Log.e("流程聊天","");
                        }

                        @Override
                        public void onFail(MXError arg0) {
                            // TODO Auto-generated method stub
                           Log.e("流程聊天",arg0.getMessage());
                        }

                        @Override
                        public void onSuccess() {
                            // TODO Auto-generated method stub
                            Log.e("流程聊天","");
                        }
                    });
                }

            }
        }

        class ViewHolder {
            ImageView img_tip;
            TextView tv_timeStamp;
            TextView tv_stepname;
            TextView tv_content_userName;
            ImageView img_people;
            TextView tv_actionname;
            TextView tv_comments;
            ImageView iv_buttom_line;
            ImageView tv_line_up;
            ImageView tv_line_buttom;
            OnClickListener listener = new OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            };
        }
    }

    @Override
    public void onSuccess(int statusCode, Object result) {
//        if (result != null && result instanceof FlowProcessInfo) {
//            mFlowProcessInfo = (FlowProcessInfo) result;
//            if (mFlowProcessInfo.getResult() != null) {
//                mAdapter.setData(mFlowProcessInfo.getResult(), PULLDOWN_TOREFRESH);
//                mAdapter.notifyDataSetChanged();
//            }
//        }
//        if (getActivity() != null) {
//            Stepdes lastItem = new Stepdes();
//            lastItem.UserID = ((WorkFlowFormDetalActivity) getActivity()).mDocResultInfo.getResult().getCurrentUserId();
//
//            lastItem.Actiontime = "当前";
//            lastItem.StepName = ((WorkFlowFormDetalActivity) getActivity()).mDocResultInfo.getResult().getCurrentNodeName();
//            lastItem.userName = ((WorkFlowFormDetalActivity) getActivity()).mDocResultInfo.getResult().getCurrentUsername();
//            lastItem.Action = "";
//            lastItem.LoginName = ((WorkFlowFormDetalActivity) getActivity()).mDocResultInfo.getResult().getCurrentUserId();
//            mAdapter.arrayList.add(lastItem);
//            mAdapter.notifyDataSetChanged();
//        }
//        hideLoadingView();
//        netWorkManager.logFunactionFinsh(getActivity(), this, "functionFlowFail", LogManagerEnum.APP_DOC_FLOW.functionCode, mFlowProcessInfo.getMessage(), INetWorkManager.State.SUCCESS);
    }

    @Override
    public void onFail(int taskType, int statusCode, String errorMsg,
                       Object result) {
//        netWorkManager.logFunactionFinsh(getActivity(), this, "functionFlowFail", LogManagerEnum.APP_DOC_FLOW.functionCode, errorMsg, INetWorkManager.State.FAIL);
//        if (result != null) {
//            ToastInfo toast = ToastInfo.getInstance(getActivity());
//            toast.setView(mInflater, R.drawable.prompt_error, result.toString());
//            toast.show(Toast.LENGTH_SHORT);
//        }
//
//        if (mAdapter == null || mAdapter.getCount() == 0) {
//            hideLoadingShowError();
//        }
//        if (mPullToRefreshListView != null)
//            mPullToRefreshListView.onRefreshComplete();
//		((EmptyLayout)getRootView()).showError();
    }


}
