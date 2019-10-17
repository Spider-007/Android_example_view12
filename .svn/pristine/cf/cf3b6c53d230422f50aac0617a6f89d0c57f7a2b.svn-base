package fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.ScaleAnimation;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schedulemanagerlibrary.R;
import com.htmitech.api.BookInit;
import com.htmitech.dao.SYS_UserDAO;
import com.htmitech.datepicker.PopChooseTimeHelper;
import com.htmitech.domain.SYS_Department;
import com.htmitech.domain.SYS_User;
import com.htmitech.listener.CallCheckAllUserListener;
import com.htmitech.myEnum.ChooseSystemBook;
import com.htmitech.myEnum.ChooseTreeHierarchy;
import com.htmitech.myEnum.ChooseWay;
import com.htmitech.myEnum.ChooseWayEnum;
import com.htmitech.myEnum.LogManagerEnum;
import com.htmitech.photoselector.ui.PhotoPreviewActivity;
import com.htmitech.photoselector.ui.PhotoSelectorActivity;
import com.htmitech.pop.SelectPicPopupWindow;
import com.htmitech.thread.AnsynHttpRequest;
import com.htmitech.thread.CHTTP;
import com.htmitech.unit.ActivityUnit;
import com.htmitech.utils.OAConText;
import com.mx.google.gson.Gson;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Interface.INetWorkManager;
import Interface.SelectPoisition;
import activity.ScheduleDetailActivity;
import activity.SchedulePoiSearchActivity;
import dao.AppliationCenterDao;
import dao.FormExtensionFilesDao;
import entity.OtherValues;
import entity.enentapibean.ChangedFieldsInfo;
import entity.enentapibean.EventApiParameters;
import entity.enentapibean.EventApiResultInfo;
import htmitech.com.componentlibrary.ComboBox;
import htmitech.com.componentlibrary.api.ComponentInit;
import htmitech.com.componentlibrary.base.MyBaseFragment;
import htmitech.com.componentlibrary.entity.AuthorInfo;
import htmitech.com.componentlibrary.entity.DocResultInfo;
import htmitech.com.componentlibrary.entity.EditField;
import htmitech.com.componentlibrary.entity.EditFieldList;
import htmitech.com.componentlibrary.entity.FieldItem;
import htmitech.com.componentlibrary.entity.Fielditems;
import htmitech.com.componentlibrary.entity.FormExtensionFiles;
import htmitech.com.componentlibrary.entity.InfoRegion;
import htmitech.com.componentlibrary.entity.InfoTab;
import htmitech.com.componentlibrary.entity.PhotoModel;
import htmitech.com.componentlibrary.listener.CallBackImageSelectImpJava;
import htmitech.com.componentlibrary.listener.CallbackFormListener;
import htmitech.com.componentlibrary.listener.HTCallbackEMIUserListener;
import htmitech.com.componentlibrary.listener.ICallCheckUserListener;
import htmitech.com.componentlibrary.listener.IFindKeyByValueListener;
import htmitech.com.componentlibrary.listener.ISelectPoisitionListener;
import htmitech.com.componentlibrary.listener.ObserverCallBackType;
import htmitech.com.componentlibrary.listener.ScrollViewListener;
import htmitech.com.componentlibrary.unit.HTActivityUnit;
import htmitech.com.componentlibrary.unit.PreferenceUtils;
import htmitech.com.componentlibrary.unit.ServerUrlConstant;
import htmitech.formConfig.SelectPhoto6001_6002_6101_6102;
import htmitech.listener.HTSaveFormExtensionFiles;
import htmitech.listener.ICellOnclick6102;
import htmitech.listener.YiJianOnclickLisener;
import htmitech.view.FormContainerView;
import mobilereport.com.chatkit.domain.TextStyle;
import mobilereport.com.chatkit.util.WaterMarkTextUtil;
import proxy.LogManagerProxy;
import proxy.NetWorkManager;
import schedulebean.ScheduleCpId;
import utils.DeviceUtils;
import utils.FileSizeUtil;
import widget.ObservableScrollView;

import static constant.ActivityResultConstant.OPTION_REQUEST_CODE_0;


/***
 * 工作流表单详情页面
 *
 * @author joe
 * @date 2017-06-28 16:47:13
 */
@SuppressLint("ValidFragment")
public class ScheduleFormFragment extends MyBaseFragment implements ScrollViewListener, CallBackImageSelectImpJava, CallbackFormListener, ObserverCallBackType, ISelectPoisitionListener {
    private static final String TAG = "ScheduleFormFragment";
    private DialogFragment mNewFragment;

    private int pullStyle = 0;
    private static final int PULLDOWN_TOREFRESH = 0;
    private static final int PULLUP_TOLOADMORE = 1;

    private int mPageNum = 1;
    private boolean has_more = false;

    private LayoutInflater mInflater;
    private View mEmptyView;
    private FormContainerView mLinearlayout_formdetail;
    //private DocResultInfo mDocResultInfo;

    private EditText currentEditText;
    private TextView currentEditTextView;

    private float dmvalue = 1; // 设置字体大小用

    private int screenWidth = 0, i; // 屏幕寬度

