package htmitech.formConfig;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.List;

import htmitech.com.componentlibrary.api.ComponentInit;
import htmitech.com.componentlibrary.entity.DocResultInfo;
import htmitech.com.componentlibrary.entity.EditField;
import htmitech.com.componentlibrary.entity.EditFieldList;
import htmitech.com.componentlibrary.entity.FieldItem;
import htmitech.com.componentlibrary.unit.DrawableUtils;
import htmitech.com.componentlibrary.unit.SysConvertUtil;
import htmitech.com.formlibrary.R;

/**
 * **********************处理可编辑的签名字段**********************
 */
public class EditableOpinion2002 {
    private Context context;
    private LayoutInflater mInflater;
    private List<TextView> list_tvsize;
    private List<EditField> EditFileds;
    private LinearLayout lineView;
    private DocResultInfo mDocResultInfo;
    private LinearLayout layout_yijian;
    private FieldItem item;
    private int com_workflow_mobileconfig_IM_enabled;
    private int com_workflow_mobileconfig_opinion_style;

    public void setMustInputLoacal() {
        layout_yijian.setBackgroundResource(R.drawable.form_input_bg_must_local);

    }

    public EditableOpinion2002(Context context) {
        this.context = context;
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public LinearLayout editableOpinion(String VlineVisible, FieldItem item, LayoutInflater mInflater, List<TextView> list_tvsize, List<EditField> EditFileds, DocResultInfo mDocResultInfo, int com_workflow_mobileconfig_IM_enabled,int com_workflow_mobileconfig_opinion_style) {
        // 2, **********************处理可编辑的签名字段**********************
        this.item = item;
        this.mInflater = mInflater;
        this.list_tvsize = list_tvsize;
        this.EditFileds = EditFileds;
        this.mDocResultInfo = mDocResultInfo;
        this.com_workflow_mobileconfig_IM_enabled = com_workflow_mobileconfig_IM_enabled;
        this.com_workflow_mobileconfig_opinion_style = com_workflow_mobileconfig_opinion_style;
        lineView = new LinearLayout(context);                                //建立容器
        lineView.setOrientation(LinearLayout.VERTICAL);
        lineView.setGravity(Gravity.CENTER_VERTICAL);

        if (VlineVisible.equalsIgnoreCase("true")) {
            lineView.setBackground(DrawableUtils.getLayerDrawable(context, item.getBackColor()));//设置显示背景
        } else {
            lineView.setBackgroundColor(SysConvertUtil.formattingH(item.getBackColor()));
        }

        return setReView2002(item);
    }

    public LinearLayout setReView2002(FieldItem item) {
        lineView.removeAllViews();
        if (item.opintions != null && item.opintions.size() > 0) {                    //如果有多个可签名字段
        } else {                                                                    //暂时没有
            // 仅增加name显示														//和意见一样的布局
            LinearLayout layout = (LinearLayout) mInflater.inflate(R.layout.layout_formdetail_cell_foropion_lib, null);
            TextView name = (TextView) layout.findViewById(R.id.form_fielditem_option_name);
            list_tvsize.add(name);
            TextView text = (TextView) layout.findViewById(R.id.form_fielditem_option_text);
            list_tvsize.add(text);
            TextView username = (TextView) layout.findViewById(R.id.form_fielditem_option_username);
            list_tvsize.add(username);
            TextView savetime = (TextView) layout.findViewById(R.id.form_fielditem_option_saveTime);
            list_tvsize.add(savetime);
            text.setVisibility(View.GONE);
            username.setVisibility(View.GONE);                                        //签名人和签名时间、签名不显示
            savetime.setVisibility(View.GONE);
            /**
             * add by heyang
             * date 2016-7-20
             * 让字垂直居中
             */

            name.setGravity(Gravity.CENTER_VERTICAL);
            text.setGravity(Gravity.CENTER_VERTICAL);
            username.setGravity(Gravity.CENTER_VERTICAL);
            savetime.setGravity(Gravity.CENTER_VERTICAL);
            if (item.isNameVisible()) {                                                //如果显示名称 - 名称是显示title
                String strName = item.getName();                           //拼接名称
                String split = item.getSplitString();
                strName = strName + split;
                name.setVisibility(View.VISIBLE);                                    //显示
                name.setText(strName);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);   //布局参数
                params.gravity = Gravity.CENTER_VERTICAL;
                lineView.addView(layout, params);                                    //添加到布局
            }
        }
        /**
         * 2015 7 31 号修改 用于单击输入签名
         */
        LinearLayout opionlayout = (LinearLayout) mInflater.inflate(R.layout.layout_formdetail_cell_opiontext_lib, null);           //构建细节布局 现在只是宽高20dp的图片
        final TextView tvOption = (TextView) opionlayout.findViewById(R.id.form_fielditem_option);
        layout_yijian = (LinearLayout) opionlayout.findViewById(R.id.layout_yijian);
        TextView form_fielditem_option_username = (TextView) opionlayout.findViewById(R.id.form_fielditem_option_username);
        form_fielditem_option_username.setTextColor(context.getResources().getColor(R.color.form_bg_uncontent));
        tvOption.setVisibility(View.INVISIBLE);

        /**
         * add by heyang
         * date 2016-7-20
         * 让字垂直居中
         */
        tvOption.setGravity(Gravity.CENTER_VERTICAL);

        /**
         * add by heyang
         * date 2016-7-18
         * if isMustInput is true then change backgroundclolor as "color_mustinputback"
         */
        if (item.isMustInput()) {
            layout_yijian.setBackgroundResource(R.drawable.form_input_bg_must);
        }
        list_tvsize.add(form_fielditem_option_username);
        list_tvsize.add(tvOption);
        String valueString = "";
        if (null != item.signs) {
            for (int i = 0; i < item.signs.size(); i++) {
                if (i == 0) {
                    valueString += item.signs.get(i).userName + "\r\n" + item.signs.get(i).saveTime;
                } else {
                    valueString += "\r\n" + item.signs.get(i).userName + "\r\n" + item.signs.get(i).saveTime;
                }
            }
        }
        //如果vale是空的 那么就把sign拼的字符串赋值给value， 保证value一定有值
        if (item.getValue() != null && item.getValue().equals("")) {
            item.setValue(valueString);
        }

        if (item.getValue() != null && !"".equals(item.getValue())) {
            form_fielditem_option_username.setVisibility(View.VISIBLE);                        //表格显示，显示当前用户名
            form_fielditem_option_username.setText(item.getValue());
        }
        final String name = ComponentInit.getInstance().getOAUserName();                                  //单击布局拿到当前用户
        EditField edit = new EditField();
        if (item.getValue().contains(name)) {
            edit.tempValue = name;
        }
        String[] nameList = item.getValue().split("\r\n");
        if (nameList[nameList.length - 1].contains(name)) {
            layout_yijian.setVisibility(View.GONE);
        } else {
            layout_yijian.setVisibility(View.VISIBLE);
        }
        //选项布局建立tag
        edit.setKey(item.getKey());
        edit.setInput(item.getInput());
        edit.setMode(item.getMode());
        edit.setIsExt(item.isExt());
        edit.setFormKey(item.getFormKey());
        opionlayout.setTag(edit);
        opionlayout.setOnClickListener(new OpionlayoutClickListener(EditFileds, name, tvOption));
        ImageView img = (ImageView) opionlayout.findViewById(R.id.form_fielditem_editimage);                //显示可编辑图片
        img.setVisibility(View.VISIBLE);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        params.gravity = Gravity.CENTER_VERTICAL;
        lineView.addView(opionlayout, params);

        return lineView;
    }


