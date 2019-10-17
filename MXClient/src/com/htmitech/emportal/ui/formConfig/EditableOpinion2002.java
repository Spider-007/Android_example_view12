package com.htmitech.emportal.ui.formConfig;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.htmitech.emportal.HtmitechApplication;
import com.htmitech.emportal.R;
import com.htmitech.emportal.entity.DocResultInfo;
import com.htmitech.emportal.entity.EditField;
import com.htmitech.emportal.entity.EditFieldList;
import com.htmitech.photoselector.model.workflow.FieldItem;
import com.htmitech.emportal.ui.detail.DetailActivity;
import com.htmitech.emportal.ui.detail.DetailActivity2;
import com.htmitech.htcommonformplugin.entity.Editfields;
import com.htmitech.photoselector.model.Fielditems;
import com.htmitech.htcommonformplugin.entity.GetDetailEntity;
import com.minxing.client.util.SysConvertUtil;

import java.util.List;

import htmitech.com.componentlibrary.unit.PreferenceUtils;

/**
 *  **********************处理可编辑的签名字段**********************
 */
public class EditableOpinion2002 {
    private Context context;

    public EditableOpinion2002(Context context){
        this.context = context;
    }


    public LinearLayout editableOpinion(String VlineVisible,FieldItem item,LayoutInflater mInflater,List<TextView> list_tvsize,List<EditField> EditFileds){
        // 2, **********************处理可编辑的签名字段**********************

        LinearLayout lineView = new LinearLayout(                                    //建立容器
                HtmitechApplication.instance());
        lineView.setOrientation(LinearLayout.VERTICAL);
        lineView.setGravity(Gravity.CENTER_VERTICAL);
        lineView.setBackgroundColor(SysConvertUtil.formattingH(item.getBackColor()));
        if (VlineVisible.equalsIgnoreCase("true"))
            lineView.setBackgroundResource(R.drawable.corners_bg_white_press_stroke);

        if (item.opintions != null && item.opintions.size() > 0) {                    //如果有多个可签名字段
        } else {                                                                    //暂时没有
            // 仅增加name显示														//和意见一样的布局
            LinearLayout layout = (LinearLayout) mInflater.inflate(
                    R.layout.layout_formdetail_cell_foropion_lib, null);
            TextView name = (TextView) layout
                    .findViewById(R.id.form_fielditem_option_name);
            list_tvsize.add(name);
            TextView text = (TextView) layout
                    .findViewById(R.id.form_fielditem_option_text);
            list_tvsize.add(text);
            TextView username = (TextView) layout
                    .findViewById(R.id.form_fielditem_option_username);
            list_tvsize.add(username);
            TextView savetime = (TextView) layout
                    .findViewById(R.id.form_fielditem_option_saveTime);
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
                String strName = item.getBeforeNameString()                            //拼接名称
                        + item.getName() + item.getEndNameString();
                String split = item.getSplitString();
                strName = strName + split;
                name.setVisibility(View.VISIBLE);                                    //显示
                name.setText(strName);
                if (item.getNameColor().equalsIgnoreCase("red")) {
                    name.setTextColor(Color.RED);
                }
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(    //布局参数
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                params.gravity = Gravity.CENTER_VERTICAL;

                lineView.addView(layout, params);                                    //添加到布局
/*
                        // 2015-8-18 for DetailActivity2
						if (DetailActivity2.currentActivity)
							lineView.setEnabled(false);*/
            }
        }
        /**
         * 2015 7 31 号修改 用于单击输入签名
         */
        LinearLayout opionlayout = (LinearLayout) mInflater.inflate(            //构建细节布局 现在只是宽高20dp的图片
                R.layout.layout_formdetail_cell_opiontext_lib, null);
        final TextView tvOption = (TextView) opionlayout
                .findViewById(R.id.form_fielditem_option);
        LinearLayout layout_yijian = (LinearLayout) opionlayout.findViewById(R.id.layout_yijian);
        TextView form_fielditem_option_username =  (TextView) opionlayout
                .findViewById(R.id.form_fielditem_option_username);
//        form_fielditem_option_username.setVisibility(View.VISIBLE);
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



        // 2015-8-18 for DetailActivity2
                /*if (DetailActivity2.currentActivity)
                    opionlayout.setEnabled(false);*/
        if (item.getValue() != null && !"".equals(item.getValue())) {
            form_fielditem_option_username.setVisibility(View.VISIBLE);                        //表格显示，显示当前用户名
            form_fielditem_option_username.setText(item.getValue());
//
//            form_fielditem_option_username.setText("");
//            for (int i = 0; i < arrayName.length; i++) {
//                SpannableString spannableString = new SpannableString(arrayName[i] + (i != arrayName.length - 1 ? "," : ""));
//                ClickableSpan clickttt = new PhoneClickText(getActivity(), arrayUserId[i], "Flow", new FlowPeopleOnclick());
//                spannableString.setSpan(clickttt, 0, arrayName[i].length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
//                holder.tv_content_userName.append(spannableString);
//                holder.tv_content_userName.setMovementMethod(LinkMovementMethod.getInstance());
//            }

//            form_fielditem_option_username.setOnClickListener(new PersonOnClick());
        }


        final String name = PreferenceUtils                                    //单击布局拿到当前用户
                .getOAUserName(context);
        EditField edit = new EditField();
        if(item.getValue().contains(name)){
            edit.tempValue = name;
        }
        String[] nameList = item.getValue().split("\r\n");
        if(nameList[nameList.length - 1].contains(name)){
            layout_yijian.setVisibility(View.GONE);
        }else{
            layout_yijian.setVisibility(View.VISIBLE);
        }
                                           //选项布局建立tag
        edit.setKey(item.getKey());
        edit.setSign(item.getSign());
        edit.setInput(item.getInput());
        edit.setMode(item.getMode());
        edit.setFormKey(item.getFormKey());
        opionlayout.setTag(edit);
        opionlayout.setOnClickListener(new OpionlayoutClickListener(EditFileds,name,tvOption));

        ImageView img = (ImageView) opionlayout
                .findViewById(R.id.form_fielditem_editimage);                //显示可编辑图片
        img.setVisibility(View.VISIBLE);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        params.gravity = Gravity.CENTER_VERTICAL;
        lineView.addView(opionlayout, params);

        return lineView;                                                    //返回可编辑字段容器

    }


