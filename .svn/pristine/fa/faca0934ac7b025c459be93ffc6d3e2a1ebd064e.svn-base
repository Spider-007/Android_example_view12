package htmitech.formConfig;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import htmitech.com.componentlibrary.ComboBox;
import htmitech.com.componentlibrary.SingleChoicePopupWindow;
import htmitech.com.componentlibrary.api.ComponentInit;
import htmitech.com.componentlibrary.entity.CheckForm;
import htmitech.com.componentlibrary.entity.Dics;
import htmitech.com.componentlibrary.entity.DocResultInfo;
import htmitech.com.componentlibrary.entity.EditField;
import htmitech.com.componentlibrary.entity.EditFieldList;
import htmitech.com.componentlibrary.entity.FieldItem;
import htmitech.com.componentlibrary.listener.ScrollViewListener;
import htmitech.com.componentlibrary.unit.DrawableUtils;
import htmitech.com.componentlibrary.unit.SysConvertUtil;
import htmitech.com.formlibrary.R;

/**
 * 下拉框
 */
public class SpringLayout412_403_402_401 {
    private Context context;
    private String[] options;
    private String[] values;
    private String[] ids;
    private  LayoutInflater mInflater;
    private List<TextView> list_tvsize;
    private ArrayList<ComboBox> mComboBoxList;
    private List<ComboBox> list_comboboxsize;
    private List<EditField> EditFileds;
    private ScrollViewListener mScrollViewListener;
    private LinearLayout lineView;
    private List<CheckForm> listform;
    private DocResultInfo mDocResultInfo;
    private TextView currentEditTextViews;
    private ComboBox comboxValue;
    private SingleChoicePopupWindow mSingleChoicePopupWindow;

    private int intputTypes;
    private int TabStyle;
    public void setOptions(String[]... options) {
        this.options = options[0];
        this.values = options[1];
        this.ids = options[2];
    }

    public void setMustInputLoacal(){
        comboxValue.setBackgroundResource(R.drawable.form_input_bg_must_local);
    }

