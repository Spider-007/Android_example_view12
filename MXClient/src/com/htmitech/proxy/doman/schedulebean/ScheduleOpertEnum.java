package com.htmitech.proxy.doman.schedulebean;

/**
 * Created by yanxin on 2017-9-22.
 * 日程操作枚举
 */
public enum ScheduleOpertEnum {//edit,confirm,delete,share
    edit("edit", "edit"),
    confirm("confirm", "confirm"),
    delete("delete", "delete"),
    share("share", "share");
    public String key;
    public String value;

    ScheduleOpertEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
