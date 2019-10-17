package com.htmitech.emportal.entity;

/**
 * Created by Think on 2016/11/23.
 */
import java.io.Serializable;

public class SelectionsInfo implements Serializable {

    private static final long serialVersionUID = 111111L;

    public String  dataValue;
    public String  selectValue;

    public String getDataValue() {
        return dataValue;
    }

    public void setDataValue(String dataValue) {
        this.dataValue = dataValue;
    }

    public String getSelectValue() {
        return selectValue;
    }

    public void setSelectValue(String selectValue) {
        this.selectValue = selectValue;
    }
}
