package com.htmitech.emportal.ui.plugin.report;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.htmitech.emportal.R;
import com.htmitech.emportal.entity.PieChartResult;
import com.htmitech.emportal.ui.chart.TableChartDraw;

public class TableChartActivity extends Activity  {
	
	private ImageButton leftBackButton;
	private ImageButton rightMenuButton;
	private TextView timeTextView;
	private PieChartResult pieChartResult = null;
	private String title = "";
	private boolean isShare;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.supply_content_table);
		
		initViews();
	}

	private void initViews() {
		initTitle();
		tableChart();
	}

	protected void finishWithAnimation() {
		super.finish();
		overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
	}

	private void initTitle() {
		// title
		((TextView) findViewById(R.id.title_name)).setText("供应动态");

		String strTitle = getIntent().getStringExtra("title");
		if (strTitle != null && strTitle.length() > 0)
			((TextView) findViewById(R.id.title_name)).setText(strTitle);

		String strDate = getIntent().getStringExtra("datestr");
		((TextView) findViewById(R.id.supply_time_textview_pie))
				.setText(strDate);
		isShare=getIntent().getBooleanExtra("isshare", true);

		// 返回按钮
		leftBackButton = (ImageButton) findViewById(R.id.title_left_button);
		leftBackButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finishWithAnimation();
			}
		});
		// 右侧按钮
		// 下拉菜单按钮
		//TODO 添加右侧按钮监听
		rightMenuButton = (ImageButton) findViewById(R.id.title_right_image_button);
		rightMenuButton.setVisibility(View.VISIBLE);
		if(isShare){
			rightMenuButton.setVisibility(View.GONE);
		}
		
		//增加回到首页按钮监听
		rightMenuButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent= new Intent(TableChartActivity.this, CopyOfsupplyActivity.class);  
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  
				startActivity(intent);
				overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
				
			}
		});
		
		String strChartTitle = getIntent().getStringExtra("strChartTitle");
		((TextView) findViewById(R.id.supply_time_textview_charttitle))
		.setText(strChartTitle);
	}
	

	private void tableChart() {
		LinearLayout ll = ((LinearLayout) findViewById(R.id.frame_chart_table));
		TableChartDraw tableChart = new TableChartDraw(this);
		tableChart.isLineColorSwap = false;
		tableChart.setTableHead(((TextView) findViewById(R.id.title_name)).getText().toString(), 
				((TextView) findViewById(R.id.supply_time_textview_charttitle)).getText().toString());
		pieChartResult = (PieChartResult) getIntent().getSerializableExtra(PieChartActivity.PIE_DATA);
		
		if (pieChartResult.getItems().length < 1){
			//无数据
			((TextView) findViewById(R.id.supply_time_textview_charttitle)).setText("");
		}
		
		int currentSelect = getIntent().getIntExtra(PieChartActivity.SELECT_TYPE,
				PieChartActivity.TON);
		View v = tableChart.getTableChartWidthPieData(pieChartResult,currentSelect);

		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT); // 创建 Linearlayout 布局参数
		ll.removeAllViews();
		ll.addView(v, params);
	}
	
	
}
