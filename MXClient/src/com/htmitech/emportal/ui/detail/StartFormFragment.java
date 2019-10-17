package com.htmitech.emportal.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.htmitech.api.BookInit;
import com.htmitech.combobox.ComboBox;
import com.htmitech.commonx.util.DeviceUtils;
import com.htmitech.datepicker.PopChooseTimeHelper;
import com.htmitech.domain.SYS_Department;
import com.htmitech.domain.SYS_User;
import com.htmitech.edittext.SuperEditText;
import com.htmitech.emportal.HtmitechApplication;
import com.htmitech.emportal.R;
import htmitech.com.componentlibrary.base.MyBaseFragment;
import com.htmitech.emportal.entity.AuthorInfo;
import com.htmitech.emportal.entity.DocResultInfo;
import com.htmitech.emportal.entity.EditField;
import com.htmitech.emportal.entity.EditFieldList;
import com.htmitech.emportal.entity.InfoRegion;
import com.htmitech.photoselector.model.workflow.FieldItem;
import com.htmitech.emportal.entity.OAConText;
import com.htmitech.emportal.ui.widget.BaseDialog;
import com.htmitech.emportal.ui.widget.DialogCancelListener;
import com.htmitech.emportal.ui.widget.DialogConfirmListener;
import com.htmitech.emportal.ui.widget.MyAlertDialogFragment;
import com.htmitech.listener.CallCheckAllUserListener;
import com.htmitech.myEnum.ChooseSystemBook;
import com.htmitech.myEnum.ChooseTreeHierarchy;
import com.htmitech.myEnum.ChooseWay;
import com.htmitech.myEnum.ChooseWayEnum;
import com.htmitech.myEnum.TimeEnum;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import htmitech.com.componentlibrary.content.ConcreteLogin;
import htmitech.com.componentlibrary.unit.PreferenceUtils;

/**
 * 表单
 *
 * @author tenggang
 */
public class StartFormFragment extends MyBaseFragment implements ScrollViewListener {
    private DialogFragment mNewFragment;

    private int pullStyle = 0;
    private static final int PULLDOWN_TOREFRESH = 0;
    private static final int PULLUP_TOLOADMORE = 1;

    private int mPageNum = 1;
    private boolean has_more = false;

    private LayoutInflater mInflater;
    private View mEmptyView;
    private LinearLayout mLinearlayout_formdetail;
    private DocResultInfo mDocResultInfo;

    private EditText currentEditText;
    public TextView currentEditTextView;

    private float dmvalue = 1; // 设置字体大小用

    private int screenWidth = 0; // 屏幕寬度

    /* 表单项编辑意见操作用到的变量 —— begin */
    private List<EditField> EditFileds = new ArrayList<EditField>(); // 缓存已经编辑的表单字段，回发用。
    private View mClickView = null; // 中间变量，用以缓存当前编辑的文本框

    private ArrayList<ComboBox> mComboBoxList = null;
    private String VlineVisible = "false";
    private List<TextView> list_tvsize;
    private List<EditText> list_etsize;
    private List<RadioButton> list_rbsize;
    private List<CheckBox> list_cbsize;
    private List<ComboBox> list_comboboxsize;
    ComboBox comboxValue;
    int height = DeviceUtils.dip2px(HtmitechApplication.getApplication(), 50);

    String[] options;
    String[] values;
    String[] ids;

	/* 表单项编辑意见操作用到的变量 —— end */

    protected int getLayoutId() {
        return R.layout.fragment_form;
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param context
     * @param spValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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
        dmvalue = dm.scaledDensity;
        screenWidth = dm.widthPixels;
        mComboBoxList = new ArrayList<ComboBox>();
        initValues();
    }

    private void initValues() {
        if (getActivity() != null) {
            mEmptyView = View.inflate(getActivity(),
                    R.layout.layout_empty_view, null);
            mDocResultInfo = ((StartDetailActivity) getActivity()).mDocResultInfo;
        }
        mLinearlayout_formdetail = (LinearLayout) findViewById(R.id.linearlayout_formdetail);
        initFormDetail();
    }

