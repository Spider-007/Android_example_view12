package com.example.archivermodule.pdfsign;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;


import com.example.archivermodule.R;
import com.kinggrid.iapppdf.company.common.KinggridConstant;
import com.kinggrid.iapppdf.ui.viewer.IAppPDFActivity;
import com.kinggrid.pdfservice.Annotation;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 文字批注的弹出界面
 * 
 * @author Kevin <br/>
 *         create at 2013-10-23 上午11:29:09
 */
public class TextAnnotDialog implements KinggridConstant, OnClickListener {

	public static final String TAG = "TextAnnotDialog";
    public PopupWindow popupWindow;
    public Context context;
    protected Annotation annotOld;
    protected View view;

    protected Button annot_save = null;
    protected ImageButton annot_delete = null;
    protected Button annot_close = null;

    protected TextView annotAuthor;
    protected TextView annotModifyTime;
    protected EditText annotContent;

    protected OnSaveAnnotListener saveAnnotListener = null;
    protected OnCloseAnnotListener closeAnnotListener = null;
    protected OnDeleteAnnotListener deleteAnnotListener = null;

    /**
     * 构造函数
     * 
     * @param activity
     * @param width
     * @param height
     * @param annotation
     */
    public TextAnnotDialog(Context context, final Annotation annotation) {
        this.context = context;
        this.annotOld = annotation;
        initWindow();
        
        show();
    }

