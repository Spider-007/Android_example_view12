package com.htmitech.emportal.ui.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.htmitech.MyView.EmptyLayout;
import com.htmitech.api.BookInit;
import com.htmitech.commonx.util.DeviceUtils;
import com.htmitech.emportal.HtmitechApplication;
import com.htmitech.emportal.R;
import com.htmitech.emportal.base.IBaseCallback;
import com.htmitech.emportal.db.AuthorInfoDAOImpl;
import com.htmitech.emportal.entity.ActionInfo;
import com.htmitech.emportal.entity.AuthorInfo;
import com.htmitech.emportal.entity.DoActionParameter;
import com.htmitech.emportal.entity.DoActionResultInfo;
import com.htmitech.emportal.entity.DocInfoParameters;
import com.htmitech.emportal.entity.DocResultInfo;
import com.htmitech.emportal.entity.EditField;
import com.htmitech.emportal.entity.EditFieldList;
import com.htmitech.emportal.entity.OAConText;
import com.htmitech.emportal.entity.RouteInfo;
import com.htmitech.emportal.entity.StartDocFlowParameter;
import com.htmitech.emportal.ui.ActivityResultConstant;
import com.htmitech.emportal.ui.detail.AdapterFragmentForCollection.ChildType;
import com.htmitech.emportal.ui.detail.model.DocInfoModel;
import com.htmitech.emportal.ui.pop.FunctionPopupWindow;
import com.htmitech.emportal.ui.widget.BaseDialog;
import com.htmitech.emportal.ui.widget.DialogCancelListener;
import com.htmitech.emportal.ui.widget.DialogClearListener;
import com.htmitech.emportal.ui.widget.DialogConfirmListener;
import com.htmitech.emportal.ui.widget.DialogSelectAllUserListener;
import com.htmitech.emportal.ui.widget.LoadingView;
import com.htmitech.emportal.ui.widget.MainViewPager;
import com.htmitech.emportal.ui.widget.MyAlertDialogFragment;
import com.htmitech.emportal.ui.widget.MyNextCodeDialog;
import com.htmitech.emportal.ui.widget.MyNextPersonDialog;
import com.htmitech.emportal.ui.widget.NewTopTabIndicator;
import com.htmitech.emportal.ui.widget.ToastInfo;
import com.htmitech.emportal.ui.widget.flatingactionButton.AddFloatingActionButton;
import com.htmitech.emportal.ui.widget.flatingactionButton.FloatingActionButton;
import com.htmitech.myEnum.BottomButtonSlyteEnum;
import com.htmitech.proxy.interfaces.INetWorkManager;
import com.htmitech.proxy.myenum.LogManagerEnum;
import com.htmitech.proxy.util.LogManagerProxy;
import com.htmitech.proxy.util.NetWorkManager;

import java.util.ArrayList;
import java.util.List;

import cn.feng.skin.manager.base.BaseFragmentActivity;
import htmitech.com.componentlibrary.listener.ObserverCallBackType;

//import com.htmitech.emportal.ui.widget.MyNextRouteDialog;

/***
 * @author gulbel 发起流程详情页面
 */
