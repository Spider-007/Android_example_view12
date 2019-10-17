package com.htmitech.proxy.pop;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.htmitech.emportal.R;
import com.htmitech.proxy.activity.DeviceSafeMainActivity;
import com.htmitech.proxy.adapter.ListPopupWindowAdapter;
import com.htmitech.proxy.interfaces.CallBackSchedulePosition;


/**
 * Created by yanxin on 2017-7-4.
 */
public class ListPopupWindow extends PopupWindow implements View.OnClickListener{
    public Activity context;
    private final View mMenuView;
    private final ListView mListView;
    private final TextView tvFinish;
    public ListPopupWindowAdapter adapter;
    public TextView content;
    private String contentText;
    public int flag = -1;
    private CallBackSchedulePosition mCallBackSchedulePosition;
    private final TextView tvItem;
    private Integer tmpValue = -1;
    //将位置信息抽成全局变量，防止点击取消后popwindow选中位置也被更改
    public int index;
    public int positionTmp;

    public ListPopupWindow(final Activity context, ListPopupWindowAdapter adapter, final TextView content, int positition,
                           final int index, CallBackSchedulePosition mCallBackSchedulePosition){
        super(context);
        this.context = context;
        this.adapter = adapter;
        this.content = content;
        this.index = index;
        this.mCallBackSchedulePosition = mCallBackSchedulePosition;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.popupwindow_list, null);
        mListView = (ListView)mMenuView.findViewById(R.id.listview);
        tvFinish = (TextView)mMenuView.findViewById(R.id.tv_finish);
        tvItem = (TextView)mMenuView.findViewById(R.id.tv_item);
        tvFinish.setOnClickListener(this);
        tvItem.setOnClickListener(this);
        if(adapter != null){
            if(positition >=999)
                adapter.selectIndex(0);
            else
            adapter.selectIndex(positition);
            mListView.setAdapter(adapter);
        }
        //设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.FILL_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.AnimBottom);
//		//实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
            mMenuView.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    if(mMenuView != null && event != null){
                        int height = mMenuView.findViewById(R.id.pop_layout_list).getTop();
                        int y=(int) event.getY();
                        if(event.getAction()==MotionEvent.ACTION_UP){
                            if(y<height){
                                dismiss();
                            }
                        }

                    }

                    return true;
                }
            });

        this.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = ( ListPopupWindow.this.context).getWindow().getAttributes();
                lp.alpha = 1f;
                (ListPopupWindow.this.context).getWindow().setAttributes(lp);
            }
        });
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = 0.7f;
        context.getWindow().setAttributes(lp);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                positionTmp = position;
                TextView mTextView = null;
                LinearLayout ll_root = null;
                for(int i = 0 ;i < parent.getCount();i++){
                    mTextView = (TextView) parent.getChildAt(i).findViewById(R.id.tv_content);
                     ll_root = (LinearLayout)parent.getChildAt(i).findViewById(R.id.ll_root);
                    ll_root.setBackgroundColor(Color.parseColor("#ffffffff"));
                    mTextView.setSelected(false);
                }
                mTextView= (TextView) view.findViewById(R.id.tv_content);
                mTextView.setSelected(true);
                ll_root =  (LinearLayout)view.findViewById(R.id.ll_root);
                ll_root.setBackgroundColor(Color.parseColor("#fcf8e2"));
//                ListPopupWindow.this.mCallBackSchedulePosition.getPosition(position,index);
                try{
                    tmpValue = (Integer) mListView.getItemAtPosition(position);
                    if(tmpValue <999 && tmpValue >0){
                        contentText = tmpValue +"台";
                    } else  if(tmpValue >= 999){
                        contentText = "无限制";
                    }

                }catch (Exception e){

                }
            }
        });
//        mListView.setItemChecked(positition+1,true);
//        mListView.setSelection(positition + 1);

    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.tv_finish){
            ListPopupWindow.this.mCallBackSchedulePosition.getPosition(positionTmp,index);
            ((DeviceSafeMainActivity) context).saveDeviceConfig(tmpValue);
            if(contentText != null && !"".equals(contentText))
            content.setText(contentText);
            dismiss();
        }else if(id == R.id.tv_item){
            dismiss();
        }
    }
    public int getId(){
        return flag;
    }

}
