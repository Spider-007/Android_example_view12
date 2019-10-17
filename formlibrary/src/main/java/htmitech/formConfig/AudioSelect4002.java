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
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import htmitech.com.componentlibrary.entity.FieldItem;
import htmitech.com.componentlibrary.entity.FormExtensionFiles;
import htmitech.com.componentlibrary.media_unit.TimeUtils;
import htmitech.com.componentlibrary.unit.DensityUtil;
import htmitech.com.componentlibrary.unit.DrawableUtils;
import htmitech.com.componentlibrary.unit.SysConvertUtil;
import htmitech.com.componentlibrary.unit.ToastUtil;
import htmitech.com.componentlibrary.unit.Utils;
import htmitech.com.formlibrary.R;
import htmitech.entity.FilePickerMusic;
import htmitech.listener.HTSaveFormExtensionFiles;
import htmitech.listener.ICellOnclick6102;
import htmitech.listener.YiJianOnclickLisener;
import htmitech.util.BitmapFactoryUtil;

/**
 * Created by Administrator on 2018-7-9.
 */

public class AudioSelect4002 {


    public Context context;
    public LinearLayout parentLayout;
    public float widthCommonForm;
    public YiJianOnclickLisener mCellOnclickLisener1;
    public ICellOnclick6102 mIntentPhone;
    public List<FilePickerMusic> mListPickerAudioSelect;
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
    private String input;

    public AudioSelect4002(Context context) {
        this.context = context;
    }

    //可编辑字段 输入方式为11
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public LinearLayout audioVideo(String VlineVisible, FieldItem item, LayoutInflater mInflater, List<TextView> list_tvsize, YiJianOnclickLisener mCellOnclickLisener, int com_workflow_mobileconfig_IM_enabled, String comeShare, HTSaveFormExtensionFiles mHTSaveFormExtensionFiles, ICellOnclick6102 mIntentPhone, int typeStyle) {
        {
            this.mCellOnclickLisener1 = mCellOnclickLisener;
            this.workflow_item = item;
            isDelete = false;
            this.mHTSaveFormExtensionFiles = mHTSaveFormExtensionFiles;
            this.mInflater = mInflater;
            this.list_tvsize = list_tvsize;
            this.mIntentPhone = mIntentPhone;
            this.comeShare = comeShare;
            this.typeStyle = typeStyle;
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


            return setReViewAudioVideo(item);
        }
    }

