package com.htmitech.emportal.ui.detail;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.htmitech.MyView.EmptyLayout;
import com.htmitech.api.BookInit;
import com.htmitech.commonx.util.DeviceUtils;
import com.htmitech.commonx.util.LogUtil;
import com.htmitech.domain.SYS_Department;
import com.htmitech.domain.SYS_User;
import com.htmitech.emportal.HtmitechApplication;
import com.htmitech.emportal.R;
import com.htmitech.emportal.base.IBaseCallback;
import com.htmitech.emportal.common.CommonSettings;
import com.htmitech.emportal.db.AuthorInfoDAOImpl;
import com.htmitech.emportal.entity.ActionInfo;
import com.htmitech.emportal.entity.AuthorInfo;
import com.htmitech.emportal.entity.DoActionParameter;
import com.htmitech.emportal.entity.DoActionResultInfo;
import com.htmitech.emportal.entity.DocResultInfo;
import com.htmitech.emportal.entity.EditField;
import com.htmitech.emportal.entity.EditFieldList;
import com.htmitech.emportal.entity.FormInfo;
import com.htmitech.emportal.entity.H5DoActionParameter;
import com.htmitech.emportal.entity.H5DocResultInfo;
import com.htmitech.emportal.entity.OAConText;
import com.htmitech.emportal.entity.RouteInfo;
import com.htmitech.emportal.entity.StartDocFlowParameter;
import com.htmitech.emportal.entity.resultInfo;
import com.htmitech.emportal.ui.ActivityResultConstant;
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
import com.htmitech.emportal.utils.SystemUser2SYSUser;
import com.htmitech.listener.CallCheckAllUserListener;
import com.htmitech.myEnum.BottomButtonSlyteEnum;
import com.htmitech.myEnum.ChooseSystemBook;
import com.htmitech.myEnum.ChooseTreeHierarchy;
import com.htmitech.myEnum.ChooseWay;
import com.htmitech.myEnum.ChooseWayEnum;
import com.htmitech.proxy.interfaces.INetWorkManager;
import com.htmitech.proxy.myenum.LogManagerEnum;
import com.htmitech.proxy.util.LogManagerProxy;
import com.htmitech.proxy.util.NetWorkManager;
import com.minxing.kit.plugin.web.share.Share;

import org.apache.cordova.CordovaActivity;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CordovaWebViewImpl;
import org.apache.cordova.engine.mx.SystemWebChromeClient;
import org.apache.cordova.engine.mx.SystemWebView;
import org.apache.cordova.engine.mx.SystemWebViewEngine;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import htmitech.com.componentlibrary.listener.ObserverCallBackType;

//import com.htmitech.emportal.ui.widget.MyNextRouteDialog;

/***
 * @author gulbel 发起流程详情页面
 */
public class H5StartDetailActivity extends CordovaActivity implements
        OnClickListener, IBaseCallback, View.OnTouchListener, CallBackLayout ,ObserverCallBackType {

    private MainViewPager mViewPager_mycollection;
    private AdapterFragmentForCollection mAdapter;
    private NewTopTabIndicator mMyTopTabIndicator;
    private DocInfoModel mDocInfoModel;
    private LoadingView mLoadingView = null;
    private H5DocResultInfo mH5DocResultInfo;
    private AddFloatingActionButton menuMultipleActions = null;
    private String mDocAttachmentID = null;
    private MyNextCodeDialog nextcodeDialog; // 单选路由
//  private MyNextRouteDialog nextrouteDialog; // 多选路由
    private MyNextPersonDialog nextpersonDialog;
    private boolean IsMultiSelectUser;
    private boolean IsMultiSelectRoute;
    private String mCurrentNodeName ;
    private RouteInfo hasSelectedRouteInfo; // add by gulbel 2015-08-20
    private DoActionResultInfo mDoActionResultInfo;
//  private DoActionParameter mDoActionParameter;
    private H5DoActionParameter mDoActionParameter;
    private String comment; // 在流程中显示的意见，以用户最后在表单上输入的意见为准
    private String currentDocID; // 发起后返回在docID
    private String currentDocKind; // 发起后返回在docID
    private MyAlertDialogFragment mNewFragment = null;
    private DetailActivityLayout mDetailActivityLayout;
    private EmptyLayout mEmptyLayout;
    private LinearLayout layout_buttom;
    private FunctionPopupWindow mFunctionPopupWindow;
    private BottomButtonSlyteEnum mBottomButtonSlyteEnum = null;
    private HorizontalScrollView hs_scrollview;
    private Button NativeButton;
    private TextView titDoc;
    private static String jsonSubmit;
    private static resultInfo mresultInfo ;
    private int popupHeight;
    private int popupWidth;

    int screenWidth;
    int screenHeight;
    int lastX;
    int lastY;
    private boolean isTuoZhuai = false;
    private float x, y;
    private float ex, ey;
    private int left, top, right, bottom;
    private boolean action_move = false;
    private ProgressBar bar;
    private String app_id;
    private boolean isIsFreeSelectUser = false;

    //增加log日志
    private INetWorkManager netWorkManager;
    private  StartDocFlowParameter mDocInfoParameters;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h5detail);

        super.init();
        // Load your application
        // launchUrl = "file:///android_asset/www/index2.html";
