//package com.htmitech.emportal.ui.formConfig;
//
//import android.content.Context;
//import android.graphics.Color;
//import android.util.Log;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.htmitech.emportal.HtmitechApplication;
//import com.htmitech.emportal.R;
//import com.htmitech.emportal.entity.EditField;
//import com.htmitech.photoselector.model.workflow.FieldItem;
//import com.htmitech.emportal.ui.detail.DetailActivity2;
//import com.htmitech.htcommonformplugin.entity.Editfields;
//import com.htmitech.photoselector.model.Fielditems;
//import com.htmitech.htcommonformplugin.fragment.GeneralFormDetailFragment;
//import com.htmitech.proxy.dao.AppliationCenterDao;
//import com.minxing.client.util.PreferenceUtils;
//import com.minxing.client.util.SysConvertUtil;
//import com.minxing.kit.api.MXAPI;
//import com.minxing.kit.api.bean.MXError;
//import com.minxing.kit.api.callback.MXApiCallback;
//
//import java.util.List;
//
///**
// * // 1，**********************处理可编辑的意见字段******************************
// */
//public class FormOpinion2001 {
//    public Context context;
//    public AppliationCenterDao mAppliationCenterDao;
//    public FormOpinion2001(Context context) {
//        this.context = context;
//        mAppliationCenterDao = new AppliationCenterDao(context);
//    }
//    //可编辑字段 输入方式为11
//    public LinearLayout Opinion2001(String VlineVisible,FieldItem item,LayoutInflater mInflater,List<TextView> list_tvsize,FormFragment.CellOnclickLisener mCellOnclickLisener,int com_workflow_mobileconfig_IM_enabled){
//        {
//            // 1，**********************处理可编辑的意见字段******************************
//            LinearLayout lineView = new LinearLayout(                            //设置一个容器和背景及显示方式
//                    HtmitechApplication.instance());
//            lineView.setOrientation(LinearLayout.VERTICAL);
//            lineView.setGravity(Gravity.CENTER_VERTICAL);
//            lineView.setBackgroundColor(SysConvertUtil.formattingH(item.getBackColor()));
//            if (VlineVisible.equalsIgnoreCase("true"))
//                lineView.setBackgroundResource(R.drawable.corners_bg_white_press_stroke);
//
//            if (DetailActivity2.currentActivity)
//                lineView.setEnabled(false);
//
//            if (item.opintions != null && item.opintions.size() > 0) {                //当存在多个意见的时候，先显示
//                // 显示之前的意见
//                for (int i = 0; i < item.opintions.size(); i++) {
//                    LinearLayout layout = (LinearLayout) mInflater.inflate(
//                            R.layout.layout_formdetail_cell_foropion_lib, null);
//                    TextView name = (TextView) layout
//                            .findViewById(R.id.form_fielditem_option_name);
//                    list_tvsize.add(name);
//                    TextView text = (TextView) layout
//                            .findViewById(R.id.form_fielditem_option_text);
//                    list_tvsize.add(text);
//                    TextView username = (TextView) layout
//                            .findViewById(R.id.form_fielditem_option_username);
//                    list_tvsize.add(username);
//                    TextView savetime = (TextView) layout
//                            .findViewById(R.id.form_fielditem_option_saveTime);
//                    list_tvsize.add(savetime);
//
//
//                    /**
//                     * add by heyang
//                     * date 2016-7-20
//                     * 让字垂直居中
//                     */
//                    name.setGravity(Gravity.CENTER_VERTICAL);
//                    text.setGravity(Gravity.CENTER_VERTICAL);
//                    username.setGravity(Gravity.CENTER_VERTICAL);
//                    savetime.setGravity(Gravity.CENTER_VERTICAL);
//
//
//                    boolean isNameVisible = item.isNameVisible();
//                    if (i == 0 && isNameVisible) {
//                        String strName = item.getBeforeNameString()
//                                + item.getName() + item.getEndNameString();
//                        String split = item.getSplitString();
//                        strName = strName + split;
//                        name.setVisibility(View.VISIBLE);
//                        name.setText(strName);
//                        if (item.getNameColor().equalsIgnoreCase("red")) {
//                            name.setTextColor(Color.RED);
//                        }
//                        text.setText(item.opintions.get(i).opinionText.replace("\\\\r\\\\n", "\r\n"));
//                        username.setText(item.opintions.get(i).userName);
//
//                        if(com_workflow_mobileconfig_IM_enabled != 0&&mAppliationCenterDao.isEmiUser(item.opintions.get(i).UserID)){
//                            String LoginName = mAppliationCenterDao.getLoginName(item.opintions.get(i).UserID);
//                            if(LoginName.equalsIgnoreCase("admin")||LoginName.equalsIgnoreCase(MXAPI.getInstance(context)
//                                    .currentUser().getLoginName())){
//                            }else {
//                                username.setTextColor(context.getResources().getColor(R.color.form_bg_user));
//                                username.setOnClickListener(new PersonOnClick(
//                                        mAppliationCenterDao.getLoginName(item.opintions.get(i).UserID)));
//                            }
//
//                        }
//
//                        savetime.setText(item.opintions.get(i).saveTime);
//                    } else {
//                        name.setVisibility(View.GONE);
//                        text.setText(item.opintions.get(i).opinionText.replace("\\\\r\\\\n", "\r\n"));
//                        username.setText(item.opintions.get(i).userName);
//                        if(com_workflow_mobileconfig_IM_enabled != 0&&mAppliationCenterDao.isEmiUser(item.opintions.get(i).UserID)){
//                            String LoginName = mAppliationCenterDao.getLoginName(item.opintions.get(i).UserID);
//                            if(LoginName.equalsIgnoreCase("admin")||LoginName.equalsIgnoreCase(MXAPI.getInstance(context)
//                                    .currentUser().getLoginName())){
//                            }else {
//                                username.setTextColor(context.getResources().getColor(R.color.form_bg_user));
//                                username.setOnClickListener(new PersonOnClick(
//                                        mAppliationCenterDao.getLoginName(item.opintions.get(i).UserID)));
//                            }
//
//                        }
//                        savetime.setText(item.opintions.get(i).saveTime);
//                    }
//                    // 2015-8-18 for DetailActivity2
//                    if (DetailActivity2.currentActivity)
//                        username.setEnabled(false);
//
//                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                            LinearLayout.LayoutParams.MATCH_PARENT,
//                            LinearLayout.LayoutParams.WRAP_CONTENT, 1);
//                    params.gravity = Gravity.CENTER_VERTICAL;
//
//                    lineView.addView(layout, params);                            //串起来添加到意见字段
//                }
//
//            } else {                                                            //没有意见信息，那么将只显示标题
//                // 仅增加name显示
//                LinearLayout layout = (LinearLayout) mInflater.inflate(
//                        R.layout.layout_formdetail_cell_foropion_lib, null);
//                TextView name = (TextView) layout
//                        .findViewById(R.id.form_fielditem_option_name);
//                list_tvsize.add(name);
//                TextView text = (TextView) layout
//                        .findViewById(R.id.form_fielditem_option_text);
//                list_tvsize.add(text);
//                TextView username = (TextView) layout
//                        .findViewById(R.id.form_fielditem_option_username);
//                list_tvsize.add(username);
//                TextView savetime = (TextView) layout
//                        .findViewById(R.id.form_fielditem_option_saveTime);
//                list_tvsize.add(savetime);
//                text.setVisibility(View.GONE);
//                username.setVisibility(View.GONE);
//                savetime.setVisibility(View.GONE);
//
//                /**
//                 * add by heyang
//                 * date 2016-7-20
//                 * 让字垂直居中
//                 */
//
//                name.setGravity(Gravity.CENTER_VERTICAL);
//                text.setGravity(Gravity.CENTER_VERTICAL);
//                username.setGravity(Gravity.CENTER_VERTICAL);
//                savetime.setGravity(Gravity.CENTER_VERTICAL);
//
//                /**
//                 * add by heyang
//                 * date 2016-7-18
//                 * if isMustInput is true then change backgroundclolor as "color_mustinputback"
//                 */
//                if (item.isMustInput()) {
//                    name.setBackgroundColor(context.getResources().getColor(R.color.color_mustinputtouming));
//                    text.setBackgroundColor(context.getResources().getColor(R.color.color_mustinputtouming));
//                    username.setBackgroundColor(context.getResources().getColor(R.color.color_mustinputtouming));
//                    savetime.setBackgroundColor(context.getResources().getColor(R.color.color_mustinputtouming));
//                    lineView.setBackgroundResource(R.drawable.corners_bg_mustinput);
//                }
//
//                if (item.isNameVisible()) {
//                    String strName = item.getBeforeNameString()
//                            + item.getName() + item.getEndNameString();
//                    String split = item.getSplitString();
//                    strName = strName + split;
//                    name.setVisibility(View.VISIBLE);
//                    name.setText(strName);
//                    if (item.getNameColor().equalsIgnoreCase("red")) {
//                        name.setTextColor(Color.RED);
//                    }
//                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                            LinearLayout.LayoutParams.MATCH_PARENT,
//                            LinearLayout.LayoutParams.WRAP_CONTENT, 1);
//                    params.gravity = Gravity.CENTER_VERTICAL;
//                    lineView.addView(layout, params);                            //只实例出名字添加到 容器中
//                }
//            }
//            LinearLayout opionlayout = (LinearLayout) mInflater.inflate(
//                    R.layout.layout_formdetail_cell_opiontext_lib, null);            //实例化表格
//
//            TextView tvOption = (TextView) opionlayout
//                    .findViewById(R.id.form_fielditem_option);                    //新创建一个表格 包在实例里，这绝逼是新花的布局不想改逻辑
//            tvOption.setHint("请填写意见");
//            tvOption.setVisibility(View.VISIBLE);
//            ImageView iv_icon = (ImageView) opionlayout.findViewById(R.id.form_fielditem_editimage);
//            iv_icon.setVisibility(View.GONE);
//            list_tvsize.add(tvOption);
//            /**
//             * add by heyang
//             * date 2016-7-18
//             * if isMustInput is true then change backgroundclolor as "color_mustinputback"
//             */
//            if (item.isMustInput()) {
//                tvOption.setBackgroundColor(context.getResources().getColor(R.color.form_bg_must));
//            }
//
//            String name =  PreferenceUtils                                    //单击布局拿到当前用户
//                    .getOAUserName(context);
//            EditField edit = new EditField();                                    //实例化可编辑字段数组
//            edit.setKey(item.getKey());                                            //存key
//            edit.setSign(item.getSign());
//            edit.setInput(item.getInput());
//            edit.setMode(item.getMode());
//            edit.setValue(item.getValue());                                        //存value
//            edit.tempValue = item.getValue().contains(name) ? (""+item.getValue()): "";
//            edit.setFormKey(item.getFormKey());
//            tvOption.setTag(edit);                                            //利用布局视图的tag 存住该视图的编辑项
//            tvOption.setOnClickListener(mCellOnclickLisener);
////                ImageView img = (ImageView) opionlayout
////                        .findViewById(R.id.form_fielditem_editimage);
////                img.setVisibility(View.VISIBLE);                                    //图标显示
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                    LinearLayout.LayoutParams.MATCH_PARENT,
//                    LinearLayout.LayoutParams.WRAP_CONTENT, 1);
//            params.gravity = Gravity.CENTER_VERTICAL;
//            lineView.addView(opionlayout, params);
//            // 2015-8-18 for DetailActivity2
//            if (DetailActivity2.currentActivity) {
//                lineView.setEnabled(false);
//                opionlayout.setEnabled(false);
//            }
//
//            return lineView;
//
//        }
//    }
//    public class PersonOnClick implements View.OnClickListener {
//
//        private String userName;
//
//        public PersonOnClick(String userName) {
//            // TODO Auto-generated constructor stub
//            this.userName = userName;
//        }
//
//        @Override
//        public void onClick(View v) {
//            // TODO Auto-generated method stub
//            if (userName.equalsIgnoreCase("admin")) {
//                return;
//            } else if (userName.equalsIgnoreCase(MXAPI.getInstance(context)
//                    .currentUser().getLoginName())) {
//                return;
//            } else {
//                Log.d("FlowFragment", "发起聊天，对：" + userName);
//                String[] loginNames = new String[]{userName};
//                // TODO Auto-generated method stub
//                MXAPI.getInstance(context).chat(loginNames,
//                        new MXApiCallback() {
//                            @Override
//                            public void onLoading() {
//                                // TODO Auto-generated method stub
//
//                            }
//
//                            @Override
//                            public void onFail(MXError arg0) {
//                                // TODO Auto-generated method stub
//
//                            }
//
//                            @Override
//                            public void onSuccess() {
//                                // TODO Auto-generated method stub
//
//                            }
//                        });
//            }
//        }
//
//    }
//
//    //可编辑字段 输入方式为11
//    public LinearLayout Opinion2001CommonForm(boolean VlineVisible, Fielditems item, LayoutInflater mInflater, List<TextView> list_tvsize, GeneralFormDetailFragment.CellOnclickLisener mCellOnclickLisener, int com_workflow_mobileconfig_IM_enabled){
//        {
//            // 1，**********************处理可编辑的意见字段******************************
//            LinearLayout lineView = new LinearLayout(                            //设置一个容器和背景及显示方式
//                    HtmitechApplication.instance());
//            lineView.setOrientation(LinearLayout.VERTICAL);
//            lineView.setGravity(Gravity.CENTER_VERTICAL);
//            lineView.setBackgroundColor(SysConvertUtil.formattingH(item.getFiled_backcolor()));
//            if (VlineVisible)
//                lineView.setBackgroundResource(R.drawable.corners_bg_white_press_stroke);
//
//            if (DetailActivity2.currentActivity)
//                lineView.setEnabled(false);
//
//            if (item.getOpintions() != null && item.getOpintions().size() > 0) {                //当存在多个意见的时候，先显示
//                // 显示之前的意见
//                for (int i = 0; i < item.getOpintions().size(); i++) {
//                    LinearLayout layout = (LinearLayout) mInflater.inflate(
//                            R.layout.layout_formdetail_cell_foropion_lib, null);
//                    TextView name = (TextView) layout
//                            .findViewById(R.id.form_fielditem_option_name);
//                    list_tvsize.add(name);
//                    TextView text = (TextView) layout
//                            .findViewById(R.id.form_fielditem_option_text);
//                    list_tvsize.add(text);
//                    TextView username = (TextView) layout
//                            .findViewById(R.id.form_fielditem_option_username);
//                    list_tvsize.add(username);
//                    TextView savetime = (TextView) layout
//                            .findViewById(R.id.form_fielditem_option_saveTime);
//                    list_tvsize.add(savetime);
//
//
//                    /**
//                     * add by heyang
//                     * date 2016-7-20
//                     * 让字垂直居中
//                     */
//
//                    name.setGravity(Gravity.CENTER_VERTICAL);
//                    text.setGravity(Gravity.CENTER_VERTICAL);
//                    username.setGravity(Gravity.CENTER_VERTICAL);
//                    savetime.setGravity(Gravity.CENTER_VERTICAL);
//
//                    /**
//                     * add by heyang
//                     * date 2016-7-18
//                     * if isMustInput is true then change backgroundclolor as "color_mustinputback"
//                     */
//                    if (item.isMustinput()) {
//                        name.setBackgroundColor(context.getResources().getColor(R.color.color_mustinputtouming));
//                        text.setBackgroundColor(context.getResources().getColor(R.color.color_mustinputtouming));
//                        username.setBackgroundColor(context.getResources().getColor(R.color.color_mustinputtouming));
//                        savetime.setBackgroundColor(context.getResources().getColor(R.color.color_mustinputtouming));
//                        lineView.setBackgroundResource(R.drawable.corners_bg_mustinput);
//                    }
//
//                    boolean isNameVisible = item.isName_visible();
//                    if (i == 0 && isNameVisible) {
//                        String strName = item.getBefore_name_str()
//                                + item.getName() + item.getEnd_name_str();
//                        String split = item.getSplit_str();
//                        strName = strName + split;
//                        name.setVisibility(View.VISIBLE);
//                        name.setText(strName);
//                        if (item.getName_color().equalsIgnoreCase("red")) {
//                            name.setTextColor(Color.RED);
//                        }
//                        text.setText(item.getOpintions().get(i).opinion_text.replace("\\\\r\\\\n", "\r\n"));
//                        username.setText(item.getOpintions().get(i).user_name);
//                        if(com_workflow_mobileconfig_IM_enabled != 0&&mAppliationCenterDao.isEmiUser(item.getOpintions().get(i).user_id)){
//                            String LoginName = mAppliationCenterDao.getLoginName(item.getOpintions().get(i).user_id);
//                            if(LoginName.equalsIgnoreCase("admin")||LoginName.equalsIgnoreCase(MXAPI.getInstance(context)
//                                    .currentUser().getLoginName())){
//                            }else {
//                                username.setTextColor(context.getResources().getColor(R.color.form_bg_user));
//                                username.setOnClickListener(new PersonOnClick(
//                                        mAppliationCenterDao.getLoginName(item.getOpintions().get(i).user_id)));
//                            }
//
//                        }
//
//                        savetime.setText(item.getOpintions().get(i).save_time);
//                    } else {
//                        name.setVisibility(View.GONE);
//                        text.setText(item.getOpintions().get(i).opinion_text.replace("\\\\r\\\\n", "\r\n"));
//                        username.setText(item.getOpintions().get(i).user_name);
//                        if(com_workflow_mobileconfig_IM_enabled != 0&&mAppliationCenterDao.isEmiUser(item.getOpintions().get(i).user_id)){
//                            String LoginName = mAppliationCenterDao.getLoginName(item.getOpintions().get(i).user_id);
//                            if(LoginName.equalsIgnoreCase("admin")||LoginName.equalsIgnoreCase(MXAPI.getInstance(context)
//                                    .currentUser().getLoginName())){
//                            }else {
//                                username.setTextColor(context.getResources().getColor(R.color.form_bg_user));
//                                username.setOnClickListener(new PersonOnClick(
//                                        mAppliationCenterDao.getLoginName(item.getOpintions().get(i).user_id)));
//                            }
//
//                        }
//                        savetime.setText(item.getOpintions().get(i).save_time);
//                    }
//                    // 2015-8-18 for DetailActivity2
//                    if (DetailActivity2.currentActivity)
//                        username.setEnabled(false);
//
//                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                            LinearLayout.LayoutParams.MATCH_PARENT,
//                            LinearLayout.LayoutParams.WRAP_CONTENT, 1);
//                    params.gravity = Gravity.CENTER_VERTICAL;
//
//                    lineView.addView(layout, params);                            //串起来添加到意见字段
//                }
//
//            } else {                                                            //没有意见信息，那么将只显示标题
//                // 仅增加name显示
//                LinearLayout layout = (LinearLayout) mInflater.inflate(
//                        R.layout.layout_formdetail_cell_foropion_lib, null);
//                TextView name = (TextView) layout
//                        .findViewById(R.id.form_fielditem_option_name);
//                list_tvsize.add(name);
//                TextView text = (TextView) layout
//                        .findViewById(R.id.form_fielditem_option_text);
//                list_tvsize.add(text);
//                TextView username = (TextView) layout
//                        .findViewById(R.id.form_fielditem_option_username);
//                list_tvsize.add(username);
//                TextView savetime = (TextView) layout
//                        .findViewById(R.id.form_fielditem_option_saveTime);
//                list_tvsize.add(savetime);
//                text.setVisibility(View.GONE);
//                username.setVisibility(View.GONE);
//                savetime.setVisibility(View.GONE);
//
//                /**
//                 * add by heyang
//                 * date 2016-7-20
//                 * 让字垂直居中
//                 */
//
//                name.setGravity(Gravity.CENTER_VERTICAL);
//                text.setGravity(Gravity.CENTER_VERTICAL);
//                username.setGravity(Gravity.CENTER_VERTICAL);
//                savetime.setGravity(Gravity.CENTER_VERTICAL);
//
//                /**
//                 * add by heyang
//                 * date 2016-7-18
//                 * if isMustInput is true then change backgroundclolor as "color_mustinputback"
//                 */
//                if (item.isMustinput()) {
//                    name.setBackgroundColor(context.getResources().getColor(R.color.color_mustinputtouming));
//                    text.setBackgroundColor(context.getResources().getColor(R.color.color_mustinputtouming));
//                    username.setBackgroundColor(context.getResources().getColor(R.color.color_mustinputtouming));
//                    savetime.setBackgroundColor(context.getResources().getColor(R.color.color_mustinputtouming));
//                    lineView.setBackgroundResource(R.drawable.corners_bg_mustinput);
//                }
//
//                if (item.isName_visible()) {
//                    String strName = item.getBefore_name_str()
//                            + item.getName() + item.getEnd_name_str();
//                    String split = item.getSplit_str();
//                    strName = strName + split;
//                    name.setVisibility(View.VISIBLE);
//                    name.setText(strName);
//                    if (item.getName_color().equalsIgnoreCase("red")) {
//                        name.setTextColor(Color.RED);
//                    }
//                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                            LinearLayout.LayoutParams.MATCH_PARENT,
//                            LinearLayout.LayoutParams.WRAP_CONTENT, 1);
//                    params.gravity = Gravity.CENTER_VERTICAL;
//                    lineView.addView(layout, params);                            //只实例出名字添加到 容器中
//                }
//            }
//            LinearLayout opionlayout = (LinearLayout) mInflater.inflate(
//                    R.layout.layout_formdetail_cell_opiontext_lib, null);            //实例化表格
//            LinearLayout layout_yijian = (LinearLayout) opionlayout.findViewById(R.id.layout_yijian);
//            TextView tvOption = (TextView) opionlayout
//                    .findViewById(R.id.form_fielditem_option);                    //新创建一个表格 包在实例里，这绝逼是新花的布局不想改逻辑
//            tvOption.setVisibility(View.INVISIBLE);                                //占住位置 但是不显示
//            tvOption.setHint("请填写意见");
//            tvOption.setVisibility(View.VISIBLE);
//            tvOption.setTextColor(context.getResources().getColor(R.color.huise));
//            ImageView iv_icon = (ImageView) opionlayout.findViewById(R.id.form_fielditem_editimage);
//            iv_icon.setVisibility(View.GONE);
//            list_tvsize.add(tvOption);
//            /**
//             * add by heyang
//             * date 2016-7-18
//             * if isMustInput is true then change backgroundclolor as "color_mustinputback"
//             */
//            if (item.isMustinput()) {
//                tvOption.setBackgroundColor(context.getResources().getColor(R.color.color_mustinputtouming));
//                lineView.setBackgroundResource(R.drawable.corners_bg_mustinput);
//            }
//
//            String name =  PreferenceUtils                                    //单击布局拿到当前用户
//                    .getOAUserName(context);
//            Editfields edit = new Editfields();                                    //实例化可编辑字段数组
//            edit.setKey(item.getKey());                                            //存key
//            edit.setValue(item.getValue());                                        //存value
//            tvOption.setTag(edit);
//            //利用布局视图的tag 存住该视图的编辑项
//            if(mCellOnclickLisener!=null)
//                tvOption.setOnClickListener(mCellOnclickLisener);
////                ImageView img = (ImageView) opionlayout
////                        .findViewById(R.id.form_fielditem_editimage);
////                img.setVisibility(View.VISIBLE);                                    //图标显示
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                    LinearLayout.LayoutParams.MATCH_PARENT,
//                    LinearLayout.LayoutParams.WRAP_CONTENT, 1);
//            params.gravity = Gravity.CENTER_VERTICAL;
//            lineView.addView(opionlayout, params);
//            // 2015-8-18 for DetailActivity2
//            if (DetailActivity2.currentActivity) {
//                lineView.setEnabled(false);
//                opionlayout.setEnabled(false);
//            }
//
//            return lineView;
//
//        }
//    }
//
//
//
//}
