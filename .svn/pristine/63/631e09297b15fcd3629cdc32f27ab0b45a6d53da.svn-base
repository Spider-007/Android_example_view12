package htmitech.formConfig;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import htmitech.com.componentlibrary.RadioCheckLayout;
import htmitech.com.componentlibrary.entity.DocResultInfo;
import htmitech.com.componentlibrary.entity.EditField;
import htmitech.com.componentlibrary.entity.EditFieldList;
import htmitech.com.componentlibrary.entity.FieldItem;
import htmitech.com.componentlibrary.entity.ReportSosoResult;
import htmitech.com.componentlibrary.unit.DrawableUtils;
import htmitech.com.componentlibrary.unit.SysConvertUtil;
import htmitech.com.formlibrary.R;

/**
 * 单选 多选的RadioButton标签
 */
public class RadioButton521_22_23_31_32_33 {
    private Context context;
    private LinearLayout lineView;
    private LayoutInflater mInflater;
    private List<EditField> EditFileds;
    private List<TextView> list_tvsize;
    private DocResultInfo mDocResultInfo;
    public RadioButton521_22_23_31_32_33(Context context) {
        this.context = context;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public LinearLayout radioButtonLayout(String VlineVisible, FieldItem item, LayoutInflater mInflater, List<TextView> list_tvsize,  List<EditField> EditFileds,DocResultInfo mDocResultInfo) {
        this.mInflater = mInflater;
        this.EditFileds = EditFileds;
        this.list_tvsize = list_tvsize;
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

        return setReView521_22_23_31_32_33(item);

    }

    public LinearLayout setReView521_22_23_31_32_33(FieldItem item) {
        lineView.removeAllViews();
        List<ReportSosoResult> listform = new ArrayList<ReportSosoResult>();//放单选多选按钮内容
        final EditField edit1 = new EditField();                                        //选项布局建立tag
        edit1.setKey(item.getKey());
//        edit1.setSign(item.getSign());
        edit1.setInput(item.getInput());
        edit1.setMode(item.getMode());
        edit1.setFormKey(item.getFormKey());
//        DocResultInfo mDocResultInfo = null;
//        if (context instanceof WorkFlowFormDetalActivity) {
//            mDocResultInfo = ((WorkFlowFormDetalActivity) context).mDocResultInfo;
//        }
        LinearLayout editValuelayout = null;
        if (item.isNameRN()) //判断name 和 value 是否在同一行显示
            editValuelayout = (LinearLayout) mInflater.inflate(R.layout.layout_formdetail_cell_edittext_vertical_lib, null);
        else
            editValuelayout = (LinearLayout) mInflater.inflate(R.layout.layout_formdetail_cell_edittext_lib, null);
        // name 处理
        TextView tv = (TextView) editValuelayout.findViewById(R.id.form_fielditem_text);
        list_tvsize.add(tv);

        if (item.getAlign().equalsIgnoreCase("center")) {
            tv.setGravity(Gravity.CENTER);
        } else if (item.getAlign().equalsIgnoreCase("left")) {
            tv.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        } else if (item.getAlign().equalsIgnoreCase("right")) {
            tv.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        }

        if (item.isNameVisible()) {
            String strName = item.getName();
//            String strName = item.getBeforeNameString() + item.getName() + item.getEndNameString();
            String split = item.getSplitString();
            strName = strName + split;
            tv.setText(strName);
//            if (item.getNameColor().equalsIgnoreCase("red")) {
//                tv.setTextColor(Color.RED);
//            }
        } else{
            tv.setVisibility(View.GONE);
            editValuelayout.setVisibility(View.GONE);

        }


        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        params.gravity = Gravity.CENTER_VERTICAL;
        lineView.addView(editValuelayout, params);

        if (item.Dics != null && item.Dics.size() != 0) {
            int input = Integer.parseInt(item.getInput().trim());
            for (int i = 0; i < item.Dics.size(); i++) {
                switch (input) {
                    case 521:  //单选（结果取ID）
                    case 531://多选（结果取ID）
                        listform.add(new ReportSosoResult(item.Dics.get(i).id, item.Dics.get(i).name, ""));
                        break;
                    case 522://单选  （结果取Name）
                    case 532://多选 （结果取Name）
                        listform.add(new ReportSosoResult("", item.Dics.get(i).name, ""));
                        break;
                    case 523: //单选 （结果取Value）
                    case 533://多选 （结果取Value）
                        listform.add(new ReportSosoResult("", item.Dics.get(i).name, item.Dics.get(i).value));
                        break;

                }
            }

            switch (input) {
                case 521:  //单选（结果取ID）
                case 522://单选  （结果取Name）
                case 523: //单选 （结果取Value）
                    RadioCheckLayout radiolayout = new RadioCheckLayout(context, lineView, listform, item.getValue(), RadioCheckLayout.Type.SingleSelect);
                    final DocResultInfo finalMDocResultInfo = mDocResultInfo;
                    radiolayout.setOnClickChecked(new RadioCheckLayout.OnClickChecked() {
                        @Override
                        public void getOnChecked(String strValue) {
                            detailEdit(EditFileds, edit1, finalMDocResultInfo,strValue,false);
                        }
                    });
                    break;
                case 531://多选（结果取ID）
                case 532://多选 （结果取Name）
                case 533://多选 （结果取Value）
                    RadioCheckLayout radiolayout1 = new RadioCheckLayout(context, lineView, listform, item.getValue(), RadioCheckLayout.Type.MultiSelect);
                    final DocResultInfo finalMDocResultInfo1 = mDocResultInfo;
                    radiolayout1.setOnClickChecked(new RadioCheckLayout.OnClickChecked() {
                        @Override
                        public void getOnChecked(String strValue) {
                            detailEdit(EditFileds, edit1, finalMDocResultInfo1,strValue,true);
                        }
                    });
                    break;
            }
//            lineView.setBackgroundResource(R.drawable.corners_bg_grey_press_stroke);
            return lineView;
        } else {
//            lineView.setBackgroundResource(R.drawable.corners_bg_grey_press_stroke);
            return lineView;
        }
    }

    private void detailEdit(List<EditField> EditFileds, EditField edt1, DocResultInfo mDocResultInfo,String strValue,boolean isFlag){
//        edt1.setValue(strValue);
        if (EditFileds != null && EditFileds.size() == 0) {       //添加到可编辑数组中
            edt1.setValue(strValue);
            EditFileds.add(edt1);
        }else {
            boolean hasfind = false;
            for (int j = 0; j < EditFileds.size(); j++) {        //遍历可回发数组 是否有相同的key，如果有直接
                if (EditFileds.get(j).getKey()                    //setValue 否则直接存入
                        .equals(edt1.getKey())) {
                    hasfind = true;
                    EditFileds.get(j).setValue(
                            strValue);
                    break;
                }
            }
            if (!hasfind){
                edt1.setValue(strValue);
                EditFileds.add(edt1);
            }

        }
        mDocResultInfo.getResult()
                .setEditFields(EditFileds);                                //刷新可回发字段集
        EditFieldList mustFieldList = EditFieldList
                .getInstance();
        for (int i = 0; i < mustFieldList.getList().size(); i++) {        //遍历必填项，如果key相同设置value
            if (mustFieldList.getList().get(i).getKey().equalsIgnoreCase(edt1.getKey())) {
                mustFieldList.getList().get(i).setValue(strValue);
            }
        }

    }


}