    private String VlineVisible = "false";
    private InfoRegion[] paramaters;
    private ArrayList<ComboBox> mComboBoxList = null;
    private ScaleAnimation showAnimation;
    private ScaleAnimation hiddnAnimation;
    ImageView im_down;//折叠表单后面的箭头图标
    int flag = 0;
    private List<TextView> list_tvsize;
    private List<EditText> list_etsize;
    private List<RadioButton> list_rbsize;
    private List<CheckBox> list_cbsize;
    private List<ComboBox> list_comboboxsize;
    private float heightLinear = 50.0f;
    int height = DeviceUtils.dip2px(ComponentInit.getInstance().getContext(), heightLinear);
    ComboBox comboxValue;
    String app_id;
    private PopChooseTimeHelper mPopBirthHelper = null;
    private SelectPicPopupWindow menuWindow;
    private String imageFilePath;//临时拍照路径
    private Map<String, Object> imageParme = new HashMap<String, Object>();
    private Map<String, Object> fieldMap = new HashMap<String, Object>();
    private Map<String, Object> linerLayoutMap = new HashMap<String, Object>();
    private int com_workflow_mobileconfig_include_options;
    private String opinionText;
    public int tabType = 0;
    public int tabStyle = 0;
    //新增级联事件日志
    private INetWorkManager netWorkManager;
    private EventApiParameters mEventApiParameters;
    private EditField editCurrent;
    public static String event_api_url = ServerUrlConstant.SERVER_BASE_URL() + ServerUrlConstant.OA_STARTFLOW_EVENTAPI;
    public static String HTTPEVENTAPI = "httpeventapi";
    private InfoTab tab;
    private AppliationCenterDao mAppliationCenterDao;

    public ScheduleFormFragment() {
        super();
        // TODO Auto-generated constructor stub
    }

    @SuppressLint("ValidFragment")
    public ScheduleFormFragment(InfoTab tab, String app_id, int com_workflow_mobileconfig_include_options) {
        this.app_id = app_id;
        this.tab = tab;
        this.paramaters = tab.regions;
        this.com_workflow_mobileconfig_include_options = com_workflow_mobileconfig_include_options;
        this.tabType = tab.tabType;
        this.tabStyle = tab.tabStyle;
        netWorkManager = LogManagerProxy.getProxyInstance(NetWorkManager.class);
    }

    public List<EditField> getEditFileds() {
        return EditFileds;
    }

    /*
  * 刷新当前页
  * */
    public void switchTabInfo(List<InfoTab> infoTabs) {
        for (InfoTab mInfoTab : infoTabs) {
            for (InfoRegion mInfoRegion : mInfoTab.regions) {
                mLinearlayout_formdetail.refreshData(mInfoRegion.getFieldItems());
            }
        }
    }

    /*
  * 刷新当前页
  * */
    public void refreshData(DocResultInfo mDocResultInfo) {
        mLinearlayout_formdetail.refreshData(mDocResultInfo);
    }

    /*
  * 刷新当前页
  * */
    public void changeTabInfo(InfoTab tab) {
        this.tab = tab;
        this.paramaters = tab.regions;
        if (null != mLinearlayout_formdetail)
            mLinearlayout_formdetail.removeAllViews();
        initValues();
    }

    /* 表单项编辑意见操作用到的变量 —— begin */
    private List<EditField> EditFileds = new ArrayList<EditField>(); // 缓存已经编辑的表单字段，回发用。
    // int index;
    // EditText et = null;
    // int[] SPINNER_1 = {R.string.menuItem_mobileReceiver_opinionAboutAgree,
    // R.string.menuItem_mobileReceiver_opinionAgree,R.string.menuItem_mobileReceiver_opinionRead};
    private View mClickView = null; // 中间变量，用以缓存当前编辑的文本框

    private View mTextView = null; //意见需要显示的控件 1011
    // MySpinner opinion_spinner = null;
    private boolean isslipping = false;
    ObservableScrollView scrollView;
    /* 表单项编辑意见操作用到的变量 —— end */

    protected int getLayoutId() {
        return R.layout.fragment_form_schedule;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        list_tvsize = new ArrayList<TextView>();
        list_etsize = new ArrayList<EditText>();
        list_rbsize = new ArrayList<RadioButton>();
        list_cbsize = new ArrayList<CheckBox>();
        list_comboboxsize = new ArrayList<ComboBox>();
        ComponentInit.getInstance().setmCallBackImageSelectImpJava(this);
        ComponentInit.getInstance().setmCallbackFormListener(this);
        this.mInflater = inflater;
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     * 初始化View。
     */
    protected void initViews() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        dmvalue = dm.scaledDensity;            //缩放密度
        screenWidth = dm.widthPixels;        //宽度像素

        initValues();
    }

