package com.htmitech.emportal.ui.formConfig;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.htmitech.edittext.SuperEditText;
import com.htmitech.emportal.HtmitechApplication;
import com.htmitech.emportal.R;
import com.htmitech.emportal.entity.DocResultInfo;
import com.htmitech.emportal.entity.EditField;
import com.htmitech.emportal.entity.EditFieldList;
import com.htmitech.emportal.ui.detail.DetailActivity;
import com.htmitech.emportal.ui.detail.DetailActivity2;
import com.htmitech.htcommonformplugin.entity.Editfields;
import com.htmitech.photoselector.model.Fielditems;
import com.htmitech.htcommonformplugin.entity.GetDetailEntity;
import com.htmitech.unit.DensityUtil;
import com.minxing.client.util.SysConvertUtil;
import com.htmitech.photoselector.model.workflow.FieldItem;

import java.util.List;

/**
 * Created tony 2016/9/6.
 */
public class OtherLayout {
    private Context context;
    private  List<EditText> list_etsize;
    private EditText currentEditText;
    private List<EditField> EditFileds;
    private List<Editfields> mEditFileds;

    public OtherLayout(Context context){
        this.context = context;
    }


    public LinearLayout otherLayoutMeth(String VlineVisible,FieldItem item,LayoutInflater mInflater,List<TextView> list_tvsize, List<EditText> list_etsize,EditText currentEditTextd,List<EditField> EditFiledsd){
        // 4，**********************其他非意见可编辑字段**********************
        this.list_etsize = list_etsize;
        this.currentEditText = currentEditTextd;
        this.EditFileds = EditFiledsd;
        LinearLayout lineView = new LinearLayout(
                HtmitechApplication.instance());
        lineView.setOrientation(LinearLayout.VERTICAL);
        lineView.setGravity(Gravity.CENTER_VERTICAL);
        lineView.setBackgroundColor(SysConvertUtil.formattingH(item.getBackColor()));
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

        if (item.getAlign().equalsIgnoreCase("center")) {
            tv.setGravity(Gravity.CENTER);
        } else if (item.getAlign().equalsIgnoreCase("left")) {
            tv.setGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL);
        } else if (item.getAlign().equalsIgnoreCase("right")) {
            tv.setGravity(Gravity.RIGHT|Gravity.CENTER_VERTICAL);
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
            //增加最大字符限制
            tvEditValue.setFilters(new InputFilter[]{new InputFilter.LengthFilter(item.getMaxLength())});
            SuperEditText supperEditValue = (SuperEditText) editValuelayout.findViewById(R.id.form_fielditem_supperedit);
            supperEditValue.setInputType(Integer.parseInt(item.getInput().trim()), item.getMaxLength());
            supperEditValue.setFilters(new InputFilter[]{new InputFilter.LengthFilter(item.getMaxLength())});
            supperEditValue.IsMustInput(item.isMustInput(), item.getValue());
            supperEditValue.setVisibility(View.VISIBLE);
            if( Integer.parseInt(item.getInput().trim()) == 102){
                supperEditValue.setMinHeight(DensityUtil.dip2px(context,110));
                supperEditValue.setGravity(Gravity.TOP);
            }else {
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
            // 2015-8-18 for DetailActivity2
            if (DetailActivity2.currentActivity) {
                tvEditValue.setEnabled(false);
            }
            tvEditValue.setVisibility(View.VISIBLE);
            try{
                String value = item.getValue().replace("\\\\r\\\\n", "\r\n");
                tvEditValue.setText(value + "");
            }catch (Exception e){
                e.printStackTrace();
            }
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
                    .setOnFocusChangeListener(new View.OnFocusChangeListener() {

                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            // TODO Auto-generated method stub
                            if (hasFocus) {
                                currentEditText = (EditText) v;
                                OtherLayout.this.list_etsize.add(currentEditText);
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
                        mDocResultInfo = ((DetailActivity2) context).mDocResultInfo;
                    } else {
                        mDocResultInfo = ((DetailActivity) context).mDocResultInfo;
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
                                        ((EditField) currentEditText
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
//            if (item.isMustInput()) {
//
//            }
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
        return lineView;
    }


    public LinearLayout otherLayoutMethCommonForm(boolean VlineVisible, Fielditems item, LayoutInflater mInflater, List<TextView> list_tvsize, List<EditText> list_etsize, EditText currentEditTextd, List<Editfields> EditFileds){
        // 4，**********************其他非意见可编辑字段**********************
        this.list_etsize = list_etsize;
        this.currentEditText = currentEditTextd;
        this.mEditFileds = EditFileds;
        LinearLayout lineView = new LinearLayout(
                HtmitechApplication.instance());
        lineView.setOrientation(LinearLayout.VERTICAL);
        lineView.setGravity(Gravity.CENTER_VERTICAL);
        lineView.setBackgroundColor(SysConvertUtil.formattingH(item.getFiled_backcolor()));
        if (VlineVisible)
            lineView.setBackgroundResource(R.drawable.corners_bg_white_press_stroke);

        LinearLayout editValuelayout = null;
        if (item.isName_rn()) //判断name 和 value 是否在同一行显示
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
        if (item.isMustinput()) {
            tv.setBackgroundColor(context.getResources().getColor(R.color.color_mustinputtouming));
            lineView.setBackgroundResource(R.drawable.corners_bg_mustinput);
        }
        if (item.getAlign().equalsIgnoreCase("center")) {
            tv.setGravity(Gravity.CENTER);
        } else if (item.getAlign().equalsIgnoreCase("left")) {
            tv.setGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL);
        } else if (item.getAlign().equalsIgnoreCase("right")) {
            tv.setGravity(Gravity.RIGHT|Gravity.CENTER_VERTICAL);
        }

        if (item.isName_visible()) {
            String strName = item.getBefore_name_str()
                    + item.getName() + item.getEnd_name_str();
            String split = item.getSplit_str();
            strName = strName + split;
            tv.setText(strName);
            if (item.getName_color().equalsIgnoreCase("red")) {
                tv.setTextColor(Color.RED);
            }
        } else
            tv.setVisibility(View.GONE);

        if (item.getInput_type() != null && (Integer.parseInt(item.getInput_type().trim()) == 200 ||
                Integer.parseInt(item.getInput_type().trim()) == 201 ||
                Integer.parseInt(item.getInput_type().trim()) == 202 ||
                Integer.parseInt(item.getInput_type().trim()) == 203 ||
                Integer.parseInt(item.getInput_type().trim()) == 101 ||
                Integer.parseInt(item.getInput_type().trim()) == 102)) {
            // value处理
            EditText tvEditValue = (EditText) editValuelayout.findViewById(R.id.form_fielditem_editvalue);
            SuperEditText supperEditValue = (SuperEditText) editValuelayout.findViewById(R.id.form_fielditem_supperedit);
            supperEditValue.setFilters(new InputFilter[]{new InputFilter.LengthFilter(item.getMaxlength())});
            supperEditValue.setInputType(Integer.parseInt(item.getInput_type().trim()), item.getMaxlength());
            supperEditValue.IsMustInput(item.isMustinput(), item.getValue());
            supperEditValue.setVisibility(View.VISIBLE);
            if( Integer.parseInt(item.getInput_type().trim()) == 102){
                supperEditValue.setMinHeight(DensityUtil.dip2px(context,110));
                supperEditValue.setGravity(Gravity.TOP);
            }else {
                supperEditValue.setGravity(Gravity.CENTER_VERTICAL);
            }
            list_etsize.add(supperEditValue);
            tvEditValue = supperEditValue;
            // 2015-8-18 for DetailActivity2
            if (DetailActivity2.currentActivity) {
                tvEditValue.setEnabled(false);
            }
            tvEditValue.setVisibility(View.VISIBLE);
            try{
                String value = item.getValue().replace("\\\\r\\\\n", "\r\n");
                tvEditValue.setText(value + "");
            }catch (Exception e){
                e.printStackTrace();
            }
            if (item.getValue_color().equalsIgnoreCase("red")) {
                tvEditValue.setTextColor(Color.RED);
            }
            Editfields edit = new Editfields();
            edit.setKey(item.getKey());
            edit.setValue(item.getValue());
            tvEditValue.setTag(edit);
            tvEditValue
                    .setOnFocusChangeListener(new View.OnFocusChangeListener() {

                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            // TODO Auto-generated method stub
                            if (hasFocus) {
                                currentEditText = (EditText) v;
                                OtherLayout.this.list_etsize.add(currentEditText);
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
                    Editfields edit = (Editfields) currentEditText.getTag();
                    edit.setValue(strValue);
                    if (mEditFileds != null && mEditFileds.size() == 0)
                        mEditFileds.add(edit);
                    else {
                        boolean hasfind = false;
                        for (int j = 0; j < mEditFileds.size(); j++) {
                            if (mEditFileds.get(j).getKey()
                                    .equals(edit.getKey())) {
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
//                        mGetDetailEntity = ((DetailActivity2) context).mDocResultInfo;
                    } else {
//                        mGetDetailEntity = ((GeneralFormDetalActivity) context).mGetDetailEntity;
                    }
                    // DocResultInfo mDocResultInfo = ((DetailActivity)
                    // getActivity()).mDocResultInfo;
                    mGetDetailEntity.getResult().setEditfields(mEditFileds);
                    // 2,必填字段处理--将输入的意见放入相应必填字段中
//                    EditFieldList mustFieldList = EditFieldList
//                            .getInstance();
//                    for (int i = 0; i < mustFieldList.getList().size(); i++) {
//                        if (mustFieldList
//                                .getList()
//                                .get(i)
//                                .getKey()
//                                .equalsIgnoreCase(
//                                        ((EditField) currentEditText
//                                                .getTag()).getKey())) {
//                            mustFieldList.getList().get(i)
//                                    .setValue(strValue);
//                        }
//                    }

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
            if (item.getBefore_value_str().length() > 0) {
                beforetv.setText(item.getBefore_value_str());
                if (item.getValue_color().equalsIgnoreCase("red")) {
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
            if (item.isMustinput()) {
                supperEditValue.isMustInput = true;
                if(item.getValue() == null || item.getValue().equals("")){
                    endtv.setBackgroundColor(context.getResources().getColor(R.color.color_mustinputtouming));

                    beforetv.setBackgroundColor(context.getResources().getColor(R.color.color_mustinputtouming));

                    lineView.setBackgroundResource(R.drawable.corners_bg_mustinput);
                }

            }
            if (item.getEnd_value_str().length() > 0) {
                endtv.setText(item.getEnd_value_str());
                if (item.getValue_color().equalsIgnoreCase("red")) {
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
        return lineView;
    }
}
