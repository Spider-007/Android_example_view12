package com.htmitech.emportal.ui.detail;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
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
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.htmitech.api.BookInit;
import com.htmitech.combobox.ComboBox;
import com.htmitech.commonx.util.DeviceUtils;
import com.htmitech.datepicker.PopChooseTimeHelper;
import com.htmitech.emportal.HtmitechApplication;
import htmitech.com.componentlibrary.base.MyBaseFragment;
import com.htmitech.emportal.entity.AuthorInfo;
import com.htmitech.emportal.entity.DocResultInfo;
import com.htmitech.emportal.entity.EditField;
import com.htmitech.emportal.entity.EditFieldList;
import com.htmitech.emportal.entity.InfoRegion;
import com.htmitech.emportal.entity.OAConText;
import com.htmitech.emportal.ui.formConfig.CallBackDoAction;
import com.htmitech.emportal.ui.formConfig.CheckDate300_301;
import com.htmitech.emportal.ui.formConfig.DepartmentCheck911_912;
import com.htmitech.emportal.ui.formConfig.EditableOpinion2002;
import com.htmitech.emportal.ui.formConfig.ModeOpinion19;
import com.htmitech.emportal.ui.formConfig.OtherLayout;
import com.htmitech.emportal.ui.formConfig.PeopleCheck611_612_613;
import com.htmitech.emportal.ui.formConfig.RadioButton501_02_03_11_513;
import com.htmitech.emportal.ui.formConfig.RadioDepartment901_902;
import com.htmitech.emportal.ui.formConfig.RadioPeople601_02_03;
import com.htmitech.emportal.ui.formConfig.SpringLayout412_403_402_401;
import com.htmitech.emportal.R;
import com.htmitech.emportal.ui.formConfig.Zidian514;
import com.htmitech.photoselector.model.workflow.FieldItem;
import com.htmitech.photoselector.model.PhotoModel;
import com.htmitech.photoselector.myinterface.CallBackImageSelectImp;
import com.htmitech.pop.SelectPicPopupWindow;
import com.htmitech.proxy.util.FileSizeUtil;
import com.htmitech.unit.DensityUtil;
import com.minxing.client.util.SysConvertUtil;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import htmitech.com.componentlibrary.unit.PreferenceUtils;
import mobilereport.com.chatkit.domain.TextStyle;
import mobilereport.com.chatkit.util.WaterMarkTextUtil;

//import com.htmitech.commonx.base.view.combo.ComboBox;
//import com.htmitech.commonx.base.view.combo.IValueChagedEvent;

/**
 * 表单
 *
 * @author tenggang
 */
@SuppressLint("ValidFragment")
public class FormFragment extends MyBaseFragment implements ScrollViewListener, CallBackDoAction, CallBackImageSelectImp {
    private static final String TAG = "FormFragment";
    private DialogFragment mNewFragment;

    private int pullStyle = 0;
    private static final int PULLDOWN_TOREFRESH = 0;
    private static final int PULLUP_TOLOADMORE = 1;

    private int mPageNum = 1;
    private boolean has_more = false;

    private LayoutInflater mInflater;
    private View mEmptyView;
    private LinearLayout mLinearlayout_formdetail;
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
    ComboBox comboxValue;
    String app_id;
    String[] options;
    String[] values;
    String[] ids;
    private PopChooseTimeHelper mPopBirthHelper = null;

    private SelectPicPopupWindow menuWindow;

    private String imageFilePath;//临时拍照路径
    private Map<String, Object> imageParme = new HashMap<String, Object>();

    public FormFragment() {
        super();
        // TODO Auto-generated constructor stub
    }

    @SuppressLint("ValidFragment")
    public FormFragment(InfoRegion[] paramaters, String app_id) {
        this.app_id = app_id;
        this.paramaters = paramaters;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        list_tvsize = new ArrayList<TextView>();
        list_etsize = new ArrayList<EditText>();
        list_rbsize = new ArrayList<RadioButton>();
        list_cbsize = new ArrayList<CheckBox>();
        list_comboboxsize = new ArrayList<ComboBox>();
        BookInit.getInstance().setmCallBackImageSelectImp(this);
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
            mEmptyView = View.inflate(getActivity(),
                    R.layout.layout_empty_view, null);   //空视图
        }
        mLinearlayout_formdetail = (LinearLayout) findViewById(R.id.linearlayout_formdetail); //整个表单的大框
        if (getActivity() instanceof DetailActivity) {
            if (((DetailActivity) getActivity()).isWaterSecurity == 1) {
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

        initFormDetail();
    }

    @Override
    public void checkImageSelect(ArrayList<htmitech.com.componentlibrary.entity.PhotoModel> mSelectedImage, htmitech.com.componentlibrary.entity.Fielditems item, htmitech.com.componentlibrary.entity.FieldItem workflow_item) {

    }


    public class ChildViews {
        public LinearLayout.LayoutParams parms;
        public LinearLayout layout;
    }

    /**
     * 初始化 表单细节
     * 每一个 FieldItems 为一行， 一行也可能包含多个字段
     */

    private void initFormDetail() {

        //InfoRegion[] regionArray = mDocResultInfo.getResult().getRegionItems();  // 获取所有RegionItems的集合  包含多个表单项
        if (paramaters != null && paramaters.length > 0) {

            /**
             * 对数据拆分额
             */
            for (int i = 0; i < paramaters.length; i++) {       //	历每一个 表单项
                LinearLayout lineView = new LinearLayout(        //创建一个linearLayout 容器 当做行视图来用
                        HtmitechApplication.instance());
                lineView.setFocusable(false);
                lineView.setBackgroundResource(R.drawable.corners_bg_white_press_stroke);    //2015-09-22添加 for 项目申报字表行背景颜色
                //  lineView.setBackgroundColor(paramaters[i].getBackColor());
                /**
                 * V2.0add
                 */
                if (paramaters[i].isSplitRegion() && paramaters[i].getSplitAction() == 0) {//如果是0那么添加一个空白行不做任何操作
                    int height = DeviceUtils.dip2px(HtmitechApplication.getApplication(), 20);
                    LinearLayout.LayoutParams param0 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height, 1); //创建 Linearlayout 布局参数
                    lineView.setBackgroundResource(R.drawable.corners_bg_white_empty_little_stroke);
                    mLinearlayout_formdetail.addView(lineView, param0);
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
                                            mLinearlayout_formdetail.getChildAt(k).setVisibility((mLinearlayout_formdetail.getChildAt(k).getVisibility() == View.GONE) ? View.VISIBLE : View.GONE);
                                            if ((mLinearlayout_formdetail.getChildAt(k).getVisibility() == View.GONE)) {//如果是被关闭了找他下面是否还有子类全部关闭
                                                if (iv_circle != null) {
                                                    iv_circle.setImageResource(R.drawable.btn_angle_up_circle);
                                                }
                                                isHead(paramaters[k], k);

                                            } else {//如果是打开则改变箭头图标
                                                if (iv_circle != null) {
                                                    iv_circle.setImageResource(R.drawable.btn_angle_down_circle);
                                                }
                                                ImageView iv_circle_child = (ImageView) mLinearlayout_formdetail.getChildAt(k).getTag(R.id.tag_second);
                                                if (iv_circle_child != null) {
                                                    iv_circle_child.setImageResource(R.drawable.btn_angle_up_circle);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
//                                else if (nowClickNum != -1 && paramaters[nowClickNum].isSplitRegion() && paramaters[nowClickNum].getSplitAction() == 2) {//如果是2跳转到其它页面
//                                    /**
//                                     * 当子表单需要跳转
//                                     */
//                                }

                        }
                    });
                    if (paramaters[i].getScrollFlag() != 1) {
                        initOneRecord(lineView, paramaters[i]);
                        //初始化一个记录 利用一个容器 和 一个 数据的详细描述
//                        int height = Dp2Px(HtmitechApplication.getApplication(), heightLinear);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1); //创建 Linearlayout 布局参数
                        params.gravity = Gravity.CENTER;
                        lineView.setGravity(Gravity.CENTER);
                        mLinearlayout_formdetail.addView(lineView, params);    //把一个数据实例和一个 视图加入到lineView
                    } else {
                        initOneRecord(lineView, paramaters[i]);
                    }
                }
            }
        }
        //进入界面折叠子表默认折叠
        goneStart();
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
        HorizontalScrollView hs = new HorizontalScrollView(HtmitechApplication.instance());
        LinearLayout.LayoutParams hsparamsScroll = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1); //创建 Linearlayout 布局参数
        hsparamsScroll.gravity = Gravity.CENTER_VERTICAL;
        hs.setLayoutParams(hsparamsScroll);


        LinearLayout linearScroll = new LinearLayout(HtmitechApplication.instance());
        LinearLayout.LayoutParams linparamsScroll = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1); //创建 Linearlayout 布局参数
