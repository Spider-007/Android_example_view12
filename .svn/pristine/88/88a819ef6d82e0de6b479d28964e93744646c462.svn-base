package com.htmitech.htcommonformplugin.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
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

import com.htmitech.MyView.EmptyLayout;
import com.htmitech.api.BookInit;
import com.htmitech.combobox.ComboBox;
import com.htmitech.commonx.util.DeviceUtils;
import com.htmitech.datepicker.PopChooseTimeHelper;
import com.htmitech.emportal.HtmitechApplication;
import htmitech.com.componentlibrary.base.MyBaseFragment;
import com.htmitech.emportal.entity.AuthorInfo;
import com.htmitech.emportal.entity.EditFieldList;
import com.htmitech.emportal.entity.OAConText;
import com.htmitech.emportal.ui.detail.DetailActivity2;
import com.htmitech.emportal.ui.detail.ObservableScrollView;
import com.htmitech.emportal.ui.detail.OpinionInputActivity;
import com.htmitech.emportal.ui.detail.ScrollViewListener;
import com.htmitech.emportal.ui.formConfig.CallBackDoAction;
import com.htmitech.emportal.R;
import com.htmitech.htcommonformplugin.entity.Editfields;
import com.htmitech.htcommonformplugin.entity.GetDetailEntity;
import com.htmitech.htcommonformplugin.entity.Regions;
import com.htmitech.photoselector.myinterface.CallBackImageSelectImp;
import com.htmitech.photoselector.ui.PhotoSelectorActivity;
import com.htmitech.pop.SelectPicPopupWindow;
import com.htmitech.proxy.util.FileSizeUtil;
import com.htmitech.unit.ActivityUnit;
import com.minxing.client.util.SysConvertUtil;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import htmitech.com.componentlibrary.entity.Fielditems;
import htmitech.com.componentlibrary.entity.PhotoModel;

/***
 * 表单详情页面
 *
 * @author joe
 * @date 2017/04/17
 */
@SuppressLint("ValidFragment")
public class GeneralFormDetailFragment extends MyBaseFragment implements ScrollViewListener, CallBackDoAction, CallBackImageSelectImp {
    private static final String TAG = "GeneralFormDetailFragme";
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

    private boolean VlineVisible = false;
    private List<Regions> paramaters;
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


    public GeneralFormDetailFragment() {
        super();
        // TODO Auto-generated constructor stub
    }

    @SuppressLint("ValidFragment")
    public GeneralFormDetailFragment(List<Regions> paramaters, String app_id) {
        this.app_id = app_id;
        this.paramaters = paramaters;
        this.i = i;
    }


    /* 表单项编辑意见操作用到的变量 —— begin */
    private List<Editfields> mEditFileds = new ArrayList<Editfields>(); // 缓存已经编辑的表单字段，回发用。
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

    EmptyLayout emptyLayout;

    protected int getLayoutId() {
        return R.layout.fragment_formdetal;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        BookInit.getInstance().setmCallBackImageSelectImp(this);
        list_tvsize = new ArrayList<TextView>();
        list_etsize = new ArrayList<EditText>();
        list_rbsize = new ArrayList<RadioButton>();
        list_cbsize = new ArrayList<CheckBox>();
        list_comboboxsize = new ArrayList<ComboBox>();
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
        emptyLayout = (EmptyLayout) this.findViewById(R.id.emptyLayout);
        scrollView = (ObservableScrollView) this.findViewById(R.id.sc_freement);
        scrollView.setScrollViewListener(this);
        if (getActivity() != null) {
            mEmptyView = View.inflate(getActivity(),
                    R.layout.layout_empty_view, null);   //空视图
        }
        mLinearlayout_formdetail = (LinearLayout) findViewById(R.id.linearlayout_formdetail); //整个表单的大框

//        if (((GeneralFormDetalActivity) getActivity()).isWaterSecurity == 1) {
//            TextStyle textStyle = new TextStyle();
//            textStyle.setColor("#EAEAEA");
//            textStyle.setFontSize(24);
//            WaterMarkTextUtil.setWaterMarkTextBg(scrollView, getActivity(), OAConText.getInstance(getActivity()).UserName, 1, textStyle);
//            scrollView.getBackground().setAlpha((int) (0.5 * 255));
//        }
        initFormDetail();
    }

    @Override
    public void checkImageSelect(ArrayList<htmitech.com.componentlibrary.entity.PhotoModel> mSelectedImage, htmitech.com.componentlibrary.entity.Fielditems common_item, htmitech.com.componentlibrary.entity.FieldItem workflow_item) {

        Log.e(TAG, "checkImageSelect: " + mSelectedImage.size() + "formKey: " + common_item);

        List<PhotoModel> listImgTemp = new ArrayList<PhotoModel>();
        if (mSelectedImage != null && mSelectedImage.size() > 0) {
            for (PhotoModel mPhotoModel : mSelectedImage) {
                mPhotoModel.setItem_common(common_item);
                listImgTemp.add(mPhotoModel);
            }
        }
//        if (((GeneralFormDetalActivity) getActivity()).formMap.get(common_item.getField_id()) != null && ((GeneralFormDetalActivity) getActivity()).formMap.get(common_item.getField_id()) instanceof SelectPhoto6001_6002_6101_6102) {
//            ((SelectPhoto6001_6002_6101_6102) ((GeneralFormDetalActivity) getActivity()).formMap.get(common_item.getField_id())).updateImg(getActivity(), listImgTemp,1);
//        }

    }


    public class ChildViews {
        public LinearLayout.LayoutParams parms;
        public LinearLayout layout;
    }

    /**
     * 初始化 表单细节
     * 初始化 表单细节
     * 每一个 FieldItems 为一行， 一行也可能包含多个字段
     */

