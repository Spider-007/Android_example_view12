package com.htmitech.proxy.doman;

import java.io.Serializable;

/**
 * Created by yanxin on 2017-4-28.
 */
public class AppclientVersion implements Serializable{
    public int createBy;
    public String createTime;
    public String downloadUrl;
    public int envType;
    public int fileId;
    public String filePath;
    public long groupCorpId;
    public int id;
    public int mustupdated;
    public String plistUrl;
    public int resetClient;
    public int statusFlag;
    public int type;
    public int updateBy;
    public String updateDesc;
    public String updateTime;
    public Object userId;
    public String versionName;
    public int versionNo;
    public boolean NeedUpgrade;

    public String getDownload_url() {
        return downloadUrl;
    }

    public void setDownload_url(String download_url) {
        this.downloadUrl = download_url;
    }

    public int getEnv_type() {
        return envType;
    }

    public void setEnv_type(int env_type) {
        this.envType = env_type;
    }

    public int getReset_client() {
        return resetClient;
    }

    public void setReset_client(int reset_client) {
        this.resetClient = reset_client;
    }

    public int getMustupdated() {
        return mustupdated;
    }

    public void setMustupdated(int mustupdated) {
        this.mustupdated = mustupdated;
    }

    public String getUpdate_desc() {
        return updateDesc;
    }

    public void setUpdate_desc(String update_desc) {
        this.updateDesc = update_desc;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getVersion_name() {
        return versionName;
    }

    public void setVersion_name(String version_name) {
        this.versionName = version_name;
    }

    public int getVersion_no() {
        return versionNo;
    }

    public void setVersion_no(int version_no) {
        this.versionNo = version_no;
    }

    public int getFile_id() {
        return fileId;
    }

    public void setFile_id(int file_id) {
        this.fileId = file_id;
    }

    public int getStatus_flag() {
        return statusFlag;
    }

    public void setStatus_flag(int status_flag) {
        this.statusFlag = status_flag;
    }

    public int getCreate_by() {
        return createBy;
    }

    public void setCreate_by(int create_by) {
        this.createBy = create_by;
    }

    public int getUpdate_by() {
        return updateBy;
    }

    public void setUpdate_by(int update_by) {
        this.updateBy = update_by;
    }

    public long getGroup_corp_id() {
        return groupCorpId;
    }

    public void setGroup_corp_id(int group_corp_id) {
        this.groupCorpId = group_corp_id;
    }

    public String getPlist_url() {
        return plistUrl;
    }

    public void setPlist_url(String plist_url) {
        this.plistUrl = plist_url;
    }
}
