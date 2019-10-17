package activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.schedulemanagerlibrary.R;
import com.htmitech.api.BookInit;
import com.htmitech.base.BaseFragmentActivity;
import com.htmitech.myEnum.BottomButtonSlyteEnum;
import com.htmitech.myEnum.LogManagerEnum;
import com.htmitech.thread.AnsynHttpRequest;
import com.htmitech.thread.CHTTP;
import com.htmitech.thread.GetFinalRefreshTokenAfterRequestValue;
import com.htmitech.utils.OAConText;
import com.minxing.kit.api.MXAPI;
import com.minxing.kit.api.bean.ShareLink;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Interface.CallBackLayout;
import Interface.INetWorkManager;
import Interface.SelectPoisition;
import adapter.WorkFlowForCollectionAdapter;
import dao.AppliationCenterDao;
import entity.ChangedFieldsInfo;
import entity.DoActionParameter;
import entity.DoActionResultInfo;
import entity.EventApiParameters;
import entity.EventApiResultInfo;
import entity.RouteInfo;
import entity.SchusersCertainRequest;
import entity.SchusersCertainResponseRoot;
import entity.StartDocFlowParameterBuild;
import entiy.AppInfo;
import entiy.AppVersion;
import fragment.ScheduleFormFragment;
import htmitech.com.componentlibrary.entity.ActionInfo;
import htmitech.com.componentlibrary.entity.DocInfoDes;
import htmitech.com.componentlibrary.entity.DocResultInfo;
import htmitech.com.componentlibrary.entity.EditField;
import htmitech.com.componentlibrary.entity.EditFieldList;
import htmitech.com.componentlibrary.entity.FieldItem;
import htmitech.com.componentlibrary.entity.InfoRegion;
import htmitech.com.componentlibrary.entity.InfoTab;
import htmitech.com.componentlibrary.listener.ObserverCallBackType;
import htmitech.com.componentlibrary.unit.ServerUrlConstant;
import htmitech.formConfig.SelectPoisition7001;
import proxy.LogManagerProxy;
import proxy.NetWorkManager;
import schedulebean.ScheduleCpId;
import schedulebean.ScheduleCreateRequest;
import schedulebean.ScheduleDeleteRequestBean;
import schedulebean.ScheduleDetailRequest;
import schedulebean.ScheduleDetailResultBean;
import schedulebean.SchedulePlaceBean;
import schedulebean.ScheduleUpdateBean;
import utils.DeviceUtils;
import utils.NetworkUtil;
import utils.ScheduleUtils;
import widget.AddFloatingActionButton;
import widget.DetailActivityLayout;
import widget.EmptyLayout;
import widget.FloatingActionButton;
import widget.FunctionPopupWindow;
import widget.LoadingView;
import widget.MainViewPager;
import widget.MyAlertDialogFragment;
import widget.MyNextCodeDialog;
import widget.MyNextPersonDialog;
import widget.NewTopTabIndicator;


/***
 * @author gulbel 发起流程详情页面
 */
