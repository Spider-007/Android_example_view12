//package com.htmitech.emportal.ui.formConfig;
//
//import android.app.Activity;
//import android.content.Context;
//import android.util.DisplayMetrics;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.bumptech.glide.Glide;
//import com.htmitech.emportal.HtmitechApplication;
//import com.htmitech.emportal.R;
//import com.htmitech.emportal.ui.detail.DetailActivity;
//import com.htmitech.emportal.ui.detail.DetailActivity2;
//import com.htmitech.emportal.ui.detail.FormFragment;
//import com.htmitech.htcommonformplugin.activity.GeneralFormDetalActivity;
//import com.htmitech.htcommonformplugin.dao.FormExtensionFilesDao;
//import com.htmitech.htcommonformplugin.fragment.GeneralFormDetailFragment;
//import com.htmitech.photoselector.model.Fielditems;
//import com.htmitech.photoselector.model.FormExtensionFiles;
//import com.htmitech.photoselector.ui.PhotoPreviewActivity;
//import com.htmitech.proxy.util.ImageCompressUtil;
//import com.htmitech.unit.ActivityUnit;
//import com.htmitech.unit.DensityUtil;
//import com.minxing.client.util.SysConvertUtil;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//
//import htmitech.com.componentlibrary.entity.FieldItem;
//import htmitech.com.componentlibrary.entity.PhotoModel;
//
//
///**
// * // 1，**********************处理可编辑的意见字段******************************
// */
//public class SelectPhoto6001_6002_6101_6102 {
//
//    public Context context;
//    public LinearLayout parentLayout;
//    public float widthCommonForm;
//    public GeneralFormDetailFragment.CellOnclickLisener6001_6002_6101_6102 mCellOnclickLisener;
//    public FormFragment.CellOnclickLisener6001_6002_6101_6102 mCellOnclickLisener1;
//    public List<PhotoModel> mListPickerAudioSelect;
//    public boolean isDelete;
//    public FieldItem workflow_item;
//    public Fielditems common_item;
//    private int indexCash = 0;
//
//    public SelectPhoto6001_6002_6101_6102(Context context) {
//        this.context = context;
//    }
//
//    //可编辑字段 输入方式为11
//    public LinearLayout Photo6102(String VlineVisible, FieldItem item, LayoutInflater mInflater, List<TextView> list_tvsize, FormFragment.CellOnclickLisener6001_6002_6101_6102 mCellOnclickLisener, int com_workflow_mobileconfig_IM_enabled) {
////        {
////            this.mCellOnclickLisener1 = mCellOnclickLisener;
////            this.workflow_item = item;
////            isDelete = false;
////            // 1，**********************处理可编辑的意见字段******************************
////            LinearLayout lineView = new LinearLayout(                            //设置一个容器和背景及显示方式
////                    HtmitechApplication.instance());
////            lineView.setOrientation(LinearLayout.VERTICAL);
////            lineView.setGravity(Gravity.CENTER_VERTICAL);
////            lineView.setBackgroundColor(SysConvertUtil.formattingH(item.getBackColor()));
////            if (VlineVisible.equalsIgnoreCase("true"))
////                lineView.setBackgroundResource(R.drawable.corners_bg_white_press_stroke);
////
////            if (DetailActivity2.currentActivity)
////                lineView.setEnabled(false);
////            DisplayMetrics dm = new DisplayMetrics();
////            //获取屏幕信息
////            ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
////            int screenWidth = dm.widthPixels;
////            widthCommonForm = screenWidth * (((float) item.getPercent()) / 100);
////            LinearLayout layout = (LinearLayout) mInflater.inflate(
////                    R.layout.layout_formdetail_cell_photo6001_6002_6101_6102_lib, null);
////            if(item.isMustInput()){
////                layout.setBackgroundResource(R.drawable.form_input_bg_must);
////            }
////            parentLayout = (LinearLayout) layout.findViewById(R.id.ll_photo_img);
//////            ImageView iv_photo_add = (ImageView)layout.findViewById(R.id.iv_photo_add);
//////            iv_photo_add.setOnClickListener(mCellOnclickLisener);
////            List<PhotoModel> listImgTemp = new ArrayList<PhotoModel>();
////            if (item.getExt_filed_images() != null && item.getExt_filed_images().size() > 0) {
////                List<FormExtensionFiles> mFormExtensionFilesList = item.getExt_filed_images();
////                for (FormExtensionFiles mFormExtensionFiles : mFormExtensionFilesList) {
////                    PhotoModel mPhotoModel = new PhotoModel();
////                    mPhotoModel.setItem_workflow(item);
////                    mPhotoModel.setOriginalPath(mFormExtensionFiles.file_url);
////                    mPhotoModel.setChecked(false);
////                    mPhotoModel.setFile_id(mFormExtensionFiles.file_id);
////                    listImgTemp.add(mPhotoModel);
////                }
////
////            }
////            updateImg(context, listImgTemp, 0);
////            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
////                    LinearLayout.LayoutParams.MATCH_PARENT,
////                    LinearLayout.LayoutParams.WRAP_CONTENT, 1);
////            params.gravity = Gravity.CENTER_VERTICAL;
////            lineView.addView(layout, params);
////
////            return lineView;
////
////        }
//    }
//
//
//    //可编辑字段 输入方式为11
//    public LinearLayout Photo6102CommonForm(boolean VlineVisible, Fielditems item, LayoutInflater mInflater, List<TextView> list_tvsize, GeneralFormDetailFragment.CellOnclickLisener6001_6002_6101_6102 mCellOnclickLisener, int com_workflow_mobileconfig_IM_enabled) {
////        {
////            this.mCellOnclickLisener = mCellOnclickLisener;
////            this.common_item = item;
////            // 1，**********************处理可编辑的意见字段******************************
////            LinearLayout lineView = new LinearLayout(                            //设置一个容器和背景及显示方式
////                    HtmitechApplication.instance());
////            lineView.setOrientation(LinearLayout.VERTICAL);
////            lineView.setGravity(Gravity.CENTER_VERTICAL);
////            lineView.setBackgroundColor(SysConvertUtil.formattingH(item.getFiled_backcolor()));
////
////            DisplayMetrics dm = new DisplayMetrics();
////            //获取屏幕信息
////            ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
////            int screenWidth = dm.widthPixels;
////            widthCommonForm = screenWidth * (((float) item.getPercent()) / 100);
////            if (VlineVisible)
////                lineView.setBackgroundResource(R.drawable.corners_bg_white_press_stroke);
////
////            if (DetailActivity2.currentActivity)
////                lineView.setEnabled(false);
////            LinearLayout layout = (LinearLayout) mInflater.inflate(
////                    R.layout.layout_formdetail_cell_photo6001_6002_6101_6102_lib, null);
////            if(item.isMustinput()){
////                layout.setBackgroundResource(R.drawable.form_input_bg_must);
////            }
////            parentLayout = (LinearLayout) layout.findViewById(R.id.ll_photo_img);
//////            ImageView iv_photo_add = (ImageView)layout.findViewById(R.id.iv_photo_add);
//////            iv_photo_add.setOnClickListener(mCellOnclickLisener);
////            List<PhotoModel> listImgTemp = new ArrayList<PhotoModel>();
////            if (item.getExt_filed_images() != null && item.getExt_filed_images().size() > 0) {
////                List<FormExtensionFiles> mFormExtensionFilesList = item.getExt_filed_images();
////                for (FormExtensionFiles mFormExtensionFiles : mFormExtensionFilesList) {
////                    PhotoModel mPhotoModel = new PhotoModel();
////                    mPhotoModel.setItem_common(item);
////                    mPhotoModel.setOriginalPath(mFormExtensionFiles.file_url);
////                    listImgTemp.add(mPhotoModel);
////                }
////            }
////            updateImg(context, listImgTemp, 0);
////            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
////                    LinearLayout.LayoutParams.MATCH_PARENT,
////                    LinearLayout.LayoutParams.WRAP_CONTENT, 1);
////            params.gravity = Gravity.CENTER_VERTICAL;
////            lineView.addView(layout, params);
////            return lineView;
////
////        }
//    }
//
//    /**
//     * 更新显示图片
//     *
//     * @param context
//     * @param listImg
//     * @param wherecome 0 初始化 拍照  1 相册  2 删除
//     */
//    public void updateImg(final Context context, final List<PhotoModel> listImg, int wherecome) {
//
//        switch (wherecome) {
//            case 0:
//                if(mListPickerAudioSelect ==null){
//                    mListPickerAudioSelect = new ArrayList<PhotoModel>();
//                    mListPickerAudioSelect.addAll(listImg);
//                }else {
//                    mListPickerAudioSelect.addAll(listImg);
//                }
//                break;
//            case 1:
//                if (mListPickerAudioSelect.size() > 0) {
//                    Iterator<PhotoModel> listImgsIter = mListPickerAudioSelect.iterator();
//                    while (listImgsIter.hasNext()) {
//                        PhotoModel photoModel = listImgsIter.next();
//                        if (photoModel.isChecked())
//                            listImgsIter.remove();
//                    }
//                }
//                mListPickerAudioSelect.addAll(listImg);
//                break;
//            case 2:
//                this.mListPickerAudioSelect = listImg;
//                break;
//            default:
//                mListPickerAudioSelect.addAll(listImg);
//                break;
//        }
//        setCompress();
//        parentLayout.removeAllViews();
//        int width = DensityUtil.dip2px(context, 70);
//        int num = (int) (widthCommonForm / width);
//        LayoutInflater inflater = LayoutInflater.from(context);
//        LinearLayout parnentChildLayout = null;
//        int indexcolum = 0;
//        for (int i = 0; i < mListPickerAudioSelect.size() + 1; i++) {
//            if (i % num == 0) {
//                parnentChildLayout = new LinearLayout(                            //设置一个容器和背景及显示方式
//                        HtmitechApplication.instance());
//                parnentChildLayout.setOrientation(LinearLayout.HORIZONTAL);
//                parnentChildLayout.setGravity(Gravity.CENTER_VERTICAL);
//            }
//            if (i == mListPickerAudioSelect.size()) {
//                //添加最后一个“+”图标
//
//                if ((workflow_item != null && Integer.parseInt(workflow_item.getMode()) == 1) || (common_item != null && Integer.parseInt(common_item.getMode()) == 1)
//                        ) {
//                   if((context instanceof DetailActivity) ? !((DetailActivity) context).comeShare.equals("1") : !((GeneralFormDetalActivity) context).comeShare.equals("1")){
//                       ImageView imageView = new ImageView(context);
//                       imageView.setImageResource(R.drawable.btn_add_pic); //图片资源
//                       LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                               DensityUtil.dip2px(context, 60),
//                               DensityUtil.dip2px(context, 60));
//                       params.setMargins(DensityUtil.dip2px(context, 8), DensityUtil.dip2px(context, 2), 2, 2);
//                       parnentChildLayout.addView(imageView, params);
//                       if (mCellOnclickLisener instanceof GeneralFormDetailFragment.CellOnclickLisener6001_6002_6101_6102) {
//                           imageView.setOnClickListener(mCellOnclickLisener);
//                       } else {
//                           imageView.setOnClickListener(mCellOnclickLisener1);
//                       }
//                   }
//
//                }
//            } else {
//                RelativeLayout childLayout = (RelativeLayout) inflater.inflate(R.layout.layout_pic, null);
//                ImageView imageView = (ImageView) childLayout.findViewById(R.id.img);
//                ImageView imageClose = (ImageView) childLayout.findViewById(R.id.img_delete);
//                imageClose.setTag(mListPickerAudioSelect.get(i));
//                if ((workflow_item != null && Integer.parseInt(workflow_item.getMode()) == 1) || (common_item != null && Integer.parseInt(common_item.getMode()) == 1)
//                        ) {
//                    if((context instanceof DetailActivity) ? !((DetailActivity) context).comeShare.equals("1") : !((GeneralFormDetalActivity) context).comeShare.equals("1")){
//                        imageClose.setVisibility(View.VISIBLE);
//                    }else {
//                        imageClose.setVisibility(View.GONE);
//                    }
//
//                }else {
//                    imageClose.setVisibility(View.GONE);
//                }
////                imageimageClosemView.setTag(listImg.get(i));
//                Glide.with(context).load(mListPickerAudioSelect.get(i).getOriginalPath()).
//                        placeholder(com.htmitech.addressbook.R.drawable.pictures_no).
//                        error(com.htmitech.addressbook.R.drawable.pictures_no).into(imageView);
////                BitmapUtils.instance().display(imageView, listImg.get(i).getOriginalPath());
////                imageClose.setOnClickListener(new View.OnClickListener() {
////                    @Override
////                    public void onClick(View view) {
////                        Object obj = view.getTag();
////                        int tag = Integer.parseInt(String.valueOf(obj));
////                        mListPickerAudioSelect.remove(tag);
////                        updateImg(context, mListPickerAudioSelect);
////                    }
////                });
//                imageClose.setOnClickListener(new ImageViewDelect());
////                imageClose.setOnClickListener(mCellOnclickLisener);
//                imageView.setOnClickListener(new ImageViewOnClick(mListPickerAudioSelect, i));
//                parnentChildLayout.addView(childLayout);
//            }
//            indexcolum++;
//            if (indexcolum == num) {
//                indexcolum = 0;
//                parentLayout.addView(parnentChildLayout);
//            } else if (indexcolum < num && (mListPickerAudioSelect.size() + 1) - i < num && (mListPickerAudioSelect.size() + 1) % num == indexcolum) {
//                parentLayout.addView(parnentChildLayout);
//            }
//
//        }
//
//    }
//
//    public void setCompress() {
//        final File mFileTemp1 = new File(mListPickerAudioSelect.get(indexCash).getOriginalPath());
//        ImageCompressUtil.with(context).load(mFileTemp1).setCompressListener(new ImageCompressUtil.onCompressFileCallBack() {
//            @Override
//            public void onCompressStart() {
//            }
//            @Override
//            public void onCompressFileResult(File file) {
//                file.renameTo(mFileTemp1);
//                indexCash++;
//                if (mListPickerAudioSelect.size() > indexCash)
//                    setCompress();
//            }
//            @Override
//            public void onCompressError(Exception e) {
//
//            }
//        });
//    }
//
//    public class ImageViewDelect implements View.OnClickListener {
//
//        @Override
//        public void onClick(View v) {
//            Object obj = v.getTag();
//            PhotoModel mphotoModel = (PhotoModel) obj;
//            for (int i = 0; i < mListPickerAudioSelect.size(); i++) {
//                if (mphotoModel.equals(mListPickerAudioSelect.get(i))) {
//                    if (mphotoModel.isNet()) {
//                        FormExtensionFilesDao mFormExtensionFilesDao = new FormExtensionFilesDao(context);
//                        FormExtensionFiles mFormExtensionFiles = new FormExtensionFiles();
//                        if (common_item != null) {
//                            mFormExtensionFiles.file_id = "";
//                            mFormExtensionFiles.data_id = "";
//                            mFormExtensionFiles.form_id = "";
//                            mFormExtensionFiles.user_id = "";
//                            mFormExtensionFiles.file_id = common_item.getField_id();
//                            mFormExtensionFiles.ext_field_type = common_item.getInput_type();
//                        } else {
//                            mFormExtensionFiles.file_id = "";
//                            mFormExtensionFiles.data_id = "";
//                            mFormExtensionFiles.form_id = "";
//                            mFormExtensionFiles.user_id = "";
//                            mFormExtensionFiles.field_id = workflow_item.getField_id();
//                            mFormExtensionFiles.ext_field_type = workflow_item.getInput();
//                        }
//                        mFormExtensionFilesDao.saveExtensionFiles(mFormExtensionFiles);
//                    }
//                    mListPickerAudioSelect.remove(mphotoModel);
//                    updateImg(context, mListPickerAudioSelect, 2);
//                }
//            }
//
//        }
//    }
//
//
//    public class ImageViewOnClick implements View.OnClickListener {
//        private List<PhotoModel> mphotoModelList;
//        private int position;
//
//        public ImageViewOnClick(List<PhotoModel> mphotoModelList, int position) {
//            this.mphotoModelList = mphotoModelList;
//            this.position = position;
//        }
//
//        @Override
//        public void onClick(View view) {
//            Map<String, Object> map = new HashMap<String, Object>();
//            map.put("photos", mphotoModelList);
//            map.put("position", position);
//            ActivityUnit.switchTo((Activity) context, PhotoPreviewActivity.class, map);
//
//
//        }
//    }
//
//}
