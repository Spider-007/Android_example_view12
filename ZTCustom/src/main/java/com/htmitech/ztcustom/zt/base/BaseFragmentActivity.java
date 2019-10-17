package com.htmitech.ztcustom.zt.base;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.dialog.CustomProgressDialog;


public class BaseFragmentActivity extends cn.feng.skin.manager.base.BaseFragmentActivity {
	CustomProgressDialog progressDialog = null;
	/**
	 * 初始化prodialog
	 * 
	 * @param mContext
	 */
	public void showProgressDialog(Context mContext) {
		if (progressDialog == null) {
			progressDialog = CustomProgressDialog.createDialog(this);
			// progressDialog.setMessage("正在加载中...");
		}
		progressDialog.setCanceledOnTouchOutside(false); // 不消失 可以用返回键
		progressDialog.setCancelable(false);// 不可以用“返回键”取消
		progressDialog.show();
	}

	public void dimssDialog() {
		if (progressDialog != null && progressDialog.isShowing()) {
			try {
				progressDialog.dismiss();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
		overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
	}
}
