package com.htmitech.unit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;

import mobilereport.com.chatkit.domain.TextStyle;
import mobilereport.com.chatkit.util.WaterMarkTextUtil;

public class ImageUtil {

	private static BitmapDrawable drawable;

	/**
	 * 生成水印文字图片
	 */
	public static BitmapDrawable drawTextToBitmap(Context gContext, String gText) {
		TextStyle textStyle = new TextStyle();
		textStyle.setColor("#EAEAEA");
		textStyle.setFontSize(24);
		return WaterMarkTextUtil.drawTextToBitmap(gContext,gText,1,textStyle,Color.WHITE);
	}
}
