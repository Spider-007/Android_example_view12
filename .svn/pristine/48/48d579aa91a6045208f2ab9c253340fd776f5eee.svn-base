package com.htmitech.emportal.ui.formConfig;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.htmitech.combobox.ComboBox;
import com.htmitech.emportal.HtmitechApplication;
import com.htmitech.emportal.R;
import com.htmitech.emportal.entity.DocResultInfo;
import com.htmitech.emportal.entity.EditField;
import com.htmitech.emportal.entity.EditFieldList;
import com.htmitech.photoselector.model.workflow.FieldItem;
import com.htmitech.emportal.ui.detail.DetailActivity;
import com.htmitech.emportal.ui.detail.DetailActivity2;
import com.htmitech.emportal.ui.detail.ScrollViewListener;
import com.htmitech.htcommonformplugin.entity.Editfields;
import com.htmitech.photoselector.model.Fielditems;
import com.htmitech.htcommonformplugin.entity.GetDetailEntity;
import com.minxing.client.util.SysConvertUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 下拉框
 */
public class SpringLayout412_403_402_401 {
    private Context context;
    private String[] options;
    private  String[] values;
    private String[] ids;
    public void setOptions(String[]... options){
        this.options = options[0];
        this.values = options[1];
        this.ids = options[2];
    }

    public SpringLayout412_403_402_401(Context context){
        this.context = context;
    }

    public LinearLayout springLayout(String VlineVisible,final FieldItem item,LayoutInflater mInflater,List<TextView> list_tvsize,ArrayList<ComboBox> mComboBoxList,List<ComboBox> list_comboboxsize,List<EditField> EditFileds,ScrollViewListener mScrollViewListener){

        // 2, *********************选择项********************************
        LinearLayout lineView = new LinearLayout(                                    //创建先拉列表线性布局
                HtmitechApplication.instance());
        lineView.setOrientation(LinearLayout.VERTICAL);
        lineView.setGravity(Gravity.CENTER_VERTICAL);
        lineView.setBackgroundColor(SysConvertUtil.formattingH(item.getBackColor()));
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
            tv.setBackgroundColor(context.getResources().getColor(R.color.form_bg_must));
            lineView.setBackgroundResource(R.drawable.form_input_bg_must);
        }
        if (item.getAlign().equalsIgnoreCase("center")) {                    //判断名称显示位置
            tv.setGravity(Gravity.CENTER);
        } else if (item.getAlign().equalsIgnoreCase("left")) {
            tv.setGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL);
        } else if (item.getAlign().equalsIgnoreCase("right")) {
            tv.setGravity(Gravity.RIGHT|Gravity.CENTER_VERTICAL);
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
        final ComboBox comboxValue = (ComboBox) editValuelayout
                .findViewById(R.id.form_fielditem_editvalue);
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
            lineView.setBackgroundResource(R.drawable.form_input_bg_must);
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
                else{
                    comboxValue.setText(item.getValue());
                }
                Log.d("item.getInput()", Integer.parseInt(item.getInput().trim()) + "======= ");
            }


            comboxValue.setListViewOnClickListener(new ComboBoxClickListener(item,comboxValue,EditFileds));