    public SpringLayout412_403_402_401(Context context) {
        this.context = context;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public LinearLayout springLayout(String VlineVisible, FieldItem item, LayoutInflater mInflater, List<TextView> list_tvsize, ArrayList<ComboBox> mComboBoxList, List<ComboBox> list_comboboxsize, List<EditField> EditFileds, ScrollViewListener mScrollViewListener, int TabStyle, DocResultInfo mDocResultInfo) {

        this.mInflater = mInflater;
        this.list_tvsize = list_tvsize;
        this.mComboBoxList = mComboBoxList;
        this.list_comboboxsize = list_comboboxsize;
        this.EditFileds = EditFileds;
        this.mDocResultInfo = mDocResultInfo;
        this.mScrollViewListener = mScrollViewListener;
        this.TabStyle = TabStyle;
        // 2, *********************选择项********************************
        lineView = new LinearLayout(                                    //创建先拉列表线性布局
                context);
        lineView.setOrientation(LinearLayout.VERTICAL);
        lineView.setGravity(Gravity.CENTER_VERTICAL);
        lineView.setBackgroundColor(SysConvertUtil.formattingH(item.getBackColor()));
        if (VlineVisible.equalsIgnoreCase("true")) {
//            lineView.setBackgroundResource(R.drawable.corners_bg_white_press_stroke_v);
            lineView.setBackground(DrawableUtils.getLayerDrawable(context, item.getBackColor()));
        }
        this.TabStyle = TabStyle;
        if(TabStyle ==1){
            return setReView401_02_03_12_net(item);
        }else {

            return setReView412_03_02_01(item);
        }
    }

    /**
     *
     * @param inputType
     * @param arrayList
     * @param currentValue
     * @return
     */
    public String getName(int inputType, List<FieldItem.dic> arrayList, String currentValue){
        if(arrayList == null){
            arrayList = new ArrayList<>();
        }
        switch (inputType) {
            case 401:  //单选（结果取ID）
            case 413://多选 （结果取ID）
            case 411://多选（结果取ID）

                for(FieldItem.dic mDics : arrayList){
                    if(TextUtils.equals(mDics.id,currentValue)){
                        return mDics.name;
                    }
                }

                break;
            case 402://单选  （结果取Name）
            case 414://单选  （结果取Name）
            case 412://多选 （结果取Name）

                for(FieldItem.dic mDics : arrayList){
                    if(TextUtils.equals(mDics.name,currentValue)){
                        return mDics.name;
                    }
                }
                break;
            case 403: //单选 （结果取Value）
            case 415://多选 （结果取Value）

                for(FieldItem.dic mDics : arrayList){
                    if(TextUtils.equals(mDics.value,currentValue)){
                        return mDics.name;
                    }
                }
                break;
        }
        return currentValue;
    }

    public LinearLayout setReView412_03_02_01(FieldItem item){
        lineView.removeAllViews();
        LinearLayout editValuelayout = (LinearLayout) mInflater.inflate(R.layout.layout_formdetail_cell_combobox_lib, null);
        editValuelayout.setBackgroundResource(R.drawable.corners_bg_white_press_stroke_or);
        // name 处理
        TextView tv = (TextView) editValuelayout.findViewById(R.id.form_fielditem_text);                    //名称控件
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
            tv.setBackgroundColor(context.getResources().getColor(R.color.form_bg_must));
//            lineView.setBackgroundResource(R.drawable.form_input_bg_must);
        }
        if (item.getAlign().equalsIgnoreCase("center")) {                    //判断名称显示位置
            tv.setGravity(Gravity.CENTER);
        } else if (item.getAlign().equalsIgnoreCase("left")) {
            tv.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        } else if (item.getAlign().equalsIgnoreCase("right")) {
            tv.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        }

        if (item.isNameVisible()) {                                            //如果名称显示
//            String strName = item.getBeforeNameString() + item.getName() + item.getEndNameString();
            String strName =  item.getName() ;
            String split = item.getSplitString();
            strName = strName + split;
            tv.setText(strName);
//            if (item.getNameColor().equalsIgnoreCase("red")) {
//                tv.setTextColor(Color.RED);
//            }
        } else
            tv.setVisibility(View.GONE);
        // 初始化下拉框控件
        comboxValue = (ComboBox) editValuelayout.findViewById(R.id.form_fielditem_editvalue);
        comboxValue.setScrollViewListener(mScrollViewListener);
        comboxValue.setGravity(Gravity.CENTER_VERTICAL);
        mComboBoxList.add(comboxValue);
        list_comboboxsize.add(comboxValue);
        /**
         * add by heyang
         * date 2016-7-18
         * if isMustInput is true then change backgroundclolor as "color_mustinputback"
         */
        if (item.isMustInput()) {
//            lineView.setBackgroundResource(R.drawable.form_input_bg_must);
            comboxValue.isMustInput(true);
        }
        // 2015-8-18 for DetailActivity2
//        if (DetailActivity2.currentActivity) {                                        //如果只读
//            TextView textValue = (TextView) editValuelayout
//                    .findViewById(R.id.form_fielditem_textView);
//            /**
//             * add by heyang
//             * date 2016-7-20
//             * 让字垂直居中
//             */
//            textValue.setGravity(Gravity.CENTER_VERTICAL);
//            list_tvsize.add(textValue);
//            textValue.setVisibility(View.VISIBLE);
//            textValue.setText(item.getValue());
//            comboxValue.setVisibility(View.GONE);
//        } else {
        // value处理
        comboxValue.isEnable(false);        //设置自定义控件是否可以编辑
        comboxValue.setVisibility(View.VISIBLE);
//        if (item.getValueColor().equalsIgnoreCase("red")) {
//            comboxValue.setTextColor("#000000"); //设置自定义控件字体颜色
//        }
        EditField edit = new EditField();                        //设置可编辑item
        edit.setKey(item.getKey());
//        edit.setSign(item.getSign());
        edit.setInput(item.getInput());
        edit.setMode(item.getMode());
        edit.setFormKey(item.getFormKey());
        edit.setIsExt(item.isExt());
        comboxValue.setTag(edit);
        comboxValue.setText(item.getValue());
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
            else {
                comboxValue.setText(item.getValue());
            }
            Log.d("item.getInput()", Integer.parseInt(item.getInput().trim()) + "======= ");
        }
        int input = Integer.parseInt(item.getInput().trim());
        if(input == 413 || input == 414 || input == 415){
            comboxValue.setOnClickListener(new OnClickListener413(comboxValue,input,item.getKey(),item.getMaxLength()));
        }else{
            comboxValue.setListViewOnClickListener(new ComboBoxClickListener(item, comboxValue, EditFileds));
        }


//        }

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        params.gravity = Gravity.CENTER_VERTICAL;
        lineView.addView(editValuelayout, params);


