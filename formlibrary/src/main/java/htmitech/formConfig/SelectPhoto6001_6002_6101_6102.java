package htmitech.formConfig;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
//import com.htmitech.photoselector.ui.PhotoPreviewActivity;
//import com.htmitech.unit.ActivityUnit;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import htmitech.com.componentlibrary.entity.FieldItem;
import htmitech.com.componentlibrary.entity.FormExtensionFiles;
import htmitech.com.componentlibrary.entity.PhotoModel;
import htmitech.com.componentlibrary.unit.DensityUtil;
import htmitech.com.componentlibrary.unit.DrawableUtils;
import htmitech.com.componentlibrary.unit.ImageCompressUtil;
import htmitech.com.componentlibrary.unit.SysConvertUtil;
import htmitech.com.componentlibrary.unit.ToastUtil;
import htmitech.com.formlibrary.R;
//import htmitech.dao.FormExtensionFilesDao;
import htmitech.listener.HTSaveFormExtensionFiles;
import htmitech.listener.ICellOnclick6102;
import htmitech.listener.YiJianOnclickLisener;


/**
 * // 1，**********************处理可编辑的意见字段******************************
 */
public class SelectPhoto6001_6002_6101_6102 {

    public Context context;
    public LinearLayout parentLayout;
    public float widthCommonForm;
    public YiJianOnclickLisener mCellOnclickLisener1;
    public ICellOnclick6102 mIntentPhone;
    public List<PhotoModel> listImgs;
    public boolean isDelete;
    public FieldItem workflow_item;
    private int indexCash = 0;
    private LayoutInflater mInflater;
    private List<TextView> list_tvsize;
    private int com_workflow_mobileconfig_IM_enabled;
    private LinearLayout lineView;
    private String comeShare;
    private HTSaveFormExtensionFiles mHTSaveFormExtensionFiles;
    private int typeStyle;

    public SelectPhoto6001_6002_6101_6102(Context context) {
        this.context = context;
    }

    //可编辑字段 输入方式为11
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public LinearLayout Photo6102(String VlineVisible, FieldItem item, LayoutInflater mInflater, List<TextView> list_tvsize, YiJianOnclickLisener mCellOnclickLisener, int com_workflow_mobileconfig_IM_enabled, String comeShare, HTSaveFormExtensionFiles mHTSaveFormExtensionFiles, ICellOnclick6102 mIntentPhone, int typeStyle) {
        {
            this.mCellOnclickLisener1 = mCellOnclickLisener;
            this.workflow_item = item;
            isDelete = false;
            this.typeStyle = typeStyle;
            this.mHTSaveFormExtensionFiles = mHTSaveFormExtensionFiles;
            this.mInflater = mInflater;
            this.list_tvsize = list_tvsize;
            this.mIntentPhone = mIntentPhone;
            this.comeShare = comeShare;
            this.com_workflow_mobileconfig_IM_enabled = com_workflow_mobileconfig_IM_enabled;
            // 1，**********************处理可编辑的意见字段******************************
            lineView = new LinearLayout(                            //设置一个容器和背景及显示方式
                    context);
            lineView.setOrientation(LinearLayout.VERTICAL);
            lineView.setGravity(Gravity.CENTER_VERTICAL);
            lineView.setBackgroundColor(SysConvertUtil.formattingH(item.getBackColor()));
            if (VlineVisible.equalsIgnoreCase("true")) {
//            lineView.setBackgroundResource(R.drawable.corners_bg_white_press_stroke_v);
                lineView.setBackground(DrawableUtils.getLayerDrawable(context, item.getBackColor()));//设置显示背景
            }


            return setReView6001_002_101_102(item);
        }
    }


