package com.htmitech.emportal.ui.formConfig;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.htmitech.emportal.HtmitechApplication;
import com.htmitech.emportal.R;
import com.htmitech.photoselector.model.workflow.FieldItem;
import com.htmitech.photoselector.model.Fielditems;
import com.htmitech.proxy.dao.AppliationCenterDao;
import com.minxing.client.util.SysConvertUtil;
import com.minxing.kit.api.MXAPI;
import com.minxing.kit.api.bean.MXError;
import com.minxing.kit.api.callback.MXApiCallback;

import java.util.List;

/**
 * Created by htrf-pc on 2016/9/7.
 */
public class ModeOpinion19 {

    public Context context;
    public AppliationCenterDao mAppliationCenterDao;

    public ModeOpinion19(Context context) {
        this.context = context;
        mAppliationCenterDao = new AppliationCenterDao(context);
    }

    public LinearLayout modeOpinionLayout(String VlineVisible, FieldItem item, LayoutInflater mInflater, List<TextView> list_tvsize,int com_workflow_mobileconfig_IM_enabled) {
        // 只读意见的显示和响应
//             tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19);
        LinearLayout lineView = new LinearLayout(
                HtmitechApplication.instance());
        lineView.setOrientation(LinearLayout.VERTICAL);            //设置垂直显示
        lineView.setGravity(Gravity.CENTER_VERTICAL);
        try{
            lineView.setBackgroundColor(SysConvertUtil.formattingH(item.getBackColor()));
        }catch (Exception e){

        }

        if (VlineVisible.equalsIgnoreCase("true"))
            lineView.setBackgroundResource(R.drawable.corners_bg_white_press_stroke);    //设置显示背景

        for (int i = 0; i < item.opintions.size(); i++) {        //查看多条意见
            LinearLayout layout = (LinearLayout) mInflater.inflate( //填充意见布局
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

            /**
             * add by heyang
             * date 2016-7-20
             * 让字垂直居中
             */
            name.setGravity(Gravity.CENTER_VERTICAL);
            text.setGravity(Gravity.CENTER_VERTICAL);
            username.setGravity(Gravity.CENTER_VERTICAL);
            savetime.setGravity(Gravity.CENTER_VERTICAL);

            boolean isNameVisible = item.isNameVisible();            //判断名称是否显示
            if (i == 0 && isNameVisible) {                            //如果是第一个名称 并且显示
                String strName = item.getBeforeNameString()
                        + item.getName() + item.getEndNameString(); // 拼接显示名字和结束符号
                String split = item.getSplitString();                //拿到分割符号
                strName = strName + split;
                name.setVisibility(View.VISIBLE);
                name.setText(strName);
                text.setText(item.opintions.get(i).opinionText.replace("\\\\r\\\\n", "\r\n"));    //
                username.setText(item.opintions.get(i).userName);
                if(com_workflow_mobileconfig_IM_enabled != 0&&mAppliationCenterDao.isEmiUser(item.opintions.get(i).UserID)){
                    String LoginName = mAppliationCenterDao.getLoginName(item.opintions.get(i).UserID);
                    if(LoginName.equalsIgnoreCase("admin")||LoginName.equalsIgnoreCase(MXAPI.getInstance(context)
                            .currentUser().getLoginName())){
                    }else {
                        username.setTextColor(context.getResources().getColor(R.color.form_bg_user));
                        username.setOnClickListener(new PersonOnClick(
                                mAppliationCenterDao.getLoginName(item.opintions.get(i).UserID)));
                    }

                }

                savetime.setText(item.opintions.get(i).saveTime);
            } else {
                name.setVisibility(View.GONE);                        //否则不显示名字
                text.setText(item.opintions.get(i).opinionText.replace("\\\\r\\\\n", "\r\n"));
                username.setText(item.opintions.get(i).userName);
                if(com_workflow_mobileconfig_IM_enabled != 0&&mAppliationCenterDao.isEmiUser(item.opintions.get(i).UserID)){
                    String LoginName = mAppliationCenterDao.getLoginName(item.opintions.get(i).UserID);
                    if(LoginName.equalsIgnoreCase("admin")||LoginName.equalsIgnoreCase(MXAPI.getInstance(context)
                            .currentUser().getLoginName())){
                    }else {
                        username.setTextColor(context.getResources().getColor(R.color.form_bg_user));
                        username.setOnClickListener(new PersonOnClick(
                                mAppliationCenterDao.getLoginName(item.opintions.get(i).UserID)));
                    }

                }

                savetime.setText(item.opintions.get(i).saveTime);
            }


//            name.measure(0,0);
//            text.measure(0,0);
//            username.measure(0,0);
//            savetime.measure(0,0);
//            int height=name.getMeasuredHeight()+text.getMeasuredHeight()+username.getMeasuredHeight()+savetime.getMeasuredHeight();
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 1);
            params.gravity = Gravity.CENTER_VERTICAL;
            lineView.addView(layout, params);                                    // 只读显示多个意见字段
        }
        lineView.setBackgroundResource(R.drawable.corners_bg_grey_press_stroke);
        return lineView;                                                            //传回意见表格
    }



