package com.htmitech.emportal.ui.detail;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.htmitech.emportal.ui.widget.PhoneClickText;
import com.htmitech.commonx.pulltorefresh.library.PullToRefreshBase;
import com.htmitech.commonx.pulltorefresh.library.PullToRefreshBase.Mode;
import com.htmitech.commonx.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.htmitech.commonx.pulltorefresh.library.PullToRefreshListView;
import com.htmitech.emportal.HtmitechApplication;
import com.htmitech.emportal.R;
import com.htmitech.emportal.base.IBaseCallback;
import htmitech.com.componentlibrary.base.MyBaseFragment;
import com.htmitech.emportal.common.AppPreferenceHelper;
import com.htmitech.emportal.entity.DocInfoParameters;
import com.htmitech.emportal.entity.DocResultInfo;
import com.htmitech.emportal.entity.FlowProcessInfo;
import com.htmitech.emportal.entity.OAConText;
import com.htmitech.emportal.entity.Stepdes;
import com.htmitech.emportal.ui.detail.model.DocInfoModel;
import com.htmitech.emportal.ui.widget.LoadingView;
import com.htmitech.emportal.ui.widget.ToastInfo;
import com.htmitech.proxy.dao.AppliationCenterDao;
import com.htmitech.proxy.interfaces.INetWorkManager;
import com.htmitech.proxy.myenum.LogManagerEnum;
import com.htmitech.proxy.util.LogManagerProxy;
import com.htmitech.proxy.util.NetWorkManager;
import com.minxing.kit.api.MXAPI;
import com.minxing.kit.api.bean.MXError;
import com.minxing.kit.api.callback.MXApiCallback;

import java.util.ArrayList;

import htmitech.com.componentlibrary.content.ConcreteLogin;
import htmitech.com.componentlibrary.listener.ObserverCallBackType;

import static android.R.attr.delay;

/**
 * 流程
 *
 * @author tenggang
 */
public class FlowFragment extends MyBaseFragment implements IBaseCallback ,ObserverCallBackType {
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
    private DocInfoModel mDocInfoModel;

