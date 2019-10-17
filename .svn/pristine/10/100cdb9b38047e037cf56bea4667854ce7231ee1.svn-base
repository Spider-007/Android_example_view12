package com.htmitech.emportal.ui.chart;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.htmitech.emportal.R;
import com.htmitech.emportal.entity.TableChartResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class ListChartActivity extends Activity {

	private ImageButton leftBackButton;
	private TableChartResult tableData;
	View view = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_table_chart);
		initTitle();
		tableData = (TableChartResult) getIntent().getSerializableExtra("Table_Data");
		if ( getIntent().getIntExtra("Type_Id", 0) == 3) {
			//view = drawTable(tableData);
			TableChartDraw tableChart = new TableChartDraw(this);
			tableChart.isLineColorSwap = true;
			view = tableChart.getTableChart(tableData);
		}
		if (view != null)
			((ViewGroup)findViewById(R.id.chart_layout)).addView(view);
	}
	
	private int getItemWidth(int count) {
		   DisplayMetrics dm = getResources().getDisplayMetrics();		   
		   int scrWidth = (int) (dm.widthPixels / count); 				   		
		   return scrWidth;
	}
	
	/*private View drawTable(TableChartResult tableData) {
		ArrayList<String> keyList =new ArrayList<String>();	//转换json数据
		LinearLayout line = new LinearLayout(this); //列xl
		
		line.setOrientation(LinearLayout.VERTICAL);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		line.setLayoutParams(params);
		
		LinearLayout rowHead = new LinearLayout(this);
		rowHead.setOrientation(LinearLayout.HORIZONTAL);
		rowHead.setLayoutParams(params);	
		
		String json = tableData.getListDataSet().getTable().getJSONObject(0).toJSONString();
		keyList = jsonToArray(json);

		//绘制表头
		for (int i = keyList.size() - 1; i >= 0; i--) {
			LinearLayout itemHead = (LinearLayout) getLayoutInflater().inflate(R.layout.table_item, null);
			itemHead.setLayoutParams(new LayoutParams(getItemWidth(keyList.size()), LayoutParams.WRAP_CONTENT));
			//itemHead.setBackgroundColor(color);
			if (i == keyList.size() - 1) {
				itemHead.setBackgroundResource(R.drawable.item_left_line_color);
			} else {
				itemHead.setBackgroundResource(R.drawable.item_line_color);
			}
			
			TextView keyText = (TextView) itemHead.findViewById(R.id.form_text);
			keyText.setText(keyList.get(i));
			rowHead.addView(itemHead);
		}
		line.addView(rowHead);
		
		boolean lineColorFlag  = false;
		
		//绘制内容
		for (int x = 0; x < tableData.getListDataSet().getTable().size(); x++) {
			LinearLayout row = new LinearLayout(this);
			
			row.setOrientation(LinearLayout.HORIZONTAL);
			row.setLayoutParams(params);
			
			//把fastJson 字符串转换成普通json 再转成key集合 再存入 arr 反向取出 - 序对
			keyList.clear();
			String KeyJson = tableData.getListDataSet().getTable().getJSONObject(x).toJSONString();
			keyList = jsonToArray(KeyJson);
			for (int y = keyList.size() - 1; y >= 0; y--) {
				LinearLayout item = (LinearLayout) getLayoutInflater().inflate(R.layout.table_item, null);
				item.setLayoutParams(new LayoutParams(getItemWidth(keyList.size()), LayoutParams.WRAP_CONTENT));
				if (lineColorFlag)
					item.setBackgroundResource(R.drawable.item_bottom_right_line_color);
				if (y == keyList.size() - 1) {
					if (lineColorFlag)
						item.setBackgroundResource(R.drawable.item_light_blue_line_);
					else 
						item.setBackgroundResource(R.drawable.item_left_line);
				}
				TextView keyText = (TextView) item.findViewById(R.id.form_text);
				keyText.setText( tableData.getListDataSet().getTable().getJSONObject(x).getString(keyList.get(y)) );
				row.addView(item);
				
			}
			lineColorFlag = !lineColorFlag;
			line.addView(row);
		}
		return line;
	}*/

	private ArrayList<String> jsonToArray(String json) {
		ArrayList<String> list = new ArrayList<String>();
		JSONObject tablejson = null;
		try {
			tablejson = new JSONObject(json);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Iterator<String> it = tablejson.keys();
		list.clear();
		while (it.hasNext()) {  
			  String str = it.next();
			  list.add(str);
		}
		return list;
	}
	
	protected void finishWithAnimation() {
		super.finish();
		overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
	}

	private void initTitle() {
		// title
		((TextView) findViewById(R.id.title_name)).setText("钢厂详情");

		// 返回按钮
		leftBackButton = (ImageButton) findViewById(R.id.title_left_button);
		leftBackButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finishWithAnimation();
			}
		});
	}
}
