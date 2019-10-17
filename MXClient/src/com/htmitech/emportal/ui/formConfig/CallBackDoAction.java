package com.htmitech.emportal.ui.formConfig;

import android.widget.TextView;

import com.htmitech.emportal.entity.AuthorInfo;

import java.util.ArrayList;

/**
 * Created by htrf-pc on 2016/9/6.
 */
public interface CallBackDoAction {
    public void callHandle_doAction_hasAuthor(ArrayList<AuthorInfo> AuthorInfoTemp,TextView currentEditTextView);
}
