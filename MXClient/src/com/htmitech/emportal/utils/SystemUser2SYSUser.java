package com.htmitech.emportal.utils;

import com.htmitech.domain.SYS_User;
import com.htmitech.emportal.entity.AuthorInfo;

import java.util.ArrayList;

/**
 * Created by htrf-pc on 2016/7/6.
 */
public class SystemUser2SYSUser {

    public static ArrayList<SYS_User> system2SysUser(AuthorInfo[] infos){
        ArrayList<SYS_User> userList = new ArrayList<SYS_User>();
        if(infos == null){
            return userList;
        }
        for(AuthorInfo mAuthorInfo : infos){
            SYS_User mSYS_User = new SYS_User();
            mSYS_User.setFullName(mAuthorInfo.getUserName());
            mSYS_User.setUserId(mAuthorInfo.getUserID());
            userList.add(mSYS_User);
        }
        return userList;
    }

}