//        }

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        params.gravity = Gravity.CENTER_VERTICAL;
        lineView.addView(editValuelayout, params);


        return lineView;

    }

    public LinearLayout springLayoutCommonForm(boolean VlineVisible, final Fielditems item, LayoutInflater mInflater, List<TextView> list_tvsize, ArrayList<ComboBox> mComboBoxList, List<ComboBox> list_comboboxsize, List<Editfields> mEditFileds, ScrollViewListener mScrollViewListener){

        // 2, *********************选择项********************************
        LinearLayout lineView = new LinearLayout(                                    //创建先拉列表线性布局
                HtmitechApplication.instance());
        lineView.setOrientation(LinearLayout.VERTICAL);
        lineView.setGravity(Gravity.CENTER_VERTICAL);
        lineView.setBackgroundColor(SysConvertUtil.formattingH(item.getFiled_backcolor()));
        if (VlineVisible)
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
        if (item.isMustinput()) {
            tv.setBackgroundColor(context.getResources().getColor(R.color.color_mustinputtouming));
            lineView.setBackgroundResource(R.drawable.corners_bg_mustinput);
        }
        if (item.getAlign().equalsIgnoreCase("center")) {                    //判断名称显示位置
            tv.setGravity(Gravity.CENTER|Gravity.CENTER_VERTICAL);
        } else if (item.getAlign().equalsIgnoreCase("left")) {
            tv.setGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL);
        } else if (item.getAlign().equalsIgnoreCase("right")) {
            tv.setGravity(Gravity.RIGHT|Gravity.CENTER_VERTICAL);
        }

        if (item.isName_visible()) {                                            //如果名称显示
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
        // 初始化下拉框控件
        final ComboBox comboxValue = (ComboBox) editValuelayout
                .findViewById(R.id.form_fielditem_editvalue);
        comboxValue.setScrollViewListener(mScrollViewListener);
        comboxValue.setGravity(Gravity.CENTER_VERTICAL);
        mComboBoxList.add(comboxValue);
        list_comboboxsize.add(comboxValue);
        /**
         * add by heyang
         * date 2016-7-18
         * if isMustInput is true then change backgroundclolor as "color_mustinputback"
         */
        if (item.isMustinput()) {
            lineView.setBackgroundResource(R.drawable.corners_bg_mustinput);
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
        if (item.getValue_color().equalsIgnoreCase("red")) {
            comboxValue.setTextColor("#000000"); //设置自定义控件字体颜色
        }
        EditField edit = new EditField();                        //设置可编辑item
        edit.setKey(item.getKey());
        edit.setSign(item.getValue());
        edit.setInput(item.getInput_type());
        edit.setMode(item.getMode());
        edit.setFormKey(item.getFormkey());
        comboxValue.setTag(edit);

        int currentSelectedIndex = -1;                                        //下拉列表的选择项
        if (item.getDics() != null) {
            options = new String[item.getDics().size()];
            values = new String[item.getDics().size()];
            ids = new String[item.getDics().size()];
            for (int i = 0; i < item.getDics().size(); i++) {
                options[i] = item.getDics().get(i).name;
                values[i] = item.getDics().get(i).value;
                ids[i] = item.getDics().get(i).id;

                if (item.getValue().equalsIgnoreCase(values[i])
                        || item.getValue().equalsIgnoreCase(
                        options[i]) || item.getValue().equalsIgnoreCase(ids[i])) {
                    currentSelectedIndex = i;
                }
            }
            if (Integer.parseInt(item.getInput_type().trim()) == 412) {
                comboxValue.setData(options);
                comboxValue.isEnable(true);
            } else {
                comboxValue.isEnable(false);
                comboxValue.setData(options);
            }

            if (currentSelectedIndex != -1)
                comboxValue.setText(options[currentSelectedIndex]);
            else{
                comboxValue.setText(item.getValue());
            }
            Log.d("item.getInput()", Integer.parseInt(item.getInput_type().trim()) + "======= ");
        }


        comboxValue.setListViewOnClickListener(new ComboBoxClickListenerCommonForm(item,comboxValue,mEditFileds));

//        }

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        params.gravity = Gravity.CENTER_VERTICAL;
        lineView.addView(editValuelayout, params);


        return lineView;

    }

    public class ComboBoxClickListener implements ComboBox.ListViewItemClickListener{
        private FieldItem item;
        private ComboBox comboxValue;
        private List<EditField> EditFileds;
        public ComboBoxClickListener(FieldItem item,ComboBox comboxValue,List<EditField> EditFileds){
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
            // 2015-8-17
            DocResultInfo mDocResultInfo = null;
            if (DetailActivity2.currentActivity) {
                mDocResultInfo = ((DetailActivity2)context).mDocResultInfo;
            } else {
                mDocResultInfo = ((DetailActivity) context).mDocResultInfo;
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
    }

    public class ComboBoxClickListenerCommonForm implements ComboBox.ListViewItemClickListener{
        private Fielditems item;
        private ComboBox comboxValue;
        private List<Editfields> EditFileds;
        public ComboBoxClickListenerCommonForm(Fielditems item, ComboBox comboxValue, List<Editfields> EditFileds){
            this.item = item;
            this.comboxValue = comboxValue;
            this.EditFileds = EditFileds;
        }
        @Override
        public void onItemClick(int position) {
            String strValue = comboxValue.getText();

            if (Integer.parseInt(item.getInput_type().trim()) == 401 || Integer.parseInt(item.getInput_type().trim()) == 403) {

                for (int i = 0; i < options.length; i++) {
                    if (strValue.equals(options[i])) {
                        if (Integer.parseInt(item.getInput_type().trim()) == 401)
                            strValue = ids[i];
                        if (Integer.parseInt(item.getInput_type().trim()) == 403)
                            strValue = values[i];
                        break;
                    }

                }
            }

            Editfields edit = (Editfields) comboxValue.getTag();


            edit.setValue(strValue);

            if (EditFileds != null && EditFileds.size() == 0) {
                EditFileds.add(edit);
                // 将有可能出现的key_ID,key_VALUE 隐藏字段保存到编辑框中
                Editfields edit_Value = new Editfields();
                edit_Value.setKey(edit.getKey() + "_VALUE");
                edit_Value.setValue(strValue);//
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
                    Editfields edit_Value = new Editfields();
                    edit_Value.setKey(edit.getKey() + "_VALUE");
                    edit_Value.setValue(strValue);
                    EditFileds.add(edit_Value);

                }
            }
            Log.d("FormFragment", "EditFileds:" + EditFileds);
            // 2015-8-17
            GetDetailEntity mGetDetailEntity = null;
            if (DetailActivity2.currentActivity) {
//                mGetDetailEntity = ((DetailActivity2)context).mDocResultInfo;
            } else {
//                mGetDetailEntity = ((GeneralFormDetalActivity) context).mGetDetailEntity;
            }
            // DocResultInfo mDocResultInfo = ((DetailActivity)
            // getActivity()).mDocResultInfo;
            mGetDetailEntity.getResult()
                    .setEditfields(EditFileds);
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


}
