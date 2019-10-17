package htmitech.formConfig;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.List;

import htmitech.com.componentlibrary.SuperEditText;
import htmitech.com.componentlibrary.api.ComponentInit;
import htmitech.com.componentlibrary.entity.DocResultInfo;
import htmitech.com.componentlibrary.entity.EditField;
import htmitech.com.componentlibrary.entity.FieldItem;
import htmitech.com.componentlibrary.unit.DensityUtil;
import htmitech.com.componentlibrary.unit.DrawableUtils;
import htmitech.com.componentlibrary.unit.SysConvertUtil;
import htmitech.com.formlibrary.R;
import htmitech.com.componentlibrary.entity.EditFieldList;

/**
 * Created tony 2016/9/6.
 */
public class OtherLayout {
    private Context context;
    private List<EditText> list_etsize;
    private EditText currentEditText;
    private List<EditField> EditFileds;
    private boolean isMustInput;
    private LayoutInflater mInflater;
    private List<TextView> list_tvsize;
    private LinearLayout lineView;
    private DocResultInfo mDocResultInfo;
    private int TabStyle;
    LinearLayout editValuelayout = null;

    public void setMustInputLoacal(String value){
        SuperEditText supperEditValue = (SuperEditText) editValuelayout.findViewById(R.id.form_fielditem_supperedit);
        supperEditValue.mustInputLocal(isMustInput,value,0);
    }
    public OtherLayout(Context context) {
        this.context = context;
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public LinearLayout otherLayoutMeth(String VlineVisible, FieldItem item, LayoutInflater mInflater, List<TextView> list_tvsize, List<EditText> list_etsize, EditText currentEditTextd, List<EditField> EditFiledsd,DocResultInfo mDocResultInfo,int TabStyle) {
        // 4，**********************其他非意见可编辑字段**********************
        this.list_etsize = list_etsize;
        this.currentEditText = currentEditTextd;
        this.EditFileds = EditFiledsd;
        this.mInflater = mInflater;
        this.mDocResultInfo = mDocResultInfo;
        this.list_tvsize = list_tvsize;
        this.TabStyle = TabStyle;
        isMustInput = item.isMustInput();
        lineView = new LinearLayout(context);
        lineView.setOrientation(LinearLayout.VERTICAL);
        lineView.setGravity(Gravity.CENTER_VERTICAL);
        lineView.setBackgroundColor(SysConvertUtil.formattingH(item.getBackColor()));
        if (VlineVisible.equalsIgnoreCase("true")) {
//            lineView.setBackgroundResource(R.drawable.corners_bg_white_press_stroke_v);
            lineView.setBackground(DrawableUtils.getLayerDrawable(context, item.getBackColor()));
        }

        return setReViewOthers(item);

    }

    public LinearLayout setReViewOthers(FieldItem item){
        lineView.removeAllViews();


        if(TabStyle==1){
            if (item.isNameRN()||item.getInput().equals("102")) //判断name 和 value 是否在同一行显示
                editValuelayout = (LinearLayout) mInflater.inflate(
                        R.layout.layout_formdetail_cell_edittext_vertical_net_lib,
                        null);
            else
                editValuelayout = (LinearLayout) mInflater.inflate(
                        R.layout.layout_formdetail_cell_edittext_net_lib, null);
        }else {
            if (item.isNameRN()) //判断name 和 value 是否在同一行显示
                editValuelayout = (LinearLayout) mInflater.inflate(
                        R.layout.layout_formdetail_cell_edittext_vertical_lib,
                        null);
            else
                editValuelayout = (LinearLayout) mInflater.inflate(
                        R.layout.layout_formdetail_cell_edittext_lib, null);
        }

//        if (item.isNameRN()) //判断name 和 value 是否在同一行显示
//            editValuelayout = (LinearLayout) mInflater.inflate(R.layout.layout_formdetail_cell_edittext_vertical_lib, null);
//        else
//            editValuelayout = (LinearLayout) mInflater.inflate(R.layout.layout_formdetail_cell_edittext_lib, null);

        // name 处理
        final TextView tv = (TextView) editValuelayout.findViewById(R.id.form_fielditem_text);
        list_tvsize.add(tv);

//        if (item.getAlign().equalsIgnoreCase("center")) {
//            tv.setGravity(Gravity.CENTER);
//        } else if (item.getAlign().equalsIgnoreCase("left")) {
//            tv.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
//        } else if (item.getAlign().equalsIgnoreCase("right")) {
//            tv.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
//        }
        if(TabStyle==1){
            tv.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
            tv.setTextColor(Color.parseColor("#999999"));
            item.setIsSplitWithLine(0);
        }else {
            if (item.getAlign().equalsIgnoreCase("center")) {
                tv.setGravity(Gravity.CENTER);
            } else if (item.getAlign().equalsIgnoreCase("left")) {
                tv.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
            } else if (item.getAlign().equalsIgnoreCase("right")) {
                tv.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
            }
        }
//        if(!item.isExt()){
//            item.setNameVisible(0);
//        }
        if (item.isNameVisible()) {
            String strName = item.getName();
//            String strName = item.getBeforeNameString() + item.getName() + item.getEndNameString();
            String split = item.getSplitString();
            strName = strName + split;
            tv.setText(strName);
//            if (item.getNameColor().equalsIgnoreCase("red")) {
            if(TabStyle != 1)   // 不为互联网风格的时候对name进行取配置颜色的操作
            tv.setTextColor(item.getValueFontColor());
//            }
        } else
            tv.setVisibility(View.GONE);

//        if (item.getInput() != null && (Integer.parseInt(item.getInput().trim()) == 200 ||
//                Integer.parseInt(item.getInput().trim()) == 201 ||
//                Integer.parseInt(item.getInput().trim()) == 202 ||
//                Integer.parseInt(item.getInput().trim()) == 203 ||
//                Integer.parseInt(item.getInput().trim()) == 101 ||
//                Integer.parseInt(item.getInput().trim()) == 102)) {
        if (item.getInput() != null){
            // value处理
            EditText tvEditValue = (EditText) editValuelayout.findViewById(R.id.form_fielditem_editvalue);
            //增加最大字符限制
            tvEditValue.setFilters(new InputFilter[]{new InputFilter.LengthFilter(item.getMaxLength())});
            SuperEditText supperEditValue = (SuperEditText) editValuelayout.findViewById(R.id.form_fielditem_supperedit);
            supperEditValue.setTabStyle(TabStyle);
            supperEditValue.setInputType(Integer.parseInt(item.getInput().trim()), item.getMaxLength());
            supperEditValue.setFilters(new InputFilter[]{new InputFilter.LengthFilter(item.getMaxLength())});
            if(item.isMustInput()&&TabStyle==1){
                supperEditValue.setHint("（必填）");
                supperEditValue.setHintTextColor(Color.parseColor("#CCCCCC"));
                supperEditValue.IsMustInput(false, item.getValue());
            }else {
                supperEditValue.IsMustInput(item.isMustInput(), item.getValue());
            }
            supperEditValue.setVisibility(View.VISIBLE);
            if (Integer.parseInt(item.getInput().trim()) == 102) {
//                supperEditValue.setMinHeight(DensityUtil.dip2px(context, 110));
                supperEditValue.setMaxLines(4);
                supperEditValue.setMinLines(4);
                supperEditValue.setScrollContainer(true);
                supperEditValue.setGravity(Gravity.CENTER_VERTICAL);
                supperEditValue.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        // 解决scrollView中嵌套EditText导致其不能上下滑动的问题
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        switch (event.getAction() & MotionEvent.ACTION_MASK) {
                            case MotionEvent.ACTION_UP:
                                v.getParent().requestDisallowInterceptTouchEvent(false);
                                break;
                        }
                        return false;
                    }
                });
            } else {
                supperEditValue.setGravity(Gravity.CENTER_VERTICAL);
            }
            list_etsize.add(supperEditValue);

