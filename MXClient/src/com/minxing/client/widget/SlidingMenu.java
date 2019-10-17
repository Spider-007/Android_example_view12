package com.minxing.client.widget;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.htmitech.app.widget.RoundImageView;
import com.minxing.client.AppConstants;
import com.htmitech.emportal.R;
import com.minxing.client.activity.SystemSettingActivity;
import com.minxing.kit.api.MXAPI;
import com.minxing.kit.api.bean.MXCurrentUser;
import com.minxing.kit.api.bean.MXNetwork;
import com.nostra13.universalimageloader.core.ImageLoader;

import htmitech.com.componentlibrary.unit.ResourceUtil;

public class SlidingMenu extends ScrollView {
	private Context context;
	private RoundImageView slidAvatar;
	private TextView slidName;
	private LinearLayout slidNetworkMainHeader;
	private LinearLayout slidNetworkMain;
	private LinearLayout slidNetworkExternalHeader;
	private View slidNetworkExternalDivider;
	private LinearLayout slidNetworkExternal;
	private LinearLayout slidBrowseExternalNetwork;
	private RelativeLayout slidUser;
	private DrawerLayout drawerLayout;
	private RelativeLayout slidSetting;
	private TextView slidNewflag;
	private boolean showExtNetwork = false;
	private OnNetworkSwitchListener onNetworkSwitchListener;
	
	public interface OnNetworkSwitchListener {
        public void switchNetwork(MXNetwork net);
    }
	
	public SlidingMenu(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
	}

