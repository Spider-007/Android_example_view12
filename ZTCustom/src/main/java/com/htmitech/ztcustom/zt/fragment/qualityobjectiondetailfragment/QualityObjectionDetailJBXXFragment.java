package com.htmitech.ztcustom.zt.fragment.qualityobjectiondetailfragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;


import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.adapter.QualityObjectionDetailJBXXAdapter;
import com.htmitech.ztcustom.zt.app.ZTCustomInit;
import com.htmitech.ztcustom.zt.base.BaseFragment;
import com.htmitech.ztcustom.zt.constant.ContantValues;
import com.htmitech.ztcustom.zt.domain.QualityObjectionDetailSearchResult;
import com.htmitech.ztcustom.zt.domain.QualityObjectionDetailSearchResultList;
import com.htmitech.ztcustom.zt.domain.QualityObjectionDetailsListRequest;
import com.htmitech.ztcustom.zt.interfaces.ObserverCallBack;
import com.htmitech.ztcustom.zt.interfaces.OnClickBigImageListener;
import com.htmitech.ztcustom.zt.thread.AnsynHttpRequest;
import com.htmitech.ztcustom.zt.unit.CHTTP;
import com.htmitech.ztcustom.zt.util.FastJsonUtils;
import com.htmitech.ztcustom.zt.util.ImageUtils;
import com.htmitech.ztcustom.zt.view.photoview.PhotoView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class QualityObjectionDetailJBXXFragment extends BaseFragment implements View.OnClickListener {


    private ViewPager viewPager;
    private ListView listView;
    private List<View> picUrlList;
    private PhotoView bigImageView;
    int j = 0;
    private String id;
    private int resourceId = R.drawable.img_zlyy_list_thumbnail;
    private SimpleAdapter simpleAdapter;
    private List<Map<String, Object>> data;
    private ArrayList<QualityObjectionDetailSearchResultList> list;
    private ViewPagerAdapter viewPagerAdapter;
    private OnClickBigImageListener imageListener;
    private QualityObjectionDetailJBXXAdapter jbxxAdapter;


    public QualityObjectionDetailJBXXFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quality_objection_detail_jbxx, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnClickBigImageListener) {
            imageListener = (OnClickBigImageListener) context;
        }
    }

    @Override
    protected void initView() {
        viewPager = (ViewPager) getView().findViewById(R.id.vp_quality_objection_details);
        listView = (ListView) getView().findViewById(R.id.quality_objection_detail_listview);
        bigImageView = (PhotoView) getView().findViewById(R.id.iv_quality_objection_detail_big_pic);
        bigImageView.setOnClickListener(this);
        bigImageView.setVisibility(View.GONE);
    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        id = bundle.getString("id");
        Log.e("YJH", "QualityObjectionDetailJBXXFragment->initData->id->>> "+id );
        //上面的viewpager
        picUrlList = new ArrayList<View>();
        viewPagerAdapter = new ViewPagerAdapter(picUrlList);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                j = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //下方的listview
        data = getData();
        jbxxAdapter=new QualityObjectionDetailJBXXAdapter(getActivity(),data);
        listView.setAdapter(jbxxAdapter);

//        int resource = R.layout.zb_broad_detal_item;
//        // from表示数组中存储数据源中的Map中的各个Key
//        String[] from = {"key", "value"};
//        // to表示数据源中的Map中的各个Key对应的Value应该显示到哪些控件
//        int[] to = {R.id.tv_key, R.id.tv_value};
//        simpleAdapter = new SimpleAdapter(getActivity(), data, resource, from, to);
//        listView.setAdapter(simpleAdapter);
//        simpleAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.iv_quality_objection_detail_big_pic:
//
//                break;
//        }
    }
//    <--------------------Administrator -> 2019-8-17:18:27:质量异议 crash处理-> try--------------------->
    private List<Map<String, Object>> getData() {

        showProgressDialog(getActivity());
        final List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

        AnsynHttpRequest.request(getActivity(), updateData(), ContantValues.GETZLYYDETAILSEARCH, CHTTP.POST, new ObserverCallBack() {

            @Override
            public void success(String successMessage) {
                dimssDialog();
                QualityObjectionDetailSearchResult result = FastJsonUtils.getPerson(successMessage, QualityObjectionDetailSearchResult.class);
                if (result == null) {
                    return;
                }
                try {
                    list = new ArrayList<QualityObjectionDetailSearchResultList>();
                    list.clear();
                    list.addAll(result.results);
                    Map<String, Object> item;
                    Log.e("tag", "" + successMessage);
                    for (int i = 0; i < list.size(); i++) {
                        item = new HashMap<String, Object>();
                        item.put("key", list.get(i).key);
                        item.put("value", list.get(i).value);
                        data.add(item);
                    }
//                simpleAdapter.notifyDataSetChanged();
                    jbxxAdapter.notifyDataSetChanged();
                    setViewPagerImage(result.smallphoto);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("YJH", "success->null-->>"+e + "----------------" +e.getMessage() );
                }
            }

            @Override
            public void notNetwork() {
                // TODO Auto-generated method stub
                dimssDialog();
            }

            @Override
            public void fail(String exceptionMessage) {
                // TODO Auto-generated method stub
                dimssDialog();
            }
        });
        return data;
    }


    @SuppressLint("SetTextI18n")
    public void setViewPagerImage(List<String> smallphoto) {
        try {
            for (int i = 0; i < smallphoto.size(); i++) {
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.qualityobjectionetails_viewpager_item, null);
                ImageView iv = (ImageView) view.findViewById(R.id.quality_objection_detail_viewpager_item_imageview);
                TextView textView = (TextView) view.findViewById(R.id.quality_objection_detail_textview);
                textView.setText((i + 1) + "/" + smallphoto.size());
                Bitmap bitmap = ImageUtils.base64ToBitmap(smallphoto.get(i));
                iv.setImageBitmap(bitmap);
    //            iv.setBackgroundResource(smallphoto.get(i));
                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("OnClick", j + "");
                        imageListener.clickImageView(id, id + "-0" + (j + 1));
                    }
                });
                picUrlList.add(view);
            }
            viewPagerAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("YJH", "setViewPagerImage: "+e + "-------817------" +e.getMessage() );
        }
    }


    private QualityObjectionDetailsListRequest updateData() {
        QualityObjectionDetailsListRequest request = new QualityObjectionDetailsListRequest();
//        request.userid =  ZTCustomInit.get().getmCache().getmListDetails().AccountId;
        request.userid = ZTCustomInit.get().getmCache().getCisAccountDetail().cisAccountId;
        Log.e("YJH", "updateData->QualityObjectionDetailJBXXFragment:"+request.userid);
        request.id = id;
        return request;
    }


    public class ViewPagerAdapter extends PagerAdapter {
        private List<View> list;

        public ViewPagerAdapter(List<View> list) {
            this.list = list;
        }

        @Override
        public int getCount() {

            if (list != null && list.size() > 0) {
                return list.size();
            } else {
                return 0;
            }
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(list.get(position));
            return list.get(position);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

    }


    @Override
    public void lazyLoad() {


    }

    @Override
    public boolean onBackPressed() {

        return false;
    }
}
