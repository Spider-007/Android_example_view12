package com.htmitech.htworkflowformpluginnew.listener;

import android.content.Context;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.htmitech.emportal.ui.homepage.BinnerBitmapMessage;
import com.htmitech.emportal.ui.homepage.HomeGridSysle;
import com.htmitech.htworkflowformpluginnew.entity.DocSearchParameters;
import com.htmitech.proxy.doman.AppInfo;
import com.minxing.client.tab.MenuTabItem;

/**
 * Created by Think on 2017/7/19.
 */

public interface IWorkFlowCountCallBack {
     void ShowNumber(AppInfo mAppInfo, TextView textNumber, Context context, DocSearchParameters docSearchParameters, BaseAdapter baseAdapter);
     void ShowNumber(BinnerBitmapMessage mBinnerBitmapMessage, Context context, DocSearchParameters docSearchParameters);
     void ShowNumber(HomeGridSysle.BinnerBitmapMessage mBinnerBitmapMessage, Context context, DocSearchParameters docSearchParameters, BaseAdapter baseAdapter);
     void ShowNumber(MenuTabItem homeTabItem, Context context, DocSearchParameters docSearchParameters);
     void ShowNumber(BinnerBitmapMessage mBinnerBitmapMessage, Context context, DocSearchParameters docSearchParameters, BaseAdapter baseAdapter);

}