    public LinearLayout setReViewAudioVideo(final FieldItem item) {
        lineView.removeAllViews();

        DisplayMetrics dm = new DisplayMetrics();
        //获取屏幕信息
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        widthCommonForm = screenWidth * (((float) item.getPercent()) / 100);
        LinearLayout layout = (LinearLayout) mInflater.inflate(
                R.layout.audio_select_video, null);
        LinearLayout ll_style = (LinearLayout) layout.findViewById(R.id.ll_style);
        LinearLayout ll_audio_video_pa = (LinearLayout) layout.findViewById(R.id.ll_audio_video_pa);
        ImageView iv_photo_add = (ImageView) layout.findViewById(R.id.iv_audio_video);
        if (typeStyle == 1) {
            TextView form_fielditem_beforetext = (TextView) layout.findViewById(R.id.form_fielditem_beforetext);
            form_fielditem_beforetext.setText(item.getName());
            form_fielditem_beforetext.setTextColor(Color.parseColor("#999999"));
            ll_style.setVisibility(View.VISIBLE);
            ll_audio_video_pa.setVisibility(View.GONE);
            parentLayout = (LinearLayout) layout.findViewById(R.id.ll_audio_video_style);
            iv_photo_add = (ImageView) layout.findViewById(R.id.iv_audio_video_style);

        } else {
            ll_style.setVisibility(View.GONE);
            ll_audio_video_pa.setVisibility(View.VISIBLE);
            parentLayout = (LinearLayout) layout.findViewById(R.id.ll_audio_video);
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
                    if(((item.getInput().equals("4001") || item.getInput().equals("5001")) || item.getInput().equals("5002")) && mListPickerAudioSelect.size() > 0){
                        ToastUtil.showShort(context,"此处为单选，如要更换请先删除已选");
                    }else{
                        mCellOnclickLisener1.onClick(v);
                    }

                }

            }
        });
        List<FilePickerMusic> listImgTemp = new ArrayList<FilePickerMusic>();
        input = item.getInput();
        if (TextUtils.equals(item.getInput(), "4001") || TextUtils.equals(item.getInput(), "4002") || TextUtils.equals(item.getInput(), "4101") || TextUtils.equals(item.getInput(), "4102")) {
            iv_photo_add.setImageResource(R.drawable.btn_tape);
            ll_audio_video_pa.setOrientation(LinearLayout.VERTICAL);
            if (item.getFiledAudios() != null && item.getFiledAudios().size() > 0) {
                List<FormExtensionFiles> mFormExtensionFilesList = item.getFiledAudios();
                for (FormExtensionFiles mFormExtensionFiles : mFormExtensionFilesList) {
                    FilePickerMusic mPhotoModel = new FilePickerMusic();
                    mPhotoModel.setItem_workflow(item);
                    mPhotoModel.path = mFormExtensionFiles.getFileUrl();
                    mPhotoModel.fileMediumUrl = mFormExtensionFiles.getFileMediumUrl();
                    mPhotoModel.isCheck = false;
                    mPhotoModel.setDuration(mFormExtensionFiles.getMediaDuration() * 1000);
                    mPhotoModel.setType(1);
                    mPhotoModel.setFile_id(mFormExtensionFiles.fileId);
                    listImgTemp.add(mPhotoModel);
                }

            }
        } else {
            iv_photo_add.setImageResource(R.drawable.btn_video);
            ll_audio_video_pa.setOrientation(LinearLayout.VERTICAL);

            if (item.getFiledVideos() != null && item.getFiledVideos().size() > 0) {
                List<FormExtensionFiles> mFormExtensionFilesList = item.getFiledVideos();
                for (FormExtensionFiles mFormExtensionFiles : mFormExtensionFilesList) {
                    FilePickerMusic mPhotoModel = new FilePickerMusic();
                    mPhotoModel.setItem_workflow(item);
                    mPhotoModel.path = mFormExtensionFiles.getFileUrl();
                    mPhotoModel.fileMediumUrl = mFormExtensionFiles.getFileMediumUrl();
                    mPhotoModel.isCheck = false;
                    mPhotoModel.setType(2);
                    mPhotoModel.setDuration(mFormExtensionFiles.getMediaDuration() * 1000);
                    mPhotoModel.setFile_id(mFormExtensionFiles.fileId);
                    listImgTemp.add(mPhotoModel);
                }

            }
        }


        updateAudioVideo(context, listImgTemp, 0);
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
    public void updateAudioVideo(final Context context, final List<FilePickerMusic> listImg, int wherecome) {
        if(!TextUtils.isEmpty(input) && (input.equals("4001") || input.equals("5001"))){
            wherecome = 2;
        }
        switch (wherecome) {
            case 0:
                if (mListPickerAudioSelect == null) {
                    mListPickerAudioSelect = new ArrayList<FilePickerMusic>();
                    mListPickerAudioSelect.addAll(listImg);
                } else {
                    mListPickerAudioSelect.addAll(listImg);
                }
                break;
            case 1:
                if (mListPickerAudioSelect.size() > 0) {
                    Iterator<FilePickerMusic> listImgsIter = mListPickerAudioSelect.iterator();
                    while (listImgsIter.hasNext()) {
                        FilePickerMusic photoModel = listImgsIter.next();
                        if (photoModel.isCheck)
                            listImgsIter.remove();
                    }
                }
                mListPickerAudioSelect.addAll(listImg);
                break;
            case 2:
                this.mListPickerAudioSelect = listImg;
                break;
            default:
                mListPickerAudioSelect.addAll(listImg);
                break;
        }
        parentLayout.removeAllViews();
//        setCompress();
        int width = DensityUtil.dip2px(context, 70);
        int num = (int) (widthCommonForm / width);
        num = 1;
        LayoutInflater inflater = LayoutInflater.from(context);
        LinearLayout parnentChildLayout = null;
        int indexcolum = 0;
        for (int i = 0; i < mListPickerAudioSelect.size(); i++) {
//            if(mListPickerAudioSelect.get(i).getType() == 2){
//                num = 1;
//            }
            if (i % num == 0) {
                parnentChildLayout = new LinearLayout(                            //设置一个容器和背景及显示方式
                        context);
                parnentChildLayout.setOrientation(LinearLayout.HORIZONTAL);
                parnentChildLayout.setGravity(Gravity.CENTER_VERTICAL);
            }
            if (i == mListPickerAudioSelect.size()) {
                //添加最后一个“+”图标

//                if ((workflow_item != null && Integer.parseInt(workflow_item.getMode()) == 1)
//                        ) {
//                    if (!comeShare.equals("1")) {
//                        ImageView imageView = new ImageView(context);
//                        imageView.setImageResource(R.drawable.btn_tape); //图片资源
//                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                                DensityUtil.dip2px(context, 60),
//                                DensityUtil.dip2px(context, 60));
//                        params.setMargins(DensityUtil.dip2px(context, 8), DensityUtil.dip2px(context, 2), 2, 2);
//                        parnentChildLayout.addView(imageView, params);
//
//                        imageView.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                mCellOnclickLisener1.onClick(v);
//                            }
//                        });
//
//                    }
//
//                }
            } else {
                RelativeLayout childLayout = (RelativeLayout) inflater.inflate(R.layout.layout_pic_video, null);
                final ImageView imageView = (ImageView) childLayout.findViewById(R.id.img);
                LinearLayout audio_mp3 = (LinearLayout) childLayout.findViewById(R.id.audio_mp3);
                ImageView imageClose = (ImageView) childLayout.findViewById(R.id.img_delete);
                ImageView iv_xf = (ImageView) childLayout.findViewById(R.id.iv_xf);
                TextView tv_mp3_timer = (TextView) childLayout.findViewById(R.id.tv_mp3_timer);
                TextView tv_duration = (TextView) childLayout.findViewById(R.id.tv_duration);
                TextView tv_zz = (TextView) childLayout.findViewById(R.id.tv_zz);
//                TextView tv_zz = (TextView) childLayout.findViewById(R.id.tv_zz);
//                tv_zz.getBackground().setAlpha(100);
                iv_xf.setVisibility(View.VISIBLE);
                iv_xf.setImageResource(R.drawable.icon_video_play);
                imageClose.setTag(mListPickerAudioSelect.get(i));
                tv_duration.setText(TimeUtils.long2String(mListPickerAudioSelect.get(i).getDuration()));


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
//                Glide.with(context).load(R.drawable.icon_tape_preview).
//                        placeholder(R.drawable.icon_tape_preview).
//                        error(R.drawable.icon_tape_preview).into(imageView);
                tv_mp3_timer.setText(TimeUtils.long2String(mListPickerAudioSelect.get(i).getDuration()));
                if (mListPickerAudioSelect.get(i).getType() == 1) {
                    tv_duration.setVisibility(View.GONE);
                    audio_mp3.setVisibility(View.VISIBLE);
                    imageView.setVisibility(View.GONE);
                    iv_xf.setVisibility(View.GONE);
//                    imageView.setImageResource(R.drawable.icon_tape_preview);
                } else {
                    tv_duration.setVisibility(View.VISIBLE);
                    audio_mp3.setVisibility(View.GONE);
                    tv_zz.setVisibility(View.VISIBLE);
                    tv_zz.getBackground().setAlpha(100);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(context, 200));
                    imageView.setLayoutParams(params);
                    tv_zz.setLayoutParams(params);
                    if (mListPickerAudioSelect.get(i).getPath().contains("http:")) {
                        Glide.with(context)
                                .load(mListPickerAudioSelect.get(i).getFileMediumUrl()).error(R.drawable.img_preview_video).placeholder(R.drawable.img_preview_video)
                                .into(new SimpleTarget<GlideDrawable>() {
                                    @Override
                                    public void onResourceReady(GlideDrawable resource, GlideAnimation glideAnimation) {
                                        imageView.setBackgroundDrawable(resource);
                                    }
                                });
                    }else{
                        imageView.setBackgroundDrawable(BitmapFactoryUtil.get().getDrawable(mListPickerAudioSelect.get(i).getPath()));
                    }


//                    Glide.with(context).load(BitmapFactoryUtil.get().getDrawable(mListPickerAudioSelect.get(i).getPath())).
//                            placeholder(R.drawable.pictures_no).
//                            error(R.drawable.pictures_no).into(imageView);
                }

                imageClose.setOnClickListener(new ImageViewDelect());
                imageView.setOnClickListener(new AudioVideoPlay(mListPickerAudioSelect.get(i).getType(), mListPickerAudioSelect.get(i).path));
                audio_mp3.setOnClickListener(new AudioVideoPlay(mListPickerAudioSelect.get(i).getType(), mListPickerAudioSelect.get(i).path));
                parnentChildLayout.addView(childLayout);
            }
            indexcolum++;
            if (indexcolum == num) {
                indexcolum = 0;
                parentLayout.addView(parnentChildLayout);
            } else if (indexcolum < num && (mListPickerAudioSelect.size()) - i < num && (mListPickerAudioSelect.size()) % num == indexcolum) {
                parentLayout.addView(parnentChildLayout);
            }

        }
    }


    public class AudioVideoPlay implements View.OnClickListener {
        private String path;
        private int type;

        public AudioVideoPlay(int type, String path) {
            this.path = path;
            this.type = type;
        }

        @Override
        public void onClick(View v) {
            if (type == 2) {
                Utils.playVideo(context, path);
            } else {
                Utils.playAudio(context, path);
            }
//            Utils.playAudio(context, path);
        }
    }

    public class ImageViewDelect implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Object obj = v.getTag();
            FilePickerMusic mphotoModel = (FilePickerMusic) obj;
            for (int i = 0; i < mListPickerAudioSelect.size(); i++) {
                if (mphotoModel.equals(mListPickerAudioSelect.get(i))) {
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
                    mListPickerAudioSelect.remove(mphotoModel);
                    updateAudioVideo(context, mListPickerAudioSelect, 2);
                }
            }

        }
    }

}
