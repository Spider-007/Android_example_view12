package com.htmitech.photoselector.domain;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.htmitech.photoselector.controller.AlbumController;
import com.htmitech.photoselector.model.AlbumModel;
import com.htmitech.photoselector.ui.PhotoSelectorActivity;

import java.util.List;

import htmitech.com.componentlibrary.entity.PhotoModel;


@SuppressLint("HandlerLeak")
public class PhotoSelectorDomain {

	private AlbumController albumController;

	public PhotoSelectorDomain(Context context) {
		albumController = new AlbumController(context);
	}

	public void getReccent(final PhotoSelectorActivity.OnLocalReccentListener listener) {
		final Handler handler = new Handler() {
			@SuppressWarnings("unchecked")
			@Override
			public void handleMessage(Message msg) {
				listener.onPhotoLoaded((List<PhotoModel>) msg.obj);
			}
		};
		new Thread(new Runnable() {
			@Override
			public void run() {
				List<PhotoModel> photos = albumController.getCurrent();
				Message msg = new Message();
				msg.obj = photos;
				handler.sendMessage(msg);
			}
		}).start();
	}

	/** ��ȡ����б� */
	public void updateAlbum(final PhotoSelectorActivity.OnLocalAlbumListener listener) {
		final Handler handler = new Handler() {
			@SuppressWarnings("unchecked")
			@Override
			public void handleMessage(Message msg) {
				listener.onAlbumLoaded((List<AlbumModel>) msg.obj);
			}
		};
		new Thread(new Runnable() {
			@Override
			public void run() {
				List<AlbumModel> albums = albumController.getAlbums();
				Message msg = new Message();
				msg.obj = albums;
				handler.sendMessage(msg);
			}
		}).start();
	}

	/** ��ȡ��������µ�������Ƭ��Ϣ */
	public void getAlbum(final String name, final PhotoSelectorActivity.OnLocalReccentListener listener) {
		final Handler handler = new Handler() {
			@SuppressWarnings("unchecked")
			@Override
			public void handleMessage(Message msg) {
				listener.onPhotoLoaded((List<PhotoModel>) msg.obj);
			}
		};
		new Thread(new Runnable() {
			@Override
			public void run() {
				List<PhotoModel> photos = albumController.getAlbum(name);
				Message msg = new Message();
				msg.obj = photos;
				handler.sendMessage(msg);
			}
		}).start();
	}

}