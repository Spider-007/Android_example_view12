package htmitech.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import htmitech.com.componentlibrary.ComboBox;
import htmitech.com.componentlibrary.api.ComponentInit;
import htmitech.com.componentlibrary.datepicker.PopChooseTimeHelper;
import htmitech.com.componentlibrary.entity.AuthorInfo;
import htmitech.com.componentlibrary.entity.DocResultInfo;
import htmitech.com.componentlibrary.entity.EditField;
import htmitech.com.componentlibrary.entity.EditFieldList;
import htmitech.com.componentlibrary.entity.FieldItem;
import htmitech.com.componentlibrary.entity.InfoRegion;
import htmitech.com.componentlibrary.entity.InfoTab;
import htmitech.com.componentlibrary.listener.ScrollViewListener;
import htmitech.com.componentlibrary.unit.DensityUtil;
import htmitech.com.componentlibrary.unit.SysConvertUtil;
import htmitech.com.componentlibrary.unit.Utils;
import htmitech.com.formlibrary.R;
import htmitech.formConfig.AudioSelect4002;
import htmitech.formConfig.CheckDate300_301;
import htmitech.formConfig.DepartmentCheck911_912;
import htmitech.formConfig.EditableOpinion2002;
import htmitech.formConfig.EditableOpinionSig2003;
import htmitech.formConfig.FormOpinion2001;
import htmitech.formConfig.FormOpinion2004;
import htmitech.formConfig.IntervalControl8001_002;
import htmitech.formConfig.ModeOpinion19;
import htmitech.formConfig.NotEditLayout;
import htmitech.formConfig.OtherLayout;
import htmitech.formConfig.PeopleCheck611_612_613;
import htmitech.formConfig.RadioButton501_02_03_11_513;
import htmitech.formConfig.RadioButton521_22_23_31_32_33;
import htmitech.formConfig.RadioDepartment901_902;
import htmitech.formConfig.RadioPeople601_02_03;
import htmitech.formConfig.SelectPhoto6001_6002_6101_6102;
import htmitech.formConfig.SelectPoisition7001;
import htmitech.formConfig.SpringLayout412_403_402_401;
import htmitech.formConfig.Zidian514;
import htmitech.listener.CallBackDoAction;
import htmitech.listener.HTSaveFormExtensionFiles;
import htmitech.listener.ICellOnclick6102;
import htmitech.listener.YiJianOnclickLisener;
import htmitech.util.AccssFormKey;

/**
 * Created by htrf-pc on 2018-3-15.
 */
public class FormContainerView extends LinearLayout implements CallBackDoAction {
    private InfoRegion[] paramaters;
    private Context context;
    private float heightLinear = 50.0f;
    private int height;
    private String VlineVisible = "false";
    private int indexCount;
    private int isWaterSecurity = -1;
    private Map<String, Object> linerLayoutMap = new HashMap<String, Object>();
    private LayoutInflater mInflater;
    private Map<String, Object> fieldMap;
    private List<TextView> list_tvsize;
    private List<EditText> list_etsize;
    private List<RadioButton> list_rbsize;
    private List<CheckBox> list_cbsize;
    private EditText currentEditText;
    private List<ComboBox> list_comboboxsize;
    private PopChooseTimeHelper mPopBirthHelper = null;
    private String opinionText;
    public int tabType = 0;
    public int tabStyle = 0;
    private InfoTab tab;
    private int com_workflow_mobileconfig_IM_enabled;
    private int com_workflow_mobileconfig_opinion_style;
    public Map<String, Object> formMap;
    /* 表单项编辑意见操作用到的变量 —— begin */
    private List<EditField> EditFileds;

    public int getCom_workflow_mobileconfig_opinion_style() {
        return com_workflow_mobileconfig_opinion_style;
    }

    public void setCom_workflow_mobileconfig_opinion_style(int com_workflow_mobileconfig_opinion_style) {
        this.com_workflow_mobileconfig_opinion_style = com_workflow_mobileconfig_opinion_style;
    }

    private String comeShare;
    String[] options;
    String[] values;
    String[] ids;

    public ScrollView scrollView;

    public Map<String, Integer> viewHight = new HashMap<String, Integer>();

    private Handler handler = new Handler();

    private DocResultInfo mDocResultInfo;
    private CallBackDoAction mCallBackDoAction;
    private ICellOnclick6102 mCellOnclickLisener6001_6002_6101_6102;

    private ICellOnclick6102 mCellOnclickLisener4002_4101;

    private ICellOnclick6102 mIntentPhone;

    private YiJianOnclickLisener mYiJianOnclickLisener;

    private YiJianOnclickLisener mCellOnclickLisener;

    private ICellOnclick6102 mCellOnclickLisener2004;

    private ScrollViewListener mScrollViewListener = new ScrollViewListener.DefalutScrollViewListener();//必填参数

    private HTSaveFormExtensionFiles mHTSaveFormExtensionFiles = new HTSaveFormExtensionFiles.DefaultHTSaveFormExtensionFiles();

    private TextView currentEditTextView;
    private ArrayList<ComboBox> mComboBoxList = new ArrayList<ComboBox>(); //必须传入

    public ScrollViewListener getmScrollViewListener() {
        return mScrollViewListener;
    }

    public List<ComboBox> getList_comboboxsize() {
        return list_comboboxsize;
    }

    public void setList_comboboxsize(List<ComboBox> list_comboboxsize) {
        this.list_comboboxsize = list_comboboxsize;
    }

    public void setmScrollViewListener(ScrollViewListener mScrollViewListener) {
        this.mScrollViewListener = mScrollViewListener;
    }

    public void setScrollView(ScrollView scrollView) {
        this.scrollView = scrollView;
    }

    public ScrollView getScrollView() {
        return scrollView;
    }

    public ArrayList<ComboBox> getmComboBoxList() {
        return mComboBoxList;
    }

    public void setmComboBoxList(ArrayList<ComboBox> mComboBoxList) {
        this.mComboBoxList = mComboBoxList;
    }

    public HTSaveFormExtensionFiles getmHTSaveFormExtensionFiles() {
        return mHTSaveFormExtensionFiles;
    }

    public void setmHTSaveFormExtensionFiles(HTSaveFormExtensionFiles mHTSaveFormExtensionFiles) {
        this.mHTSaveFormExtensionFiles = mHTSaveFormExtensionFiles;
    }

    public ICellOnclick6102 getmCellOnclickLisener2004() {
        return mCellOnclickLisener2004;
    }

    public void setmCellOnclickLisener2004(ICellOnclick6102 mCellOnclickLisener2004) {
        this.mCellOnclickLisener2004 = mCellOnclickLisener2004;
    }

    public YiJianOnclickLisener getmCellOnclickLisener() {
        return mCellOnclickLisener;
    }

    public void setmCellOnclickLisener(YiJianOnclickLisener mCellOnclickLisener) {
        this.mCellOnclickLisener = mCellOnclickLisener;
    }

    public ICellOnclick6102 getmIntentPhone() {
        return mIntentPhone;
    }

    public void setmIntentPhone(ICellOnclick6102 mIntentPhone) {
        this.mIntentPhone = mIntentPhone;
    }

    public YiJianOnclickLisener getmYiJianOnclickLisener() {
        return mYiJianOnclickLisener;
    }

    public void setmYiJianOnclickLisener(YiJianOnclickLisener mYiJianOnclickLisener) {
        this.mYiJianOnclickLisener = mYiJianOnclickLisener;
    }

    public ICellOnclick6102 getmCellOnclickLisener4002_4101() {
        return mCellOnclickLisener4002_4101;
    }

    public void setmCellOnclickLisener4002_4101(ICellOnclick6102 mCellOnclickLisener4002_4101) {
        this.mCellOnclickLisener4002_4101 = mCellOnclickLisener4002_4101;
    }

    public ICellOnclick6102 getmCellOnclickLisener6001_6002_6101_6102() {
        return mCellOnclickLisener6001_6002_6101_6102;
    }

    public void setmCellOnclickLisener6001_6002_6101_6102(ICellOnclick6102 mCellOnclickLisener6001_6002_6101_6102) {
        this.mCellOnclickLisener6001_6002_6101_6102 = mCellOnclickLisener6001_6002_6101_6102;
    }

    public CallBackDoAction getmCallBackDoAction() {
        return mCallBackDoAction;
    }

    public void setmCallBackDoAction(CallBackDoAction mCallBackDoAction) {
        this.mCallBackDoAction = mCallBackDoAction;
    }

    public String getComeShare() {
        return comeShare;
    }

    public void setComeShare(String comeShare) {
        this.comeShare = comeShare;
    }

    public Map<String, Object> getFormMap() {
        return formMap;
    }

    public void setFormMap(Map<String, Object> formMap) {
        this.formMap = formMap;
    }

    public List<EditField> getEditFileds() {
        return EditFileds;
    }

    public void setEditFileds(List<EditField> editFileds) {
        EditFileds = editFileds;
    }

    public int getCom_workflow_mobileconfig_IM_enabled() {
        return com_workflow_mobileconfig_IM_enabled;
    }

