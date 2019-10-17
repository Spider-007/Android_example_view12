package com.htmitech.emportal.ui.formConfig;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.htmitech.api.BookInit;
import com.htmitech.domain.SYS_Department;
import com.htmitech.domain.SYS_User;
import com.htmitech.emportal.HtmitechApplication;
import com.htmitech.emportal.R;
import com.htmitech.emportal.entity.AuthorInfo;
import com.htmitech.emportal.entity.EditField;
import com.htmitech.photoselector.model.workflow.FieldItem;
import com.htmitech.emportal.ui.detail.DetailActivity2;
import com.htmitech.photoselector.model.Fielditems;
import com.htmitech.listener.CallCheckAllUserListener;
import com.htmitech.myEnum.ChooseSystemBook;
import com.htmitech.myEnum.ChooseTreeHierarchy;
import com.htmitech.myEnum.ChooseWay;
import com.htmitech.myEnum.ChooseWayEnum;

import java.util.ArrayList;

/**
 * Created by htrf-pc on 2016/9/8.
 */
public class RadioDepartment901_902 {

    private Context context;
    private TextView currentEditTextView;
    private CallBackDoAction mCallBackDoAction;
    public RadioDepartment901_902(Context context){
        this.context = context;
    }

    public LinearLayout departmentLayout(String VlineVisible,FieldItem item,LayoutInflater mInflater,TextView currentEditTextViewd,CallBackDoAction mCallBackDoActions){
        //912 表示选择部门返回ID  911 是返回NAME
        currentEditTextView = currentEditTextViewd;
        this.mCallBackDoAction = mCallBackDoActions;
        {
            // 5,
            // ***********************人员多选选择：id1;id2;id3**************************

            LinearLayout lineView = new LinearLayout(
                    HtmitechApplication.instance());
            lineView.setOrientation(LinearLayout.VERTICAL);
            if (VlineVisible.equalsIgnoreCase("true"))
                lineView.setBackgroundResource(R.drawable.corners_bg_white_press_stroke);

            // 2015-8-18 for DetailActivity2
            if (DetailActivity2.currentActivity)
                lineView.setEnabled(false);

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
            if (item.getAlign().equalsIgnoreCase("center")) {
                tv.setGravity(Gravity.CENTER);
            } else if (item.getAlign().equalsIgnoreCase("left")) {
                tv.setGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL);
            } else if (item.getAlign().equalsIgnoreCase("right")) {
                tv.setGravity(Gravity.RIGHT|Gravity.CENTER_VERTICAL);
            }

            if (item.isNameVisible()) {
                String strName = item.getBeforeNameString()
                        + item.getName() + item.getEndNameString();
                String split = item.getSplitString();
                strName = strName + split;
                tv.setText(strName);
                if (item.getNameColor().equalsIgnoreCase("red")) {
                    tv.setTextColor(Color.RED);
                }
            } else
                tv.setVisibility(View.GONE);

            // value处理

            TextView tvEditValue = (TextView) editValuelayout
                    .findViewById(R.id.form_fielditem_editvalue);
            tvEditValue.setVisibility(View.VISIBLE);
            tvEditValue.setText(item.getValue());
            if (item.getValueColor().equalsIgnoreCase("red")) {
                tvEditValue.setTextColor(Color.RED);
            }

            // 2015-8-18 for DetailActivity2
            if (DetailActivity2.currentActivity)
                tvEditValue.setEnabled(false);

            EditField edit = new EditField();
            edit.setFormKey(item.getFormKey());
            edit.setKey(item.getKey());
            edit.setSign(item.getSign());
            edit.setInput(item.getInput());
            edit.setMode(item.getMode());
            tvEditValue.setTag(edit);
            tvEditValue.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(final View v) {

                    currentEditTextView = (TextView) v;
                    BookInit.getInstance().setCallCheckUserListener(context, ChooseWayEnum.DEPARTMENTCHOOSE, ChooseWay.SINGLE_CHOOSE, ChooseTreeHierarchy
                            .HIERARCHY, ChooseSystemBook.ADDRESSBOOK, "选择部门", true, null, new CallCheckAllUserListener() {
                        @Override
                        public void checkAll(ArrayList<SYS_User> checkAllUser, ArrayList<SYS_Department> checkAllDepartment) {
                            if (checkAllUser != null && checkAllUser.size() != 0) {
                                ((TextView) v).setText("");
                                ArrayList<AuthorInfo> mAuthorInfoList = new ArrayList<AuthorInfo>();
                                for (SYS_User mSYS_User : checkAllUser) {
                                    AuthorInfo mAuthorInfo = new AuthorInfo();
                                    mAuthorInfo.setUserID(mSYS_User.getUserId());
                                    mAuthorInfo.setUserName(mSYS_User.getFullName());
                                    mAuthorInfoList.add(mAuthorInfo);
                                }
                                mCallBackDoAction.callHandle_doAction_hasAuthor(mAuthorInfoList,currentEditTextView);
                            } else if (checkAllDepartment != null && checkAllDepartment.size() != 0) {
                                ((TextView) v).setText("");
                                ArrayList<AuthorInfo> mAuthorInfoList = new ArrayList<AuthorInfo>();
                                for (SYS_Department mSYS_Department : checkAllDepartment) {
                                    AuthorInfo mAuthorInfo = new AuthorInfo();
                                    mAuthorInfo.setUserID(mSYS_Department.getDepartmentCode());
                                    mAuthorInfo.setUserName(mSYS_Department.getFullName());
                                    mAuthorInfoList.add(mAuthorInfo);
                                }
                                mCallBackDoAction.callHandle_doAction_hasAuthor(mAuthorInfoList,currentEditTextView);
                            }
                        }
                    });
                }
            });

            // editValuelayout.setOnClickListener(new CellOnclickLisener());
            // ImageView img = (ImageView) editValuelayout
            // .findViewById(R.id.form_fielditem_editimage);
            // img.setVisibility(View.VISIBLE);
            if (item.isMustInput()
                    && (item.getValue() == null || item.getValue().trim()
                    .length() == 0))
                tvEditValue.setBackgroundColor(context.getResources().getColor(
                        R.color.color_mustinputback));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 1);
            lineView.addView(editValuelayout, params);

            lineView.setBackgroundResource(R.drawable.corners_bg_grey_press_stroke);
            return lineView;

        }

    }

    public LinearLayout departmentLayoutCommonForm(boolean VlineVisible, Fielditems item, LayoutInflater mInflater, TextView currentEditTextViewd, CallBackDoAction mCallBackDoActions){
        //912 表示选择部门返回ID  911 是返回NAME
        currentEditTextView = currentEditTextViewd;
        this.mCallBackDoAction = mCallBackDoActions;
        {
            // 5,
            // ***********************人员多选选择：id1;id2;id3**************************

            LinearLayout lineView = new LinearLayout(
                    HtmitechApplication.instance());
            lineView.setOrientation(LinearLayout.VERTICAL);
            if (VlineVisible)
                lineView.setBackgroundResource(R.drawable.corners_bg_white_press_stroke);

            // 2015-8-18 for DetailActivity2
            if (DetailActivity2.currentActivity)
                lineView.setEnabled(false);

            LinearLayout editValuelayout = null;
            if (item.isName_rn())
                editValuelayout = (LinearLayout) mInflater.inflate(
                        R.layout.layout_formdetail_cell_textview_vertical_lib,
                        null);
            else
                editValuelayout = (LinearLayout) mInflater.inflate(
                        R.layout.layout_formdetail_cell_textview_lib, null);

            // name 处理
            TextView tv = (TextView) editValuelayout
                    .findViewById(R.id.form_fielditem_text);
            if (item.getAlign().equalsIgnoreCase("center")) {
                tv.setGravity(Gravity.CENTER);
            } else if (item.getAlign().equalsIgnoreCase("left")) {
                tv.setGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL);
            } else if (item.getAlign().equalsIgnoreCase("right")) {
                tv.setGravity(Gravity.RIGHT|Gravity.CENTER_VERTICAL);
            }

            if (item.isName_visible()) {
                String strName = item.getBefore_name_str()
                        + item.getName() + item.getEnd_name_str();
                String split = item.getSplit_str();
                strName = strName + split;
                tv.setText(strName);
                if (item.getName_color().equalsIgnoreCase("red")) {
                    tv.setTextColor(Color.RED);
                }
            } else
                tv.setVisibility(View.GONE);

            // value处理

            TextView tvEditValue = (TextView) editValuelayout
                    .findViewById(R.id.form_fielditem_editvalue);
            tvEditValue.setVisibility(View.VISIBLE);
            tvEditValue.setText(item.getValue());
            if (item.getValue_color().equalsIgnoreCase("red")) {
                tvEditValue.setTextColor(Color.RED);
            }

            // 2015-8-18 for DetailActivity2
            if (DetailActivity2.currentActivity)
                tvEditValue.setEnabled(false);

            EditField edit = new EditField();
            edit.setFormKey(item.getFormkey());
            edit.setKey(item.getKey());
            edit.setSign(item.getValue());
            edit.setInput(item.getInput_type());
            edit.setMode(item.getMode());
            tvEditValue.setTag(edit);
            tvEditValue.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(final View v) {

                    currentEditTextView = (TextView) v;
                    BookInit.getInstance().setCallCheckUserListener(context, ChooseWayEnum.DEPARTMENTCHOOSE, ChooseWay.SINGLE_CHOOSE, ChooseTreeHierarchy
                            .HIERARCHY, ChooseSystemBook.ADDRESSBOOK, "选择部门", true, null, new CallCheckAllUserListener() {
                        @Override
                        public void checkAll(ArrayList<SYS_User> checkAllUser, ArrayList<SYS_Department> checkAllDepartment) {
                            if (checkAllUser != null && checkAllUser.size() != 0) {
                                ((TextView) v).setText("");
                                ArrayList<AuthorInfo> mAuthorInfoList = new ArrayList<AuthorInfo>();
                                for (SYS_User mSYS_User : checkAllUser) {
                                    AuthorInfo mAuthorInfo = new AuthorInfo();
                                    mAuthorInfo.setUserID(mSYS_User.getUserId());
                                    mAuthorInfo.setUserName(mSYS_User.getFullName());
                                    mAuthorInfoList.add(mAuthorInfo);
                                }
                                mCallBackDoAction.callHandle_doAction_hasAuthor(mAuthorInfoList,currentEditTextView);
                            } else if (checkAllDepartment != null && checkAllDepartment.size() != 0) {
                                ((TextView) v).setText("");
                                ArrayList<AuthorInfo> mAuthorInfoList = new ArrayList<AuthorInfo>();
                                for (SYS_Department mSYS_Department : checkAllDepartment) {
                                    AuthorInfo mAuthorInfo = new AuthorInfo();
                                    mAuthorInfo.setUserID(mSYS_Department.getDepartmentCode());
                                    mAuthorInfo.setUserName(mSYS_Department.getFullName());
                                    mAuthorInfoList.add(mAuthorInfo);
                                }
                                mCallBackDoAction.callHandle_doAction_hasAuthor(mAuthorInfoList,currentEditTextView);
                            }
                        }
                    });
                }
            });

            // editValuelayout.setOnClickListener(new CellOnclickLisener());
            // ImageView img = (ImageView) editValuelayout
            // .findViewById(R.id.form_fielditem_editimage);
            // img.setVisibility(View.VISIBLE);
            if (item.isMustinput()
                    && (item.getValue() == null || item.getValue().trim()
                    .length() == 0))
                tvEditValue.setBackgroundColor(context.getResources().getColor(
                        R.color.color_mustinputback));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 1);
            lineView.addView(editValuelayout, params);

            lineView.setBackgroundResource(R.drawable.corners_bg_grey_press_stroke);
            return lineView;

        }

    }
}
