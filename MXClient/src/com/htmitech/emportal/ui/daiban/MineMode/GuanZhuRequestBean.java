package com.htmitech.emportal.ui.daiban.MineMode;

import com.htmitech.emportal.entity.OAConText;

import java.io.Serializable;

/**
 * Created by yanxin on 2016-9-28.
 */
public class GuanZhuRequestBean implements Serializable {
    private static final long serialVersionUID = -8592118312872488357L;

    public OAConText context;

    public String Title;

    public String ModelName;

    public String TodoFlag;

    public String recordStartIndex = "0";

    public String recordEndIndex = "15";

    public String app_id;
}
