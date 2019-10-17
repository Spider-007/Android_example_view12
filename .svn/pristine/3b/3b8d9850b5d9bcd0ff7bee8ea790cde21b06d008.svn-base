package htmitech.com.componentlibrary;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import htmitech.com.componentlibrary.entity.DocResultInfo;
import htmitech.com.componentlibrary.entity.EditField;
import htmitech.com.componentlibrary.entity.ReportSosoResult;
import htmitech.com.componentlibrary.unit.DensityUtil;

/**
 * Created by htrf-pc on 2018-3-2.
 */
public class RadioCheckLayout {
    private Type input_type = Type.SingleSelect;
    private Map<Integer, ArrayList<DamageCaeck>> cacheMap;
    private Context context;
    private TextView checkView;
    private ArrayList<DamageCaeck> mDamageCaeckList = new ArrayList<DamageCaeck>();

    private List<EditField> EditFileds = null; // 缓存已经编辑的表单字段，回发用。
    private EditField edt = null;
    private DocResultInfo mDocResultInfo = null;
    private OnClickChecked mOnClickChecked;



    public RadioCheckLayout(Context context, LinearLayout layout_type, List<ReportSosoResult> reportSosoResults, String value, Type inputType) {
        this.context = context;
        this.input_type = inputType;
        cacheMap = new HashMap<Integer, ArrayList<DamageCaeck>>();
        LinearLayout layout_child = new LinearLayout(context);
        layout_child.setOrientation(LinearLayout.VERTICAL);
        layout_child.setGravity(Gravity.CENTER_VERTICAL);

        LinearLayout.LayoutParams layoutParams_child = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layout_child.setLayoutParams(layoutParams_child);
        layout_type.addView(layout_child);

        List<ReportSosoResult> mDictitemlistList = reportSosoResults;
        WindowManager wm = ((Activity) context).getWindowManager();
        int width = wm.getDefaultDisplay().getWidth();
        int num = 0;
        int textHight = 70;
        if (width == 1080) {
            textHight = DensityUtil.dip2px(context, 40);
        } else {
            textHight = DensityUtil.dip2px(context, 40);
        }

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER_VERTICAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT, textHight);
        layoutParams.setMargins(0, 10, 20, 10);
        layout.setLayoutParams(layoutParams);
        layout.setOrientation(LinearLayout.HORIZONTAL);
                    /*
                     * 排序
                     */
        Comparator comp = new Comparator() {
            public int compare(Object o1, Object o2) {
                ReportSosoResult p1 = (ReportSosoResult) o1;
                ReportSosoResult p2 = (ReportSosoResult) o2;
                if (p1.name.length() < p2.name.length())
                    return -1;
                else if (p1.name.length() == p2.name.length())
                    return 0;
                else if (p1.name.length() > p2.name.length())
                    return 1;
                return 0;
            }
        };
        Collections.sort(mDictitemlistList, comp);
        /*
         * 四个字 每个TextView的宽度
		 */
        int colum4 = (width - 160) / 4;
        /*
         *两个字 每个TextView的宽度
		 */
        int colum2 = (width - 80) / 2;
        /*
         *一个字 每个TextView的宽度
		 */
        int colum1 = (width - 40) / 1;

