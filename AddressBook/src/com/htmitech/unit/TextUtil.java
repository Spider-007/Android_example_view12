package com.htmitech.unit;

import android.text.TextUtils;

/**
 * Created by Administrator on 2018/5/9.
 */

public class TextUtil {

    public static boolean equals(CharSequence str1, CharSequence str2) {

        try {
            if (TextUtils.isEmpty(str1)) {
                str1 = "";
            }
            if (TextUtils.isEmpty(str2)) {
                str2 = "";
            }
            return TextUtils.equals(str1, str2);
        } catch (Exception e) {
            return false;
        }

    }

}