//        linparamsScroll.gravity = Gravity.CENTER_VERTICAL;
        linearScroll.setOrientation(LinearLayout.VERTICAL);//滑动的linearlayout外边边框
        linearScroll.setLayoutParams(linparamsScroll);
//        linearScroll.setGravity(Gravity.CENTER_VERTICAL);


        LinearLayout linearHead = new LinearLayout(HtmitechApplication.instance());
        LinearLayout.LayoutParams linearHeadparmas = new LinearLayout.LayoutParams(
                headCellWidth * info.getScrollFixColCount(), LinearLayout.LayoutParams.WRAP_CONTENT); //创建 Linearlayout 布局参数,根据固定列数的不同设置不同的宽度，防止前面的列被缩小
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
        if ((DensityUtil.sp2px(HtmitechApplication.instance(), 13) * n) > cellwidth) {
            cellwidth = DensityUtil.sp2px(HtmitechApplication.instance(), 13) * n;
        }
        boolean flagFrist = false;
        for (int i = 0; i < paramaters.length; i++) {
            if (RegionID.equals(paramaters[i].getParentRegionID()) && paramaters[i].getScrollFlag() == 1) {
                LinearLayout lineaempty = new LinearLayout(HtmitechApplication.instance());//为了点击折叠 没办法充数的
                LinearLayout.LayoutParams linearemptyparmas = new LinearLayout.LayoutParams(0, 0); //创建 Linearlayout 布局参数 宽高都为0  不占地方
                if (flagFrist)
                    mLinearlayout_formdetail.addView(lineaempty, linearemptyparmas);
                flagFrist = true;
                int ScroFixColCount = paramaters[i].getScrollFixColCount();
                LinearLayout linearcellscroll = new LinearLayout(HtmitechApplication.instance());//滑动里面的每一行
                linearcellscroll.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout linearcellhead = new LinearLayout(HtmitechApplication.instance());//固定的那里面的每一行
                linearcellhead.setOrientation(LinearLayout.HORIZONTAL);
                for (int j = 0; j < paramaters[i].getFieldItems().length; j++) {

                    if (j >= ScroFixColCount) {//把每个单元加入横向滑动中
                        LinearLayout.LayoutParams linearcellscrollparmas = new LinearLayout.LayoutParams(
                                cellwidth, height); //创建 Linearlayout 布局参数
                        linearcellscroll.addView(initOneCell(paramaters[i].getFieldItems()[j]), linearcellscrollparmas);
                    } else {
                        LinearLayout.LayoutParams linearcellheadparmas = new LinearLayout.LayoutParams(
                                headCellWidth, height); //创建 Linearlayout 布局参数
                        linearcellhead.addView(initOneCell(paramaters[i].getFieldItems()[j]), linearcellheadparmas);
                    }
                }
                linearScroll.addView(linearcellscroll);
                linearHead.addView(linearcellhead);
            }
        }
        //循环结束后添加进linearlayout中
        hs.addView(linearScroll);
        lineView.addView(linearHead);
        lineView.addView(hs);
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

    private void initOneRecord(LinearLayout lineView, InfoRegion entity) {
        FieldItem[] fieldItemArray = entity.getFieldItems();                    //初始化每一个表单项
        String RegionID = entity.getRegionID();
        boolean IsTable = entity.isTable();
        String TableID = entity.getTableID();
        String ParentRgionID = entity.getParentRegionID();
        boolean isSplitRegion = entity.isSplitRegion();
        int SplitAction = entity.getSplitAction();
        int ScrollFlag = entity.getScrollFlag();
        lineView.setTag(R.id.tag_first, RegionID);//给linearlayout设置一个标记
        VlineVisible = entity.getVlineVisible();
        if (fieldItemArray == null || "".equals(fieldItemArray))
            return;

        if (ScrollFlag == 1) {//是横向滑动的
            for (int j = 0; j < paramaters.length; j++) {
                if (RegionID.equals(paramaters[j].getParentRegionID())) {//如果是头 那么先把头显示出来在去执行下面的部分
                    for (int i = 0; i < fieldItemArray.length; i++) {
                        // Layout_weight的深刻理解
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0,
                                height,
                                fieldItemArray[i].getPercent());// 宽 0 利用weight 控制， weight 利用表单占比params.gravity = Gravity.CENTER_VERTICAL;
                        lineView.setGravity(Gravity.CENTER_VERTICAL);
                        lineView.addView(initOneCell(fieldItemArray[i]), params);//初始化每一个表单项里的小name value
                    }
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT, height, 1); //创建 Linearlayout 布局参数
                    params.gravity = Gravity.CENTER_VERTICAL;
                    lineView.setGravity(Gravity.CENTER_VERTICAL);
                    mLinearlayout_formdetail.addView(lineView, params);    //把一个数据实例和一个 视图加入到lineView

                    LinearLayout lineViewScroll = new LinearLayout(        //创建一个linearLayout 容器 当做行视图来用
                            HtmitechApplication.instance());
                    lineViewScroll.setBackgroundResource(R.drawable.corners_bg_white_press_stroke);    //2015-09-22添加 for 项目申报字表行背景颜色
                    lineViewScroll.setGravity(Gravity.CENTER_VERTICAL);
                    lineViewScroll.setOrientation(LinearLayout.HORIZONTAL);
                    ScrollLinear(entity, lineViewScroll);
                    LinearLayout.LayoutParams paramsScroll = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1); //创建 Linearlayout 布局参数
                    mLinearlayout_formdetail.addView(lineViewScroll, paramsScroll);    //把一个数据实例和一个 视图加入到lineView
                    break;
                }
            }
            /**
             * 弱弱的加个折叠箭头
             */
            if (isSplitRegion && SplitAction == 1) {
                im_down = new ImageView(HtmitechApplication.getApplication());
                im_down.setImageResource(R.drawable.btn_angle_down_circle);
                //默认是外层的backcolor颜色
                im_down.setBackgroundColor(entity.getBackColor());
                lineView.setTag(R.id.tag_second, im_down);
                LinearLayout.LayoutParams param_down = new LinearLayout.LayoutParams(height, height); //创建 Linearlayout 布局参数
                LinearLayout.LayoutParams param_image = new LinearLayout.LayoutParams(height, height - 3); //创建 Linearlayout 布局参数
                im_down.setLayoutParams(param_image);
                param_down.gravity = Gravity.CENTER_VERTICAL;
                if (fieldItemArray[i].getBackColor() != 0 && fieldItemArray[i].getBackColor() != -1) {//如果里面内容的背景色不为空并且部位-1  那么下拉箭头的颜色的是里面backcolor的颜色
                    im_down.setBackgroundColor(fieldItemArray[i].getBackColor());
                }
                LinearLayout ll_imageview = new LinearLayout(HtmitechApplication.instance());
                ll_imageview.setOrientation(LinearLayout.VERTICAL);
                TextView tv_line = new TextView(HtmitechApplication.instance());
                tv_line.setBackgroundColor(getResources().getColor(R.color.imageline));
                LinearLayout.LayoutParams param_line = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 3); //创建 Linearlayout 布局参数
                tv_line.setLayoutParams(param_line);
                ll_imageview.addView(im_down);
                ll_imageview.addView(tv_line);
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
                if (Integer.parseInt(fieldItemArray[i].getInput().trim()) == 2003 ||
                        Integer.parseInt(fieldItemArray[i].getInput().trim()) == 2002 ||
                        Integer.parseInt(fieldItemArray[i].getInput().trim()) == 2001 ||
                        Integer.parseInt(fieldItemArray[i].getInput().trim()) == 2004 ||
                        Integer.parseInt(fieldItemArray[i].getInput().trim()) == 6001 ||
                        Integer.parseInt(fieldItemArray[i].getInput().trim()) == 6002 ||
                        Integer.parseInt(fieldItemArray[i].getInput().trim()) == 6101 ||
                        Integer.parseInt(fieldItemArray[i].getInput().trim()) == 6102 ||
                        Integer.parseInt(fieldItemArray[i].getInput().trim()) == 501 ||
                        Integer.parseInt(fieldItemArray[i].getInput().trim()) == 502 ||
                        Integer.parseInt(fieldItemArray[i].getInput().trim()) == 503 ||
                        Integer.parseInt(fieldItemArray[i].getInput().trim()) == 511 ||
                        Integer.parseInt(fieldItemArray[i].getInput().trim()) == 512 ||
                        Integer.parseInt(fieldItemArray[i].getInput().trim()) == 513 ||
                        Integer.parseInt(fieldItemArray[i].getInput().trim()) == 102 ||
                        Integer.parseInt(fieldItemArray[i].getInput().trim()) == 514) {
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
                int allWidth = DensityUtil.sp2px(HtmitechApplication.instance(), 20) * fieldItemArray[i].getValue().length();
                int cellWidth = screenWidth * fieldItemArray[i].getPercent() / 100;
                isSing = allWidth > ((cellWidth * 2) - (DeviceUtils.dip2px(HtmitechApplication.getApplication(), 5) * 2));
            }

            for (int i = 0; i < fieldItemArray.length; i++) {
                // 如果大于30个字符就用字符的长度除以30在乘以height取整 在加一
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, (isSing) ? LinearLayout.LayoutParams.MATCH_PARENT : height, fieldItemArray[i].getPercent());
                params.gravity = Gravity.CENTER_VERTICAL;
                lineView.setGravity(Gravity.CENTER_VERTICAL);
                lineView.addView(initOneCell(fieldItemArray[i]), params);            //初始化每一个表单项里的小name value
            }


        } else {
            //意见类 高度设置成matchparent
            for (int i = 0; i < fieldItemArray.length; i++) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, fieldItemArray[i].getPercent());   // 宽 0 利用weight 控制， weight 利用表单占比
                LinearLayout.LayoutParams params_round = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);   // 宽 0 利用weight 控制， weight 利用表单占比
                params.gravity = Gravity.CENTER_VERTICAL;
                lineView.setGravity(Gravity.CENTER_VERTICAL);
                LinearLayout linearLayout = initOneCell(fieldItemArray[i]);
                linearLayout.setPadding(0, height / 5, 0, height / 5);
                LinearLayout linearlayoutround = new LinearLayout(HtmitechApplication.getInstance());
                linearlayoutround.addView(linearLayout, params_round);
                linearlayoutround.setBackgroundColor(SysConvertUtil.formattingH(fieldItemArray[i].getBackColor()));
                lineView.addView(linearlayoutround, params);            //初始化每一个表单项里的小name value
