package com.htmitech.emportal.ui.daiban.MineMode;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by yanxin on 2016-9-27.
 */
public class PrevenanceResultBean implements Serializable {
    public ArrayList<PrevenanceResult> Result;
    public Message Message;
    public int Status;
    public void parseJson(String json) throws Exception {

        //反序列化成Doc结构了
        PrevenanceResultBean entity = JSON.parseObject(json, PrevenanceResultBean.class);
        this.Result = entity.Result;
        this.Message = entity.Message;
        this.Status = entity.Status;
    }
}
