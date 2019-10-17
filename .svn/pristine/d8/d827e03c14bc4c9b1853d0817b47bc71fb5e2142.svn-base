package com.htmitech.pop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.text.Layout;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.htmitech.addressbook.R;
import com.htmitech.app.Constant;
import com.htmitech.myEnum.ChooseWay;
import com.htmitech.unit.DensityUtil;


public class ActionSheetDialog {
    private Context context;
    private Dialog dialog;
    private TextView txt_title;
    private Button txt_cancel;
    private LinearLayout lLayout_content;
    private ScrollView sLayout_content;
    private boolean showTitle = false;
    private List<SheetItem> sheetItemList;
    private Display display;
    private Button btn_neg;
    private int CheckNormal = R.drawable.btn_check_normal;
    private int CheckSelected = R.drawable.btn_check_selected;
    private int RadioNormal = R.drawable.btn_radio_normal;
    private int RadioSelected = R.drawable.btn_radio_selected;
    private ChooseWay mChooseWay = ChooseWay.SINGLE_CHOOSE;
    public String value;
    public String key;
    private HashMap<String, String> hashMap = new HashMap<>();
    private View view;
    private ListView my_scrollview;
    private LayoutInflater inflater;
    private DialogAdapter mDialogAdapter;

    public ActionSheetDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public ActionSheetDialog builder() {
        inflater = LayoutInflater.from(context);
        // 获取Dialog布局
        view = inflater.inflate(
                R.layout.zt_view_actionsheet, null);

        // 设置Dialog最小宽度为屏幕宽度

        view.setMinimumWidth(display.getWidth() - display.getWidth() / 4);

        // 获取自定义Dialog布局中的控件
//        sLayout_content = (ScrollView) view.findViewById(R.id.sLayout_content);
//        lLayout_content = (LinearLayout) view
//                .findViewById(R.id.lLayout_content);
        txt_title = (TextView) view.findViewById(R.id.txt_title);
        my_scrollview = (ListView) view.findViewById(R.id.my_scrollview);
        txt_cancel = (Button) view.findViewById(R.id.btn_pos);
        btn_neg = (Button) view.findViewById(R.id.btn_neg);
        txt_cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                hashMap.clear();
                mOnSheetItemClickListener.onClick(hashMap);
            }
        });
        btn_neg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                mOnSheetItemClickListener.onClick(hashMap);
            }
        });
        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.AlertDialogStyle);
        dialog.setContentView(view);

        view.setLayoutParams(new FrameLayout.LayoutParams((int) (display
                .getWidth() * 0.85), LayoutParams.WRAP_CONTENT));
