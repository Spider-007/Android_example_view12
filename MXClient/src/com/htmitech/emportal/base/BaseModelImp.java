package com.htmitech.emportal.base;

import java.util.HashMap;

/**
 * Created by htrf-pc on 2017/1/10.
 */
public interface BaseModelImp {
    public void getDataFromServerByType(int type,
                                        HashMap<String, String> paramHashMap);
    public void getDataFromServerByType(int type, Object paramObject);
}