    private INetWorkManager netWorkManager ;


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
        mDocInfoModel = new DocInfoModel(this);
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
                    public void onPullUpToRefresh(
                            PullToRefreshBase<ListView> refreshView) {
                    }
                });

    }

    private void pullDownToRefresh() {
        long time = AppPreferenceHelper.getInstance(
                HtmitechApplication.instance()).getLong(
                this.getClass().getName(), System.currentTimeMillis());
        String label = "上次更新:";
        try{
            label = "上次更新:"
                    + DateUtils.formatDateTime(getActivity(), time,
                    DateUtils.FORMAT_SHOW_TIME
                            | DateUtils.FORMAT_ABBREV_ALL);
        }catch (Exception e){

        }

        mPullToRefreshListView.getLoadingLayoutProxy().setLastUpdatedLabel(
                label);


        DocResultInfo mDocResultInfo = ((DetailActivity) getActivity()).mDocResultInfo;
        if (mDocResultInfo != null && mDocResultInfo.getResult() != null) {
            // 发起网络请求，获取所有待办列表
            DocInfoParameters mDocInfoParameters = new DocInfoParameters();
            mDocInfoParameters.context = OAConText.getInstance(HtmitechApplication
                    .instance());
            mDocInfoParameters.DocId = mDocResultInfo.getResult().getDocID(); //"HZ286f024cf9144b014d22ae386f495e";
            mDocInfoParameters.DocType = ((DetailActivity) getActivity()).getDocType();
            mDocInfoParameters.Kind = ((DetailActivity) getActivity()).getDocKind(); //2015-08-11
            mDocInfoParameters.app_id=((DetailActivity) getActivity()).app_id;
            mDocInfoModel.getDataFromServerByType(DocInfoModel.TYPE_GET_FLOW,
                    mDocInfoParameters);
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
        if(requestName.equals("functionFlow")){
            pullDownToRefresh();
        }
    }

    @Override
    public void fail(String exceptionMessage, int type, String requestName) {

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
            if (stepName.equals("结束")) {
                if (entity.getAction().equals("暂存") || entity.getAction().equals("已阅")||entity.getAction().equals("保存")) {
                    holder.tv_stepname.setText("结束");
                    holder.tv_content_userName.setText("");
                    holder.tv_actionname.setText(entity.getAction());
                    holder.tv_comments.setText("");
                    if (entity.getOAUserName() != null && !entity.getOAUserName().contains(";")) {
                        holder.tv_content_userName.setText("");
                        SpannableString spannableString = new SpannableString(entity.getOAUserName());
//                        ClickableSpan clickttt = new PhoneClickText(getActivity(), entity.getLoginName(), "Flow", new FlowPeopleOnclick());
                        ClickableSpan clickttt = new PhoneClickText(getActivity(), new AppliationCenterDao(getActivity()).getLoginName(entity.getUserID()), "Flow", new FlowPeopleOnclick(entity.getUserID()),entity.getUserID());
                        spannableString.setSpan(clickttt, 0, entity.getOAUserName().length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                        holder.tv_content_userName.append(spannableString);
                        holder.tv_content_userName.setMovementMethod(LinkMovementMethod.getInstance());
                    } else if (entity.getOAUserName() != null && entity.getOAUserName().contains(";")) {
                        String[] arrayName = entity.getOAUserName().split(";");
                        String[] arrayUserId = entity.getUserID().split(";");
                        String nameString = "";
                        holder.tv_content_userName.setText("");
                        for (int i = 0; i < arrayName.length; i++) {
                            SpannableString spannableString = new SpannableString(arrayName[i] + (i != arrayName.length - 1 ? "," : ""));
//                            ClickableSpan clickttt = new PhoneClickText(getActivity(), arrayUserId[i], "Flow", new FlowPeopleOnclick());
                            ClickableSpan clickttt = new PhoneClickText(getActivity(), new AppliationCenterDao(getActivity()).getLoginName(arrayUserId[i]), "Flow", new FlowPeopleOnclick(arrayUserId[i]),arrayUserId[i]);
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
                    if (entity.getOAUserName() != null && !entity.getOAUserName().contains(";")) {
                        holder.tv_content_userName.setText("");
                        SpannableString spannableString = new SpannableString(entity.getOAUserName());
//                        ClickableSpan clickttt = new PhoneClickText(getActivity(), entity.getLoginName(), "Flow", new FlowPeopleOnclick());
                        ClickableSpan clickttt = new PhoneClickText(getActivity(), new AppliationCenterDao(getActivity()).getLoginName(entity.getUserID()), "Flow", new FlowPeopleOnclick(entity.getUserID()),entity.getUserID());
                        spannableString.setSpan(clickttt, 0, entity.getOAUserName().length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                        holder.tv_content_userName.append(spannableString);
                        holder.tv_content_userName.setMovementMethod(LinkMovementMethod.getInstance());
                    } else if (entity.getOAUserName() != null && entity.getOAUserName().contains(";")) {
                        String[] arrayName = entity.getOAUserName().split(";");
                        String[] arrayUserId = entity.getUserID().split(";");
                        String nameString = "";
                        holder.tv_content_userName.setText("");
                        for (int i = 0; i < arrayName.length; i++) {
                            SpannableString spannableString = new SpannableString(arrayName[i] + (i != arrayName.length - 1 ? "," : ""));
//                            ClickableSpan clickttt = new PhoneClickText(getActivity(), arrayUserId[i], "Flow", new FlowPeopleOnclick());
                            ClickableSpan clickttt = new PhoneClickText(getActivity(), new AppliationCenterDao(getActivity()).getLoginName(arrayUserId[i]), "Flow", new FlowPeopleOnclick(arrayUserId[i]),arrayUserId[i]);

                            spannableString.setSpan(clickttt, 0, arrayName[i].length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                            holder.tv_content_userName.append(spannableString);
                            holder.tv_content_userName.setMovementMethod(LinkMovementMethod.getInstance());
                        }
                    }
                }
            } else {
                if (entity.getOAUserName() != null && !entity.getOAUserName().contains(";")) {
                    holder.tv_stepname.setText(stepName + ":");
                    holder.tv_content_userName.setText("");
                    SpannableString spannableString = new SpannableString(entity.getOAUserName());
                    ClickableSpan clickttt = new PhoneClickText(getActivity(), new AppliationCenterDao(getActivity()).getLoginName(entity.getUserID()), "Flow", new FlowPeopleOnclick(entity.getUserID()),entity.getUserID());
                    spannableString.setSpan(clickttt, 0, entity.getOAUserName().length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    holder.tv_content_userName.append(spannableString);
                    holder.tv_content_userName.setMovementMethod(LinkMovementMethod.getInstance());

//                    holder.tv_content_userName.setText(entity.getOAUserName());
                    holder.tv_actionname.setText(entity.getAction() + ":");
                    holder.tv_comments.setText(entity.getComments());

                } else if (entity.getOAUserName() != null && entity.getOAUserName().contains(";")) {
                    holder.tv_stepname.setText(stepName + ":");
                    String[] arrayName = null;
                    String[] arrayUserId = null;
                    if(entity != null && entity.getUserID() != null){
                        arrayName = entity.getOAUserName().split(";");
                        arrayUserId = entity.getUserID().split(";");
                    }
                    String nameString = "";
                    holder.tv_content_userName.setText("");
                    if(arrayName != null){
                        for (int i = 0; i < arrayName.length; i++) {
                            SpannableString spannableString = new SpannableString(arrayName[i] + (i != arrayName.length - 1 ? ",\n" : ""));
                            if(arrayUserId != null && arrayUserId.length > i){
//                                ClickableSpan clickttt = new PhoneClickText(getActivity(), arrayUserId[i], "Flow", new FlowPeopleOnclick());
                                ClickableSpan clickttt = new PhoneClickText(getActivity(), new AppliationCenterDao(getActivity()).getLoginName(arrayUserId[i]), "Flow", new FlowPeopleOnclick(arrayUserId[i]),arrayUserId[i]);
                                spannableString.setSpan(clickttt, 0, arrayName[i].length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                                holder.tv_content_userName.append(spannableString);
                                holder.tv_content_userName.setMovementMethod(LinkMovementMethod.getInstance());
                            }
                        }
                    }

                    holder.tv_actionname.setText(entity.getAction() + ":");
                    holder.tv_comments.setText(entity.getComments());
                }

//                holder.tv_content_userName.setOnClickListener(new FlowPeopleOnclick());
            }
            holder.img_tip.setImageResource(R.drawable.sign_oa_flow_line_previous_point);
            if (!stepName.equals("结束")) {
                holder.img_tip.setImageResource(R.drawable.sign_oa_flow_line_current);
                holder.tv_actionname.setText(entity.getAction());
            }else if(stepName.equals("结束")){
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
            return convertView;
        }

        public class FlowPeopleOnclick implements OnClickListener {
            String user_id ;

            public FlowPeopleOnclick(String user_id) {
                this.user_id = user_id;
            }

            @Override
            public void onClick(View v) {
                if(((DetailActivity)getActivity()).com_workflow_mobileconfig_IM_enabled == 0||!(new AppliationCenterDao(getActivity()).isEmiUser(user_id))){
                    return;
                }
                String UserID = v.getTag().toString().split(":")[1];
                ConcreteLogin mCpmcreteLogin = ConcreteLogin.getInstance();
                mCpmcreteLogin.chatMX(getActivity(),UserID);
//                if (UserID.equalsIgnoreCase("admin")) {
////                    Toast.makeText(getActivity(), "不能发起对admin的聊天",
////                            Toast.LENGTH_SHORT).show();
//                    return;
//                } else if (UserID.equalsIgnoreCase(MXAPI.getInstance(getActivity()).currentUser().getLoginName())) {
////                    Toast.makeText(getActivity(), "不能发起对自己的聊天",
////                            Toast.LENGTH_SHORT).show();
//                    return;
//                } else {
//                    Log.d("FlowFragment", "发起聊天，对：" + UserID);
//                    String[] loginNames = new String[]{UserID};
//                    // TODO Auto-generated method stub
//                    MXAPI.getInstance(getActivity()).chat(loginNames, new MXApiCallback() {
//                        @Override
//                        public void onLoading() {
//                            // TODO Auto-generated method stub
//
//                        }
//
//                        @Override
//                        public void onFail(MXError arg0) {
//                            // TODO Auto-generated method stub
//
//                        }
//
//                        @Override
//                        public void onSuccess() {
//                            // TODO Auto-generated method stub
//
//                        }
//                    });
//                }

            }
        }

        class ViewHolder {
            ImageView img_tip;
            TextView tv_timeStamp;
            TextView tv_stepname;
            TextView tv_content_userName;
            TextView tv_actionname;
            TextView tv_comments;
            ImageView iv_buttom_line;
            ImageView tv_line_up;
            ImageView tv_line_buttom;
            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            };
        }
    }

    @Override
    public void onSuccess(int statusCode, Object result) {
        if (result != null && result instanceof FlowProcessInfo) {
            mFlowProcessInfo = (FlowProcessInfo) result;
            if (mFlowProcessInfo.getResult() != null) {
                mAdapter.setData(mFlowProcessInfo.getResult().getStepdes(),
                        PULLDOWN_TOREFRESH);
                mAdapter.notifyDataSetChanged();
            }
        }
        if (getActivity() != null) {
            Stepdes lastItem = new Stepdes();
            lastItem.UserID = ((DetailActivity) getActivity()).mDocResultInfo.getResult().getCurrentUserId();

            lastItem.Actiontime = "当前";
            lastItem.StepName = ((DetailActivity) getActivity()).mDocResultInfo.getResult().getCurrentNodeName();
            lastItem.OAUserName = ((DetailActivity) getActivity()).mDocResultInfo.getResult().getCurrentUsername();
            lastItem.Action = "";
            lastItem.LoginName = ((DetailActivity) getActivity()).mDocResultInfo.getResult().getCurrentAuthorId();
            mAdapter.arrayList.add(lastItem);
            mAdapter.notifyDataSetChanged();
        }
        hideLoadingView();
        netWorkManager.logFunactionFinsh(getActivity(),this,"functionFlowFail",LogManagerEnum.APP_DOC_FLOW.functionCode,mFlowProcessInfo.getMessage().getStatusMessage(), INetWorkManager.State.SUCCESS);
    }

    @Override
    public void onFail(int taskType, int statusCode, String errorMsg,
                       Object result) {
        netWorkManager.logFunactionFinsh(getActivity(),this,"functionFlowFail",LogManagerEnum.APP_DOC_FLOW.functionCode,errorMsg, INetWorkManager.State.FAIL);
        if (result != null) {
            ToastInfo toast = ToastInfo.getInstance(getActivity());
            toast.setView(mInflater, R.drawable.prompt_error, result.toString());
            toast.show(Toast.LENGTH_SHORT);
        }

        if (mAdapter == null || mAdapter.getCount() == 0) {
            hideLoadingShowError();
        }
        if (mPullToRefreshListView != null)
            mPullToRefreshListView.onRefreshComplete();
//		((EmptyLayout)getRootView()).showError();
    }


}
