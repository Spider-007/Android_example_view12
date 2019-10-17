package htmitech.formConfig;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import htmitech.com.componentlibrary.HtmitechCheckBox;
import htmitech.com.componentlibrary.entity.CheckForm;
import htmitech.com.componentlibrary.entity.DocResultInfo;
import htmitech.com.componentlibrary.entity.EditField;
import htmitech.com.componentlibrary.entity.FieldItem;
import htmitech.com.componentlibrary.unit.DensityUtil;
import htmitech.com.componentlibrary.unit.DrawableUtils;
import htmitech.com.componentlibrary.unit.SysConvertUtil;
import htmitech.com.formlibrary.R;

/**
 * Created by htrf-pc on 2016/9/21.
 */
public class Zidian514 {
    private Context context;
    private LinearLayout lineView;
    private List<EditField> EditFileds;
    private DocResultInfo mDocResultInfo;
    public Zidian514(Context context) {
        this.context = context;
    }

    public LinearLayout radioButtonLayout(String VlineVisible, FieldItem item, LayoutInflater mInflater, List<EditField> EditFileds,DocResultInfo mDocResultInfo) {
        this.EditFileds = EditFileds;
        this.mDocResultInfo = mDocResultInfo;
        lineView = new LinearLayout(
                context);
        lineView.setOrientation(LinearLayout.VERTICAL);
        lineView.setGravity(Gravity.CENTER_VERTICAL);
        lineView.setBackgroundColor(SysConvertUtil.formattingH(item.getBackColor()));
        if (VlineVisible.equalsIgnoreCase("true")) {
//            lineView.setBackgroundResource(R.drawable.corners_bg_white_press_stroke_v);
            lineView.setBackground(DrawableUtils.getLayerDrawable(context, item.getBackColor()));//设置显示背景
        }

        return setReView514(item);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public LinearLayout setReView514(FieldItem item){
        lineView.removeAllViews();
        List<CheckForm> listform = new ArrayList<CheckForm>();//放单选多选按钮内容

        EditField edit1 = new EditField();                                        //选项布局建立tag
        edit1.setKey(item.getKey());
//        edit1.setSign(item.getSign());
        edit1.setInput(item.getInput());
        edit1.setMode(item.getMode());
        edit1.setIsExt(item.isExt());
        edit1.setFormKey(item.getFormKey());
//        DocResultInfo mDocResultInfo = null;
//        mDocResultInfo = ((WorkFlowFormDetalActivity) context).mDocResultInfo;


        if (item.Dics != null && item.Dics.size() != 0) {
            for (int i = 0; i < item.Dics.size(); i++) {
                listform.add(new CheckForm("", item.Dics.get(i).name, ""));
            }
            String[] checkValue = null;
            if (item.getValue() != null && !item.getValue().equals("")) {
                checkValue = item.getValue().split("|");
            }
            String tempCheck = "";
            if (checkValue != null) {
                for (String check : checkValue) {
                    boolean flag = false;
                    for (int i = 0; i < item.Dics.size(); i++) {
                        if (item.Dics.get(i).name.equals(check)) {
                            flag = true;
                            break;
                        }
                    }
                    if (!flag) {
                        tempCheck += check + "|";
                    }
                }
            }
            if (!tempCheck.equals("") && tempCheck.length() > 0)
                tempCheck = tempCheck.substring(0, tempCheck.length() - 1);


            HtmitechCheckBox hcb = new HtmitechCheckBox(context);
            hcb.setView(lineView);
            hcb.setList(listform, checkValue);
            hcb.setEdit(EditFileds, edit1, mDocResultInfo);

            TextView myTextView = new TextView(context);
            myTextView.setText(tempCheck);
            myTextView.setTextIsSelectable(true);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            params.setMargins(5, 0, 5, 0);
            myTextView.setLayoutParams(params);
            myTextView.setPadding(0, 5, 0, 5);
            myTextView.setGravity(Gravity.CENTER);
            myTextView.setTextSize(DensityUtil.sp2px(context, 17));
            myTextView.setBackgroundResource(R.drawable.text_back);
//            if(!tempCheck .equals(""))
            lineView.addView(myTextView);
            return lineView;
        } else {
            return lineView;
        }
    }
}
