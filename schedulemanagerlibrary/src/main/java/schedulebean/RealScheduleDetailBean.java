package schedulebean;

import android.content.Context;

import com.htmitech.utils.OAConText;

import java.util.List;

import htmitech.com.componentlibrary.entity.EditField;

/**
 * Created by yanxin on 2017-9-25.
 */
public class RealScheduleDetailBean {
    public void save(List<EditField> dataList, Context context, String app_id){
        String url = "http://htrf.dscloud.me:8083/data-crab/schschedule/save";
        ScheduleCreateRequest scheduleDetailRequest = new ScheduleCreateRequest();
        scheduleDetailRequest.userId = OAConText.getInstance(context).UserID;
        scheduleDetailRequest.groupCorpId = OAConText.getInstance(context).group_corp_id;
        scheduleDetailRequest.corpId = "97226251340611590";
        scheduleDetailRequest.appId = Long.parseLong(app_id);
        if (dataList != null && dataList.size() > 0) {
            for (int j = 0; j < dataList.size(); j++) {
                        if (dataList.get(j).getKey().equals("schTitle")) {
                            scheduleDetailRequest.schTitle = dataList.get(j).getValue();
                        } else if (dataList.get(j).getKey().equals("schBeginTime")) {
                            scheduleDetailRequest.schBeginTime = dataList.get(j).getValue();
                        } else if (dataList.get(j).getKey().equals("schEndTime")) {
                            scheduleDetailRequest.schEndTime = dataList.get(j).getValue();
                        }else if (dataList.get(j).getKey().equals("schEndTime")){
                            scheduleDetailRequest.spanDayFlag = dataList.get(j).getValue();
                        }else if (dataList.get(j).getKey().equals("schEndTime")){

                        }else if (dataList.get(j).getKey().equals("schEndTime")){

                        }else if (dataList.get(j).getKey().equals("schEndTime")){

                        }else if (dataList.get(j).getKey().equals("schEndTime")){

                        }else if (dataList.get(j).getKey().equals("schEndTime")){

                        }else if (dataList.get(j).getKey().equals("schEndTime")){

                        }else if (dataList.get(j).getKey().equals("schEndTime")){

                        }else if (dataList.get(j).getKey().equals("schEndTime")){

                        }else if (dataList.get(j).getKey().equals("schEndTime")){

                        }else if (dataList.get(j).getKey().equals("schEndTime")){

                        }else if (dataList.get(j).getKey().equals("schEndTime")){

                        }else if (dataList.get(j).getKey().equals("schEndTime")){

                        }else if (dataList.get(j).getKey().equals("schEndTime")){

                        }else if (dataList.get(j).getKey().equals("schEndTime")){

                        }else if (dataList.get(j).getKey().equals("schEndTime")){

                        }else if (dataList.get(j).getKey().equals("schEndTime")){

                        }else if (dataList.get(j).getKey().equals("schEndTime")){

                        }else if (dataList.get(j).getKey().equals("schEndTime")){

                        }else if (dataList.get(j).getKey().equals("schEndTime")){

                        }else if (dataList.get(j).getKey().equals("schEndTime")){

                        }else if (dataList.get(j).getKey().equals("schEndTime")){

                        }else if (dataList.get(j).getKey().equals("schEndTime")){

                        }else if (dataList.get(j).getKey().equals("schEndTime")){

                        }else if (dataList.get(j).getKey().equals("schEndTime")){

                        }else if (dataList.get(j).getKey().equals("schEndTime")){

                        }else if (dataList.get(j).getKey().equals("schEndTime")){

                        }else if (dataList.get(j).getKey().equals("schEndTime")){

                        }else if (dataList.get(j).getKey().equals("schEndTime")){

                        }else if (dataList.get(j).getKey().equals("schEndTime")){

                        }else if (dataList.get(j).getKey().equals("schEndTime")){

                        }else if (dataList.get(j).getKey().equals("schEndTime")){

                        }else if (dataList.get(j).getKey().equals("schEndTime")){

                        }else if (dataList.get(j).getKey().equals("schEndTime")){

                        }else if (dataList.get(j).getKey().equals("schEndTime")){

                        }else if (dataList.get(j).getKey().equals("schEndTime")){

                        }else if (dataList.get(j).getKey().equals("schEndTime")){

                        }else if (dataList.get(j).getKey().equals("schEndTime")){

                        }else if (dataList.get(j).getKey().equals("schEndTime")){

                        }else if (dataList.get(j).getKey().equals("schEndTime")){

                        }
            }
        }

    }
}
