package com.htmitech.emportal.ui.widget;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.htmitech.emportal.HtmitechApplication;
import com.htmitech.emportal.R;

/**
 * 
 * 
 */
public class ToastInfo {

    private static ToastInfo instance;

    private Toast toast;

    private View view;

    public void setText(String s) {
        if (view != null) {
            TextView info = (TextView) view.findViewById(R.id.layout_toast_content);
            info.setText(s);
        } else {
            toast.setText(s);
        }
    }

    public void setText(int id) {
        if (view != null) {
            TextView info = (TextView) view.findViewById(R.id.layout_toast_content);
            info.setText(id);
        } else {
            toast.setText(id);
        }
    }

    public void show(int duration) {
        if (toast.getView().isShown()) {
            toast.cancel();
        }
        toast.setDuration(duration);
        toast.show();
    }

    public void cancel() {
        if (toast.getView().isShown()) {
            toast.cancel();
        }
    }

    public void setGravity(int gravity, int xOffset, int yOffset) {
        if (toast != null) {
            toast.setGravity(gravity, xOffset, yOffset);
        }
    }

    public void setView(LayoutInflater inflater, int drawableIcon, int toastInfo) {
        if (toast != null) {
            view = inflater.inflate(R.layout.layout_toast, null);
            ImageView icon = (ImageView) view.findViewById(R.id.layout_toast_icon);
            icon.setImageResource(drawableIcon);
            TextView info = (TextView) view.findViewById(R.id.layout_toast_content);
            info.setText(toastInfo);
            toast.setView(view);
        }
    }

    public void setView(LayoutInflater inflater, BitmapDrawable drawableIcon, String toastInfo) {
        if (toast != null) {
            view = inflater.inflate(R.layout.layout_toast, null);
            ImageView icon = (ImageView) view.findViewById(R.id.layout_toast_icon);
            icon.setImageDrawable(drawableIcon);
            TextView info = (TextView) view.findViewById(R.id.layout_toast_content);
            info.setText(toastInfo);
            toast.setView(view);
        }
    }

    public void setView(LayoutInflater inflater, int drawableIcon, String toastInfo) {
        if (toast != null) {
            view = inflater.inflate(R.layout.layout_toast, null);
            ImageView icon = (ImageView) view.findViewById(R.id.layout_toast_icon);
            icon.setImageResource(drawableIcon);
            TextView info = (TextView) view.findViewById(R.id.layout_toast_content);
            info.setText(toastInfo);
            toast.setView(view);
        }
    }

    public void setToastText(String toastInfo) {
        if (toast != null) {
            toast.setText(toastInfo);
        }
    }

    public void setView(LayoutInflater inflater, BitmapDrawable drawableIcon, int toastInfo) {
        if (toast != null) {
            view = inflater.inflate(R.layout.layout_toast, null);
            toast.setView(view);
        }
    }

    /*****************************************************************/
    private ToastInfo(Context context) {
        instance = this;
        toast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
    }

    public static ToastInfo getInstance(Context appContext) {
        if (instance == null) {
            synchronized (ToastInfo.class) {
                if (instance == null) {
                    if(appContext != null){
                        new ToastInfo(appContext.getApplicationContext());
                    }else{
                        new ToastInfo(HtmitechApplication.getInstance());
                    }
                }
            }
        }
        return instance;
    }
    /*****************************************************************/
}