    private void initValues() {
        mComboBoxList = new ArrayList<ComboBox>();
        scrollView = (ObservableScrollView) this.findViewById(R.id.sc_freement);
        scrollView.setScrollViewListener(this);
        if (getActivity() != null) {
            mEmptyView = View.inflate(getActivity(), R.layout.layout_empty_view, null);   //空视图
        }
        mLinearlayout_formdetail = (FormContainerView) findViewById(R.id.linearlayout_formdetail); //整个表单的大框
        if (getActivity() instanceof ScheduleDetailActivity) {
            if (((ScheduleDetailActivity) getActivity()).isWaterSecurity == 1) {
                TextStyle textStyle = new TextStyle();
                textStyle.setColor("#EAEAEA");
                textStyle.setFontSize(24);
                WaterMarkTextUtil.setWaterMarkTextBg(scrollView, getActivity(), OAConText.getInstance(getActivity()).UserName, 1, textStyle);
                scrollView.getBackground().setAlpha((int) (0.5 * 255));
            }
        } else {
            TextStyle textStyle = new TextStyle();
            textStyle.setColor("#EAEAEA");
            textStyle.setFontSize(24);
            WaterMarkTextUtil.setWaterMarkTextBg(scrollView, getActivity(), OAConText.getInstance(getActivity()).UserName, 1, textStyle);
            scrollView.getBackground().setAlpha((int) (0.5 * 255));
        }
        mAppliationCenterDao = new AppliationCenterDao(getActivity());
        /**
         * 表达那初始化工作
         */
        ComponentInit.getInstance().setmHTCallbackEMIUserListener(new HTCallbackEMIUserListener() {
            @Override
            public boolean isEMIUser(String userId) {
                return mAppliationCenterDao.isEmiUser(userId);
            }
        });
        ComponentInit.getInstance().setUsingColorStyle(BookInit.getInstance().getmApcUserdefinePortal().getUsing_color_style());
        ComponentInit.getInstance().setEMPUserID(PreferenceUtils.getEMPUserID(getActivity()));
        ComponentInit.getInstance().setAttribute1(PreferenceUtils.getAttribute1(getActivity()));
        ComponentInit.getInstance().setEMPUserName(PreferenceUtils.getEMPUserName(getActivity()));
        ComponentInit.getInstance().setThirdDepartmentName(PreferenceUtils.getThirdDepartmentName(getActivity()));
        ComponentInit.getInstance().setOAUserName(PreferenceUtils.getOAUserName(getActivity()));
        ComponentInit.getInstance().setmISelectPoisitionListener(this);

        mLinearlayout_formdetail.setmComboBoxList(mComboBoxList);
        mLinearlayout_formdetail.setmCellOnclickLisener6001_6002_6101_6102(new CellOnclickLisener6001_6002_6101_6102());
        mLinearlayout_formdetail.setmYiJianOnclickLisener(new CYiJianOnclickLisener());
        mLinearlayout_formdetail.setmCellOnclickLisener(new CellOnclickLisener());
        mLinearlayout_formdetail.setmCellOnclickLisener2004(new CellOnclickLisener2004());
        mLinearlayout_formdetail.setmHTSaveFormExtensionFiles(new HTSaveFormExtensionFiles() {
            @Override
            public void saveFormExtension(FormExtensionFiles mFormExtensionFiles) {
                FormExtensionFilesDao mFormExtensionFilesDao = new FormExtensionFilesDao(getActivity());
                mFormExtensionFilesDao.saveExtensionFilesJava(mFormExtensionFiles);
            }
        });
        mLinearlayout_formdetail.setmIntentPhone(new ICellOnclick6102() {
            @Override
            public <T> void onClick(View v, T t) {
                ActivityUnit.switchTo(getActivity(), PhotoPreviewActivity.class, (Map) t);
            }

            @Override
            public void onClick(View v) {

            }
        });

        ComponentInit.getInstance().setmIFindKeyByValueListener(new IFindKeyByValueListener() {
            @Override
            public String findKeyByValueListener(View view,String key, String value) {
                String names = value;
                if (key.equals("accompany") || key.equals("participation")) {
                    names = "";
                    String[] split = value.split("\\|");
                    if (split != null) {
                        SYS_UserDAO sys_userDAO = new SYS_UserDAO(getActivity());
                        ArrayList<SYS_User> sys_users = new ArrayList<SYS_User>();
                        for (int i = 0; i < split.length; i++) {
                            SYS_User userIdSYS_user = null;
                            try {
                                if (!TextUtils.isEmpty(split[i])) {
                                    userIdSYS_user = sys_userDAO.findLoginNameSYS_User(split[i]);
                                    if (userIdSYS_user != null) {
                                        names += userIdSYS_user.FullName + "|";
                                    }else{
                                        names += split[i];
                                    }
                                    sys_users.add(userIdSYS_user);
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        if(null != view)
                            BookInit.getInstance().getTempPeopleAppInfo().put(view.hashCode()+"",sys_users);
                    }
                }
                return names;
            }
        });
        mLinearlayout_formdetail.setmScrollViewListener(this);
        mLinearlayout_formdetail.setList_cbsize(list_cbsize);
        mLinearlayout_formdetail.setList_comboboxsize(list_comboboxsize);
        mLinearlayout_formdetail.setList_etsize(list_etsize);
        mLinearlayout_formdetail.setList_tvsize(list_tvsize);
        mLinearlayout_formdetail.setList_rbsize(list_rbsize);
        mLinearlayout_formdetail.initData(((ScheduleDetailActivity) getActivity()).mDocResultInfo.getResult().getTabItems().get(0), ((ScheduleDetailActivity) getActivity()).mDocResultInfo, currentEditTextView, EditFileds, fieldMap, ((ScheduleDetailActivity) getActivity()).comeShare, ((ScheduleDetailActivity) getActivity()).com_workflow_mobileconfig_IM_enabled, ((ScheduleDetailActivity) getActivity()).isWaterSecurity);
        SelectPoisition.getInstances().setSelectPoisitionInterface(new SelectPoisition.SelectPoisitionInterface() {
            @Override
            public void OnLocationChange(ScheduleCpId mScheduleCpId) {
                mLinearlayout_formdetail.setLocationTitle(mScheduleCpId.cpTitle);

                SelectPoisition.getInstances().setmScheduleCpId(mScheduleCpId);
            }
        });

        ComponentInit.getInstance().setmICallCheckUserListener(new ICallCheckUserListener() {
            @Override
            public void getCheckUserListener(final View view, IChooseWay mChooseWay, Choose mChoose) {
                ChooseWayEnum mChooseWayEnum = null;
                ChooseWay mChooseWayA = null;
                String title = "";
                currentEditTextView = (TextView) view;
                switch (mChooseWay) {
                    case DEPARTMENTCHOOSE:
                        title = "部门选择";
                        mChooseWayEnum = ChooseWayEnum.DEPARTMENTCHOOSE;
                        break;

                    case PEOPLECHOOSE:
                        title = "人员选择";
                        mChooseWayEnum = ChooseWayEnum.PEOPLECHOOSE;
                        break;
                }
                switch (mChoose) {
                    case SINGLE_CHOOSE:
                        mChooseWayA = ChooseWay.SINGLE_CHOOSE;
                        break;
                    case MORE_CHOOSE:
                        mChooseWayA = ChooseWay.MORE_CHOOSE;
                        break;
                }
                BookInit.getInstance().setCheckAllUser(BookInit.getInstance().getTempPeopleAppInfo().get(view.hashCode()+""));
                BookInit.getInstance().setCallCheckUserListener(getActivity(), mChooseWayEnum, mChooseWayA, ChooseTreeHierarchy
                        .HIERARCHY, ChooseSystemBook.ADDRESSBOOK, title, true, null, new CallCheckAllUserListener() {
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
                            BookInit.getInstance().setCheckAllUser(checkAllUser);
                            BookInit.getInstance().getTempPeopleAppInfo().put(view.hashCode()+"",checkAllUser);
                            mLinearlayout_formdetail.callHandle_doAction_hasAuthor(mAuthorInfoList, currentEditTextView);
                        } else if (checkAllDepartment != null && checkAllDepartment.size() != 0) {
                            ArrayList<AuthorInfo> mAuthorInfoList = new ArrayList<AuthorInfo>();
                            for (SYS_Department mSYS_Department : checkAllDepartment) {
                                AuthorInfo mAuthorInfo = new AuthorInfo();
                                mAuthorInfo.setUserId(mSYS_Department.getDepartmentCode());
                                mAuthorInfo.setUserName(mSYS_Department.getFullName());
                                mAuthorInfoList.add(mAuthorInfo);
                            }
                            mLinearlayout_formdetail.callHandle_doAction_hasAuthor(mAuthorInfoList, currentEditTextView);
                        }
                    }
                });
            }

        });

    }


    @Override
    public void onFormClick(EditField edit) {
        FieldItem item = null;
        if (edit == null) {
            mEventApiParameters = new EventApiParameters();
            mEventApiParameters.appId = app_id;
            mEventApiParameters.appVersionId = ((ScheduleDetailActivity) getActivity()).app_version_id;
            mEventApiParameters.userId = OAConText.getInstance(getActivity()).UserID;
            mEventApiParameters.flowId = ((ScheduleDetailActivity) getActivity()).docId;
            mEventApiParameters.formKey = tab.formKey;
            mEventApiParameters.eventType = tab.tabEvent.eventType + "";
            List<OtherValues> otherValuesList = new ArrayList<OtherValues>();
            List<EditField> editFields = ((ScheduleDetailActivity) getActivity()).mDocResultInfo.getResult().getEditFields();
            if (fieldMap.size() > 0) {
                Set<String> set = fieldMap.keySet();
                for (String str : set) {
                    OtherValues otherValues = new OtherValues();
                    otherValues.fieldKey = ((FieldItem) fieldMap.get(str)).getKey();
                    otherValues.fieldValue = ((FieldItem) fieldMap.get(str)).getValue();
                    if (editFields != null && editFields.size() > 0) {
                        for (EditField editField : editFields) {
                            if (editField.getKey().equals(((FieldItem) fieldMap.get(str)).getKey())) {
                                otherValues.fieldValue = editField.getValue();
                            }
                        }
                    }
                    otherValuesList.add(otherValues);
                }
            }

            mEventApiParameters.otherValues = otherValuesList;
            netWorkManager.logFunactionStart(getActivity(), ScheduleFormFragment.this, LogManagerEnum.EVENT_API);
//            AnsynHttpRequest.requestByPost(getActivity(), mEventApiParameters, event_api_url, CHTTP.POST_LOG, ScheduleFormFragment.this, HTTPEVENTAPI, LogManagerEnum.GGENERAL.functionCode);
            return;
        } else {
            item = (FieldItem) fieldMap.get(edit.getKey());
        }
        if (item != null && item.getAjaxEvent() == null) {
            return;
        } else {
            this.editCurrent = edit;
            List<EditField> editFields = EditFileds;
            mEventApiParameters = new EventApiParameters();
            mEventApiParameters.appId = app_id;
            mEventApiParameters.appVersionId = ((ScheduleDetailActivity) getActivity()).app_version_id;
            mEventApiParameters.userId = OAConText.getInstance(getActivity()).UserID;
            mEventApiParameters.flowId = ((ScheduleDetailActivity) getActivity()).docId;
            mEventApiParameters.formKey = tab.formKey;
            mEventApiParameters.fieldKey = edit.getKey();
            mEventApiParameters.fieldValue = edit.getValue();
            mEventApiParameters.eventType = item.getAjaxEvent().eventType + "";
            List<OtherValues> otherValuesList = new ArrayList<OtherValues>();
            if (fieldMap.size() > 0) {
                Set<String> set = fieldMap.keySet();
                for (String str : set) {
                    OtherValues otherValues = new OtherValues();
                    otherValues.fieldKey = ((FieldItem) fieldMap.get(str)).getKey();
                    otherValues.fieldValue = ((FieldItem) fieldMap.get(str)).getValue();
                    if (editFields.size() > 0) {
                        for (EditField editField : editFields) {
//                            if (editField.getKey().equals(((FieldItem) fieldMap.get(str)).getKey())) {
//                                otherValues.field_value = editField.getValue();
//                            }
                        }
                    }
                    otherValuesList.add(otherValues);
                }
            }

            mEventApiParameters.otherValues = otherValuesList;
            netWorkManager.logFunactionStart(getActivity(), ScheduleFormFragment.this, LogManagerEnum.EVENT_API);
        }
    }

    @Override
    public void success(String requestValue, int type, String requestName) {
        if (requestName.equals(HTTPEVENTAPI)) {
            try {
                if (requestValue != null) {
                    Gson gson = new Gson();
                    EventApiResultInfo mEventApiResultInfo = gson.fromJson(requestValue, EventApiResultInfo.class);
                    if (mEventApiResultInfo.getCode() == 200 && mEventApiResultInfo.getResultStatus() == 0) {
                        netWorkManager.logFunactionFinsh(getActivity(), ScheduleFormFragment.this, "event_api_success", LogManagerEnum.EVENT_API.functionCode, "success", INetWorkManager.State.SUCCESS);
                        List<ChangedFieldsInfo> changedFieldsInfoList = null;
                        if (mEventApiResultInfo.getResult().getChangedFields() != null) {
                            changedFieldsInfoList = mEventApiResultInfo.getResult().getChangedFields();
                        }
                        if (changedFieldsInfoList.size() > 0) {
                            for (ChangedFieldsInfo mChangedFieldsInfo : changedFieldsInfoList) {
                                if (mChangedFieldsInfo.getFieldKey() != null) {
                                    FieldItem item = (FieldItem) fieldMap.get(mChangedFieldsInfo.getFieldKey());
                                    if (item != null) {
                                        item.setValue(mChangedFieldsInfo.getFieldValue());
                                        if (mChangedFieldsInfo.getDics() != null && mChangedFieldsInfo.getDics().size() > 0) {
                                            item.Dics = mChangedFieldsInfo.getDics();
                                        }
                                        item.setIsExt(mChangedFieldsInfo.getHiden());
                                        item.setEditable(mChangedFieldsInfo.getEditable());
                                        mLinearlayout_formdetail.swichType(Integer.parseInt(item.getInput()), item);

                                        //预防出现隐藏字段 且是不可编辑字段需要同步提交一些值，比如地理信息
                                        EditField edit_field = new EditField();
                                        edit_field.setKey(mChangedFieldsInfo.getFieldKey());
                                        edit_field.setValue(mChangedFieldsInfo.getFieldValue());
                                        edit_field.setFormKey(tab.formKey);
                                        edit_field.setSign("true");
                                        edit_field.setIsExt(mChangedFieldsInfo.getHiden() == 0);
                                        edit_field.setInput(item.getInput());
                                        boolean hasfind = false;
                                        for (int j = 0; j < EditFileds.size(); j++) {
                                            if (EditFileds.get(j).getKey().equals(mChangedFieldsInfo.getFieldKey())) {
                                                hasfind = true;
                                                EditFileds.get(j).setValue(mChangedFieldsInfo.getFieldValue());
                                                break;
                                            }
                                        }
                                        if (!hasfind)
                                            EditFileds.add(edit_field);

                                    } else {
                                        EditField edit_field = new EditField();
                                        edit_field.setKey(mChangedFieldsInfo.getFieldKey());
                                        edit_field.setValue(mChangedFieldsInfo.getFieldValue());
                                        edit_field.setFormKey(tab.formKey);
                                        boolean hasfind = false;
                                        for (int j = 0; j < EditFileds.size(); j++) {
                                            if (EditFileds.get(j).getKey().equals(mChangedFieldsInfo.getFieldKey())) {
                                                hasfind = true;
                                                EditFileds.get(j).setValue(mChangedFieldsInfo.getFieldValue());
                                                break;
                                            }
                                        }
                                        if (!hasfind)
                                            EditFileds.add(edit_field);
                                    }
                                }
                            }

                        }
                    } else if (mEventApiResultInfo.getCode() == 200 && mEventApiResultInfo.getResultStatus() == 1) {
                        netWorkManager.logFunactionFinsh(getActivity(), ScheduleFormFragment.this, "event_api_success", LogManagerEnum.EVENT_API.functionCode, "success", INetWorkManager.State.SUCCESS);
                        Toast.makeText(getActivity(), mEventApiResultInfo.getResult().getResultMsg(), Toast.LENGTH_SHORT).show();
                       /* FieldItem item = (FieldItem) fieldMap.get(editCurrent.getKey());
                        if (item != null) {
                            item.setValue("");
                        }
                        swichType(Integer.parseInt(item.getInput()), item);*/
                        return;
                    } else {
                        netWorkManager.logFunactionFinsh(getActivity(), ScheduleFormFragment.this, "event_api_fail", LogManagerEnum.EVENT_API.functionCode, "success", INetWorkManager.State.FAIL);
                    }

                }
            } catch (Exception e) {
                netWorkManager.logFunactionFinsh(getActivity(), ScheduleFormFragment.this, "event_api_fail", LogManagerEnum.EVENT_API.functionCode, "success", INetWorkManager.State.FAIL);
                e.printStackTrace();
            }
        } else if (requestName.equals(LogManagerEnum.EVENT_API.functionCode)) {
            AnsynHttpRequest.requestByPost(getActivity(), mEventApiParameters, event_api_url, CHTTP.POST_LOG, ScheduleFormFragment.this, HTTPEVENTAPI, LogManagerEnum.EVENT_API.functionCode);

        }

    }

    @Override
    public void fail(String exceptionMessage, int type, String requestName) {
        if (requestName.equals(HTTPEVENTAPI)) {
            Log.e(TAG, "success: " + exceptionMessage);
            netWorkManager.logFunactionFinsh(getActivity(), ScheduleFormFragment.this, "event_api_success", LogManagerEnum.EVENT_API.functionCode, "success", INetWorkManager.State.FAIL);
        } else if (requestName.equals(LogManagerEnum.EVENT_API)) {
            AnsynHttpRequest.requestByPost(getActivity(), mEventApiParameters, event_api_url, CHTTP.POST_LOG, ScheduleFormFragment.this, HTTPEVENTAPI, LogManagerEnum.EVENT_API.functionCode);

        }

    }

    @Override
    public void notNetwork() {

    }

    @Override
    public void callbackMainUI(String successMessage) {

    }
    @Override
    public void startSchedulePoiSearchContext() {
        HashMap map = new HashMap<String, String>();
        map.put("appId", app_id);
        HTActivityUnit.switchTo(getActivity(), SchedulePoiSearchActivity.class, map);
    }


    public class ChildViews {
        public LinearLayout.LayoutParams parms;
        public LinearLayout layout;
    }


    /**
     * 遍历表单默认折叠
     */
    private void goneStart() {
        for (int i = 0; i < paramaters.length; i++) {
            for (int j = 0; j < i; j++) {
                if (paramaters[i].getParentRegionID() != null) {
                    if (paramaters[i].getParentRegionID().equals(paramaters[j].getRegionID())) {
                        mLinearlayout_formdetail.getChildAt(i).setVisibility(View.GONE);
                        break;
                    }
                } else {
                    break;
                }
            }
        }
    }

    /**
     * V2.0add
     * 判断下面是否还有子节点  并且当父节点关闭 下面的子节点全部关闭
     *
     * @param parmater
     * @param n
     */
    private void isHead(InfoRegion parmater, final int n) {
        String tableID = parmater.getTableID();
        String RegionID = parmater.getRegionID();
        for (int i = n; i < paramaters.length; i++) {
            if (paramaters[i].getParentRegionID().equals(RegionID)) {
//                mLinearlayout_formdetail.getChildAt(i).startAnimation((mLinearlayout_formdetail.getChildAt(i).getVisibility() == View.GONE) ? showAnimation : hiddnAnimation);
                mLinearlayout_formdetail.getChildAt(i).setVisibility((mLinearlayout_formdetail.getChildAt(i).getVisibility() == View.GONE) ? View.VISIBLE : View.GONE);
                ImageView iv_circle = (ImageView) mLinearlayout_formdetail.getChildAt(i).getTag(R.id.tag_second);
                if (iv_circle != null) {
                    if (mLinearlayout_formdetail.getChildAt(i).getVisibility() == View.GONE) {
                        iv_circle.setImageResource(R.drawable.btn_angle_up_circle);
                    } else {
                        iv_circle.setImageResource(R.drawable.btn_angle_down_circle);
                    }
                }
                //递归调用自己
                isHead(paramaters[i], i);
            }
            //当父被关闭下面的子全被关闭
            if (mLinearlayout_formdetail.getChildAt(n).getVisibility() == View.GONE) {
                for (int m = n + 1; m < paramaters.length; m++) {
                    if (paramaters[m].getParentRegionID().equals(paramaters[n].getRegionID())) {
                        mLinearlayout_formdetail.getChildAt(m).setVisibility(View.GONE);
                    } else {
                        break;
                    }
                }
            }


        }
    }


    public static boolean isNumeric(String str) {
        for (int i = str.length(); --i >= 0; ) {
            int chr = str.charAt(i);
            if (chr < 48 || chr > 57)
                return false;
        }
        return true;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 10003) {
            if (resultCode == Activity.RESULT_OK) {
                FieldItem item_workflow = (FieldItem) imageParme.get(imageFilePath);
                Log.e(TAG, "onActivityResult: " + item_workflow.getKey());
                PhotoModel mPhotoModel = new PhotoModel();
                mPhotoModel.setOriginalPath(imageFilePath);
                mPhotoModel.setItem_workflow(item_workflow);
                List<PhotoModel> listImgTemp = new ArrayList<PhotoModel>();
                listImgTemp.add(mPhotoModel);
                if (((ScheduleDetailActivity) getActivity()).formMap.get(item_workflow.getFieldId()) != null && ((ScheduleDetailActivity) getActivity()).formMap.get(item_workflow.getFieldId()) instanceof SelectPhoto6001_6002_6101_6102) {
                    ((SelectPhoto6001_6002_6101_6102) ((ScheduleDetailActivity) getActivity()).formMap.get(item_workflow.getFieldId())).updateImg(getActivity(), listImgTemp, 0);
                }
            }
        }

        if (data == null || TextUtils.isEmpty(data.getStringExtra("option")) || mClickView == null) {
            return;
        }
        String strOption = data.getStringExtra("option");

        if (OPTION_REQUEST_CODE_0 == 0) {
            if (mClickView instanceof TextView) {
                opinionText = strOption;
                ((TextView) mClickView).setTextColor(getResources().getColor(R.color.black));
                ((TextView) mClickView).setText(strOption);
                if (getActivity() instanceof ScheduleDetailActivity) {
                    ((ScheduleDetailActivity) getActivity()).setComment(strOption);
                }
            } else {

                LinearLayout layout = (LinearLayout) mClickView;
                // 显示当前编辑的意见
                TextView tvOption = (TextView) layout.findViewById(R.id.form_fielditem_option);
                list_tvsize.add(tvOption);
                tvOption.setTextColor(Color.RED);
                if (strOption != null && strOption.length() > 0) {
                    tvOption.setVisibility(View.VISIBLE);
                    tvOption.setText(strOption);
                    // 显示时加上当前时间
                    tvOption.setText(strOption + "\n"
                            + DateFormatUtils.format(new Date(), "yyyy-M-d h:mm"));
                } else if (strOption.length() == 0) {
                    tvOption.setText("");
                    tvOption.setVisibility(View.INVISIBLE);
                }
            }
        } else if (OPTION_REQUEST_CODE_0 == 1) {


        }


        // 1,将已经编辑的字段和值放入到办理是需要回发的EditField[] 中
        ScheduleFormFragment.this.mClickView = mClickView;
        EditField edit = (EditField) ScheduleFormFragment.this.mClickView.getTag();
        edit.setValue(strOption);
        edit.tempValue = strOption;

        //在其他地方显示
        if (edit.ShowView != null) {
            if (edit.ShowView instanceof TextView) {
                list_tvsize.add((TextView) edit.ShowView);
                ((TextView) edit.ShowView).setText(strOption);
            }
        }
        if (EditFileds != null && EditFileds.size() == 0)
            EditFileds.add(edit);
        else {
            boolean hasfind = false;
            for (int j = 0; j < EditFileds.size(); j++) {
                if (EditFileds.get(j).getKey().equals(edit.getKey())) {
                    hasfind = true;
                    EditFileds.get(j).setValue(edit.getValue());
                    break;
                }
            }
            if (!hasfind)
                EditFileds.add(edit);
        }
        // 2015-8-17
        DocResultInfo mDocResultInfo = null;
        mDocResultInfo = ((ScheduleDetailActivity) getActivity()).mDocResultInfo;
        // DocResultInfo mDocResultInfo = ((ScheduleDetailActivity)
        // getActivity()).mDocResultInfo;
        mDocResultInfo.getResult().setEditFields(EditFileds);
        // ro.getEt().setTag(EditFileds);
        // 2,必填字段处理--将输入的意见放入相应必填字段中
        EditFieldList mustFieldList = EditFieldList.getInstance();

        for (int i = 0; i < mustFieldList.getList().size(); i++) {
            if (mustFieldList
                    .getList()
                    .get(i)
                    .getKey()
                    .equalsIgnoreCase(
                            ((EditField) ScheduleFormFragment.this.mClickView.getTag())
                                    .getKey())) {
                mustFieldList.getList().get(i).tempValue = strOption;
                mustFieldList.getList().get(i).setValue(strOption); //
            }
        }
    }


