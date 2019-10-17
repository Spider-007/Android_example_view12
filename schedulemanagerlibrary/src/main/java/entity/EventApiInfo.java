package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Think on 2017/8/25.
 */

public class EventApiInfo implements Serializable{

    private int resultStatus;
    private String resultMsg;
    private ArrayList<ChangedFieldsIEventApinfo> changedFields;

    public int getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(int result_status) {
        this.resultStatus = result_status;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String result_msg) {
        this.resultMsg = result_msg;
    }

    public List<ChangedFieldsIEventApinfo> getChangedFields() {
        return changedFields;
    }

    public void setChangedFields(ArrayList<ChangedFieldsIEventApinfo> changedFields) {
        this.changedFields = changedFields;
    }
}
