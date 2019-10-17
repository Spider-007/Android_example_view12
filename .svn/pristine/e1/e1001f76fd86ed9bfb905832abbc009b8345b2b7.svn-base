package com.htmitech.commonx.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;

import com.htmitech.commonx.base.bitmap.BitmapCommonUtils;
import com.htmitech.commonx.base.bitmap.BitmapDisplayConfig;
import com.htmitech.commonx.base.bitmap.BitmapGlobalConfig;
import com.htmitech.commonx.base.bitmap.callback.BitmapLoadCallBack;
import com.htmitech.commonx.base.bitmap.callback.BitmapLoadFrom;
import com.htmitech.commonx.base.bitmap.callback.DefaultBitmapLoadCallBack;
import com.htmitech.commonx.base.bitmap.core.AsyncDrawable;
import com.htmitech.commonx.base.bitmap.core.BitmapSize;
import com.htmitech.commonx.base.task.PriorityAsyncTask;
import com.htmitech.commonx.base.task.PriorityExecutor;
import com.htmitech.commonx.base.task.TaskHandler;

import java.io.File;
import java.lang.ref.WeakReference;

/**
 * 图片管理接口
 *
 */
public class BitmapUtils implements TaskHandler {

    private boolean pauseTask = false;
    private boolean cancelAllTask = false;
    private final Object pauseTaskLock = new Object();

    private static Context context;
    private static BitmapGlobalConfig globalConfig;
    private BitmapDisplayConfig defaultDisplayConfig;
    private static BitmapUtils mInstance;

    /**
     * 在程序启动时 初始化配置
     *
     * @param appContext
     */
    public static void init(Context appContext, BitmapGlobalConfig bitmapGlobalConfig) {
        context = appContext;
        globalConfig = bitmapGlobalConfig;
    }

    private synchronized static void syncInit() {
        if (mInstance == null) {
            mInstance = new BitmapUtils();
        }
    }

    public synchronized static BitmapUtils instance() {
        if (null == mInstance) {
            syncInit();
        }
        return mInstance;
    }

    // ///////////////////////////////////////////// create ///////////////////////////////////////////////////
    private BitmapUtils() {
        if (context == null) {
            throw new IllegalArgumentException("context may not be null,BitmapUtils may not be init in application!");
        }

        if (globalConfig == null) {
            globalConfig = BitmapGlobalConfig.getInstance(context, null);
        }
        defaultDisplayConfig = new BitmapDisplayConfig();
    }

    /**
     * 设置默认加载中图片
     *
     * @param drawable 默认图片资源
     *
     * @return 设置完成的BitmapUtils对象
     */
    public BitmapUtils configDefaultLoadingImage(Drawable drawable) {
        defaultDisplayConfig.setLoadingDrawable(drawable);
        return this;
    }

    /**
     * 设置默认加载中图片
     *
     * @param resId 默认图片资源Id
     *
     * @return 设置完成的BitmapUtils对象
     */
    public BitmapUtils configDefaultLoadingImage(int resId) {
        defaultDisplayConfig.setLoadingDrawable(context.getResources().getDrawable(resId));
        return this;
    }

    /**
     * 设置默认加载中图片
     *
     * @param bitmap 默认图片资源
     *
     * @return 设置完成的BitmapUtils对象
     */
    public BitmapUtils configDefaultLoadingImage(Bitmap bitmap) {
        defaultDisplayConfig.setLoadingDrawable(new BitmapDrawable(context.getResources(), bitmap));
        return this;
    }

    /**
     * 设置默认加载失败图片
     *
     * @param drawable 默认图片资源
     *
     * @return 设置完成的BitmapUtils对象
     */
    public BitmapUtils configDefaultLoadFailedImage(Drawable drawable) {
        defaultDisplayConfig.setLoadFailedDrawable(drawable);
        return this;
    }

    /**
     * 设置默认加载失败图片
     *
     * @param resId 默认图片资源Id
     *
     * @return 设置完成的BitmapUtils对象
     */
    public BitmapUtils configDefaultLoadFailedImage(int resId) {
        defaultDisplayConfig.setLoadFailedDrawable(context.getResources().getDrawable(resId));
        return this;
    }

    /**
     * 设置默认加载失败图片
     *
     * @param bitmap 默认图片资源
     *
     * @return 设置完成的BitmapUtils对象
     */
    public BitmapUtils configDefaultLoadFailedImage(Bitmap bitmap) {
        defaultDisplayConfig.setLoadFailedDrawable(new BitmapDrawable(context.getResources(), bitmap));
        return this;
    }