//                }
            }

        }
        /**
         * 加上后面的折叠箭头
         */
        if (isSplitRegion && SplitAction == 1) {
            im_down = new ImageView(HtmitechApplication.getApplication());
            im_down.setImageResource(R.drawable.btn_angle_down_circle);
            //默认是外层的backcolor颜色
            im_down.setBackgroundColor(SysConvertUtil.formattingH(entity.getBackColor()));
            lineView.setTag(R.id.tag_second, im_down);
            LinearLayout.LayoutParams param_down = new LinearLayout.LayoutParams(height, height); //创建 Linearlayout 布局参数
            LinearLayout.LayoutParams param_image = new LinearLayout.LayoutParams(height, height - 3); //创建 Linearlayout 布局参数
            im_down.setLayoutParams(param_image);
            param_down.gravity = Gravity.CENTER_VERTICAL;
            if (fieldItemArray[i].getBackColor() != 0 && fieldItemArray[i].getBackColor() != -1) {//如果里面内容的背景色不为空并且部位-1  那么下拉箭头的颜色的是里面backcolor的颜色
                im_down.setBackgroundColor(SysConvertUtil.formattingH(fieldItemArray[i].getBackColor()));
            }
            LinearLayout ll_imageview = new LinearLayout(HtmitechApplication.instance());
            ll_imageview.setOrientation(LinearLayout.VERTICAL);
            TextView tv_line = new TextView(HtmitechApplication.instance());
            tv_line.setBackgroundColor(getResources().getColor(R.color.imageline));
            LinearLayout.LayoutParams param_line = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 3); //创建 Linearlayout 布局参数
            tv_line.setLayoutParams(param_line);
            ll_imageview.addView(im_down);
            ll_imageview.addView(tv_line);
            lineView.addView(ll_imageview, param_down);
        }
    }

    private LinearLayout initOneCell(final FieldItem item) {

        if (item.getMode() != null) {
            int mode = Integer.parseInt(item.getMode().trim());
            switch (mode) {
                case 19:
                    // 只读意见的显示和响应
                    if (item.opintions != null && item.opintions.size() > 0) {
                        //意见字段的显示
                        ModeOpinion19 mModeOpinion19 = new ModeOpinion19(FormFragment.this.getActivity());
                        return mModeOpinion19.modeOpinionLayout(VlineVisible, item, mInflater, list_tvsize, ((DetailActivity) getActivity()).com_workflow_mobileconfig_IM_enabled);

                    }
                    break;
                case 10:
                    // 只读意见的显示和响应
                    int input = Integer.parseInt(item.getInput().trim());
                    switch (input) {
                        case 2004:
                            ModeOpinion19 mModeOpinion19 = new ModeOpinion19(FormFragment.this.getActivity());
                            return mModeOpinion19.modeOpinionLayout(VlineVisible, item, mInflater, list_tvsize, ((DetailActivity) getActivity()).com_workflow_mobileconfig_IM_enabled);
                        case 6001:
                        case 6002:
                        case 6101:
                        case 6102:
//                            SelectPhoto6001_6002_6101_6102 mSelectPhoto6001_6002_6101_6102 = new SelectPhoto6001_6002_6101_6102(FormFragment.this.getActivity());
//                            ((DetailActivity) getActivity()).formMap.put(item.getField_id(), mSelectPhoto6001_6002_6101_6102);
//                            return mSelectPhoto6001_6002_6101_6102.Photo6102(VlineVisible, item, mInflater, list_tvsize, new FormFragment.CellOnclickLisener6001_6002_6101_6102(item), ((DetailActivity) getActivity()).com_workflow_mobileconfig_IM_enabled);

                    }
                    break;
            }
        }


        // //////////////////////////////////////////////////////////////////////////
        final String value = item.getBeforeValueString() + item.getValue().replace("\\\\r\\\\n", "\r\n")      //得到value
                + item.getEndValueString();


        // 可编辑字段处理
        if (item.getMode() != null && item.getInput() != null                        //mode为1 进入可编辑项 大循环
                && item.getMode().equals("1") && !item.getInput().equals("")) {

            // 是否是必填字段，
            // 必填字段
            EditFieldList mustFieldList = EditFieldList.getInstance();                //每一个可编辑项都会返回唯一的必填项集合

            if (item.isMustInput()) {
                EditField mustInput = new EditField();                                //如果该项是必填项，创建必填项 Edit
                mustInput.setKey(item.getKey());                                    //拿到key value 名字 放到 必填项列表
                mustInput.setValue(item.getValue());
                mustInput.setInput(item.getInput());
                mustInput.setNameDesc(item.getBeforeNameString()
                        + item.getName() + item.getEndNameString());
                String name = PreferenceUtils                                    //单击布局拿到当前用户
                        .getOAUserName(this.getActivity());
                mustInput.tempValue = item.getValue().contains(name) ? ("" + item.getValue()) : "";


                mustFieldList.getList().add(mustInput);


                //特殊处理，如果是1011类型的必填字段，则需要判断之前的意见或签名是否包含当然人的
                if (Integer.parseInt(item.getInput().trim()) == 2003) {

                    boolean hasCurrentUserOption = false;

                    if (item.opintions != null && item.opintions.size() > 0) {
                        for (int i = 0; i < item.opintions.size(); i++) {
                            if (item.opintions.get(i).UserID.equalsIgnoreCase(OAConText
                                    .getInstance(HtmitechApplication.instance()).UserID)) {
                                hasCurrentUserOption = true;
                                break;
                            }
                        }
                    }

                    if (!hasCurrentUserOption)
                        mustInput.setValue("");
                }

            }

            int input = Integer.parseInt(item.getInput().trim());
            switch (input) {
                case 2003:
//                    EditableOpinionSig2003 mEditableOpinionSig2003 = new EditableOpinionSig2003(FormFragment.this.getActivity());
//                    return mEditableOpinionSig2003.editableOpinionSig(VlineVisible, item, mInflater, list_tvsize, list_rbsize, EditFileds, new YiJianOnclickLisener(), ((DetailActivity) getActivity()).com_workflow_mobileconfig_IM_enabled);

                case 2001:// 可编辑字段处理,意见
//                    FormOpinion2001 mFormOpinion2001 = new FormOpinion2001(FormFragment.this.getActivity());
//
//                    return mFormOpinion2001.Opinion2001(VlineVisible, item, mInflater, list_tvsize, new CellOnclickLisener(), ((DetailActivity) getActivity()).com_workflow_mobileconfig_IM_enabled);

                case 2002:
                    // 1，**********************处理可编辑的意见字段******************************
                    EditableOpinion2002 mEditableOpinion2002 = new EditableOpinion2002(FormFragment.this.getActivity());
                    return mEditableOpinion2002.editableOpinion(VlineVisible, item, mInflater, list_tvsize, EditFileds);

                case 2004:

////                    if (item.opintions != null && item.opintions.size() > 0) {
////                        FormOpinion2004 mFormOpinion2004 = new FormOpinion2004(FormFragment.this.getActivity());
////                        return mFormOpinion2004.Opinion2004(VlineVisible, item, mInflater, list_tvsize, new CellOnclickLisener2004(item.opintions.get(0).opinionText), ((DetailActivity) getActivity()).com_workflow_mobileconfig_IM_enabled);
////                    } else {
////                        FormOpinion2004 mFormOpinion2004 = new FormOpinion2004(FormFragment.this.getActivity());
////                        return mFormOpinion2004.Opinion2004(VlineVisible, item, mInflater, list_tvsize, new CellOnclickLisener2004(""), ((DetailActivity) getActivity()).com_workflow_mobileconfig_IM_enabled);
//                    }

                case 6001:
                case 6002:
                case 6101:
                case 6102:
//                    SelectPhoto6001_6002_6101_6102 mSelectPhoto6001_6002_6101_6102 = new SelectPhoto6001_6002_6101_6102(FormFragment.this.getActivity());
//                    ((DetailActivity) getActivity()).formMap.put(item.getField_id(), mSelectPhoto6001_6002_6101_6102);
//                    return mSelectPhoto6001_6002_6101_6102.Photo6102(VlineVisible, item, mInflater, list_tvsize, new FormFragment.CellOnclickLisener6001_6002_6101_6102(item), ((DetailActivity) getActivity()).com_workflow_mobileconfig_IM_enabled);

                case 412://获取Name支持输入(下拉框选择模式)
                case 403://获取Value
                case 402://获取Name
                case 401://获取ID

                    SpringLayout412_403_402_401 mSpringLayout412_403_402_401 = new SpringLayout412_403_402_401(FormFragment.this.getActivity());
                    mSpringLayout412_403_402_401.setOptions(options, values, ids);
                    return mSpringLayout412_403_402_401.springLayout(VlineVisible, item, mInflater, list_tvsize, mComboBoxList, list_comboboxsize, EditFileds, this);

                case 300:
                case 301:
                    // ***********************日期选择***************************
                    CheckDate300_301 mCheckDate300_301 = new CheckDate300_301(FormFragment.this.getActivity());
                    return mCheckDate300_301.getCheckDateLayout(VlineVisible, item, mInflater, list_tvsize, EditFileds, mPopBirthHelper, currentEditTextView);
                case 3011://读者多选
                case 3012://作者多选
                case 611:
                case 612://人员多选
                case 613:
                    PeopleCheck611_612_613 mPeopleCheck611_612_613 = new PeopleCheck611_612_613(FormFragment.this.getActivity());
                    return mPeopleCheck611_612_613.peopleChckLayout(VlineVisible, item, mInflater, currentEditTextView, FormFragment.this);
                case 911:
                case 912:
                    //912 表示选择部门返回ID  911 是返回NAME
                    DepartmentCheck911_912 mDepartmentCheck911_912 = new DepartmentCheck911_912(FormFragment.this.getActivity());
                    return mDepartmentCheck911_912.departmentLayout(VlineVisible, item, mInflater, currentEditTextView, FormFragment.this);
                case 3001: //读者单选
                case 3002://作者单选
                case 601:
                case 602:
                case 603: //人员单选
                    RadioPeople601_02_03 mRadioPeople601_02_03 = new RadioPeople601_02_03(FormFragment.this.getActivity());
                    return mRadioPeople601_02_03.peopleChckLayout(VlineVisible, item, mInflater, currentEditTextView, FormFragment.this);
                case 901:
                case 902:
                    RadioDepartment901_902 mRadioDepartment901_902 = new RadioDepartment901_902(FormFragment.this.getActivity());
                    return mRadioDepartment901_902.departmentLayout(VlineVisible, item, mInflater, currentEditTextView, FormFragment.this);
                case 501://单选框结果取ID
                case 502://单选框结果取Name
                case 503://单选框结果取Value
                case 511://复选框结果取ID
                case 512://复选框结果取Name
                case 513://复选框结果取Value
                    RadioButton501_02_03_11_513 mRadioButton501_02_03_11_513 = new RadioButton501_02_03_11_513(FormFragment.this.getActivity());
                    return mRadioButton501_02_03_11_513.radioButtonLayout(VlineVisible, item, mInflater, EditFileds);
                case 514: //字典
                    Zidian514 zidian = new Zidian514(FormFragment.this.getActivity());
                    return zidian.radioButtonLayout(VlineVisible, item, mInflater, EditFileds);
                default:// 4，**********************其他非意见可编辑字段**********************
                    OtherLayout otherLayout = new OtherLayout(FormFragment.this.getActivity());

                    return otherLayout.otherLayoutMeth(VlineVisible, item, mInflater, list_tvsize, list_etsize, currentEditText, EditFileds);


            }


        }

        // //////////////其他只读字段的显示
        LinearLayout layout = null;
        if (item.isNameRN())
            layout = (LinearLayout) mInflater.inflate(
                    R.layout.layout_formdetail_cell_vertical_lib, null);
        else
            layout = (LinearLayout) mInflater.inflate(
                    R.layout.layout_formdetail_cell_lib, null);

        if (VlineVisible.equalsIgnoreCase("true")) {
//            layout.setBackgroundResource(R.drawable.corners_bg_white_press_stroke);
        } else {
            layout.setBackgroundResource(R.drawable.corners_bg_grey_press_stroke);
        }

        TextView tv = (TextView) layout.findViewById(R.id.form_fielditem_text);
        tv.setGravity(Gravity.CENTER_VERTICAL);
        list_tvsize.add(tv);
        /**
         * add by heyang
         * date 2016-7-18
         * if isMustInput is true then change backgroundclolor as "color_mustinputback"
         */
        if (item.getAlign().equalsIgnoreCase("center")) {
            tv.setGravity(Gravity.CENTER);
        } else if (item.getAlign().equalsIgnoreCase("left")) {
            tv.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        } else if (item.getAlign().equalsIgnoreCase("right")) {
            tv.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        }
        String text = "";
        int nameLength = 0;
        int splitLength = 0;
        int nameColor = HtmitechApplication.instance().getResources()
                .getColor(R.color.color_ff555555);

        int valueColor = nameColor;

        //2015-09-22添加 for 项目申报字表行背景颜色
        if (item.getNameFontColor() != 0)
            nameColor = item.getNameFontColor();

        if (item.getValueFontColor() != 0)
            valueColor = item.getValueFontColor();

        boolean isNameVisible = item.isNameVisible();
        if (isNameVisible) {
            String name = item.getBeforeNameString() + item.getName()
                    + item.getEndNameString();
            String split = item.getSplitString();
            text += name + split;
            nameLength = item.getName().length() + split.length();
            splitLength = split.length();
            if (item.getNameColor().equalsIgnoreCase("red")) {
                nameColor = Color.RED;
            }
        }

        if (item.isNameRN() && value.length() > 0) {
            text += "\n";
        }
        text += value;
        final String valueTemp = value;
        if (item.getValueColor().equalsIgnoreCase("red")) {
            valueColor = Color.RED;
        }
        if (item.getName().contains("电话") || item.getName().contains("手机")
                || item.getName().contains("联系")) {
            if (value.length() == 7 || value.length() == 8
                    || value.length() == 11) {
                if (isNumeric(value)) {
                    valueColor = Color.parseColor("#5782ab");
                    tv.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Uri uri = Uri.parse("tel:" + valueTemp);
                            Intent intent = new Intent(Intent.ACTION_DIAL, uri);
                            // 直接打出去的action
                            // intent.setAction("android.intent.action.CALL");
                            startActivity(intent);
                        }
                    });
                }
            }
        }
        int NameBackColor = item.getNameBackColor();    //2015-09-22添加 for 项目申报字表行背景颜色
        int ValueBackColor = item.getValueBackColor();
        SpannableStringBuilder spanBuilder = new SpannableStringBuilder(text);
        if (nameLength > 0) {
            spanBuilder.setSpan(new ForegroundColorSpan(nameColor), 0,
                    nameLength, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            //spanBuilder.setSpan(new BackgroundColorSpan(123));
        }
        if (value.length() > 0) {
            spanBuilder
                    .setSpan(new ForegroundColorSpan(valueColor), nameLength,
                            text.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        }
        tv.setText(spanBuilder);
        if (item.getBackColor() != 0 && item.getBackColor() != -1) {

            if (layout.findViewById(R.id.form_fielditem_option).getVisibility() == View.GONE && layout.findViewById(R.id.form_fielditem_editimage).getVisibility() == View.GONE) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                params.setMargins(5, 0, 1, 1);
                params.gravity = Gravity.CENTER_VERTICAL;
                tv.setLayoutParams(params);
//                tv.setGravity(Gravity.CENTER);
            }
            tv.setBackgroundColor(SysConvertUtil.formattingH(item.getBackColor())); //2015-09-22添加 for 项目申报字表字段背景颜色
            //layout.setBackgroundColor(item.getBackColor());
        }
        //距离左侧5dp
        int left = DeviceUtils.dip2px(HtmitechApplication.getApplication(), 5);
        tv.setPadding(left, 5, 20, 5);
        return layout;
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
    public void callHandle_doAction_hasAuthor(ArrayList<AuthorInfo> AuthorInfoTemp, TextView currentEditTextView) {
        this.currentEditTextView = currentEditTextView;
        handle_doAction_hasAuthor(AuthorInfoTemp);
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
                    authorId.append(";");
                    authorName.append(";");
                }
                authorId.append(selectUser.getUserID());
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
        Log.d("FormFragment", "EditFileds:" + EditFileds);
        // 2016-1-11
        FragmentActivity sfActivity = getActivity();
        DocResultInfo mDocResultInfo = null;
        if (sfActivity != null && sfActivity instanceof StartDetailActivity)
            mDocResultInfo = ((StartDetailActivity) getActivity()).mDocResultInfo;
        else if (sfActivity != null && sfActivity instanceof DetailActivity)
            mDocResultInfo = ((DetailActivity) getActivity()).mDocResultInfo;
        else if (sfActivity != null && sfActivity instanceof DetailActivity2)
            mDocResultInfo = ((DetailActivity2) getActivity()).mDocResultInfo;
        mDocResultInfo.getResult().cleanFields();
        if (mDocResultInfo.getResult() != null)
            mDocResultInfo.getResult().setEditFields(EditFileds);
        // 2,必填字段处理--将输入的意见放入相应必填字段中
        EditFieldList mustFieldList = EditFieldList.getInstance();
        for (int i = 0; i < mustFieldList.getList().size(); i++) {
            if (mustFieldList
                    .getList()
                    .get(i)
                    .getKey()
                    .equalsIgnoreCase(
                            ((EditField) FormFragment.this.currentEditTextView
                                    .getTag()).getKey())) {
                mustFieldList.getList().get(i).setValue(strValue);
            }
        }
        currentEditTextView.setText("");
        currentEditTextView.setText(strName);

    }

    // /人员选择时清空
    private void handle_doAction_clearAuthor() {

        // 将值保存到编辑的字段中，并判断是否需要更新必填字段
        // 1,将已经编辑的字段和值放入到办理是需要回发的EditField[] 中
        String strValue = ""; // U_id1;U_id2
        String strName = ""; // 姓名1;姓名2
        EditField edit = (EditField) currentEditTextView.getTag();
        edit.setValue(strValue);
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
        Log.d("FormFragment", "EditFileds:" + EditFileds);
        // 2016-1-11
        FragmentActivity sfActivity = getActivity();
        DocResultInfo mDocResultInfo = null;
        if (sfActivity != null && sfActivity instanceof StartDetailActivity)
            mDocResultInfo = ((StartDetailActivity) getActivity()).mDocResultInfo;
        else if (sfActivity != null && sfActivity instanceof DetailActivity)
            mDocResultInfo = ((DetailActivity) getActivity()).mDocResultInfo;
        else if (sfActivity != null && sfActivity instanceof DetailActivity2)
            mDocResultInfo = ((DetailActivity2) getActivity()).mDocResultInfo;

        mDocResultInfo.getResult().setEditFields(EditFileds);
        // 2,必填字段处理--将输入的意见放入相应必填字段中
        EditFieldList mustFieldList = EditFieldList.getInstance();
        for (int i = 0; i < mustFieldList.getList().size(); i++) {
            if (mustFieldList
                    .getList()
                    .get(i)
                    .getKey()
                    .equalsIgnoreCase(
                            ((EditField) FormFragment.this.currentEditTextView
                                    .getTag()).getKey())) {
                mustFieldList.getList().get(i).setValue(strValue);
            }
        }

        currentEditTextView.setText(strName);

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
//                if (((DetailActivity) getActivity()).formMap.get(item_workflow.getField_id()) != null && ((DetailActivity) getActivity()).formMap.get(item_workflow.getField_id()) instanceof SelectPhoto6001_6002_6101_6102) {
//                    ((SelectPhoto6001_6002_6101_6102) ((DetailActivity) getActivity()).formMap.get(item_workflow.getField_id())).updateImg(getActivity(), listImgTemp, 0);
//                }
            }
        }

        // ********************测试临时代码******************************
        if (data == null || TextUtils.isEmpty(data.getStringExtra("option"))
                || mClickView == null) {
            return;
        }
        String strOption = data.getStringExtra("option");

        if (requestCode == 0) {
            if (mClickView instanceof TextView) {
                ((TextView) mClickView).setTextColor(getResources().getColor(R.color.black));
                ((TextView) mClickView).setText(strOption);
                if (!DetailActivity2.currentActivity) {
                    if (getActivity() instanceof DetailActivity) {
                        ((DetailActivity) getActivity()).setComment(strOption);
                    }
                }
            } else {

                LinearLayout layout = (LinearLayout) mClickView;
                // 显示当前编辑的意见
                TextView tvOption = (TextView) layout
                        .findViewById(R.id.form_fielditem_option);
                list_tvsize.add(tvOption);
                tvOption.setTextColor(Color.RED);
                if (strOption != null && strOption.length() > 0) {
                    tvOption.setVisibility(View.VISIBLE);
                    tvOption.setText(strOption);
                    // 已经编辑完成后返回后执行的逻辑：
                    if (!DetailActivity2.currentActivity)
                        ((DetailActivity) getActivity()).setComment(strOption);
                    // 显示时加上当前时间
                    tvOption.setText(strOption + "\n"
                            + DateFormatUtils.format(new Date(), "yyyy-M-d h:mm"));
                } else if (strOption.length() == 0) {
                    tvOption.setText("");
                    tvOption.setVisibility(View.INVISIBLE);
                }
            }
        } else if (requestCode == 1) {


        }


        // 1,将已经编辑的字段和值放入到办理是需要回发的EditField[] 中
        FormFragment.this.mClickView = mClickView;
        EditField edit = (EditField) FormFragment.this.mClickView.getTag();
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
        Log.d("FormFragment", "EditFileds:" + EditFileds);
        // 2015-8-17
        DocResultInfo mDocResultInfo = null;
        if (DetailActivity2.currentActivity) {
            mDocResultInfo = ((DetailActivity2) getActivity()).mDocResultInfo;
        } else {
            mDocResultInfo = ((DetailActivity) getActivity()).mDocResultInfo;
        }
        // DocResultInfo mDocResultInfo = ((DetailActivity)
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
                            ((EditField) FormFragment.this.mClickView.getTag())
                                    .getKey())) {
                mustFieldList.getList().get(i).tempValue = strOption;
                mustFieldList.getList().get(i).setValue(strOption); //
            }
        }
    }


    public class YiJianOnclickLisener implements OnClickListener {// 设置表格项的监听器

        @Override
        public void onClick(View v) {

			/*
             * mNewFragment =
			 * MyAlertDialogFragment.newInstance("点击了意见字段，需要弹出意见输入。",
			 * R.drawable.prompt_warn, cancelListener, confirmListener);
			 * mNewFragment.show(getFragmentManager(), "dialog");
			 */
            if (getActivity() != null
                    && (DetailActivity2.currentActivity == false)) {
                Intent intent = new Intent(getActivity(),
                        OpinionInputActivity.class);
                startActivityForResult(intent, 1);


            }

            // *********************测试临时代码******************************
            FormFragment.this.mClickView = v;

        }
    }

    public class CellOnclickLisener implements OnClickListener {// 设置表格项的监听器

        @Override
        public void onClick(View v) {

			/*
             * mNewFragment =
			 * MyAlertDialogFragment.newInstance("点击了意见字段，需要弹出意见输入。",
			 * R.drawable.prompt_warn, cancelListener, confirmListener);
			 * mNewFragment.show(getFragmentManager(), "dialog");
			 */
            if (getActivity() != null
                    && (DetailActivity2.currentActivity == false)) {
                Intent intent = new Intent(getActivity(),
                        OpinionInputActivity.class);
                intent.putExtra("app_id", app_id);
                startActivityForResult(intent, 0); // 不用ActivityResult 还可以用什么？
            }

            // *********************测试临时代码******************************
            FormFragment.this.mClickView = v;

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


    public class CellOnclickLisener2004 implements OnClickListener {// 设置表格项的监听器
        String textValue = "";

        public CellOnclickLisener2004(String textValue) {
            this.textValue = textValue;
        }

        @Override
        public void onClick(View v) {

			/*
             * mNewFragment =
			 * MyAlertDialogFragment.newInstance("点击了意见字段，需要弹出意见输入。",
			 * R.drawable.prompt_warn, cancelListener, confirmListener);
			 * mNewFragment.show(getFragmentManager(), "dialog");
			 */
            if (getActivity() != null && (DetailActivity2.currentActivity == false)) {
                Intent intent = new Intent(getActivity(), OpinionInputActivity.class);
                intent.putExtra("app_id", app_id);
//                intent.putExtra("is2004", true); //是否屏蔽常用意见列表
                intent.putExtra("textValue", textValue);
                startActivityForResult(intent, 0); // 不用ActivityResult 还可以用什么？
            }

            // *********************测试临时代码******************************
            FormFragment.this.mClickView = v;

        }

    }

    public class CellOnclickLisener6001_6002_6101_6102 implements OnClickListener {// 设置表格项的监听器
        FieldItem item;

        public CellOnclickLisener6001_6002_6101_6102(FieldItem item) {
            this.item = item;
        }

        @Override
        public void onClick(View v) {

            if (v.getId() == com.htmitech.addressbook.R.id.bt_send) {
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("workflow_item", item);
//                params.put("photo_number", ((SelectPhoto6001_6002_6101_6102) ((DetailActivity) getActivity()).formMap.get(item.getField_id())).mListPickerAudioSelect.size() );
                ArrayList<PhotoModel> album_select = new ArrayList<PhotoModel>();
//                for (PhotoModel model : ((SelectPhoto6001_6002_6101_6102) ((DetailActivity) getActivity()).formMap.get(item.getField_id())).mListPickerAudioSelect) {
//                    if (model.isChecked()) {
//                        album_select.add(model);
//                    }
//                }
                params.put("album_select", album_select);
//                params.put("photo_number", ((SelectPhoto6001_6002_6101_6102) ((DetailActivity) getActivity()).formMap.get(item.getField_id())).mListPickerAudioSelect.size() - album_select.size());
//                ActivityUnit.switchTo(getActivity(), PhotoSelectorActivity.class, params);
                menuWindow.dismiss();
            } else if (v.getId() == com.htmitech.addressbook.R.id.bt_call) {
                imageFilePath = FileSizeUtil.getTempPhotoFileName(OAConText.getInstance(getActivity()).UserID, BookInit.getInstance().getPortalId(), app_id);

                File temp = new File(imageFilePath);
                Uri imageFileUri = Uri.fromFile(temp);//获取文件的Uri
                Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//跳转到相机Activity
                it.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageFileUri);//告诉相机拍摄完毕输出图片到指定的Uri
                imageParme.put(imageFilePath, item);
                startActivityForResult(it, 10003);
                menuWindow.dismiss();
            } else if (v.getId() == com.htmitech.addressbook.R.id.bt_save) {
                menuWindow.dismiss();
            }
//            else if(v.getId() ==R.id.img_delete){
//                Object obj = v.getTag();
//                PhotoModel mphotoModel = (PhotoModel) obj;
//                listImg.remove(mphotoModel);
//                if( ((DetailActivity)getActivity()).formMap.get(mphotoModel.getItem_workflow().getField_id())!=null&& ((DetailActivity)getActivity()).formMap.get(mphotoModel.getItem_workflow().getField_id()) instanceof SelectPhoto6001_6002_6101_6102 ){
//                    List<PhotoModel> listImgTemp = new ArrayList<PhotoModel>();
//                    if(listImg.size()>0){
//                        for(PhotoModel photoModel :listImg){
//                            if(photoModel.getItem_workflow()!=null&&photoModel.getItem_workflow().getField_id().equals(mphotoModel.getItem_workflow().getField_id())){
//                                listImgTemp.add(photoModel);
//                            }
//                        }
//                    }
//                    ((SelectPhoto6001_6002_6101_6102)((DetailActivity)getActivity()).formMap.get(mphotoModel.getItem_workflow().getField_id())).updateImg(getActivity(),listImgTemp);
//                }
//                        updateImg(context, mListPickerAudioSelect);
//            }
            else {
                if (Integer.parseInt(item.getInput()) == 6001) {
//                    if (((SelectPhoto6001_6002_6101_6102) ((DetailActivity) getActivity()).formMap.get(item.getField_id())).mListPickerAudioSelect.size() >= 1) {
//                        Toast.makeText(getActivity(), "只能添加一张图片", Toast.LENGTH_SHORT).show();
//                        return;
//                    }
                    imageFilePath = FileSizeUtil.getTempPhotoFileName(OAConText.getInstance(getActivity()).UserID, BookInit.getInstance().getPortalId(), app_id);

                    File temp = new File(imageFilePath);
                    Uri imageFileUri = Uri.fromFile(temp);//获取文件的Uri
                    Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//跳转到相机Activity
                    it.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageFileUri);//告诉相机拍摄完毕输出图片到指定的Uri
                    imageParme.put(imageFilePath, item);
                    startActivityForResult(it, 10003);

                } else if (Integer.parseInt(item.getInput()) == 6101) {
//                    if (((SelectPhoto6001_6002_6101_6102) ((DetailActivity) getActivity()).formMap.get(item.getField_id())).mListPickerAudioSelect.size() >= 9) {
//                        Toast.makeText(getActivity(), "图片最多可以添加九张", Toast.LENGTH_SHORT).show();
//                        return;
//                    }
                    imageFilePath = FileSizeUtil.getTempPhotoFileName(OAConText.getInstance(getActivity()).UserID, BookInit.getInstance().getPortalId(), app_id);

                    File temp = new File(imageFilePath);
                    Uri imageFileUri = Uri.fromFile(temp);//获取文件的Uri
                    Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//跳转到相机Activity
                    it.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageFileUri);//告诉相机拍摄完毕输出图片到指定的Uri
                    imageParme.put(imageFilePath, item);
                    startActivityForResult(it, 10003);
                } else {
//                    if (Integer.parseInt(item.getInput()) == 6002 && ((SelectPhoto6001_6002_6101_6102) ((DetailActivity) getActivity()).formMap.get(item.getField_id())).mListPickerAudioSelect.size() >= 1) {
//                        Toast.makeText(getActivity(), "只能添加一张图片", Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//                    if (Integer.parseInt(item.getInput()) == 6102 && ((SelectPhoto6001_6002_6101_6102) ((DetailActivity) getActivity()).formMap.get(item.getField_id())).mListPickerAudioSelect.size() >= 9) {
//                        Toast.makeText(getActivity(), "图片最多可以添加九张", Toast.LENGTH_SHORT).show();
//                        return;
//                    }
                    if (getActivity() != null
                            && (DetailActivity2.currentActivity == false)) {
                        //TODO 添加点击照片的监听处理
                        menuWindow = new SelectPicPopupWindow(getActivity(), this);
                        menuWindow.setDetalFormMessage();
                        //显示窗口
                        menuWindow.showAtLocation(v, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                    }
                }
            }


            // *********************测试临时代码******************************
            FormFragment.this.mClickView = v;

        }

    }


//    @Override
//    public void checkImageSelect(ArrayList<PhotoModel> mSelectedImage, Fielditems item, FieldItem workflow_item) {
//        Log.e(TAG, "checkImageSelect: " + mSelectedImage.size() + "formKey: " + item);
//        List<PhotoModel> listImgTemp = new ArrayList<PhotoModel>();
//        if (mSelectedImage != null && mSelectedImage.size() > 0) {
//            for (PhotoModel mPhotoModel : mSelectedImage) {
//                mPhotoModel.setItem_workflow(workflow_item);
//                listImgTemp.add(mPhotoModel);
//            }
//        }
//        if (((DetailActivity) getActivity()).formMap.get(workflow_item.getField_id()) != null && ((DetailActivity) getActivity()).formMap.get(workflow_item.getField_id()) instanceof SelectPhoto6001_6002_6101_6102) {
//            ((SelectPhoto6001_6002_6101_6102) ((DetailActivity) getActivity()).formMap.get(workflow_item.getField_id())).updateImg(getActivity(), listImgTemp, 1);
//        }
//
//    }


}
