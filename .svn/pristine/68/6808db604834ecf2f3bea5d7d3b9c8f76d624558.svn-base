package com.htmitech.emportal.ui.formConfig;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.htmitech.emportal.HtmitechApplication;
import com.htmitech.emportal.R;
import com.htmitech.emportal.entity.DocResultInfo;
import com.htmitech.emportal.entity.EditField;
import com.htmitech.emportal.ui.detail.CheckForm;
import com.htmitech.photoselector.model.workflow.FieldItem;
import com.htmitech.emportal.ui.detail.DetailActivity;
import com.htmitech.emportal.ui.detail.DetailActivity2;
import com.htmitech.emportal.ui.detail.HtmitechCheckBox;
import com.htmitech.emportal.ui.detail.HtmitechRadioButton;
import com.htmitech.htcommonformplugin.entity.Editfields;
import com.htmitech.photoselector.model.Fielditems;
import com.htmitech.htcommonformplugin.entity.GetDetailEntity;
import com.htmitech.htcommonformplugin.weight.HtmitechCheckBoxCommonForm;
import com.htmitech.htcommonformplugin.weight.HtmitechRadioButtonCommonForm;
import com.minxing.client.util.SysConvertUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 单选 多选的RadioButton
 */
public class RadioButton501_02_03_11_513 {
    private Context context;

    public RadioButton501_02_03_11_513(Context context) {
        this.context = context;
    }

    public LinearLayout radioButtonLayout(String VlineVisible, FieldItem item, LayoutInflater mInflater, List<EditField> EditFileds) {
        LinearLayout lineView = new LinearLayout(
                HtmitechApplication.instance());
        lineView.setOrientation(LinearLayout.VERTICAL);
        lineView.setGravity(Gravity.CENTER_VERTICAL);
        lineView.setBackgroundColor(SysConvertUtil.formattingH(item.getBackColor()));
        if (VlineVisible.equalsIgnoreCase("true"))
            lineView.setBackgroundResource(R.drawable.corners_bg_white_press_stroke);
        List<CheckForm> listform = new ArrayList<CheckForm>();//放单选多选按钮内容

        EditField edit1 = new EditField();                                        //选项布局建立tag
        edit1.setKey(item.getKey());
        edit1.setSign(item.getSign());
        edit1.setInput(item.getInput());
        edit1.setMode(item.getMode());
        edit1.setFormKey(item.getFormKey());
        DocResultInfo mDocResultInfo = null;
        if (DetailActivity2.currentActivity) {
            mDocResultInfo = ((DetailActivity2) context).mDocResultInfo;
        } else {
            mDocResultInfo = ((DetailActivity) context).mDocResultInfo;
        }

        if (item.Dics != null && item.Dics.size() != 0) {
            int input = Integer.parseInt(item.getInput().trim());
            for (int i = 0; i < item.Dics.size(); i++) {



                switch (input){
                    case 501:  //单选（结果取ID）
                    case 511://多选（结果取ID）
                        listform.add(new CheckForm(item.Dics.get(i).id, item.Dics.get(i).name, ""));
                        break;
                    case 502://单选  （结果取Name）
                    case 512://多选 （结果取Name）
                        listform.add(new CheckForm("", item.Dics.get(i).name, ""));
                        break;
                    case 503: //单选 （结果取Value）
                    case 513://多选 （结果取Value）
                        listform.add(new CheckForm("", item.Dics.get(i).name, item.Dics.get(i).value));
                        break;

                }
//                if (Integer.parseInt(item.getInput().trim()) == 501 || Integer.parseInt(item.getInput().trim()) == 511) {
////                            listform.add(item.Dics.get(i).id);
//                    listform.add(new CheckForm(item.Dics.get(i).id, item.Dics.get(i).name, ""));
//                } else if (Integer.parseInt(item.getInput().trim()) == 502 || Integer.parseInt(item.getInput().trim()) == 512) {
////                            listform.add(item.Dics.get(i).name);
//                    listform.add(new CheckForm("", item.Dics.get(i).name, ""));
//                } else if (Integer.parseInt(item.getInput().trim()) == 503 || Integer.parseInt(item.getInput().trim()) == 513) {
////                            listform.add(item.Dics.get(i).value);
//                    listform.add(new CheckForm("", item.Dics.get(i).name, item.Dics.get(i).value));
//                }

            }

            switch (input){
                case 501:  //单选（结果取ID）
                case 502://单选  （结果取Name）
                case 503: //单选 （结果取Value）
                    HtmitechRadioButton radiobutton = new HtmitechRadioButton(HtmitechApplication.instance());
                    radiobutton.setView(lineView);
                    radiobutton.setTextList(listform, item.getValue());
                    radiobutton.setEdit(EditFileds, edit1, mDocResultInfo);
                    lineView.setBackgroundResource(R.drawable.corners_bg_grey_press_stroke);
                    return lineView;
                case 511://多选（结果取ID）
                case 512://多选 （结果取Name）
                case 513://多选 （结果取Value）
                    String[] checkValue = null;
                    if(item.getValue() != null && !item.getValue().equals("")){
                        checkValue = item.getValue().split(";");
                    }
                    HtmitechCheckBox hcb = new HtmitechCheckBox(HtmitechApplication.instance());
                    hcb.setView(lineView);
                    hcb.setList(listform, checkValue);
                    hcb.setEdit(EditFileds, edit1, mDocResultInfo);
                    lineView.setBackgroundResource(R.drawable.corners_bg_grey_press_stroke);
                    return lineView;

            }
//            if (Integer.parseInt(item.getInput().trim()) == 501 || Integer.parseInt(item.getInput().trim()) == 502 || Integer.parseInt(item.getInput().trim()) == 503) {
//
//            } else if (Integer.parseInt(item.getInput().trim()) == 511 || Integer.parseInt(item.getInput().trim()) == 512 || Integer.parseInt(item.getInput().trim()) == 513) {
//
//            }
        } else {
            lineView.setBackgroundResource(R.drawable.corners_bg_grey_press_stroke);
            return lineView;
        }
        lineView.setBackgroundResource(R.drawable.corners_bg_grey_press_stroke);
        return lineView;
    }

