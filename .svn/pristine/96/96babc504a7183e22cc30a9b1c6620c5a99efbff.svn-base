package com.htmitech.thread;

import android.content.Context;

import java.lang.reflect.Constructor;

/**
 * Created by htrf-pc on 2016/11/8.
 */
public class ddd {
    public void ww(Context context){
        try{
            Class<?> activityClass = context.getClassLoader().loadClass("com.plugin.LogicActivity");
            Constructor<?> constructor = activityClass.getConstructor(new Class[] {});
            Object instance = constructor.newInstance(new Object[] {});
//            mPlugin = (Plugin)instance;
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
