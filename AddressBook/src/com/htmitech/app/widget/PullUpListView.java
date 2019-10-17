package com.htmitech.app.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.htmitech.addressbook.R;

/**
 * Created by htrf-pc on 2017/1/9.
 */
public class PullUpListView extends ListView implements AbsListView.OnScrollListener {
    private View footerView;
    public int footerHeight;//底部布局的高度

    public int lastVisibleItem;//当前最后一个可见的高度

    private boolean isRemark;//标记 标记当前ListView是在最底部进行按下的

    private int startY;//按下的Y值

    private int scrollState;

    private PullState state;

    private int totalItemCount ;


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        this.scrollState = scrollState;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.lastVisibleItem = firstVisibleItem + visibleItemCount;
        this.totalItemCount = totalItemCount;
    }

    /**
     * 按下加载的几个状态
     */
    enum PullState{
        NONE,UPPULL,RELESE,ONLODING;
    }

    public PullUpListView(Context context) {
        super(context);
        initListView(context);
    }

    public PullUpListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initListView(context);
    }

    public PullUpListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initListView(context);
    }


    /**
     * 初始化ListView
     */
    private void initListView(Context context) {

        // 为ListView设置滑动监听
        setOnScrollListener(this);
        // 去掉底部分割线
        setFooterDividersEnabled(false);
        initBottomView(context);
    }


    /**
     * 初始化话底部页面
     */
    public void initBottomView(Context context) {

        if (footerView == null) {
            footerView = LayoutInflater.from(context).inflate(
                    R.layout.ht_list_pull_footerview, null);
        }
        addFooterView(footerView);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(totalItemCount == lastVisibleItem && OnScrollListener.SCROLL_STATE_IDLE == scrollState){
                    isRemark = true;
                    startY = (int) ev.getY();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                onMove(ev);
                break;
            case MotionEvent.ACTION_UP:
                if(state == PullState.RELESE){
                    state = PullState.ONLODING;
                    reflashViewByState();
                    //加载最新数据
                }if(state == PullState.UPPULL){
                    state = PullState.NONE;
                    isRemark = false;
                    reflashViewByState();
                }
                break;
        }
        return super.onTouchEvent(ev);
    }


    public void reflashViewByState(){
        View progress_ = footerView.findViewById(R.id.progress_);

        switch (state){
            case NONE:
                buttomPadding(-footerHeight);
                break;
            case UPPULL:
                progress_.setVisibility(GONE);
                break;
            case RELESE:
                progress_.setVisibility(GONE);
                break;
            case ONLODING:
                progress_.setVisibility(VISIBLE);
                buttomPadding(footerHeight);
                break;
        }
    }

    public void onMove(MotionEvent ev){
        if(!isRemark){
            return;
        }

        int tempY = (int)ev.getY();
        int space = startY - tempY;
        int bottomPadding = space - footerHeight;
        switch (state){
            case NONE:
                if(space > 0){
                    state = PullState.UPPULL;
                    reflashViewByState();
                }
                break;
            case UPPULL:
                buttomPadding(bottomPadding);
                if(space > footerHeight + 30 && scrollState == SCROLL_STATE_TOUCH_SCROLL){
                    state = PullState.RELESE;
                    reflashViewByState();
                }
                break;
            case RELESE:
                buttomPadding(bottomPadding);
                if(space <= 0){
                    state = PullState.NONE;
                    isRemark = false;
                }else{
                    state = PullState.UPPULL;
                }
                reflashViewByState();
                break;
            case ONLODING:
                buttomPadding(bottomPadding);
                break;
        }


    }

    /**
     * 设置padding
     * @param buttomPadding
     */
    public void buttomPadding(int buttomPadding){
        footerView.setPadding(footerView.getPaddingLeft(),footerView.getPaddingTop(),footerView.getPaddingRight(),buttomPadding);
    }
}
