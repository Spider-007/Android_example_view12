package scheduleEnum;

/**
 * Created by yanxin on 2017-9-28.
 * 日程提醒描述语
 */
public enum FristReminEnum {
    FRIST("0", "不需要提醒"),
    SECOND("1", "事件开始时提醒"),
    THRID("2", "提前5分钟提醒"),
    FOURTH("3", "提前10分钟提醒"),
    FIRTH("4", "提前15分钟提醒"),
    SIXTH("5", "提前30分钟提醒"),
    SEVENTH("6", "提前1小时提醒");
    public String key;
    public String value;

    FristReminEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static String getReminValut(String key) {
        for (FristReminEnum a : FristReminEnum.values()) {
            if (a.key.equals(key)) {
                return a.value;
            }
        }
        return "";
    }
}
