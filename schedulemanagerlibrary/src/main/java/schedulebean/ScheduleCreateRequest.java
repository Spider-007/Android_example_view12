package schedulebean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by yanxin on 2017-9-22.
 * 日程创建请求参数
 */
public class ScheduleCreateRequest implements Serializable {
    public String userId;
    public Long appId;
    public String groupCorpId;
    public ArrayList<Object> page = new ArrayList<Object>();
    public String schId;
    public String corpId;
    public String schTitle;
    public String schBeginTime;
    public String schEndTime;
    public String cpId;
    public String schContentId;
    public int schStatus;
    public String firstRemind;
    public String secondtRemind;
    public String remindInterval;
    public String repeatFlag;
    public String classfy;
    public String priority;
    public String spanDayFlag;
    public String trafficInfo;
    public String participation ;
    public String accompany ;
    public String dataId;
    public String formId;
    public String remark;
    public int statusFlag;
    public String efs1;
    public String efs2;
    public String efs3;
    public String efs4;
    public String efs5;
    public String efi1;
    public String efi2;
    public String efi3;
    public String efi4;
    public String efi5;
    public String efn1;
    public String efn2;
    public String efn3;
    public ScheduleCpId schPlace;
}
