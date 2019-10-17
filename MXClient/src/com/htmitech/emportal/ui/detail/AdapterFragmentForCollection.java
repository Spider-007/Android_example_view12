package com.htmitech.emportal.ui.detail;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.htmitech.emportal.entity.InfoTab;
import com.htmitech.fragment.InitWebViewFragment;

import htmitech.com.componentlibrary.base.BaseFragment;

//组织 AdapterFragment的文件 ， 可动态生成 最多4 个的fragment  
//弱引用，在 WeakReference中 引用的对象，如果对象的其他引用为空了，他引用的对象会释放。
public class AdapterFragmentForCollection extends FragmentPagerAdapter {
    private FragmentManager mFm;
    private Context mContext;
    private HashMap<Integer, WeakReference<BaseFragment>> mFragments = new HashMap<Integer, WeakReference<BaseFragment>>();
    private int mChildCount = 0;

    public static final int TAB_FORM = 0;
    public static final int TAB_TEXT = 1;
    public static final int TAB_ATTACHMENT = 2;
    public static final int TAB_FLOW = 3;

    private List<ChildType> mChildIndex = new ArrayList<ChildType>();
    public static String[] Type = new String[]{};



    public enum ChildType {
        TAB_FORM, TAB_TEXT, TAB_ATTACHMENT, TAB_FLOW, TAB_START_FORM, TAB_AIP, TAB_URL, TAB_DOCAIP
    }

    private  String currentDocId;
    public List<InfoTab> tabs;
    public  String app_id;

    //


    public AdapterFragmentForCollection(Context context, FragmentManager fm,
                                        List<ChildType> mChildIndex) {
        super(fm);
        this.mChildIndex.addAll(mChildIndex);
        mContext = context.getApplicationContext();
        mFm = fm;
    }

    public AdapterFragmentForCollection(Context context, FragmentManager fm,
                                        List<ChildType> mChildIndex, String docID, List<InfoTab> tabList,String app_id) {
        super(fm);
        this.mChildIndex.addAll(mChildIndex);
        mContext = context.getApplicationContext();
        mFm = fm;
        this.tabs = tabList;
        this.currentDocId = docID;
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

    private BaseFragment makeModule(int position, InfoTab tab) {
        BaseFragment baseFr = null;
        switch (mChildIndex.get(position)) {
            case TAB_FORM:
//                baseFr = new FormFragment(tab.Regions,app_id); //paramaters.get("TAB_FORM")
                break;

            case TAB_TEXT:
                baseFr = new TextFragment(app_id);
                break;
            case TAB_ATTACHMENT:
                baseFr = new AttachmentFragment(app_id);
                break;
            case TAB_FLOW:
                baseFr = new FlowFragment();
                break;
            case TAB_AIP:
//                baseFr = new AIPFragment(tab,currentDocId);
                break;
            case TAB_DOCAIP:
//                baseFr = new DOCAIPFragment(tab,currentDocId);
                break;

            case TAB_START_FORM://发起的流程表单
                baseFr = new StartFormFragment();
//                baseFr=new FormFragment(tab.Regions);
                break;
            case TAB_URL:

                baseFr = new InitUrlFragment();
                break;

        }


        return baseFr;
    }

    @Override
    public int getCount() {
        return mChildIndex.size();
    }

}
