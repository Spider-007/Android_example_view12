package com.example.archivermodule.pdfsign;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.view.View;

public class BitmapUtil {

	private static BitmapUtil bitmapUtil;
	
	
	/**
     * 时间戳相关设置
     */
    private int time_textSize = 32;
	private int time_textColor = Color.BLACK;
	private Align time_textAlign = Align.CENTER;
	private int timeTextWidth = 250;
	private int timeTextHeight = 15;
	private int timeTextSpace = 18;
	private int timeTextBottomSpace = 5;
	
	/**
   	 * 签批时间戳文字
   	 */
    private String stampText;
    
	private BitmapUtil(){
		
	}
	
	public static BitmapUtil getInstance(){
		if(bitmapUtil == null){
			bitmapUtil = new BitmapUtil();
		}
		return bitmapUtil;
	}
	
	/**
	 * 设置签批时间戳文字，默认:author time
	 * @param text
	 */
	public void setStampText(String text){
		this.stampText = text;
	}
	/**
	 * 设置签批时间戳与底部的间距，默认为5dp
	 * @param space 要设置的间距
	 */
	public void setStampTextBottomSpace(int space){
		this.timeTextBottomSpace = space;
	}
	/**
	 * 设置时间戳信息
	 * @param timeTextWidth
	 * @param timeTextHeight
	 * @param timeTextSpace
	 * @param timeTextSize
	 * @param timeTextColor
	 * @param timeAlign
	 */
	public void setTimeTextInfo(int timeTextWidth,int timeTextHeight,int timeTextSpace,int timeTextSize,int timeTextColor,Align timeAlign){
		this.timeTextWidth = timeTextWidth;
		this.timeTextHeight = timeTextHeight;
		this.timeTextSpace = timeTextSpace;
		this.time_textSize = timeTextSize;
		this.time_textColor = timeTextColor;
		this.time_textAlign = timeAlign;
	}
	
	
	/**
	 * 裁切Bitmap空白区域
	 * @param bitmap 源图
	 * @param bgWhite 是否绘制背景为白底
	 * @return
	 */
	public Bitmap cutImage(Bitmap bitmap, boolean bgWhite) {
		if (bitmap.getWidth() > 0 && bitmap.getHeight() > 0) {
			int left = 0;
			int top = 0;
			int right = bitmap.getWidth() - 1;
			int bottom = bitmap.getHeight() - 1;

			for (int i = 0; i < bitmap.getWidth(); i++) {
				int pix = 0;
				for (int j = 0; j < bitmap.getHeight(); j++) {
					if (bitmap.getPixel(i, j) != 0) {
						pix = bitmap.getPixel(i, j);
						break;
					}
				}
				if (pix != 0)
					break;
				left = i;
			}
			for (int i = 0; i < bitmap.getHeight(); i++) {
				int pix = 0;
				for (int j = 0; j < bitmap.getWidth(); j++) {
					if (bitmap.getPixel(j, i) != 0) {
						pix = bitmap.getPixel(j, i);
						break;
					}
				}
				if (pix != 0)
					break;
				top = i;
			}
			for (int i = bitmap.getWidth() - 1; i >= 0; i--) {
				int pix = 0;
				for (int j = 0; j < bitmap.getHeight(); j++) {
					if (bitmap.getPixel(i, j) != 0) {
						pix = bitmap.getPixel(i, j);
						break;
					}
				}
				if (pix != 0)
					break;
				right = i;
			}
			for (int i = bitmap.getHeight() - 1; i >= 0; i--) {
				int pix = 0;
				for (int j = 0; j < bitmap.getWidth(); j++) {
					if (bitmap.getPixel(j, i) != 0) {
						pix = bitmap.getPixel(j, i);
						break;
					}
				}
				if (pix != 0)
					break;
				bottom = i;
			}
			if ((right - left) > 0 && (bottom - top) > 0) {
				if(bgWhite){
					Bitmap newBitmap = null;
					Bitmap bmp = Bitmap.createBitmap(bitmap, left, top, right - left,
							bottom - top);
					newBitmap = Bitmap.createBitmap(bmp.getWidth(),
							bmp.getHeight(), bmp.getConfig());
					Canvas canvas = new Canvas(newBitmap);
					canvas.drawColor(Color.WHITE);
					canvas.drawBitmap(bmp, 0, 0, null);
					bmp.recycle();
					return newBitmap;
				}else{
					return Bitmap.createBitmap(bitmap, left, top, right - left,
							bottom - top);
				}
				
			} else {
				return null;
			}

		} else {
			return null;
		}

	}
	
