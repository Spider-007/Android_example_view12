package com.htmitech.proxy.doman;

import java.io.Serializable;

/**
 * Created by Think on 2017/5/8.
 */

public class OutputInfo implements Serializable{

    public String field_text;
    public int display_order;
    public int field_value;

    public String getField_text() {
        return field_text;
    }

    public void setField_text(String field_text) {
        this.field_text = field_text;
    }

    public int getDisplay_order() {
        return display_order;
    }

    public void setDisplay_order(int display_order) {
        this.display_order = display_order;
    }

    public int getField_value() {
        return field_value;
    }

    public void setField_value(int field_value) {
        this.field_value = field_value;
    }
}
