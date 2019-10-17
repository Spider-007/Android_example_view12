package htmitech.formConfig;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import htmitech.com.componentlibrary.SuperTextView;
import htmitech.com.componentlibrary.api.ComponentInit;
import htmitech.com.componentlibrary.entity.DocResultInfo;
import htmitech.com.componentlibrary.entity.EditField;
import htmitech.com.componentlibrary.entity.EditFieldList;
import htmitech.com.componentlibrary.entity.FieldItem;
import htmitech.com.componentlibrary.unit.DensityUtil;
import htmitech.com.componentlibrary.unit.SysConvertUtil;
import htmitech.com.formlibrary.R;
import htmitech.util.AccssFormKey;

/**
 * 新增 不可编辑layout.
 */
public class NotEditLayout {
    private FieldItem item;
    private LayoutInflater mInflater;
    private String VlineVisible;
    public int TabStyle;
    private String value;
    private List<TextView> list_tvsize;
    private Context context;
    private LinearLayout lineView;
    private List<EditField> EditFileds;
    private DocResultInfo mDocResultInfo;
    private int isWaterSecurity;

    public LinearLayout getLineView() {
        return lineView;
    }

    public void setLineView(LinearLayout lineView) {
        this.lineView = lineView;
    }

    public NotEditLayout(Context context) {
        this.context = context;
    }

    public LinearLayout setNotEditLayout(FieldItem item, LayoutInflater mInflater, String VlineVisible, int TabStyle, String value, List<EditField> EditFileds, List<TextView> list_tvsize, DocResultInfo mDocResultInfo, int isWaterSecurity) {
        this.item = item;
        this.mInflater = mInflater;
        this.VlineVisible = VlineVisible;
        this.TabStyle = TabStyle;
        this.value = value;
        this.EditFileds = EditFileds;
        this.list_tvsize = list_tvsize;
        this.mDocResultInfo = mDocResultInfo;
        this.isWaterSecurity = isWaterSecurity;
        lineView = new LinearLayout(
                context);
        lineView.setOrientation(LinearLayout.VERTICAL);
        lineView.setGravity(Gravity.CENTER_VERTICAL);
        lineView.setBackgroundColor(SysConvertUtil.formattingH(item.getBackColor()));
        if (VlineVisible.equalsIgnoreCase("true")) {
//            layout.setBackgroundResource(R.drawable.corners_bg_white_press_stroke);
        } else {
            lineView.setBackgroundResource(R.drawable.corners_bg_grey_press_stroke);
        }

        return setReView(item);
    }

    public LinearLayout setReView(FieldItem item) {
        // //////////////其他只读字段的显示
        value = item.getBeforeValueString() + AccssFormKey.findKeyToByValue(lineView, item.getKey(), item.getValue().replace("\\\\r\\\\n", "\r\n"))      //得到value
                + item.getEndValueString();
        lineView.removeAllViews();
        LinearLayout layout = null;
        if (item.isNameRN())
            layout = (LinearLayout) mInflater.inflate(
                    R.layout.layout_formdetail_cell_vertical_lib, null);
        else
            layout = (LinearLayout) mInflater.inflate(
                    R.layout.layout_formdetail_cell_lib, null);

        if (VlineVisible.equalsIgnoreCase("true")) {
            layout.setBackgroundResource(R.drawable.corners_bg_white_press_stroke_v);
        } else {
            layout.setBackgroundResource(R.drawable.corners_bg_grey_press_stroke);
        }
        if (TabStyle == 1) {
            layout.setBackgroundResource(R.drawable.corners_bg_white_press_stroke);//corners_bg_white_press_stroke
            lineView.setBackgroundColor(context.getResources().getColor(R.color.transparent));//corners_bg_white_press_stroke
        }

        SuperTextView tv = (SuperTextView) layout.findViewById(R.id.form_fielditem_text);
        if (TabStyle == 1) {
            if (item.getAlign().equalsIgnoreCase("center")) {
                tv.setGravity(Gravity.CENTER);
            } else if (item.getAlign().equalsIgnoreCase("left")) {
                tv.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
            } else if (item.getAlign().equalsIgnoreCase("right")) {
                tv.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
            }
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
//        tv.setGravity(Gravity.CENTER_VERTICAL);
//        list_tvsize.add(tv);
//        if (item.getAlign().equalsIgnoreCase("center")) {
//            tv.setGravity(Gravity.CENTER);
//        } else if (item.getAlign().equalsIgnoreCase("left")) {
//            tv.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
//        } else if (item.getAlign().equalsIgnoreCase("right")) {
//            tv.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
//        }
        String text = "";
        int nameLength = 0;
        int splitLength = 0;
        int nameColor = context.getResources().getColor(R.color.color_ff555555);
        int valueColor = nameColor;

        //2015-09-22添加 for 项目申报字表行背景颜色
        if (item.getNameFontColor() != 0)
            nameColor = item.getNameFontColor();
        if (item.getValueFontColor() != 0)
            valueColor = item.getValueFontColor();
        if (TabStyle == 1)
            valueColor = Color.parseColor("#999999");
//        else
//            valueColor = item.getNameFontColor();

        boolean isNameVisible = item.isNameVisible();
//        if(item.getHiden()==1){
//            isNameVisible = false;
//        }
        if (isNameVisible) {
            String name = item.getName();
            String split = item.getSplitString();
            text += name + split;
            nameLength = item.getName().length() + split.length();
            splitLength = split.length();
//            if (item.getNameColor().equalsIgnoreCase("red")) {
//                nameColor = Color.RED;
//            }
            if (TabStyle == 1)
                nameColor = Color.parseColor("#999999");
//            else
//                nameColor = item.getNameFontColor();
        }

        if (item.isNameRN() && value.length() > 0) {
            text += "\n";
        }
        text += value;
        final String valueTemp = value;
        if (item.getName().contains("电话") || item.getName().contains("手机")
                || item.getName().contains("联系")) {
            if (value.length() == 7 || value.length() == 8
                    || value.length() == 11) {
                if (isNumeric(value)) {
                    valueColor = Color.parseColor("#5782ab");
                    tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Uri uri = Uri.parse("tel:" + valueTemp);
                            Intent intent = new Intent(Intent.ACTION_DIAL, uri);
                            // 直接打出去的action
                            // intent.setAction("android.intent.action.CALL");
                            context.startActivity(intent);
                        }
                    });
                }
            }
        }
