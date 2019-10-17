package htmitech.formConfig;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.List;

import htmitech.com.componentlibrary.api.ComponentInit;
import htmitech.com.componentlibrary.content.ConcreteLogin;
import htmitech.com.componentlibrary.entity.EditField;
import htmitech.com.componentlibrary.entity.FieldItem;
import htmitech.com.componentlibrary.unit.DrawableUtils;
import htmitech.com.componentlibrary.unit.SysConvertUtil;
import htmitech.com.formlibrary.R;
import htmitech.listener.YiJianOnclickLisener;

/**
 * // 1，**********************处理可编辑的意见字段******************************
 */
public class FormOpinion2004 {
    public Context context;
    private LayoutInflater mInflater;
    private List<TextView> list_tvsize;
    private YiJianOnclickLisener mCellOnclickLisener;
    private int com_workflow_mobileconfig_IM_enabled;
    private LinearLayout lineView;
    private TextView text;
    private TextView textup;

    public void setMustInputLoacal() {
        if (textup != null && TextUtils.isEmpty(textup.getText().toString())) {
            textup.setBackgroundResource(R.drawable.form_input_bg_must_local);
        }
        if (text != null && TextUtils.isEmpty(text.getText().toString())) {
            text.setBackgroundResource(R.drawable.form_input_bg_must_local);
        }

    }

    public FormOpinion2004(Context context) {
        this.context = context;
    }

    //可编辑字段 输入方式为11
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public LinearLayout Opinion2004(String VlineVisible, FieldItem item, LayoutInflater mInflater, List<TextView> list_tvsize, YiJianOnclickLisener mCellOnclickLisener, int com_workflow_mobileconfig_IM_enabled) {
        {
            this.mInflater = mInflater;
            this.list_tvsize = list_tvsize;
            this.mCellOnclickLisener = mCellOnclickLisener;
            this.com_workflow_mobileconfig_IM_enabled = com_workflow_mobileconfig_IM_enabled;

            // 1，**********************处理可编辑的意见字段******************************
            lineView = new LinearLayout(                            //设置一个容器和背景及显示方式
                    context);
            lineView.setOrientation(LinearLayout.VERTICAL);
            lineView.setGravity(Gravity.CENTER_VERTICAL);

            if (VlineVisible.equalsIgnoreCase("true")) {
//            lineView.setBackgroundResource(R.drawable.corners_bg_white_press_stroke_v);
                lineView.setBackground(DrawableUtils.getLayerDrawable(context, item.getBackColor()));//设置显示背景
            } else {
                lineView.setBackgroundColor(SysConvertUtil.formattingH(item.getBackColor()));
            }

            return setReView2004(item);

        }
    }