    public void setCom_workflow_mobileconfig_IM_enabled(int com_workflow_mobileconfig_IM_enabled) {
        this.com_workflow_mobileconfig_IM_enabled = com_workflow_mobileconfig_IM_enabled;
    }

    public int getIsWaterSecurity() {
        return isWaterSecurity;
    }

    public void setIsWaterSecurity(int isWaterSecurity) {
        this.isWaterSecurity = isWaterSecurity;
    }

    public FormContainerView(Context context) {
        super(context);
        this.context = context;
    }

    public FormContainerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public FormContainerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    public List<TextView> getList_tvsize() {
        return list_tvsize;
    }

    public void setList_tvsize(List<TextView> list_tvsize) {
        this.list_tvsize = list_tvsize;
    }

    public List<EditText> getList_etsize() {
        return list_etsize;
    }

    public void setList_etsize(List<EditText> list_etsize) {
        this.list_etsize = list_etsize;
    }

    public List<RadioButton> getList_rbsize() {
        return list_rbsize;
    }

    public void setList_rbsize(List<RadioButton> list_rbsize) {
        this.list_rbsize = list_rbsize;
    }

    public List<CheckBox> getList_cbsize() {
        return list_cbsize;
    }

    public void setList_cbsize(List<CheckBox> list_cbsize) {
        this.list_cbsize = list_cbsize;
    }

    /**
     * 刷新方法
     */
    public void refreshData(FieldItem[] mFieldItems) {
        for (FieldItem mFieldItem : mFieldItems) {
            swichType(Integer.parseInt(mFieldItem.getInput()), mFieldItem);
        }
    }

    public void refreshData(InfoRegion[] paramaters){
        this.paramaters = paramaters;
        initData();
    }

    /**
     * 刷新方法
     */
    public void refreshData(DocResultInfo mDocResultInfo) {
        this.mDocResultInfo = mDocResultInfo;
        initData();
    }

    public class MyRunnable implements Runnable{
        private ScrollView scrollView;
        private int offcex;
        public MyRunnable(ScrollView scrollView,int offcex){
            this.scrollView = scrollView;
            this.offcex = offcex;
        }
        @Override
        public void run() {
            if(scrollView != null)
                scrollView.scrollTo(0, offcex);// 改变滚动条的位置
        }
    }
    //必填项校验
    public void switchInputType(String inputStr,String key) {
        if(TextUtils.isEmpty(inputStr)){
            return ;
        }
        int input = 0;
        try{
            input = Integer.parseInt(inputStr);
        }catch (Exception e){
            e.printStackTrace();
            return ;
        }
        switch (input) {
            case 2003:
                if (linerLayoutMap.get(key) instanceof EditableOpinionSig2003){
                    EditableOpinionSig2003 otherLayout = (EditableOpinionSig2003) (linerLayoutMap.get(key));
                    otherLayout.setMustInputLoacal();

                    handler.postDelayed(new MyRunnable(scrollView,viewHight.get(key)), 200);
                }
                break;

            case 2001:// 可编辑字段处理,意见
                if (linerLayoutMap.get(key) instanceof FormOpinion2001){
                    FormOpinion2001 otherLayout = (FormOpinion2001) (linerLayoutMap.get(key));
                    otherLayout.setMustInputLoacal();
                    Handler handler = new Handler();
                    handler.postDelayed(new MyRunnable(scrollView,viewHight.get(key)), 200);
                }
                break;


            case 2002:
                // 1，**********************处理可编辑的意见字段******************************
                if (linerLayoutMap.get(key) instanceof EditableOpinion2002){
                    EditableOpinion2002 otherLayout = (EditableOpinion2002) (linerLayoutMap.get(key));
                    otherLayout.setMustInputLoacal();
                    Handler handler = new Handler();
                    handler.postDelayed(new MyRunnable(scrollView,viewHight.get(key)), 200);
                }
                break;

            case 2004:
                if (linerLayoutMap.get(key) instanceof FormOpinion2004){
                    FormOpinion2004 otherLayout = (FormOpinion2004) (linerLayoutMap.get(key));
                    otherLayout.setMustInputLoacal();
                    Handler handler = new Handler();
                    handler.postDelayed(new MyRunnable(scrollView,viewHight.get(key)), 200);
                }
                break;

            case 6001:
            case 6002:
            case 6101:
            case 6102:

                break;
            case 412://获取Name支持输入(下拉框选择模式)
            case 403://获取Value
            case 402://获取Name
            case 401://获取ID
                if (linerLayoutMap.get(key) instanceof SpringLayout412_403_402_401){
                    SpringLayout412_403_402_401 otherLayout = (SpringLayout412_403_402_401) (linerLayoutMap.get(key));
                    otherLayout.setMustInputLoacal();
                    Handler handler = new Handler();
                    handler.postDelayed(new MyRunnable(scrollView,viewHight.get(key)), 200);
                }
                break;
            case 300:
            case 301:
                // ***********************日期选择***************************
                if (linerLayoutMap.get(key) instanceof CheckDate300_301){
                    CheckDate300_301 otherLayout = (CheckDate300_301) (linerLayoutMap.get(key));
                    otherLayout.setMustInputLoacal();
                    Handler handler = new Handler();
                    handler.postDelayed(new MyRunnable(scrollView,viewHight.get(key)), 200);
                }
                break;
            case 3011://读者多选
            case 3012://作者多选
            case 611:
            case 612://人员多选
            case 613:
                if (linerLayoutMap.get(key) instanceof PeopleCheck611_612_613){
                    PeopleCheck611_612_613 otherLayout = (PeopleCheck611_612_613) (linerLayoutMap.get(key));
                    otherLayout.setMustInputLoacal();
                    Handler handler = new Handler();
                    handler.postDelayed(new MyRunnable(scrollView,viewHight.get(key)), 200);
                }
                break;
            case 911:
            case 912:
                //912 表示选择部门返回ID  911 是返回NAME
                if (linerLayoutMap.get(key) instanceof DepartmentCheck911_912){
                    DepartmentCheck911_912 otherLayout = (DepartmentCheck911_912) (linerLayoutMap.get(key));
                    otherLayout.setMustInputLoacal();
                    Handler handler = new Handler();
                    handler.postDelayed(new MyRunnable(scrollView,viewHight.get(key)), 200);
                }
                break;
            case 3001: //读者单选
            case 3002://作者单选
            case 601:
            case 602:
            case 603: //人员单选
                if (linerLayoutMap.get(key) instanceof RadioPeople601_02_03){
                    RadioPeople601_02_03 otherLayout = (RadioPeople601_02_03) (linerLayoutMap.get(key));
                    otherLayout.setMustInputLoacal();
                    Handler handler = new Handler();
                    handler.postDelayed(new MyRunnable(scrollView,viewHight.get(key)), 200);
                }
                break;
            case 901:
            case 902:
                if (linerLayoutMap.get(key) instanceof RadioDepartment901_902){
                    RadioDepartment901_902 otherLayout = (RadioDepartment901_902) (linerLayoutMap.get(key));
                    otherLayout.setMustInputLoacal();
                    Handler handler = new Handler();
                    handler.postDelayed(new MyRunnable(scrollView,viewHight.get(key)), 200);
                }
                break;
            case 501://单选框结果取ID
            case 502://单选框结果取Name
            case 503://单选框结果取Value
            case 511://复选框结果取ID
            case 512://复选框结果取Name
            case 513://复选框结果取Value

                break;
            case 514: //字典

                break;
            case 521:
            case 522:
            case 523:
            case 531:
            case 532:
            case 533:

                break;
            case 8001:
            case 8002:

                break;
            default:// 4，**********************其他非意见可编辑字段**********************

                if (linerLayoutMap.get(key) instanceof OtherLayout){
                    OtherLayout otherLayout = (OtherLayout) (linerLayoutMap.get(key));
                    otherLayout.setMustInputLoacal("");
                    Handler handler = new Handler();
                    handler.postDelayed(new MyRunnable(scrollView,viewHight.get(key)), 200);
                }
                break;


        }

    }


    public void initData(InfoTab tab, DocResultInfo mDocResultInfo, TextView currentEditTextView, List<EditField> EditFileds, Map<String, Object> fieldMap, String comeShare, int com_workflow_mobileconfig_IM_enabled, int isWaterSecurity) {
        this.paramaters = tab.regions;
        this.currentEditTextView = currentEditTextView;
        this.com_workflow_mobileconfig_IM_enabled = com_workflow_mobileconfig_IM_enabled;
        this.EditFileds = EditFileds;
        this.tab = tab;
        this.comeShare = comeShare;
        this.tabType = tab.tabType;
        this.fieldMap = fieldMap;
        this.tabStyle = tab.tabStyle;
        this.tabStyle = 0;
        this.isWaterSecurity = isWaterSecurity;
        this.mDocResultInfo = mDocResultInfo;
        mInflater = LayoutInflater.from(context);
        list_tvsize = new ArrayList<TextView>();
        list_etsize = new ArrayList<EditText>();
        list_rbsize = new ArrayList<RadioButton>();
        list_cbsize = new ArrayList<CheckBox>();
        list_comboboxsize = new ArrayList<ComboBox>();
        height = DensityUtil.dip2px(context, heightLinear);
        initData();
    }