    private void initFormDetail() {
        InfoRegion[] regionArray = mDocResultInfo.getResult().TabItems.get(0).Regions;
        if (regionArray != null) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 1);
            for (int i = 0; i < regionArray.length; i++) {
                LinearLayout lineView = new LinearLayout(
                        HtmitechApplication.instance());
//			 lineView.setBackgroundResource(R.drawable.corners_bg_white_press_stroke);
//			 lineView.setBackgroundColor(regionArray[i].getBackColor());
                initOneRecord(lineView, regionArray[i]);
                mLinearlayout_formdetail.addView(lineView, params);
            }
        }
    }


    private void initOneRecord(LinearLayout lineView, InfoRegion entity) {
        FieldItem[] fieldItemArray = entity.getFieldItems();
        VlineVisible = entity.getVlineVisible();

        if (fieldItemArray == null || "".equals(fieldItemArray))
            return;

			/*
             * int width = (fieldItemArray[i].getPercent() / 100 * screenWidth);
			 * LinearLayout.LayoutParams params = new
			 * LinearLayout.LayoutParams(width,
			 * LinearLayout.LayoutParams.WRAP_CONTENT);
			 */

        boolean flag = true;
        for (int i = 0; i < fieldItemArray.length; i++) {
            /**
             * 如果是意见那种高度设置为matchparent，其他的设置成定值 或者是单选多选按钮
             */
            if (fieldItemArray[i].getInput() != null && !fieldItemArray[i].getInput().equals("")) {
                if (Integer.parseInt(fieldItemArray[i].getInput().trim()) == 2003 ||
                        Integer.parseInt(fieldItemArray[i].getInput().trim()) == 2002 ||
                        Integer.parseInt(fieldItemArray[i].getInput().trim()) == 2001||
                        Integer.parseInt(fieldItemArray[i].getInput().trim()) == 501 ||
                        Integer.parseInt(fieldItemArray[i].getInput().trim()) == 502 ||
                        Integer.parseInt(fieldItemArray[i].getInput().trim()) == 503 ||
                        Integer.parseInt(fieldItemArray[i].getInput().trim()) == 511 ||
                        Integer.parseInt(fieldItemArray[i].getInput().trim()) == 511 ||
                        Integer.parseInt(fieldItemArray[i].getInput().trim()) == 513) {
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
                int allWidth = sp2px(HtmitechApplication.instance(), 20) * fieldItemArray[i].getValue().length();
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
        } else {//意见类 高度设置成matchparent
            for (int i = 0; i < fieldItemArray.length; i++) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, fieldItemArray[i].getPercent());   // 宽 0 利用weight 控制， weight 利用表单占比
                params.gravity = Gravity.CENTER_VERTICAL;
                lineView.setGravity(Gravity.CENTER_VERTICAL);
                lineView.addView(initOneCell(fieldItemArray[i]), params);            //初始化每一个表单项里的小name value
            }
        }

    }

    private LinearLayout initOneCell(final FieldItem item) {

        // 设置意见字体大小,放大
        if (item.getMode() != null
                && Integer.parseInt(item.getMode().trim()) == 19) {
            // 只读意见的显示和响应
//             tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19);
            if (item.opintions != null && item.opintions.size() > 0) {    //意见字段的显示
                LinearLayout lineView = new LinearLayout(
                        HtmitechApplication.instance());
                lineView.setOrientation(LinearLayout.VERTICAL);            //设置垂直显示
                lineView.setGravity(Gravity.CENTER_VERTICAL);
                if (VlineVisible.equalsIgnoreCase("true"))
                    lineView.setBackgroundResource(R.drawable.corners_bg_white_press_stroke);    //设置显示背景

                for (int i = 0; i < item.opintions.size(); i++) {        //查看多条意见
                    LinearLayout layout = (LinearLayout) mInflater.inflate( //填充意见布局
                            R.layout.layout_formdetail_cell_foropion_lib, null);
                    TextView name = (TextView) layout
                            .findViewById(R.id.form_fielditem_option_name);
                    list_tvsize.add(name);
                    TextView text = (TextView) layout
                            .findViewById(R.id.form_fielditem_option_text);
                    list_tvsize.add(text);
                    TextView username = (TextView) layout
                            .findViewById(R.id.form_fielditem_option_username);
                    list_tvsize.add(username);
                    TextView savetime = (TextView) layout
                            .findViewById(R.id.form_fielditem_option_saveTime);
                    list_tvsize.add(savetime);

                    /**
                     * add by heyang
                     * date 2016-7-20
                     * 让字垂直居中
                     */
                    name.setGravity(Gravity.CENTER_VERTICAL);
                    text.setGravity(Gravity.CENTER_VERTICAL);
                    username.setGravity(Gravity.CENTER_VERTICAL);
                    savetime.setGravity(Gravity.CENTER_VERTICAL);

                    boolean isNameVisible = item.isNameVisible();            //判断名称是否显示
                    if (i == 0 && isNameVisible) {                            //如果是第一个名称 并且显示
                        String strName = item.getBeforeNameString()
                                + item.getName() + item.getEndNameString(); // 拼接显示名字和结束符号
                        String split = item.getSplitString();                //拿到分割符号
                        strName = strName + split;
                        name.setVisibility(View.VISIBLE);
                        name.setText(strName);
                        text.setText(item.opintions.get(i).opinionText.replace("\\\\r\\\\n", "\r\n"));    //
                        username.setText(item.opintions.get(i).userName);
                        username.setOnClickListener(new PersonOnClick(        //当单击意见人的时候 把UserID 传进去
                                item.opintions.get(i).UserID));
                        savetime.setText(item.opintions.get(i).saveTime);
                    } else {
                        name.setVisibility(View.GONE);                        //否则不显示名字
                        text.setText(item.opintions.get(i).opinionText.replace("\\\\r\\\\n", "\r\n"));
                        username.setText(item.opintions.get(i).userName);
                        username.setOnClickListener(new PersonOnClick(
                                item.opintions.get(i).UserID));
                        savetime.setText(item.opintions.get(i).saveTime);
                    }
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                    params.gravity = Gravity.CENTER_VERTICAL;
                    lineView.addView(layout, params);                                    // 只读显示多个意见字段
                }

                return lineView;                                                            //传回意见表格
            }

        }

        // //////////////////////////////////////////////////////////////////////////
        String value = item.getBeforeValueString() + item.getValue()
                + item.getEndValueString();

        // 可编辑字段处理
        if (item.getMode() != null && item.getInput() != null
                && item.getMode().equals("1") && !item.getInput().equals("")) {


            // 是否是必填字段，
            // 必填字段
            EditFieldList mustFieldList = EditFieldList.getInstance();                //每一个可编辑项都会返回唯一的必填项集合

            if (item.isMustInput()) {
                EditField mustInput = new EditField();                                //如果该项是必填项，创建必填项 Edit
                mustInput.setKey(item.getKey());                                    //拿到key value 名字 放到 必填项列表
                mustInput.setValue(item.getValue());
                mustInput.setNameDesc(item.getBeforeNameString()
                        + item.getName() + item.getEndNameString());
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

            if (Integer.parseInt(item.getInput().trim()) == 2003) {                    //可编辑字段 输入方式为1011

                //1，**********************处理可编辑的签名和意见字段******************************
                LinearLayout lineView = new LinearLayout(                            //设置一个容器和背景及显示方式
                        HtmitechApplication.instance());
                lineView.setOrientation(LinearLayout.VERTICAL);
                lineView.setGravity(Gravity.CENTER_VERTICAL);
                if (VlineVisible.equalsIgnoreCase("true"))
                    lineView.setBackgroundResource(R.drawable.corners_bg_white_press_stroke);

                if (DetailActivity2.currentActivity)
                    lineView.setEnabled(false);

                if (item.opintions != null && item.opintions.size() > 0) {                //当存在多个意见的时候，先显示
                    // 显示之前的意见
                    for (int i = 0; i < item.opintions.size(); i++) {
                        LinearLayout layout = (LinearLayout) mInflater.inflate(
                                R.layout.layout_formdetail_cell_foropion_lib, null);
                        TextView name = (TextView) layout.findViewById(R.id.form_fielditem_option_name);
                        list_tvsize.add(name);
                        TextView text = (TextView) layout.findViewById(R.id.form_fielditem_option_text);
                        list_tvsize.add(text);
                        TextView username = (TextView) layout.findViewById(R.id.form_fielditem_option_username);
                        list_tvsize.add(username);
                        TextView savetime = (TextView) layout.findViewById(R.id.form_fielditem_option_saveTime);
                        list_tvsize.add(savetime);
                        /**
                         * add by heyang
                         * date 2016-7-20
                         * 让字垂直居中
                         */
                        name.setGravity(Gravity.CENTER_VERTICAL);
                        text.setGravity(Gravity.CENTER_VERTICAL);
                        username.setGravity(Gravity.CENTER_VERTICAL);
                        savetime.setGravity(Gravity.CENTER_VERTICAL);

                        /**
                         * add by heyang
                         * date 2016-7-18
                         * if isMustInput is true then change backgroundclolor as "color_mustinputback"
                         */
                        if (item.isMustInput()) {
                            name.setBackgroundColor(getResources().getColor(R.color.color_mustinputtouming));
                            text.setBackgroundColor(getResources().getColor(R.color.color_mustinputtouming));
                            username.setBackgroundColor(getResources().getColor(R.color.color_mustinputtouming));
                            savetime.setBackgroundColor(getResources().getColor(R.color.color_mustinputtouming));
                            lineView.setBackgroundResource(R.drawable.corners_bg_mustinput);
                        }

                        boolean isNameVisible = item.isNameVisible();
                        if (i == 0 && isNameVisible) {
                            String strName = item.getBeforeNameString()
                                    + item.getName() + item.getEndNameString();
                            String split = item.getSplitString();
                            strName = strName + split;
                            name.setVisibility(View.VISIBLE);
                            name.setText(strName);
                            if (item.getNameColor().equalsIgnoreCase("red")) {
                                name.setTextColor(Color.RED);
                            }
                            text.setText(item.opintions.get(i).opinionText.replace("\\\\r\\\\n", "\r\n"));
                            username.setText(item.opintions.get(i).userName);
                            username.setOnClickListener(new PersonOnClick(
                                    item.opintions.get(i).UserID));
                            savetime.setText(item.opintions.get(i).saveTime);
                        } else {
                            name.setVisibility(View.GONE);
                            text.setText(item.opintions.get(i).opinionText.replace("\\\\r\\\\n", "\r\n"));
                            username.setText(item.opintions.get(i).userName);
                            username.setOnClickListener(new PersonOnClick(
                                    item.opintions.get(i).UserID));
                            savetime.setText(item.opintions.get(i).saveTime);
                        }
                        if (DetailActivity2.currentActivity)
                            username.setEnabled(false);

                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT, 1);

                        params.gravity = Gravity.CENTER_VERTICAL;
                        lineView.addView(layout, params);                            //串起来添加到意见字段
                    }

                } else {                                                            //没有意见信息，那么将只显示标题
                    // 仅增加name显示
                    LinearLayout layout = (LinearLayout) mInflater.inflate(
                            R.layout.layout_formdetail_cell_foropion_lib, null);
                    TextView name = (TextView) layout
                            .findViewById(R.id.form_fielditem_option_name);
                    list_tvsize.add(name);
                    TextView text = (TextView) layout
                            .findViewById(R.id.form_fielditem_option_text);
                    list_tvsize.add(text);
                    TextView username = (TextView) layout
                            .findViewById(R.id.form_fielditem_option_username);
                    list_tvsize.add(username);
                    TextView savetime = (TextView) layout
                            .findViewById(R.id.form_fielditem_option_saveTime);
                    list_tvsize.add(savetime);
                    text.setVisibility(View.GONE);
                    username.setVisibility(View.GONE);
                    savetime.setVisibility(View.GONE);

                    /**
                     * add by heyang
                     * date 2016-7-20
                     * 让字垂直居中
                     */
                    name.setGravity(Gravity.CENTER_VERTICAL);
                    text.setGravity(Gravity.CENTER_VERTICAL);
                    username.setGravity(Gravity.CENTER_VERTICAL);
                    savetime.setGravity(Gravity.CENTER_VERTICAL);
                    /**
                     * add by heyang
                     * date 2016-7-18
                     * if isMustInput is true then change backgroundclolor as "color_mustinputback"
                     */
                    if (item.isMustInput()) {
                        name.setBackgroundColor(getResources().getColor(R.color.color_mustinputtouming));
                        text.setBackgroundColor(getResources().getColor(R.color.color_mustinputtouming));
                        username.setBackgroundColor(getResources().getColor(R.color.color_mustinputtouming));
                        savetime.setBackgroundColor(getResources().getColor(R.color.color_mustinputtouming));
                        lineView.setBackgroundResource(R.drawable.corners_bg_mustinput);
                    }

                    if (item.isNameVisible()) {
                        String strName = item.getBeforeNameString()
                                + item.getName() + item.getEndNameString();
                        String split = item.getSplitString();
                        strName = strName + split;
                        name.setVisibility(View.VISIBLE);
                        name.setText(strName);
                        if (item.getNameColor().equalsIgnoreCase("red")) {
                            name.setTextColor(Color.RED);
                        }
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                        params.gravity = Gravity.CENTER_VERTICAL;
                        lineView.addView(layout, params);                            //只实例出名字添加到 容器中
                    }
                }


                // //////////////显示签字或意见结果
                LinearLayout layout3 = null;
                if (item.isNameRN())
                    layout3 = (LinearLayout) mInflater.inflate(
                            R.layout.layout_formdetail_cell_vertical_lib, null);
                else
                    layout3 = (LinearLayout) mInflater.inflate(
                            R.layout.layout_formdetail_cell_lib, null);

                if (VlineVisible.equalsIgnoreCase("true")) {
                    layout3.setBackgroundResource(R.drawable.corners_bg_white_press_stroke);
                } else {
                    layout3.setBackgroundResource(R.drawable.corners_bg_grey_press_stroke);
                }

                final TextView tv = (TextView) layout3.findViewById(R.id.form_fielditem_text);


                /**
                 * add by heyang
                 * date 2016-7-18
                 * if mustInput is true then change the backcolor as "color_mustinputback"
                 */
                if (item.isMustInput()) {//必填字段标黄
                    tv.setBackgroundColor(getResources().getColor(R.color.color_mustinputtouming));
                    lineView.setBackgroundResource(R.drawable.corners_bg_mustinput);
                }
                list_tvsize.add(tv);
                if (item.getAlign().equalsIgnoreCase("center")) {
                    tv.setGravity(Gravity.CENTER);
                } else if (item.getAlign().equalsIgnoreCase("left")) {
                    tv.setGravity(Gravity.LEFT);
                } else if (item.getAlign().equalsIgnoreCase("right")) {
                    tv.setGravity(Gravity.RIGHT);
                }


                //---点选签名
                /**
                 * 2016 4 21 号修改 用于单击输入签名
                 */
                final String name = PreferenceUtils                                    //单击布局拿到当前用户
                        .getOAUserName(getActivity());
                LinearLayout opionlayout1 = (LinearLayout) mInflater.inflate(            //构建细节布局 现在只是宽高20dp的图片
                        R.layout.layout_formdetail_cell_qianmingyijian_lib, null);
                if (item.isMustInput() && (item.getValue() == null || item.getValue().trim().indexOf(name) == -1))//必填字段标黄
                    lineView.setBackgroundResource(R.drawable.corners_bg_mustinput);
                RadioButton rQianming = (RadioButton) opionlayout1
                        .findViewById(R.id.radioButtonQianming);
                list_rbsize.add(rQianming);

                /**
                 * add by heyang
                 * date 2016-7-20
                 * 让字垂直居中
                 */

                rQianming.setGravity(Gravity.CENTER_VERTICAL);

                EditField edit1 = new EditField();                                        //选项布局建立tag
                edit1.setKey(item.getKey());
                edit1.setSign(item.getSign());
                edit1.setInput(item.getInput());
                edit1.setMode(item.getMode());
                edit1.setFormKey(item.getFormKey());
                rQianming.setTag(edit1);

                // 2015-8-18 for DetailActivity2
                /*if (DetailActivity2.currentActivity)
                    opionlayout.setEnabled(false);*/


                rQianming.setOnClickListener(new OnClickListener() {                    //布局单击事件
                    @Override
                    public void onClick(View v) {
                        if (name != null && !"".equals(name)) {

                            tv.setText(name);

                            // TODO Auto-generated method stub
                            // 1,将已经编辑的字段和值放入到办理是需要回发的EditField[] 中

                            String strValue = "";                                        //判断有没有签名图片来决定会发的串
                            String tmp = PreferenceUtils
                                    .getAttribute1(getActivity());
                            // 有签名
                            if (!tmp.equals("null")) {
                                strValue = PreferenceUtils
                                        .getOAUserID(getActivity())
                                        + "#"
                                        + PreferenceUtils
                                        .getAttribute1(getActivity())
                                        + "#1";
                            } else {
                                strValue = PreferenceUtils
                                        .getOAUserID(getActivity())
                                        + "#"
                                        + PreferenceUtils
                                        .getOAUserName(getActivity())
                                        + "("
                                        + PreferenceUtils
                                        .getThirdDepartmentName(getActivity())
                                        + ")#2";
                            }

                            EditField edit = (EditField) v.getTag();                //回发控件的处理流程 ，从控件中拿出可编辑结构
                            edit.setValue(strValue);
                            if (EditFileds != null && EditFileds.size() == 0)        //添加到可编辑数组中
                                EditFileds.add(edit);
                            else {
                                boolean hasfind = false;
                                for (int j = 0; j < EditFileds.size(); j++) {        //遍历可回发数组 是否有相同的key，如果有直接
                                    if (EditFileds.get(j).getKey()                    //setValue 否则直接存入
                                            .equals(edit.getKey())) {
                                        hasfind = true;
                                        EditFileds.get(j).setValue(
                                                edit.getValue());
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
                                mDocResultInfo = ((StartDetailActivity) getActivity()).mDocResultInfo;
                            }

                            // DocResultInfo mDocResultInfo = ((DetailActivity)
                            // getActivity()).mDocResultInfo;
                            mDocResultInfo.getResult()
                                    .setEditFields(EditFileds);                                //刷新可回发字段集
                            // 2,必填字段处理--将输入的意见放入相应必填字段中
                            EditFieldList mustFieldList = EditFieldList
                                    .getInstance();
                            for (int i = 0; i < mustFieldList.getList().size(); i++) {        //遍历必填项，如果key相同设置value
                                if (mustFieldList
                                        .getList()
                                        .get(i)
                                        .getKey()
                                        .equalsIgnoreCase(
                                                ((EditField) v.getTag())
                                                        .getKey())) {
                                    mustFieldList.getList().get(i)
                                            .setValue(strValue);
                                }
                            }
                        }
                    }
                });


                //---点选意见 ----------

                RadioButton rYijian = (RadioButton) opionlayout1
                        .findViewById(R.id.radioButtonYijian);
                list_rbsize.add(rYijian);

                /**
                 * add by heyang
                 * date 2016-7-20
                 * 让字垂直居中
                 */

                rYijian.setGravity(Gravity.CENTER_VERTICAL);

                /**
                 * add by heyang
                 * date 2016-7-18
                 * if mustInput is true then change the backcolor as "color_mustinputback"
                 */
                if (item.isMustInput()) {//必填字段标黄
                    rYijian.setBackgroundColor(getResources().getColor(R.color.color_mustinputtouming));
                    opionlayout1.setBackgroundResource(R.drawable.corners_bg_mustinput);
                }
                EditField edit = new EditField();                                    //实例化可编辑字段数组
                edit.setKey(item.getKey());                                            //存key
                edit.setSign(item.getSign());
                edit.setInput(item.getInput());
                edit.setMode(item.getMode());
                edit.setValue(item.getValue());
                edit.setFormKey(item.getFormKey());

                edit.ShowView = tv;

                rYijian.setTag(edit);                                            //利用布局视图的tag 存住该视图的编辑项
                rYijian.setOnClickListener(new YiJianOnclickLisener());


                LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                params1.gravity = Gravity.CENTER_VERTICAL;
                lineView.addView(opionlayout1, params1);
                lineView.addView(layout3, params1);

                // 2015-8-18 for DetailActivity2
                if (DetailActivity2.currentActivity) {
                    lineView.setEnabled(false);
                    opionlayout1.setEnabled(false);
                }
                if (item.isMustInput()) {//必填字段标黄
                    lineView.setBackgroundResource(R.drawable.corners_bg_mustinput);
                }

                return lineView;


            }
            // 可编辑字段处理,意见
            else if (Integer.parseInt(item.getInput().trim()) == 2001) {                    //可编辑字段 输入方式为11
                // 1，**********************处理可编辑的意见字段******************************
                LinearLayout lineView = new LinearLayout(                            //设置一个容器和背景及显示方式
                        HtmitechApplication.instance());
                lineView.setOrientation(LinearLayout.VERTICAL);
                lineView.setGravity(Gravity.CENTER_VERTICAL);
                if (VlineVisible.equalsIgnoreCase("true"))
                    lineView.setBackgroundResource(R.drawable.corners_bg_white_press_stroke);

                if (DetailActivity2.currentActivity)
                    lineView.setEnabled(false);

                if (item.opintions != null && item.opintions.size() > 0) {                //当存在多个意见的时候，先显示
                    // 显示之前的意见
                    for (int i = 0; i < item.opintions.size(); i++) {
                        LinearLayout layout = (LinearLayout) mInflater.inflate(
                                R.layout.layout_formdetail_cell_foropion_lib, null);
                        TextView name = (TextView) layout
                                .findViewById(R.id.form_fielditem_option_name);
                        list_tvsize.add(name);
                        TextView text = (TextView) layout
                                .findViewById(R.id.form_fielditem_option_text);
                        list_tvsize.add(text);
                        TextView username = (TextView) layout
                                .findViewById(R.id.form_fielditem_option_username);
                        list_tvsize.add(username);
                        TextView savetime = (TextView) layout
                                .findViewById(R.id.form_fielditem_option_saveTime);
                        list_tvsize.add(savetime);


                        /**
                         * add by heyang
                         * date 2016-7-20
                         * 让字垂直居中
                         */

                        name.setGravity(Gravity.CENTER_VERTICAL);
                        text.setGravity(Gravity.CENTER_VERTICAL);
                        username.setGravity(Gravity.CENTER_VERTICAL);
                        savetime.setGravity(Gravity.CENTER_VERTICAL);

                        /**
                         * add by heyang
                         * date 2016-7-18
                         * if isMustInput is true then change backgroundclolor as "color_mustinputback"
                         */
                        if (item.isMustInput()) {
                            name.setBackgroundColor(getResources().getColor(R.color.color_mustinputtouming));
                            text.setBackgroundColor(getResources().getColor(R.color.color_mustinputtouming));
                            username.setBackgroundColor(getResources().getColor(R.color.color_mustinputtouming));
                            savetime.setBackgroundColor(getResources().getColor(R.color.color_mustinputtouming));
                            lineView.setBackgroundResource(R.drawable.corners_bg_mustinput);
                        }

                        boolean isNameVisible = item.isNameVisible();
                        if (i == 0 && isNameVisible) {
                            String strName = item.getBeforeNameString()
                                    + item.getName() + item.getEndNameString();
                            String split = item.getSplitString();
                            strName = strName + split;
                            name.setVisibility(View.VISIBLE);
                            name.setText(strName);
                            if (item.getNameColor().equalsIgnoreCase("red")) {
                                name.setTextColor(Color.RED);
                            }
                            text.setText(item.opintions.get(i).opinionText.replace("\\\\r\\\\n", "\r\n"));
                            username.setText(item.opintions.get(i).userName);
                            username.setOnClickListener(new PersonOnClick(
                                    item.opintions.get(i).UserID));
                            savetime.setText(item.opintions.get(i).saveTime);
                        } else {
                            name.setVisibility(View.GONE);
                            text.setText(item.opintions.get(i).opinionText.replace("\\\\r\\\\n", "\r\n"));
                            username.setText(item.opintions.get(i).userName);
                            username.setOnClickListener(new PersonOnClick(
                                    item.opintions.get(i).UserID));
                            savetime.setText(item.opintions.get(i).saveTime);
                        }
                        // 2015-8-18 for DetailActivity2
                        if (DetailActivity2.currentActivity)
                            username.setEnabled(false);

                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                        params.gravity = Gravity.CENTER_VERTICAL;

                        lineView.addView(layout, params);                            //串起来添加到意见字段
                    }

                } else {                                                            //没有意见信息，那么将只显示标题
                    // 仅增加name显示
                    LinearLayout layout = (LinearLayout) mInflater.inflate(
                            R.layout.layout_formdetail_cell_foropion_lib, null);
                    TextView name = (TextView) layout
                            .findViewById(R.id.form_fielditem_option_name);
                    list_tvsize.add(name);
                    TextView text = (TextView) layout
                            .findViewById(R.id.form_fielditem_option_text);
                    list_tvsize.add(text);
                    TextView username = (TextView) layout
                            .findViewById(R.id.form_fielditem_option_username);
                    list_tvsize.add(username);
                    TextView savetime = (TextView) layout
                            .findViewById(R.id.form_fielditem_option_saveTime);
                    list_tvsize.add(savetime);
                    text.setVisibility(View.GONE);
                    username.setVisibility(View.GONE);
                    savetime.setVisibility(View.GONE);

                    /**
                     * add by heyang
                     * date 2016-7-20
                     * 让字垂直居中
                     */

                    name.setGravity(Gravity.CENTER_VERTICAL);
                    text.setGravity(Gravity.CENTER_VERTICAL);
                    username.setGravity(Gravity.CENTER_VERTICAL);
                    savetime.setGravity(Gravity.CENTER_VERTICAL);

                    /**
                     * add by heyang
                     * date 2016-7-18
                     * if isMustInput is true then change backgroundclolor as "color_mustinputback"
                     */
                    if (item.isMustInput()) {
                        name.setBackgroundColor(getResources().getColor(R.color.color_mustinputtouming));
                        text.setBackgroundColor(getResources().getColor(R.color.color_mustinputtouming));
                        username.setBackgroundColor(getResources().getColor(R.color.color_mustinputtouming));
                        savetime.setBackgroundColor(getResources().getColor(R.color.color_mustinputtouming));
                        lineView.setBackgroundResource(R.drawable.corners_bg_mustinput);
                    }

                    if (item.isNameVisible()) {
                        String strName = item.getBeforeNameString()
                                + item.getName() + item.getEndNameString();
                        String split = item.getSplitString();
                        strName = strName + split;
                        name.setVisibility(View.VISIBLE);
                        name.setText(strName);
                        if (item.getNameColor().equalsIgnoreCase("red")) {
                            name.setTextColor(Color.RED);
                        }
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                        params.gravity = Gravity.CENTER_VERTICAL;
                        lineView.addView(layout, params);                            //只实例出名字添加到 容器中
                    }
                }
                LinearLayout opionlayout = (LinearLayout) mInflater.inflate(
                        R.layout.layout_formdetail_cell_opiontext_lib, null);            //实例化表格
                TextView tvOption = (TextView) opionlayout
                        .findViewById(R.id.form_fielditem_option);                    //新创建一个表格 包在实例里，这绝逼是新花的布局不想改逻辑
                tvOption.setVisibility(View.INVISIBLE);                                //占住位置 但是不显示
                ImageView iv_icon = (ImageView) opionlayout.findViewById(R.id.form_fielditem_editimage);
                iv_icon.setVisibility(View.GONE);
                list_tvsize.add(tvOption);
                /**
                 * add by heyang
                 * date 2016-7-18
                 * if isMustInput is true then change backgroundclolor as "color_mustinputback"
                 */
                if (item.isMustInput()) {
                    tvOption.setBackgroundColor(getResources().getColor(R.color.color_mustinputtouming));
                    lineView.setBackgroundResource(R.drawable.corners_bg_mustinput);
                }

                EditField edit = new EditField();                                    //实例化可编辑字段数组
                edit.setKey(item.getKey());                                            //存key
                edit.setSign(item.getSign());
                edit.setInput(item.getInput());
                edit.setMode(item.getMode());
                edit.setValue(item.getValue());                                        //存value
                edit.setFormKey(item.getFormKey());
                opionlayout.setTag(edit);                                            //利用布局视图的tag 存住该视图的编辑项
                opionlayout.setOnClickListener(new CellOnclickLisener());
                ImageView img = (ImageView) opionlayout
                        .findViewById(R.id.form_fielditem_editimage);
                img.setVisibility(View.VISIBLE);                                    //图标显示
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                params.gravity = Gravity.CENTER_VERTICAL;
                lineView.addView(opionlayout, params);
                // 2015-8-18 for DetailActivity2
                if (DetailActivity2.currentActivity) {
                    lineView.setEnabled(false);
                    opionlayout.setEnabled(false);
                }

                return lineView;

            } else if (Integer.parseInt(item.getInput().trim()) == 2002) {
                // 2, **********************处理可编辑的签名字段**********************

                LinearLayout lineView = new LinearLayout(                                    //建立容器
                        HtmitechApplication.instance());
                lineView.setOrientation(LinearLayout.VERTICAL);
                lineView.setGravity(Gravity.CENTER_VERTICAL);
                if (VlineVisible.equalsIgnoreCase("true"))
                    lineView.setBackgroundResource(R.drawable.corners_bg_white_press_stroke);

                if (item.opintions != null && item.opintions.size() > 0) {                    //如果有多个可签名字段
                } else {                                                                    //暂时没有
                    // 仅增加name显示														//和意见一样的布局
                    LinearLayout layout = (LinearLayout) mInflater.inflate(
                            R.layout.layout_formdetail_cell_foropion_lib, null);
                    TextView name = (TextView) layout
                            .findViewById(R.id.form_fielditem_option_name);
                    list_tvsize.add(name);
                    TextView text = (TextView) layout
                            .findViewById(R.id.form_fielditem_option_text);
                    list_tvsize.add(text);
                    TextView username = (TextView) layout
                            .findViewById(R.id.form_fielditem_option_username);
                    list_tvsize.add(username);
                    TextView savetime = (TextView) layout
                            .findViewById(R.id.form_fielditem_option_saveTime);
                    list_tvsize.add(savetime);
                    text.setVisibility(View.GONE);
                    username.setVisibility(View.GONE);                                        //签名人和签名时间、签名不显示
                    savetime.setVisibility(View.GONE);
                    /**
                     * add by heyang
                     * date 2016-7-20
                     * 让字垂直居中
                     */

                    name.setGravity(Gravity.CENTER_VERTICAL);
                    text.setGravity(Gravity.CENTER_VERTICAL);
                    username.setGravity(Gravity.CENTER_VERTICAL);
                    savetime.setGravity(Gravity.CENTER_VERTICAL);

                    /**
                     * add by heyang
                     * date 2016-7-18
                     * if isMustInput is true then change backgroundclolor as "color_mustinputback"
                     */
                    if (item.isMustInput()) {
                        name.setBackgroundColor(getResources().getColor(R.color.color_mustinputtouming));
                        text.setBackgroundColor(getResources().getColor(R.color.color_mustinputtouming));
                        username.setBackgroundColor(getResources().getColor(R.color.color_mustinputtouming));
                        savetime.setBackgroundColor(getResources().getColor(R.color.color_mustinputtouming));
                        lineView.setBackgroundResource(R.drawable.corners_bg_mustinput);
                    }
                    if (item.isNameVisible()) {                                                //如果显示名称 - 名称是显示title
                        String strName = item.getBeforeNameString()                            //拼接名称
                                + item.getName() + item.getEndNameString();
                        String split = item.getSplitString();
                        strName = strName + split;
                        name.setVisibility(View.VISIBLE);                                    //显示
                        name.setText(strName);
                        if (item.getNameColor().equalsIgnoreCase("red")) {
                            name.setTextColor(Color.RED);
                        }
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(    //布局参数
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                        params.gravity = Gravity.CENTER_VERTICAL;

                        lineView.addView(layout, params);                                    //添加到布局
/*
                        // 2015-8-18 for DetailActivity2
						if (DetailActivity2.currentActivity)
							lineView.setEnabled(false);*/
                    }
                }
                /**
                 * 2015 7 31 号修改 用于单击输入签名
                 */
                LinearLayout opionlayout = (LinearLayout) mInflater.inflate(            //构建细节布局 现在只是宽高20dp的图片
                        R.layout.layout_formdetail_cell_opiontext_lib, null);
                final TextView tvOption = (TextView) opionlayout
                        .findViewById(R.id.form_fielditem_option);
                tvOption.setVisibility(View.INVISIBLE);

                /**
                 * add by heyang
                 * date 2016-7-20
                 * 让字垂直居中
                 */

                tvOption.setGravity(Gravity.CENTER_VERTICAL);

                /**
                 * add by heyang
                 * date 2016-7-18
                 * if isMustInput is true then change backgroundclolor as "color_mustinputback"
                 */
                if (item.isMustInput()) {
                    tvOption.setBackgroundColor(getResources().getColor(R.color.color_mustinputtouming));
                    lineView.setBackgroundResource(R.drawable.corners_bg_mustinput);
                }
                list_tvsize.add(tvOption);

                EditField edit = new EditField();                                        //选项布局建立tag
                edit.setKey(item.getKey());
                edit.setSign(item.getSign());
                edit.setInput(item.getInput());
                edit.setMode(item.getMode());
                edit.setFormKey(item.getFormKey());
                opionlayout.setTag(edit);

                // 2015-8-18 for DetailActivity2
                /*if (DetailActivity2.currentActivity)
                    opionlayout.setEnabled(false);*/
                if (item.getValue() != null && !"".equals(item.getValue())) {
                    tvOption.setVisibility(View.VISIBLE);                        //表格显示，显示当前用户名
                    tvOption.setText(item.getValue());
                }

                final String name = PreferenceUtils                                    //单击布局拿到当前用户
                        .getOAUserName(getActivity());
                opionlayout.setOnClickListener(new OnClickListener() {                    //布局单击事件
                    @Override
                    public void onClick(View v) {
                        if (name != null && !"".equals(name)) {
                            tvOption.setVisibility(View.VISIBLE);                        //表格显示，显示当前用户名
                            tvOption.setText(name);

                            // TODO Auto-generated method stub
                            // 1,将已经编辑的字段和值放入到办理是需要回发的EditField[] 中

                            String strValue = "";                                        //判断有没有签名图片来决定会发的串
                            String tmp = PreferenceUtils
                                    .getAttribute1(getActivity());
                            // 有签名
                            if (!tmp.equals("null")) {
                                strValue = PreferenceUtils
                                        .getOAUserID(getActivity())
                                        + "#"
                                        + PreferenceUtils
                                        .getAttribute1(getActivity())
                                        + "#1";
                            } else {
                                strValue = PreferenceUtils
                                        .getOAUserID(getActivity())
                                        + "#"
                                        + PreferenceUtils
                                        .getOAUserName(getActivity())
                                        + "("
                                        + PreferenceUtils
                                        .getThirdDepartmentName(getActivity())
                                        + ")#2";
                            }

                            EditField edit = (EditField) v.getTag();                //回发控件的处理流程 ，从控件中拿出可编辑结构
                            edit.setValue(strValue);
                            if (EditFileds != null && EditFileds.size() == 0)        //添加到可编辑数组中
                                EditFileds.add(edit);
                            else {
                                boolean hasfind = false;
                                for (int j = 0; j < EditFileds.size(); j++) {        //遍历可回发数组 是否有相同的key，如果有直接
                                    if (EditFileds.get(j).getKey()                    //setValue 否则直接存入
                                            .equals(edit.getKey())) {
                                        hasfind = true;
                                        EditFileds.get(j).setValue(
                                                edit.getValue());
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
                                mDocResultInfo = ((StartDetailActivity) getActivity()).mDocResultInfo;
                            }

                            // DocResultInfo mDocResultInfo = ((DetailActivity)
                            // getActivity()).mDocResultInfo;
                            mDocResultInfo.getResult()
                                    .setEditFields(EditFileds);                                //刷新可回发字段集
                            // 2,必填字段处理--将输入的意见放入相应必填字段中
                            EditFieldList mustFieldList = EditFieldList
                                    .getInstance();
                            for (int i = 0; i < mustFieldList.getList().size(); i++) {        //遍历必填项，如果key相同设置value
                                if (mustFieldList
                                        .getList()
                                        .get(i)
                                        .getKey()
                                        .equalsIgnoreCase(
                                                ((EditField) v.getTag())
                                                        .getKey())) {
                                    mustFieldList.getList().get(i)
                                            .setValue(strValue);
                                }
                            }
                        }
                    }
                });

                ImageView img = (ImageView) opionlayout
                        .findViewById(R.id.form_fielditem_editimage);                //显示可编辑图片
                img.setVisibility(View.VISIBLE);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                params.gravity = Gravity.CENTER_VERTICAL;
                lineView.addView(opionlayout, params);

                return lineView;                                                    //返回可编辑字段容器

            } else if (Integer.parseInt(item.getInput().trim()) == 412//获取Name支持输入(下拉框选择模式)
                    || Integer.parseInt(item.getInput().trim()) == 403//获取Value
                    || Integer.parseInt(item.getInput().trim()) == 402//获取Name
                    || Integer.parseInt(item.getInput().trim()) == 401) { //获取ID
                // 2, *********************选择项********************************
                LinearLayout lineView = new LinearLayout(                                    //创建先拉列表线性布局
                        HtmitechApplication.instance());
                lineView.setOrientation(LinearLayout.VERTICAL);
                lineView.setGravity(Gravity.CENTER_VERTICAL);
                lineView.setBackgroundColor(item.getBackColor());
                if (VlineVisible.equalsIgnoreCase("true"))
                    lineView.setBackgroundResource(R.drawable.corners_bg_white_press_stroke);

                LinearLayout editValuelayout = (LinearLayout) mInflater.inflate(
                        R.layout.layout_formdetail_cell_combobox_lib, null);
                // 2015-8-18 for DetailActivity2
                if (DetailActivity2.currentActivity) {
                    editValuelayout.setEnabled(false);
                }
                // name 处理
                TextView tv = (TextView) editValuelayout
                        .findViewById(R.id.form_fielditem_text);                    //名称控件
                list_tvsize.add(tv);
                /**
                 * add by heyang
                 * date 2016-7-20
                 * 让字垂直居中
                 */
                tv.setGravity(Gravity.CENTER_VERTICAL);

                /**
                 * add by heyang
                 * date 2016-7-18
                 * if isMustInput is true then change backgroundclolor as "color_mustinputback"
                 */
                if (item.isMustInput()) {
                    tv.setBackgroundColor(getResources().getColor(R.color.color_mustinputtouming));
                    lineView.setBackgroundResource(R.drawable.corners_bg_mustinput);
                }
                if (item.getAlign().equalsIgnoreCase("center")) {                    //判断名称显示位置
                    tv.setGravity(Gravity.CENTER);
                } else if (item.getAlign().equalsIgnoreCase("left")) {
                    tv.setGravity(Gravity.LEFT);
                } else if (item.getAlign().equalsIgnoreCase("right")) {
                    tv.setGravity(Gravity.RIGHT);
                }

                if (item.isNameVisible()) {                                            //如果名称显示
                    String strName = item.getBeforeNameString()
                            + item.getName() + item.getEndNameString();
                    String split = item.getSplitString();
                    strName = strName + split;
                    tv.setText(strName);
                    if (item.getNameColor().equalsIgnoreCase("red")) {
                        tv.setTextColor(Color.RED);
                    }
                } else
                    tv.setVisibility(View.GONE);
                // 初始化下拉框控件
                comboxValue = (ComboBox) editValuelayout
                        .findViewById(R.id.form_fielditem_editvalue);
                comboxValue.setScrollViewListener(this);
                comboxValue.setGravity(Gravity.CENTER_VERTICAL);
                mComboBoxList.add(comboxValue);
                list_comboboxsize.add(comboxValue);
                /**
                 * add by heyang
                 * date 2016-7-18
                 * if isMustInput is true then change backgroundclolor as "color_mustinputback"
                 */
                if (item.isMustInput()) {
                    lineView.setBackgroundResource(R.drawable.corners_bg_mustinput);
                    comboxValue.isMustInput(true);
                }
                // 2015-8-18 for DetailActivity2
                if (DetailActivity2.currentActivity) {                                        //如果只读
                    TextView textValue = (TextView) editValuelayout
                            .findViewById(R.id.form_fielditem_textView);
                    /**
                     * add by heyang
                     * date 2016-7-20
                     * 让字垂直居中
                     */
                    textValue.setGravity(Gravity.CENTER_VERTICAL);
                    list_tvsize.add(textValue);
                    textValue.setVisibility(View.VISIBLE);
                    textValue.setText(item.getValue());
                    comboxValue.setVisibility(View.GONE);
                } else {
                    // value处理
                    comboxValue.isEnable(false);        //设置自定义控件是否可以编辑
                    comboxValue.setVisibility(View.VISIBLE);
                    if (item.getValueColor().equalsIgnoreCase("red")) {
                        comboxValue.setTextColor("#000000"); //设置自定义控件字体颜色
                    }
                    EditField edit = new EditField();                        //设置可编辑item
                    edit.setKey(item.getKey());
                    edit.setSign(item.getSign());
                    edit.setInput(item.getInput());
                    edit.setMode(item.getMode());
                    edit.setFormKey(item.getFormKey());
                    comboxValue.setTag(edit);

                    int currentSelectedIndex = -1;                                        //下拉列表的选择项
                    if (item.Dics != null) {
                        options = new String[item.Dics.size()];
                        values = new String[item.Dics.size()];
                        ids = new String[item.Dics.size()];
                        for (int i = 0; i < item.Dics.size(); i++) {
                            options[i] = item.Dics.get(i).name;
                            values[i] = item.Dics.get(i).value;
                            ids[i] = item.Dics.get(i).id;

                            if (item.getValue().equalsIgnoreCase(values[i])
                                    || item.getValue().equalsIgnoreCase(
                                    options[i]) || item.getValue().equalsIgnoreCase(ids[i])) {
                                currentSelectedIndex = i;
                            }
                        }
                        if (Integer.parseInt(item.getInput().trim()) == 412) {
                            comboxValue.setData(options);
                            comboxValue.isEnable(true);
                        } else {
                            comboxValue.isEnable(false);
                            comboxValue.setData(options);
                        }

                        if (currentSelectedIndex != -1)
                            comboxValue.setText(options[currentSelectedIndex]);
                        Log.d("item.getInput()", Integer.parseInt(item.getInput().trim()) + "======= ");
                    }


                    comboxValue.setListViewOnClickListener(new ComboBox.ListViewItemClickListener() {


                        @Override
                        public void onItemClick(int postion) {
                            String strValue = comboxValue.getText();

                            if (Integer.parseInt(item.getInput().trim()) == 401 || Integer.parseInt(item.getInput().trim()) == 403) {

                                for (int i = 0; i < options.length; i++) {
                                    if (strValue.equals(options[i])) {
                                        if(Integer.parseInt(item.getInput().trim()) == 401 )
                                            strValue = ids[i];
                                        if(Integer.parseInt(item.getInput().trim()) == 403)
                                            strValue = values[i];
                                        break;
                                    }

                                }
                            }

                            EditField edit = (EditField) comboxValue.getTag();


                            edit.setValue(strValue);

                            if (EditFileds != null && EditFileds.size() == 0) {
                                EditFileds.add(edit);
                                // 将有可能出现的key_ID,key_VALUE 隐藏字段保存到编辑框中
                                EditField edit_Value = new EditField();
                                edit_Value.setKey(edit.getKey() + "_VALUE");
                                edit_Value.setValue(strValue);//
                                edit_Value.setFormKey(edit.getFormKey());//
                                EditFileds.add(edit_Value);
                            } else {
                                boolean hasfind = false;
                                for (int j = 0; j < EditFileds.size(); j++) {                //
                                    if (EditFileds.get(j).getKey()
                                            .equals(edit.getKey())) {
                                        hasfind = true;
                                        EditFileds.get(j).setValue(strValue);
                                        break;
                                    }
                                }
                                if (!hasfind) {
                                    EditFileds.add(edit);
                                }

                                // 判读是否找到了Key_Value
                                hasfind = false;
                                for (int j = 0; j < EditFileds.size(); j++) {
                                    if (EditFileds.get(j).getKey()
                                            .equals(edit.getKey() + "_VALUE")) {
                                        hasfind = true;
                                        EditFileds.get(j).setValue(strValue);
                                        break;
                                    }
                                }
                                if (!hasfind) {
                                    EditField edit_Value = new EditField();
                                    edit_Value.setKey(edit.getKey() + "_VALUE");
                                    edit_Value.setValue(strValue);
                                    edit_Value.setFormKey(edit.getFormKey());//
                                    EditFileds.add(edit_Value);

                                }
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
                            mDocResultInfo.getResult()
                                    .setEditFields(EditFileds);
                            // 2,必填字段处理--将输入的意见放入相应必填字段中
                            EditFieldList mustFieldList = EditFieldList
                                    .getInstance();
                            for (int i = 0; i < mustFieldList.getList().size(); i++) {
                                if (mustFieldList
                                        .getList()
                                        .get(i)
                                        .getKey()
                                        .equalsIgnoreCase(
                                                ((EditField) comboxValue.getTag())
                                                        .getKey())) {
                                    mustFieldList.getList().get(i)
                                            .setValue(strValue); //
                                }
                            }
                        }

                    });

                }

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                params.gravity = Gravity.CENTER_VERTICAL;
                lineView.addView(editValuelayout, params);


                return lineView;

            } else if (Integer.parseInt(item.getInput().trim()) == 300 || Integer.parseInt(item.getInput().trim()) == 301) {
                // 3, ***********************日期选择***************************

                LinearLayout lineView = new LinearLayout(
                        HtmitechApplication.instance());
                lineView.setOrientation(LinearLayout.VERTICAL);
                lineView.setGravity(Gravity.CENTER_VERTICAL);
                lineView.setBackgroundColor(item.getBackColor());
                if (VlineVisible.equalsIgnoreCase("true"))
                    lineView.setBackgroundResource(R.drawable.corners_bg_white_press_stroke);

                // 2015-8-18 for DetailActivity2
                if (DetailActivity2.currentActivity)
                    lineView.setEnabled(false);

                LinearLayout editValuelayout = null;
                if (item.isNameRN())
                    editValuelayout = (LinearLayout) mInflater.inflate(
                            R.layout.layout_formdetail_cell_textview_vertical_lib,
                            null);
                else
                    editValuelayout = (LinearLayout) mInflater.inflate(
                            R.layout.layout_formdetail_cell_textview_lib, null);

                // name 处理
                TextView tv = (TextView) editValuelayout
                        .findViewById(R.id.form_fielditem_text);


                /**
                 * add by heyang
                 * date 2016-7-18
                 * if isMustInput is true then change backgroundclolor as "color_mustinputback"
                 */
                if (item.isMustInput()) {
                    tv.setBackgroundColor(getResources().getColor(R.color.color_mustinputtouming));
                    lineView.setBackgroundResource(R.drawable.corners_bg_mustinput);
                }
                list_tvsize.add(tv);
                if (item.getAlign().equalsIgnoreCase("center")) {
                    tv.setGravity(Gravity.CENTER);
                } else if (item.getAlign().equalsIgnoreCase("left")) {
                    tv.setGravity(Gravity.LEFT);
                } else if (item.getAlign().equalsIgnoreCase("right")) {
                    tv.setGravity(Gravity.RIGHT);
                }

                if (item.isNameVisible()) {
                    String strName = item.getBeforeNameString()
                            + item.getName() + item.getEndNameString();
                    String split = item.getSplitString();
                    strName = strName + split;
                    tv.setText(strName);
                    if (item.getNameColor().equalsIgnoreCase("red")) {
                        tv.setTextColor(Color.RED);
                    }
                } else
                    tv.setVisibility(View.GONE);

                // value处理

                TextView tvEditValue = (TextView) editValuelayout
                        .findViewById(R.id.form_fielditem_editvalue);
//                if (item.isMustInput() && (item.getValue() == null || item.getValue().trim().length() == 0))
//                    tvEditValue.setBackgroundResource(R.drawable.corners_bg_mustinput);

                /**
                 * add by heyang
                 * date 2016-7-20
                 * 设置字体垂直居中
                 */
                tvEditValue.setGravity(Gravity.CENTER_VERTICAL);
                list_tvsize.add(tv);
                list_tvsize.add(tvEditValue);
                tvEditValue.setVisibility(View.VISIBLE);
                tvEditValue.setText(item.getValue());
                if (item.getValueColor().equalsIgnoreCase("red")) {
                    tvEditValue.setTextColor(Color.RED);
                }

                // 2015-8-18 for DetailActivity2
                if (DetailActivity2.currentActivity)
                    tvEditValue.setEnabled(false);

                EditField edit = new EditField();
                edit.setKey(item.getKey());
                edit.setSign(item.getSign());
                edit.setInput(item.getInput());
                edit.setMode(item.getMode());
                edit.setFormKey(item.getFormKey());
                tvEditValue.setTag(edit);
                final int input = Integer.parseInt(item.getInput().trim());
                tvEditValue.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        // TODO Auto-generated method stub

                        currentEditTextView = (TextView) v;
//                        Calendar c = Calendar.getInstance();
                        PopChooseTimeHelper mPopBirthHelper = new PopChooseTimeHelper(getActivity());
                        if (input == 300) {
                            mPopBirthHelper.setTimeEnums(TimeEnum.YEAR, TimeEnum.MONTH, TimeEnum.DAY);
                        } else {
                            mPopBirthHelper.setTimeEnums(TimeEnum.YEAR, TimeEnum.MONTH, TimeEnum.DAY, TimeEnum.HOUR, TimeEnum.SEX);
                        }

                        mPopBirthHelper.setOnClickOkListener(new PopChooseTimeHelper.OnClickOkListener() {

                            @Override
                            public void onClickOk(String birthday) {
                                // TODO Auto-generated method stub

                                currentEditTextView.setText(birthday);
                            }
                        });

                        mPopBirthHelper.show(v);
//                        new DatePickerDialog(getActivity(),
//                                new DatePickerDialog.OnDateSetListener() {
//                                    public void onDateSet(DatePicker view,
//                                                          int year, int monthOfYear,
//                                                          int dayOfMonth) {
//
//                                        Calendar c = Calendar.getInstance();
//                                        c.set(year, monthOfYear, dayOfMonth);
//                                        int Y = c.get(Calendar.YEAR);
//                                        int M = c.get(Calendar.MONTH) + 1;
//                                        int D = c.get(Calendar.DAY_OF_MONTH);
//                                        String date = Y + "-"
//                                                + (M < 10 ? "0" + M : M + "")
//                                                + "-"
//                                                + (D < 10 ? "0" + D : D + "");
//                                        currentEditTextView.setText(date);
//
//                                    }
//
//                                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH),
//                                c.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });

                tvEditValue.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void onTextChanged(CharSequence s, int start,
                                              int before, int count) {
                        // TODO Auto-generated method stub

                        // TODO Auto-generated method stub

                        // 将值保存到编辑的字段中，并判断是否需要更新必填字段
                        // 1,将已经编辑的字段和值放入到办理是需要回发的EditField[] 中
                        String strValue = currentEditTextView.getText()
                                .toString();
                        EditField edit = (EditField) currentEditTextView
                                .getTag();
                        edit.setValue(strValue);
                        if (EditFileds != null && EditFileds.size() == 0)
                            EditFileds.add(edit);
                        else {
                            boolean hasfind = false;
                            for (int j = 0; j < EditFileds.size(); j++) {
                                if (EditFileds.get(j).getKey()
                                        .equals(edit.getKey())) {
                                    hasfind = true;
                                    EditFileds.get(j).setValue(edit.getValue());
                                    break;
                                }
                            }
                            if (!hasfind)
                                EditFileds.add(edit);
                        }
                        Log.d("FormFragment", "EditFileds:" + EditFileds);

                        DocResultInfo mDocResultInfo = null;
                        if (DetailActivity2.currentActivity) {
                            mDocResultInfo = ((DetailActivity2) getActivity()).mDocResultInfo;
                        } else {
                            mDocResultInfo = ((StartDetailActivity) getActivity()).mDocResultInfo;
                        }
                        /*DocResultInfo mDocResultInfo = ((StartDetailActivity) getActivity()).mDocResultInfo;*/
                        mDocResultInfo.getResult().setEditFields(EditFileds);
                        // 2,必填字段处理--将输入的意见放入相应必填字段中
                        EditFieldList mustFieldList = EditFieldList
                                .getInstance();
                        for (int i = 0; i < mustFieldList.getList().size(); i++) {
                            if (mustFieldList
                                    .getList()
                                    .get(i)
                                    .getKey()
                                    .equalsIgnoreCase(
                                            ((EditField) StartFormFragment.this.currentEditTextView
                                                    .getTag()).getKey())) {
                                mustFieldList.getList().get(i)
                                        .setValue(strValue);
                            }
                        }

                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start,
                                                  int count, int after) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                // editValuelayout.setOnClickListener(new CellOnclickLisener());
                // ImageView img = (ImageView) editValuelayout
                // .findViewById(R.id.form_fielditem_editimage);
                // img.setVisibility(View.VISIBLE);

                /**
                 * add by heyang
                 * date 2016-7-18
                 * if isMustInput is true then change backgroundclolor as "color_mustinputback"
                 */
                if (item.isMustInput()) {
                    tvEditValue.setBackgroundResource(R.drawable.text_back);
                    lineView.setBackgroundResource(R.drawable.corners_bg_mustinput);
                }

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                params.gravity = Gravity.CENTER_VERTICAL;

                lineView.addView(editValuelayout, params);
                return lineView;
            } else if (Integer.parseInt(item.getInput().trim()) == 612||Integer.parseInt(item.getInput().trim()) == 611||Integer.parseInt(item.getInput().trim()) == 613) {
                // 5,
                // ***********************人员多选选择：id1;id2;id3**************************

                LinearLayout lineView = new LinearLayout(
                        HtmitechApplication.instance());
                lineView.setOrientation(LinearLayout.VERTICAL);
                if (VlineVisible.equalsIgnoreCase("true"))
                    lineView.setBackgroundResource(R.drawable.corners_bg_white_press_stroke);

                // 2015-8-18 for DetailActivity2
                if (DetailActivity2.currentActivity)
                    lineView.setEnabled(false);

                LinearLayout editValuelayout = null;
                if (item.isNameRN())
                    editValuelayout = (LinearLayout) mInflater.inflate(
                            R.layout.layout_formdetail_cell_textview_vertical_lib,
                            null);
                else
                    editValuelayout = (LinearLayout) mInflater.inflate(
                            R.layout.layout_formdetail_cell_textview_lib, null);

                // name 处理
                TextView tv = (TextView) editValuelayout
                        .findViewById(R.id.form_fielditem_text);
                if (item.getAlign().equalsIgnoreCase("center")) {
                    tv.setGravity(Gravity.CENTER);
                } else if (item.getAlign().equalsIgnoreCase("left")) {
                    tv.setGravity(Gravity.LEFT);
                } else if (item.getAlign().equalsIgnoreCase("right")) {
                    tv.setGravity(Gravity.RIGHT);
                }

                if (item.isNameVisible()) {
                    String strName = item.getBeforeNameString()
                            + item.getName() + item.getEndNameString();
                    String split = item.getSplitString();
                    strName = strName + split;
                    tv.setText(strName);
                    if (item.getNameColor().equalsIgnoreCase("red")) {
                        tv.setTextColor(Color.RED);
                    }
                } else
                    tv.setVisibility(View.GONE);

                // value处理

                TextView tvEditValue = (TextView) editValuelayout
                        .findViewById(R.id.form_fielditem_editvalue);
                tvEditValue.setVisibility(View.VISIBLE);
                tvEditValue.setText(item.getValue());
                if (item.getValueColor().equalsIgnoreCase("red")) {
                    tvEditValue.setTextColor(Color.RED);
                }

                // 2015-8-18 for DetailActivity2
                if (DetailActivity2.currentActivity)
                    tvEditValue.setEnabled(false);

                EditField edit = new EditField();
                edit.setKey(item.getKey());
                edit.setSign(item.getSign());
                edit.setInput(item.getInput());
                edit.setMode(item.getMode());
                tvEditValue.setTag(edit);
                tvEditValue.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(final View v) {

                        currentEditTextView = (TextView) v;
//                        MyNextPersonDialog nextpersonDialog = new MyNextPersonDialog(
//                                getActivity(), new DialogConfirmListener() {
//                            public void onConfirm(BaseDialog dialog) { // 确定
//
//                                Integer[] indexArr = ((MyNextPersonDialog) dialog)
//                                        .getSelectIndexArr();
//                                if (null == indexArr
//                                        || indexArr.length <= 0) {
//                                    ToastInfo toast = ToastInfo
//                                            .getInstance(getActivity());
//                                    toast.setText("没有选择办理人！！");
//                                    toast.show(Toast.LENGTH_SHORT);
//                                } else {
//                                    AuthorInfo[] AuthorInfoTemp = ((MyNextPersonDialog) dialog)
//                                            .getNewVector();
//                                    handle_doAction_hasAuthor(indexArr,
//                                            AuthorInfoTemp);
//                                }
//                            }
//                        }, new DialogCancelListener() {
//                            public void onCancel(BaseDialog dialog) { // 取消
//                                dialog.dismiss();
//                            }
//                        }, new DialogClearListener() {
//                            public void onClear(BaseDialog dialog) { // 清空选择
//                                handle_doAction_clearAuthor();
//                                dialog.dismiss();
//                            }
//
//                            @Override
//                            public void onClear() {
//                                // TODO Auto-generated method stub
//
//                            }
//                        }, R.style.mydialog);
//                        nextpersonDialog.show();
//
//						/*
//                         * if
//						 * (mDoActionResultInfo.getResult().isIsFreeSelectUser
//						 * ()) { //从数据库读取联系人 AuthorInfoDAOImpl authorDao = new
//						 * AuthorInfoDAOImpl(this);
//						 * nextpersonDialog.setViewValue(title,
//						 * authorDao.getAuthorForOaSelect());
//						 * //isIsFreeSelectUser = true; } else {
//						 *
//						 * }
//						 */
//                        String title = "请选择人员";
//                        nextpersonDialog.setSeletAllUserView(false);
//                        AuthorInfoDAOImpl authorDao = new AuthorInfoDAOImpl(
//                                getActivity());
//                        nextpersonDialog.setViewAllUserValue(title,
//                                authorDao.getAuthorForOaSelect(), false);
                        BookInit.getInstance().setCallCheckUserListener(getActivity(), ChooseWayEnum.PEOPLECHOOSE, ChooseWay.MORE_CHOOSE, ChooseTreeHierarchy
                                .HIERARCHY, ChooseSystemBook.ADDRESSBOOK, "选择人员", true, null, new CallCheckAllUserListener() {
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
                                    ((TextView) v).setText("");
                                    handle_doAction_hasAuthor(mAuthorInfoList);
                                } else if (checkAllDepartment != null && checkAllDepartment.size() != 0) {
                                    ArrayList<AuthorInfo> mAuthorInfoList = new ArrayList<AuthorInfo>();
                                    for (SYS_Department mSYS_Department : checkAllDepartment) {
                                        AuthorInfo mAuthorInfo = new AuthorInfo();
                                        mAuthorInfo.setUserID(mSYS_Department.getDepartmentCode());
                                        mAuthorInfo.setUserName(mSYS_Department.getFullName());
                                        mAuthorInfoList.add(mAuthorInfo);
                                    }
                                    ((TextView) v).setText("");
                                    handle_doAction_hasAuthor(mAuthorInfoList);
                                }
                            }
                        });
                    }
                });

                // editValuelayout.setOnClickListener(new CellOnclickLisener());
                // ImageView img = (ImageView) editValuelayout
                // .findViewById(R.id.form_fielditem_editimage);
                // img.setVisibility(View.VISIBLE);
                if (item.isMustInput()
                        && (item.getValue() == null || item.getValue().trim()
                        .length() == 0))
                    tvEditValue.setBackgroundColor(getResources().getColor(
                            R.color.color_mustinputback));

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                lineView.addView(editValuelayout, params);

                return lineView;

            }else if(Integer.parseInt(item.getInput().trim()) == 912||Integer.parseInt(item.getInput().trim()) == 911){
                //912 表示选择部门返回ID  911 是返回NAME

                {
                    // 5,
                    // ***********************人员多选选择：id1;id2;id3**************************

                    LinearLayout lineView = new LinearLayout(
                            HtmitechApplication.instance());
                    lineView.setOrientation(LinearLayout.VERTICAL);
                    if (VlineVisible.equalsIgnoreCase("true"))
                        lineView.setBackgroundResource(R.drawable.corners_bg_white_press_stroke);

                    // 2015-8-18 for DetailActivity2
                    if (DetailActivity2.currentActivity)
                        lineView.setEnabled(false);

                    LinearLayout editValuelayout = null;
                    if (item.isNameRN())
                        editValuelayout = (LinearLayout) mInflater.inflate(
                                R.layout.layout_formdetail_cell_textview_vertical_lib,
                                null);
                    else
                        editValuelayout = (LinearLayout) mInflater.inflate(
                                R.layout.layout_formdetail_cell_textview_lib, null);

                    // name 处理
                    TextView tv = (TextView) editValuelayout
                            .findViewById(R.id.form_fielditem_text);
                    if (item.getAlign().equalsIgnoreCase("center")) {
                        tv.setGravity(Gravity.CENTER);
                    } else if (item.getAlign().equalsIgnoreCase("left")) {
                        tv.setGravity(Gravity.LEFT);
                    } else if (item.getAlign().equalsIgnoreCase("right")) {
                        tv.setGravity(Gravity.RIGHT);
                    }

                    if (item.isNameVisible()) {
                        String strName = item.getBeforeNameString()
                                + item.getName() + item.getEndNameString();
                        String split = item.getSplitString();
                        strName = strName + split;
                        tv.setText(strName);
                        if (item.getNameColor().equalsIgnoreCase("red")) {
                            tv.setTextColor(Color.RED);
                        }
                    } else
                        tv.setVisibility(View.GONE);

                    // value处理

                    TextView tvEditValue = (TextView) editValuelayout
                            .findViewById(R.id.form_fielditem_editvalue);
                    tvEditValue.setVisibility(View.VISIBLE);
                    tvEditValue.setText(item.getValue());
                    if (item.getValueColor().equalsIgnoreCase("red")) {
                        tvEditValue.setTextColor(Color.RED);
                    }

                    // 2015-8-18 for DetailActivity2
                    if (DetailActivity2.currentActivity)
                        tvEditValue.setEnabled(false);

                    EditField edit = new EditField();
                    edit.setKey(item.getKey());
                    edit.setSign(item.getSign());
                    edit.setInput(item.getInput());
                    edit.setMode(item.getMode());
                    tvEditValue.setTag(edit);
                    tvEditValue.setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(final View v) {
                            currentEditTextView = (TextView) v;
                            BookInit.getInstance().setCallCheckUserListener(getActivity(), ChooseWayEnum.DEPARTMENTCHOOSE, ChooseWay.MORE_CHOOSE, ChooseTreeHierarchy
                                    .HIERARCHY, ChooseSystemBook.ADDRESSBOOK, "选择部门", true, null, new CallCheckAllUserListener() {
                                @Override
                                public void checkAll(ArrayList<SYS_User> checkAllUser, ArrayList<SYS_Department> checkAllDepartment) {
                                    if (checkAllUser != null && checkAllUser.size() != 0) {
                                        ((TextView) v).setText("");
                                        ArrayList<AuthorInfo> mAuthorInfoList = new ArrayList<AuthorInfo>();
                                        for (SYS_User mSYS_User : checkAllUser) {
                                            AuthorInfo mAuthorInfo = new AuthorInfo();
                                            mAuthorInfo.setUserID(mSYS_User.getUserId());
                                            mAuthorInfo.setUserName(mSYS_User.getFullName());
                                            mAuthorInfoList.add(mAuthorInfo);
                                        }
                                        handle_doAction_hasAuthor(mAuthorInfoList);
                                    } else if (checkAllDepartment != null && checkAllDepartment.size() != 0) {
                                        ((TextView) v).setText("");
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
                        }
                    });

                    // editValuelayout.setOnClickListener(new CellOnclickLisener());
                    // ImageView img = (ImageView) editValuelayout
                    // .findViewById(R.id.form_fielditem_editimage);
                    // img.setVisibility(View.VISIBLE);
                    if (item.isMustInput()
                            && (item.getValue() == null || item.getValue().trim()
                            .length() == 0))
                        tvEditValue.setBackgroundColor(getResources().getColor(
                                R.color.color_mustinputback));

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                    lineView.addView(editValuelayout, params);

                    return lineView;

                }

            } else if (Integer.parseInt(item.getInput().trim()) == 501 ||//单选框结果取ID
                    Integer.parseInt(item.getInput().trim()) == 502 ||//单选框结果取Name
                    Integer.parseInt(item.getInput().trim()) == 503 ||//单选框结果取Value
                    Integer.parseInt(item.getInput().trim()) == 511 ||//复选框结果取ID
                    Integer.parseInt(item.getInput().trim()) == 511 ||//复选框结果取Name
                    Integer.parseInt(item.getInput().trim()) == 513) { //复选框结果取Value
                LinearLayout lineView = new LinearLayout(
                        HtmitechApplication.instance());
                lineView.setOrientation(LinearLayout.VERTICAL);
                lineView.setGravity(Gravity.CENTER_VERTICAL);
                if (VlineVisible.equalsIgnoreCase("true"))
                    lineView.setBackgroundResource(R.drawable.corners_bg_white_press_stroke);
                List<CheckForm> listform = new ArrayList<CheckForm>();//放单选多选按钮内容
                if(item.isMustInput()){
                    lineView.setBackgroundResource(R.drawable.corners_bg_mustinput);
                }
                EditField edit1 = new EditField();                                        //选项布局建立tag
                edit1.setKey(item.getKey());
                edit1.setSign(item.getSign());
                edit1.setInput(item.getInput());
                edit1.setMode(item.getMode());
                edit1.setFormKey(item.getFormKey());
                DocResultInfo mDocResultInfo = null;
                if (DetailActivity2.currentActivity) {
                    mDocResultInfo = ((DetailActivity2) getActivity()).mDocResultInfo;
                } else {
                    mDocResultInfo = ((StartDetailActivity) getActivity()).mDocResultInfo;
                }

                if (item.Dics != null && item.Dics.size() != 0) {
                    for (int i = 0; i < item.Dics.size(); i++) {
                        if (Integer.parseInt(item.getInput().trim()) == 501 || Integer.parseInt(item.getInput().trim()) == 511) {
//                            listform.add(item.Dics.get(i).id);
                            listform.add(new CheckForm(item.Dics.get(i).id,item.Dics.get(i).name,""));
                        } else if (Integer.parseInt(item.getInput().trim()) == 502 || Integer.parseInt(item.getInput().trim()) == 512) {
//                            listform.add(item.Dics.get(i).name);
                            listform.add(new CheckForm("",item.Dics.get(i).name,""));
                        } else if (Integer.parseInt(item.getInput().trim()) == 503 || Integer.parseInt(item.getInput().trim()) == 513) {
//                            listform.add(item.Dics.get(i).value);
                            listform.add(new CheckForm("",item.Dics.get(i).name,item.Dics.get(i).value));
                        }
                    }
                    if (Integer.parseInt(item.getInput().trim()) == 501 || Integer.parseInt(item.getInput().trim()) == 502 || Integer.parseInt(item.getInput().trim()) == 503) {
                        HtmitechRadioButton radiobutton = new HtmitechRadioButton(HtmitechApplication.instance());
                        radiobutton.setView(lineView);
                        radiobutton.setTextList(listform,item.getValue());
                        radiobutton.setEdit(EditFileds,edit1,mDocResultInfo);
                        return lineView;
                    } else if (Integer.parseInt(item.getInput().trim()) == 511 || Integer.parseInt(item.getInput().trim()) == 512 || Integer.parseInt(item.getInput().trim()) == 513) {
                        String[] checkValue = null;
                        if(item.getValue() != null && !item.getValue().equals("")){
                            checkValue = item.getValue().split(";");
                        }
                        HtmitechCheckBox hcb = new HtmitechCheckBox(HtmitechApplication.instance());
                        hcb.setView(lineView);
                        hcb.setList(listform,checkValue);
                        hcb.setEdit(EditFileds, edit1, mDocResultInfo);
                        return lineView;
                    }
                }else{
                    return lineView;
                }

            } else {
                // 4，**********************其他非意见可编辑字段**********************
                LinearLayout lineView = new LinearLayout(
                        HtmitechApplication.instance());
                lineView.setOrientation(LinearLayout.VERTICAL);
                lineView.setGravity(Gravity.CENTER_VERTICAL);
                if (VlineVisible.equalsIgnoreCase("true"))
                    lineView.setBackgroundResource(R.drawable.corners_bg_white_press_stroke);

                LinearLayout editValuelayout = null;
                if (item.isNameRN()) //判断name 和 value 是否在同一行显示
                    editValuelayout = (LinearLayout) mInflater.inflate(
                            R.layout.layout_formdetail_cell_edittext_vertical_lib,
                            null);
                else
                    editValuelayout = (LinearLayout) mInflater.inflate(
                            R.layout.layout_formdetail_cell_edittext_lib, null);

                // name 处理
                TextView tv = (TextView) editValuelayout
                        .findViewById(R.id.form_fielditem_text);
                list_tvsize.add(tv);
                /**
                 * add by heyang
                 * date 2016-7-18
                 * if isMustInput is true then change backgroundclolor as "color_mustinputback"
                 */
                if (item.isMustInput()) {
                    tv.setBackgroundColor(getResources().getColor(R.color.color_mustinputtouming));
                    lineView.setBackgroundResource(R.drawable.corners_bg_mustinput);
                }
                if (item.getAlign().equalsIgnoreCase("center")) {
                    tv.setGravity(Gravity.CENTER);
                } else if (item.getAlign().equalsIgnoreCase("left")) {
                    tv.setGravity(Gravity.LEFT);
                } else if (item.getAlign().equalsIgnoreCase("right")) {
                    tv.setGravity(Gravity.RIGHT);
                }

                if (item.isNameVisible()) {
                    String strName = item.getBeforeNameString()
                            + item.getName() + item.getEndNameString();
                    String split = item.getSplitString();
                    strName = strName + split;
                    tv.setText(strName);
                    if (item.getNameColor().equalsIgnoreCase("red")) {
                        tv.setTextColor(Color.RED);
                    }
                } else
                    tv.setVisibility(View.GONE);

                if (item.getInput() != null && (Integer.parseInt(item.getInput().trim()) == 200 ||
                        Integer.parseInt(item.getInput().trim()) == 201 ||
                        Integer.parseInt(item.getInput().trim()) == 202 ||
                        Integer.parseInt(item.getInput().trim()) == 203 ||
                        Integer.parseInt(item.getInput().trim()) == 101 ||
                        Integer.parseInt(item.getInput().trim()) == 102)) {
                    // value处理

                    EditText tvEditValue = (EditText) editValuelayout.findViewById(R.id.form_fielditem_editvalue);
                    SuperEditText supperEditValue = (SuperEditText) editValuelayout.findViewById(R.id.form_fielditem_supperedit);
                    supperEditValue.setVisibility(View.VISIBLE);
                    list_etsize.add(supperEditValue);
                    /**
                     * add by heyang
                     * date 2016-7-20
                     * 让字垂直居中
                     */
                    tvEditValue.setGravity(Gravity.CENTER_VERTICAL);
                    /**
                     * add by heyang
                     * date 2016-7-18
                     * if isMustInput is true then change backgroundclolor as "color_mustinputback"
                     */
                    if (item.isMustInput()) {
                        lineView.setBackgroundResource(R.drawable.corners_bg_mustinput);
                        supperEditValue.isMustInput = true;
                    }
                    tvEditValue = supperEditValue;
                    // 2015-8-18 for DetailActivity2
                    if (DetailActivity2.currentActivity) {
                        tvEditValue.setEnabled(false);
                    }
                    tvEditValue.setVisibility(View.VISIBLE);
                    tvEditValue.setText(item.getValue().replace("\\\\r\\\\n", "\r\n"));
                    if (item.getValueColor().equalsIgnoreCase("red")) {
                        tvEditValue.setTextColor(Color.RED);
                    }
                    EditField edit = new EditField();
                    edit.setKey(item.getKey());
                    edit.setSign(item.getSign());
                    edit.setInput(item.getInput());
                    edit.setMode(item.getMode());
                    edit.setFormKey(item.getFormKey());
                    tvEditValue.setTag(edit);
                    tvEditValue
                            .setOnFocusChangeListener(new OnFocusChangeListener() {

                                @Override
                                public void onFocusChange(View v, boolean hasFocus) {
                                    // TODO Auto-generated method stub
                                    if (hasFocus) {
                                        currentEditText = (EditText) v;
                                        list_etsize.add(currentEditText);
                                    }
                                }
                            });

                    tvEditValue.addTextChangedListener(new TextWatcher() {

                        @Override
                        public void onTextChanged(CharSequence s, int start,
                                                  int before, int count) {
                            // TODO Auto-generated method stub

                            // TODO Auto-generated method stub

                            // 将值保存到编辑的字段中，并判断是否需要更新必填字段
                            // 1,将已经编辑的字段和值放入到办理是需要回发的EditField[] 中
                            String strValue = currentEditText.getText().toString();
                            EditField edit = (EditField) currentEditText.getTag();
                            edit.setValue(strValue);
                            if (EditFileds != null && EditFileds.size() == 0)
                                EditFileds.add(edit);
                            else {
                                boolean hasfind = false;
                                for (int j = 0; j < EditFileds.size(); j++) {
                                    if (EditFileds.get(j).getKey()
                                            .equals(edit.getKey())) {
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
                                mDocResultInfo = ((StartDetailActivity) getActivity()).mDocResultInfo;
                            }
                            // DocResultInfo mDocResultInfo = ((DetailActivity)
                            // getActivity()).mDocResultInfo;
                            mDocResultInfo.getResult().setEditFields(EditFileds);
                            // 2,必填字段处理--将输入的意见放入相应必填字段中
                            EditFieldList mustFieldList = EditFieldList
                                    .getInstance();
                            for (int i = 0; i < mustFieldList.getList().size(); i++) {
                                if (mustFieldList
                                        .getList()
                                        .get(i)
                                        .getKey()
                                        .equalsIgnoreCase(
                                                ((EditField) StartFormFragment.this.currentEditText
                                                        .getTag()).getKey())) {
                                    mustFieldList.getList().get(i)
                                            .setValue(strValue);
                                }
                            }

                        }

                        @Override
                        public void beforeTextChanged(CharSequence s, int start,
                                                      int count, int after) {
                            // TODO Auto-generated method stub

                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });

                    // before value 处理
                    TextView beforetv = (TextView) editValuelayout
                            .findViewById(R.id.form_fielditem_beforetext);
                    list_tvsize.add(beforetv);
                    /**
                     * add by heyang
                     * date 2016-7-18
                     * if isMustInput is true then change backgroundclolor as "color_mustinputback"
                     */
                    if (item.isMustInput()) {
                        beforetv.setBackgroundColor(getResources().getColor(R.color.color_mustinputtouming));
                        lineView.setBackgroundResource(R.drawable.corners_bg_mustinput);
                    }
                    if (item.getBeforeValueString().length() > 0) {
                        beforetv.setText(item.getBeforeValueString());
                        if (item.getValueColor().equalsIgnoreCase("red")) {
                            beforetv.setTextColor(Color.RED);
                        }
                        beforetv.setVisibility(View.VISIBLE);
                    } else beforetv.setVisibility(View.GONE);

                    // after value 处理
                    TextView endtv = (TextView) editValuelayout
                            .findViewById(R.id.form_fielditem_endtext);
                    /**
                     * add by heyang
                     * date 2016-7-20
                     * 让字垂直居中
                     */
                    list_tvsize.add(endtv);

                    /**
                     * add by heyang
                     * date 2016-7-18
                     * if isMustInput is true then change backgroundclolor as "color_mustinputback"
                     */
                    if (item.isMustInput()) {
                        endtv.setBackgroundColor(getResources().getColor(R.color.color_mustinputtouming));
                        lineView.setBackgroundResource(R.drawable.corners_bg_mustinput);
                    }
                    if (item.getEndValueString().length() > 0) {
                        endtv.setText(item.getEndValueString());
                        if (item.getValueColor().equalsIgnoreCase("red")) {
                            endtv.setTextColor(Color.RED);
                        }
                        endtv.setVisibility(View.VISIBLE);
                    } else endtv.setVisibility(View.GONE);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                    params.gravity = Gravity.CENTER_VERTICAL;
                    lineView.addView(editValuelayout, params);

                    return lineView;

                }
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
            layout.setBackgroundResource(R.drawable.corners_bg_white_press_stroke);
        } else {
            layout.setBackgroundResource(R.drawable.corners_bg_grey_press_stroke);
        }

        TextView tv = (TextView) layout.findViewById(R.id.form_fielditem_text);
        /**
         * add by heyang
         * date 2016-7-18
         * if isMustInput is true then change backgroundclolor as "color_mustinputback"
         */
        if (item.isMustInput()) {
            tv.setBackgroundColor(getResources().getColor(R.color.color_mustinputtouming));
            layout.setBackgroundResource(R.drawable.corners_bg_mustinput);
        }
        if (item.getAlign().equalsIgnoreCase("center")) {
            tv.setGravity(Gravity.CENTER);
        } else if (item.getAlign().equalsIgnoreCase("left")) {
            tv.setGravity(Gravity.LEFT);
        } else if (item.getAlign().equalsIgnoreCase("right")) {
            tv.setGravity(Gravity.RIGHT);
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
                tv.setGravity(Gravity.CENTER_VERTICAL);
            }
            tv.setBackgroundColor(item.getBackColor()); //2015-09-22添加 for 项目申报字表字段背景颜色
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
        edit.setValue(strValue);
        if (EditFileds != null && EditFileds.size() == 0){
            EditFileds.clear();
            EditFileds.add(edit);
        }
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
        else if (sfActivity != null && sfActivity instanceof DetailActivity)
            mDocResultInfo = ((DetailActivity2) getActivity()).mDocResultInfo;
        mDocResultInfo.getResult().cleanFields();
        if(mDocResultInfo.getResult() != null)
            mDocResultInfo.getResult().setEditFields(EditFileds);
        // 2,必填字段处理--将输入的意见放入相应必填字段中
        EditFieldList mustFieldList = EditFieldList.getInstance();
        for (int i = 0; i < mustFieldList.getList().size(); i++) {
            if (mustFieldList
                    .getList()
                    .get(i)
                    .getKey()
                    .equalsIgnoreCase(
                            ((EditField) StartFormFragment.this.currentEditTextView
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
        else if (sfActivity != null && sfActivity instanceof DetailActivity)
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
                            ((EditField) StartFormFragment.this.currentEditTextView
                                    .getTag()).getKey())) {
                mustFieldList.getList().get(i).setValue(strValue);
            }
        }

        currentEditTextView.setText(strName);

    }

    public class PersonOnClick implements OnClickListener {

        private String userID;

        public PersonOnClick(String sUserID) {
            // TODO Auto-generated constructor stub
            userID = sUserID;
        }

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            ConcreteLogin mCpmcreteLogin = ConcreteLogin.getInstance();
            mCpmcreteLogin.chatMX(getActivity(),userID);
//            if (userID.equalsIgnoreCase("admin")) {
//                Toast.makeText(getActivity(), "不能发起对admin的聊天",
//                        Toast.LENGTH_SHORT).show();
//            } else if (userID.equalsIgnoreCase(MXAPI.getInstance(getActivity())
//                    .currentUser().getLoginName())) {
//                Toast.makeText(getActivity(), "不能发起对自己的聊天", Toast.LENGTH_SHORT)
//                        .show();
//            } else {
//
//                Log.d("FlowFragment", "发起聊天，对：" + userID);
//                String[] loginNames = new String[]{userID};
//                // TODO Auto-generated method stub
//                MXAPI.getInstance(getActivity()).chat(loginNames,
//                        new MXApiCallback() {
//                            @Override
//                            public void onLoading() {
//                                // TODO Auto-generated method stub
//
//                            }
//
//                            @Override
//                            public void onFail(MXError arg0) {
//                                // TODO Auto-generated method stub
//
//                            }
//
//                            @Override
//                            public void onSuccess() {
//                                // TODO Auto-generated method stub
//
//                            }
//                        });
//            }
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        // ********************测试临时代码******************************
        if (data == null || TextUtils.isEmpty(data.getStringExtra("option"))
                || mClickView == null) {
            return;
        }

        String strOption = data.getStringExtra("option");
        LinearLayout layout = (LinearLayout) mClickView;
        // 显示当前编辑的意见
        TextView tvOption = (TextView) layout
                .findViewById(R.id.form_fielditem_option);
        tvOption.setTextColor(Color.RED);
        if (strOption != null && strOption.length() > 0) {
            tvOption.setVisibility(View.VISIBLE);
            tvOption.setText(strOption);
            // 已经编辑完成后返回后执行的逻辑：
            ((StartDetailActivity) getActivity()).setComment(strOption);
            // 显示时加上当前时间
            tvOption.setText(strOption + "\n"
                    + DateFormatUtils.format(new Date(), "yyyy-M-d h:mm"));
        } else if (strOption.length() == 0) {
            tvOption.setText("");
            tvOption.setVisibility(View.INVISIBLE);
        }

        // 1,将已经编辑的字段和值放入到办理是需要回发的EditField[] 中
        StartFormFragment.this.mClickView = mClickView;
        EditField edit = (EditField) StartFormFragment.this.mClickView.getTag();
        edit.setValue(strOption);
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
        DocResultInfo mDocResultInfo = ((StartDetailActivity) getActivity()).mDocResultInfo;
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
                            ((EditField) StartFormFragment.this.mClickView
                                    .getTag()).getKey())) {
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
            StartFormFragment.this.mClickView = v;

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
            if (getActivity() != null) {
                Intent intent = new Intent(getActivity(),
                        OpinionInputActivity.class);
                startActivityForResult(intent, 0); // 不用ActivityResult 还可以用什么？
            }

            // *********************测试临时代码******************************
            StartFormFragment.this.mClickView = v;

        }

    }

    public void toDeleteMsg(final int entityIndex) {
        mNewFragment = MyAlertDialogFragment.newInstance("确实要删除此收藏记录吗?",
                R.drawable.prompt_warn, cancelListener, confirmListener, true);
        mNewFragment.show(getFragmentManager(), "dialog");
    }

    private DialogCancelListener cancelListener = new DialogCancelListener() {
        @Override
        public void onCancel(BaseDialog dialog) {
            // TODO Auto-generated method stub
            mNewFragment.dismiss();
        }
    };

    private DialogConfirmListener confirmListener = new DialogConfirmListener() {
        public void onConfirm(BaseDialog dialog) {
            mNewFragment.dismiss();

        }
    };

    public void onResume() {
        super.onResume();
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

}
