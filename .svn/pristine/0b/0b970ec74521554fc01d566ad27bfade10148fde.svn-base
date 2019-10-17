package com.htmitech.emportal.ui.chart;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.htmitech.emportal.R;
import com.htmitech.emportal.entity.PieChartResult;
import com.htmitech.emportal.entity.TableChartResult;
import com.htmitech.emportal.ui.chart.listener.ScrollViewListener;
import com.htmitech.emportal.ui.chart.listener.ScrollViewListenerX;
import com.htmitech.emportal.ui.chart.view.ObservableScrollView;
import com.htmitech.emportal.ui.chart.view.ObservableScrollViewX;
import com.htmitech.emportal.ui.plugin.report.PieChartActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;

public class TableChartDraw extends Activity implements ScrollViewListener,
		ScrollViewListenerX {

	private Context mContext;
	HorizontalScrollView x_scroll;
	ScrollView y_scroll;
	ObservableScrollView center_scroll_y;
	ObservableScrollViewX center_scroll_x;

	public boolean isLineColorSwap = false;

	private String tableHeadItemArray[] = { "项目类型", "需求量", "占比" };

	public TableChartDraw(Context mContext) {
		this.mContext = mContext;
	}

	public TableChartDraw(int mContext) {

	}

	public void setTableHead(String demionstr, String desc) {
		tableHeadItemArray[1] = demionstr;

		if (desc != null && !desc.isEmpty()) {
			String[] strs = desc.split("\\-");
			tableHeadItemArray[0] = strs[strs.length - 1];
		}

	}

	public View getTableChart(TableChartResult tableData) {
		ArrayList<String> keyList = new ArrayList<String>(); // 记录key的集合
		View v = ((Activity) mContext).getLayoutInflater().inflate(
				R.layout.table_scroll_layout, null); // 实例化布局

		// x,y 轴的滑动视图
		x_scroll = (HorizontalScrollView) v.findViewById(R.id.x_scroll);
		y_scroll = (ScrollView) v.findViewById(R.id.y_scroll);

		center_scroll_y = (ObservableScrollView) v
				.findViewById(R.id.center_scroll_y);
		center_scroll_x = (ObservableScrollViewX) center_scroll_y
				.findViewById(R.id.center_scroll_x);

		initScroll(); // 初始化监听和滑动事件

		// x,y 轴的滑动视图内的数据容器
		LinearLayout yll = (LinearLayout) y_scroll
				.findViewById(R.id.y_linearlayout);
		LinearLayout xll = (LinearLayout) x_scroll
				.findViewById(R.id.x_linearlayout);

		// 表头的容器
		LinearLayout textll = (LinearLayout) v.findViewById(R.id.top_text);

		// 滑动区的容器
		LinearLayout line = (LinearLayout) center_scroll_y
				.findViewById(R.id.line_ll);

		// 获取key集合
		String json = tableData.getListDataSet().getTable().getJSONObject(0)
				.toJSONString();
		keyList = jsonToArray(json);

		LinearLayout rowHead = getRowLayout(); // 拿到一个空行

		// 绘制首行
		for (int i = keyList.size() - 1; i >= 0; i--) {
			// 实例化一个单元项
			LinearLayout item = (LinearLayout) ((Activity) mContext)
					.getLayoutInflater().inflate(R.layout.table_item, null);

			// 算出单元格的宽度
			item.setLayoutParams(new LayoutParams(getItemWidth(keyList.size()),
					LayoutParams.WRAP_CONTENT));

			TextView keyText = (TextView) item.findViewById(R.id.form_text);
			keyText.setText(keyList.get(i));

			if (i == keyList.size() - 1) {
				item.setBackgroundResource(R.drawable.item_left_line_color); // 改变表格竖线
				textll.addView(item); // 填充数据到表头
			} else {
				item.setBackgroundResource(R.drawable.item_line_color); // 没有左边线的背景
				rowHead.addView(item); // 填充数据到行首
			}
		}
		xll.addView(rowHead);

		boolean lineColorFlag = false; // 控制行间变色

		// 绘制内容区
		for (int x = 0; x < tableData.getListDataSet().getTable().size(); x++) {
			LinearLayout row = getRowLayout(); // 得到一个空行

			// 把fastJson 字符串转换成普通json 再转成key集合 再存入 arr 反向取出 - 序对
			keyList.clear();
			String KeyJson = tableData.getListDataSet().getTable()
					.getJSONObject(x).toJSONString();

			keyList = jsonToArray(KeyJson);
			for (int y = keyList.size() - 1; y >= 0; y--) {
				LinearLayout item = (LinearLayout) ((Activity) mContext)
						.getLayoutInflater().inflate(R.layout.table_item, null);
				item.setLayoutParams(new LayoutParams(getItemWidth(keyList
						.size()), LayoutParams.WRAP_CONTENT));
				if (lineColorFlag)
					item.setBackgroundResource(R.drawable.item_bottom_right_line_color);
				if (y == keyList.size() - 1) {
					if (lineColorFlag)
						item.setBackgroundResource(R.drawable.item_light_blue_line_);
					else
						item.setBackgroundResource(R.drawable.item_left_line);
				}

				TextView keyText = (TextView) item.findViewById(R.id.form_text);
				keyText.setText(tableData.getListDataSet().getTable()
						.getJSONObject(x).getString(keyList.get(y)));

				if (y == keyList.size() - 1) {
					yll.addView(item);
				} else {
					row.addView(item);
				}
			}

			if (isLineColorSwap)
				lineColorFlag = !lineColorFlag;
			line.addView(row);
		}

		return v;
	}

	private View initView() {

		View v = ((Activity) mContext).getLayoutInflater().inflate(
				R.layout.table_scroll_layout, null); // 实例化布局

		// x,y 轴的滑动视图
		x_scroll = (HorizontalScrollView) v.findViewById(R.id.x_scroll);
		y_scroll = (ScrollView) v.findViewById(R.id.y_scroll);

		center_scroll_y = (ObservableScrollView) v
				.findViewById(R.id.center_scroll_y);
		center_scroll_x = (ObservableScrollViewX) center_scroll_y
				.findViewById(R.id.center_scroll_x);

		initScroll(); // 初始化监听和滑动事件

		return v;
	}
	
	
	public  static  double  add(String  v1,String  v2){ 
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2); 
		return b1.add(b2).doubleValue();
		} 

	public View getTableChartWidthPieData(PieChartResult pieData,
			int currentUnit) {

		View v = initView();

		// x,y 轴的滑动视图内的数据容器
		LinearLayout yll = (LinearLayout) y_scroll
				.findViewById(R.id.y_linearlayout);
		LinearLayout xll = (LinearLayout) x_scroll
				.findViewById(R.id.x_linearlayout);

		// 表头的容器
		LinearLayout textll = (LinearLayout) v.findViewById(R.id.top_text);

		// 滑动区的容器
		LinearLayout line = (LinearLayout) center_scroll_y
				.findViewById(R.id.line_ll);

		LinearLayout rowHead = getRowLayout(); // 拿到一个空行

		// 绘制首行
		for (int i = 0; i < tableHeadItemArray.length; i++) {
			// 实例化一个单元项
			LinearLayout item = (LinearLayout) ((Activity) mContext)
					.getLayoutInflater().inflate(R.layout.table_item, null);

			// 算出单元格的宽度
			item.setLayoutParams(new LayoutParams(
					getItemWidth(tableHeadItemArray.length),
					LayoutParams.WRAP_CONTENT));

			TextView keyText = (TextView) item.findViewById(R.id.form_text);
			keyText.setText(tableHeadItemArray[i]);

			if (i == 0) {
				item.setBackgroundResource(R.drawable.item_left_line_color); // 改变表格竖线
				textll.addView(item); // 填充数据到表头
			} else {
				item.setBackgroundResource(R.drawable.item_line_color); // 没有左边线的背景
				rowHead.addView(item); // 填充数据到行首
			}
		}
		xll.addView(rowHead);

		boolean lineColorFlag = false; // 控制行间变色

		DecimalFormat df = new DecimalFormat("###0.000");
		df.setRoundingMode(RoundingMode.HALF_UP); 
		if (currentUnit == PieChartActivity.ROOT) {
			df = new DecimalFormat("###");
			df.setMaximumFractionDigits(0);// 显示小时位的最大字数
		}
		

		// 得到总和 算占比
		double count = 0;
		for (int i = 0; i < pieData.getItems().length; i++) {
			count = add(df.format(count), df.format(pieData.getItems()[i].getItemValue()));
		}


		// //格式修饰器
		// DecimalFormat df = new DecimalFormat("######0.000");

		if (pieData.getItems().length < 1) {
			// 无数据
			// v = new TextView((Activity) mContext);
			// ((TextView)v).setText("无数据");
			// return v;

			String textEmpty = "无数据";
			// 绘制内容区
			for (int m = 0; m < 1; m++) {
				LinearLayout row = getRowLayout(); // 得到一个空行

				for (int y = 0; y < tableHeadItemArray.length; y++) {
					LinearLayout item = (LinearLayout) ((Activity) mContext)
							.getLayoutInflater().inflate(R.layout.table_item,
									null);
					item.setLayoutParams(new LayoutParams(
							getItemWidth(tableHeadItemArray.length),
							LayoutParams.WRAP_CONTENT));
					if (lineColorFlag)
						item.setBackgroundResource(R.drawable.item_bottom_right_line_color);
					if (y == 0) {
						if (lineColorFlag)
							item.setBackgroundResource(R.drawable.item_light_blue_line_);
						else
							item.setBackgroundResource(R.drawable.item_left_line);
					}

					TextView keyText = (TextView) item
							.findViewById(R.id.form_text);
					/*
					 * if (y == 0) {
					 * keyText.setText(pieData.getItems()[x].getDes()); } else
					 * if (y == 1) { double ratio =
					 * (double)(pieData.getItems()[x].getItemValue()) / count *
					 * 100d; keyText.setText(df.format(ratio) + "%"); } else if
					 * (y == 2) {
					 * keyText.setText(df.format(pieData.getItems()[x]
					 * .getItemValue())); }
					 */

					if (y == 0) {
						keyText.setText(textEmpty);
					} else if (y == 1) {
						keyText.setText(textEmpty);

					} else if (y == 2) {

						keyText.setText(textEmpty);
					}

					if (y == 0) {
						yll.addView(item);
					} else {
						row.addView(item);
					}
				}

				if (isLineColorSwap)
					lineColorFlag = !lineColorFlag;
				line.addView(row);
			}

		} else {
			// 绘制内容区
			for (int x = 0; x < pieData.getItems().length + 1; x++) {
				LinearLayout row = getRowLayout(); // 得到一个空行

				for (int y = 0; y < tableHeadItemArray.length; y++) {
					LinearLayout item = (LinearLayout) ((Activity) mContext)
							.getLayoutInflater().inflate(R.layout.table_item,
									null);
					item.setLayoutParams(new LayoutParams(
							getItemWidth(tableHeadItemArray.length),
							LayoutParams.WRAP_CONTENT));
					if (lineColorFlag)
						item.setBackgroundResource(R.drawable.item_bottom_right_line_color);
					if (y == 0) {
						if (lineColorFlag)
							item.setBackgroundResource(R.drawable.item_light_blue_line_);
						else
							item.setBackgroundResource(R.drawable.item_left_line);
					}

					TextView keyText = (TextView) item
							.findViewById(R.id.form_text);
					/*
					 * if (y == 0) {
					 * keyText.setText(pieData.getItems()[x].getDes()); } else
					 * if (y == 1) { double ratio =
					 * (double)(pieData.getItems()[x].getItemValue()) / count *
					 * 100d; keyText.setText(df.format(ratio) + "%"); } else if
					 * (y == 2) {
					 * keyText.setText(df.format(pieData.getItems()[x]
					 * .getItemValue())); }
					 */
					if (x < pieData.getItems().length) {
						if (y == 0) {
							keyText.setText(pieData.getItems()[x].getDes());
						} else if (y == 1) {
							keyText.setText(df.format(pieData.getItems()[x]
									.getItemValue()));

						} else if (y == 2) {

							double ratio = (double) (pieData.getItems()[x]
									.getItemValue()) / count * 100d;
							keyText.setText(df.format(ratio) + "%");
						}
					}
					// 加合计行
					if (x == pieData.getItems().length) {
						if (y == 0) {
							keyText.setText("合计");
						} else if (y == 1) {
							keyText.setText(df.format(count));

						} else if (y == 2) {

							double ratio = (double) (count) / count * 100d;
							keyText.setText(df.format(ratio) + "%");
						}
					}

					if (y == 0) {
						yll.addView(item);
					} else {
						row.addView(item);
					}
				}

				if (isLineColorSwap)
					lineColorFlag = !lineColorFlag;
				line.addView(row);
			}
			

		}

		return v;
	}

	// private View TextView() {
	// // TODO Auto-generated method stub
	// return null;
	// }
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

	private void initScroll() {
		center_scroll_x.setScrollViewListener(this);
		center_scroll_y.setScrollViewListener(this);

		x_scroll.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				return true;
			}
		});

		y_scroll.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				return true;
			}
		});

	}

	// 返回一个横向排列子控件的空行
	private LinearLayout getRowLayout() {
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		LinearLayout row = new LinearLayout(mContext);
		row.setOrientation(LinearLayout.HORIZONTAL);
		row.setLayoutParams(params);
		return row;
	}

	// 获取item宽度 会按行宽 自适应
	private int getItemWidth(int count) {
		DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
		int scrWidth = 0;
		if ((int) (dm.widthPixels) > (37 * count)) {
			scrWidth = (int) (dm.widthPixels / count);
		} else {
			scrWidth = 37;
		}
		return scrWidth;
	}

	@Override
	public void onScrollChanged(ObservableScrollViewX scrollViewX, int x,
			int y, int oldx, int oldy) {
		x_scroll.scrollTo(scrollViewX.getScrollX(), scrollViewX.getScrollY());
		// Toast.makeText(mContext, "x", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onScrollChanged(ObservableScrollView scrollView, int x, int y,
			int oldx, int oldy) {
		y_scroll.scrollTo(scrollView.getScrollX(), scrollView.getScrollY());
		// Toast.makeText(mContext, scrollView.getScrollY() + "",
		// Toast.LENGTH_LONG).show();
	}

}
