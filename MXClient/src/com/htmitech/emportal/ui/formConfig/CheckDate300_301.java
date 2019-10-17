package com.htmitech.emportal.ui.formConfig;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.htmitech.datepicker.PopChooseTimeHelper;
import com.htmitech.emportal.HtmitechApplication;
import com.htmitech.emportal.R;
import com.htmitech.emportal.entity.DocResultInfo;
import com.htmitech.emportal.entity.EditField;
import com.htmitech.emportal.entity.EditFieldList;
import com.htmitech.photoselector.model.workflow.FieldItem;
import com.htmitech.emportal.ui.detail.DetailActivity;
import com.htmitech.emportal.ui.detail.DetailActivity2;
import com.htmitech.htcommonformplugin.entity.Editfields;
import com.htmitech.myEnum.TimeEnum;
import com.htmitech.photoselector.model.Fielditems;
import com.minxing.client.util.SysConvertUtil;

import java.util.List;

/**
 * Created by htrf-pc on 2016/9/5.
 */
public class CheckDate300_301 {
    private Context context;

    private PopChooseTimeHelper mPopBirthHelper;

    private TextView currentEditTextView;

    private List<EditField> EditFileds;
    private List<Editfields> mEditFileds;

    public CheckDate300_301(Context context) {
        this.context = context;
    }

    public LinearLayout getCheckDateLayout(String VlineVisible, FieldItem item, LayoutInflater mInflater, List<TextView> list_tvsize, List<EditField> EditFileds, PopChooseTimeHelper mPopChooseTimeHelper, TextView currentEditTextViews) {
        // 3, ***********************日期选择***************************
        this.EditFileds = EditFileds;
        this.mPopBirthHelper = mPopChooseTimeHelper;
        this.currentEditTextView = currentEditTextViews;
        LinearLayout lineView = new LinearLayout(
                HtmitechApplication.instance());
        lineView.setOrientation(LinearLayout.VERTICAL);
        lineView.setGravity(Gravity.CENTER_VERTICAL);
        lineView.setBackgroundColor(SysConvertUtil.formattingH(item.getBackColor()));
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
            tv.setBackgroundColor(context.getResources().getColor(R.color.color_mustinputtouming));
            lineView.setBackgroundResource(R.drawable.corners_bg_mustinput);
        }
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
        tvEditValue.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // TODO Auto-generated method stub

                currentEditTextView = (TextView) v;
//                        Calendar c = Calendar.getInstance();
                if (mPopBirthHelper != null) {
                    mPopBirthHelper.dismiss();
                }
                mPopBirthHelper = new PopChooseTimeHelper(context);


                if (input == 300) {
                    mPopBirthHelper.setTimeEnums(TimeEnum.YEAR, TimeEnum.MONTH, TimeEnum.DAY);
                } else {
                    mPopBirthHelper.setTimeEnums(TimeEnum.YEAR, TimeEnum.MONTH, TimeEnum.DAY, TimeEnum.HOUR, TimeEnum.SEX);
                }

                mPopBirthHelper.setOnClickOkListener(new PopChooseTimeHelper.OnClickOkListener() {

                    @Override
                    public void onClickOk(String birthday) {
                        // TODO Auto-generated method stub

                        if (birthday != null && !birthday.equals(""))
                            currentEditTextView.setText(birthday);
                    }
                });

                mPopBirthHelper.show(v);
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
                if (CheckDate300_301.this.EditFileds != null && CheckDate300_301.this.EditFileds.size() == 0)
                    CheckDate300_301.this.EditFileds.add(edit);
                else {
                    boolean hasfind = false;
                    for (int j = 0; j < CheckDate300_301.this.EditFileds.size(); j++) {
                        if (CheckDate300_301.this.EditFileds.get(j).getKey()
                                .equals(edit.getKey())) {
                            hasfind = true;
                            CheckDate300_301.this.EditFileds.get(j).setValue(edit.getValue());
                            break;
                        }
                    }
                    if (!hasfind)
                        CheckDate300_301.this.EditFileds.add(edit);
                }
                Log.d("FormFragment", "EditFileds:" + CheckDate300_301.this.EditFileds);

