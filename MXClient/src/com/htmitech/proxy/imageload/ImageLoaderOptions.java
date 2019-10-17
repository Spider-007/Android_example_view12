package com.htmitech.proxy.imageload;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.UnitTransformation;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * Tony 对第三方图片进行重写 实体 降低耦合度
 */
public class ImageLoaderOptions {

    private ImageView viewContainer;//图片容器

    private String url = "";//图片路径

    private Integer resource;  // 图片地址本地图片的路径

    private int resourceId;//站位图

    private int errorResourceId;//图片出错站位图

    private BitmapTransformation[] bitmapTransformations ;//自定义图片样式

    private DiskCacheStrategy mDiskCacheStrategy = DiskCacheStrategy.RESULT; //磁盘缓存策略

    private boolean isSkipMemoryCache = false; //是否跳过内存缓存

    public ImageLoaderOptions(Builder mBuilder) {
        this.viewContainer = mBuilder.viewContainer;
        this.url = mBuilder.url;
        this.resource = mBuilder.resource;
        this.resourceId = mBuilder.resourceId;
        this.errorResourceId = mBuilder.errorResourceId;
        this.bitmapTransformations = mBuilder.bitmapTransformations;
        this.mDiskCacheStrategy = mBuilder.mDiskCacheStrategy;
        this.isSkipMemoryCache = mBuilder.isSkipMemoryCache;
    }

    public ImageView getViewContainer() {
        return viewContainer;
    }

    public void setViewContainer(ImageView viewContainer) {
        this.viewContainer = viewContainer;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getResource() {
        return resource;
    }

    public void setResource(Integer resource) {
        this.resource = resource;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public int getErrorResourceId() {
        return errorResourceId;
    }

    public void setErrorResourceId(int errorResourceId) {
        this.errorResourceId = errorResourceId;
    }

    public BitmapTransformation[] getBitmapTransformations() {
        return bitmapTransformations;
    }

    public void setBitmapTransformations(BitmapTransformation[] bitmapTransformations) {
        this.bitmapTransformations = bitmapTransformations;
    }

    public DiskCacheStrategy getmDiskCacheStrategy() {
        return mDiskCacheStrategy;
    }

    public void setmDiskCacheStrategy(DiskCacheStrategy mDiskCacheStrategy) {
        this.mDiskCacheStrategy = mDiskCacheStrategy;
    }

    public boolean isSkipMemoryCache() {
        return isSkipMemoryCache;
    }

    public void setIsSkipMemoryCache(boolean isSkipMemoryCache) {
        this.isSkipMemoryCache = isSkipMemoryCache;
    }

    public class Builder {
        private ImageView viewContainer;//图片容器

        private String url = "";//图片路径

        private Integer resource;  // 图片地址本地图片的路径

        private int resourceId;//站位图

        private int errorResourceId;//图片出错站位图

        private BitmapTransformation[] bitmapTransformations;//自定义图片样式

        private DiskCacheStrategy mDiskCacheStrategy = DiskCacheStrategy.RESULT; //磁盘缓存策略

        private boolean isSkipMemoryCache = false; //是否跳过内存缓存

        public Builder(@NonNull ImageView viewContainer, @NonNull String url) {
            this.viewContainer = viewContainer;
            this.url = url;
        }

        public Builder(@NonNull ImageView viewContainer, @NonNull Integer resource) {
            this.viewContainer = viewContainer;
            this.resource = resource;
        }


        public Builder placeholder(int resourceId) {
            this.resourceId = resourceId;
            return this;
        }


        public Builder error(int errorResourceId) {
            this.resourceId = errorResourceId;
            return this;
        }

        public Builder transform(BitmapTransformation... bitmapTransformations) {
            this.bitmapTransformations = bitmapTransformations;
            return this;
        }

        public Builder diskCacheStrategy(DiskCacheStrategy mDiskCacheStrategy) {
            this.mDiskCacheStrategy = mDiskCacheStrategy;
            return this;
        }

        public Builder skipMemoryCache(boolean isSkipMemoryCache) {
            this.isSkipMemoryCache = isSkipMemoryCache;
            return this;
        }

        public ImageLoaderOptions builder() {
            return new ImageLoaderOptions(this);
        }

    }


}