        int columWith = (width - 80) / 3;
        int indexColum4 = 0, indexColum2 = 0, indexColum1 = 0;
        for (ReportSosoResult dic : mDictitemlistList) {
            num = (dic.name.length() * 34); //34是根据 字的大小和padding的左右宽度而来的 14 +
            if (num < colum4) {
                if (indexColum4 == 0) {
                    layout = new LinearLayout(context);
                    layout.setLayoutParams(layoutParams);
                    layout_child.addView(layout);
                }
                TextView textView = new TextView(context);
                LinearLayout.LayoutParams textparams = new LinearLayout.LayoutParams(
                        colum4, textHight, 1);
                textparams.setMargins(20, 0, 0, 0);
                textView.setText(dic.name);
                textView.setBackgroundResource(R.drawable.zt_fragment_gridview_adapter);
                textView.setLayoutParams(textparams);
                textView.setTextSize(14);
                textView.setGravity(Gravity.CENTER);
                textView.setPadding(10, 10, 10, 10);
                textView.setOnClickListener(new NameOnClickListener(layout_child, dic, textView, 0));
                layout.addView(textView);
                indexColum4++;
                if (indexColum4 == 3) {
                    indexColum4 = 0;
                }
                initChecked(textView,dic,value,inputType);

            } else if (num < colum2) {
                /**
                 * 把上一个最后一个没有Add进去的给add进去
				 */
                if(indexColum4 != 0 && layout.getChildCount() < 2){
                    indexColum4 = 0;
                    TextView mTextView = (TextView) layout.getChildAt(0);
                    LinearLayout.LayoutParams textparams = new LinearLayout.LayoutParams(
                            columWith, textHight);
                    textparams.setMargins(20, 0, 0, 0);
                    mTextView.setLayoutParams(textparams);
                }else{
                    indexColum4 = 0;
                    if (indexColum2 == 0) {
                        layout = new LinearLayout(context);
                        layout.setLayoutParams(layoutParams);
                        layout.removeAllViews();
                        layout_child.addView(layout);
                    }
                }

                TextView textView = new TextView(context);
                LinearLayout.LayoutParams textparams = new LinearLayout.LayoutParams(
                        colum2, textHight, 1);
                textparams.setMargins(20, 0, 0, 0);
                textView.setText(dic.name);
                textView.setBackgroundResource(R.drawable.zt_fragment_gridview_adapter);
                textView.setLayoutParams(textparams);
                textView.setTextSize(14);
                textView.setGravity(Gravity.CENTER);
                textView.setPadding(10, 10, 10, 10);
                textView.setOnClickListener(new NameOnClickListener(layout_child, dic, textView, 0));
                layout.addView(textView);
                indexColum2++;
                if (layout.getChildCount() == 2) {
                    indexColum2 = 0;
                }
                initChecked(textView,dic,value,inputType);
            } else {
				/**
				 * 把上一个最后一个没有Add进去的给add进去
				 */
                layout = new LinearLayout(context);
                layout.setLayoutParams(layoutParams);
                layout.removeAllViews();
                TextView textView = new TextView(context);
                LinearLayout.LayoutParams textparams = new LinearLayout.LayoutParams(
                        colum1, textHight, 1);
                textparams.setMargins(20, 0, 0, 0);
                textView.setText(dic.name);
                textView.setSingleLine(true);
                textView.setEllipsize(TextUtils.TruncateAt.END);
                textView.setBackgroundResource(R.drawable.zt_fragment_gridview_adapter);
                textView.setLayoutParams(textparams);
                textView.setTextSize(14);
                textView.setGravity(Gravity.CENTER);
                textView.setPadding(10, 10, 10, 10);
                textView.setOnClickListener(new NameOnClickListener(layout_child, dic, textView, 0));
                layout.addView(textView);
                layout_child.addView(layout);
                initChecked(textView,dic,value,inputType);
            }
        }

