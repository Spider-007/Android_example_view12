package com.htmitech.proxy.util;

import java.io.File;

/**
 * Created by htrf-pc on 2018/1/11.
 */
public class RootUtil {

    /** 判断是否具有ROOT权限 ,此方法对有些手机无效，比如小米系列 */
    public static boolean isRoot() {

        boolean res = false;

        try {
            if ((!new File("/system/bin/su").exists())
                    && (!new File("/system/xbin/su").exists())) {
                res = false;
            } else {
                res = true;
            }
            ;
        } catch (Exception e) {
            res = false;
        }
        return res;
    }
}
