package entity;



import java.io.Serializable;
import java.util.List;

import htmitech.com.componentlibrary.entity.FieldItem;

/**
 * Created by Think on 2017/8/25.
 */

public class ChangedFieldsInfo implements Serializable{
    private List<FieldItem.dic> dics;
    private String fieldKey;
    private String fieldValue;
    private int defaultDicIndex;
    private int hiden;
    private int editable;

    public List<FieldItem.dic> getDics() {
        return dics;
    }

    public void setDics(List<FieldItem.dic> dics) {
        this.dics = dics;
    }

    public String getFieldKey() {
        return fieldKey;
    }

    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    public int getDefaultDicIndex() {
        return defaultDicIndex;
    }

    public void setDefaultDicIndex(int defaultDicIndex) {
        this.defaultDicIndex = defaultDicIndex;
    }

    public int getHiden() {
        return hiden;
    }

    public void setHiden(int hiden) {
        this.hiden = hiden;
    }

    public int getEditable() {
        return editable;
    }

    public void setEditable(int editable) {
        this.editable = editable;
    }
}
