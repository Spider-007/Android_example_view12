package com.htmitech.htcommonformplugin.weight;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.htmitech.addressbook.R;
import com.htmitech.commonx.util.DeviceUtils;
import com.htmitech.emportal.HtmitechApplication;
import com.htmitech.emportal.entity.EditFieldList;
import com.htmitech.emportal.ui.detail.CheckForm;
import com.htmitech.htcommonformplugin.entity.Editfields;
import com.htmitech.htcommonformplugin.entity.GetDetailEntity;

import java.util.ArrayList;
import java.util.List;

public class HtmitechRadioButtonCommonForm {
    private Context context;
    private List<CheckForm> listText;// 单选按钮的文字内容
    private RadioGroup rgForm;
    private LinearLayout layout;
    private List<RadioButton> listRadion;
    private List<String> listContent;
    private List<Editfields> EditFileds = null; // 缓存已经编辑的表单字段，回发用。
    private Editfields edt = null;
    private GetDetailEntity mGetDetailEntity = null;
    private float heightLinear = 50.0f;
    int height = DeviceUtils.dip2px(HtmitechApplication.getApplication(), heightLinear);
    public HtmitechRadioButtonCommonForm(Context context) {
        this.context = context;
        listText = new ArrayList<CheckForm>();
        listRadion = new ArrayList<RadioButton>();
        listContent = new ArrayList<String>();
        createRadioGroup();
    }
    /**
     * 创建RadioGroup
     */
    private void createRadioGroup() {
        rgForm = new RadioGroup(context);
        rgForm.setPadding(0, height / 4, 0, height / 4);
        rgForm.setOrientation(LinearLayout.VERTICAL);
    }
    /**
     * 设置RadioButton的文字内容用list集合装
     *
     * @param listText
     */
    public void setTextList(List<CheckForm> listText,String value) {
        this.listText.addAll(listText);
        createRadionButton(value);
    }
    public void createRadionButton(String value) {
        int checkId = -1;
        for (int i = 0; i < listText.size(); i++) {
            RadioButton rb = new RadioButton(context);
            rb.setText(listText.get(i).name);
            rb.setTag(listText.get(i).getValue());
            rb.setId(i); ///首先得设置一个ID进行标识
            if(value.equals(listText.get(i).getValue())){
                checkId = rb.getId();
            }
            int pxRadio = Dp2Px(context, 60);
            LayoutParams params = new LayoutParams(
                    LayoutParams.MATCH_PARENT, pxRadio);
            rb.setLayoutParams(params);
            rb.setButtonDrawable(null);
            int toLeftLength = Dp2Px(context, 12);
            rb.setPadding(toLeftLength, 0, 0, 0);
            rb.setTextAppearance(context, R.style.RadiobuttonStyle);
            rb.setButtonDrawable(R.drawable.formradiobutton_selector);
            rgForm.addView(rb,i);
            listRadion.add(rb);
        }

        rgForm.check(checkId);  //设置RadioGroup默认选中状态
//        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams()
        rgForm.setPadding(20,0,0,0);
        layout.addView(rgForm);
        rgForm.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup arg0, int checkId) {
                // TODO Auto-generated method stub
                RadioButton rb = (RadioButton) arg0.findViewById(checkId);
                String strValue = rb.getTag().toString();
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

//                            EditField edit_Value = new EditField();
//                            edit_Value.setKey(edt.getKey() + "_VALUE");
//                            edit_Value.setValue(strValue);//
//                            edit_Value.setFormKey(edt.getFormKey());//
//                            EditFileds.add(edit_Value);

                            break;
                        }
                    }
                    if (!hasfind)
                        EditFileds.add(edt);
                }
                mGetDetailEntity.getResult()
                        .setEditfields(EditFileds);                                //刷新可回发字段集
                EditFieldList mustFieldList = EditFieldList
                        .getInstance();
                for (int i = 0; i < mustFieldList.getList().size(); i++) {        //遍历必填项，如果key相同设置value
                    if (mustFieldList.getList().get(i).getKey().equalsIgnoreCase(edt.getKey())) {
                        mustFieldList.getList().get(i).setValue(strValue);
                    }
                }
            }
        });
    }

    /**
     * 把dp-->px
     *
     * @param context
     * @param dp
     * @return
     */
    public int Dp2Px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
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

    public void setView(LinearLayout layout) {
        this.layout = layout;
    }

    /**
     * 返回选中的RadionButton的结果集合
     *
     * @return
     */
    public List<String> getContent() {
        for (int i = 0; i < listRadion.size(); i++) {
            if (listRadion.get(i).isChecked()) {
                listContent.add(listRadion.get(i).getText().toString());
            }
        }
        return listContent;
    }

    public void setEdit(List<Editfields> EditFileds, Editfields edt, GetDetailEntity mGetDetailEntity) {
        this.EditFileds = EditFileds;
        this.edt = edt;
        this.mGetDetailEntity = mGetDetailEntity;
    }

}