    /**
     * 初始化 表单细节
     * 每一个 FieldItems 为一行， 一行也可能包含多个字段
     */
    private void initData() {
        this.removeAllViews();
        if (paramaters != null && paramaters.length > 0) {
            /**
             * 对数据拆分额
             */
            for (int i = 0; i < paramaters.length; i++) {       //	历每一个 表单项
                LinearLayout lineView = new LinearLayout(        //创建一个linearLayout 容器 当做行视图来用
                        context);
                lineView.setFocusable(false);
                lineView.setBackgroundResource(R.drawable.corners_bg_white_press_stroke);    //2015-09-22添加 for 项目申报字表行背景颜色
                //  lineView.setBackgroundColor(paramaters[i].getBackColor());
                /**
                 * V2.0add
                 */
                if (paramaters[i].isSplitRegion() && paramaters[i].getSplitAction() == 0) {//如果是0那么添加一个空白行不做任何操作
                    int height = DensityUtil.dip2px(context, 20);
                    LinearLayout.LayoutParams param0 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height, 1); //创建 Linearlayout 布局参数
                    lineView.setBackgroundResource(R.drawable.corners_bg_white_empty_little_stroke);
                    if (isWaterSecurity == 1)
                        lineView.getBackground().setAlpha((int) (0.5 * 255));
                    this.addView(lineView, param0);
                } else {
                    //给每个linearlayout添加点击事件
                    lineView.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String RegionIDClick = (String) v.getTag(R.id.tag_first);
                            ImageView iv_circle = (ImageView) v.getTag(R.id.tag_second);
                            int nowClickNum = -1;
                            for (int n = 0; n < paramaters.length; n++) {
                                if (paramaters[n].getRegionID().equals(RegionIDClick)) {
                                    nowClickNum = n;
                                    break;
                                }
                            }
                            if (nowClickNum != -1 && paramaters[nowClickNum].isSplitRegion() && paramaters[nowClickNum].getSplitAction() == 1) {//如果是1代表可以下拉
                                for (int k = 0; k < paramaters.length; k++) {
                                    if (RegionIDClick != null && paramaters[k].getParentRegionID() != null) {
                                        if (paramaters[k].getParentRegionID().equals(RegionIDClick)) {
                                            //点击后如果是显示的则让不显示，不显示的显示。
                                            if (getChildAt(k) == null) {
                                                return;
                                            }
                                            getChildAt(k).setVisibility((getChildAt(k).getVisibility() == View.GONE) ? View.VISIBLE : View.GONE);
                                            if ((getChildAt(k).getVisibility() == View.GONE)) {//如果是被关闭了找他下面是否还有子类全部关闭
                                                if (iv_circle != null) {
                                                    iv_circle.setImageResource(R.drawable.btn_angle_up_circle);
                                                }
                                                isHead(paramaters[k], k);

                                            } else {//如果是打开则改变箭头图标
                                                if (iv_circle != null) {
                                                    iv_circle.setImageResource(R.drawable.btn_angle_down_circle);
                                                }
                                                ImageView iv_circle_child = (ImageView) getChildAt(k).getTag(R.id.tag_second);
                                                if (iv_circle_child != null) {
                                                    iv_circle_child.setImageResource(R.drawable.btn_angle_up_circle);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    });
                    if (paramaters[i].getScrollFlag() != 1) {
                        initOneRecord(lineView, paramaters[i]);
                        //初始化一个记录 利用一个容器 和 一个 数据的详细描述
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1); //创建 Linearlayout 布局参数
                        params.gravity = Gravity.CENTER;
                        lineView.setGravity(Gravity.CENTER);
                        this.addView(lineView, params);    //把一个数据实例和一个 视图加入到lineView
                    } else {
                        initOneRecord(lineView, paramaters[i]);
                    }
                }
            }
        }
        //进入界面折叠子表默认折叠
        goneStart();
        if (tab.tabEvent != null) {
            ComponentInit.getInstance().getmCallbackFormListener().onFormClick(null);
        }
    }

    /**
     * 遍历表单默认折叠
     */
    private void goneStart() {
        for (int i = 0; i < paramaters.length; i++) {
            for (int j = 0; j < i; j++) {
                if (paramaters[i].getParentRegionID() != null) {
                    if (paramaters[i].getParentRegionID().equals(paramaters[j].getRegionID()) && this.getChildAt(i) != null) {
                        this.getChildAt(i).setVisibility(View.GONE);
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
            if (paramaters[i].getParentRegionID().equals(RegionID) && getChildAt(i) != null) {
//                mLinearlayout_formdetail.getChildAt(i).startAnimation((mLinearlayout_formdetail.getChildAt(i).getVisibility() == View.GONE) ? showAnimation : hiddnAnimation);
                getChildAt(i).setVisibility((getChildAt(i).getVisibility() == View.GONE) ? View.VISIBLE : View.GONE);
                ImageView iv_circle = (ImageView) getChildAt(i).getTag(R.id.tag_second);
                if (iv_circle != null) {
                    if (getChildAt(i).getVisibility() == View.GONE) {
                        iv_circle.setImageResource(R.drawable.btn_angle_up_circle);
                    } else {
                        iv_circle.setImageResource(R.drawable.btn_angle_down_circle);
                    }
                }
                //递归调用自己
                isHead(paramaters[i], i);
            }
            //当父被关闭下面的子全被关闭
            if (getChildAt(n) != null && getChildAt(n).getVisibility() == View.GONE) {
                for (int m = n + 1; m < paramaters.length; m++) {
                    if (getChildAt(m) != null && paramaters[m].getParentRegionID().equals(paramaters[n].getRegionID())) {
                        getChildAt(m).setVisibility(View.GONE);
                    } else {
                        break;
                    }
                }
            }


        }
    }

    private void initOneRecord(LinearLayout lineView, InfoRegion entity) {
        FieldItem[] fieldItemArray = entity.getFieldItems();                    //初始化每一个表单项
        String RegionID = entity.getRegionID();
        boolean IsTable = entity.isTable();
        String TableID = entity.getTableID();
        ImageView im_down;
        String ParentRgionID = entity.getParentRegionID();
        boolean isSplitRegion = entity.isSplitRegion();
        int SplitAction = entity.getSplitAction();
        int ScrollFlag = entity.getScrollFlag();
        lineView.setTag(R.id.tag_first, RegionID);//给linearlayout设置一个标记
        VlineVisible = entity.getVlineVisible() == 0 ? "false" : "true";
        if (fieldItemArray == null || "".equals(fieldItemArray))
            return;

        if (ScrollFlag == 1) {//是横向滑动的
            for (int j = 0; j < paramaters.length; j++) {
                if (RegionID.equals(paramaters[j].getParentRegionID())) {//如果是头 那么先把头显示出来在去执行下面的部分
                    for (int i = 0; i < fieldItemArray.length; i++) {
                        // Layout_weight的深刻理解
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, height, fieldItemArray[i].getPercent());// 宽 0 利用weight 控制， weight 利用表单占比params.gravity = Gravity.CENTER_VERTICAL;
                        lineView.setGravity(Gravity.CENTER_VERTICAL);
                        lineView.addView(initOneCell(fieldItemArray[i], false), params);//初始化每一个表单项里的小name value

                        viewHight.put(fieldItemArray[i].getKey(), Utils.getViewHeight(this));

                    }
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT, height, 1); //创建 Linearlayout 布局参数
                    params.gravity = Gravity.CENTER_VERTICAL;
                    lineView.setGravity(Gravity.CENTER_VERTICAL);
                    addView(lineView, params);    //把一个数据实例和一个 视图加入到lineView

                    LinearLayout lineViewScroll = new LinearLayout(        //创建一个linearLayout 容器 当做行视图来用
                            context);
                    lineViewScroll.setBackgroundResource(R.drawable.corners_bg_white_press_stroke);    //2015-09-22添加 for 项目申报字表行背景颜色
                    lineViewScroll.setGravity(Gravity.CENTER_VERTICAL);
                    lineViewScroll.setOrientation(LinearLayout.HORIZONTAL);
                    ScrollLinear(entity, lineViewScroll);
                    LinearLayout.LayoutParams paramsScroll = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1); //创建 Linearlayout 布局参数
                    addView(lineViewScroll, paramsScroll);    //把一个数据实例和一个 视图加入到lineView
                    break;
                }
            }
            /**
             * 弱弱的加个折叠箭头
             */
            if (isSplitRegion && SplitAction == 1) {
                im_down = new ImageView(context);
                im_down.setImageResource(R.drawable.btn_angle_up_circle);
                //默认是外层的backcolor颜色
                im_down.setBackgroundColor(SysConvertUtil.formattingH(entity.getBackColor()));
                lineView.setTag(R.id.tag_second, im_down);
                LinearLayout.LayoutParams param_down = new LinearLayout.LayoutParams(height, height); //创建 Linearlayout 布局参数
                LinearLayout.LayoutParams param_image = new LinearLayout.LayoutParams(height, height - 3); //创建 Linearlayout 布局参数
                im_down.setLayoutParams(param_image);
                param_down.gravity = Gravity.CENTER_VERTICAL;
                if (fieldItemArray[indexCount].getBackColor() != 0 && fieldItemArray[indexCount].getBackColor() != -1) {//如果里面内容的背景色不为空并且部位-1  那么下拉箭头的颜色的是里面backcolor的颜色
                    im_down.setBackgroundColor(fieldItemArray[indexCount].getBackColor());
                }

                if (isWaterSecurity != -1) {
                    if (isWaterSecurity == 1) {
                        im_down.getBackground().setAlpha((int) (0.25 * 255));
                    }
                } else {
                    im_down.getBackground().setAlpha((int) (0.25 * 255));
                }

                LinearLayout ll_imageview = new LinearLayout(context);
                ll_imageview.setOrientation(LinearLayout.VERTICAL);
                ll_imageview.setBackgroundResource(R.color.transparent);
                TextView tv_line = new TextView(context);
                tv_line.setBackgroundColor(getResources().getColor(R.color.imageline));
                LinearLayout.LayoutParams param_line = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 3); //创建 Linearlayout 布局参数
                tv_line.setLayoutParams(param_line);
                ll_imageview.addView(im_down);
                ll_imageview.addView(tv_line);
//                ll_imageview.getBackground().setAlpha(0);
                lineView.addView(ll_imageview, param_down);
            }
            return;
        }
        boolean flag = true;
        for (int i = 0; i < fieldItemArray.length; i++) {
            /**
             * 如果是意见那种高度设置为matchparent，其他的设置成定值
             */
            if (fieldItemArray[i].getInput() != null && !fieldItemArray[i].getInput().equals("")) {
                if (!"".equals(fieldItemArray[i].getInput().trim()) && (Integer.parseInt(fieldItemArray[i].getInput().trim()) == 2003 ||
                        Integer.parseInt(fieldItemArray[i].getInput().trim()) == 2002 ||
                        Integer.parseInt(fieldItemArray[i].getInput().trim()) == 2001 ||
                        Integer.parseInt(fieldItemArray[i].getInput().trim()) == 2004 ||
                        Integer.parseInt(fieldItemArray[i].getInput().trim()) == 6001 ||
                        Integer.parseInt(fieldItemArray[i].getInput().trim()) == 6002 ||
                        Integer.parseInt(fieldItemArray[i].getInput().trim()) == 6101 ||
                        Integer.parseInt(fieldItemArray[i].getInput().trim()) == 6102 ||
                        Integer.parseInt(fieldItemArray[i].getInput().trim()) == 4002 ||
                        Integer.parseInt(fieldItemArray[i].getInput().trim()) == 4001 ||
                        Integer.parseInt(fieldItemArray[i].getInput().trim()) == 4101 ||
                        Integer.parseInt(fieldItemArray[i].getInput().trim()) == 4102 ||
                        Integer.parseInt(fieldItemArray[i].getInput().trim()) == 5101 ||
                        Integer.parseInt(fieldItemArray[i].getInput().trim()) == 5102 ||
                        Integer.parseInt(fieldItemArray[i].getInput().trim()) == 5001 ||
                        Integer.parseInt(fieldItemArray[i].getInput().trim()) == 5002 ||
                        Integer.parseInt(fieldItemArray[i].getInput().trim()) == 501 ||
                        Integer.parseInt(fieldItemArray[i].getInput().trim()) == 502 ||
                        Integer.parseInt(fieldItemArray[i].getInput().trim()) == 503 ||
                        Integer.parseInt(fieldItemArray[i].getInput().trim()) == 511 ||
                        Integer.parseInt(fieldItemArray[i].getInput().trim()) == 512 ||
                        Integer.parseInt(fieldItemArray[i].getInput().trim()) == 513 ||
                        Integer.parseInt(fieldItemArray[i].getInput().trim()) == 521 ||
                        Integer.parseInt(fieldItemArray[i].getInput().trim()) == 522 ||
                        Integer.parseInt(fieldItemArray[i].getInput().trim()) == 523 ||
                        Integer.parseInt(fieldItemArray[i].getInput().trim()) == 531 ||
                        Integer.parseInt(fieldItemArray[i].getInput().trim()) == 532 ||
                        Integer.parseInt(fieldItemArray[i].getInput().trim()) == 533 ||
                        Integer.parseInt(fieldItemArray[i].getInput().trim()) == 102 ||
                        Integer.parseInt(fieldItemArray[i].getInput().trim()) == 7001 ||
                        Integer.parseInt(fieldItemArray[i].getInput().trim()) == 514)) {
                    flag = false;
                    break;
                }
            }
        }
        if (flag) {
            boolean isSing = false;

            //获取屏幕宽度
            Resources resources = this.getResources();
            DisplayMetrics dm = resources.getDisplayMetrics();
            int screenWidth = dm.widthPixels;
            for (int i = 0; i < fieldItemArray.length; i++) {
                if (null != fieldItemArray[i] && null != fieldItemArray[i].getValue()) {
                    int allWidth = DensityUtil.sp2px(context, 20) * fieldItemArray[i].getValue().length();
                    int cellWidth = screenWidth * fieldItemArray[i].getPercent() / 100;
                    isSing = allWidth > ((cellWidth * 2) - (DensityUtil.dip2px(context, 5) * 2));
                }
            }

            for (int i = 0; i < fieldItemArray.length; i++) {
                // 如果大于30个字符就用字符的长度除以30在乘以height取整 在加一
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, (isSing) ? LinearLayout.LayoutParams.MATCH_PARENT : height, fieldItemArray[i].getPercent());
                params.gravity = Gravity.CENTER_VERTICAL;
                lineView.setGravity(Gravity.CENTER_VERTICAL);
                lineView.addView(initOneCell(fieldItemArray[i], false), params);            //初始化每一个表单项里的小name value
                viewHight.put(fieldItemArray[i].getKey(), Utils.getViewHeight(this));
            }


        } else {
            //意见类 高度设置成matchparent
            for (int i = 0; i < fieldItemArray.length; i++) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, fieldItemArray[i].getPercent());   // 宽 0 利用weight 控制， weight 利用表单占比
                LinearLayout.LayoutParams params_round = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);   // 宽 0 利用weight 控制， weight 利用表单占比
                params.gravity = Gravity.CENTER_VERTICAL;
                lineView.setGravity(Gravity.CENTER_VERTICAL);
                LinearLayout linearLayout = initOneCell(fieldItemArray[i], true);
                viewHight.put(fieldItemArray[i].getKey(), Utils.getViewHeight(this));
//                linearLayout.setPadding(0, height / 5, 0, height / 5);
                LinearLayout linearlayoutround = new LinearLayout(context);
                linearlayoutround.addView(linearLayout, params_round);
                linearlayoutround.setBackgroundColor(SysConvertUtil.formattingH(fieldItemArray[i].getBackColor()));
                if(fieldItemArray[i].getMode() != null && fieldItemArray[i].getMode().equals("100")){ //新增 mode为100的时候为隐藏字段 不做任何处理
                    linearlayoutround.setVisibility(INVISIBLE);
                }
                if (isWaterSecurity == 1) {
                    if (null != linearlayoutround && null != linearlayoutround.getBackground())
                        linearlayoutround.getBackground().setAlpha((int) (0.5 * 255));
                }

