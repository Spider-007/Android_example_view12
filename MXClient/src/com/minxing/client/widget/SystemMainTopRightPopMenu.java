package com.minxing.client.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.htmitech.emportal.R;
import com.htmitech.emportal.ui.appcenter.AppCenterActivity;

import htmitech.com.componentlibrary.content.ConcreteLogin;
import htmitech.com.componentlibrary.unit.ResourceUtil;

public class SystemMainTopRightPopMenu extends PopupWindow {

	private Context mContext;
	private View contentView = null;

	/** 发布消息 */
	private LinearLayout btn_send_text;
	/** 创建任务*/
	private LinearLayout btn_create_task;
	/** 组织活动*/
	private LinearLayout btn_activity;
	/** 发起投票*/
	private LinearLayout btn_poll;
	/** 发起聊天 */
	private LinearLayout btn_new_conversation;
	/** 添加联系人 */
	private LinearLayout btn_add_contact;
	/** 通讯录 */
	private LinearLayout btn_contacts;
	/** 应用中心 */
	private LinearLayout btn_appcenter;
	/** 扫一扫 */
	private LinearLayout btn_scan;
	/** 工作分享 */
	private LinearLayout btn_new_circle_message;

	private ImageView line_create_task;
	private ImageView line_activity;
	private ImageView line_poll;
	private ImageView line_new_conversation;
	private ImageView line_add_contact;
	private ImageView line_contacts;
	private ImageView line_appcenter;
	private ImageView line_scan_divider;
	private ImageView line_new_circle_message;


