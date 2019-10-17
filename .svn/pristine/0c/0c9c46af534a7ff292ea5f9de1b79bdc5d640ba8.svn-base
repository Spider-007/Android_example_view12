package com.htmitech.htexceptionmanage.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.htmitech.htexceptionmanage.entity.SourceContentInfo;
import com.htmitech.htexceptionmanage.fragment.ManageExceptionFormFragment;
import com.htmitech.htexceptionmanage.fragment.ManageExceptionTextFragment;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import htmitech.com.componentlibrary.base.BaseFragment;

public class ManageExceptionForCollectionAdapter extends FragmentPagerAdapter {
    private FragmentManager mFm;
    private Context mContext;
    private HashMap<Integer, WeakReference<BaseFragment>> mFragments = new HashMap<Integer, WeakReference<BaseFragment>>();
    private List<ChildType> mChildIndex = new ArrayList<ChildType>();
    public static String[] Type = new String[]{};
    public String app_id;

    public enum ChildType {
        TAB_FORM, TAB_TEXT
    }


    public ManageExceptionForCollectionAdapter(Context context, FragmentManager fm,
                                               List<ChildType> mChildIndex) {
        super(fm);
        this.mChildIndex.addAll(mChildIndex);
        mContext = context.getApplicationContext();
        mFm = fm;
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
        item = makeModule(position);
        if (item != null) {
            WeakReference<BaseFragment> ref = new WeakReference<BaseFragment>(
                    item);
            mFragments.put(position, ref);
        }
        return item;
    }

    private BaseFragment makeModule(int position) {
        BaseFragment baseFr = null;
        switch (mChildIndex.get(position)) {
            case TAB_FORM:
                baseFr = new ManageExceptionFormFragment();
                break;
            case TAB_TEXT:
                baseFr = new ManageExceptionTextFragment();
                break;
        }
        return baseFr;

    }

    @Override
    public int getCount() {
        return mChildIndex.size();
    }

}