    public LinearLayout modeOpinionLayoutCommonForm(boolean VlineVisible, Fielditems item, LayoutInflater mInflater, List<TextView> list_tvsize, int com_workflow_mobileconfig_IM_enabled) {
        // 只读意见的显示和响应
//             tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19);
        LinearLayout lineView = new LinearLayout(
                HtmitechApplication.instance());
        lineView.setOrientation(LinearLayout.VERTICAL);            //设置垂直显示
        lineView.setGravity(Gravity.CENTER_VERTICAL);
        try{
            lineView.setBackgroundColor(SysConvertUtil.formattingH(item.getFiled_backcolor()));
        }catch (Exception e){

        }

        if (VlineVisible==true)
            lineView.setBackgroundResource(R.drawable.corners_bg_white_press_stroke);    //设置显示背景

        for (int i = 0; i < item.getOpintions().size(); i++) {        //查看多条意见
            LinearLayout layout = (LinearLayout) mInflater.inflate( //填充意见布局
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

            /**
             * add by heyang
             * date 2016-7-20
             * 让字垂直居中
             */
            name.setGravity(Gravity.CENTER_VERTICAL);
            text.setGravity(Gravity.CENTER_VERTICAL);
            username.setGravity(Gravity.CENTER_VERTICAL);
            savetime.setGravity(Gravity.CENTER_VERTICAL);

            boolean isNameVisible = item.isName_visible();            //判断名称是否显示
            if (i == 0 && isNameVisible) {                            //如果是第一个名称 并且显示
                String strName = item.getBefore_name_str()
                        + item.getName() + item.getEnd_name_str(); // 拼接显示名字和结束符号
                String split = item.getSplit_str();                //拿到分割符号
                strName = strName + split;
                name.setVisibility(View.VISIBLE);
                name.setText(strName);
                text.setText(item.getOpintions().get(i).opinion_text.replace("\\\\r\\\\n", "\r\n"));    //
                username.setText(item.getOpintions().get(i).user_name);
                if(com_workflow_mobileconfig_IM_enabled != 0&&mAppliationCenterDao.isEmiUser(item.getOpintions().get(i).user_id)){
                    String LoginName = mAppliationCenterDao.getLoginName(item.getOpintions().get(i).user_id);
                    if(LoginName.equalsIgnoreCase("admin")||LoginName.equalsIgnoreCase(MXAPI.getInstance(context)
                            .currentUser().getLoginName())){
                    }else {
                        username.setTextColor(context.getResources().getColor(R.color.form_bg_user));
                        username.setOnClickListener(new PersonOnClick(
                                mAppliationCenterDao.getLoginName(item.getOpintions().get(i).user_id)));
                    }

                }

                savetime.setText(item.getOpintions().get(i).save_time);
            } else {
                name.setVisibility(View.GONE);                        //否则不显示名字
                text.setText(item.getOpintions().get(i).opinion_text.replace("\\\\r\\\\n", "\r\n"));
                username.setText(item.getOpintions().get(i).user_name);
                if(com_workflow_mobileconfig_IM_enabled != 0&&mAppliationCenterDao.isEmiUser(item.getOpintions().get(i).user_id)){
                    String LoginName = mAppliationCenterDao.getLoginName(item.getOpintions().get(i).user_id);
                    if(LoginName.equalsIgnoreCase("admin")||LoginName.equalsIgnoreCase(MXAPI.getInstance(context)
                            .currentUser().getLoginName())){
                    }else {
                        username.setTextColor(context.getResources().getColor(R.color.form_bg_user));
                        username.setOnClickListener(new PersonOnClick(
                                mAppliationCenterDao.getLoginName(item.getOpintions().get(i).user_id)));
                    }

                }
                savetime.setText(item.getOpintions().get(i).save_time);
            }


//            name.measure(0,0);
//            text.measure(0,0);
//            username.measure(0,0);
//            savetime.measure(0,0);
//            int height=name.getMeasuredHeight()+text.getMeasuredHeight()+username.getMeasuredHeight()+savetime.getMeasuredHeight();
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 1);
            params.gravity = Gravity.CENTER_VERTICAL;
            lineView.addView(layout, params);                                    // 只读显示多个意见字段
        }
        lineView.setBackgroundResource(R.drawable.corners_bg_grey_press_stroke);
        return lineView;                                                            //传回意见表格
    }


    public class PersonOnClick implements View.OnClickListener {

        private String userName;

        public PersonOnClick(String userName) {
            // TODO Auto-generated constructor stub
            this.userName = userName;
        }

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            if (userName != null) {
                if (userName.equalsIgnoreCase("admin")) {
                    return;
                } else if (userName.equalsIgnoreCase(MXAPI.getInstance(context)
                        .currentUser().getLoginName())) {
                    return;
                } else {

                    Log.d("FlowFragment", "发起聊天，对：" + userName);
                    String[] loginNames = new String[]{userName};
                    // TODO Auto-generated method stub
                    MXAPI.getInstance(context).chat(loginNames,
                            new MXApiCallback() {
                                @Override
                                public void onLoading() {
                                    // TODO Auto-generated method stub

                                }

                                @Override
                                public void onFail(MXError arg0) {
                                    // TODO Auto-generated method stub

                                }

                                @Override
                                public void onSuccess() {
                                    // TODO Auto-generated method stub

                                }
                            });
                }
            }
        }

    }

}
