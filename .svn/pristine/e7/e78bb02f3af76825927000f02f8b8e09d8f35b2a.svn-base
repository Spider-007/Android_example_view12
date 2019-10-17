package adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fragment.ScheduleFormFragment;
import htmitech.com.componentlibrary.base.BaseFragment;
import htmitech.com.componentlibrary.entity.InfoTab;

//组织 AdapterFragment的文件 ， 可动态生成 最多4 个的fragment
//弱引用，在 WeakReference中 引用的对象，如果对象的其他引用为空了，他引用的对象会释放。
public class WorkFlowForCollectionAdapter extends FragmentPagerAdapter {
    private FragmentManager mFm;
    private Context mContext;
    private HashMap<Integer, WeakReference<BaseFragment>> mFragments = new HashMap<Integer, WeakReference<BaseFragment>>();
    private int mChildCount = 0;
    private int com_workflow_mobileconfig_include_options;

    public static final int TAB_FORM = 0;
    public static final int TAB_TEXT = 1;
    public static final int TAB_ATTACHMENT = 2;
    public static final int TAB_FLOW = 3;

    private List<ChildType> mChildIndex = new ArrayList<ChildType>();
    public static String[] Type = new String[]{};


    public enum ChildType {
        TAB_FORM, TAB_TEXT, TAB_ATTACHMENT, TAB_FLOW, TAB_START_FORM, TAB_AIP, TAB_URL, TAB_DOCAIP, TAB_OFD_DOC, TAB_OFD_FORM,TAB_SCHEDULE_FORM
    }

    private String currentDocId;
    public List<InfoTab> tabs;
    public String app_id;

    //


    public WorkFlowForCollectionAdapter(Context context, FragmentManager fm,
                                        List<ChildType> mChildIndex) {
        super(fm);
        this.mChildIndex.addAll(mChildIndex);
        mContext = context.getApplicationContext();
        mFm = fm;
    }

    public WorkFlowForCollectionAdapter(Context context, FragmentManager fm,
                                        List<ChildType> mChildIndex, String docID, List<InfoTab> tabList, String app_id, int com_workflow_mobileconfig_include_options) {
        super(fm);
        this.mChildIndex.addAll(mChildIndex);
        mContext = context.getApplicationContext();
        mFm = fm;
        this.tabs = tabList;
        this.currentDocId = docID;
        this.app_id = app_id;
        this.com_workflow_mobileconfig_include_options = com_workflow_mobileconfig_include_options;
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
            case TAB_SCHEDULE_FORM:
                baseFr = new ScheduleFormFragment(tab, app_id, com_workflow_mobileconfig_include_options);
                break;
            default:
                baseFr = new ScheduleFormFragment(tab, app_id, com_workflow_mobileconfig_include_options);
                break;
//            case TAB_OFD_DOC:
//                baseFr = new WorkFlowOFDFragment(app_id, tab.tabType);
//                break;
//            case TAB_OFD_FORM:
//                baseFr = new WorkFlowOFDFragment(app_id, tab.tabType);
//                break;

        }
        return baseFr;
    }
    public boolean isTabForm(){
        return mChildIndex.contains(ChildType.TAB_FORM);
    }

    public int tabFormCount(){
        for(int i = 0; i < mChildIndex.size() ;i++){
            if(mChildIndex.get(i) == ChildType.TAB_FORM){
                return i;
            }
        }
        return 0;
    }
    @Override
    public int getCount() {
        return mChildIndex.size();
    }

}
