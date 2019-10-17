package com.htmitech.htworkflowformpluginnew.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/7/18.
 */

public class PdfSignInfoResult implements Serializable {
    public String downloadUrl;
    public String fileId;
    public String fileName;
    public int fileType;
    public String updateTime;
    public long userId;
    public String userName;
}
