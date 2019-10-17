package htmitech.formConfig;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import htmitech.com.componentlibrary.api.ComponentInit;
import htmitech.com.componentlibrary.entity.EditField;
import htmitech.com.componentlibrary.entity.FieldItem;
import htmitech.com.componentlibrary.unit.DrawableUtils;
import htmitech.com.componentlibrary.unit.SysConvertUtil;
import htmitech.com.formlibrary.R;

/**
 * Created by yanxin on 2017-9-25.
 */
public class SelectPoisition7001 {
    public Context context;
    private EditText etLocation;
    public String app_id;
    public String cpTitle = "";
    public static EditText title ;
    public List<EditField> EditFiledsd;
    private String VlineVisible;
    public SelectPoisition7001(Context context, String app_id, String cpTitle, List<EditField> EditFiledsd,String VlineVisible){
        this.context = context;
        this.VlineVisible = VlineVisible;
        this.app_id = app_id;
        if(!TextUtils.isEmpty(cpTitle))
        this.cpTitle = cpTitle;
        this.EditFiledsd = EditFiledsd;
    }
    public LinearLayout selectPoisitionLayout(LayoutInflater mInflater,FieldItem item){
        View view = mInflater.inflate(R.layout.layout_select_poisition, null);
        LinearLayout selectPoisitionRoot = (LinearLayout)view.findViewById(R.id.select_poisition_root);
        TextView tv_item_name = (TextView) view.findViewById(R.id.tv_item_name);

        selectPoisitionRoot.setBackgroundColor(SysConvertUtil.formattingH(item.getBackColor()));
        if (VlineVisible.equalsIgnoreCase("true")) {
//            lineView.setBackgroundResource(R.drawable.corners_bg_white_press_stroke_v);
            selectPoisitionRoot.setBackground(DrawableUtils.getLayerDrawable(context, item.getBackColor()));//设置显示背景
        }
        etLocation = (EditText)view.findViewById(R.id.et_edit);
        title = etLocation;
        tv_item_name.setText(item.getName());
        etLocation.setText(""+cpTitle);
//        etLocation.setText(item.getValue());
        //----------------------------------------------------------------
         final EditField edit = new EditField();
        edit.setKey(item.getKey());
//        edit.setSign(item.getSign());
        edit.setInput(item.getInput());
        edit.setMode(item.getMode());
        edit.setFormKey(item.getFormKey());
        edit.setValue(item.getValue());
        edit.setIsExt(item.isExt());
        etLocation.setTag(edit);
        EditFiledsd.add(edit);

        etLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                edit.setValue(etLocation.getText().toString());
                boolean hasfind = false;
                for (int j = 0; j < EditFiledsd.size(); j++) {
                    if (EditFiledsd.get(j).getKey()
                            .equals(edit.getKey())) {
                        hasfind = true;
                        EditFiledsd.get(j).setValue(edit.getValue());
                        break;
                    }}
                if (!hasfind)
                    EditFiledsd.add(edit);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //------------------------------------------------------------------
        ImageView ivLocation = (ImageView)view.findViewById(R.id.iv_location);

//        SelectPoisition.getInstances().setSelectPoisitionInterface(new SelectPoisition.SelectPoisitionInterface() {
//            @Override
//            public void OnLocationChange(ScheduleCpId mScheduleCpId) {
//                etLocation.setText(mScheduleCpId.cpTitle+"");
////                SelectPoisition.getInstances().setmScheduleCpId(mScheduleCpId);
//            }
//        });
        ivLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                AppliationCenterDao mAppliationCenterDao = new AppliationCenterDao(context);
//                AppInfo appInfo = mAppliationCenterDao.getAppInfo(app_id);
//                try {
//                    HashMap map = new HashMap<String, String>();
//                    map.put("app_id", app_id);
//                    HTActivityUnit.switchTo((Activity)context, SchedulePoiSearchActivity.class, map);
////                                ClentAppUnit.getInstance(ClientTabActivity.this).setActivity(appInfo, map);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }

                ComponentInit.getInstance().getmISelectPoisitionListener().startSchedulePoiSearchContext();
//                Intent intent = new Intent(context, SchedulePoiSearchActivity.class);
//                context.startActivity(intent);
            }
        });
        return selectPoisitionRoot;
    }

    public void setLocationTitle(String locationTitle){
        etLocation.setText(locationTitle);
    }
}
