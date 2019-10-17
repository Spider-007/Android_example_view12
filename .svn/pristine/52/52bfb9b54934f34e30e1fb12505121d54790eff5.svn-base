package com.htmitech.commonx.base.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.htmitech.commonx.base.net.DownloadManager;

import java.util.HashMap;

import htmitech.com.componentlibrary.dialog.LoadingDialog;

public abstract class BaseFragment extends cn.feng.skin.manager.base.BaseFragment {

    public View mView;
    LoadingDialog mLoadingDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(getLayoutId(), container, false);
        mLoadingDialog = new LoadingDialog(getActivity());
        mLoadingDialog.setCancelable(true);
        mLoadingDialog.setCanceledOnTouchOutside(true);
        initViews();
        isVisible = getUserVisibleHint();
        return mView;
    }


    protected void runOnUiThread(Runnable action) {
        if (!isActivityAlive()) {
            return;
        }
        getActivity().runOnUiThread(action);
    }

    protected View getRootView() {
        return mView;
    }

    protected View findViewById(int id) {
        return mView.findViewById(id);
    }

    protected boolean isActivityAlive() {
        return getActivity() != null;
    }

    /** Fragment当前状态是否可见 */
    protected boolean isVisible;


    @Override
    public boolean getUserVisibleHint() {
        return super.getUserVisibleHint();
    }

    /**
	 * 获取布局id，用于setContentView。
	 *
	 * @return id
	 */
	protected abstract int getLayoutId();

	/**
	 * 初始化View。
	 */
	protected abstract void initViews();


    public void showDialog(){
        if(mLoadingDialog != null && !mLoadingDialog.isShowing()){
            mLoadingDialog.show();

        }
    }

    public void setCancelable(boolean flag){
        mLoadingDialog.setCancelable(flag);
    }

    public void dismissDialog(){
        if(mLoadingDialog != null && mLoadingDialog.isShowing()){

            mLoadingDialog.dismiss();
        }
    }

    public DownloadManager getDownloadManager() {
        return null;
    }

    //得到需要上报内容的信息—（**注 需要额外添加一个键值对，当前属于哪个fragment  例如  key:fragmentType vlaue:ofdFragment **）
    public HashMap<String, String> getSingInfoMap() {
        return null;
    }
}