//        launchUrl = "file:///android_asset/appstore/plugin/core/www/qjlc_ziyong.html";
//        loadUrl(launchUrl);
//              数据请求成功后打开URL链接
//        launchUrl = "file://"+ CommonSettings.DEFAULT_DOCFILE_CACHE_FOLDER + "/www/index.html";
//        loadUrl(launchUrl);
    }

    @Override
    protected CordovaWebView makeWebView() {
        SystemWebView webView = (SystemWebView)findViewById(R.id.cordovaWebView);
        SystemWebViewEngine engine = new SystemWebViewEngine(webView);
        webView.setWebChromeClient(new SystemWebChromeClient(engine) {
            @Override
            public void onProgressChanged(WebView webView, int i) {
                if (i == 100) {
                    bar.setVisibility(View.INVISIBLE);
                } else {
                    if (View.INVISIBLE == bar.getVisibility()) {
                        bar.setVisibility(View.VISIBLE);
                    }
                    bar.setProgress(i);
                }
                super.onProgressChanged(webView, i);
            }
        });
        return new CordovaWebViewImpl(engine);
    }

    @Override
    protected void createViews() {
        initView();
        if (preferences.contains("BackgroundColor")) {
            int backgroundColor = preferences.getInteger("BackgroundColor", Color.BLACK);
            // Background of activity:
            appView.getView().setBackgroundColor(backgroundColor);
        }
        appView.getView().requestFocusFromTouch();
    }

    /**
     * 初始化UI
     */
    protected void initView() {
        bar = (ProgressBar) findViewById(R.id.webpluginProgressBar);
        layout_buttom = (LinearLayout) findViewById(R.id.layout_buttom);
        hs_scrollview = (HorizontalScrollView) findViewById(R.id.hs_scrollview);
        EditFieldList mustFieldList = EditFieldList.getInstance();
        mustFieldList.Clear();
        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels - 50;
        View titleBar = findViewById(R.id.layout_detail_titlebar);
        titleBar.findViewById(R.id.imgview_titlebar_back).setOnClickListener(this);
        mLoadingView = (LoadingView) findViewById(R.id.loadingview_detail5);

        Intent intent = getIntent();
        app_id =  intent.getStringExtra("app_id");
        titDoc =  ((TextView) titleBar.findViewById(R.id.textview_titlebar_title));
        showLoadingView();
         //发起网络请求，获取发起需要的表单
        mDocInfoModel = new DocInfoModel(this);
        mDocInfoParameters = new StartDocFlowParameter();
        mDocInfoParameters.context = OAConText.getInstance(HtmitechApplication
                .instance());
        mDocInfoParameters.flowid = intent.getStringExtra("com_workflow_plugin_H5_startor_paramter");;
        mDocInfoParameters.DocType ="oa";
        mDocInfoParameters.otherparameter = "请假申请";
        mDocInfoParameters.app_id = app_id;


        netWorkManager = LogManagerProxy.getProxyInstance(NetWorkManager.class);
        netWorkManager.logFunactionStart(this, this, "functionStart", LogManagerEnum.STARTWORKFLOWH5.functionCode);




    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public String getDocId() {
        return currentDocID;
    }

    public String getDocKind() {
        return currentDocKind;
    }

    public String getDocType() {
        String docType = "oa";
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
            case R.id.function5:
                if (!isTuoZhuai) {
                    if (!mFunctionPopupWindow.isShowing()) {
//                      menuMultipleActions.startAnimation(animation);
                        menuMultipleActions.setImageDrawable(getResources().getDrawable(R.drawable.btn_operation_homelabel_on));
                        mFunctionPopupWindow = new FunctionPopupWindow(this,
                                new MenuOnClickListener(), mH5DocResultInfo.getResult()
                                .getListActionInfo().size());
                        mFunctionPopupWindow.initArcMenu(mH5DocResultInfo.getResult()
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



    /**
     * 退出
     **/
    public void exit() {

        finish();
    }

    private void handle_doAction_hasAuthor(ArrayList<AuthorInfo> AuthorInfoTemp) {
        // 假定通过界面选择了第一个人
        StringBuffer authorId = new StringBuffer();
        if (AuthorInfoTemp != null) {
            for (int i = 0; i < AuthorInfoTemp.size(); i++) {
                AuthorInfo selectUser = AuthorInfoTemp.get(i);
                Toast.makeText(H5StartDetailActivity.this,
                        "将提交给：" + selectUser.getUserName(), Toast.LENGTH_SHORT)
                        .show();
                if (i > 0)
                    authorId.append(";");

                authorId.append(selectUser.getUserID());
            }
        }

		 /*else {
            for (int i = 0; i < indexArr.length; i++) {
				AuthorInfo selectUser = mDoActionResultInfo.getResult()
						.getListAuthorInfo()[indexArr[i].intValue()];
				Toast.makeText(DetailActivity.this,
						"将提交给：" + selectUser.getUserName(), Toast.LENGTH_SHORT)
						.show();
				authorId.append(selectUser.getUserID());
			}
		}*/
        // 再次提交
        DocInfoModel mDocInfoModel = new DocInfoModel(H5StartDetailActivity.this);
        if (hasSelectedRouteInfo != null) {
            mDoActionParameter.NextNodeId = hasSelectedRouteInfo.getRouteID(); // 需要选路径时，传入用户选择的路由。再次提交服务器。
        }
        mDoActionParameter.app_id=app_id;
        mDoActionParameter.Kind = "oa";
        mDoActionParameter.SelectAuthorID = authorId.toString(); // 需要选路径时，传入用户选择的路由。再次提交服务器。
        mDocInfoModel.getDataFromServerByType(DocInfoModel.TYPE_DOACTION_TASK,
                mDoActionParameter);

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
        DocInfoModel mDocInfoModel = new DocInfoModel(H5StartDetailActivity.this);
        mDoActionParameter.NextNodeId = authorId.toString(); // 需要选路径时，传入用户选择的路由。再次提交服务器。
        // mDoActionParameter.SelectAuthorID = null; //
        // 需要选路径时，传入用户选择的路由。再次提交服务器。
        mDoActionParameter.DocType = getDocType();// 恢复flowid

        mDoActionParameter.context = OAConText.getInstance(HtmitechApplication
                .instance());
        mDoActionParameter.DocId = getDocId();
        mDoActionParameter.DocType = getDocType();
        mDoActionParameter.Kind = getDocKind(); // 2015-08-11
        mDoActionParameter.FlowId = mH5DocResultInfo.getResult().getFlowId();
        mDoActionParameter.FlowName = mH5DocResultInfo.getResult().getFlowId();
        mDoActionParameter.CurrentNodeid = mH5DocResultInfo.getResult()
                .getCurrentNodeID();
        mDoActionParameter.CurrentTrackid = mH5DocResultInfo.getResult()
                .getCurrentTrackId();
        mDoActionParameter.app_id = app_id;
        mDocInfoModel.getDataFromServerByType(DocInfoModel.TYPE_DOACTION_TASK,
                mDoActionParameter);
    }

    @Override
    public void onSuccess(int requestTypeId, Object result) {

        if (requestTypeId == DocInfoModel.TYPE_START_DETAILTASK_H5) {
            if(result != null){
                Intent intent = new Intent();
                intent.setAction("com.xiazdong");
                intent.putExtra("data",((H5DocResultInfo) result).getJsonResult() );
                H5StartDetailActivity.this.sendBroadcast(intent);
                launchUrl = "file://"+ CommonSettings.DEFAULT_DOCFILE_CACHE_FOLDER + File.separator+app_id+ "/www/index.html";
                loadUrl(launchUrl);
            }
            if (result != null && result instanceof H5DocResultInfo) {
                mH5DocResultInfo = (H5DocResultInfo) result;
                currentDocID = mH5DocResultInfo.getResult().getDocID();
                currentDocKind = mH5DocResultInfo.getResult().getKind();
                if (mH5DocResultInfo.getResult().getListActionInfo() == null
                        || mH5DocResultInfo.getResult().getListActionInfo()
                        .size() == 0) {
                    menuMultipleActions = (AddFloatingActionButton) findViewById(R.id.function5);

                    menuMultipleActions.setSize(FloatingActionButton.SIZE_NORMAL);
                    menuMultipleActions.setOnClickListener(this);
                    menuMultipleActions.setOnTouchListener(this);

                } else {
                    menuMultipleActions = (AddFloatingActionButton) findViewById(R.id.function5);
                    menuMultipleActions.setSize(FloatingActionButton.SIZE_NORMAL);
                    menuMultipleActions.setOnClickListener(this);
                    menuMultipleActions.setOnTouchListener(this);
                    mFunctionPopupWindow = new FunctionPopupWindow(this, new MenuOnClickListener(), mH5DocResultInfo.getResult()
                            .getListActionInfo().size());
                    mFunctionPopupWindow.initArcMenu(mH5DocResultInfo.getResult().getListActionInfo());
                    menuMultipleActions.setVisibility(View.VISIBLE);
                }

            }
            netWorkManager.logFunactionFinsh(H5StartDetailActivity.this, H5StartDetailActivity.this, "h5StartFail", LogManagerEnum.GET_COMMONFORM_DETAIL.functionCode, mH5DocResultInfo.getMessage().getStatusMessage(), INetWorkManager.State.SUCCESS);

        } else if (requestTypeId == DocInfoModel.TYPE_DOACTION_TASK) {
            // 办理成功返回
            if (result != null && result instanceof DoActionResultInfo) {
                mDoActionResultInfo = (DoActionResultInfo) result;

                if (mDoActionResultInfo.getResult().getResultCode().equals("2")) {// 操作：提交--选择下一节点
                    Toast.makeText(H5StartDetailActivity.this, "操作：提交--选择下路由节点！",
                            Toast.LENGTH_SHORT).show();

                    String title = mDoActionResultInfo.getResult()
                            .getResultInfo();
                    IsMultiSelectRoute = mDoActionResultInfo.getResult()
                            .isIsMultiSelectRoute();
//                    mH5DocResultInfo.getResult().setDocType(getDocType());// 恢复flowid
                    if (!IsMultiSelectRoute) {
                        nextcodeDialog = new MyNextCodeDialog(
                                H5StartDetailActivity.this,
                                dialogConfirmOnclickListener,
                                dialogCancelOnclickListener, R.style.mydialog);
                        nextcodeDialog.show();
                        int sum = mDoActionResultInfo.getResult()
                                .getListRouteInfo().length;
                        for (int i = 0; i < sum; i++) {
                            RadioButton radioBtn = new RadioButton(
                                    H5StartDetailActivity.this);
                            radioBtn.setText(mDoActionResultInfo.getResult()
                                    .getListRouteInfo()[i].getRouteName());
                            radioBtn.setTextColor(getResources().getColor(
                                    R.color.color_ff555555));
                            nextcodeDialog.setViewValue(title, radioBtn);
                        }
                    }
                } else if (mDoActionResultInfo.getResult().getResultCode()
                        .equals("4")) {// 直接选人
//                    Toast.makeText(H5StartDetailActivity.this, "请选择人员！",
//							Toast.LENGTH_SHORT).show();
                            String title = mDoActionResultInfo.getResult()
                                    .getResultInfo();
                    IsMultiSelectUser = mDoActionResultInfo.getResult()
                            .isIsMultiSelectUser();

                    hasSelectedRouteInfo = mDoActionResultInfo.getResult().getHasSelectedRoute();
                    if (hasSelectedRouteInfo != null) {
                        if (hasSelectedRouteInfo.getRouteName() != null) {
//							Toast.makeText(DetailActivity.this, "将提交到节点："+ hasSelectedRouteInfo.getRouteName(),
//							Toast.LENGTH_SHORT).show();
                            title += "--将提交到:" + hasSelectedRouteInfo.getRouteName();
                        } else if (hasSelectedRouteInfo.getRouteID() != null) {
//							Toast.makeText(DetailActivity.this, "将提交到节点："+ hasSelectedRouteInfo.getRouteID(),
//									Toast.LENGTH_SHORT).show();
                            title += "--将提交到:" + hasSelectedRouteInfo.getRouteID();
                        }
                    }

//					nextpersonDialog = new MyNextPersonDialog(this,
//							dialogConfirmOnclickListener,
//							dialogCancelOnclickListener,
//							dialogClearOnclickListener, R.style.mydialog);
//					nextpersonDialog.show();
//					/*if (mDoActionResultInfo.getResult().isIsFreeSelectUser()) {
//						//从数据库读取联系人
//						AuthorInfoDAOImpl authorDao = new AuthorInfoDAOImpl(this);
//						nextpersonDialog.setViewValue(title, authorDao.getAuthorForOaSelect());
//						//isIsFreeSelectUser = true;
//					} else {
//
//					}*/

//					nextpersonDialog.setViewValue(title, mDoActionResultInfo
//							.getResult().getListAuthorInfo(), IsMultiSelectUser, mDoActionResultInfo.getResult().isIsFreeSelectUser());
                    ArrayList<SYS_User> userList = SystemUser2SYSUser.system2SysUser(mDoActionResultInfo.getResult().getListAuthorInfo());

                    isIsFreeSelectUser = mDoActionResultInfo.getResult().isIsFreeSelectUser();
                    BookInit.getInstance().setCallCheckUserListener(H5StartDetailActivity.this, ChooseWayEnum.PEOPLECHOOSE, (!IsMultiSelectUser ? ChooseWay.SINGLE_CHOOSE : ChooseWay.MORE_CHOOSE), ChooseTreeHierarchy
                            .HIERARCHY, ChooseSystemBook.SYSTEM, title, isIsFreeSelectUser, userList, new CallCheckAllUserListener() {
                        @Override
                        public void checkAll(ArrayList<SYS_User> checkAllUser, ArrayList<SYS_Department> checkAllDepartment) {
                            if (checkAllUser != null && checkAllUser.size() != 0) {
                                ArrayList<AuthorInfo> mAuthorInfoList = new ArrayList<AuthorInfo>();
                                for (SYS_User mSYS_User : checkAllUser) {
                                    AuthorInfo mAuthorInfo = new AuthorInfo();
                                    mAuthorInfo.setUserID(mSYS_User.getUserId());
                                    mAuthorInfo.setUserName(mSYS_User.getFullName());
                                    mAuthorInfoList.add(mAuthorInfo);
                                }
                                handle_doAction_hasAuthor(mAuthorInfoList);
                            } else if (checkAllDepartment != null && checkAllDepartment.size() != 0) {
                                ArrayList<AuthorInfo> mAuthorInfoList = new ArrayList<AuthorInfo>();
                                for (SYS_Department mSYS_Department : checkAllDepartment) {
                                    AuthorInfo mAuthorInfo = new AuthorInfo();
                                    mAuthorInfo.setUserID(mSYS_Department.getDepartmentCode());
                                    mAuthorInfo.setUserName(mSYS_Department.getFullName());
                                    mAuthorInfoList.add(mAuthorInfo);
                                }
                                handle_doAction_hasAuthor(mAuthorInfoList);
                            }
                        }
                    });
                } else if (mDoActionResultInfo.getResult().getResultCode()
                        .equals("9")) {
                    mNewFragment = MyAlertDialogFragment.newInstance(
                            mDoActionResultInfo.getResult().getResultInfo(),
                            R.drawable.prompt_warn, null, confirmListener,
                            false);


                } else if (mDoActionResultInfo.getResult().getResultCode()
                        .equals("0")) {// 办理成功
                    Toast.makeText(H5StartDetailActivity.this, "操作成功！",
                            Toast.LENGTH_SHORT).show();
                    setResult(ActivityResultConstant.DOACTION_RESULT_OK,
                            getIntent());
                    exit();
                } else {
                    mNewFragment = MyAlertDialogFragment.newInstance(
                            mDoActionResultInfo.getResult().getResultInfo(),
                            R.drawable.prompt_warn, null, confirmListener,
                            false);


                }
            }
        }else {
            netWorkManager.logFunactionFinsh(H5StartDetailActivity.this, H5StartDetailActivity.this, "h5StartFail", LogManagerEnum.GET_COMMONFORM_DETAIL.functionCode, mH5DocResultInfo.getMessage().getStatusMessage(), INetWorkManager.State.FAIL);

        }
//        switch (mBottomButtonSlyteEnum) {
//            case DRAG:
//                layout_buttom.setVisibility(View.GONE);
//                hs_scrollview.setVisibility(View.GONE);
//                menuMultipleActions.setVisibility(View.VISIBLE);
//                break;
//            case BOTTOM:
//                layout_buttom.setVisibility(View.VISIBLE);
//                hs_scrollview.setVisibility(View.VISIBLE);
//                menuMultipleActions.setVisibility(View.GONE);
//                initArcMenu(mH5DocResultInfo.getResult()
//                        .getListActionInfo());
//                break;
//        }
        hideLoadingView();
    }

    private DialogConfirmListener confirmListener = new DialogConfirmListener() {
        public void onConfirm(BaseDialog dialog) {
            mNewFragment.dismiss();
        }
    };

    private DialogConfirmListener confirmAndExitListener = new DialogConfirmListener() {
        public void onConfirm(BaseDialog dialog) {
            mNewFragment.dismiss();
            exit();

        }
    };

    public DialogCancelListener mDialogCancelListener = new DialogCancelListener() {

        @Override
        public void onCancel(BaseDialog dialog) {
            mNewFragment.dismiss();
            exit();
        }
    };

    public DialogConfirmListener dialogConfirmOnclickListener = new DialogConfirmListener() {
        public void onConfirm(BaseDialog dialog) {
            if (null != nextpersonDialog && nextpersonDialog.isShowing()) { // 选择人
                Integer[] indexArr = nextpersonDialog.getSelectIndexArr();
                if (null == indexArr || indexArr.length <= 0) {
                    ToastInfo toast = ToastInfo
                            .getInstance(H5StartDetailActivity.this);
                    toast.setText("没有选择办理人！！");
                    toast.show(Toast.LENGTH_SHORT);
                } else if (!IsMultiSelectUser && indexArr.length > 1) {
                    ToastInfo toast = ToastInfo
                            .getInstance(H5StartDetailActivity.this);
                    toast.setText("只能选择一个办理人！");
                    toast.show(Toast.LENGTH_SHORT);
                } else {
                    AuthorInfo[] AuthorInfoTemp = nextpersonDialog.getNewVector();
//                    AuthorInfo[] AuthorInfoTemp = nextpersonDialog
//                            .getNewVector();
//                    handle_doAction_hasAuthor(indexArr, AuthorInfoTemp);
                    // handle_doAction_hasAuthor(indexArr);
                }
            } else if (null != nextcodeDialog && nextcodeDialog.isShowing()) { // 选节点---单选
                if (!IsMultiSelectRoute) {
                    int index = nextcodeDialog.getSelectIndex();
                    if (index < 0) { // 未选择
                        ToastInfo toast = ToastInfo
                                .getInstance(H5StartDetailActivity.this);
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

            if (actionList.get(i).getActionID().equalsIgnoreCase("Start_submit")) // 提交
                // item.setColorNormalResId(R.color.pink);
                item.setIcon(R.drawable.btn_action_submit);
            else if (actionList.get(i).getActionID().equalsIgnoreCase("Start_reject")) // 退回
                // item.setColorNormalResId(R.color.pink_pressed);
                item.setIcon(R.drawable.btn_action_return);
            else if (actionList.get(i).getActionID().equalsIgnoreCase("Start_readed")) // 已读
                item.setIcon(R.drawable.btn_action_read);
                // item.setColorNormalResId(R.color.color_26ffffff);
            else if (actionList.get(i).getActionID()
                    .equalsIgnoreCase("Start_addreader")) // 阅知
                item.setIcon(R.drawable.btn_action_yuezhi);
                // item.setColorNormalResId(R.color.color_33000000);
            else if (actionList.get(i).getActionID()
                    .equalsIgnoreCase("Start_getback")) // 拿回
                item.setIcon(R.drawable.btn_action_takeback);
                // item.setColorNormalResId(R.color.color_44b2b2b2);
            else if (actionList.get(i).getActionID().equalsIgnoreCase("Start_Share")) // 分享
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
        if (requestName.equals("functionStart")) {
            mDocInfoModel.getDataFromServerByType(
                    DocInfoModel.TYPE_START_DETAILTASK_H5, mDocInfoParameters);
            BookInit.getInstance().setFlag(false);
        }
    }

    @Override
    public void fail(String exceptionMessage, int type, String requestName) {
        if (requestName.equals("functionStart")) {
            mDocInfoModel.getDataFromServerByType(
                    DocInfoModel.TYPE_START_DETAILTASK_H5, mDocInfoParameters);
            BookInit.getInstance().setFlag(false);
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
        public void onClick(final View v) {
            // 关按钮
//			menuMultipleActions.toggle();
            mFunctionPopupWindow.dismiss();
            menuMultipleActions.setImageDrawable(getResources().getDrawable(R.drawable.btn_operation_homelabel_off));
            // TODO Auto-generated method stub
//            showLoadingView();
            (H5StartDetailActivity.this).loadUrl("javascript:doSubmit()");
            // 1,必填字段判读
            // 必填字段判断

//            EditFieldList mustFieldList = EditFieldList.getInstance();
//            for (int i = 0; i < mustFieldList.getList().size(); i++) {
//                if (mustFieldList.getList().get(i).getValue() == null
//                        || mustFieldList.getList().get(i).getValue().equals("")) {
//                    Toast.makeText(
//                            H5StartDetailActivity.this,
//                            "必填字段 "
//                                    + mustFieldList.getList().get(i)
//                                    .getNameDesc() + "尚未填写！",
//                            Toast.LENGTH_SHORT).show();
////                    hideLoadingView();
//                    return;
//                }
//            }

//            if(jsonSubmit != null){
//                hideLoadingView();
//            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    if(mresultInfo == null){
                        return;
                    }
                    DocInfoModel mDocInfoModel = new DocInfoModel(H5StartDetailActivity.this);
                    mDoActionParameter = new H5DoActionParameter();
                    mDoActionParameter.ActionName = v.getTag().toString();
                    mDoActionParameter.context = OAConText
                            .getInstance(HtmitechApplication.instance());
                    mDoActionParameter.DocId = getDocId();
                    mDoActionParameter.DocType = getDocType();
                    mDoActionParameter.Kind = getDocKind();// 2015-08-11
                    mDoActionParameter.FlowId = mH5DocResultInfo.getResult().getFlowId();
                    mDoActionParameter.FlowName = mH5DocResultInfo.getResult()
                            .getFlowName();
                    mDoActionParameter.CurrentNodeid = mH5DocResultInfo.getResult()
                            .getCurrentNodeID();
                    mDoActionParameter.CurrentTrackid = mH5DocResultInfo.getResult()
                            .getCurrentTrackId();

//            if (mH5DocResultInfo.getResult().getEditFields() != null
//                    && mH5DocResultInfo.getResult().getEditFields().size() > 0) {
//                mDoActionParameter.EditFields = new EditField[mH5DocResultInfo
//                        .getResult().getEditFields().size()];
//                for (int j = 0; j < mH5DocResultInfo.getResult().getEditFields()
//                        .size(); j++) {
//                    mDoActionParameter.EditFields[j] = mH5DocResultInfo
//                            .getResult().getEditFields().get(j);
//                }
//            }
                    if(mresultInfo.getFormInfo() != null && mresultInfo.getFormInfo().size()>0){
                        mDoActionParameter.EditFields= new FormInfo[mresultInfo.getFormInfo().size()];
                        for (int j = 0; j < mresultInfo.getFormInfo()
                                .size(); j++) {
                            mDoActionParameter.EditFields[j] = mresultInfo.getFormInfo().get(j);
                        }
                    }
                    mDoActionParameter.NextNodeId = null; // 需要选路径时，传入用户选择的路由。再次提交服务器。
                    mDoActionParameter.SelectAuthorID = null; // 需要选路径时，传入用户选择的路由。再次提交服务器。

                    mDoActionParameter.Comments = comment;
                    mDoActionParameter.CommentFieldName = "";
                    mDoActionParameter.app_id = app_id;
                    mDocInfoModel.getDataFromServerByType(
                            DocInfoModel.TYPE_DOACTION_TASK, mDoActionParameter);
                    showLoadingView();
                    mresultInfo = null;
                }
            }, 500);


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
                mH5DocResultInfo = new H5DocResultInfo();
                try {
                    mH5DocResultInfo.parseJson(result.toString());
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if (mH5DocResultInfo.getResult() != null) {
                    Toast.makeText(
                            H5StartDetailActivity.this,
                            mDoActionResultInfo.getResult().getResultInfo()
                                    + "("
                                    + mDoActionResultInfo.getResult()
                                    .getResultCode() + ")",
                            Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(H5StartDetailActivity.this, "获取详情失败！",
                            Toast.LENGTH_SHORT).show();
                netWorkManager.logFunactionFinsh(H5StartDetailActivity.this, H5StartDetailActivity.this, "h5StartFail", LogManagerEnum.GET_COMMONFORM_DETAIL.functionCode, mH5DocResultInfo.getMessage().getStatusMessage(), INetWorkManager.State.FAIL);
            } else if (taskType == DocInfoModel.TYPE_DOACTION_TASK) {
                mDoActionResultInfo = new DoActionResultInfo();
                try {
                    mDoActionResultInfo.parseJson(result.toString());
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if (mDoActionResultInfo.getResult() != null) {
                    Toast.makeText(
                            H5StartDetailActivity.this,
                            mDoActionResultInfo.getResult().getResultInfo()
                                    + "("
                                    + mDoActionResultInfo.getResult()
                                    .getResultCode() + ")",
                            Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(H5StartDetailActivity.this, "操作失败！",
                            Toast.LENGTH_SHORT).show();
            } else {
                ToastInfo toast = ToastInfo.getInstance(this);
                toast.setView(getLayoutInflater(), R.drawable.prompt_error,
                        result.toString());
                toast.show(Toast.LENGTH_SHORT);
            }
        }

//        hideLoadingView();
    }



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

    public static class Receiver1 extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            jsonSubmit = intent.getExtras().getString("submitData");
            Log.i("Recevier2", "接收到:"+jsonSubmit);
            mresultInfo = new resultInfo();
            mresultInfo = JSON.parseObject(jsonSubmit,resultInfo.class);

        }


    }

    @Override
    public void onResume() {
        /**
         * 设置为横屏
         */
        if(getRequestedOrientation()!= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.appView.backHistory();
    }
}