	@SuppressWarnings("deprecation")
	public SystemMainTopRightPopMenu(final Context context) {
		super(context);
		mContext = context;
		contentView = LayoutInflater.from(mContext).inflate(R.layout.system_top_right_menu, null);


		this.setContentView(contentView);
		this.setWidth(LayoutParams.WRAP_CONTENT);
		this.setHeight(LayoutParams.WRAP_CONTENT);
		this.setFocusable(true);
		this.setOutsideTouchable(true);
		this.setTouchable(true);
		this.setBackgroundDrawable(new BitmapDrawable());
		/*发布消息 */
		btn_send_text = (LinearLayout) contentView.findViewById(R.id.popbtn_send_text);
		btn_send_text.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//参数activity:当前Activity
				//参数name:新消息界面标题
				ConcreteLogin.getInstance().startSendText((Activity) mContext, v.getContext().getResources().getString(R.string.circle_menu_send_text));
				dismiss();
			}
		});

		/*创建任务*/
		line_create_task = (ImageView) contentView.findViewById(R.id.line_create_task);
		btn_create_task = (LinearLayout) contentView.findViewById(R.id.popbtn_create_task);
		btn_create_task.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ConcreteLogin.getInstance().createTask((Activity) mContext, v.getContext().getResources().getString(R.string.circle_menu_create_task));
				dismiss();
			}
		});

		/*组织活动*/
		line_activity = (ImageView) contentView.findViewById(R.id.line_activity);
		btn_activity = (LinearLayout) contentView.findViewById(R.id.popbtn_activity);
		btn_activity.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ConcreteLogin.getInstance().setupActivity((Activity) mContext, v.getContext().getResources().getString(R.string.circle_menu_activity));
				dismiss();
			}
		});

		/* 发起投票*/
		line_poll = (ImageView) contentView.findViewById(R.id.line_poll);
		btn_poll = (LinearLayout) contentView.findViewById(R.id.popbtn_poll);
		btn_poll.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ConcreteLogin.getInstance().poll((Activity) mContext, v.getContext().getResources().getString(R.string.circle_menu_poll));
				dismiss();
			}
		});

		/* 发起聊天 */
		line_new_conversation = (ImageView) contentView.findViewById(R.id.line_new_conversation);
		btn_new_conversation = (LinearLayout) contentView.findViewById(R.id.popbtn_new_conversation);
		btn_new_conversation.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ConcreteLogin.getInstance().startNewChat((Activity) mContext);
				((Activity) mContext).overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
				dismiss();
			}
		});

		/* 添加联系人 */
		line_add_contact = (ImageView) contentView.findViewById(R.id.line_add_contact);
		btn_add_contact = (LinearLayout) contentView.findViewById(R.id.popbtn_add_contact);
		btn_add_contact.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ConcreteLogin.getInstance().addContacts(context);
				dismiss();
			}
		});


		/* 通讯录 */
		line_contacts = (ImageView) contentView.findViewById(R.id.line_contacts);
		btn_contacts = (LinearLayout) contentView.findViewById(R.id.popbtn_contacts);
		btn_contacts.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				ConcreteLogin.getInstance().startMXContactsActivity(context);
				dismiss();
			}
		});

		/* 应用中心 */
		line_appcenter = (ImageView) contentView.findViewById(R.id.line_appcenter);
		btn_appcenter = (LinearLayout) contentView.findViewById(R.id.popbtn_appcenter);
		btn_appcenter.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, AppCenterActivity.class);
				((Activity) mContext).startActivity(intent);
				((Activity) mContext).overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
				dismiss();
			}
		});




		/*工作分享*/
		line_new_circle_message = (ImageView) contentView.findViewById(R.id.line_new_circle_message);
		btn_new_circle_message = (LinearLayout) contentView.findViewById(R.id.popbtn_new_circle_message);
		btn_new_circle_message.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ConcreteLogin.getInstance().shareToCircle((Activity) mContext,"");
				((Activity) mContext).overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
				dismiss();
			}
		});


		/* 扫一扫 */
		btn_scan = (LinearLayout) contentView.findViewById(R.id.popbtn_scan);
		line_scan_divider = (ImageView) contentView.findViewById(R.id.line_scan_divider);
		btn_scan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ConcreteLogin.getInstance().startScan((Activity) mContext);
				((Activity) mContext).overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
				dismiss();
			}
		});

	}

	public void setForTodo(){

		btn_send_text.setVisibility(View.GONE);

		btn_create_task.setVisibility(View.GONE);
		line_create_task.setVisibility(View.GONE);

		btn_activity.setVisibility(View.GONE);
		line_activity.setVisibility(View.GONE);

		btn_poll.setVisibility(View.GONE);
		line_poll.setVisibility(View.GONE);

		btn_new_conversation.setVisibility(View.GONE);
		line_new_conversation.setVisibility(View.GONE);

		btn_add_contact.setVisibility(View.GONE);
		line_add_contact.setVisibility(View.GONE);

		btn_contacts.setVisibility(View.GONE);
		line_contacts.setVisibility(View.GONE);

		btn_appcenter.setVisibility(View.VISIBLE);
		line_appcenter.setVisibility(View.VISIBLE);

		btn_new_circle_message.setVisibility(View.VISIBLE);
		line_new_circle_message.setVisibility(View.VISIBLE);

		if (ResourceUtil.getConfBoolean(mContext, R.string.client_show_scan)) {
			btn_scan.setVisibility(View.VISIBLE);
			line_scan_divider.setVisibility(View.VISIBLE);
		}
	}


	public void setForChat(){

		btn_send_text.setVisibility(View.GONE);

		btn_create_task.setVisibility(View.GONE);
		line_create_task.setVisibility(View.GONE);

		btn_activity.setVisibility(View.GONE);
		line_activity.setVisibility(View.GONE);

		btn_poll.setVisibility(View.GONE);
		line_poll.setVisibility(View.GONE);

		btn_new_conversation.setVisibility(View.VISIBLE);
		line_new_conversation.setVisibility(View.VISIBLE);

		btn_add_contact.setVisibility(View.VISIBLE);
		line_add_contact.setVisibility(View.VISIBLE);

		btn_contacts.setVisibility(View.GONE);
		line_contacts.setVisibility(View.GONE);

		btn_appcenter.setVisibility(View.GONE);
		line_appcenter.setVisibility(View.GONE);

		btn_scan.setVisibility(View.VISIBLE);
		line_scan_divider.setVisibility(View.VISIBLE);

		btn_new_circle_message.setVisibility(View.VISIBLE);
		line_new_circle_message.setVisibility(View.VISIBLE);


	}

	public void setForCircle(){

		btn_send_text.setVisibility(View.VISIBLE);

		btn_create_task.setVisibility(View.VISIBLE);
		line_create_task.setVisibility(View.VISIBLE);

		btn_activity.setVisibility(View.VISIBLE);
		line_activity.setVisibility(View.VISIBLE);

		btn_poll.setVisibility(View.VISIBLE);
		line_poll.setVisibility(View.VISIBLE);

		btn_new_conversation.setVisibility(View.GONE);
		line_new_conversation.setVisibility(View.GONE);

		btn_add_contact.setVisibility(View.GONE);
		line_add_contact.setVisibility(View.GONE);

		btn_contacts.setVisibility(View.GONE);
		line_contacts.setVisibility(View.GONE);

		btn_appcenter.setVisibility(View.GONE);
		line_appcenter.setVisibility(View.GONE);

		btn_new_circle_message.setVisibility(View.GONE);
		line_new_circle_message.setVisibility(View.GONE);

		btn_scan.setVisibility(View.GONE);
		line_scan_divider.setVisibility(View.GONE);
	}


}