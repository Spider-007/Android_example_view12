package htmitech.com.componentlibrary;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;

public class SelectPicPopupWindow extends PopupWindow {


	private Button bt_call, bt_send, bt_save,bt_copy;
	private View mMenuView;
	private Activity context;
	public SelectPicPopupWindow(Activity context, OnClickListener itemsOnClick) {
		super(context);
		this.context = context;
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mMenuView = inflater.inflate(R.layout.component_frament_message_mobile, null);
		bt_call = (Button) mMenuView.findViewById(R.id.bt_call);
		bt_send = (Button) mMenuView.findViewById(R.id.bt_send);
		bt_save = (Button) mMenuView.findViewById(R.id.bt_save);
		bt_copy = (Button) mMenuView.findViewById(R.id.bt_copy);
//		//取消按钮
//		btn_cancel.setOnClickListener(new OnClickListener() {
//
//			public void onClick(View v) {
//				//销毁弹出框
//				dismiss();
//			}
//		});
		//设置按钮监听
		bt_call.setOnClickListener(itemsOnClick);
		bt_send.setOnClickListener(itemsOnClick);
		bt_save.setOnClickListener(itemsOnClick);
		bt_copy.setOnClickListener(itemsOnClick);
		//设置SelectPicPopupWindow的View
		this.setContentView(mMenuView);
		//设置SelectPicPopupWindow弹出窗体的宽
		this.setWidth(LayoutParams.FILL_PARENT);
		//设置SelectPicPopupWindow弹出窗体的高
		this.setHeight(LayoutParams.WRAP_CONTENT);
		//设置SelectPicPopupWindow弹出窗体可点击
		this.setFocusable(true);
		//设置SelectPicPopupWindow弹出窗体动画效果
		this.setAnimationStyle(R.style.AnimBottom);
//		//实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0x00000000);
		//设置SelectPicPopupWindow弹出窗体的背景
		this.setBackgroundDrawable(dw);
		//mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
		mMenuView.setOnTouchListener(new OnTouchListener() {
			
			public boolean onTouch(View v, MotionEvent event) {
				
				int height = mMenuView.findViewById(R.id.pop_layout).getTop();
				int y=(int) event.getY();
				if(event.getAction()==MotionEvent.ACTION_UP){
					if(y<height){
						dismiss();
					}
				}				
				return true;
			}
		});
		this.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				WindowManager.LayoutParams lp = ( SelectPicPopupWindow.this.context).getWindow().getAttributes();
				lp.alpha = 1f;
				(SelectPicPopupWindow.this.context).getWindow().setAttributes(lp);
			}
		});
		WindowManager.LayoutParams lp = context.getWindow().getAttributes();
		lp.alpha = 0.7f;
		context.getWindow().setAttributes(lp);

	}
	public void setMessage(){
		bt_call.setText("拍照上传");
		bt_send.setText("相册上传");
		bt_save.setText("取消选择");
		bt_copy.setVisibility(View.GONE);
	}

	public void setFromAudio(String type){
		if(type.equals("4001") || type.equals("4101")){
//			bt_send.setText("从媒体库选择");
			bt_send.setVisibility(View.GONE);
		}
		bt_call.setText("录音");
		bt_send.setText("从媒体库选择");
		bt_save.setText("取消");
		bt_copy.setVisibility(View.GONE);
	}

	public void setFromVideo(){
		bt_call.setText("录像");// 暂不支持
		bt_send.setText("从媒体库选择");
		bt_save.setText("取消");
		bt_copy.setVisibility(View.GONE);
		bt_call.setVisibility(View.GONE);
	}

	public void setDetalFormMessage(){
		bt_call.setText("拍照");
		bt_send.setText("从手机相册选择");
		bt_save.setText("取消");
		bt_copy.setVisibility(View.GONE);
	}

	public void setSave(){
		bt_call.setText("新建联系人");
		bt_call.setTag("new_people");
		bt_send.setText("添加至现有联系人");
		bt_send.setTag("modify_people");
		bt_copy.setVisibility(View.GONE);
		bt_save.setVisibility(View.GONE);
	}
}
