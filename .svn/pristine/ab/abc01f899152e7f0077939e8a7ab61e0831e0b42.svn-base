package com.htmitech.htworkflowformpluginnew.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * Created by Administrator on 2018/5/16.
 */

public class AnimatorUtil {
    // 隐藏动画
    private ObjectAnimator mAnimatorSetHide;
    // 恢复动画
    private ObjectAnimator mAnimatorSetBack;

    public synchronized  void animateHide(final View view) {
        // 取消其他动画
        if (mAnimatorSetBack != null && mAnimatorSetBack.isRunning()) {
            mAnimatorSetBack.cancel();
        }
        if (mAnimatorSetHide != null && mAnimatorSetHide.isRunning()) {

        } else {
            mAnimatorSetHide = ObjectAnimator.ofFloat(view, "alpha", 1, 0);
            mAnimatorSetHide.setInterpolator(new AccelerateDecelerateInterpolator());
            mAnimatorSetHide.setDuration(300);
            mAnimatorSetHide.addListener(new AnimatorListenerAdapter() {
                public void onAnimationEnd(Animator animation) {
                    view.setVisibility(View.GONE);
                }
            });
            mAnimatorSetHide.start();
        }

    }

    public synchronized void animateBack(final View view) {
        // 取消其他动画
        if (mAnimatorSetHide != null && mAnimatorSetHide.isRunning()) {
            mAnimatorSetHide.cancel();
        }
        if (mAnimatorSetBack != null && mAnimatorSetBack.isRunning()) {

        } else {
            mAnimatorSetBack = ObjectAnimator.ofFloat(view, "alpha", 0, 1);
            mAnimatorSetBack.setInterpolator(new AccelerateDecelerateInterpolator());
            mAnimatorSetBack.setDuration(300);
            mAnimatorSetBack.addListener(new AnimatorListenerAdapter() {
                public void onAnimationEnd(Animator animation) {
                    view.setVisibility(View.VISIBLE);
                }
            });
            mAnimatorSetBack.start();
        }

    }

}
