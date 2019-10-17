package com.htmitech.commonx.base.view.combo;




import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.ztcustom.R;


public class ComboBox extends EditText{
	private int buttonwidth;
	private boolean isEditable = true ;
	private IValueChagedEvent valuechanged;
	public void setValuechanged(IValueChagedEvent valuechanged) {
		this.valuechanged = valuechanged;
	}

	public ComboBox(Context paramContext) {
		super(paramContext);
	}

	public void setIsEditable(boolean  isEditable){
		this.isEditable = isEditable ;
		this.setCursorVisible(false);
		this.setInputType(InputType.TYPE_NULL);//禁止输入法
	}
	
	@SuppressWarnings("deprecation")
	public ComboBox(Context paramContext, AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
		Bitmap localBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.btn_matterform_selected);
		BitmapDrawable localBitmapDrawable = new BitmapDrawable(localBitmap);
		int i = localBitmap.getWidth();
		int j = localBitmap.getHeight();
		buttonwidth = 30;
		if (buttonwidth <= 5) {
			buttonwidth = buttonwidth * 2;
		}
		localBitmapDrawable.setBounds(0, 0, 30, j);
		setCompoundDrawables(null, null, localBitmapDrawable, null);
	}
	
	public void setLinkImage(int imageresid) {
		BitmapDrawable localBitmapDrawable1 = (BitmapDrawable) getResources()
				.getDrawable(imageresid);
		Bitmap localBitmap1 = localBitmapDrawable1.getBitmap();
		int k = localBitmap1.getWidth();
		int m = localBitmap1.getHeight();
		localBitmapDrawable1.setBounds(0, 0, k, m);
		setCompoundDrawables(null, null, localBitmapDrawable1, null);

	}

	// public void a(aj paramaj)
	// {
	// this.a = paramaj;
	// }

	public boolean onTouchEvent(MotionEvent paramMotionEvent) {
		paramMotionEvent.getRawX();
		paramMotionEvent.getRawY();

		float f1 = paramMotionEvent.getX();
		paramMotionEvent.getY();
		if(!isEditable){ // 不可编辑状态
			if (f1 > 0 ) {
				if (f1 < getWidth()) {
					if (popupWindow != null) {
						if(popupWindow.isShowing()){
							closePopupWindow();
						}else{
							popupWindow.showAsDropDown(this);
							popupWindow.update();
							this.requestFocus();
						}

					}
				}
				return false;
			} else {
				closePopupWindow();
			}
			return true ;
		}

		float f2 = getWidth() - buttonwidth;
		if (f1 > f2) {
			float f3 = getWidth();
			if (f1 < f3) {
				if (popupWindow != null) {
					if(popupWindow.isShowing()){
						closePopupWindow();
					}else{
						popupWindow.showAsDropDown(this);
						popupWindow.update();
						this.requestFocus();
					}
				}
			}
			return false;
		} else {
			closePopupWindow();
		}

		return super.onTouchEvent(paramMotionEvent);
	}

	private PopupWindow popupWindow = null;
	private String[] option = null;

	public String[] getOption(){
		return option ;
	}
	
	public void setOption(String... options) {
		option = options;
		setPopupWindowValues();
	}
	
	public void setMyOption(String[] options) {
		option = options;
		setPopupWindowValues();
	}
	
	private String[] value = null;
	
	public void setMyValue(String[] value) {
		this.value = value;
	}
	
	private String[] ids = null;
	
	public void setMyIds(String[] ids) {
		this.ids = ids;
	}

	private LinearLayout c_ll = null;

	private void creatPopupWindow(int windth, int height) {
		// if popupWindow is null then initialize it
		if (popupWindow == null) {
			// get layout inflater from system service of
			// LAYOUT_INFLATER_SERVICE

			ScrollView c_sv = new ScrollView(this.getContext());
			popupWindow = new PopupWindow(c_sv, windth, LayoutParams.WRAP_CONTENT);
			if(c_ll==null){
				c_ll = new LinearLayout(this.getContext());
			}
			c_ll.setOrientation(LinearLayout.VERTICAL);
			c_sv.addView(c_ll, LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT);
			popupWindow.setOutsideTouchable(true);
			

			this.setOnFocusChangeListener(new OnFocusChangeListener() {
				
				@Override
				public void onFocusChange(View v, boolean hasFocus) {
					// TODO Auto-generated method stub
					if(!hasFocus){
						closePopupWindow();
					}
				}
			});
		}
		// show popup window on specified location and update it
		// popupWindow.showAtLocation(this.findViewById(R.id.LinearLayout_main),
		// Gravity.CENTER_VERTICAL, 100, 200);
	}

	private void setPopupWindowValues() {
		LayoutInflater layoutInflater = (LayoutInflater) this.getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (option != null) {
			//int length = option.length >= 5 ? 5 : option.length ;
			int length = option.length ;
			for (int i = 0; i < length; i++) {
				View view = layoutInflater.inflate(R.layout.combobox_layout,
						null);
				TextView tvv = (TextView) view
						.findViewById(R.id.combobox_option);
				tvv.setText(option[i]);

//				tvv.setGravity(Gravity.CENTER);
				tvv.setOnClickListener(oncliclis);
				if(c_ll==null){
					c_ll = new LinearLayout(this.getContext());
				}
				c_ll.addView(view, LayoutParams.MATCH_PARENT,
						LayoutParams.WRAP_CONTENT);
				
			}
		} else {
			View view = layoutInflater.inflate(R.layout.combobox_layout, null);
			TextView tvv = (TextView) view.findViewById(R.id.combobox_option);
			tvv.setText("");
//			tvv.setGravity(Gravity.CENTER);
//			tvv.setOnClickListener(oncliclis);
			if(c_ll==null){
				c_ll = new LinearLayout(this.getContext());
			}
			c_ll.addView(view, LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT);
		}
	}

	public void closePopupWindow() {
		if (popupWindow != null) {
			popupWindow.dismiss();
		}
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		creatPopupWindow(w, h);
		super.onSizeChanged(w, h, oldw, oldh);
	}

	OnClickListener oncliclis = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String text = ((TextView)v).getText().toString();
			setText(text);
			int indexSelected = 0 ;
			for (int i=0;i<option.length;i++){
				if (option[i].equalsIgnoreCase(text)){
					indexSelected = i;
					break;
				}
			}
			
			if (valuechanged != null)
				valuechanged.ValueChanged(ComboBox.this, text, value[indexSelected], ids[indexSelected]);
			closePopupWindow();
		}
	};

	
	
	
	
	
	
	

}