	/**
	 * 保存View的缓存图片
	 * @param view
	 * @return
	 */
	public Bitmap getViewCacheBitmap(View view){
		view.setDrawingCacheEnabled(true);
		Bitmap bitmap_cache = view.getDrawingCache();
		return bitmap_cache;
	}
	
	public Bitmap getStampBitmap(Bitmap src, Bitmap userName, String time){
		Bitmap result_bmp = null;
		Bitmap stamp_bmp = null;
		if (src != null) {
			Bitmap time_bmp = getTimeBitmap(src.getWidth(), 0, time, userName);
			//stamp_bmp = spliceLandBitmap(userName, time_bmp, false);
			result_bmp = spliceBitmap(src, time_bmp, false);
		}
		
		return result_bmp;
	}
	
	/**
	 * 获取签名戳图片
	 * @param parent_width
	 * @param parent_height
	 * @param text
	 * @return
	 */
	private Bitmap getTimeBitmap(int parent_width, int parent_height, String time, Bitmap nameBitmap){
		int stamp_width = 0;
		int height = 0;
		
		timeTextWidth = time_textSize * time.length() + nameBitmap.getWidth();
		//如果时间戳比图片宽，就以时间戳宽度为准
		if(timeTextWidth > parent_width){
			stamp_width = timeTextWidth; 
		} else {
			stamp_width = parent_width;
		}
		
		if(nameBitmap.getHeight() > timeTextHeight){
			height = nameBitmap.getHeight();
		}else{
			height = timeTextHeight;
		}
		//timeTextSpace = 0;

		Bitmap time_bitmap = makeTextBitmap(stamp_width,
				parent_height + timeTextSpace + height + timeTextBottomSpace, time, nameBitmap);
		
		return time_bitmap;
	}
	
	/**
     * 竖向拼接图片
     * @param first_bitmap
     * @param second_bitmap
     * @param bgWhite 背景色是否为白色
     * @return
     */
    public Bitmap spliceBitmap(Bitmap first_bitmap, Bitmap second_bitmap, boolean bgWhite){
		int new_width = (first_bitmap.getWidth() > second_bitmap.getWidth() ? first_bitmap
 				.getWidth() : second_bitmap.getWidth());
		int new_height = first_bitmap.getHeight() + second_bitmap.getHeight();
		Bitmap new_bitmap = Bitmap.createBitmap(new_width,new_height, Config.ARGB_8888);
		
		Canvas canvas = new Canvas(new_bitmap);
		if(bgWhite){
 			canvas.drawColor(Color.WHITE);
 		}
 		canvas.drawBitmap(first_bitmap, 0, 0, null);// 在 0，0坐标开始画入原图片src
 		float left = 0;
 		float res = first_bitmap.getWidth() - second_bitmap.getWidth();
 		if(res > 0 ){
 			left = res;
 		}
 		canvas.drawBitmap(second_bitmap, left, first_bitmap.getHeight(), null);

 		canvas.save(Canvas.ALL_SAVE_FLAG);
 		canvas.restore();
 		
 		first_bitmap.recycle();
 		second_bitmap.recycle();
		return new_bitmap;
	}
	
	
	/**
     * 横向拼接图片
     * @param first_bitmap
     * @param second_bitmap
     * @param bgWhite 背景色是否为白色
     * @return
     */
    public Bitmap spliceLandBitmap(Bitmap first_bitmap, Bitmap second_bitmap, boolean bgWhite){
    	int new_width = first_bitmap.getWidth() + second_bitmap.getWidth();
		int new_height = first_bitmap.getHeight() > second_bitmap.getHeight() ? first_bitmap.getHeight() : second_bitmap.getHeight();
		Bitmap new_bitmap = Bitmap.createBitmap(new_width,new_height, Config.ARGB_8888);
		
		Canvas canvas = new Canvas(new_bitmap);
		if(bgWhite){
 			canvas.drawColor(Color.WHITE);
 		}
 		canvas.drawBitmap(first_bitmap, 0, 0, null);// 在 0，0坐标开始画入原图片src
 		canvas.drawBitmap(second_bitmap, first_bitmap.getWidth(), 0, null);

 		canvas.save(Canvas.ALL_SAVE_FLAG);
 		canvas.restore();
 		
 		first_bitmap.recycle();
 		second_bitmap.recycle();
		return new_bitmap;
	}
    
