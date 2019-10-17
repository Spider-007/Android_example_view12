package htmitech.com.componentlibrary.unit;

import android.content.Context;

import java.lang.ref.WeakReference;
import java.util.HashMap;

/**
 * Created by Administrator on 2018-7-24.
 */

public class RefreshTotal {
    public static HashMap<Integer, Boolean> hashMap = new HashMap<>();
    public final static int YYZX = 0x1000;
    public final static int ZY = 0x1001;

    /**
     * 判断当前Activity是否需要刷新
     *
     * @return
     */
    public static boolean isResfreshActivity(int type) {
        if (hashMap.get(type) != null) {
            return hashMap.get(type);
        }
        return false;
    }

    /**
     * 加入需要刷新的Activity
     */
    public static void addReshActivity() {
        hashMap.clear();
        hashMap.put(YYZX, true);
        hashMap.put(ZY, true);
    }

    public static boolean isZY() {
        return isResfreshActivity(ZY);
    }

    public static boolean isYYZX() {
        return isResfreshActivity(YYZX);
    }

    public static void refersh(int type, boolean isFlag) {
        hashMap.put(type, isFlag);
    }

    public static void refershZY(boolean isFlag) {
        hashMap.put(ZY, isFlag);
    }

    public static void refershYYZX(boolean isFlag) {
        hashMap.put(YYZX, isFlag);
    }
}
