package com.htmitech.commonx.base.net;

import android.content.Context;
import android.database.Cursor;

import com.htmitech.commonx.base.DbUtils;
import com.htmitech.commonx.base.HttpUtils;
import com.htmitech.commonx.base.db.converter.ColumnConverter;
import com.htmitech.commonx.base.db.converter.ColumnConverterFactory;
import com.htmitech.commonx.base.db.sqlite.ColumnDbType;
import com.htmitech.commonx.base.db.sqlite.Selector;
import com.htmitech.commonx.base.exception.DbException;
import com.htmitech.commonx.base.exception.HttpException;
import com.htmitech.commonx.base.http.HttpHandler;
import com.htmitech.commonx.base.http.ResponseInfo;
import com.htmitech.commonx.base.http.callback.RequestCallBack;
import com.htmitech.commonx.util.LogUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import htmitech.com.componentlibrary.listener.DownLoadStop;

public class DownloadManager implements DownLoadStop {

	private List<DownloadInfo> downloadInfoList;

	private int maxDownloadThread = 3;

	private Context mContext;
	private DbUtils db;

	public DownloadManager(Context appContext) {
		ColumnConverterFactory.registerColumnConverter(HttpHandler.State.class,
				new HttpHandlerStateConverter());
		mContext = appContext;
		db = DbUtils.create(mContext);
		try {
			downloadInfoList = db.findAll(Selector.from(DownloadInfo.class));
		} catch (DbException e) {
			LogUtil.e(e.getMessage(), e);
		}
		if (downloadInfoList == null) {
			downloadInfoList = new ArrayList<DownloadInfo>();
		}
	}

	public int getDownloadInfoListCount() {
		return downloadInfoList.size();
	}

	public DownloadInfo getDownloadInfo(int index) {
		return downloadInfoList.get(index);
	}

	public void addNewDownload(String url, String fileName, String target,
			boolean autoResume, boolean autoRename,
			final RequestCallBack<File> callback) throws DbException {
		/*final DownloadInfo downloadInfo = new DownloadInfo();
		downloadInfo.setDownloadUrl(url);
		downloadInfo.setAutoRename(autoRename);
		downloadInfo.setAutoResume(autoResume);
		downloadInfo.setFileName(fileName);
		downloadInfo.setFileSavePath(target);
		HttpUtils http = new HttpUtils();
		http.configRequestThreadPoolSize(maxDownloadThread);
		HttpHandler<File> handler = http.download(url, target, autoResume,
				autoRename, new ManagerCallBack(downloadInfo, callback));
		downloadInfo.setHandler(handler);
		downloadInfo.setState(handler.getState());
		downloadInfoList.add(downloadInfo);
		db.saveBindingId(downloadInfo);*/
		addNewDownload_return(url, fileName, target,
				autoResume, autoRename,
				callback);
	}
	
	public DownloadInfo addNewDownload_return(String url, String fileName, String target,
                                              boolean autoResume, boolean autoRename,
                                              final RequestCallBack<File> callback) throws DbException {
		final DownloadInfo downloadInfo = new DownloadInfo();
		downloadInfo.setDownloadUrl(url);
		downloadInfo.setAutoRename(autoRename);
		downloadInfo.setAutoResume(autoResume);
		downloadInfo.setFileName(fileName);
		downloadInfo.setFileSavePath(target);
		HttpUtils http = new HttpUtils();
		http.configRequestThreadPoolSize(maxDownloadThread);
		HttpHandler<File> handler = http.download(url, target, autoResume,
				autoRename, new ManagerCallBack(downloadInfo, callback));
		downloadInfo.setHandler(handler);
		downloadInfo.setState(handler.getState());
		downloadInfoList.add(downloadInfo);
		db.saveBindingId(downloadInfo);
		
		return downloadInfo;
	}

	public boolean isAllSuccess(){
		for(DownloadInfo mDownloadInfo : downloadInfoList){
			if(mDownloadInfo.getState() != HttpHandler.State.SUCCESS){
				return false;
			}
		}
		return true;
	}

	
	public void resumeDownload(int index, final RequestCallBack<File> callback)
			throws DbException {
		final DownloadInfo downloadInfo = downloadInfoList.get(index);
		resumeDownload(downloadInfo, callback);
	}

	public void resumeDownload(DownloadInfo downloadInfo,
			final RequestCallBack<File> callback) throws DbException {
		HttpUtils http = new HttpUtils();
		http.configRequestThreadPoolSize(maxDownloadThread);
		HttpHandler<File> handler = http.download(
				downloadInfo.getDownloadUrl(), downloadInfo.getFileSavePath(),
				downloadInfo.isAutoResume(), downloadInfo.isAutoRename(),
				new ManagerCallBack(downloadInfo, callback));
		downloadInfo.setHandler(handler);
		downloadInfo.setState(handler.getState());
		db.saveOrUpdate(downloadInfo);
	}

	public void removeDownload(int index) throws DbException {
		DownloadInfo downloadInfo = downloadInfoList.get(index);
		removeDownload(downloadInfo);
	}

	public void removeDownload(DownloadInfo downloadInfo) throws DbException {
		HttpHandler<File> handler = downloadInfo.getHandler();
		if (handler != null && !handler.isCancelled()) {
			handler.cancel();
		}
		downloadInfoList.remove(downloadInfo);
		db.delete(downloadInfo);
	}

