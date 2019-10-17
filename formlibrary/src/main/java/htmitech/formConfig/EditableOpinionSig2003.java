package htmitech.formConfig;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;



import java.util.List;

import htmitech.com.componentlibrary.api.ComponentInit;
import htmitech.com.componentlibrary.content.ConcreteLogin;
import htmitech.com.componentlibrary.entity.DocResultInfo;
import htmitech.com.componentlibrary.entity.EditField;
import htmitech.com.componentlibrary.entity.FieldItem;
import htmitech.com.componentlibrary.unit.DrawableUtils;
import htmitech.com.componentlibrary.unit.SysConvertUtil;
import htmitech.com.formlibrary.R;
import htmitech.com.componentlibrary.entity.EditFieldList;
import htmitech.listener.YiJianOnclickLisener;

/**
 * //1，**********************处理可编辑的签名和意见字段******************************
 * <p/>
 * <p/>
 * Created by htrf-pc on 2016/9/5.
 */
public class EditableOpinionSig2003 {
    private Context context;
    private LayoutInflater mInflater;
    private List<TextView> list_tvsize;
    private  List<RadioButton> list_rbsize;
    private  List<EditField> EditFileds;
    private YiJianOnclickLisener mYiJianOnclickLisener;
    private int com_workflow_mobileconfig_IM_enabled;
    private LinearLayout lineView;
    private String VlineVisible;
    private DocResultInfo mDocResultInfo;
    private  FieldItem item;
    public void setMustInputLoacal(){
        lineView.setBackgroundResource(R.drawable.form_input_bg_must_local);
    }
    public EditableOpinionSig2003(Context context) {
        this.context = context;
    }