        return lineView;
    }

    public LinearLayout setReView401_02_03_12_net(FieldItem item){
        lineView.removeAllViews();
        listform = new ArrayList<CheckForm>();//放单选多选按钮内容
        LinearLayout editValuelayout = null;
        if (item.isNameRN())
            editValuelayout = (LinearLayout) mInflater.inflate(
                    R.layout.layout_formdetail_cell_textview_vertical_net_lib,
                    null);
        else
            editValuelayout = (LinearLayout) mInflater.inflate(
                    R.layout.layout_formdetail_cell_edittext_net_lib, null);
        // name 处理
        TextView tv = (TextView) editValuelayout
                .findViewById(R.id.form_fielditem_text);
        /**
         * add by heyang
         * date 2016-7-18
         * if isMustInput is true then change backgroundclolor as "color_mustinputback"
         */
        if (item.isMustInput()) {
//            tv.setBackgroundColor(context.getResources().getColor(R.color.color_mustinputtouming));
//            lineView.setBackgroundResource(R.drawable.corners_bg_mustinput);
        }
        if (TabStyle == 1) {
            tv.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
            tv.setTextColor(Color.parseColor("#999999"));
            item.setIsSplitWithLine(0);
        }
        list_tvsize.add(tv);
//        if (item.getAlign().equalsIgnoreCase("center")) {
//            tv.setGravity(Gravity.CENTER);
//        } else if (item.getAlign().equalsIgnoreCase("left")) {
//            tv.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
//        } else if (item.getAlign().equalsIgnoreCase("right")) {
//            tv.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
//        }

        if (item.isNameVisible()) {
//            String strName = item.getBeforeNameString() + item.getName() + item.getEndNameString();
            String strName =  item.getName() ;
            String split = item.getSplitString();
            strName = strName + split;
            tv.setText(strName);
//            if (item.getNameColor().equalsIgnoreCase("red")) {
//                tv.setTextColor(Color.RED);
//            }
        } else
            tv.setVisibility(View.GONE);

        // value处理

        final TextView tvEditValue = (TextView) editValuelayout
                .findViewById(R.id.form_fielditem_editvalue);
        FrameLayout flEditValue = (FrameLayout) editValuelayout.findViewById(R.id.fl_fielditem_editvalue);
        flEditValue.setVisibility(View.VISIBLE);
        View view_edit_fielditem = (View)editValuelayout.findViewById(R.id.view_edit_fielditem);
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
        final int input = Integer.parseInt(item.getInput().trim());
        tvEditValue.setText(getName(input,item.Dics,item.getValue()));
//        if (item.getValueFontColor().equalsIgnoreCase("red")) {
//            tvEditValue.setTextColor(Color.RED);
//        }


        final EditField edit = new EditField();
        edit.setKey(item.getKey());
//        edit.setSign(item.getSign());
        edit.setInput(item.getInput());
        edit.setMode(item.getMode());
        edit.setFormKey(item.getFormKey());
        edit.setValue(item.getValue());
        tvEditValue.setTag(edit);
        EditFileds.add(edit);
        if(item.getValue()!=null&&!item.getValue().trim().equals("")){
            ComponentInit.getInstance().getmCallbackFormListener().onFormClick(edit);
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
//            DocResultInfo mDocResultInfo = null;
//            mDocResultInfo = ((WorkFlowFormDetalActivity) context).mDocResultInfo;

            // DocResultInfo mDocResultInfo = ((DetailActivity)
            // getActivity()).mDocResultInfo;
            mDocResultInfo.getResult().setEditFields(EditFileds);
            EditFieldList mustFieldList = EditFieldList
                    .getInstance();
            for (int i = 0; i < mustFieldList.getList().size(); i++) {
                if (mustFieldList
                        .getList()
                        .get(i)
                        .getKey()
                        .equalsIgnoreCase(
                                (edit).getKey())) {
                    mustFieldList.getList().get(i)
                            .setValue(edit.getValue());
                }
            }
        }

        if(input==412){
            tvEditValue.setEnabled(true);
        }else {
            tvEditValue.setEnabled(false);
            view_edit_fielditem.setVisibility(View.VISIBLE);
            if(input == 413 || input == 414 || input == 415){
                view_edit_fielditem.setOnClickListener(new OnClickListener413(tvEditValue,input,item.getKey(),item.getMaxLength()));
            }else{
                view_edit_fielditem.setOnClickListener(new OnClickItem(input, tvEditValue));
            }

        }
        tvEditValue.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                // TODO Auto-generated method stub

                // TODO Auto-generated method stub

                // 将值保存到编辑的字段中，并判断是否需要更新必填字段
                // 1,将已经编辑的字段和值放入到办理是需要回发的EditField[] 中
                if(currentEditTextViews==null||currentEditTextViews.getText()==null||currentEditTextViews.getText().toString().equals("")){
                    return;
                }
                if(input == 413 || input == 414 || input == 415){
                    return;
                }
                String strValue = currentEditTextViews.getText()
                        .toString();
                EditField edit = (EditField) currentEditTextViews
                        .getTag();
                for (int i = 0; i < listform.size(); i++) {
                    if(strValue.equals(listform.get(i).name)){
                        edit.setValue(listform.get(i).getValue());
                    }
                }
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

//                DocResultInfo mDocResultInfo = null;
//
//                mDocResultInfo = ((WorkFlowFormDetalActivity) context).mDocResultInfo;

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
                                    ((EditField) currentEditTextViews
                                            .getTag()).getKey())) {
                        mustFieldList.getList().get(i)
                                .setValue(strValue);
                    }
                }
                ComponentInit.getInstance().getmCallbackFormListener().onFormClick(edit);
                currentEditTextViews.setBackgroundResource(R.color.transparent);
                tvEditValue.setBackgroundResource(R.color.transparent);
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
//
        ImageView img = (ImageView) editValuelayout
                .findViewById(R.id.form_fielditem_editimage);
        img.setImageResource(R.drawable.header_arrow_right);
        img.setVisibility(View.VISIBLE);
        tvEditValue.setBackgroundResource(R.color.transparent);
        if(input == 413 || input == 414 || input == 415){
            img.setOnClickListener(new OnClickListener413(tvEditValue,input,item.getKey(),item.getMaxLength()));
        }else{
            img.setOnClickListener(new OnClickItem(input, tvEditValue));
        }

        /**
         * add by heyang
         * date 2016-7-18
         * if isMustInput is true then change backgroundclolor as "color_mustinputback"
         */

        if (item.isMustInput()) {
//            tvEditValue.setBackgroundResource(R.drawable.text_back);
//            lineView.setBackgroundResource(R.drawable.corners_bg_mustinput);
            tvEditValue.setHint("（必填）");
            tvEditValue.setHintTextColor(Color.parseColor("#CCCCCC"));
        }

        if (item.Dics != null && item.Dics.size() != 0) {
            int inputs = Integer.parseInt(item.getInput().trim());
            for (int i = 0; i < item.Dics.size(); i++) {
                switch (inputs) {
                    case 401:  //单选（结果取ID）
                    case 413://多选 （结果取Value）
                    case 411://多选（结果取ID）
                        listform.add(new CheckForm(item.Dics.get(i).id, item.Dics.get(i).name, ""));
                        break;
                    case 402://单选  （结果取Name）
                    case 414://单选  （结果取Name）
                    case 412://多选 （结果取Name）
                        listform.add(new CheckForm("", item.Dics.get(i).name, ""));
                        break;
                    case 403: //单选 （结果取Value）
                    case 415://多选 （结果取Value）
                        listform.add(new CheckForm("", item.Dics.get(i).name, item.Dics.get(i).value));
                        break;
                }
            }
        }

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        params.gravity = Gravity.CENTER_VERTICAL;

        lineView.addView(editValuelayout, params);
        return lineView;

    }

    class OnClickItem implements View.OnClickListener {

        private int input;
        private TextView tvEditValue;

        public OnClickItem(int input, TextView tvEditValue) {
            this.input = input;
            this.tvEditValue = tvEditValue;
        }

        @Override
        public void onClick(View v) {

            // TODO Auto-generated method stub

            currentEditTextViews = tvEditValue;
//                        Calendar c = Calendar.getInstance();
            if (mSingleChoicePopupWindow != null) {
                mSingleChoicePopupWindow.dismiss();
            }

            mSingleChoicePopupWindow = new SingleChoicePopupWindow(context);
            if (input == 401 || input ==402 || input == 403|| input == 412) {

                if (listform.size() > 0) {
                    String[] singles = new String[listform.size()];
                    for (int i = 0; i < listform.size(); i++) {
                        singles[i] = listform.get(i).name;
                    }
                    mSingleChoicePopupWindow.setCheckForm(singles);
                }

            }
            mSingleChoicePopupWindow.setOnClickOkListener(new SingleChoicePopupWindow.OnClickOkListener() {
                @Override
                public void onClickOk(String birthday) {
                    // TODO Auto-generated method stub
                    if (birthday != null && !birthday.equals("")){
                        currentEditTextViews.setText(birthday);
                        currentEditTextViews.setBackgroundResource(R.color.transparent);
                    }


                }
            });
            mSingleChoicePopupWindow.show(v);
        }

    }

    public class ComboBoxClickListener implements ComboBox.ListViewItemClickListener {
        private FieldItem item;
        private ComboBox comboxValue;
        private List<EditField> EditFileds;

        public ComboBoxClickListener(FieldItem item, ComboBox comboxValue, List<EditField> EditFileds) {
            this.item = item;
            this.comboxValue = comboxValue;
            this.EditFileds = EditFileds;
        }

        @Override
        public void onItemClick(int position) {
            String strValue = comboxValue.getText();

            if (Integer.parseInt(item.getInput().trim()) == 401 || Integer.parseInt(item.getInput().trim()) == 403) {

                for (int i = 0; i < options.length; i++) {
                    if (strValue.equals(options[i])) {
                        if (Integer.parseInt(item.getInput().trim()) == 401)
                            strValue = ids[i];
                        if (Integer.parseInt(item.getInput().trim()) == 403)
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
//            DocResultInfo mDocResultInfo = null;
//            mDocResultInfo = ((WorkFlowFormDetalActivity) context).mDocResultInfo;
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
    }

    public class OnClickListener413 implements View.OnClickListener{
        private View searchTextView;
        private int intputType;
        private String fieldName;
        private int maxLength;
        public OnClickListener413(View searchTextView,int intputType,String fieldName,int maxLength){
            this.searchTextView = searchTextView;
            this.intputType = intputType;
            this.fieldName = fieldName;
            this.maxLength = maxLength;
        }

        @Override
        public void onClick(View view) {
            intputTypes = intputType;
            ComponentInit.getInstance().getmISearch413().getSearch(searchTextView,intputTypes,EditFileds,mDocResultInfo,TabStyle);
            Intent intent = new Intent();
            intent.putExtra("fieldName",fieldName);
            intent.putExtra("maxLength",maxLength);
            intent.setClassName(context,"com.htmitech.proxy.activity.SearchActivity");
            try{
                context.startActivity(intent);
            }catch (Exception e){
            }
        }
    }

}