    public LinearLayout editableOpinionCommonForm(boolean VlineVisible, Fielditems item, LayoutInflater mInflater, List<TextView> list_tvsize, List<Editfields> mEditFileds){
        // 2, **********************处理可编辑的签名字段**********************

        LinearLayout lineView = new LinearLayout(                                    //建立容器
                HtmitechApplication.instance());
        lineView.setOrientation(LinearLayout.VERTICAL);
        lineView.setGravity(Gravity.CENTER_VERTICAL);
        lineView.setBackgroundColor(SysConvertUtil.formattingH(item.getFiled_backcolor()));
        if (VlineVisible)
            lineView.setBackgroundResource(R.drawable.corners_bg_white_press_stroke);

        if (item.getOpintions() != null && item.getOpintions().size() > 0) {                    //如果有多个可签名字段
        } else {                                                                    //暂时没有
            // 仅增加name显示														//和意见一样的布局
            LinearLayout layout = (LinearLayout) mInflater.inflate(
                    R.layout.layout_formdetail_cell_foropion_lib, null);
            TextView name = (TextView) layout
                    .findViewById(R.id.form_fielditem_option_name);
            list_tvsize.add(name);
            TextView text = (TextView) layout
                    .findViewById(R.id.form_fielditem_option_text);
            list_tvsize.add(text);
            TextView username = (TextView) layout
                    .findViewById(R.id.form_fielditem_option_username);
            list_tvsize.add(username);
            TextView savetime = (TextView) layout
                    .findViewById(R.id.form_fielditem_option_saveTime);
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


            if (item.isName_visible()) {                                                //如果显示名称 - 名称是显示title
                String strName = item.getBefore_name_str()                            //拼接名称
                        + item.getName() + item.getEnd_name_str();
                String split = item.getSplit_str();
                strName = strName + split;
                name.setVisibility(View.VISIBLE);                                    //显示
                name.setText(strName);
                if (item.getName_color().equalsIgnoreCase("red")) {
                    name.setTextColor(Color.RED);
                }
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(    //布局参数
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                params.gravity = Gravity.CENTER_VERTICAL;

                lineView.addView(layout, params);                                    //添加到布局
/*
                        // 2015-8-18 for DetailActivity2
						if (DetailActivity2.currentActivity)
							lineView.setEnabled(false);*/
            }
        }
        /**
         * 2015 7 31 号修改 用于单击输入签名
         */
        LinearLayout opionlayout = (LinearLayout) mInflater.inflate(            //构建细节布局 现在只是宽高20dp的图片
                R.layout.layout_formdetail_cell_opiontext_lib, null);
        final TextView tvOption = (TextView) opionlayout
                .findViewById(R.id.form_fielditem_option);
        LinearLayout layout_yijian = (LinearLayout) opionlayout.findViewById(R.id.layout_yijian);
        TextView form_fielditem_option_username =  (TextView) opionlayout
                .findViewById(R.id.form_fielditem_option_username);
//        form_fielditem_option_username.setVisibility(View.VISIBLE);
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
        if (item.isMustinput()) {
            layout_yijian.setBackgroundResource(R.drawable.form_input_bg_must);
        }
        list_tvsize.add(form_fielditem_option_username);
        list_tvsize.add(tvOption);



        // 2015-8-18 for DetailActivity2
                /*if (DetailActivity2.currentActivity)
                    opionlayout.setEnabled(false);*/
        if (item.getValue() != null && !"".equals(item.getValue())) {
            form_fielditem_option_username.setVisibility(View.VISIBLE);                        //表格显示，显示当前用户名
            form_fielditem_option_username.setText(item.getValue());
//
//            form_fielditem_option_username.setText("");
//            for (int i = 0; i < arrayName.length; i++) {
//                SpannableString spannableString = new SpannableString(arrayName[i] + (i != arrayName.length - 1 ? "," : ""));
//                ClickableSpan clickttt = new PhoneClickText(getActivity(), arrayUserId[i], "Flow", new FlowPeopleOnclick());
//                spannableString.setSpan(clickttt, 0, arrayName[i].length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
//                holder.tv_content_userName.append(spannableString);
//                holder.tv_content_userName.setMovementMethod(LinkMovementMethod.getInstance());
//            }

//            form_fielditem_option_username.setOnClickListener(new PersonOnClick());
        }


        final String name = PreferenceUtils                                    //单击布局拿到当前用户
                .getOAUserName(context);
        Editfields edit = new Editfields();
        String[] nameList = item.getValue().split("\r\n");
        if(nameList[nameList.length - 1].contains(name)){
            layout_yijian.setVisibility(View.GONE);
        }else{
            layout_yijian.setVisibility(View.VISIBLE);
        }
        //选项布局建立tag
        edit.setKey(item.getKey());
        opionlayout.setTag(edit);
        opionlayout.setOnClickListener(new OpionlayoutClickListenerCommonForm(mEditFileds,name,tvOption));

        ImageView img = (ImageView) opionlayout
                .findViewById(R.id.form_fielditem_editimage);                //显示可编辑图片
        img.setVisibility(View.VISIBLE);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        params.gravity = Gravity.CENTER_VERTICAL;
        lineView.addView(opionlayout, params);

        return lineView;                                                    //返回可编辑字段容器

    }


    public class OpionlayoutClickListener implements  View.OnClickListener{

        private List<EditField> EditFileds;
        private String name;
        private TextView tvOption;
        public OpionlayoutClickListener(List<EditField> EditFileds,String name,TextView tvOption){
            this.EditFileds = EditFileds;
            this.name = name;
            this.tvOption = tvOption;
        }


        @Override
        public void onClick(View v) {

            if (name != null && !"".equals(name)) {
                tvOption.setVisibility(View.VISIBLE);                        //表格显示，显示当前用户名
                tvOption.setText(name);

                // TODO Auto-generated method stub
                // 1,将已经编辑的字段和值放入到办理是需要回发的EditField[] 中

                String strValue = "";                                        //判断有没有签名图片来决定会发的串
                String tmp = PreferenceUtils
                        .getAttribute1(context);
                EditField edit = (EditField) v.getTag();
                // 有签名
                if (tmp !=null&&!tmp.equals("null")&&!tmp.trim().equals("")) {
                    strValue = PreferenceUtils
                            .getOAUserID(context)
                            + "#"
                            + PreferenceUtils
                            .getAttribute1(context)
                            + "#1";

                } else {
                    if(PreferenceUtils.getThirdDepartmentName(context)!=null&&!PreferenceUtils.getThirdDepartmentName(context).equals("")){
                        strValue = PreferenceUtils
                                .getOAUserID(context)
                                + "#"
                                + PreferenceUtils
                                .getOAUserName(context)
                                + "("
                                + PreferenceUtils
                                .getThirdDepartmentName(context)
                                + ")#2";
                    }else {
                        strValue = PreferenceUtils
                                .getOAUserID(context)
                                + "#"
                                + PreferenceUtils
                                .getOAUserName(context)
                                +""
                                +"#2";
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
                        if (EditFileds.get(j).getKey()                    //setValue 否则直接存入
                                .equals(edit.getKey())) {
                            hasfind = true;
                            EditFileds.get(j).setValue(
                                    edit.getValue());
                            EditFileds.get(j).tempValue = strValue;
                            break;
                        }
                    }
                    if (!hasfind)
                        EditFileds.add(edit);
                }
                Log.d("FormFragment", "EditFileds:" + EditFileds);

                // 2015-8-17
                DocResultInfo mDocResultInfo = null;
                if (DetailActivity2.currentActivity) {
                    mDocResultInfo = ((DetailActivity2) context).mDocResultInfo;
                } else {
                    mDocResultInfo = ((DetailActivity) context).mDocResultInfo;
                }

                // DocResultInfo mDocResultInfo = ((DetailActivity)
                // getActivity()).mDocResultInfo;
                mDocResultInfo.getResult()
                        .setEditFields(EditFileds);                                //刷新可回发字段集
                // 2,必填字段处理--将输入的意见放入相应必填字段中
                EditFieldList mustFieldList = EditFieldList
                        .getInstance();
                for (int i = 0; i < mustFieldList.getList().size(); i++) {        //遍历必填项，如果key相同设置value
                    if (mustFieldList
                            .getList()
                            .get(i)
                            .getKey()
                            .equalsIgnoreCase(
                                    ((EditField) v.getTag())
                                            .getKey())) {
                        mustFieldList.getList().get(i)
                                .setValue(strValue);
                        mustFieldList.getList().get(i).tempValue = strValue;
                    }
                }
            }
        }
    }

    public class OpionlayoutClickListenerCommonForm implements  View.OnClickListener{

        private List<Editfields> mEditFileds;
        private String name;
        private TextView tvOption;
        public OpionlayoutClickListenerCommonForm(List<Editfields> EditFileds,String name,TextView tvOption){
            this.mEditFileds = EditFileds;
            this.name = name;
            this.tvOption = tvOption;
        }


        @Override
        public void onClick(View v) {

            if (name != null && !"".equals(name)) {
                tvOption.setVisibility(View.VISIBLE);                        //表格显示，显示当前用户名
                tvOption.setText(name);

                // TODO Auto-generated method stub
                // 1,将已经编辑的字段和值放入到办理是需要回发的EditField[] 中

                String strValue = "";                                        //判断有没有签名图片来决定会发的串
                String tmp = PreferenceUtils
                        .getAttribute1(context);
                Editfields edit = (Editfields) v.getTag();
                // 有签名
                if (tmp !=null&&!tmp.equals("null")&&!tmp.trim().equals("")) {
                    strValue = PreferenceUtils
                            .getOAUserID(context)
                            + "#"
                            + PreferenceUtils
                            .getAttribute1(context)
                            + "#1";
                } else {
                    if(PreferenceUtils.getThirdDepartmentName(context)!=null&&!PreferenceUtils.getThirdDepartmentName(context).equals("")){
                        strValue = PreferenceUtils
                                .getOAUserID(context)
                                + "#"
                                + PreferenceUtils
                                .getOAUserName(context)
                                + "("
                                + PreferenceUtils
                                .getThirdDepartmentName(context)
                                + ")#2";
                    }else {
                        strValue = PreferenceUtils
                                .getOAUserID(context)
                                + "#"
                                + PreferenceUtils
                                .getOAUserName(context)
                                +""
                                +"#2";
                    }
                }


                //回发控件的处理流程 ，从控件中拿出可编辑结构
                edit.setValue(strValue);
                if (mEditFileds != null && mEditFileds.size() == 0)        //添加到可编辑数组中
                    mEditFileds.add(edit);
                else {
                    boolean hasfind = false;
                    for (int j = 0; j < mEditFileds.size(); j++) {        //遍历可回发数组 是否有相同的key，如果有直接
                        if (mEditFileds.get(j).getKey()                    //setValue 否则直接存入
                                .equals(edit.getKey())) {
                            hasfind = true;
                            mEditFileds.get(j).setValue(
                                    edit.getValue());
                            break;
                        }
                    }
                    if (!hasfind)
                        mEditFileds.add(edit);
                }
                Log.d("FormFragment", "EditFileds:" + mEditFileds);

                // 2015-8-17
                GetDetailEntity mGetDetailEntity = null;
                if (DetailActivity2.currentActivity) {
//                    mGetDetailEntity = ((DetailActivity2) context).mDocResultInfo;
                } else {
//                    mGetDetailEntity = ((GeneralFormDetalActivity) context).mGetDetailEntity;
                }

                // DocResultInfo mDocResultInfo = ((DetailActivity)
                // getActivity()).mDocResultInfo;
                mGetDetailEntity.getResult()
                        .setEditfields(mEditFileds);                                //刷新可回发字段集
                // 2,必填字段处理--将输入的意见放入相应必填字段中
                EditFieldList mustFieldList = EditFieldList
                        .getInstance();
                for (int i = 0; i < mustFieldList.getList().size(); i++) {        //遍历必填项，如果key相同设置value
                    if (mustFieldList
                            .getList()
                            .get(i)
                            .getKey()
                            .equalsIgnoreCase(
                                    ((EditField) v.getTag())
                                            .getKey())) {
                        mustFieldList.getList().get(i)
                                .setValue(strValue);
                        mustFieldList.getList().get(i).tempValue = strValue;
                    }
                }
            }
        }
    }


}
