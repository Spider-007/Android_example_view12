
package com.example.archivermodule.pdfsign;

import android.graphics.Bitmap;
import android.widget.Toast;


import com.example.archivermodule.BookShower;
import com.example.archivermodule.R;

import com.kinggrid.iapppdf.ui.viewer.IAppPDFActivity;
import com.kinggrid.pdfservice.Annotation;

/**
 * 批注工具类：插入和显示批注
 * com.kinggrid.iapppdf.demo.AnnotUtil
 * @author wmm
 * create at 2015年9月15日 上午9:00:36
 */
public class AnnotUtil {

	private static final String TAG = "AnnotUtil";
	private BookShower bookShower;
	private String userName;
	
	public AnnotUtil(BookShower bookShower,String userName) {
		this.bookShower = bookShower;
		this.userName = userName;
	}

	/**
	 * 插入文字批注
	 * @param x 插入批注在文档X值
	 * @param y 插入批注在文档Y值
	 */
	public void addTextAnnot(final float x,final float y) {
		final Annotation annotation = new Annotation();
		annotation.setAuthorName(userName);
		TextAnnotDialog showAnnotContent = new TextAnnotDialog(bookShower, annotation);
		showAnnotContent.setDeleteBtnGone();
		showAnnotContent
				.setSaveAnnotListener(new TextAnnotDialog.OnSaveAnnotListener() {

					@Override
					public void onAnnotSave(final Annotation annotTextNew) {
//						if (IAppPDFActivity.progressBarStatus != 0) { //正在渲染
//							return;
//						}
						bookShower.doSaveTextAnnot(annotTextNew, x, y);
					}

					@Override
					public void onAnnotSave(Bitmap bitmap, String time) {
						// TODO Auto-generated method stub
						
					}
				});

	}
	/**
	 * 插入语音批注
	 * @param x 插入批注在文档X值
	 * @param y 插入批注在文档Y值
	 */
	public void addSoundAnnot(final float x,final float y){
		final String autherString = IAppPDFActivity.userName;
		final Annotation annotation = new Annotation();
		annotation.setAuthorName(autherString);
		annotation.setUnType("");
			final SoundAnnotDialog showAnnotSound = new SoundAnnotDialog(
					bookShower, 400, 300, annotation);
			showAnnotSound
					.setSaveAnnotListener(new SoundAnnotDialog.OnSaveAnnotListener() {

						@Override
						public void onAnnotSave(final Annotation annotTextNew) {
							bookShower.doSaveSoundAnnot(annotTextNew, x, y);
							bookShower.unLockScreen();
						}
					});

			showAnnotSound
					.setCloseAnnotListener(new SoundAnnotDialog.OnCloseAnnotListener() {

						@Override
						public void onAnnotClose(final String filePath) {
							bookShower.doCloseSoundAnnot(filePath);
							bookShower.unLockScreen();
						}
					});

	}
	
	/**
	 * 显示文字批注
	 * @param annotation
	 */
	public void showTextAnnot(final Annotation annotation){

		if(!annotation.getAuthorName().equals(IAppPDFActivity.userName)){
			Toast.makeText(bookShower, R.string.username_different_edit,
					Toast.LENGTH_SHORT).show();
		}

		TextAnnotDialog showAnnotContent = new TextAnnotDialog(bookShower, annotation);
		showAnnotContent
				.setSaveAnnotListener(new TextAnnotDialog.OnSaveAnnotListener() {

					@Override
					public void onAnnotSave(
							final Annotation annotTextNew) {
//						if (IAppPDFActivity.progressBarStatus != 0) {
//							return;
//						}
						bookShower.doUpdateTextAnnotation(annotTextNew);
					}

					@Override
					public void onAnnotSave(Bitmap bitmap, String time) {
						// TODO Auto-generated method stub
						
					}
				});
		showAnnotContent
				.setDeleteAnnotListener(new TextAnnotDialog.OnDeleteAnnotListener() {

					@Override
					public void onAnnotDelete() {
						bookShower.doDeleteTextAnnotation(annotation);
					}
				});
	}
	/**
	 * 显示语音批注
	 * @param annotation
	 */
	public void showSoundAnnot(final Annotation annotation){
		
			final SoundAnnotDialog showAnnotSound = new SoundAnnotDialog(
					bookShower, 400, 300, annotation);
			showAnnotSound
					.setDeleteAnnotListener(new SoundAnnotDialog.OnDeleteAnnotListener() {

						@Override
						public void onAnnotDelete(final String filePath) {
							bookShower.doDeleteSoundAnnot(annotation, filePath);
							bookShower.unLockScreen();
						}
					});

	}
}

