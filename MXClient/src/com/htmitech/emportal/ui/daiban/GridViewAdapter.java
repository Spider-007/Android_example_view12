package com.htmitech.emportal.ui.daiban;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.htmitech.MyView.GlideRoundTransform;
import com.htmitech.commonx.util.DeviceUtils;
import com.htmitech.emportal.HtmitechApplication;
import com.htmitech.emportal.R;
import com.htmitech.emportal.ui.applicationcenter.CircleProgressView;
import com.htmitech.emportal.ui.homepage.BinnerBitmapMessage;
import com.minxing.kit.api.bean.MXAppInfo;

import java.util.ArrayList;
import java.util.List;

public class GridViewAdapter extends BaseAdapter {

    private List<BinnerBitmapMessage> currentOcuList;
    private MXAppInfo AddOcuInfo;
    private MXAppInfo showAllTodoOcuInfo;
    private final String allDaiban = "96898629507219540"; //收文的appid
    private final String allDaiban_CN = "所有待办";
    private int com_workflow_mobileconfig_tabbutton_style;
    private Context context;
    private String todoFlag = "";
    public int checkIndex = 0;

    public int getCheckIndex() {
        return checkIndex;
    }

    public void setCheckIndex(int checkIndex) {
        this.checkIndex = checkIndex;
    }

    public int getCom_workflow_mobileconfig_tabbutton_style() {
        return com_workflow_mobileconfig_tabbutton_style;
    }

    public void setCom_workflow_mobileconfig_tabbutton_style(int com_workflow_mobileconfig_tabbutton_style) {
        this.com_workflow_mobileconfig_tabbutton_style = com_workflow_mobileconfig_tabbutton_style;
    }
    //	public GridViewAdapter(ArrayList<BinnerBitmapMessage>  currentOcuList,Context mContext) {
//		this.context = mContext;
//		Bitmap daiban_bit = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.icon_oa_todo_style4);
//		Bitmap add = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.btn_add);
//			currentOcuList.add(0,new BinnerBitmapMessage(daiban_bit,allDaiban,"全部","",0,null,"db_todo"));
//			this.currentOcuList = currentOcuList;
//
//	}

    public GridViewAdapter(ArrayList<BinnerBitmapMessage> currentOcuList, String todoFlag, String app_id, int mobileconfig_customer_shortcuts, Context mContext) {
        this.context = HtmitechApplication.instance();
        Bitmap daiban_bit = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_oa_todo_style4);
        Bitmap add = BitmapFactory.decodeResource(context.getResources(), R.drawable.btn_add);
        if (todoFlag == null) {
            todoFlag = "0";
        }
        if (todoFlag.equals("0")) {
            currentOcuList.add(0, new BinnerBitmapMessage(daiban_bit, app_id, "全部", "", 0, null, "todo_flag", todoFlag));
        } else {
            currentOcuList.add(0, new BinnerBitmapMessage(daiban_bit, app_id, "全部", "", 0, null, "todo_flag", todoFlag));
        }
        if (mobileconfig_customer_shortcuts == 1) {
            currentOcuList.add(new BinnerBitmapMessage(add, "", context.getResources().getString(R.string.edit), "", 0, null, "add", todoFlag));
        }
        this.currentOcuList = currentOcuList;

    }
