package htmitech.com.componentlibrary;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import java.util.ArrayList;
import java.util.List;

import htmitech.com.componentlibrary.entity.CheckForm;
import htmitech.com.componentlibrary.entity.DocResultInfo;
import htmitech.com.componentlibrary.entity.EditField;
import htmitech.com.componentlibrary.entity.EditFieldList;
import htmitech.com.componentlibrary.unit.DensityUtil;

public class HtmitechCheckBox {

    private Context context;
    private List<CheckForm> listText;// 多选按钮的文字内容
    private LinearLayout layout;
    private List<CheckBox> listCB;
    private List<String> listContent;
    private List<EditField> EditFileds = null; // 缓存已经编辑的表单字段，回发用。
    private EditField edt = null;
    private DocResultInfo mDocResultInfo = null;
    private float heightLinear = 50.0f;
    int height ;
    private String[] checkValueName;

    public HtmitechCheckBox(Context context) {
        // TODO Auto-generated constructor stub
        this.context = context;
        height = DensityUtil.dip2px(context, heightLinear);
    }

    /**
     * 设置复选框的文字内容
     *
     * @param listText
     */
    public void setList(List<CheckForm> listText, String[] checkValue) {
        this.checkValueName = checkValue;
        this.listText.addAll(listText);
        createCheckBox();
    }

    private void createCheckBox() {
        for (int i = 0; i < listText.size(); i++) {
            CheckBox cb = new CheckBox(context);
            cb.setTextAppearance(context, R.style.FormCheckBox);
            cb.setButtonDrawable(context.getResources().getDrawable(
                    R.drawable.formcheckbox_selector));
            int pxBox = DensityUtil.dip2px(context, 40);
            if (checkValueName != null) {
                for (String checkValue : checkValueName) {
                    if (checkValue.equals("" + listText.get(i).getValue())) {
                        cb.setChecked(true);
                        break;
                    }
                }
            }
            int toLeftLength = DensityUtil.dip2px(context, 12);
            LayoutParams params = new LayoutParams(
                    LayoutParams.MATCH_PARENT, pxBox);
            params.setMargins(toLeftLength, 0, 0, 0);
            cb.setLayoutParams(params);

            cb.setPadding(toLeftLength, 0, 0, 0);


            cb.setText(listText.get(i).name);
//            cb.setTextSize(sp2px(context, 17f));
            cb.setTag(listText.get(i).getValue());
            cb.setOnClickListener(new CheckBoxOnclick());
            listCB.add(cb);
            layout.addView(cb);
        }
        layout.setPadding(20, 0, 0, 0);
    }


    public void setView(LinearLayout layout) {
        this.layout = layout;
        this.layout.setOrientation(LinearLayout.VERTICAL);

        listText = new ArrayList<CheckForm>();
        listCB = new ArrayList<CheckBox>();
        listContent = new ArrayList<String>();

    }

    public List<String> getContent() {
        for (int i = 0; i < listCB.size(); i++) {
            if (listCB.get(i).isChecked()) {
                listContent.add(listCB.get(i).getText().toString());
            }
        }
        return listContent;
    }

    public void setEdit(List<EditField> EditFileds, EditField edt, DocResultInfo mDocResultInfo) {
        this.EditFileds = EditFileds;
        this.edt = edt;
        this.mDocResultInfo = mDocResultInfo;
    }

    public class CheckBoxOnclick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            String strValue = "";
            for (int i = 0; i < listCB.size(); i++) {
                if (listCB.get(i).isChecked()) {
                    if (i != listCB.size()) {
                        strValue += (listCB.get(i).getTag().toString() + "|");
                    } else {
                        strValue += listCB.get(i).getTag().toString();
                    }
                }
            }
            edt.setValue(strValue);
            if (EditFileds != null && EditFileds.size() == 0)        //添加到可编辑数组中
                EditFileds.add(edt);
            else {
                boolean hasfind = false;
                for (int j = 0; j < EditFileds.size(); j++) {        //遍历可回发数组 是否有相同的key，如果有直接
                    if (EditFileds.get(j).getKey()                    //setValue 否则直接存入
                            .equals(edt.getKey())) {
                        hasfind = true;
                        EditFileds.get(j).setValue(
                                edt.getValue());
                        break;
                    }
                }
                if (!hasfind)
                    EditFileds.add(edt);
            }
            mDocResultInfo.getResult()
                    .setEditFields(EditFileds);                                //刷新可回发字段集
            EditFieldList mustFieldList = EditFieldList
                    .getInstance();
            for (int i = 0; i < mustFieldList.getList().size(); i++) {        //遍历必填项，如果key相同设置value
                if (mustFieldList.getList().get(i).getKey().equalsIgnoreCase(edt.getKey())) {
                    mustFieldList.getList().get(i).setValue(strValue);
                }
            }
        }
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue (DisplayMetrics类中属性scaledDensity)
     * @return
     */
    public int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

}
