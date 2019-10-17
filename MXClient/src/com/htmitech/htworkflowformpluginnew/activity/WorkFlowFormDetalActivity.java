package com.htmitech.htworkflowformpluginnew.activity;

import android.app.AlertDialog;
import android.app.LocalActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.htmitech.MyView.EmptyLayout;
import com.htmitech.addressbook.ClassEvent;
import com.htmitech.api.BookInit;
import com.htmitech.app.Constant;
import com.htmitech.app.widget.DrawableCenterTextView;
import com.htmitech.base.BaseFragmentActivity;

import htmitech.com.componentlibrary.base.BaseFragment;

import com.htmitech.commonx.base.cache.FileNameGenerator;
import com.htmitech.commonx.base.cache.MD5FileNameGenerator;
import com.htmitech.commonx.base.exception.DbException;
import com.htmitech.commonx.base.exception.HttpException;
import com.htmitech.commonx.base.http.ResponseInfo;
import com.htmitech.commonx.base.http.callback.RequestCallBack;
import com.htmitech.commonx.base.net.DownloadManager;
import com.htmitech.commonx.util.DeviceUtils;
import com.htmitech.domain.SYS_Department;
import com.htmitech.domain.SYS_User;
import com.htmitech.edittext.SuperEditText;
import com.htmitech.emportal.HtmitechApplication;
import com.htmitech.emportal.R;
import com.htmitech.emportal.base.IBaseCallback;
import com.htmitech.emportal.common.CommonSettings;
import com.htmitech.emportal.entity.OAConText;
import com.htmitech.emportal.receive.RegistOrUnRegisterReceive;
import com.htmitech.emportal.ui.ActivityResultConstant;
import com.htmitech.emportal.ui.detail.CallBackLayout;
import com.htmitech.emportal.ui.detail.DetailActivityLayout;
import com.htmitech.emportal.ui.helppage.HelpActivity;
import com.htmitech.emportal.ui.widget.BaseDialog;
import com.htmitech.emportal.ui.widget.DialogCancelListener;
import com.htmitech.emportal.ui.widget.DialogConfirmListener;
import com.htmitech.emportal.ui.widget.LoadingView;
import com.htmitech.emportal.ui.widget.MainViewPager;
import com.htmitech.emportal.ui.widget.MyAlertDialogFragment;
import com.htmitech.emportal.ui.widget.NewTopTabIndicator;
import com.htmitech.emportal.ui.widget.flatingactionButton.AddFloatingActionButton;
import com.htmitech.emportal.ui.widget.flatingactionButton.FloatingActionButton;
import com.htmitech.emportal.utils.Base64;
import com.htmitech.emportal.utils.CompressUtil;
import com.htmitech.htcommonformplugin.dao.FormExtensionFilesDao;
import com.htmitech.htcommonformplugin.entity.Extfields;
import com.htmitech.htcommonformplugin.entity.GetSaveExtFieldsEntity;
import com.htmitech.htcommonformplugin.entity.SaveExtFieldsInfoParameter;
import com.htmitech.htcommonformplugin.model.GetCommonFromInfoModel;
import com.htmitech.htnativestartformplugin.activity.StartDetailActivity;
import com.htmitech.htworkflowformpluginnew.adapter.WorkFlowForCollectionAdapter;
import com.htmitech.htworkflowformpluginnew.entity.AttentionParameters;
import com.htmitech.htworkflowformpluginnew.entity.AttentionResult;
import com.htmitech.htworkflowformpluginnew.entity.DoActionParameter;
import com.htmitech.htworkflowformpluginnew.entity.DoActionResultInfo;
import com.htmitech.htworkflowformpluginnew.entity.DocInfoParameters;
import com.htmitech.htworkflowformpluginnew.entity.PdfSignInfoResult;
import com.htmitech.htworkflowformpluginnew.entity.PdfSignInfoRootResult;
import com.htmitech.htworkflowformpluginnew.entity.RouteInfo;
import com.htmitech.htworkflowformpluginnew.entity.SelectRouteInfo;
import com.htmitech.htworkflowformpluginnew.entity.UploadSignFileEntity;
import com.htmitech.htworkflowformpluginnew.fragment.WorkFlowFormDetailFragment;

import com.htmitech.htworkflowformpluginnew.fragment.WorkFlowInitUrlFragment;
import com.htmitech.htworkflowformpluginnew.util.BadgeAllUnit;
import com.htmitech.htworkflowformpluginnew.util.Base64Utils;
import com.htmitech.htworkflowformpluginnew.util.CRC32Utils;
import com.htmitech.htworkflowformpluginnew.util.SystemUser2SYSUser;
import com.htmitech.htworkflowformpluginnew.weight.FunctionPopupWindow;
import com.htmitech.listener.CallCheckAllUserListener;

import htmitech.com.componentlibrary.entity.FieldItem;
import htmitech.com.componentlibrary.entity.Fielditems;
import htmitech.com.componentlibrary.entity.bigfileentity.SaveBigFileEditFieldsList;
import htmitech.com.componentlibrary.entity.bigfileentity.SaveBigFileExtFields;
import htmitech.com.componentlibrary.listener.ObserverCallBackType;

import com.htmitech.myEnum.BottomButtonSlyteEnum;
import com.htmitech.myEnum.ChooseSystemBook;
import com.htmitech.myEnum.ChooseTreeHierarchy;
import com.htmitech.myEnum.ChooseWay;
import com.htmitech.myEnum.ChooseWayEnum;
import com.htmitech.myEnum.LogManagerEnum;
import com.htmitech.photoselector.myinterface.CallBackImageSelectImp;
import com.htmitech.pop.ActionSheetDialog;
import com.htmitech.proxy.doman.GetPictureResult;
import com.htmitech.proxy.doman.PictureInfo;
import com.htmitech.proxy.interfaces.INetWorkManager;

import com.htmitech.proxy.util.LogManagerProxy;
import com.htmitech.proxy.util.NetWorkManager;
import com.htmitech.unit.TextUtil;
import com.htmitech_updown.updownloadmanagement.UploadBigFileFactory;
import com.htmitech_updown.updownloadmanagement.uploadfile.UploadManager;
import com.htmitech_updown.updownloadmanagement.view.CustomCircleProgressBar;
import com.htmitech_updown.updownloadmanagement.view.NetClickListener;
import com.htmitech_updown.updownloadmanagement.view.NetPopupWindow;
import com.minxing.client.util.FastJsonUtils;
import com.minxing.client.util.FileUtils;
import com.minxing.client.util.Utils;
import com.minxing.kit.api.MXAPI;
import com.minxing.kit.api.bean.ShareLink;
import com.mx.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import htmitech.com.componentlibrary.entity.ActionInfo;
import htmitech.com.componentlibrary.entity.AuthorInfo;
import htmitech.com.componentlibrary.entity.DocResultInfo;
import htmitech.com.componentlibrary.entity.EditField;
import htmitech.com.componentlibrary.entity.EditFieldList;
import htmitech.com.componentlibrary.entity.FormExtensionFiles;
import htmitech.com.componentlibrary.entity.InfoRegion;
import htmitech.com.componentlibrary.entity.InfoTab;
import htmitech.com.componentlibrary.entity.PhotoModel;
import htmitech.com.componentlibrary.entity.TrackInfo;
import htmitech.com.componentlibrary.unit.ClickToast;
import htmitech.com.componentlibrary.unit.NetConnectionUtils;
import htmitech.com.componentlibrary.unit.PreferenceUtils;
import htmitech.com.componentlibrary.unit.RefreshTotal;
import htmitech.com.componentlibrary.unit.SecuritySharedPreference;
import htmitech.com.componentlibrary.unit.ServerUrlConstant;
import htmitech.entity.FilePickerMusic;
import htmitech.formConfig.AudioSelect4002;
import htmitech.formConfig.SelectPhoto6001_6002_6101_6102;
import htmitech.util.BitmapFactoryUtil;

import com.htmitech.thread.AnsynHttpRequest;
import com.htmitech.thread.CHTTP;
import com.htmitech.thread.GetFinalRefreshTokenAfterRequestValue;

import static com.kinggrid.iapppdf.ui.viewer.IAppPDFActivity.fileName;
import static com.minxing.kit.t.fm;

/***
 * 代办 已办 详情
 *
 * @author joe
 * @date 2017/04/17
 */
