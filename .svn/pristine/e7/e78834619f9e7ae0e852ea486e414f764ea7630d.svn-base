//package com.htmitech.htcommonformplugin.activity;
//
//import android.app.AlertDialog;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.graphics.drawable.Drawable;
//import android.os.Bundle;
//import android.support.v4.app.FragmentManager;
//import android.util.DisplayMetrics;
//import android.util.Log;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.HorizontalScrollView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.htmitech.MyView.EmptyLayout;
//import com.htmitech.addressbook.ClassEvent;
//import com.htmitech.app.widget.DrawableCenterTextView;
//import com.htmitech.commonx.util.DeviceUtils;
//import com.htmitech.edittext.SuperEditText;
//import com.htmitech.emportal.HtmitechApplication;
//import com.htmitech.emportal.R;
//import com.htmitech.emportal.base.IBaseCallback;
//import com.htmitech.emportal.base.MyBaseFragmentActivity;
//import com.htmitech.emportal.common.ServerUrlConstant;
//import com.htmitech.emportal.entity.EditFieldList;
//import com.htmitech.emportal.entity.OAConText;
//import com.htmitech.emportal.entity.RouteInfo;
//import com.htmitech.emportal.receive.RegistOrUnRegisterReceive;
//import com.htmitech.emportal.ui.detail.CallBackLayout;
//import com.htmitech.emportal.ui.detail.DetailActivityLayout;
//import com.htmitech.emportal.ui.detail.model.DocInfoModel;
//import com.htmitech.emportal.ui.formConfig.SelectPhoto6001_6002_6101_6102;
//import com.htmitech.emportal.ui.helppage.HelpActivity;
//import com.htmitech.emportal.ui.pop.FunctionPopupWindow;
//import com.htmitech.emportal.ui.widget.BaseDialog;
//import com.htmitech.emportal.ui.widget.DialogCancelListener;
//import com.htmitech.emportal.ui.widget.DialogClearListener;
//import com.htmitech.emportal.ui.widget.DialogConfirmListener;
//import com.htmitech.emportal.ui.widget.LoadingView;
//import com.htmitech.emportal.ui.widget.MainViewPager;
//import com.htmitech.emportal.ui.widget.MyAlertDialogFragment;
//import com.htmitech.emportal.ui.widget.MyNextCodeDialog;
//import com.htmitech.emportal.ui.widget.MyNextPersonDialog;
//import com.htmitech.emportal.ui.widget.MyNextRouteDialog;
//import com.htmitech.emportal.ui.widget.NewTopTabIndicator;
//import com.htmitech.emportal.ui.widget.flatingactionButton.AddFloatingActionButton;
//import com.htmitech.emportal.ui.widget.flatingactionButton.FloatingActionButton;
//import com.htmitech.htcommonformplugin.adapter.FragmentForCollectionAdapter;
//import com.htmitech.htcommonformplugin.dao.FormExtensionFilesDao;
//import com.htmitech.htcommonformplugin.entity.Actions;
//import com.htmitech.htcommonformplugin.entity.Attentioniteminfo;
//import com.htmitech.htcommonformplugin.entity.Detailparameter;
//import com.htmitech.htcommonformplugin.entity.DoActionInfopPrameter;
//import com.htmitech.htcommonformplugin.entity.Editfields;
//import com.htmitech.htcommonformplugin.entity.Extfields;
//import com.htmitech.htcommonformplugin.entity.GetAttentionYesOrNoEntity;
//import com.htmitech.htcommonformplugin.entity.GetDetailEntity;
//import com.htmitech.htcommonformplugin.entity.GetDoActionEntity;
//import com.htmitech.htcommonformplugin.entity.GetSaveExtFieldsEntity;
//import com.htmitech.htcommonformplugin.entity.Regions;
//import com.htmitech.htcommonformplugin.entity.SaveExtFieldsInfoParameter;
//import com.htmitech.htcommonformplugin.entity.Tabitems;
//import com.htmitech.htcommonformplugin.model.GetCommonFromInfoModel;
//import com.htmitech.listener.ObserverCallBackType;
//import com.htmitech.myEnum.BottomButtonSlyteEnum;
//import com.htmitech.photoselector.model.FormExtensionFiles;
//import com.htmitech.photoselector.model.PhotoModel;
//import com.htmitech.proxy.doman.GetPictureResult;
//import com.htmitech.proxy.doman.PictureInfo;
//import com.htmitech.proxy.interfaces.INetWorkManager;
//import com.htmitech.proxy.myenum.LogManagerEnum;
//import com.htmitech.proxy.util.LogManagerProxy;
//import com.htmitech.proxy.util.NetWorkManager;
//import com.loopj.android.http.AsyncHttpClient;
//import com.loopj.android.http.JsonHttpResponseHandler;
//import com.loopj.android.http.RequestHandle;
//import com.minxing.client.util.PreferenceUtils;
//import com.minxing.client.util.Utils;
//import com.minxing.kit.api.MXAPI;
//import com.minxing.kit.api.bean.ShareLink;
//import com.mx.google.gson.Gson;
//import com.mx.google.gson.JsonSyntaxException;
//
//import org.apache.http.Header;
//import org.apache.http.entity.StringEntity;
//import org.greenrobot.eventbus.EventBus;
//import org.json.JSONObject;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//import cn.feng.skin.manager.base.BaseFragmentActivity;
//import thread.AnsynHttpRequest;
//import thread.CHTTP;
//import thread.GetFinalRefreshTokenAfterRequestValue;
//
///***
// * 代办 已办 详情
// *
// * @author joe
// * @date 2017/04/17
// */
//public class GeneralFormDetalActivity extends MyBaseFragmentActivity implements
//        OnClickListener, IBaseCallback, View.OnTouchListener, CallBackLayout, ObserverCallBackType {
//    private MainViewPager mViewPager_mycollection;
//    private FragmentForCollectionAdapter mAdapter;
//    private NewTopTabIndicator mMyTopTabIndicator;
//    private GetCommonFromInfoModel mGetCommonFromInfoModel;
//    public GetDetailEntity mGetDetailEntity;
//    public List<Editfields> editfieldsTemp;
//    private LoadingView mLoadingView = null;
//    private AddFloatingActionButton menuMultipleActions = null;
//    public int curItem = 0;
//    private MyAlertDialogFragment mNewFragment = null;
//
//    //分享相关变量
//    public String apiUrlTmp = null; //用于拼接分享参数
//    public String apiUrl = null;
//    public ShareLink shareLink = null;
//    public String docTitle = null;
//    public String iconId = null;
//    public static boolean oneInit = false;
//    public String TodoFlag = "0";
//
//    private String mDocAttachmentID = null;
//    public String id;
//    public String user_id;
//    public String subsystemid;
//    public String data_id;
//    public String form_id;
//    public String iconurl;
//    private MyNextCodeDialog nextcodeDialog; // 单选路由
//    private MyNextRouteDialog nextrouteDialog; // 多选路由
//    private MyNextPersonDialog nextpersonDialog;
//    private boolean IsMultiSelectUser;
//    private boolean IsMultiSelectRoute;
//
//    private RouteInfo hasSelectedRouteInfo; //add by gulbel 2015-08-20
//
//    private boolean isIsFreeSelectUser = false;
//
//    private GetDoActionEntity mGetDoActionEntity;
//    private DoActionInfopPrameter mDoActionInfopPrameter;
//
//    private String comment; // 在流程中显示的意见，以用户最后在表单上输入的意见为准
//    private FunctionPopupWindow mFunctionPopupWindow;
//    private DetailActivityLayout mDetailActivityLayout;
//    private EmptyLayout mEmptyLayout;
//    private String docKind;
//    private LinearLayout layout_buttom;
//    private Detailparameter mDetailparameter;
//    private BottomButtonSlyteEnum mBottomButtonSlyteEnum;
//    private HorizontalScrollView hs_scrollview;
//    private boolean isSave = false;
//    private boolean isReject = false;
//    public String app_id;
//    private INetWorkManager netWorkManager;
//
//    public int actionButtonStyle;
//    public int customerShortcuts;
//    public int com_workflow_mobileconfig_IM_enabled;
//    public int isFavValue;
//    public int isOtherFavValue;
//    public int isWaterSecurity;
//    public int isShare;
//    public String comeShare = "-1";
//
//    public String newtag;
//    public String time;
//    public String todoflag;
//    public String otherinfo1;
//    public String otherinfo2;
//    public String attention_flag;
//    //扩展字段添加的变量 2017-6-12 11:38:00
//    public Map<String, Object> formMap;
//    private String getPictureUrl = "";
//    private static final String HTTPTYPEPICTUTR = "getDicPicture";
//    public ArrayList<FormExtensionFiles> formExtensionFilesList;
//    public ArrayList<FormExtensionFiles> formExtensionFilesListUP;
//    public List<PhotoModel> listImgCash = new ArrayList<PhotoModel>();
//    private File mFileTemp;
//    int indexTemp = 0;
//    private GetPictureResult mGetPictureResult;
//    private Gson mGson = new Gson();
//    private PictureInfo mPictureInfo;
//    private FormExtensionFilesDao mFormExtensionFilesDao;
//
//
//    public void setComment(String comment) {
//        this.comment = comment;
//    }
//
//    protected int getLayoutById() {
//        return R.layout.activity_tododetail;
//    }
//
//
//    @Override
//    protected void onCreate(Bundle arg0) {
//        // TODO Auto-generated method stub
//        mDetailActivityLayout = (DetailActivityLayout) LayoutInflater.from(this).inflate(
//                R.layout.activity_tododetail, null);
//        super.onCreate(arg0);
//        setContentView(mDetailActivityLayout);
//        mEmptyLayout = (EmptyLayout) mDetailActivityLayout.findViewById(R.id.emptyLayout);
//        mEmptyLayout.setErrorButtonClickListener(this);
//        formMap = new HashMap<String, Object>();
//        initView();
//        mBottomButtonSlyteEnum = BottomButtonSlyteEnum.getBottomButtonSlyteEnum(actionButtonStyle);
//
//    }
//
//    /**
//     * 初始化UI
//     */
//    //该方法在 onCreate 的时候调用
//    protected void initView() {
//        layout_buttom = (LinearLayout) findViewById(R.id.layout_buttom);
//        hs_scrollview = (HorizontalScrollView) findViewById(R.id.hs_scrollview);
//        mDetailActivityLayout.setValue(this);
//        EditFieldList mustFieldList = EditFieldList.getInstance();
//        mustFieldList.Clear();
//        DisplayMetrics dm = getResources().getDisplayMetrics();
//        screenWidth = dm.widthPixels;
//        screenHeight = dm.heightPixels - 50;
//        View titleBar = findViewById(R.id.layout_detail_titlebar);    //构件titlebar
//        titleBar.findViewById(R.id.imgview_titlebar_back).setOnClickListener(
//                this);
//        mLoadingView = (LoadingView) findViewById(R.id.loadingview_detail); //构建 加载视图
//
//
//        Intent intent = getIntent();
//        id = intent.getStringExtra("id");
//        user_id = intent.getStringExtra("user_id");
//        subsystemid = intent.getStringExtra("subsystemid");
//        form_id = intent.getStringExtra("form_id");
//        data_id = intent.getStringExtra("data_id");
//        app_id = intent.getStringExtra("app_id");
//        docTitle = intent.getStringExtra("doc_title");
//        comeShare = intent.getStringExtra("come_share");
//        iconurl = intent.getStringExtra("iconurl");
//        newtag = intent.getStringExtra("newtag");
//        time = intent.getStringExtra("time");
//        todoflag = intent.getStringExtra("todoflag");
//        otherinfo1 = intent.getStringExtra("otherinfo1");
//        otherinfo2 = intent.getStringExtra("otherinfo2");
//        if (comeShare == null) {
//            comeShare = "-1";
//        }
//        actionButtonStyle = intent.getIntExtra("com_commonform_mobileconfig_actionbutton_style", -1);
//        com_workflow_mobileconfig_IM_enabled = intent.getIntExtra("com_commonform_mobileconfig_IM_enabled", 1);
//        customerShortcuts = intent.getIntExtra("com_commonform_mobileconfig_customer_shortcuts", 1);
//        isFavValue = intent.getIntExtra("com_commonform_mobileconfig_include_myfav", 1);
//        isOtherFavValue = intent.getIntExtra("com_commonform_mobileconfig_include_otherfav", 1);
//        isWaterSecurity = intent.getIntExtra("com_commonform_mobileconfig_include_security", 1);
//        isShare = intent.getIntExtra("com_commonform_mobileconfig_include_share", 1);
//
//        ((TextView) titleBar.findViewById(R.id.textview_titlebar_title))
//                .setText(docTitle);
//        showLoadingView();
//        // 发起网络请求，获取详细
//        mGetCommonFromInfoModel = new GetCommonFromInfoModel(this);
//        mDetailparameter = new Detailparameter();
//        mDetailparameter.user_id = user_id;
//        mDetailparameter.form_id = form_id;
//        mDetailparameter.data_id = data_id;
//        mDetailparameter.id = id;
//        mDetailparameter.subsystemid = subsystemid;
//        mDetailparameter.app_id = app_id;
//
////        mGetCommonFromInfoModel.getDataFromServerByType(GetCommonFromInfoModel.TYPE_GET_DETAILTASK,
////                mDetailparameter);
//
//        //启动帮助页面
//        if (PreferenceUtils.isLoginForm(this)) {
//            PreferenceUtils.setLoginForm(this, false);
//            Intent i = new Intent(this, HelpActivity.class);
//            i.putExtra(HelpActivity.CURRENT_HELPPAGE,
//                    HelpActivity.FORM_FLOW_HELPPAGE);
//            startActivity(i);
//        }
//        netWorkManager = LogManagerProxy.getProxyInstance(NetWorkManager.class);
//        netWorkManager.logFunactionStart(this, this, "commonformStart", LogManagerEnum.GET_COMMONFORM_DETAIL.functionCode);
//
//    }
//
//    @Override
//    public void success(String requestValue, int type, String requestName) {
//        if (requestName.equals("commonformStart")) {
//            mGetCommonFromInfoModel.getDataFromServerByType(GetCommonFromInfoModel.TYPE_GET_DETAILTASK,
//                    mDetailparameter);
//        } else if (requestName.equals("commonformDetail")) {
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
//                if (indexTemp >= formExtensionFilesListUP.size()) {
//                    List<Extfields> extfieldList = new ArrayList<Extfields>();
//                    if (formMap.size() > 0) {
//                        Set<String> set = formMap.keySet();
//                        for (String str : set) {// 遍历set去出里面的的Key
//                            ArrayList<FormExtensionFiles> formExtensionFilesArrayList = mFormExtensionFilesDao.getExtensionFiles(str);
//                            if (((SelectPhoto6001_6002_6101_6102) formMap.get(str)).common_item != null) {
//                                StringBuffer buffer = new StringBuffer();
//                                Extfields mExtfields = new Extfields();
//                                for (int i = 0; i < formExtensionFilesArrayList.size(); i++) {
//                                    buffer.append(formExtensionFilesArrayList.get(i).getFile_id() == null ? "" : formExtensionFilesArrayList.get(i).getFile_id());
//                                    if (i != (formExtensionFilesArrayList.size() - 1))
//                                        buffer.append(",");
//                                }
//                                mExtfields.setExt_field_type(((SelectPhoto6001_6002_6101_6102) formMap.get(str)).common_item.getInput_type());
//                                mExtfields.setField_id(str);
//                                mExtfields.setValue(buffer.toString());
//                                extfieldList.add(mExtfields);
//                            }
//                        }
//
//                    }
//
//                    SaveExtFieldsInfoParameter mSaveExtFieldsInfoParameter = new SaveExtFieldsInfoParameter();
//                    mSaveExtFieldsInfoParameter.app_id = app_id;
//                    mSaveExtFieldsInfoParameter.user_id = user_id;
//                    mSaveExtFieldsInfoParameter.form_id = form_id;
//                    mSaveExtFieldsInfoParameter.data_id = data_id;
//                    mSaveExtFieldsInfoParameter.extfields = extfieldList;
//                    mGetCommonFromInfoModel.getDataFromServerByType(GetCommonFromInfoModel.TYPE_SAVEEXTFIELDS,
//                            mSaveExtFieldsInfoParameter);
//                }
//
//            } else {
//                return;
//            }
//
//        }
//    }
//
//    @Override
//    public void fail(String exceptionMessage, int type, String requestName) {
//        if (requestName.equals("commonformStart")) {
//            mGetCommonFromInfoModel.getDataFromServerByType(GetCommonFromInfoModel.TYPE_GET_DETAILTASK,
//                    mDetailparameter);
//        } else if (requestName.equals("commonformDetail")) {
//            functionDetail();
//        } else if (requestName.equals("login_home_back")) {
//            Log.d("DetailActivtiy", "唤醒失败");
//        }
//    }
//
//    @Override
//    public void notNetwork() {
//
//    }
//
//    @Override
//    public void callbackMainUI(String successMessage) {
//
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.imgview_titlebar_back:
//                exit();
//            /*if (PreferenceUtils.getCurrentPage().equalsIgnoreCase(HomeSet.METRO_PAGE)) {
//                ClientTabActivity.backFlag = true;
//			}
//			DetailActivity.this.finish();*/
//                break;
//            case R.id.function:
//                if (!isTuoZhuai) {
//                    if (mFunctionPopupWindow == null) {
//                        return;
//                    }
//                    if (!mFunctionPopupWindow.isShowing()) {
////					menuMultipleActions.startAnimation(animation);
//                        menuMultipleActions.setImageDrawable(getResources().getDrawable(R.drawable.btn_operation_homelabel_on));
//                        List<Actions> actionList = new ArrayList<Actions>();
//                        actionList = mGetDetailEntity.getResult()
//                                .getActions();
//                        boolean havaShare = false;
//                        if (isShare == 1) {
//                            for (Actions mActions : actionList) {
//                                if (mActions.getAction_id().equals("Share"))
//                                    havaShare = true;
//                            }
//                            if (!havaShare) {
//                                Actions mActions = new Actions();
//                                mActions.setAction_name("分享");
//                                mActions.setAction_id("Share");
//                                actionList.add(0, mActions);
//                            }
//
//                        }
//                        mFunctionPopupWindow = new FunctionPopupWindow(this,
//                                new MenuOnClickListener(), actionList.size());
//                        mFunctionPopupWindow.initArcMenuCommForm(actionList);
//                        popupWidth = mFunctionPopupWindow.mMenuView.getMeasuredWidth();
//                        popupWidth = DeviceUtils.dip2px(this, 55) + popupWidth;
//
//                        popupHeight = mFunctionPopupWindow.getHeight();
//                        int[] location = new int[2];
//                        v.getLocationOnScreen(location);
//                        mFunctionPopupWindow.showAtLocation(v, Gravity.NO_GRAVITY,
//                                (location[0] - popupWidth), location[1]
//                                        - popupHeight);
//                        mFunctionPopupWindow.update();
//                    } else {
//                        menuMultipleActions.setImageDrawable(getResources().getDrawable(R.drawable.btn_operation_homelabel_off));
//                        mFunctionPopupWindow.dismiss();
//                    }
//                }
//                break;
//            case R.id.buttonError: //重试按钮
////                showLoadingView();
////                mEmptyLayout.hide();
////                mGetCommonFromInfoModel.getDataFromServerByType(GetCommonFromInfoModel.TYPE_GET_DETAILTASK,
////                        mDetailparameter);
//                exit();
//                break;
//        }
//    }
//
//    int popupHeight;
//    int popupWidth;
//
//    /**
//     * 退出
//     **/
//    public void exit() {
//        ClassEvent mClassEvent = new ClassEvent();
//        mClassEvent.msg = "angle";
//        EventBus.getDefault().post(mClassEvent);
//        finish();
//    }
//
//    public boolean isOneSuccess = false; //是否是第一次成功
//
//    @Override
//    public void onSuccess(int requestTypeId, Object result) {
//        isOneSuccess = true;
//        if (requestTypeId == GetCommonFromInfoModel.TYPE_GET_DETAILTASK) {
//            if (result != null && result instanceof GetDetailEntity) {
//                mGetDetailEntity = (GetDetailEntity) result;
//                mViewPager_mycollection = (MainViewPager) findViewById(R.id.viewPager_qarank);
//                FragmentManager fm = getSupportFragmentManager();
//                mViewPager_mycollection.setOffscreenPageLimit(4);
//                mMyTopTabIndicator = (NewTopTabIndicator) this
//                        .findViewById(R.id.topTabIndicator_detail);
//                ArrayList<FragmentForCollectionAdapter.ChildType> list = new ArrayList<FragmentForCollectionAdapter.ChildType>();
//                List<Tabitems> tabitemsList = new ArrayList<Tabitems>();
//                List<String> listStr = new ArrayList<String>();
//                //判读tab的类型，加载类型
//                if (mGetDetailEntity != null &&
//                        mGetDetailEntity.getResult() != null && mGetDetailEntity.getResult().getTabitems() != null) {
//                    for (int i = 0; i < mGetDetailEntity.getResult().getTabitems().size(); i++) {
//                        List<Regions> regions = mGetDetailEntity.getResult().getTabitems().get(i).getRegions();
//                        String tabName = mGetDetailEntity.getResult().getTabitems().get(i).getTab_name();
//                        if (mGetDetailEntity.getResult().getTabitems().get(i).getTab_type() == 1) {
//                            listStr.add(tabName);
//                            list.add(FragmentForCollectionAdapter.ChildType.TAB_FORM);
//                            tabitemsList.add(mGetDetailEntity.getResult().getTabitems().get(i));
//                        } else if (mGetDetailEntity.getResult().getTabitems().get(i).getTab_type() == 4) {
//                            boolean isExit = false;
//                            for (int j = 0; j < mGetDetailEntity.getResult().getAttachments().size(); j++) {
//                                if (mGetDetailEntity.getResult().getTabitems().get(i).getTab_formkey()
//                                        .equals(mGetDetailEntity.getResult().getAttachments().get(j).attachment_tab_formkey)) {
//                                    isExit = true;
//                                    break;
//                                }
//
//                            }
//                            if (isExit) {
//                                listStr.add(tabName);
//                                list.add(FragmentForCollectionAdapter.ChildType.TAB_ATTACHMENT);
//                                tabitemsList.add(mGetDetailEntity.getResult().getTabitems().get(i));
//                            }
//
//
//                        }
//                    }
//                }
//                String[] arrayTopTabIndicator = new String[listStr.size()];
//                listStr.toArray(arrayTopTabIndicator);
//                mMyTopTabIndicator.setCommonData2(mViewPager_mycollection,
//                        arrayTopTabIndicator, R.color.color_title,
//                        R.color.color_ff888888);
//                mAdapter = new FragmentForCollectionAdapter(HtmitechApplication
//                        .instance().getApplicationContext(), fm, list, tabitemsList, app_id);
//                mViewPager_mycollection.setAdapter(mAdapter);
//                mViewPager_mycollection.setNoScroll(true);
//                menuMultipleActions = (AddFloatingActionButton) findViewById(R.id.function);
//                if (mGetDetailEntity.getResult()
//                        .getActions() == null || mGetDetailEntity.getResult()
//                        .getActions().size() == 0) {
//                    menuMultipleActions.setVisibility(View.GONE);
//                } else {
//                    menuMultipleActions.setVisibility(View.VISIBLE);
//                    mFunctionPopupWindow = new FunctionPopupWindow(this,
//                            new MenuOnClickListener(), mGetDetailEntity.getResult()
//                            .getActions().size());
//                    mFunctionPopupWindow.initArcMenuCommForm(mGetDetailEntity.getResult()
//                            .getActions());
//                }
//                menuMultipleActions.setOnClickListener(this);
//                menuMultipleActions.setOnTouchListener(this);
//                menuMultipleActions.setSize(FloatingActionButton.SIZE_NORMAL);
//
//                switch (mBottomButtonSlyteEnum) {
//                    case DRAG:
//                        layout_buttom.setVisibility(View.GONE);
//                        hs_scrollview.setVisibility(View.GONE);
//                        if (mGetDetailEntity.getResult()
//                                .getActions() == null || mGetDetailEntity.getResult()
//                                .getActions().size() == 0) {
//                            menuMultipleActions.setVisibility(View.GONE);
//                        } else {
//                            menuMultipleActions.setVisibility(View.VISIBLE);
//                        }
//
//                        break;
//                    case BOTTOM:
//                        layout_buttom.setVisibility(View.VISIBLE);
//                        hs_scrollview.setVisibility(View.VISIBLE);
//                        menuMultipleActions.setVisibility(View.GONE);
//                        initArcMenuCommonForm(mGetDetailEntity.getResult()
//                                .getActions());
//                        break;
//                }
//                if (mGetDetailEntity.getResult()
//                        .getActions() == null || mGetDetailEntity.getResult()
//                        .getActions().size() == 0 || comeShare.equals("1")) {
//                    layout_buttom.setVisibility(View.GONE);
//                    hs_scrollview.setVisibility(View.GONE);
//                    menuMultipleActions.setVisibility(View.GONE);
//                }
//                netWorkManager.logFunactionFinsh(GeneralFormDetalActivity.this, GeneralFormDetalActivity.this, "commonformStartFail", LogManagerEnum.GET_COMMONFORM_DETAIL.functionCode, mGetDetailEntity.getMessage().getStatusMessage(), INetWorkManager.State.SUCCESS);
//            } else {
//                netWorkManager.logFunactionFinsh(GeneralFormDetalActivity.this, GeneralFormDetalActivity.this, "commonformStartFail", LogManagerEnum.GET_COMMONFORM_DETAIL.functionCode, mGetDetailEntity.getMessage().getStatusMessage(), INetWorkManager.State.FAIL);
//            }
//
//        } else if (requestTypeId == GetCommonFromInfoModel.TYPE_DOACTION_TASK) {
//            // 办理成功返回
//            if (result != null && result instanceof GetDoActionEntity) {
//                mGetDoActionEntity = (GetDoActionEntity) result;
//                Toast.makeText(GeneralFormDetalActivity.this, mGetDoActionEntity.getResult().error_msg, Toast.LENGTH_SHORT).show();
//                exit();
//                SaveFiles();
//            }
//
//
//        } else if (requestTypeId == GetCommonFromInfoModel.TYPE_SAVEEXTFIELDS) {
//            if (result != null && result instanceof GetSaveExtFieldsEntity) {
//                GetSaveExtFieldsEntity mGetSaveExtFieldsEntity = (GetSaveExtFieldsEntity) result;
//                mFormExtensionFilesDao = new FormExtensionFilesDao(GeneralFormDetalActivity.this);
//                mFormExtensionFilesDao.deleteExtensionFiles();
//                Log.d("Tag", "onSuccess: " + mGetSaveExtFieldsEntity.getResult().error_msg);
//            }
//        }
//
//        hideLoadingView();
//
//
//    }
//
//    public void SaveFiles() {
//        //TODO 有关文件保存的操作
//        formExtensionFilesList = new ArrayList<FormExtensionFiles>();
//        mFormExtensionFilesDao = new FormExtensionFilesDao(GeneralFormDetalActivity.this);
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
//                mFormExtensionFiles.data_id = data_id;
//                mFormExtensionFiles.form_id = form_id;
//                mFormExtensionFiles.user_id = user_id;
//                mFormExtensionFiles.app_id = app_id;
//                mFormExtensionFiles.field_id = mPhotoModel.getItem_common().getField_id();
//                mFormExtensionFiles.ext_field_type = mPhotoModel.getItem_common().getInput_type();
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
//                            if (((SelectPhoto6001_6002_6101_6102) formMap.get(str)).common_item != null) {
//                                StringBuffer buffer = new StringBuffer();
//                                Extfields mExtfields = new Extfields();
//                                for (int i = 0; i < formExtensionFilesArrayList.size(); i++) {
//                                    buffer.append(formExtensionFilesArrayList.get(i).getFile_id());
//                                    formExtensionFilesArrayList.get(i).getFile_id();
//                                    if (i != (formExtensionFilesArrayList.size() - 1))
//                                        buffer.append(",");
//                                }
//                                mExtfields.setExt_field_type(((SelectPhoto6001_6002_6101_6102) formMap.get(str)).common_item.getInput_type());
//                                mExtfields.setField_id(str);
//                                mExtfields.setValue(buffer.toString());
//                                extfieldList.add(mExtfields);
//                            }
//                        }
//
//                    }
//
//                    SaveExtFieldsInfoParameter mSaveExtFieldsInfoParameter = new SaveExtFieldsInfoParameter();
//                    mSaveExtFieldsInfoParameter.app_id = app_id;
//                    mSaveExtFieldsInfoParameter.user_id = user_id;
//                    mSaveExtFieldsInfoParameter.form_id = form_id;
//                    mSaveExtFieldsInfoParameter.data_id = data_id;
//                    mSaveExtFieldsInfoParameter.extfields = extfieldList;
//                    mGetCommonFromInfoModel.getDataFromServerByType(GetCommonFromInfoModel.TYPE_SAVEEXTFIELDS,
//                            mSaveExtFieldsInfoParameter);
//                }
//
//            }
//        }
//    }
//
////    public ArrayList<PhotoModel> getPhotoModes(List<FragmentForCollectionAdapter.ChildType> mChildIndex, List<Tabitems> tabList) {
////        ArrayList<PhotoModel> photoModels = new ArrayList<PhotoModel>();
////        int ponstion = 0;
////        for (FragmentForCollectionAdapter.ChildType childType : mChildIndex) {
////            switch (childType) {
////                case TAB_FORM:
////                    GeneralFormDetailFragment mGeneralFormDetailFragment = (GeneralFormDetailFragment) mAdapter.getItem(ponstion);
////                    photoModels.addAll(mGeneralFormDetailFragment.listImg);
////                    break;
////            }
////            ponstion++;
////        }
////        return photoModels;
////    }
//
//
//    private DialogConfirmListener confirmListener = new DialogConfirmListener() {
//        public void onConfirm(BaseDialog dialog) {
//            netWorkManager.logFunactionFinsh(GeneralFormDetalActivity.this, GeneralFormDetalActivity.this, "commonformDetailFail", LogManagerEnum.GET_COMMONFORM_DO_ACTION.functionCode, mGetDoActionEntity == null ? mGetDetailEntity.getMessage().getStatusMessage() : mGetDoActionEntity.getMessage().getStatusMessage(), INetWorkManager.State.FAIL);
//            mNewFragment.dismiss();
//        }
//    };
//
//    private DialogConfirmListener confirmAndExitListener = new DialogConfirmListener() {
//        public void onConfirm(BaseDialog dialog) {
//
//            netWorkManager.logFunactionFinsh(GeneralFormDetalActivity.this, GeneralFormDetalActivity.this, "commonformStartFail", LogManagerEnum.GET_COMMONFORM_DETAIL.functionCode, mGetDoActionEntity == null ? mGetDetailEntity.getMessage().getStatusMessage() : mGetDoActionEntity.getMessage().getStatusMessage(), INetWorkManager.State.FAIL);
//            mNewFragment.dismiss();
//            exit();
//
//        }
//    };
//
//    public DialogCancelListener mDialogCancelListener = new DialogCancelListener() {
//
//        @Override
//        public void onCancel(BaseDialog dialog) {
//            mNewFragment.dismiss();
//            exit();
//        }
//    };
//
//
//    public DialogClearListener dialogClearOnclickListener = new DialogClearListener() {
//        public void onClear(BaseDialog dialog) { // 清空选择
//            if (null != nextpersonDialog && nextpersonDialog.isShowing())
//                nextpersonDialog.clearSelect();
//            else if (null != nextrouteDialog && nextrouteDialog.isShowing()) { // 选节点--多选
//                nextrouteDialog.clearSelect();
//            }
//        }
//
//        @Override
//        public void onClear() {
//            // TODO Auto-generated method stub
//
//        }
//    };
//
//    public DialogCancelListener dialogCancelOnclickListener = new DialogCancelListener() {
//        public void onCancel(BaseDialog dialog) {
//            if (null != nextpersonDialog && nextpersonDialog.isShowing()) { // 选择人
//                netWorkManager.logFunactionFinsh(GeneralFormDetalActivity.this, GeneralFormDetalActivity.this, "commonformDetailFail", LogManagerEnum.GET_COMMONFORM_DO_ACTION.functionCode, mGetDoActionEntity.getMessage().getStatusMessage(), INetWorkManager.State.CANCEL);
//                nextpersonDialog.dismiss();
//            } else if (null != nextcodeDialog && nextcodeDialog.isShowing()) { // 选节点--单选
//                netWorkManager.logFunactionFinsh(GeneralFormDetalActivity.this, GeneralFormDetalActivity.this, "commonformDetailFail", LogManagerEnum.GET_COMMONFORM_DO_ACTION.functionCode, mGetDoActionEntity.getMessage().getStatusMessage(), INetWorkManager.State.CANCEL);
//                nextcodeDialog.dismiss();
//            } else if (null != nextrouteDialog && nextrouteDialog.isShowing()) { // 选节点--多选
//                netWorkManager.logFunactionFinsh(GeneralFormDetalActivity.this, GeneralFormDetalActivity.this, "commonformDetailFail", LogManagerEnum.GET_COMMONFORM_DO_ACTION.functionCode, mGetDoActionEntity.getMessage().getStatusMessage(), INetWorkManager.State.CANCEL);
//                nextrouteDialog.dismiss();
//            }
//        }
//    };
//
//
//    private void initArcMenuCommonForm(List<Actions> actionList) {
//        if (actionList != null) {
//            boolean havaShare = false;
//            if (isShare == 1) {
//                for (Actions mActions : actionList) {
//                    if (mActions.getAction_id().equals("Share"))
//                        havaShare = true;
//                }
//                if (!havaShare) {
//                    Actions mActions = new Actions();
//                    mActions.setAction_name("分享");
//                    mActions.setAction_id("Share");
//                    actionList.add(0, mActions);
//                }
//            }
//            layout_buttom.removeAllViews();
//            final int itemCount = actionList.size();
//            for (int i = 0; i < itemCount; i++) {
////                if (!actionList.get(i).getActionID().equalsIgnoreCase("reject")) { //退回加在最后一个
//                DrawableCenterTextView item = new DrawableCenterTextView(this);
//                item.setTextSize(14);
//                item.setTextColor(getResources().getColor(R.color.buttom_color));
//                Drawable drawable = null;
//                item.setGravity(Gravity.CENTER_VERTICAL);
//                item.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));
//                if (actionList.get(i).getAction_id().equalsIgnoreCase("submit") && TodoFlag.equals("0"))    //提交
//                    drawable = getResources().getDrawable(R.drawable.btn_action_submit);
//                else if (actionList.get(i).getAction_id().equalsIgnoreCase("readed") && TodoFlag.equals("0"))    //已读
//                    drawable = getResources().getDrawable(R.drawable.btn_action_read);
//                else if (actionList.get(i).getAction_id().equalsIgnoreCase("addreader"))    //阅知
//                    drawable = getResources().getDrawable(R.drawable.btn_action_yuezhi);
//                else if (actionList.get(i).getAction_id().equalsIgnoreCase("getback") && TodoFlag.equals("0"))    //拿回
//                    drawable = getResources().getDrawable(R.drawable.btn_action_takeback);
//                else if (actionList.get(i).getAction_id().equalsIgnoreCase("Share"))    //分享
//                    drawable = getResources().getDrawable(R.drawable.btn_action_share);
//                else if (actionList.get(i).getAction_id().equalsIgnoreCase("save") && TodoFlag.equals("0"))
//                    drawable = getResources().getDrawable(R.drawable.btn_action_save);
//                else if (actionList.get(i).getAction_id().equalsIgnoreCase("reject"))
//                    drawable = getResources().getDrawable(R.drawable.btn_action_save);
//                else if (actionList.get(i).getAction_id().equalsIgnoreCase("Attention"))//关注
//                    drawable = getResources().getDrawable(R.drawable.btn_action_attention);
//                else {
//                    drawable = getResources().getDrawable(R.drawable.btn_action_submit);
//                }
//                if (drawable != null)
//                    drawable.setBounds(0, 0, drawable.getMinimumWidth() / 2, drawable.getMinimumHeight() / 2);
//                item.setCompoundDrawables(drawable, null, null, null);
//                item.setText(actionList.get(i).getAction_name());
//                if (actionList.get(i).getAction_id().equals("Attention")) {
//                    item.setTag(actionList.get(i).getAction_name());
//                } else {
//                    item.setTag(actionList.get(i).getAction_name());
//                }
//                item.setBackgroundResource(R.drawable.buttom_message);
//                item.setOnClickListener(new MenuOnClickListener());
//                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
//                item.setCompoundDrawablePadding(DeviceUtils.dip2px(this, 8));
//                item.setLayoutParams(params);
//                layout_buttom.addView(item);
//            }
//        }
//    }
//
//    private void ShareListener() {
//        // 弹选择窗
//        //设置分享参数
//        shareLink = new ShareLink();
//        shareLink.setTitle("分享表单");
//        shareLink.setDesc(docTitle);
////        shareLink.setThumbnail(iconId);
//        shareLink.setUrl(ServerUrlConstant.SERVER_BASE_URL() + ServerUrlConstant.GET_COMMONFORM_DETAL);
//        apiUrlTmp = id + "|" + form_id + "|" + data_id + "|" + app_id + "|" + user_id + "|" + subsystemid + "|" + docTitle;
//        AlertDialog.Builder builder = new AlertDialog.Builder(
//                GeneralFormDetalActivity.this);
//        builder.setTitle("请选择分享位置");
//        final String[] pos = {"分享给同事", "分享到工作圈"};
//        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                if (curItem == 0) {
//                    apiUrl = "CF" + apiUrlTmp;
//                    shareLink.setAppUrl(apiUrl);
//                    MXAPI.getInstance(GeneralFormDetalActivity.this).shareToChat(GeneralFormDetalActivity.this, shareLink);
//                } else {
//                    apiUrl = "CF" + apiUrlTmp;
//                    shareLink.setAppUrl(apiUrl);
//                    shareLink.setTitle("分享表单:" + docTitle);
//                    MXAPI.getInstance(GeneralFormDetalActivity.this).shareToCircle(GeneralFormDetalActivity.this, shareLink);
//                }
//                curItem = 0;
//            }
//        });
//        builder.setSingleChoiceItems(pos, 0,
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        curItem = which;
//                    }
//                });
//        builder.show();
//    }
//
//
//    private String menuViewTag;
//
//
//    public void functionDetail() {
//        //关按钮
//        mFunctionPopupWindow.dismiss();
//        menuMultipleActions.setImageDrawable(getResources().getDrawable(R.drawable.btn_operation_homelabel_off));
//        //分享逻辑
//        if (menuViewTag.contains("关注")) {
//            AttentionFunction(menuViewTag.trim().toString());
//        } else if (menuViewTag.contains("分享")) {
//            ShareListener();
//            return;
//        } else {
//
//            String action_key = "";
//            String action_id = "";
//            String action_name = "";
//            String action_hint = "";
//            for (Actions mActions : mGetDetailEntity.getResult().getActions()) {
//                if (mActions.getAction_name().equals(menuViewTag.toString())) {
//                    action_key = mActions.getAction_key();
//                    action_id = mActions.getAction_id();
//                    action_hint = mActions.getAction_hint();
//                    action_name = mActions.getAction_name();
//                }
//            }
//
//            // TODO Auto-generated method stub
//            showLoadingView();
//            // 1,必填字段判读
//            // 必填字段判断
//            EditFieldList mustFieldList = EditFieldList.getInstance();
//            final String name1 = PreferenceUtils                                    //拿到当前用户
//                    .getOAUserName(GeneralFormDetalActivity.this);
//            for (int i = 0; i < mustFieldList.getList().size(); i++) {
//                if (mustFieldList.getList().get(i).is_ext()) {
//                    continue;
//                }
//                if (mustFieldList.getList().get(i).getValue() == null
//                        || mustFieldList.getList().get(i).getValue().trim().equals("")
//                        ) {
//                    SuperEditText.isChecked = true;
//                    Toast.makeText(
//                            GeneralFormDetalActivity.this,
//                            "必填字段 "
//                                    + mustFieldList.getList().get(i)
//                                    .getNameDesc() + "尚未填写！",
//                            Toast.LENGTH_SHORT).show();
//                    hideLoadingView();
//                    return;
//                }
//
//                if (mustFieldList.getList().get(i).getInput() != null && !mustFieldList.getList().get(i).getInput().equals("")) {
//                    int input = Integer.parseInt(mustFieldList.getList().get(i).getInput());
//                    switch (input) {
//                        case 2001:
//                        case 2002:
//                        case 2003:
//                        case 2004:
//                            if (mustFieldList.getList().get(i).tempValue.equals("")) {
//                                SuperEditText.isChecked = true;
//                                Toast.makeText(
//                                        GeneralFormDetalActivity.this,
//                                        "必填字段 "
//                                                + mustFieldList.getList().get(i)
//                                                .getNameDesc() + "尚未填写！",
//                                        Toast.LENGTH_SHORT).show();
//                                hideLoadingView();
//                                return;
//                            }
//
//                            break;
//                    }
//                }
//
//            }
//            GetCommonFromInfoModel mGetCommonFromInfoModel = new GetCommonFromInfoModel(GeneralFormDetalActivity.this);
//
//            mDoActionInfopPrameter = new DoActionInfopPrameter();
//            mDoActionInfopPrameter.id = id;
//            mDoActionInfopPrameter.app_id = app_id;
//            mDoActionInfopPrameter.user_id = user_id;
//            mDoActionInfopPrameter.form_id = form_id;
//            mDoActionInfopPrameter.action_key = action_key;
//            mDoActionInfopPrameter.action_id = action_id;
//            mDoActionInfopPrameter.data_id = data_id;
//            mDoActionInfopPrameter.subsystemid = subsystemid;
//            mDoActionInfopPrameter.editfields = mGetDetailEntity.getResult().getEditfields();
//
//            mGetCommonFromInfoModel.getDataFromServerByType(
//                    GetCommonFromInfoModel.TYPE_DOACTION_TASK, mDoActionInfopPrameter);
//
////            formExtensionFilesList = new ArrayList<FormExtensionFiles>();
////            if(listImg !=null&& listImg.size()>0){
////                for(PhotoModel mPhotoModel :listImg){
////                    mFormExtensionFilesDao = new FormExtensionFilesDao(GeneralFormDetalActivity.this);
////                    FormExtensionFiles mFormExtensionFiles = new FormExtensionFiles();
////                    mFormExtensionFiles.data_id = data_id;
////                    mFormExtensionFiles.form_id = form_id;
////                    mFormExtensionFiles.user_id = user_id;
////                    mFormExtensionFiles.app_id = app_id;
////                    mFormExtensionFiles.subsystemid = subsystemid;
////                    mFormExtensionFiles.field_id = mPhotoModel.getItem().getField_id();
////                    mFormExtensionFiles.ext_field_type = mPhotoModel.getItem().getInput_type();
////                    mFormExtensionFiles.file_path = mPhotoModel.getOriginalPath();
////                    mFormExtensionFiles.status_flag = 0;
////                    mFormExtensionFiles.ID = mFormExtensionFilesDao.saveExtensionFiles(mFormExtensionFiles);
////                    formExtensionFilesList.add(mFormExtensionFiles);
////                }
////                getPictureUrl = ServerUrlConstant.SERVER_EMPAPI_URL() + ServerUrlConstant.FilecontrollerPicture;
////                if (formExtensionFilesList != null && formExtensionFilesList.size() > 0) {
////                    for (int i = 0; i < formExtensionFilesList.size(); i++) {
////                        mFileTemp = new File(formExtensionFilesList.get(i).getFile_path());
////                        AnsynHttpRequest.requestByPostWithToken(this, mFileTemp, getPictureUrl, CHTTP.POSTWITHTOKEN, this, HTTPTYPEPICTUTR, LogManagerEnum.GGENERAL.functionCode);
////                    }
////
////                }
////            }
//        }
//    }
//
//    public class MenuOnClickListener implements OnClickListener {
//
//        @Override
//        public void onClick(View v) {
//            menuViewTag = v.getTag().toString();
//            netWorkManager.logFunactionStart(GeneralFormDetalActivity.this, GeneralFormDetalActivity.this, "commonformDetail", LogManagerEnum.GET_COMMONFORM_DO_ACTION.functionCode);
////            functionDetail();
//
//
//        }
//
//    }
//
//    public void AttentionFunction(String ActionName) {
//        final String tmpActionName = ActionName;
//        attention_flag = "0";
//        if (tmpActionName.equals("取消关注")) {
//            attention_flag = "0";
//        } else {
//            attention_flag = "1";
//        }
//        String path = ServerUrlConstant.SERVER_BASE_URL() + ServerUrlConstant.SET_COMMONFORM_YESORNO;
//        AsyncHttpClient client = new AsyncHttpClient();
//        OAConText instance = OAConText.getInstance(getApplicationContext());
//        client.addHeader("Content-Type", "application/json");
//        Attentioniteminfo mAttentioniteminfo = new Attentioniteminfo();
//        mAttentioniteminfo.iconurl = iconurl;
//        mAttentioniteminfo.title = docTitle;
//        mAttentioniteminfo.time = time;
//        mAttentioniteminfo.otherinfo1 = otherinfo1;
//        mAttentioniteminfo.otherinfo2 = otherinfo2;
//        mAttentioniteminfo.todoflag = todoflag;
//        mAttentioniteminfo.newtag = newtag;
//        mAttentioniteminfo.form_id = form_id;
//        mAttentioniteminfo.data_id = data_id;
//        mAttentioniteminfo.id = id;
//        mAttentioniteminfo.subsystemid = subsystemid;
//        mAttentioniteminfo.app_id = app_id;
//        mAttentioniteminfo.user_id = user_id;
//        mAttentioniteminfo.attention_flag = attention_flag;
//        mAttentioniteminfo.allow_push = "0";
//        try {
//            Gson mGson = new Gson();
//            String jsonToString = mGson.toJson(mAttentioniteminfo);
//            StringEntity stringEntity = new StringEntity(
//                    jsonToString.toString(), "utf-8");
//            RequestHandle post = client.post(getApplicationContext(), path,
//                    stringEntity, null, new JsonHttpResponseHandler() {
//                        @Override
//                        public void onSuccess(int statusCode, Header[] headers,
//                                              JSONObject response) {
//                            GetAttentionYesOrNoEntity mGetAttentionYesOrNoEntity = new GetAttentionYesOrNoEntity();
//                            if (response != null) {
//                                try {
//                                    Gson gson = new Gson();
//                                    mGetAttentionYesOrNoEntity = gson.fromJson(response.toString(), GetAttentionYesOrNoEntity.class);
//                                } catch (JsonSyntaxException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                            if (mGetAttentionYesOrNoEntity.getMessage().getStatusMessage().contains("添加关注")) {
//                                Toast.makeText(getApplicationContext(),
//                                        "添加关注成功", Toast.LENGTH_SHORT).show();
//                            } else if (mGetAttentionYesOrNoEntity.getMessage().getStatusMessage().contains("取消关注")) {
//                                Toast.makeText(getApplicationContext(),
//                                        "取消关注成功", Toast.LENGTH_SHORT).show();
//                            }
//                            for (int i = 0; i < mGetDetailEntity.getResult()
//                                    .getActions().size(); i++) {
//                                if (mGetDetailEntity.getResult()
//                                        .getActions().get(i).getAction_name().equals("添加关注")) {
//                                    mGetDetailEntity.getResult()
//                                            .getActions().get(i).setAction_name("取消关注");
//                                    break;
//                                }
//                                if (mGetDetailEntity.getResult()
//                                        .getActions().get(i).getAction_name().equals("取消关注")) {
//                                    mGetDetailEntity.getResult()
//                                            .getActions().get(i).setAction_name("添加关注");
//                                    break;
//                                }
//                            }
//                            LogManagerEnum.GET_COMMONFORM_DO_ACTION.functionId = LogManagerEnum.APP_DOC_INFO.functionId;  //修改functionid为0问题
//                            initArcMenuCommonForm(mGetDetailEntity.getResult().getActions());//重新刷新底部tab关注状态
//                            netWorkManager.logFunactionFinsh(GeneralFormDetalActivity.this, GeneralFormDetalActivity.this, "commonformDetailFail", LogManagerEnum.GET_COMMONFORM_DO_ACTION.functionCode, "添加关注成功", INetWorkManager.State.SUCCESS);
//                            super.onSuccess(statusCode, headers, response);
//                        }
//
//                        @Override
//                        public void onFailure(int statusCode, Header[] headers,
//                                              Throwable throwable, JSONObject errorResponse) {
//                            LogManagerEnum.GET_COMMONFORM_DO_ACTION.functionId = LogManagerEnum.APP_DOC_INFO.functionId;//修改functionid为0问题
//                            netWorkManager.logFunactionFinsh(GeneralFormDetalActivity.this, GeneralFormDetalActivity.this, "commonformDetailFail", LogManagerEnum.GET_COMMONFORM_DO_ACTION.functionCode, "添加关注失败", INetWorkManager.State.FAIL);
//                            super.onFailure(statusCode, headers, throwable,
//                                    errorResponse);
//                            throwable.printStackTrace();
//                        }
//                    });
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    private void hideLoadingShowError() {
//        mLoadingView.setVisibility(View.GONE);
//        mLoadingView.stopLoading();
//    }
//
//    private void hideLoadingView() {
//        mLoadingView.setVisibility(View.GONE);
//        mLoadingView.stopLoading();
//    }
//
//    private void showLoadingView() {
//        mLoadingView.setVisibility(View.VISIBLE);
//        mLoadingView.startLoading();
//    }
//
//    public void onFail(int taskType, int statusCode, String errorMsg,
//                       Object result) {
//        if (result != null) {
//
//            if (taskType == DocInfoModel.TYPE_GET_DETAILTASK) {
//
//                try {
//                    mGetDetailEntity = new GetDetailEntity();
//                    mGetDetailEntity.parseJson(result.toString());
//                } catch (Exception e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//                if (mGetDetailEntity.getMessage() != null && mGetDetailEntity.getMessage().getStatusMessage() != null) {
//                    mNewFragment = MyAlertDialogFragment.newInstance(mGetDetailEntity.getMessage().getStatusMessage(),
//                            R.drawable.prompt_warn, mDialogCancelListener, confirmAndExitListener, false);
//                    try {
//                        mNewFragment.show(getSupportFragmentManager(), "dialog");
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//                }
//
//            } else if (taskType == GetCommonFromInfoModel.TYPE_DOACTION_TASK) {
//                mGetDoActionEntity = new GetDoActionEntity();
//                try {
//                    mGetDoActionEntity.parseJson(result.toString());
//                } catch (Exception e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//                if (mGetDoActionEntity.getResult() != null) {
//
//                    mNewFragment = MyAlertDialogFragment.newInstance("失败:" + mGetDoActionEntity.getResult().getError_msg() + "("
//                                    + mGetDoActionEntity.getResult().getResult_code()
//                                    + ")",
//                            R.drawable.prompt_warn, null, confirmListener, false);
//
//                    mNewFragment.show(getSupportFragmentManager(), "dialog");
//                } else
//                    Toast.makeText(GeneralFormDetalActivity.this, "操作失败！",
//                            Toast.LENGTH_SHORT).show();
//            }
//        } else {
//            //展示其他错误。。。。
//            Toast.makeText(GeneralFormDetalActivity.this, "操作失败！" + errorMsg,
//                    Toast.LENGTH_SHORT).show();
//        }
//        hideLoadingView();
//        if (!Utils.isNetworkAvailable()) {
//            mEmptyLayout.setNoWifiButtonClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    showLoadingView();
//                    mEmptyLayout.hide();
//                    mGetCommonFromInfoModel.getDataFromServerByType(GetCommonFromInfoModel.TYPE_GET_DETAILTASK,
//                            mDetailparameter);
//                }
//            });
//            mEmptyLayout.showNowifi();
//        } else if (!isOneSuccess) {
//            mEmptyLayout.showError();
//        }
//
//    }
//
//    int screenWidth;
//    int screenHeight;
//    int lastX;
//    int lastY;
//    private boolean isTuoZhuai = false;
//    private float x, y;
//    private float ex, ey;
//    private int left, top, right, bottom;
//    private boolean action_move = false;
//    private long startTime = 0;
//    private long endTime = 0;
//
//    @Override
//    public boolean onTouch(View v, MotionEvent event) {
//        int action = event.getAction();
//        ex = 0;
//        ey = 0;
//        switch (action) {
//            case MotionEvent.ACTION_DOWN:
//
//                startTime = System.currentTimeMillis();
//                x = event.getX();
//                y = event.getY();
//                lastX = (int) event.getRawX();
//                lastY = (int) event.getRawY();
//                isTuoZhuai = false;
//                break;
//            /**
//             * layout(l,t,r,b) l Left position, relative to parent t Top position,
//             * relative to parent r Right position, relative to parent b Bottom
//             * position, relative to parent
//             * */
//            case MotionEvent.ACTION_MOVE:
//
//                action_move = true;
//                isTuoZhuai = true;
//                int dx = (int) event.getRawX() - lastX;
//                int dy = (int) event.getRawY() - lastY;
//                left = v.getLeft() + dx;
//                top = v.getTop() + dy;
//                right = v.getRight() + dx;
//                bottom = v.getBottom() + dy;
//                if (left < 0) {
//                    left = 0;
//                    right = left + v.getWidth();
//                }
//                if (right > screenWidth) {
//                    right = screenWidth;
//                    left = right - v.getWidth();
//                }
//                if (top - (mFunctionPopupWindow.isShowing() ? popupHeight : 0) < 0) {
//                    top = mFunctionPopupWindow.isShowing() ? popupHeight : 0;
//                    bottom = top + v.getHeight();
//                }
//                if (bottom > screenHeight) {
//                    bottom = screenHeight;
//                    top = bottom - v.getHeight();
//                }
//
//                v.layout(left, top, right, bottom);
//                lastX = (int) event.getRawX();
//                lastY = (int) event.getRawY();
//                int[] location = new int[2];
//                v.getLocationOnScreen(location);
//                mFunctionPopupWindow.update(location[0] - popupWidth, location[1]
//                        - popupHeight, -1, -1);
//                break;
//            case MotionEvent.ACTION_UP:
//
//                endTime = System.currentTimeMillis();
//                ex = event.getX() - x;
//                ey = event.getY() - y;
//                //当从点击到弹起小于半秒的时候,则判断为点击,如果超过则不响应点击事件
//                //以前是根据点击挪动距离来进行判断，有些手机敏感性强导致无法弹出等问题
//                if ((endTime - startTime) > 0.1 * 2000L) {
//                    isTuoZhuai = true;
//                } else {
//                    isTuoZhuai = false;
//                }
//                break;
//        }
//        return false;
//    }
//
//    @Override
//    public void callBackLayout() {
//        // TODO Auto-generated method stub
//        if (action_move)
//            menuMultipleActions.layout(left, top, right, bottom);
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (HtmitechApplication.isHomeBack) {
//            netWorkManager = LogManagerProxy.getProxyInstance(NetWorkManager.class);
//            netWorkManager.logFunactionOnce(this, this, "login_home_back", LogManagerEnum.APP_RESUME.functionCode);
//            HtmitechApplication.isHomeBack = false;
//        }
//        //注册监听按home键的广播
//        RegistOrUnRegisterReceive.registerHomeKeyEventReceive(this);
//    }
//
//    @Override
//    protected void onPause() {
//        RegistOrUnRegisterReceive.unRegisterHomeKeyEventReceive(this);
//        super.onPause();
//    }
//}
