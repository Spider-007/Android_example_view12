package com.htmitech.ztcustom.zt.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;


import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.enums.DicttypeEnum;
import com.htmitech.ztcustom.zt.util.DensityUtil;

import java.util.ArrayList;
import java.util.List;

public class ActionSheetDialog {
	private Context context;
	private Dialog dialog;
	private TextView txt_title;
	private Button txt_cancel;
	private LinearLayout lLayout_content;
	private ScrollView sLayout_content;
	private boolean showTitle = false;
	private List<SheetItem> sheetItemList;
	private Display display;
	private Button btn_neg;
	public ActionSheetDialog(Context context) {
		this.context = context;
		WindowManager windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		display = windowManager.getDefaultDisplay();
	}

	public ActionSheetDialog builder() {
		// 获取Dialog布局
		View view = LayoutInflater.from(context).inflate(
				R.layout.zt_view_actionsheet, null);

		// 设置Dialog最小宽度为屏幕宽度

		view.setMinimumWidth(display.getWidth() - display.getWidth() / 4);

		// 获取自定义Dialog布局中的控件
		sLayout_content = (ScrollView) view.findViewById(R.id.sLayout_content);
		lLayout_content = (LinearLayout) view
				.findViewById(R.id.lLayout_content);
		txt_title = (TextView) view.findViewById(R.id.txt_title);
		txt_cancel = (Button) view.findViewById(R.id.btn_pos);
		btn_neg = (Button)view.findViewById(R.id.btn_neg);
		txt_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		btn_neg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				dialog.dismiss();
				mOnSheetItemClickListener.onClick(currentId);
			}
		});
		// 定义Dialog布局和参数
		dialog = new Dialog(context, R.style.AlertDialogStyle);
		dialog.setContentView(view);
