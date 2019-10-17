package htmitech.util;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.util.HashMap;

/**
 * Created by Administrator on 2018-7-9.
 */

public class BitmapFactoryUtil {
    private HashMap<String, Bitmap> bitmapHashMap = new HashMap<>();
    private static BitmapFactoryUtil instance;

    private BitmapFactoryUtil() {

    }

    public static BitmapFactoryUtil get() {
        if (instance == null) {
            instance = new BitmapFactoryUtil();
        }
        return instance;
    }

    /**
     * 添加一个对应key的bitmap
     *
     * @param key
     * @param mBitmap
     */
    public void addBitmap(String key, Bitmap mBitmap) {
        bitmapHashMap.put(key, mBitmap);
    }


    /**
     * 获取对应key的bitmap
     *
     * @param key
     * @return
     */
    public Bitmap getBitmap(String key) {
        return bitmapHashMap.get(key);
    }

    public Drawable getDrawable(String key) {
        return new BitmapDrawable(bitmapHashMap.get(key));
    }

    /**
     * 清除对应key的
     *
     * @param key
     */
    public void recycleKey(String key) {
        bitmapHashMap.get(key).recycle();
        bitmapHashMap.remove(key);
    }

    /**
     * 清除所有的
     */
    public void recycleAll() {
        if (bitmapHashMap != null) {


            for (String key : bitmapHashMap.keySet()) {
                if(bitmapHashMap.get(key) != null){
                    bitmapHashMap.get(key).recycle();
                }

            }
            bitmapHashMap.clear();
        }
    }


}