//        int NameBackColor = item.getNameBackColor();    //2015-09-22添加 for 项目申报字表行背景颜色
//        int ValueBackColor = item.getValueBackColor();
        SpannableStringBuilder spanBuilder = new SpannableStringBuilder(tv.setTexts(text,Integer.parseInt(item.getInput().trim())));
        if (nameLength > 0) {
            spanBuilder.setSpan(new ForegroundColorSpan(nameColor), 0, nameLength, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            //spanBuilder.setSpan(new BackgroundColorSpan(123));
        }
        if (value.length() > 0) {
            spanBuilder.setSpan(new ForegroundColorSpan(valueColor), nameLength, text.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        }
        tv.setText(spanBuilder);
        if (item.getBackColor() != 0 && item.getBackColor() != -1) {
            if (layout.findViewById(R.id.form_fielditem_option).getVisibility() == View.GONE && layout.findViewById(R.id.form_fielditem_editimage).getVisibility() == View.GONE) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                params.setMargins(1, 0, 1, 1);
                params.gravity = Gravity.CENTER_VERTICAL;
                tv.setLayoutParams(params);
//                tv.setGravity(Gravity.CENTER);
            }
            if (TabStyle != 1)
                tv.setBackgroundColor(SysConvertUtil.formattingH(item.getBackColor())); //2015-09-22添加 for 项目申报字表字段背景颜色

            //layout.setBackgroundColor(item.getBackColor());
        }
        if (isWaterSecurity == 1) {
            if (null != lineView && null != lineView.getBackground())
                lineView.getBackground().setAlpha((int) (0.5 * 255));
            if (null != tv && null != tv.getBackground())
                tv.getBackground().setAlpha((int) (0.5 * 255));
        }

        final EditField edit = new EditField();
        edit.setKey(item.getKey());
//        edit.setSign(item.getSign());
        edit.setInput(item.getInput());
        edit.setMode(item.getMode());
        edit.setFormKey(item.getFormKey());
        edit.setIsExt(item.isExt());
        edit.setValue(item.getValue());
        tv.setTag(edit);
        if (item.getValue() != null && !item.getValue().trim().equals("")) {
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
//            StartResultInfo mDocResultInfo = null;
//
//            mDocResultInfo = ((StartDetailActivity) context).mDocResultInfo;

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

        //距离左侧5dp
        int left = DensityUtil.dip2px(context, 1);
        tv.setPadding(left, 5, left, 5);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        params.gravity = Gravity.CENTER_VERTICAL;
        lineView.addView(layout, params);
//        if(item.getHiden() == 1){
//            lineView.removeAllViews();
//        }
        return lineView;
    }

    public static boolean isNumeric(String str) {
        for (int i = str.length(); --i >= 0; ) {
            int chr = str.charAt(i);
            if (chr < 48 || chr > 57)
                return false;
        }
        return true;
    }
}
