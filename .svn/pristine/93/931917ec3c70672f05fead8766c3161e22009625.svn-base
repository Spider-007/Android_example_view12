package com.htmitech.emportal.ui.detail;

/**
 * Created by htrf-pc on 2016/9/1.
 */
public class CheckForm {

    public CheckForm(String id,String name,String value){
        this.name = name;
        this.value = value;
        this.id = id;
    }

    public String getValue(){
        if(id.equals("") && !value.equals("")){
            return value;
        }else if(value.equals("") && !id.equals("")){
            return id;
        }else if(id.equals("") && value.equals("")){
            return name;
        }
        return "";
    }


    public String name;
    public String value;
    public String id;
}
