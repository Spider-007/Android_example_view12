package schedulebean;

import java.io.Serializable;

/**
 * Created by yanxin on 2017-9-25.
 * 常用地点返回信息
 */
public class ScheduleOftenResultBean implements Serializable {
    public int code;
    public String message;
    public String debugMsg;
    public ScheduleOftenBean result;
}
