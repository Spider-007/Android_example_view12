package com.htmitech.emportal.ui.widget;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;

import com.htmitech.emportal.R;

public class MyAlertDialogFragment extends DialogFragment {
    private static DialogCancelListener cancelListener;
    private static DialogConfirmListener confirmListener;
    private static int bitmapDrawable;

    private String confirmBtnStr = "关闭";
    private String cancelBtnStr = "";
    private static String titles;
    public static MyAlertDialogFragment newInstance(String title, int iconDrawable, DialogCancelListener cancel,
            DialogConfirmListener confirm,boolean choice) {
        MyAlertDialogFragment frag = new MyAlertDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        cancelListener = cancel;
        titles = title;
        confirmListener = confirm;
        bitmapDrawable = iconDrawable;
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String title = getArguments().getString("title");
        MyMessageDialog mDialog =
                new MyMessageDialog(this.getActivity(), R.style.mydialog, confirmListener, cancelListener);
//        mDialog.setBtnConfirm("关闭");
        mDialog.setViewText(bitmapDrawable, title);
        if(!TextUtils.isEmpty(confirmBtnStr) && !TextUtils.isEmpty(cancelBtnStr)){
            mDialog.setButtonText(confirmBtnStr, cancelBtnStr);
        }
        return mDialog;
    }

    public void setButtonText(String confirmBtnStr, String cancelBtnStr) {
        this.confirmBtnStr = confirmBtnStr;
        this.cancelBtnStr = cancelBtnStr;
    }
}