public class StartDetailActivity extends BaseFragmentActivity implements
        OnClickListener, IBaseCallback, View.OnTouchListener, CallBackLayout, ObserverCallBackType {

    private MainViewPager mViewPager_mycollection;
    private AdapterFragmentForCollection mAdapter;
    private NewTopTabIndicator mMyTopTabIndicator;
    private DocInfoModel mDocInfoModel;
    public DocResultInfo mDocResultInfo;
    private LoadingView mLoadingView = null;

    private AddFloatingActionButton menuMultipleActions = null;

    private String mDocAttachmentID = null;

    private MyNextCodeDialog nextcodeDialog; // 单选路由
    //	private MyNextRouteDialog nextrouteDialog; // 多选路由
    private MyNextPersonDialog nextpersonDialog;
    private boolean IsMultiSelectUser;
    private boolean IsMultiSelectRoute;
    String mCurrentNodeName ;

    private RouteInfo hasSelectedRouteInfo; // add by gulbel 2015-08-20

    private DoActionResultInfo mDoActionResultInfo;
    private DoActionParameter mDoActionParameter;

    private String comment; // 在流程中显示的意见，以用户最后在表单上输入的意见为准

    private String currentDocID; // 发起后返回在docID

    private String currentDocKind; // 发起后返回在docID

    private MyAlertDialogFragment mNewFragment = null;
    private DetailActivityLayout mDetailActivityLayout;
    private EmptyLayout mEmptyLayout;
    private String app_id;
    private String flowid ;
    public void setComment(String comment) {
        this.comment = comment;
    }
    private LinearLayout layout_buttom;
    private FunctionPopupWindow mFunctionPopupWindow;
    private BottomButtonSlyteEnum mBottomButtonSlyteEnum;
    private HorizontalScrollView hs_scrollview;
    TextView titDoc;
    private StartDocFlowParameter mDocInfoParameters;

    private INetWorkManager netWorkManager;

    protected int getLayoutById() {
        return R.layout.activity_detail;
    }

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        mDetailActivityLayout = (DetailActivityLayout) LayoutInflater
                .from(this).inflate(R.layout.activity_detail, null);
        super.onCreate(arg0);
        setContentView(mDetailActivityLayout);
        Intent intent = getIntent();
        app_id = intent.getStringExtra("app_id");
        flowid = intent.getStringExtra("com_workflow_plugin_startor_paramter");
        mEmptyLayout = (EmptyLayout) mDetailActivityLayout.findViewById(R.id.emptyLayout);
        mEmptyLayout.setErrorButtonClickListener(this);
        mBottomButtonSlyteEnum = BookInit.getInstance().getmBottomButtonEnum();
        initView();

    }

    /**
     * 初始化UI
     */
    protected void initView() {

        layout_buttom = (LinearLayout) findViewById(R.id.layout_buttom);
        hs_scrollview = (HorizontalScrollView) findViewById(R.id.hs_scrollview);
        EditFieldList mustFieldList = EditFieldList.getInstance();
        mustFieldList.Clear();
        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels - 50;
        View titleBar = findViewById(R.id.layout_detail_titlebar);
        titleBar.findViewById(R.id.imgview_titlebar_back).setOnClickListener(
                this);
        // ((TextView) titleBar.findViewById(R.id.textview_titlebar_title))
        // .setText("关于*****的");

        mLoadingView = (LoadingView) findViewById(R.id.loadingview_detail);


        titDoc =  ((TextView) titleBar.findViewById(R.id.textview_titlebar_title));
        netWorkManager = LogManagerProxy.getProxyInstance(NetWorkManager.class);
        showLoadingView();
        // 发起网络请求，获取发起需要的表单
        mDocInfoModel = new DocInfoModel(this);
        mDocInfoParameters = new StartDocFlowParameter();
        mDocInfoParameters.context = OAConText.getInstance(HtmitechApplication
                .instance());
        mDocInfoParameters.flowid = flowid;
        mDocInfoParameters.app_id=app_id;
        mDocInfoParameters.DocType = getDocType();
        netWorkManager.logFunactionStart(StartDetailActivity.this, StartDetailActivity.this, "functionStart", LogManagerEnum.STARTDOC_FLOW.getFunctionCode());

//        mDocInfoModel.getDataFromServerByType(
//                DocInfoModel.TYPE_START_DETAILTASK, mDocInfoParameters);
    }

    public String getDocId() {
        return currentDocID;
    }

    public String getDocKind() {
        return currentDocKind;
    }

    public String getDocType() {
        String docType = getIntent().getStringExtra("com_workflow_plugin_startor_paramter");
        return docType;
    }

    public String getMDocAttachmentID() {
        return mDocAttachmentID;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgview_titlebar_back:
                exit();
                break;
            case R.id.function:
                if (!isTuoZhuai) {
                    if (!mFunctionPopupWindow.isShowing()) {
//                        menuMultipleActions.startAnimation(animation);
                        menuMultipleActions.setImageDrawable(getResources().getDrawable(R.drawable.btn_operation_homelabel_on));
                        mFunctionPopupWindow = new FunctionPopupWindow(this,
                                new MenuOnClickListener(), mDocResultInfo.getResult()
                                .getListActionInfo().size());
                        mFunctionPopupWindow.initArcMenu(mDocResultInfo.getResult()
                                .getListActionInfo());
                        popupWidth = mFunctionPopupWindow.mMenuView.getMeasuredWidth();
                        popupWidth = DeviceUtils.dip2px(this, 55) + popupWidth;

                        popupHeight = mFunctionPopupWindow.getHeight();
                        int[] location = new int[2];
                        v.getLocationOnScreen(location);
                        // 显示窗口
                        // mFunctionPopupWindow.showAtLocation(v,
                        // Gravity.NO_GRAVITY,
                        // (location[0] + v.getWidth() / 2) - popupWidth
                        // / 2, location[1] - popupHeight); //
                        // 设置layout在PopupWindow中显示的位置


                        mFunctionPopupWindow.showAtLocation(v, Gravity.NO_GRAVITY,
                                (location[0] - popupWidth), location[1]
                                        - popupHeight);
                        mFunctionPopupWindow.update();

                    } else {
                        menuMultipleActions.setImageDrawable(getResources().getDrawable(R.drawable.btn_operation_homelabel_off));
                        // function.setBackgroundResource(R.drawable.zt_htmitech_shezhi_1);
                        mFunctionPopupWindow.dismiss();
                    }
                }
                break;
        }
    }

    int popupHeight;
    int popupWidth;

    /**
     * 退出
     **/
    public void exit() {
        finish();
    }

    private void handle_doAction_hasAuthor(Integer[] indexArr,
                                           AuthorInfo[] AuthorInfoTemp) {
        // 假定通过界面选择了第一个人
        StringBuffer authorId = new StringBuffer();
        if (AuthorInfoTemp != null) {
            for (int i = 0; i < indexArr.length; i++) {
                AuthorInfo selectUser = AuthorInfoTemp[indexArr[i].intValue()];
                Toast.makeText(StartDetailActivity.this,
                        "将提交给：" + selectUser.getUserName(), Toast.LENGTH_SHORT)
                        .show();
                authorId.append(selectUser.getUserID());
            }
        }

        // 再次提交
        DocInfoModel mDocInfoModel = new DocInfoModel(StartDetailActivity.this);
        if (hasSelectedRouteInfo != null) {
            mDoActionParameter.NextNodeId = hasSelectedRouteInfo.getRouteID(); // 需要选路径时，传入用户选择的路由。再次提交服务器。
        }
        mDoActionParameter.SelectAuthorID = authorId.toString(); // 需要选路径时，传入用户选择的路由。再次提交服务器。
        mDoActionParameter.DocType = getDocType();// 恢复flowid

        mDoActionParameter.context = OAConText.getInstance(HtmitechApplication
                .instance());
        mDoActionParameter.DocId = getDocId();
        mDoActionParameter.DocType = getDocType();
        mDoActionParameter.Kind = getDocKind(); // 2015-08-11
        mDoActionParameter.app_id=app_id;
        mDoActionParameter.FlowId = mDocResultInfo.getResult().getFlowId();
        mDoActionParameter.FlowName = mDocResultInfo.getResult().getFlowName();
        mDoActionParameter.CurrentNodeid = mDocResultInfo.getResult()
                .getCurrentNodeID();
        mDoActionParameter.CurrentTrackid = mDocResultInfo.getResult()
                .getCurrentTrackId();
        netWorkManager.logFunactionStart(StartDetailActivity.this, StartDetailActivity.this, "functionDetail", LogManagerEnum.DODO_ACTION_NATIVE.getFunctionCode());

//        mDocInfoModel.getDataFromServerByType(DocInfoModel.TYPE_DOACTION_TASK_START,
//                mDoActionParameter);

    }

    private void handle_doAction_hasRoute(Integer[] indexArr) {
        // 假定选择了最后一个路由 ----测试接口代码
        StringBuffer authorId = new StringBuffer();
        for (int i = 0; i < indexArr.length; i++) {
            RouteInfo selectRouteInfo = mDoActionResultInfo.getResult()
                    .getListRouteInfo()[indexArr[i].intValue()];
            authorId.append(selectRouteInfo.getRouteID());
        }
        // 再次提交
        DocInfoModel mDocInfoModel = new DocInfoModel(StartDetailActivity.this);
        mDoActionParameter.NextNodeId = authorId.toString(); // 需要选路径时，传入用户选择的路由。再次提交服务器。
        // mDoActionParameter.SelectAuthorID = null; //
        // 需要选路径时，传入用户选择的路由。再次提交服务器。
        mDoActionParameter.DocType = getDocType();// 恢复flowid

        mDoActionParameter.context = OAConText.getInstance(HtmitechApplication
                .instance());
        mDoActionParameter.DocId = getDocId();
        mDoActionParameter.DocType = getDocType();
        mDoActionParameter.Kind = getDocKind(); // 2015-08-11
        mDoActionParameter.FlowId = mDocResultInfo.getResult().getFlowId();
        mDoActionParameter.FlowName = mDocResultInfo.getResult().getFlowId();
        mDoActionParameter.CurrentNodeid = mDocResultInfo.getResult()
                .getCurrentNodeID();
        mDoActionParameter.CurrentTrackid = mDocResultInfo.getResult()
                .getCurrentTrackId();
        netWorkManager.logFunactionStart(StartDetailActivity.this, StartDetailActivity.this, "functionDetail", LogManagerEnum.DODO_ACTION_NATIVE.getFunctionCode());

//        mDocInfoModel.getDataFromServerByType(DocInfoModel.TYPE_DOACTION_TASK_START,
//                mDoActionParameter);
    }

    @Override
    public void onSuccess(int requestTypeId, Object result) {

        if (requestTypeId == DocInfoModel.TYPE_START_DETAILTASK) {

            if (result != null && result instanceof DocResultInfo) {
                mDocResultInfo = (DocResultInfo) result;
                netWorkManager.logFunactionFinsh(StartDetailActivity.this, StartDetailActivity.this, "functionStartSuccess", LogManagerEnum.STARTDOC_FLOW.getFunctionCode(), "success", INetWorkManager.State.SUCCESS);
                mViewPager_mycollection = (MainViewPager) findViewById(R.id.viewPager_qarank);
                FragmentManager fm = getSupportFragmentManager();
                mViewPager_mycollection.setOffscreenPageLimit(4);
                mMyTopTabIndicator = (NewTopTabIndicator) this
                        .findViewById(R.id.topTabIndicator_detail);

                mDocResultInfo.getResult().setDocType(getDocType());
               mCurrentNodeName =  mDocResultInfo.getResult().getCurrentNodeName();
                currentDocID = mDocResultInfo.getResult().getDocID();
                currentDocKind = mDocResultInfo.getResult().getKind(); // 2015-08-11
                ArrayList<ChildType> list = new ArrayList<AdapterFragmentForCollection.ChildType>();
                List<String> listStr = new ArrayList<String>();
                listStr.add("新建表单");
                list.add(ChildType.TAB_START_FORM);
                String[] arrayTopTabIndicator = new String[listStr.size()];
                listStr.toArray(arrayTopTabIndicator);
                titDoc.setText(mCurrentNodeName);
                if(arrayTopTabIndicator.length != 1) {
                    mMyTopTabIndicator.setCommonData2(mViewPager_mycollection,
                            arrayTopTabIndicator, R.color.ht_hred_title,
                            R.color.color_title);
                }
                mAdapter = new AdapterFragmentForCollection(HtmitechApplication
                        .instance().getApplicationContext(), fm, list);
                if ((mViewPager_mycollection != null) && (mAdapter != null))
                    mViewPager_mycollection.setAdapter(mAdapter);
                if (mDocResultInfo.getResult().getListActionInfo() == null
                        || mDocResultInfo.getResult().getListActionInfo()
                        .size() == 0) {
                    menuMultipleActions = (AddFloatingActionButton) findViewById(R.id.function);

                    menuMultipleActions.setSize(FloatingActionButton.SIZE_NORMAL);
                    menuMultipleActions.setOnClickListener(this);
                    menuMultipleActions.setOnTouchListener(this);

                } else {
                    menuMultipleActions = (AddFloatingActionButton) findViewById(R.id.function);

                    menuMultipleActions.setSize(FloatingActionButton.SIZE_NORMAL);
                    menuMultipleActions.setOnClickListener(this);
                    menuMultipleActions.setOnTouchListener(this);
//					initArcMenu(menuMultipleActions, mDocResultInfo.getResult()
//							.getListActionInfo());
                    mFunctionPopupWindow = new FunctionPopupWindow(this, new MenuOnClickListener(), mDocResultInfo.getResult()
                            .getListActionInfo().size());
                    mFunctionPopupWindow.initArcMenu(mDocResultInfo.getResult().getListActionInfo());
                    menuMultipleActions.setVisibility(View.VISIBLE);
                }

            }else {
                netWorkManager.logFunactionFinsh(StartDetailActivity.this, StartDetailActivity.this, "functionStartFail", LogManagerEnum.STARTDOC_FLOW.getFunctionCode(), "fail", INetWorkManager.State.FAIL);

            }

        } else if (requestTypeId == DocInfoModel.TYPE_DOACTION_TASK_START) {
            // 办理成功返回
            if (result != null && result instanceof DoActionResultInfo) {
                mDoActionResultInfo = (DoActionResultInfo) result;

                if (mDoActionResultInfo.getResult().getResultCode().equals("2")) {// 操作：提交--选择下一节点
                    netWorkManager.logFunactionFinsh(StartDetailActivity.this, StartDetailActivity.this, "functionDetailSuccess", LogManagerEnum.DODO_ACTION_NATIVE.getFunctionCode(), "success", INetWorkManager.State.SUCCESS);

                    Toast.makeText(StartDetailActivity.this, "操作：提交--选择下路由节点！",
                            Toast.LENGTH_SHORT).show();

                    String title = mDoActionResultInfo.getResult()
                            .getResultInfo();
                    IsMultiSelectRoute = mDoActionResultInfo.getResult()
                            .isIsMultiSelectRoute();
                    mDocResultInfo.getResult().setDocType(getDocType());// 恢复flowid
                    if (!IsMultiSelectRoute) {
                        nextcodeDialog = new MyNextCodeDialog(
                                StartDetailActivity.this,
                                dialogConfirmOnclickListener,
                                dialogCancelOnclickListener, R.style.mydialog);
                        nextcodeDialog.show();
                        int sum = mDoActionResultInfo.getResult()
                                .getListRouteInfo().length;
                        for (int i = 0; i < sum; i++) {
                            RadioButton radioBtn = new RadioButton(
                                    StartDetailActivity.this);
                            radioBtn.setText(mDoActionResultInfo.getResult()
                                    .getListRouteInfo()[i].getRouteName());
                            radioBtn.setTextColor(getResources().getColor(
                                    R.color.color_ff555555));
                            nextcodeDialog.setViewValue(title, radioBtn);
                        }
                    }
                } else if (mDoActionResultInfo.getResult().getResultCode()
                        .equals("4")) {// 直接选人
                    netWorkManager.logFunactionFinsh(StartDetailActivity.this, StartDetailActivity.this, "functionDetailSuccess", LogManagerEnum.DODO_ACTION_NATIVE.getFunctionCode(), "success", INetWorkManager.State.SUCCESS);

                    String title = mDoActionResultInfo.getResult()
                            .getResultInfo();
                    IsMultiSelectUser = mDoActionResultInfo.getResult() // replace
                            .isIsMultiSelectUser();

                    hasSelectedRouteInfo = mDoActionResultInfo.getResult()
                            .getHasSelectedRoute();
                    if (hasSelectedRouteInfo != null) {
                        if (hasSelectedRouteInfo.getRouteName() != null) {
                            title += "--将提交到:"
                                    + hasSelectedRouteInfo.getRouteName();
                        } else if (hasSelectedRouteInfo.getRouteID() != null) {
                            title += "--将提交到:"
                                    + hasSelectedRouteInfo.getRouteID();
                        }
                    }

                    nextpersonDialog = new MyNextPersonDialog(this,
                            dialogConfirmOnclickListener,
                            dialogCancelOnclickListener,
                            dialogClearOnclickListener, R.style.mydialog);
                    nextpersonDialog.show();
                    nextpersonDialog.setViewValue(title, mDoActionResultInfo
                            .getResult().getListAuthorInfo(), IsMultiSelectUser, mDoActionResultInfo
                            .getResult().isIsFreeSelectUser());

                    if (mDoActionResultInfo.getResult().isIsFreeSelectUser()) {

                        final String title2 = title;
                        nextpersonDialog
                                .setOnSelectAllUserListener(new DialogSelectAllUserListener() {

                                    @Override
                                    public void onSelectAllUser() {
                                        // TODO Auto-generated method stub
                                        nextpersonDialog = new MyNextPersonDialog(
                                                StartDetailActivity.this,
                                                dialogConfirmOnclickListener,
                                                dialogCancelOnclickListener,
                                                dialogClearOnclickListener,
                                                R.style.mydialog);

                                        nextpersonDialog.show();
                                        nextpersonDialog
                                                .setSeletAllUserView(false);
                                        AuthorInfoDAOImpl authorDao = new AuthorInfoDAOImpl(
                                                StartDetailActivity.this);
                                        nextpersonDialog.setViewAllUserValue(
                                                title2,
                                                authorDao
                                                        .getAuthorForOaSelect(), IsMultiSelectUser);
                                    }
                                });

                    }
                } else if (mDoActionResultInfo.getResult().getResultCode()
                        .equals("9")) {
                    netWorkManager.logFunactionFinsh(StartDetailActivity.this, StartDetailActivity.this, "functionDetailFail", LogManagerEnum.DODO_ACTION_NATIVE.getFunctionCode(), "fail", INetWorkManager.State.FAIL);
                    mNewFragment = MyAlertDialogFragment.newInstance(
                            mDoActionResultInfo.getResult().getResultInfo(),
                            R.drawable.prompt_warn, null, confirmListener,
                            false);

                    mNewFragment.show(getSupportFragmentManager(), "dialog");
                } else if (mDoActionResultInfo.getResult().getResultCode()
                        .equals("0")) {// 办理成功
                    netWorkManager.logFunactionFinsh(StartDetailActivity.this, StartDetailActivity.this, "functionDetailSuccess", LogManagerEnum.DODO_ACTION_NATIVE.getFunctionCode(), "success", INetWorkManager.State.SUCCESS);

                    Toast.makeText(StartDetailActivity.this, "操作成功！",
                            Toast.LENGTH_SHORT).show();
                    setResult(ActivityResultConstant.DOACTION_RESULT_OK,
                            getIntent());
                    exit();
                } else {
                    mNewFragment = MyAlertDialogFragment.newInstance(
                            mDoActionResultInfo.getResult().getResultInfo(),
                            R.drawable.prompt_warn, null, confirmListener,
                            false);

                    mNewFragment.show(getSupportFragmentManager(), "dialog");
                    netWorkManager.logFunactionFinsh(StartDetailActivity.this, StartDetailActivity.this, "functionDetailFail", LogManagerEnum.DODO_ACTION_NATIVE.getFunctionCode(), "fail", INetWorkManager.State.FAIL);

                }
            }
        }
        switch (mBottomButtonSlyteEnum) {
            case DRAG:
                layout_buttom.setVisibility(View.GONE);
                hs_scrollview.setVisibility(View.GONE);
                menuMultipleActions.setVisibility(View.VISIBLE);
                break;
            case BOTTOM:
                layout_buttom.setVisibility(View.VISIBLE);
                hs_scrollview.setVisibility(View.VISIBLE);
                menuMultipleActions.setVisibility(View.GONE);
                initArcMenu(mDocResultInfo.getResult()
                        .getListActionInfo());
                break;
        }
        hideLoadingView();
    }

    private DialogConfirmListener confirmListener = new DialogConfirmListener() {
        public void onConfirm(BaseDialog dialog) {
            mNewFragment.dismiss();
        }
    };

    public DialogConfirmListener dialogConfirmOnclickListener = new DialogConfirmListener() {
        public void onConfirm(BaseDialog dialog) {
            if (null != nextpersonDialog && nextpersonDialog.isShowing()) { // 选择人
                Integer[] indexArr = nextpersonDialog.getSelectIndexArr();
                if (null == indexArr || indexArr.length <= 0) {
                    ToastInfo toast = ToastInfo
                            .getInstance(StartDetailActivity.this);
                    toast.setText("没有选择办理人！！");
                    toast.show(Toast.LENGTH_SHORT);
                } else if (!IsMultiSelectUser && indexArr.length > 1) {
                    ToastInfo toast = ToastInfo
                            .getInstance(StartDetailActivity.this);
                    toast.setText("只能选择一个办理人！");
                    toast.show(Toast.LENGTH_SHORT);
                } else {
                    AuthorInfo[] AuthorInfoTemp = nextpersonDialog
                            .getNewVector();
                    handle_doAction_hasAuthor(indexArr, AuthorInfoTemp);
                    // handle_doAction_hasAuthor(indexArr);
                }
            } else if (null != nextcodeDialog && nextcodeDialog.isShowing()) { // 选节点---单选
                if (!IsMultiSelectRoute) {
                    int index = nextcodeDialog.getSelectIndex();
                    if (index < 0) { // 未选择
                        ToastInfo toast = ToastInfo
                                .getInstance(StartDetailActivity.this);
                        toast.setText("没有选择下一节点！！");
                        toast.show(Toast.LENGTH_SHORT);
                    } else {
                        handle_doAction_hasRoute(new Integer[]{index});
                    }
                    nextcodeDialog.dismiss();
                }

            } /*else if (null != nextrouteDialog && nextrouteDialog.isShowing()) {// 选节点--多选
                if (IsMultiSelectRoute) {
					Integer[] indexArr = nextrouteDialog.getSelectIndexArr();
					if (null == indexArr || indexArr.length <= 0) {
						ToastInfo toast = ToastInfo
								.getInstance(StartDetailActivity.this);
						toast.setText("没有选择路由节点！！");
						toast.show(Toast.LENGTH_SHORT);
					} else if (!IsMultiSelectRoute && indexArr.length > 1) {
						ToastInfo toast = ToastInfo
								.getInstance(StartDetailActivity.this);
						toast.setText("只能选择一个路由节点！");
						toast.show(Toast.LENGTH_SHORT);
					} else {
						handle_doAction_hasRoute(indexArr);
						// handler.sendEmptyMessage(OPERATION_SUBMIT_NEXTPEOPLE);
					}
				}
			}*/
        }
    };

    public DialogClearListener dialogClearOnclickListener = new DialogClearListener() {
        public void onClear(BaseDialog dialog) { // 清空选择
            if (null != nextpersonDialog && nextpersonDialog.isShowing())
                nextpersonDialog.clearSelect();
//			else if (null != nextrouteDialog && nextrouteDialog.isShowing()) { // 选节点--多选
//				nextrouteDialog.clearSelect();
//			}
        }

        @Override
        public void onClear() {
            // TODO Auto-generated method stub

        }
    };

    public DialogCancelListener dialogCancelOnclickListener = new DialogCancelListener() {
        public void onCancel(BaseDialog dialog) {
            if (null != nextpersonDialog && nextpersonDialog.isShowing()) { // 选择人
                nextpersonDialog.dismiss();
            } else if (null != nextcodeDialog && nextcodeDialog.isShowing()) { // 选节点--单选
                nextcodeDialog.dismiss();
            } /*else if (null != nextrouteDialog && nextrouteDialog.isShowing()) { // 选节点--多选
				nextrouteDialog.dismiss();
			}*/
        }
    };

    private void initArcMenu(List<ActionInfo> actionList) {
        final int itemCount = actionList.size();
        for (int i = 0; i < itemCount; i++) {

            FloatingActionButton item = new FloatingActionButton(this);

            if (actionList.get(i).getActionID().equalsIgnoreCase("submit")) // 提交
                // item.setColorNormalResId(R.color.pink);
                item.setIcon(R.drawable.btn_action_submit);
            else if (actionList.get(i).getActionID().equalsIgnoreCase("reject")) // 退回
                // item.setColorNormalResId(R.color.pink_pressed);
                item.setIcon(R.drawable.btn_action_return);
            else if (actionList.get(i).getActionID().equalsIgnoreCase("readed")) // 已读
                item.setIcon(R.drawable.btn_action_read);
                // item.setColorNormalResId(R.color.color_26ffffff);
            else if (actionList.get(i).getActionID()
                    .equalsIgnoreCase("addreader")) // 阅知
                item.setIcon(R.drawable.btn_action_yuezhi);
                // item.setColorNormalResId(R.color.color_33000000);
            else if (actionList.get(i).getActionID()
                    .equalsIgnoreCase("getback")) // 拿回
                item.setIcon(R.drawable.btn_action_takeback);
                // item.setColorNormalResId(R.color.color_44b2b2b2);
            else if (actionList.get(i).getActionID().equalsIgnoreCase("Share")) // 分享
                item.setIcon(R.drawable.btn_action_share);
            else
                item.setIcon(R.drawable.btn_action_save);
            // item.setColorNormalResId(R.color.color_55555555); //暂存
			/* item.setText(actionList.get(i).getActionName()); */
            item.setTitle(actionList.get(i).getActionName());
            item.setTag(actionList.get(i).getActionID());
            item.setColorNormalResId(R.color.mx_title_bar_color);
            item.setOnClickListener(new MenuOnClickListener());

            layout_buttom.addView(item);
        }
    }

    @Override
    public void success(String requestValue, int type, String requestName) {
        if (requestName.equals("functionDetail")) {
            mDocInfoModel.getDataFromServerByType(
                    DocInfoModel.TYPE_DOACTION_TASK_START, mDoActionParameter);

        }else if(requestName.equals("functionStart")){
            mDocInfoModel.getDataFromServerByType(
                    DocInfoModel.TYPE_START_DETAILTASK, mDocInfoParameters);
        }
    }

    @Override
    public void fail(String exceptionMessage, int type, String requestName) {
        if (requestName.equals("functionDetail")) {
            mDocInfoModel.getDataFromServerByType(
                    DocInfoModel.TYPE_DOACTION_TASK_START, mDoActionParameter);
        }else if(requestName.equals("functionStart")){
            mDocInfoModel.getDataFromServerByType(
                    DocInfoModel.TYPE_START_DETAILTASK, mDocInfoParameters);
        }
    }

    @Override
    public void notNetwork() {

    }

    @Override
    public void callbackMainUI(String successMessage) {

    }


    public class MenuOnClickListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            // 关按钮