	public void stopDownload(int index) throws DbException {
		DownloadInfo downloadInfo = downloadInfoList.get(index);
		stopDownload(downloadInfo);
	}

	public void stopDownload(DownloadInfo downloadInfo) throws DbException {
		HttpHandler<File> handler = downloadInfo.getHandler();
		if (handler != null && !handler.isCancelled()) {
			handler.cancel();
		} else {
			downloadInfo.setState(HttpHandler.State.CANCELLED);
		}
		db.saveOrUpdate(downloadInfo);
	}

	@Override
	public void stopAllDownload() throws DbException {
		for (DownloadInfo downloadInfo : downloadInfoList) {
			HttpHandler<File> handler = downloadInfo.getHandler();
			if (handler != null && !handler.isCancelled()) {
				handler.cancel();
			} else {
				downloadInfo.setState(HttpHandler.State.CANCELLED);
			}
		}
		db.saveOrUpdateAll(downloadInfoList);
	}

	public void backupDownloadInfoList() throws DbException {
		for (DownloadInfo downloadInfo : downloadInfoList) {
			HttpHandler<File> handler = downloadInfo.getHandler();
			if (handler != null) {
				downloadInfo.setState(handler.getState());
			}
		}
		db.saveOrUpdateAll(downloadInfoList);
	}

	public int getMaxDownloadThread() {
		return maxDownloadThread;
	}

	public void setMaxDownloadThread(int maxDownloadThread) {
		this.maxDownloadThread = maxDownloadThread;
	}

	public class ManagerCallBack extends RequestCallBack<File> {
		private DownloadInfo downloadInfo;
		private RequestCallBack<File> baseCallBack;

		public RequestCallBack<File> getBaseCallBack() {
			return baseCallBack;
		}

		public void setBaseCallBack(RequestCallBack<File> baseCallBack) {
			this.baseCallBack = baseCallBack;
		}

		private ManagerCallBack(DownloadInfo downloadInfo,
				RequestCallBack<File> baseCallBack) {
			this.baseCallBack = baseCallBack;
			this.downloadInfo = downloadInfo;
		}

		@Override
		public Object getUserTag() {
			if (baseCallBack == null)
				return null;
			return baseCallBack.getUserTag();
		}

		@Override
		public void setUserTag(Object userTag) {
			if (baseCallBack == null)
				return;
			baseCallBack.setUserTag(userTag);
		}

		@Override
		public void onStart() {
			HttpHandler<File> handler = downloadInfo.getHandler();
			if (handler != null) {
				downloadInfo.setState(handler.getState());
			}
			try {
				db.saveOrUpdate(downloadInfo);
			} catch (DbException e) {
				LogUtil.e(e.getMessage(), e);
			}
			if (baseCallBack != null) {
				baseCallBack.onStart();
			}
		}

		@Override
		public void onCancelled() {
			HttpHandler<File> handler = downloadInfo.getHandler();
			if (handler != null) {
				downloadInfo.setState(handler.getState());
			}
			try {
				db.saveOrUpdate(downloadInfo);
			} catch (DbException e) {
				LogUtil.e(e.getMessage(), e);
			}
			if (baseCallBack != null) {
				baseCallBack.onCancelled();
			}
		}

		@Override
		public void onLoading(long total, long current, boolean isUploading) {
			HttpHandler<File> handler = downloadInfo.getHandler();
			if (handler != null) {
				downloadInfo.setState(handler.getState());
			}
			downloadInfo.setFileLength(total);
			downloadInfo.setProgress(current);
			try {
				db.saveOrUpdate(downloadInfo);
			} catch (DbException e) {
				LogUtil.e(e.getMessage(), e);
			}
			if (baseCallBack != null) {
				baseCallBack.onLoading(total, current, isUploading);
			}
		}

		@Override
		public void onSuccess(ResponseInfo<File> responseInfo) {
			HttpHandler<File> handler = downloadInfo.getHandler();
			if (handler != null) {
				downloadInfo.setState(handler.getState());
			}
			try {
				db.saveOrUpdate(downloadInfo);
			} catch (DbException e) {
				LogUtil.e(e.getMessage(), e);
			}
			if (baseCallBack != null) {
				baseCallBack.onSuccess(responseInfo);
			}
		}

		@Override
		public void onFailure(HttpException error, String msg) {
			HttpHandler<File> handler = downloadInfo.getHandler();
			if (handler != null) {
				downloadInfo.setState(handler.getState());
			}
			try {
				db.saveOrUpdate(downloadInfo);
			} catch (DbException e) {
				LogUtil.e(e.getMessage(), e);
			}
			if (baseCallBack != null) {
				baseCallBack.onFailure(error, msg);
			}
		}
	}

	private class HttpHandlerStateConverter implements
            ColumnConverter<HttpHandler.State> {

		@Override
		public HttpHandler.State getFieldValue(Cursor cursor, int index) {
			return HttpHandler.State.valueOf(cursor.getInt(index));
		}

		@Override
		public HttpHandler.State getFieldValue(String fieldStringValue) {
			if (fieldStringValue == null)
				return null;
			return HttpHandler.State.valueOf(fieldStringValue);
		}

		@Override
		public Object fieldValue2ColumnValue(HttpHandler.State fieldValue) {
			return fieldValue.value();
		}

		@Override
		public ColumnDbType getColumnDbType() {
			return ColumnDbType.INTEGER;
		}
	}
}
