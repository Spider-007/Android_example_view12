package com.htmitech.emportal.ui.detail;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.util.Log;
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
import com.htmitech.addressbook.ClassEvent;
import com.htmitech.api.BookInit;
import com.htmitech.app.widget.DrawableCenterTextView;
import com.htmitech.commonx.util.DeviceUtils;
import com.htmitech.domain.SYS_Department;
import com.htmitech.domain.SYS_User;
import com.htmitech.edittext.SuperEditText;
import com.htmitech.emportal.HtmitechApplication;
import com.htmitech.emportal.R;
import com.htmitech.emportal.base.IBaseCallback;
import com.htmitech.emportal.common.CommonSettings;
import com.htmitech.emportal.entity.ActionInfo;
import com.htmitech.emportal.entity.AuthorInfo;
import com.htmitech.emportal.entity.DoActionParameter;
import com.htmitech.emportal.entity.DoActionResultInfo;
import com.htmitech.emportal.entity.DocInfoParameters;
import com.htmitech.emportal.entity.DocResultInfo;
import com.htmitech.emportal.entity.EditField;
import com.htmitech.emportal.entity.EditFieldList;
import com.htmitech.emportal.entity.InfoRegion;
import com.htmitech.emportal.entity.OAConText;
import com.htmitech.emportal.entity.RouteInfo;
import com.htmitech.emportal.receive.RegistOrUnRegisterReceive;
import com.htmitech.emportal.ui.ActivityResultConstant;
import com.htmitech.emportal.ui.detail.AdapterFragmentForCollection.ChildType;
import com.htmitech.emportal.ui.detail.model.DocInfoModel;
import com.htmitech.emportal.ui.helppage.HelpActivity;
import com.htmitech.emportal.ui.pop.FunctionPopupWindow;
import com.htmitech.emportal.ui.widget.BaseDialog;
import com.htmitech.emportal.ui.widget.DialogCancelListener;
import com.htmitech.emportal.ui.widget.DialogClearListener;
import com.htmitech.emportal.ui.widget.DialogConfirmListener;
import com.htmitech.emportal.ui.widget.LoadingView;
import com.htmitech.emportal.ui.widget.MainViewPager;
import com.htmitech.emportal.ui.widget.MyAlertDialogFragment;
import com.htmitech.emportal.ui.widget.MyNextCodeDialog;
import com.htmitech.emportal.ui.widget.MyNextPersonDialog;
import com.htmitech.emportal.ui.widget.MyNextRouteDialog;
import com.htmitech.emportal.ui.widget.NewTopTabIndicator;
import com.htmitech.emportal.ui.widget.ToastInfo;
import com.htmitech.emportal.ui.widget.flatingactionButton.AddFloatingActionButton;
import com.htmitech.emportal.ui.widget.flatingactionButton.FloatingActionButton;
import com.htmitech.emportal.utils.SystemUser2SYSUser;
import com.htmitech.htcommonformplugin.dao.FormExtensionFilesDao;
import com.htmitech.htcommonformplugin.entity.GetSaveExtFieldsEntity;
import com.htmitech.htcommonformplugin.model.GetCommonFromInfoModel;
import com.htmitech.listener.CallCheckAllUserListener;
import com.htmitech.myEnum.BottomButtonSlyteEnum;
import com.htmitech.myEnum.ChooseSystemBook;
import com.htmitech.myEnum.ChooseTreeHierarchy;
import com.htmitech.myEnum.ChooseWay;
import com.htmitech.myEnum.ChooseWayEnum;
import com.htmitech.photoselector.model.FormExtensionFiles;
import com.htmitech.photoselector.model.PhotoModel;
import com.htmitech.proxy.doman.GetPictureResult;
import com.htmitech.proxy.doman.PictureInfo;
import com.htmitech.proxy.interfaces.INetWorkManager;
import com.htmitech.proxy.myenum.LogManagerEnum;
import com.htmitech.proxy.util.LogManagerProxy;
import com.htmitech.proxy.util.NetWorkManager;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.minxing.kit.api.MXAPI;
import com.minxing.kit.api.bean.ShareLink;
import com.mx.google.gson.Gson;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.feng.skin.manager.base.BaseFragmentActivity;
import htmitech.com.componentlibrary.listener.ObserverCallBackType;
import htmitech.com.componentlibrary.unit.PreferenceUtils;
import htmitech.com.componentlibrary.unit.ServerUrlConstant;


/***
 * @author tenggang 待办 已办 详情页面
 */