    public LinearLayout radioButtonLayoutCommonForm(boolean VlineVisible, Fielditems item, LayoutInflater mInflater, List<Editfields> EditFileds) {
        LinearLayout lineView = new LinearLayout(
                HtmitechApplication.instance());
        lineView.setOrientation(LinearLayout.VERTICAL);
        lineView.setGravity(Gravity.CENTER_VERTICAL);
        lineView.setBackgroundColor(SysConvertUtil.formattingH(item.getFiled_backcolor()));
        if (VlineVisible)
            lineView.setBackgroundResource(R.drawable.corners_bg_white_press_stroke);
        List<CheckForm> listform = new ArrayList<CheckForm>();//放单选多选按钮内容

        Editfields edit1 = new Editfields();                                        //选项布局建立tag
        edit1.setKey(item.getKey());
        GetDetailEntity mGetDetailEntity = null;
        if (DetailActivity2.currentActivity) {
//            mGetDetailEntity = ((DetailActivity2) context).mDocResultInfo;
        } else {
//            mGetDetailEntity = ((GeneralFormDetalActivity) context).mGetDetailEntity;
        }

        if (item.getDics() != null && item.getDics().size() != 0) {
            int input = Integer.parseInt(item.getInput_type().trim());
            for (int i = 0; i < item.getDics().size(); i++) {



                switch (input){
                    case 501:  //单选（结果取ID）
                    case 511://多选（结果取ID）
                        listform.add(new CheckForm(item.getDics().get(i).id, item.getDics().get(i).name, ""));
                        break;
                    case 502://单选  （结果取Name）
                    case 512://多选 （结果取Name）
                        listform.add(new CheckForm("", item.getDics().get(i).name, ""));
                        break;
                    case 503: //单选 （结果取Value）
                    case 513://多选 （结果取Value）
                        listform.add(new CheckForm("", item.getDics().get(i).name, item.getDics().get(i).value));
                        break;

                }
//                if (Integer.parseInt(item.getInput().trim()) == 501 || Integer.parseInt(item.getInput().trim()) == 511) {
////                            listform.add(item.Dics.get(i).id);
//                    listform.add(new CheckForm(item.Dics.get(i).id, item.Dics.get(i).name, ""));
//                } else if (Integer.parseInt(item.getInput().trim()) == 502 || Integer.parseInt(item.getInput().trim()) == 512) {
////                            listform.add(item.Dics.get(i).name);
//                    listform.add(new CheckForm("", item.Dics.get(i).name, ""));
//                } else if (Integer.parseInt(item.getInput().trim()) == 503 || Integer.parseInt(item.getInput().trim()) == 513) {
////                            listform.add(item.Dics.get(i).value);
//                    listform.add(new CheckForm("", item.Dics.get(i).name, item.Dics.get(i).value));
//                }

            }

            switch (input){
                case 501:  //单选（结果取ID）
                case 502://单选  （结果取Name）
                case 503: //单选 （结果取Value）
                    HtmitechRadioButtonCommonForm radiobutton = new HtmitechRadioButtonCommonForm(HtmitechApplication.instance());
                    radiobutton.setView(lineView);
                    radiobutton.setTextList(listform, item.getValue());
                    radiobutton.setEdit(EditFileds, edit1, mGetDetailEntity);
                    lineView.setBackgroundResource(R.drawable.corners_bg_grey_press_stroke);
                    return lineView;
                case 511://多选（结果取ID）
                case 512://多选 （结果取Name）
                case 513://多选 （结果取Value）
                    String[] checkValue = null;
                    if(item.getValue() != null && !item.getValue().equals("")){
                        checkValue = item.getValue().split(";");
                    }
                    HtmitechCheckBoxCommonForm hcb = new HtmitechCheckBoxCommonForm(HtmitechApplication.instance());
                    hcb.setView(lineView);
                    hcb.setList(listform, checkValue);
                    hcb.setEdit(EditFileds, edit1, mGetDetailEntity);
                    lineView.setBackgroundResource(R.drawable.corners_bg_grey_press_stroke);
                    return lineView;

            }
//            if (Integer.parseInt(item.getInput().trim()) == 501 || Integer.parseInt(item.getInput().trim()) == 502 || Integer.parseInt(item.getInput().trim()) == 503) {
//
//            } else if (Integer.parseInt(item.getInput().trim()) == 511 || Integer.parseInt(item.getInput().trim()) == 512 || Integer.parseInt(item.getInput().trim()) == 513) {
//
//            }
        } else {
            lineView.setBackgroundResource(R.drawable.corners_bg_grey_press_stroke);
            return lineView;
        }
        lineView.setBackgroundResource(R.drawable.corners_bg_grey_press_stroke);
        return lineView;
    }

}
