package com.htmitech.htworkflowformpluginnew.fragment;

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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.ScaleAnimation;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.htmitech.api.BookInit;
import com.htmitech.commonx.util.DeviceUtils;
import com.htmitech.datepicker.PopChooseTimeHelper;
import com.htmitech.domain.SYS_Department;
import com.htmitech.domain.SYS_User;
import com.htmitech.emportal.HtmitechApplication;
import com.htmitech.emportal.R;
import com.htmitech.emportal.entity.OAConText;
import com.htmitech.emportal.ui.widget.ToastInfo;
import com.htmitech.htcommonformplugin.dao.FormExtensionFilesDao;
import com.htmitech.htcommonformplugin.entity.OtherValues;
import com.htmitech.htworkflowformpluginnew.activity.OpinionInputActivity;
import com.htmitech.htworkflowformpluginnew.activity.WebSearchActivity;
import com.htmitech.htworkflowformpluginnew.entity.entity.ChangedFieldsInfo;
import com.htmitech.htworkflowformpluginnew.entity.entity.EventApiParameters;
import com.htmitech.htworkflowformpluginnew.entity.entity.EventApiResultInfo;
import com.htmitech.htworkflowformpluginnew.weight.ObservableScrollView;
import com.htmitech.listener.CallCheckAllUserListener;
import com.htmitech.listener.ISearchResult;

import htmitech.com.componentlibrary.base.MyBaseFragment;
import htmitech.com.componentlibrary.listener.ObserverCallBackType;
import com.htmitech.myEnum.ChooseSystemBook;
import com.htmitech.myEnum.ChooseTreeHierarchy;
import com.htmitech.myEnum.ChooseWay;
import com.htmitech.myEnum.ChooseWayEnum;
import com.htmitech.myEnum.LogManagerEnum;
import com.htmitech.photoselector.ui.PhotoPreviewActivity;
import com.htmitech.photoselector.ui.PhotoSelectorActivity;
import com.htmitech.pop.SelectPicPopupWindow;
import com.htmitech.proxy.dao.AppliationCenterDao;
import com.htmitech.proxy.interfaces.INetWorkManager;

import com.htmitech.proxy.util.FileSizeUtil;
import com.htmitech.proxy.util.LogManagerProxy;
import com.htmitech.proxy.util.NetWorkManager;
import com.htmitech.thread.AnsynHttpRequest;
import com.htmitech.thread.CHTTP;
import com.htmitech.unit.ActivityUnit;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import htmitech.com.componentlibrary.ComboBox;
import htmitech.com.componentlibrary.api.ComponentInit;
import htmitech.com.componentlibrary.entity.AuthorInfo;
import htmitech.com.componentlibrary.entity.Dics;
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
import htmitech.com.componentlibrary.listener.ISearch413;
import htmitech.com.componentlibrary.unit.PreferenceUtils;
import htmitech.com.componentlibrary.unit.ServerUrlConstant;
import htmitech.formConfig.SelectPhoto6001_6002_6101_6102;
import htmitech.listener.HTSaveFormExtensionFiles;
import htmitech.listener.ICellOnclick6102;
import htmitech.view.FormContainerView;
import mobilereport.com.chatkit.domain.TextStyle;
import mobilereport.com.chatkit.util.WaterMarkTextUtil;

import static com.htmitech.htworkflowformpluginnew.constant.ActivityResultConstant.OPTION_REQUEST_CODE_0;
import static com.htmitech.htworkflowformpluginnew.constant.ActivityResultConstant.OPTION_REQUEST_CODE_1;


/***
 * 工作流表单详情页面
 *
 * @author joe
 * @date 2017-06-28 16:47:13
 */
