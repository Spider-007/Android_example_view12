package com.htmitech.htworkflowformpluginnew.adapter;

import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import htmitech.com.componentlibrary.base.BaseFragment;

import com.htmitech.emportal.common.CommonSettings;
import com.htmitech.htnativestartformplugin.fragment.StartFormFragment;
import com.htmitech.htworkflowformpluginnew.fragment.WorkFlowAttachmentFragment;
import com.htmitech.htworkflowformpluginnew.fragment.WorkFlowFlowFragment;
import com.htmitech.htworkflowformpluginnew.fragment.WorkFlowFormDetailFragment;
import com.htmitech.htworkflowformpluginnew.fragment.WorkFlowInitUrlFragment;
import com.htmitech.htworkflowformpluginnew.fragment.WorkFlowTextFragment;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    public LocalActivityManager localActivityManager;
    private View pdfSignTab;


    public enum ChildType {
        TAB_FORM, TAB_TEXT, TAB_ATTACHMENT, TAB_FLOW, TAB_START_FORM, TAB_AIP, TAB_URL, TAB_DOCAIP, TAB_OFD_DOC, TAB_OFD_FORM, TAB_SCHEDULE_FORM, TAB_PDF_SIGN
    }

    private String currentDocId;
    public List<InfoTab> tabs;
    public String app_id;

    //


    public WorkFlowForCollectionAdapter(Context context, FragmentManager fm,
                                        List<ChildType> mChildIndex, LocalActivityManager localActivityManager) {
        super(fm);
        this.mChildIndex.addAll(mChildIndex);
        mContext = context.getApplicationContext();
        mFm = fm;
        this.localActivityManager = localActivityManager;
    }

    public WorkFlowForCollectionAdapter(Context context, FragmentManager fm,
                                        List<ChildType> mChildIndex, String docID, List<InfoTab> tabList, String app_id, int com_workflow_mobileconfig_include_options, LocalActivityManager localActivityManager) {
        super(fm);
        this.mChildIndex.addAll(mChildIndex);
        mContext = context.getApplicationContext();
        mFm = fm;
        this.tabs = tabList;
        this.currentDocId = docID;
        this.app_id = app_id;
        this.com_workflow_mobileconfig_include_options = com_workflow_mobileconfig_include_options;
        this.localActivityManager = localActivityManager;
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
    public Object instantiateItem(ViewGroup container, int position) {
        if (mChildIndex.get(position) == ChildType.TAB_PDF_SIGN) {
            String fileId = "";
            String fileType = ".pdf";
//            if (null != mDocResultInfo && null != mDocResultInfo.getResult() && null != mDocResultInfo.getResult().Docview_Pdf) {
//                fileId = mDocResultInfo.getResult().Docview_Pdf.file_id;
//                if (!TextUtils.isEmpty(mDocResultInfo.getResult().Docview_Pdf.file_extension))
//                    fileType = "." + mDocResultInfo.getResult().Docview_Pdf.file_extension;
//            }

//            File file = new File(SDCARD_PATH + "/办公室发文稿纸as.pdf");
//            File file = new File(SDCARD_PATH + "/办公室发文稿纸as.pdf");
//            File file = new File(  Environment
//                    .getExternalStorageDirectory().getAbsolutePath()+ File.separator+"bgsfwgz.pdf");
            if(null != getPdfSignFile() && getPdfSignFile().exists()){
                Uri uri = Uri.fromFile(getPdfSignFile());
                Intent intent;
                intent = new Intent("android.intent.action.VIEW", uri);
                intent.setClassName(mContext, "com.example.archivermodule.BookShower");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                pdfSignTab = getView("flow", intent);
                container.addView(this.pdfSignTab);
                return this.pdfSignTab;
            }else{
                Uri uri = Uri.fromFile(new File(Environment
                        .getExternalStorageDirectory().getPath().toString() + "/blank.pdf"));
                Intent intent;
                intent = new Intent("android.intent.action.VIEW", uri);
                intent.setClassName(mContext, "com.example.archivermodule.BookShower");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                pdfSignTab = getView("flow", intent);
                container.addView(this.pdfSignTab);
                return pdfSignTab;
            }

        } else {
            return super.instantiateItem(container, position);
        }


    }

    public View getView(String id, Intent intent) {
            return localActivityManager.startActivity(id, intent).getDecorView();
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
                if (tab != null) {
                    baseFr = new WorkFlowFormDetailFragment(tab, app_id, com_workflow_mobileconfig_include_options); //paramaters.get("TAB_FORM")
                }
                break;

            case TAB_TEXT:
                baseFr = new WorkFlowTextFragment(app_id);
                break;
            case TAB_ATTACHMENT:
                baseFr = new WorkFlowAttachmentFragment(app_id);
                break;
            case TAB_FLOW:
                if(null != tab)
                baseFr = new WorkFlowFlowFragment(tab.flowId);
                break;
            case TAB_AIP:
//                baseFr = new AIPFragment(tab,currentDocId);
                break;
            case TAB_DOCAIP:
//                baseFr = new DOCAIPFragment(tab,currentDocId);
                break;
            case TAB_START_FORM://发起的流程表单
                baseFr = new StartFormFragment(tab, app_id);
//                baseFr=new FormFragment(tab.Regions);
                break;
            case TAB_URL:
                baseFr = new WorkFlowInitUrlFragment();
                break;
            case TAB_SCHEDULE_FORM:
//                baseFr = new com.htmitech.scheduleDetail.fragment.ScheduleFormFragment(tab, app_id, com_workflow_mobileconfig_include_options);
                break;
            case TAB_PDF_SIGN:

                break;
            default:
                baseFr = new WorkFlowTextFragment(app_id);
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

    public boolean isTabForm() {
        return mChildIndex.contains(ChildType.TAB_FORM);
    }

    public int tabFormCount() {
        for (int i = 0; i < mChildIndex.size(); i++) {
            if (mChildIndex.get(i) == ChildType.TAB_FORM) {
                return i;
            }
        }
        return 0;
    }

    @Override
    public int getCount() {
        return mChildIndex.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        if (object instanceof Fragment) {
            return ((Fragment) object).getView() == view;
        } else {
            return object == view;
        }

    }

    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        if (mChildIndex.get(position) != ChildType.TAB_PDF_SIGN) {
            super.setPrimaryItem(container, position, object);
        }

    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        if (mChildIndex.get(position) != ChildType.TAB_PDF_SIGN) {
            super.destroyItem(container, position, object);
        }
    }
    public File pdfSignFile;
    public void setPdfSignFile(File file){
        pdfSignFile= file;
    }
    public File getPdfSignFile(){
        return pdfSignFile;
    }

}
