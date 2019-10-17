package htmitech.formConfig;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import htmitech.com.componentlibrary.GeneralDialogJava;
import htmitech.com.componentlibrary.HtmitechCheckBox;
import htmitech.com.componentlibrary.HtmitechRadioButton;
import htmitech.com.componentlibrary.SingleChoicePopupWindow;
import htmitech.com.componentlibrary.api.ComponentInit;
import htmitech.com.componentlibrary.entity.CheckForm;
import htmitech.com.componentlibrary.entity.DocResultInfo;
import htmitech.com.componentlibrary.entity.EditField;
import htmitech.com.componentlibrary.entity.EditFieldList;
import htmitech.com.componentlibrary.entity.FieldItem;
import htmitech.com.componentlibrary.unit.DrawableUtils;
import htmitech.com.componentlibrary.unit.SysConvertUtil;
import htmitech.com.formlibrary.R;

/**
 * 单选 多选的RadioButton
 */
public class RadioButton501_02_03_11_513 {
    private Context context;
    private LinearLayout lineView;
    private LayoutInflater mInflater;
    private List<EditField> EditFileds;
    List<CheckForm> listform;
    private SingleChoicePopupWindow mSingleChoicePopupWindow;
    private List<TextView> list_tvsize;
    private TextView currentEditTextViews;
    private Map<String,String> mapList =new HashMap<String, String>();
    private DocResultInfo mDocResultInfo;

