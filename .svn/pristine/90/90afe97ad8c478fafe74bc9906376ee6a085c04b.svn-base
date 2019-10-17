package widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.basewidgetlibrary.R;


public class LoadingView extends RelativeLayout {
	private ImageView mLoadingProgressView = null;
	private ImageView mDefaultImageView = null;
	private TextView mLoadingTextView = null;
	private AnimationDrawable mLoadingAnimationDrawable = null;
	private View mBgView;

	public LoadingView(Context context) {
		super(context);
		init();
	}

	public LoadingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public LoadingView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		LayoutInflater inflater = (LayoutInflater) getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		ViewGroup viewGroup = (ViewGroup) inflater.inflate(
				R.layout.layout_loading, this, true);
		mBgView = viewGroup.findViewById(R.id.empty_view);
		mDefaultImageView = (ImageView) viewGroup
				.findViewById(R.id.imageview_loadingview);
		mLoadingProgressView = (ImageView) viewGroup
				.findViewById(R.id.imageview_loadingview_progress_bar);
		mLoadingTextView = (TextView) viewGroup
				.findViewById(R.id.textview_loadingview);
		mLoadingTextView.setText(R.string.loading);
		mLoadingProgressView.setImageResource(R.drawable.refresh_loading);
		mLoadingAnimationDrawable = (AnimationDrawable) mLoadingProgressView
				.getDrawable();
	}

	public void setBgColor(int color) {
		if (mBgView != null) {
			mBgView.setBackgroundColor(color);
		}
	}

	public void setLoadingText(String text) {
		mLoadingTextView.setText(text);
	}

	public void setLoadingTextVisable(int visibility) {
		mLoadingTextView.setVisibility(visibility);
	}

	public void setDefaultImageViewVisable(int visibility) {
		mDefaultImageView.setVisibility(visibility);
	}

	public void startLoading() {
		if (mLoadingProgressView.getVisibility() != View.VISIBLE) {
			mLoadingProgressView.setVisibility(View.VISIBLE);
		}
		mLoadingTextView.setText(R.string.loading);
		setVisibility(View.VISIBLE);
		mLoadingAnimationDrawable.start();
	}

	public void stopLoading() {
		if (mLoadingProgressView.getVisibility() != View.GONE) {
			mLoadingProgressView.setVisibility(View.GONE);
		}
		setVisibility(View.GONE);
		mLoadingAnimationDrawable.stop();
	}

	public void onError(String errorString) {
		if (TextUtils.isEmpty(errorString)) {
			mLoadingTextView.setText(R.string.empty_prompt_text_view);
		} else {
			mLoadingTextView.setText(errorString);
		}
		mLoadingAnimationDrawable.stop();
		mLoadingProgressView.setVisibility(View.GONE);
	}

	public void onError() {
		// 使用默认文本
		onError(null);
	}
}
