package utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.services.core.PoiItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import schedulebean.countInfoBean;

/**
 * Created by yanxin on 2017-9-24.
 */
public class ScheduleUtils {
    public static PoiItem currentSelectLocation;  //搜索后点击的
    public static AMapLocation currentLocation;   //一开启就有定位
    private static final long ONE_DAY_MS = 24 * 60 * 60 * 1000;

    /*
    * 获取连续日程,不处理非跨日程的
    * */
    public static ArrayList<countInfoBean> getScheduleState(String startTime, String endTime) throws Exception {
        //http://blog.csdn.net/androidforwell/article/details/53738674
        ArrayList<countInfoBean> dataList = null;
        try {
            dataList = new ArrayList();
            SimpleDateFormat formatter = new SimpleDateFormat(
                    "yyyy-MM-dd");
            Date date_start = formatter.parse(startTime);
            Date date_end = formatter.parse(endTime);

            //计算日期从开始时间于结束时间的0时计算
            Calendar fromCalendar = Calendar.getInstance();
            fromCalendar.setTime(date_start);
            fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
            fromCalendar.set(Calendar.MINUTE, 0);
            fromCalendar.set(Calendar.SECOND, 0);
            fromCalendar.set(Calendar.MILLISECOND, 0);

            Calendar toCalendar = Calendar.getInstance();
            toCalendar.setTime(date_end);
            toCalendar.set(Calendar.HOUR_OF_DAY, 0);
            toCalendar.set(Calendar.MINUTE, 0);
            toCalendar.set(Calendar.SECOND, 0);
            toCalendar.set(Calendar.MILLISECOND, 0);

            int s = (int) ((toCalendar.getTimeInMillis() - fromCalendar.getTimeInMillis()) / (ONE_DAY_MS));
            if (s > 0) {
                for (int i = 0; i <= s; i++) {
                    long todayDate = fromCalendar.getTimeInMillis() + i * ONE_DAY_MS;
                    /**
                     * yyyy-MM-dd E :2012-09-01
                     */
//                    dataList.add(getCustonFormatTime(todayDate,"yyyy-MM-dd"));
                    countInfoBean countInfoBean = new countInfoBean();
                    countInfoBean.Date = getCustonFormatTime(todayDate, "yyyy-MM-dd");
                    countInfoBean.State = "red";
                    dataList.add(countInfoBean);
                    Log.i("打印日期", getCustonFormatTime(todayDate, "yyyy-MM-dd"));
                }
            } else {//此时在同一天之内
//                Log.i("打印日期",getCustonFormatTime(startTime,"yyyy-MM-dd"));
//                dataList.add(startTime);
                countInfoBean countInfoBean = new countInfoBean();
                countInfoBean.Date = startTime;
                countInfoBean.State = "green";
                dataList.add(countInfoBean);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    private static String getCustonFormatTime(long time, String formatStr) {
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        Date d1 = new Date(time);
        return format.format(d1);
    }

    public static Date transferDateTime(String dateStr) {
        Date date = null;
        try {
            String format = "yyyy-MM-dd HH:mm:ss";
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /*
   * 搜索关键字变色
   * */
    public static SpannableStringBuilder setSpecifiedTextsColor(String text, String specifiedTexts, int color) {
        List<Integer> sTextsStartList = new ArrayList<Integer>();

        int sTextLength = specifiedTexts.length();
        String temp = text;
        int lengthFront = 0;//记录被找出后前面的字段的长度
        int start = -1;
        do {
            start = temp.indexOf(specifiedTexts);

            if (start != -1) {
                start = start + lengthFront;
                sTextsStartList.add(start);
                lengthFront = start + sTextLength;
                temp = text.substring(lengthFront);
            }

        } while (start != -1);

        SpannableStringBuilder styledText = new SpannableStringBuilder(text);
        for (Integer i : sTextsStartList) {
            styledText.setSpan(
                    new ForegroundColorSpan(color),
                    i,
                    i + sTextLength,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        return styledText;
    }

    /*
    * 开启本地推送
    * */
    public static void setAlarmTime(Context context, long triggerAtMillis, String appId, String schId, String detailConfig,
                                    String dropdownOptionManual, String includeSecurity, String title, String fristremin) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent("com.htmitech.proxy.BroadcastReceiver.AlarmReceiver");
        intent.putExtra("app_id", appId);
        intent.putExtra("com_schedule_mobileconfig_detailview_config", detailConfig);
        intent.putExtra("sch_id", schId);
        intent.putExtra("com_schedule_mobileconfig_dropdown_option_manual", dropdownOptionManual);
        intent.putExtra("com_schedule_mobileconfig_include_security", includeSecurity);
        intent.putExtra("title", title);
        intent.putExtra("fristremin", fristremin);
        PendingIntent sender = PendingIntent.getBroadcast(
                context,  Integer.parseInt(schId), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //闹铃间隔， 这里设为1分钟闹一次，在第2步我们将每隔1分钟收到一次广播
        //int interval = 60 * 1000;
        //am.setRepeating(AlarmManager.RTC_WAKEUP, timeInMillis, interval, sender);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            am.setExact(AlarmManager.RTC_WAKEUP, triggerAtMillis, sender);
        } else {
            am.set(AlarmManager.RTC_WAKEUP, triggerAtMillis, sender);
        }
//        am.set(AlarmManager.RTC_WAKEUP, triggerAtMillis, sender);
    }

    /*
   * 通过通知开启本地推送
   * */
    public static void setAlarmTimeFromNotifycation(Context context, long triggerAtMillis, String appId, String schId, String title, String fristremin,String pushTitle) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent("com.htmitech.proxy.BroadcastReceiver.AlarmFromNotifycationReceiver");
        intent.putExtra("app_id", appId);
        intent.putExtra("sch_id", schId);
        intent.putExtra("push_title", pushTitle);
        intent.putExtra("title", title);
        intent.putExtra("fristremin", fristremin);
        PendingIntent sender = PendingIntent.getBroadcast(
                context, Integer.parseInt(schId), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //闹铃间隔， 这里设为1分钟闹一次，在第2步我们将每隔1分钟收到一次广播
        //int interval = 60 * 1000;
        //am.setRepeating(AlarmManager.RTC_WAKEUP, timeInMillis, interval, sender);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            am.setExact(AlarmManager.RTC_WAKEUP, triggerAtMillis, sender);
        } else {
            am.set(AlarmManager.RTC_WAKEUP, triggerAtMillis, sender);
        }
//        am.set(AlarmManager.RTC_WAKEUP, triggerAtMillis, sender);
    }

    /*
    *   毫秒转日期
    * */
    public static String getFormatedDateTime(String pattern, long dateTime) {
        SimpleDateFormat sDateFormat = new SimpleDateFormat(pattern);
        return sDateFormat.format(new Date(dateTime + 0));
    }

    /*
    * 比较两天时间是否大于一小时
    * */
    public static boolean compareTowDate(String startDate, String endDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date startTime = simpleDateFormat.parse(startDate);
            Date endTime = simpleDateFormat.parse(endDate);
            long disTime = endTime.getTime() - startTime.getTime();
            if (disTime / 1000 / 3600 >= 1) {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    /*
   * 比较时间是否为失效
   * */
    public static boolean FailSchedule(String startDate, String endDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date startTime = simpleDateFormat.parse(startDate);
            Date endTime = simpleDateFormat.parse(endDate);
            long disTime = endTime.getTime() - startTime.getTime();
            if (disTime > 0) {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    /*
  * 比较时间是否为失效
  * */
    public static boolean isFailSchedule(String startDate, String endDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date startTime = simpleDateFormat.parse(startDate);
            Date endTime = simpleDateFormat.parse(endDate);
            long disTime = endTime.getTime() - startTime.getTime();
            if (disTime >= 0) {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    /*
    * 判断是否为全天
    * true为全天日程
    * false为非全天
    * */
    public static boolean isAllDay(String startTime, String endTime, String today) {
        if (TextUtils.isEmpty(startTime) || TextUtils.isEmpty(endTime) || TextUtils.isEmpty(today)) {
            return false;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date startTimeDate = simpleDateFormat.parse(startTime);
            Date endTimeDate = simpleDateFormat.parse(endTime);
            Date todayDate = simpleDateFormat.parse(today);
            long endDiff = endTimeDate.getTime() - todayDate.getTime();
            long startDiff = todayDate.getTime() - startTimeDate.getTime();
            if (startDiff > 0 && endDiff > 0) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /*
  * 判断是否为整数
  * @param str 传入的字符串
  * @return 是整数返回true,否则返回false
*/

    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

}
