package htmitech.com.componentlibrary.unit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;

/**
 * 图片工具类。
 * 
 * @author zhanghuimin
 * 
 */
public class DrawableUtils {

	private DrawableUtils() {

	}

	/**
	 * 转换Drawable成Bitmap。
	 * 
	 * @param drawable
	 *            drawable
	 * @return bitmap
	 */

	public static Bitmap convertDrawableToBitmap(Drawable drawable) {
		if (drawable instanceof BitmapDrawable) {
			return ((BitmapDrawable) drawable).getBitmap();
		}

		int width = drawable.getIntrinsicWidth();
		int height = drawable.getIntrinsicHeight();
		Bitmap bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, width, height);
		drawable.draw(canvas);
		return bitmap;
	}

	/**
	 * create rounded corner bitmap.
	 * 
	 * @param bitmap
	 *            bitmap
	 *            radius
	 * @return round corner bitmap;
	 */
	public static Bitmap roundCornered(Bitmap bitmap, int radiusx, int radiusy) {

		if (bitmap == null) {
			return null;
		}
		Bitmap result = null;
		
		try {
			result = Bitmap.createBitmap(bitmap.getWidth(),
					bitmap.getHeight(), Config.ARGB_8888);
		} catch (OutOfMemoryError e) {
			result = bitmap;
		}
		
		if (result == null) {
			return bitmap;
		}
		
		Canvas canvas = new Canvas(result);

		Paint paint = new Paint();
		Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		RectF rectF = new RectF(rect);
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		canvas.drawRoundRect(rectF, radiusx, radiusy, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return result;
	}

	public static Bitmap convertGreyImg(Bitmap img) {

		int width = img.getWidth(); // 获取位图的宽
		int height = img.getHeight(); // 获取位图的高

		int[] pixels = new int[width * height]; // 通过位图的大小创建像素点数组

		img.getPixels(pixels, 0, width, 0, 0, width, height);
		int alpha = 0xFF << 24;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				int grey = pixels[width * i + j];

				int red = ((grey & 0x00FF0000) >> 16);
				int green = ((grey & 0x0000FF00) >> 8);
				int blue = (grey & 0x000000FF);

				grey = (int) ((float) red * 0.3 + (float) green * 0.59 + (float) blue * 0.11);
				grey = alpha | (grey << 16) | (grey << 8) | grey;
				pixels[width * i + j] = grey;
			}
		}
		Bitmap result = Bitmap.createBitmap(width, height, Config.RGB_565);
		result.setPixels(pixels, 0, width, 0, 0, width, height);
		return result;

	}

	public static LayerDrawable getLayerDrawable(Context context, int color) {
		//创建Drawable
		GradientDrawable gradientDrawable = getGraDra(context, color, 0);
//        GradientDrawable grayDrawable =getGraDra(context, color, 0);
		LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{gradientDrawable});
		//设置padding
		layerDrawable.setLayerInset(0, DensityUtil.dip2px(context, -1), DensityUtil.dip2px(context, -1), 0, DensityUtil.dip2px(context, -1));
		//设置drawable为背景
//        ViewCompat.setBackground(acTvInfo,layerDrawable);
		return layerDrawable;
	}

	public static GradientDrawable getGraDra(Context context, int color, float dp) {
		GradientDrawable drawable = new GradientDrawable();
		drawable.setColor(SysConvertUtil.formattingH(color));
		drawable.setStroke(DensityUtil.dip2px(context, 1), Color.parseColor("#B4B4B4"));
		drawable.setCornerRadius(DensityUtil.dip2px(context, dp));
		return drawable;
	}
}