public class DetailActivity extends BaseFragmentActivity implements
        OnClickListener, IBaseCallback, View.OnTouchListener, CallBackLayout, ObserverCallBackType {

    private MainViewPager mViewPager_mycollection;
    private AdapterFragmentForCollection mAdapter;
    private NewTopTabIndicator mMyTopTabIndicator;
    private DocInfoModel mDocInfoModel;
    public DocResultInfo mDocResultInfo;
    private LoadingView mLoadingView = null;
    private AddFloatingActionButton menuMultipleActions = null;
    public int curItem = 0;

    private MyAlertDialogFragment mNewFragment = null;


    //分享相关变量
    public String apiUrlTmp = null; //用于拼接分享参数
    public String apiUrl = null;
    public ShareLink shareLink = null;
    public String docTitle = null;
    public String iconId = null;
    public static boolean oneInit = false;
    public String TodoFlag = "0";

    private String mDocAttachmentID = null;


    private MyNextCodeDialog nextcodeDialog; // 单选路由
    private MyNextRouteDialog nextrouteDialog; // 多选路由
    private MyNextPersonDialog nextpersonDialog;
    private boolean IsMultiSelectUser;
    private boolean IsMultiSelectRoute;

    private RouteInfo hasSelectedRouteInfo; //add by gulbel 2015-08-20

    private boolean isIsFreeSelectUser = false;

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

    //新增
    public int isWaterSecurity; //是不是支持水印
    public int isShare;
    ;  //是否支持表单分享功能。
    public int isTextUrl; //是否正文显示WebWiew
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


    public void setComment(String comment) {
        this.comment = comment;
    }

    protected int getLayoutById() {
        return R.layout.activity_detail;
    }

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        mDetailActivityLayout = (DetailActivityLayout) LayoutInflater.from(this).inflate(
                R.layout.activity_detail, null);
        setContentView(mDetailActivityLayout);
        mEmptyLayout = (EmptyLayout) mDetailActivityLayout.findViewById(R.id.emptyLayout);
        mEmptyLayout.setErrorButtonClickListener(this);
        formMap = new HashMap<String, Object>();
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
        titleBar.findViewById(R.id.imgview_titlebar_back).setOnClickListener(
                this);
        // ((TextView) titleBar.findViewById(R.id.textview_titlebar_title))
        // .setText("关于*****的");

        mLoadingView = (LoadingView) findViewById(R.id.loadingview_detail); //构建 加载视图

        Intent intent = getIntent();
        UserID = intent.getStringExtra("UserID");
        OA_UserId = intent.getStringExtra("OA_UserId");
        OA_UserName = intent.getStringExtra("OA_UserName");
        OA_UnitId = intent.getStringExtra("OA_UnitId");
        ThirdDepartmentName = intent.getStringExtra("ThirdDepartmentName");
        ThirdDepartmentId = intent.getStringExtra("ThirdDepartmentId");
        attribute1 = intent.getStringExtra("attribute1");
        docId = intent.getStringExtra("DocId");
        docType = intent.getStringExtra("DocType");
        docTitle = intent.getStringExtra("DocTitle");
        docKind = intent.getStringExtra("Kind");//2015-08-11
        TodoFlag = intent.getStringExtra("TodoFlag");//2016-8-2 by heyang//判断是待办还是已办
        app_id = intent.getStringExtra("app_id");
        actionButtonStyle = intent.getIntExtra("actionButtonStyle", -1);
        com_workflow_mobileconfig_IM_enabled = intent.getIntExtra("com_workflow_mobileconfig_IM_enabled", 1);
        isWaterSecurity = intent.getIntExtra("isWaterSecurity", 0);
        isShare = intent.getIntExtra("isShare", 0);
        isTextUrl = intent.getIntExtra("isTextUrl", 0);
        app_version_id = intent.getStringExtra("app_version_id");
        comeShare = intent.getStringExtra("come_share");
        if (comeShare == null) {
            comeShare = "-1";
        }
        ((TextView) titleBar.findViewById(R.id.textview_titlebar_title))
                .setText(docTitle);

        showLoadingView();
        // 发起网络请求，获取详细
        mDocInfoModel = new DocInfoModel(this);
        mDocInfoParameters = new DocInfoParameters();
        if (comeShare.equals("1")) {
            mDocInfoParameters.DocId = docId;
            mDocInfoParameters.app_id = app_id;
            mDocInfoParameters.app_version_id = app_version_id;
            mDocInfoParameters.context = new OAConText();
            mDocInfoParameters.context.UserID = UserID;
            mDocInfoParameters.context.OA_UserName = OA_UserName;
            mDocInfoParameters.context.OA_UserId = OA_UserId;
            mDocInfoParameters.context.OA_UnitId = OA_UnitId;
            mDocInfoParameters.context.ThirdDepartmentName = ThirdDepartmentName;
            mDocInfoParameters.context.ThirdDepartmentId = ThirdDepartmentId;
            mDocInfoParameters.context.login_name = OA_UserName;
        } else {
            mDocInfoParameters.DocId = docId;
            mDocInfoParameters.DocType = docType;
            mDocInfoParameters.Kind = docKind;
            mDocInfoParameters.app_id = app_id;
            mDocInfoParameters.app_version_id = app_version_id;
            mDocInfoParameters.context = OAConText.getInstance(HtmitechApplication
                    .instance());
        }
        iconId = intent.getStringExtra("IconId");
        if (intent.getStringExtra("IconId") == null) {
            iconId = "";
        }
        String departmentId = PreferenceUtils.getThirdDepartmentId(this) != null ? PreferenceUtils.getThirdDepartmentId(this) : " ";
        String departmentName = PreferenceUtils.getThirdDepartmentName(this) != null ? PreferenceUtils.getThirdDepartmentName(this) : " ";
        String attribute = PreferenceUtils.getAttribute1(this) != null ? PreferenceUtils.getAttribute1(this) : " ";
        String unitId = mDocInfoParameters.context.OA_UnitId != null ? mDocInfoParameters.context.OA_UnitId : " ";


        apiUrlTmp = mDocInfoParameters.context.UserID + "|" + mDocInfoParameters.context.OA_UserId + "|" + mDocInfoParameters.context.OA_UserName
                + "|" + docId + "|" + docKind + "|" + docType + "|" + app_id + "|" + app_version_id + "|" + departmentId + "|" + departmentName + "|" + attribute
                + "|" + unitId;


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

    @Override
    public void success(String requestValue, int type, String requestName) {
//        if (requestName.equals("functionStart")) {
//            mDocInfoModel.getDataFromServerByType(DocInfoModel.TYPE_GET_DETAILTASK,
//                    mDocInfoParameters);
//        } else if (requestName.equals("functionDetail")) {
//            functionDetail();
//        } else if (requestName.equals("login_home_back")) {
//            Log.d("DetailActivity", "唤醒成功");
//        } else if (requestName.equals(HTTPTYPEPICTUTR)) {
//
//            requestValue = GetFinalRefreshTokenAfterRequestValue.getFinalRequestValue(requestValue, type, getPictureUrl, mFileTemp, this, requestName, LogManagerEnum.GGENERAL.functionCode);
//            if (requestValue != null && !requestValue.equals("")) {
//                mGetPictureResult = mGson.fromJson(requestValue.toString(), GetPictureResult.class);
//                mPictureInfo = mGetPictureResult.getResult();
//                if (formExtensionFilesListUP != null && indexTemp < formExtensionFilesListUP.size()) {
//                    formExtensionFilesListUP.get(indexTemp).setFile_id(mPictureInfo.getPictureId() + "");
//                    mFormExtensionFilesDao.updateExtensionFiles(formExtensionFilesListUP.get(indexTemp));
//                    indexTemp++;
//                }
//
//                if (indexTemp >= formExtensionFilesListUP.size()) {
//                    List<Extfields> extfieldList = new ArrayList<Extfields>();
//                    if (formMap.size() > 0) {
//                        Set<String> set = formMap.keySet();
//                        for (String str : set) {// 遍历set去出里面的的Key
//                            ArrayList<FormExtensionFiles> formExtensionFilesArrayList = mFormExtensionFilesDao.getExtensionFiles(str);
//                            if (((SelectPhoto6001_6002_6101_6102) formMap.get(str)).workflow_item != null) {
//                                StringBuffer buffer = new StringBuffer();
//                                Extfields mExtfields = new Extfields();
//                                for (int i = 0; i < formExtensionFilesArrayList.size(); i++) {
//                                    buffer.append(formExtensionFilesArrayList.get(i).getFile_id() == null ? "" : formExtensionFilesArrayList.get(i).getFile_id());
//                                    if (i != (formExtensionFilesArrayList.size() - 1))
//                                        buffer.append(",");
//                                }
//                                mExtfields.setExt_field_type(((SelectPhoto6001_6002_6101_6102) formMap.get(str)).workflow_item.getInput());
//                                mExtfields.setField_id(str);
//                                mExtfields.setValue(buffer.toString());
//                                extfieldList.add(mExtfields);
//                            }
//                        }
//                        SaveExtFieldsInfoParameter mSaveExtFieldsInfoParameter = new SaveExtFieldsInfoParameter();
//                        mSaveExtFieldsInfoParameter.app_id = app_id;
//                        mSaveExtFieldsInfoParameter.user_id = OAConText.getInstance(DetailActivity.this).UserID;
//                        mSaveExtFieldsInfoParameter.form_id = formId;
//                        mSaveExtFieldsInfoParameter.data_id = docId;
//                        mSaveExtFieldsInfoParameter.extfields = extfieldList;
//                        mDocInfoModel.getDataFromServerByType(GetCommonFromInfoModel.TYPE_SAVEEXTFIELDS,
//                                mSaveExtFieldsInfoParameter);
//                    }
//
//
//                }
//            }
//        }
    }

    @Override
    public void fail(String exceptionMessage, int type, String requestName) {
        if (requestName.equals("functionStart")) {
            mDocInfoModel.getDataFromServerByType(DocInfoModel.TYPE_GET_DETAILTASK,
                    mDocInfoParameters);
        } else if (requestName.equals("functionDetail")) {
            functionDetail();
        } else if (requestName.equals("login_home_back")) {
            Log.d("DetailActivtiy", "唤醒失败");
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
                            for (int i = 0; i < mDocResultInfo.getResult()
                                    .getListActionInfo().size(); i++) {
                                if (mDocResultInfo.getResult()
                                        .getListActionInfo().get(i).getActionID().equals("Share")) {
                                    mDocResultInfo.getResult()
                                            .getListActionInfo().remove(i);
                                }
                            }
                        }
                        mFunctionPopupWindow = new FunctionPopupWindow(this,
                                new MenuOnClickListener(), mDocResultInfo.getResult()
                                .getListActionInfo().size());
                        mFunctionPopupWindow.initArcMenu(mDocResultInfo.getResult()
                                .getListActionInfo(), TodoFlag);
                        popupWidth = mFunctionPopupWindow.mMenuView.getMeasuredWidth();
                        popupWidth = DeviceUtils.dip2px(this, 55) + popupWidth;

                        popupHeight = mFunctionPopupWindow.getHeight();
                        int[] location = new int[2];
                        v.getLocationOnScreen(location);
                        mFunctionPopupWindow.showAtLocation(v, Gravity.NO_GRAVITY,
                                (location[0] - popupWidth), location[1]
                                        - popupHeight);
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

    /**
     * 退出
     **/
    public void exit() {
        //删除已经存在的正文
        File filezip = new File(CommonSettings.DEFAULT_DOCFILE_CACHE_FOLDER
                + File.separator + app_id + File.separator + getDocId() + ".zip");
        if (filezip.exists()) {
            filezip.delete();
        }

        File file = new File(CommonSettings.DEFAULT_DOCFILE_CACHE_FOLDER
                + File.separator + app_id + File.separator + getDocId() + ".doc");
        if (file.exists()) {
            file.delete();
        }

        ///
        finish();
    }

    private void handle_doAction_hasAuthor(ArrayList<AuthorInfo> AuthorInfoTemp) {
        // 假定通过界面选择了第一个人
        StringBuffer authorId = new StringBuffer();
        if (AuthorInfoTemp != null) {
            for (int i = 0; i < AuthorInfoTemp.size(); i++) {
                AuthorInfo selectUser = AuthorInfoTemp.get(i);
                Toast.makeText(DetailActivity.this,
                        "将提交给：" + selectUser.getUserName(), Toast.LENGTH_SHORT)
                        .show();
                if (i > 0)
                    authorId.append("|");

                authorId.append(selectUser.getUserID());
            }
        }
        // 再次提交
        DocInfoModel mDocInfoModel = new DocInfoModel(DetailActivity.this);
        if (hasSelectedRouteInfo != null) {
            mDoActionParameter.NextNodeId = hasSelectedRouteInfo.getRouteID(); // 需要选路径时，传入用户选择的路由。再次提交服务器。
        }
        mDoActionParameter.app_id = app_id;
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

            if (indexArr.length > 1 && i != indexArr.length - 1)
                authorId.append("|");
        }
        // 再次提交
        DocInfoModel mDocInfoModel = new DocInfoModel(DetailActivity.this);
        mDoActionParameter.NextNodeId = authorId.toString(); // 需要选路径时，传入用户选择的路由。再次提交服务器。
        // mDoActionParameter.SelectAuthorID = null; //
        // 需要选路径时，传入用户选择的路由。再次提交服务器。
        mDoActionParameter.app_id = app_id;
        mDocInfoModel.getDataFromServerByType(DocInfoModel.TYPE_DOACTION_TASK,
                mDoActionParameter);


    }

    public boolean isOneSuccess = false; //是否是第一次成功

    @Override
    public void onSuccess(int requestTypeId, Object result) {
        isOneSuccess = true;
        if (requestTypeId == DocInfoModel.TYPE_GET_DETAILTASK) {
            if (result != null && result instanceof DocResultInfo) {
                mDocResultInfo = (DocResultInfo) result;
                formId = mDocResultInfo.getResult().getFlowId();
                mDocResultInfo.getResult().setDocType(getDocType());
                mDocResultInfo.getResult().setKind(getDocKind());
                mViewPager_mycollection = (MainViewPager) findViewById(R.id.viewPager_qarank);
                FragmentManager fm = getSupportFragmentManager();
                mViewPager_mycollection.setOffscreenPageLimit(4);
                mMyTopTabIndicator = (NewTopTabIndicator) this
                        .findViewById(R.id.topTabIndicator_detail);

                ArrayList<ChildType> list = new ArrayList<ChildType>();
                List<String> listStr = new ArrayList<String>();
//				listStr.add("表单");
//				list.add(ChildType.TAB_FORM);

                if (mDocResultInfo != null &&
                        mDocResultInfo.getResult() != null && mDocResultInfo.getResult().TabItems != null) {

                    for (int i = 0; i < mDocResultInfo.getResult().TabItems.size(); i++) {

                        if (mDocResultInfo.getResult().TabItems.get(i).TabName != null) {

                            //过滤掉无效的tab
                            if (mDocResultInfo.getResult().TabItems.get(i).TabType == 1) {
                                InfoRegion[] regions = mDocResultInfo.getResult().TabItems.get(i).Regions;
                                if (regions == null)
                                    continue;
                                if (regions.length == 1) {
                                    if (regions[0].getFieldItems() == null || regions[0].getFieldItems().length == 0) {
                                        continue;
                                    }
                                }
                            }

                            String tabName = mDocResultInfo.getResult().TabItems.get(i).TabName;

                            if (tabName.equals("正文") && isTextUrl == 0 && !comeShare.equals("1")) {
                                if ((mDocAttachmentID = mDocResultInfo.getResult().getDocAttachmentID()) != null
                                        && mDocResultInfo.getResult().getDocAttachmentID()
                                        .length() > 0) {
                                    listStr.add("正文");
                                    list.add(ChildType.TAB_TEXT);
                                }
                            }
                            if (tabName.equals("正文") && isTextUrl == 1 && !comeShare.equals("1")) {
                                if ((mDocAttachmentID = mDocResultInfo.getResult().getDocAttachmentID()) != null
                                        && mDocResultInfo.getResult().getDocAttachmentID()
                                        .length() > 0) {
                                    DocviewUrl = mDocResultInfo.getResult().getDocviewUrl();
                                    listStr.add("正文");
                                    list.add(ChildType.TAB_URL);
                                }
                            } else if (tabName.equals("附件") && !comeShare.equals("1")) {
                                if (mDocResultInfo.getResult().getListAttInfo() != null
                                        && mDocResultInfo.getResult().getListAttInfo().size() > 0) {
                                    listStr.add("附件");
                                    list.add(ChildType.TAB_ATTACHMENT);
                                }
                            } else if (tabName.equals("流程") && !comeShare.equals("1")) {
                                listStr.add("流程");
                                list.add(ChildType.TAB_FLOW);
                            } else {
                                listStr.add(tabName);
                                //判读tab的类型，加载类型
                                if (mDocResultInfo.getResult().TabItems.get(i).TabType == 1) {
                                    list.add(ChildType.TAB_FORM);
                                } else if (mDocResultInfo.getResult().TabItems.get(i).TabType == 2 && !comeShare.equals("1")) {
                                    if (mDocResultInfo.getResult().TabItems.get(i).FormKey.equals("zhengwei"))
                                        list.add(ChildType.TAB_DOCAIP);
                                    else
                                        list.add(ChildType.TAB_AIP);
                                } else if (mDocResultInfo.getResult().TabItems.get(i).TabType == 3 && !comeShare.equals("1")) {
                                    list.add(ChildType.TAB_URL);
                                }
                            }
                        }
                    }


                }

                if (!listStr.contains("正文") && !comeShare.equals("1")) {
                    //2015-7-18 追加  mDocAttachmentID；
                    if ((mDocAttachmentID = mDocResultInfo.getResult().getDocAttachmentID()) != null
                            && mDocResultInfo.getResult().getDocAttachmentID()
                            .length() > 0) {
                        // 隐藏 “正文”
                        if (isTextUrl == 1) {
                            DocviewUrl = mDocResultInfo.getResult().getDocviewUrl();
                            listStr.add("正文");
                            list.add(ChildType.TAB_URL);
                        } else {
                            listStr.add("正文");
                            list.add(ChildType.TAB_TEXT);
                        }

                    }
                }

                if (!listStr.contains("附件") && !comeShare.equals("1")) {
                    if (mDocResultInfo.getResult().getListAttInfo() != null
                            && mDocResultInfo.getResult().getListAttInfo().size() > 0) {
                        listStr.add("附件");
                        list.add(ChildType.TAB_ATTACHMENT);
                    }
                }
                if (!listStr.contains("流程") && !comeShare.equals("1")) {
                    listStr.add("流程");
                    list.add(ChildType.TAB_FLOW);
                }


                String[] arrayTopTabIndicator = new String[listStr.size()];
                listStr.toArray(arrayTopTabIndicator);
                mMyTopTabIndicator.setCommonData2(mViewPager_mycollection,
                        arrayTopTabIndicator, R.color.color_title,
                        R.color.color_ff888888);
                mAdapter = new AdapterFragmentForCollection(HtmitechApplication
                        .instance().getApplicationContext(), fm, list, mDocResultInfo.getResult().getDocID(), mDocResultInfo.getResult().TabItems, app_id);
                mViewPager_mycollection.setAdapter(mAdapter);
                mViewPager_mycollection.setNoScroll(true);

                menuMultipleActions = (AddFloatingActionButton) findViewById(R.id.function);

                if (mDocResultInfo.getResult()
                        .getListActionInfo() == null || mDocResultInfo.getResult()
                        .getListActionInfo().size() == 0 || comeShare.equals("1")) {
                    menuMultipleActions.setVisibility(View.GONE);
                } else {
                    menuMultipleActions.setVisibility(View.VISIBLE);
                    mFunctionPopupWindow = new FunctionPopupWindow(this,
                            new MenuOnClickListener(), mDocResultInfo.getResult()
                            .getListActionInfo().size());
                    mFunctionPopupWindow.initArcMenu(mDocResultInfo.getResult()
                            .getListActionInfo(), TodoFlag);
                }


                menuMultipleActions.setOnClickListener(this);
                menuMultipleActions.setOnTouchListener(this);
                menuMultipleActions.setSize(FloatingActionButton.SIZE_NORMAL);

                switch (mBottomButtonSlyteEnum) {
                    case DRAG:
                        layout_buttom.setVisibility(View.GONE);
                        hs_scrollview.setVisibility(View.GONE);
                        if (mDocResultInfo.getResult()
                                .getListActionInfo() == null || mDocResultInfo.getResult()
                                .getListActionInfo().size() == 0) {
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
                            for (int i = 0; i < mDocResultInfo.getResult()
                                    .getListActionInfo().size(); i++) {
                                if (mDocResultInfo.getResult()
                                        .getListActionInfo().get(i).getActionID().equals("Share")) {
                                    mDocResultInfo.getResult()
                                            .getListActionInfo().remove(i);
                                }
                            }
                        }
                        initArcMenu(mDocResultInfo.getResult()
                                .getListActionInfo());
                        break;
                }
                netWorkManager.logFunactionFinsh(DetailActivity.this, DetailActivity.this, "functionStartFail", LogManagerEnum.APP_DOC_INFO.functionCode, mDocResultInfo.getMessage().getStatusMessage(), INetWorkManager.State.SUCCESS);
            } else {
                netWorkManager.logFunactionFinsh(DetailActivity.this, DetailActivity.this, "functionStartFail", LogManagerEnum.APP_DOC_INFO.functionCode, mDocResultInfo.getMessage().getStatusMessage(), INetWorkManager.State.FAIL);
            }

        } else if (requestTypeId == DocInfoModel.TYPE_DOACTION_TASK) {
            // 办理成功返回
            if (result != null && result instanceof DoActionResultInfo) {
                mDoActionResultInfo = (DoActionResultInfo) result;
                if (mDoActionResultInfo.getResult().getResultCode().equals("2")) {// 操作：提交--选择下一节点

                    String title = mDoActionResultInfo.getResult()
                            .getResultInfo();
                    IsMultiSelectRoute = mDoActionResultInfo.getResult()
                            .isIsMultiSelectRoute();
                    nextcodeDialog = new MyNextCodeDialog(
                            DetailActivity.this,
                            dialogConfirmOnclickListener,
                            dialogCancelOnclickListener, R.style.mydialog);
                    nextcodeDialog.show();
                    int sum = mDoActionResultInfo.getResult()
                            .getListRouteInfo().length;
                    for (int i = 0; i < sum; i++) {
                        RadioButton radioBtn = new RadioButton(
                                DetailActivity.this);
                        radioBtn.setText(mDoActionResultInfo.getResult()
                                .getListRouteInfo()[i].getRouteName());
                        radioBtn.setTextColor(getResources().getColor(
                                R.color.color_ff555555));
                        nextcodeDialog.setViewValue(title, radioBtn);
                    }

                } else if (mDoActionResultInfo.getResult().getResultCode()
                        .equals("4")) {// 直接选人
                    String title = mDoActionResultInfo.getResult()
                            .getResultInfo();
                    IsMultiSelectUser = mDoActionResultInfo.getResult()
                            .isIsMultiSelectUser();

                    hasSelectedRouteInfo = mDoActionResultInfo.getResult().getHasSelectedRoute();
                    if (hasSelectedRouteInfo != null) {
                        if (hasSelectedRouteInfo.getRouteName() != null) {
                            title += "--将提交到:" + hasSelectedRouteInfo.getRouteName();
                        } else if (hasSelectedRouteInfo.getRouteID() != null) {
                            title += "--将提交到:" + hasSelectedRouteInfo.getRouteID();
                        }
                    }
                    ArrayList<SYS_User> userList = SystemUser2SYSUser.system2SysUser(mDoActionResultInfo.getResult().getListAuthorInfo());

                    isIsFreeSelectUser = mDoActionResultInfo.getResult().isIsFreeSelectUser();
                    BookInit.getInstance().setCallCheckUserListener(DetailActivity.this, ChooseWayEnum.PEOPLECHOOSE, (!IsMultiSelectUser ? ChooseWay.SINGLE_CHOOSE : ChooseWay.MORE_CHOOSE), ChooseTreeHierarchy
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
                        .equals("84")) {
                    //不适合在手机端办理
                    mNewFragment = MyAlertDialogFragment.newInstance(mDoActionResultInfo.getResult().getResultInfo(),
                            R.drawable.prompt_warn, null, confirmListener, false);
                    mNewFragment.show(getSupportFragmentManager(), "dialog");
                } else if (mDoActionResultInfo.getResult().getResultCode()
                        .equals("9")) {
                    mNewFragment = MyAlertDialogFragment.newInstance(mDoActionResultInfo.getResult().getResultInfo(),
                            R.drawable.prompt_warn, null, confirmListener, false);
                    mNewFragment.show(getSupportFragmentManager(), "dialog");
                } else if (mDoActionResultInfo.getResult().getResultCode()
                        .equals("0")) {// 办理成功
                    ClassEvent mClassEvent = new ClassEvent();
                    mClassEvent.msg = "TextViewSize";
                    EventBus.getDefault().post(mClassEvent);
                    if (!isSave && !isReject) {
                        Toast.makeText(DetailActivity.this, mDoActionResultInfo.getResult().getResultInfo(),
                                Toast.LENGTH_LONG).show();
                    }
                    if (isReject) {
                        Toast.makeText(DetailActivity.this, "退回上一节点成功",
                                Toast.LENGTH_LONG).show();
                        isReject = false;
                    }
                    isSave = false;
                    Intent intent = getIntent();
                    intent.putExtra("kind", docKind);
                    setResult(ActivityResultConstant.DOACTION_RESULT_OK, getIntent());
                    netWorkManager.logFunactionFinsh(DetailActivity.this, DetailActivity.this, "functionDetailFail", LogManagerEnum.APP_DO_ACTION.functionCode, mDoActionResultInfo.getMessage(), INetWorkManager.State.SUCCESS);
                    exit();
                    SaveFiles();

                } else {
                    mNewFragment = MyAlertDialogFragment.newInstance(mDoActionResultInfo.getResult().getResultInfo(),
                            R.drawable.prompt_warn, null, confirmListener, false);
                    mNewFragment.show(getSupportFragmentManager(), "dialog");
                    mFormExtensionFilesDao = new FormExtensionFilesDao(DetailActivity.this);
                    mFormExtensionFilesDao.deleteExtensionFiles();
                }
            }

        } else if (requestTypeId == GetCommonFromInfoModel.TYPE_SAVEEXTFIELDS) {
            if (result != null && result instanceof GetSaveExtFieldsEntity) {
                GetSaveExtFieldsEntity mGetSaveExtFieldsEntity = (GetSaveExtFieldsEntity) result;
                mFormExtensionFilesDao = new FormExtensionFilesDao(DetailActivity.this);
                mFormExtensionFilesDao.deleteExtensionFiles();
                Log.d("Tag", "onSuccess: " + mGetSaveExtFieldsEntity.getResult().error_msg);
//                Toast.makeText(DetailActivity.this, mGetSaveExtFieldsEntity.getResult().error_msg, Toast.LENGTH_SHORT).show();
            }
        }

        hideLoadingView();
    }

    public void SaveFiles() {
//        //TODO 有关文件保存的操作
//        formExtensionFilesList = new ArrayList<FormExtensionFiles>();
//        mFormExtensionFilesDao = new FormExtensionFilesDao(DetailActivity.this);
//        boolean isNewpic = false;
//        if (formMap.size() > 0) {
//            Set<String> set = formMap.keySet();
//            for (String str : set) {// 遍历set去出里面的的Key
//                for (PhotoModel mPhotoModel : ((SelectPhoto6001_6002_6101_6102) formMap.get(str)).mListPickerAudioSelect) {
//                    listImgCash.add(mPhotoModel);
//                }
//
//            }
//        }
//        if (listImgCash != null) {
//            for (PhotoModel mPhotoModel : listImgCash) {
//                FormExtensionFiles mFormExtensionFiles = new FormExtensionFiles();
//                mFormExtensionFiles.data_id = docId;
//                mFormExtensionFiles.form_id = formId;
//                mFormExtensionFiles.user_id = OAConText.getInstance(DetailActivity.this).UserID;
//                mFormExtensionFiles.app_id = app_id;
//                mFormExtensionFiles.field_id = mPhotoModel.getItem_workflow().getField_id();
//                mFormExtensionFiles.ext_field_type = mPhotoModel.getItem_workflow().getInput();
//                mFormExtensionFiles.file_path = mPhotoModel.getOriginalPath();
//                mFormExtensionFiles.status_flag = 0;
//                mFormExtensionFiles.file_id = mPhotoModel.getFile_id();
//                mFormExtensionFiles.setNet(mPhotoModel.isNet());
//                mFormExtensionFiles.ID = mFormExtensionFilesDao.saveExtensionFiles(mFormExtensionFiles);
//                formExtensionFilesList.add(mFormExtensionFiles);
//            }
//            getPictureUrl = ServerUrlConstant.SERVER_EMPAPI_URL() + ServerUrlConstant.FilecontrollerPicture;
//            formExtensionFilesListUP = new ArrayList<FormExtensionFiles>();
//            if (formExtensionFilesList != null && formExtensionFilesList.size() > 0) {
//                for (int i = 0; i < formExtensionFilesList.size(); i++) {
//                    if (formExtensionFilesList.get(i).isNet()) {
//                        continue;
//                    }
//                    formExtensionFilesListUP.add(formExtensionFilesList.get(i));
//                }
//            }
//            if (formExtensionFilesListUP != null && formExtensionFilesListUP.size() > 0) {
//                for (int i = 0; i < formExtensionFilesListUP.size(); i++) {
//                    isNewpic = true;
//                    mFileTemp = new File(formExtensionFilesListUP.get(i).getFile_path());
//                    AnsynHttpRequest.requestByPostWithToken(this, mFileTemp, getPictureUrl, CHTTP.POSTWITHTOKEN, this, HTTPTYPEPICTUTR, LogManagerEnum.GGENERAL.functionCode);
//                }
//            }
//            if (!isNewpic) {
//                if (formExtensionFilesList != null) {
//                    List<Extfields> extfieldList = new ArrayList<Extfields>();
//                    if (formMap.size() > 0) {
//                        Set<String> set = formMap.keySet();
//                        for (String str : set) {// 遍历set去出里面的的Key
//                            ArrayList<FormExtensionFiles> formExtensionFilesArrayList = mFormExtensionFilesDao.getExtensionFiles(str);
//                            if (((SelectPhoto6001_6002_6101_6102) formMap.get(str)).workflow_item != null) {
//                                StringBuffer buffer = new StringBuffer();
//                                Extfields mExtfields = new Extfields();
//                                for (int i = 0; i < formExtensionFilesArrayList.size(); i++) {
//                                    buffer.append(formExtensionFilesArrayList.get(i).getFile_id());
//                                    formExtensionFilesArrayList.get(i).getFile_id();
//                                    if (i != (formExtensionFilesArrayList.size() - 1))
//                                        buffer.append(",");
//                                }
//                                mExtfields.setExt_field_type(((SelectPhoto6001_6002_6101_6102) formMap.get(str)).workflow_item.getInput());
//                                mExtfields.setField_id(str);
//                                mExtfields.setValue(buffer.toString());
//                                extfieldList.add(mExtfields);
//                            }
//                        }
//                        SaveExtFieldsInfoParameter mSaveExtFieldsInfoParameter = new SaveExtFieldsInfoParameter();
//                        mSaveExtFieldsInfoParameter.app_id = app_id;
//                        mSaveExtFieldsInfoParameter.user_id = OAConText.getInstance(DetailActivity.this).UserID;
//                        mSaveExtFieldsInfoParameter.form_id = formId;
//                        mSaveExtFieldsInfoParameter.data_id = docId;
//                        mSaveExtFieldsInfoParameter.extfields = extfieldList;
//                        mDocInfoModel.getDataFromServerByType(GetCommonFromInfoModel.TYPE_SAVEEXTFIELDS,
//                                mSaveExtFieldsInfoParameter);
//                    }
//
//                }
//
//            }
//        }
    }

    private DialogConfirmListener confirmListener = new DialogConfirmListener() {
        public void onConfirm(BaseDialog dialog) {
            netWorkManager.logFunactionFinsh(DetailActivity.this, DetailActivity.this, "functionDetailFail", LogManagerEnum.APP_DO_ACTION.functionCode, mDoActionResultInfo == null ? mDocResultInfo.getMessage().getStatusMessage() : mDoActionResultInfo.getMessage(), INetWorkManager.State.FAIL);
            mNewFragment.dismiss();
        }
    };

    private DialogConfirmListener confirmAndExitListener = new DialogConfirmListener() {
        public void onConfirm(BaseDialog dialog) {

            netWorkManager.logFunactionFinsh(DetailActivity.this, DetailActivity.this, "functionStartFail", LogManagerEnum.APP_DOC_INFO.functionCode, mDoActionResultInfo == null ? mDocResultInfo.getMessage().getStatusMessage() : mDoActionResultInfo.getMessage(), INetWorkManager.State.FAIL);
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
                            .getInstance(DetailActivity.this);
                    toast.setText("没有选择办理人！！");
                    toast.show(Toast.LENGTH_SHORT);
                } else if (!IsMultiSelectUser && indexArr.length > 1) {
                    ToastInfo toast = ToastInfo
                            .getInstance(DetailActivity.this);
                    toast.setText("只能选择一个办理人！");
                    toast.show(Toast.LENGTH_SHORT);
                } else {
                    AuthorInfo[] AuthorInfoTemp = nextpersonDialog.getNewVector();
                }
            } else if (null != nextcodeDialog && nextcodeDialog.isShowing()) { // 选节点---单选
                int index = nextcodeDialog.getSelectIndex();
                if (index < 0) { // 未选择
                    ToastInfo toast = ToastInfo
                            .getInstance(DetailActivity.this);
                    toast.setText("没有选择下一节点！！");
                    toast.show(Toast.LENGTH_SHORT);
                } else {
                    handle_doAction_hasRoute(new Integer[]{index});
                }
                nextcodeDialog.dismiss();

            } else if (null != nextrouteDialog && nextrouteDialog.isShowing()) {// 选节点--多选
                if (IsMultiSelectRoute) {
                    Integer[] indexArr = nextrouteDialog.getSelectIndexArr();
                    if (null == indexArr || indexArr.length <= 0) {
                        ToastInfo toast = ToastInfo
                                .getInstance(DetailActivity.this);
                        toast.setText("没有选择路由节点！！");
                        toast.show(Toast.LENGTH_SHORT);
                    } else if (!IsMultiSelectRoute && indexArr.length > 1) {
                        ToastInfo toast = ToastInfo
                                .getInstance(DetailActivity.this);
                        toast.setText("只能选择一个路由节点！");
                        toast.show(Toast.LENGTH_SHORT);
                    } else {
                        handle_doAction_hasRoute(indexArr);
                        // handler.sendEmptyMessage(OPERATION_SUBMIT_NEXTPEOPLE);
                    }
                }
            }
        }
    };

    public DialogClearListener dialogClearOnclickListener = new DialogClearListener() {
        public void onClear(BaseDialog dialog) { // 清空选择
            if (null != nextpersonDialog && nextpersonDialog.isShowing())
                nextpersonDialog.clearSelect();
            else if (null != nextrouteDialog && nextrouteDialog.isShowing()) { // 选节点--多选
                nextrouteDialog.clearSelect();
            }
        }

        @Override
        public void onClear() {
            // TODO Auto-generated method stub

        }
    };

    public DialogCancelListener dialogCancelOnclickListener = new DialogCancelListener() {
        public void onCancel(BaseDialog dialog) {
            if (null != nextpersonDialog && nextpersonDialog.isShowing()) { // 选择人
                netWorkManager.logFunactionFinsh(DetailActivity.this, DetailActivity.this, "functionDetailFail", LogManagerEnum.APP_DO_ACTION.functionCode, mDoActionResultInfo.getMessage(), INetWorkManager.State.CANCEL);
                nextpersonDialog.dismiss();
            } else if (null != nextcodeDialog && nextcodeDialog.isShowing()) { // 选节点--单选
                netWorkManager.logFunactionFinsh(DetailActivity.this, DetailActivity.this, "functionDetailFail", LogManagerEnum.APP_DO_ACTION.functionCode, mDoActionResultInfo.getMessage(), INetWorkManager.State.CANCEL);
                nextcodeDialog.dismiss();
            } else if (null != nextrouteDialog && nextrouteDialog.isShowing()) { // 选节点--多选
                netWorkManager.logFunactionFinsh(DetailActivity.this, DetailActivity.this, "functionDetailFail", LogManagerEnum.APP_DO_ACTION.functionCode, mDoActionResultInfo.getMessage(), INetWorkManager.State.CANCEL);
                nextrouteDialog.dismiss();
            }
        }
    };


    private void initArcMenu(List<ActionInfo> actionList) {
        if (actionList != null) {
            layout_buttom.removeAllViews();
            final int itemCount = actionList.size();
            for (int i = 0; i < itemCount; i++) {
//                if (!actionList.get(i).getActionID().equalsIgnoreCase("reject")) { //退回加在最后一个
                DrawableCenterTextView item = new DrawableCenterTextView(this);
                item.setTextSize(14);
                item.setTextColor(getResources().getColor(R.color.buttom_color));
                Drawable drawable = null;
                item.setGravity(Gravity.CENTER_VERTICAL);
                item.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));
                if (actionList.get(i).getActionID().equalsIgnoreCase("submit") && TodoFlag.equals("0"))    //提交
                    //item.setColorNormalResId(R.color.pink);
                    drawable = getResources().getDrawable(R.drawable.btn_action_submit);
//					item.setText("提交");
                else if (actionList.get(i).getActionID().equalsIgnoreCase("readed") && TodoFlag.equals("0"))    //已读
                    drawable = getResources().getDrawable(R.drawable.btn_action_read);
//					item.setIcon(R.drawable.btn_action_read);
                    //item.setColorNormalResId(R.color.color_26ffffff);
                else if (actionList.get(i).getActionID().equalsIgnoreCase("addreader"))    //阅知
                    drawable = getResources().getDrawable(R.drawable.btn_action_yuezhi);
//					item.setText("阅知");
//					item.setIcon(R.drawable.btn_action_yuezhi);
                    //item.setColorNormalResId(R.color.color_33000000);
                else if (actionList.get(i).getActionID().equalsIgnoreCase("getback") && TodoFlag.equals("0"))    //拿回
                    drawable = getResources().getDrawable(R.drawable.btn_action_takeback);
//					item.setText("拿回");
//					item.setIcon(R.drawable.btn_action_takeback);
                    //item.setColorNormalResId(R.color.color_44b2b2b2);
                else if (actionList.get(i).getActionID().equalsIgnoreCase("Share"))    //分享
                    drawable = getResources().getDrawable(R.drawable.btn_action_share);
//					item.setText("分享");
//					item.setIcon(R.drawable.btn_action_share);
                else if (actionList.get(i).getActionID().equalsIgnoreCase("save") && TodoFlag.equals("0"))
//					item.setText("暂存");
                    drawable = getResources().getDrawable(R.drawable.btn_action_save);
                else if (actionList.get(i).getActionID().equalsIgnoreCase("reject"))
                    drawable = getResources().getDrawable(R.drawable.btn_action_save);
                else if (actionList.get(i).getActionID().equalsIgnoreCase("Attention"))
                    drawable = getResources().getDrawable(R.drawable.btn_action_attention);
                else {
                    drawable = getResources().getDrawable(R.drawable.btn_action_submit);
                }
//					item.setIcon(R.drawable.btn_action_save);
                //item.setColorNormalResId(R.color.color_55555555);  	//暂存
            /*item.setText(actionList.get(i).getActionName());*/
                if (drawable != null)
                    drawable.setBounds(0, 0, drawable.getMinimumWidth() / 2, drawable.getMinimumHeight() / 2);

                item.setCompoundDrawables(drawable, null, null, null);
                item.setText(actionList.get(i).getActionName());
                if (actionList.get(i).getActionID().equals("Attention")) {
                    item.setTag(actionList.get(i).getActionName());
                } else {
                    item.setTag(actionList.get(i).getActionID());
                }
//                item.setTag(actionList.get(i).getActionID());
                item.setBackgroundResource(R.drawable.buttom_message);
                item.setOnClickListener(new MenuOnClickListener());

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
                item.setCompoundDrawablePadding(DeviceUtils.dip2px(this, 8));
                item.setLayoutParams(params);
//				item.setColorNormalResId(R.color.mx_title_bar_color);
                layout_buttom.addView(item);
            }
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
                DetailActivity.this);
        builder.setTitle("请选择分享位置");
        final String[] pos = {"分享给同事", "分享到工作组"};
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (curItem == 0) {
                    apiUrl = "aa" + apiUrlTmp;
                    shareLink.setAppUrl(apiUrl);
                    MXAPI.getInstance(DetailActivity.this).shareToChat(DetailActivity.this, shareLink);
                } else {
                    apiUrl = "bb" + apiUrlTmp;
                    shareLink.setAppUrl(apiUrl);
                    shareLink.setTitle("分享表单:" + docTitle);
                    MXAPI.getInstance(DetailActivity.this).shareToCircle(DetailActivity.this, shareLink);
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

        // TODO Auto-generated method stub
        showLoadingView();
        // 1,必填字段判读
        // 必填字段判断
        EditFieldList mustFieldList = EditFieldList.getInstance();
        final String name1 = PreferenceUtils                                    //拿到当前用户
                .getOAUserName(DetailActivity.this);
        for (int i = 0; i < mustFieldList.getList().size(); i++) {
            if (mustFieldList.getList().get(i).getValue() == null
                    || mustFieldList.getList().get(i).getValue().trim().equals("")
                    ) {
                SuperEditText.isChecked = true;
                Toast.makeText(
                        DetailActivity.this,
                        "必填字段 "
                                + mustFieldList.getList().get(i)
                                .getNameDesc() + "尚未填写！",
                        Toast.LENGTH_SHORT).show();
                hideLoadingView();
                return;
            }

            if (mustFieldList.getList().get(i).getInput() != null && !mustFieldList.getList().get(i).getInput().equals("")) {
                int input = Integer.parseInt(mustFieldList.getList().get(i).getInput());
                switch (input) {
                    case 2001:
                    case 2002:
                    case 2003:
                        if (mustFieldList.getList().get(i).tempValue.equals("")) {
                            SuperEditText.isChecked = true;
                            Toast.makeText(
                                    DetailActivity.this,
                                    "必填字段 "
                                            + mustFieldList.getList().get(i)
                                            .getNameDesc() + "尚未填写！",
                                    Toast.LENGTH_SHORT).show();
                            hideLoadingView();
                            return;
                        }
                        break;
                }
            }

        }
        DocInfoModel mDocInfoModel = new DocInfoModel(DetailActivity.this);
        mDoActionParameter = new DoActionParameter();
        mDoActionParameter.ActionName = menuViewTag.toString();
        if (menuViewTag.equalsIgnoreCase("save"))
            isSave = true;
        if (menuViewTag.equalsIgnoreCase("reject"))
            isReject = true;
        mDoActionParameter.context = OAConText
                .getInstance(HtmitechApplication.instance());
        mDoActionParameter.DocId = getIntent().getStringExtra("DocId"); // "HZ286f024cf9144b014d22ae386f495e";
        mDoActionParameter.DocType = getIntent().getStringExtra("DocType");
        mDoActionParameter.Kind = getIntent().getStringExtra("Kind");//2015-08-11

        mDoActionParameter.FlowName = mDocResultInfo.getResult().getFlowName();
        mDoActionParameter.FlowId = mDocResultInfo.getResult().getFlowId();

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
        mDoActionParameter.app_id = app_id;

        mDocInfoModel.getDataFromServerByType(
                DocInfoModel.TYPE_DOACTION_TASK, mDoActionParameter);

    }

    public class MenuOnClickListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            menuViewTag = v.getTag().toString();
            if (menuViewTag.contains("关注")) {
                AttentionFunction(menuViewTag.trim().toString());
            } else {

                netWorkManager.logFunactionStart(DetailActivity.this, DetailActivity.this, "functionDetail", LogManagerEnum.APP_DO_ACTION.functionCode);
            }
            if (mFunctionPopupWindow != null)
                mFunctionPopupWindow.dismiss();
        }

    }

    public void AttentionFunction(String ActionName) {
        final String tmpActionName = ActionName;
        String path = ServerUrlConstant.SERVER_BASE_URL() + ServerUrlConstant.SET_ATTENTION_YESORNO;
        AsyncHttpClient client = new AsyncHttpClient();
        OAConText instance = OAConText.getInstance(getApplicationContext());
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
            jsonObject.put("LogFunctionId", LogManagerEnum.APP_DOC_INFO.functionId);
            jsonObjectAll.put("DocId", getIntent().getStringExtra("DocId"));
            if (ActionName.equals("添加关注")) {
                jsonObjectAll.put("AttentionFlag", "1");
            } else if (ActionName.equals("取消关注")) {
                jsonObjectAll.put("AttentionFlag", "0");
            }
            jsonObjectAll.put("AllowPush", "1");
            jsonObjectAll.put("DocTitle", getIntent().getStringExtra("DocTitle"));
            jsonObjectAll.put("DocType", getIntent().getStringExtra("DocType"));
            jsonObjectAll.put("SendFrom", getIntent().getStringExtra("sendFrom"));
            jsonObjectAll.put("SendDate", getIntent().getStringExtra("sendDate"));
            jsonObjectAll.put("iconId", getIntent().getStringExtra("IconId"));
            jsonObjectAll.put("Kind", getIntent().getStringExtra("Kind"));
            jsonObjectAll.put("context", jsonObject);
            jsonObjectAll.put("app_id", app_id);
            StringEntity stringEntity = new StringEntity(
                    jsonObjectAll.toString(), "utf-8");
            RequestHandle post = client.post(getApplicationContext(), path,
                    stringEntity, null, new JsonHttpResponseHandler() {

                        @Override
                        public void onSuccess(int statusCode, Header[] headers,
                                              JSONObject response) {

                            if (tmpActionName.equals("添加关注")) {
                                Toast.makeText(getApplicationContext(),
                                        "添加关注成功", Toast.LENGTH_SHORT).show();
                                setResult(ActivityResultConstant.DOACTION_RESULT_OK, getIntent());
                            } else if (tmpActionName.equals("取消关注")) {
                                Toast.makeText(getApplicationContext(),
                                        "取消关注成功", Toast.LENGTH_SHORT).show();
                                setResult(ActivityResultConstant.DOACTION_RESULT_OK, getIntent());
                            }
                            for (int i = 0; i < mDocResultInfo.getResult()
                                    .getListActionInfo().size(); i++) {
                                if (mDocResultInfo.getResult()
                                        .getListActionInfo().get(i).getActionName().equals("添加关注")) {
                                    mDocResultInfo.getResult()
                                            .getListActionInfo().get(i).setActionName("取消关注");
                                    break;
                                }
                                if (mDocResultInfo.getResult()
                                        .getListActionInfo().get(i).getActionName().equals("取消关注")) {
                                    mDocResultInfo.getResult()
                                            .getListActionInfo().get(i).setActionName("添加关注");
                                    break;
                                }
                            }
                            LogManagerEnum.APP_DO_ACTION.functionId = LogManagerEnum.APP_DOC_INFO.functionId;  //修改functionid为0问题
                            initArcMenu(mDocResultInfo.getResult().getListActionInfo());//重新刷新底部tab关注状态
                            netWorkManager.logFunactionFinsh(DetailActivity.this, DetailActivity.this, "functionDetailFail", LogManagerEnum.APP_DO_ACTION.functionCode, "添加关注成功", INetWorkManager.State.SUCCESS);
                            super.onSuccess(statusCode, headers, response);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers,
                                              Throwable throwable, JSONObject errorResponse) {
                            LogManagerEnum.APP_DO_ACTION.functionId = LogManagerEnum.APP_DOC_INFO.functionId;//修改functionid为0问题
                            netWorkManager.logFunactionFinsh(DetailActivity.this, DetailActivity.this, "functionDetailFail", LogManagerEnum.APP_DO_ACTION.functionCode, "添加关注失败", INetWorkManager.State.FAIL);
                            super.onFailure(statusCode, headers, throwable,
                                    errorResponse);
                            throwable.printStackTrace();
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
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

                try {
                    mDocResultInfo = new DocResultInfo();
                    mDocResultInfo.parseJson(result.toString());
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if (mDocResultInfo.getMessage() != null && mDocResultInfo.getMessage().getStatusMessage() != null) {
                    mNewFragment = MyAlertDialogFragment.newInstance(mDocResultInfo.getMessage().getStatusMessage(),
                            R.drawable.prompt_warn, mDialogCancelListener, confirmAndExitListener, false);
                    try {
                        mNewFragment.show(getSupportFragmentManager(), "dialog");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            } else if (taskType == DocInfoModel.TYPE_DOACTION_TASK) {
                mDoActionResultInfo = new DoActionResultInfo();
                try {
                    mDoActionResultInfo.parseJson(result.toString());
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if (mDoActionResultInfo.getResult() != null) {

                    mNewFragment = MyAlertDialogFragment.newInstance("失败:" + mDoActionResultInfo.getResult().getResultInfo() + "("
                                    + mDoActionResultInfo.getResult().getResultCode()
                                    + ")",
                            R.drawable.prompt_warn, null, confirmListener, false);

                    mNewFragment.show(getSupportFragmentManager(), "dialog");
                } else
                    Toast.makeText(DetailActivity.this, "操作失败！",
                            Toast.LENGTH_SHORT).show();
            } else {
                ToastInfo toast = ToastInfo.getInstance(this);
                toast.setView(getLayoutInflater(), R.drawable.prompt_error,
                        result.toString());
                toast.show(Toast.LENGTH_SHORT);
            }
        } else {
            //展示其他错误。。。。
            Toast.makeText(DetailActivity.this, "操作失败！" + errorMsg,
                    Toast.LENGTH_SHORT).show();
        }
        hideLoadingView();


        if (!isOneSuccess) {
            mEmptyLayout.showError();
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
}