    public class CYiJianOnclickLisener implements YiJianOnclickLisener {// 设置表格项的监听器

        @Override
        public void onClick(View v) {

            if (getActivity() != null && !((ScheduleDetailActivity) getActivity()).comeShare.equals("1")) {
//                Intent intent = new Intent(getActivity(), OpinionInputActivity.class);
//                intent.putExtra("appId", app_id);
//                if (v instanceof TextView) {
//                    intent.putExtra("textValue", ((TextView) v).getText().toString());
//                }
//                intent.putExtra("com_workflow_mobileconfig_include_options", com_workflow_mobileconfig_include_options);
//                startActivityForResult(intent, OPTION_REQUEST_CODE_1);
            }
            // *********************测试临时代码******************************
            ScheduleFormFragment.this.mClickView = v;

        }
    }

    public class CellOnclickLisener implements YiJianOnclickLisener {// 设置表格项的监听器

        @Override
        public void onClick(View v) {

//            if (getActivity() != null && !((ScheduleDetailActivity) getActivity()).comeShare.equals("1")) {
//                Intent intent = new Intent(getActivity(), OpinionInputActivity.class);
//                intent.putExtra("appId", app_id);
//                intent.putExtra("com_workflow_mobileconfig_include_options", com_workflow_mobileconfig_include_options);
//                startActivityForResult(intent, OPTION_REQUEST_CODE_0);
//            }
//            ScheduleFormFragment.this.mClickView = v;
        }
    }