	/**
	 * 组合两张图片
	 * 
	 * @param src
	 *            源图片
	 * @param oterhBitmap
	 *            其他图片
	 * @return 合成图片
	 */
     private Bitmap groupBitmap(Bitmap src, Bitmap oterhBitmap, boolean isBgWhite, float[] src_xy, float[] other_xy) {
    	 int new_bitmap_width, new_bitmap_height;
 		new_bitmap_width = (src.getWidth() > oterhBitmap.getWidth() ? src
 				.getWidth() : oterhBitmap.getWidth());
 		new_bitmap_height = (src.getHeight() > oterhBitmap.getHeight() ? src
 				.getHeight() : oterhBitmap.getHeight());
 		Bitmap new_bitmap = Bitmap.createBitmap(new_bitmap_width, new_bitmap_height,
 				Config.ARGB_8888);
 		Canvas canvas = new Canvas(new_bitmap);
 		if(isBgWhite){
 			canvas.drawColor(Color.WHITE);
 		}
 		canvas.drawBitmap(src, src_xy[0], src_xy[1], null);
 		canvas.drawBitmap(oterhBitmap, other_xy[0], other_xy[1], null);

 		canvas.save(Canvas.ALL_SAVE_FLAG);
 		canvas.restore();

 		return new_bitmap;
	}
     
     /**
      * 生成时间戳
      * @param width
      * @param height
      * @param text
      * @return
      */
     private Bitmap makeTextBitmap(int width, int height, String text, Bitmap nameStampbitmap) {
    	 if(width > 0 && height > 0){
   			Bitmap text_bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
   			Canvas canvasTemp = new Canvas(text_bitmap);
   			canvasTemp.drawColor(Color.TRANSPARENT);
   			Paint p = new Paint();
   			p.setAntiAlias(true);
   			p.setColor(time_textColor);
   			p.setTextSize(time_textSize);
   			p.setTextAlign(time_textAlign);
   			if(time_textAlign == Align.LEFT){
   				
   				if(nameStampbitmap != null){
   					
   					canvasTemp.drawBitmap(nameStampbitmap, 0, height - timeTextBottomSpace - nameStampbitmap.getHeight(), p);
   					canvasTemp.drawText(text, nameStampbitmap.getWidth() , height - timeTextBottomSpace - (nameStampbitmap.getHeight() / 2), p);
   					
   				} else {
   					canvasTemp.drawText(text, 0, height - timeTextBottomSpace, p);
   				}
   			} else if(time_textAlign == Align.CENTER){
   				
   				if(nameStampbitmap != null){
   					
   					canvasTemp.drawBitmap(nameStampbitmap, width / 2 - nameStampbitmap.getWidth(), height - timeTextBottomSpace - nameStampbitmap.getHeight(), p);
   					canvasTemp.drawText(text, (width / 2) + nameStampbitmap.getWidth(), height - timeTextBottomSpace/* - nameStampbitmap.getHeight() / 2*/, p);
   					
   				} else {
   					canvasTemp.drawText(text, width/2, height - timeTextBottomSpace, p);
   				}
   			} else if(time_textAlign == Align.RIGHT){
   				
   				if(nameStampbitmap != null){
   					
   					canvasTemp.drawBitmap(nameStampbitmap, width - nameStampbitmap.getWidth() - (timeTextWidth / 2), height - timeTextBottomSpace - nameStampbitmap.getHeight(), p);
   					canvasTemp.drawText(text, width, height - timeTextBottomSpace - nameStampbitmap.getHeight() / 2, p);
   					
   				} else {
   					canvasTemp.drawText(text, width, height - timeTextBottomSpace, p);
   				}
   			}
   			
   			return text_bitmap;
   		}
   		return null;
  	}
}
