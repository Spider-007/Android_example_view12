package com.htmitech.htcommonformplugin.entity;

import com.alibaba.fastjson.JSON;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Think on 2017/4/19.
 */

public class GetDetailEntity implements Serializable{

    public Formdetail Result;

    public com.htmitech.emportal.entity.Message Message;

    public int Status;
    public void parseJson(String json) throws Exception {

        //反序列化成Doc结构了
        GetDetailEntity entity = JSON.parseObject(json, GetDetailEntity.class);
        this.Result = entity.Result;
        this.Message = entity.Message;
        this.Status = entity.Status;
    }

    public Formdetail getResult() {
        return Result;
    }

    public void setResult(Formdetail result) {
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
