package com.htmitech.listener;

import com.htmitech.domain.SYS_Department;
/**
 * 
 * 单位通讯录中点击进入下一个部门通知相对应的Fragment
 * 
 * @author Tony
 *
 */
public interface ChildFragmentListener{
	public void notifyDataSetChanged(SYS_Department upSYS_Department,SYS_Department mSYS_Department);
}