    /**
     * 设置默认图片最大长宽
     *
     * @param maxWidth 图片最大宽度
     * @param maxHeight 图片最大高度
     *
     * @return 设置完成的BitmapUtils对象
     */
    public BitmapUtils configDefaultBitmapMaxSize(int maxWidth, int maxHeight) {
        defaultDisplayConfig.setBitmapMaxSize(new BitmapSize(maxWidth, maxHeight));
        return this;
    }

    /**
     * 设置默认图片最大长宽
     *
     * @param maxSize Bitmap对象
     *
     * @return 设置完成的BitmapUtils对象
     */
    public BitmapUtils configDefaultBitmapMaxSize(BitmapSize maxSize) {
        defaultDisplayConfig.setBitmapMaxSize(maxSize);
        return this;
    }

    /**
     * 设置默认图片加载中动画
     *
     * @param animation 动画资源
     *
     * @return 设置完成的BitmapUtils对象
     */
    public BitmapUtils configDefaultImageLoadAnimation(Animation animation) {
        defaultDisplayConfig.setAnimation(animation);
        return this;
    }

    /**
     * 设置是否允许图片自动旋转
     *
     * @param autoRotation 允许旋转true，不允许false
     *
     * @return 设置完成的BitmapUtils对象
     */
    public BitmapUtils configDefaultAutoRotation(boolean autoRotation) {
        defaultDisplayConfig.setAutoRotation(autoRotation);
        return this;
    }

    /**
     * 设置是否显示原始图片
     *
     * @param autoRotation 显示原始图片true，不显示false
     *
     * @return 设置完成的BitmapUtils对象
     */
    public BitmapUtils configDefaultShowOriginal(boolean showOriginal) {
        defaultDisplayConfig.setShowOriginal(showOriginal);
        return this;
    }

    /**
     * 为当前Bitmap对象设置自定义BitmapConfig
     *
     * @param config 自定义BitmapConfig
     *
     * @return 设置完成的BitmapUtils对象
     */
    public BitmapUtils configDefaultBitmapConfig(Bitmap.Config config) {
        defaultDisplayConfig.setBitmapConfig(config);
        return this;
    }

    /**
     * 为当前Bitmap对象设置自定义BitmapDisplayConfig
     *
     * @param config 自定义BitmapDisplayConfig
     *
     * @return 设置完成的BitmapUtils对象
     */
    public BitmapUtils configDefaultDisplayConfig(BitmapDisplayConfig displayConfig) {
        defaultDisplayConfig = displayConfig;
        return this;
    }

    /**
     * 网络加载图片
     *
     * @param container 加载对象
     * @param uri url地址
     */
    public <T extends View> void display(T container, String uri) {
        display(container, uri, null, null);
    }

    /**
     * 网络加载图片
     *
     * @param container 加载对象
     * @param uri url地址
     * @param displayConfig 自定义displayConfig
     */
    public <T extends View> void display(T container, String uri, BitmapDisplayConfig displayConfig) {
        display(container, uri, displayConfig, null);
    }

    /**
     * 网络加载图片
     *
     * @param container 加载对象
     * @param uri url地址
     * @param callBack 自定义BitmapLoadCallBack
     */
    public <T extends View> void display(T container, String uri, BitmapLoadCallBack<T> callBack) {
        display(container, uri, null, callBack);
    }

    /**
     * 网络加载图片
     *
     * @param container 加载对象
     * @param uri url地址
     * @param displayConfig 自定义displayConfig
     * @param callBack 自定义BitmapLoadCallBack
     */
    public <T extends View>  void display(T container, String uri, BitmapDisplayConfig displayConfig,
                                          BitmapLoadCallBack<T> callBack) {
        if (container == null) {
            return;
        }

        if (callBack == null) {
            callBack = new DefaultBitmapLoadCallBack<T>();
        }

        if (displayConfig == null || displayConfig == defaultDisplayConfig) {
            displayConfig = defaultDisplayConfig.cloneNew();
        }

        // Optimize Max Size
        BitmapSize size = displayConfig.getBitmapMaxSize();
        displayConfig.setBitmapMaxSize(BitmapCommonUtils.optimizeMaxSizeByView(container, size.getWidth(),
                size.getHeight()));

        container.clearAnimation();

        if (TextUtils.isEmpty(uri)) {
            callBack.onLoadFailed(container, uri, displayConfig.getLoadFailedDrawable());
            return;
        }

        // start loading
        callBack.onPreLoad(container, uri, displayConfig);

        // find bitmap from mem cache.
        Bitmap bitmap = globalConfig.getBitmapCache().getBitmapFromMemCache(uri, displayConfig);
        Bitmap bitmap1 = globalConfig.getBitmapCache().getBitmap(uri);
//        if (bitmap != null) {
//            callBack.onLoadStarted(container, uri, displayConfig);
//            callBack.onLoadCompleted(container, uri, bitmap, displayConfig, BitmapLoadFrom.MEMORY_CACHE);
//        } else
        if (!bitmapLoadTaskExist(container, uri, callBack)) {

            final BitmapLoadTask<T> loadTask = new BitmapLoadTask<T>(container, uri, displayConfig, callBack);

            // get executor
            PriorityExecutor executor = globalConfig.getBitmapLoadExecutor();
            File diskCacheFile = this.getBitmapFileFromDiskCache(uri);
            boolean diskCacheExist = diskCacheFile != null && diskCacheFile.exists();
            if (diskCacheExist && executor.isBusy()) {
                executor = globalConfig.getDiskCacheExecutor();
            }
            // set loading image
            Drawable loadingDrawable = displayConfig.getLoadingDrawable();
            if (loadingDrawable != null && loadingDrawable.getIntrinsicWidth() > 0
                    && loadingDrawable.getIntrinsicHeight() > 0) {
                callBack.setDrawable(container, new AsyncDrawable<T>(loadingDrawable, loadTask));
            }
            loadTask.setPriority(displayConfig.getPriority());
            loadTask.executeOnExecutor(executor);
        }
    }

