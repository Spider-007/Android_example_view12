package com.example.archivermodule;

import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;;

public class ArchiverModuleActivity extends cn.feng.skin.manager.base.BaseFragmentActivity {
    private ArchiverFragment mArchiverFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.archiver_prefix_activity_main_arch);
        mArchiverFragment = new ArchiverFragment();
        String path = getIntent().getStringExtra("path");
        String name = getIntent().getStringExtra("name");

        if (TextUtils.isEmpty(path)) {
            path = Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        if(TextUtils.isEmpty(name)){
            name = "文件管理器";
        }
        Bundle bundle = new Bundle();
        bundle.putString("path", path);
        bundle.putString("name", name);

        mArchiverFragment.setArguments(bundle);
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.ll_content, mArchiverFragment);
        transaction.commitAllowingStateLoss();
    }
}
