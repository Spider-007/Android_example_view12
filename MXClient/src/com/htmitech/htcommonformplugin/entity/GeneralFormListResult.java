package com.htmitech.htcommonformplugin.entity;

import com.alibaba.fastjson.JSON;
import com.htmitech.emportal.entity.Message;

import java.util.ArrayList;

/**
 * Created by htrf-pc on 2017/4/19.
 */
public class GeneralFormListResult {

    public ArrayList<Listiteminfo> Result;

    public Message Message;

    public int Status;
    public void parseJson(String json) throws Exception {

        //反序列化成Doc结构了
        GeneralFormListResult entity = JSON.parseObject(json, GeneralFormListResult.class);
        this.Result = entity.Result;
        this.Message = entity.Message;
        this.Status = entity.Status;
    }
    public ArrayList<Listiteminfo> getResult() {
        return Result;
    }

    public void setResult(ArrayList<Listiteminfo> result) {
        Result = result;
    }

    public com.htmitech.emportal.entity.Message getMessage() {
        return Message;
    }

    public void setMessage(com.htmitech.emportal.entity.Message message) {
        Message = message;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }
}
