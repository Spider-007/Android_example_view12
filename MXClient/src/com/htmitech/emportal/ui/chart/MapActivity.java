package com.htmitech.emportal.ui.chart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.htmitech.emportal.R;
import com.htmitech.emportal.base.IBaseCallback;
import com.htmitech.emportal.base.MyBaseFragment;
import com.htmitech.emportal.entity.BarLineChartResult;
import com.htmitech.emportal.entity.BarLineChartResultInfo;
import com.htmitech.emportal.entity.PieChartResult;
import com.htmitech.emportal.entity.PieChartResultInfo;
import com.htmitech.emportal.entity.ReportListResult;
import com.htmitech.emportal.entity.ReportResult;
import com.htmitech.emportal.entity.ReportResultInfo;
import com.htmitech.emportal.ui.chart.model.task.ChartListModel;
import com.htmitech.emportal.ui.chart.view.BarChart3D01View;
import com.htmitech.emportal.ui.chart.view.PieChart01View;
import com.htmitech.emportal.ui.plugin.report.CopyOfsupplyActivity;
import com.htmitech.emportal.ui.plugin.report.ReportParametersFromUI;
import com.htmitech.emportal.ui.widget.flatingactionButton.FloatingActionButton;
import com.htmitech.emportal.ui.widget.flatingactionButton.FloatingActionsMenu;
import com.radio.SegmentedGroup;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;