    // ///////////////////////////////////////////// cache
    // /////////////////////////////////////////////////////////////////

    /**
     * 清除所有内存缓存和磁盘缓存
     */
    public void clearCache() {
        globalConfig.clearCache();
    }

    /**
     * 清除内存缓存
     */
    public void clearMemoryCache() {
        globalConfig.clearMemoryCache();
    }

    /**
     * 清除磁盘缓存
     */
    public void clearDiskCache() {
        globalConfig.clearDiskCache();
    }

    /**
     * 清除内存和磁盘中指定的缓存文件，
     *
     * @param uri 文件名
     */
    public void clearCache(String uri) {
        globalConfig.clearCache(uri);
    }

    /**
     * 清除内存中指定的缓存文件
     *
     * @param uri 文件名
     */
    public void clearMemoryCache(String uri) {
        globalConfig.clearMemoryCache(uri);
    }

    /**
     * 清除磁盘中指定的缓存文件
     *
     * @param uri 文件名
     */
    public void clearDiskCache(String uri) {
        globalConfig.clearDiskCache(uri);
    }

    /**
     * 刷新内存和磁盘缓存
     */
    public void flushCache() {
        globalConfig.flushCache();
    }

    /**
     * 关闭内存和磁盘缓存
     */
    public void closeCache() {
        globalConfig.closeCache();
    }

    /**
     * 从磁盘中获取指定缓存文件
     *
     * @param uri 指定文件名
     */
    public File getBitmapFileFromDiskCache(String uri) {
        return globalConfig.getBitmapCache().getBitmapFileFromDiskCache(uri);
    }

    /**
     * 从内存中获取指定缓存文件
     *
     * @param uri 指定文件名
     */
    public Bitmap getBitmapFromMemCache(String uri, BitmapDisplayConfig config) {
        if (config == null) {
            config = defaultDisplayConfig;
        }
        return globalConfig.getBitmapCache().getBitmapFromMemCache(uri, config);
    }

    // //////////////////////////////////////// tasks
    // //////////////////////////////////////////////////////////////////////

    /**
     * 任务支持下载暂停
     */
    @Override
    public boolean supportPause() {
        return true;
    }

    /**
     * 任务支持下载中断
     */
    @Override
    public boolean supportResume() {
        return true;
    }

    /**
     * 任务支持下载取消
     */
    @Override
    public boolean supportCancel() {
        return true;
    }

    /**
     * 任务暂停
     */
    @Override
    public void pause() {
        pauseTask = true;
        flushCache();
    }

    /**
     * 任务恢复
     */
    @Override
    public void resume() {
        pauseTask = false;
        synchronized (pauseTaskLock) {
            pauseTaskLock.notifyAll();
        }
    }

    /**
     * 任务取消
     */
    @Override
    public void cancel() {
        pauseTask = true;
        cancelAllTask = true;
        synchronized (pauseTaskLock) {
            pauseTaskLock.notifyAll();
        }
    }

    /**
     * 任务是否已经暂停
     */
    @Override
    public boolean isPaused() {
        return pauseTask;
    }

    /**
     * 任务是否已经取消
     */
    @Override
    public boolean isCancelled() {
        return cancelAllTask;
    }

