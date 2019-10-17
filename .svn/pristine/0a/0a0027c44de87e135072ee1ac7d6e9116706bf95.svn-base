package com.htmitech.photo;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.htmitech.addressbook.R;
import com.htmitech.api.BookInit;
import com.htmitech.app.widget.ClipImageView;
import com.htmitech.domain.OrgUser;
import com.htmitech.domain.SYS_User;
import com.htmitech.listener.CallBackRequestListener;
import com.htmitech.unit.Base64Unit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 裁剪类
 */
public class TailoringActivity extends FragmentActivity implements View.OnClickListener, CallBackRequestListener {
	private ImageView btn_daiban_person;
	private TextView tv_save;
	private ClipImageView imageView;
	private SYS_User mSYS_User;
	private ProgressBar progress_;
	private OrgUser mOrgUser;
	private String path;
	private long currentTime = 0;
	private String tempPath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_caijian);
		imageView = (ClipImageView) findViewById(R.id.src_pic);
		btn_daiban_person = (ImageView) findViewById(R.id.btn_daiban_person);
		progress_ = (ProgressBar) findViewById(R.id.progress_);
		tempPath = Environment.getExternalStorageDirectory().getAbsolutePath();//手机设置的存储位置
		tv_save = (TextView) findViewById(R.id.tv_save);
		currentTime = System.currentTimeMillis();
		path = getIntent().getStringExtra("imagePath");
		// 设置需要裁剪的图片
		ImageLoader.getInstance(10, ImageLoader.Type.LIFO).loadImage(path, imageView);
		btn_daiban_person.setOnClickListener(this);
		tv_save.setOnClickListener(this);
		BookInit.getInstance().setCallBackRequestListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_daiban_person) {
			this.finish();
		} else if (v.getId() == R.id.tv_save) {
			progress_.setVisibility(View.VISIBLE);
			writeFileByBitmap2(imageView.clip());
//			String bittoBase64 = Base64Unit.bitmapToBase64(imageView.clip());
			String bittoBase64 = tempPath+"/"+currentTime + ".png";
			if (BookInit.getInstance().getOrgUser() != null) {
				mOrgUser = BookInit.getInstance().getOrgUser();
				mOrgUser.setEfs1(bittoBase64);
				BookInit.getInstance().getmCallbackMX().callSavePeopleMessage(mOrgUser, 2, bittoBase64);
				imageView.clip().recycle();
			}

		}
	}

	public void writeFileByBitmap2(Bitmap bitmap) {

		File file = new File(tempPath);
		File imageFile = new File(file, currentTime + ".png");


		if (!file.exists()) {
			file.mkdirs();
		}
		try {
			imageFile.createNewFile();
			FileOutputStream outputStream = new FileOutputStream(imageFile);
			bitmap.compress(Bitmap.CompressFormat.PNG, 90, outputStream);
			outputStream.flush();
			outputStream.close();
		} catch (FileNotFoundException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void classBackRequstPeopleOnSuccess() {
		BookInit.getInstance().setOrgUser(mOrgUser);
		BookInit.getInstance().setBitmap(imageView.clip());
		progress_.setVisibility(View.GONE);
		Toast.makeText(this, "上传成功", Toast.LENGTH_SHORT).show();
		File file = new File(tempPath);
		File imageFile = new File(file, currentTime + ".png");
		imageFile.delete();
		this.finish();
		try {
			if (SelectPhotoActivity.instance != null) {
				SelectPhotoActivity.instance.finish();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void classBackRequstPeopleOnFail() {
//		BookInit.getInstance().setRequestUser(null);
//		BookInit.getInstance().setBitmap(null);
		progress_.setVisibility(View.GONE);
		Toast.makeText(this, "上传失败", Toast.LENGTH_SHORT).show();
		File file = new File(tempPath);
		File imageFile = new File(file, currentTime + ".png");
		imageFile.delete();
		this.finish();
	}
}
