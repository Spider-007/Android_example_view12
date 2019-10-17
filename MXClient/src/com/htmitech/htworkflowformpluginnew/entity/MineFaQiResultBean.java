package com.htmitech.htworkflowformpluginnew.entity;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by yanxin on 2016-9-27.
 */

/**
 * 改造完成 1.1.5 获取我发起的列表
 *
 */
public class MineFaQiResultBean implements Serializable{
    public ArrayList<MyFQResult> Result;
    public int code;
    public String message;
    public String debugMsg;
    public void parseJson(String json) throws Exception {
        //反序列化成Doc结构了
        MineFaQiResultBean entity = JSON.parseObject(json, MineFaQiResultBean.class);
        if(entity.Result == null){
            this.Result = new ArrayList<MyFQResult>();
        }else{
            this.Result = entity.Result;
        }

        this.message = entity.message;
        this.code = entity.code;
    }
}