//		Window dialogWindow = dialog.getWindow();
//		dialogWindow.setGravity(Gravity.LEFT | Gravity.BOTTOM);
//		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//		lp.x = 0;
//		lp.y = 0;
//		dialogWindow.setAttributes(lp);

		return this;
	}

	public ActionSheetDialog setTitle(String title) {
		showTitle = true;
		txt_title.setVisibility(View.VISIBLE);
		txt_title.setText(title);
		return this;
	}

	public ActionSheetDialog setCancelable(boolean cancel) {
		dialog.setCancelable(cancel);
		return this;
	}

	public ActionSheetDialog setCanceledOnTouchOutside(boolean cancel) {
		dialog.setCanceledOnTouchOutside(cancel);
		return this;
	}

	/**
	 *
	 *            条目名称
	 *            条目字体颜色，设置null则默认蓝色
	 * @return
	 */
	public ActionSheetDialog addSheetItem(ArrayList<String> map) {
		if (sheetItemList == null) {
			sheetItemList = new ArrayList<SheetItem>();
		}
		for(String s :map){
			sheetItemList.add(new SheetItem(s, ActionSheetDialog.SheetItemColor.GRAY));
		}
		return this;
	}
	/**
	 *
	 *            条目名称
	 *            条目字体颜色，设置null则默认蓝色
	 *
	 *            colorList 右侧颜色
	 * @return
	 */

	public ActionSheetDialog addSheetItem(ArrayList<String> map,ArrayList<String> colorList){
		if (sheetItemList == null) {
			sheetItemList = new ArrayList<SheetItem>();
		}
		for(int i = 0; i < map.size();i++){
			sheetItemList.add(new SheetItem(map.get(i), ActionSheetDialog.SheetItemColor.GRAY,colorList.get(i)));
		}
		return this;
	}


	/** 设置条目布局 */
	private void setSheetItems() {
		if (sheetItemList == null || sheetItemList.size() <= 0) {
			return;
		}

		int size = sheetItemList.size();

		// TODO 高度控制，非最佳解决办法
		// 添加条目过多的时候控制高度
		if (size >= 7) {
			LayoutParams params = (LayoutParams) sLayout_content
					.getLayoutParams();
			params.height = display.getHeight() / 2;
			sLayout_content.setLayoutParams(params);
		}

		// 循环添加条目
		for (int i = 1; i <= size; i++) {
			SheetItem sheetItem = sheetItemList.get(i - 1);
			final String strItem = sheetItem.name;
			SheetItemColor color = sheetItem.color;
			String colorLeft = sheetItem.bitColor;
			TextView textView = new TextView(context);
			textView.setTag(i+"");
			textView.setText(strItem);
			textView.setTextSize(18);
			textView.setGravity(Gravity.CENTER);



			// 字体颜色
			if (color == null) {
				textView.setTextColor(Color.parseColor(SheetItemColor.Blue
						.getName()));
			} else {
				textView.setTextColor(Color.parseColor(color.getName()));
			}

			// 高度
			float scale = context.getResources().getDisplayMetrics().density;
			int height = (int) (45 * scale + 0.5f);
//			textView.setLayoutParams(new LinearLayout.LayoutParams(
//					LayoutParams.WRAP_CONTENT, height));



			if(!colorLeft.equals("")){
				textView.setLayoutParams(new LayoutParams(
						LayoutParams.WRAP_CONTENT, height));
				TextView colorText = new TextView(context);

				LinearLayout layout = new LinearLayout(context);
				LayoutParams layoutParams = new LayoutParams(
						LayoutParams.MATCH_PARENT, height);
//				layoutParams.setMargins(DensityUtil.dip2px(context, 50), 0, DensityUtil.dip2px(context, 10), 0);
				layout.setLayoutParams(layoutParams);
				layout.setOrientation(LinearLayout.HORIZONTAL);
				layout.setGravity(Gravity.CENTER_VERTICAL);
				layout.setTag(i+"");
				LayoutParams para = new LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
				para.setMargins(DensityUtil.dip2px(context,50),0, DensityUtil.dip2px(context,10),0);
				para.width = DensityUtil.dip2px(context, 18);
				para.height = DensityUtil.dip2px(context,10);
				colorText.setLayoutParams(para);
				colorText.setBackgroundColor(Color.parseColor("#"+colorLeft));
				colorText.setGravity(Gravity.CENTER);

				layout.addView(colorText);
				layout.addView(textView);

				// 背景图片
				if (size == 1) {
					if (showTitle) {
						layout.setBackgroundResource(R.drawable.zt_actionsheet_bottom_selector);
					} else {
						layout.setBackgroundResource(R.drawable.zt_actionsheet_single_selector);
					}
				} else {
					if (showTitle) {
						if (i >= 1 && i < size) {
							layout.setBackgroundResource(R.drawable.zt_actionsheet_middle_selector);
						} else {
							layout.setBackgroundResource(R.drawable.zt_actionsheet_middle_selector);
						}
					} else {
						if (i == 1) {
							layout.setBackgroundResource(R.drawable.zt_actionsheet_top_selector);
						} else if (i < size) {
							layout.setBackgroundResource(R.drawable.zt_actionsheet_middle_selector);
						} else {
							layout.setBackgroundResource(R.drawable.zt_actionsheet_middle_selector);
						}
					}
				}

				// 点击事件
				layout.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						currentId = (String) v.getTag();
						for (int i = 0; i < lLayout_content.getChildCount(); i++) {
							View view = lLayout_content.getChildAt(i);
							view.setBackgroundResource(R.drawable.zt_actionsheet_middle_normal);
						}
						v.setBackgroundResource(R.drawable.zt_actionsheet_middle_pressed);
					}
				});

				lLayout_content.addView(layout);
			}else{
// 背景图片
				textView.setLayoutParams(new LayoutParams(
						LayoutParams.MATCH_PARENT, height));
				if (size == 1) {
					if (showTitle) {
						textView.setBackgroundResource(R.drawable.zt_actionsheet_bottom_selector);
					} else {
						textView.setBackgroundResource(R.drawable.zt_actionsheet_single_selector);
					}
				} else {
					if (showTitle) {
						if (i >= 1 && i < size) {
							textView.setBackgroundResource(R.drawable.zt_actionsheet_middle_selector);
						} else {
							textView.setBackgroundResource(R.drawable.zt_actionsheet_middle_selector);
						}
					} else {
						if (i == 1) {
							textView.setBackgroundResource(R.drawable.zt_actionsheet_top_selector);
						} else if (i < size) {
							textView.setBackgroundResource(R.drawable.zt_actionsheet_middle_selector);
						} else {
							textView.setBackgroundResource(R.drawable.zt_actionsheet_middle_selector);
						}
					}
				}
				// 点击事件
				textView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						currentId = DicttypeEnum.getCode(strItem);
						for (int i = 0; i < lLayout_content.getChildCount(); i++) {
							View view = lLayout_content.getChildAt(i);
							view.setBackgroundResource(R.drawable.zt_actionsheet_middle_normal);
						}
						v.setBackgroundResource(R.drawable.zt_actionsheet_middle_pressed);
					}
				});
				lLayout_content.addView(textView);
			}



		}
	}
	public String currentId;
	public void show() {
		setSheetItems();
		dialog.show();
	}
	public OnSheetItemClickListener mOnSheetItemClickListener;
	public ActionSheetDialog setOnSheetItemClickListener(OnSheetItemClickListener mOnSheetItemClickListener){
		this.mOnSheetItemClickListener = mOnSheetItemClickListener;
		return this;
	}
	public interface OnSheetItemClickListener {
		void onClick(String code);
	}


	public class SheetItem {
		String name;
		SheetItemColor color;
		private String bitColor = "" ;
		public SheetItem(String name, SheetItemColor color) {
			this.name = name;
			this.color = color;
		}
		public SheetItem(String name, SheetItemColor color,String bitColor) {
			this.name = name;
			this.color = color;
			this.bitColor = bitColor;
		}
	}

	public enum SheetItemColor {
		Blue("#037BFF"), Red("#FD4A2E"),GRAY("#999999");

		private String name;

		private SheetItemColor(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}
}
