package com.example.archivermodule;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.archivermodule.adapter.UnrarAdapter;
import com.example.archivermodule.archiver.ArchiverManager;
import com.example.archivermodule.archiver.IArchiverListener;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import htmitech.com.componentlibrary.base.MyBaseFragment;
import htmitech.com.componentlibrary.unit.HTActivityUnit;

/**
 * Created by Administrator on 2018-6-11.
 */

public class ArchiverFragment extends MyBaseFragment {
    private ListView listview_attachment;
    private String path;
    private UnrarAdapter mUnrarAdapter;
    private ImageButton leftButton;
    private TextView title_name;
    private View system_title;

    public class TepmPath{
        public TepmPath(String path,String name){
            this.path = path;
            this.name = name;
        }
        public String path;
        public String name;
    }

    private ArrayList<TepmPath> paths;
    @Override
    protected int getLayoutId() {
        return R.layout.archiver_prefix_fragment_archiver;
    }

    @Override
    protected void initViews() {
        listview_attachment = (ListView) findViewById(R.id.listview_attachment);
        title_name = (TextView) findViewById(R.id.title_name);
        leftButton = (ImageButton) findViewById(R.id.title_left_button);
        system_title = findViewById(R.id.system_title);
        system_title.setVisibility(View.VISIBLE);
        initData();
    }

    public void initData(){
//        listview_attachment
        paths = new ArrayList<>();

        Bundle mBunlde = getArguments();
        path = mBunlde.getString("path");
        String name = mBunlde.getString("name");
        int lastIndex = name.lastIndexOf(".");
        if(lastIndex == -1){
            title_name.setText(name);
            paths.add(new TepmPath(path,name));
        }else{
            title_name.setText(name.substring(0,name.lastIndexOf(".")));
            paths.add(new TepmPath(path,name.substring(0,name.lastIndexOf("."))));
        }


        mUnrarAdapter = new UnrarAdapter(getActivity(),getFiles(path));

        listview_attachment.setAdapter(mUnrarAdapter);
        listview_attachment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String path = mUnrarAdapter.getFile(position).getAbsolutePath();
                String type = path.substring(path.lastIndexOf(".")+1);
                if(mUnrarAdapter.getFile(position).isFile()){
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("path", path);
                    map.put("name",  mUnrarAdapter.getFile(position).getName());
                    try {
                        HTActivityUnit.switchTo(getActivity(), "com.htmitech.proxy.activity.GeneralFileViewActivity", map);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else{
                    String name = mUnrarAdapter.getFile(position).getName();
                    paths.add(new TepmPath(path,name));
                    title_name.setText(name);
                    ArchiverFragment.this.path = path;
                    mUnrarAdapter.setData(getFiles(path));
                }

            }
        });

        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(paths.size() > 1){
                    paths.remove(paths.size() - 1);
                    mUnrarAdapter.setData(getFiles(paths.get(paths.size() - 1).path));
                    title_name.setText(paths.get(paths.size() - 1).name);
                }else{
                    getActivity().finish();
                }

            }
        });

    }

    public File[] getFiles(String path){
        File file=new File(path);
        File[] files=file.listFiles();

        for(File file1 : files){
            String paths = file1.getAbsolutePath();
            if(paths.substring(paths.lastIndexOf(".") + 1).contains("rar") || paths.substring(paths.lastIndexOf(".") + 1).contains("zip")){
                unZipRar(paths.substring(paths.lastIndexOf(".") + 1),paths);
            }
        }

        files=file.listFiles();

        return files;
    }

    public void unZipRar(String type, final String file) {

        ArchiverManager.getInstance().doUnArchiver(file, path, "password", new IArchiverListener() {
            @Override
            public void onStartArchiver() {
                showDialog();
            }

            @Override
            public void onProgressArchiver(int current, int total) {

            }

            @Override
            public void onEndArchiver() {
                dismissDialog();
                File file1 = new File(file);
                file1.delete();
                mUnrarAdapter.setData(getFiles(path));
            }
        });
    }
}
