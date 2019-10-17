
package com.example.archivermodule.pdfsign;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.example.archivermodule.R;
import com.kinggrid.iapppdf.ui.viewer.PDFHandWriteView;

/**
 * 签名窗口
 * com.kinggrid.iapppdf.demo.SignWindow
 * @author wmm
 * create at 2015年8月21日 下午5:17:31
 */
public class SignDialog implements OnClickListener {
	
	private Context context;
	private Activity activity;
	private PopupWindow signature_popupWindow;
	private Button btn_pen,btn_close,btn_clear,btn_save,btn_redo,btn_undo;
	private PDFHandWriteView sign_handWriteView;
	private String copy_right;
	private OnSignatureListener signatureListener;
	/**
	 * 签名设置框
	 */
	private SignConfigDialog signConfigDialog;
	
	public SignDialog(Activity activity, String copyRight){
		this.context = activity;
		this.copy_right = copyRight;
		this.activity = activity;
	}

	public void show(){
		View popop_view_layout  = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.signature_kinggrid, null);
		initSignInfo(popop_view_layout);
		signature_popupWindow = new PopupWindow(popop_view_layout, 1200, 800);
		signature_popupWindow.setAnimationStyle(android.R.style.Widget_PopupWindow);
		signature_popupWindow.setOutsideTouchable(true);
		signature_popupWindow.setFocusable(true);
		signature_popupWindow.showAtLocation(popop_view_layout, Gravity.CENTER, 0, 0);
		
	}
	/**
	 * 
	 */
	public void pause(){
		if(signConfigDialog != null){
			signConfigDialog.dismiss();
		}
	}
	/**
	 * 
	 */
	public void dismiss(){
		pause();
		//关闭dialog
		if (signature_popupWindow != null) {
			signature_popupWindow.dismiss();
		}
	}
	
	private void initSignInfo(View layout){
		sign_handWriteView = (PDFHandWriteView) layout.findViewById(R.id.v_canvas);
		sign_handWriteView.setCopyRight(activity, copy_right);
		signConfigDialog = new SignConfigDialog(context, sign_handWriteView,"sign_size","sign_color","sign_type",50);
		//设置默认画笔
		sign_handWriteView.setPenInfo(signConfigDialog.getPenMaxSizeFromXML("sign_size"), 
				signConfigDialog.getPenColorFromXML("sign_color"), signConfigDialog.getPenTypeFromXML("sign_type"));
		// 笔型
		btn_pen = (Button) layout.findViewById(R.id.btn_pen);
		btn_pen.setOnClickListener(this);
		// 关闭
		btn_close = (Button) layout.findViewById(R.id.btn_close);
		btn_close.setOnClickListener(this);
		// 清屏
		btn_clear = (Button) layout.findViewById(R.id.btn_clear);
		btn_clear.setOnClickListener(this);
		// 保存
		btn_save = (Button) layout.findViewById(R.id.btn_save);
		btn_save.setOnClickListener(this);
		// 重做
		btn_redo = (Button) layout.findViewById(R.id.btn_redo);
		btn_redo.setOnClickListener(this);
		// 撤销
		btn_undo = (Button) layout.findViewById(R.id.btn_undo);
		btn_undo.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		int i = v.getId();
		if (i == R.id.btn_pen) {//			new SignConfigDialog(context, sign_handWriteView,"demo_penSize","demo_penColor","demo_penType",RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT,50);
			signConfigDialog.showSettingWindow(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

		} else if (i == R.id.btn_save) {
			if (!sign_handWriteView.canSave()) {
				Toast.makeText(context, "没有签批内容，无需保存！",
						Toast.LENGTH_SHORT).show();
				return;
			}
			// 保存
			if (signatureListener != null) {
				signatureListener.onSignatureSave(sign_handWriteView);
			}

		} else if (i == R.id.btn_redo) {// 重做
			sign_handWriteView.doRedoHandwriteInfo();

		} else if (i == R.id.btn_undo) {// 撤销
			sign_handWriteView.doUndoHandwriteInfo();

		} else if (i == R.id.btn_close) {//关闭
			if (signature_popupWindow != null) {
				signature_popupWindow.dismiss();
			}

		} else if (i == R.id.btn_clear) {// 清屏
			sign_handWriteView.doClearHandwriteInfo();

		}
	}
	
	/** 
	 * 设置签名监听
	 */
	public void setSignatureListener(OnSignatureListener signatureListener) {
		this.signatureListener = signatureListener;
	}
	
	public interface OnSignatureListener{
		public void onSignatureSave(PDFHandWriteView pdfHandWriteView);
	}
	
	
}