//    现在需要增加一个新需求，就是在待办或已办的筛选面板中，能够控制是否显示“所有待办 ”或“所有已办”的筛选按钮。
//    我考虑直接扩展原来有的配置项“com_workflow_mobileconfig_customer_hasdone_shortcutsview	”与“com_workflow_mobileconfig_customer_todo_shortcutsview”的定义来完成。
//    原来是定义：【是否需要在已办中显示“筛选面板”。 0，不显示；1，显示】
//    可以把定义修改为：【是否需要在已办中显示“筛选面板”。 0，不显示；1，显示（自动含所有已办），2：显示（不含所有已办）】，当值为新增加的2时，不显示“所有已办”筛选按钮。

    public GridViewAdapter(ArrayList<BinnerBitmapMessage> currentOcuList, String todoFlag, String app_id, int mobileconfig_customer_shortcuts, Context mContext, int hasdoneShortcuts, int todoShortcuts) {
        this.context = HtmitechApplication.instance();
        Bitmap daiban_bit ;
        if(!TextUtils.isEmpty(todoFlag) && todoFlag.equals("0")){
            daiban_bit = BitmapFactory.decodeResource(context.getResources(), R.drawable.btn_all_backlog);
        }else{
            daiban_bit = BitmapFactory.decodeResource(context.getResources(), R.drawable.btn_all_finished);
        }
        this.todoFlag = todoFlag;
        Bitmap add = BitmapFactory.decodeResource(context.getResources(), R.drawable.btn_add);
        if (todoFlag == null) {
            todoFlag = "0";
        }

        String tempFlag = currentOcuList.size() > 0 ? currentOcuList.get(0).Caption : "";

        if(!tempFlag.equals("全部")) {
            if (todoFlag.equals("0")) {
                if (todoShortcuts == 2) {

                }else{
                    currentOcuList.add(0, new BinnerBitmapMessage(daiban_bit, app_id, "全部", "", 0, null, "todo_flag", todoFlag));
                }

            } else {
                if (hasdoneShortcuts == 2){

                }else{
                    currentOcuList.add(0, new BinnerBitmapMessage(daiban_bit, app_id, "全部", "", 0, null, "todo_flag", todoFlag));
                }

            }

            if (todoFlag.equals("1")) {
                if (hasdoneShortcuts == 1) {
                    currentOcuList.add(new BinnerBitmapMessage(add, "", context.getResources().getString(R.string.edit), "", 0, null, "add", todoFlag));
                }
            } else {
                if (todoShortcuts == 1) {
                    currentOcuList.add(new BinnerBitmapMessage(add, "", context.getResources().getString(R.string.edit), "", 0, null, "add", todoFlag));
                }
            }
        }

        this.currentOcuList = currentOcuList;


    }

    public GridViewAdapter(ArrayList<BinnerBitmapMessage> currentOcuList, String todoFlag, String app_id, int mobileconfig_customer_shortcuts, Context mContext, int hasdoneShortcuts, int todoShortcuts,int hasdoneShortcutsView, int todoShortcutsView) {
        this.context = HtmitechApplication.instance();
        Bitmap daiban_bit ;
        if(!TextUtils.isEmpty(todoFlag) && todoFlag.equals("0")){
            daiban_bit = BitmapFactory.decodeResource(context.getResources(), R.drawable.btn_all_backlog);
        }else{
            daiban_bit = BitmapFactory.decodeResource(context.getResources(), R.drawable.btn_all_finished);
        }
        this.todoFlag = todoFlag;
        Bitmap add = BitmapFactory.decodeResource(context.getResources(), R.drawable.btn_add);
        if (todoFlag == null) {
            todoFlag = "0";
        }

        String tempFlag = currentOcuList.size() > 0 ? currentOcuList.get(0).Caption : "";

        if(!tempFlag.equals("全部")) {
            if (todoFlag.equals("0")) {
                if (todoShortcutsView == 2) {

                }else{
                    currentOcuList.add(0, new BinnerBitmapMessage(daiban_bit, app_id, "全部", "", 0, null, "todo_flag", todoFlag));
                }

            } else {
                if (hasdoneShortcutsView == 2){

                }else{
                    currentOcuList.add(0, new BinnerBitmapMessage(daiban_bit, app_id, "全部", "", 0, null, "todo_flag", todoFlag));
                }

            }

            if (todoFlag.equals("1")) {
                if (hasdoneShortcuts == 1) {
                    currentOcuList.add(new BinnerBitmapMessage(add, "", context.getResources().getString(R.string.edit), "", 0, null, "add", todoFlag));
                }
            } else {
                if (todoShortcuts == 1) {
                    currentOcuList.add(new BinnerBitmapMessage(add, "", context.getResources().getString(R.string.edit), "", 0, null, "add", todoFlag));
                }
            }
        }

        this.currentOcuList = currentOcuList;


    }

    public List<BinnerBitmapMessage> getCurrentOcuList() {
        return currentOcuList;
    }

    public void setCurrentOcuList(List<BinnerBitmapMessage> currentOcuList) {
        this.currentOcuList = currentOcuList;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return currentOcuList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return currentOcuList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        BinnerBitmapMessage mBinnerBitmapMessage = currentOcuList.get(position);
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(HtmitechApplication.instance())
                    .inflate(R.layout.layout_griditem, null);
            holder = new ViewHolder();
            holder.item_text = (TextView) convertView
                    .findViewById(R.id.name_tv);
            holder.iv_icon = (ImageView) convertView
                    .findViewById(R.id.icon_iv);
            holder.edit_ll = (LinearLayout) convertView
                    .findViewById(R.id.edit_ll);
            holder.iv_delete = (ImageView) convertView.findViewById(R.id.delet_iv);
            holder.angle_nulber = (TextView) convertView.findViewById(R.id.angle_nulber);
            holder.img_no_installed = (ImageView) convertView.findViewById(R.id.img_no_installed);
            holder.circleProgressbar = (CircleProgressView) convertView.findViewById(R.id.circleProgressbar);
            ViewGroup.LayoutParams mLayoutParams = holder.iv_icon.getLayoutParams();
            holder.iv_icon.setLayoutParams(mLayoutParams);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.angle_nulber.setTag(mBinnerBitmapMessage.appid);
        mBinnerBitmapMessage.angle_nulber = holder.angle_nulber;
        holder.angle_nulber.setText(mBinnerBitmapMessage.number);
        if (!mBinnerBitmapMessage.number.equals("") && mBinnerBitmapMessage.number.contains("+")) {
            RelativeLayout.LayoutParams layoutParms = new RelativeLayout.LayoutParams(DeviceUtils.dip2px(context, 20), DeviceUtils.dip2px(context, 15));
            layoutParms.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            mBinnerBitmapMessage.angle_nulber.setLayoutParams(layoutParms);
        }

        holder.angle_nulber.setVisibility(mBinnerBitmapMessage.numberFlag);
        currentOcuList.get(position).numberText = holder.angle_nulber;
        if (mBinnerBitmapMessage.appInfo != null && !mBinnerBitmapMessage.getCaption().contains("全部")) {
            Glide.with(context).load(mBinnerBitmapMessage.appInfo.getPicture_normal()).placeholder(R.drawable.icon_app_centre_normal).error(R.drawable.icon_app_centre_normal).transform(new GlideRoundTransform(context)).into(holder.iv_icon);
        } else {
            if (currentOcuList.get(position).mBitmap != null) {
                holder.iv_icon.setImageBitmap(currentOcuList.get(position).mBitmap);
            }

        }
//		if(mBinnerBitmapMessage.appInfo != null){
//			if(mBinnerBitmapMessage.appInfo.getApk_flag() == 2){
//				holder.img_no_installed.setVisibility(View.VISIBLE);
//				holder.img_no_installed.setImageResource(R.drawable.img_no_installed);
//			}else if(mBinnerBitmapMessage.appInfo.getApk_flag() == 1){
//				holder.img_no_installed.setVisibility(View.VISIBLE);
//				holder.img_no_installed.setImageResource(R.drawable.img_new);
//			}else{
//				holder.img_no_installed.setVisibility(View.GONE);
//			}
//		}


        if (currentOcuList.get(position).Caption.equals("全部")) {
            if(!TextUtils.isEmpty(todoFlag) && todoFlag.equals("0")){
                holder.iv_icon.setImageResource(R.drawable.btn_all_backlog);
                holder.item_text.setText(((com_workflow_mobileconfig_tabbutton_style == 1)||(com_workflow_mobileconfig_tabbutton_style == 3)) ? R.string.all_db_text : R.string.all_dy_text);
            }else if(!TextUtils.isEmpty(todoFlag) && todoFlag.equals("1")){
                holder.iv_icon.setImageResource(R.drawable.btn_all_finished);
                holder.item_text.setText(((com_workflow_mobileconfig_tabbutton_style == 1)||(com_workflow_mobileconfig_tabbutton_style == 4))? R.string.all_yb_text: R.string.all_yy_text);
            }else{
                holder.iv_icon.setImageResource(R.drawable.btn_all_backlog);
            }
        }else{
            holder.item_text.setText(currentOcuList.get(position).Caption);
        }
        if(checkIndex == position){
            holder.edit_ll.setBackgroundResource(R.color.huise);
        }
        return convertView;
    }

    class ViewHolder {
        public TextView item_text;
        public ImageView iv_icon;
        public ImageView iv_delete;
        public ImageView img_no_installed;
        public TextView angle_nulber;
        public LinearLayout edit_ll;
        public CircleProgressView circleProgressbar;
    }

}