@SuppressLint("ValidFragment")
public class WebViewFormDetailFragment extends MyBaseFragment implements htmitech.com.componentlibrary.listener.ScrollViewListener, CallBackImageSelectImpJava, CallbackFormListener, ObserverCallBackType {
    private static final String TAG = "FormFragment";
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
    int height = DeviceUtils.dip2px(HtmitechApplication.getApplication(), heightLinear);
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
    public int tabStyle= 0;
    //新增级联事件日志
    private INetWorkManager netWorkManager;
    private EventApiParameters mEventApiParameters;
    private EditField editCurrent;
    public static String event_api_url = ServerUrlConstant.SERVER_EMPAPI_URL() + ServerUrlConstant.OA_STARTFLOW_EVENTAPI_JAVA;
    public static String HTTPEVENTAPI = "httpeventapi";
    private InfoTab tab;
    private AppliationCenterDao mAppliationCenterDao;
    public WebViewFormDetailFragment() {
        super();
        // TODO Auto-generated constructor stub
    }

    @SuppressLint("ValidFragment")
    public WebViewFormDetailFragment(InfoTab tab, String app_id, int com_workflow_mobileconfig_include_options) {
        this.app_id = app_id;
        this.tab = tab;
        this.paramaters = tab.regions;
        this.com_workflow_mobileconfig_include_options = com_workflow_mobileconfig_include_options;
        this.tabType = tab.tabType;
        this.tabStyle = tab.tabStyle;
        netWorkManager = LogManagerProxy.getProxyInstance(NetWorkManager.class);
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
        return R.layout.fragment_form;
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

    public void refash(InfoRegion[] paramaters){
        mLinearlayout_formdetail.refreshData(paramaters);
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
        if (getActivity() instanceof WebSearchActivity) {
            if (((WebSearchActivity) getActivity()).isWaterSecurity == 1) {
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
        ComponentInit.getInstance().setOAUserName(PreferenceUtils.getOAUserName(getActivity()));
        ComponentInit.getInstance().setAppId(app_id);
        mLinearlayout_formdetail.setmComboBoxList(mComboBoxList);
        mLinearlayout_formdetail.setmCellOnclickLisener6001_6002_6101_6102(new CellOnclickLisener6001_6002_6101_6102());
        mLinearlayout_formdetail.setmYiJianOnclickLisener(new CYiJianOnclickLisener());
        mLinearlayout_formdetail.setmCellOnclickLisener(new CellOnclickLisener());
        mLinearlayout_formdetail.setmCellOnclickLisener2004(new CellOnclickLisener2004());
        mLinearlayout_formdetail.setFormMap(((WebSearchActivity) getActivity()).formMap);
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
        mLinearlayout_formdetail.setmScrollViewListener(this);
        mLinearlayout_formdetail.setList_cbsize(list_cbsize);
        mLinearlayout_formdetail.setList_comboboxsize(list_comboboxsize);
        mLinearlayout_formdetail.setList_etsize(list_etsize);
        mLinearlayout_formdetail.setList_tvsize(list_tvsize);
        mLinearlayout_formdetail.setList_rbsize(list_rbsize);
        if (tab.regions == null) {
            ToastInfo toastInfo = ToastInfo.getInstance(getActivity());
            toastInfo.setText("数据异常，请重新进入。");
            toastInfo.show(Toast.LENGTH_SHORT);
            return;
        }
        ComponentInit.getInstance().setmISearch413(new ISearch413() {
            @Override
            public void getSearch(final View view, final int intputType, final List<EditField> editFields, final DocResultInfo mDocResultInfo, int TabStyle) {
                BookInit.getInstance().setmISearchResult(new ISearchResult() {
                    @Override
                    public void searchResult(Dics dics) {
                        if(view != null){
                            String strValue = "";
                            if(view instanceof TextView){
                                TextView textView = (TextView) view;
                                textView.setMaxLines(1);
                                textView.setMaxEms(10);
                                textView.setText(dics.name);
                                textView.setSingleLine(true);
                                textView.setBackgroundColor(getResources().getColor(R.color.transparent));
                                textView.setEllipsize(TextUtils.TruncateAt.END);

                            }else if(view instanceof ComboBox){
                                ComboBox mComboBox = (ComboBox) view;
                                mComboBox.setText(dics.name);
                            }
                            switch (intputType){
                                case 413:
                                    strValue = dics.id;
                                    break;
                                case 414:
                                    strValue = dics.name;
                                    break;
                                case 415:
                                    strValue =dics.value;
                                    break;
                            }


                            EditField edit = (EditField) view.getTag();


                            edit.setValue(strValue);

                            if (editFields != null && editFields.size() == 0) {
                                editFields.add(edit);
                                // 将有可能出现的key_ID,key_VALUE 隐藏字段保存到编辑框中
                                EditField edit_Value = new EditField();
                                edit_Value.setKey(edit.getKey() );
                                edit_Value.setValue(strValue);//
                                edit_Value.setFormKey(edit.getFormKey());//
                                editFields.add(edit_Value);
                            } else {
                                boolean hasfind = false;
                                for (int j = 0; j < editFields.size(); j++) {                //
                                    if (editFields.get(j).getKey()
                                            .equals(edit.getKey())) {
                                        hasfind = true;
                                        editFields.get(j).setValue(strValue);
                                        break;
                                    }
                                }
                                if (!hasfind) {
                                    editFields.add(edit);
                                }

                                // 判读是否找到了Key_Value
                                hasfind = false;
                                for (int j = 0; j < editFields.size(); j++) {
                                    if (editFields.get(j).getKey()
                                            .equals(edit.getKey() )) {
                                        hasfind = true;
                                        editFields.get(j).setValue(strValue);
                                        break;
                                    }
                                }
                                if (!hasfind) {
                                    EditField edit_Value = new EditField();
                                    edit_Value.setKey(edit.getKey() );
                                    edit_Value.setValue(strValue);
                                    edit_Value.setFormKey(edit.getFormKey());//
                                    editFields.add(edit_Value);

                                }
                            }
                            Log.d("FormFragment", "EditFileds:" + editFields);
//            DocResultInfo mDocResultInfo = null;
//            mDocResultInfo = ((WorkFlowFormDetalActivity) context).mDocResultInfo;
                            mDocResultInfo.getResult()
                                    .setEditFields(editFields);
                            // 2,必填字段处理--将输入的意见放入相应必填字段中
                            EditFieldList mustFieldList = EditFieldList
                                    .getInstance();
                            for (int i = 0; i < mustFieldList.getList().size(); i++) {
                                if (mustFieldList
                                        .getList()
                                        .get(i)
                                        .getKey()
                                        .equalsIgnoreCase(
                                                ((EditField) view.getTag())
                                                        .getKey())) {
                                    mustFieldList.getList().get(i)
                                            .setValue(strValue); //
                                }
                            }
                        }
                    }
                });
            }
        });
        mLinearlayout_formdetail.initData(tab, ((WebSearchActivity) getActivity()).mDocResultInfo, currentEditTextView, EditFileds, fieldMap, ((WebSearchActivity) getActivity()).comeShare, ((WebSearchActivity) getActivity()).com_workflow_mobileconfig_IM_enabled, ((WebSearchActivity) getActivity()).isWaterSecurity);
        ComponentInit.getInstance().setmICallCheckUserListener(new ICallCheckUserListener() {
            @Override
            public void getCheckUserListener(View view, IChooseWay mChooseWay, Choose mChoose) {
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
    public void onDestroy(){
        super.onDestroy();
    }
    @Override
    public void onFormClick(EditField edit) {
        FieldItem item = null;
        if (edit == null) {
            if(tab.tabEvent.eventType == -1){
                return;
            }
            mEventApiParameters = new EventApiParameters();
            mEventApiParameters.appId = app_id;
            mEventApiParameters.appVersionId = ((WebSearchActivity) getActivity()).app_version_id;
            mEventApiParameters.userId = OAConText.getInstance(getActivity()).UserID;
            mEventApiParameters.flowId = tab.flowId;
            mEventApiParameters.formKey = tab.formKey;
            mEventApiParameters.formId = tab.formKey;
            mEventApiParameters.dataId = tab.dataId;
            mEventApiParameters.eventType = tab.tabEvent.eventType + "";
            List<OtherValues> otherValuesList = new ArrayList<OtherValues>();
            List<EditField> editFields = ((WebSearchActivity) getActivity()).mDocResultInfo.getResult().getEditFields();
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
            netWorkManager.logFunactionStart(getActivity(), WebViewFormDetailFragment.this, LogManagerEnum.EVENT_API);
//            AnsynHttpRequest.requestByPost(getActivity(), mEventApiParameters, event_api_url, CHTTP.POST_LOG, StartFormFragment.this, HTTPEVENTAPI, LogManagerEnum.GGENERAL.functionCode);
            return;
        } else {
            item = (FieldItem) fieldMap.get(edit.getKey());
        }
        if (item != null && (item.getAjaxEvent() == null || -1 != item.getAjaxEvent().eventType)) {
            return;
        } else {
            this.editCurrent = edit;
            List<EditField> editFields = ((WebSearchActivity) getActivity()).mDocResultInfo.getResult().getEditFields();
            mEventApiParameters = new com.htmitech.htworkflowformpluginnew.entity.entity.EventApiParameters();
            mEventApiParameters.appId = app_id;
            mEventApiParameters.appVersionId = ((WebSearchActivity) getActivity()).app_version_id;
            mEventApiParameters.userId = OAConText.getInstance(getActivity()).UserID;
            mEventApiParameters.flowId = tab.flowId;
            mEventApiParameters.formKey = tab.formKey;
            mEventApiParameters.formId = tab.formKey;
            mEventApiParameters.dataId = tab.dataId;
            mEventApiParameters.fieldKey = edit.getKey();
            mEventApiParameters.fieldValue = edit.getValue();
            if (item != null && item.getAjaxEvent() != null)
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
                            if (editField.getKey().equals(((FieldItem) fieldMap.get(str)).getKey())) {
                                otherValues.fieldValue = editField.getValue();
                            }
                        }
                    }
                    otherValuesList.add(otherValues);
                }
            }

            mEventApiParameters.otherValues = otherValuesList;
            netWorkManager.logFunactionStart(getActivity(), WebViewFormDetailFragment.this, LogManagerEnum.EVENT_API);
        }
    }

    //TODO 临时数据 模拟接口
    public void successTemp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String jsonStr = readRawFile();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        success(jsonStr, 0, HTTPEVENTAPI);
                    }
                });

            }
        }).start();
    }

    private String readRawFile() {
//        String content;
//        Resources resources = this.getResources();
//        InputStream is = null;
//        try {
//            is = resources.openRawResource(R.raw.bi2019);
//            byte buffer[] = new byte[is.available()];
//            is.read(buffer);
//            content = new String(buffer);
//            return content;
//        } catch (IOException e) {
//            Log.e("write file", e.toString());
//            return null;
//        } finally {
//            if (is != null) {
//                try {
//                    is.close();
//                } catch (IOException e) {
//                    Log.e("close file", e.toString());
//                }
//            }
//        }
        return "0";
    }

    @Override
    public void success(String requestValue, int type, String requestName) {
        if (requestName.equals(HTTPEVENTAPI)) {
            try {
                if (requestValue != null) {
                    Gson gson = new Gson();
                    EventApiResultInfo mEventApiResultInfo = gson.fromJson(requestValue, EventApiResultInfo.class);
                    if (mEventApiResultInfo.getCode() == 200 && mEventApiResultInfo.getResultStatus() == 0) {
                        netWorkManager.logFunactionFinsh(getActivity(), WebViewFormDetailFragment.this, "event_api_success", LogManagerEnum.EVENT_API.functionCode, "success", INetWorkManager.State.SUCCESS);
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
                                        boolean isHiden = false;
                                        if(mChangedFieldsInfo.getHiden() == 0){
                                            isHiden = false;
                                        }else{
                                            isHiden = true;
                                        }
                                        item.setIsExt(isHiden ? 1 : 0);
                                        item.setEditable(mChangedFieldsInfo.getEditable());
                                        mLinearlayout_formdetail.swichType(Integer.parseInt(item.getInput()), item);

                                        //预防出现隐藏字段 且是不可编辑字段需要同步提交一些值，比如地理信息
                                        EditField edit_field = new EditField();
                                        edit_field.setKey(mChangedFieldsInfo.getFieldKey());
                                        edit_field.setValue(mChangedFieldsInfo.getFieldValue());
                                        edit_field.setFormKey(tab.formKey);
                                        edit_field.setSign("true");
                                        edit_field.setIsExt(isHiden);
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

                        } else if (!TextUtils.isEmpty(mEventApiResultInfo.getResult().getResultMsg())) {
                            ToastInfo toastInfo = ToastInfo.getInstance(getActivity());
                            toastInfo.setText("" + mEventApiResultInfo.getResult().getResultMsg());
                            toastInfo.show(Toast.LENGTH_LONG);
                        }
                    } else if (mEventApiResultInfo.getCode() == 200 && mEventApiResultInfo.getResultStatus() == 1) {
                        netWorkManager.logFunactionFinsh(getActivity(), WebViewFormDetailFragment.this, "event_api_success", LogManagerEnum.EVENT_API.functionCode, "success", INetWorkManager.State.SUCCESS);
                        Toast.makeText(getActivity(), mEventApiResultInfo.getResult().getResultMsg(), Toast.LENGTH_SHORT).show();
                       /* FieldItem item = (FieldItem) fieldMap.get(editCurrent.getKey());
                        if (item != null) {
                            item.setValue("");
                        }
                        swichType(Integer.parseInt(item.getInput()), item);*/
                        return;
                    } else {
                        netWorkManager.logFunactionFinsh(getActivity(), WebViewFormDetailFragment.this, "event_api_fail", LogManagerEnum.EVENT_API.functionCode, "success", INetWorkManager.State.FAIL);
                    }

                }
            } catch (Exception e) {
                netWorkManager.logFunactionFinsh(getActivity(), WebViewFormDetailFragment.this, "event_api_fail", LogManagerEnum.EVENT_API.functionCode, "success", INetWorkManager.State.FAIL);
                e.printStackTrace();
            }
        } else if (requestName.equals(LogManagerEnum.EVENT_API.functionCode)) {
            AnsynHttpRequest.requestByPost(getActivity(), mEventApiParameters, event_api_url, CHTTP.POST_LOG, WebViewFormDetailFragment.this, HTTPEVENTAPI, LogManagerEnum.EVENT_API.functionCode);

        }
    }

    @Override
    public void fail(String exceptionMessage, int type, String requestName) {
        if (requestName.equals(HTTPEVENTAPI)) {
            Log.e(TAG, "success: " + exceptionMessage);
            netWorkManager.logFunactionFinsh(getActivity(), WebViewFormDetailFragment.this, "event_api_success", LogManagerEnum.EVENT_API.functionCode, "success", INetWorkManager.State.FAIL);
        } else if (requestName.equals(LogManagerEnum.EVENT_API)) {
            AnsynHttpRequest.requestByPost(getActivity(), mEventApiParameters, event_api_url, CHTTP.POST_LOG, WebViewFormDetailFragment.this, HTTPEVENTAPI, LogManagerEnum.EVENT_API.functionCode);

        }

    }

    @Override
    public void notNetwork() {

    }

    @Override
    public void callbackMainUI(String successMessage) {

    }






    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub

        if (requestCode == 10003) {
            if (resultCode == Activity.RESULT_OK) {
                FieldItem item_workflow = (FieldItem) imageParme.get(imageFilePath);
                Log.e(TAG, "onActivityResult: " + item_workflow.getKey());
                PhotoModel mPhotoModel = new PhotoModel();
                mPhotoModel.setOriginalPath(imageFilePath);
                mPhotoModel.setItem_workflow(item_workflow);
                List<PhotoModel> listImgTemp = new ArrayList<PhotoModel>();
                listImgTemp.add(mPhotoModel);
                if (((WebSearchActivity) getActivity()).formMap.get(item_workflow.getFieldId()) != null && ((WebSearchActivity) getActivity()).formMap.get(item_workflow.getFieldId()) instanceof SelectPhoto6001_6002_6101_6102) {
                    ((SelectPhoto6001_6002_6101_6102) ((WebSearchActivity) getActivity()).formMap.get(item_workflow.getFieldId())).updateImg(getActivity(), listImgTemp, 0);
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
//                if (getActivity() instanceof WebSearchActivity) {
//                    ((WebSearchActivity) getActivity()).setComment(strOption);
//                }
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
        WebViewFormDetailFragment.this.mClickView = mClickView;
        EditField edit = (EditField) WebViewFormDetailFragment.this.mClickView.getTag();
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
        mDocResultInfo = ((WebSearchActivity) getActivity()).mDocResultInfo;
        // DocResultInfo mDocResultInfo = ((WorkFlowFormDetalActivity)
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
                            ((EditField) WebViewFormDetailFragment.this.mClickView.getTag())
                                    .getKey())) {
                mustFieldList.getList().get(i).tempValue = strOption;
                mustFieldList.getList().get(i).setValue(strOption); //
            }
        }
    }


    public class YiJianOnclickLisener implements OnClickListener {// 设置表格项的监听器

        @Override
        public void onClick(View v) {

            if (getActivity() != null && !((WebSearchActivity) getActivity()).comeShare.equals("1")) {
                Intent intent = new Intent(getActivity(), OpinionInputActivity.class);
                intent.putExtra("app_id", app_id);
                if (v instanceof TextView) {
                    intent.putExtra("textValue", ((TextView) v).getText().toString());
                }
                intent.putExtra("com_workflow_mobileconfig_include_options", com_workflow_mobileconfig_include_options);
                startActivityForResult(intent, OPTION_REQUEST_CODE_1);
            }
            // *********************测试临时代码******************************
            WebViewFormDetailFragment.this.mClickView = v;

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

    @Override
    public void checkImageSelect(ArrayList<PhotoModel> mSelectedImage, Fielditems item, FieldItem workflow_item) {
        Log.e(TAG, "checkImageSelect: " + mSelectedImage.size() + "formKey: " + item);
        List<PhotoModel> listImgTemp = new ArrayList<PhotoModel>();
        if (mSelectedImage != null && mSelectedImage.size() > 0) {
            for (PhotoModel mPhotoModel : mSelectedImage) {
                mPhotoModel.setItem_workflow(workflow_item);
                listImgTemp.add(mPhotoModel);
            }
        }
        if (((WebSearchActivity) getActivity()).formMap.get(workflow_item.getFieldId()) != null && ((WebSearchActivity) getActivity()).formMap.get(workflow_item.getFieldId()) instanceof SelectPhoto6001_6002_6101_6102) {
            ((SelectPhoto6001_6002_6101_6102) ((WebSearchActivity) getActivity()).formMap.get(workflow_item.getFieldId())).updateImg(getActivity(), listImgTemp, 1);
        }
    }


    public class CellOnclickLisener6001_6002_6101_6102 implements ICellOnclick6102 {// 设置表格项的监听器


        @Override
        public <T> void onClick(View v, T t) {
            FieldItem item = (FieldItem) t;
            if (v.getId() == com.htmitech.addressbook.R.id.bt_send) {
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("workflow_item", item);
//                params.put("photo_number", ((SelectPhoto6001_6002_6101_6102) ((WorkFlowFormDetalActivity) getActivity()).formMap.get(item.getField_id())).mListPickerAudioSelect.size() );
                ArrayList<PhotoModel> album_select = new ArrayList<PhotoModel>();
                for (PhotoModel model : ((SelectPhoto6001_6002_6101_6102) ((WebSearchActivity) getActivity()).formMap.get(item.getFieldId())).listImgs) {
                    if (model.isChecked()) {
                        album_select.add(model);
                    }
                }
                params.put("album_select", album_select);
                params.put("photo_number", ((SelectPhoto6001_6002_6101_6102) ((WebSearchActivity) getActivity()).formMap.get(item.getFieldId())).listImgs.size() - album_select.size());
                ActivityUnit.switchTo(getActivity(), PhotoSelectorActivity.class, params);
                menuWindow.dismiss();
            } else if (v.getId() == com.htmitech.addressbook.R.id.bt_call) {
                imageFilePath = FileSizeUtil.getTempPhotoFileName(OAConText.getInstance(getActivity()).UserID, BookInit.getInstance().getPortalId(), app_id);

                File temp = new File(imageFilePath);
                Uri imageFileUri = Uri.fromFile(temp);//获取文件的Uri
                Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//跳转到相机Activity
                it.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);//告诉相机拍摄完毕输出图片到指定的Uri
                imageParme.put(imageFilePath, item);
                startActivityForResult(it, 10003);
                menuWindow.dismiss();
            } else if (v.getId() == com.htmitech.addressbook.R.id.bt_save) {
                menuWindow.dismiss();
            } else {
                if (!"".equals(item.getInput().trim()) && Integer.parseInt(item.getInput().trim()) == 6001) {
                    if (((SelectPhoto6001_6002_6101_6102) ((WebSearchActivity) getActivity()).formMap.get(item.getFieldId())).listImgs.size() >= 1) {
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
                    if (((SelectPhoto6001_6002_6101_6102) ((WebSearchActivity) getActivity()).formMap.get(item.getFieldId())).listImgs.size() >= 9) {
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
                    if (!"".equals(item.getInput().trim()) && Integer.parseInt(item.getInput().trim()) == 6002 && ((SelectPhoto6001_6002_6101_6102) ((WebSearchActivity) getActivity()).formMap.get(item.getFieldId())).listImgs.size() >= 1) {
                        Toast.makeText(getActivity(), "只能添加一张图片", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (!"".equals(item.getInput().trim()) && Integer.parseInt(item.getInput().trim()) == 6102 && ((SelectPhoto6001_6002_6101_6102) ((WebSearchActivity) getActivity()).formMap.get(item.getFieldId())).listImgs.size() >= 9) {
                        Toast.makeText(getActivity(), "图片最多可以添加九张", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (getActivity() != null && !((WebSearchActivity) getActivity()).comeShare.equals("1")) {
                        //TODO 添加点击照片的监听处理
                        menuWindow = new SelectPicPopupWindow(getActivity(), this);
                        menuWindow.setDetalFormMessage();
                        //显示窗口
                        menuWindow.showAtLocation(v, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                    }
                }
            }


            // *********************测试临时代码******************************
            WebViewFormDetailFragment.this.mClickView = v;

        }

        @Override
        public void onClick(View v) {

        }

    }

    public class CYiJianOnclickLisener implements htmitech.listener.YiJianOnclickLisener {// 设置表格项的监听器

        @Override
        public void onClick(View v) {

            if (getActivity() != null && !((WebSearchActivity) getActivity()).comeShare.equals("1")) {
                Intent intent = new Intent(getActivity(), OpinionInputActivity.class);
                intent.putExtra("app_id", app_id);
                if (v instanceof TextView) {
                    intent.putExtra("textValue", ((TextView) v).getText().toString());
                }
                intent.putExtra("com_workflow_mobileconfig_include_options", com_workflow_mobileconfig_include_options);
                startActivityForResult(intent, OPTION_REQUEST_CODE_1);
            }
            // *********************测试临时代码******************************
            WebViewFormDetailFragment.this.mClickView = v;

        }
    }

    public class CellOnclickLisener implements htmitech.listener.YiJianOnclickLisener {// 设置表格项的监听器

        @Override
        public void onClick(View v) {

            if (getActivity() != null && !((WebSearchActivity) getActivity()).comeShare.equals("1")) {
                Intent intent = new Intent(getActivity(), OpinionInputActivity.class);
                intent.putExtra("app_id", app_id);
                intent.putExtra("com_workflow_mobileconfig_include_options", com_workflow_mobileconfig_include_options);
                startActivityForResult(intent, OPTION_REQUEST_CODE_0);
            }
            WebViewFormDetailFragment.this.mClickView = v;
        }
    }


    public class CellOnclickLisener2004 implements ICellOnclick6102 {// 设置表格项的监听器
        String textValue = "";

        @Override
        public <T> void onClick(View v, T t) {
            textValue = (String) t;
            if (getActivity() != null && !((WebSearchActivity) getActivity()).comeShare.equals("1")) {
                Intent intent = new Intent(getActivity(), OpinionInputActivity.class);
                intent.putExtra("app_id", app_id);
//                intent.putExtra("is2004", true); //是否屏蔽常用意见列表
                if (v instanceof TextView) {
                    intent.putExtra("textValue", ((TextView) v).getText().toString());
                }
                intent.putExtra("com_workflow_mobileconfig_include_options", com_workflow_mobileconfig_include_options);
                startActivityForResult(intent, OPTION_REQUEST_CODE_0);
            }

            WebViewFormDetailFragment.this.mClickView = v;

        }

        @Override
        public void onClick(View v) {

        }

    }
}
