package com.htmitech.ztcustom.zt.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.htmitech.ztcustom.zt.dialog.CustomProgressDialog;


public abstract class BaseFragment extends Fragment {

    public boolean isRefresh = false;
    CustomProgressDialog progressDialog = null;
    /**
     * Fragment当前状态是否可见
     */
    protected boolean isVisible;

    /**
     * 初始化prodialog
     *
     * @param mContext
     */
    public void showProgressDialog(Context mContext) {
//        if (progressDialog == null) {
        progressDialog = CustomProgressDialog.createDialog(mContext);
        // progressDialog.setMessage("正在加载中...");
//        }
        progressDialog.setCanceledOnTouchOutside(false); // 不消失 可以用返回键
        progressDialog.setCancelable(false);// 不可以用“返回键”取消
        progressDialog.show();
    }

    public void dimssDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            try {
                progressDialog.dismiss();
                progressDialog = null;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public abstract boolean onBackPressed();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
    }

    @Override
    public void onStart() {
        super.onStart();
        // 告诉FragmentActivity，当前Fragment在栈顶
    }

    protected abstract void initView();

    protected abstract void initData();

    //setUserVisibleHint  adapter中的每个fragment切换的时候都会被调用，如果是切换到当前页，那么isVisibleToUser==true，否则为false
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    /**
     * 可见
     */
    protected void onVisible() {
        lazyLoad();
    }


    /**
     * 不可见
     */
    protected void onInvisible() {


    }

    /**
     * 延迟加载
     */
    public void lazyLoad() {

    }

    public void setRefresh() {
        isRefresh = true;
    }


}
