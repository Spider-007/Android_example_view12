package com.htmitech.commonx.util;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

/**
 * 音效播放
 *
 */
public class SoundPoolUtils {

	private static SoundPool mSoundPool = new SoundPool(10,
			AudioManager.STREAM_SYSTEM, 5);

	/**
	 * 加载声音文件
	 * 
	 * @param ctx
	 *            当前Context
	 * @param resId
	 *            source id
	 * @return
	 */
	public static int load(Context ctx, int resId) {
		return load(ctx, resId, 1);
	}

	/**
	 * 加载声音文件
	 * 
	 * @param ctx
	 *            当前Context
	 * @param resId
	 *            source id
	 * @param priority
	 *            加载优先级
	 * @return 成功返回sound Id
	 */
	public static int load(Context ctx, int resId, int priority) {
		return mSoundPool.load(ctx, resId, priority);
	}

	/**
	 * 播放指定声音
	 * 
	 * @param resId
	 *            source id
	 */
	public static void play(int resId) {
		if (mSoundPool != null) {
			mSoundPool.play(resId, 1, 1, 1, 0, 1);
		}
	}

	/**
	 * 释放指定声音
	 * 
	 * @param resId
	 *            source id
	 */
	public static void unload(int resId) {
		if (mSoundPool != null) {
			mSoundPool.unload(resId);
		}
	}

	/**
	 * 释放声音对象
	 */
	public static void destroySoundPool() {
		if (mSoundPool != null) {
			mSoundPool.release();
		}
	}
}
