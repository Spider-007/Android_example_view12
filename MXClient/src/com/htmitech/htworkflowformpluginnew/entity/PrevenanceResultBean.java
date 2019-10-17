package com.htmitech.htworkflowformpluginnew.entity;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by yanxin on 2016-9-27.
 */

/**
 * 1.1.4 获取我关注的列表\
 * 改造完成
 */
public class PrevenanceResultBean implements Serializable {
    public ArrayList<PrevenanceResult> result;
    public int code;
    public String message;
    public String debugMsg;

    public void parseJson(String json) throws Exception {

        //反序列化成Doc结构了
        PrevenanceResultBean entity = JSON.parseObject(json, PrevenanceResultBean.class);
        if (entity.result == null) {
            this.result = new ArrayList<PrevenanceResult>();
        } else {
            this.result = entity.result;
        }

        this.message = entity.message;
        this.code = entity.code;
    }
}