    public LinearLayout setReView6001_002_101_102(final FieldItem item) {
        lineView.removeAllViews();
        DisplayMetrics dm = new DisplayMetrics();
        //获取屏幕信息
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        widthCommonForm = screenWidth * (((float) item.getPercent()) / 100);
        LinearLayout layout = (LinearLayout) mInflater.inflate(
                R.layout.layout_formdetail_cell_photo6001_6002_6101_6102_lib, null);

        ImageView iv_photo_add = (ImageView) layout.findViewById(R.id.iv_photo_add);
        parentLayout = (LinearLayout) layout.findViewById(R.id.ll_photo_img);
        LinearLayout ll_native = (LinearLayout) layout.findViewById(R.id.ll_native);
        LinearLayout ll_style = (LinearLayout) layout.findViewById(R.id.ll_style);
        if(typeStyle == 1){
            ll_native.setVisibility(View.GONE);
            ll_style.setVisibility(View.VISIBLE);
            parentLayout = (LinearLayout) layout.findViewById(R.id.ll_photo_img_style);
            iv_photo_add = (ImageView) layout.findViewById(R.id.iv_photo_add_style);
            TextView form_fielditem_beforetext = (TextView) layout.findViewById(R.id.form_fielditem_beforetext);
            form_fielditem_beforetext.setText(item.getName());
            form_fielditem_beforetext.setTextColor(Color.parseColor("#999999"));
        }else{
            ll_native.setVisibility(View.VISIBLE);
            ll_style.setVisibility(View.GONE);

        }
        if (item.isMustInput()) {
            parentLayout.setBackgroundColor(context.getResources().getColor(R.color.form_bg_must));
        }
        if(Integer.parseInt(workflow_item.getMode()) == 1){
            iv_photo_add.setVisibility(View.VISIBLE);
        }else{
            iv_photo_add.setVisibility(View.GONE);
        }
        iv_photo_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (mCellOnclickLisener1 != null) {
                    mCellOnclickLisener1.onClick(v);
                }

            }
        });
        List<PhotoModel> listImgTemp = new ArrayList<PhotoModel>();
        if (item.getFiledImages() != null && item.getFiledImages().size() > 0) {
            List<FormExtensionFiles> mFormExtensionFilesList = item.getFiledImages();
            for (FormExtensionFiles mFormExtensionFiles : mFormExtensionFilesList) {
                PhotoModel mPhotoModel = new PhotoModel();
                mPhotoModel.setItem_workflow(item);
                mPhotoModel.setOriginalPath(mFormExtensionFiles.fileUrl);
                mPhotoModel.setChecked(false);
                mPhotoModel.setFile_id(mFormExtensionFiles.fileId);
                listImgTemp.add(mPhotoModel);
            }

        }
        updateImg(context, listImgTemp, 0);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        params.gravity = Gravity.CENTER_VERTICAL;
        lineView.addView(layout, params);

        return lineView;
    }

    /**
     * 更新显示图片
     *
     * @param context
     * @param listImg
     * @param wherecome 0 初始化 拍照  1 相册  2 删除
     */
    public void updateImg(final Context context, final List<PhotoModel> listImg, int wherecome) {

        switch (wherecome) {
            case 0:
                if (listImgs == null) {
                    listImgs = new ArrayList<PhotoModel>();
                    listImgs.addAll(listImg);
                } else {
                    listImgs.addAll(listImg);
                }
                break;
            case 1:
                if (listImgs.size() > 0) {
                    Iterator<PhotoModel> listImgsIter = listImgs.iterator();
                    while (listImgsIter.hasNext()) {
                        PhotoModel photoModel = listImgsIter.next();
                        if (photoModel.isChecked())
                            listImgsIter.remove();
                    }
                }
                listImgs.addAll(listImg);
                break;
            case 2:
                this.listImgs = listImg;
                break;
            default:
                listImgs.addAll(listImg);
                break;
        }
        parentLayout.removeAllViews();
        setCompress();
        int width = DensityUtil.dip2px(context, 70);
        int num = (int) (widthCommonForm / width);
        LayoutInflater inflater = LayoutInflater.from(context);
        LinearLayout parnentChildLayout = null;
        int indexcolum = 0;
        for (int i = 0; i < listImgs.size(); i++) {
            if (i % num == 0) {
                parnentChildLayout = new LinearLayout(                            //设置一个容器和背景及显示方式
                        context);
                parnentChildLayout.setOrientation(LinearLayout.HORIZONTAL);
                parnentChildLayout.setGravity(Gravity.CENTER_VERTICAL);
            }
            if (i == listImgs.size()) {
                //添加最后一个“+”图标

                if ((workflow_item != null && Integer.parseInt(workflow_item.getMode()) == 1)
                        ) {
                    if (!comeShare.equals("1")) {
                        ImageView imageView = new ImageView(context);
                        imageView.setImageResource(R.drawable.btn_add_pic); //图片资源
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                DensityUtil.dip2px(context, 60),
                                DensityUtil.dip2px(context, 60));
                        params.setMargins(DensityUtil.dip2px(context, 8), DensityUtil.dip2px(context, 2), 2, 2);
                        parnentChildLayout.addView(imageView, params);

                        imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mCellOnclickLisener1.onClick(v);
                            }
                        });

                    }

                }
            } else {
                RelativeLayout childLayout = (RelativeLayout) inflater.inflate(R.layout.layout_pic, null);
                ImageView imageView = (ImageView) childLayout.findViewById(R.id.img);
                ImageView imageClose = (ImageView) childLayout.findViewById(R.id.img_delete);
                imageClose.setTag(listImgs.get(i));
                if ((workflow_item != null && Integer.parseInt(workflow_item.getMode()) == 1)
                        ) {
                    if (!comeShare.equals("1")) {
                        imageClose.setVisibility(View.VISIBLE);
                    } else {
                        imageClose.setVisibility(View.GONE);
                    }

                } else {
                    imageClose.setVisibility(View.GONE);
                }
                Glide.with(context).load(listImgs.get(i).getOriginalPath()).
                        placeholder(R.drawable.pictures_no).
                        error(R.drawable.pictures_no).into(imageView);
                imageClose.setOnClickListener(new ImageViewDelect());
                imageView.setOnClickListener(new ImageViewOnClick(listImgs, i));
                parnentChildLayout.addView(childLayout);
            }
            indexcolum++;
            if (indexcolum == num) {
                indexcolum = 0;
                parentLayout.addView(parnentChildLayout);
            } else if (indexcolum < num && (listImgs.size()) - i < num && (listImgs.size()) % num == indexcolum) {
                parentLayout.addView(parnentChildLayout);
            }

        }

    }

    public void setCompress() {
        if (listImgs.size() > 0) {
            if(TextUtils.isEmpty(listImgs.get(indexCash).getOriginalPath())){
                return;
            }
            final File mFileTemp1 = new File(listImgs.get(indexCash).getOriginalPath());
            ImageCompressUtil.with(context).load(mFileTemp1).setCompressListener(new ImageCompressUtil.onCompressFileCallBack() {
                @Override
                public void onCompressStart() {
                }

                @Override
                public void onCompressFileResult(File file) {
                    file.renameTo(mFileTemp1);
                    indexCash++;
                    if (listImgs.size() > indexCash)
                        setCompress();
                }

                @Override
                public void onCompressError(Exception e) {

                }
            });
        }
    }

    public class ImageViewDelect implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Object obj = v.getTag();
            PhotoModel mphotoModel = (PhotoModel) obj;
            for (int i = 0; i < listImgs.size(); i++) {
                if (mphotoModel.equals(listImgs.get(i))) {
                    if (mphotoModel.isNet()) {

//                        FormExtensionFilesDao mFormExtensionFilesDao = new FormExtensionFilesDao(context);
                        FormExtensionFiles mFormExtensionFiles = new FormExtensionFiles();
                        mFormExtensionFiles.fileId = "";
                        mFormExtensionFiles.dataId = "";
                        mFormExtensionFiles.formId = "";
//                        mFormExtensionFiles.user_id = "";
                        mFormExtensionFiles.fieldId = workflow_item.getFieldId();
                        mFormExtensionFiles.fileType = workflow_item.getInput();
                        mHTSaveFormExtensionFiles.saveFormExtension(mFormExtensionFiles);
                    }
                    listImgs.remove(mphotoModel);
                    updateImg(context, listImgs, 2);
                }
            }

        }
    }


    public class ImageViewOnClick implements View.OnClickListener {
        private List<PhotoModel> mphotoModelList;
        private int position;

        public ImageViewOnClick(List<PhotoModel> mphotoModelList, int position) {
            this.mphotoModelList = mphotoModelList;
            this.position = position;
        }

        @Override
        public void onClick(View view) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("photos", mphotoModelList);
            map.put("position", position);
//            ActivityUnit.switchTo((Activity) context, PhotoPreviewActivity.class, map);
            if (mIntentPhone != null)
                mIntentPhone.onClick(view, map);

        }
    }

}
