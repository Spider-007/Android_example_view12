package com.htmitech.htworkflowformpluginnew.entity;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONArray;
import com.minxing.client.util.FastJsonUtils;

import java.io.Serializable;
import java.util.List;


public class DocSearchParameters implements Serializable {

    //改造完成
    private static final long serialVersionUID = -8592118312872488357L;

    public String userId;

    public String importance;

    public String title;

    public String modelName;

    public String todoFlag;

    public int recordStartIndex = 0;

    public int recordEndIndex = 15;

    public String appId;

    public String timeoutValue;

    public String startTime;//新增

    public String endTime;//新增

    public List<Others> others;//新增


    public void setOthers(String others) {
        if(!TextUtils.isEmpty(others)){
            try{
                this.others = FastJsonUtils.getPersonList(others, Others.class);
            }catch (Exception e){

            }

        }
    }
}
