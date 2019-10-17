package com.htmitech.htworkflowformpluginnew.util;

import com.htmitech.domain.SYS_User;

import java.util.ArrayList;
import java.util.List;

import htmitech.com.componentlibrary.entity.AuthorInfo;

/**
 * Created by htrf-pc on 2016/7/6.
 */
public class SystemUser2SYSUser {

    public static ArrayList<SYS_User> system2SysUser(List<AuthorInfo> infos){
        ArrayList<SYS_User> userList = new ArrayList<SYS_User>();
        if(infos == null){
            return userList;
        }
        for(AuthorInfo mAuthorInfo : infos){
            SYS_User mSYS_User = new SYS_User();
            mSYS_User.setFullName(mAuthorInfo.getUserName());
            mSYS_User.setUserId(mAuthorInfo.getUserId());
            userList.add(mSYS_User);
        }
        return userList;
    }

}
