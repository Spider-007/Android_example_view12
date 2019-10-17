package com.htmitech.emportal.service;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.RemoteViews;

import com.htmitech.emportal.R;
import com.htmitech.others.FileUtil;
import com.minxing.client.ClientTabActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * =======================================================
 * 在线更新Service
 *
 * @author Administrator Tony
 *
 * =======================================================
 *
 */
public class UpdateService extends Service {

	private static final int TIMEOUT = 10 * 1000;// 超时

	private String down_url = "http://www.cxzg.com/YiYunBrowser.apk";

	private static final int DOWN_OK = 1;

	private static final int DOWN_ERROR = 0;

	private String app_name;

	private NotificationManager notificationManager;

	private Notification notification;

	private Intent updateIntent;

	private PendingIntent pendingIntent;

	private int notification_id = 0;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (intent != null) {
			app_name = intent.getStringExtra("app_name");
			down_url = intent.getStringExtra("url");
			// 创建文件
			FileUtil.createFile(app_name);
			createNotification();
			createThread();
		}
		return super.onStartCommand(intent, flags, startId);

	}

	/***
	 * 开线程下载
	 */
	public void createThread() {
		/***
		 * 更新UI
		 */
		final Handler handler = new Handler() {
			@TargetApi(Build.VERSION_CODES.KITKAT)
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
					case DOWN_OK:
//						Uri packageUri = Uri.parse("package:" + Utils.getAppProcessName(UpdateService.this));
//						Intent deleteIntent = new Intent();
//						deleteIntent.setAction(Intent.ACTION_DELETE);
//						deleteIntent.setData(packageUri);
//						startActivity(deleteIntent);
						openFile(FileUtil.updateFile);
						notificationManager.cancel(notification_id);
						// stopService();
						break;
					case DOWN_ERROR:
						notification.contentIntent = pendingIntent;
						notification.tickerText = "下载失败";
						notificationManager.notify(notification_id, notification);
//						notification.setLatestEventInfo(UpdateService.this,
//								app_name, "下载失败", pendingIntent);
//						notification = new Notification.Builder(UpdateService.this)
//								.setContentTitle(app_name)
//								.setContentText("下载失败")
//								.setContentIntent(pendingIntent)
//								.setSmallIcon(R.drawable.ic_launcher)
//								.build();
						break;

					default:
						stopService(updateIntent);
						break;
				}

			}

		};

		final Message message = new Message();

		new Thread(new Runnable() {
			@Override
			public void run() {

				try {
					long downloadSize = downloadUpdateFile(down_url,
							FileUtil.updateFile.toString());
					if (downloadSize > 0) {
						// 下载成功
						message.what = DOWN_OK;
						handler.sendMessage(message);
					}

				} catch (Exception e) {
					e.printStackTrace();
					message.what = DOWN_ERROR;
					handler.sendMessage(message);
				}

			}
		}).start();
	}

	/***
	 * 创建通知栏
	 */
	RemoteViews contentView;

	@SuppressWarnings("deprecation")
	public void createNotification() {
		notification = new Notification(R.drawable.ic_launcher, "更新新版本！",
				System.currentTimeMillis());
		// 将使用默认的声音来提醒用户
		// notification.defaults = Notification.DEFAULT_SOUND;
		notificationManager = (NotificationManager) this
				.getSystemService(NOTIFICATION_SERVICE);
		Intent mIntent = new Intent(this, ClientTabActivity.class);
		// 这里需要设置Intent.FLAG_ACTIVITY_NEW_TASK属性
		mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		PendingIntent mContentIntent = PendingIntent.getActivity(this, 0,
				mIntent, 0);
		// 这里必需要用setLatestEventInfo(上下文,标题,内容,PendingIntent)不然会报错.
		contentView = new RemoteViews(getPackageName(),
				R.layout.notification_item);
		contentView.setImageViewResource(R.id.notificationImage,R.drawable.ic_launcher);
		contentView.setTextViewText(R.id.notificationTitle, "正在下载");
		contentView.setTextViewText(R.id.notificationPercent, "0%");
		contentView.setProgressBar(R.id.notificationProgress, 100, 0, false);
		notification.contentView = contentView;
		// 这里发送通知(消息ID,通知对象)
		notificationManager.notify(notification_id, notification);
	}

	/***
	 * 下载文件
	 *
	 * @return
	 * @throws MalformedURLException
	 */
	public long downloadUpdateFile(String down_url, String file)
			throws Exception {
		int down_step = 5;// 提示step
		int totalSize;// 文件总大小
		int downloadCount = 0;// 已经下载好的大小
		int updateCount = 0;// 已经上传的文件大小
		InputStream inputStream;
		OutputStream outputStream;

		URL url = new URL(down_url);
		HttpURLConnection httpURLConnection = (HttpURLConnection) url
				.openConnection();
		httpURLConnection.setConnectTimeout(TIMEOUT);
		httpURLConnection.setReadTimeout(TIMEOUT);
		// 获取下载文件的size
		totalSize = httpURLConnection.getContentLength();
		if (httpURLConnection.getResponseCode() == 404) {
			throw new Exception("fail!");
		}
		inputStream = httpURLConnection.getInputStream();
		outputStream = new FileOutputStream(file, false);// 文件存在则覆盖掉
		byte buffer[] = new byte[1024];
		int readsize = 0;
		while ((readsize = inputStream.read(buffer)) != -1) {
			outputStream.write(buffer, 0, readsize);
			downloadCount += readsize;// 时时获取下载到的大小
			/**
			 * 每次增张5%
			 */
			if (updateCount == 0
					|| (downloadCount * 100 / totalSize - down_step) >= updateCount) {
				updateCount += down_step;
				// 改变通知栏
				// notification.setLatestEventInfo(this, "正在下载...", updateCount
				// + "%" + "", pendingIntent);
				contentView.setTextViewText(R.id.notificationPercent,
						updateCount + "%");
				contentView.setProgressBar(R.id.notificationProgress, 100,
						updateCount, false);
				// show_view
				notificationManager.notify(notification_id, notification);

			}

		}
		if (httpURLConnection != null) {
			httpURLConnection.disconnect();
		}
		inputStream.close();
		outputStream.close();

		return downloadCount;

	}

	@TargetApi(Build.VERSION_CODES.KITKAT)
	private void openFile(File file) {
		// TODO Auto-generated method stub
		String fileString = file.toString();
		if (!fileString.substring(fileString.length() - 3, fileString.length())
				.equals("apk")) {
			return;
		}

		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(android.content.Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(file),
				"application/vnd.android.package-archive");
		startActivity(intent);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
			ActivityManager mActivityManager=(ActivityManager)getSystemService(ACTIVITY_SERVICE);
			mActivityManager.clearApplicationUserData();
		}

	}
}
