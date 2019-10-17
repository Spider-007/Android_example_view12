package htmitech.formConfig;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

//
//import com.htmitech.api.BookInit;
//import com.htmitech.domain.SYS_Department;
//import com.htmitech.domain.SYS_User;
//import com.htmitech.listener.CallCheckAllUserListener;
//import com.htmitech.myEnum.ChooseSystemBook;
//import com.htmitech.myEnum.ChooseTreeHierarchy;
//import com.htmitech.myEnum.ChooseWay;
//import com.htmitech.myEnum.ChooseWayEnum;

import htmitech.com.componentlibrary.api.ComponentInit;
import htmitech.com.componentlibrary.entity.EditField;
import htmitech.com.componentlibrary.entity.FieldItem;
import htmitech.com.componentlibrary.listener.ICallCheckUserListener;
import htmitech.com.componentlibrary.unit.DrawableUtils;
import htmitech.com.componentlibrary.unit.SysConvertUtil;
import htmitech.com.formlibrary.R;
import htmitech.listener.CallBackDoAction;
import htmitech.util.AccssFormKey;

/**
 * Created by htrf-pc on 2016/9/6.
 */
public class PeopleCheck611_612_613 {

    public Context context;
    private TextView currentEditTextView;
    private CallBackDoAction mCallBackDoAction;
    private LayoutInflater mInflater;
    private  LinearLayout lineView;
    private int tabStyle ;
    private TextView tvEditValue;
    public void setMustInputLoacal(){
        if(tabStyle == 0){
            tvEditValue.setBackgroundResource(R.drawable.form_input_bg_must_local);
        }else{
            tvEditValue.setBackgroundResource(R.drawable.form_input_bg_must_style_local);
        }
    }
    public PeopleCheck611_612_613(Context context) {
        this.context = context;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public LinearLayout peopleChckLayout(String VlineVisible, FieldItem item, LayoutInflater mInflater, TextView currentEditTextViewd, CallBackDoAction mCallBackDoActions,int tabStyle) {
        // 5,
        // ***********************人员多选选择：id1;id2;id3**************************
        this.currentEditTextView = currentEditTextViewd;
        this.mCallBackDoAction = mCallBackDoActions;
        this.mInflater = mInflater;
        this.tabStyle = tabStyle;
        lineView = new LinearLayout(
                context);
        lineView.setOrientation(LinearLayout.VERTICAL);
        lineView.setBackgroundColor(SysConvertUtil.formattingH(item.getBackColor()));
        if (VlineVisible.equalsIgnoreCase("true")) {
//            lineView.setBackgroundResource(R.drawable.corners_bg_white_press_stroke_v);
            lineView.setBackground(DrawableUtils.getLayerDrawable(context, item.getBackColor()));//设置显示背景
        }

       return setReView611_12_13(item);

    }

    public LinearLayout setReView611_12_13(FieldItem item){
        lineView.removeAllViews();
        LinearLayout editValuelayout = null;
        if (item.isNameRN())
            editValuelayout = (LinearLayout) mInflater.inflate(
                    R.layout.layout_formdetail_cell_textview_vertical_lib,
                    null);
        else
            editValuelayout = (LinearLayout) mInflater.inflate(
                    R.layout.layout_formdetail_cell_textview_lib, null);

        // name 处理
        TextView tv = (TextView) editValuelayout
                .findViewById(R.id.form_fielditem_text);
        if(tabStyle ==1){
            tv.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
            tv.setTextColor(Color.parseColor("#999999"));
            item.setIsSplitWithLine(0);
        }else {
            if (item.getAlign().equalsIgnoreCase("center")) {
                tv.setGravity(Gravity.CENTER);
            } else if (item.getAlign().equalsIgnoreCase("left")) {
                tv.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
            } else if (item.getAlign().equalsIgnoreCase("right")) {
                tv.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
            }
        }


        if (item.isNameVisible()) {
//            String strName = item.getBeforeNameString() + item.getName() + item.getEndNameString();
            String strName = item.getName();
            String split = item.getSplitString();
            strName = strName + split;
            tv.setText(strName);
//            if (item.getNameColor().equalsIgnoreCase("red")) {
//                tv.setTextColor(Color.RED);
//            }
        } else
            tv.setVisibility(View.GONE);

        // value处理

        tvEditValue = (TextView) editValuelayout
                .findViewById(R.id.form_fielditem_editvalue);
        tvEditValue.setVisibility(View.VISIBLE);
        tvEditValue.setText(AccssFormKey.findKeyToByValue(tvEditValue, item.getKey(), item.getValue()));
//        if (item.getValueColor().equalsIgnoreCase("red")) {
//            tvEditValue.setTextColor(Color.RED);
//        }

        EditField edit = new EditField();
        edit.setFormKey(item.getFormKey());
        edit.setKey(item.getKey());
//        edit.setSign(item.getSign());
        edit.setInput(item.getInput());
        edit.setMode(item.getMode());
        edit.setIsExt(item.isExt());
        tvEditValue.setTag(edit);
        tvEditValue.setHint("请选择人员");
        tvEditValue.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {

                currentEditTextView = (TextView) v;
                ((TextView) v).setText("");
//                ArrayList<AuthorInfo> mAuthorInfoList = ;
//                mCallBackDoAction.callHandle_doAction_hasAuthor(mAuthorInfoList, currentEditTextView);

                ComponentInit.getInstance().getmICallCheckUserListener().getCheckUserListener(currentEditTextView, ICallCheckUserListener.IChooseWay.PEOPLECHOOSE,ICallCheckUserListener.Choose.MORE_CHOOSE);
//                BookInit.getInstance().setCallCheckUserListener(context, ChooseWayEnum.PEOPLECHOOSE, ChooseWay.MORE_CHOOSE, ChooseTreeHierarchy
//                        .HIERARCHY, ChooseSystemBook.ADDRESSBOOK, "选择人员", true, null, new CallCheckAllUserListener() {
//                    @Override
//                    public void checkAll(ArrayList<SYS_User> checkAllUser, ArrayList<SYS_Department> checkAllDepartment) {
//                        if (checkAllUser != null && checkAllUser.size() != 0) {
//                            ((TextView) v).setText("");
//                            ArrayList<AuthorInfo> mAuthorInfoList = new ArrayList<AuthorInfo>();
//                            for (SYS_User mSYS_User : checkAllUser) {
//                                AuthorInfo mAuthorInfo = new AuthorInfo();
//                                mAuthorInfo.setUserId(mSYS_User.getUserId());
//                                mAuthorInfo.setUserName(mSYS_User.getFullName());
//                                mAuthorInfoList.add(mAuthorInfo);
//                            }
//                            mCallBackDoAction.callHandle_doAction_hasAuthor(mAuthorInfoList, currentEditTextView);
//                        } else if (checkAllDepartment != null && checkAllDepartment.size() != 0) {
//                            ((TextView) v).setText("");
//                            ArrayList<AuthorInfo> mAuthorInfoList = new ArrayList<AuthorInfo>();
//                            for (SYS_Department mSYS_Department : checkAllDepartment) {
//                                AuthorInfo mAuthorInfo = new AuthorInfo();
//                                mAuthorInfo.setUserId(mSYS_Department.getDepartmentCode());
//                                mAuthorInfo.setUserName(mSYS_Department.getFullName());
//                                mAuthorInfoList.add(mAuthorInfo);
//                            }
//                            mCallBackDoAction.callHandle_doAction_hasAuthor(mAuthorInfoList, currentEditTextView);
//                        }
//                    }
//                });
            }
        });
        tvEditValue.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                // TODO Auto-generated method stub

