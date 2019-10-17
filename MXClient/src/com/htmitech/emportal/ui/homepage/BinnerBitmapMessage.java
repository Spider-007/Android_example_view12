package com.htmitech.emportal.ui.homepage;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.htmitech.proxy.doman.AppInfo;

import java.io.Serializable;

/**
 * Created by htrf-pc on 2016/8/31.
 */
public class BinnerBitmapMessage implements Serializable{
    public long id;
    public Bitmap mBitmap;
    public String appid;
    public String Caption;
    public TextView numberText;
    public String number = "" ;
    public AppInfo appInfo;
    public String compCode;
    public int numberFlag = View.GONE;
    public TextView angle_nulber;
    public String todoFlag = "" ;
    public String modleName;
    /**
     * 栏目是否选中
     * */
    public Integer selected;
    public boolean isCJ = true;

    private BaseAdapter mBaseAdapter;

    public BaseAdapter getmBaseAdapter() {
        return mBaseAdapter;
    }

    public void setmBaseAdapter(BaseAdapter mBaseAdapter) {
        this.mBaseAdapter = mBaseAdapter;
    }

    public BinnerBitmapMessage(Bitmap mBitmap, String appid, String Caption,
                               String FavDisOrder, long id, AppInfo appInfo, String compCode, TextView angle_nulber) {
        this.mBitmap = mBitmap;
        this.appid = appid;
        this.Caption = Caption;
        this.id = id;
        this.appid = appid;
        this.appInfo = appInfo;
        this.compCode = compCode;
        this.angle_nulber = angle_nulber;
//        this.FavDisOrder = Integer.parseInt(FavDisOrder);
    }
    public BinnerBitmapMessage(Bitmap mBitmap, String appid,String Caption,
                               String FavDisOrder,long id,AppInfo appInfo,String compCode) {
        this.mBitmap = mBitmap;
        this.appid = appid;
        this.Caption = Caption;
        this.id = id;
        this.appid = appid;
        this.appInfo = appInfo;
        this.compCode = compCode;
//        this.FavDisOrder = Integer.parseInt(FavDisOrder);
    }



    public BinnerBitmapMessage(Bitmap mBitmap, String appid,String Caption,
                               String FavDisOrder,long id,AppInfo appInfo,String compCode,String todoFlag) {
        this.mBitmap = mBitmap;
        this.appid = appid;
        this.Caption = Caption;
        this.id = id;
        this.appid = appid;
        this.appInfo = appInfo;
        this.compCode = compCode;
        this.todoFlag = todoFlag;
//        this.FavDisOrder = Integer.parseInt(FavDisOrder);
    }

    public BinnerBitmapMessage(Bitmap mBitmap, String appid,String Caption,
                               String FavDisOrder,long id,AppInfo appInfo,String compCode,String todoFlag,String modleName) {
        this.mBitmap = mBitmap;
        this.appid = appid;
        this.Caption = Caption;
        this.id = id;
        this.appid = appid;
        this.appInfo = appInfo;
        this.compCode = compCode;
        this.todoFlag = todoFlag;
        this.modleName = modleName;
//        this.FavDisOrder = Integer.parseInt(FavDisOrder);
    }

    public void setNumber(String number){
        this.number = number;
    }

    public boolean equals(Object obj) {
        if (obj instanceof BinnerBitmapMessage) {
            BinnerBitmapMessage u = (BinnerBitmapMessage) obj;
            return this.appInfo.getApp_id() == u.appInfo.getApp_id();
        }
        return super.equals(obj);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Bitmap getmBitmap() {
        return mBitmap;
    }

    public void setmBitmap(Bitmap mBitmap) {
        this.mBitmap = mBitmap;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getCaption() {
        if(TextUtils.isEmpty(Caption)){
            Caption = "";
        }
        return Caption;
    }

    public void setCaption(String caption) {
        Caption = caption;
    }

    public TextView getNumberText() {
        return numberText;
    }

    public void setNumberText(TextView numberText) {
        this.numberText = numberText;
    }

    public String getNumber() {
        return number;
    }

    public AppInfo getAppInfo() {
        if(appInfo == null){
            appInfo = new AppInfo();
        }
        return appInfo;
    }

    public void setAppInfo(AppInfo appInfo) {
        this.appInfo = appInfo;
    }

    public String getCompCode() {
        return compCode;
    }

    public void setCompCode(String compCode) {
        this.compCode = compCode;
    }

    public int getNumberFlag() {
        return numberFlag;
    }

    public void setNumberFlag(int numberFlag) {
        this.numberFlag = numberFlag;
    }

    public TextView getAngle_nulber() {
        return angle_nulber;
    }

    public void setAngle_nulber(TextView angle_nulber) {
        this.angle_nulber = angle_nulber;
    }

    public String getTodoFlag() {
        return todoFlag;
    }

    public void setTodoFlag(String todoFlag) {
        this.todoFlag = todoFlag;
    }

    public String getModleName() {
        return modleName;
    }

    public void setModleName(String modleName) {
        this.modleName = modleName;
    }

    public Integer getSelected() {
        return selected;
    }

    public void setSelected(Integer selected) {
        this.selected = selected;
    }

    public boolean isCJ() {
        return isCJ;
    }

    public void setCJ(boolean CJ) {
        isCJ = CJ;
    }
}
