package com.htmitech_updown.updownloadmanagement.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.updownloadmanagement.R;
import com.htmitech_updown.updownloadmanagement.adapter.SellSipeListAdapter;
import com.htmitech_updown.updownloadmanagement.db.DbUtil;
import com.htmitech_updown.updownloadmanagement.uploadbean.ChunkInfo;
import com.htmitech_updown.updownloadmanagement.uploadbean.UploadFileInfoBean;
import com.htmitech_updown.updownloadmanagement.utils.DimenUtil;
import com.htmitech_updown.updownloadmanagement.view.SwipeListLayout;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 */

public class UpLoadFileFinishFragment extends BaseFragment implements View.OnClickListener, SearchView.OnQueryTextListener {

    private Context context;
    private List<UploadFileInfoBean> uploadFileInfoBeanList;
    private ListView listView;
    private SellSipeListAdapter adapter;
    private Set<SwipeListLayout> sets = new HashSet();
    private SearchView sv_search;
    private RelativeLayout rv_serach;
    private ImageView iv_serach;
    private TextView tv_serach;

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        uploadFileInfoBeanList = DbUtil.getUploadFileInfoFinishList();
        adapter = new SellSipeListAdapter(context, uploadFileInfoBeanList, sets);
        listView.setAdapter(adapter);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    //当listview开始滑动时，若有item的状态为Open，则Close，然后移除
                    case SCROLL_STATE_TOUCH_SCROLL:
                        if (sets.size() > 0) {
                            for (SwipeListLayout s : sets) {
                                s.setStatus(SwipeListLayout.Status.Close, true);
                                sets.remove(s);
                            }
                        }
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        rv_serach.setOnClickListener(new View.OnClickListener() {

            @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                iv_serach.setVisibility(View.GONE);
                tv_serach.setVisibility(View.GONE);
                sv_search.setVisibility(View.VISIBLE);
                sv_search.onActionViewExpanded();
                InputMethodManager inputManager = (InputMethodManager) sv_search.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(sv_search, 0);
            }
        });
        sv_search.setOnCloseListener(new SearchView.OnCloseListener() {

            @Override
            public boolean onClose() {
                return true;
            }
        });
        if (sv_search != null) {
            try {
                //--拿到字节码
                Class<?> argClass = sv_search.getClass();
                //--指定某个私有属性,mSearchPlate是搜索框父布局的名字
                Field ownField = argClass.getDeclaredField("mSearchPlate");
                //--暴力反射,只有暴力反射才能拿到私有属性
                ownField.setAccessible(true);
                View mView = (View) ownField.get(sv_search);
                //--设置背景
                mView.setBackgroundColor(Color.TRANSPARENT);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 为该SearchView组件设置事件监听器
        sv_search.setOnQueryTextListener(this);
        sv_search.setIconifiedByDefault(false);
        // 注册广播接收器，接收刷新数据的广播
        IntentFilter filterFinish = new IntentFilter();
        filterFinish.addAction("uploadfinishupdatedata");
        getActivity().registerReceiver(updateDataReceiver, filterFinish);
    }

    BroadcastReceiver updateDataReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                String act = "";
                act = intent.getAction();
                if ("uploadfinishupdatedata".equals(act)) {
                    uploadActivityData();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.context = activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.updownload_prefix_upoadfile_finish_fragment, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = (ListView) view.findViewById(R.id.lv_upload_file_finish);
        sv_search = (SearchView) view.findViewById(R.id.upload_finish_sv_search);
        rv_serach = (RelativeLayout) view.findViewById(R.id.upload_finish_rv_serach);
        iv_serach = (ImageView) view.findViewById(R.id.upload_finish_iv_serach);
        tv_serach = (TextView) view.findViewById(R.id.upload_finish_tv_serach);
        int search_mag_icon_id = sv_search.getContext().getResources().getIdentifier("android:id/search_mag_icon", null, null);
        ImageView search_mag_icon = (ImageView) sv_search.findViewById(search_mag_icon_id);// 获取搜索图标
        search_mag_icon.setImageResource(R.drawable.mx_search_bar_icon_normal);// 图标都是用src的
        // 设置提示文字的颜色,这里走了点奇招,用Html类方法
        sv_search.setQueryHint(Html.fromHtml("<font color = #999999>" + "请输入标题关键字搜索" + "</font>"));
        int id = sv_search.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView textView = (TextView) sv_search.findViewById(id);
        textView.setTextSize(DimenUtil.sp2px(context, 5));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (updateDataReceiver != null) {
            context.unregisterReceiver(updateDataReceiver);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onClick(View v) {

    }

    public void uploadActivityData() {
        List<UploadFileInfoBean> uploadFileInfoBeanList = DbUtil.getUploadFileInfoFinishList();
        if (this.uploadFileInfoBeanList != null) {
            this.uploadFileInfoBeanList.clear();
            this.uploadFileInfoBeanList.addAll(uploadFileInfoBeanList);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        sv_search.clearFocus();
//        sv_search.setVisibility(View.GONE);
//        iv_serach.setVisibility(View.VISIBLE);
//        tv_serach.setVisibility(View.VISIBLE);
        Log.e("QUERY", query);
        List<UploadFileInfoBean> uploadFileInfoBeanList = DbUtil.getUploadFileInfoFinishSearchList(query);
        if (this.uploadFileInfoBeanList != null) {
            this.uploadFileInfoBeanList.clear();
            this.uploadFileInfoBeanList.addAll(uploadFileInfoBeanList);
            adapter.notifyDataSetChanged();
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if(newText.equals("")){
            if (uploadFileInfoBeanList != null) {
                uploadFileInfoBeanList.clear();
                uploadFileInfoBeanList.addAll(DbUtil.getUploadFileInfoFinishList());
                adapter.notifyDataSetChanged();
            }
        }
        return false;
    }

    @Override
    public void lazyLoad() {
        if (uploadFileInfoBeanList != null) {
            uploadFileInfoBeanList.clear();
            uploadFileInfoBeanList.addAll(DbUtil.getUploadFileInfoFinishList());
            adapter.notifyDataSetChanged();
        }

    }
}
