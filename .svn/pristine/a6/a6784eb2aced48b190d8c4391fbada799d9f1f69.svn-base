package com.htmitech.ztcustom.zt.unit;

import android.content.Context;
import android.widget.Toast;

import com.htmitech.ztcustom.zt.dialog.ActionSheetDialog;
import com.minxing.kit.api.MXAPI;
import com.minxing.kit.api.bean.ShareLink;

import java.util.ArrayList;

public class ShareUnit {
    public static int curItem = 0;
    //intentFlag 表示 要分享页面  WW VV UU

    static public void ShareListener(final Context context, String title, String iconId, final String appUrl, final String intentFlag) {
        // 设置分享参数
        final ShareLink shareLink = new ShareLink();
        shareLink.setTitle("" + title);
        shareLink.setDesc("分享伤损平台");
        shareLink.setThumbnail(iconId);
        ArrayList<String> itemName = new ArrayList<String>();
        itemName.add("联系人");
        itemName.add("工作圈");
        new ActionSheetDialog(context)
                .builder()
                .setTitle("请选择分享位置")
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .addSheetItem(itemName)
                .setOnSheetItemClickListener(
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(String code) {
                                if (code == null) {
                                    Toast.makeText(context, "请选择分享方式！", Toast.LENGTH_SHORT)
                                            .show();
                                    return;
                                }
                                String apiUrls = "";
                                if (code.equals("联系人")) {
                                    apiUrls = intentFlag + appUrl;
                                    shareLink.setAppUrl(apiUrls);
                                    MXAPI.getInstance(context).shareToChat(context, shareLink);
                                } else {
                                    apiUrls = intentFlag + appUrl;
                                    shareLink.setAppUrl(apiUrls);
                                    MXAPI.getInstance(context).shareToCircle(context, shareLink);
                                }
                            }
                        }).show();
    }
}
