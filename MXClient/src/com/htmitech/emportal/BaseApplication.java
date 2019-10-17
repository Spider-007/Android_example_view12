package com.htmitech.emportal;

import android.content.res.AssetManager;
import android.support.multidex.MultiDexApplication;
import android.view.LayoutInflater;

public abstract class BaseApplication extends MultiDexApplication {

	private static com.htmitech.emportal.BaseApplication mInstance = null;

	@Override
	public void onCreate() {
		super.onCreate();
		if (mInstance == null) {
			mInstance = this;
		}
		init();
	}

	/**
	 * 获取BaseApplication对象。
	 * 
	 * @return Context
	 */
	public static com.htmitech.emportal.BaseApplication getInstance() {
		return mInstance;
	}

	/**
	 * 获取String通过id。
	 * 
	 * @param id
	 * @return String
	 */
	public static String getStringById(int id) {
		return mInstance.getString(id);
	}

	/**
	 * 获取AssetManager。
	 * 
	 * @return AssetManager
	 */
	public static AssetManager getAssetManager() {
		return mInstance.getAssets();
	}

	/**
	 * 获取LayoutInflater。
	 * 
	 * @return LayoutInflater
	 */
	public static LayoutInflater getLayoutInflater() {
		return LayoutInflater.from(mInstance);
	}

	/**
	 * init方法，用于初始化数据。
	 */
	public abstract void init();


}
