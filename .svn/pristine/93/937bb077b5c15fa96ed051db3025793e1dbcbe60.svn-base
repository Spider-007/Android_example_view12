package com.htmitech.emportal.ui.document.data;


import java.util.ArrayList;

public class DocumentNodeEntity implements DocumentBaseEntity {

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
    public String type;
    public String nodeIds;
    public String nodeIdList;
    public String userId;


    /**
     * 下载路径
     */
    public String getNodeIconDownloadURL() {
        return picPath;
    }

    public void setNodeIconDownloadURL(String nodeIconDownloadURL) {
        picPath = nodeIconDownloadURL;
    }

    @Override
    public int getType() {
        // 表示目录
        return DocumentBaseEntity.TYPE_DOCUMENT_NODE;
    }


}
