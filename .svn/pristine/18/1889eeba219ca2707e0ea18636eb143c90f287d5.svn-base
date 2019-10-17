package htmitech.formConfig;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;


import java.util.List;

import htmitech.com.componentlibrary.SeekBarPressure;
import htmitech.com.componentlibrary.entity.DocResultInfo;
import htmitech.com.componentlibrary.entity.EditField;
import htmitech.com.componentlibrary.entity.EditFieldList;
import htmitech.com.componentlibrary.entity.FieldItem;
import htmitech.com.componentlibrary.unit.DrawableUtils;
import htmitech.com.componentlibrary.unit.SysConvertUtil;
import htmitech.com.formlibrary.R;

/**
 * 单选 多选的RadioButton
 */
public class IntervalControl8001_002 {
    private Context context;
    private LayoutInflater mInflater;
    private List<EditField> EditFileds;
    private LinearLayout lineView;
    private DocResultInfo mDocResultInfo = null;;
    public IntervalControl8001_002(Context context) {
        this.context = context;
    }

    public LinearLayout radioButtonLayout(String VlineVisible, FieldItem item, LayoutInflater mInflater, List<EditField> EditFileds,DocResultInfo mDocResultInfo ) {

        this.mInflater = mInflater;
        this.EditFileds = EditFileds;
        this.mDocResultInfo = mDocResultInfo;
        lineView = new LinearLayout(
                context);
        lineView.setOrientation(LinearLayout.VERTICAL);
        lineView.setGravity(Gravity.CENTER_VERTICAL);
        lineView.setBackgroundColor(SysConvertUtil.formattingH(item.getBackColor()));
        if (VlineVisible.equalsIgnoreCase("true")) {
//            lineView.setBackgroundResource(R.drawable.corners_bg_white_press_stroke_v);
            lineView.setBackground(DrawableUtils.getLayerDrawable(context, item.getBackColor()));//设置显示背景
        }

        return setReView8001_8002(item);

    }

    public LinearLayout setReView8001_8002(FieldItem item) {
        lineView.removeAllViews();
        final EditField edit1 = new EditField();                                        //选项布局建立tag
        edit1.setKey(item.getKey());
//        edit1.setSign(item.getSign());
        edit1.setInput(item.getInput());
        edit1.setMode(item.getMode());
        edit1.setIsExt(item.isExt());
        edit1.setFormKey(item.getFormKey());
//        DocResultInfo mDocResultInfo = null;
//        if (context instanceof WorkFlowFormDetalActivity) {
//            mDocResultInfo = ((WorkFlowFormDetalActivity) context).mDocResultInfo;
//        }
        LinearLayout intervalControLayout = (LinearLayout) mInflater.inflate(R.layout.layout_interval_control_lib, null);
        int input = Integer.parseInt(item.getInput().trim());
        switch (input) {
            case 8001://滑动选择_单值
                SeekBarPressure seekBarPressure = (SeekBarPressure) intervalControLayout.findViewById(R.id.sbp_interval_control);
                seekBarPressure.setTwoProgress(false);
                seekBarPressure.setProgressLow(Double.parseDouble(item.getValue()));
                final DocResultInfo finalMDocResultInfo = mDocResultInfo;
                seekBarPressure.setOnSeekBarChangeListener(new SeekBarPressure.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressBefore() {

                    }

                    @Override
                    public void onProgressChanged(SeekBarPressure seekBar, double progressLow, double progressHigh) {
                        detailEdit(progressLow + "", EditFileds, edit1, finalMDocResultInfo);
                    }

                    @Override
                    public void onProgressAfter() {


                    }
                });
                break;
            case 8002://滑动选择_区间
                SeekBarPressure seekBarPressure1 = (SeekBarPressure) intervalControLayout.findViewById(R.id.sbp_interval_control);
                seekBarPressure1.setTwoProgress(true);
                String[] checkValue = null;
                if (item.getValue() != null && !item.getValue().equals("")) {
                    checkValue = item.getValue().split(";");
                }
                seekBarPressure1.setProgressLow(Double.parseDouble(checkValue[0]));
                seekBarPressure1.setProgressHigh(Double.parseDouble(checkValue[1]));
                final DocResultInfo finalMDocResultInfo1 = mDocResultInfo;
                seekBarPressure1.setOnSeekBarChangeListener(new SeekBarPressure.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressBefore() {

                    }

                    @Override
                    public void onProgressChanged(SeekBarPressure seekBar, double progressLow, double progressHigh) {
                        detailEdit(progressLow + ";" + progressHigh, EditFileds, edit1, finalMDocResultInfo1);
                    }

                    @Override
                    public void onProgressAfter() {


                    }
                });
                break;
        }
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        params1.gravity = Gravity.CENTER_VERTICAL;
        lineView.addView(intervalControLayout, params1);
        lineView.setBackgroundResource(R.drawable.corners_bg_grey_press_stroke);
        return lineView;
    }

    private void detailEdit(String strValue, List<EditField> EditFileds, EditField edt, DocResultInfo mDocResultInfo) {

        edt.setValue(strValue);
        if (EditFileds != null && EditFileds.size() == 0)        //添加到可编辑数组中
            EditFileds.add(edt);
        else {
            boolean hasfind = false;
            for (int j = 0; j < EditFileds.size(); j++) {        //遍历可回发数组 是否有相同的key，如果有直接
                if (EditFileds.get(j).getKey()                    //setValue 否则直接存入
                        .equals(edt.getKey())) {
                    hasfind = true;
                    EditFileds.get(j).setValue(
                            edt.getValue());
                    break;
                }
            }
            if (!hasfind)
                EditFileds.add(edt);
        }
        if (mDocResultInfo==null){
            return;
        }
        mDocResultInfo.getResult()
                .setEditFields(EditFileds);                                //刷新可回发字段集
        EditFieldList mustFieldList = EditFieldList
                .getInstance();
        for (int i = 0; i < mustFieldList.getList().size(); i++) {        //遍历必填项，如果key相同设置value
            if (mustFieldList.getList().get(i).getKey().equalsIgnoreCase(edt.getKey())) {
                mustFieldList.getList().get(i).setValue(strValue);
            }
        }

    }

}
