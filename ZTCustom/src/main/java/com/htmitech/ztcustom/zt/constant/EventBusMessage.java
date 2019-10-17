package com.htmitech.ztcustom.zt.constant;

/**
 * Created by htmitech on 2018/8/15.
 */

public class EventBusMessage {

    public String name;
    public String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



        public EventBusMessage(String name, String id) {
        this.name = name;
        this.id = id;
         }


}