    public ArrayList<ComboBox> getComboxList() {
        return mComboBoxList;
    }

    @Override
    public void onScrollChanged() {
        for (ComboBox mComboBox : mComboBoxList) {
            mComboBox.closePopupWindow();
        }
    }

    @Override
    public void onZoomText(float zoom) {
        setViewSize(zoom);
    }

    @Override
    public void onRequfouch() {
        mLinearlayout_formdetail.setFocusable(true);
        mLinearlayout_formdetail.setFocusableInTouchMode(true);
        mLinearlayout_formdetail.requestFocus();
        onScrollChanged();
    }

    public void setViewSize(float zoom) {
        for (TextView mTextView : list_tvsize) {
            mTextView.setTextSize(zoom);
        }
        for (EditText medit : list_etsize) {
            if (medit != null) {
                medit.setTextSize(zoom);
            }
        }
        for (RadioButton mRadioButton : list_rbsize) {
            mRadioButton.setTextSize(zoom);
        }
        for (CheckBox mCheckBox : list_cbsize) {
            mCheckBox.setTextSize(zoom);
        }

        for (ComboBox mComboBox : list_comboboxsize) {
            mComboBox.setTextSize(zoom);
        }
    }


    public class CellOnclickLisener2004 implements ICellOnclick6102 {// 设置表格项的监听器
        String textValue = "";