                lineView.addView(linearlayoutround, params);            //初始化每一个表单项里的小name value
//                }
            }

        }
        /**
         * 加上后面的折叠箭头
         */
        if (isSplitRegion && SplitAction == 1) {
            im_down = new ImageView(context);
            im_down.setImageResource(R.drawable.btn_angle_up_circle);
            //默认是外层的backcolor颜色
            if (tabStyle != 1)
                im_down.setBackgroundColor(SysConvertUtil.formattingH(entity.getBackColor()));
            else
                im_down.setBackgroundColor(context.getResources().getColor(R.color.transparent));
            lineView.setTag(R.id.tag_second, im_down);
            LinearLayout.LayoutParams param_down = new LinearLayout.LayoutParams(height, height); //创建 Linearlayout 布局参数
            LinearLayout.LayoutParams param_image = new LinearLayout.LayoutParams(height, height - 3); //创建 Linearlayout 布局参数
            im_down.setLayoutParams(param_image);
            param_down.gravity = Gravity.CENTER_VERTICAL;
            if (fieldItemArray[indexCount].getBackColor() != 0 && fieldItemArray[indexCount].getBackColor() != -1) {//如果里面内容的背景色不为空并且部位-1  那么下拉箭头的颜色的是里面backcolor的颜色
                if (tabStyle != 1)
                    im_down.setBackgroundColor(SysConvertUtil.formattingH(fieldItemArray[indexCount].getBackColor()));
                else
                    im_down.setBackgroundColor(context.getResources().getColor(R.color.transparent));
            }
            LinearLayout ll_imageview = new LinearLayout(context);
            ll_imageview.setOrientation(LinearLayout.VERTICAL);
            TextView tv_line = new TextView(context);
            tv_line.setBackgroundColor(getResources().getColor(R.color.imageline));
            LinearLayout.LayoutParams param_line = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 3); //创建 Linearlayout 布局参数
            tv_line.setLayoutParams(param_line);
            ll_imageview.addView(im_down);
            ll_imageview.addView(tv_line);
            lineView.addView(ll_imageview, param_down);
        }
    }

    /**
     * 横向滑动子表单
     */
    private void ScrollLinear(InfoRegion info, LinearLayout lineView) {

        //获取屏幕宽度
        Resources resources = this.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        int cellwidth = dm.widthPixels / 4;
        int headCellWidth = cellwidth;
        /**
         * 初始化横向滑动控件  把linearlayout放到horizontalScrollView中
         */
        HorizontalScrollView hs = new HorizontalScrollView(context);
        LinearLayout.LayoutParams hsparamsScroll = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1); //创建 Linearlayout 布局参数
        hsparamsScroll.gravity = Gravity.CENTER_VERTICAL;
        hs.setLayoutParams(hsparamsScroll);


        LinearLayout linearScroll = new LinearLayout(context);
        LinearLayout.LayoutParams linparamsScroll = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1); //创建 Linearlayout 布局参数
