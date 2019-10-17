package entity;

import java.io.Serializable;

/**
 * Created by yanxin on 2018-4-2.
 */
public class ChangedFieldsIEventApinfo implements Serializable {
    private String dics;
    private String fieldKey;
    private String fieldValue;
    private String defaultDicIndex;


    public String getDics() {
        return dics;
    }

    public void setDics(String dics) {
        this.dics = dics;
    }

    public String getField_key() {
        return fieldKey;
    }

    public void setFieldKey(String field_key) {
        this.fieldKey = field_key;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String field_value) {
        this.fieldValue = field_value;
    }

    public String getDefault_dic_index() {
        return defaultDicIndex;
    }

    public void setDefaultDicIndex(String default_dic_index) {
        this.defaultDicIndex = default_dic_index;
    }

}
