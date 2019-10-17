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

public class TwoFragment extends Fragment {

    private ImageView iv;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one, null);
        iv = (ImageView) view.findViewById(R.id.iv);
        iv.setBackgroundResource(R.drawable.help_system_2);
        return view;
    }

}
