package com.htmitech.proxy.doman.schedulebean;

import java.io.Serializable;

/**
 * Created by yanxin on 2017-9-22.
 * 日程列表请求参数
 */
public class ScheduleListRequest implements Serializable {
    public int pageNum;
    public int pageSize;
    public String userId;
    public Long appId;
    public String groupCorpId;
    public String corpId;
    public String schTitle;
    public String startTime;
    public String endTime;
//    public String schStatus;   //由schAdviceStatus这个参数替代
    public String schAdviceStatus;

}
