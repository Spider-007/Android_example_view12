package com.htmitech.htnativestartformplugin.entity;


import java.io.Serializable;
import java.util.List;

import htmitech.com.componentlibrary.entity.FieldItem;

/**
 * Created by Think on 2017/8/25.
 */

public class ChangedFieldsInfo implements Serializable{
    private List<FieldItem.dic> dics;
    private String field_key;
    private String field_value;
    private int default_dic_index;

    private int hiden;
    private int editable;

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

    public List<FieldItem.dic> getDics() {
        return dics;
    }

    public void setDics(List<FieldItem.dic> dics) {
        this.dics = dics;
    }

    public String getField_key() {
        return field_key;
    }

    public void setField_key(String field_key) {
        this.field_key = field_key;
    }

    public String getField_value() {
        return field_value;
    }

    public void setField_value(String field_value) {
        this.field_value = field_value;
    }

    public int getDefault_dic_index() {
        return default_dic_index;
    }

    public void setDefault_dic_index(int default_dic_index) {
        this.default_dic_index = default_dic_index;
    }



}
