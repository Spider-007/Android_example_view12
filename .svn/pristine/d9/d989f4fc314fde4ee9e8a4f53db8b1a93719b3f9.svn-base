package com.htmitech.MyView.barline;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;


/**
 * Created by htrf-pc on 2017/1/4.
 */
public class WaterMarkTextUtil {
    //暂时最好都是进行平铺的
    //设置背景
    public static void setWaterMarkTextBg(View view, Context gContext, String gText, int displayType, TextStyle mTextStyle) {
        view.setBackgroundDrawable(drawTextToBitmap(gContext, gText, displayType, mTextStyle, Color.WHITE));
    }

    /**
     * 生成水印文字图片
     */
    public static BitmapDrawable drawTextToBitmap(Context gContext, String gText, int displayType, TextStyle mTextStyle, int backColor) {

        gText = gText;
//        if (ACache.get(gContext).getAsBitmap(gText) != null) {
//            BitmapDrawable drawable = new BitmapDrawable(ACache.get(gContext).getAsBitmap(gText));
//            drawable.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
//            drawable.setDither(true);
//            return drawable;
//        }
        WindowManager mWindowManager = (WindowManager) gContext.getSystemService(Context.WINDOW_SERVICE);

        int width = mWindowManager.getDefaultDisplay().getWidth();
        if (mTextStyle == null) {
            mTextStyle = new TextStyle();
            mTextStyle.setColor("#CCCCCC");
            mTextStyle.setFontSize(30);
            mTextStyle.setAlign("center");
        }

        DisplayMetrics metric = new DisplayMetrics();
        int widthPixels = metric.widthPixels;
        BitmapDrawable drawable =null;
        try {
            Bitmap bitmap = Bitmap.createBitmap(width / 2, 550, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            canvas.drawColor(backColor);
            Paint paint = new Paint();
            paint.setColor(Color.parseColor(mTextStyle.getColor()));
            paint.setColor(Color.BLACK);
            paint.setAlpha(80);
            paint.setAntiAlias(true);
            paint.setTextAlign(Paint.Align.CENTER);
            if (mTextStyle.getFontWeight().equalsIgnoreCase("bold")) {
                Typeface font = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD);
                paint.setTypeface(font);
            }
            float scaledDensity = gContext.getResources().getDisplayMetrics().scaledDensity;
            paint.setTextSize(scaledDensity * mTextStyle.getFontSize() + 0.5f * (mTextStyle.getFontSize() >= 0 ? 1 : -1));
            Path path = new Path();
            path.moveTo(0, 550);
            path.lineTo(width / 2, 0);
            canvas.drawTextOnPath(gText, path, 0, 20, paint);
            canvas.save(Canvas.ALL_SAVE_FLAG);
            canvas.restore();
//            ACache.get(gContext).put(gText, bitmap);
            drawable=new BitmapDrawable(bitmap);
            switch (displayType) {
                case 0:
                    drawable.setTileModeXY(Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
                    break;
                case 1:
                    drawable.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
                    break;
            }

            drawable.setDither(true);
            return drawable;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return drawable;

    }
}