                // TODO Auto-generated method stub

                // 将值保存到编辑的字段中，并判断是否需要更新必填字段
                // 1,将已经编辑的字段和值放入到办理是需要回发的EditField[] 中

                String strValue = tvEditValue.getText().toString();

                tvEditValue.setBackgroundResource(R.drawable.text_back);

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
        // editValuelayout.setOnClickListener(new CellOnclickLisener());
         ImageView img = (ImageView) editValuelayout.findViewById(R.id.form_fielditem_editimage);
        // img.setVisibility(View.VISIBLE);
        if(tabStyle == 1){
            if(item.isMustInput() && TextUtils.isEmpty(item.getValue())){
                tvEditValue.setHint("（必填）");
                tvEditValue.setHintTextColor(Color.parseColor("#CCCCCC"));
            }else if(!item.isMustInput() && TextUtils.isEmpty(item.getValue())){
                tvEditValue.setHint("请选择人员");
                tvEditValue.setHintTextColor(Color.parseColor("#CCCCCC"));
            }
            img.setImageResource(R.drawable.header_arrow_right);
            tvEditValue.setBackgroundColor(context.getResources().getColor(R.color.white));
            img.setVisibility(View.VISIBLE);
        }else{
            img.setVisibility(View.GONE);
            if (item.isMustInput() && (item.getValue() == null || item.getValue().trim().length() == 0)) {
//            tvEditValue.setBackgroundColor(context.getResources().getColor(R.color.color_mustinputback));
                tvEditValue.setBackgroundResource(R.drawable.form_edittext_stroke);
            } else {
                tvEditValue.setBackgroundResource(R.drawable.form_edittext_stroke_nomustinput);
            }
        }

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        lineView.addView(editValuelayout, params);

        return lineView;
    }


}