    public RadioButton501_02_03_11_513(Context context) {
        this.context = context;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public LinearLayout radioButtonLayout(String VlineVisible, FieldItem item, LayoutInflater mInflater, List<TextView> list_tvsize, List<EditField> EditFileds, int TabStyle, DocResultInfo mDocResultInfo) {
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

        if (TabStyle == 1) {
            return setReView501_02_03_11_513_net(item);
        } else {
            return setReView501_02_03_11_513(item);
        }
    }

    public LinearLayout setReView501_02_03_11_513(FieldItem item){
        lineView.removeAllViews();
        listform = new ArrayList<CheckForm>();//放单选多选按钮内容

        EditField edit1 = new EditField();                                        //选项布局建立tag
        edit1.setKey(item.getKey());
//        edit1.setSign(item.getSign());
        edit1.setInput(item.getInput());
        edit1.setMode(item.getMode());
        edit1.setIsExt(item.isExt());
        edit1.setFormKey(item.getFormKey());
//        DocResultInfo mDocResultInfo = null;
//
//        mDocResultInfo = ((WorkFlowFormDetalActivity) context).mDocResultInfo;


        if (item.Dics != null && item.Dics.size() != 0) {
            int input = Integer.parseInt(item.getInput().trim());
            for (int i = 0; i < item.Dics.size(); i++) {


                switch (input) {
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

            switch (input) {
                case 501:  //单选（结果取ID）
                case 502://单选  （结果取Name）
                case 503: //单选 （结果取Value）
                    HtmitechRadioButton radiobutton = new HtmitechRadioButton(context);
                    radiobutton.setView(lineView);
                    radiobutton.setTextList(listform, item.getValue());
                    radiobutton.setEdit(EditFileds, edit1, mDocResultInfo);
                    lineView.setBackgroundResource(R.drawable.corners_bg_grey_press_stroke);
                    return lineView;
                case 511://多选（结果取ID）
                case 512://多选 （结果取Name）
                case 513://多选 （结果取Value）
                    String[] checkValue = null;
                    if (item.getValue() != null && !item.getValue().equals("")) {
                        checkValue = item.getValue().split(";");
                    }
                    HtmitechCheckBox hcb = new HtmitechCheckBox(context);
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

    public LinearLayout setReView501_02_03_11_513_net(FieldItem item){
        lineView.removeAllViews();
        listform = new ArrayList<CheckForm>();//放单选多选按钮内容
        LinearLayout editValuelayout = null;
        if (item.isNameRN())
            editValuelayout = (LinearLayout) mInflater.inflate(
                    R.layout.layout_formdetail_cell_textview_vertical_net_lib,
                    null);
        else
            editValuelayout = (LinearLayout) mInflater.inflate(
                    R.layout.layout_formdetail_cell_textview_net_lib, null);
        // name 处理
        TextView tv = (TextView) editValuelayout
                .findViewById(R.id.form_fielditem_text);
        /**
         * add by heyang
         * date 2016-7-18
         * if isMustInput is true then change backgroundclolor as "color_mustinputback"
         */
        list_tvsize.add(tv);
        tv.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        tv.setTextColor(Color.parseColor("#999999"));
        item.setIsSplitWithLine(0);
        if (item.isNameVisible()) {
            String strName = item.getBeforeValueString()
                    + item.getName() + item.getEndValueString();
            String split = item.getSplitString();
            if (item.isSplitWithLine()==1)
                strName = strName + split;
            tv.setText(strName);
//            if (item.getNameFontColor().equalsIgnoreCase("red")) {
//                tv.setTextColor(Color.RED);
//            }
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
//
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
        final int input = Integer.parseInt(item.getInput().trim());
        tvEditValue.setOnClickListener(new OnClickItem(input, tvEditValue));

        tvEditValue.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                // TODO Auto-generated method stub

                // 将值保存到编辑的字段中，并判断是否需要更新必填字段
                // 1,将已经编辑的字段和值放入到办理是需要回发的EditField[] 中
                String strValue = currentEditTextViews.getText()
                        .toString();
                EditField edit = (EditField) currentEditTextViews
                        .getTag();
                if(strValue.contains(";")){
                    String[]strArray = strValue.split(";");
                    StringBuffer sb = new StringBuffer();
                    for(int i=0;i<strArray.length;i++){
                        sb.append(mapList.get(strArray[i]));
                        if(i<strArray.length-1)
                            sb.append(";");
                    }
                    edit.setValue(sb.toString());
                }else {
                    edit.setValue(mapList.get(strValue));
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

        ImageView img = (ImageView) editValuelayout
                .findViewById(R.id.form_fielditem_editimage);
        img.setImageResource(R.drawable.header_arrow_right);
        img.setVisibility(View.VISIBLE);
        img.setOnClickListener(new OnClickItem(input,tvEditValue));
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
            if (input == 501 || input == 502 || input == 503) {
                if (mSingleChoicePopupWindow != null) {
                    mSingleChoicePopupWindow.dismiss();
                }
                mSingleChoicePopupWindow = new SingleChoicePopupWindow(context);
                if (listform.size() > 0) {
                    String[] singles = new String[listform.size()];
                    for (int i = 0; i < listform.size(); i++) {
                        singles[i] = listform.get(i).name;
                        mapList.put(listform.get(i).name,listform.get(i).getValue());

                    }
                    mSingleChoicePopupWindow.setCheckForm(singles);
                }
                mSingleChoicePopupWindow.show(v);
                mSingleChoicePopupWindow.setOnClickOkListener(new SingleChoicePopupWindow.OnClickOkListener() {
                    @Override
                    public void onClickOk(String birthday) {
                        // TODO Auto-generated method stub
                        if (birthday != null && !birthday.equals("")){

                            currentEditTextViews.setText(birthday);
                        }


                    }
                });

            } else if (input == 511 || input == 512 || input == 513) {
                new GeneralDialogJava(context, listform, new GeneralDialogJava.IGeneralDialogItem() {
                    @Override
                    public void ItemClick(ArrayList<CheckForm> checkForms) {
                        StringBuffer sb = new StringBuffer();
                        for (int i=0;i<checkForms.size();i++) {
                            if (checkForms.get(i) == null || checkForms.get(i).name.equals("")) {
                                sb.append("");
                            } else {
                                sb.append(checkForms.get(i)
                                        .name);
                                mapList.put(checkForms.get(i).name,checkForms.get(i).getValue());
                                if(i<checkForms.size()-1)
                                    sb.append(";");
                            }
                        }
                        currentEditTextViews.setText(sb);
                    }
                }).builder().show();
            }
        }
    }

    /**
     * 设置显示选项
     *
     * @return
     */
    public String setText(String singleChoice) {
        StringBuffer sb = new StringBuffer();
        if (singleChoice == null) {
            return "";
        } else {
            sb.append(singleChoice);
        }

        return sb.toString();
    }

}
