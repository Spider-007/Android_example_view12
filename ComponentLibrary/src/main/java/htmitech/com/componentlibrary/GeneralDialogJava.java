package htmitech.com.componentlibrary;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import htmitech.com.componentlibrary.adapter.GeneralDialogAdapter;
import htmitech.com.componentlibrary.entity.BasicJava;
import htmitech.com.componentlibrary.entity.CheckForm;
import htmitech.com.componentlibrary.unit.DensityUtil;

/**
 * 编写一个通用的dialog 里面包含下拉单选 下拉多选 带有下拉选择
 */
public class GeneralDialogJava {
    public Context context;
    public List<CheckForm> checkForms;
    public ArrayList<BasicJava> basics;
    public int layoutId = -1;
    public GeneralDialogAdapter mGeneralDialogAdapter;
    public ViewHould mViewHould;
    public Dialog dialog;
    public View view;
    public int itemHight;
    public IGeneralDialogItem mIGeneralDialogItem;

    public IGeneralDialogItem getmIGeneralDialogItem() {
        return mIGeneralDialogItem;
    }

    public GeneralDialogJava setmIGeneralDialogItem(IGeneralDialogItem mIGeneralDialogItem) {
        this.mIGeneralDialogItem = mIGeneralDialogItem;
        return this;
    }

    /**
     *
     * @param context
     */
    public GeneralDialogJava(Context context, IGeneralDialogItem mIGeneralDialogItem){
        this.context = context;
        this.mIGeneralDialogItem = mIGeneralDialogItem;
    }

    /**
     *
     * @param context
     */
    public GeneralDialogJava(Context context, List<CheckForm> checkForms, IGeneralDialogItem mIGeneralDialogItem){
        this.context = context;
        this.checkForms = checkForms;
        this.mIGeneralDialogItem = mIGeneralDialogItem;
    }

    /**
     *
     * @param context
     * @param checkForms
     * @param layoutId
     */
    public GeneralDialogJava(Context context, ArrayList<CheckForm> checkForms, int layoutId, IGeneralDialogItem mIGeneralDialogItem){
        this.context = context;
        this.checkForms = checkForms;
        this.layoutId = layoutId;
        this.mIGeneralDialogItem = mIGeneralDialogItem;
    }

    /**
     * 如果layoutId为-1 则表示使用的是默认的
     *
     *
     * @return
     */
    public GeneralDialogJava builder(){
        view = LayoutInflater.from(context).inflate(
                layoutId != -1 ? layoutId : R.layout.dialog_general_pull_down, null);
        mViewHould = new ViewHould();
        refresh();
        mViewHould.general_pull_down = (ListView) view.findViewById(R.id.general_pull_down);
        mViewHould.tv_quxiao = (TextView) view.findViewById(R.id.tv_quxiao);
        mViewHould.tv_queding = (TextView) view.findViewById(R.id.tv_queding);
        mGeneralDialogAdapter = new GeneralDialogAdapter(context,basics);
        mViewHould.general_pull_down.setAdapter(mGeneralDialogAdapter);
        itemHight = DensityUtil.dip2px(context, 50);
        if (basics.size() >= 6 ) {
            LinearLayout.LayoutParams sp_params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, itemHight * 6);
            mViewHould.general_pull_down.setLayoutParams(sp_params);
        }else{
            LinearLayout.LayoutParams sp_params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            mViewHould.general_pull_down.setLayoutParams(sp_params);
        }
        mViewHould.general_pull_down.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                basics.get(i).isCheck = !basics.get(i).isCheck;
                mGeneralDialogAdapter.setData(basics);
            }
        });
        mViewHould.tv_quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        mViewHould.tv_queding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mIGeneralDialogItem != null){
                    mIGeneralDialogItem.ItemClick(getCheckForms());
                }

                dialog.dismiss();
            }
        });
        dialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        dialog.setContentView(view);
        dialog.getWindow().setGravity(Gravity.BOTTOM);

        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);
		ColorDrawable dw = new ColorDrawable(0x00000000);
		dw.setAlpha(100);
		dialog.getWindow().setBackgroundDrawable(dw);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {

                dialog.cancel();
            }
        });
        return this;

    }
    public ArrayList<CheckForm> getCheckForms(){
        ArrayList<CheckForm> checkForms = new ArrayList<CheckForm>();
        for(BasicJava mBasicJava :basics){
            if(mBasicJava.isCheck){
                checkForms.add(mBasicJava.checkForm);
            }
        }
        return checkForms;
    }
    public void refresh(){
        basics = new ArrayList<BasicJava>();
        if(checkForms != null){
            for(CheckForm checkForm :checkForms){
                basics.add(new BasicJava(checkForm,false));
            }
        }
    }


    /**
     * 刷新
     * @param list
     * @return
     */
    public GeneralDialogJava setData(ArrayList list){
        mGeneralDialogAdapter.setData(list);
        return this;
    }



    public class ViewHould{
        public ListView general_pull_down;
        public TextView tv_quxiao;
        public TextView tv_queding;
    }

    public <T extends View> T getView(int viewId)
    {
        if (view == null)
        {
            view = view.findViewById(viewId);
        }
        return (T) view;
    }


    public interface IGeneralDialogItem{
        void ItemClick(ArrayList<CheckForm> checkForms);
    }


    public void show() {

//		gradview.setCallBackApplicationCenter(this);
        try {
            if (!dialog.isShowing()) {
                dialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}