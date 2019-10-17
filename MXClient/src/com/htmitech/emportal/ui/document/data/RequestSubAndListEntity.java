package com.htmitech.emportal.ui.document.data;

import java.io.Serializable;

/**
 * Created by yanxin on 2017-9-5.
 * 请求资料库实体（对应java端整合后的新接口）
 */
public class RequestSubAndListEntity implements Serializable{
    public String pageSize;
    public String pageNum;
    public String parentId;
    public String name;
    public String remark;
    public String groupCorpId;
}
