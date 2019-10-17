package htmitech.formConfig;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.Calendar;
import java.util.Date;
import java.util.List;

import htmitech.com.componentlibrary.api.ComponentInit;
import htmitech.com.componentlibrary.datepicker.PopChooseTimeHelper;
import htmitech.com.componentlibrary.entity.DocResultInfo;
import htmitech.com.componentlibrary.entity.EditField;
import htmitech.com.componentlibrary.entity.FieldItem;
import htmitech.com.componentlibrary.myEnum.TimeEnum;
import htmitech.com.componentlibrary.unit.DrawableUtils;
import htmitech.com.componentlibrary.unit.SysConvertUtil;
import htmitech.com.formlibrary.R;
import htmitech.com.componentlibrary.entity.EditFieldList;
import htmitech.com.componentlibrary.entity.Editfields;

/**
 * Created by htrf-pc on 2016/9/5.
 */
public class CheckDate300_301 {
    private Context context;

    private PopChooseTimeHelper mPopBirthHelper;

    private TextView currentEditTextView;

    private List<EditField> EditFileds;
    private List<Editfields> mEditFileds;

    private LayoutInflater mInflater;
    private LinearLayout lineView;
    private List<TextView> list_tvsize;
    private DocResultInfo mDocResultInfo;
    private int TabStyle;
    private String[] am_pm = new String[]{"上午","下午"} ;;
    private String[] weeks = new String []{"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};
    private TextView tvEditValue;
    public void setMustInputLoacal(){
        if(TabStyle == 0){
            tvEditValue.setBackgroundResource(R.drawable.form_input_bg_must_local);
        }else{
            tvEditValue.setBackgroundResource(R.drawable.form_input_bg_must_style_local);
        }

    }
    public CheckDate300_301(Context context) {
        this.context = context;
    }

    public String getName(int inputType,String currentValue){
        if(TextUtils.equals(currentValue,"getnow()")){
            Calendar c = Calendar.getInstance();
            int norYear = c.get(Calendar.YEAR);
            int curMonth = c.get(Calendar.MONTH) + 1;//通过Calendar算出的月数要+1
            int curDate = c.get(Calendar.DATE);
            int apm = c.get(Calendar.AM_PM);
            int curWeek = c.get(Calendar.DAY_OF_WEEK);
            int curMin = c.get(Calendar.HOUR_OF_DAY);
            int curSec = c.get(Calendar.MINUTE);
            int curSeconds = c.get(Calendar.SECOND);

            Calendar cal = Calendar.getInstance();//这一句必须要设置，否则美国认为第一天是周日，而我国认为是周一，对计算当期日期是第几周会有错误
            cal.setFirstDayOfWeek(Calendar.MONDAY); // 设置每周的第一天为星期一
            cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);// 每周从周一开始
            cal.setMinimalDaysInFirstWeek(7); // 设置每周最少为7天
            cal.setTime(new Date());
            int curYWeeks=cal.get(Calendar.WEEK_OF_YEAR);
            int curYear = norYear;

            switch (inputType){
                case 300:
                    return curYear +"-"+String.format("%02d", curMonth)+"-"+String.format("%02d", curMonth);
                case 301:
                    return curYear +"-"+String.format("%02d", curMonth)+"-"+String.format("%02d", curMonth)+" "+curMin+":"+curSec+":"+curSeconds;
                case 302:
                    return curYear+"";
                case 303:
                    return curYear +"-"+String.format("%02d", curMonth);
                case 304:
                    return weeks[curWeek];
                case 305:
                    return curYear +"-"+String.format("%02d", curMonth)+"-"+String.format("%02d", curMonth) +" "+am_pm[curYWeeks];
            }
        }
        return currentValue;

    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public LinearLayout getCheckDateLayout(String VlineVisible, FieldItem item, LayoutInflater mInflater, List<TextView> list_tvsize, List<EditField> EditFileds, PopChooseTimeHelper mPopChooseTimeHelper, TextView currentEditTextViews, int TabStyle, DocResultInfo mDocResultInfo) {
        // 3, ***********************日期选择***************************
        this.EditFileds = EditFileds;
        this.mPopBirthHelper = mPopChooseTimeHelper;
        this.currentEditTextView = currentEditTextViews;
        this.mInflater = mInflater;
        this.list_tvsize = list_tvsize;
        this.TabStyle = TabStyle;
        this.mDocResultInfo = mDocResultInfo;
        lineView = new LinearLayout(context);
        lineView.setOrientation(LinearLayout.VERTICAL);
        lineView.setGravity(Gravity.CENTER_VERTICAL);
        lineView.setBackgroundColor(SysConvertUtil.formattingH(item.getBackColor()));
        if (VlineVisible.equalsIgnoreCase("true")) {
//            lineView.setBackgroundResource(R.drawable.corners_bg_white_press_stroke_v);
            lineView.setBackground(DrawableUtils.getLayerDrawable(context, item.getBackColor()));//设置显示背景
        }
        return setReView300_301(item);

    }

    public LinearLayout setReView300_301(FieldItem item) {
        lineView.removeAllViews();

        LinearLayout editValuelayout = null;
        if (TabStyle == 1) {
            if (item.isNameRN())
                editValuelayout = (LinearLayout) mInflater.inflate(
                        R.layout.layout_formdetail_cell_textview_vertical_net_lib,
                        null);
            else
                editValuelayout = (LinearLayout) mInflater.inflate(
                        R.layout.layout_formdetail_cell_textview_net_lib, null);
        } else {
            if (item.isNameRN())
                editValuelayout = (LinearLayout) mInflater.inflate(
                        R.layout.layout_formdetail_cell_textview_vertical_lib,
                        null);
            else
                editValuelayout = (LinearLayout) mInflater.inflate(
                        R.layout.layout_formdetail_cell_textview_lib, null);
        }

        // name 处理
        TextView tv = (TextView) editValuelayout.findViewById(R.id.form_fielditem_text);
        // value处理
        tvEditValue = (TextView) editValuelayout.findViewById(R.id.form_fielditem_editvalue);
        /**
         * add by heyang
         * date 2016-7-18
         * if isMustInput is true then change backgroundclolor as "color_mustinputback"
         */

        list_tvsize.add(tv);
        if (TabStyle == 1) {
            tv.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
            tv.setTextColor(Color.parseColor("#999999"));
            item.setIsSplitWithLine(0);
        } else {
            if (item.getAlign().equalsIgnoreCase("center")) {
                tv.setGravity(Gravity.CENTER);
            } else if (item.getAlign().equalsIgnoreCase("left")) {
                tv.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
            } else if (item.getAlign().equalsIgnoreCase("right")) {
                tv.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
            }
        }

        if (item.isNameVisible()) {
//            String strName = item.getBeforeNameString() + item.getName() + item.getEndNameString();
            String strName = item.getName();
            String split = item.getSplitString();
            strName = strName + split;
            tv.setText(strName);
        } else {
            tv.setVisibility(View.GONE);
        }

        editValuelayout.setBackgroundResource(R.drawable.corners_bg_white_press_stroke_or);
//        TextView tvEditValue = (TextView) editValuelayout.findViewById(R.id.form_fielditem_editvalue);
//                if (item.isMustInput() && (item.getValue() == null || item.getValue().trim().length() == 0))
//                    tvEditValue.setBackgroundResource(R.drawable.corners_bg_mustinput);

        /**
         * add by heyang
         * date 2016-7-20
         * 设置字体垂直居中
         */
        final int input = Integer.parseInt(item.getInput().trim());
        tvEditValue.setGravity(Gravity.CENTER_VERTICAL);
        list_tvsize.add(tv);
        list_tvsize.add(tvEditValue);
        tvEditValue.setVisibility(View.VISIBLE);
        String values = getName(input,item.getValue());
        tvEditValue.setText(values);
        item.setValue(values);
        EditField edit = new EditField();
        edit.setKey(item.getKey());
        edit.setInput(item.getInput());
        edit.setMode(item.getMode());
        edit.setFormKey(item.getFormKey());
        edit.setIsExt(item.isExt());
        edit.setValue(item.getValue());
        tvEditValue.setTag(edit);
        tvEditValue.setHint("请选择时间");

        if (item.getValue() != null && !item.getValue().trim().equals("")) {
            ComponentInit.getInstance().getmCallbackFormListener().onFormClick(edit);
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
//                    StartResultInfo mDocResultInfo = null;

//                    mDocResultInfo = ((StartDetailActivity) context).mDocResultInfo;

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

//        if(item.getValue() != null && !item.getValue().trim().equals("")) {
//                    ComponentInit.getInstance().getmCallbackFormListener().onFormClick(edit);
//                    if (EditFileds != null && EditFileds.size() == 0)
//                        EditFileds.add(edit);
//                    else {
//                        boolean hasfind = false;
//                        for (int j = 0; j < EditFileds.size(); j++) {
//                            if (EditFileds.get(j).getKey()
//                                    .equals(edit.getKey())) {
//                                hasfind = true;
//                                EditFileds.get(j).setValue(edit.getValue());
//                                break;
//                            }
//                        }
//                        if (!hasfind)
//                            EditFileds.add(edit);
//                    }
//                    Log.d("FormFragment", "EditFileds:" + EditFileds);
//                    // 2015-8-17
////                    StartResultInfo mDocResultInfo = null;
//
////                    mDocResultInfo = ((StartDetailActivity) context).mDocResultInfo;
//
//                    // DocResultInfo mDocResultInfo = ((DetailActivity)
//                    // getActivity()).mDocResultInfo;
//                    mDocResultInfo.getResult().setEditFields(EditFileds);
//                    EditFieldList mustFieldList = EditFieldList
//                            .getInstance();
//                    for (int i = 0; i < mustFieldList.getList().size(); i++) {
//                        if (mustFieldList
//                                .getList()
//                                .get(i)
//                                .getKey()
//                                .equalsIgnoreCase(
//                                        (edit).getKey())) {
//                            mustFieldList.getList().get(i)
//                                    .setValue(edit.getValue());
//                        }
//                    }
//        }



        tvEditValue.setOnClickListener(new OnClickItem(input, tvEditValue));

        tvEditValue.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub

                // TODO Auto-generated method stub

                // 将值保存到编辑的字段中，并判断是否需要更新必填字段
                // 1,将已经编辑的字段和值放入到办理是需要回发的EditField[] 中
                String strValue = currentEditTextView.getText().toString();
                EditField edit = (EditField) currentEditTextView.getTag();
                edit.setValue(strValue);
                if (CheckDate300_301.this.EditFileds != null && CheckDate300_301.this.EditFileds.size() == 0)
                    CheckDate300_301.this.EditFileds.add(edit);
                else {
                    boolean hasfind = false;
                    for (int j = 0; j < CheckDate300_301.this.EditFileds.size(); j++) {
                        if (CheckDate300_301.this.EditFileds.get(j).getKey().equals(edit.getKey())) {
                            hasfind = true;
                            CheckDate300_301.this.EditFileds.get(j).setValue(edit.getValue());
                            break;
                        }
                    }
                    if (!hasfind)
                        CheckDate300_301.this.EditFileds.add(edit);
                }
                Log.d("FormFragment", "EditFileds:" + CheckDate300_301.this.EditFileds);

//                DocResultInfo mDocResultInfo = this.mDocResultInfo;

//                mDocResultInfo = ((WorkFlowFormDetalActivity) context).mDocResultInfo;
                        /*DocResultInfo mDocResultInfo = ((StartDetailActivity) getActivity()).mDocResultInfo;*/
                mDocResultInfo.getResult().setEditFields(CheckDate300_301.this.EditFileds);
                // 2,必填字段处理--将输入的意见放入相应必填字段中
                EditFieldList mustFieldList = EditFieldList.getInstance();
                for (int i = 0; i < mustFieldList.getList().size(); i++) {
                    if (mustFieldList.getList().get(i).getKey().equalsIgnoreCase(((EditField) currentEditTextView.getTag()).getKey())) {
                        mustFieldList.getList().get(i).setValue(strValue);
                    }
                }
                ComponentInit.getInstance().getmCallbackFormListener().onFormClick(edit);
//                tvEditValue.setBackgroundResource(R.drawable.text_back);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        if (TabStyle == 1) {
            ImageView img = (ImageView) editValuelayout
                    .findViewById(R.id.form_fielditem_editimage);
            img.setImageResource(R.drawable.icon_time);
            img.setVisibility(View.VISIBLE);
            img.setOnClickListener(new OnClickItem(input, tvEditValue));
        }

        /**
         * add by heyang
         * date 2016-7-18
         * if isMustInput is true then change backgroundclolor as "color_mustinputback"
         */
        if (item.isMustInput() && TabStyle == 0) {
            tvEditValue.setBackgroundResource(R.drawable.form_edittext_stroke);
            lineView.setBackgroundResource(R.drawable.corners_bg_mustinput);
            if(TextUtils.isEmpty(item.getValue())){
                tvEditValue.setHintTextColor(Color.parseColor("#CCCCCC"));
            }
        }else if(!item.isMustInput() && TabStyle == 0){
            tvEditValue.setBackgroundResource(R.drawable.form_edittext_stroke_nomustinput);
        }

        if(TabStyle == 1){
            tvEditValue.setHint("（必填）");
            if(TextUtils.isEmpty(item.getValue())){
                tvEditValue.setHintTextColor(Color.parseColor("#CCCCCC"));
            }
//            tvEditValue.setBackgroundColor(g);

        }

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
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

            currentEditTextView = tvEditValue;
//                        Calendar c = Calendar.getInstance();
            if (mPopBirthHelper != null) {
                mPopBirthHelper.dismiss();
            }
            mPopBirthHelper = new PopChooseTimeHelper(context);
            if (input == 300) {
//                    mPopBirthHelper.setTimeEnums(TimeEnum.YEAR, TimeEnum.MONTH, TimeEnum.DAY);
                mPopBirthHelper.setTimeEnums(TimeEnum.YEAR, TimeEnum.MONTH, TimeEnum.DAY, TimeEnum.AMPM);
            } else if (input == 302) {
                mPopBirthHelper.setTimeEnums(TimeEnum.YEAR);
            } else if (input == 303) {
                mPopBirthHelper.setTimeEnums(TimeEnum.YEAR, TimeEnum.MONTH);
            } else if (input == 304) {
                mPopBirthHelper.setTimeEnums(TimeEnum.YEAR, TimeEnum.Y_WEEK);
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
    }


}
