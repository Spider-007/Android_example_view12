package com.htmitech.emportal.ui.document.data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by yanxin on 2017-9-5.
 * 资料库子目录与文件item列表中的实体
 */
public class FsDocResultList implements Serializable {
    public String logFunction;
    public String logException;
    public String logBehavior;
    public String beLogList;
    public String exLogList;
    public String pageSize;
    public String pageNum;
    public String orderBy;
    public ArrayList<Object> page;
    public String createTime;
    public String updateTime;
    public String id;
    public String parentId;
    public String name;
    public String remark;
    public String iconPicId;
    public String groupCorpId;
    public int statusFlag;
    public String createBy;
    public String updateBy;
    public String picPath;
    public int type;
    public String extname;
    public String filePath;
    public String filename;
    public String fileId;
    public String nodeIds;
    public String nodeIdList;
    public String userId;
    //添加是否选中标示位
    public boolean isCheck;
    public boolean isDownLoading = false;
    public boolean isDownLoadFinish = false;
    public int total;
    public int current;
}