    //可编辑字段 输入方式为1011
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public LinearLayout editableOpinionSig(String VlineVisible, FieldItem item, LayoutInflater mInflater, List<TextView> list_tvsize, List<RadioButton> list_rbsize, List<EditField> EditFileds, YiJianOnclickLisener mYiJianOnclickLisener, int com_workflow_mobileconfig_IM_enabled,DocResultInfo mDocResultInfo) {
        this.item = item;
        this.mInflater = mInflater;
        this.list_tvsize = list_tvsize;
        this.list_rbsize = list_rbsize;
        this.EditFileds = EditFileds;
        this.mYiJianOnclickLisener = mYiJianOnclickLisener;
        this.mDocResultInfo = mDocResultInfo;
        this.com_workflow_mobileconfig_IM_enabled = com_workflow_mobileconfig_IM_enabled;
        this.VlineVisible = VlineVisible;
        //1，**********************处理可编辑的签名和意见字段******************************
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

        return setReView2003(item);

    }
    public LinearLayout setReView2003(FieldItem item){
        lineView.removeAllViews();
        if (item.opintions != null && item.opintions.size() > 0) {                //当存在多个意见的时候，先显示
            // 显示之前的意见
            for (int i = 0; i < item.opintions.size(); i++) {
                LinearLayout layout = (LinearLayout) mInflater.inflate(
                        R.layout.layout_formdetail_cell_foropion_lib, null);
                TextView name = (TextView) layout.findViewById(R.id.form_fielditem_option_name);
                list_tvsize.add(name);
                TextView text = (TextView) layout.findViewById(R.id.form_fielditem_option_text);
                list_tvsize.add(text);
                TextView username = (TextView) layout.findViewById(R.id.form_fielditem_option_username);
                list_tvsize.add(username);
                TextView savetime = (TextView) layout.findViewById(R.id.form_fielditem_option_saveTime);
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

                /**
                 * add by heyang
                 * date 2016-7-18
                 * if isMustInput is true then change backgroundclolor as "color_mustinputback"
                 */
                if (item.isMustInput()) {

                    text.setBackgroundResource(R.drawable.form_edittext_stroke);
                    //-----------------以下是给时间和名字设置背景色，这部分觉得应该是无用代码------暂时屏蔽----------
//                    name.setBackgroundColor(context.getResources().getColor(R.color.color_mustinputtouming));
//                    username.setBackgroundColor(context.getResources().getColor(R.color.color_mustinputtouming));
//                    savetime.setBackgroundColor(context.getResources().getColor(R.color.color_mustinputtouming));
//                    lineView.setBackgroundResource(R.drawable.corners_bg_mustinput);
                    //----------------------------------------------------------------------------------
                }

                boolean isNameVisible = item.isNameVisible();
                if (i == 0 && isNameVisible) {
//                    String strName = item.getBeforeNameString() + item.getName() + item.getEndNameString();
                    String strName = item.getName();
                    String split = item.getSplitString();
                    strName = strName + split;
                    name.setVisibility(View.VISIBLE);
                    name.setText(strName);
//                    if (item.getNameColor().equalsIgnoreCase("red")) {
//                        name.setTextColor(Color.RED);
//                    }
                    text.setText(item.opintions.get(i).opinionText.replace("\\\\r\\\\n", "\r\n"));
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
                } else {
                    name.setVisibility(View.GONE);
                    text.setText(item.opintions.get(i).opinionText.replace("\\\\r\\\\n", "\r\n"));
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
//                if (BookInit.getInstance().getmApcUserdefinePortal().getUsing_color_style() == 1) {
//                    username.setTextColor(context.getResources().getColor(R.color.form_bg_user));
//                }

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT, 1);

                params.gravity = Gravity.CENTER_VERTICAL;
                lineView.addView(layout, params);                            //串起来添加到意见字段
            }

        } else {                                                            //没有意见信息，那么将只显示标题
            // 仅增加name显示
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
            username.setVisibility(View.GONE);
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
            /**
             * add by heyang
             * date 2016-7-18
             * if isMustInput is true then change backgroundclolor as "color_mustinputback"
             */
            if (item.isMustInput()) {
                text.setBackgroundResource(R.drawable.form_edittext_stroke);
                //-----------------以下是给时间和名字设置背景色，这部分觉得应该是无用代码------暂时屏蔽----------
//                name.setBackgroundColor(context.getResources().getColor(R.color.color_mustinputtouming));
//                username.setBackgroundColor(context.getResources().getColor(R.color.color_mustinputtouming));
//                savetime.setBackgroundColor(context.getResources().getColor(R.color.color_mustinputtouming));
                //---------------------------------------------------------------------------------------
//                lineView.setBackgroundResource(R.drawable.corners_bg_mustinput);
            }

            if (item.isNameVisible()) {
                String strName = item.getName();
//                String strName = item.getBeforeNameString() + item.getName() + item.getEndNameString();
                String split = item.getSplitString();
                strName = strName + split;
                name.setVisibility(View.VISIBLE);
                name.setText(strName);
//                if (item.getNameColor().equalsIgnoreCase("red")) {
//                    name.setTextColor(Color.RED);
//                }
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                params.gravity = Gravity.CENTER_VERTICAL;
                lineView.addView(layout, params);                            //只实例出名字添加到 容器中
            }
        }


        // //////////////显示签字或意见结果
        LinearLayout layout3 = null;
        if (item.isNameRN())
            layout3 = (LinearLayout) mInflater.inflate(
                    R.layout.layout_formdetail_cell_vertical_lib, null);
        else
            layout3 = (LinearLayout) mInflater.inflate(
                    R.layout.layout_formdetail_cell_lib, null);

        if (VlineVisible.equalsIgnoreCase("true")) {
            layout3.setBackgroundResource(R.drawable.corners_bg_white_press_stroke);
        } else {
            layout3.setBackgroundResource(R.drawable.corners_bg_grey_press_stroke);
        }

        final TextView tv = (TextView) layout3.findViewById(R.id.form_fielditem_text);


        /**
         * add by heyang
         * date 2016-7-18
         * if mustInput is true then change the backcolor as "color_mustinputback"
         */
        if (item.isMustInput()) {//必填字段标黄
//            tv.setBackgroundColor(context.getResources().getColor(R.color.color_mustinputtouming));
            tv.setBackgroundResource(R.drawable.form_edittext_stroke);
        }
        list_tvsize.add(tv);
        if (item.getAlign().equalsIgnoreCase("center")) {
            tv.setGravity(Gravity.CENTER);
        } else if (item.getAlign().equalsIgnoreCase("left")) {
            tv.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        } else if (item.getAlign().equalsIgnoreCase("right")) {
            tv.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        }


        //---点选签名
        /**
         * 2016 4 21 号修改 用于单击输入签名
         */
        final String name =  ComponentInit.getInstance()                                    //单击布局拿到当前用户
                .getOAUserName();
        LinearLayout opionlayout1 = (LinearLayout) mInflater.inflate(            //构建细节布局 现在只是宽高20dp的图片
                R.layout.layout_formdetail_cell_qianmingyijian_lib, null);
        if (item.isMustInput() && (item.getValue() == null || item.getValue().trim().indexOf(name) == -1))//必填字段标黄
            lineView.setBackgroundResource(R.drawable.corners_bg_mustinput);
        RadioButton rQianming = (RadioButton) opionlayout1
                .findViewById(R.id.radioButtonQianming);
        list_rbsize.add(rQianming);

        /**
         * add by heyang
         * date 2016-7-20
         * 让字垂直居中
         */

        rQianming.setGravity(Gravity.CENTER_VERTICAL);

        EditField edit1 = new EditField();                                        //选项布局建立tag
        edit1.setKey(item.getKey());
//        edit1.setSign(item.getSign());
        edit1.setInput(item.getInput());
        edit1.setMode(item.getMode());
        edit1.setFormKey(item.getFormKey());
        edit1.setIsExt(item.isExt());
        rQianming.setTag(edit1);

        // 2015-8-18 for DetailActivity2
                /*if (DetailActivity2.currentActivity)
                    opionlayout.setEnabled(false);*/


        rQianming.setOnClickListener(new OpionlayoutClickListener(EditFileds, name, tv));


        //---点选意见 ----------

        RadioButton rYijian = (RadioButton) opionlayout1
                .findViewById(R.id.radioButtonYijian);
        list_rbsize.add(rYijian);

        /**
         * add by heyang
         * date 2016-7-20
         * 让字垂直居中
         */

        rYijian.setGravity(Gravity.CENTER_VERTICAL);

        /**
         * add by heyang
         * date 2016-7-18
         * if mustInput is true then change the backcolor as "color_mustinputback"
         */
        if (item.isMustInput()) {//必填字段标黄
            rYijian.setBackgroundColor(context.getResources().getColor(R.color.color_mustinputtouming));
            opionlayout1.setBackgroundResource(R.drawable.corners_bg_mustinput);
        }
        EditField edit = new EditField();                                    //实例化可编辑字段数组
        edit.setKey(item.getKey());                                            //存key
//        edit.setSign(item.getSign());
        edit.setInput(item.getInput());
        edit.setMode(item.getMode());
        edit.setValue(item.getValue());
        edit.setFormKey(item.getFormKey());
        if (item.getValue().contains(name)) {
            edit.tempValue = name;
        }
        edit.ShowView = tv;

        rYijian.setTag(edit);                                            //利用布局视图的tag 存住该视图的编辑项
        rYijian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mYiJianOnclickLisener.onClick(v);
            }
        });


        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        params1.gravity = Gravity.CENTER_VERTICAL;
        lineView.addView(opionlayout1, params1);
        lineView.addView(layout3, params1);

        if (item.isMustInput()) {//必填字段标黄
            lineView.setBackgroundResource(R.drawable.form_input_bg_must);
        }

        return lineView;
    }


    public class PersonOnClick implements View.OnClickListener {

        private String userID;

        public PersonOnClick(String sUserID) {
            // TODO Auto-generated constructor stub
            this.userID = sUserID;
        }

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            ConcreteLogin mCpmcreteLogin = ConcreteLogin.getInstance();
            mCpmcreteLogin.chatMX(context,userID);
//            if (userID.equalsIgnoreCase("admin")) {
//                return;
//            } else if (userID.equalsIgnoreCase(MXAPI.getInstance(context)
//                    .currentUser().getLoginName())) {
//                return;
//            } else {
//
//                Log.d("FlowFragment", "发起聊天，对：" + userID);
//                String[] loginNames = new String[]{userID};
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


    public class OpionlayoutClickListener implements View.OnClickListener {

        private List<EditField> EditFileds;
        private String name;
        private TextView tv;

        public OpionlayoutClickListener(List<EditField> EditFileds, String name, TextView tv) {
            this.EditFileds = EditFileds;
            this.name = name;
            this.tv = tv;
        }


        @Override
        public void onClick(View v) {
            if (name != null && !"".equals(name)) {
                if (item.isMustInput()){
                    lineView.setBackgroundResource(R.drawable.form_input_bg_must);
                }
                tv.setText(name);

                // TODO Auto-generated method stub
                // 1,将已经编辑的字段和值放入到办理是需要回发的EditField[] 中

                String strValue = "";                                        //判断有没有签名图片来决定会发的串
                String tmp = ComponentInit.getInstance().getAttribute1();
                // 有签名
                if (null != tmp && !tmp.equals("null")) {
                    strValue = ComponentInit.getInstance().getEMPUserID() + "#" + ComponentInit.getInstance().getAttribute1() + "#1";
                } else {
                    strValue = ComponentInit.getInstance().getEMPUserID() + "#" + ComponentInit.getInstance().getEMPUserName() + "(" + ComponentInit.getInstance().getThirdDepartmentName() + ")#2";
                }

                EditField edit = (EditField) v.getTag();                //回发控件的处理流程 ，从控件中拿出可编辑结构
                edit.setValue(" ");
                edit.tempValue = " ";
                if (EditFileds != null && EditFileds.size() == 0)        //添加到可编辑数组中
                    EditFileds.add(edit);
                else {
                    boolean hasfind = false;
                    for (int j = 0; j < EditFileds.size(); j++) {        //遍历可回发数组 是否有相同的key，如果有直接
                        if (EditFileds.get(j).getKey().equals(edit.getKey())) {                //setValue 否则直接存入
                            hasfind = true;
                            EditFileds.get(j).setValue(edit.getValue());
                            EditFileds.get(j).tempValue = edit.getValue();
                            break;
                        }
                    }
                    if (!hasfind)
                        EditFileds.add(edit);
                }
                Log.d("FormFragment", "EditFileds:" + EditFileds);

                // 2015-8-17
//                DocResultInfo mDocResultInfo = null;
//
//                mDocResultInfo = ((WorkFlowFormDetalActivity) context).mDocResultInfo;


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

}
