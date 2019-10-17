package com.minxing.client.upgrade;

import com.htmitech.proxy.doman.AppclientVersion;

import java.io.Serializable;

/**
 * Created by yanxin on 2017-5-2.
 */
public class EmpmAppUpgradeInfo implements Serializable {
    public int code;
    public Object debugMsg;
    public String message;
    public AppclientVersion result;
}