//			menuMultipleActions.toggle();
            mFunctionPopupWindow.dismiss();
            menuMultipleActions.setImageDrawable(getResources().getDrawable(R.drawable.btn_operation_homelabel_off));
            // TODO Auto-generated method stub
            showLoadingView();
            // 1,必填字段判读
            // 必填字段判断
            EditFieldList mustFieldList = EditFieldList.getInstance();
            for (int i = 0; i < mustFieldList.getList().size(); i++) {
                if (mustFieldList.getList().get(i).getValue() == null
                        || mustFieldList.getList().get(i).getValue().equals("")) {
                    Toast.makeText(
                            StartDetailActivity.this,
                            "必填字段 "
                                    + mustFieldList.getList().get(i)
                                    .getNameDesc() + "尚未填写！",
                            Toast.LENGTH_SHORT).show();
                    hideLoadingView();
                    return;
                }
            }

            DocInfoModel mDocInfoModel = new DocInfoModel(
                    StartDetailActivity.this);
            mDoActionParameter = new DoActionParameter();
            mDoActionParameter.ActionName = v.getTag().toString();
            mDoActionParameter.context = OAConText
                    .getInstance(HtmitechApplication.instance());
            mDoActionParameter.DocId = getDocId();
            mDoActionParameter.DocType = getDocType();
            mDoActionParameter.Kind = getDocKind();// 2015-08-11

            mDoActionParameter.FlowId = mDocResultInfo.getResult().getFlowId();
            mDoActionParameter.FlowName = mDocResultInfo.getResult()
                    .getFlowName();
            mDoActionParameter.CurrentNodeid = mDocResultInfo.getResult()
                    .getCurrentNodeID();
            mDoActionParameter.CurrentTrackid = mDocResultInfo.getResult()
                    .getCurrentTrackId();

            if (mDocResultInfo.getResult().getEditFields() != null
                    && mDocResultInfo.getResult().getEditFields().size() > 0) {
                mDoActionParameter.EditFields = new EditField[mDocResultInfo
                        .getResult().getEditFields().size()];
                for (int j = 0; j < mDocResultInfo.getResult().getEditFields()
                        .size(); j++) {
                    mDoActionParameter.EditFields[j] = mDocResultInfo
                            .getResult().getEditFields().get(j);
                }
            }
            mDoActionParameter.NextNodeId = null; // 需要选路径时，传入用户选择的路由。再次提交服务器。
            mDoActionParameter.SelectAuthorID = null; // 需要选路径时，传入用户选择的路由。再次提交服务器。

            mDoActionParameter.Comments = comment;
            mDoActionParameter.CommentFieldName = "";
            mDoActionParameter.app_id=app_id;
            netWorkManager.logFunactionStart(StartDetailActivity.this, StartDetailActivity.this, "functionDetail", LogManagerEnum.DODO_ACTION_NATIVE.getFunctionCode());