    // /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 获取当前任务
     *
     * @param container 当前下载及显示对象
     * @param callBack BitmapLoadCallBack
     *
     * @return 当前对象所持有的任务
     */
    @SuppressWarnings("unchecked")
    private static <T extends View> BitmapLoadTask<T> getBitmapTaskFromContainer(T container,
                                                                                 BitmapLoadCallBack<T> callBack) {
        if (container != null) {
            final Drawable drawable = callBack.getDrawable(container);
            if (drawable instanceof AsyncDrawable) {
                final AsyncDrawable<T> asyncDrawable = (AsyncDrawable<T>) drawable;
                return asyncDrawable.getBitmapWorkerTask();
            }
        }
        return null;
    }

    /**
     * 当前对象是否持有任务
     *
     * @param container 当前下载及显示对象
     * @param uri 文件名
     * @param callBack BitmapLoadCallBack
     *
     * @return 当前对象所持有的任务
     */
    private static <T extends View> boolean bitmapLoadTaskExist(T container, String uri, BitmapLoadCallBack<T> callBack) {
        final BitmapLoadTask<T> oldLoadTask = getBitmapTaskFromContainer(container, callBack);

        if (oldLoadTask != null) {
            final String oldUrl = oldLoadTask.uri;
            if (TextUtils.isEmpty(oldUrl) || !oldUrl.equals(uri)) {
                oldLoadTask.cancel(true);
            } else {
                return true;
            }
        }
        return false;
    }

    public class BitmapLoadTask<T extends View> extends PriorityAsyncTask<Object, Object, Bitmap> {
        private final String uri;
        private final WeakReference<T> containerReference;
        private final BitmapLoadCallBack<T> callBack;
        private final BitmapDisplayConfig displayConfig;

        private BitmapLoadFrom from = BitmapLoadFrom.DISK_CACHE;

        public BitmapLoadTask(T container, String uri, BitmapDisplayConfig config, BitmapLoadCallBack<T> callBack) {
            if (container == null || uri == null || config == null || callBack == null) {
                throw new IllegalArgumentException("args may not be null");
            }

            this.containerReference = new WeakReference<T>(container);
            this.callBack = callBack;
            this.uri = uri;
            this.displayConfig = config;
        }

        @Override
        protected Bitmap doInBackground(Object...params) {

            synchronized (pauseTaskLock) {
                while (pauseTask && !this.isCancelled()) {
                    try {
                        pauseTaskLock.wait();
                        if (cancelAllTask) {
                            return null;
                        }
                    } catch (Throwable e) {
                    }
                }
            }

            Bitmap bitmap = null;

            // get cache from disk cache
            if (!this.isCancelled() && this.getTargetContainer() != null) {
                this.publishProgress(PROGRESS_LOAD_STARTED);
                bitmap = globalConfig.getBitmapCache().getBitmapFromDiskCache(uri, displayConfig);
            }

            // download image
            if (bitmap == null && !this.isCancelled() && this.getTargetContainer() != null) {
                bitmap = globalConfig.getBitmapCache().downloadBitmap(uri, displayConfig, this);
                from = BitmapLoadFrom.URI;
            }

            return bitmap;
        }

        public void updateProgress(long total, long current) {
            this.publishProgress(PROGRESS_LOADING, total, current);
        }

        private static final int PROGRESS_LOAD_STARTED = 0;
        private static final int PROGRESS_LOADING = 1;

        @Override
        protected void onProgressUpdate(Object...values) {
            if (values == null || values.length == 0)
                return;

            final T container = this.getTargetContainer();
            if (container == null)
                return;

            switch ((Integer) values[0]) {
                case PROGRESS_LOAD_STARTED:
                    callBack.onLoadStarted(container, uri, displayConfig);
                    break;
                case PROGRESS_LOADING:
                    if (values.length != 3)
                        return;
                    callBack.onLoading(container, uri, displayConfig, (Long) values[1], (Long) values[2]);
                    break;
                default:
                    break;
            }
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            final T container = this.getTargetContainer();
            if (container != null) {
                if (bitmap != null) {
                    callBack.onLoadCompleted(container, this.uri, bitmap, displayConfig, from);
                } else {
                    callBack.onLoadFailed(container, this.uri, displayConfig.getLoadFailedDrawable());
                }
            }
        }

        @Override
        protected void onCancelled(Bitmap bitmap) {
            synchronized (pauseTaskLock) {
                pauseTaskLock.notifyAll();
            }
        }

        public T getTargetContainer() {
            return containerReference.get();

            // final T container = containerReference.get();
            // final BitmapLoadTask<T> bitmapWorkerTask = getBitmapTaskFromContainer(container, callBack);
            //
            // if (this == bitmapWorkerTask) {
            // return container;
            // }
            //
            // return null;
        }
    }
}
