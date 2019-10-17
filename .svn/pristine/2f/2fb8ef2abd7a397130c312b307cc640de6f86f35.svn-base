package com.htmitech.emportal.ui.announcement.entity;

import com.alibaba.fastjson.JSON;
import com.htmitech.emportal.entity.Message;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by yanxin on 2017-3-30.
 */
public class ResponseAnnouncementList implements Serializable{
    public ArrayList<AnnouncementListResult> Result;
    public Message Message;
    public int Status;

    public void parseJson(String json) throws Exception {
        //反序列化成Doc结构了
        ResponseAnnouncementList entity = JSON.parseObject(json, ResponseAnnouncementList.class);
        this.Result = entity.Result;
        this.Message = entity.Message;
        this.Status = entity.Status;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
