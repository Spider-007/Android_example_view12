package com.htmitech.htcommonformplugin.entity;

import com.htmitech.photoselector.model.Fielditems;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Think on 2017/4/19.
 */

public class Regions implements Serializable{

    private int displayorder;

    private boolean vlinevisible;

    private String region_id;

    private String region_name;

    private List<Fielditems> fielditems ;

    private boolean is_table;

    private String table_id;

    private String parent_table_id;

    private int region_backcolor;

    private boolean is_split_region;

    private int split_action;

    private String parent_region_id;

    private int scroll_flag;

    private int scroll_fix_col_count;

    public int getDisplayorder() {
        return displayorder;
    }

    public void setDisplayorder(int displayorder) {
        this.displayorder = displayorder;
    }

    public boolean isVlinevisible() {
        return vlinevisible;
    }

    public void setVlinevisible(boolean vlinevisible) {
        this.vlinevisible = vlinevisible;
    }

    public String getRegion_id() {
        return region_id;
    }

    public void setRegion_id(String region_id) {
        this.region_id = region_id;
    }

    public String getRegion_name() {
        return region_name;
    }

    public void setRegion_name(String region_name) {
        this.region_name = region_name;
    }

    public List<Fielditems> getFielditems() {
        return fielditems;
    }

    public void setFielditems(List<Fielditems> fielditems) {
        this.fielditems = fielditems;
    }

    public boolean is_table() {
        return is_table;
    }

    public void setIs_table(boolean is_table) {
        this.is_table = is_table;
    }

    public String getTable_id() {
        return table_id;
    }

    public void setTable_id(String table_id) {
        this.table_id = table_id;
    }

    public String getParent_table_id() {
        return parent_table_id;
    }

    public void setParent_table_id(String parent_table_id) {
        this.parent_table_id = parent_table_id;
    }

    public int getRegion_backcolor() {
        return region_backcolor;
    }

    public void setRegion_backcolor(int region_backcolor) {
        this.region_backcolor = region_backcolor;
    }

    public boolean is_split_region() {
        return is_split_region;
    }

    public void setIs_split_region(boolean is_split_region) {
        this.is_split_region = is_split_region;
    }

    public int getSplit_action() {
        return split_action;
    }

    public void setSplit_action(int split_action) {
        this.split_action = split_action;
    }

    public String getParent_region_id() {
        return parent_region_id;
    }

    public void setParent_region_id(String parent_region_id) {
        this.parent_region_id = parent_region_id;
    }

    public int getScroll_flag() {
        return scroll_flag;
    }

    public void setScroll_flag(int scroll_flag) {
        this.scroll_flag = scroll_flag;
    }

    public int getScroll_fix_col_count() {
        return scroll_fix_col_count;
    }

    public void setScroll_fix_col_count(int scroll_fix_col_count) {
        this.scroll_fix_col_count = scroll_fix_col_count;
    }
}
