package com.htmitech.ztcustom.zt.adapter;

/**
 * 探伤情况汇总的listview的adapter
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.constant.TanShangCompleteResultBean;
import com.htmitech.ztcustom.zt.domain.permissions.LineList;
import com.htmitech.ztcustom.zt.view.hvlistview.MyHScrollView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class TanShangCompleteSumTableAdapter extends BaseAdapter {

    private List<TanShangCompleteResultBean> list;
    private Context context;
    private View mHead;
    private ArrayList<LineList> array;
    private DecimalFormat df = new DecimalFormat("#0.000");
    private DecimalFormat dfint = new DecimalFormat("#0");

    public TanShangCompleteSumTableAdapter(ArrayList<LineList> array,
                                           Context context, View mHead) {
        if (array == null) {
            this.array = new ArrayList<LineList>();
        } else {
            this.array = array;
        }
        this.context = context;
        this.mHead = mHead;
        notifyDataSetChanged();
    }

    public void setData(ArrayList<LineList> array) {
        if (array == null) {
            this.array = new ArrayList<LineList>();
        } else {
            this.array = array;
        }
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return array.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return array.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public View getView(int position, View convertview, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder vh;
        ViewHoderItem mViewHoderItem;
        LineList mLineList = array.get(position);
        if (convertview == null) {
            vh = new ViewHolder();
            convertview = LayoutInflater.from(context).inflate(
                    R.layout.complete_sum_list_item, null, false);

            vh.tv_xianming = (TextView) convertview
                    .findViewById(R.id.tv_complete_xianming);
            vh.zt_layout_sum_item = (LinearLayout) convertview
                    .findViewById(R.id.zt_layout_sum_item);
            if (position % 2 == 0) {
                convertview.setBackgroundColor(context.getResources().getColor(
                        R.color.listview_item_jiaoti_color));
            }

            for (int i = 0; i < mLineList.order.size(); i++) {
                vh.zt_layout_sum_item.addView(addView(i));
            }

            convertview.setTag(vh);
        } else {
            vh = (ViewHolder) convertview.getTag();
            int num = mLineList.order.size() - vh.zt_layout_sum_item.getChildCount();
            if (num < 0) {
                int count = vh.zt_layout_sum_item.getChildCount() - 1;
                for (int i = 0; i < -num; i++) {
                    vh.zt_layout_sum_item.removeViewAt(count - i);
                }
            } else if (num > 0) {
                for (int i = 0; i < num; i++) {
                    vh.zt_layout_sum_item.addView(addView(i));
                }
            }
        }

        vh.tv_xianming.setText(mLineList.line_name);
        for (int i = 0; i < vh.zt_layout_sum_item.getChildCount(); i++) {
            mViewHoderItem = (ViewHoderItem) vh.zt_layout_sum_item
                    .getChildAt(i).getTag();
            mViewHoderItem.tv_xianbie.setText(mLineList.order.get(i)
                    .getLine_typename());
            mViewHoderItem.tv_gg.setText(df.format(mLineList.order.get(i)
                    .getGg_sum()) + "");
            mViewHoderItem.tv_csgh.setText(dfint.format(mLineList.order.get(i)
                    .getCsgh_sum()) + "");
            mViewHoderItem.tv_xsgh.setText(dfint.format(mLineList.order.get(i)
                    .getXsgh_sum()) + "");
            mViewHoderItem.tv_qyh.setText(dfint.format(mLineList.order.get(i)
                    .getQyh_sum()) + "");
            mViewHoderItem.tv_lrh.setText(dfint.format(mLineList.order.get(i)
                    .getLrh_sum()) + "");
            mViewHoderItem.tv_jghf.setText(dfint.format(mLineList.order.get(i)
                    .getJghf_sum()) + "");
            mViewHoderItem.tv_kdxc.setText(dfint.format(mLineList.order.get(i)
                    .getKdxc_sum()) + "");
            mViewHoderItem.tv_hjgc.setText(dfint.format(mLineList.order.get(i)
                    .getHjgc_sum()) + "");
            mViewHoderItem.tv_gmgc.setText(dfint.format(mLineList.order.get(i)
                    .getGmgc_sum()) + "");
        }
        return convertview;
    }

    public View addView(int i) {
        ViewHoderItem mViewHoderItem = new ViewHoderItem();
        View v = LayoutInflater.from(context).inflate(
                R.layout.zt_z_tanshang_huizong, null);
        MyHScrollView scrollView1 = (MyHScrollView) v
                .findViewById(R.id.complete_horizontalScrollView1);
        mViewHoderItem.scrollView = scrollView1;
        mViewHoderItem.tv_xianbie = (TextView) v
                .findViewById(R.id.tv_complete_xianbie);
        mViewHoderItem.tv_gg = (TextView) v
                .findViewById(R.id.tv_complete_gg);
        mViewHoderItem.tv_csgh = (TextView) v
                .findViewById(R.id.tv_complete_csgh);
        mViewHoderItem.tv_xsgh = (TextView) v
                .findViewById(R.id.tv_complete_xsgh);
        mViewHoderItem.tv_qyh = (TextView) v
                .findViewById(R.id.tv_complete_qyh);
        mViewHoderItem.tv_lrh = (TextView) v
                .findViewById(R.id.tv_complete_lrh);
        mViewHoderItem.tv_jghf = (TextView) v
                .findViewById(R.id.tv_complete_jghf);
        mViewHoderItem.tv_kdxc = (TextView) v
                .findViewById(R.id.tv_complete_kdxc);
        mViewHoderItem.tv_hjgc = (TextView) v
                .findViewById(R.id.tv_complete_hjgc);
        mViewHoderItem.tv_gmgc = (TextView) v
                .findViewById(R.id.tv_complete_gmgc);
        MyHScrollView headSrcrollView = (MyHScrollView) mHead
                .findViewById(R.id.complete_horizontalScrollView1);
        headSrcrollView
                .AddOnScrollChangedListener(new OnScrollChangedListenerImp(
                        scrollView1));
        if (i % 2 == 0) {
            v.setBackgroundColor(context.getResources().getColor(R.color.listview_item_jiaoti_color));
        } else {
            v.setBackgroundColor(context.getResources().getColor(R.color.allbackground));
        }
        v.setTag(mViewHoderItem);
        return v;
    }

    class ViewHolder {

        LinearLayout zt_layout_sum_item;
        private TextView tv_xianming;

    }

    class ViewHoderItem {
        HorizontalScrollView scrollView;

        private TextView tv_xianbie;
        private TextView tv_gg;
        private TextView tv_csgh;
        private TextView tv_xsgh;
        private TextView tv_qyh;
        private TextView tv_lrh;
        private TextView tv_jghf;
        private TextView tv_kdxc;
        private TextView tv_hjgc;
        private TextView tv_gmgc;
    }

    class OnScrollChangedListenerImp implements MyHScrollView.OnScrollChangedListener {
        MyHScrollView mScrollViewArg;

        public OnScrollChangedListenerImp(MyHScrollView scrollViewar) {
            mScrollViewArg = scrollViewar;
        }

        @Override
        public void onScrollChanged(int l, int t, int oldl, int oldt) {
            mScrollViewArg.smoothScrollTo(l, t);
        }
    }

    ;

}
