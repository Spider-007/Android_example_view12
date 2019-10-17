package com.htmitech.proxy.doman.schedulebean;

import com.htmitech.emportal.utils.UIUtils;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by yanxin on 2017-9-22.
 */
public class ScheduleListItemBean implements Serializable {
    public String logFunction;
    public String logException;
    public String logBehavior;
    public String beLogList;
    public String exLogList;
    public String pageSize;
    public String pageNum;
    public String orderBy;
    public ArrayList<Object> page;
    public String createTime;
    public String updateTime;
    public String schId;
    public String groupCorpId;
    public String corpId;
    public String schTitle;
    public String schBeginTime;
    public String schEndTime;
    public String cpId;
    public String schContentId;
    public int schStatus;
    public String firstRemind;
    public String secondtRemind;
    public String remindInterval;
    public String repeatFlag;
    public String classfy;
    public String priority;
    public String spanDayFlag;  //0不跨天1跨天
    public String trafficInfo;
    public String dataId;
    public String formId;
    public String remark;
    public int statusFlag;
    public String createBy;
    public String updateBy;
    public String efs1;
    public String efs2;
    public String efs3;
    public String efs4;
    public String efs5;
    public String efi1;
    public String efi2;
    public String efi3;
    public String efi4;
    public String efi5;
    public String efd1;
    public String efd2;
    public String efd3;
    public String efn1;
    public String efn2;
    public String efn3;
    public String appId;
    public String startTime;
    public String endTime;
    public String userId;
    public String participation;
    public String accompany;
    public String buttonInfo;
    public String schPlace;
    public int rawX = UIUtils.getScreenWidth();
}