                DocResultInfo mDocResultInfo = null;
                if (DetailActivity2.currentActivity) {
                    mDocResultInfo = ((DetailActivity2) context).mDocResultInfo;
                } else {
                    mDocResultInfo = ((DetailActivity) context).mDocResultInfo;
                }
                        /*DocResultInfo mDocResultInfo = ((StartDetailActivity) getActivity()).mDocResultInfo;*/
                mDocResultInfo.getResult().setEditFields(CheckDate300_301.this.EditFileds);
                // 2,必填字段处理--将输入的意见放入相应必填字段中
                EditFieldList mustFieldList = EditFieldList
                        .getInstance();
                for (int i = 0; i < mustFieldList.getList().size(); i++) {
                    if (mustFieldList
                            .getList()
                            .get(i)
                            .getKey()
                            .equalsIgnoreCase(
                                    ((EditField) currentEditTextView
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
    }

    public LinearLayout getCheckDateLayoutCommonForm(boolean VlineVisible, Fielditems item, LayoutInflater mInflater, List<TextView> list_tvsize, List<Editfields> EditFileds, PopChooseTimeHelper mPopChooseTimeHelper, TextView currentEditTextViews) {
        // 3, ***********************日期选择***************************
        this.mEditFileds = EditFileds;
        this.mPopBirthHelper = mPopChooseTimeHelper;
        this.currentEditTextView = currentEditTextViews;
        LinearLayout lineView = new LinearLayout(
                HtmitechApplication.instance());
        lineView.setOrientation(LinearLayout.VERTICAL);
        lineView.setGravity(Gravity.CENTER_VERTICAL);
        lineView.setBackgroundColor(SysConvertUtil.formattingH(item.getFiled_backcolor()));
        if (VlineVisible)
            lineView.setBackgroundResource(R.drawable.corners_bg_white_press_stroke);

        // 2015-8-18 for DetailActivity2
        if (DetailActivity2.currentActivity)
            lineView.setEnabled(false);

        LinearLayout editValuelayout = null;
        if (item.isName_rn())
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
        if (item.isMustinput()) {
            tv.setBackgroundColor(context.getResources().getColor(R.color.color_mustinputtouming));
            lineView.setBackgroundResource(R.drawable.corners_bg_mustinput);
        }
        list_tvsize.add(tv);
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
        if (item.getValue_color().equalsIgnoreCase("red")) {
            tvEditValue.setTextColor(Color.RED);
        }

        // 2015-8-18 for DetailActivity2
        if (DetailActivity2.currentActivity)
            tvEditValue.setEnabled(false);

        EditField edit = new EditField();
        edit.setKey(item.getKey());
        edit.setSign(item.getValue());
        edit.setInput(item.getInput_type());
        edit.setMode(item.getMode());
        edit.setFormKey(item.getFormkey());
        tvEditValue.setTag(edit);
        final int input = Integer.parseInt(item.getInput_type().trim());
        tvEditValue.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // TODO Auto-generated method stub

                currentEditTextView = (TextView) v;
//                        Calendar c = Calendar.getInstance();
                if (mPopBirthHelper != null) {
                    mPopBirthHelper.dismiss();
                }
                mPopBirthHelper = new PopChooseTimeHelper(context);


                if (input == 300) {
                    mPopBirthHelper.setTimeEnums(TimeEnum.YEAR, TimeEnum.MONTH, TimeEnum.DAY);
                } else {
                    mPopBirthHelper.setTimeEnums(TimeEnum.YEAR, TimeEnum.MONTH, TimeEnum.DAY, TimeEnum.HOUR, TimeEnum.SEX);
                }

                mPopBirthHelper.setOnClickOkListener(new PopChooseTimeHelper.OnClickOkListener() {

                    @Override
                    public void onClickOk(String birthday) {
                        // TODO Auto-generated method stub

                        if (birthday != null && !birthday.equals(""))
                            currentEditTextView.setText(birthday);
                    }
                });

                mPopBirthHelper.show(v);
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
                Editfields edit = (Editfields) currentEditTextView
                        .getTag();
                edit.setValue(strValue);
                if (CheckDate300_301.this.mEditFileds != null && CheckDate300_301.this.mEditFileds.size() == 0)
                    CheckDate300_301.this.mEditFileds.add(edit);
                else {
                    boolean hasfind = false;
                    for (int j = 0; j < CheckDate300_301.this.mEditFileds.size(); j++) {
                        if (CheckDate300_301.this.mEditFileds.get(j).getKey()
                                .equals(edit.getKey())) {
                            hasfind = true;
                            CheckDate300_301.this.mEditFileds.get(j).setValue(edit.getValue());
                            break;
                        }
                    }
                    if (!hasfind)
                        CheckDate300_301.this.mEditFileds.add(edit);
                }
                Log.d("FormFragment", "EditFileds:" + CheckDate300_301.this.mEditFileds);

                DocResultInfo mDocResultInfo = null;
                if (DetailActivity2.currentActivity) {
                    mDocResultInfo = ((DetailActivity2) context).mDocResultInfo;
                } else {
                    mDocResultInfo = ((DetailActivity) context).mDocResultInfo;
                }
                        /*DocResultInfo mDocResultInfo = ((StartDetailActivity) getActivity()).mDocResultInfo;*/
                mDocResultInfo.getResult().setEditFields(CheckDate300_301.this.EditFileds);
                // 2,必填字段处理--将输入的意见放入相应必填字段中
                EditFieldList mustFieldList = EditFieldList
                        .getInstance();
                for (int i = 0; i < mustFieldList.getList().size(); i++) {
                    if (mustFieldList
                            .getList()
                            .get(i)
                            .getKey()
                            .equalsIgnoreCase(
                                    ((EditField) currentEditTextView
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
        if (item.isMustinput()) {
            tvEditValue.setBackgroundResource(R.drawable.text_back);
            lineView.setBackgroundResource(R.drawable.corners_bg_mustinput);
        }

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        params.gravity = Gravity.CENTER_VERTICAL;

        lineView.addView(editValuelayout, params);
        return lineView;
    }
}