//        linparamsScroll.gravity = Gravity.CENTER_VERTICAL;
        linearScroll.setOrientation(LinearLayout.VERTICAL);//滑动的linearlayout外边边框
        linearScroll.setLayoutParams(linparamsScroll);
//        linearScroll.setGravity(Gravity.CENTER_VERTICAL);


        LinearLayout linearHead = new LinearLayout(context);
        LinearLayout.LayoutParams linearHeadparmas = new LinearLayout.LayoutParams(headCellWidth * info.getScrollFixColCount(), LinearLayout.LayoutParams.WRAP_CONTENT); //创建 Linearlayout 布局参数,根据固定列数的不同设置不同的宽度，防止前面的列被缩小
        linearHead.setOrientation(LinearLayout.VERTICAL);//固定的那部分linearlayout
        linearHead.setLayoutParams(linearHeadparmas);


        String RegionID = info.getRegionID();
        //遍历所有的参数 找到最长的那个  然后宽度按照那个设置 ，如果得到的都没有超过屏幕四分之一那就按照默认的也就是屏幕四分之一设置每个格的宽度
        int n = 0;
        for (int i = 0; i < paramaters.length; i++) {
            if (RegionID.equals(paramaters[i].getParentRegionID()) && paramaters[i].getScrollFlag() == 1) {
                for (int j = 0; j < paramaters[i].getFieldItems().length; j++) {
                    int valueSize = paramaters[i].getFieldItems()[j].getValue().length();
                    if (valueSize > n) {
                        n = valueSize;
                    }
                }
            }
        }
        //如果通过字体大小个数算出的宽度大一屏幕四分之一则按照字体算出的大小设置控件的宽度
        if ((DensityUtil.sp2px(context, 13) * n) > cellwidth) {
            cellwidth = DensityUtil.sp2px(context, 13) * n;
        }
        boolean flagFrist = false;
        for (int i = 0; i < paramaters.length; i++) {
            if (RegionID.equals(paramaters[i].getParentRegionID()) && paramaters[i].getScrollFlag() == 1) {
                LinearLayout lineaempty = new LinearLayout(context);//为了点击折叠 没办法充数的
                LinearLayout.LayoutParams linearemptyparmas = new LinearLayout.LayoutParams(0, 0); //创建 Linearlayout 布局参数 宽高都为0  不占地方
                if (flagFrist)
                    this.addView(lineaempty, linearemptyparmas);
                flagFrist = true;
                int ScroFixColCount = paramaters[i].getScrollFixColCount();
                LinearLayout linearcellscroll = new LinearLayout(context);//滑动里面的每一行
                linearcellscroll.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout linearcellhead = new LinearLayout(context);//固定的那里面的每一行
                linearcellhead.setOrientation(LinearLayout.HORIZONTAL);
                for (int j = 0; j < paramaters[i].getFieldItems().length; j++) {
                    if (j >= ScroFixColCount) {//把每个单元加入横向滑动中
                        LinearLayout.LayoutParams linearcellscrollparmas = new LinearLayout.LayoutParams(cellwidth, height); //创建 Linearlayout 布局参数
                        linearcellscroll.addView(initOneCell(paramaters[i].getFieldItems()[j], false), linearcellscrollparmas);
                        viewHight.put(paramaters[i].getFieldItems()[j].getKey(), Utils.getViewHeight(this));
                    } else {
                        LinearLayout.LayoutParams linearcellheadparmas = new LinearLayout.LayoutParams(headCellWidth, height); //创建 Linearlayout 布局参数
                        linearcellhead.addView(initOneCell(paramaters[i].getFieldItems()[j], false), linearcellheadparmas);
                        viewHight.put(paramaters[i].getFieldItems()[j].getKey(), Utils.getViewHeight(this));
                    }
                }
                //给头部linearlayout设置边框  给滑动部分linearlayout设置边框
                linearcellhead.setBackgroundResource(R.drawable.corners_bg_grey_press_stroke);
                linearcellscroll.setBackgroundResource(R.drawable.corners_bg_grey_press_stroke);
                //把横向和头部的linearlayout设置进大框的linearlayout内
                linearScroll.addView(linearcellscroll);
                linearHead.addView(linearcellhead);
            }
        }
        //循环结束后添加进linearlayout中
        hs.addView(linearScroll);
        lineView.addView(linearHead);
        lineView.addView(hs);
    }


    private LinearLayout initOneCell(final FieldItem item, boolean isOpintion) {

        if (item.getMode() != null) {
            int mode = 0;
            if (!"".equals(item.getMode().trim())) {
                mode = Integer.parseInt(item.getMode().trim());
            }
            switch (mode) {
                case 19:
                    // 只读意见的显示和响应
                    if (item.opintions != null && item.opintions.size() > 0) {
                        //意见字段的显示
                        ModeOpinion19 mModeOpinion19 = new ModeOpinion19(context);
                        return mModeOpinion19.modeOpinionLayout(VlineVisible, item, mInflater, list_tvsize, com_workflow_mobileconfig_IM_enabled);

                    }
                    break;
                case 10:
                    // 只读意见的显示和响应
                    int input = 0;
                    if (item.getInput() != null && !"".equals(item.getInput().trim())) {
                        input = Integer.parseInt(item.getInput().trim());
                    }
                    switch (input) {
                        case 2001:
                        case 2002:
                        case 2003:
                        case 2004:
                            // 只读意见的显示和响应
                            //意见字段的显示
                            ModeOpinion19 mModeOpinion19 = new ModeOpinion19(context);
                            return mModeOpinion19.modeOpinionLayout(VlineVisible, item, mInflater, list_tvsize, com_workflow_mobileconfig_IM_enabled);
                        case 5101:
                        case 5102:
                        case 4002:
                        case 4001:
                        case 4101:
                        case 4102:
                        case 5001:
                        case 5002:
                            AudioSelect4002 mAudioSelect4002 = new AudioSelect4002(context);
                            formMap.put(item.getFieldId(), mAudioSelect4002);
                            linerLayoutMap.put(item.getKey(), mAudioSelect4002);
                            return mAudioSelect4002.audioVideo(VlineVisible, item, mInflater, list_tvsize, new CellOnclickLisener4002_4101(item), com_workflow_mobileconfig_IM_enabled, comeShare, mHTSaveFormExtensionFiles, mIntentPhone,tabStyle);

                        case 6001:
                        case 6002:
                        case 6101:
                        case 6102:
                            SelectPhoto6001_6002_6101_6102 mSelectPhoto6001_6002_6101_6102 = new SelectPhoto6001_6002_6101_6102(context);
                            formMap.put(item.getFieldId(), mSelectPhoto6001_6002_6101_6102);
                            linerLayoutMap.put(item.getKey(), mSelectPhoto6001_6002_6101_6102);
                            return mSelectPhoto6001_6002_6101_6102.Photo6102(VlineVisible, item, mInflater, list_tvsize, new CellOnclickLisener6001_6002_6101_6102(item), com_workflow_mobileconfig_IM_enabled, comeShare, mHTSaveFormExtensionFiles, mIntentPhone,tabStyle);
                    }
                    break;
            }
        }


        // //////////////////////////////////////////////////////////////////////////
        if (null == item.getValue()) {
            item.setValue("");
        }
        final String value = item.getBeforeValueString() + AccssFormKey.findKeyToByValue(null, item.getKey(), item.getValue().replace("\\\\r\\\\n", "\r\n"))      //得到value
                + item.getEndValueString();

        fieldMap.put(item.getKey(), item);

        // 可编辑字段处理
        if (item.getMode() != null && item.getInput() != null && item.getMode().equals("1") && !item.getInput().equals("")) {//mode为1 进入可编辑项 大循环

            // 是否是必填字段，
            // 必填字段
            EditFieldList mustFieldList = EditFieldList.getInstance();                //每一个可编辑项都会返回唯一的必填项集合
            int input = 0;
            if (!"".equals(item.getInput().trim())) {
                input = Integer.parseInt(item.getInput().trim());
            }
            if (item.isMustInput()) {
                EditField mustInput = new EditField();                                //如果该项是必填项，创建必填项 Edit
                mustInput.setKey(item.getKey());                                    //拿到key value 名字 放到 必填项列表
                mustInput.setValue(item.getValue());
                mustInput.setInput(item.getInput());
//                mustInput.setNameDesc(item.getBeforeNameString() + item.getName() + item.getEndNameString());
                mustInput.setNameDesc(item.getName());
                String name = ComponentInit.getInstance().getOAUserName();                                  //单击布局拿到当前用户
                switch (input) {
                    case 2003:
                    case 2002:
                        break;
                    default:
                        mustInput.tempValue = item.getValue().contains(name) ? ("" + item.getValue()) : "";
                        break;
                }
//
                mustFieldList.getList().add(mustInput);

                //特殊处理，如果是1011类型的必填字段，则需要判断之前的意见或签名是否包含当然人的
                if (!"".equals(item.getInput().trim()) && Integer.parseInt(item.getInput().trim()) == 2003) {

                    boolean hasCurrentUserOption = false;

                    if (item.opintions != null && item.opintions.size() > 0) {
                        for (int i = 0; i < item.opintions.size(); i++) {
                            if (!TextUtils.isEmpty(item.opintions.get(i).UserID) && item.opintions.get(i).UserID.equalsIgnoreCase(ComponentInit.getInstance()
                                    .getInstance().getEMPUserID())) {
                                hasCurrentUserOption = true;
                                break;
                            }
                        }
                    }

                    if (!hasCurrentUserOption)
                        mustInput.setValue("");
                }

            }

            switch (input) {
                case 2003:
                    EditableOpinionSig2003 mEditableOpinionSig2003 = new EditableOpinionSig2003(context);
                    linerLayoutMap.put(item.getKey(), mEditableOpinionSig2003);
                    return mEditableOpinionSig2003.editableOpinionSig(VlineVisible, item, mInflater, list_tvsize, list_rbsize, EditFileds, mYiJianOnclickLisener, com_workflow_mobileconfig_IM_enabled, mDocResultInfo);

                case 2001:
                    // 可编辑字段处理,意见
                    FormOpinion2001 mFormOpinion2001 = new FormOpinion2001(context);
                    linerLayoutMap.put(item.getKey(), mFormOpinion2001);
                    return mFormOpinion2001.Opinion2001(VlineVisible, item, mInflater, list_tvsize, mCellOnclickLisener, com_workflow_mobileconfig_IM_enabled);

                case 2002:
                    // 处理可编辑的意见字段
                    EditableOpinion2002 mEditableOpinion2002 = new EditableOpinion2002(context);
                    linerLayoutMap.put(item.getKey(), mEditableOpinion2002);
                    return mEditableOpinion2002.editableOpinion(VlineVisible, item, mInflater, list_tvsize, EditFileds, mDocResultInfo,com_workflow_mobileconfig_IM_enabled,com_workflow_mobileconfig_opinion_style);

                case 2004:

                    if (item.opintions != null && item.opintions.size() > 0) {
                        opinionText = item.opintions.get(0).opinionText;
                        FormOpinion2004 mFormOpinion2004 = new FormOpinion2004(context);
                        linerLayoutMap.put(item.getKey(), mFormOpinion2004);
                        return mFormOpinion2004.Opinion2004(VlineVisible, item, mInflater, list_tvsize, new CellOnclickLisener2004(opinionText), com_workflow_mobileconfig_IM_enabled);
                    } else {
                        opinionText = "";
                        FormOpinion2004 mFormOpinion2004 = new FormOpinion2004(context);
                        linerLayoutMap.put(item.getKey(), mFormOpinion2004);
                        return mFormOpinion2004.Opinion2004(VlineVisible, item, mInflater, list_tvsize, new CellOnclickLisener2004(opinionText), com_workflow_mobileconfig_IM_enabled);
                    }

                case 6001:
                case 6002:
                case 6101:
                case 6102:
                    SelectPhoto6001_6002_6101_6102 mSelectPhoto6001_6002_6101_6102 = new SelectPhoto6001_6002_6101_6102(context);
                    formMap.put(item.getFieldId(), mSelectPhoto6001_6002_6101_6102);
                    linerLayoutMap.put(item.getKey(), mSelectPhoto6001_6002_6101_6102);
                    return mSelectPhoto6001_6002_6101_6102.Photo6102(VlineVisible, item, mInflater, list_tvsize, new CellOnclickLisener6001_6002_6101_6102(item), com_workflow_mobileconfig_IM_enabled, comeShare, mHTSaveFormExtensionFiles, mIntentPhone,tabStyle);

                case 412://获取Name支持输入(下拉框选择模式)
                case 403://获取Value
                case 402://获取Name
                case 401://获取ID
                    SpringLayout412_403_402_401 mSpringLayout412_403_402_401 = new SpringLayout412_403_402_401(context);
                    mSpringLayout412_403_402_401.setOptions(options, values, ids);
                    linerLayoutMap.put(item.getKey(), mSpringLayout412_403_402_401);
                    return mSpringLayout412_403_402_401.springLayout(VlineVisible, item, mInflater, list_tvsize, mComboBoxList, list_comboboxsize, EditFileds, mScrollViewListener, tabStyle, mDocResultInfo);
                case 300:
                case 301:
                case 302:
                case 303:
                case 304:
                    // ***********************日期选择***************************
                    CheckDate300_301 mCheckDate300_301 = new CheckDate300_301(context);
                    linerLayoutMap.put(item.getKey(), mCheckDate300_301);
                    return mCheckDate300_301.getCheckDateLayout(VlineVisible, item, mInflater, list_tvsize, EditFileds, mPopBirthHelper, currentEditTextView, tabStyle, mDocResultInfo);
                case 3011://读者多选
                case 3012://作者多选
                case 611:
                case 612://人员多选
                case 613:
                    PeopleCheck611_612_613 mPeopleCheck611_612_613 = new PeopleCheck611_612_613(context);
                    linerLayoutMap.put(item.getKey(), mPeopleCheck611_612_613);
                    return mPeopleCheck611_612_613.peopleChckLayout(VlineVisible, item, mInflater, currentEditTextView, this, tabStyle);
                case 911:
                case 912:
                    //912 表示选择部门返回ID  911 是返回NAME
                    DepartmentCheck911_912 mDepartmentCheck911_912 = new DepartmentCheck911_912(context);
                    linerLayoutMap.put(item.getKey(), mDepartmentCheck911_912);
                    return mDepartmentCheck911_912.departmentLayout(VlineVisible, item, mInflater, currentEditTextView, this, tabStyle, EditFileds);
                case 3001: //读者单选
                case 3002://作者单选
                case 601:
                case 602:
                case 603: //人员单选
                    RadioPeople601_02_03 mRadioPeople601_02_03 = new RadioPeople601_02_03(context);
                    linerLayoutMap.put(item.getKey(), mRadioPeople601_02_03);
                    return mRadioPeople601_02_03.peopleChckLayout(VlineVisible, item, mInflater, currentEditTextView, this, tabStyle);
                case 901:
                case 902:
                    RadioDepartment901_902 mRadioDepartment901_902 = new RadioDepartment901_902(context);
                    linerLayoutMap.put(item.getKey(), mRadioDepartment901_902);
                    return mRadioDepartment901_902.departmentLayout(VlineVisible, item, mInflater, currentEditTextView, this, tabStyle);
                case 501://单选框结果取ID
                case 502://单选框结果取Name
                case 503://单选框结果取Value
                case 511://复选框结果取ID
                case 512://复选框结果取Name
                case 513://复选框结果取Value
                    RadioButton501_02_03_11_513 mRadioButton501_02_03_11_513 = new RadioButton501_02_03_11_513(context);
                    linerLayoutMap.put(item.getKey(), mRadioButton501_02_03_11_513);
                    return mRadioButton501_02_03_11_513.radioButtonLayout(VlineVisible, item, mInflater, list_tvsize, EditFileds, tabStyle, mDocResultInfo);
                case 514: //字典
                    Zidian514 zidian = new Zidian514(context);
                    linerLayoutMap.put(item.getKey(), zidian);
                    return zidian.radioButtonLayout(VlineVisible, item, mInflater, EditFileds, mDocResultInfo);
                case 521:
                case 522:
                case 523:
                case 531:
                case 532:
                case 533:
                    RadioButton521_22_23_31_32_33 mRadioButton521_22_23_31_32_33 = new RadioButton521_22_23_31_32_33(context);
                    linerLayoutMap.put(item.getKey(), mRadioButton521_22_23_31_32_33);
                    return mRadioButton521_22_23_31_32_33.radioButtonLayout(VlineVisible, item, mInflater, list_tvsize, EditFileds, mDocResultInfo);
                case 8001:
                case 8002:
                    IntervalControl8001_002 mIntervalControl8001_002 = new IntervalControl8001_002(context);
                    linerLayoutMap.put(item.getKey(), mIntervalControl8001_002);
                    return mIntervalControl8001_002.radioButtonLayout(VlineVisible, item, mInflater, EditFileds, mDocResultInfo);
                case 7001:
                    SelectPoisition7001 selectPoisition7001 = new SelectPoisition7001(context, "", item.getValue(), EditFileds, VlineVisible);
                    linerLayoutMap.put(item.getKey(), selectPoisition7001);
                    return selectPoisition7001.selectPoisitionLayout(mInflater, item);

                case 4002://可选可录制
                case 4001://仅录制
                case 5101:
                case 5102:
                case 4101://可选可录制
                case 4102://仅录制
                case 5001:
                case 5002:
                    AudioSelect4002 mAudioSelect4002 = new AudioSelect4002(context);
                    formMap.put(item.getFieldId(), mAudioSelect4002);
                    linerLayoutMap.put(item.getKey(), mAudioSelect4002);
                    return mAudioSelect4002.audioVideo(VlineVisible, item, mInflater, list_tvsize, new CellOnclickLisener4002_4101(item), com_workflow_mobileconfig_IM_enabled, comeShare, mHTSaveFormExtensionFiles, mIntentPhone,tabStyle);
                default:// 其他非意见可编辑字段
                    OtherLayout otherLayout = new OtherLayout(context);
                    linerLayoutMap.put(item.getKey(), otherLayout);
                    return otherLayout.otherLayoutMeth(VlineVisible, item, mInflater, list_tvsize, list_etsize, currentEditText, EditFileds, mDocResultInfo, tabStyle);
            }


        }
//        if(TextUtils.equals(item.getMode(),"100")){
//            return new LinearLayout(context);
//        }
        // //////////////其他只读字段的显示
//        LinearLayout layout = null;
//        if (item.isNameRN())
//            layout = (LinearLayout) mInflater.inflate(R.layout.layout_formdetail_cell_vertical_lib, null);
//        else
//            layout = (LinearLayout) mInflater.inflate(R.layout.layout_formdetail_cell_lib, null);
//
//        if (VlineVisible.equalsIgnoreCase("true")) {
//            layout.setBackgroundResource(R.drawable.corners_bg_white_press_stroke_v);
//        } else {
//            layout.setBackgroundResource(R.drawable.corners_bg_grey_press_stroke);
//        }
//
//        TextView tv = (TextView) layout.findViewById(R.id.form_fielditem_text);
//        tv.setGravity(Gravity.CENTER_VERTICAL);
//        list_tvsize.add(tv);
//        /**
//         * add by heyang
//         * date 2016-7-18
//         * if isMustInput is true then change backgroundclolor as "color_mustinputback"
//         */
//        if (item.getAlign().equalsIgnoreCase("center")) {
//            tv.setGravity(Gravity.CENTER);
//        } else if (item.getAlign().equalsIgnoreCase("left")) {
//            tv.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
//        } else if (item.getAlign().equalsIgnoreCase("right")) {
//            tv.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
//        }
//        String text = "";
//        int nameLength = 0;
//        int splitLength = 0;
//        int nameColor = context.getResources().getColor(R.color.color_ff555555);
//
//        int valueColor = nameColor;
//
//        //2015-09-22添加 for 项目申报字表行背景颜色
//        if (item.getNameFontColor() != 0)
//            nameColor = item.getNameFontColor();
//
//        if (item.getValueFontColor() != 0)
//            valueColor = item.getValueFontColor();
//
//        boolean isNameVisible = item.isNameVisible();
//        if (isNameVisible) {
//            String name = item.getName();
//            String split = item.getSplitString();
//            text += name + split;
//            nameLength = item.getName().length() + split.length();
//            splitLength = split.length();
////            if (item.getNameColor().equalsIgnoreCase("red")) {
////                nameColor = Color.RED;
////            }
//        }
//
//        if (item.isNameRN() && value.length() > 0) {
//            text += "\n";
//        }
//        text += value;
//        final String valueTemp = value;
////        if (item.getValueColor().equalsIgnoreCase("red")) {
////            valueColor = Color.RED;
////        }
//        if (item.getName().contains("电话") || item.getName().contains("手机") || item.getName().contains("联系")) {
//            if (value.length() == 7 || value.length() == 8 || value.length() == 11) {
//                if (isNumeric(value)) {
//                    valueColor = Color.parseColor("#5782ab");
//                    tv.setOnClickListener(new OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Uri uri = Uri.parse("tel:" + valueTemp);
//                            Intent intent = new Intent(Intent.ACTION_DIAL, uri);
//                            // 直接打出去的action
//                            // intent.setAction("android.intent.action.CALL");
//                            context.startActivity(intent);
//                        }
//                    });
//                }
//            }
//        }
////        int NameBackColor = item.getNameBackColor();    //2015-09-22添加 for 项目申报字表行背景颜色
////        int ValueBackColor = item.getValueBackColor();
//        SpannableStringBuilder spanBuilder = new SpannableStringBuilder(text);
//        if (nameLength > 0) {
//            spanBuilder.setSpan(new ForegroundColorSpan(nameColor), 0, nameLength, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
//            //spanBuilder.setSpan(new BackgroundColorSpan(123));
//        }
//        if (value.length() > 0) {
//            spanBuilder.setSpan(new ForegroundColorSpan(valueColor), nameLength, text.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
//        }
//        tv.setText(spanBuilder);
//        if (item.getBackColor() != 0 && item.getBackColor() != -1) {
//
//            if (layout.findViewById(R.id.form_fielditem_option).getVisibility() == View.GONE && layout.findViewById(R.id.form_fielditem_editimage).getVisibility() == View.GONE) {
//                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
//                params.setMargins(1, 0, 1, 1);
//                params.gravity = Gravity.CENTER_VERTICAL;
//                tv.setLayoutParams(params);
////                tv.setGravity(Gravity.CENTER);
//            }
//            if (!isOpintion)
//                tv.setBackgroundColor(SysConvertUtil.formattingH(item.getBackColor())); //2015-09-22添加 for 项目申报字表字段背景颜色
//            //layout.setBackgroundColor(item.getBackColor());
//        }
//        //距离左侧1dp
//        int left = DensityUtil.dip2px(context, 1);
//        tv.setPadding(left, 5, 20, 5);
        NotEditLayout mNotEditLayout = new NotEditLayout(context);
        linerLayoutMap.put(item.getKey(), mNotEditLayout);
        LinearLayout linearLayout = mNotEditLayout.setNotEditLayout(item, mInflater, VlineVisible, tabStyle, value, EditFileds, list_tvsize, mDocResultInfo, isWaterSecurity);
        if(TextUtils.equals(item.getMode(),"100")){ //新增 如果mode为100 则进行隐藏
            linearLayout.setVisibility(INVISIBLE);
        }
        mNotEditLayout.getLineView().setBackgroundResource(R.drawable.corners_bg_grey_press_stroke);
        return linearLayout;
//        return layout;
    }

    public static boolean isNumeric(String str) {
        for (int i = str.length(); --i >= 0; ) {
            int chr = str.charAt(i);
            if (chr < 48 || chr > 57)
                return false;
        }
        return true;
    }


    public void swichType(int input, FieldItem item) {

        switch (input) {
            case 2003:
                if (linerLayoutMap.get(item.getKey()) instanceof EditableOpinionSig2003) {
                    EditableOpinionSig2003 mEditableOpinionSig2003 = (EditableOpinionSig2003) (linerLayoutMap.get(item.getKey()));
                    mEditableOpinionSig2003.setReView2003(item);
                }
                break;

            case 2001:// 可编辑字段处理,意见
                if (linerLayoutMap.get(item.getKey()) instanceof FormOpinion2001) {
                    FormOpinion2001 mFormOpinion2001 = (FormOpinion2001) (linerLayoutMap.get(item.getKey()));
                    mFormOpinion2001.setReView2001(item);
                }
                break;


            case 2002:
                // 1，**********************处理可编辑的意见字段******************************
                if (linerLayoutMap.get(item.getKey()) instanceof EditableOpinion2002) {
                    EditableOpinion2002 mEditableOpinion2002 = (EditableOpinion2002) (linerLayoutMap.get(item.getKey()));
                    mEditableOpinion2002.setReView2002(item);
                }
                break;

            case 2004:
                if (linerLayoutMap.get(item.getKey()) instanceof FormOpinion2004) {
                    FormOpinion2004 mFormOpinion2004 = (FormOpinion2004) (linerLayoutMap.get(item.getKey()));
                    mFormOpinion2004.setReView2004(item);
                }
                break;
            case 5101:
            case 5102:
            case 4002:
            case 4001:
            case 4101:
            case 4102:
            case 5001:
            case 5002:
                if(linerLayoutMap.get(item.getKey()) instanceof AudioSelect4002){
                    AudioSelect4002 mAudioSelect4002 = (AudioSelect4002) linerLayoutMap.get(item.getKey());
                    mAudioSelect4002.setReViewAudioVideo(item);
                }
                break;
            case 6001:
            case 6002:
            case 6101:
            case 6102:
                if (linerLayoutMap.get(item.getKey()) instanceof SelectPhoto6001_6002_6101_6102) {
                    SelectPhoto6001_6002_6101_6102 mSelectPhoto6001_6002_6101_6102 = (SelectPhoto6001_6002_6101_6102) (linerLayoutMap.get(item.getKey()));
                    mSelectPhoto6001_6002_6101_6102.setReView6001_002_101_102(item);
                }
                break;
            case 412://获取Name支持输入(下拉框选择模式)
            case 403://获取Value
            case 402://获取Name
            case 401://获取ID
                if (linerLayoutMap.get(item.getKey()) instanceof SpringLayout412_403_402_401) {
                    SpringLayout412_403_402_401 mSpringLayout412_403_402_401 = (SpringLayout412_403_402_401) (linerLayoutMap.get(item.getKey()));
                    if (tabStyle == 1) {
                        mSpringLayout412_403_402_401.setReView401_02_03_12_net(item);
                    } else {
                        mSpringLayout412_403_402_401.setReView412_03_02_01(item);
                    }
                }
                break;
            case 300:
            case 301:
                // ***********************日期选择***************************
                if (linerLayoutMap.get(item.getKey()) instanceof CheckDate300_301) {
                    CheckDate300_301 mCheckDate300_301 = (CheckDate300_301) (linerLayoutMap.get(item.getKey()));
                    mCheckDate300_301.setReView300_301(item);
                }
                break;
            case 3011://读者多选
            case 3012://作者多选
            case 611:
            case 612://人员多选
            case 613:
                if (linerLayoutMap.get(item.getKey()) instanceof PeopleCheck611_612_613) {
                    PeopleCheck611_612_613 mPeopleCheck611_612_613 = (PeopleCheck611_612_613) (linerLayoutMap.get(item.getKey()));
                    mPeopleCheck611_612_613.setReView611_12_13(item);
                }
                break;
            case 911:
            case 912:
                //912 表示选择部门返回ID  911 是返回NAME
                if (linerLayoutMap.get(item.getKey()) instanceof DepartmentCheck911_912) {
                    DepartmentCheck911_912 mDepartmentCheck911_912 = (DepartmentCheck911_912) (linerLayoutMap.get(item.getKey()));
                    mDepartmentCheck911_912.setReView911_912(item);
                }
                break;
            case 3001: //读者单选
            case 3002://作者单选
            case 601:
            case 602:
            case 603: //人员单选
                if (linerLayoutMap.get(item.getKey()) instanceof RadioPeople601_02_03) {
                    RadioPeople601_02_03 mRadioPeople601_02_03 = (RadioPeople601_02_03) (linerLayoutMap.get(item.getKey()));
                    mRadioPeople601_02_03.setReView601_02_03(item);
                }
                break;
            case 901:
            case 902:
                if (linerLayoutMap.get(item.getKey()) instanceof RadioDepartment901_902) {
                    RadioDepartment901_902 mRadioDepartment901_902 = (RadioDepartment901_902) (linerLayoutMap.get(item.getKey()));
                    mRadioDepartment901_902.setReView901_02(item);
                }
                break;
            case 501://单选框结果取ID
            case 502://单选框结果取Name
            case 503://单选框结果取Value
            case 511://复选框结果取ID
            case 512://复选框结果取Name
            case 513://复选框结果取Value
                if (linerLayoutMap.get(item.getKey()) instanceof RadioButton501_02_03_11_513) {
                    RadioButton501_02_03_11_513 mRadioButton501_02_03_11_513 = (RadioButton501_02_03_11_513) (linerLayoutMap.get(item.getKey()));
                    if (tabStyle == 1) {
                        mRadioButton501_02_03_11_513.setReView501_02_03_11_513_net(item);
                    } else {
                        mRadioButton501_02_03_11_513.setReView501_02_03_11_513(item);
                    }
                }
                break;
            case 514: //字典
                if (linerLayoutMap.get(item.getKey()) instanceof Zidian514) {
                    Zidian514 zidian = (Zidian514) (linerLayoutMap.get(item.getKey()));
                    zidian.setReView514(item);
                }
                break;
            case 521:
            case 522:
            case 523:
            case 531:
            case 532:
            case 533:
                if (linerLayoutMap.get(item.getKey()) instanceof RadioButton521_22_23_31_32_33) {
                    RadioButton521_22_23_31_32_33 mRadioButton521_22_23_31_32_33 = (RadioButton521_22_23_31_32_33) (linerLayoutMap.get(item.getKey()));
                    mRadioButton521_22_23_31_32_33.setReView521_22_23_31_32_33(item);
                }
                break;
            case 8001:
            case 8002:
                if (linerLayoutMap.get(item.getKey()) instanceof IntervalControl8001_002) {
                    IntervalControl8001_002 mIntervalControl8001_002 = (IntervalControl8001_002) (linerLayoutMap.get(item.getKey()));
                    mIntervalControl8001_002.setReView8001_8002(item);
                }
                break;
            default:// 4，**********************其他非意见可编辑字段**********************

                if (linerLayoutMap.get(item.getKey()) instanceof OtherLayout) {
                    OtherLayout otherLayout = (OtherLayout) (linerLayoutMap.get(item.getKey()));
                    otherLayout.setReViewOthers(item);
                } else if (linerLayoutMap.get(item.getKey()) instanceof NotEditLayout) {
                    NotEditLayout mNotEditLayout = (NotEditLayout) (linerLayoutMap.get(item.getKey()));
                    mNotEditLayout.setReView(item);
                }
                break;


        }

    }

    @Override
    public void callHandle_doAction_hasAuthor(ArrayList<AuthorInfo> AuthorInfoTemp, TextView currentEditTextView) {
        this.currentEditTextView = currentEditTextView;
        handle_doAction_hasAuthor(AuthorInfoTemp);
    }

    public void setLocationTitle(String locationTitle) {
        ((SelectPoisition7001) linerLayoutMap.get("schPlace")).setLocationTitle(locationTitle);
    }

    // /人员选择确定
    private void handle_doAction_hasAuthor(ArrayList<AuthorInfo> AuthorInfoTemp) {
        // 界面选择了人
        StringBuffer authorId = new StringBuffer();
        StringBuffer authorName = new StringBuffer();
        if (AuthorInfoTemp != null) {
            for (int i = 0; i < AuthorInfoTemp.size(); i++) {
                AuthorInfo selectUser = AuthorInfoTemp.get(i);
                if (authorId.length() > 0) {
                    authorId.append("|");
                    authorName.append("|");
                }
                authorId.append(selectUser.getUserId());
                authorName.append(selectUser.getUserName());
            }
        }
        // 将值保存到编辑的字段中，并判断是否需要更新必填字段
        // 1,将已经编辑的字段和值放入到办理是需要回发的EditField[] 中
        String strValue = authorId.toString(); // U_id1;U_id2
        String strName = authorName.toString(); // 姓名1;姓名2
        EditField edit = (EditField) currentEditTextView.getTag();
        String input = edit.getInput();
        if (!input.equals("")) {
            int inputInteger = Integer.parseInt(input);
            switch (inputInteger) {
                case 613:
                case 912:
                case 601:
                case 902:
                case 603:
                case 3001:
                case 3011:
                case 3002:
                case 3012:
                case 611:
                    edit.setValue(strValue);
                    break;
                case 911:
                case 602:
                case 901:
                case 612:
                    edit.setValue(strName);
                    break;
            }

        }
        if (EditFileds != null && EditFileds.size() == 0) {
            EditFileds.clear();
            EditFileds.add(edit);
        } else {
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
        if (mDocResultInfo != null) {
            mDocResultInfo.getResult().cleanFields();
            if (mDocResultInfo.getResult() != null)
                mDocResultInfo.getResult().setEditFields(EditFileds);
        }
        // 2,必填字段处理--将输入的意见放入相应必填字段中
        EditFieldList mustFieldList = EditFieldList.getInstance();
        for (int i = 0; i < mustFieldList.getList().size(); i++) {
            if (mustFieldList
                    .getList()
                    .get(i)
                    .getKey()
                    .equalsIgnoreCase(
                            ((EditField) currentEditTextView
                                    .getTag()).getKey())) {
                mustFieldList.getList().get(i).setValue(strValue);
            }
        }
        currentEditTextView.setText("");
        currentEditTextView.setText(strName);

    }

    public class CellOnclickLisener2004 implements YiJianOnclickLisener {// 设置表格项的监听器
        String textValue = "";

        public CellOnclickLisener2004(String textValue) {
            this.textValue = textValue;
        }

        @Override
        public void onClick(View v) {
            mCellOnclickLisener2004.onClick(v, textValue);

        }

    }

    public class CellOnclickLisener6001_6002_6101_6102 implements YiJianOnclickLisener {// 设置表格项的监听器
        FieldItem item;

        public CellOnclickLisener6001_6002_6101_6102(FieldItem item) {
            this.item = item;
        }

        @Override
        public void onClick(View v) {
            mCellOnclickLisener6001_6002_6101_6102.onClick(v, item);
        }

    }

    public class CellOnclickLisener4002_4101 implements YiJianOnclickLisener {// 设置表格项的监听器
        FieldItem item;

        public CellOnclickLisener4002_4101(FieldItem item) {
            this.item = item;
        }

        @Override
        public void onClick(View v) {
            if(mCellOnclickLisener4002_4101 != null)
                mCellOnclickLisener4002_4101.onClick(v, item);
        }

    }
}