    private void initFormDetail() {

        //InfoRegion[] regionArray = mDocResultInfo.getResult().getRegionItems();  // 获取所有RegionItems的集合  包含多个表单项
        if (paramaters != null && paramaters.size() > 0) {

            /**
             * 对数据拆分额
             */
            for (int i = 0; i < paramaters.size(); i++) {       //	历每一个 表单项
                LinearLayout lineView = new LinearLayout(        //创建一个linearLayout 容器 当做行视图来用
                        HtmitechApplication.instance());
                lineView.setFocusable(false);

                //add by heyang 2017/05/07 动态设置每个整条目的背景颜色和边框
                GradientDrawable drawable = new GradientDrawable();
                drawable.setStroke(1, Color.parseColor("#66B4B4B4"));
                drawable.setColor(SysConvertUtil.formattingH(paramaters.get(i).getRegion_backcolor()));
                lineView.setBackgroundDrawable(drawable);

//                lineView.setBackgroundResource(R.drawable.corners_bg_white_press_stroke);    //2015-09-22添加 for 项目申报字表行背景颜色
//                lineView.setBackgroundColor(SysConvertUtil.formattingH(paramaters.get(i).getRegion_backcolor()));
                /**
                 * V2.0add
                 */
                if (paramaters.get(i).is_split_region() && paramaters.get(i).getSplit_action() == 0) {//如果是0那么添加一个空白行不做任何操作
                    int height = DeviceUtils.dip2px(HtmitechApplication.getApplication(), 20);
                    LinearLayout.LayoutParams param0 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height, 1); //创建 Linearlayout 布局参数
                    lineView.setBackgroundResource(R.drawable.corners_bg_white_empty_little_stroke);
                    lineView.getBackground().setAlpha((int) (0.5 * 255));
                    mLinearlayout_formdetail.addView(lineView, param0);
                } else {
                    //给每个linearlayout添加点击事件
                    lineView.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String RegionIDClick = (String) v.getTag(R.id.tag_first);
                            ImageView iv_circle = (ImageView) v.getTag(R.id.tag_second);
                            int nowClickNum = -1;
                            for (int n = 0; n < paramaters.size(); n++) {
                                if (paramaters.get(n).getRegion_id().equals(RegionIDClick)) {
                                    nowClickNum = n;
                                    break;
                                }
                            }
                            if (nowClickNum != -1 && paramaters.get(nowClickNum).is_split_region() && paramaters.get(nowClickNum).getSplit_action() == 1) {//如果是1代表可以下拉
                                for (int k = 0; k < paramaters.size(); k++) {
                                    if (RegionIDClick != null && paramaters.get(k).getParent_region_id() != null) {
                                        if (paramaters.get(k).getParent_region_id().equals(RegionIDClick)) {
                                            //点击后如果是显示的则让不显示，不显示的显示。
                                            mLinearlayout_formdetail.getChildAt(k).setVisibility((mLinearlayout_formdetail.getChildAt(k).getVisibility() == View.GONE) ? View.VISIBLE : View.GONE);
                                            if ((mLinearlayout_formdetail.getChildAt(k).getVisibility() == View.GONE)) {//如果是被关闭了找他下面是否还有子类全部关闭
                                                if (iv_circle != null) {
                                                    iv_circle.setImageResource(R.drawable.btn_angle_up_circle);
                                                }
                                                isHead(paramaters.get(k), k);

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
                    if (paramaters.get(i).getScroll_flag() != 1) {
                        initOneRecord(lineView, paramaters.get(i));
                        //初始化一个记录 利用一个容器 和 一个 数据的详细描述
//                        int height = Dp2Px(HtmitechApplication.getApplication(), heightLinear);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1); //创建 Linearlayout 布局参数
                        params.gravity = Gravity.CENTER;
                        lineView.setGravity(Gravity.CENTER);
                        mLinearlayout_formdetail.addView(lineView, params);    //把一个数据实例和一个 视图加入到lineView
                    } else {
                        initOneRecord(lineView, paramaters.get(i));
                    }
                }
            }
        }
        //进入界面折叠子表默认折叠
        goneStart();
    }

    /**
     * 遍默认历表单折叠
     */
    private void goneStart() {
        for (int i = 0; i < paramaters.size(); i++) {
            for (int j = 0; j < paramaters.size(); j++) {
                if (paramaters.get(i).getParent_region_id() != null && !paramaters.get(i).getParent_region_id().trim().equals("")) {
                    if (paramaters.get(i).getParent_region_id().equals(paramaters.get(j).getParent_region_id())) {
                        mLinearlayout_formdetail.getChildAt(j).setVisibility(View.GONE);
                    }
                }
            }
        }
    }

    /**
     * 横向滑动子表单
     */
    private void ScrollLinear(Regions info, LinearLayout lineView) {

//        //获取屏幕宽度
//        Resources resources = this.getResources();
//        DisplayMetrics dm = resources.getDisplayMetrics();
//        int cellwidth = dm.widthPixels / 4;
//        int headCellWidth = cellwidth;
//        /**
//         * 初始化横向滑动控件  把linearlayout放到horizontalScrollView中
//         */
//        HorizontalScrollView hs = new HorizontalScrollView(HtmitechApplication.instance());
//        LinearLayout.LayoutParams hsparamsScroll = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1); //创建 Linearlayout 布局参数
//        hsparamsScroll.gravity = Gravity.CENTER_VERTICAL;
//        hs.setLayoutParams(hsparamsScroll);
//
//
//        LinearLayout linearScroll = new LinearLayout(HtmitechApplication.instance());
//        LinearLayout.LayoutParams linparamsScroll = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1); //创建 Linearlayout 布局参数
////        linparamsScroll.gravity = Gravity.CENTER_VERTICAL;
//        linearScroll.setOrientation(LinearLayout.VERTICAL);//滑动的linearlayout外边边框
//        linearScroll.setLayoutParams(linparamsScroll);
////        linearScroll.setGravity(Gravity.CENTER_VERTICAL);
//
//
//        LinearLayout linearHead = new LinearLayout(HtmitechApplication.instance());
//        LinearLayout.LayoutParams linearHeadparmas = new LinearLayout.LayoutParams(
//                headCellWidth * info.getScroll_fix_col_count(), LinearLayout.LayoutParams.WRAP_CONTENT); //创建 Linearlayout 布局参数,根据固定列数的不同设置不同的宽度，防止前面的列被缩小
//        linearHead.setOrientation(LinearLayout.VERTICAL);//固定的那部分linearlayout
//        linearHead.setLayoutParams(linearHeadparmas);
//
//
//        String RegionID = info.getRegion_id();
//        //遍历所有的参数 找到最长的那个  然后宽度按照那个设置 ，如果得到的都没有超过屏幕四分之一那就按照默认的也就是屏幕四分之一设置每个格的宽度
//        int n = 0;
//        for (int i = 0; i < paramaters.size(); i++) {
//            if (RegionID.equals(paramaters.get(i).getParent_region_id()) && paramaters.get(i).getScroll_flag() == 1) {
//                for (int j = 0; j < paramaters.get(i).getFielditems().size(); j++) {
//                    int valueSize = paramaters.get(i).getFielditems().get(j).getValue().length();
//                    if (valueSize > n) {
//                        n = valueSize;
//                    }
//                }
//            }
//        }
//        //如果通过字体大小个数算出的宽度大一屏幕四分之一则按照字体算出的大小设置控件的宽度
//        if ((DensityUtil.sp2px(HtmitechApplication.instance(), 13) * n) > cellwidth) {
//            cellwidth = DensityUtil.sp2px(HtmitechApplication.instance(), 13) * n;
//        }
//        boolean flagFrist = false;
//        for (int i = 0; i < paramaters.size(); i++) {
//            if (RegionID.equals(paramaters.get(i).getParent_region_id()) && paramaters.get(i).getScroll_flag() == 1) {
//                LinearLayout lineaempty = new LinearLayout(HtmitechApplication.instance());//为了点击折叠 没办法充数的
//                LinearLayout.LayoutParams linearemptyparmas = new LinearLayout.LayoutParams(0, 0); //创建 Linearlayout 布局参数 宽高都为0  不占地方
//                if (flagFrist)
//                    mLinearlayout_formdetail.addView(lineaempty, linearemptyparmas);
//                flagFrist = true;
//                int ScroFixColCount = paramaters.get(i).getScroll_fix_col_count();
//                LinearLayout linearcellscroll = new LinearLayout(HtmitechApplication.instance());//滑动里面的每一行
//                linearcellscroll.setOrientation(LinearLayout.HORIZONTAL);
//                LinearLayout linearcellhead = new LinearLayout(HtmitechApplication.instance());//固定的那里面的每一行
//                linearcellhead.setOrientation(LinearLayout.HORIZONTAL);
//                for (int j = 0; j < paramaters.get(i).getFielditems().size(); j++) {
//
//                    if (j >= ScroFixColCount) {//把每个单元加入横向滑动中
//                        LinearLayout.LayoutParams linearcellscrollparmas = new LinearLayout.LayoutParams(
//                                cellwidth, height); //创建 Linearlayout 布局参数
//                        linearcellscroll.addView(initOneCell(paramaters.get(i).getFielditems().get(j)), linearcellscrollparmas);
//                    } else {
//                        LinearLayout.LayoutParams linearcellheadparmas = new LinearLayout.LayoutParams(
//                                headCellWidth, height); //创建 Linearlayout 布局参数
//                        linearcellhead.addView(initOneCell(paramaters.get(i).getFielditems().get(j)), linearcellheadparmas);
//                    }
//                }
//                linearScroll.addView(linearcellscroll);
//                linearHead.addView(linearcellhead);
//            }
//        }
//        //循环结束后添加进linearlayout中
//        hs.addView(linearScroll);
//        lineView.addView(linearHead);
//        lineView.addView(hs);
    }

    /**
     * V2.0add
     * 判断下面是否还有子节点  并且当父节点关闭 下面的子节点全部关闭
     *
     * @param parmater
     * @param n
     */
    private void isHead(Regions parmater, final int n) {
        String tableID = parmater.getTable_id();
        String RegionID = parmater.getRegion_id();
        for (int i = n; i < paramaters.size(); i++) {
            if (paramaters.get(i).getParent_region_id().equals(RegionID)) {
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
                isHead(paramaters.get(i), i);
            }
            //当父被关闭下面的子全被关闭
            if (mLinearlayout_formdetail.getChildAt(n).getVisibility() == View.GONE) {
                for (int m = n + 1; m < paramaters.size(); m++) {
                    if (paramaters.get(m).getParent_region_id().equals(paramaters.get(n).getRegion_id())) {
                        mLinearlayout_formdetail.getChildAt(m).setVisibility(View.GONE);
                    } else {
                        break;
                    }
                }
            }


        }
    }

    private void initOneRecord(LinearLayout lineView, Regions entity) {
//        List<Fielditems> fieldItemList = entity.getFielditems();                    //初始化每一个表单项
//        String RegionID = entity.getRegion_id();
//        boolean IsTable = entity.is_table();
//        String TableID = entity.getTable_id();
//        String ParentRgionID = entity.getParent_region_id();
//        boolean isSplitRegion = entity.is_split_region();
//        int SplitAction = entity.getSplit_action();
//        int ScrollFlag = entity.getScroll_flag();
//        lineView.setTag(R.id.tag_first, RegionID);//给linearlayout设置一个标记
//        VlineVisible = entity.isVlinevisible();
//        if (fieldItemList == null || "".equals(fieldItemList)) {
//            return;
//        }
//        //是横向滑动的
//        if (ScrollFlag == 1) {
//            for (int j = 0; j < paramaters.size(); j++) {
//                if (RegionID.equals(paramaters.get(j).getParent_region_id())) {//如果是头 那么先把头显示出来在去执行下面的部分
//                    for (int i = 0; i < fieldItemList.size(); i++) {
//                        // Layout_weight的深刻理解
//                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0,
//                                height,
//                                fieldItemList.get(i).getPercent());// 宽 0 利用weight 控制， weight 利用表单占比params.gravity = Gravity.CENTER_VERTICAL;
//                        lineView.setGravity(Gravity.CENTER_VERTICAL);
//                        lineView.addView(initOneCell(fieldItemList.get(i)), params);//初始化每一个表单项里的小name value
//                    }
//                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                            LinearLayout.LayoutParams.MATCH_PARENT, height, 1); //创建 Linearlayout 布局参数
//                    params.gravity = Gravity.CENTER_VERTICAL;
//                    lineView.setGravity(Gravity.CENTER_VERTICAL);
//                    mLinearlayout_formdetail.addView(lineView, params);    //把一个数据实例和一个 视图加入到lineView
//
//                    LinearLayout lineViewScroll = new LinearLayout(        //创建一个linearLayout 容器 当做行视图来用
//                            HtmitechApplication.instance());
//                    lineViewScroll.setBackgroundResource(R.drawable.corners_bg_white_press_stroke);    //2015-09-22添加 for 项目申报字表行背景颜色
//                    lineViewScroll.setGravity(Gravity.CENTER_VERTICAL);
//                    lineViewScroll.setOrientation(LinearLayout.HORIZONTAL);
//                    ScrollLinear(entity, lineViewScroll);
//                    LinearLayout.LayoutParams paramsScroll = new LinearLayout.LayoutParams(
//                            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1); //创建 Linearlayout 布局参数
//                    mLinearlayout_formdetail.addView(lineViewScroll, paramsScroll);    //把一个数据实例和一个 视图加入到lineView
//                    break;
//                }
//            }
//            /**
//             * 弱弱的加个折叠箭头
//             */
//            if (isSplitRegion && SplitAction == 1) {
//                im_down = new ImageView(HtmitechApplication.getApplication());
//                im_down.setImageResource(R.drawable.btn_angle_down_circle);
//                //默认是外层的backcolor颜色
//                im_down.setBackgroundColor(SysConvertUtil.formattingH(entity.getRegion_backcolor()));
//                lineView.setTag(R.id.tag_second, im_down);
//                LinearLayout.LayoutParams param_down = new LinearLayout.LayoutParams(height, height); //创建 Linearlayout 布局参数
//                LinearLayout.LayoutParams param_image = new LinearLayout.LayoutParams(height, height - 3); //创建 Linearlayout 布局参数
//                im_down.setLayoutParams(param_image);
//                param_down.gravity = Gravity.CENTER_VERTICAL;
//                if (fieldItemList.get(i).getFiled_backcolor() != 0 && fieldItemList.get(i).getFiled_backcolor() != -1) {//如果里面内容的背景色不为空并且部位-1  那么下拉箭头的颜色的是里面backcolor的颜色
//                    im_down.setBackgroundColor(SysConvertUtil.formattingH(fieldItemList.get(i).getFiled_backcolor()));
//                }
//                LinearLayout ll_imageview = new LinearLayout(HtmitechApplication.instance());
//                ll_imageview.setOrientation(LinearLayout.VERTICAL);
//                TextView tv_line = new TextView(HtmitechApplication.instance());
//                tv_line.setBackgroundColor(getResources().getColor(R.color.imageline));
//                LinearLayout.LayoutParams param_line = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 3); //创建 Linearlayout 布局参数
//                tv_line.setLayoutParams(param_line);
//                ll_imageview.addView(im_down);
//                ll_imageview.addView(tv_line);
//                lineView.addView(ll_imageview, param_down);
//            }
//            return;
//        }
//        boolean flag = true;
//        for (int i = 0; i < fieldItemList.size(); i++) {
//            /**
//             * 如果是意见那种高度设置为matchparent，其他的设置成定值
//             */
//            if (fieldItemList.get(i).getInput_type() != null && !fieldItemList.get(i).getInput_type().equals("")) {
//                if (Integer.parseInt(fieldItemList.get(i).getInput_type().trim()) == 2003 ||
//                        Integer.parseInt(fieldItemList.get(i).getInput_type().trim()) == 2002 ||
//                        Integer.parseInt(fieldItemList.get(i).getInput_type().trim()) == 2004 ||
//                        Integer.parseInt(fieldItemList.get(i).getInput_type().trim()) == 2001 ||
//                        Integer.parseInt(fieldItemList.get(i).getInput_type().trim()) == 6001 ||
//                        Integer.parseInt(fieldItemList.get(i).getInput_type().trim()) == 6002 ||
//                        Integer.parseInt(fieldItemList.get(i).getInput_type().trim()) == 6101 ||
//                        Integer.parseInt(fieldItemList.get(i).getInput_type().trim()) == 6102 ||
//                        Integer.parseInt(fieldItemList.get(i).getInput_type().trim()) == 501 ||
//                        Integer.parseInt(fieldItemList.get(i).getInput_type().trim()) == 502 ||
//                        Integer.parseInt(fieldItemList.get(i).getInput_type().trim()) == 503 ||
//                        Integer.parseInt(fieldItemList.get(i).getInput_type().trim()) == 511 ||
//                        Integer.parseInt(fieldItemList.get(i).getInput_type().trim()) == 512 ||
//                        Integer.parseInt(fieldItemList.get(i).getInput_type().trim()) == 513 ||
//                        Integer.parseInt(fieldItemList.get(i).getInput_type().trim()) == 102 ||
//                        Integer.parseInt(fieldItemList.get(i).getInput_type().trim()) == 101 ||
//                        Integer.parseInt(fieldItemList.get(i).getInput_type().trim()) == 514) {
//                    flag = false;
//                    break;
//                }
//            }
//        }
//        if (flag) {
//            boolean isSing = false;
//            //获取屏幕宽度
//            Resources resources = this.getResources();
//            DisplayMetrics dm = resources.getDisplayMetrics();
//            int screenWidth = dm.widthPixels;
//            for (int i = 0; i < fieldItemList.size(); i++) {
//                int allWidth = DensityUtil.sp2px(HtmitechApplication.instance(), 20) * fieldItemList.get(i).getValue().length();
//                int cellWidth = screenWidth * fieldItemList.get(i).getPercent() / 100;
//                isSing = allWidth > ((cellWidth * 2) - (DeviceUtils.dip2px(HtmitechApplication.getApplication(), 5) * 2));
//            }
//
//            for (int i = 0; i < fieldItemList.size(); i++) {
//                // 如果大于30个字符就用字符的长度除以30在乘以height取整 在加一
//                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, (isSing) ? LinearLayout.LayoutParams.MATCH_PARENT : height, fieldItemList.get(i).getPercent());
//                params.gravity = Gravity.CENTER_VERTICAL;
//                lineView.setGravity(Gravity.CENTER_VERTICAL);
//                lineView.addView(initOneCell(fieldItemList.get(i)), params);            //初始化每一个表单项里的小name value
//            }
//
//
//        } else {
//            //意见类 高度设置成matchparent
//            for (int i = 0; i < fieldItemList.size(); i++) {
//                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, fieldItemList.get(i).getPercent());   // 宽 0 利用weight 控制， weight 利用表单占比
//                LinearLayout.LayoutParams params_round = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);   // 宽 0 利用weight 控制， weight 利用表单占比
//                params.gravity = Gravity.CENTER_VERTICAL;
//                lineView.setGravity(Gravity.CENTER_VERTICAL);
//                LinearLayout linearLayout = initOneCell(fieldItemList.get(i));
//                linearLayout.setPadding(0, height / 5, 0, height / 5);
//                LinearLayout linearlayoutround = new LinearLayout(HtmitechApplication.getInstance());
//                linearlayoutround.addView(linearLayout, params_round);
//                try {
//                    linearlayoutround.setBackgroundColor(SysConvertUtil.formattingH(fieldItemList.get(i).getFiled_backcolor()));
//                } catch (Exception e) {
//
//                }
//
//                lineView.addView(linearlayoutround, params);            //初始化每一个表单项里的小name value
////                }
//            }
//
//        }
//        /**
//         * 加上后面的折叠箭头
//         */
//        if (isSplitRegion && SplitAction == 1) {
//            im_down = new ImageView(HtmitechApplication.getApplication());
//            im_down.setImageResource(R.drawable.btn_angle_up_circle);
//            //默认是外层的backcolor颜色
//            im_down.setBackgroundColor(SysConvertUtil.formattingH(entity.getRegion_backcolor()));
//            lineView.setTag(R.id.tag_second, im_down);
//            LinearLayout.LayoutParams param_down = new LinearLayout.LayoutParams(height, height); //创建 Linearlayout 布局参数
//            LinearLayout.LayoutParams param_image = new LinearLayout.LayoutParams(height, height - 3); //创建 Linearlayout 布局参数
//            im_down.setLayoutParams(param_image);
//            param_down.gravity = Gravity.CENTER_VERTICAL;
//            if (fieldItemList.get(i).getFiled_backcolor() != 0 && fieldItemList.get(i).getFiled_backcolor() != -1) {//如果里面内容的背景色不为空并且部位-1  那么下拉箭头的颜色的是里面backcolor的颜色
//                im_down.setBackgroundColor(SysConvertUtil.formattingH(fieldItemList.get(i).getFiled_backcolor()));
//            }
//            LinearLayout ll_imageview = new LinearLayout(HtmitechApplication.instance());
//            ll_imageview.setOrientation(LinearLayout.VERTICAL);
//            TextView tv_line = new TextView(HtmitechApplication.instance());
//            tv_line.setBackgroundColor(getResources().getColor(R.color.imageline));
//            LinearLayout.LayoutParams param_line = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 3); //创建 Linearlayout 布局参数
//            tv_line.setLayoutParams(param_line);
//            tv_line.getBackground().setAlpha((int) (0.5 * 255));
//            ll_imageview.addView(im_down);
//            ll_imageview.addView(tv_line);
//            lineView.addView(ll_imageview, param_down);
//        }
    }

//    private LinearLayout initOneCell(final Fielditems item) {
//
//        if (item.getMode() != null) {
//            int mode = Integer.parseInt(item.getMode().trim());
//            switch (mode) {
//                case 19:
//                    // 只读意见的显示和响应
//                    if (item.getOpintions() != null && item.getOpintions().size() > 0) {
//                        //意见字段的显示
//                        ModeOpinion19 mModeOpinion19 = new ModeOpinion19(GeneralFormDetailFragment.this.getActivity());
//                        return mModeOpinion19.modeOpinionLayoutCommonForm(VlineVisible, item, mInflater, list_tvsize, ((GeneralFormDetalActivity) getActivity()).com_workflow_mobileconfig_IM_enabled);
//
//                    }
//                    break;
//                case 10:
//                    // 只读意见的显示和响应
//                    int input = Integer.parseInt(item.getInput_type().trim());
//                    switch (input) {
//                        case 2004:
//                            ModeOpinion19 mModeOpinion19 = new ModeOpinion19(GeneralFormDetailFragment.this.getActivity());
//                            return mModeOpinion19.modeOpinionLayoutCommonForm(VlineVisible, item, mInflater, list_tvsize, ((GeneralFormDetalActivity) getActivity()).com_workflow_mobileconfig_IM_enabled);
//                        case 6001:
//                        case 6002:
//                        case 6101:
//                        case 6102:
//                            SelectPhoto6001_6002_6101_6102 mSelectPhoto6001_6002_6101_6102 = new SelectPhoto6001_6002_6101_6102(GeneralFormDetailFragment.this.getActivity());
//                            ((GeneralFormDetalActivity) getActivity()).formMap.put(item.getField_id(), mSelectPhoto6001_6002_6101_6102);
////                            return mSelectPhoto6001_6002_6101_6102.Photo6102CommonForm(VlineVisible, item, mInflater, list_tvsize, new CellOnclickLisener6001_6002_6101_6102(item), ((GeneralFormDetalActivity) getActivity()).com_workflow_mobileconfig_IM_enabled);
//
//                    }
//                    break;
//            }
//        }
//        final String value = item.getBefore_value_str() + item.getValue().replace("\\\\r\\\\n", "\r\n")      //得到value
//                + item.getEnd_value_str();
//        // 可编辑字段处理
//        if (item.getMode() != null && item.getInput_type() != null                        //mode为1 进入可编辑项 大循环
//                && item.getMode().equals("1") && !item.getInput_type().equals("")) {
//
//            // 是否是必填字段，
//            // 必填字段
//            EditFieldList mustFieldList = EditFieldList.getInstance();                //每一个可编辑项都会返回唯一的必填项集合
//
//            if (item.isMustinput()) {
//                EditField mustInput = new EditField();                                //如果该项是必填项，创建必填项 Edit
//                mustInput.setKey(item.getKey());                                    //拿到key value 名字 放到 必填项列表
//                mustInput.setValue(item.getValue());
//                mustInput.setInput(item.getInput_type());
//                mustInput.setNameDesc(item.getBefore_name_str()
//                        + item.getName() + item.getEnd_name_str());
//                String name = PreferenceUtils                                    //单击布局拿到当前用户
//                        .getOAUserName(this.getActivity());
//                mustInput.tempValue = item.getValue().contains(name) ? ("" + item.getValue()) : "";
//
//
//                mustFieldList.getList().add(mustInput);
//
//
//                //特殊处理，如果是1011类型的必填字段，则需要判断之前的意见或签名是否包含当然人的
//                if (Integer.parseInt(item.getInput_type().trim()) == 2003) {
//
//                    boolean hasCurrentUserOption = false;
//
//                    if (item.getOpintions() != null && item.getOpintions().size() > 0) {
//                        for (int i = 0; i < item.getOpintions().size(); i++) {
//                            if (item.getOpintions().get(i).user_id.equalsIgnoreCase(OAConText
//                                    .getInstance(HtmitechApplication.instance()).UserID)) {
//                                hasCurrentUserOption = true;
//                                break;
//                            }
//                        }
//                    }
//
//                    if (!hasCurrentUserOption)
//                        mustInput.setValue("");
//                } else if (Integer.parseInt(item.getInput_type().trim()) == 2004) {
//                    if (item.getOpintions() != null && item.getOpintions().size() > 0) {
//                        for (int i = 0; i < item.getOpintions().size(); i++) {
//                            mustInput.tempValue = item.getOpintions().get(0).opinion_text;
//                            break;
//                        }
//                    }
//                }
//
//
//            }
//
//            int input = Integer.parseInt(item.getInput_type().trim());
//            switch (input) {
//                case 2003:
//                    EditableOpinionSig2003 mEditableOpinionSig2003 = new EditableOpinionSig2003(GeneralFormDetailFragment.this.getActivity());
//                    return mEditableOpinionSig2003.editableOpinionSigCommonForm(VlineVisible, item, mInflater, list_tvsize, list_rbsize, mEditFileds, new YiJianOnclickLisener(), ((GeneralFormDetalActivity) getActivity()).com_workflow_mobileconfig_IM_enabled);
//
//                case 2001:// 可编辑字段处理,意见
//                    FormOpinion2001 mFormOpinion2001 = new FormOpinion2001(GeneralFormDetailFragment.this.getActivity());
//
//                    return mFormOpinion2001.Opinion2001CommonForm(VlineVisible, item, mInflater, list_tvsize, new CellOnclickLisener(), ((GeneralFormDetalActivity) getActivity()).com_workflow_mobileconfig_IM_enabled);
//
//                case 2002:
//                    // 1，**********************处理可编辑的意见字段******************************
//                    EditableOpinion2002 mEditableOpinion2002 = new EditableOpinion2002(GeneralFormDetailFragment.this.getActivity());
//                    return mEditableOpinion2002.editableOpinionCommonForm(VlineVisible, item, mInflater, list_tvsize, mEditFileds);
//
//                case 2004:
//                    if (item.getOpintions() != null && item.getOpintions().size() > 0) {
//
//                        FormOpinion2004 mFormOpinion2004 = new FormOpinion2004(GeneralFormDetailFragment.this.getActivity());
//                        return mFormOpinion2004.Opinion2004CommonForm(VlineVisible, item, mInflater, list_tvsize, new CellOnclickLisener2004(item.getOpintions().get(0).opinion_text), ((GeneralFormDetalActivity) getActivity()).com_workflow_mobileconfig_IM_enabled);
//                    } else {
//                        FormOpinion2004 mFormOpinion2004 = new FormOpinion2004(GeneralFormDetailFragment.this.getActivity());
//                        return mFormOpinion2004.Opinion2004CommonForm(VlineVisible, item, mInflater, list_tvsize, new CellOnclickLisener2004(""), ((GeneralFormDetalActivity) getActivity()).com_workflow_mobileconfig_IM_enabled);
//                    }
//
////                    SelectPhoto6001_6002_6101_6102 mSelectPhoto6001_6002_6101_6102 = new SelectPhoto6001_6002_6101_6102(GeneralFormDetailFragment.this.getActivity());
////                    ((GeneralFormDetalActivity)getActivity()).formMap.put(item.getKey(),mSelectPhoto6001_6002_6101_6102);
////                    return mSelectPhoto6001_6002_6101_6102.Photo6102CommonForm(VlineVisible, item, mInflater, list_tvsize, new CellOnclickLisener6001_6002_6101_6102(item), ((GeneralFormDetalActivity) getActivity()).com_workflow_mobileconfig_IM_enabled);
//
//                case 6001:
//                case 6002:
//                case 6101:
//                case 6102:
//                    SelectPhoto6001_6002_6101_6102 mSelectPhoto6001_6002_6101_6102 = new SelectPhoto6001_6002_6101_6102(GeneralFormDetailFragment.this.getActivity());
//                    ((GeneralFormDetalActivity) getActivity()).formMap.put(item.getField_id(), mSelectPhoto6001_6002_6101_6102);
////                    return mSelectPhoto6001_6002_6101_6102.Photo6102CommonForm(VlineVisible, item, mInflater, list_tvsize, new CellOnclickLisener6001_6002_6101_6102(item), ((GeneralFormDetalActivity) getActivity()).com_workflow_mobileconfig_IM_enabled);
//                case 412://获取Name支持输入(下拉框选择模式)
//                case 403://获取Value
//                case 402://获取Name
//                case 401://获取ID
//
//                    SpringLayout412_403_402_401 mSpringLayout412_403_402_401 = new SpringLayout412_403_402_401(GeneralFormDetailFragment.this.getActivity());
//                    mSpringLayout412_403_402_401.setOptions(options, values, ids);
//                    return mSpringLayout412_403_402_401.springLayoutCommonForm(VlineVisible, item, mInflater, list_tvsize, mComboBoxList, list_comboboxsize, mEditFileds, this);
//
//                case 300:
//                case 301:
//                    // ***********************日期选择***************************
//                    CheckDate300_301 mCheckDate300_301 = new CheckDate300_301(GeneralFormDetailFragment.this.getActivity());
//                    return mCheckDate300_301.getCheckDateLayoutCommonForm(VlineVisible, item, mInflater, list_tvsize, mEditFileds, mPopBirthHelper, currentEditTextView);
//                case 3011://读者多选
//                case 3012://作者多选
//                case 611:
//                case 612://人员多选
//                case 613:
//                    PeopleCheck611_612_613 mPeopleCheck611_612_613 = new PeopleCheck611_612_613(GeneralFormDetailFragment.this.getActivity());
//                    return mPeopleCheck611_612_613.peopleChckLayoutCommonForm(VlineVisible, item, mInflater, currentEditTextView, GeneralFormDetailFragment.this);
//                case 911:
//                case 912:
//                    //912 表示选择部门返回ID  911 是返回NAME
//                    DepartmentCheck911_912 mDepartmentCheck911_912 = new DepartmentCheck911_912(GeneralFormDetailFragment.this.getActivity());
//                    return mDepartmentCheck911_912.departmentLayoutCommonForm(VlineVisible, item, mInflater, currentEditTextView, GeneralFormDetailFragment.this);
//                case 3001: //读者单选
//                case 3002://作者单选
//                case 601:
//                case 602:
//                case 603: //人员单选
//                    RadioPeople601_02_03 mRadioPeople601_02_03 = new RadioPeople601_02_03(GeneralFormDetailFragment.this.getActivity());
//                    return mRadioPeople601_02_03.peopleChckLayoutCommonForm(VlineVisible, item, mInflater, currentEditTextView, GeneralFormDetailFragment.this);
//                case 901:
//                case 902:
//                    RadioDepartment901_902 mRadioDepartment901_902 = new RadioDepartment901_902(GeneralFormDetailFragment.this.getActivity());
//                    return mRadioDepartment901_902.departmentLayoutCommonForm(VlineVisible, item, mInflater, currentEditTextView, GeneralFormDetailFragment.this);
//                case 501://单选框结果取ID
//                case 502://单选框结果取Name
//                case 503://单选框结果取Value
//                case 511://复选框结果取ID
//                case 512://复选框结果取Name
//                case 513://复选框结果取Value
//                    RadioButton501_02_03_11_513 mRadioButton501_02_03_11_513 = new RadioButton501_02_03_11_513(GeneralFormDetailFragment.this.getActivity());
//                    return mRadioButton501_02_03_11_513.radioButtonLayoutCommonForm(VlineVisible, item, mInflater, mEditFileds);
//                case 514: //字典
//                    Zidian514 zidian = new Zidian514(GeneralFormDetailFragment.this.getActivity());
//                    return zidian.radioButtonLayoutCommonForm(VlineVisible, item, mInflater, mEditFileds);
//                default:// 4，**********************其他非意见可编辑字段**********************
//                    OtherLayout otherLayout = new OtherLayout(GeneralFormDetailFragment.this.getActivity());
//                    return otherLayout.otherLayoutMethCommonForm(VlineVisible, item, mInflater, list_tvsize, list_etsize, currentEditText, mEditFileds);
//
//
//            }
//
//
//        }
//
//        // //////////////其他只读字段的显示
//        LinearLayout layout = null;
//        if (item.isName_rn())
//            layout = (LinearLayout) mInflater.inflate(
//                    R.layout.layout_formdetail_cell_vertical_lib, null);
//        else
//            layout = (LinearLayout) mInflater.inflate(
//                    R.layout.layout_formdetail_cell_lib, null);
//
//        if (VlineVisible) {
////            layout.setBackgroundResource(R.drawable.corners_bg_white_press_stroke);
//        } else {
//            layout.setBackgroundResource(R.drawable.corners_bg_grey_press_stroke);
//        }
//
//        TextView tv = (TextView) layout.findViewById(R.id.form_fielditem_text);
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
//        int nameColor = HtmitechApplication.instance().getResources()
//                .getColor(R.color.color_ff555555);
//
//        int valueColor = nameColor;
//
//        //2015-09-22添加 for 项目申报字表行背景颜色
//        if (item.getName_fontcolor() != 0)
//            nameColor = item.getName_fontcolor();
//
//        if (item.getValue_fontcolor() != 0)
//            valueColor = item.getValue_fontcolor();
//
//        boolean isNameVisible = item.isName_visible();
//        if (isNameVisible) {
//            String name = item.getBefore_name_str() + item.getName()
//                    + item.getEnd_name_str();
//            String split = item.getSplit_str();
//            text += name + split;
//            nameLength = item.getName().length() + split.length();
//            splitLength = split.length();
//            if (item != null && item.getName_color() != null && item.getName_color().equalsIgnoreCase("red")) {
//                nameColor = Color.RED;
//            }
//        }
//
//        if (item.isName_rn() && value.length() > 0) {
//            text += "\n";
//        }
//        text += value;
//        final String valueTemp = value;
//        if (item != null && item.getName_color() != null && item.getValue_color().equalsIgnoreCase("red")) {
//            valueColor = Color.RED;
//        }
//        if (item.getName().contains("电话") || item.getName().contains("手机")
//                || item.getName().contains("联系")) {
//            if (value.length() == 7 || value.length() == 8
//                    || value.length() == 11) {
//                if (isNumeric(value)) {
//                    valueColor = Color.parseColor("#5782ab");
//                    tv.setOnClickListener(new OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Uri uri = Uri.parse("tel:" + valueTemp);
//                            Intent intent = new Intent(Intent.ACTION_DIAL, uri);
//                            // 直接打出去的action
//                            // intent.setAction("android.intent.action.CALL");
//                            startActivity(intent);
//                        }
//                    });
//                }
//            }
//        }
//
//        SpannableStringBuilder spanBuilder = new SpannableStringBuilder(text);
//        if (nameLength > 0) {
//            spanBuilder.setSpan(new ForegroundColorSpan(nameColor), 0,
//                    nameLength, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
//            //spanBuilder.setSpan(new BackgroundColorSpan(123));
//        }
//        if (value.length() > 0) {
//            spanBuilder
//                    .setSpan(new ForegroundColorSpan(valueColor), nameLength,
//                            text.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
//        }
//        tv.setText(spanBuilder);
//        if (item.getFiled_backcolor() != 0 && item.getFiled_backcolor() != -1) {
//
//            if (layout.findViewById(R.id.form_fielditem_option).getVisibility() == View.GONE && layout.findViewById(R.id.form_fielditem_editimage).getVisibility() == View.GONE) {
//                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
//                params.setMargins(5, 0, 1, 1);
//                params.gravity = Gravity.CENTER_VERTICAL;
//                tv.setLayoutParams(params);
////                tv.setGravity(Gravity.CENTER);
//            }
//            tv.setBackgroundColor(SysConvertUtil.formattingH(item.getFiled_backcolor())); //2015-09-22添加 for 项目申报字表字段背景颜色
//            layout.setBackgroundColor(SysConvertUtil.formattingH(item.getFiled_backcolor()));
//        }
//        //距离左侧5dp
//        int left = DeviceUtils.dip2px(HtmitechApplication.getApplication(), 5);
//        tv.setPadding(left, 5, 20, 5);
//        return layout;
//    }

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
//        handle_doAction_hasAuthor(AuthorInfoTemp);
    }

    // /人员选择确定  作废
//    private void handle_doAction_hasAuthor(ArrayList<AuthorInfo> AuthorInfoTemp) {
//        // 界面选择了人
//        StringBuffer authorId = new StringBuffer();
//        StringBuffer authorName = new StringBuffer();
//        if (AuthorInfoTemp != null) {
//            for (int i = 0; i < AuthorInfoTemp.size(); i++) {
//                AuthorInfo selectUser = AuthorInfoTemp.get(i);
//                if (authorId.length() > 0) {
//                    authorId.append(";");
//                    authorName.append(";");
//                }
//                authorId.append(selectUser.getUserID());
//                authorName.append(selectUser.getUserName());
//            }
//        }
//        // 将值保存到编辑的字段中，并判断是否需要更新必填字段
//        // 1,将已经编辑的字段和值放入到办理是需要回发的EditField[] 中
//        String strValue = authorId.toString(); // U_id1;U_id2
//        String strName = authorName.toString(); // 姓名1;姓名2
//        Editfields edit = (Editfields) currentEditTextView.getTag();
//        if (mEditFileds != null && mEditFileds.size() == 0) {
//            mEditFileds.clear();
//            mEditFileds.add(edit);
//        } else {
//            boolean hasfind = false;
//            for (int j = 0; j < mEditFileds.size(); j++) {
//                if (mEditFileds.get(j).getKey().equals(edit.getKey())) {
//                    hasfind = true;
//                    mEditFileds.get(j).setValue(edit.getValue());
//                    break;
//                }
//            }
//            if (!hasfind)
//                mEditFileds.add(edit);
//        }
//        Log.d("FormFragment", "mEditFileds:" + mEditFileds);
//        // 2016-1-11
//        FragmentActivity sfActivity = getActivity();
//        GetDetailEntity mGetDetailEntity = null;
//        if (sfActivity != null && sfActivity instanceof GeneralFormDetalActivity) {
//            mGetDetailEntity = ((GeneralFormDetalActivity) getActivity()).mGetDetailEntity;
//        }
//        if (mGetDetailEntity.getResult() != null
//                && mGetDetailEntity.getResult().getEditfields() != null
//                && mGetDetailEntity.getResult().getEditfields().size() == 0) {
//            mGetDetailEntity.getResult().setEditfields(mEditFileds);
//        } else {
//            for (Editfields mEditfieldsTemp : mEditFileds) {
//                mGetDetailEntity.getResult().getEditfields().add(mEditfieldsTemp);
//            }
//        }
//
//        // 2,必填字段处理--将输入的意见放入相应必填字段中
//        EditFieldList mustFieldList = EditFieldList.getInstance();
//        for (int i = 0; i < mustFieldList.getList().size(); i++) {
//            if (mustFieldList
//                    .getList()
//                    .get(i)
//                    .getKey()
//                    .equalsIgnoreCase(
//                            ((EditField) GeneralFormDetailFragment.this.currentEditTextView
//                                    .getTag()).getKey())) {
//                mustFieldList.getList().get(i).setValue(strValue);
//            }
//        }
//        currentEditTextView.setText("");
//        currentEditTextView.setText(strName);
//
//    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 10002) {
            if (resultCode == Activity.RESULT_OK) {
                Fielditems common_item = (Fielditems) imageParme.get(imageFilePath);
                Log.e(TAG, "onActivityResult: " + common_item.getKey());
                PhotoModel mPhotoModel = new PhotoModel();
                mPhotoModel.setOriginalPath(imageFilePath);
                mPhotoModel.setItem_common(common_item);
                List<PhotoModel> listImgTemp = new ArrayList<PhotoModel>();
                listImgTemp.add(mPhotoModel);
//                if (((GeneralFormDetalActivity) getActivity()).formMap.get(common_item.getField_id()) != null && ((GeneralFormDetalActivity) getActivity()).formMap.get(common_item.getField_id()) instanceof SelectPhoto6001_6002_6101_6102) {
//                    ((SelectPhoto6001_6002_6101_6102) ((GeneralFormDetalActivity) getActivity()).formMap.get(common_item.getField_id())).updateImg(getActivity(), listImgTemp,0);
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
//                    if (getActivity() instanceof DetailActivity) {
//                        ((DetailActivity) getActivity()).setComment(strOption);
//                    } else if (getActivity() instanceof GeneralFormDetalActivity) {
//                        ((GeneralFormDetalActivity) getActivity()).setComment(strOption);
//                    }
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
                    if (!DetailActivity2.currentActivity) {
//                        if (getActivity() instanceof DetailActivity) {
//                            ((DetailActivity) getActivity()).setComment(strOption);
//                        } else if (getActivity() instanceof GeneralFormDetalActivity) {
//                            ((GeneralFormDetalActivity) getActivity()).setComment(strOption);
//                        }
                    }
                    // 显示时加上当前时间
                    tvOption.setText(strOption + "\n"
                            + DateFormatUtils.format(new Date(), "yyyy-M-d h:mm"));
                } else if (strOption.length() == 0) {
                    tvOption.setText("");
                    tvOption.setVisibility(View.INVISIBLE);
                }
            }


        }


        // 1,将已经编辑的字段和值放入到办理是需要回发的EditField[] 中
        Editfields edit = (Editfields) GeneralFormDetailFragment.this.mClickView.getTag();
        edit.setValue(strOption);
        if (mEditFileds != null && mEditFileds.size() == 0)
            mEditFileds.add(edit);
        else {
            boolean hasfind = false;
            for (int j = 0; j < mEditFileds.size(); j++) {
                if (mEditFileds.get(j).getKey().equals(edit.getKey())) {
                    hasfind = true;
                    mEditFileds.get(j).setValue(edit.getValue());
                    break;
                }
            }
            if (!hasfind)
                mEditFileds.add(edit);
        }
        Log.d("FormFragment", "mEditFileds:" + mEditFileds);
        // 2015-8-17
        GetDetailEntity mGetDetailEntity = null;
        if (DetailActivity2.currentActivity) {
//            mGetDetailEntity = ((DetailActivity2) getActivity()).mDocResultInfo;
        } else {
//            mGetDetailEntity = ((GeneralFormDetalActivity) getActivity()).mGetDetailEntity;
        }
        // DocResultInfo mDocResultInfo = ((DetailActivity)
        // getActivity()).mDocResultInfo;

        if (mEditFileds != null && mEditFileds.size() > 0) {
            List<Editfields> mEditFiledsTemp = mEditFileds;
            for (int i = 0; i < mEditFiledsTemp.size(); i++) {
                if (mGetDetailEntity.getResult().getEditfields().size() != 0) {
                    List<Editfields> mEditFiledsTemp2 = mGetDetailEntity.getResult().getEditfields();
                    for (int j = 0; j < mEditFiledsTemp2.size(); j++) {
                        if (mEditFiledsTemp.get(i).getKey().equals(mEditFiledsTemp2.get(j).getKey())) {
                            mGetDetailEntity.getResult().getEditfields().remove(j);
                        }
                    }
                }
                mGetDetailEntity.getResult().getEditfields().add(mEditFiledsTemp.get(i));

            }

        }

        // ro.getEt().setTag(mEditFileds);
        // 2,必填字段处理--将输入的意见放入相应必填字段中
        EditFieldList mustFieldList = EditFieldList.getInstance();

        for (int i = 0; i < mustFieldList.getList().size(); i++) {
            if (mustFieldList
                    .getList()
                    .get(i)
                    .getKey()
                    .equalsIgnoreCase(
                            ((Editfields) GeneralFormDetailFragment.this.mClickView.getTag())
                                    .getKey())) {
                mustFieldList.getList().get(i).tempValue = strOption;
                mustFieldList.getList().get(i).setValue(strOption);
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
                intent.putExtra("app_id", app_id);
                intent.putExtra("is2004", true); //是否屏蔽常用意见列表
                startActivityForResult(intent, 1);


            }

            // *********************测试临时代码******************************
            GeneralFormDetailFragment.this.mClickView = v;

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
                intent.putExtra("is2004", true); //是否屏蔽常用意见列表
                startActivityForResult(intent, 0); // 不用ActivityResult 还可以用什么？
            }

            // *********************测试临时代码******************************
            GeneralFormDetailFragment.this.mClickView = v;

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
            if (getActivity() != null
                    && (DetailActivity2.currentActivity == false)) {
                Intent intent = new Intent(getActivity(),
                        OpinionInputActivity.class);
                intent.putExtra("app_id", app_id);
                intent.putExtra("is2004", true); //是否屏蔽常用意见列表
                intent.putExtra("textValue", textValue); //是否屏蔽常用意见列表
                startActivityForResult(intent, 0); // 不用ActivityResult 还可以用什么？
            }

            // *********************测试临时代码******************************
            GeneralFormDetailFragment.this.mClickView = v;

        }

    }

    public class CellOnclickLisener6001_6002_6101_6102 implements OnClickListener {// 设置表格项的监听器
        Fielditems item;

        public CellOnclickLisener6001_6002_6101_6102(Fielditems item) {
            this.item = item;
        }

        @Override
        public void onClick(View v) {

            if (v.getId() == com.htmitech.addressbook.R.id.bt_send) {
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("common_item", item);
//                params.put("photo_number", ((SelectPhoto6001_6002_6101_6102) ((DetailActivity) getActivity()).formMap.get(item.getField_id())).mListPickerAudioSelect.size() );
                ArrayList<PhotoModel> album_select = new ArrayList<PhotoModel>();
//                for (PhotoModel model : ((SelectPhoto6001_6002_6101_6102) ((GeneralFormDetalActivity) getActivity()).formMap.get(item.getField_id())).mListPickerAudioSelect) {
//                    if (model.isChecked()) {
//                        album_select.add(model);
//                    }
//                }
                params.put("album_select", album_select);
//                params.put("photo_number", ((SelectPhoto6001_6002_6101_6102) ((GeneralFormDetalActivity) getActivity()).formMap.get(item.getField_id())).mListPickerAudioSelect.size() - album_select.size());
                ActivityUnit.switchTo(getActivity(), PhotoSelectorActivity.class, params);
                menuWindow.dismiss();
            } else if (v.getId() == com.htmitech.addressbook.R.id.bt_call) {
                imageFilePath =  FileSizeUtil.getTempPhotoFileName(OAConText.getInstance(getActivity()).UserID,BookInit.getInstance().getPortalId(),app_id);
                File temp = new File(imageFilePath);
                Uri imageFileUri = Uri.fromFile(temp);//获取文件的Uri
                Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//跳转到相机Activity
                it.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageFileUri);//告诉相机拍摄完毕输出图片到指定的Uri
                imageParme.put(imageFilePath, item);
                startActivityForResult(it, 10002);
                menuWindow.dismiss();
            } else if (v.getId() == com.htmitech.addressbook.R.id.bt_save) {
                menuWindow.dismiss();
            }
//            else if(v.getId() ==R.id.img_delete){
//                Object obj = v.getTag();
//                PhotoModel  mphotoModel = (PhotoModel) obj;
//                listImg.remove(mphotoModel);
//                if( ((GeneralFormDetalActivity)getActivity()).formMap.get(mphotoModel.getItem().getField_id())!=null&& ((GeneralFormDetalActivity)getActivity()).formMap.get(mphotoModel.getItem().getField_id()) instanceof SelectPhoto6001_6002_6101_6102 ){
//                    ((SelectPhoto6001_6002_6101_6102)((GeneralFormDetalActivity)getActivity()).formMap.get(mphotoModel.getItem().getField_id())).updateImg(getActivity(),listImg);
//                }
////                        updateImg(context, mListPickerAudioSelect);
//            }
            else {
                if (Integer.parseInt(item.getInput_type()) == 6001 ) {
//                    if (((SelectPhoto6001_6002_6101_6102) ((GeneralFormDetalActivity) getActivity()).formMap.get(item.getField_id())).mListPickerAudioSelect.size() >= 1) {
//                        Toast.makeText(getActivity(),"只能添加一张图片",Toast.LENGTH_SHORT).show();
//                        return;
//                    }
                    imageFilePath = FileSizeUtil.getTempPhotoFileName(OAConText.getInstance(getActivity()).UserID,BookInit.getInstance().getPortalId(),app_id);

                    File temp = new File(imageFilePath);
                    Uri imageFileUri = Uri.fromFile(temp);//获取文件的Uri
                    Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//跳转到相机Activity
                    it.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageFileUri);//告诉相机拍摄完毕输出图片到指定的Uri
                    imageParme.put(imageFilePath, item);
                    startActivityForResult(it, 10003);

                } else if (Integer.parseInt(item.getInput_type()) == 6101) {
//                    if (((SelectPhoto6001_6002_6101_6102) ((GeneralFormDetalActivity) getActivity()).formMap.get(item.getField_id())).mListPickerAudioSelect.size() >=9) {
//                        Toast.makeText(getActivity(),"图片最多可以添加九张",Toast.LENGTH_SHORT).show();
//                        return;
//                    }
                    imageFilePath = FileSizeUtil.getTempPhotoFileName(OAConText.getInstance(getActivity()).UserID,BookInit.getInstance().getPortalId(),app_id);

                    File temp = new File(imageFilePath);
                    Uri imageFileUri = Uri.fromFile(temp);//获取文件的Uri
                    Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//跳转到相机Activity
                    it.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageFileUri);//告诉相机拍摄完毕输出图片到指定的Uri
                    imageParme.put(imageFilePath, item);
                    startActivityForResult(it, 10003);
                } else {
//                    if (Integer.parseInt(item.getInput_type()) == 6002&&((SelectPhoto6001_6002_6101_6102) ((GeneralFormDetalActivity) getActivity()).formMap.get(item.getField_id())).mListPickerAudioSelect.size() >= 1) {
//                        Toast.makeText(getActivity(),"只能添加一张图片",Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//                    if (Integer.parseInt(item.getInput_type()) == 6102&&((SelectPhoto6001_6002_6101_6102) ((GeneralFormDetalActivity) getActivity()).formMap.get(item.getField_id())).mListPickerAudioSelect.size() >=9) {
//                        Toast.makeText(getActivity(),"图片最多可以添加九张",Toast.LENGTH_SHORT).show();
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
            GeneralFormDetailFragment.this.mClickView = v;

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

//    @Override
//    public void checkImageSelect(ArrayList<PhotoModel> mSelectedImage, Fielditems common_item, FieldItem workflow_item) {
//
//        Log.e(TAG, "checkImageSelect: " + mSelectedImage.size() + "formKey: " + common_item);
//
//        List<PhotoModel> listImgTemp = new ArrayList<PhotoModel>();
//        if (mSelectedImage != null && mSelectedImage.size() > 0) {
//            for (PhotoModel mPhotoModel : mSelectedImage) {
//                mPhotoModel.setItem_common(common_item);
//                listImgTemp.add(mPhotoModel);
//            }
//        }
//        if (((GeneralFormDetalActivity) getActivity()).formMap.get(common_item.getField_id()) != null && ((GeneralFormDetalActivity) getActivity()).formMap.get(common_item.getField_id()) instanceof SelectPhoto6001_6002_6101_6102) {
//            ((SelectPhoto6001_6002_6101_6102) ((GeneralFormDetalActivity) getActivity()).formMap.get(common_item.getField_id())).updateImg(getActivity(), listImgTemp,1);
//        }
//
//    }



}