//            mDocInfoModel.getDataFromServerByType(
//                    DocInfoModel.TYPE_DOACTION_TASK_START, mDoActionParameter);

        }

    }

    private void hideLoadingShowError() {
        mLoadingView.setVisibility(View.GONE);
        mLoadingView.stopLoading();
    }

    private void hideLoadingView() {
        mLoadingView.setVisibility(View.GONE);
        mLoadingView.stopLoading();
    }

    private void showLoadingView() {
        mLoadingView.setVisibility(View.VISIBLE);
        mLoadingView.startLoading();
    }

    public void onFail(int taskType, int statusCode, String errorMsg,
                       Object result) {
        if (result != null) {

            if (taskType == DocInfoModel.TYPE_GET_DETAILTASK) {
                mDocResultInfo = new DocResultInfo();
                netWorkManager.logFunactionFinsh(StartDetailActivity.this, StartDetailActivity.this, "functionStartFail", LogManagerEnum.STARTDOC_FLOW.getFunctionCode(), "fail", INetWorkManager.State.FAIL);
                try {
                    mDocResultInfo.parseJson(result.toString());
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if (mDocResultInfo.getResult() != null) {
                    Toast.makeText(
                            StartDetailActivity.this,
                            mDoActionResultInfo.getResult().getResultInfo()
                                    + "("
                                    + mDoActionResultInfo.getResult()
                                    .getResultCode() + ")",
                            Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(StartDetailActivity.this, "获取详情失败！",
                            Toast.LENGTH_SHORT).show();
            } else if (taskType == DocInfoModel.TYPE_DOACTION_TASK_START) {
                netWorkManager.logFunactionFinsh(StartDetailActivity.this, StartDetailActivity.this, "functionDetailFail", LogManagerEnum.DODO_ACTION_NATIVE.getFunctionCode(),"fail", INetWorkManager.State.FAIL);
                mDoActionResultInfo = new DoActionResultInfo();
                try {
                    mDoActionResultInfo.parseJson(result.toString());

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();

                }
                if (mDoActionResultInfo.getResult() != null) {

                    Toast.makeText(
                            StartDetailActivity.this,
                            mDoActionResultInfo.getResult().getResultInfo()
                                    + "("
                                    + mDoActionResultInfo.getResult()
                                    .getResultCode() + ")",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(StartDetailActivity.this, "操作失败！",
                            Toast.LENGTH_SHORT).show();
                                }
            } else {

                ToastInfo toast = ToastInfo.getInstance(this);
                toast.setView(getLayoutInflater(), R.drawable.prompt_error,
                        result.toString());
                toast.show(Toast.LENGTH_SHORT);
            }
        }

        hideLoadingView();
    }

    int screenWidth;
    int screenHeight;
    int lastX;
    int lastY;
    private boolean isTuoZhuai = false;
    private float x, y;
    private float ex, ey;
    private int left, top, right, bottom;
    private boolean action_move = false;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        // Toast.makeText(DraftTest.this, "λ�ã�"+x+","+y,
        // Toast.LENGTH_SHORT).show();
        ex = 0;
        ey = 0;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                x = event.getX();
                y = event.getY();
                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();
                isTuoZhuai = false;
                break;
            /**
             * layout(l,t,r,b) l Left position, relative to parent t Top position,
             * relative to parent r Right position, relative to parent b Bottom
             * position, relative to parent
             * */
            case MotionEvent.ACTION_MOVE:
                isTuoZhuai = true;
                action_move = true;
                int dx = (int) event.getRawX() - lastX;
                int dy = (int) event.getRawY() - lastY;
                left = v.getLeft() + dx;
                top = v.getTop() + dy;
                right = v.getRight() + dx;
                bottom = v.getBottom() + dy;
                if (left < 0) {
                    left = 0;
                    right = left + v.getWidth();
                }
                if (right > screenWidth) {
                    right = screenWidth;
                    left = right - v.getWidth();
                }
                if (top < 0) {
                    top = 0;
                    bottom = top + v.getHeight();
                }
                if (bottom > screenHeight) {
                    bottom = screenHeight;
                    top = bottom - v.getHeight();
                }
                v.layout(left, top, right, bottom);
                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();
                int[] location = new int[2];
                v.getLocationOnScreen(location);
                mFunctionPopupWindow.update(location[0] - popupWidth, location[1]
                        - popupHeight, -1, -1);
                break;
            case MotionEvent.ACTION_UP:
                ex = event.getX() - x;
                ey = event.getY() - y;
                if (Math.abs(ex) == 0 && Math.abs(ey) == 0) {
                    isTuoZhuai = false;
                } else {
                    isTuoZhuai = true;
                }
                break;
        }
        return false;
    }

    @Override
    public void callBackLayout() {
        // TODO Auto-generated method stub
        if (action_move) {
            menuMultipleActions.layout(left, top, right, bottom);
        }
    }
}
