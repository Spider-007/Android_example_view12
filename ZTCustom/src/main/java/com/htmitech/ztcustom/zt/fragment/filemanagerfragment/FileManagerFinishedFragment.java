package com.htmitech.ztcustom.zt.fragment.filemanagerfragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.adapter.FileManagerAdapter;
import com.htmitech.ztcustom.zt.base.BaseFragment;
import com.htmitech.ztcustom.zt.db.QualityObjectionDao;
import com.htmitech.ztcustom.zt.domain.FileManagerEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FileManagerFinishedFragment extends BaseFragment {


    private ListView listView;
    private List<FileManagerEntity> list;
    private FileManagerAdapter adapter;
    private boolean isFristShow = true;

    public FileManagerFinishedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_file_manager_finished, container, false);
    }

    @Override
    protected void initView() {
        listView = (ListView) getView().findViewById(R.id.lv_file_manager_finished);
    }

    @Override
    protected void initData() {
        Log.e("FILE", "FINISHED");
        if (list != null) {
            list.clear();
        } else {
            list = new ArrayList<FileManagerEntity>();
            adapter = new FileManagerAdapter(getActivity(), list, null);
            listView.setAdapter(adapter);
        }
        list.addAll(new QualityObjectionDao(getActivity()).getUploadFinishedFiles());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void lazyLoad() {
        if (isFristShow) {
            isFristShow = false;
        }
        if (!isFristShow) {
            initData();
        }
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }
}
