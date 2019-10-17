package com.htmitech.listener;

import android.content.Context;
import android.widget.EditText;

import com.htmitech.domain.OrgUser;
import com.htmitech.domain.SYS_User;
import com.htmitech.domain.T_UserRelationship;

/**
 * 提供主页面回调的接口
 * @author htrf-pc
 *
 */

public interface CallbackMX {
	//发送消息
	public void callbackSendMessage(String userID);
	//讨论组
	public void callbackGroups();
	//功能号
	public void callbackFunction();
	//特别关注 list
	public void callbackSpecialFocus();
	//特别关注 按钮
	public void callbackSpecialFocusButton();
	//添加按钮
	public void callAddBook();
	//点击返回按钮
	public void callBackMain();
	//点击个人信息
	public void callUserMeesageMain();
	//关闭个人信息
	public void closeDrawer();
	//功能号
	public void callFunction();
	//添加常用联系人 回调主页面的网络请求
	public void callAddCUser(T_UserRelationship mT_UserRelationship,String app_id);
	//删除常用联系人  回调主页面的网络请求
	public void callRemoveCUser(T_UserRelationship mT_UserRelationship,String app_id);

	/**
	 * 点击保存个人信息
	 * @param mSYS_User
	 */
	public void callSavePeopleMessage(OrgUser mSYS_User,int type,String bittoBase64);

	/**
	 * 回调是否刷新完成
	 * @param flag
	 */
	public void sysUserSuccess(boolean flag);

	/**
	 * 回调日期函数
	 */
	public void settingData(Context context,EditText edit);

	/**
	 * 返回男女
	 */
	public void settingGender(Context context,EditText edit);


	/**
	 * 上传门户、字体、风格信息
	 */
	public void updatePortalSizeStyle(String using_home_style,String using_color_style,String using_font_style);


	public void setIntent(Context context,int type); //跳转手势密码页面 1表示跳转到手势密码页面 2表示跳转到带有手势密码且有指纹的 3 表示只有指纹的

	public boolean isGesturePwdEnable();

	public boolean isInitGesturePwd();

	public boolean isZWGensturePswd();

	public void upgrade();//升级信息

	public class DefaultCallBackMX implements CallbackMX{

		@Override
		public void callbackSendMessage(String userID) {

		}

		@Override
		public void callbackGroups() {

		}

		@Override
		public void callbackFunction() {

		}

		@Override
		public void callbackSpecialFocus() {

		}

		@Override
		public void callbackSpecialFocusButton() {

		}

		@Override
		public void callAddBook() {

		}

		@Override
		public void callBackMain() {

		}

		@Override
		public void callUserMeesageMain() {

		}

		@Override
		public void closeDrawer() {

		}

		@Override
		public void callFunction() {

		}

		@Override
		public void callAddCUser(T_UserRelationship mT_UserRelationship, String app_id) {

		}

		@Override
		public void callRemoveCUser(T_UserRelationship mT_UserRelationship, String app_id) {

		}

		@Override
		public void callSavePeopleMessage(OrgUser mSYS_User, int type,String bittoBase64) {

		}

		@Override
		public void sysUserSuccess(boolean flag) {

		}

		@Override
		public void settingData(Context context, EditText edit) {

		}

		@Override
		public void settingGender(Context context, EditText edit) {

		}

		@Override
		public void updatePortalSizeStyle(String using_home_style, String using_color_style, String using_font_style) {

		}

		@Override
		public void setIntent(Context context, int type) {

		}

		@Override
		public boolean isGesturePwdEnable() {
			return false;
		}

		@Override
		public boolean isInitGesturePwd() {
			return false;
		}

		@Override
		public boolean isZWGensturePswd() {
			return false;
		}

		@Override
		public void upgrade() {

		}
	}
}