	public SlidingMenu(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	public SlidingMenu(Context context) {
		super(context);
		this.context = context;
	}
	
	public void setOnNetworkSwitchListener(OnNetworkSwitchListener onNetworkSwitchListener) {
		this.onNetworkSwitchListener = onNetworkSwitchListener;
	}

	public void init(DrawerLayout drawer) {
		showExtNetwork = ResourceUtil.getConfBoolean(context, R.string.client_show_ext_network);
		this.drawerLayout = drawer;
		View main = LayoutInflater.from(context).inflate(R.layout.sliding_drawer, null);
		this.addView(main);
		slidUser = (RelativeLayout) main.findViewById(R.id.slid_user);
		slidAvatar = (RoundImageView) main.findViewById(R.id.slid_avatar);
		slidName = (TextView) main.findViewById(R.id.slid_name);

		slidNetworkMainHeader = (LinearLayout) main.findViewById(R.id.slid_network_main_header);
		slidNetworkMain = (LinearLayout) main.findViewById(R.id.slid_network_main);
		slidNetworkExternalHeader = (LinearLayout) main.findViewById(R.id.slid_network_external_header);
		slidNetworkExternalDivider = (View) main.findViewById(R.id.slid_network_external_divider);
		slidNetworkExternal = (LinearLayout) main.findViewById(R.id.slid_network_external);
		slidBrowseExternalNetwork = (LinearLayout) main.findViewById(R.id.slid_browse_external_network);
		slidSetting = (RelativeLayout) main.findViewById(R.id.slid_setting);
		slidNewflag = (TextView) main.findViewById(R.id.slid_newflag);
		
		slidUser.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				drawerLayout.closeDrawers();
				MXAPI.getInstance(context).viewCurrentUser();
			}
		});
		slidSetting.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, SystemSettingActivity.class);
				drawerLayout.closeDrawers();
				context.startActivity(intent);
				((Activity)context).overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
			}
		});
		slidBrowseExternalNetwork.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				drawerLayout.closeDrawers();
				MXAPI.getInstance(context).viewNetworkList();
			}
		});
		MXCurrentUser user = MXAPI.getInstance(context).currentUser();
		if(user != null){
			updateAvatar(user);
		}
	}

	private void updateAvatar(MXCurrentUser user) {
		ImageLoader.getInstance().displayImage(user.getAvatarUrl(), slidAvatar, AppConstants.USER_AVATAR_OPTIONS, AppConstants.animateFirstListener);
		slidName.setText(user.getName());
	}

	public void refreshNetwork(MXCurrentUser user) {
		updateAvatar(user);
		appendNetworks(user);
	}

	private void appendNetworks(MXCurrentUser user) {
		if(user == null){
			return;
		}
		List<MXNetwork> networks = user.getNetworks();

		slidNetworkMain.removeAllViews();
		slidNetworkExternal.removeAllViews();

		List<MXNetwork> innerNets = new ArrayList<MXNetwork>();
		List<MXNetwork> outerNets = new ArrayList<MXNetwork>();
		boolean isCurrentOutNet = false;
		
		for (MXNetwork network : networks) {
			
			if (network.isExternal()) {
				outerNets.add(network);
				if(network.getId() == user.getNetworkID()){
					isCurrentOutNet = true;
				}
			} else {
				innerNets.add(network);
			}
		}

		// 初始化内部社区
		for (int i = 0; i < innerNets.size(); i++) {
			MXNetwork net = innerNets.get(i);
			View item = LayoutInflater.from(context).inflate(R.layout.network_item, null);
			TextView mynetwork = (TextView) item.findViewById(R.id.mynetwork);
			ImageView checkmark = (ImageView) item.findViewById(R.id.checkmark);
			TextView sms_num = (TextView) item.findViewById(R.id.sms_num);
			ImageView work_circle_icon = (ImageView) item.findViewById(R.id.work_circle_icon);

			mynetwork.setText(net.getName());
			
			int chatUnread = MXAPI.getInstance(context).queryNetworkChatUnread(net.getId());
			boolean circleMark = MXAPI.getInstance(context).checkNetworkCircleUnread(net.getId());
			
			if (chatUnread > 0 || circleMark) {
				if (chatUnread > 0) {
					sms_num.setVisibility(View.VISIBLE);
					work_circle_icon.setVisibility(View.GONE);
					String unReadCount = chatUnread <= 99 ? String.valueOf(chatUnread) : "...";
					sms_num.setText(unReadCount);
				} else {
					sms_num.setVisibility(View.GONE);
					work_circle_icon.setVisibility(View.VISIBLE);
					sms_num.setText("");
				}
			} else {
				sms_num.setVisibility(View.GONE);
				work_circle_icon.setVisibility(View.GONE);
				sms_num.setText("");
			}
			
			setOnClickListener(item, net);
			slidNetworkMain.addView(item);
			

			if (user.getNetworkID() == net.getId()) {
				checkmark.setVisibility(View.VISIBLE);
				slidNetworkMainHeader.setSelected(true);
				slidNetworkExternalHeader.setSelected(false);
				item.setSelected(true);
				slidBrowseExternalNetwork.setVisibility(View.VISIBLE);
				sms_num.setVisibility(View.GONE);
				work_circle_icon.setVisibility(View.GONE);
				sms_num.setText("");
			} else {
				if (!slidNetworkMainHeader.isSelected()) {
					slidNetworkMainHeader.setSelected(false);
				}
				item.setSelected(false);
				checkmark.setVisibility(View.GONE);
			}
		}

		// 初始化外部社区
		if (outerNets.isEmpty() || !showExtNetwork) {
			slidNetworkExternalHeader.setVisibility(View.GONE);
			slidNetworkExternalDivider.setVisibility(View.GONE);
			slidNetworkExternal.setVisibility(View.GONE);
			if (isCurrentOutNet || !showExtNetwork) {
				slidBrowseExternalNetwork.setVisibility(View.GONE);
			} else {
				slidBrowseExternalNetwork.setVisibility(View.VISIBLE);
			}
		} else {
			slidNetworkExternalHeader.setVisibility(View.VISIBLE);
			slidNetworkExternalDivider.setVisibility(View.VISIBLE);
			slidNetworkExternal.setVisibility(View.VISIBLE);
			slidBrowseExternalNetwork.setVisibility(View.VISIBLE);
			for (int i = 0; i < outerNets.size(); i++) {
				MXNetwork net = outerNets.get(i);
				View item = LayoutInflater.from(context).inflate(R.layout.network_item, null);
				TextView mynetwork = (TextView) item.findViewById(R.id.mynetwork);
				ImageView checkmark = (ImageView) item.findViewById(R.id.checkmark);
				TextView sms_num = (TextView) item.findViewById(R.id.sms_num);
				ImageView work_circle_icon = (ImageView) item.findViewById(R.id.work_circle_icon);

				mynetwork.setText(net.getName());
				
				int chatUnread = MXAPI.getInstance(context).queryNetworkChatUnread(net.getId());
				boolean circleMark = MXAPI.getInstance(context).checkNetworkCircleUnread(net.getId());

				if (chatUnread > 0 || circleMark) {
					if (chatUnread > 0) {
						sms_num.setVisibility(View.VISIBLE);
						work_circle_icon.setVisibility(View.GONE);
						String unReadCount = chatUnread <= 99 ? String.valueOf(chatUnread) : "...";
						sms_num.setText(unReadCount);
					} else {
						sms_num.setVisibility(View.GONE);
						work_circle_icon.setVisibility(View.VISIBLE);
						sms_num.setText("");
					}
				} else {
					sms_num.setVisibility(View.GONE);
					work_circle_icon.setVisibility(View.GONE);
					sms_num.setText("");
				}
				
				if (user.getNetworkID() == net.getId()) {
					checkmark.setVisibility(View.VISIBLE);
					slidNetworkExternalHeader.setSelected(true);
					slidNetworkMainHeader.setSelected(false);
					item.setSelected(true);
					slidBrowseExternalNetwork.setVisibility(View.GONE);
					sms_num.setVisibility(View.GONE);
					work_circle_icon.setVisibility(View.GONE);
					sms_num.setText("");
				} else {
					checkmark.setVisibility(View.GONE);
					item.setSelected(false);
					if (!slidNetworkExternalHeader.isSelected()) {
						slidNetworkExternalHeader.setSelected(false);
					}
				}
				
				setOnClickListener(item, net);
				slidNetworkExternal.addView(item);
			}
		}
	}

	public void setOnClickListener(final View view, final MXNetwork net) {
		view.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				MXCurrentUser user = MXAPI.getInstance(context).currentUser();
				if (user.getNetworkID() != net.getId()) {
					if (MXAPI.getInstance(context).switchNetwork(view.getContext(), net.getId())) {
						drawerLayout.closeDrawers();
						if (onNetworkSwitchListener != null) {
							onNetworkSwitchListener.switchNetwork(net);
						}
						return;
					}
				}
				drawerLayout.closeDrawers();
			}
		});
	}

	public void UpgradeNewVersionMark(boolean showMark) {
		if (showMark) {
			slidNewflag.setVisibility(View.VISIBLE);
		} else {
			slidNewflag.setVisibility(View.GONE);
		}
	}
}
