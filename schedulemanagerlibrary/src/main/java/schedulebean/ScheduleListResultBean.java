package schedulebean;

import java.io.Serializable;

/**
 * Created by yanxin on 2017-9-22.
 */
public class ScheduleListResultBean implements Serializable {
    public int code;
    public String message;
    public String debugMsg;
    public ScheduleListBean result;
}
