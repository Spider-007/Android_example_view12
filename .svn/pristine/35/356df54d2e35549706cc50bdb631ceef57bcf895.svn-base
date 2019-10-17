package com.htmitech.htnativestartformplugin.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.htmitech.MyView.EmptyLayout;
import com.htmitech.api.BookInit;
import com.htmitech.app.widget.DrawableCenterTextView;

import com.htmitech.commonx.util.DeviceUtils;
import com.htmitech.commonx.util.NetworkUtil;
import com.htmitech.domain.SYS_Department;
import com.htmitech.domain.SYS_User;
import com.htmitech.edittext.SuperEditText;
import com.htmitech.emportal.HtmitechApplication;
import com.htmitech.emportal.R;
import com.htmitech.emportal.base.IBaseCallback;

import com.htmitech.emportal.entity.AuthorInfo;
import com.htmitech.emportal.entity.DoActionResultInfo;

import com.htmitech.emportal.entity.OAConText;

import com.htmitech.emportal.entity.RouteInfo;
import com.htmitech.emportal.entity.StartDocFlowParameterBuild;
import com.htmitech.emportal.ui.ActivityResultConstant;
import com.htmitech.emportal.ui.detail.CallBackLayout;
import com.htmitech.emportal.ui.detail.DetailActivityLayout;
import com.htmitech.emportal.ui.detail.model.DocInfoModel;

import com.htmitech.emportal.ui.widget.BaseDialog;
import com.htmitech.emportal.ui.widget.DialogCancelListener;
import com.htmitech.emportal.ui.widget.DialogClearListener;
import com.htmitech.emportal.ui.widget.DialogConfirmListener;
import com.htmitech.emportal.ui.widget.LoadingView;
import com.htmitech.emportal.ui.widget.MainViewPager;
import com.htmitech.emportal.ui.widget.MyAlertDialogFragment;
import com.htmitech.emportal.ui.widget.MyNextCodeDialog;
import com.htmitech.emportal.ui.widget.MyNextPersonDialog;
import com.htmitech.emportal.ui.widget.NewTopTabIndicator;
import com.htmitech.emportal.ui.widget.ToastInfo;
import com.htmitech.emportal.ui.widget.flatingactionButton.AddFloatingActionButton;
import com.htmitech.emportal.ui.widget.flatingactionButton.FloatingActionButton;

import com.htmitech.htcommonformplugin.dao.FormExtensionFilesDao;
import com.htmitech.htnativestartformplugin.entity.DoActionParameterStart;

import com.htmitech.htnativestartformplugin.fragment.StartFormFragment;
import com.htmitech.htworkflowformpluginnew.activity.MuliteRouteSelectPeopleActivity;
import com.htmitech.htworkflowformpluginnew.activity.WorkFlowFormDetalActivity;
import com.htmitech.htworkflowformpluginnew.adapter.WorkFlowForCollectionAdapter;

import com.htmitech.htworkflowformpluginnew.entity.SelectRouteInfo;
import com.htmitech.htworkflowformpluginnew.util.SystemUser2SYSUser;
import com.htmitech.htworkflowformpluginnew.weight.FunctionPopupWindow;
import com.htmitech.listener.CallCheckAllUserListener;

import htmitech.com.componentlibrary.entity.FormExtensionFiles;
import htmitech.com.componentlibrary.entity.PhotoModel;
import htmitech.com.componentlibrary.entity.bigfileentity.SaveBigFileEditFieldsList;
import htmitech.com.componentlibrary.entity.bigfileentity.SaveBigFileExtFields;
import htmitech.com.componentlibrary.listener.ObserverCallBackType;
import com.htmitech.myEnum.BottomButtonSlyteEnum;
import com.htmitech.myEnum.ChooseSystemBook;
import com.htmitech.myEnum.ChooseTreeHierarchy;
import com.htmitech.myEnum.ChooseWay;
import com.htmitech.myEnum.ChooseWayEnum;
import com.htmitech.proxy.interfaces.INetWorkManager;
import com.htmitech.proxy.myenum.LogManagerEnum;
import com.htmitech.proxy.util.LogManagerProxy;
import com.htmitech.proxy.util.NetWorkManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.feng.skin.manager.base.BaseFragmentActivity;
import htmitech.com.componentlibrary.ComboBox;
import htmitech.com.componentlibrary.entity.ActionInfo;
import htmitech.com.componentlibrary.entity.DocResultInfo;
import htmitech.com.componentlibrary.entity.EditField;
import htmitech.com.componentlibrary.entity.EditFieldList;
import htmitech.com.componentlibrary.entity.InfoTab;
import htmitech.com.componentlibrary.unit.ClickToast;
import htmitech.com.componentlibrary.unit.ServerUrlConstant;
import htmitech.entity.FilePickerMusic;
import htmitech.formConfig.AudioSelect4002;
import htmitech.formConfig.SelectPhoto6001_6002_6101_6102;

import com.htmitech.thread.AnsynHttpRequest;
import com.htmitech.thread.CHTTP;
import com.htmitech.thread.GetFinalRefreshTokenAfterRequestValue;
import com.htmitech_updown.updownloadmanagement.UploadBigFileFactory;

/***
 * @author gulbel 发起流程详情页面
 */
