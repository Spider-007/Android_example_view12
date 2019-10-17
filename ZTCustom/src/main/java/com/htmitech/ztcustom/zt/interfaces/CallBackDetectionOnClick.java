package com.htmitech.ztcustom.zt.interfaces;


import com.htmitech.ztcustom.zt.domain.DetectionValue;

/**
 *
 * 点击圆形的回调接口
 *
 *
 */
public interface CallBackDetectionOnClick {

    public void onClick(boolean flag, DetectionValue mDetectionValue);

    public void onTotal(double total);

}
