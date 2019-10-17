package com.htmitech.ztcustom.zt.chinarailway;


import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.base.BaseFragmentActivity;


/**
 * 质保书搜索
 */
public class QualityBookSearchNewsActivity extends BaseFragmentActivity implements View.OnClickListener {

    private RelativeLayout rlQualitybook1;
    private RelativeLayout rlQualitybook2;
    private RelativeLayout rlQualitybook3;
    private RelativeLayout rlQualitybook4;
    private RelativeLayout rlQualitybook5;
    private RelativeLayout rlQualitybook11;
    private RelativeLayout rlQualitybook22;
    private RelativeLayout rlQualitybook33;
    private RelativeLayout rlQualitybook44;
    private RelativeLayout rlQualitybook55;
    private TextView tvBuilder;
    private TextView tvReceivegoods;
    private TextView tvStations;
    private EditText etSearchBianhao;
    private EditText etSearchCarnum;
    private static int REQUESTCODE1 = 1;
    private static int REQUESTCODE2 = 2;
    private static int REQUESTCODE3 = 3;
    private int REQUESTCODE4 = 4;
    private Button btnSearch;
    private int VISIBILITY1 = 0;
    private int VISIBILITY2 = 0;
    private String year;
    private String month;
    private ImageButton imgBack;
    private String jsdwid;
    private String shdwid;
    private String dzid;
    private ImageView imgClean1;
    private ImageView imgClean2;
    private ImageView imgClean3;
    private String jsdwname;
    private String shdwname;
    private String dzname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quality_book_search_news);
        initView();
        Intent intent = getIntent();
        year = intent.getStringExtra("year");
        month = intent.getStringExtra("month");
    }

    private void initView() {
        imgBack = (ImageButton) findViewById(R.id.ib_quality_book_back);
        rlQualitybook1 = (RelativeLayout) findViewById(R.id.rl_qualitybook1);
        rlQualitybook2 = (RelativeLayout) findViewById(R.id.rl_qualitybook2);
        rlQualitybook3 = (RelativeLayout) findViewById(R.id.rl_qualitybook3);
        rlQualitybook4 = (RelativeLayout) findViewById(R.id.rl_qualitybook4);
        rlQualitybook5 = (RelativeLayout) findViewById(R.id.rl_qualitybook5);
        rlQualitybook11 = (RelativeLayout) findViewById(R.id.rl_qualitybook1_1);
        rlQualitybook22 = (RelativeLayout) findViewById(R.id.rl_qualitybook2_2);
        rlQualitybook33 = (RelativeLayout) findViewById(R.id.rl_qualitybook3_3);
        rlQualitybook44 = (RelativeLayout) findViewById(R.id.rl_qualitybook4_4);
        rlQualitybook55 = (RelativeLayout) findViewById(R.id.rl_qualitybook5_5);
        imgClean1 = (ImageView) findViewById(R.id.img_clean1);
        imgClean2 = (ImageView) findViewById(R.id.img_clean2);
        imgClean3 = (ImageView) findViewById(R.id.img_clean3);
        tvBuilder = (TextView) findViewById(R.id.tv_builder);
        tvReceivegoods = (TextView) findViewById(R.id.tv_receivegoods);
        tvStations = (TextView) findViewById(R.id.tv_stations);
        etSearchBianhao = (EditText) findViewById(R.id.et_search_bianhao);
        etSearchCarnum = (EditText) findViewById(R.id.et_search_carnum);
        btnSearch = (Button) findViewById(R.id.btn_search);
        rlQualitybook1.setOnClickListener(this);
        rlQualitybook2.setOnClickListener(this);
        rlQualitybook3.setOnClickListener(this);
        rlQualitybook4.setOnClickListener(this);
        rlQualitybook5.setOnClickListener(this);
        imgClean1.setOnClickListener(this);
        imgClean2.setOnClickListener(this);
        imgClean3.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        etSearchCarnum.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(QualityBookSearchNewsActivity.this.getCurrentFocus()
                                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    resultIntent();
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view.getId() ==R.id.rl_qualitybook1 ){
            Intent intent1 = new Intent(QualityBookSearchNewsActivity.this, QualityBookKeyWordsSearchActivity.class);
            intent1.putExtra("builder", 1);
            intent1.putExtra("year",year);
            intent1.putExtra("month",month);
            startActivityForResult(intent1,REQUESTCODE1);
        }else if(view.getId() ==R.id.rl_qualitybook2){
            Intent intent2 = new Intent(QualityBookSearchNewsActivity.this, QualityBookKeyWordsSearchActivity.class);
            intent2.putExtra("receivegoods",2);
            intent2.putExtra("year",year);
            intent2.putExtra("month",month);
            intent2.putExtra("jsdwid",jsdwid);
            startActivityForResult(intent2,REQUESTCODE2);
        }else if(view.getId() ==R.id.rl_qualitybook3){
            Intent intent3 = new Intent(QualityBookSearchNewsActivity.this, QualityBookKeyWordsSearchActivity.class);
            intent3.putExtra("station",3);
            intent3.putExtra("year",year);
            intent3.putExtra("month",month);
            intent3.putExtra("shdwid",shdwid);
            startActivityForResult(intent3,REQUESTCODE3);
        }else if(view.getId() ==R.id.rl_qualitybook4){
            if(VISIBILITY1 == 0){
                rlQualitybook44.setVisibility(View.VISIBLE);
                VISIBILITY1 = 1;
            }else if(VISIBILITY1 == 1){
                rlQualitybook44.setVisibility(View.GONE);
                VISIBILITY1 = 0;
            }
        }else if(view.getId() ==R.id.rl_qualitybook5){
            if(VISIBILITY2 == 0){
                rlQualitybook55.setVisibility(View.VISIBLE);
                VISIBILITY2 = 1;
            }else if(VISIBILITY2 == 1){
                rlQualitybook55.setVisibility(View.GONE);
                VISIBILITY2 = 0;
            }
        }else if(view.getId() ==R.id.btn_search){
            resultIntent();
        }else if(view.getId() ==R.id.img_clean1){
            jsdwname = "";
            tvBuilder.setText(jsdwname);
            rlQualitybook11.setVisibility(View.GONE);
        }else if(view.getId() ==R.id.img_clean2){
            shdwname = "";
            tvReceivegoods.setText(shdwname);
            rlQualitybook22.setVisibility(View.GONE);
        }else if(view.getId() ==R.id.img_clean3){
            dzname = "";
            tvStations.setText(dzname);
            rlQualitybook33.setVisibility(View.GONE);
        }

    }

    private void resultIntent(){
        Intent intent4 = new Intent();
        if(jsdwname != "" && jsdwname != null){
            intent4.putExtra("jsdwid",jsdwid);
        }else{
            intent4.putExtra("jsdwid","");
        }
        if(shdwname != "" && shdwname != null){
            intent4.putExtra("shdwid",shdwid);
        }else{
            intent4.putExtra("shdwid","");
        }
        if(dzname != "" && dzname != null){
            intent4.putExtra("dzid",dzid);
        }else{
            intent4.putExtra("dzid","");
        }
        String bianhao = etSearchBianhao.getText().toString().trim();
        String carnum = etSearchCarnum.getText().toString().trim();
        intent4.putExtra("bianhao",bianhao);
        intent4.putExtra("carnum",carnum);
        setResult(REQUESTCODE4,intent4);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == 11){
            jsdwname = data.getStringExtra("jsdwname");
            jsdwid = data.getStringExtra("jsdwid");
            if(jsdwname != null){
                rlQualitybook11.setVisibility(View.VISIBLE);
                if(jsdwname != null && 13 < jsdwname.length()){
                    StringBuilder stringBuilder = new StringBuilder();
                    String substring = jsdwname.substring(0, 13);
                    stringBuilder = stringBuilder.append(substring+"...");
                    tvBuilder.setText(stringBuilder);
                }else{
                    tvBuilder.setText(jsdwname);
                }
            }else{
                rlQualitybook11.setVisibility(View.GONE);
            }


        }else if(requestCode == 2 && resultCode == 22){
            shdwid = data.getStringExtra("shdwid");
            shdwname = data.getStringExtra("shdwname");
            if(shdwname != null){
            rlQualitybook22.setVisibility(View.VISIBLE);
            if(shdwname != null && 13 < shdwname.length()){
                StringBuilder stringBuilder = new StringBuilder();
                String substring = shdwname.substring(0, 13);
                stringBuilder = stringBuilder.append(substring+"...");
                tvReceivegoods.setText(stringBuilder);
            }else{
                tvReceivegoods.setText(shdwname);
            }
            }else{
                rlQualitybook22.setVisibility(View.GONE);
            }
        }else if(requestCode == 3 && resultCode == 33){
            dzname = data.getStringExtra("dzname");
            dzid = data.getStringExtra("dzid");
            if(dzname != null){
            rlQualitybook33.setVisibility(View.VISIBLE);
            if(dzname != null && 13 < dzname.length()){
                StringBuilder stringBuilder = new StringBuilder();
                String substring = dzname.substring(0, 13);
                stringBuilder = stringBuilder.append(substring+"...");
                tvStations.setText(stringBuilder);
            }else{
                tvStations.setText(dzname);
            }
            }else{
                rlQualitybook33.setVisibility(View.GONE);
            }
        }

    }
}