    public LinearLayout setReView2004(final FieldItem item) {
        lineView.removeAllViews();
        if (item.opintions != null && item.opintions.size() > 0) {                //当存在多个意见的时候，先显示
            // 显示之前的意见
            for (int i = 0; i < item.opintions.size(); i++) {
                LinearLayout layout = (LinearLayout) mInflater.inflate(
                        R.layout.layout_formdetail_cell_foropion2004, null);
                TextView name = (TextView) layout
                        .findViewById(R.id.form_fielditem_option_name);
                list_tvsize.add(name);
                textup = (TextView) layout
                        .findViewById(R.id.form_fielditem_option_text);
                list_tvsize.add(textup);
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
                textup.setGravity(Gravity.CENTER_VERTICAL);
                username.setGravity(Gravity.CENTER_VERTICAL);
                savetime.setGravity(Gravity.CENTER_VERTICAL);

                if (item.isMustInput()) {
                    textup.setBackgroundResource(R.drawable.form_edittext_stroke);
                    //-----------------以下是给时间和名字设置背景色，这部分觉得应该是无用代码------暂时屏蔽----------
//                        name.setBackgroundColor(context.getResources().getColor(R.color.form_bg_must));
                    //---------------------------------------------------------------------------------------
                } else {
                    textup.setBackgroundResource(R.drawable.text_back);
                }
                textup.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void onTextChanged(CharSequence s, int start,
                                              int before, int count) {
                        // TODO Auto-generated method stub

                        // TODO Auto-generated method stub

                        // 将值保存到编辑的字段中，并判断是否需要更新必填字段
                        // 1,将已经编辑的字段和值放入到办理是需要回发的EditField[] 中
                        String strValue = textup.getText().toString();

                        if (!TextUtils.isEmpty(strValue)) {
                            if (item.isMustInput()) {
                                textup.setBackgroundResource(R.drawable.form_edittext_stroke);
                                //-----------------以下是给时间和名字设置背景色，这部分觉得应该是无用代码------暂时屏蔽----------
//                        name.setBackgroundColor(context.getResources().getColor(R.color.form_bg_must));
                                //---------------------------------------------------------------------------------------
                            } else {
                                textup.setBackgroundResource(R.drawable.text_back);
                            }
                        }

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

                boolean isNameVisible = item.isNameVisible();
                if (i == 0 && isNameVisible) {
                    String strName = item.getName();
//                        String strName = item.getBeforeNameString() + item.getName() + item.getEndNameString();
                    String split = item.getSplitString();
                    strName = strName + split;
                    name.setVisibility(View.VISIBLE);
                    name.setText(strName);
//                        if (item.getNameColor().equalsIgnoreCase("red")) {
//                            name.setTextColor(Color.RED);
//                        }
                    textup.setText(item.opintions.get(i).opinionText.replace("\\\\r\\\\n", "\r\n"));
                    username.setText(item.opintions.get(i).userName);
                    savetime.setText(item.opintions.get(i).saveTime);
                    if (com_workflow_mobileconfig_IM_enabled != 0 && ComponentInit.getInstance().getmHTCallbackEMIUserListener().isEMIUser(item.opintions.get(i).UserID)) {
                        String LoginName = item.opintions.get(i).LoginName;
                        if (LoginName.equalsIgnoreCase("admin") || LoginName.equalsIgnoreCase(ComponentInit.getInstance().getLoginName(context))) {
                            username.setTextColor(context.getResources().getColor(R.color.form_bg_uncontent));
                        } else {
                            username.setTextColor(context.getResources().getColor(R.color.ht_hred_title));
                            if (ComponentInit.getInstance().getUsingColorStyle() == 1) {
                                username.setTextColor(context.getResources().getColor(R.color.form_bg_user));
                            } else if (ComponentInit.getInstance().getUsingColorStyle() == 3) {
                                username.setTextColor(context.getResources().getColor(R.color.form_bg_user_red));
                            }
                            username.setOnClickListener(new PersonOnClick(LoginName));
                        }

                    }
                } else {
                    name.setVisibility(View.GONE);
                    textup.setText(item.opintions.get(i).opinionText.replace("\\\\r\\\\n", "\r\n"));
                    username.setText(item.opintions.get(i).userName);
                    if (com_workflow_mobileconfig_IM_enabled != 0 && ComponentInit.getInstance().getmHTCallbackEMIUserListener().isEMIUser(item.opintions.get(i).UserID)) {
                        String LoginName = item.opintions.get(i).LoginName;
                        if (LoginName.equalsIgnoreCase("admin") || LoginName.equalsIgnoreCase(ComponentInit.getInstance().getLoginName(context))) {
                            username.setTextColor(context.getResources().getColor(R.color.form_bg_uncontent));
                        } else {
                            username.setTextColor(context.getResources().getColor(R.color.ht_hred_title));
                            if (ComponentInit.getInstance().getUsingColorStyle() == 1) {
                                username.setTextColor(context.getResources().getColor(R.color.form_bg_user));
                            } else if (ComponentInit.getInstance().getUsingColorStyle() == 3) {
                                username.setTextColor(context.getResources().getColor(R.color.form_bg_user_red));
                            }
                            username.setOnClickListener(new PersonOnClick(LoginName));
                        }

                    }
                    savetime.setText(item.opintions.get(i).saveTime);
                }

//                    if (BookInit.getInstance().getmApcUserdefinePortal().getUsing_color_style() == 1) {
//                        username.setTextColor(context.getResources().getColor(R.color.form_bg_user));
//                    }

                String name1 = ComponentInit.getInstance()                                    //单击布局拿到当前用户
                        .getOAUserName();
                EditField edit = new EditField();                                    //实例化可编辑字段数组
                edit.setKey(item.getKey());                                            //存key
//                    edit.setSign(item.getSign());
                edit.setInput(item.getInput());
                edit.setMode(item.getMode());
                edit.setIsExt(item.isExt());
                edit.setValue(item.getValue());                                        //存value
                edit.tempValue = item.getValue().contains(name1) ? ("" + item.getValue()) : "";
                edit.setFormKey(item.getFormKey());
                textup.setTag(edit);                                            //利用布局视图的tag 存住该视图的编辑项
                textup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mCellOnclickLisener.onClick(v);
                    }
                });
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                params.gravity = Gravity.CENTER_VERTICAL;
                lineView.addView(layout, params);                            //串起来添加到意见字段
            }

        } else {                                                            //没有意见信息，那么将只显示标题
            // 显示可输入的框
            LinearLayout layout = (LinearLayout) mInflater.inflate(
                    R.layout.layout_formdetail_cell_foropion2004, null);
            TextView name = (TextView) layout
                    .findViewById(R.id.form_fielditem_option_name);
            list_tvsize.add(name);
            text = (TextView) layout
                    .findViewById(R.id.form_fielditem_option_text);
            list_tvsize.add(text);
            TextView username = (TextView) layout
                    .findViewById(R.id.form_fielditem_option_username);
            list_tvsize.add(username);
            TextView savetime = (TextView) layout
                    .findViewById(R.id.form_fielditem_option_saveTime);
            list_tvsize.add(savetime);
            text.setVisibility(View.VISIBLE);
            username.setVisibility(View.GONE);
            savetime.setVisibility(View.GONE);
            name.setVisibility(View.GONE);


            /**
             * add by heyang
             * date 2016-7-20
             * 让字垂直居中
             */

            name.setGravity(Gravity.CENTER_VERTICAL);
            text.setGravity(Gravity.CENTER_VERTICAL);
            username.setGravity(Gravity.CENTER_VERTICAL);
            savetime.setGravity(Gravity.CENTER_VERTICAL);

            /**
             * add by heyang
             * date 2016-7-18
             * if isMustInput is true then change backgroundclolor as "color_mustinputback"
             */
            if (item.isMustInput()) {
                text.setBackgroundResource(R.drawable.form_edittext_stroke);
                //-----------------以下是给时间和名字设置背景色，这部分觉得应该是无用代码------暂时屏蔽----------
//                    name.setBackgroundColor(context.getResources().getColor(R.color.form_bg_must));
                //---------------------------------------------------------------------------------------
            } else {
                text.setBackgroundResource(R.drawable.form_edittext_stroke_nomustinput);
            }
            text.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {
                    // TODO Auto-generated method stub

                    // TODO Auto-generated method stub

                    // 将值保存到编辑的字段中，并判断是否需要更新必填字段
                    // 1,将已经编辑的字段和值放入到办理是需要回发的EditField[] 中
                    String strValue = text.getText().toString();

                    if (!TextUtils.isEmpty(strValue)) {
                        if (item.isMustInput()) {
                            text.setBackgroundResource(R.drawable.form_edittext_stroke);
                            //-----------------以下是给时间和名字设置背景色，这部分觉得应该是无用代码------暂时屏蔽----------
//                    name.setBackgroundColor(context.getResources().getColor(R.color.form_bg_must));
                            //---------------------------------------------------------------------------------------
                        } else {
                            text.setBackgroundResource(R.drawable.form_edittext_stroke_nomustinput);
                        }
                    }

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
            if (item.isNameVisible()) {
                String strName = item.getName();
//                    String strName = item.getBeforeNameString() + item.getName() + item.getEndNameString();
                String split = item.getSplitString();
                strName = strName + split;
                name.setVisibility(View.VISIBLE);
                name.setText(strName);
                name.setVisibility(View.GONE);
//                    if (item.getNameColor().equalsIgnoreCase("red")) {
//                        name.setTextColor(Color.RED);
//                    }

                String name1 = ComponentInit.getInstance()                                    //单击布局拿到当前用户
                        .getOAUserName();
                EditField edit = new EditField();                                    //实例化可编辑字段数组
                edit.setKey(item.getKey());                                            //存key
//                    edit.setSign(item.getSign());
                edit.setInput(item.getInput());
                edit.setIsExt(item.isExt());
                edit.setMode(item.getMode());
                edit.setValue(item.getValue());                                        //存value
                edit.tempValue = item.getValue().contains(name1) ? ("" + item.getValue()) : "";
                edit.setFormKey(item.getFormKey());
                text.setTag(edit);                                            //利用布局视图的tag 存住该视图的编辑项
                text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mCellOnclickLisener.onClick(v);
                    }
                });

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                params.gravity = Gravity.CENTER_VERTICAL;
                lineView.addView(layout, params);                            //只实例出名字添加到 容器中
            } else {
                String strName = item.getName();
//                    String strName = item.getBeforeNameString() + item.getName() + item.getEndNameString();
                String split = item.getSplitString();
                strName = strName + split;
                name.setVisibility(View.VISIBLE);
                name.setText(strName);
                name.setVisibility(View.GONE);
//                    if (item.getNameColor().equalsIgnoreCase("red")) {
//                        name.setTextColor(Color.RED);
//                    }
                String name1 = ComponentInit.getInstance()                                    //单击布局拿到当前用户
                        .getOAUserName();
                EditField edit = new EditField();                                    //实例化可编辑字段数组
                edit.setKey(item.getKey());                                            //存key
//                    edit.setSign(item.getSign());
                edit.setInput(item.getInput());
                edit.setMode(item.getMode());
                edit.setIsExt(item.isExt());
                edit.setValue(item.getValue());                                        //存value
                edit.tempValue = item.getValue().contains(name1) ? ("" + item.getValue()) : "";
                edit.setFormKey(item.getFormKey());
                text.setTag(edit);                                     //利用布局视图的tag 存住该视图的编辑项
                text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mCellOnclickLisener.onClick(v);
                    }
                });
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                params.gravity = Gravity.CENTER_VERTICAL;

                lineView.addView(layout, params);
            }
        }