    public class OpionlayoutClickListener implements View.OnClickListener {

        private List<EditField> EditFileds;
        private String name;
        private TextView tvOption;

        public OpionlayoutClickListener(List<EditField> EditFileds, String name, TextView tvOption) {
            this.EditFileds = EditFileds;
            this.name = name;
            this.tvOption = tvOption;
        }


        @Override
        public void onClick(View v) {

            if (name != null && !"".equals(name)) {
                tvOption.setVisibility(View.VISIBLE);                        //表格显示，显示当前用户名
                tvOption.setText(name);
                if (item.isMustInput()) {
                    layout_yijian.setBackgroundResource(R.drawable.form_input_bg_must);
                }
                // TODO Auto-generated method stub
                // 1,将已经编辑的字段和值放入到办理是需要回发的EditField[] 中

                String strValue = "";                                        //判断有没有签名图片来决定会发的串
                String tmp = ComponentInit.getInstance().getAttribute1();
                EditField edit = (EditField) v.getTag();
                // 有签名
                if (tmp != null && !tmp.equals("null") && !tmp.trim().equals("")) {
                    strValue = ComponentInit.getInstance().getEMPUserID() + "#" + ComponentInit.getInstance().getAttribute1() + "#1";

                } else {
                    if (ComponentInit.getInstance().getThirdDepartmentName() != null && !ComponentInit.getInstance().getThirdDepartmentName().equals("")) {
                        if(com_workflow_mobileconfig_opinion_style == 2){
                            strValue = ComponentInit.getInstance().getEMPUserID() + "#" + ComponentInit.getInstance().getEMPUserName() + "(" + ComponentInit.getInstance().getThirdDepartmentName() + ")#2";
                        }else{
                            strValue = ComponentInit.getInstance().getEMPUserID() + "#" + ComponentInit.getInstance().getEMPUserName()+"#2";
                        }

                    } else {
                        strValue = ComponentInit.getInstance().getEMPUserID() + "#" + ComponentInit.getInstance().getEMPUserName() + "" + "#2";
                    }
                }

                //回发控件的处理流程 ，从控件中拿出可编辑结构
                edit.tempValue = strValue;
                edit.setValue(strValue);
                if (EditFileds != null && EditFileds.size() == 0)        //添加到可编辑数组中
                    EditFileds.add(edit);
                else {
                    boolean hasfind = false;
                    for (int j = 0; j < EditFileds.size(); j++) {        //遍历可回发数组 是否有相同的key，如果有直接
                        if (EditFileds.get(j).getKey().equals(edit.getKey())) {                //setValue 否则直接存入
                            hasfind = true;
                            EditFileds.get(j).setValue(edit.getValue());
                            EditFileds.get(j).tempValue = strValue;
                            break;
                        }
                    }
                    if (!hasfind)
                        EditFileds.add(edit);
                }
                Log.d("FormFragment", "EditFileds:" + EditFileds);

                // 2015-8-17
//                DocResultInfo mDocResultInfo = mDocResultInfo;
//                mDocResultInfo = mDocResultInfo;
                mDocResultInfo.getResult().setEditFields(EditFileds);                                //刷新可回发字段集
                // 2,必填字段处理--将输入的意见放入相应必填字段中
                EditFieldList mustFieldList = EditFieldList.getInstance();
                for (int i = 0; i < mustFieldList.getList().size(); i++) {        //遍历必填项，如果key相同设置value
                    if (mustFieldList.getList().get(i).getKey().equalsIgnoreCase(((EditField) v.getTag()).getKey())) {
                        mustFieldList.getList().get(i).setValue(strValue);
                        mustFieldList.getList().get(i).tempValue = strValue;
                    }
                }
            }
        }
    }
}
