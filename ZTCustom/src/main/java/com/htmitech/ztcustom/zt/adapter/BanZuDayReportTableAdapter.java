package com.htmitech.ztcustom.zt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.constant.BanZuDayReportBean;
import com.htmitech.ztcustom.zt.view.hvlistview.MyHScrollView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class BanZuDayReportTableAdapter extends BaseAdapter {

    private Context context;
    private View mHead;
    private ArrayList<BanZuDayReportBean> array;
    DecimalFormat df = new DecimalFormat("#.000");

    public BanZuDayReportTableAdapter(ArrayList<BanZuDayReportBean> array,
                                      Context context, View mHead) {
        super();
        this.array = array;
        this.context = context;
        this.mHead = mHead;
        notifyDataSetChanged();
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
    public View getView(int position, View convertView, ViewGroup continer) {
        // TODO Auto-generated method stub
        ViewHolder vh;
        ViewHolderItem vhi;
        BanZuDayReportBean bean = array.get(position);
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.banzu_day_report_item, null, false);

            vh.ll_banzu_item = (LinearLayout) convertView
                    .findViewById(R.id.banzu_ll_layout_item);
            vh.tv_banzu = (TextView) convertView
                    .findViewById(R.id.tv_banzuitem_banzu);
            if (position % 2 == 0) {
                convertView.setBackgroundColor(context.getResources().getColor(R.color.listview_item_jiaoti_color));
            }

            for (int i = 0; i < bean.getBanzuitem().size(); i++) {
                vh.ll_banzu_item.addView(addView(i));
            }
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
            int num = bean.getBanzuitem().size() - vh.ll_banzu_item.getChildCount();
            if (num < 0) {
                int count = vh.ll_banzu_item.getChildCount() - 1;
                for (int i = 0; i < -num; i++) {
                    vh.ll_banzu_item.removeViewAt(count - i);
                }
            } else if (num > 0) {
                for (int i = 0; i < num; i++) {
                    vh.ll_banzu_item.addView(addView(i));
                }
            }
        }

        vh.tv_banzu.setText(bean.getMerge_name());
        for (int i = 0; i < vh.ll_banzu_item.getChildCount(); i++) {
            vhi = (ViewHolderItem) vh.ll_banzu_item.getChildAt(i).getTag();
            vhi.tv_fenlei.setText(bean.getBanzuitem().get(i).getType_name());
            vhi.tv_gongzuoriqi.setText(bean.getBanzuitem().get(i).getWork_day());
            vhi.tv_qidianlicheng.setText(df.format(bean.getBanzuitem().get(i).getStart_milage()));
            vhi.tv_zhongdianlicheng.setText(bean.getBanzuitem().get(i).getMilage_start_end().toString());
            vhi.tv_gudaohao.setText(bean.getBanzuitem().get(i).getGdh());
            vhi.tv_daochahao.setText(bean.getBanzuitem().get(i).getDch());
            vhi.tv_ganggui.setText(bean.getBanzuitem().get(i).getGg_gg() + "");
            vhi.tv_changshanguanghan.setText(bean.getBanzuitem().get(i).getHf_csgh() + "");
            vhi.tv_xianshanguanghan.setText(bean.getBanzuitem().get(i).getHf_xsgh() + "");
            vhi.tv_lvrehan.setText(bean.getBanzuitem().get(i).getHf_lrh() + "");
            vhi.tv_jiaguhanfeng.setText(bean.getBanzuitem().get(i).getHf_jghf() + "");
            vhi.tv_kedongxincha.setText(bean.getBanzuitem().get(i).getDc_kdxc() + "");
            vhi.tv_hejingangcha.setText(bean.getBanzuitem().get(i).getDc_hjgc() + "");
            vhi.tv_gaomenggangcha.setText(bean.getBanzuitem().get(i).getDc_gmgc() + "");
            vhi.tv_weiwanchengyuanyin.setText(bean.getBanzuitem().get(i).getWwcyy());
            vhi.tv_gongzuoneirong.setText(bean.getBanzuitem().get(i).getGznr());
        }

        return convertView;
    }

    public View addView(int i) {
        ViewHolderItem vhi = new ViewHolderItem();
        View view = LayoutInflater.from(context).inflate(
                R.layout.banzu_all_item, null, false);
        MyHScrollView scrollView1 = (MyHScrollView) view
                .findViewById(R.id.banzu_horizontalScrollView1);
        vhi.scrollView = scrollView1;

        vhi.tv_fenlei = (TextView) view.findViewById(R.id.tv_banzu_fenlei);
        vhi.tv_gongzuoriqi = (TextView) view
                .findViewById(R.id.tv_banzu_gongzuoriqi);
        vhi.tv_qidianlicheng = (TextView) view
                .findViewById(R.id.tv_banzu_qidianlicheng);
        vhi.tv_zhongdianlicheng = (TextView) view
                .findViewById(R.id.tv_banzu_zongdianlicheng);
        vhi.tv_gudaohao = (TextView) view.findViewById(R.id.tv_banzu_gudaohao);
        vhi.tv_daochahao = (TextView) view
                .findViewById(R.id.tv_banzu_daochahao);
        vhi.tv_gongzuoneirong = (TextView) view
                .findViewById(R.id.tv_banzu_gongzuoneirong);
        vhi.tv_ganggui = (TextView) view.findViewById(R.id.tv_banzu_ganggui);
        vhi.tv_changshanguanghan = (TextView) view
                .findViewById(R.id.tv_banzu_changshanguanghan);
        vhi.tv_xianshanguanghan = (TextView) view
                .findViewById(R.id.tv_banzu_xianshanguanghan);
        vhi.tv_lvrehan = (TextView) view.findViewById(R.id.tv_banzu_lvrehan);
        vhi.tv_jiaguhanfeng = (TextView) view
                .findViewById(R.id.tv_banzu_jiaguhanfeng);
        vhi.tv_kedongxincha = (TextView) view
                .findViewById(R.id.tv_banzu_kedongxincha);
        vhi.tv_hejingangcha = (TextView) view
                .findViewById(R.id.tv_banzu_hejingangcha);
        vhi.tv_gaomenggangcha = (TextView) view
                .findViewById(R.id.tv_banzu_gaomenggangcha);
        vhi.tv_weiwanchengyuanyin = (TextView) view
                .findViewById(R.id.tv_banzu_weiwanchengyuanyin);
        MyHScrollView headSrcrollView = (MyHScrollView) mHead
                .findViewById(R.id.banzu_horizontalScrollView1);
        headSrcrollView
                .AddOnScrollChangedListener(new OnScrollChangedListenerImp(
                        scrollView1));
        if (i % 2 == 0) {
            view.setBackgroundColor(context.getResources().getColor(
                    R.color.listview_item_jiaoti_color));
        } else {
            view.setBackgroundColor(context.getResources().getColor(
                    R.color.allbackground));
        }
        view.setTag(vhi);
        return view;
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

    class ViewHolder {
        private TextView tv_banzu;

        private LinearLayout ll_banzu_item;
    }

    class ViewHolderItem {

        HorizontalScrollView scrollView;
        private TextView tv_fenlei;
        private TextView tv_gongzuoriqi;
        private TextView tv_qidianlicheng;
        private TextView tv_zhongdianlicheng;
        private TextView tv_gudaohao;
        private TextView tv_daochahao;
        private TextView tv_gongzuoneirong;
        private TextView tv_ganggui;
        private TextView tv_changshanguanghan;
        private TextView tv_xianshanguanghan;
        private TextView tv_lvrehan;
        private TextView tv_jiaguhanfeng;
        private TextView tv_kedongxincha;
        private TextView tv_hejingangcha;
        private TextView tv_gaomenggangcha;
        private TextView tv_weiwanchengyuanyin;

    }

}
