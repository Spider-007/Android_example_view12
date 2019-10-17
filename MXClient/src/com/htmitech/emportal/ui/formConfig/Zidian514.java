package com.htmitech.emportal.ui.formConfig;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.htmitech.emportal.HtmitechApplication;
import com.htmitech.emportal.R;
import com.htmitech.emportal.entity.DocResultInfo;
import com.htmitech.emportal.entity.EditField;
import com.htmitech.photoselector.model.workflow.FieldItem;
import com.htmitech.emportal.ui.detail.CheckForm;
import com.htmitech.emportal.ui.detail.DetailActivity;
import com.htmitech.emportal.ui.detail.DetailActivity2;
import com.htmitech.emportal.ui.detail.HtmitechCheckBox;
import com.htmitech.htcommonformplugin.entity.Editfields;
import com.htmitech.photoselector.model.Fielditems;
import com.htmitech.htcommonformplugin.entity.GetDetailEntity;
import com.htmitech.htcommonformplugin.weight.HtmitechCheckBoxCommonForm;
import com.htmitech.unit.DensityUtil;
import com.minxing.client.util.SysConvertUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by htrf-pc on 2016/9/21.
 */
public class Zidian514 {
    private Context context;

    public Zidian514(Context context) {
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
            for (int i = 0; i < item.Dics.size(); i++) {
                listform.add(new CheckForm("", item.Dics.get(i).name, ""));
            }
            String[] checkValue = null;
            if(item.getValue() != null && !item.getValue().equals("")){
                checkValue = item.getValue().split(";");
            }
            String tempCheck = "";
            if(checkValue != null) {
                for (String check : checkValue) {
                    boolean flag = false;
                    for (int i = 0; i < item.Dics.size(); i++) {
                        if (item.Dics.get(i).name.equals(check)) {
                            flag = true;
                            break;
                        }
                    }
                    if (!flag) {
                        tempCheck += check + ";";
                    }
                }
            }
            if(!tempCheck .equals("") && tempCheck.length() > 0)
              tempCheck = tempCheck.substring(0,tempCheck.length() - 1);


            HtmitechCheckBox hcb = new HtmitechCheckBox(HtmitechApplication.instance());
            hcb.setView(lineView);
            hcb.setList(listform, checkValue);
            hcb.setEdit(EditFileds, edit1, mDocResultInfo);

            TextView myTextView = new TextView(context);
            myTextView.setText(tempCheck);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            params.setMargins(5, 0, 5, 0);
            myTextView.setLayoutParams(params);
            myTextView.setPadding(0, 5, 0, 5);
            myTextView.setGravity(Gravity.CENTER);
            myTextView.setTextSize(DensityUtil.dip2px(context, 10));
            myTextView.setBackgroundResource(R.drawable.text_back);
//            if(!tempCheck .equals(""))
            lineView.addView(myTextView);
            return lineView;
        } else {
            return lineView;
        }
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
        GetDetailEntity mGetDetailEntity= null;
        if (DetailActivity2.currentActivity) {
//            mGetDetailEntity = ((DetailActivity2) context).mDocResultInfo;
        } else {
//            mGetDetailEntity = ((GeneralFormDetalActivity) context).mGetDetailEntity;
        }

        if (item.getDics() != null && item.getDics().size() != 0) {
            for (int i = 0; i < item.getDics().size(); i++) {
                listform.add(new CheckForm("", item.getDics().get(i).name, ""));
            }
            String[] checkValue = null;
            if(item.getValue() != null && !item.getValue().equals("")){
                checkValue = item.getValue().split(";");
            }
            String tempCheck = "";
            if(checkValue != null) {
                for (String check : checkValue) {
                    boolean flag = false;
                    for (int i = 0; i < item.getDics().size(); i++) {
                        if (item.getDics().get(i).name.equals(check)) {
                            flag = true;
                            break;
                        }
                    }
                    if (!flag) {
                        tempCheck += check + ";";
                    }
                }
            }
            if(!tempCheck .equals("") && tempCheck.length() > 0)
                tempCheck = tempCheck.substring(0,tempCheck.length() - 1);


            HtmitechCheckBoxCommonForm hcb = new HtmitechCheckBoxCommonForm(HtmitechApplication.instance());
            hcb.setView(lineView);
            hcb.setList(listform, checkValue);
            hcb.setEdit(EditFileds, edit1, mGetDetailEntity);

            TextView myTextView = new TextView(context);
            myTextView.setText(tempCheck);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            params.setMargins(5, 0, 5, 0);
            myTextView.setLayoutParams(params);
            myTextView.setPadding(0, 5, 0, 5);
            myTextView.setGravity(Gravity.CENTER);
            myTextView.setTextSize(DensityUtil.dip2px(context, 10));
            myTextView.setBackgroundResource(R.drawable.text_back);
//            if(!tempCheck .equals(""))
            lineView.addView(myTextView);
            return lineView;
        } else {
            return lineView;
        }
    }
}