//		Window dialogWindow = dialog.getWindow();
//		dialogWindow.setGravity(Gravity.LEFT | Gravity.BOTTOM);
//		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//		lp.x = 0;
//		lp.y = 0;
//		dialogWindow.setAttributes(lp);

        return this;
    }

    public ActionSheetDialog setTitle(String title) {
        showTitle = true;
        txt_title.setVisibility(View.VISIBLE);
        txt_title.setText(title);
        return this;
    }

    public ActionSheetDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public ActionSheetDialog setCanceledOnTouchOutside(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    /**
     * 条目名称
     * 条目字体颜色，设置null则默认蓝色
     *
     * @return
     */

    public ActionSheetDialog addSheetItemMap(HashMap<String, String> map) {
        if (sheetItemList == null) {
            sheetItemList = new ArrayList<SheetItem>();
        }
        for (String s : map.keySet()) {
            sheetItemList.add(new SheetItem(map.get(s), SheetItemColor.GRAY, s, ""));
        }
        return this;
    }

    public ActionSheetDialog addSheetItem(ArrayList<String> map) {
        if (sheetItemList == null) {
            sheetItemList = new ArrayList<SheetItem>();
        }
        for (String s : map) {
            sheetItemList.add(new SheetItem(s, SheetItemColor.GRAY));
        }
        return this;
    }

    /**
     * 条目名称
     * 条目字体颜色，设置null则默认蓝色
     * <p/>
     * colorList 右侧颜色
     *
     * @return
     */

    public ActionSheetDialog addSheetItem(ArrayList<String> map, ArrayList<String> colorList) {
        if (sheetItemList == null) {
            sheetItemList = new ArrayList<SheetItem>();
        }
        for (int i = 0; i < map.size(); i++) {
            sheetItemList.add(new SheetItem(map.get(i), SheetItemColor.GRAY, colorList.get(i)));
        }
        return this;
    }


    /**
     * 设置条目布局
     */
    private void setSheetItems() {
        if (sheetItemList == null || sheetItemList.size() <= 0) {
            return;
        }

        int size = sheetItemList.size();

        // TODO 高度控制，非最佳解决办法
        // 添加条目过多的时候控制高度
        if (size >= 5) {
            LayoutParams params = (LayoutParams) my_scrollview
                    .getLayoutParams();
            params.height = display.getHeight() / 2;
            my_scrollview.setLayoutParams(params);

//            view.setLayoutParams(new FrameLayout.LayoutParams((int) (display
//                    .getWidth() * 0.85), display.getHeight() / 2));
        }
        mDialogAdapter = new DialogAdapter();
        my_scrollview.setAdapter(mDialogAdapter);
        // 循环添加条目
//        for (int i = 1; i <= size; i++) {
//            SheetItem sheetItem = sheetItemList.get(i - 1);
//            final String strItem = sheetItem.name;
//            final String strKey = sheetItem.code;
//            SheetItemColor color = sheetItem.color;
//            String colorLeft = sheetItem.bitColor;
//            TextView textView = new TextView(context);
//            textView.setEllipsize(TextUtils.TruncateAt.END);
//            textView.setMaxLines(1);
//            textView.setTag(i + "");
//            textView.setText(strItem);
//            textView.setTextSize(18);
//            textView.setGravity(Gravity.CENTER_VERTICAL);
//            textView.setDuplicateParentStateEnabled(true);
//            TextView mMyCheckBox = new TextView(context);
//            mMyCheckBox.setId(R.id.check_dialog);
//            if (mChooseWay == ChooseWay.MORE_CHOOSE) {
//                mMyCheckBox.setBackgroundResource(CheckNormal);
//            } else {
//                mMyCheckBox.setBackgroundResource(RadioNormal);
//            }
//            mMyCheckBox.setGravity(Gravity.CENTER);
//            mMyCheckBox.setDuplicateParentStateEnabled(true);
//            mMyCheckBox.setLayoutParams(new LayoutParams(
//                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
//            // 字体颜色
//            if (color == null) {
//                textView.setTextColor(Color.parseColor(SheetItemColor.Blue
//                        .getName()));
//            } else {
//                textView.setTextColor(Color.parseColor(color.getName()));
//            }
//
//            // 高度
//            float scale = context.getResources().getDisplayMetrics().density;
//            int height = DensityUtil.dip2px(context,50);
////			textView.setLayoutParams(new LinearLayout.LayoutParams(
////					LayoutParams.WRAP_CONTENT, height));
//
//
//            if (!colorLeft.equals("")) {
//                textView.setLayoutParams(new LayoutParams(
//                        LayoutParams.WRAP_CONTENT, height));
//                TextView colorText = new TextView(context);
//
//                LinearLayout layout = new LinearLayout(context);
//                LayoutParams layoutParams = new LayoutParams(
//                        LayoutParams.MATCH_PARENT, height);
////				layoutParams.setMargins(DensityUtil.dip2px(context, 50), 0, DensityUtil.dip2px(context, 10), 0);
//                layout.setLayoutParams(layoutParams);
//                layout.setOrientation(LinearLayout.HORIZONTAL);
//                layout.setGravity(Gravity.CENTER_VERTICAL);
//                layout.setTag(i + "");
//                LayoutParams para = new LayoutParams(
//                        LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//                para.setMargins(0, 0, DensityUtil.dip2px(context, 5), 0);
//                colorText.setLayoutParams(para);
//                colorText.setBackgroundColor(Color.parseColor("#" + colorLeft));
//                colorText.setGravity(Gravity.CENTER);
//
//                layout.addView(colorText);
//                layout.addView(textView);
//
//                // 背景图片
//                if (size == 1) {
//                    if (showTitle) {
//                        layout.setBackgroundResource(R.drawable.zt_actionsheet_bottom_selector);
//                    } else {
//                        layout.setBackgroundResource(R.drawable.zt_actionsheet_single_selector);
//                    }
//                } else {
//                    if (showTitle) {
//                        if (i >= 1 && i < size) {
//                            layout.setBackgroundResource(R.drawable.zt_actionsheet_middle_selector);
//                        } else {
//                            layout.setBackgroundResource(R.drawable.zt_actionsheet_middle_selector);
//                        }
//                    } else {
//                        if (i == 1) {
//                            layout.setBackgroundResource(R.drawable.zt_actionsheet_top_selector);
//                        } else if (i < size) {
//                            layout.setBackgroundResource(R.drawable.zt_actionsheet_middle_selector);
//                        } else {
//                            layout.setBackgroundResource(R.drawable.zt_actionsheet_middle_selector);
//                        }
//                    }
//                }
//
//                // 点击事件
//                layout.setOnClickListener(new OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        value = (String) v.getTag();
//                        for (int i = 0; i < lLayout_content.getChildCount(); i++) {
//                            View view = lLayout_content.getChildAt(i);
//                            view.setBackgroundResource(R.drawable.zt_actionsheet_middle_normal);
//                        }
//                        v.setBackgroundResource(R.drawable.zt_actionsheet_middle_pressed);
//                    }
//                });
//
//                lLayout_content.addView(layout);
//            } else {
//// 背景图片
//                LinearLayout layout = new LinearLayout(context);
//                LayoutParams layoutParams = new LayoutParams(
//                        LayoutParams.MATCH_PARENT, height);
////				layoutParams.setMargins(DensityUtil.dip2px(context, 50), 0, DensityUtil.dip2px(context, 10), 0);
//                layout.setLayoutParams(layoutParams);
//                layout.setOrientation(LinearLayout.HORIZONTAL);
//                layout.setGravity(Gravity.CENTER_VERTICAL);
//                layout.setTag("false");
//                LayoutParams para = new LayoutParams(
//                        LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//                mMyCheckBox.setLayoutParams(para);
//                textView.setLayoutParams(new LayoutParams(
//                        0, height, 1));
//                layout.addView(mMyCheckBox);
//                layout.addView(textView);
//
//
//                if (size == 1) {
//                    if (showTitle) {
//                        layout.setBackgroundResource(R.drawable.zt_actionsheet_bottom_selector);
//                    } else {
//                        layout.setBackgroundResource(R.drawable.zt_actionsheet_single_selector);
//                    }
//                } else {
//                    if (showTitle) {
//                        if (i >= 1 && i < size) {
//                            layout.setBackgroundResource(R.drawable.zt_actionsheet_middle_selector);
//                        } else {
//                            layout.setBackgroundResource(R.drawable.zt_actionsheet_middle_selector);
//                        }
//                    } else {
//                        if (i == 1) {
//                            layout.setBackgroundResource(R.drawable.zt_actionsheet_top_selector);
//                        } else if (i < size) {
//                            layout.setBackgroundResource(R.drawable.zt_actionsheet_middle_selector);
//                        } else {
//                            layout.setBackgroundResource(R.drawable.zt_actionsheet_middle_selector);
//                        }
//                    }
//                }
//                // 点击事件
//                layout.setOnClickListener(new OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        switch (mChooseWay) {
//                            case SINGLE_CHOOSE:
//                                key = strKey;
//                                value = strItem;
//                                for (int i = 0; i < lLayout_content.getChildCount(); i++) {
//                                    View view = lLayout_content.getChildAt(i);
//                                    TextView view1 = (TextView) view.findViewById(R.id.check_dialog);
//                                    view1.setBackgroundResource(RadioNormal);
//                                    view.setBackgroundResource(R.drawable.zt_actionsheet_middle_normal);
//                                }
//                                TextView view1 = (TextView) v.findViewById(R.id.check_dialog);
//                                view1.setBackgroundResource(RadioSelected);
//                                v.setBackgroundResource(R.drawable.zt_actionsheet_middle_pressed);
//                                if (hashMap != null) {
//                                    hashMap.clear();
//                                    hashMap.put(key, value);
//                                } else {
//                                    hashMap = new HashMap<>();
//                                    hashMap.put(key, value);
//                                }
//                                break;
//                            case MORE_CHOOSE:
//                                TextView view2 = (TextView) v.findViewById(R.id.check_dialog);
//                                if (v.getTag().toString().equals("false")) {
//                                    v.setTag("true");
//                                    view2.setBackgroundResource(CheckSelected);
//                                    key = strKey;
//                                    value = strItem;
//                                    hashMap.put(key, value);
//                                } else {
//                                    v.setTag("false");
//                                    view2.setBackgroundResource(CheckNormal);
//                                    key = strKey;
//                                    value = strItem;
//                                    hashMap.remove(key);
//                                }
//                                break;
//                        }
//
//                    }
//                });
//                lLayout_content.addView(layout);
//            }
//
//
//        }
    }


    public class AdapterOnClick implements OnClickListener {
        private String strKey;
        private String strItem;
        private int ponstion;

        public AdapterOnClick(String strKey, String strItem, int ponstion) {
            this.strKey = strKey;
            this.strItem = strItem;
            this.ponstion = ponstion;
        }

        @Override
        public void onClick(View v) {
            switch (mChooseWay) {
                case SINGLE_CHOOSE:
                    key = strKey;
                    value = strItem;
                    for (int i = 0; i < sheetItemList.size(); i++) {
                        sheetItemList.get(i).isCheck = false;
                    }
                    sheetItemList.get(ponstion).isCheck = true;
                    if (hashMap != null) {
                        hashMap.clear();
                        hashMap.put(key, value);
                    } else {
                        hashMap = new HashMap<>();
                        hashMap.put(key, value);
                    }
                    mDialogAdapter.notifyDataSetChanged();
                    break;
                case MORE_CHOOSE:
                    sheetItemList.get(ponstion).isCheck = !sheetItemList.get(ponstion).isCheck;
                    if(sheetItemList.get(ponstion).isCheck){
                        key = strKey;
                        value = strItem;
                        hashMap.put(key, value);
                    }else{
                        key = strKey;
                        value = strItem;
                        hashMap.remove(key);
                    }
                    mDialogAdapter.notifyDataSetChanged();
                    break;
                default:
                    key = strKey;
                    value = strItem;
                    for (int i = 0; i < sheetItemList.size(); i++) {
                        sheetItemList.get(i).isCheck = false;
                    }
                    sheetItemList.get(ponstion).isCheck = true;
                    if (hashMap != null) {
                        hashMap.clear();
                        hashMap.put(key, value);
                    } else {
                        hashMap = new HashMap<>();
                        hashMap.put(key, value);
                    }
                    mDialogAdapter.notifyDataSetChanged();
                    break;
            }
        }
    }

    public void show() {
        setSheetItems();
        dialog.show();
    }

    public OnSheetItemClickListener mOnSheetItemClickListener;

    public ActionSheetDialog setOnSheetItemClickListener(OnSheetItemClickListener mOnSheetItemClickListener) {
        this.mOnSheetItemClickListener = mOnSheetItemClickListener;
        return this;
    }

    public interface OnSheetItemClickListener {
        void onClick(HashMap<String, String> hashMap);
    }

    public ActionSheetDialog setChooseWay(ChooseWay mChooseWay) {
        this.mChooseWay = mChooseWay;
        return this;
    }


    public ActionSheetDialog setRightImageView(int checkNormal, int checkSelected, int radioNormal, int radioSelected) {
        this.CheckNormal = checkNormal;
        this.CheckSelected = checkSelected;
        this.RadioSelected = radioSelected;
        this.RadioNormal = radioNormal;
        return this;
    }

    public class DialogAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return sheetItemList.size();
        }

        @Override
        public Object getItem(int i) {
            return sheetItemList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            ViewHolder mViewHolder = null;
            if (convertView == null) {
                mViewHolder = new ViewHolder();
                convertView = inflater.inflate(R.layout.zt_dialog_adapter, null);
                mViewHolder.tv_logo = (TextView) convertView.findViewById(R.id.check_dialog);
                mViewHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
                convertView.setTag(mViewHolder);
            } else {
                mViewHolder = (ViewHolder) convertView.getTag();
            }
            if (mChooseWay == ChooseWay.MORE_CHOOSE) {
                mViewHolder.tv_logo.setBackgroundResource(CheckNormal);
            } else if (mChooseWay == ChooseWay.SINGLE_CHOOSE){
                mViewHolder.tv_logo.setBackgroundResource(RadioNormal);
            }else{
                mViewHolder.tv_logo.setVisibility(View.GONE);
                mViewHolder.tv_title.setGravity(Gravity.CENTER);
            }
            mViewHolder.tv_title.setText(sheetItemList.get(i).name);
            switch (mChooseWay) {
                case SINGLE_CHOOSE:
                    if(sheetItemList.get(i).isCheck){
                        mViewHolder.tv_logo.setBackgroundResource(RadioSelected);
//                        convertView.setBackgroundResource(R.drawable.zt_actionsheet_middle_pressed);
                    }else{
                        mViewHolder.tv_logo.setBackgroundResource(RadioNormal);
//                        convertView.setBackgroundResource(R.drawable.zt_actionsheet_middle_normal);
                    }
                    break;
                case MORE_CHOOSE:

                    if(sheetItemList.get(i).isCheck){
                        mViewHolder.tv_logo.setBackgroundResource(CheckSelected);
//                        convertView.setBackgroundResource(R.drawable.zt_actionsheet_middle_pressed);
                    }else{
                        mViewHolder.tv_logo.setBackgroundResource(CheckNormal);
//                        convertView.setBackgroundResource(R.drawable.zt_actionsheet_middle_normal);
                    }
                    break;
                case NOT_CHECK:
                    if(sheetItemList.get(i).isCheck){
                        convertView.setBackgroundResource(R.color.huise);
                    }else{
                        convertView.setBackgroundResource(R.color.white);
                    }

                    break;
            }

            convertView.setOnClickListener(new AdapterOnClick(sheetItemList.get(i).code, sheetItemList.get(i).name,i));
            return convertView;
        }

        class ViewHolder {
            TextView tv_logo, tv_title;
        }
    }

    public class SheetItem {
        String name;
        String code;
        SheetItemColor color;
        public boolean isCheck = false;
        private String bitColor = "";

        public SheetItem(String name, SheetItemColor color) {
            this.name = name;
            this.color = color;
        }

        public SheetItem(String name, SheetItemColor color, String code, String not) {
            this.name = name;
            this.color = color;
            this.code = code;
        }

        public SheetItem(String name, SheetItemColor color, String bitColor) {
            this.name = name;
            this.color = color;
            this.bitColor = bitColor;
        }
    }

    public enum SheetItemColor {
        Blue("#037BFF"), Red("#FD4A2E"), GRAY("#999999");

        private String name;

        private SheetItemColor(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
