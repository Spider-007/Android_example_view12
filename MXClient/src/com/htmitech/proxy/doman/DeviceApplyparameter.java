package com.htmitech.proxy.doman;

import java.io.Serializable;

/**
 * Created by Think on 2017/5/4.
 */

public class DeviceApplyparameter implements Serializable {

    public String applyId;

    public String pictureId;

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getPictureId() {
        return pictureId;
    }

    public void setPictureId(String pictureId) {
        this.pictureId = pictureId;
    }
}