public class StartDetailActivity extends BaseFragmentActivity implements
        OnClickListener, IBaseCallback, View.OnTouchListener, CallBackLayout, ObserverCallBackType {

    private MainViewPager mViewPager_mycollection;
    private WorkFlowForCollectionAdapter mAdapter;
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
    String mCurrentNodeName;

    private RouteInfo hasSelectedRouteInfo; // add by gulbel 2015-08-20

    private DoActionResultInfo mDoActionResultInfo;
    private DoActionParameterStart mDoActionParameter;

    private String comment; // 在流程中显示的意见，以用户最后在表单上输入的意见为准

    private String currentDocID; // 发起后返回在docID

    private String currentDocKind; // 发起后返回在docID

    private MyAlertDialogFragment mNewFragment = null;
    //    private DetailActivityLayout mDetailActivityLayout;
    private EmptyLayout mEmptyLayout;
    private String app_id;
    public String app_version_id;//当前应用的版本
    public String flow_id = "";
    //新增
    public int isWaterSecurity; //是不是支持水印
    public int isShare;
    public int com_workflow_mobileconfig_IM_enabled;
    public String comeShare = "0";
    private DetailActivityLayout mDetailActivityLayout;
    private String doActionUrl = ServerUrlConstant.SERVER_JAVA_URL() + ServerUrlConstant.OA_DOAction_METHOD_JAVA;
    private String startDetailUrl = ServerUrlConstant.SERVER_JAVA_URL() + ServerUrlConstant.OA_STARTFLOW_METHOD_BUILD_JAVA;
    private String menuViewTag;

    public void setComment(String comment) {
        this.comment = comment;
    }

    private LinearLayout layout_buttom;
    private FunctionPopupWindow mFunctionPopupWindow;
    private BottomButtonSlyteEnum mBottomButtonSlyteEnum;
    private HorizontalScrollView hs_scrollview;
    TextView titDoc;
    //    private StartDocFlowParameter mDocInfoParameters;
    private StartDocFlowParameterBuild mDocInfoParameters;

    private INetWorkManager netWorkManager;

    public Map<String, Object> formMap;
    public String parentAppId;
    public String parentAppVersionID;

    protected int getLayoutById() {
        return R.layout.activity_detail;
    }

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        mDetailActivityLayout = (DetailActivityLayout) LayoutInflater
                .from(this).inflate(R.layout.activity_detail, null);
        setContentView(mDetailActivityLayout);
        mDetailActivityLayout.setValue(this);
        Intent intent = getIntent();
        app_id = intent.getStringExtra("app_id");
        parentAppId = intent.getStringExtra("parentAppId");
        app_version_id = intent.getStringExtra("app_version_id");
        parentAppVersionID = intent.getStringExtra("parentAppVersionID");
        mCurrentNodeName = intent.getStringExtra("appName");
        isWaterSecurity = Integer.parseInt(intent.getStringExtra("com_workflow_mobileconfig_include_security") == null ? "0" : intent.getStringExtra("com_workflow_mobileconfig_include_security"));
        isShare = Integer.parseInt(intent.getStringExtra("com_workflow_mobileconfig_include_security") == null ? "0" : intent.getStringExtra("com_workflow_mobileconfig_include_security"));
        com_workflow_mobileconfig_IM_enabled = Integer.parseInt(intent.getStringExtra("com_workflow_mobileconfig_include_security") == null ? "0" : intent.getStringExtra("com_workflow_mobileconfig_include_security"));
        mEmptyLayout = (EmptyLayout) findViewById(R.id.emptyLayout);
//        mEmptyLayout.setErrorButtonClickListener(this);
//        mBottomButtonSlyteEnum = BookInit.getInstance().getmBottomButtonEnum();
        String com_workflow_mobileconfig_actionbutton_style = getIntent().getStringExtra("com_workflow_mobileconfig_actionbutton_style");//操作按钮配置
        if (TextUtils.isEmpty(com_workflow_mobileconfig_actionbutton_style)) {
            com_workflow_mobileconfig_actionbutton_style = "1";
        }
        mBottomButtonSlyteEnum = BottomButtonSlyteEnum.getBottomButtonSlyteEnum(Integer.parseInt(com_workflow_mobileconfig_actionbutton_style));
        formMap = new HashMap<String, Object>();
        initView();
//		SelectPoisition.getInstances().hiden = 9;
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


        titDoc = ((TextView) titleBar.findViewById(R.id.textview_titlebar_title));
        titDoc.setText(mCurrentNodeName);
        netWorkManager = LogManagerProxy.getProxyInstance(NetWorkManager.class);
        showLoadingView();
        // 发起网络请求，获取发起需要的表单
        mDocInfoModel = new DocInfoModel(this);
        mDocInfoParameters = new StartDocFlowParameterBuild();
        mDocInfoParameters.userId = OAConText.getInstance(HtmitechApplication
                .instance()).UserID;
//        mDocInfoParameters.flowid = flowid;
        mDocInfoParameters.appId = app_id;
        mDocInfoParameters.appVersionId = app_version_id;
//        mDocInfoParameters.DocType = getDocType();
        netWorkManager.logFunactionStart(StartDetailActivity.this, StartDetailActivity.this, "nativeStart", LogManagerEnum.STARTDOC_FLOW.getFunctionCode());

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
            mDoActionParameter.nextNodeId = hasSelectedRouteInfo.getRouteID(); // 需要选路径时，传入用户选择的路由。再次提交服务器。
        }
        mDoActionParameter.SelectAuthorID = authorId.toString(); // 需要选路径时，传入用户选择的路由。再次提交服务器。
        mDoActionParameter.DocType = getDocType();// 恢复flowid

//        mDoActionParameter.context = OAConText.getInstance(HtmitechApplication
//                .instance());
        mDoActionParameter.docId = getDocId();
//        mDoActionParameter.do = getDocType();
        mDoActionParameter.systemCode = mDocResultInfo.getResult().getSystemCode(); // 2015-08-11
        mDoActionParameter.appId = app_id;
        mDoActionParameter.userId = OAConText.getInstance(this).UserID;
        mDoActionParameter.flowId = mDocResultInfo.getResult().getFlowId();
        mDoActionParameter.flowName = mDocResultInfo.getResult().getFlowName();
        mDoActionParameter.currentNodeId = mDocResultInfo.getResult()
                .getCurrentNodeID();
        mDoActionParameter.currentTrackId = mDocResultInfo.getResult()
                .getCurrentTrackId();
        netWorkManager.logFunactionStart(StartDetailActivity.this, StartDetailActivity.this, "nativeDetail", LogManagerEnum.DODO_ACTION_NATIVE.functionCode);

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
        mDoActionParameter.nextNodeId = authorId.toString(); // 需要选路径时，传入用户选择的路由。再次提交服务器。
        // mDoActionParameter.SelectAuthorID = null; //
        // 需要选路径时，传入用户选择的路由。再次提交服务器。
        mDoActionParameter.DocType = getDocType();// 恢复flowid

//        mDoActionParameter.context = OAConText.getInstance(HtmitechApplication
//                .instance());
        mDoActionParameter.docId = getDocId();
        mDoActionParameter.DocType = getDocType();
        mDoActionParameter.systemCode = mDocResultInfo.getResult().getSystemCode(); // 2015-08-11
        mDoActionParameter.flowId = mDocResultInfo.getResult().getFlowId();
        mDoActionParameter.flowName = mDocResultInfo.getResult().getFlowId();
        mDoActionParameter.currentNodeId = mDocResultInfo.getResult()
                .getCurrentNodeID();
        mDoActionParameter.currentTrackId = mDocResultInfo.getResult()
                .getCurrentTrackId();
        mDoActionParameter.userId = OAConText.getInstance(this).UserID;
        netWorkManager.logFunactionStart(StartDetailActivity.this, StartDetailActivity.this, "nativeDetail", LogManagerEnum.DODO_ACTION_NATIVE.functionCode);