        @Override
        public <T> void onClick(View v, T t) {
            textValue = (String) t;
            if (getActivity() != null && !((ScheduleDetailActivity) getActivity()).comeShare.equals("1")) {
//                Intent intent = new Intent(getActivity(), OpinionInputActivity.class);
//                intent.putExtra("appId", app_id);
////                intent.putExtra("is2004", true); //是否屏蔽常用意见列表
//                if (v instanceof TextView) {
//                    intent.putExtra("textValue", ((TextView) v).getText().toString());
//                }
//                intent.putExtra("com_workflow_mobileconfig_include_options", com_workflow_mobileconfig_include_options);
//                startActivityForResult(intent, OPTION_REQUEST_CODE_0);
            }

            ScheduleFormFragment.this.mClickView = v;

        }

        @Override
        public void onClick(View v) {

        }

    }

    public class CellOnclickLisener6001_6002_6101_6102 implements ICellOnclick6102 {// 设置表格项的监听器


        @Override
        public <T> void onClick(View v, T t) {
            FieldItem item = (FieldItem) t;
            if (v.getId() == R.id.bt_send) {
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("workflow_item", item);
//                params.put("photo_number", ((SelectPhoto6001_6002_6101_6102) ((ScheduleDetailActivity) getActivity()).formMap.get(item.getField_id())).listImgs.size() );
                ArrayList<PhotoModel> album_select = new ArrayList<PhotoModel>();
                for (PhotoModel model : ((SelectPhoto6001_6002_6101_6102) ((ScheduleDetailActivity) getActivity()).formMap.get(item.getFieldId())).listImgs) {
                    if (model.isChecked()) {
                        album_select.add(model);
                    }
                }
                params.put("album_select", album_select);
                params.put("photo_number", ((SelectPhoto6001_6002_6101_6102) ((ScheduleDetailActivity) getActivity()).formMap.get(item.getFieldId())).listImgs.size() - album_select.size());
                ActivityUnit.switchTo(getActivity(), PhotoSelectorActivity.class, params);
                menuWindow.dismiss();
            } else if (v.getId() == R.id.bt_call) {
                imageFilePath = FileSizeUtil.getTempPhotoFileName(OAConText.getInstance(getActivity()).UserID, BookInit.getInstance().getPortalId(), app_id);

                File temp = new File(imageFilePath);
                Uri imageFileUri = Uri.fromFile(temp);//获取文件的Uri
                Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//跳转到相机Activity
                it.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);//告诉相机拍摄完毕输出图片到指定的Uri
                imageParme.put(imageFilePath, item);
                startActivityForResult(it, 10003);
                menuWindow.dismiss();
            } else if (v.getId() == R.id.bt_save) {
                menuWindow.dismiss();
            } else {
                if (!"".equals(item.getInput().trim()) && Integer.parseInt(item.getInput().trim()) == 6001) {
                    if (((SelectPhoto6001_6002_6101_6102) ((ScheduleDetailActivity) getActivity()).formMap.get(item.getFieldId())).listImgs.size() >= 1) {
                        Toast.makeText(getActivity(), "只能添加一张图片", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    imageFilePath = FileSizeUtil.getTempPhotoFileName(OAConText.getInstance(getActivity()).UserID, BookInit.getInstance().getPortalId(), app_id);

                    File temp = new File(imageFilePath);
                    Uri imageFileUri = Uri.fromFile(temp);//获取文件的Uri
                    Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//跳转到相机Activity
                    it.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);//告诉相机拍摄完毕输出图片到指定的Uri
                    imageParme.put(imageFilePath, item);
                    startActivityForResult(it, 10003);

                } else if (!"".equals(item.getInput().trim()) && Integer.parseInt(item.getInput().trim()) == 6101) {
                    if (((SelectPhoto6001_6002_6101_6102) ((ScheduleDetailActivity) getActivity()).formMap.get(item.getFieldId())).listImgs.size() >= 9) {
                        Toast.makeText(getActivity(), "图片最多可以添加九张", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    imageFilePath = FileSizeUtil.getTempPhotoFileName(OAConText.getInstance(getActivity()).UserID, BookInit.getInstance().getPortalId(), app_id);

                    File temp = new File(imageFilePath);
                    Uri imageFileUri = Uri.fromFile(temp);//获取文件的Uri
                    Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//跳转到相机Activity
                    it.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);//告诉相机拍摄完毕输出图片到指定的Uri
                    imageParme.put(imageFilePath, item);
                    startActivityForResult(it, 10003);
                } else {
                    if (!"".equals(item.getInput().trim()) && Integer.parseInt(item.getInput().trim()) == 6002 && ((SelectPhoto6001_6002_6101_6102) ((ScheduleDetailActivity) getActivity()).formMap.get(item.getFieldId())).listImgs.size() >= 1) {
                        Toast.makeText(getActivity(), "只能添加一张图片", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (!"".equals(item.getInput().trim()) && Integer.parseInt(item.getInput().trim()) == 6102 && ((SelectPhoto6001_6002_6101_6102) ((ScheduleDetailActivity) getActivity()).formMap.get(item.getFieldId())).listImgs.size() >= 9) {
                        Toast.makeText(getActivity(), "图片最多可以添加九张", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (getActivity() != null && !((ScheduleDetailActivity) getActivity()).comeShare.equals("1")) {
                        //TODO 添加点击照片的监听处理
                        menuWindow = new SelectPicPopupWindow(getActivity(), this);
                        menuWindow.setDetalFormMessage();
                        //显示窗口
                        menuWindow.showAtLocation(v, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                    }
                }
            }


            // *********************测试临时代码******************************
            ScheduleFormFragment.this.mClickView = v;

        }

        @Override
        public void onClick(View v) {

        }

    }


    @Override
    public void checkImageSelect(ArrayList<PhotoModel> mSelectedImage, Fielditems item, FieldItem workflow_item) {
        Log.e(TAG, "checkImageSelect: " + mSelectedImage.size() + "formKey: " + item);
//        if (Integer.parseInt(workflow_item.getInput()) == 6002) {
//            if (mSelectedImage != null && mSelectedImage.size() >= 1) {
//                Toast.makeText(getActivity(),"只能添加一张图片",Toast.LENGTH_SHORT).show();
//                return;
//            }
//        }else if(Integer.parseInt(workflow_item.getInput()) == 6102){
//            if (mSelectedImage != null && mSelectedImage.size() >= 9) {
//                Toast.makeText(getActivity(),"图片最多可以添加九张",Toast.LENGTH_SHORT).show();
//                return;
//            }
//        }
        List<PhotoModel> listImgTemp = new ArrayList<PhotoModel>();
        if (mSelectedImage != null && mSelectedImage.size() > 0) {
            for (PhotoModel mPhotoModel : mSelectedImage) {
                mPhotoModel.setItem_workflow(workflow_item);
                listImgTemp.add(mPhotoModel);
            }
        }
        if (((ScheduleDetailActivity) getActivity()).formMap.get(workflow_item.getFieldId()) != null && ((ScheduleDetailActivity) getActivity()).formMap.get(workflow_item.getFieldId()) instanceof SelectPhoto6001_6002_6101_6102) {
            ((SelectPhoto6001_6002_6101_6102) ((ScheduleDetailActivity) getActivity()).formMap.get(workflow_item.getFieldId())).updateImg(getActivity(), listImgTemp, 1);
        }
    }
}