public class MapActivity extends MyBaseFragment implements IBaseCallback,
		OnClickListener {

	public static final int PIE = 0;
	public static final int TABLE = 1;
	private int pieOrTable = 0;

	private ReportResult[] oneChartDetail = null;
	private ChartListModel chartModel = null;

	private BarLineChartResult barLineChartResult = null;
	private PieChartResult pieChartResult = null;

	private PieChart01View pieChartView = null;

	// String[] chartTypeArr = {"GetChartDefiniensHasDataByIDAndParameters",
	// "GetChartDefiniensHasDataByIDAndParameters",
	// "GetPieDefiniensHasByIDAndParameters",
	// "GetListDefiniensHasDataByIDAndParameters"};
	private ReportListResult[] charts = null;

	public ReportListResult[] getCharts() {
		return charts;
	}

	public void setCharts(ReportListResult[] charts) {
		this.charts = charts;
	}

	private static int currentIndex = 0;

	SegmentedGroup radioBtn = null;
	LinearLayout ll = null;

	private LayoutInflater mInflater;
	private RelativeLayout mLinearlayout_chart;

	private FloatingActionsMenu menuMultipleActions = null;

	// 接收界面上输入的外部参数条件
	ReportParametersFromUI reportParameterFromUI = null;

	public ReportParametersFromUI getReportParameterFromUI() {
		return reportParameterFromUI;
	}

	public void setReportParameterFromUI(
			ReportParametersFromUI reportParameterFromUI) {
		this.reportParameterFromUI = reportParameterFromUI;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.mInflater = inflater;
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onFail(int requestTypeId, int statusCode, String errorMsg,
			Object result) {
		// TODO Auto-generated method stub

	}

	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.fragment_chart;
	}

	@Override
	protected void initViews() {
		// TODO Auto-generated method stub
		// register
		EventBus.getDefault().register(this);
		mLinearlayout_chart = (RelativeLayout) findViewById(R.id.linearlayout_chart); // 整个图表的大框
		postMan(currentIndex);
		initCharMenu();
	}

	private void initCharMenu() {
		menuMultipleActions = (FloatingActionsMenu) findViewById(R.id.chart_menu);

		menuMultipleActions.setVisibility(View.VISIBLE);

		FloatingActionButton item = new FloatingActionButton(this.getActivity());
		item.setTitle("横竖屏切换");
		item.setTag(1);
		item.setIcon(R.drawable.btn_change);
		menuMultipleActions.addButton(item);

		FloatingActionButton item2 = new FloatingActionButton(
				this.getActivity());
		item2.setTitle("查看图例详情");
		item2.setTag(2);
		item2.setIcon(R.drawable.btn_details);
		item2.setOnClickListener(this);
		menuMultipleActions.addButton(item2);

		FloatingActionButton item3 = new FloatingActionButton(
				this.getActivity());
		item3.setTitle("视图");
		item3.setTag(3);
		item3.setIcon(R.drawable.btn_view);
		item3.setOnClickListener(this);
		menuMultipleActions.addButton(item3);

		FloatingActionButton item4 = new FloatingActionButton(
				this.getActivity());
		item4.setTitle("分享");
		item4.setTag(4);
		item4.setIcon(R.drawable.btn_share);
		menuMultipleActions.addButton(item4);
		item4.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v instanceof ImageButton) {
			int tag = ((Integer) (v.getTag())).intValue();
			FragmentManager fm = getActivity().getSupportFragmentManager();
			if (tag == 1) {
			} else if (tag == 2) {
				if (pieChartResult != null) {
					PieDetailDialog dialog = new PieDetailDialog(getActivity(),
							R.style.mydialog, pieChartView);
					dialog.setViewValue("请选择图例数据", pieChartResult.getItems());
					dialog.show();
				}
			} else if (tag == 3) {
				if (pieChartResult == null)
					return;
				if (pieOrTable == PIE) {
					tableChart();
					pieOrTable = TABLE;
				} else if (pieOrTable == TABLE) {
					pieChart();
					pieOrTable = PIE;
				}
			} else if (tag == 4) {
				Intent i = new Intent(getActivity(), CopyOfsupplyActivity.class);
				startActivity(i);
			}
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		// Unregister
		EventBus.getDefault().unregister(this);
	}

	/** EventBus 接收到主Activity条件变更事件后更新报表参数重绘报表 */
	public void onEventMainThread(ReportParametersFromUI reportParameterFromUI) {
		Log.d("MapActivity", "Received event at MapActivity");
		// 1，跟新参数
		this.reportParameterFromUI = reportParameterFromUI;
		// 2，刷新当前图表
		postMan(currentIndex);

		// //利用Report_ID发起get请求，获取报表参数
		// charts =
		// (ReportListResult[])(getIntent().getSerializableExtra("ReportList"));
		// if (charts == null && charts.length == 0){
		// //显示传入参数错误
		// Toast.makeText(getActivity(), "传入参数错误", Toast.LENGTH_LONG).show();
		// }
		// else {
		// //获取图默认的（第一个）图表详情
		// //设置默认参数
		// reportParameterFromUI =
		// (ReportParametersFromUI)(getIntent().getSerializableExtra("ReportParameters"));
		//
		// postMan(currentIndex);
		// }
	}

	private void postMan(int pos) {
		chartModel = new ChartListModel(this);
		chartModel.getDataFromServerByType(
				ChartListModel.TYPE_GETCHARTParameters, null,
				charts[pos].getReport_ID());
	}

	public static class OneChartDetailPostEntity implements Serializable {
		public String reportGuid;
		public ReportResult[] parameterList;
	}

	// 请求参数编辑函数
	private Object getOneChartDetailPostEntity() {
		OneChartDetailPostEntity oneChart = new OneChartDetailPostEntity();
		oneChart.reportGuid = charts[currentIndex].getReport_ID();
		oneChart.parameterList = oneChartDetail;
		// 临时代码，不共用
		if (oneChart.parameterList.length == 1
				&& reportParameterFromUI.getItems().size() == 1) {
			oneChart.parameterList[0].setValues(this.reportParameterFromUI
					.getItems().get(0).getValues()); // "201505"
		} else if (oneChart.parameterList.length == 2
				&& reportParameterFromUI.getItems().size() == 2) {
			oneChart.parameterList[0].setValues(this.reportParameterFromUI
					.getItems().get(0).getValues()); // new String[]{"20150601"}
			oneChart.parameterList[1].setValues(this.reportParameterFromUI
					.getItems().get(1).getValues()); // new String[]{"20150631"}
		}
		return oneChart;
	}

	// 待修改 有冗余代码
	private void barChart3D() {
		ll = (LinearLayout) View.inflate(getActivity(),
				R.layout.chart_and_button, null);
		// ll = (LinearLayout)
		// getLayoutInflater().inflate(com.htmitech.emportalzt.R.layout.chart_and_button,
		// null);
		((ViewGroup) (ll.findViewById(R.id.chart_frame)))
				.addView(new BarChart3D01View(this.getActivity(),
						barLineChartResult));
		if (charts.length > 1) {
			setRadioButton(charts.length);
		}
		// setContentView(ll);
		mLinearlayout_chart.removeAllViews();
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT); // 创建 Linearlayout 布局参数

		mLinearlayout_chart.addView(ll, params);
		mLinearlayout_chart.addView(menuMultipleActions);
	}

	private void setRadioButton(int length) {
		if (ll != null)
			radioBtn = (SegmentedGroup) ll.findViewById(R.id.radio_group);
		radioBtn.setVisibility(View.VISIBLE);
		for (int i = 0; i < length; i++) {
			// RadioButton radioButton = (RadioButton)
			// getLayoutInflater().inflate(R.layout.radio_button_item, null);
			RadioButton radioButton = (RadioButton) View.inflate(getActivity(),
					R.layout.radio_button_item, null);
			radioButton.setTag(i);
			radioButton.setText(charts[i].getNavigateMenuName());
			// radioButton.setText(i + "");
			radioButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					currentIndex = ((Integer) (v.getTag())).intValue();
					postMan(currentIndex);
					pieOrTable = PIE;
					// Toast.makeText(MapActivity.this, currentIndex + "",
					// Toast.LENGTH_SHORT).show();
				}
			});
			radioBtn.addView(radioButton);
			radioBtn.updateBackground();
		}
		((RadioButton) radioBtn.getChildAt(currentIndex)).setChecked(true);
	}

	// 待修改
	private void pieChart() {
		ll = (LinearLayout) View.inflate(getActivity(),
				R.layout.chart_and_button, null);
		pieChartView = new PieChart01View(this.getActivity(), pieChartResult,
				getOneChartDetailPostEntity());
		((ViewGroup) (ll.findViewById(R.id.chart_frame))).addView(pieChartView);
		if (charts.length > 1) {
			setRadioButton(charts.length);
		}

		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT); // 创建 Linearlayout 布局参数
		mLinearlayout_chart.removeAllViews();
		mLinearlayout_chart.addView(ll, params);
		mLinearlayout_chart.addView(menuMultipleActions);
	}

	private void tableChart() {
		ll = (LinearLayout) View.inflate(getActivity(),
				R.layout.chart_and_button, null);
		TableChartDraw tableChart = new TableChartDraw(getActivity());
		tableChart.isLineColorSwap = false;
		View v = tableChart.getTableChartWidthPieData(pieChartResult,0);

		(ll.findViewById(R.id.chart_frame)).setPadding(0, 15, 0, 0);
		((ViewGroup) (ll.findViewById(R.id.chart_frame))).addView(v);
		if (charts.length > 1) {
			setRadioButton(charts.length);
		}

		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT); // 创建 Linearlayout 布局参数

		mLinearlayout_chart.removeAllViews();
		mLinearlayout_chart.addView(ll, params);
		mLinearlayout_chart.addView(menuMultipleActions);
	}

	@Override
	public void onSuccess(int requestTypeId, Object result) {
		if (requestTypeId == ChartListModel.TYPE_GETCHARTParameters) {
			oneChartDetail = ((ReportResultInfo) result).getResult();
			// if else 来判断走哪个task
			if (charts[currentIndex].getReportType() == ChartListModel.TYPE_CHART_BAR) {
				chartModel.getDataFromServerByType(
						ChartListModel.TYPE_CHART_BAR,
						getOneChartDetailPostEntity());
			} else if (charts[currentIndex].getReportType() == ChartListModel.TYPE_CHART_PIE) {
				chartModel.getDataFromServerByType(
						ChartListModel.TYPE_CHART_PIE,
						getOneChartDetailPostEntity());
			}
		} else if (requestTypeId == ChartListModel.TYPE_CHART_BAR) {// 返回具体报表绘图参数的处理逻辑
			barLineChartResult = ((BarLineChartResultInfo) result).getResult();
			// 利用数据 绘制报表
			barChart3D();
		} else if (requestTypeId == ChartListModel.TYPE_CHART_PIE) {// 返回具体报表绘图参数的处理逻辑
			pieChartResult = ((PieChartResultInfo) result).getResult();
			pieChart();
		}
		// 利用图表详细数据和Report_ID组织成一个结构来请求某一个具体图表的展示数据

		// Toast.makeText(this, oneChartDetail[0].getDes(),
		// Toast.LENGTH_LONG).show();
	}

}
