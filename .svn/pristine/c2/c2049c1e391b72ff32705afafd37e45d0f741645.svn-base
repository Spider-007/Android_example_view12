package com.htmitech.listener;

import android.graphics.Bitmap;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.htmitech.domain.SYS_Department;
import com.htmitech.domain.SYS_User;

import java.util.ArrayList;

/**
 * Tony
 */
public interface CallBackChoosePeople {
    /**
     * 回调已选择人员的全部信息
     * @param userList
     * @param chooseNumber
     * @param pothoBitmap
     * @param ivAvatar
     * @param default_tv
     * @param isImage
     * @param color
     * @param itemHight
     * @param isCheck
     */
    public void callBackChoosePeopleMessage(ArrayList<SYS_User> userList,int chooseNumber,Bitmap pothoBitmap,ImageView ivAvatar,TextView default_tv,boolean isImage,int color,int itemHight,boolean isCheck,CheckBox mCheckBox);

    /**
     *
     *回调已选择部门所有信息
     * @param departmentList
     * @param chooseNumber
     * @param pothoBitmap
     * @param ivAvatar
     * @param itemHight
     * @param isCheck
     */

    public void callBackChooseDepartment(ArrayList<SYS_Department> departmentList,int chooseNumber,Bitmap pothoBitmap,ImageView ivAvatar,int itemHight,boolean isCheck,CheckBox mCheckBox);

    /**
     * 点击外部以及头部让弹出的已选择页面进行消失
     *
     */
    public void setShutDownAnimation();

    /**
     *在已选择页面删除的情况下
     * 返回主页进行刷新页面数字以及颜色变化
     * 确定按钮的可点击性
     * @param number
     */
    public void callBackDeletePeople(int number);

    /**
     *  当常用联系人和组织机构同时存在的情况下
     *  必须得等层级结构或者树形结构加载完毕之后
     *  常用联系人才能可选
     *
     */
    public void callBackProGone();
}
