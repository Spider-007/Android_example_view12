package com.htmitech.proxy.doman;

/**
 * Created by htrf-pc on 2017/10/13.
 */
public class RequestResortJson {

    public long portalId;
    public long userId;
    public long appId;
    public long displayOrder;


    public long getAppId() {
        return appId;
    }

    public long getDisplayOrder() {
        return displayOrder;
    }

    public long getPortalId() {
        return portalId;
    }

    public long getUserId() {
        return userId;
    }

    public void setAppId(long appId) {
        this.appId = appId;
    }

    public void setDisplayOrder(long displayOrder) {
        this.displayOrder = displayOrder;
    }

    public void setPortalId(long portalId) {
        this.portalId = portalId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
