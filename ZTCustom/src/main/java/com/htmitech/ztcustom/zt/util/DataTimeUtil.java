package com.htmitech.ztcustom.zt.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by htrf-pc on 2017/1/10.
 */
public class DataTimeUtil {

    /**
     * 默认日期格式
     */
    public static String DEFAULT_FORMAT = "yyyy-MM-dd";

    /**
     * 第一个参数是StartData 第二个参数是endData
     *
     * @param dayTime
     * @return
     */
    public static String[] getTime(String dayTime) {
        String startDate="", endDate="";
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            if (Integer.parseInt(dayTime) < 14 && Integer.parseInt(dayTime) != 1) {

                calendar.add(Calendar.MONTH, 0);
                calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dayTime) + 1);//
                startDate = format.format(calendar.getTime());
                calendar = Calendar.getInstance();
                calendar.add(Calendar.MONTH, 1);
                calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dayTime));
                endDate = format.format(calendar.getTime());


            } else if (dayTime.equals("1")) {

                calendar.add(Calendar.MONTH, 0);
                calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dayTime));//
                startDate = format.format(calendar.getTime());
                calendar = Calendar.getInstance();
                calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                endDate = format.format(calendar.getTime());

            } else {
                calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dayTime));
                calendar.add(Calendar.MONTH, -1);
                startDate = format.format(calendar.getTime());
                calendar.add(Calendar.MONTH, 1);
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                endDate = format.format(calendar.getTime());


            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return new String[]{"", ""};
        }
        return new String[]{startDate, endDate};

    }

    /**
     * 格式化日期
     *
     * @param date 日期对象
     * @return String 日期字符串
     */
    public static String formatDate(Date date) {
        SimpleDateFormat f = new SimpleDateFormat(DEFAULT_FORMAT);
        String sDate = f.format(date);
        return sDate;
    }

    /**
     * 获取当年的第一天
     *
     * @param
     * @return
     */
    public static Date getCurrYearFirst() {
        Calendar currCal = Calendar.getInstance();
        int currentYear = currCal.get(Calendar.YEAR);
        return getYearFirst(currentYear);
    }

    /**
     * 获取当年的最后一天
     *
     * @param
     * @return
     */
    public static Date getCurrYearLast() {
        Calendar currCal = Calendar.getInstance();
        int currentYear = currCal.get(Calendar.YEAR);
        return getYearLast(currentYear);
    }

    /**
     * 获取某年第一天日期
     *
     * @param year 年份
     * @return Date
     */
    public static Date getYearFirst(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        Date currYearFirst = calendar.getTime();
        return currYearFirst;
    }

    /**
     * 获取某年最后一天日期
     *
     * @param year 年份
     * @return Date
     */
    public static Date getYearLast(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        Date currYearLast = calendar.getTime();

        return currYearLast;
    }


    /**
     * 获取当前月底第一天
     *
     * @return
     */
    public static Date getCurrentMonthFrist() {
        // 获取前月的第一天
        Calendar cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 0);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        return cale.getTime();

    }

    /**
     * 获取当前月的最后一天
     *
     * @return
     */
    public static Date getCurrentMonthLast() {
        // 获取前月的最后一天
        Calendar cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 1);
        cale.set(Calendar.DAY_OF_MONTH, 0);
        return cale.getTime();
    }
}
