package com.htmitech.htworkflowformpluginnew.entity;

import java.io.Serializable;
import java.util.List;

import htmitech.com.componentlibrary.entity.AuthorInfo;

/**
 * Created by heyang on 2018-1-15.
 */
public class SelectRouteInfo implements Serializable {
    public String routeId;
    public String routeName;
    public int isAllowSelectUser;
    public int isMultiSelectUser;
    public int isFreeSelectUser;
    //当前路由下，已经选择的办理人员列表
    public List<AuthorInfo> routeAuthors;
}
