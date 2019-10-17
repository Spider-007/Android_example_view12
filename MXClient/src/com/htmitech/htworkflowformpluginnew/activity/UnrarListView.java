package com.htmitech.htworkflowformpluginnew.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.archivermodule.adapter.UnrarAdapter;
import com.example.archivermodule.archiver.ArchiverManager;
import com.example.archivermodule.archiver.IArchiverListener;
import com.htmitech.base.BaseFragmentActivity;
import com.htmitech.commonx.pulltorefresh.library.PullToRefreshBase;
import com.htmitech.commonx.pulltorefresh.library.PullToRefreshListView;
import com.htmitech.emportal.HtmitechApplication;
import com.htmitech.emportal.R;
import com.htmitech.emportal.common.CommonFileType;
import com.htmitech.emportal.ui.widget.ToastInfo;
import com.htmitech.proxy.activity.GeneralFileViewActivity;
import com.htmitech.proxy.util.HTActivityUnit;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018-6-6.
 */

public class UnrarListView extends BaseFragmentActivity {
    private PullToRefreshListView lvUnRar;
    private String destpath= Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"htmitech/emportal/25348506440962318/23/25348506440962849/HZ28d8a963baffe10163d31786de3aa3/rar"+File.separator;
    private String path;
    private ImageButton leftButton;
    private TextView title_name;
    private View system_title;
    private UnrarAdapter mUnrarAdapter;
    private ArrayList<TepmPath> paths;
    public class TepmPath{
        public TepmPath(String path,String name){
            this.path = path;
            this.name = name;
        }
        public String path;
        public String name;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_attachment);
        path = getIntent().getStringExtra("path");
        String name = getIntent().getStringExtra("name");
        lvUnRar = (PullToRefreshListView) findViewById(R.id.listview_attachment);

        leftButton = (ImageButton) findViewById(R.id.title_left_button);
        title_name = (TextView) findViewById(R.id.title_name);
        system_title = findViewById(R.id.system_title);
        system_title.setVisibility(View.VISIBLE);
        title_name.setText(""+name.substring(0,name.lastIndexOf(".")));
        lvUnRar.setMode(PullToRefreshBase.Mode.DISABLED);
        paths = new ArrayList<>();
        paths.add(new TepmPath(path,name.substring(0,name.lastIndexOf("."))));
        lvUnRar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String path = mUnrarAdapter.getFile(position).getAbsolutePath();
                String type = path.substring(path.lastIndexOf(".")+1);
                if(mUnrarAdapter.getFile(position).isFile()){
                    if(type.contains("pdf") || type.contains("doc") || type.contains("xls")){
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("path", path);
                        map.put("name",  mUnrarAdapter.getFile(position).getName());
                        HTActivityUnit.switchTo(UnrarListView.this, GeneralFileViewActivity.class, map);
                    }else{
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.fromFile(mUnrarAdapter.getFile(position)),
                                getFileType(type));
                        try {
                            startActivity(intent);
                        } catch (Exception e) {
                            ToastInfo toastInfo = ToastInfo
                                    .getInstance(HtmitechApplication.instance());
                            toastInfo.setText("系统无法打开文件！请下载相关辅助软件！");
                            toastInfo.show(Toast.LENGTH_SHORT);
                        }
                    }
                }else{
                    String name = mUnrarAdapter.getFile(position).getName();
                    paths.add(new TepmPath(path,name));
                    title_name.setText(name);
                    UnrarListView.this.path = path;
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
                    UnrarListView.this.finish();
                }

            }
        });

        mUnrarAdapter = new UnrarAdapter(this,getFiles(path));
        if(!TextUtils.isEmpty(path)){
            lvUnRar.setAdapter(mUnrarAdapter);
        }





    }

    public String getFileType(String type) {
        String aimTypeString = "";

        for (int i = 0; i < CommonFileType.MIME_MapTable.length; i++) { // MIME_MapTable??在这里你一定有疑问，这个MIME_MapTable是什么？
            if (type.equals(CommonFileType.MIME_MapTable[i][0])) {
                aimTypeString = CommonFileType.MIME_MapTable[i][1];
                break;
            }

        }
        return aimTypeString;
    }

    public File[] getFiles(String path){
        File file=new File(path);
        File[] files=file.listFiles();

        try {
            for(File file1 : files){
                String paths = file1.getAbsolutePath();
                if(paths.substring(paths.lastIndexOf(".") + 1).contains("rar") || paths.substring(paths.lastIndexOf(".") + 1).contains("zip")){
                    unZipRar(paths.substring(paths.lastIndexOf(".") + 1),paths);
                }
            }

            files=file.listFiles();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return files;
    }


    public void unZipRar(String type, final String file){

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
//        if (type.contains("rar")) {
//
//
//            ArchiverManager.getInstance().doUnArchiver(file, path, "password", new IArchiverListener() {
//                @Override
//                public void onStartArchiver() {
//                    showDialog();
//                }
//
//                @Override
//                public void onProgressArchiver(int current, int total) {
//
//                }
//
//                @Override
//                public void onEndArchiver() {
//                    dismissDialog();
//                    File file1 = new File(file);
//                    file1.delete();
//                }
//            });
//        } else {
//            File srcFile = new File(file);
//            try {
//                CompressUtil.unzip(srcFile, path,
//                        "password");
//
//            } catch (ZipException e) {
//                e.printStackTrace();
//            }
//        }
    }
}
