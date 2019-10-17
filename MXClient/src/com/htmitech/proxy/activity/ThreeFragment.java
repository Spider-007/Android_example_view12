package com.htmitech.proxy.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.htmitech.emportal.R;

/**
 * Created by Administrator on 2019/9/24 0024.
 */

public class ThreeFragment extends Fragment {

    private ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one, null);
        imageView = (ImageView) view.findViewById(R.id.iv);
        imageView.setBackgroundResource(R.drawable.help_system_3);
        return view;
    }

}