public class WorkFlowFormDetalActivity extends BaseFragmentActivity implements
        OnClickListener, IBaseCallback, View.OnTouchListener, CallBackLayout, ObserverCallBackType, CallBackImageSelectImp {

    private MainViewPager mViewPager_mycollection;
    private WorkFlowForCollectionAdapter mAdapter;
    private NewTopTabIndicator mMyTopTabIndicator;
    public DocResultInfo mDocResultInfo;
    private LoadingView mLoadingView = null;
    private AddFloatingActionButton menuMultipleActions = null;
    public int curItem = 0;
    private MyAlertDialogFragment mNewFragment = null;
    //分享相关变量
    public String apiUrlTmp = null; //用于拼接分享参数
    public String apiUrl = null;
    //    public ShareLink shareLink = null;
    public String docTitle = null;
    public String iconId = null;
    public static boolean oneInit = false;
    public String TodoFlag = "0";
    private String mDocAttachmentID = null;
    private String docAttachmentType = null;
    private boolean IsMultiSelectRoute;
    private AttentionParameters attentionParameters;
    private DoActionResultInfo mDoActionResultInfo;
    private DoActionParameter mDoActionParameter;
    private String comment; // 在流程中显示的意见，以用户最后在表单上输入的意见为准
    private FunctionPopupWindow mFunctionPopupWindow;
    private DetailActivityLayout mDetailActivityLayout;
    private EmptyLayout mEmptyLayout;
    private String docKind;
    private LinearLayout layout_buttom;
    private DocInfoParameters mDocInfoParameters;
    private BottomButtonSlyteEnum mBottomButtonSlyteEnum;
    private HorizontalScrollView hs_scrollview;
    private boolean isSave = false;
    private boolean isReject = false;
    private int actionButtonStyle;
    public String app_id;
    public int com_workflow_mobileconfig_IM_enabled;
    private INetWorkManager netWorkManager;
    public String comeShare = "-1";
    public boolean isDocUrl = false;
    public int isTextUrl = 3; //是否正文显示WebWiew
    public String com_workflow_mobileconfig_downloadType;
    //新增
    public int isWaterSecurity; //是不是支持水印
    public int isShare;
    //是否支持表单分享功能。
    public String app_version_id;
    //新增 正文html链接 2017-06-09 12:07:09
    public String DocviewUrl;
    //新增 扩展字段 2017-6-12 11:39:17
    public Map<String, Object> formMap;
    private String getPictureUrl = "";
    private static final String HTTPTYPEPICTUTR = "getDicPicture";
    public ArrayList<FormExtensionFiles> formExtensionFilesList;
    public ArrayList<FormExtensionFiles> formExtensionFilesListUP;
    public List<PhotoModel> listImgCash = new ArrayList<PhotoModel>();
    public List<FilePickerMusic> listFilePickerMusic = new ArrayList<FilePickerMusic>();
    private File mFileTemp;
    int indexTemp = 0;
    private GetPictureResult mGetPictureResult;
    private Gson mGson = new Gson();
    private PictureInfo mPictureInfo;
    private FormExtensionFilesDao mFormExtensionFilesDao;


    public String docId;
    public String docType;
    public String formId;
    public String UserID;
    public String OA_UserId;
    public String OA_UserName;
    public String OA_UnitId;
    public String ThirdDepartmentName;
    public String ThirdDepartmentId;
    public String attribute1;

    private String ActionName;
    //新增，办理过程中意见类型的字段是否仅能输入','0，（默认）可以输入也可以从选择常用意见；1，不支持常用意见，只能输入。','0', 2017/11/22 18:05
    private int com_workflow_mobileconfig_include_options;

    public String getDocInfoPath = ServerUrlConstant.SERVER_JAVA_URL() + ServerUrlConstant.OA_GETDOCINFO_METHOD_JAVA;
    public String doActionPath = ServerUrlConstant.SERVER_JAVA_URL() + ServerUrlConstant.OA_DOAction_METHOD_JAVA;
    public String attentionPath = ServerUrlConstant.SERVER_JAVA_URL() + ServerUrlConstant.SET_ATTENTION_YESORNO_JAVA;
    public String getSignPdfUrl = ServerUrlConstant.SERVER_JAVA_URL() + ServerUrlConstant.GET_SIGNPDF_INFO;
    //    public String getSignPdfUrl = "http://192.168.88.33:8080/data-crab/gateway/flowClawController/transmit/getSignFileInfo";
    public static final String GET_DOCINFO = "getDocInfo";
    public static final String DO_ACTION = "doAction";
    public static final String ATTENTION_FLOW = "attentionFlow";
    public static final String PDFSIGN_INFO = "pdfSignInfo";
    public static final String PDFSIGN_UPLOAD = "pdfSignUpload";
    public ShareLink shareLink = null;
    private int com_workflow_mobileconfig_include_myfav;
    private String com_workflow_mobileconfig_confirmdialog;
    private String appShortName;
    public String flowId;
    public LocalActivityManager localActivityManager;
    private ArrayList<WorkFlowForCollectionAdapter.ChildType> list;
    private List<InfoTab> listTab;
    private FragmentManager fm;
    public int com_workflow_mobileconfig_opinion_style;
    private String fileId;

    public void setComment(String comment) {
        this.comment = comment;
    }

    protected int getLayoutById() {
        return R.layout.activity_detail;
    }

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        FileUtils.copyFiles(this);//为防止金格签批模板为空需要添加一个空模板
        //初始化金格签批是否保存的初始化值
        SecuritySharedPreference sp = new SecuritySharedPreference(this, PreferenceUtils.PDFSIGNSAVE, Context.MODE_PRIVATE);
        sp.edit().putBoolean("pdfsignsave",false).commit();
        mDetailActivityLayout = (DetailActivityLayout) LayoutInflater.from(this).inflate(
                R.layout.activity_detail, null);
        setContentView(mDetailActivityLayout);
        mEmptyLayout = (EmptyLayout) mDetailActivityLayout.findViewById(R.id.emptyLayout);
        mEmptyLayout.setErrorButtonClickListener(this);
        formMap = new HashMap<String, Object>();

        localActivityManager = new LocalActivityManager(this, true);
        localActivityManager.dispatchCreate(arg0);

        BookInit.getInstance().setmCallBackImageSelectImp(this);
        initView();

    }


    /**
     * 初始化UI
     */
    //该方法在 onCreate 的时候调用
    protected void initView() {
        layout_buttom = (LinearLayout) findViewById(R.id.layout_buttom);
        hs_scrollview = (HorizontalScrollView) findViewById(R.id.hs_scrollview);
        mDetailActivityLayout.setValue(this);
        EditFieldList mustFieldList = EditFieldList.getInstance();
        mustFieldList.Clear();
        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels - 50;
        View titleBar = findViewById(R.id.layout_detail_titlebar);    //构件titlebar
        titleBar.findViewById(R.id.imgview_titlebar_back).setOnClickListener(this);
        // ((TextView) titleBar.findViewById(R.id.textview_titlebar_title))
        // .setText("关于*****的");

        mLoadingView = (LoadingView) findViewById(R.id.loadingview_detail); //构建 加载视图

        Intent intent = getIntent();
        UserID = intent.getStringExtra("UserID");
        if (TextUtils.isEmpty(UserID)) {
            UserID = BookInit.getInstance().getCrrentUserId();
        }

        com_workflow_mobileconfig_confirmdialog = intent.getStringExtra("com_workflow_mobileconfig_confirmdialog");
        if (TextUtils.isEmpty(com_workflow_mobileconfig_confirmdialog)) {
            com_workflow_mobileconfig_confirmdialog = "0";
        }
        OA_UserId = intent.getStringExtra("OA_UserId");
        OA_UserName = intent.getStringExtra("OA_UserName");
        OA_UnitId = intent.getStringExtra("OA_UnitId");
        ThirdDepartmentName = intent.getStringExtra("ThirdDepartmentName");
        ThirdDepartmentId = intent.getStringExtra("ThirdDepartmentId");
        attribute1 = intent.getStringExtra("attribute1");
        docId = intent.getStringExtra("DocId");
        docType = intent.getStringExtra("DocType");
        docTitle = intent.getStringExtra("DocTitle");
        flowId = intent.getStringExtra("flowId");
        docKind = intent.getStringExtra("Kind");//2015-08-11
        TodoFlag = intent.getStringExtra("TodoFlag");//2016-8-2 by heyang//判断是待办还是已办
        app_id = intent.getStringExtra("app_id");
        appShortName = intent.getStringExtra("appShortName");
        actionButtonStyle = intent.getIntExtra("com_workflow_mobileconfig_actionbutton_style", -1);
        com_workflow_mobileconfig_IM_enabled = intent.getIntExtra("com_workflow_mobileconfig_IM_enabled", 1);
        isWaterSecurity = intent.getIntExtra("com_workflow_mobileconfig_include_security", 0);
        isShare = intent.getIntExtra("com_workflow_mobileconfig_include_share", 0);
        isTextUrl = intent.getIntExtra("com_workflow_mobileconfig_docview_style", 0);
        com_workflow_mobileconfig_downloadType = intent.getStringExtra("com_workflow_mobileconfig_docDownloadType");
        com_workflow_mobileconfig_include_myfav = intent.getIntExtra("com_workflow_mobileconfig_include_myfav", 0);
        app_version_id = intent.getStringExtra("app_version_id");
        comeShare = intent.getStringExtra("come_share");
        com_workflow_mobileconfig_include_options = intent.getIntExtra("com_workflow_mobileconfig_include_options", 0);
        com_workflow_mobileconfig_opinion_style = intent.getIntExtra("com_workflow_mobileconfig_opinion_style", 0);

        if (comeShare == null) {
            comeShare = "-1";
        }
        if (Constant.IS_DZKF) {
            ((TextView) titleBar.findViewById(R.id.textview_titlebar_title)).setText(appShortName + "详情");
        } else {
            ((TextView) titleBar.findViewById(R.id.textview_titlebar_title)).setText(docTitle);
        }
        showLoadingView();
        // 发起网络请求，获取详细
        mDocInfoParameters = new DocInfoParameters();
        if (comeShare.equals("1")) {
            mDocInfoParameters.docId = docId;
            mDocInfoParameters.appId = app_id;
            mDocInfoParameters.appVersionId = app_version_id;
            mDocInfoParameters.userId = UserID;
            if (!TextUtils.isEmpty(flowId)) {
                mDocInfoParameters.flowId = flowId;
            } else {
                flowId = "";
            }
        } else {
            mDocInfoParameters.docId = docId;
            mDocInfoParameters.systemCode = docKind;
            mDocInfoParameters.appId = app_id;
            mDocInfoParameters.appVersionId = app_version_id;
            mDocInfoParameters.userId = UserID;
            if (!TextUtils.isEmpty(flowId)) {
                mDocInfoParameters.flowId = flowId;
            } else {
                flowId = "";
            }
        }
        iconId = intent.getStringExtra("IconId");
        if (intent.getStringExtra("IconId") == null) {
            iconId = "";
        }
        String departmentId = PreferenceUtils.getThirdDepartmentId(this) != null ? PreferenceUtils.getThirdDepartmentId(this) : " ";
        String departmentName = PreferenceUtils.getThirdDepartmentName(this) != null ? PreferenceUtils.getThirdDepartmentName(this) : " ";
        String attribute = PreferenceUtils.getAttribute1(this) != null ? PreferenceUtils.getAttribute1(this) : " ";
//        String unitId = mDocInfoParameters.context.OA_UnitId != null ? mDocInfoParameters.context.OA_UnitId : " ";


//        apiUrlTmp = mDocInfoParameters.context.UserID + "|" + mDocInfoParameters.context.OA_UserId + "|" + mDocInfoParameters.context.OA_UserName
//                + "|" + docId + "|" + docKind + "|" + docType + "|" + app_id + "|" + app_version_id + "|" + departmentId + "|" + departmentName + "|" + attribute
//                + "|" + unitId;
        apiUrlTmp = mDocInfoParameters.appId + "|" + mDocInfoParameters.userId + "|" + mDocInfoParameters.appVersionId + "|" + mDocInfoParameters.docId + "|" + mDocInfoParameters.systemCode + "|" + flowId;


        //启动帮助页面
        if (PreferenceUtils.isLoginForm(this)) {
            PreferenceUtils.setLoginForm(this, false);
            Intent i = new Intent(this, HelpActivity.class);
            i.putExtra(HelpActivity.CURRENT_HELPPAGE,
                    HelpActivity.FORM_FLOW_HELPPAGE);
            startActivity(i);
        }

        netWorkManager = LogManagerProxy.getProxyInstance(NetWorkManager.class);
//

        try {
            netWorkManager.logFunactionStart(this, this, "functionStart", LogManagerEnum.APP_DOC_INFO.functionCode);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mBottomButtonSlyteEnum = BottomButtonSlyteEnum.getBottomButtonSlyteEnum(actionButtonStyle);

    }

    public boolean isOneSuccess = true; //是否是第一次成功
    boolean hasPdfUrl = false;

    @Override
    public void success(String requestValue, int type, String requestName) {
        if (requestName.equals("functionStart")) {
            AnsynHttpRequest.requestByPostWithToken(this, mDocInfoParameters, getDocInfoPath, CHTTP.POSTWITHTOKEN, this, GET_DOCINFO, LogManagerEnum.APP_DOC_INFO.functionCode);
        } else if ("doActionfunctionStart".equals(requestName)) {
            setmenuMultipleActionsCanNotClick();
            AnsynHttpRequest.requestByPostWithToken(this, mDoActionParameter, doActionPath, CHTTP.POSTWITHTOKEN, this, DO_ACTION, LogManagerEnum.APP_DO_ACTION.functionCode);
        } else if (requestName.equals("functionDetail")) {
            if (mAdapter.getItem(0) instanceof WorkFlowInitUrlFragment) {
                ((WorkFlowInitUrlFragment) (mAdapter.getItem(0))).submit();
            } else {
                functionDetail();
            }

        } else if (requestName.equals("login_home_back")) {
            Log.d("DetailActivity", "唤醒成功");
        } else if (requestName.equals(HTTPTYPEPICTUTR)) {

            requestValue = GetFinalRefreshTokenAfterRequestValue.getFinalRequestValue(this, requestValue, type, getPictureUrl, mFileTemp, this, requestName, LogManagerEnum.GGENERAL.functionCode);
            if (requestValue != null && !requestValue.equals("")) {
                mGetPictureResult = mGson.fromJson(requestValue.toString(), GetPictureResult.class);
                mPictureInfo = mGetPictureResult.getResult();
                if (formExtensionFilesListUP != null && indexTemp < formExtensionFilesListUP.size()) {
                    formExtensionFilesListUP.get(indexTemp).setFileId(mPictureInfo.getPictureId() + "");
                    mFormExtensionFilesDao.updateExtensionFilesJava(formExtensionFilesListUP.get(indexTemp));
                    indexTemp++;
                }

                if (indexTemp >= formExtensionFilesListUP.size()) {
                    List<Extfields> extfieldList = new ArrayList<Extfields>();
                    if (formMap.size() > 0) {
                        Set<String> set = formMap.keySet();
                        for (String str : set) {// 遍历set去出里面的的Key
                            ArrayList<FormExtensionFiles> formExtensionFilesArrayList = mFormExtensionFilesDao.getExtensionFilesJava(str);
                            if (((SelectPhoto6001_6002_6101_6102) formMap.get(str)).workflow_item != null) {
                                StringBuffer buffer = new StringBuffer();
                                Extfields mExtfields = new Extfields();
                                for (int i = 0; i < formExtensionFilesArrayList.size(); i++) {
                                    buffer.append(formExtensionFilesArrayList.get(i).getFileId() == null ? "" : formExtensionFilesArrayList.get(i).getFileId());
                                    if (i != (formExtensionFilesArrayList.size() - 1))
                                        buffer.append(",");
                                }
                                mExtfields.setExt_field_type(((SelectPhoto6001_6002_6101_6102) formMap.get(str)).workflow_item.getInput());
                                mExtfields.setField_id(str);
                                mExtfields.setValue(buffer.toString());
                                extfieldList.add(mExtfields);
                            }
                        }

                    }
                    SaveExtFieldsInfoParameter mSaveExtFieldsInfoParameter = new SaveExtFieldsInfoParameter();
                    mSaveExtFieldsInfoParameter.app_id = app_id;
                    mSaveExtFieldsInfoParameter.user_id = OAConText.getInstance(WorkFlowFormDetalActivity.this).UserID;
                    mSaveExtFieldsInfoParameter.form_id = formId;
                    mSaveExtFieldsInfoParameter.data_id = docId;
                    mSaveExtFieldsInfoParameter.extfields = extfieldList;
//                    mDocInfoModel.getDataFromServerByType(GetCommonFromInfoModel.TYPE_SAVEEXTFIELDS, mSaveExtFieldsInfoParameter);
                }
            }
            hideLoadingView();
        } else if (requestName.equals(LogManagerEnum.WORKFLOWATTENTION.functionCode)) {
            AttentionLog();
        } else if (GET_DOCINFO.equals(requestName)) {//请求表单详情
            isOneSuccess = true;
            requestValue = GetFinalRefreshTokenAfterRequestValue.getFinalRequestValue(this, requestValue, type, getDocInfoPath, mDocInfoParameters, this, requestName, LogManagerEnum.APP_DOC_INFO.functionCode);
            if (!TextUtils.isEmpty(requestValue)) {
                mDocResultInfo = FastJsonUtils.getPerson(requestValue, DocResultInfo.class);
                if (mDocResultInfo != null) {

                    //多节点
                    if (mDocResultInfo.getResult() != null && mDocResultInfo.getResult().getListTrackInfo() != null && mDocResultInfo.getResult().getListTrackInfo().size() != 0) {
                        LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
                        for (TrackInfo mListTrackInfo : mDocResultInfo.getResult().getListTrackInfo()) {
                            hashMap.put(mListTrackInfo.getTrackId(), mListTrackInfo.getTrackName());
                        }
                        new ActionSheetDialog(this)
                                .builder()
                                .setTitle(mDocResultInfo.getMessage())
                                .setCancelable(false)
                                .setCanceledOnTouchOutside(false)
                                .addSheetItemMap(hashMap)
                                .setOnSheetItemClickListener(
                                        new ActionSheetDialog.OnSheetItemClickListener() {
                                            @Override
                                            public void onClick(HashMap<String, String> map) {
                                                if (map == null || map.size() == 0) {
                                                    WorkFlowFormDetalActivity.this.finish();
                                                }
                                                for (String key : map.keySet()) {
                                                    mDocInfoParameters.trackId = key;
//                                                    mDocInfoModel.getDataFromServerByType(DocInfoModel.TYPE_GET_DETAILTASK, mDocInfoParameters);
                                                    netWorkManager.logFunactionStart(WorkFlowFormDetalActivity.this, WorkFlowFormDetalActivity.this, "functionStart", LogManagerEnum.APP_DOC_INFO.functionCode);
                                                }
                                            }
                                        }).show();
                        return;
                    }
                    if (mDocResultInfo.getResult() == null || mDocResultInfo.code != 200) {
                        if(null != mDocResultInfo && null != mDocResultInfo.getMessage())
                        Toast.makeText(this, mDocResultInfo.getMessage(), Toast.LENGTH_SHORT).show();
                        hideLoadingView();
                        exit();
                        return;
                    }

                    formId = mDocResultInfo.getResult().getFlowId();
                    mDocResultInfo.getResult().setSystemCode(getDocKind());
                    mViewPager_mycollection = (MainViewPager) findViewById(R.id.viewPager_qarank);
                    fm = getSupportFragmentManager();
                    mViewPager_mycollection.setOffscreenPageLimit(4);
                    mMyTopTabIndicator = (NewTopTabIndicator) this.findViewById(R.id.topTabIndicator_detail);

                    list = new ArrayList<WorkFlowForCollectionAdapter.ChildType>();
                    List<String> listStr = new ArrayList<String>();
                    listTab = new ArrayList<InfoTab>();

                    if (mDocResultInfo != null && mDocResultInfo.getResult() != null && mDocResultInfo.getResult().TabItems != null) {

                        // for 循环内的都是tab页签里面的  也就是表单配置器里面配置的  无论实际有没有放正文或者附件文件 都要显示出tab页  宁可是空的
                        for (int i = 0; i < mDocResultInfo.getResult().TabItems.size(); i++) {

                            if (mDocResultInfo.getResult().TabItems.get(i).tabName != null) {
                                //过滤掉无效的tab
                                if (mDocResultInfo.getResult().TabItems.get(i).tabType == 1) {
                                    InfoRegion[] regions = mDocResultInfo.getResult().TabItems.get(i).regions;
                                    if (regions == null)
                                        continue;
                                    if (regions.length == 1) {
                                        if (regions[0].getFieldItems() == null || regions[0].getFieldItems().length == 0) {
                                            continue;
                                        }
                                    }
                                }
                                /**
                                 * TabType
                                 *
                                 1: 慧通原生【表单】
                                 2: 点聚AIP签批【表单】
                                 3: URL网页【表单】
                                 4：OWA网页【正文】
                                 5: 原生【正文】
                                 6: 【附件】
                                 7: 【流程】
                                 8: 点聚AIP签批【正文】
                                 9: 金格PDF签批【表单】
                                 10: 金格PDF签批【正文】
                                 11: 数科OFD签批【表单】
                                 12: 数科OFD签批【正文】

                                 */

                                String tabName = mDocResultInfo.getResult().TabItems.get(i).tabName;
                                int tabType = mDocResultInfo.getResult().TabItems.get(i).tabType;

                                if (tabType == 1) {//慧通原生【表单】
                                    listStr.add(tabName);
                                    listTab.add(mDocResultInfo.getResult().TabItems.get(i));
                                    list.add(WorkFlowForCollectionAdapter.ChildType.TAB_FORM);
                                } else if (tabType == 2 && !comeShare.equals("1")) {//点聚AIP签批【表单】
//                                    listStr.add(tabName);
//                                    listTab.add(mDocResultInfo.getResult().TabItems.get(i));
//                                    if (mDocResultInfo.getResult().TabItems.get(i).formKey.equals("zhengwei")) {
//                                        list.add(WorkFlowForCollectionAdapter.ChildType.TAB_DOCAIP);
//                                    } else {
//                                        list.add(WorkFlowForCollectionAdapter.ChildType.TAB_AIP);
//                                    }
                                } else if (tabType == 3 && !comeShare.equals("1")) {//URL网页【表单】
                                    DocviewUrl = mDocResultInfo.getResult().TabItems.get(i).tabContent;
                                    listStr.add(tabName);
                                    listTab.add(mDocResultInfo.getResult().TabItems.get(i));
                                    list.add(WorkFlowForCollectionAdapter.ChildType.TAB_URL);
                                } else if (tabType == 4 && !comeShare.equals("1")) {//OWA网页【正文】

                                    mDocAttachmentID = mDocResultInfo.getResult().getDocAttachmentID();
                                    docAttachmentType = mDocResultInfo.getResult().getDocAttachmentType();
                                    listStr.add("正文");
//                                    if (isTextUrl == 2) {
//                                        isDocUrl = true;
//                                    } else if (isTextUrl == 1) {
//                                        isDocUrl = false;
//                                    }
                                    list.add(WorkFlowForCollectionAdapter.ChildType.TAB_URL);
                                    listTab.add(mDocResultInfo.getResult().TabItems.get(i));
                                } else if (tabType == 5 && !comeShare.equals("1")) {//原生【正文】
                                    mDocAttachmentID = mDocResultInfo.getResult().getDocAttachmentID();
                                    docAttachmentType = mDocResultInfo.getResult().getDocAttachmentType();
                                    listStr.add("正文");
                                    list.add(WorkFlowForCollectionAdapter.ChildType.TAB_TEXT);
                                    listTab.add(mDocResultInfo.getResult().TabItems.get(i));
                                } else if (tabType == 6 && !comeShare.equals("1")) {//【附件】
                                    listStr.add("附件");
                                    list.add(WorkFlowForCollectionAdapter.ChildType.TAB_ATTACHMENT);
                                    listTab.add(mDocResultInfo.getResult().TabItems.get(i));
                                } else if (tabType == 7 && !comeShare.equals("1")) {//【流程】
                                    listStr.add("流程");
                                    list.add(WorkFlowForCollectionAdapter.ChildType.TAB_FLOW);
                                    listTab.add(mDocResultInfo.getResult().TabItems.get(i));
                                } else if (tabType == 8 && !comeShare.equals("1")) {//点聚正文

                                } else if (tabType == 9 && !comeShare.equals("1")) {//金格PDF签批【表单】

                                } else if (tabType == 10 && !comeShare.equals("1")) {//金格PDF签批【正文】
                                    listStr.add("签批");
                                    list.add(WorkFlowForCollectionAdapter.ChildType.TAB_PDF_SIGN);
                                    listTab.add(mDocResultInfo.getResult().TabItems.get(i));
                                    hasPdfUrl = true;
                                } else if (tabType == 11 && !comeShare.equals("1")) {//数科OFD签批【表单】
                                    mDocAttachmentID = mDocResultInfo.getResult().getDocAttachmentID();
                                    docAttachmentType = mDocResultInfo.getResult().getDocAttachmentType();
                                    listStr.add("表单");
                                    list.add(WorkFlowForCollectionAdapter.ChildType.TAB_OFD_FORM);
                                    listTab.add(mDocResultInfo.getResult().TabItems.get(i));
                                } else if (tabType == 12 && !comeShare.equals("1")) {//数科OFD签批【正文】
                                    mDocAttachmentID = mDocResultInfo.getResult().getDocAttachmentID();
                                    docAttachmentType = mDocResultInfo.getResult().getDocAttachmentType();
                                    listStr.add("正文");
                                    list.add(WorkFlowForCollectionAdapter.ChildType.TAB_OFD_DOC);
                                    listTab.add(mDocResultInfo.getResult().TabItems.get(i));
                                }
                            }
                        }

                    }

                    String[] arrayTopTabIndicator = new String[listStr.size()];
                    listStr.toArray(arrayTopTabIndicator);
                    mMyTopTabIndicator.setCommonData2(mViewPager_mycollection, arrayTopTabIndicator, R.color.color_title, R.color.color_ff888888);
                    if (hasPdfUrl) {
                        getSignPdfInfo();
                    } else {
                        mAdapter = new WorkFlowForCollectionAdapter(HtmitechApplication.instance().getApplicationContext(), fm, list, mDocResultInfo.getResult().getDocID(), listTab, app_id, com_workflow_mobileconfig_include_options, localActivityManager);
                        mViewPager_mycollection.setAdapter(mAdapter);
                    }

                    mViewPager_mycollection.setNoScroll(true);
                    menuMultipleActions = (AddFloatingActionButton) findViewById(R.id.function);

                    if (mDocResultInfo.getResult().getListActionInfo() == null || mDocResultInfo.getResult().getListActionInfo().size() == 0 || comeShare.equals("1")) {
                        menuMultipleActions.setVisibility(View.GONE);
                    } else {
                        menuMultipleActions.setVisibility(View.VISIBLE);
                        mFunctionPopupWindow = new FunctionPopupWindow(this, new MenuOnClickListener(), mDocResultInfo.getResult().getListActionInfo().size());
                        mFunctionPopupWindow.initArcMenu(mDocResultInfo.getResult().getListActionInfo(), TodoFlag);
                    }

                    menuMultipleActions.setOnClickListener(this);
                    menuMultipleActions.setOnTouchListener(this);
                    menuMultipleActions.setSize(FloatingActionButton.SIZE_NORMAL);

                    switch (mBottomButtonSlyteEnum) {
                        case DRAG:
                            layout_buttom.setVisibility(View.GONE);
                            hs_scrollview.setVisibility(View.GONE);
                            if (mDocResultInfo.getResult().getListActionInfo() == null || mDocResultInfo.getResult().getListActionInfo().size() == 0) {
                                menuMultipleActions.setVisibility(View.GONE);
                            } else {
                                menuMultipleActions.setVisibility(View.VISIBLE);
                            }
                            break;
                        case BOTTOM:
                            if (comeShare.equals("1")) {
                                break;
                            }
                            layout_buttom.setVisibility(View.VISIBLE);
                            hs_scrollview.setVisibility(View.VISIBLE);
                            menuMultipleActions.setVisibility(View.GONE);
                            if (isShare == 0) {
                                if(null != mDocResultInfo && null != mDocResultInfo.getResult() &&  null != mDocResultInfo.getResult().getListActionInfo()){
                                    for (int i = 0; i < mDocResultInfo.getResult().getListActionInfo().size(); i++) {
                                        if (mDocResultInfo.getResult().getListActionInfo().get(i).getActionID().equals("Share")) {
                                            mDocResultInfo.getResult().getListActionInfo().remove(i);
                                        }
                                    }
                                }

                            }
                            initArcMenu(mDocResultInfo.getResult().getListActionInfo());
                            break;
                    }
                    netWorkManager.logFunactionFinsh(WorkFlowFormDetalActivity.this, WorkFlowFormDetalActivity.this, "functionStartFail", LogManagerEnum.APP_DOC_INFO.functionCode, mDocResultInfo.getMessage(), INetWorkManager.State.SUCCESS);
                } else {
                    netWorkManager.logFunactionFinsh(WorkFlowFormDetalActivity.this, WorkFlowFormDetalActivity.this, "functionStartFail", LogManagerEnum.APP_DOC_INFO.functionCode, "返回实体为空", INetWorkManager.State.FAIL);
                }
            } else {
                netWorkManager.logFunactionFinsh(WorkFlowFormDetalActivity.this, WorkFlowFormDetalActivity.this, "functionStartFail", LogManagerEnum.APP_DOC_INFO.functionCode, "返回参数为空", INetWorkManager.State.FAIL);
            }
            hideLoadingView();
        } else if (PDFSIGN_INFO.equals(requestName)) {
            try {
                PdfSignInfoRootResult pdfSignInfoRootResult = JSONObject.parseObject(requestValue, PdfSignInfoRootResult.class);
                if (null != pdfSignInfoRootResult && null != pdfSignInfoRootResult.result && !TextUtils.isEmpty(pdfSignInfoRootResult.result.downloadUrl)) {
                    startDownload(pdfSignInfoRootResult.result);
                } else {
                    mAdapter = new WorkFlowForCollectionAdapter(HtmitechApplication.instance().getApplicationContext(), fm, list, mDocResultInfo.getResult().getDocID(), listTab, app_id, com_workflow_mobileconfig_include_options, localActivityManager);
                    mViewPager_mycollection.setAdapter(mAdapter);
                }
            } catch (Exception e) {
                mAdapter = new WorkFlowForCollectionAdapter(HtmitechApplication.instance().getApplicationContext(), fm, list, mDocResultInfo.getResult().getDocID(), listTab, app_id, com_workflow_mobileconfig_include_options, localActivityManager);
                mViewPager_mycollection.setAdapter(mAdapter);
                e.printStackTrace();
            }
        } else if (PDFSIGN_UPLOAD.equals(requestName)) {
            String s = requestValue;
        } else if (DO_ACTION.equals(requestName)) {//提交
            requestValue = GetFinalRefreshTokenAfterRequestValue.getFinalRequestValue(this, requestValue, type, doActionPath, mDoActionParameter, this, requestName, LogManagerEnum.APP_DO_ACTION.functionCode);
            setmenuMultipleActionsCanClick();
            if(hasPdfUrl){
                uploadSignPdfFile();
            }
            hideLoadingView();
            if (!TextUtils.isEmpty(requestValue)) {
                mDoActionResultInfo = FastJsonUtils.getPerson(requestValue, DoActionResultInfo.class);
                if (null != mDoActionResultInfo && null != mDoActionResultInfo.getResult()) {
                    // 办理成功返回
                    if (mDoActionResultInfo.getResult().getResultCode().equals("2")) {// 操作：提交--选择下一节点 选择路由
                        String title = mDoActionResultInfo.getResult().getResultInfo();
                        IsMultiSelectRoute = mDoActionResultInfo.getResult().isMultiSelectRoute();//判断是否是多选路由
                        int sum = 0;
                        if (null != mDoActionResultInfo.getResult().getListRouteInfo()) {
                            sum = mDoActionResultInfo.getResult().getListRouteInfo().length;
                        }
                        LinkedHashMap<String, String> routeHashMap = new LinkedHashMap<String, String>();
                        for (int i = 0; i < sum; i++) {//多路由
                            routeHashMap.put(mDoActionResultInfo.getResult().getListRouteInfo()[i].getRouteID(), mDoActionResultInfo.getResult().getListRouteInfo()[i].getRouteName());
                        }
                        ActionSheetDialog actionSheetDialog = new ActionSheetDialog(this)
                                .builder()
                                .setTitle(title)
                                .setCancelable(false)
                                .setCanceledOnTouchOutside(false)
                                .addSheetItemMap(routeHashMap)
                                .setOnSheetItemClickListener(
                                        new ActionSheetDialog.OnSheetItemClickListener() {
                                            @Override
                                            public void onClick(HashMap<String, String> map) {
                                                if (map != null && map.size() != 0) {
                                                    List<SelectRouteInfo> SelectRoutes = new ArrayList<SelectRouteInfo>();
                                                    for (String key : map.keySet()) {
                                                        SelectRouteInfo selectRouteInfo = new SelectRouteInfo();
                                                        String value = map.get(key);
                                                        selectRouteInfo.routeId = key;
                                                        selectRouteInfo.routeName = value;
                                                        SelectRoutes.add(selectRouteInfo);
                                                    }
                                                    //提交选择的路由到服务器，得到路由下对应的人员
                                                    handle_doAction_hasRoute(SelectRoutes);
                                                } else {
                                                    netWorkManager.logFunactionFinsh(WorkFlowFormDetalActivity.this, WorkFlowFormDetalActivity.this, "functionDetailFail", LogManagerEnum.APP_DO_ACTION.functionCode, mDoActionResultInfo.getMessage(), INetWorkManager.State.CANCEL);
                                                }
                                            }
                                        });

                        if (!IsMultiSelectRoute) {//单路由
                            actionSheetDialog.setChooseWay(ChooseWay.SINGLE_CHOOSE).show();
                        } else {
                            actionSheetDialog.setChooseWay(ChooseWay.MORE_CHOOSE).show();
                        }
                    } else if (mDoActionResultInfo.getResult().getResultCode().equals("4")) {// 选人
//                        String title = mDoActionResultInfo.getResult().getResultInfo();
                        if (null != mDoActionResultInfo.getResult().getListRouteInfo() && null != mDoActionResultInfo.getResult()) {
                            if (mDoActionResultInfo.getResult().getListRouteInfo().length > 1) {//多于一个说明需要出来新增的多路由人员选择界面
                                Intent intentSelectPeople = new Intent(WorkFlowFormDetalActivity.this, MuliteRouteSelectPeopleActivity.class);
                                ArrayList<RouteInfo> ListRouteInfo = new ArrayList<RouteInfo>();
                                for (int i = 0; i < mDoActionResultInfo.getResult().getListRouteInfo().length; i++) {
                                    ListRouteInfo.add(mDoActionResultInfo.getResult().getListRouteInfo()[i]);
                                }
                                intentSelectPeople.putExtra("ListRouteInfo", ListRouteInfo);
//                                intentSelectPeople.putExtra("title", title);
                                startActivityForResult(intentSelectPeople, 1000);
                            } else if (mDoActionResultInfo.getResult().getListRouteInfo().length == 1) {//如果只有一个说明不需要出来人新增的多路由人员选择界面
                                final RouteInfo routeInfo = mDoActionResultInfo.getResult().getListRouteInfo()[0];
                                String title = "";
                                if (routeInfo != null) {
                                    title = routeInfo.getRouteName();
                                }
                                //冗余处理，防止不让本地选择人员，但是没有返回人员集合的情况下，造成死循环。
                                if (!routeInfo.isAllowSelectUser && routeInfo.isFreeSelectUser && (routeInfo.getRouteAuthors() == null || routeInfo.getRouteAuthors().size() == 0)) {
                                    routeInfo.isAllowSelectUser = true;
                                }
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
                                    BookInit.getInstance().setCallCheckUserListener(WorkFlowFormDetalActivity.this, ChooseWayEnum.PEOPLECHOOSE, (!routeInfo.isMultiSelectUser ? ChooseWay.SINGLE_CHOOSE : ChooseWay.MORE_CHOOSE), ChooseTreeHierarchy
                                            .HIERARCHY, ChooseSystemBook.SYSTEM, title, routeInfo.isFreeSelectUser, userList, new CallCheckAllUserListener() {
                                        @Override
                                        public void checkAll(ArrayList<SYS_User> checkAllUser, ArrayList<SYS_Department> checkAllDepartment) {
                                            if (checkAllUser != null && checkAllUser.size() != 0) {
                                                ArrayList<AuthorInfo> mAuthorInfoList = new ArrayList<AuthorInfo>();
                                                for (SYS_User mSYS_User : checkAllUser) {
                                                    AuthorInfo mAuthorInfo = new AuthorInfo();
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

                    } else if (mDoActionResultInfo.getResult().getResultCode().equals("84")) {
                        //不适合在手机端办理
                        mNewFragment = MyAlertDialogFragment.newInstance(mDoActionResultInfo.getResult().getResultInfo(), R.drawable.prompt_warn, null, confirmListener, false);
                        mNewFragment.show(getSupportFragmentManager(), "dialog");
                    } else if (mDoActionResultInfo.getResult().getResultCode().equals("9")) {
                        mNewFragment = MyAlertDialogFragment.newInstance(mDoActionResultInfo.getResult().getResultInfo(), R.drawable.prompt_warn, null, confirmListener, false);
                        mNewFragment.show(getSupportFragmentManager(), "dialog");
                    } else if (mDoActionResultInfo.getResult().getResultCode().equals("0")) {// 办理成功
                        BadgeAllUnit.get().clearBadgeUnit();
                        RefreshTotal.addReshActivity();
                        if (menuViewTag.equalsIgnoreCase("save") || menuViewTag.equalsIgnoreCase("submit")) {
                            try {
                                uploadSignFile();
                            } catch (Exception e) {
                                doneFinish();
                                e.printStackTrace();
                            }
                        } else {
                            hideLoadingView();
//                            ClassEvent mClassEvent = new ClassEvent();
//                            mClassEvent.msg = "TextViewSize";
//                            EventBus.getDefault().post(mClassEvent);
                            if (!isSave && !isReject) {
                                Toast.makeText(WorkFlowFormDetalActivity.this, mDoActionResultInfo.getMessage(), Toast.LENGTH_LONG).show();
                            }
                            if (isReject) {
                                Toast.makeText(WorkFlowFormDetalActivity.this, "退回上一节点成功", Toast.LENGTH_LONG).show();
                                isReject = false;
                            }
                            isSave = false;
                            Intent intent = getIntent();
                            intent.putExtra("kind", docKind);
                            setResult(ActivityResultConstant.DOACTION_RESULT_OK, getIntent());
                            netWorkManager.logFunactionFinsh(WorkFlowFormDetalActivity.this, WorkFlowFormDetalActivity.this, "functionDetailFail", LogManagerEnum.APP_DO_ACTION.functionCode, mDoActionResultInfo.getMessage(), INetWorkManager.State.SUCCESS);
                            exit();
                            if (formMap.size() > 0)
                                SaveFiles();
                        }

                    } else {
                        mNewFragment = MyAlertDialogFragment.newInstance(mDoActionResultInfo.getResult().getResultInfo(), R.drawable.prompt_warn, null, confirmListener, false);
                        mNewFragment.show(getSupportFragmentManager(), "dialog");
                        mFormExtensionFilesDao = new FormExtensionFilesDao(WorkFlowFormDetalActivity.this);
                        mFormExtensionFilesDao.deleteExtensionFiles();
                    }
                } else {
                    Toast.makeText(this, "操作失败", Toast.LENGTH_SHORT).show();
                    netWorkManager.logFunactionFinsh(WorkFlowFormDetalActivity.this, WorkFlowFormDetalActivity.this, "functionStartFail", LogManagerEnum.APP_DO_ACTION.functionCode, "", INetWorkManager.State.FAIL);
                }

            } else {
                netWorkManager.logFunactionFinsh(WorkFlowFormDetalActivity.this, WorkFlowFormDetalActivity.this, "functionStartFail", LogManagerEnum.APP_DO_ACTION.functionCode, "返回参数为空", INetWorkManager.State.FAIL);
            }
        } else if (ATTENTION_FLOW.equals(requestName)) {
            requestValue = GetFinalRefreshTokenAfterRequestValue.getFinalRequestValue(this, requestValue, type, attentionPath, attentionParameters, this, requestName, LogManagerEnum.WORKFLOWATTENTION.functionCode);
            if (!TextUtils.isEmpty(requestValue)) {
                AttentionResult attentionResult = FastJsonUtils.getPerson(requestValue, AttentionResult.class);
                if (null != attentionResult) {

//                    if (attentionResult.result.equals("1")) {
//                        Toast.makeText(getApplicationContext(), "添加关注成功", Toast.LENGTH_SHORT).show();
//                        setResult(ActivityResultConstant.DOACTION_RESULT_OK, getIntent());
//                    } else if (attentionResult.result.equals("0")) {
//                        Toast.makeText(getApplicationContext(), "取消关注成功", Toast.LENGTH_SHORT).show();
//                        setResult(ActivityResultConstant.DOACTION_RESULT_OK, getIntent());
//                    }

                    if (attentionResult.result.equals("1")) {
                        for (int i = 0; i < mDocResultInfo.getResult().getListActionInfo().size(); i++) {
                            if (mDocResultInfo.getResult().getListActionInfo().get(i).getActionName().equals("添加关注")) {
                                mDocResultInfo.getResult().getListActionInfo().get(i).setActionName("取消关注");
                                Toast.makeText(getApplicationContext(), "添加关注成功", Toast.LENGTH_SHORT).show();
                                setResult(ActivityResultConstant.DOACTION_RESULT_OK, getIntent());
                                break;
                            }
                            if (mDocResultInfo.getResult().getListActionInfo().get(i).getActionName().equals("取消关注")) {
                                mDocResultInfo.getResult().getListActionInfo().get(i).setActionName("添加关注");
                                Toast.makeText(getApplicationContext(), "取消关注成功", Toast.LENGTH_SHORT).show();
                                setResult(ActivityResultConstant.DOACTION_RESULT_OK, getIntent());
                                break;
                            }
                        }
                    }
                    initArcMenu(mDocResultInfo.getResult().getListActionInfo());//重新刷新底部tab关注状态
                    netWorkManager.logFunactionFinsh(WorkFlowFormDetalActivity.this, WorkFlowFormDetalActivity.this, "functionDetailFail", LogManagerEnum.WORKFLOWATTENTION.functionCode, "添加关注成功", INetWorkManager.State.SUCCESS);

                } else {
                    netWorkManager.logFunactionFinsh(WorkFlowFormDetalActivity.this, WorkFlowFormDetalActivity.this, "functionDetailFail", LogManagerEnum.WORKFLOWATTENTION.functionCode, "添加关注失败", INetWorkManager.State.FAIL);
                }
            } else {
                netWorkManager.logFunactionFinsh(WorkFlowFormDetalActivity.this, WorkFlowFormDetalActivity.this, "functionDetailFail", LogManagerEnum.WORKFLOWATTENTION.functionCode, "添加关注失败", INetWorkManager.State.FAIL);
            }
            hideLoadingView();
        }
    }

    @Override
    public void fail(String exceptionMessage, int type, String requestName) {
        if (requestName.equals("functionStart")) {
            AnsynHttpRequest.requestByPostWithToken(this, mDocInfoParameters, getDocInfoPath, CHTTP.POSTWITHTOKEN, this, GET_DOCINFO, LogManagerEnum.APP_DOC_INFO.functionCode);
        } else if ("doActionfunctionStart".equals(requestName)) {
            setmenuMultipleActionsCanNotClick();
            AnsynHttpRequest.requestByPostWithToken(this, mDoActionParameter, doActionPath, CHTTP.POSTWITHTOKEN, this, DO_ACTION, LogManagerEnum.APP_DO_ACTION.functionCode);
        } else if (requestName.equals("functionDetail")) {
            functionDetail();
        } else if (requestName.equals("login_home_back")) {
            Log.d("DetailActivtiy", "唤醒失败");
        } else if (requestName.equals(LogManagerEnum.WORKFLOWATTENTION.functionCode)) {
            AttentionLog();
        } else if (GET_DOCINFO.equals(requestName)) {//获取表单详情
            try {
                mDocResultInfo = new DocResultInfo();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (mDocResultInfo.getMessage() != null) {
                mNewFragment = MyAlertDialogFragment.newInstance(exceptionMessage, R.drawable.prompt_warn, mDialogCancelListener, confirmAndExitListener, false);
                try {
                    mNewFragment.show(getSupportFragmentManager(), "dialog");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            hideLoadingView();
        } else if (DO_ACTION.equals(requestName)) {
            if(hasPdfUrl){
                uploadSignPdfFile();
            }
            mDoActionResultInfo = new DoActionResultInfo();
            if (mDoActionResultInfo.getResult() != null) {
                mNewFragment = MyAlertDialogFragment.newInstance("失败:" + exceptionMessage + "(" + mDoActionResultInfo.getResult().getResultCode() + ")", R.drawable.prompt_warn, null, confirmListener, false);
                mNewFragment.show(getSupportFragmentManager(), "dialog");
            } else {
                Toast.makeText(WorkFlowFormDetalActivity.this, "操作失败！", Toast.LENGTH_SHORT).show();
            }
            setmenuMultipleActionsCanClick();
            hideLoadingView();
        } else if (ATTENTION_FLOW.equals(requestName)) {
            hideLoadingView();
            netWorkManager.logFunactionFinsh(WorkFlowFormDetalActivity.this, WorkFlowFormDetalActivity.this, "functionDetailFail", LogManagerEnum.WORKFLOWATTENTION.functionCode, exceptionMessage, INetWorkManager.State.FAIL);
        } else if (PDFSIGN_INFO.equals(requestName)) {
            mAdapter = new WorkFlowForCollectionAdapter(HtmitechApplication.instance().getApplicationContext(), fm, list, mDocResultInfo.getResult().getDocID(), listTab, app_id, com_workflow_mobileconfig_include_options, localActivityManager);
            mViewPager_mycollection.setAdapter(mAdapter);
        }

        if (!Utils.isNetworkAvailable()) {
            mEmptyLayout.setNoWifiButtonClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    showLoadingView();
                    mEmptyLayout.hide();
                    try {
                        netWorkManager.logFunactionStart(WorkFlowFormDetalActivity.this, WorkFlowFormDetalActivity.this, "functionStart", LogManagerEnum.APP_DOC_INFO.functionCode);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            mEmptyLayout.showNowifi();
        } else if (!isOneSuccess) {
            mEmptyLayout.showError();
            isOneSuccess = false;
        }

    }

    @Override
    public void notNetwork() {

    }

    @Override
    public void callbackMainUI(String successMessage) {

    }


    public String getDocId() {
        String docId = getIntent().getStringExtra("DocId");
        return docId;
    }

    public String getDocType() {
        String docType = getIntent().getStringExtra("DocType");
        return docType;
    }

    //2015-08-11
    public String getDocKind() {
        String kind = getIntent().getStringExtra("Kind");
        return kind;
    }

    public String getMDocAttachmentID() {
        return mDocAttachmentID;
    }

    public String getDocAttachmentType() {
        return docAttachmentType;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgview_titlebar_back:
                exit();
            /*if (PreferenceUtils.getCurrentPage().equalsIgnoreCase(HomeSet.METRO_PAGE)) {
                ClientTabActivity.backFlag = true;
			}
			DetailActivity.this.finish();*/
                break;
            case R.id.function:
                if (!isTuoZhuai) {
                    if (mFunctionPopupWindow == null) {
                        return;
                    }
                    if (!mFunctionPopupWindow.isShowing()) {
//					menuMultipleActions.startAnimation(animation);
                        menuMultipleActions.setImageDrawable(getResources().getDrawable(R.drawable.btn_operation_homelabel_on));
                        if (isShare == 0) {
                            for (int i = 0; i < mDocResultInfo.getResult().getListActionInfo().size(); i++) {
                                if (mDocResultInfo.getResult().getListActionInfo().get(i).getActionID().equals("Share")) {
                                    mDocResultInfo.getResult().getListActionInfo().remove(i);
                                }
                            }
                        }
                        mFunctionPopupWindow = new FunctionPopupWindow(this, new MenuOnClickListener(), mDocResultInfo.getResult().getListActionInfo().size());
                        mFunctionPopupWindow.initArcMenu(mDocResultInfo.getResult().getListActionInfo(), TodoFlag);
                        popupWidth = mFunctionPopupWindow.mMenuView.getMeasuredWidth();
                        popupWidth = DeviceUtils.dip2px(this, 55) + popupWidth;
                        popupHeight = mFunctionPopupWindow.getHeight();
                        int[] location = new int[2];
                        v.getLocationOnScreen(location);
                        mFunctionPopupWindow.showAtLocation(v, Gravity.NO_GRAVITY, (location[0] - popupWidth), location[1] - popupHeight);
                        mFunctionPopupWindow.update();
                    } else {
                        menuMultipleActions.setImageDrawable(getResources().getDrawable(R.drawable.btn_operation_homelabel_off));
                        mFunctionPopupWindow.dismiss();
                    }
                }
                break;
            case R.id.buttonError: //重试按钮
//                showLoadingView();
//                mEmptyLayout.hide();
//                mDocInfoModel.getDataFromServerByType(DocInfoModel.TYPE_GET_DETAILTASK,
//                        mDocInfoParameters);
                exit();
                break;
        }
    }

    int popupHeight;
    int popupWidth;


    @Override
    public void onBackPressed() {
        exit();
    }

    /*
    * 获取签批文件信息
    * */
    public void getSignPdfInfo() {
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("docId", "HZ28d8a9647776ab0164781221ce0036");
//        jsonObject.put("appId", "25348506440962849");
//        jsonObject.put("userId", "25348506440962550");
//        jsonObject.put("signType", "10");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("docId", docId);
        jsonObject.put("appId", app_id);
        jsonObject.put("userId", PreferenceUtils.getEMPUserID(this));
        jsonObject.put("signType", "10");
        AnsynHttpRequest.requestByPostWithToken(this, jsonObject, getSignPdfUrl, CHTTP.POSTWITHTOKEN, this, PDFSIGN_INFO, LogManagerEnum.WORKFLOWATTENTION.functionCode);
    }

    /*
    * 上传签批文件
    * */
//    public String uploadPdfSignBase64Url = "http://192.168.88.33:8080/data-crab/gateway/flowClawController/transmit/uploadSignFileByBase64";
    public String uploadPdfSignBase64Url = ServerUrlConstant.SERVER_JAVA_URL() + ServerUrlConstant.UPLOAD_PDF_SIGN;

    public void uploadSignPdfFile() {
        try {
            SecuritySharedPreference sp = new SecuritySharedPreference(this, PreferenceUtils.PDFSIGNSAVE, Context.MODE_PRIVATE);
            boolean pdfsignsave = sp.getBoolean("pdfsignsave", false);
            if(pdfsignsave &&  null != mAdapter && null !=mAdapter.getPdfSignFile()){
                //判断是否在签批页面点击了保存按钮，如果保存了再提交
                String fileBody = Base64Utils.encodeBase64File(mAdapter.getPdfSignFile().getAbsolutePath());
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("appId", app_id);
                jsonObject.put("docId", docId);
                jsonObject.put("userId", PreferenceUtils.getEMPUserID(this));
                jsonObject.put("signType", "10");
//            jsonObject.put("fileId", "97226251340611590");
                jsonObject.put("fileBody", fileBody);
                AnsynHttpRequest.requestByPostWithToken(this, jsonObject, uploadPdfSignBase64Url, CHTTP.POSTWITHTOKEN, this, PDFSIGN_UPLOAD, LogManagerEnum.WORKFLOWATTENTION.functionCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
   * 正文附件下载
   * */
    private DownloadManager mDownloadManager;

    public void startDownload(PdfSignInfoResult mPdfSignInfoResult) {
        if (mDownloadManager == null) {
            mDownloadManager = new DownloadManager(
                    HtmitechApplication.instance());
        }
        try {
            if (null != mPdfSignInfoResult && !TextUtils.isEmpty(mPdfSignInfoResult.downloadUrl)) {
                String downLoadUrl = mPdfSignInfoResult.downloadUrl;
                String fileName = mPdfSignInfoResult.fileName;
                fileId = mPdfSignInfoResult.fileId;
                if (null != mDownloadManager && !TextUtils.isEmpty(downLoadUrl)) {
                    mDownloadManager.addNewDownload(downLoadUrl,
                            fileName,
                            CommonSettings.DEFAULT_DOCFILE_CACHE_FOLDER
                                    + File.separator + app_id + File.separator + fileId+".zip", false, false,
                            mRequestCallBack);
                }

            } else {
                mAdapter = new WorkFlowForCollectionAdapter(HtmitechApplication.instance().getApplicationContext(), fm, list, mDocResultInfo.getResult().getDocID(), listTab, app_id, com_workflow_mobileconfig_include_options, localActivityManager);
                mViewPager_mycollection.setAdapter(mAdapter);
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
    }

    private void unZipFile() {
        new Thread(new Runnable() {
            public void run() {
                try {
                    /** 解压 **/
                    File srcFile = new File(
                            CommonSettings.DEFAULT_DOCFILE_CACHE_FOLDER
                                    + File.separator + app_id + File.separator + fileId + ".zip");
                    final String destination = CommonSettings.DEFAULT_DOCFILE_CACHE_FOLDER
                            + File.separator + app_id + File.separator;
                    try {
                        FileNameGenerator fileNameGenerator = new MD5FileNameGenerator();
                        String password = fileNameGenerator.generate(fileId + fileNameGenerator.generate(fileId));
                        final File[] unzip = CompressUtil.unzip(srcFile, destination, password);
                        System.out.println("文件解压成功！！");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                mAdapter = new WorkFlowForCollectionAdapter(HtmitechApplication.instance().getApplicationContext(), fm, list, mDocResultInfo.getResult().getDocID(), listTab, app_id, com_workflow_mobileconfig_include_options, localActivityManager);
                                mAdapter.setPdfSignFile(unzip[0]);
                                mViewPager_mycollection.setAdapter(mAdapter);
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("文件解压失败！！");
                        mAdapter = new WorkFlowForCollectionAdapter(HtmitechApplication.instance().getApplicationContext(), fm, list, mDocResultInfo.getResult().getDocID(), listTab, app_id, com_workflow_mobileconfig_include_options, localActivityManager);
                        mViewPager_mycollection.setAdapter(mAdapter);
                    }

                } catch (Exception e) {
                    System.out.println("文件解压失败！！");
                    mAdapter = new WorkFlowForCollectionAdapter(HtmitechApplication.instance().getApplicationContext(), fm, list, mDocResultInfo.getResult().getDocID(), listTab, app_id, com_workflow_mobileconfig_include_options, localActivityManager);
                    mViewPager_mycollection.setAdapter(mAdapter);
                }
            }
        }).start();
    }


    private RequestCallBack<File> mRequestCallBack = new RequestCallBack<File>() {

        public void onStart() {

        }

        public void onCancelled() {
//            mMyTopTabIndicator.setCommonData2(mViewPager_mycollection,
//                    arrayTopTabIndicator, R.color.color_title,
//                    R.color.color_ff888888);
//            initAdapter(fm, list);
//            mAdapter.downLoadDocumentStatus(false);
            mAdapter = new WorkFlowForCollectionAdapter(HtmitechApplication.instance().getApplicationContext(), fm, list, mDocResultInfo.getResult().getDocID(), listTab, app_id, com_workflow_mobileconfig_include_options, localActivityManager);
            mViewPager_mycollection.setAdapter(mAdapter);
        }

        public void onLoading(long total, long current, boolean isUploading) {

        }

        @Override
        public void onSuccess(ResponseInfo<File> responseInfo) {
            unZipFile();
            Log.e("Tag", responseInfo.toString());
//            Toast.makeText(WorkFlowFormDetalActivity.this, "123", Toast.LENGTH_LONG).show();
//            mMyTopTabIndicator.setCommonData2(mViewPager_mycollection,
//                    arrayTopTabIndicator, R.color.color_title,
//                    R.color.color_ff888888);
//            initAdapter(fm, list);
//            mAdapter.downLoadDocumentStatus(true);
//            unZipFile(responseInfo.ge);
//            initPdf();
        }

        @Override
        public void onFailure(HttpException error, String msg) {
            Toast.makeText(WorkFlowFormDetalActivity.this, "正文下载失败！" + msg, Toast.LENGTH_LONG).show();
//            mMyTopTabIndicator.setCommonData2(mViewPager_mycollection,
//                    arrayTopTabIndicator, R.color.color_title,
//                    R.color.color_ff888888);
//            initAdapter(fm, list);
//            mAdapter.downLoadDocumentStatus(false);
            mAdapter = new WorkFlowForCollectionAdapter(HtmitechApplication.instance().getApplicationContext(), fm, list, mDocResultInfo.getResult().getDocID(), listTab, app_id, com_workflow_mobileconfig_include_options, localActivityManager);
            mViewPager_mycollection.setAdapter(mAdapter);

        }
    };

    /**
     * 退出
     **/
    public void exit() {
        RefreshTotal.addReshActivity();
        if (mLoadingView.getVisibility() == View.VISIBLE) {
            return;
        }

        //退出删除当前文件夹
        deleteFile(new File(CommonSettings.DEFAULT_DOCFILE_CACHE_FOLDER + File.separator + app_id));
        deleteFile(new File(CommonSettings.DEFAULT_ATTACHMENT_CACHE_FOLDER + File.separator + app_id));

//        //删除已经存在的正文
//        File filezip = new File(CommonSettings.DEFAULT_DOCFILE_CACHE_FOLDER + File.separator + app_id + File.separator + getMDocAttachmentID() + ".zip");
//        if (filezip.exists()) {
//            filezip.delete();
//        }
//
//        File file = new File(CommonSettings.DEFAULT_DOCFILE_CACHE_FOLDER + File.separator + app_id + File.separator + getMDocAttachmentID() + ".doc");
//        if (file.exists()) {
//            file.delete();
//        }
//
//        File fileofd = new File(CommonSettings.DEFAULT_DOCFILE_CACHE_FOLDER + File.separator + app_id + File.separator + getMDocAttachmentID() + ".ofd");
//        if (fileofd.exists()) {
//            file.delete();
//        }

        if (mAdapter != null) {
            for (int i = 0; i < mAdapter.getCount(); i++) {
                BaseFragment mFragment = (BaseFragment) mAdapter.getItem(i);
                if (null != mFragment && mFragment.getDownloadManager() != null) {
                    try {
                        if (null != mFragment && null != mFragment.getDownloadManager())
                            mFragment.getDownloadManager().stopAllDownload();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        ClassEvent mClassEvent = new ClassEvent();
        mClassEvent.msg = "angle";
        EventBus.getDefault().post(mClassEvent);
        finish();
    }

    private void deleteFile(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                File f = files[i];
                deleteFile(f);
            }
            file.delete();//如要保留文件夹，只删除文件，请注释这行
        } else if (file.exists()) {
            file.delete();
        }
    }

    private String uploadSignInfoPath = ServerUrlConstant.SERVER_JAVA_URL() + ServerUrlConstant.OA_UPLOAD_SIGNINFO_BYBASE_64;
    private HashMap<String, UploadSignFileEntity> hashMapUploadEntity = new HashMap<String, UploadSignFileEntity>();

    int currentFinish = 0;//当前上报文件和没有文件的个数 用于判断什么时候完成
    int allUploadSignCount = 0;
    HashMap<String, String> hashMapSignFile;

    private void uploadSignFile() throws Exception {
        showLoadingView();
        for (int i = 0; i < mAdapter.getCount(); i++) {
            BaseFragment mFragment = (BaseFragment) mAdapter.getItem(i);
            if (mFragment.getSingInfoMap() != null) {
                hashMapSignFile = new HashMap<String, String>();
                hashMapSignFile.clear();
                hashMapSignFile = mFragment.getSingInfoMap();
                String filePath = hashMapSignFile.get("fileurl");
                File fileofd = new File(filePath);
                if (fileofd.exists()) {
                    String base64String = Base64Utils.encodeBase64File(filePath);
                    UploadSignFileEntity entity = new UploadSignFileEntity();
                    entity.fileBody = base64String;
                    entity.appId = app_id;
                    entity.docId = docId;
//                    entity.docId = "HZ28ef826136afe00161371b218002f5";
                    entity.userId = OAConText.getInstance(HtmitechApplication.getApplication()).UserID;
                    entity.signType = hashMapSignFile.get("signType");
                    entity.fileId = hashMapSignFile.get("fileId");
                    String fileMd5Frist = hashMapSignFile.get("fileMd5Frist");
                    String fileMd5End = CRC32Utils.getCRC32(new File(filePath));
                    if (fileMd5Frist.equals(fileMd5End)) {
                        continue;
                    }
                    hashMapUploadEntity.put(hashMapSignFile.get("fragmenttype"), entity);
                    allUploadSignCount++;
                    AnsynHttpRequest.requestByPostWithToken(this, hashMapUploadEntity.get(hashMapSignFile.get("fragmenttype")), uploadSignInfoPath, CHTTP.POSTWITHTOKEN, new ObserverCallBackType() {

                        @Override
                        public void success(String requestValue, int type, String requestName) {
                            requestValue = GetFinalRefreshTokenAfterRequestValue.getFinalRequestValue(WorkFlowFormDetalActivity.this, requestValue, type, uploadSignInfoPath, hashMapUploadEntity.get(requestName), this, requestName, LogManagerEnum.APP_UP_LOAD_FILE.functionCode);
                            currentFinish++;
                            if (currentFinish == allUploadSignCount) {
                                doneFinish();
                            }
                        }

                        @Override
                        public void fail(String exceptionMessage, int type, String requestName) {
                            currentFinish++;
                            if (currentFinish == allUploadSignCount) {
                                doneFinish();
                            }
                        }

                        @Override
                        public void notNetwork() {
                            currentFinish++;
                            if (currentFinish == allUploadSignCount) {
                                doneFinish();
                            }
                        }

                        @Override
                        public void callbackMainUI(String successMessage) {

                        }
                    }, hashMapSignFile.get("fragmenttype"), LogManagerEnum.APP_UP_LOAD_FILE.functionCode);
                }
            }
        }
        if (0 == allUploadSignCount) {
            doneFinish();
        }
    }

    public void setmenuMultipleActionsCanClick() {
        if (menuMultipleActions != null) {
            menuMultipleActions.setFocusable(true);
            menuMultipleActions.setEnabled(true);
        }
    }

    public void setmenuMultipleActionsCanNotClick() {
        if (menuMultipleActions != null) {
            menuMultipleActions.setFocusable(false);
            menuMultipleActions.setEnabled(false);
        }
    }

    public void doneFinish() {
        setmenuMultipleActionsCanClick();
        hideLoadingView();
//        ClassEvent mClassEvent = new ClassEvent();
//        mClassEvent.msg = "TextViewSize";
//        EventBus.getDefault().post(mClassEvent);
        if (!isSave && !isReject) {
            Toast.makeText(WorkFlowFormDetalActivity.this, mDoActionResultInfo.getResult().getResultInfo(), Toast.LENGTH_LONG).show();
        }
        if (isReject) {
            Toast.makeText(WorkFlowFormDetalActivity.this, "退回上一节点成功", Toast.LENGTH_LONG).show();
            isReject = false;
        }
        isSave = false;
        Intent intent = getIntent();
        intent.putExtra("kind", docKind);
        setResult(ActivityResultConstant.DOACTION_RESULT_OK, getIntent());

        netWorkManager.logFunactionFinsh(WorkFlowFormDetalActivity.this, WorkFlowFormDetalActivity.this, "functionDetailFail", LogManagerEnum.APP_DO_ACTION.functionCode, mDoActionResultInfo.getMessage(), INetWorkManager.State.SUCCESS);
        exit();
        if (formMap.size() > 0) {
            try {
                SaveFiles();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    private void handle_doAction_hasRoute(List<SelectRouteInfo> SelectRoutes) {
        // 再次提交
        mDoActionParameter.appId = app_id;
        mDoActionParameter.selectRoutes = SelectRoutes;
        setmenuMultipleActionsCanNotClick();
        try {
            netWorkManager.logFunactionStart(this, this, "doActionfunctionStart", LogManagerEnum.APP_DO_ACTION.functionCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        showLoadingView();
    }

    @Override
    public void onSuccess(int requestTypeId, Object result) {

        if (requestTypeId == GetCommonFromInfoModel.TYPE_SAVEEXTFIELDS) {
            if (result != null && result instanceof GetSaveExtFieldsEntity) {
                GetSaveExtFieldsEntity mGetSaveExtFieldsEntity = (GetSaveExtFieldsEntity) result;
                mFormExtensionFilesDao = new FormExtensionFilesDao(WorkFlowFormDetalActivity.this);
                mFormExtensionFilesDao.deleteExtensionFiles();
                Log.d("Tag", "onSuccess: " + mGetSaveExtFieldsEntity.getResult().error_msg);
//                Toast.makeText(DetailActivity.this, mGetSaveExtFieldsEntity.getResult().error_msg, Toast.LENGTH_SHORT).show();
            }
        }

        hideLoadingView();
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
        formExtensionFilesList = new ArrayList<FormExtensionFiles>();
        mFormExtensionFilesDao = new FormExtensionFilesDao(WorkFlowFormDetalActivity.this);

        SaveBigFileExtFields saveBigFileExtFields = new SaveBigFileExtFields();

        saveBigFileExtFields.appId = app_id;

        saveBigFileExtFields.dataId = getDocId();

        saveBigFileExtFields.formId = formId;

        saveBigFileExtFields.userId = OAConText.getInstance(WorkFlowFormDetalActivity.this).UserID;


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

                    mExtfields.uniqueId = flowId + str;


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

                    mExtfields.uniqueId = flowId + str;

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
                            UploadBigFileFactory.get().createUploadTask(mPhotoModel.getOriginalPath(), "表单", formId, flowId + str, 0);
                    }
                } else if (formMap.get(str) instanceof AudioSelect4002) {
                    for (FilePickerMusic mFilePickerMusic : ((AudioSelect4002) formMap.get(str)).mListPickerAudioSelect) {
                        if (!mFilePickerMusic.isNet())
                            UploadBigFileFactory.get().createUploadTask(mFilePickerMusic.getPath(), "表单", formId, flowId + str, (int) (mFilePickerMusic.getDuration() / 1000));
                    }
                }

            }

            if ((menuViewTag.equalsIgnoreCase("submit") || menuViewTag.equalsIgnoreCase("save")) && saveBigFileExtFields.editFields.size() > 0) {
                ClickToast.get().showToast(this, Toast.LENGTH_LONG);
            }
        }

    }

    private DialogConfirmListener confirmListener = new DialogConfirmListener() {
        public void onConfirm(BaseDialog dialog) {
            netWorkManager.logFunactionFinsh(WorkFlowFormDetalActivity.this, WorkFlowFormDetalActivity.this, "functionDetailFail", LogManagerEnum.APP_DO_ACTION.functionCode, mDoActionResultInfo == null ? mDocResultInfo.getMessage() : mDoActionResultInfo.getMessage(), INetWorkManager.State.FAIL);
            mNewFragment.dismiss();
        }
    };

    private DialogConfirmListener confirmAndExitListener = new DialogConfirmListener() {
        public void onConfirm(BaseDialog dialog) {

            netWorkManager.logFunactionFinsh(WorkFlowFormDetalActivity.this, WorkFlowFormDetalActivity.this, "functionStartFail", LogManagerEnum.APP_DOC_INFO.functionCode, mDoActionResultInfo == null ? mDocResultInfo.getMessage() : mDoActionResultInfo.getMessage(), INetWorkManager.State.FAIL);
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


    private void initArcMenu(List<ActionInfo> actionList) {
        if (actionList != null) {
            layout_buttom.removeAllViews();
            int itemCount = actionList.size();
            for (int i = 0; i < itemCount; i++) {
                DrawableCenterTextView item = new DrawableCenterTextView(this);
                item.setTextSize(14);
                item.setTextColor(getResources().getColor(R.color.buttom_color));
                Drawable drawable = null;
                item.setGravity(Gravity.CENTER_VERTICAL);
                item.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));
                if (actionList.get(i).getActionID().equalsIgnoreCase("submit") && TodoFlag != null && TodoFlag.equals("0"))    //提交
                    drawable = getResources().getDrawable(R.drawable.btn_bottom_action_submit);
                else if (actionList.get(i).getActionID().equalsIgnoreCase("readed") && TodoFlag != null && TodoFlag.equals("0"))    //已读
                    drawable = getResources().getDrawable(R.drawable.btn_action_read);
                else if (actionList.get(i).getActionID().equalsIgnoreCase("addreader"))    //阅知
                    drawable = getResources().getDrawable(R.drawable.btn_action_yuezhi);
                else if (actionList.get(i).getActionID().equalsIgnoreCase("getback") && TodoFlag != null && TodoFlag.equals("0"))    //拿回
                    drawable = getResources().getDrawable(R.drawable.btn_action_takeback);
                else if (actionList.get(i).getActionID().equalsIgnoreCase("Share"))    //分享
                    drawable = getResources().getDrawable(R.drawable.btn_bottom_action_share);
                else if (actionList.get(i).getActionID().equalsIgnoreCase("save") && TodoFlag != null && TodoFlag.equals("0"))
                    drawable = getResources().getDrawable(R.drawable.btn_bottom_action_save);
                else if (actionList.get(i).getActionID().equalsIgnoreCase("reject"))
                    drawable = getResources().getDrawable(R.drawable.btn_bottom_action_save);
                else if (actionList.get(i).getActionID().equalsIgnoreCase("Attention"))
                    drawable = getResources().getDrawable(R.drawable.btn_bottom_action_attention);
                else {
                    drawable = getResources().getDrawable(R.drawable.btn_bottom_action_submit);
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

        if (actionList == null || actionList.size() == 0) {
            hs_scrollview.setVisibility(View.GONE);
        }
    }

    private void ShareListener() {
        // 弹选择窗
        //设置分享参数
        shareLink = new ShareLink();
        shareLink.setTitle("分享表单");
        shareLink.setDesc(docTitle);
        shareLink.setThumbnail(iconId);
        shareLink.setUrl(ServerUrlConstant.SERVER_BASE_URL() + ServerUrlConstant.OA_GETDOCINFO_METHOD);

        AlertDialog.Builder builder = new AlertDialog.Builder(
                WorkFlowFormDetalActivity.this);
        builder.setTitle("请选择分享位置");
        final String[] pos = {"分享给同事", "分享到工作组"};
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (curItem == 0) {
                    apiUrl = "aa" + apiUrlTmp;
                    shareLink.setAppUrl(apiUrl);
                    MXAPI.getInstance(WorkFlowFormDetalActivity.this).shareToChat(WorkFlowFormDetalActivity.this, shareLink);
                } else {
                    apiUrl = "bb" + apiUrlTmp;
                    shareLink.setAppUrl(apiUrl);
                    shareLink.setTitle("分享表单:" + docTitle);
                    MXAPI.getInstance(WorkFlowFormDetalActivity.this).shareToCircle(WorkFlowFormDetalActivity.this, shareLink);
                }
                curItem = 0;
            }
        });
        builder.setSingleChoiceItems(pos, 0,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        curItem = which;
                    }
                });
        builder.show();
    }


    private String menuViewTag;
    private String menuViewName;

    public void functionDetail() {
        //关按钮
        mFunctionPopupWindow.dismiss();
        menuMultipleActions.setImageDrawable(getResources().getDrawable(R.drawable.btn_operation_homelabel_off));
        //分享逻辑
        if (menuViewTag.equals("Share") && isShare == 1) {
            //调用分享逻辑
            ShareListener();
            return;
        }
        if (menuViewTag.equals("添加关注") || menuViewTag.equals("取消关注")) {
            AttentionFunction(menuViewTag.toString());
            return;
        }


        boolean isMustinputExcept = false;
        if (Constant.com_workflow_mobileconfig_check_mustinput_except.equalsIgnoreCase(menuViewTag)) {
            isMustinputExcept = true;
        }

        // TODO Auto-generated method stub
        showLoadingView();
        // 1,必填字段判读
        // 必填字段判断
        EditFieldList mustFieldList = EditFieldList.getInstance();
        final String name1 = PreferenceUtils.getOAUserName(WorkFlowFormDetalActivity.this);//拿到当前用户
        if (!isMustinputExcept) {
            for (int i = 0; i < mustFieldList.getList().size(); i++) {
                if (mustFieldList.getList().get(i).getIsExt()) {
                    continue;
                }
                if (mustFieldList.getList().get(i).getValue() == null || mustFieldList.getList().get(i).getValue().trim().equals("")) {
                    if (mustFieldList.getList().get(i).getIsExt()) {
                        continue;
                    }
                    SuperEditText.isChecked = true;
                    try {
                        if (mAdapter.isTabForm()) {
                            BaseFragment mBaseFragment = (BaseFragment) mAdapter.getItem(mAdapter.tabFormCount());
                            if (mBaseFragment instanceof WorkFlowFormDetailFragment) {
                                WorkFlowFormDetailFragment mWorkFlowFormDetailFragment = (WorkFlowFormDetailFragment) mBaseFragment;
                                mWorkFlowFormDetailFragment.getmLinearlayout_formdetail().switchInputType(mustFieldList.getList().get(i).getInput(), mustFieldList.getList().get(i).getKey());
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(WorkFlowFormDetalActivity.this, "必填字段 " + mustFieldList.getList().get(i).getNameDesc() + "尚未填写！", Toast.LENGTH_SHORT).show();
                    hideLoadingView();
                    return;
                }

                if (mustFieldList.getList().get(i).getInput() != null && !mustFieldList.getList().get(i).getInput().equals("")) {
                    int input = Integer.parseInt(mustFieldList.getList().get(i).getInput());
                    switch (input) {
                        case 2001:
                        case 2002:
                        case 2003:
                        case 2004:

                            if (mustFieldList.getList().get(i).tempValue.equals("")) {

                                try {
                                    if (mAdapter.isTabForm()) {
                                        BaseFragment mBaseFragment = (BaseFragment) mAdapter.getItem(mAdapter.tabFormCount());
                                        if (mBaseFragment instanceof WorkFlowFormDetailFragment) {
                                            WorkFlowFormDetailFragment mWorkFlowFormDetailFragment = (WorkFlowFormDetailFragment) mBaseFragment;
                                            mWorkFlowFormDetailFragment.getmLinearlayout_formdetail().switchInputType(mustFieldList.getList().get(i).getInput(), mustFieldList.getList().get(i).getKey());
                                        }
                                    }
                                } catch (Exception e) {

                                }
                                if (!mustFieldList.getList().get(i).getValue().contains(name1)) {
                                    SuperEditText.isChecked = true;
                                    Toast.makeText(WorkFlowFormDetalActivity.this, "必填字段 " + mustFieldList.getList().get(i).getNameDesc() + "尚未填写！", Toast.LENGTH_SHORT).show();
                                    hideLoadingView();
                                    return;
                                }


                            }
                            break;
                    }
                }

            }
        }

        mDoActionParameter = new DoActionParameter();
        mDoActionParameter.actionName = menuViewName;
        mDoActionParameter.actionId = menuViewTag.toString();
        if (menuViewTag.equalsIgnoreCase("save"))
            isSave = true;
        if (menuViewTag.equalsIgnoreCase("reject"))
            isReject = true;
        mDoActionParameter.userId = UserID;
        mDoActionParameter.docId = getIntent().getStringExtra("DocId"); // "HZ286f024cf9144b014d22ae386f495e";
        mDoActionParameter.systemCode = mDocResultInfo.getResult().getSystemCode();//2015-08-11
        mDoActionParameter.flowName = mDocResultInfo.getResult().getFlowName();
        mDoActionParameter.flowId = mDocResultInfo.getResult().getFlowId();
        mDoActionParameter.currentNodeId = mDocResultInfo.getResult().getCurrentNodeID();
        mDoActionParameter.currentTrackId = mDocResultInfo.getResult().getCurrentTrackId();
        String dataId = "";
        for (InfoTab mInfoTab : mDocResultInfo.getResult().getTabItems()) {
            if (!TextUtils.isEmpty(mInfoTab.dataId)) {
                dataId = mInfoTab.dataId;
                break;
            }
        }
        if (mDocResultInfo.getResult().getEditFields() != null && mDocResultInfo.getResult().getEditFields().size() > 0) {
            mDoActionParameter.editFields = new EditField[mDocResultInfo.getResult().getEditFields().size()];
            for (int j = 0; j < mDocResultInfo.getResult().getEditFields().size(); j++) {
                mDocResultInfo.getResult().getEditFields().get(j).setDataId(dataId);
                mDoActionParameter.editFields[j] = mDocResultInfo.getResult().getEditFields().get(j);
            }
        }
        mDoActionParameter.comments = comment;
        mDoActionParameter.appId = app_id;
        setmenuMultipleActionsCanNotClick();
        try {
            netWorkManager.logFunactionStart(this, this, "doActionfunctionStart", LogManagerEnum.APP_DO_ACTION.functionCode);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void checkImageSelect(ArrayList<PhotoModel> mSelectedImage, htmitech.com.componentlibrary.entity.Fielditems item, FieldItem workflow_item) {
        List<PhotoModel> listImgTemp = new ArrayList<PhotoModel>();
        if (mSelectedImage != null && mSelectedImage.size() > 0) {
            for (PhotoModel mPhotoModel : mSelectedImage) {
                mPhotoModel.setItem_workflow(workflow_item);
                listImgTemp.add(mPhotoModel);
            }
        }
        if ((formMap.get(workflow_item.getFieldId()) != null && (formMap.get(workflow_item.getFieldId()) instanceof SelectPhoto6001_6002_6101_6102))) {
            ((SelectPhoto6001_6002_6101_6102) (formMap.get(workflow_item.getFieldId()))).updateImg(this, listImgTemp, 1);
        }
    }

    public class MenuOnClickListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            menuViewTag = v.getTag().toString();
            if (v instanceof TextView) {
                menuViewName = ((TextView) v).getText().toString();
            } else if (v instanceof FloatingActionButton) {
                menuViewName = ((FloatingActionButton) v).getTitle();
            }

            if (menuViewTag.contains("关注")) {
                AttentionFunction(menuViewTag.trim().toString());
            } else {
                if (!TextUtils.equals(com_workflow_mobileconfig_confirmdialog, "0") && !menuViewName.equals("分享")) {
                    new com.htmitech.pop.AlertDialog(WorkFlowFormDetalActivity.this).builder().setTitle("提示").setMsg("是否确认【" + menuViewName + "】").setPositiveButton("是", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ensureHasUploadFile();
//                            netWorkManager.logFunactionStart(WorkFlowFormDetalActivity.this, WorkFlowFormDetalActivity.this, "functionDetail", LogManagerEnum.APP_DO_ACTION.functionCode);
                        }
                    }).setNegativeButton("否", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    }).show();
                } else {
                    ensureHasUploadFile();
                }
            }
            if (mFunctionPopupWindow != null)
                mFunctionPopupWindow.dismiss();
        }

    }

    public void ensureHasUploadFile() {
        int index = 0;
        if (formMap.size() > 0) {

            Set<String> set = formMap.keySet();

            for (String str : set) {// 遍历set去出里面的的Key

                if (formMap.get(str) instanceof SelectPhoto6001_6002_6101_6102) {
                    SelectPhoto6001_6002_6101_6102 mSelectPhoto6001_6002_6101_6102 = ((SelectPhoto6001_6002_6101_6102) formMap.get(str));
                    for (PhotoModel mPhotoModel : mSelectPhoto6001_6002_6101_6102.listImgs) {
                        if (!mPhotoModel.isNet())
                            index++;
                    }
                    if (index == 0) {
                        continue;
                    }

                } else if (formMap.get(str) instanceof AudioSelect4002) {

                    for (FilePickerMusic mFilePickerMusic : ((AudioSelect4002) formMap.get(str)).mListPickerAudioSelect) {
                        if (!mFilePickerMusic.isNet())
                            index++;
                    }
                    if (index == 0) {
                        continue;
                    }

                }

            }
        }

        if (index > 0) {
            if (NetConnectionUtils.hasNetWorkConnection(WorkFlowFormDetalActivity.this)) {//有网
                if (NetConnectionUtils.hasWifiConnection(WorkFlowFormDetalActivity.this)) {//有wifi
                    netWorkManager.logFunactionStart(WorkFlowFormDetalActivity.this, WorkFlowFormDetalActivity.this, "functionDetail", LogManagerEnum.APP_DO_ACTION.functionCode);
                } else {
                    int code = PreferenceUtils.getUploadBigFileNet(WorkFlowFormDetalActivity.this);
                    if (code == 2 || code == 3) {
                        noWifi();
                    }else{
                        netWorkManager.logFunactionStart(WorkFlowFormDetalActivity.this, WorkFlowFormDetalActivity.this, "functionDetail", LogManagerEnum.APP_DO_ACTION.functionCode);
                    }
                }
            }
        } else {
            netWorkManager.logFunactionStart(WorkFlowFormDetalActivity.this, WorkFlowFormDetalActivity.this, "functionDetail", LogManagerEnum.APP_DO_ACTION.functionCode);
        }
    }

    private NetPopupWindow popupWindow;

    //开始后发现没连接wifi的回掉
    public void noWifi() {
        //当前没有wifi，弹出popwindow
        popupWindow = new NetPopupWindow(this, new NetClickListener() {
            @Override
            public void onNetClick(String code) {
                netWorkManager.logFunactionStart(WorkFlowFormDetalActivity.this, WorkFlowFormDetalActivity.this, "functionDetail", LogManagerEnum.APP_DO_ACTION.functionCode);
            }
        });

        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        } else {
            //设置我们弹出的PopupWindow的位置，基于某个视图之下
            popupWindow.showAtLocation(mDetailActivityLayout, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        }
    }

    public void AttentionFunction(String ActionName) {
        this.ActionName = ActionName;
        netWorkManager.logFunactionStart(WorkFlowFormDetalActivity.this, WorkFlowFormDetalActivity.this, LogManagerEnum.WORKFLOWATTENTION);

    }

    public void AttentionLog() {
        final String tmpActionName = ActionName;
        OAConText instance = OAConText.getInstance(getApplicationContext());
        attentionParameters = new AttentionParameters();
        attentionParameters.userId = instance.UserID;
        attentionParameters.docId = getIntent().getStringExtra("DocId");
        if (ActionName.equals("添加关注")) {
            attentionParameters.attentionFlag = "1";
        } else if (ActionName.equals("取消关注")) {
            attentionParameters.attentionFlag = "0";
        }
        attentionParameters.allowPush = "1";
        attentionParameters.doctype = getIntent().getStringExtra("DocType");
        attentionParameters.docTitle = getIntent().getStringExtra("DocTitle");
        attentionParameters.sendFrom = getIntent().getStringExtra("sendFrom");
        attentionParameters.doctype = getIntent().getStringExtra("DocType");
        attentionParameters.sendDate = getIntent().getStringExtra("sendDate");
        attentionParameters.iconid = getIntent().getStringExtra("IconId");
        attentionParameters.flowId = mDocResultInfo.getResult().getFlowId();
        attentionParameters.systemCode = mDocResultInfo.getResult().getSystemCode();
        attentionParameters.appId = app_id;

        AnsynHttpRequest.requestByPostWithToken(this, attentionParameters, attentionPath, CHTTP.POSTWITHTOKEN, this, ATTENTION_FLOW, LogManagerEnum.WORKFLOWATTENTION.functionCode);

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
    private long startTime = 0;
    private long endTime = 0;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        ex = 0;
        ey = 0;
        switch (action) {
            case MotionEvent.ACTION_DOWN:

                startTime = System.currentTimeMillis();
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

                action_move = true;
                isTuoZhuai = true;
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
                if (top - (mFunctionPopupWindow.isShowing() ? popupHeight : 0) < 0) {
                    top = mFunctionPopupWindow.isShowing() ? popupHeight : 0;
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

                endTime = System.currentTimeMillis();
                ex = event.getX() - x;
                ey = event.getY() - y;
                //当从点击到弹起小于半秒的时候,则判断为点击,如果超过则不响应点击事件
                //以前是根据点击挪动距离来进行判断，有些手机敏感性强导致无法弹出等问题
                if ((endTime - startTime) > 0.1 * 2000L) {
                    isTuoZhuai = true;
                } else {
                    isTuoZhuai = false;
                }
                break;
        }
        return false;
    }

    @Override
    public void callBackLayout() {
        // TODO Auto-generated method stub
        if (action_move)
            menuMultipleActions.layout(left, top, right, bottom);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (HtmitechApplication.isHomeBack) {
            netWorkManager = LogManagerProxy.getProxyInstance(NetWorkManager.class);
            netWorkManager.logFunactionOnce(this, this, "login_home_back", LogManagerEnum.APP_RESUME.functionCode);
            HtmitechApplication.isHomeBack = false;
        }
        //注册监听按home键的广播
        RegistOrUnRegisterReceive.registerHomeKeyEventReceive(this);
    }

    @Override
    protected void onPause() {
        RegistOrUnRegisterReceive.unRegisterHomeKeyEventReceive(this);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BitmapFactoryUtil.get().recycleAll();//释放缓存中的数据

    }
}