//        mDocInfoModel.getDataFromServerByType(DocInfoModel.TYPE_DOACTION_TASK_START,
//                mDoActionParameter);
    }

    @Override
    public void onSuccess(final int requestTypeId, Object result) {

//        if (requestTypeId == DocInfoModel.TYPE_START_DETAILTASK_BUILD) {

//            if (result != null && result instanceof Object) {
////                mDocResultInfo = (StartResultInfo) result;
////                netWorkManager.logFunactionFinsh(StartDetailActivity.this, StartDetailActivity.this, "nativeStartSuccess", LogManagerEnum.STARTDOC_FLOW.functionCode, "success", INetWorkManager.State.SUCCESS);
////                mViewPager_mycollection = (MainViewPager) findViewById(R.id.viewPager_qarank);
////                FragmentManager fm = getSupportFragmentManager();
////                mViewPager_mycollection.setOffscreenPageLimit(4);
////                mMyTopTabIndicator = (NewTopTabIndicator) this
////                        .findViewById(R.id.topTabIndicator_detail);
////
////                mDocResultInfo.getResult().setDocType(getDocType());
//////                mCurrentNodeName = mDocResultInfo.getResult().getCurrentNodeName();
////                currentDocID = mDocResultInfo.getResult().getDocID();
////                currentDocKind = mDocResultInfo.getResult().getKind(); // 2015-08-11
////                flow_id = mDocResultInfo.getResult().getFlowId();
////                ArrayList<ChildType> list = new ArrayList<ChildType>();
////                List<String> listStr = new ArrayList<String>();
////                listStr.add("新建表单");
////                list.add(ChildType.TAB_START_FORM);
////                String[] arrayTopTabIndicator = new String[listStr.size()];
////                listStr.toArray(arrayTopTabIndicator);
////                titDoc.setText(mCurrentNodeName);
////                if (arrayTopTabIndicator.length != 1) {
////                    mMyTopTabIndicator.setCommonData2(mViewPager_mycollection,
////                            arrayTopTabIndicator, R.color.ht_hred_title,
////                            R.color.color_title);
////                }
//////                mAdapter = new AdapterFragmentForCollection(HtmitechApplication
//////                        .instance().getApplicationContext(), fm, list);
////                mAdapter = new AdapterFragmentForCollection(HtmitechApplication
////                        .instance().getApplicationContext(), fm, list, mDocResultInfo.getResult().getDocID(), mDocResultInfo.getResult().TabItems, app_id);
////                if ((mViewPager_mycollection != null) && (mAdapter != null))
////                    mViewPager_mycollection.setAdapter(mAdapter);
////                if (mDocResultInfo.getResult().getListActionInfo() == null
////                        || mDocResultInfo.getResult().getListActionInfo()
////                        .size() == 0) {
////                    menuMultipleActions = (AddFloatingActionButton) findViewById(R.id.function);
////
////                    menuMultipleActions.setSize(FloatingActionButton.SIZE_NORMAL);
////                    menuMultipleActions.setOnClickListener(this);
////                    menuMultipleActions.setOnTouchListener(this);
////
////                } else {
////                    menuMultipleActions = (AddFloatingActionButton) findViewById(R.id.function);
////                    menuMultipleActions.setSize(FloatingActionButton.SIZE_NORMAL);
////                    menuMultipleActions.setOnClickListener(this);
////                    menuMultipleActions.setOnTouchListener(this);
//////					initArcMenu(menuMultipleActions, mDocResultInfo.getResult()
//////							.getListActionInfo());
////                    mFunctionPopupWindow = new FunctionPopupWindow(this, new MenuOnClickListener(), mDocResultInfo.getResult()
////                            .getListActionInfo().size());
////                    mFunctionPopupWindow.initArcMenu(mDocResultInfo.getResult().getListActionInfo());
////                    menuMultipleActions.setVisibility(View.VISIBLE);
////                }
//
//            } else {
//                netWorkManager.logFunactionFinsh(StartDetailActivity.this, StartDetailActivity.this, "nativeStartFail", LogManagerEnum.STARTDOC_FLOW.functionCode, "fail", INetWorkManager.State.FAIL);
//
//                mEmptyLayout.setEmptyButtonClickListener(new OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        refreshByErrorOrNoWifi(requestTypeId);
//                    }
//                });
//                mEmptyLayout.showEmpty();
//            }
//
//        } else if (requestTypeId == DocInfoModel.TYPE_DOACTION_TASK_START) {
//            // 办理成功返回
//            if (result != null && result instanceof DoActionResultInfo) {
//                mDoActionResultInfo = (DoActionResultInfo) result;
//
//                if (mDoActionResultInfo.getResult().getResultCode().equals("2")) {// 操作：提交--选择下一节点
//                    netWorkManager.logFunactionFinsh(StartDetailActivity.this, StartDetailActivity.this, "nativeDetailSuccess", LogManagerEnum.DODO_ACTION_NATIVE.getFunctionCode(), "success", INetWorkManager.State.SUCCESS);
//
//                    Toast.makeText(StartDetailActivity.this, "操作：提交--选择下路由节点！",
//                            Toast.LENGTH_SHORT).show();
//
//                    String title = mDoActionResultInfo.getResult()
//                            .getResultInfo();
//                    IsMultiSelectRoute = mDoActionResultInfo.getResult()
//                            .isIsMultiSelectRoute();
////                    mDocResultInfo.getResult().setDocType(getDocType());// 恢复flowid
//                    if (!IsMultiSelectRoute) {
//                        nextcodeDialog = new MyNextCodeDialog(
//                                StartDetailActivity.this,
//                                dialogConfirmOnclickListener,
//                                dialogCancelOnclickListener, R.style.mydialog);
//                        nextcodeDialog.show();
//                        int sum = mDoActionResultInfo.getResult()
//                                .getListRouteInfo().length;
//                        for (int i = 0; i < sum; i++) {
//                            RadioButton radioBtn = new RadioButton(
//                                    StartDetailActivity.this);
//                            radioBtn.setText(mDoActionResultInfo.getResult()
//                                    .getListRouteInfo()[i].getRouteName());
//                            radioBtn.setTextColor(getResources().getColor(
//                                    R.color.color_ff555555));
//                            nextcodeDialog.setViewValue(title, radioBtn);
//                        }
//                    }
//                } else if (mDoActionResultInfo.getResult().getResultCode()
//                        .equals("4")) {// 直接选人
//                    netWorkManager.logFunactionFinsh(StartDetailActivity.this, StartDetailActivity.this, "nativeDetailSuccess", LogManagerEnum.DODO_ACTION_NATIVE.getFunctionCode(), "success", INetWorkManager.State.SUCCESS);
//
//                    String title = mDoActionResultInfo.getResult()
//                            .getResultInfo();
//                    IsMultiSelectUser = mDoActionResultInfo.getResult() // replace
//                            .isIsMultiSelectUser();
//
//                    hasSelectedRouteInfo = mDoActionResultInfo.getResult()
//                            .getHasSelectedRoute();
//                    if (hasSelectedRouteInfo != null) {
//                        if (hasSelectedRouteInfo.getRouteName() != null) {
//                            title += "--将提交到:"
//                                    + hasSelectedRouteInfo.getRouteName();
//                        } else if (hasSelectedRouteInfo.getRouteID() != null) {
//                            title += "--将提交到:"
//                                    + hasSelectedRouteInfo.getRouteID();
//                        }
//                    }
//
//                    nextpersonDialog = new MyNextPersonDialog(this,
//                            dialogConfirmOnclickListener,
//                            dialogCancelOnclickListener,
//                            dialogClearOnclickListener, R.style.mydialog);
//                    nextpersonDialog.show();
//                    nextpersonDialog.setViewValue(title, mDoActionResultInfo
//                            .getResult().getListAuthorInfo(), IsMultiSelectUser, mDoActionResultInfo
//                            .getResult().isIsFreeSelectUser());
//
//                    if (mDoActionResultInfo.getResult().isIsFreeSelectUser()) {
//
//                        final String title2 = title;
//                        nextpersonDialog
//                                .setOnSelectAllUserListener(new DialogSelectAllUserListener() {
//
//                                    @Override
//                                    public void onSelectAllUser() {
//                                        // TODO Auto-generated method stub
//                                        nextpersonDialog = new MyNextPersonDialog(
//                                                StartDetailActivity.this,
//                                                dialogConfirmOnclickListener,
//                                                dialogCancelOnclickListener,
//                                                dialogClearOnclickListener,
//                                                R.style.mydialog);
//
//                                        nextpersonDialog.show();
//                                        nextpersonDialog
//                                                .setSeletAllUserView(false);
//                                        AuthorInfoDAOImpl authorDao = new AuthorInfoDAOImpl(
//                                                StartDetailActivity.this);
//                                        nextpersonDialog.setViewAllUserValue(
//                                                title2,
//                                                authorDao
//                                                        .getAuthorForOaSelect(), IsMultiSelectUser);
//                                    }
//                                });
//
//                    }
//                } else if (mDoActionResultInfo.getResult().getResultCode()
//                        .equals("9")) {
//                    netWorkManager.logFunactionFinsh(StartDetailActivity.this, StartDetailActivity.this, "nativeDetailFail", LogManagerEnum.DODO_ACTION_NATIVE.getFunctionCode(), "fail", INetWorkManager.State.FAIL);
//                    mNewFragment = MyAlertDialogFragment.newInstance(
//                            mDoActionResultInfo.getResult().getResultInfo(),
//                            R.drawable.prompt_warn, null, confirmListener,
//                            false);
//
//                    mNewFragment.show(getSupportFragmentManager(), "dialog");
//                } else if (mDoActionResultInfo.getResult().getResultCode()
//                        .equals("0")) {// 办理成功
//                    netWorkManager.logFunactionFinsh(StartDetailActivity.this, StartDetailActivity.this, "nativeDetailSuccess", LogManagerEnum.DODO_ACTION_NATIVE.getFunctionCode(), "success", INetWorkManager.State.SUCCESS);
//
//                    Toast.makeText(StartDetailActivity.this, "操作成功！",
//                            Toast.LENGTH_SHORT).show();
//                    setResult(ActivityResultConstant.DOACTION_RESULT_OK,
//                            getIntent());
//                    exit();
//                } else {
//                    mNewFragment = MyAlertDialogFragment.newInstance(
//                            mDoActionResultInfo.getResult().getResultInfo(),
//                            R.drawable.prompt_warn, null, confirmListener,
//                            false);
//
//                    mNewFragment.show(getSupportFragmentManager(), "dialog");
//                    netWorkManager.logFunactionFinsh(StartDetailActivity.this, StartDetailActivity.this, "nativeDetailFail", LogManagerEnum.DODO_ACTION_NATIVE.getFunctionCode(), "fail", INetWorkManager.State.FAIL);
//
//                }
//            }
//        }
        switch (mBottomButtonSlyteEnum) {
            case DRAG:
                layout_buttom.setVisibility(View.GONE);
                hs_scrollview.setVisibility(View.GONE);
                if (null != menuMultipleActions)
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
								.getInstance(ScheduleDetailActivity.this);
						toast.setText("没有选择路由节点！！");
						toast.show(Toast.LENGTH_SHORT);
					} else if (!IsMultiSelectRoute && indexArr.length > 1) {
						ToastInfo toast = ToastInfo
								.getInstance(ScheduleDetailActivity.this);
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
        if (actionList != null) {
            layout_buttom.removeAllViews();
            final int itemCount = actionList.size();
            for (int i = 0; i < itemCount; i++) {
                DrawableCenterTextView item = new DrawableCenterTextView(this);
                item.setTextSize(14);
                item.setTextColor(getResources().getColor(R.color.buttom_color));
                Drawable drawable = null;
                item.setGravity(Gravity.CENTER_VERTICAL);
                item.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));
                if (actionList.get(i).getActionID().equalsIgnoreCase("submit"))    //提交
                    drawable = getResources().getDrawable(R.drawable.btn_action_submit);
                else if (actionList.get(i).getActionID().equalsIgnoreCase("readed"))    //已读
                    drawable = getResources().getDrawable(R.drawable.btn_action_read);
                else if (actionList.get(i).getActionID().equalsIgnoreCase("addreader"))    //阅知
                    drawable = getResources().getDrawable(R.drawable.btn_action_yuezhi);
                else if (actionList.get(i).getActionID().equalsIgnoreCase("getback"))    //拿回
                    drawable = getResources().getDrawable(R.drawable.btn_action_takeback);
                else if (actionList.get(i).getActionID().equalsIgnoreCase("Share"))    //分享
                    drawable = getResources().getDrawable(R.drawable.btn_action_share);
                else if (actionList.get(i).getActionID().equalsIgnoreCase("save"))
                    drawable = getResources().getDrawable(R.drawable.btn_action_save);
                else if (actionList.get(i).getActionID().equalsIgnoreCase("reject"))
                    drawable = getResources().getDrawable(R.drawable.btn_action_save);
                else if (actionList.get(i).getActionID().equalsIgnoreCase("Attention"))
                    drawable = getResources().getDrawable(R.drawable.btn_action_attention);
                else {
                    drawable = getResources().getDrawable(R.drawable.btn_action_submit);
                }
                if (drawable != null)
                    drawable.setBounds(0, 0, drawable.getMinimumWidth() / 2, drawable.getMinimumHeight() / 2);

                item.setCompoundDrawables(drawable, null, null, null);
                item.setText(actionList.get(i).getActionName());
                if (actionList.get(i).getActionID().equals("Attention")) {
                    item.setTag(actionList.get(i).getActionName());
                } else {
                    item.setTag(actionList.get(i).getActionID());
                }
                item.setBackgroundResource(R.drawable.buttom_message);
                item.setOnClickListener(new MenuOnClickListener());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
                item.setCompoundDrawablePadding(DeviceUtils.dip2px(this, 8));
                item.setLayoutParams(params);
                layout_buttom.addView(item);
            }
        }
    }

    @Override
    public void success(String requestValue, int type, String requestName) {
        if (requestName.equals("nativeDetail")) {
//            mDocInfoModel.getDataFromServerByType(
//                    DocInfoModel.TYPE_DOACTION_TASK_START, mDoActionParameter);
            AnsynHttpRequest.requestByPostWithToken(this, mDoActionParameter, doActionUrl, CHTTP.POSTWITHTOKEN, this, "doAction", LogManagerEnum.APP_DO_ACTION.functionCode);

        } else if (requestName.equals("nativeStart")) {
//            mDocInfoModel.getDataFromServerByType(
//                    DocInfoModel.TYPE_START_DETAILTASK_BUILD, mDocInfoParameters);
            AnsynHttpRequest.requestByPostWithToken(this, mDocInfoParameters, startDetailUrl, CHTTP.POSTWITHTOKEN, this, "getStartDetail", LogManagerEnum.STARTWORKFLOWH5.functionCode);
        } else if ("doActionfunctionStart".equals(requestName)) {
            AnsynHttpRequest.requestByPostWithToken(this, mDoActionParameter, doActionUrl, CHTTP.POSTWITHTOKEN, this, "doAction", LogManagerEnum.APP_DO_ACTION.functionCode);
        } else if (requestName.equals("doAction")) {
            requestValue = GetFinalRefreshTokenAfterRequestValue.getFinalRequestValue(this, requestValue, type, doActionUrl, mDoActionParameter, this, requestName, LogManagerEnum.APP_DO_ACTION.functionCode);
            if (!TextUtils.isEmpty(requestValue)) {
                mDoActionResultInfo = JSONObject.parseObject(requestValue, DoActionResultInfo.class);
                if (mDoActionResultInfo.getCode() == 200 && mDoActionResultInfo.getResult().getResultCode().equals("2")) {// 操作：提交--选择下一节点
                    netWorkManager.logFunactionFinsh(StartDetailActivity.this, StartDetailActivity.this, "nativeDetailSuccess", LogManagerEnum.DODO_ACTION_NATIVE.getFunctionCode(), "success", INetWorkManager.State.SUCCESS);

                    Toast.makeText(StartDetailActivity.this, "操作：提交--选择下路由节点！",
                            Toast.LENGTH_SHORT).show();

                    String title = mDoActionResultInfo.getResult()
                            .getResultInfo();
                    IsMultiSelectRoute = mDoActionResultInfo.getResult()
                            .isIsMultiSelectRoute();
//                    mDocResultInfo.getResult().setDocType(getDocType());// 恢复flowid
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
                } else if (mDoActionResultInfo.getCode() == 200 && mDoActionResultInfo.getResult().getResultCode()
                        .equals("4")) {// 直接选人
                    String title = mDoActionResultInfo.getResult().getResultInfo();
                    if (null != mDoActionResultInfo.getResult().getListRouteInfo() && null != mDoActionResultInfo.getResult()) {
                        if (mDoActionResultInfo.getResult().getListRouteInfo().length > 1) {//多于一个说明需要出来新增的多路由人员选择界面
                            Intent intentSelectPeople = new Intent(StartDetailActivity.this, MuliteRouteSelectPeopleActivity.class);
                            ArrayList<com.htmitech.emportal.entity.RouteInfo> ListRouteInfo = new ArrayList<com.htmitech.emportal.entity.RouteInfo>();
                            for (int i = 0; i < mDoActionResultInfo.getResult().getListRouteInfo().length; i++) {
                                ListRouteInfo.add(mDoActionResultInfo.getResult().getListRouteInfo()[i]);
                            }
                            intentSelectPeople.putExtra("ListRouteInfo", ListRouteInfo);
                            intentSelectPeople.putExtra("title", title);
                            startActivityForResult(intentSelectPeople, 1000);
                        } else if (mDoActionResultInfo.getResult().getListRouteInfo().length == 1) {//如果只有一个说明不需要出来人新增的多路由人员选择界面
                            final com.htmitech.emportal.entity.RouteInfo routeInfo = mDoActionResultInfo.getResult().getListRouteInfo()[0];
                            if (!routeInfo.isAllowSelectUser()) {
                                //想需要上传的集合中添加数据
                                ArrayList<SelectRouteInfo> SelectRoutes = new ArrayList<SelectRouteInfo>();//存放要上传的路由和人员信息
                                SelectRouteInfo selectRouteInfo = new SelectRouteInfo();
                                selectRouteInfo.routeId = routeInfo.routeID;
                                selectRouteInfo.routeName = routeInfo.routeName;
                                selectRouteInfo.isAllowSelectUser = routeInfo.isAllowSelectUser ? 1 : 0;
                                selectRouteInfo.isFreeSelectUser = routeInfo.isFreeSelectUser ? 1 : 0;
                                selectRouteInfo.isMultiSelectUser = routeInfo.isMultiSelectUser ? 1 : 0;
                                selectRouteInfo.routeAuthors = routeInfo.routeAuthors;
                                //向需要上传的SelectRoutes中添加数据
                                SelectRoutes.add(selectRouteInfo);
                                handle_doAction_hasRoute(SelectRoutes);
                            } else {
                                ArrayList<SYS_User> userList = SystemUser2SYSUser.system2SysUser(routeInfo.routeAuthors);
                                BookInit.getInstance().setCallCheckUserListener(StartDetailActivity.this, ChooseWayEnum.PEOPLECHOOSE, (!routeInfo.isMultiSelectUser ? ChooseWay.SINGLE_CHOOSE : ChooseWay.MORE_CHOOSE), ChooseTreeHierarchy
                                        .HIERARCHY, ChooseSystemBook.SYSTEM, title, routeInfo.isFreeSelectUser, userList, new CallCheckAllUserListener() {
                                    @Override
                                    public void checkAll(ArrayList<SYS_User> checkAllUser, ArrayList<SYS_Department> checkAllDepartment) {
                                        if (checkAllUser != null && checkAllUser.size() != 0) {
                                            ArrayList<htmitech.com.componentlibrary.entity.AuthorInfo> mAuthorInfoList = new ArrayList<htmitech.com.componentlibrary.entity.AuthorInfo>();
                                            for (SYS_User mSYS_User : checkAllUser) {
                                                htmitech.com.componentlibrary.entity.AuthorInfo mAuthorInfo = new htmitech.com.componentlibrary.entity.AuthorInfo();
                                                mAuthorInfo.setUserId(mSYS_User.getUserId());
                                                mAuthorInfo.setUserName(mSYS_User.getFullName());
                                                mAuthorInfoList.add(mAuthorInfo);
                                            }
                                            //想需要上传的集合中添加数据
                                            ArrayList<SelectRouteInfo> SelectRoutes = new ArrayList<SelectRouteInfo>();//存放要上传的路由和人员信息
                                            SelectRouteInfo selectRouteInfo = new SelectRouteInfo();
                                            selectRouteInfo.routeId = routeInfo.routeID;
                                            selectRouteInfo.routeName = routeInfo.routeName;
                                            selectRouteInfo.isAllowSelectUser = routeInfo.isAllowSelectUser ? 1 : 0;
                                            selectRouteInfo.isFreeSelectUser = routeInfo.isFreeSelectUser ? 1 : 0;
                                            selectRouteInfo.isMultiSelectUser = routeInfo.isMultiSelectUser ? 1 : 0;
                                            selectRouteInfo.routeAuthors = mAuthorInfoList;
                                            //向需要上传的SelectRoutes中添加数据
                                            SelectRoutes.add(selectRouteInfo);
                                            handle_doAction_hasRoute(SelectRoutes);
                                        }
                                    }
                                });
                            }
                        }
                    }

                } else if (mDoActionResultInfo.getCode() == 200 && mDoActionResultInfo.getResult().getResultCode()
                        .equals("9")) {
                    netWorkManager.logFunactionFinsh(StartDetailActivity.this, StartDetailActivity.this, "nativeDetailFail", LogManagerEnum.DODO_ACTION_NATIVE.getFunctionCode(), "fail", INetWorkManager.State.FAIL);
                    mNewFragment = MyAlertDialogFragment.newInstance(
                            mDoActionResultInfo.getResult().getResultInfo(),
                            R.drawable.prompt_warn, null, confirmListener,
                            false);

                    mNewFragment.show(getSupportFragmentManager(), "dialog");
                } else if (mDoActionResultInfo.getCode() == 200 && mDoActionResultInfo.getResult().getResultCode()
                        .equals("0")) {// 办理成功
                    netWorkManager.logFunactionFinsh(StartDetailActivity.this, StartDetailActivity.this, "nativeDetailSuccess", LogManagerEnum.DODO_ACTION_NATIVE.getFunctionCode(), "success", INetWorkManager.State.SUCCESS);

                    Toast.makeText(StartDetailActivity.this, "操作成功！",
                            Toast.LENGTH_SHORT).show();
                    setResult(ActivityResultConstant.DOACTION_RESULT_OK,
                            getIntent());
                    if (menuViewTag.equalsIgnoreCase("save") || menuViewTag.equalsIgnoreCase("submit")){
                        if (formMap.size() > 0) {
                            try {
                                SaveFiles();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }

                    exit();
                } else {
                    mNewFragment = MyAlertDialogFragment.newInstance(
                            mDoActionResultInfo.getMessage(),
                            R.drawable.prompt_warn, null, confirmListener,
                            false);

                    mNewFragment.show(getSupportFragmentManager(), "dialog");
                    netWorkManager.logFunactionFinsh(StartDetailActivity.this, StartDetailActivity.this, "nativeDetailFail", LogManagerEnum.DODO_ACTION_NATIVE.getFunctionCode(), "fail", INetWorkManager.State.FAIL);

                }
            }

        } else if (requestName.equals("getStartDetail")) {
            requestValue = GetFinalRefreshTokenAfterRequestValue.getFinalRequestValue(this, requestValue, type, startDetailUrl, mDocInfoParameters, this, requestName, LogManagerEnum.STARTWORKFLOWH5.functionCode);
            if (!TextUtils.isEmpty(requestValue)) {
                hideLoadingView();
                mDocResultInfo = JSONObject.parseObject(requestValue, DocResultInfo.class);
                netWorkManager.logFunactionFinsh(StartDetailActivity.this, StartDetailActivity.this, "nativeStartSuccess", LogManagerEnum.STARTDOC_FLOW.functionCode, "success", INetWorkManager.State.SUCCESS);
                if (null != mDocResultInfo && mDocResultInfo.code == 200) {
                    mViewPager_mycollection = (MainViewPager) findViewById(R.id.viewPager_qarank);
                    FragmentManager fm = getSupportFragmentManager();
                    mViewPager_mycollection.setOffscreenPageLimit(4);
                    mMyTopTabIndicator = (NewTopTabIndicator) this
                            .findViewById(R.id.topTabIndicator_detail);

//                mDocResultInfo.getResult().setDocType(getDocType());
//                mCurrentNodeName = mDocResultInfo.getResult().getCurrentNodeName();
                    if (null != mDocResultInfo.getResult())
                        currentDocID = mDocResultInfo.getResult().getDocID();
//                currentDocKind = mDocResultInfo.getResult().getKind(); // 2015-08-11
                    if (null != mDocResultInfo.getResult())
                        flow_id = mDocResultInfo.getResult().getFlowId();
                    ArrayList<WorkFlowForCollectionAdapter.ChildType> list = new ArrayList<WorkFlowForCollectionAdapter.ChildType>();
                    List<String> listStr = new ArrayList<String>();
                    listStr.add("新建表单");
                    list.add(WorkFlowForCollectionAdapter.ChildType.TAB_START_FORM);
                    String[] arrayTopTabIndicator = new String[listStr.size()];
                    listStr.toArray(arrayTopTabIndicator);
                    titDoc.setText(mCurrentNodeName);
                    if (arrayTopTabIndicator.length != 1) {
                        mMyTopTabIndicator.setCommonData2(mViewPager_mycollection,
                                arrayTopTabIndicator, R.color.ht_hred_title,
                                R.color.color_title);
                    }
//                mAdapter = new AdapterFragmentForCollection(HtmitechApplication
//                        .instance().getApplicationContext(), fm, list);
                    mAdapter = new WorkFlowForCollectionAdapter(HtmitechApplication
                            .instance().getApplicationContext(), fm, list, mDocResultInfo.getResult().getDocID(), mDocResultInfo.getResult().TabItems, app_id, 0);
//                HtmitechApplication.instance().getApplicationContext(), fm, list, mDocResultInfo.getResult().getDocID(), listTab, app_id, com_workflow_mobileconfig_include_options
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
                        switch (mBottomButtonSlyteEnum) {
                            case DRAG:
                                layout_buttom.setVisibility(View.GONE);
                                hs_scrollview.setVisibility(View.GONE);
                                if (null != menuMultipleActions)
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
                    }
                }

            }

        }
    }

    @Override
    public void fail(String exceptionMessage, int type, String requestName) {
        if (requestName.equals("nativeDetail")) {
//            mDocInfoModel.getDataFromServerByType(
//                    DocInfoModel.TYPE_DOACTION_TASK_START, mDoActionParameter);
            AnsynHttpRequest.requestByPostWithToken(this, mDoActionParameter, doActionUrl, CHTTP.POSTWITHTOKEN, this, "doAction", LogManagerEnum.APP_DO_ACTION.functionCode);
        } else if ("doActionfunctionStart".equals(requestName)) {
            AnsynHttpRequest.requestByPostWithToken(this, mDoActionParameter, doActionUrl, CHTTP.POSTWITHTOKEN, this, "doAction", LogManagerEnum.APP_DO_ACTION.functionCode);
        } else if (requestName.equals("nativeStart")) {
//            mDocInfoModel.getDataFromServerByType(
//                    DocInfoModel.TYPE_START_DETAILTASK_BUILD, mDocInfoParameters);
            AnsynHttpRequest.requestByPostWithToken(this, mDocInfoParameters, startDetailUrl, CHTTP.POSTWITHTOKEN, this, "getStartDetail", LogManagerEnum.STARTWORKFLOWH5.functionCode);
        } else if (requestName.equals("doAction")) {
            netWorkManager.logFunactionFinsh(StartDetailActivity.this, StartDetailActivity.this, "nativeDetailFail", LogManagerEnum.DODO_ACTION_NATIVE.functionCode, "fail", INetWorkManager.State.FAIL);
            Toast.makeText(StartDetailActivity.this, exceptionMessage,
                    Toast.LENGTH_SHORT).show();

        } else if (requestName.equals("getStartDetail")) {
            Toast.makeText(StartDetailActivity.this, exceptionMessage,
                    Toast.LENGTH_SHORT).show();
            hideLoadingView();
        }
    }

    @Override
    public void notNetwork() {
        if (!NetworkUtil.isNetworkAvailable(this)) {
            mEmptyLayout.setNoWifiButtonClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            mEmptyLayout.showNowifi();
        }
    }

    @Override
    public void callbackMainUI(String successMessage) {

    }

    //必填字段的判断以及背景变色 TODO tony
    public void mustFiledView(StartFormFragment mStartFormFragment) {
        for (ComboBox mComboBox : mStartFormFragment.getComboxList()) {
            if (mComboBox != null && mComboBox.isMustinput() && TextUtils.isEmpty(mComboBox.getText().toString())) {
                mComboBox.setBackgroundResource(R.drawable.combobox_ismustinput);
            }
        }

        for (EditText mSuperEditText : mStartFormFragment.getList_etsize()) {
            SuperEditText mSuperEditTexts = null;
            if (mSuperEditText instanceof SuperEditText) {
                mSuperEditTexts = (SuperEditText) mSuperEditText;
            }
            if (mSuperEditTexts != null && mSuperEditTexts.isMustInput() && TextUtils.isEmpty(mSuperEditTexts.getText().toString())) {
                mSuperEditTexts.setBackgroundResource(R.drawable.combobox_ismustinput);
            }
        }
//        tvEditValue.setHint("（必填）");
        for (TextView mTextView : mStartFormFragment.getList_tvsize()) {
            if (mTextView != null && mTextView.getHint() != null && (mTextView.getText() == null || mTextView.getText().toString() == null || mTextView.getText().toString().equals("")) && mTextView.getHint().toString().equals("（必填）")) {
                mTextView.setBackgroundResource(R.drawable.combobox_ismustinput);
            }
        }
    }

    private void handle_doAction_hasRoute(List<SelectRouteInfo> SelectRoutes) {
        // 再次提交
        mDoActionParameter.appId = app_id;
        mDoActionParameter.selectRoutes = SelectRoutes;
        try {
            netWorkManager.logFunactionStart(this, this, "doActionfunctionStart", LogManagerEnum.APP_DO_ACTION.functionCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        showLoadingView();
    }

    public class MenuOnClickListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            // 关按钮
//			menuMultipleActions.toggle();
            if(v.getTag() != null && !TextUtils.isEmpty(v.getTag().toString())){
                menuViewTag = v.getTag().toString();
            }

            mFunctionPopupWindow.dismiss();
            menuMultipleActions.setImageDrawable(getResources().getDrawable(R.drawable.btn_operation_homelabel_off));
            // TODO Auto-generated method stub
            showLoadingView();
            StartFormFragment mStartFormFragment = (StartFormFragment) mAdapter.getItem(0);
            mustFiledView(mStartFormFragment);
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
            mDoActionParameter = new DoActionParameterStart();
            mDoActionParameter.actionName = v.getTag().toString();
            mDoActionParameter.actionId = v.getTag().toString();
//            mDoActionParameter.context = OAConText
//                    .getInstance(HtmitechApplication.instance());
            mDoActionParameter.docId = getDocId();
            mDoActionParameter.DocType = getDocType();
            mDoActionParameter.systemCode = mDocResultInfo.getResult().getSystemCode(); // 2015-08-11

            mDoActionParameter.flowId = mDocResultInfo.getResult().getFlowId();
            mDoActionParameter.flowName = mDocResultInfo.getResult()
                    .getFlowName();
            mDoActionParameter.currentNodeId = mDocResultInfo.getResult()
                    .getCurrentNodeID();
            mDoActionParameter.currentTrackId = mDocResultInfo.getResult()
                    .getCurrentTrackId();
            String dataId = "";
            for (InfoTab mInfoTab : mDocResultInfo.getResult().getTabItems()) {
                if (!TextUtils.isEmpty(mInfoTab.dataId)) {
                    dataId = mInfoTab.dataId;
                    break;
                }
            }
            if (mDocResultInfo.getResult().getEditFields() != null
                    && mDocResultInfo.getResult().getEditFields().size() > 0) {
                mDoActionParameter.editFields = new EditField[mDocResultInfo
                        .getResult().getEditFields().size()];
                for (int j = 0; j < mDocResultInfo.getResult().getEditFields()
                        .size(); j++) {
//                    mDocResultInfo.getResult().getEditFields().get(j).dataId = mDocResultInfo.getResult().getDataId();
//                    mDocResultInfo.getResult().getEditFields().get(j).dataId = mDocResultInfo.getResult().TabItems.get(0).dataId;
                    mDocResultInfo.getResult().getEditFields().get(j).setDataId(dataId);
                    mDoActionParameter.editFields[j] = mDocResultInfo
                            .getResult().getEditFields().get(j);
                }
            }
            mDoActionParameter.nextNodeId = null; // 需要选路径时，传入用户选择的路由。再次提交服务器。
            mDoActionParameter.SelectAuthorID = null; // 需要选路径时，传入用户选择的路由。再次提交服务器。

            mDoActionParameter.Comments = comment;
            mDoActionParameter.CommentFieldName = "";
            mDoActionParameter.appId = app_id;
            mDoActionParameter.userId = OAConText.getInstance(StartDetailActivity.this).UserID;
            netWorkManager.logFunactionStart(StartDetailActivity.this, StartDetailActivity.this, "nativeDetail", LogManagerEnum.DODO_ACTION_NATIVE.getFunctionCode());

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

    public void onFail(final int taskType, int statusCode, String errorMsg,
                       Object result) {
        if (result != null) {

            if (taskType == DocInfoModel.TYPE_GET_DETAILTASK) {
                mDocResultInfo = new DocResultInfo();
                netWorkManager.logFunactionFinsh(StartDetailActivity.this, StartDetailActivity.this, "nativeStartFail", LogManagerEnum.STARTDOC_FLOW.functionCode, "fail", INetWorkManager.State.FAIL);
                try {
//                    mDocResultInfo.parseJson(result.toString());
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
                netWorkManager.logFunactionFinsh(StartDetailActivity.this, StartDetailActivity.this, "nativeDetailFail", LogManagerEnum.DODO_ACTION_NATIVE.functionCode, "fail", INetWorkManager.State.FAIL);
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
//                    finish();
                }
            } else {

                ToastInfo toast = ToastInfo.getInstance(this);
                toast.setView(getLayoutInflater(), R.drawable.prompt_error,
                        result.toString());
                toast.show(Toast.LENGTH_SHORT);
            }
        }

        if (!NetworkUtil.isNetworkAvailable(this)) {
            mEmptyLayout.setNoWifiButtonClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    refreshByErrorOrNoWifi(taskType);
                }
            });
            mEmptyLayout.showNowifi();
        } else {
            mEmptyLayout.setErrorButtonClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            mEmptyLayout.showError();
        }

        hideLoadingView();
    }


    public void refreshByErrorOrNoWifi(int taskType) {
        if (taskType == DocInfoModel.TYPE_GET_DETAILTASK) {
            netWorkManager.logFunactionStart(StartDetailActivity.this, StartDetailActivity.this, "nativeStart", LogManagerEnum.STARTDOC_FLOW.functionCode);
        } else if (taskType == DocInfoModel.TYPE_DOACTION_TASK_START) {
            netWorkManager.logFunactionStart(StartDetailActivity.this, StartDetailActivity.this, "nativeDetail", LogManagerEnum.DODO_ACTION_NATIVE.functionCode);
        }
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

    public void SaveFiles() {
        //TODO 有关文件保存的操作
//        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
//                .detectDiskReads()
//                .detectDiskWrites()
//                .detectNetwork() // or .detectAll() for all detectable problems
//                .penaltyLog()
//                .build());
//        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
//                .detectLeakedSqlLiteObjects()
//                .detectLeakedClosableObjects()
//                .penaltyLog()
//                .penaltyDeath()
//                .build());

        SaveBigFileExtFields saveBigFileExtFields = new SaveBigFileExtFields();

        saveBigFileExtFields.appId = app_id;

        saveBigFileExtFields.dataId = getDocId();

        saveBigFileExtFields.formId = mDocResultInfo.getResult().getFormId() ;

        saveBigFileExtFields.userId = OAConText.getInstance(StartDetailActivity.this).UserID;


        saveBigFileExtFields.editFields = new ArrayList<>();

        if (formMap.size() > 0) {

            Set<String> set = formMap.keySet();

            for (String str : set) {// 遍历set去出里面的的Key
                int index = 0;
                if (formMap.get(str) instanceof SelectPhoto6001_6002_6101_6102) {

                    SaveBigFileEditFieldsList mExtfields = new SaveBigFileEditFieldsList();

                    SelectPhoto6001_6002_6101_6102 mSelectPhoto6001_6002_6101_6102 = ((SelectPhoto6001_6002_6101_6102) formMap.get(str));

                    mExtfields.key = mSelectPhoto6001_6002_6101_6102.workflow_item.getKey();

                    mExtfields.extFieldType = mSelectPhoto6001_6002_6101_6102.workflow_item.getInput();

                    mExtfields.fieldId = str;

                    for (PhotoModel mPhotoModel : mSelectPhoto6001_6002_6101_6102.listImgs) {
//                            UploadBigFileFactory.get().createUploadTask();
                        if (!mPhotoModel.isNet())
                            index++;
                    }
                    if (index == 0) {
                        continue;
                    }

                    mExtfields.fieldFileCount = ((AudioSelect4002) formMap.get(str)).mListPickerAudioSelect.size() + "";

                    mExtfields.uniqueId = mDocResultInfo.getResult().getFlowId() + str;


                    saveBigFileExtFields.editFields.add(mExtfields);

                } else if (formMap.get(str) instanceof AudioSelect4002) {

                    SaveBigFileEditFieldsList mExtfields = new SaveBigFileEditFieldsList();

                    AudioSelect4002 mAudioSelect4002 = ((AudioSelect4002) formMap.get(str));

                    mExtfields.key = mAudioSelect4002.workflow_item.getKey();
                    for (FilePickerMusic mFilePickerMusic : ((AudioSelect4002) formMap.get(str)).mListPickerAudioSelect) {
                        if (!mFilePickerMusic.isNet())
                            index++;
                    }
                    if (index == 0) {
                        continue;
                    }
                    mExtfields.extFieldType = mAudioSelect4002.workflow_item.getInput();

                    mExtfields.uniqueId = mDocResultInfo.getResult().getFlowId() + str;

                    mExtfields.fieldFileCount = ((AudioSelect4002) formMap.get(str)).mListPickerAudioSelect.size() + "";

                    mExtfields.fieldId = str;

                    saveBigFileExtFields.editFields.add(mExtfields);


                }

            }
        }

        UploadBigFileFactory.get().setContext(this).setSaveBigFileExtField(saveBigFileExtFields);
        if (formMap.size() > 0) {
            Set<String> set = formMap.keySet();
            for (String str : set) {// 遍历set去出里面的的Key
                if (formMap.get(str) instanceof SelectPhoto6001_6002_6101_6102) {
                    SelectPhoto6001_6002_6101_6102 mSelectPhoto6001_6002_6101_6102 = ((SelectPhoto6001_6002_6101_6102) formMap.get(str));
                    for (PhotoModel mPhotoModel : mSelectPhoto6001_6002_6101_6102.listImgs) {
//                            UploadBigFileFactory.get().createUploadTask();
                        if (!mPhotoModel.isNet())
                            UploadBigFileFactory.get().createUploadTask(mPhotoModel.getOriginalPath(), "表单", mDocResultInfo.getResult().getFormId(), mDocResultInfo.getResult().getFlowId() + str, 0);
                    }
                } else if (formMap.get(str) instanceof AudioSelect4002) {
                    for (FilePickerMusic mFilePickerMusic : ((AudioSelect4002) formMap.get(str)).mListPickerAudioSelect) {
                        if (!mFilePickerMusic.isNet())
                            UploadBigFileFactory.get().createUploadTask(mFilePickerMusic.getPath(), "表单", mDocResultInfo.getResult().getFormId(), mDocResultInfo.getResult().getFlowId()  + str, (int) (mFilePickerMusic.getDuration() / 1000));
                    }
                }

            }

            if (menuViewTag.equalsIgnoreCase("submit") || menuViewTag.equalsIgnoreCase("save")) {
                ClickToast.get().showToast(this, Toast.LENGTH_LONG);
            }
        }

    }

    @Override
    public void callBackLayout() {
        // TODO Auto-generated method stub
        if (action_move) {
            menuMultipleActions.layout(left, top, right, bottom);
        }
    }
}