    /**
     * 初始化布局
     * 
     * @param width
     * @param height
     */
	public void initWindow() {
		view = LayoutInflater.from(context).inflate(R.layout.eben_annot_layout,
				null);
		annotContent = (EditText) view.findViewById(R.id.annot_text);
		
		if(annotOld != null){
			annotAuthor = (TextView) view.findViewById(R.id.annot_author);
			annotAuthor.setText(annotOld.getAuthorName());
			annotModifyTime = (TextView) view.findViewById(R.id.annot_modify_time);
			annotModifyTime.setText(annotOld.getCurDateTime());
			annotContent.setText(annotOld.getAnnoContent());
		}else{
			annotContent.setBackgroundColor(Color.TRANSPARENT);
		}
		
		// 关闭
		annot_close = (Button) view.findViewById(R.id.annot_close);
		annot_close.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				closeAnnotWindow();
			}
		});

		// 保存
		annot_save = (Button) view.findViewById(R.id.annot_save);
		annot_save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				saveAnnot(annotContent.getText().toString());
			}
		});

		// 删除
		annot_delete = (ImageButton) view.findViewById(R.id.annot_delete);
		annot_delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(annotOld != null){
					if (annotOld.getAuthorName().equals(IAppPDFActivity.userName)) {
						deleteAnnot();
					} else {
						Toast.makeText(context, R.string.username_different_del,
								Toast.LENGTH_LONG).show();
					}
				}
				
			}
		});

		if(annotOld != null){
			if (!annotAuthor.getText().toString().equals(IAppPDFActivity.userName)) {
				annot_delete.setVisibility(View.GONE);
			}

			if (!annotAuthor.getText().toString().equals(IAppPDFActivity.userName)) {
				annotContent.setEnabled(false);
				
			}
		}
		

		popupWindow = new PopupWindow(view, (int) (594 * IAppPDFActivity.densityCoefficient), (int) (373 * IAppPDFActivity.densityCoefficient));
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(false);
		
	}

    /**
     * 显示文字批注PopupWindow
     * 
     * @param parent
     * @param offsetX
     * @param offsetY
     */
    public void show() {
         popupWindow.showAtLocation(view, Gravity.CENTER_HORIZONTAL, 0, 0);
         popupWindow.update();
         annotContent.requestFocus();
         /*if(annotContent.isEnabled()){
         	annotContent.post(new Runnable(){

 				@Override
 				public void run() {
 					// TODO Auto-generated method stub
 					InputMethodManager im = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
 		            im.showSoftInput(annotContent,0);
 				}
             	
             });
         }*/
    }

    /**
     * 隐藏删除按钮
     */
    public void setDeleteBtnGone() {
        if (annot_delete != null && annot_delete.getVisibility() == View.VISIBLE) {
            annot_delete.setVisibility(View.GONE);
        }
    }
    
    
    @Override
    public void onClick(final View v) {


    }

    /**
     * 删除批注
     */
    private void deleteAnnot() {
    	if(deleteAnnotListener != null){
    		deleteAnnotListener.onAnnotDelete();
    	}
        closeAnnotWindow();
    }

    /**
     * 保存批注
     * 
     * @param annotTextNew
     *            批注内容
     */
    private void saveAnnot(final String annotTextNew) {
    	if(annotOld != null){
    		if (!annotTextNew.equals(annotOld.getAnnoContent()) && !annotTextNew.equals("")) {
                annotOld.setAnnoContent(annotTextNew);
                final long dateTaken = System.currentTimeMillis();
                annotOld.setCurDateTime(DateFormat.format("yyyy-MM-dd kk:mm:ss", dateTaken).toString());
                if(saveAnnotListener != null){
                	saveAnnotListener.onAnnotSave(annotOld);
                }
            } else if(annotTextNew.equals("")){
            	Toast.makeText(context, "没有注释内容", Toast.LENGTH_LONG).show();
            }else if(annotTextNew.equals(annotOld.getAnnoContent())){
            	Toast.makeText(context, "注释内容未修改", Toast.LENGTH_LONG).show();
            }
            closeAnnotWindow();
    	}else{
    		Bitmap valid_word_bitmap = saveValidWord();
    		
        	
    		if (valid_word_bitmap != null) {
    			closeAnnotWindow();
    			if(saveAnnotListener != null){
    				String time = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                	saveAnnotListener.onAnnotSave(valid_word_bitmap, time);
                }
    		} else {
    			Log.e(TAG, "签批内容为空");
    		}
    	}
        
    }
    
    private Bitmap saveValidWord(){
		Bitmap bitmap = null;
		if(!TextUtils.isEmpty(annotContent.getText().toString())){
			annotContent.setCursorVisible(false);
			Bitmap cache = BitmapUtil.getInstance().getViewCacheBitmap(annotContent);
			if(cache != null){
				bitmap = BitmapUtil.getInstance().cutImage(cache,false);
			}
		}
		return bitmap;
	}

    /**
     * 关闭文字批注窗口
     */
    private void closeAnnotWindow() {
    	InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
    	if(imm.isActive()){
    		imm.hideSoftInputFromWindow(annotContent.getWindowToken(), 0);//强制隐藏输入法
    	}
    	
        if (popupWindow != null) {
        	if(closeAnnotListener != null){
        		closeAnnotListener.onAnnotClose();
        	}
            Intent intent = new Intent();
			intent.setAction("com.kinggrid.annotation.close");
			context.sendBroadcast(intent);
            popupWindow.dismiss();
            popupWindow = null;
            view.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    /**
     * 设置删除注释监听
     * 
     * @param deleteAnnotListener
     *            监听接口
     */
    public void setDeleteAnnotListener(final OnDeleteAnnotListener deleteAnnotListener) {
        this.deleteAnnotListener = deleteAnnotListener;
    }

    /**
     * 设置保存注释监听
     * 
     * @param saveAnnotListener
     *            监听接口
     */
    public void setSaveAnnotListener(final OnSaveAnnotListener saveAnnotListener) {
        this.saveAnnotListener = saveAnnotListener;
    }

    /**
     * 设置关闭注释监听
     * 
     * @param closeAnnotListener
     *            监听接口
     */
    public void setCloseAnnotListener(final OnCloseAnnotListener closeAnnotListener) {
        this.closeAnnotListener = closeAnnotListener;
    }

    /**
     * 保存事件通知
     * 
     * @author mmwan
     * 
     */
    public interface OnSaveAnnotListener {

        public void onAnnotSave(Annotation annotTextNew);
        
        public void onAnnotSave(Bitmap bitmap, String time);

    }

    /**
     * 删除批注接口
     * 
     * @author mmwan
     * 
     */
    public interface OnDeleteAnnotListener {

        public void onAnnotDelete();

    }

    /**
     * 关闭批注窗口监听
     * 
     * @author mmwan
     * 
     */
    public interface OnCloseAnnotListener {

        public void onAnnotClose();

    }

}
