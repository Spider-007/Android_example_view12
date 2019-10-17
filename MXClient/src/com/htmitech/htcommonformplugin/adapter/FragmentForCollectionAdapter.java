package com.htmitech.htcommonformplugin.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.htmitech.htcommonformplugin.entity.Tabitems;
import com.htmitech.htcommonformplugin.fragment.GeneralFormDetailFragment;
import com.htmitech.htcommonformplugin.fragment.GeneralFormAttachmentFragment;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import htmitech.com.componentlibrary.base.BaseFragment;

//组织 AdapterFragment的文件 ， 可动态生成 最多4 个的fragment  
//弱引用，在 WeakReference中 引用的对象，如果对象的其他引用为空了，他引用的对象会释放。
public class FragmentForCollectionAdapter extends FragmentPagerAdapter {
    private FragmentManager mFm;
    private Context mContext;
    private HashMap<Integer, WeakReference<BaseFragment>> mFragments = new HashMap<Integer, WeakReference<BaseFragment>>();
    private int mChildCount = 0;
    private List<ChildType> mChildIndex = new ArrayList<ChildType>();
    public static String[] Type = new String[]{};
    public List<Tabitems> tabs;
    public  String app_id;

    public enum ChildType {
        TAB_FORM, TAB_TEXT, TAB_ATTACHMENT, TAB_FLOW, TAB_START_FORM, TAB_AIP, TAB_URL, TAB_DOCAIP
    }

    public FragmentForCollectionAdapter(Context context, FragmentManager fm,
                                        List<ChildType> mChildIndex, List<Tabitems> tabList, String app_id) {
        super(fm);
        this.mChildIndex.addAll(mChildIndex);
        mContext = context.getApplicationContext();
        mFm = fm;
        this.tabs = tabList;
        this.app_id = app_id;
    }
    @Override
    public Fragment getItem(int position) {
        if (mFragments.containsKey(position)) {
            WeakReference<BaseFragment> ref = mFragments.get(position);
            if (ref.get() != null) {
                return ref.get();
            }
        }
        BaseFragment item = null;
        if (tabs != null && position < tabs.size())
            item = makeModule(position, tabs.get(position));
        else
            item = makeModule(position, null);
        if (item != null) {
            WeakReference<BaseFragment> ref = new WeakReference<BaseFragment>(
                    item);
            mFragments.put(position, ref);
        }
        return item;
    }

    private BaseFragment makeModule(int position, Tabitems tab) {
        BaseFragment baseFr = null;
        switch (mChildIndex.get(position)) {
            case TAB_FORM:
                baseFr = new GeneralFormDetailFragment(tab.getRegions(),app_id);
                break;
            case TAB_ATTACHMENT:
                baseFr = new GeneralFormAttachmentFragment(tab.getTab_formkey(),app_id);
                break;
        }
        return baseFr;
    }

    @Override
    public int getCount() {
        return mChildIndex.size();
    }

}