            /**
             * add by heyang
             * date 2016-7-20
             * 让字垂直居中
             */
            tvEditValue.setGravity(Gravity.CENTER_VERTICAL);

            tvEditValue = supperEditValue;
            tvEditValue.setVisibility(View.VISIBLE);
            try {
                String value = item.getValue().replace("\\\\r\\\\n", "\r\n");
                tvEditValue.setText(value + "");
            } catch (Exception e) {
                e.printStackTrace();
            }
            tvEditValue.setTextColor(item.getValueFontColor());
//            if (item.getValueColor().equalsIgnoreCase("red")) {
//                tvEditValue.setTextColor(Color.RED);
//            }
            final EditField edit = new EditField();
            if (item.getKey() != null && !item.getKey().trim().equals(""))
                edit.setKey(item.getKey());
//            edit.setSign(item.getSign());
            edit.setInput(item.getInput());
            edit.setIsExt(item.isExt());
            edit.setMode(item.getMode());
            edit.setFormKey(item.getFormKey());
            edit.setValue(item.getValue());
            tvEditValue.setTag(edit);

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
//                // 2015-8-17
//                StartResultInfo mDocResultInfo = null;
//
//                mDocResultInfo = ((StartDetailActivity) context).mDocResultInfo;

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

            tvEditValue.setOnFocusChangeListener(new View.OnFocusChangeListener() {

                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    // TODO Auto-generated method stub
//                    if (hasFocus) {
//                        currentEditText = (EditText) v;
//                        OtherLayout.this.list_etsize.add(currentEditText);
//                    }
//                    if (isMustInput) {
//                        if (hasFocus) {
//                            ((EditText) v).setBackgroundResource(R.drawable.from_input_native_bt_face);
//                        } else {
//                            ((EditText) v).setBackgroundResource(R.drawable.from_input_native_bt);
//                        }
//
//                    } else {
//                        if (hasFocus) {
//                            ((EditText) v).setBackgroundResource(R.drawable.from_input_native_not_bt_face);
//                        } else {
//                            ((EditText) v).setBackgroundResource(R.drawable.from_input_native_not_bt);
//                        }
//                    }

                    if (hasFocus) {
                        currentEditText = (EditText) v;
                        tv.setTextColor(Color.parseColor("#297BFB"));
                        OtherLayout.this.list_etsize.add(currentEditText);
                    }else {
                        tv.setTextColor(Color.parseColor("#999999"));
                        ComponentInit.getInstance().getmCallbackFormListener().onFormClick(edit);
                        hiddenSoftInputFromWindow((EditText) v);
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
                    if(currentEditText == null){
                        return;
                    }
                    String strValue = currentEditText.getText().toString();
                    EditField edit = (EditField) currentEditText.getTag();
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
//                    DocResultInfo mDocResultInfo = null;
//                    mDocResultInfo = ((WorkFlowFormDetalActivity) context).mDocResultInfo;
                    mDocResultInfo.getResult().setEditFields(EditFileds);
                    // 2,必填字段处理--将输入的意见放入相应必填字段中
                    EditFieldList mustFieldList = EditFieldList.getInstance();
                    for (int i = 0; i < mustFieldList.getList().size(); i++) {
                        if (mustFieldList.getList().get(i).getKey().equalsIgnoreCase(((EditField) currentEditText.getTag()).getKey())) {
                            mustFieldList.getList().get(i).setValue(strValue);
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
//            if (item.isMustInput()) {
//
//            }
            if (item.getBeforeValueString().length() > 0) {
                beforetv.setText(item.getBeforeValueString());
//                if (item.getValueColor().equalsIgnoreCase("red")) {
//                    beforetv.setTextColor(Color.RED);
//                }
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
//            if (item.isMustInput()) {
//                supperEditValue.isMustInput = true;
//                if(item.getValue() == null || item.getValue().equals("")){
//                    endtv.setBackgroundColor(context.getResources().getColor(R.color.color_mustinputtouming));
//
//                    beforetv.setBackgroundColor(context.getResources().getColor(R.color.color_mustinputtouming));
//
//                    lineView.setBackgroundResource(R.drawable.corners_bg_mustinput);
//                }
//
//            }
            if (item.getEndValueString().length() > 0) {
                endtv.setText(item.getEndValueString());
//                if (item.getValueColor().equalsIgnoreCase("red")) {
//                    endtv.setTextColor(Color.RED);
//                }
                endtv.setVisibility(View.VISIBLE);
            } else endtv.setVisibility(View.GONE);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 1);
            params.gravity = Gravity.CENTER_VERTICAL;
            lineView.addView(editValuelayout, params);

            return lineView;

        }
        return lineView;
    }
    /**
     * EditText隐藏键盘
     */
    public static void hiddenSoftInputFromWindow(EditText editText) {
        InputMethodManager m = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//        m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        m.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }
}
