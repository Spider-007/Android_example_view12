package com.htmitech.ztcustom.zt.domain;

/**
 * Created by htrf-pc on 2016/9/27.
 */
public class DetectionValue {

    public String color;

    public double value;

    public String name;

    public String code ;

    public String begin_stat_date;

    public String end_stat_date;
    /**
     * 里面应该还缺少ID等一下参数  等接口有了在进行调整
     * @param color
     * @param value
     */
        public DetectionValue(String color,double value,String name,String code,String begin_stat_date,String end_stat_date){
        this.color = color;
        this.value = value;
        this.name = name;
        this.code = code;
            this.begin_stat_date = begin_stat_date;
            this.end_stat_date = end_stat_date;
    }


}