//            LinearLayout opionlayout = (LinearLayout) mInflater.inflate(
//                    R.layout.layout_formdetail_cell_opiontext, null);            //实例化表格
//
//            TextView tvOption = (TextView) opionlayout
//                    .findViewById(R.id.form_fielditem_option);                    //新创建一个表格 包在实例里，这绝逼是新花的布局不想改逻辑
//            tvOption.setVisibility(View.INVISIBLE);                                //占住位置 但是不显示
//            tvOption.setHint("请填写意见");
//            tvOption.setVisibility(View.VISIBLE);
//            tvOption.setTextColor(context.getResources().getColor(R.color.huise));
//            ImageView iv_icon = (ImageView) opionlayout.findViewById(R.id.form_fielditem_editimage);
//            iv_icon.setVisibility(View.GONE);
//            list_tvsize.add(tvOption);
        /**
         * add by heyang
         * date 2016-7-18
         * if isMustInput is true then change backgroundclolor as "color_mustinputback"
         */
//            if (item.isMustInput()) {
//                tvOption.setBackgroundColor(context.getResources().getColor(R.color.color_mustinputtouming));
//                lineView.setBackgroundResource(R.drawable.corners_bg_mustinput);
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
//            opionlayout.setTag(edit);                                            //利用布局视图的tag 存住该视图的编辑项
//            opionlayout.setOnClickListener(mCellOnclickLisener);
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

        return lineView;
    }

    public class PersonOnClick implements View.OnClickListener {

        private String user_name;

        public PersonOnClick(String user_name) {
            // TODO Auto-generated constructor stub
            this.user_name = user_name;
        }

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            ConcreteLogin mCpmcreteLogin = ConcreteLogin.getInstance();
            mCpmcreteLogin.chatMX(context, user_name);
//            if (user_name.equalsIgnoreCase("admin")) {
//                return;
//            } else if (user_name.equalsIgnoreCase(MXAPI.getInstance(context)
//                    .currentUser().getLoginName())) {
//                return;
//            } else {
//
//                Log.d("FlowFragment", "发起聊天，对：" + user_name);
//                String[] loginNames = new String[]{user_name};
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
        }

    }

}
