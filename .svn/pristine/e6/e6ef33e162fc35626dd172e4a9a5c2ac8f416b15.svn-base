package com.htmitech.emportal.entity;

import com.htmitech.photoselector.model.workflow.FieldItem;

import java.io.Serializable;


public class InfoRegion implements Serializable {

    private static final long serialVersionUID = 1L;

    private int DisplayOrder;
    private String VlineVisible;
    private String RegionID;
    private FieldItem[] fieldItems;
    private int BackColor;

    /**
     * 新增的字段
     */
    private boolean IsTable;
    private String TableID;
    private String ParentTableID;
    private boolean IsSplitRegion;
    private int SplitAction;
    private String ParentRegionID;


    public int getScrollFlag() {
        return ScrollFlag;
    }

    public void setScrollFlag(int scrollFlag) {
        ScrollFlag = scrollFlag;
    }

    public int getScrollFixColCount() {
        return ScrollFixColCount;
    }

    public void setScrollFixColCount(int scrollFixColCount) {
        ScrollFixColCount = scrollFixColCount;
    }

    private int ScrollFlag;//是否为横向滑动的
    private int ScrollFixColCount;//固定几列




    public int getBackColor() {
        return BackColor;
    }


    public void setBackColor(int backColor) {
        this.BackColor = backColor;
    }


    public int getDisplayOrder() {
        return DisplayOrder;
    }


    public void setDisplayOrder(int displayOrder) {
        DisplayOrder = displayOrder;
    }


    public String getVlineVisible() {
        return VlineVisible;
    }


    public void setVlineVisible(String vlineVisible) {
        VlineVisible = vlineVisible;
    }


    public String getRegionID() {
        return RegionID;
    }


    public void setRegionID(String regionID) {
        RegionID = regionID;
    }


    public FieldItem[] getFieldItems() {
        return fieldItems;
    }

    public void setFieldItems(FieldItem[] fieldItems) {
        this.fieldItems = fieldItems;
    }


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public boolean isTable() {
        return IsTable;
    }

    public void setIsTable(boolean isTable) {
        IsTable = isTable;
    }

    public String getTableID() {
        return TableID;
    }

    public void setTableID(String tableID) {
        TableID = tableID;
    }

    public String getParentTableID() {
        return ParentTableID;
    }

    public void setParentTableID(String parentTableID) {
        ParentTableID = parentTableID;
    }

    public boolean isSplitRegion() {
        return IsSplitRegion;
    }

    public void setIsSplitRegion(boolean isSplitRegion) {
        IsSplitRegion = isSplitRegion;
    }

    public int getSplitAction() {
        return SplitAction;
    }

    public void setSplitAction(int splitAction) {
        SplitAction = splitAction;
    }

    public String getParentRegionID() {
        return ParentRegionID;
    }

    public void setParentRegionID(String parentRegionID) {
        ParentRegionID = parentRegionID;
    }

    public String getstr(String Str) {
        String str = Str.trim().equals("anyType{}") ? "" : Str;
        return str;
    }

}
