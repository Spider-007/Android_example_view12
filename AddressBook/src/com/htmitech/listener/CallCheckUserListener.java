package com.htmitech.listener;

import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.htmitech.domain.SYS_Department;
import com.htmitech.domain.SYS_User;

import java.util.ArrayList;
import java.util.List;

/**
 * 回调选择人员的接口
 */
public interface CallCheckUserListener {
	/**
	 *
	 * @param mUser
	 * @param checkAllUser
	 * @param isCheck
	 * @param currentImage
	 * @param mCb
	 * @param default_tv
	 * @param isImage
	 */

	public void checkUser(SYS_User mUser,ArrayList<SYS_User> checkAllUser,boolean isCheck,ImageView currentImage,CheckBox mCb,TextView default_tv,boolean isImage);   //人员的选择

	/**
	 * 部门选择
	 * @param crrentDepartment
	 * @param checkDepartment
	 * @param isCheck
	 * @param currentImage
	 * @param mCb
	 */
	public void checkDepartment(SYS_Department crrentDepartment,ArrayList<SYS_Department> checkDepartment,boolean isCheck,ImageView currentImage,CheckBox mCb);
}