        layout = new LinearLayout(context);
        layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT, 20);
        layout.setLayoutParams(layoutParams);
        layout_child.addView(layout);
    }

    /**
     * 每个TextView的点击事件
     */
    public class NameOnClickListener implements View.OnClickListener {

        private ReportSosoResult mDictitemlist;

        private TextView tvName;

        private LinearLayout layout_child;
        private int postion;

        public NameOnClickListener(LinearLayout layout_child, ReportSosoResult mDictitemlist, TextView tvName, int postion) {
            this.mDictitemlist = mDictitemlist;
            this.tvName = tvName;
            this.layout_child = layout_child;
            this.postion = postion;
        }

        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub
            boolean isFlag = true;
            if (input_type == null || input_type.equals("")) {
                input_type = Type.SingleSelect;
            }
            if (input_type.equals(Type.SingleSelect)) {
                if (cacheMap.get(postion) == null || cacheMap.get(postion).size() == 0) {
                    cacheMap.put(postion, new ArrayList<DamageCaeck>());
                }

                cacheMap.get(postion).clear();
                for (int i = 0; i < layout_child.getChildCount(); i++) {
                    LinearLayout mView = (LinearLayout) layout_child.getChildAt(i);
                    for (int j = 0; j < mView.getChildCount(); j++) {
                        TextView view = (TextView) mView.getChildAt(j);
                        view.setTextColor(context.getResources().getColor(R.color.ht_check_text_color));
                        view.setBackgroundResource(R.drawable.zt_fragment_gridview_adapter);
                    }

                }
                tvName.setTextColor(context.getResources().getColor(R.color.white));
                tvName.setBackgroundResource(R.drawable.fragment_gridview_adapter_down);


                /**
                 * 缓存当前集合
                 */
                cacheMap.get(postion).add(new DamageCaeck(mDictitemlist, isFlag, postion));
                mDamageCaeckList
                        .add(new DamageCaeck(mDictitemlist, isFlag, postion));
                checkView = tvName;
                String strValue = "";
                if(!mDictitemlist.getId().equals("")){
                    strValue = mDictitemlist.getId();
                }else if(!mDictitemlist.getValue().equals("")){
                    strValue = mDictitemlist.getValue();
                }else {
                    strValue = mDictitemlist.getName();
                }
                if (mOnClickChecked!=null){
                    mOnClickChecked.getOnChecked(strValue);
                }
            } else {
                for (DamageCaeck mDamageCaeck : mDamageCaeckList) {
                    if (mDamageCaeck.mDictitemlist.getName().equals(
                            mDictitemlist.getName())) {
                        mDamageCaeckList.remove(mDamageCaeck);
                        tvName.setTextColor(context.getResources().getColor(R.color.ht_check_text_color));
                        tvName.setBackgroundResource(R.drawable.zt_fragment_gridview_adapter);
                        isFlag = false;
                        break;
                    }
                }
                if (isFlag) {
                    tvName.setTextColor(context.getResources().getColor(R.color.white));
                    tvName.setBackgroundResource(R.drawable.fragment_gridview_adapter_down);
                    mDamageCaeckList
                            .add(new DamageCaeck(mDictitemlist, isFlag, postion));

                }

                StringBuffer stringBuffer = new StringBuffer();
                for(DamageCaeck damageCaeck : mDamageCaeckList){
                    if(!damageCaeck.mDictitemlist.getId().equals("")){
                        stringBuffer.append(damageCaeck.mDictitemlist.getId());
                    }else if(!damageCaeck.mDictitemlist.getValue().equals("")){
                        stringBuffer.append(damageCaeck.mDictitemlist.getValue());
                    }else {
                        stringBuffer.append(damageCaeck.mDictitemlist.getName());
                    }
                    stringBuffer.append("|");
                }
                String strValue = "";
                if(stringBuffer.length() > 0){
                    strValue = stringBuffer.substring(0,stringBuffer.length() - 1);
                }
                if (mOnClickChecked!=null){
                    mOnClickChecked.getOnChecked(strValue);
                }
            }
        }

    }

    /**
     * 获取全部已选择的
     * @return
     */
    public ArrayList<DamageCaeck> getCheckAll(){
        return mDamageCaeckList;
    }

    private class DamageCaeck {
        ReportSosoResult mDictitemlist;
        boolean isFlag;
        int postion;

        public DamageCaeck(ReportSosoResult mDictitemlist, boolean isFlag, int postion) {
            this.mDictitemlist = mDictitemlist;
            this.isFlag = isFlag;
            this.postion = postion;
        }
    }
    public enum Type{
        SingleSelect, MultiSelect
    }

    private void initChecked( TextView textview ,ReportSosoResult mDictitemlist,String value,Type type){
        switch (type){
            case SingleSelect:
                if(mDictitemlist.getName().equals(value)){
                    textview.setTextColor(context.getResources().getColor(R.color.white));
                    textview.setBackgroundResource(R.drawable.fragment_gridview_adapter_down);
                    mDamageCaeckList
                            .add(new DamageCaeck(mDictitemlist, false, 0));
                }
                break;
            case MultiSelect:
                String[] checkValue = null;
                if (value != null && !value.equals("")) {
                    checkValue = value.split(";");
                }
                if(checkValue != null){
                    for(String checkvalue : checkValue){
                        if(mDictitemlist.getName().equals(checkvalue)){
                            textview.setTextColor(context.getResources().getColor(R.color.white));
                            textview.setBackgroundResource(R.drawable.fragment_gridview_adapter_down);
                            mDamageCaeckList
                                    .add(new DamageCaeck(mDictitemlist, false, 0));
                            break;
                        }
                    }
                }
                break;
        }

    }

    public void setOnClickChecked(OnClickChecked mOnClickChecked){
        this.mOnClickChecked = mOnClickChecked;
    }

    //回调函数
    public interface OnClickChecked {
      public void getOnChecked(String strValue);
    }

}

