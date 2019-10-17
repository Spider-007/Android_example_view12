package com.htmitech.commonx.util;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.MediaScannerConnectionClient;
import android.net.Uri;

/**
 * 媒体扫描类，在文库添加媒体文件（如图片、音视频等）到SD卡时调用，以便更新系统数据库记录
 * 
 * @author guhaoxin
 * @date 2012-11-16
 */
public class MediaScanner {

	private MediaScannerNotifier mNotifier = null;

	public MediaScanner(Context context, MediaScannerModel model) {
		if (null == mNotifier) {
			mNotifier = new MediaScannerNotifier(context, model);
		}
	}

	private class MediaScannerNotifier implements MediaScannerConnectionClient {

		private MediaScannerConnection mConn = null;

		private Context mContext = null;

		private MediaScannerModel mModel = null;

		public MediaScannerNotifier(final Context context,
				final MediaScannerModel model) {
			mContext = context;
			mModel = model;
			mConn = new MediaScannerConnection(mContext, this);
			mConn.connect();
		}

		@Override
		public void onMediaScannerConnected() {
			if (null != mModel.mFilePath) {
				mConn.scanFile(mModel.mFilePath, mModel.mMimeType);
			}
			if (null != mModel.mFilePaths) {
				for (String file : mModel.mFilePaths) {
					mConn.scanFile(file, mModel.mMimeType);
				}
			}
			mModel.reset();
		}

		@Override
		public void onScanCompleted(String path, Uri uri) {
			mConn.disconnect();
		}

	}

	public static class MediaScannerModel {

		public String mFilePath = null;
		public String mMimeType = null;
		public String[] mFilePaths = null;

		public void reset() {
			mFilePath = null;
			mMimeType = null;
			mFilePaths = null;
		}
	}

}