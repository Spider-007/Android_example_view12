package com.htmitech.ztcustom.zt.app;

/**
 * Created by Administrator on 2018-8-30.
 */

public class ZTCustomInit {
    private static volatile ZTCustomInit instance = null;
    private Cache mCache = new Cache() ;

    public Cache getmCache() {
        return mCache;
    }

    public void setmCache(Cache mCache) {
        this.mCache = mCache;
    }

    private ZTCustomInit(){

    }

    public static ZTCustomInit get(){
        if(instance == null){
            synchronized (ZTCustomInit.class){
                if(instance == null){
                    instance = new ZTCustomInit();
                }
            }
        }
        return instance;
    }

    public void clear(){
        mCache = null;
    }

}
