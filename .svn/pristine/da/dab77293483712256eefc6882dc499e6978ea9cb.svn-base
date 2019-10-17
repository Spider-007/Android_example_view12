package com.minxing.client.util;

import android.graphics.Color;

import com.htmitech.api.BookInit;

import java.util.regex.Pattern;

/**
 * Created by Think on 2017/4/25.
 */

public class SysConvertUtil {

    public static int formattingH(int a) {
        String hex = "";
        String color = "";
        int isWaterSecurity = BookInit.getInstance().getIsWaterSecurity();
        try {
            hex = Integer.toHexString(a);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (isWaterSecurity == 1) {
            if (hex.length() == 8) {
                color = "#33" + hex.substring(2, hex.length());
            } else if (hex.length() == 6) {
                color = "#33" + hex;
            } else {
                color = "#33ffffff";
            }
            String reg = "#[a-f0-9A-F]{8}";
            if (!Pattern.matches(reg, color)) {
                color = "#33ffffff";
            }
            return Color.parseColor(color);
        } else {
            return a;
        }
    }


}