public class ScheduleDetailActivity extends BaseFragmentActivity implements
        OnClickListener, View.OnTouchListener, CallBackLayout, ObserverCallBackType {

//    @InjectView(R.id.tv_error_warning)
    TextView tvErrorWarning;
    private MainViewPager mViewPager_mycollection;
    private WorkFlowForCollectionAdapter mAdapter;
    private NewTopTabIndicator mMyTopTabIndicator;
//    private DocInfoModel mDocInfoModel;
    public DocResultInfo mDocResultInfo;
    private LoadingView mLoadingView = null;
    public int isWaterSecurity = 0;
    public int com_workflow_mobileconfig_IM_enabled = 0;
    public String app_version_id = "";
    public String docId = "";

    private AddFloatingActionButton menuMultipleActions = null;
    private HashMap<String, ChangedFieldsInfo> dictoryHashMap = new HashMap();

    private String mDocAttachmentID = null;

    private MyNextCodeDialog nextcodeDialog; // 单选路由
    //	private MyNextRouteDialog nextrouteDialog; // 多选路由
    private MyNextPersonDialog nextpersonDialog;
    private boolean IsMultiSelectUser;
    private boolean IsMultiSelectRoute;


    private RouteInfo hasSelectedRouteInfo; // add by gulbel 2015-08-20

    private DoActionResultInfo mDoActionResultInfo;
    private DoActionParameter mDoActionParameter;

    private String comment; // 在流程中显示的意见，以用户最后在表单上输入的意见为准

    private String currentDocID; // 发起后返回在docID

    private String currentDocKind; // 发起后返回在docID

    private MyAlertDialogFragment mNewFragment = null;
    //    private DetailActivityLayout mDetailActivityLayout;
    private EmptyLayout mEmptyLayout;
    public String comeShare = "0";
    public String app_id;
    private InfoTab docInfoDes;
    private InfoTab tmpDocInfoDes;
    private String dropdownOptionManual;
    public List<ChangedFieldsInfo> dictory;
    private ArrayList<ActionInfo> mDataList = new ArrayList<ActionInfo>();
    private String flag;   //0为创建 1为详情    2为分享过来的
    private String schId;
    private ShareLink shareLink;
    private ScheduleFormFragment mScheduleFormFragment;
    private ScheduleDetailResultBean scheduleDetailResultBean;
    public String cpTitle;
    public SchedulePlaceBean schPlace;     // 详情获取到的位置信息
    private String corpIdShare;
    private String groupCorpIdShare;
    private String userIDShare;
    private ScheduleCreateRequest scheduleCreateRequest;   //日程创建实体
    private String deleteUrl;
    private ScheduleDeleteRequestBean mScheduleDeleteRequestBean;
    private String updateUrl;
    private ScheduleUpdateBean scheduleUpdateBean;
    private String createUrl;
    private String detailUrl;
    private boolean isEdit = false;     //当前是否为编辑状态
    private ArrayList<String> hidenList;
    private ArrayList<String> mustInputList;
    private String confirmMineUrl;
    private SchusersCertainRequest confirmMineScheduleRequest;
    private TextView tv_error_warning;


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
    private String actionbuttonStyle;
    private String createviewConfig;
    private String detailviewConfig;
    public String includeSecurity;
    private DetailActivityLayout mDetailActivityLayout;
    public static String event_api_url = ServerUrlConstant.SERVER_JAVA_URL() + ServerUrlConstant.OA_SCHEDULE_EVENTAPI_JAVA;
    public static String HTTPEVENTAPI = "httpeventapi";

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        mDetailActivityLayout = (DetailActivityLayout) LayoutInflater
                .from(this).inflate(R.layout.activity_schedule_detail, null);
        setContentView(mDetailActivityLayout);
        tvErrorWarning = (TextView) findViewById(R.id.tv_error_warning);
        netWorkManager = LogManagerProxy.getProxyInstance(NetWorkManager.class);
        tvErrorWarning.setOnClickListener(this);
        mDetailActivityLayout.setValue(this);
        Intent intent = getIntent();
        actionbuttonStyle = intent.getStringExtra("com_schedule_mobileconfig_actionbutton_style");
        createviewConfig = intent.getStringExtra("com_schedule_mobileconfig_createview_config");
        detailviewConfig = intent.getStringExtra("com_schedule_mobileconfig_detailview_config");
        includeSecurity = intent.getStringExtra("com_schedule_mobileconfig_include_security");
        if (!TextUtils.isEmpty(includeSecurity) && includeSecurity.equals("1")) {
            isWaterSecurity = 1;
        }
        dropdownOptionManual = intent.getStringExtra("com_schedule_mobileconfig_dropdown_option_manual");
        app_version_id = intent.getStringExtra("app_version_id");
        app_id = intent.getStringExtra("app_id");
        flag = intent.getStringExtra("flag");
        schId = intent.getStringExtra("sch_id");
        userIDShare = intent.getStringExtra("UserID");
        corpIdShare = intent.getStringExtra("corpId");    // 分享用
        groupCorpIdShare = intent.getStringExtra("groupCorpId");// 分享用
        schId = intent.getStringExtra("sch_id");
        if (null != flag && flag.equals("0")) {
            ActionInfo actionInfo1 = new ActionInfo();
            actionInfo1.setActionName("提交");
            actionInfo1.setActionID("create");
            mDataList.add(actionInfo1);
        }
        initConfigData();
        if(null == mDocResultInfo && null ==  mDocResultInfo.getResult()){
            Toast.makeText(this, "参数配置异常", Toast.LENGTH_SHORT).show();
            return;
        }
        if (flag.equals("1")) {    //获取详情
            getDataFromServer();
        } else if (flag.equals("2")) {    // 分享时获取详情
            getDataFromServerThrowShare();
        }
//        mDocResultInfo.getResult().TabItems = new ArrayList<InfoTab>(this.docInfoDes);
        ArrayList<InfoTab> infoTabs = new ArrayList<InfoTab>();
        infoTabs.add(docInfoDes);
        if (null != mDocResultInfo && null != mDocResultInfo.getResult())
            mDocResultInfo.getResult().TabItems = infoTabs;
//        mDocResultInfo = (DocResultInfo) createviewConfig;
        mEmptyLayout = (EmptyLayout) findViewById(R.id.emptyLayout);
        tv_error_warning = (TextView) findViewById(R.id.tv_error_warning);
//        mEmptyLayout.setErrorButtonClickListener(this);
        mBottomButtonSlyteEnum = BookInit.getInstance().getmBottomButtonEnum();
        BookInit.getInstance().setIsWaterSecurity(Integer.parseInt(includeSecurity == null ? "0" : includeSecurity));      // 设置水印
        formMap = new HashMap<String, Object>();
        if (null != mDocResultInfo && null != mDocResultInfo.getResult())
            initView();
//        menuMultipleActions.setVisibility(View.VISIBLE);
    }

    private void initConfigData() {
        try {
            if (null != flag && flag.equals("0")) {
                docInfoDes = JSONObject.parseObject(createviewConfig, InfoTab.class);
                for (int i = 0; i < docInfoDes.regions.length; i++) {
                    if (!docInfoDes.regions[i].isSplitRegion()) {     //不为折叠表的时候将mode至为1
                        for (int j = 0; j < docInfoDes.regions[i].getFieldItems().length; j++) {
                            docInfoDes.regions[i].getFieldItems()[j].setMode("1");
                        }
                    }
                }
                tmpDocInfoDes = JSONObject.parseObject(createviewConfig, InfoTab.class);
                for (int i = 0; i < tmpDocInfoDes.regions.length; i++) {
                    if (!docInfoDes.regions[i].isSplitRegion()) {     //不为折叠表的时候将mode至为1
                        for (int j = 0; j < tmpDocInfoDes.regions[i].getFieldItems().length; j++) {
                            tmpDocInfoDes.regions[i].getFieldItems()[j].setMode("1");
                        }
                    }
                }
            } else if (null != flag && flag.equals("2")) {   //分享
                AppliationCenterDao appliationCenterDao = new AppliationCenterDao(this);
                AppInfo appInfo = appliationCenterDao.getAppInfo(app_id);
                if (null != appInfo && null != appInfo.getmAppVersionList() && appInfo.getmAppVersionList().size() > 0) {
                    AppVersion appVersion = appInfo.getmAppVersionList().get(0);
                    if (null != appVersion && null != appVersion.getAppVersionConfigArrayList()) {
                        for (int i = 0; i < appVersion.getAppVersionConfigArrayList().size(); i++) {
                            if (appVersion.getAppVersionConfigArrayList().get(i).getConfig_code().equals("com_schedule_mobileconfig_detailview_config")) {
                                detailviewConfig = appVersion.getAppVersionConfigArrayList().get(i).getConfig_value();
                            } else if (appVersion.getAppVersionConfigArrayList().get(i).getConfig_code().equals("com_schedule_mobileconfig_dropdown_option_manual")) {
                                dropdownOptionManual = appVersion.getAppVersionConfigArrayList().get(i).getConfig_value();
                            }
                        }
                    }
                    docInfoDes = JSONObject.parseObject(detailviewConfig, InfoTab.class);
                    tmpDocInfoDes = JSONObject.parseObject(detailviewConfig, InfoTab.class);
                }

            } else {
                docInfoDes = JSONObject.parseObject(detailviewConfig, InfoTab.class);

                tmpDocInfoDes = JSONObject.parseObject(detailviewConfig, InfoTab.class);

            }

            dictory = JSONObject.parseArray(dropdownOptionManual, ChangedFieldsInfo.class);
            for (int i = 0; i < dictory.size(); i++) {
                dictoryHashMap.put(dictory.get(i).getFieldKey(), dictory.get(i));
                for (int j = 0; j < docInfoDes.regions.length; j++) {
                    for (int k = 0; k < docInfoDes.regions[j].getFieldItems().length; k++) {
                        if (docInfoDes.regions[j].getFieldItems()[k].getKey().equals(dictory.get(i).getFieldKey())) {
                            docInfoDes.regions[j].getFieldItems()[k].setDics(dictory.get(i).getDics());
                        }
                    }
                }
            }
            for (int i = 0; i < dictory.size(); i++) {
                for (int j = 0; j < tmpDocInfoDes.regions.length; j++) {
                    for (int k = 0; k < tmpDocInfoDes.regions[j].getFieldItems().length; k++) {
                        if (tmpDocInfoDes.regions[j].getFieldItems()[k].getKey().equals(dictory.get(i).getFieldKey())) {
                            tmpDocInfoDes.regions[j].getFieldItems()[k].setDics(dictory.get(i).getDics());
                        }
                    }
                }
            }
            mDocResultInfo = new DocResultInfo();
            DocInfoDes docInfoDess = new DocInfoDes();
            mDocResultInfo.setResult(docInfoDess);

        } catch (Exception e) {
            e.printStackTrace();
        }
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
        mLoadingView = (LoadingView) findViewById(R.id.loadingview_detail);


        titDoc = ((TextView) titleBar.findViewById(R.id.textview_titlebar_title));
        if (flag.equals("0")) {
            titDoc.setText("日程创建");
        } else {
            titDoc.setText("日程详情");
        }
        if (flag.equals("0")) {
            getEventApiData();

        }

    }

    public String getDocKind() {
        return currentDocKind;
    }

    public String getDocType() {
        String docType = getIntent().getStringExtra("com_workflow_plugin_startor_paramter");
        return docType;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (flag.equals("0")) {
                if(null != mScheduleFormFragment){
                    List<EditField> editFileds = mScheduleFormFragment.getEditFileds();
                    for (int i = 0; i < editFileds.size(); i++) {
                        if (!TextUtils.isEmpty(editFileds.get(i).getValue()) && !TextUtils.isEmpty(editFileds.get(i).getInput()) && !editFileds.get(i).getInput().equals("401")) {
                            isEdit = true;
                        }
                    }
                }
            }
            if (isEdit)
                exit();
            else
                finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        int i1 = v.getId();
        if (i1 == R.id.imgview_titlebar_back) {
            if (flag.equals("0")) {
                List<EditField> editFileds = mScheduleFormFragment.getEditFileds();
                for (int i = 0; i < editFileds.size(); i++) {
                    if (!TextUtils.isEmpty(editFileds.get(i).getValue()) && !TextUtils.isEmpty(editFileds.get(i).getInput()) && !editFileds.get(i).getInput().equals("401")) {
                        isEdit = true;
                    }
                }
            }
            if (isEdit)
                exit();
            else
                finish();

        } else if (i1 == R.id.tv_error_warning) {
            tvErrorWarning.setVisibility(View.GONE);

        } else if (i1 == R.id.function) {
            if (!isTuoZhuai) {
                if (!mFunctionPopupWindow.isShowing()) {
//                        menuMultipleActions.startAnimation(animation);
                    menuMultipleActions.setImageDrawable(getResources().getDrawable(R.drawable.btn_operation_homelabel_on));
                    mFunctionPopupWindow = new FunctionPopupWindow(this,
                            new MenuOnClickListener(), mDataList.size());
                    mFunctionPopupWindow.initArcMenu(mDataList);
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
                    // function.setBackgroundResource(R.drawable.zt_htmitech_shezhi_1);
                    mFunctionPopupWindow.dismiss();
                }
            }

        }
    }

    int popupHeight;
    int popupWidth;

    /**
     * 退出
     **/
    public void exit() {
        final com.htmitech.pop.AlertDialog mDialog = new com.htmitech.pop.AlertDialog(ScheduleDetailActivity.this);
        mDialog.builder().setTitle("是否放弃已编辑的内容？")
                .setNegativeButton("继续编辑", new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.setDismiss();
                    }
                })
                .setPositiveButton("放弃", new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.setDismiss();
                        finish();
                    }
                }).setType().show();
    }

    public void initData() {
//        netWorkManager.logFunactionFinsh(ScheduleDetailActivity.this, ScheduleDetailActivity.this, "nativeStartSuccess", LogManagerEnum.STARTDOC_FLOW.functionCode, "success", INetWorkManager.State.SUCCESS);
        mViewPager_mycollection = (MainViewPager) findViewById(R.id.viewPager_qarank);
        FragmentManager fm = getSupportFragmentManager();
        mViewPager_mycollection.setOffscreenPageLimit(4);
        mMyTopTabIndicator = (NewTopTabIndicator) this
                .findViewById(R.id.topTabIndicator_detail);

//        mDocResultInfo.getResult().setDocType(getDocType());
//                mCurrentNodeName = mDocResultInfo.getResult().getCurrentNodeName();
        currentDocID = mDocResultInfo.getResult().getDocID();
//        currentDocKind = mDocResultInfo.getResult().getKind(); // 2015-08-11
//                flowId = mDocResultInfo.getResult().getFlowId();
        SelectPoisition.getInstances().setmScheduleCpId(null);
        ArrayList<WorkFlowForCollectionAdapter.ChildType> list = new ArrayList<WorkFlowForCollectionAdapter.ChildType>();
        List<String> listStr = new ArrayList<String>();
        listStr.add("新建表单");
        list.add(WorkFlowForCollectionAdapter.ChildType.TAB_SCHEDULE_FORM);
        String[] arrayTopTabIndicator = new String[listStr.size()];
        listStr.toArray(arrayTopTabIndicator);
//                titDoc.setText(mCurrentNodeName);
        if (arrayTopTabIndicator.length != 1) {
            mMyTopTabIndicator.setCommonData2(mViewPager_mycollection,
                    arrayTopTabIndicator, R.color.ht_hred_title,
                    R.color.color_title);
        }
        List<InfoTab> tabItems = mDocResultInfo.getResult().TabItems;
        mAdapter = new WorkFlowForCollectionAdapter(this, fm, list, mDocResultInfo.getResult().getDocID(), tabItems, app_id, 1);
        if ((mViewPager_mycollection != null) && (mAdapter != null))
            mViewPager_mycollection.setAdapter(mAdapter);

        mScheduleFormFragment = (ScheduleFormFragment) mAdapter.getItem(0);
        if (null == mDataList) {
            menuMultipleActions = (AddFloatingActionButton) findViewById(R.id.function);
            menuMultipleActions.setSize(FloatingActionButton.SIZE_NORMAL);
            menuMultipleActions.setOnClickListener(this);
            menuMultipleActions.setOnTouchListener(this);

        } else {
            menuMultipleActions = (AddFloatingActionButton) findViewById(R.id.function);
            menuMultipleActions.setSize(FloatingActionButton.SIZE_NORMAL);
            menuMultipleActions.setOnClickListener(this);
            menuMultipleActions.setOnTouchListener(this);

            mFunctionPopupWindow = new FunctionPopupWindow(this, new MenuOnClickListener(), mDataList.size());
            mFunctionPopupWindow.initArcMenu(mDataList);
            menuMultipleActions.setVisibility(View.VISIBLE);
        }
        if (!TextUtils.isEmpty(flag) && flag.equals("2")) {
            menuMultipleActions.setVisibility(View.GONE);
        } else {
            menuMultipleActions.setVisibility(View.VISIBLE);
        }
        if (null != flag && flag.equals("1") || null != flag && flag.equals("2")) {
            for (int i = 0; i < mDocResultInfo.getResult().TabItems.get(0).regions.length; i++) {
                for (int j = 0; j < mDocResultInfo.getResult().TabItems.get(0).regions[i].getFieldItems().length; j++) {
                    mDocResultInfo.getResult().TabItems.get(0).regions[i].getFieldItems()[j].setMode("10");
                }
            }

        }

    }

    @Override
    public void success(String requestValue, int type, String requestName) {
        dismissDialog();
        if (requestName.equals(HTTPEVENTAPI)) {
            try {
                if (requestValue != null) {
                    EventApiResultInfo mEventApiResultInfo = JSONObject.parseObject(requestValue, EventApiResultInfo.class);
                    if (null != mEventApiResultInfo && null != mEventApiResultInfo.getResult() && null != mEventApiResultInfo.getResult().getChangedFields()) {
                        for (int i = 0; i < mEventApiResultInfo.getResult().getChangedFields().size(); i++) {
                            for (int j = 0; j < mDocResultInfo.getResult().TabItems.get(0).regions.length; j++) {
                                for (int k = 0; k < mDocResultInfo.getResult().TabItems.get(0).regions[j].getFieldItems().length; k++) {
                                    if (mEventApiResultInfo.getResult().getChangedFields().get(i).getField_key().equals(mDocResultInfo.getResult().TabItems.get(0).regions[j].getFieldItems()[k].getKey())) {
                                        mDocResultInfo.getResult().TabItems.get(0).regions[j].getFieldItems()[k].setValue(mEventApiResultInfo.getResult().getChangedFields().get(i).getFieldValue());
                                    }
                                }
                            }
                        }
                    }
//
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                initData();
            }
        } else if (requestName.equals("getDetail")) {
            try {
                requestValue = GetFinalRefreshTokenAfterRequestValue.getFinalRequestValue(this, requestValue, type, detailUrl, scheduleDetailResultBean, this, "getDetail", LogManagerEnum.SCHEDULEDETAIL.getFunctionCode());
                if (!TextUtils.isEmpty(requestValue)) {
                    scheduleDetailResultBean = JSONObject.parseObject(requestValue, ScheduleDetailResultBean.class);
                    netWorkManager.logFunactionFinsh(ScheduleDetailActivity.this, ScheduleDetailActivity.this, "getScheduleDataFinish", LogManagerEnum.SCHEDULEDETAIL.getFunctionCode(), scheduleDetailResultBean.message, INetWorkManager.State.SUCCESS);
                    schPlace = scheduleDetailResultBean.result.schPlace;
                    JSONObject buttonInfo = JSONObject.parseObject(scheduleDetailResultBean.result.buttonInfo);
                    Class<?> clazz = scheduleDetailResultBean.result.getClass();
                    Field[] fields = clazz.getDeclaredFields();

                    for (Field field : fields) {
                        String name = field.getName();
                        Field f = scheduleDetailResultBean.result.getClass().getDeclaredField(name);
                        f.setAccessible(true);

                        for (InfoRegion infoRegion : docInfoDes.regions) {
                            for (FieldItem mFieldItem : infoRegion.getFieldItems()) {
                                if (name.equals(mFieldItem.getKey())) {
                                    String value = "";
                                    if (f.get(scheduleDetailResultBean.result) instanceof SchedulePlaceBean) {
                                        SchedulePlaceBean mSchedulePlaceBean = (SchedulePlaceBean) f.get(scheduleDetailResultBean.result);
                                        value = mSchedulePlaceBean.cpTitle;
                                        ///
                                        ScheduleCpId scheduleCpId = new ScheduleCpId();
                                        scheduleCpId.cpDesc = mSchedulePlaceBean.cpDesc;
                                        scheduleCpId.cpTitle = mSchedulePlaceBean.cpTitle;
                                        scheduleCpId.cpLatitude = mSchedulePlaceBean.cpLatitude;
                                        scheduleCpId.cpLongitude = mSchedulePlaceBean.cpLongitude;
                                        SelectPoisition.getInstances().setmScheduleCpId(scheduleCpId);
                                    } else {
                                        value = f.get(scheduleDetailResultBean.result) + "";
                                    }

                                    mFieldItem.setValue(value);

                                    for (ChangedFieldsInfo mChangedFieldsInfo : dictory) {
                                        if (mChangedFieldsInfo.getFieldKey().equals("" + name)) {
                                            List<FieldItem.dic> dicsValue = mChangedFieldsInfo.getDics();
                                            for (FieldItem.dic dics : dicsValue) {
                                                if (dics.value.equals(value)) {
                                                    mFieldItem.setValue(dics.name);
                                                    break;
                                                }
                                            }

                                        }
                                    }
                                    break;
                                }
                            }
                        }
                    }


                    if (null != buttonInfo && null != buttonInfo.getString("actions")) {
                        String actions = buttonInfo.getString("actions");
                        JSONObject buttonBean = JSONObject.parseObject(actions);
                        if (null != mDataList) {
                            mDataList.clear();
                        }
                        for (String key : buttonBean.keySet()) {
                            String value = (String) buttonBean.get(key);
                            ActionInfo actionInfo = new ActionInfo();
                            actionInfo.setActionName(value);
                            actionInfo.setActionID(key);
                            mDataList.add(actionInfo);
                        }
                    }
                    if (null != buttonInfo && null != buttonInfo.getString("hidenfields")) {
                        String hidenfields = buttonInfo.getString("hidenfields");
                        hidenList = new ArrayList<String>();
                        String[] split = hidenfields.split(",");
                        if (null != split && split.length > 0) {
                            for (int i = 0; i < split.length; i++) {
                                hidenList.add(split[i]);
                            }
                        }
                        InfoRegion[] mInfoRegionList = new InfoRegion[docInfoDes.regions.length];
                        int index = 0;
                        for (int j = 0; j < hidenList.size(); j++) {
                            for (int i = 0; i < docInfoDes.regions.length; i++) {
                                boolean isFlag = false;
                                for (int k = 0; k < docInfoDes.regions[i].getFieldItems().length; k++) {
                                    if (docInfoDes.regions[i].getFieldItems()[k].getKey().equals(hidenList.get(j))) {
                                        isFlag = true;
                                        break;
                                    }
                                }
                                if (!isFlag) {
                                    mInfoRegionList[index++] = docInfoDes.regions[i];
                                }
                            }
                        }
                        docInfoDes.regions = Arrays.copyOf(mInfoRegionList, index);
//                        tmpDocInfoDes.get(0).Regions = Arrays.copyOf(mInfoRegionList, index);
//                        mDocResultInfo.getResult().TabItems = new ArrayList<InfoTab>(docInfoDes);
                        ArrayList<InfoTab> infoTabs = new ArrayList<InfoTab>();
                        infoTabs.add(docInfoDes);
                        mDocResultInfo.getResult().TabItems = infoTabs;
                    }
                    mustInputList = new ArrayList<String>();
                    try {
                        if (null != buttonInfo && null != buttonInfo.getString("mustinput")) {
                            String mustinput = buttonInfo.getString("mustinput");
                            String[] split = mustinput.split(",");
                            if (null != split && split.length > 0) {
                                for (int i = 0; i < split.length; i++) {
                                    mustInputList.add(split[i]);
                                }
                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Map map = JSONObject.parseObject(requestValue, Map.class);
                    Map<String, Object> result = (Map) map.get("result");
                    for (String key : result.keySet()) {
                        ChangedFieldsInfo mChangedFieldsInfo = new ChangedFieldsInfo();
                        if (result.get(key) instanceof JSONArray) {      //将人员信息数组转为string串
                            String span = "";
                            JSONArray array = (JSONArray) result.get(key);
                            for (int i = 0; i < array.size(); i++) {
                                span += array.get(i).toString() + ";";
                            }
                            mChangedFieldsInfo.setFieldValue(span);
                        } else {
                            for (int i = 0; i < dictory.size(); i++) {
                                String field_key = dictory.get(i).getFieldKey();
                                if (field_key.equals(key)) {
                                    String value = result.get(field_key) + "";
                                    for (int j = 0; j < dictory.get(i).getDics().size(); j++) {
                                        if (dictory.get(i).getDics().get(j).value.equals(value)) {
                                            result.put(key, dictory.get(i).getDics().get(j).name);
                                        }
                                    }
                                }
                            }
                            mChangedFieldsInfo.setFieldValue(result.get(key).toString());
                        }

                        mChangedFieldsInfo.setFieldKey(key);
                        dictory.add(mChangedFieldsInfo);
                    }
                    if (null != scheduleDetailResultBean && null != scheduleDetailResultBean.result) {
                        cpTitle = scheduleDetailResultBean.result.schPlace.cpTitle;
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            initData();
        } else if (requestName.equals("createSchedule")) {
            ScheduleDetailResultBean mScheduleDetailResultBean = null;
            requestValue = GetFinalRefreshTokenAfterRequestValue.getFinalRequestValue(this, requestValue, type, createUrl, scheduleCreateRequest, this, "createSchedule", LogManagerEnum.SCHEDULECREATE.getFunctionCode());
            if (!TextUtils.isEmpty(requestValue)) {
                try {
                    mScheduleDetailResultBean = JSONObject.parseObject(requestValue, ScheduleDetailResultBean.class);
                    netWorkManager.logFunactionFinsh(ScheduleDetailActivity.this, ScheduleDetailActivity.this, "createScheduleFinish", LogManagerEnum.SCHEDULECREATE.getFunctionCode(), mScheduleDetailResultBean.message, INetWorkManager.State.SUCCESS);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (null != scheduleCreateRequest) {
                    String schBeginTime = scheduleCreateRequest.schBeginTime + ":00";
                    String firstRemind = scheduleCreateRequest.firstRemind;
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    long time = 0;
                    try {
                        time = simpleDateFormat.parse(schBeginTime).getTime();
                        if (null != mScheduleDetailResultBean && !TextUtils.isEmpty(firstRemind) && !firstRemind.equals("0")) {
                            ScheduleUtils.setAlarmTime(this, time - ((Integer.parseInt(firstRemind) - 1) * 5 * 60 * 1000), app_id,
                                    mScheduleDetailResultBean.result.schId, detailviewConfig, dropdownOptionManual, includeSecurity,
                                    scheduleCreateRequest.schTitle, scheduleCreateRequest.firstRemind);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                if (null != mScheduleDetailResultBean && mScheduleDetailResultBean.code == 200) {
                    Toast.makeText(ScheduleDetailActivity.this, mScheduleDetailResultBean.message, Toast.LENGTH_SHORT).show();
                } else if (null != mScheduleDetailResultBean) {
                    Toast.makeText(ScheduleDetailActivity.this, mScheduleDetailResultBean.message.length() > 100 ?
                            mScheduleDetailResultBean.message.substring(0, 99) : mScheduleDetailResultBean.message, Toast.LENGTH_SHORT).show();
                }

                finish();
            }

        } else if (requestName.equals("update")) {
            requestValue = GetFinalRefreshTokenAfterRequestValue.getFinalRequestValue(this, requestValue, type, updateUrl, scheduleUpdateBean, this, "update", LogManagerEnum.SCHEDULEUPDATE.getFunctionCode());
            if (!TextUtils.isEmpty(requestValue)) {
                ScheduleDetailResultBean scheduleDetailResultBean = JSONObject.parseObject(requestValue, ScheduleDetailResultBean.class);
                if (null != scheduleDetailResultBean && scheduleDetailResultBean.code == 200) {
                    netWorkManager.logFunactionFinsh(ScheduleDetailActivity.this, ScheduleDetailActivity.this, "updateScheduleFinish", LogManagerEnum.SCHEDULEUPDATE.getFunctionCode(), scheduleDetailResultBean.message, INetWorkManager.State.SUCCESS);
                    Toast.makeText(ScheduleDetailActivity.this, scheduleDetailResultBean.message, Toast.LENGTH_SHORT).show();
                } else if (null != scheduleDetailResultBean) {
                    Toast.makeText(ScheduleDetailActivity.this, scheduleDetailResultBean.message.length() > 100 ?
                            scheduleDetailResultBean.message.substring(0, 99) : scheduleDetailResultBean.message, Toast.LENGTH_SHORT).show();
                }
                finish();
            }

        } else if (requestName.equals("deleteSchedule")) {
            requestValue = GetFinalRefreshTokenAfterRequestValue.getFinalRequestValue(this, requestValue, type, deleteUrl, mScheduleDeleteRequestBean, this, "deleteSchedule", LogManagerEnum.SCHEDULEDELETE.getFunctionCode());
            if (!TextUtils.isEmpty(requestValue)) {
                netWorkManager.logFunactionFinsh(ScheduleDetailActivity.this, ScheduleDetailActivity.this, "deleteScheduleFinish", LogManagerEnum.SCHEDULEDELETE.getFunctionCode(), requestValue, INetWorkManager.State.SUCCESS);
                Toast.makeText(ScheduleDetailActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else if (requestName.equals("confirmSchedule")) {
            requestValue = GetFinalRefreshTokenAfterRequestValue.getFinalRequestValue(this, requestValue, type, confirmMineUrl, confirmMineScheduleRequest, this, "confirmSchedule", LogManagerEnum.SCHEDULECONFIRM.getFunctionCode());
            if (!TextUtils.isEmpty(requestValue)) {
                netWorkManager.logFunactionFinsh(ScheduleDetailActivity.this, ScheduleDetailActivity.this, "confirmScheduleFinish", LogManagerEnum.SCHEDULECONFIRM.getFunctionCode(), requestValue, INetWorkManager.State.SUCCESS);
                SchusersCertainResponseRoot schusersCertainResponseRoot = JSONObject.parseObject(requestValue, SchusersCertainResponseRoot.class);
                if (null != schusersCertainResponseRoot)
                    Toast.makeText(ScheduleDetailActivity.this, schusersCertainResponseRoot.getMessage(), Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    public void fail(String exceptionMessage, int type, String requestName) {
        if (requestName.equals(HTTPEVENTAPI)) {
            initData();
        } else {
            dismissDialog();
            netWorkManager.logFunactionFinsh(ScheduleDetailActivity.this, ScheduleDetailActivity.this, "getScheduleDataFinish", LogManagerEnum.SCHEDULEDETAIL.getFunctionCode(), exceptionMessage, INetWorkManager.State.FAIL);
        }

    }

    @Override
    public void notNetwork() {
        dismissDialog();
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
        dismissDialog();
    }

    public void getDataFromServer() {
        netWorkManager.logFunactionStart(this, ScheduleDetailActivity.this, "getDetailStart", LogManagerEnum.SCHEDULEDETAIL.getFunctionCode());
//        String url = "http://htrf.dscloud.me:8083/data-crab/schschedule/getDetail";
        detailUrl = ServerUrlConstant.SERVER_EMPAPI_URL() + ServerUrlConstant.GET_SCHEDULE_DETAIL;
        ScheduleDetailRequest scheduleDetailRequest = new ScheduleDetailRequest();
        scheduleDetailRequest.appId = Long.parseLong(app_id);
        scheduleDetailRequest.corpId = BookInit.getInstance().getCorp_id();
        scheduleDetailRequest.groupCorpId = OAConText.getInstance(this).group_corp_id;
        scheduleDetailRequest.schId = Long.parseLong(schId);
        scheduleDetailRequest.userId = OAConText.getInstance(this).UserID;
        AnsynHttpRequest.requestByPostWithToken(this, scheduleDetailRequest, detailUrl, CHTTP.POSTWITHTOKEN, this, "getDetail", LogManagerEnum.GGENERAL.getFunctionCode());
    }

    public void getDataFromServerThrowShare() {
        netWorkManager.logFunactionStart(this, ScheduleDetailActivity.this, "getDetailStart", LogManagerEnum.SCHEDULEDETAIL.getFunctionCode());
//        String url = "http://htrf.dscloud.me:8083/data-crab/schschedule/getDetail";
        detailUrl = ServerUrlConstant.SERVER_EMPAPI_URL() + ServerUrlConstant.GET_SCHEDULE_DETAIL;
        ScheduleDetailRequest scheduleDetailRequest = new ScheduleDetailRequest();
        scheduleDetailRequest.appId = Long.parseLong(app_id);
        scheduleDetailRequest.corpId = corpIdShare;
        scheduleDetailRequest.groupCorpId = groupCorpIdShare;
        scheduleDetailRequest.schId = Long.parseLong(schId);
        scheduleDetailRequest.userId = userIDShare;
        AnsynHttpRequest.requestByPostWithToken(this, scheduleDetailRequest, detailUrl, CHTTP.POSTWITHTOKEN, this, "getDetail", LogManagerEnum.GGENERAL.getFunctionCode());
    }

    private void deleteFromServer() {
        showDialog();
        netWorkManager.logFunactionStart(this, ScheduleDetailActivity.this, "deleteScheduleStart", LogManagerEnum.SCHEDULEDELETE.getFunctionCode());
        try {
//                    String deleteUrl = "http://htrf.dscloud.me:8083/data-crab/schschedule/deleteschedule";
            deleteUrl = ServerUrlConstant.SERVER_EMPAPI_URL() + ServerUrlConstant.DELETE_SCHEDULE;
            mScheduleDeleteRequestBean = new ScheduleDeleteRequestBean();
            mScheduleDeleteRequestBean.appId = Long.parseLong(app_id);
            mScheduleDeleteRequestBean.corpId = BookInit.getInstance().getCorp_id();
            mScheduleDeleteRequestBean.groupCorpId = OAConText.getInstance(ScheduleDetailActivity.this).group_corp_id;
            mScheduleDeleteRequestBean.schId = Long.parseLong(schId);
            mScheduleDeleteRequestBean.userId = OAConText.getInstance(ScheduleDetailActivity.this).UserID;
            AnsynHttpRequest.requestByPostWithToken(ScheduleDetailActivity.this, mScheduleDeleteRequestBean, deleteUrl, CHTTP.POSTWITHTOKEN, ScheduleDetailActivity.this, "deleteSchedule", LogManagerEnum.SCHEDULEDELETE.getFunctionCode());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private void updateFromServer() {
        showDialog();
        netWorkManager.logFunactionStart(this, ScheduleDetailActivity.this, "updateScheduleStart", LogManagerEnum.SCHEDULEUPDATE.getFunctionCode());
        String startTime = "";
        String endtime = "";
//        List<EditField> editFileds = mScheduleFormFragment.getEditFileds();
        List<EditField> editFileds = mDocResultInfo.getResult().getEditFields();
//                String updateUrl = "http://192.168.88.228:8080/data-crab/schschedule/update";
        updateUrl = ServerUrlConstant.SERVER_EMPAPI_URL() + ServerUrlConstant.UPDATE_SCHEDULE;
        scheduleUpdateBean = new ScheduleUpdateBean();
        if (editFileds != null
                && editFileds.size() > 0) {

            Field[] fileds = scheduleUpdateBean.getClass().getFields();
            for (Field field : fileds) {
                field.setAccessible(true);
                String name = field.getName();
                String type = field.getType().toString();
                String value = "";
                for (int j = 0; j < editFileds.size(); j++) {
//                            for(int k = 0; k < mDocResultInfo.getResult().getEditFields().size(); k++){
                    if (editFileds.get(j).getKey().equals("schBeginTime")) {
                        startTime = editFileds.get(j).getValue().split("\\.")[0] + ":00";
                    } else if (editFileds.get(j).getKey().equals("schEndTime")) {
                        endtime = editFileds.get(j).getValue().split("\\.")[0] + ":00";
                    }
//                            }

                    if (editFileds.get(j).getKey().equals(name)) {
                        value = editFileds.get(j).getValue();
                        break;
                    }
                }

                try {
                    if (type.contains("java.util.ArrayList")) {
//                                Long[] lo = (Long[]) Array.newInstance(Long.class);
                        Type t = null;
                        try {
                            t = ScheduleUpdateBean.class.getDeclaredField(name).getGenericType();
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }


                        Type tempType = null;
                        if (ParameterizedType.class.isAssignableFrom(t.getClass())) {
                            for (Type t1 : ((ParameterizedType) t).getActualTypeArguments()) {
                                tempType = t1;
                            }
                        }
                        List list = (List) field.get(scheduleUpdateBean);
                        if (tempType != null) {
                            String listType = tempType.toString();
                            if (listType.contains("java.lang.Long")) {
                                if (!TextUtils.isEmpty(value)) {
                                    String[] values = value.split(";");
                                    for (int i = 0; i < values.length; i++) {
                                        if (!TextUtils.isEmpty(values[i])) {
                                            list.add(Long.parseLong(values[i]));
                                        }

                                    }
                                }

                            }
                        }

                    } else {
                        if (type.contains("String")) {
                            field.setAccessible(true);
                            field.set(scheduleUpdateBean, value);
                        } else if (type.contains("Long")) {
                            if (!TextUtils.isEmpty(value)) {
                                field.setAccessible(true);
                                field.set(scheduleUpdateBean, Long.parseLong(value));
                            }

                        } else if (type.contains("int")) {
                            if (!TextUtils.isEmpty(value)) {
                                field.setAccessible(true);
                                field.set(scheduleUpdateBean, Integer.parseInt(value));
                            }

                        }

                    }

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
        }
        if (!ScheduleUtils.isInteger(scheduleUpdateBean.classfy)) {
            String classfy = getIdThrowValue("classfy", scheduleUpdateBean.classfy);
            scheduleUpdateBean.classfy = classfy;
        }
        if (!ScheduleUtils.isInteger(scheduleUpdateBean.firstRemind)) {
            String firstRemind = getIdThrowValue("firstRemind", scheduleUpdateBean.firstRemind);
            scheduleUpdateBean.firstRemind = firstRemind;
        }
        if (!ScheduleUtils.isInteger(scheduleUpdateBean.priority)) {
            String priority = getIdThrowValue("priority", scheduleUpdateBean.priority);
            scheduleUpdateBean.priority = priority;
        }
        if (!ScheduleUtils.isInteger(scheduleUpdateBean.repeatFlag)) {
            String repeatFlag = getIdThrowValue("repeatFlag", scheduleUpdateBean.repeatFlag);
            scheduleUpdateBean.repeatFlag = repeatFlag;
        }
        if (TextUtils.isEmpty(scheduleUpdateBean.schTitle)) {
            tvErrorWarning.setText("日程主题未填写!");
            tvErrorWarning.setVisibility(View.VISIBLE);
            dismissDialog();
            return;
        }
        Date startDate = ScheduleUtils.transferDateTime(startTime);
        Date endDate = ScheduleUtils.transferDateTime(endtime);
        long between = (endDate.getTime() - startDate.getTime()) / 1000;//除以1000是为了转换成秒
        long day1 = between / (24 * 3600);
        boolean isOk = ScheduleUtils.compareTowDate(scheduleUpdateBean.schBeginTime + ":00", scheduleUpdateBean.schEndTime + ":00");
        if (!isOk) {
            tvErrorWarning.setText("结束时间必须大于开始时间至少1小时！");
            tvErrorWarning.setVisibility(View.VISIBLE);
            dismissDialog();
            return;
        } else {
            tvErrorWarning.setVisibility(View.GONE);
        }
        if (day1 > 1)
            scheduleUpdateBean.spanDayFlag = "1";
        else
            scheduleUpdateBean.spanDayFlag = "0";
        ScheduleCpId scheduleCpId = new ScheduleCpId();
        if (null == SelectPoisition.getInstances().getmScheduleCpId()) {
            if (null != SelectPoisition7001.title && null != SelectPoisition7001.title.getText())
                scheduleCpId.cpTitle = SelectPoisition7001.title.getText().toString();
        } else {
            scheduleCpId = SelectPoisition.getInstances().getmScheduleCpId();
        }
        scheduleUpdateBean.userId = OAConText.getInstance(ScheduleDetailActivity.this).UserID;
        scheduleUpdateBean.schId = schId;
        scheduleUpdateBean.groupCorpId = OAConText.getInstance(ScheduleDetailActivity.this).group_corp_id;
        scheduleUpdateBean.corpId = BookInit.getInstance().getCorp_id();
        scheduleUpdateBean.appId = Long.parseLong(app_id);
        scheduleUpdateBean.schPlace = scheduleCpId;
        scheduleUpdateBean.repeatFlag = "1";
        scheduleUpdateBean.statusFlag = 1;
        scheduleUpdateBean.schStatus = 1;
        AnsynHttpRequest.requestByPostWithToken(ScheduleDetailActivity.this, scheduleUpdateBean, updateUrl, CHTTP.POSTWITHTOKEN, ScheduleDetailActivity.this, "update", LogManagerEnum.SCHEDULEUPDATE.getFunctionCode());
    }

    private void createFromServer() {
        showDialog();
//        netWorkManager.logFunactionStart(this, ScheduleDetailActivity.this, "createSchedule", LogManagerEnum.SCHEDULECREATE.getFunctionCode());
        String startTime = "";
        String endtime = "";
        createUrl = ServerUrlConstant.SERVER_EMPAPI_URL() + ServerUrlConstant.CREATE_SCHEDULE;
        scheduleCreateRequest = new ScheduleCreateRequest();
        if (mDocResultInfo.getResult().getEditFields() != null
                && mDocResultInfo.getResult().getEditFields().size() > 0) {

            Field[] fileds = scheduleCreateRequest.getClass().getFields();
            for (Field field : fileds) {
                field.setAccessible(true);
                String name = field.getName();
                String type = field.getType().toString();
                String value = "";
                for (int j = 0; j < mDocResultInfo.getResult().getEditFields()
                        .size(); j++) {
                    if (mDocResultInfo.getResult().getEditFields().get(j).getKey().equals("schBeginTime")) {
                        if (!TextUtils.isEmpty(mDocResultInfo.getResult().getEditFields().get(j).getValue())) {
                            startTime = mDocResultInfo.getResult().getEditFields().get(j).getValue().split("\\.")[0];
                        }
                    } else if (mDocResultInfo.getResult().getEditFields().get(j).getKey().equals("schEndTime")) {
                        if (!TextUtils.isEmpty(mDocResultInfo.getResult().getEditFields().get(j).getValue())) {
                            endtime = mDocResultInfo.getResult().getEditFields().get(j).getValue().split("\\.")[0];
                        }
                    } else if (mDocResultInfo.getResult().getEditFields().get(j).getKey().equals("firstRemind")) {
                        Log.e("value", mDocResultInfo.getResult().getEditFields().get(j).getValue());
                    }
                    if (mDocResultInfo.getResult().getEditFields().get(j).getKey().equals(name)) {
                        value = mDocResultInfo.getResult().getEditFields().get(j).getValue();
                        break;
                    }
                }

                try {
                    if (type.contains("java.util.ArrayList")) {
                        Type t = null;
                        try {
                            t = ScheduleCreateRequest.class.getDeclaredField(name).getGenericType();
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        Type tempType = null;
                        if (ParameterizedType.class.isAssignableFrom(t.getClass())) {
                            for (Type t1 : ((ParameterizedType) t).getActualTypeArguments()) {
                                tempType = t1;
                            }
                        }
                        List list = (List) field.get(scheduleCreateRequest);
                        if (tempType != null) {
                            String listType = tempType.toString();
                            if (listType.contains("java.lang.Long")) {
                                if (!TextUtils.isEmpty(value)) {
                                    String[] values = value.split(";");
                                    for (int i = 0; i < values.length; i++) {
                                        if (!TextUtils.isEmpty(values[i])) {
                                            list.add(Long.parseLong(values[i]));
                                        }

                                    }
                                }

                            }
                        }

                    } else {
                        if (type.contains("String")) {
                            field.setAccessible(true);
                            field.set(scheduleCreateRequest, value);
                        } else if (type.contains("Long")) {
                            if (!TextUtils.isEmpty(value)) {
                                field.setAccessible(true);
                                field.set(scheduleCreateRequest, Long.parseLong(value));
                            }

                        } else if (type.contains("int")) {
                            if (!TextUtils.isEmpty(value)) {
                                field.setAccessible(true);
                                field.set(scheduleCreateRequest, Integer.parseInt(value));
                            }

                        }

                    }

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
        }
        if (TextUtils.isEmpty(scheduleCreateRequest.schTitle)) {
            tvErrorWarning.setText("日程主题未填写!");
            tvErrorWarning.setVisibility(View.VISIBLE);
            dismissDialog();
            return;
        }
        if (TextUtils.isEmpty(startTime)) {
            tvErrorWarning.setText("开始时间没有填写！");
            tvErrorWarning.setVisibility(View.VISIBLE);
            dismissDialog();
            return;
        }
        if (TextUtils.isEmpty(endtime)) {
            tvErrorWarning.setText("结束时间没有填写！");
            tvErrorWarning.setVisibility(View.VISIBLE);
            dismissDialog();
            return;
        }
        Date startDate = ScheduleUtils.transferDateTime(startTime + ":00");
        Date endDate = ScheduleUtils.transferDateTime(endtime + ":00");
        long between = (endDate.getTime() - startDate.getTime()) / 1000;//除以1000是为了转换成秒
        long day1 = between / (24 * 3600);
        List<ChangedFieldsInfo> dictory = ScheduleDetailActivity.this.dictory;
        List<EditField> editFileds = mScheduleFormFragment.getEditFileds();
        boolean isOk = ScheduleUtils.compareTowDate(scheduleCreateRequest.schBeginTime + ":00", scheduleCreateRequest.schEndTime + ":00");
        if (!isOk) {
            tvErrorWarning.setText("结束时间必须大于开始时间1小时！");
            tvErrorWarning.setVisibility(View.VISIBLE);
            dismissDialog();
            return;
        } else {
            tvErrorWarning.setVisibility(View.GONE);
        }
        if (day1 > 1)
            scheduleCreateRequest.spanDayFlag = "1";
        else
            scheduleCreateRequest.spanDayFlag = "0";
        scheduleCreateRequest.userId = OAConText.getInstance(ScheduleDetailActivity.this).UserID;
        scheduleCreateRequest.groupCorpId = OAConText.getInstance(ScheduleDetailActivity.this).group_corp_id;
        scheduleCreateRequest.corpId = BookInit.getInstance().getCorp_id();
        scheduleCreateRequest.appId = Long.parseLong(app_id);
        if (TextUtils.isEmpty(scheduleCreateRequest.classfy)) {
            for (int i = 0; i < dictory.size(); i++) {
                if (null != dictory.get(i).getFieldKey()) {
                    if (dictory.get(i).getFieldKey().equals("classfy")) {
                        scheduleCreateRequest.classfy = dictory.get(i).getDefaultDicIndex() + "";
                    }
                }

            }
        }
        if (TextUtils.isEmpty(scheduleCreateRequest.priority)) {
            for (int i = 0; i < dictory.size(); i++) {
                if (null != (dictory.get(i).getFieldKey())) {
                    if (dictory.get(i).getFieldKey().equals("priority")) {
                        scheduleCreateRequest.priority = dictory.get(i).getDefaultDicIndex() + "";
                    }
                }

            }
        }
        if (TextUtils.isEmpty(scheduleCreateRequest.repeatFlag)) {
            for (int i = 0; i < dictory.size(); i++) {
                if (null != dictory.get(i).getFieldKey()) {
                    if (dictory.get(i).getFieldKey().equals("repeatFlag")) {
                        scheduleCreateRequest.repeatFlag = dictory.get(i).getDefaultDicIndex() + "";
                    }
                }

            }
        }
        if (TextUtils.isEmpty(scheduleCreateRequest.firstRemind)) {
            for (int i = 0; i < dictory.size(); i++) {
                if (null != dictory.get(i).getFieldKey()) {
                    if (dictory.get(i).getFieldKey().equals("firstRemind")) {
                        scheduleCreateRequest.firstRemind = dictory.get(i).getDefaultDicIndex() + "";
                    }
                }

            }
        }
        ScheduleCpId scheduleCpId = new ScheduleCpId();
        if (null == SelectPoisition.getInstances().getmScheduleCpId()) {
            if (null != SelectPoisition7001.title && !TextUtils.isEmpty(SelectPoisition7001.title.getText()))
                scheduleCpId.cpTitle = SelectPoisition7001.title.getText().toString();
        } else {
            scheduleCpId = SelectPoisition.getInstances().getmScheduleCpId();
        }
        scheduleCreateRequest.schPlace = scheduleCpId;
        scheduleCreateRequest.repeatFlag = "1";
        AnsynHttpRequest.requestByPostWithToken(ScheduleDetailActivity.this, scheduleCreateRequest, createUrl, CHTTP.POSTWITHTOKEN, ScheduleDetailActivity.this, "createSchedule", LogManagerEnum.SCHEDULECREATE.getFunctionCode());
    }

    public void getEventApiData() {
        EventApiParameters mEventApiParameters = new EventApiParameters();
        mEventApiParameters.appId = app_id;
        mEventApiParameters.appVersionId = app_version_id;
        mEventApiParameters.userId = OAConText.getInstance(this).UserID;
        mEventApiParameters.flowId = "schschedule";
//        mEventApiParameters.formKey = tab.formKey;
        mEventApiParameters.formKey = mDocResultInfo.getResult().TabItems.get(0).formKey;
//        mEventApiParameters.eventType = tab.tabEvent.eventType + "";
        mEventApiParameters.eventType = mDocResultInfo.getResult().TabItems.get(0).tabEvent.eventType + "";
        AnsynHttpRequest.requestByPost(this, mEventApiParameters, event_api_url, CHTTP.POST_LOG, this, HTTPEVENTAPI, LogManagerEnum.EVENT_API.functionCode);
    }

    public String getIdThrowValue(String fieldKey, String name) {
        ChangedFieldsInfo changedFieldsInfo = dictoryHashMap.get(fieldKey);
        if (null != changedFieldsInfo) {
            for (int i = 0; i < changedFieldsInfo.getDics().size(); i++) {
                if (name.equals(changedFieldsInfo.getDics().get(i).name)) {
                    return changedFieldsInfo.getDics().get(i).value;
                }
            }
        }
        return "";
    }

    public class MenuOnClickListener implements OnClickListener {

        private String startTime;
        private String endtime;

        @Override
        public void onClick(View v) {
            String menuViewTag = v.getTag().toString();
            // 关按钮
            mFunctionPopupWindow.dismiss();
            menuMultipleActions.setImageDrawable(getResources().getDrawable(R.drawable.btn_operation_homelabel_off));
            // TODO Auto-generated method stub
//            showLoadingView();
            // 1,必填字段判读
            // 必填字段判断
//            EditFieldList mustFieldList = EditFieldList.getInstance();
//            for (int i = 0; i < mustFieldList.getList().size(); i++) {
//                if (mustFieldList.getList().get(i).getValue() == null
//                        || mustFieldList.getList().get(i).getValue().equals("")) {
//                    Toast.makeText(
//                            ScheduleDetailActivity.this,
//                            "必填字段 "
//                                    + mustFieldList.getList().get(i)
//                                    .getNameDesc() + "尚未填写！",
//                            Toast.LENGTH_SHORT).show();
//                    hideLoadingView();
//                    return;
//                }
//            }
            if (null != menuViewTag && menuViewTag.equals("create")) {
//                String createUrl = "http://htrf.dscloud.me:8083/data-crab/schschedule/save";
                createFromServer();
            } else if (null != menuViewTag && menuViewTag.equals("update")) {
                updateFromServer();
            } else if (null != menuViewTag && menuViewTag.equals("share")) {
                ShareListener();
            } else if (null != menuViewTag && menuViewTag.equals("delete")) {
                DeleteConfirm();

            } else if (null != menuViewTag && menuViewTag.equals("edit")) {
                if (mustInputList != null) {
                    for (int i = 0; i < mustInputList.size(); i++) {
                        String mustInputKey = mustInputList.get(i);
                        for (int j = 0; j < mDocResultInfo.getResult().TabItems.get(0).regions.length; j++) {
                            for (int k = 0; k < mDocResultInfo.getResult().TabItems.get(0).regions[j].getFieldItems().length; k++) {
                                if (!docInfoDes.regions[j].isSplitRegion()) {
                                    mDocResultInfo.getResult().TabItems.get(0).regions[j].getFieldItems()[k].setMustInput(true);
                                }
                            }
                        }
                    }
                }
                for (int i = 0; i < mDocResultInfo.getResult().TabItems.get(0).regions.length; i++) {
                    for (int j = 0; j < mDocResultInfo.getResult().TabItems.get(0).regions[i].getFieldItems().length; j++) {
                        if (!docInfoDes.regions[i].isSplitRegion()) {
                            mDocResultInfo.getResult().TabItems.get(0).regions[i].getFieldItems()[j].setMode("1");
                        }
                    }
                }
                mScheduleFormFragment.refreshData(mDocResultInfo);
                mDataList.clear();
                ActionInfo actionInfo1 = new ActionInfo();
                actionInfo1.setActionName("更新");
                actionInfo1.setActionID("update");
                mDataList.add(actionInfo1);
                isEdit = true;
//                    mScheduleFormFragment.switchTabInfo(mDocResultInfo.getResult().getTabItems());
            } else if (null != menuViewTag && menuViewTag.equalsIgnoreCase("confirm")) {
                confirmMineSchedule();

            }
            if (null != mFunctionPopupWindow && mFunctionPopupWindow.isShowing()) {
                mFunctionPopupWindow.dismiss();
            }

        }

    }

    /*
    * 确认自己的日程
    * */
    private void confirmMineSchedule() {
        //TODO
        showDialog();
        netWorkManager.logFunactionStart(this, ScheduleDetailActivity.this, "confirmScheduleStart", LogManagerEnum.SCHEDULEDELETE.getFunctionCode());
        try {
//                    String deleteUrl = "http://htrf.dscloud.me:8083/data-crab/schschedule/deleteschedule";
            confirmMineUrl = ServerUrlConstant.SERVER_EMPAPI_URL() + ServerUrlConstant.CONFIRM_MINE_SCHEDULE;
            confirmMineScheduleRequest = new SchusersCertainRequest();
            confirmMineScheduleRequest.appId = Long.parseLong(app_id);
            confirmMineScheduleRequest.schId = schId;
            confirmMineScheduleRequest.userId = OAConText.getInstance(ScheduleDetailActivity.this).UserID;
            ;
            AnsynHttpRequest.requestByPostWithToken(ScheduleDetailActivity.this, confirmMineScheduleRequest, confirmMineUrl, CHTTP.POSTWITHTOKEN, ScheduleDetailActivity.this, "confirmSchedule", LogManagerEnum.SCHEDULEDELETE.getFunctionCode());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private void DeleteConfirm() {
        final com.htmitech.pop.AlertDialog mDialog = new com.htmitech.pop.AlertDialog(this);
        mDialog.builder().setTitle("您确定要取消该日程")
                .setNegativeButton("取消", new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.setDismiss();
                    }
                })
                .setPositiveButton("确定", new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.setDismiss();
                        deleteFromServer();
                    }
                }).setType().show();
    }

    //分享
    public int curItem = 0;
    public String apiUrl = null;

    private void ShareListener() {
        // 弹选择窗
        //设置分享参数
        shareLink = new ShareLink();
        shareLink.setTitle("分享日程");
//        shareLink.setDesc(docTitle);
//        shareLink.setThumbnail(iconId);
        shareLink.setUrl(ServerUrlConstant.SERVER_EMPAPI_URL() + ServerUrlConstant.GET_SCHEDULE_DETAIL);

        AlertDialog.Builder builder = new AlertDialog.Builder(
                ScheduleDetailActivity.this);
        builder.setTitle("请选择分享位置");
        final String[] pos = {"分享给同事", "分享到工作组"};
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            private String jsonString;

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (curItem == 0) {
                    apiUrl = "RC" + OAConText.getInstance(ScheduleDetailActivity.this).UserID + "|" + app_id + "|" + schId + "|" +
                            BookInit.getInstance().getCorp_id() + "|" + OAConText.getInstance(ScheduleDetailActivity.this).group_corp_id;
                    shareLink.setAppUrl(apiUrl);
                    MXAPI.getInstance(ScheduleDetailActivity.this).shareToChat(ScheduleDetailActivity.this, shareLink);
                } else {
                    apiUrl = "RC" + OAConText.getInstance(ScheduleDetailActivity.this).UserID + "|" + app_id + "|" + schId + "|" +
                            BookInit.getInstance().getCorp_id() + "|" + OAConText.getInstance(ScheduleDetailActivity.this).group_corp_id;
                    shareLink.setAppUrl(apiUrl);
                    shareLink.setTitle("分享日程");
                    MXAPI.getInstance(ScheduleDetailActivity.this).shareToCircle(ScheduleDetailActivity.this, shareLink);
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


    private void hideLoadingView() {
        mLoadingView.setVisibility(View.GONE);
        mLoadingView.stopLoading();
    }

    public void onFail(final int taskType, int statusCode, String errorMsg,
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
