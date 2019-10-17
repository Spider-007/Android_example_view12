package com.minxing.client.util;

import android.app.Activity;

import java.util.ArrayList;

/**
 * Created by htrf-pc on 2017/8/31.
 */
public class DocumentNodeUtil {
    public static ArrayList<Activity> removeDouNode = new ArrayList<Activity>();


    public static void add(Activity a){
        removeDouNode.add(a);
    }


    public static void removeAll(){
        try{
            for(Activity c : removeDouNode){
                c.finish();
            }
            removeDouNode.clear();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
