package com.htmitech.ztcustom.zt.fragment.filemanagerfragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;


import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.adapter.FileManagerAdapter;
import com.htmitech.ztcustom.zt.base.BaseFragment;
import com.htmitech.ztcustom.zt.db.QualityObjectionDao;
import com.htmitech.ztcustom.zt.domain.FileManagerEntity;
import com.htmitech.ztcustom.zt.interfaces.FileManagerUploadCallBack;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FileManagerUploadFragment extends BaseFragment implements FileManagerUploadCallBack {


    private ListView listView;
    private List<FileManagerEntity> list;
    private FileManagerAdapter adapter;

    public FileManagerUploadFragment() {
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
        return inflater.inflate(R.layout.fragment_file_manager_upload, container, false);
    }


    @Override
    protected void initView() {
        listView = (ListView) getView().findViewById(R.id.lv_file_manager_upload);
    }

    @Override
    protected void initData() {
        Log.e("FILE", "UPLOAD");
        if (list != null) {
            list.clear();
        } else {
            list = new ArrayList<FileManagerEntity>();
            adapter = new FileManagerAdapter(getActivity(), list, this);
            listView.setAdapter(adapter);
        }
        list.addAll(new QualityObjectionDao(getActivity()).getNeedUploadFiles());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void lazyLoad() {

    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void uploadFileSuccess() {
        Toast.makeText(getActivity(), "文件提报成功", Toast.LENGTH_SHORT).show();
        initData();
    }
}
