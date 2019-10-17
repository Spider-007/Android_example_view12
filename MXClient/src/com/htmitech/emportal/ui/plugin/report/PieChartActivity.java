package com.htmitech.emportal.ui.plugin.report;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.SlidingDrawer;
import android.widget.SlidingDrawer.OnDrawerOpenListener;
import android.widget.TextView;
import android.widget.Toast;

import com.htmitech.emportal.R;
import com.htmitech.emportal.base.IBaseCallback;
import com.htmitech.emportal.entity.Item;
import com.htmitech.emportal.entity.PieChartResult;
import com.htmitech.emportal.entity.PieChartResultInfo;
import com.htmitech.emportal.entity.ReportResult;
import com.htmitech.emportal.entity.ReportResultInfo;
import com.htmitech.emportal.ui.chart.CopyofPieDetailDialog;
import com.htmitech.emportal.ui.chart.CopyofPieDetailDialog.IItemCallBack;
import com.htmitech.emportal.ui.chart.MapActivity;
import com.htmitech.emportal.ui.chart.model.task.ChartListModel;
import com.htmitech.emportal.ui.chart.view.CopyOfPieChartView;
import com.htmitech.emportal.ui.chart.view.IPieItemClickListener;
import com.htmitech.emportal.ui.widget.LoadingView;
import com.htmitech.emportal.ui.widget.flatingactionButton.FloatingActionButton;
import com.htmitech.emportal.ui.widget.flatingactionButton.FloatingActionsMenu;
import com.minxing.client.widget.SystemMainTopRightPopMenu;
import com.minxing.kit.api.MXAPI;
import com.minxing.kit.api.bean.ShareLink;

import java.lang.ref.WeakReference;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import htmitech.com.componentlibrary.unit.PreferenceUtils;
import htmitech.com.componentlibrary.unit.ServerUrlConstant;

public class PieChartActivity extends Activity implements IBaseCallback,
		OnClickListener {

	public static final String PIE_BEGIN_TIME = "form_list_chart_beginTime";
	public static final String PIE_END_TIME = "form_list_chart_endTime";
	public static final String PROJECT_ID = "form_list_chart_projectId";
	public static final String BUILD_ID = "form_list_chart_buildId";
	public static final String PIE_REPORT_ID = "form_list_chart_reportId";
	public static final String SELECT_TYPE = "select_type";
	public static final String DIMENSIONALITY_TYPE = "dimensionality_type";
	public static final String DIMENSIONALITY_VALUE = "dimensionality_value";

	public static final String SHARE_TITLE = "share_title";
	public static final String PIE_DATA = "pie_data";
	public static final String SHARELINK_USERNAME = "form_list_chart_username";
	public static final String SHARELINK_MARK = "form_list_chart_sharelinkmark";
	public static final String SHARELINK_CURRENTDIMENSIONALITY = "form_list_chart_share_currentdimensionality";
	public final static int TON = 0;
	public final static int ROOT = 1;
	public final static int KM = 2;

	private LoadingView mLoadingView = null;
	/* 网络异常显示 */
	// private RelativeLayout mError;

	private ImageButton leftBackButton;
	private ImageButton rightMenuButton;
	private TextView timeTextView;

	// 全局的 Tab 信号量
	private static int currentSelect = TON;

	// 内容容器
	private SlidingDrawer mDrawer;

	// tab 容器
	private RadioGroup rgTab;

	// 下拉图
	private SystemMainTopRightPopMenu functionPopMenu;

	private ArrayList<ImageButton> imageButtons;
	private ArrayList<ImageButton> selectButtonArr;

	// 已经限定的维度(限定了具体维度为具体的值，该数组用来存储已经限定的维度）
	private ArrayList<String> selectDimensionality = new ArrayList<String>();
	// 已经限定的维度的对应值(限定了具体维度为具体的值，该数组用来存储已经限定的维度的具体值，于selectButton一一对应）
	private ArrayList<String> selectDimensionality_value = new ArrayList<String>();
	// 当前选中的维度，可自由切换维度
	private String currentDimensionality = "";

	private boolean mExpanded = false;
	private boolean isFirst = true;

	// 可选的维度对应的参数值
	public final String[] DIMENSIONALITY_TYPE1 = { "projecttype", "gangchang",
			"daili", "guixing", "caizhi", "changdu", "sudu", "rechuli",
			"banshichu" };
	// 配置情况使用
	public final String[] DIMENSIONALITY_TYPE2 = { "projecttype", "gangchang",
			"daili", "guixing", "changdu", "rechuli", "banshichu" };

	// // 可选的维度对应的参数值
	// public static final String[] DIMENSIONALITY_TYPE_DES1 = { "项目类型",
	// "钢厂", "代理", "轨型", "材质", "长度", "速度",
	// "热处理", "办事处" };
	// // 配置情况使用
	// public static final String[] DIMENSIONALITY_TYPE_DES2 = { "热处理", "轨型",
	// "长度" };

	HashMap<String, String> DIMENSIONALITY_TYPE_DES = null;
	// 按钮图片的 集合
	HashMap<String, Integer> picPreMap = null;
	HashMap<String, Integer> picMap = null;
	private String title = "";
	// 整个饼图的大框
	private RelativeLayout pieChartLayout = null;

	// 饼图内的按钮
	private FloatingActionsMenu menuMultipleActions = null;

	private ChartListModel chartModel = null;
	private String mReportId = "";

	private ReportResult[] oneChartDetail = null;
	// 接收界面上输入的外部参数条件
	ReportParametersFromUI reportParameterFromUI = null;
	private PieChartResult pieChartResult = null;
	private WeakReference<CopyOfPieChartView>  pieChartView = null;

	String projectID = "";
	String buildID = "";
	// 时间切换标记
	public final int MONTH = 0;
	public final int YEAR = 2;
	private int timeFlag = MONTH;
	private String beginTime = "";
	private String endTime = "";
	private String[][] paramValues = new String[][] { { "" }, { "" }, { "" },
			{ "" }, { "" }, { "" }, { "" }, { "" }, { "" }, { "" }, { "" },
			{ "" }, { "" }, { "" }, { "" } };

	public final int PIE = 0;
	public final int TABLE = 1;
	private int pieOrTable = 0;

	// 分享相关变量
	public String apiUrlTmp = null; // 用于拼接分享参数
	public String apiUrl = null;
	public ShareLink shareLink = null;
	public String iconId = null;
	public int curItem = 0;

	private boolean isShare = false;
    //图例测试开发部分
	private TextView tv_count;
	private ListView lv_symbol;
	private ArrayList<Item> chartItems = new ArrayList<Item>();
	private FloatingActionButton item2;
	private FloatingActionButton item4;
	private FloatingActionButton item;
	private FloatingActionButton item3;
	 
	
	private class DetailAdapter extends ArrayAdapter<Item> {
		Context mContext;
		public DetailAdapter(ArrayList<Item> detailItems, Context mContext) {
			super(mContext, 0, detailItems);
			this.mContext = mContext;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO 自动生成的方法存根
			if (null == convertView) {
				convertView = LayoutInflater.from(mContext)
	                    .inflate(R.layout.list_item_detail_tuli, null);
			}
			Item item = getItem(position);
			ImageView detailImageView =  (ImageView)convertView.findViewById(R.id.detail_image);
			detailImageView.setBackgroundColor(item.getChartColor());
			TextView detailTextView = (TextView)convertView.findViewById(R.id.detail_text);
			detailTextView.setText(item.getDes());
			
			return convertView;
		}
		
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		title = getIntent().getStringExtra("title");
		if (!title.equalsIgnoreCase("配置量"))
			setContentView(R.layout.supply_two_main);
		else
			setContentView(R.layout.supply_two_main2);

		if (title.equalsIgnoreCase("需求量"))
			findViewById(R.id.image_button_9).setVisibility(View.INVISIBLE);

		mLoadingView = (LoadingView) findViewById(R.id.loadingview_detail); // 构建
																			// 加载视图
		// mError = (RelativeLayout) findViewById(R.id.error_detail);
		// mError.setVisibility(View.GONE);

		buildID = getIntent().getStringExtra(PieChartActivity.BUILD_ID);
		projectID = getIntent().getStringExtra(PieChartActivity.PROJECT_ID);
		beginTime = getIntent().getStringExtra(PieChartActivity.PIE_BEGIN_TIME);
		endTime = getIntent().getStringExtra(PieChartActivity.PIE_END_TIME);

		String shareFromUserName = getIntent().getStringExtra(
				PieChartActivity.SHARELINK_USERNAME);
		String shareMark = getIntent().getStringExtra(
				PieChartActivity.SHARELINK_MARK);

		paramValues[0] = new String[] { PreferenceUtils.getLoginName(this) };
		paramValues[1] = new String[] { projectID == null ? "" : projectID };
		paramValues[2] = new String[] { buildID == null ? "" : buildID };
		paramValues[3] = new String[] { beginTime };
		paramValues[4] = new String[] { endTime };

		tv_count = (TextView) findViewById(R.id.tv_count);
		lv_symbol = (ListView) findViewById(R.id.lv_symbol);
		lv_symbol.setOverScrollMode(View.OVER_SCROLL_NEVER); 
		if (shareMark != null && shareMark.length() > 0){
			//表明是分享过来的

			isShare = true;
			if (shareFromUserName != null && shareFromUserName.length() > 0) {
				paramValues[0] = new String[] { shareFromUserName };
			}
			// 设置是否可以点击操作

			((LinearLayout) findViewById(R.id.slidingDrawer_content_ll))
					.setVisibility(View.INVISIBLE);

		}

		currentSelect = getIntent().getIntExtra(PieChartActivity.SELECT_TYPE,
				TON);
		mReportId = getIntent().getStringExtra(PieChartActivity.PIE_REPORT_ID);

		// 取得前一页面限定的条件
		ArrayList<String> dimensionality_value = getIntent()
				.getStringArrayListExtra(PieChartActivity.DIMENSIONALITY_VALUE);
		ArrayList<String> dimensionality_type = getIntent()
				.getStringArrayListExtra(PieChartActivity.DIMENSIONALITY_TYPE);

		if (dimensionality_value != null && dimensionality_value.size() > 0) {

			isFirst = false;
			// 符合该条件时，说明已经是第二次打开该页面。进入了下一层报表
			for (int i = 0; i < DIMENSIONALITY_TYPE1.length; i++) {
				for (int j = 0; j < dimensionality_type.size(); j++) {
					if (dimensionality_type.get(j).equals(
							DIMENSIONALITY_TYPE1[i])) {
						paramValues[i + 5] = new String[] { dimensionality_value
								.get(j) };
						break;
					}
				}
			}

			// 设置限定条件
			selectDimensionality.addAll(dimensionality_type);
			selectDimensionality_value.addAll(dimensionality_value);

		} else {
			// 首次打开该页面
			// selectDimensionality.add("projecttype");
			isFirst = true;
		}

		/*
		 * 1.实例化Imagebutton 修改默认图片，把第一个按钮的背景图设置为选中。
		 */
		//
		
		
		
		initViews();
		
		// 2.去服务端请求数据 利用传入的
		postManParameter();

		// 启动帮助页
//		if (PreferenceUtils.isLoginSupplyPie(this)) {
//			PreferenceUtils.setLoginSupplyPie(this, false);
//			final Dialog dialog = new Dialog(PieChartActivity.this,
//					R.style.Dialog_Fullscreen);
//			dialog.setContentView(R.layout.layout_help_supplypie);
//			ImageView iv1 = (ImageView) dialog
//					.findViewById(R.id.ivNavigater_supplytop1);
//			iv1.setOnClickListener(new OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					dialog.dismiss();
//				}
//			});
//			dialog.show();
//		}
	}

	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		
//		if (pieChartView.get() == null){
			postManParameter();
//		}
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
	}


	private void initViews() {
		initImageButton();
		initSlidingDrawerAndContent();
		initPieChartRelativeLayout();
		initCharMenu();
		initTitle();
		
	}

	private void initImageButton() {
		imageButtons = new ArrayList<ImageButton>();

		picMap = new HashMap<String, Integer>();
		picPreMap = new HashMap<String, Integer>();
		DIMENSIONALITY_TYPE_DES = new HashMap<String, String>();

		title = getIntent().getStringExtra("title");
		if (!title.equalsIgnoreCase("配置量")) {

			imageButtons.add((ImageButton) findViewById(
					R.id.framelayout_supplt_pie)
					.findViewById(R.id.slidingDrawer_content_ll)
					.findViewById(R.id.row_linear1)
					.findViewById(R.id.image_button_1));
			imageButtons.add((ImageButton) findViewById(
					R.id.framelayout_supplt_pie)
					.findViewById(R.id.slidingDrawer_content_ll)
					.findViewById(R.id.row_linear1)
					.findViewById(R.id.image_button_2));
			imageButtons.add((ImageButton) findViewById(
					R.id.framelayout_supplt_pie)
					.findViewById(R.id.slidingDrawer_content_ll)
					.findViewById(R.id.row_linear1)
					.findViewById(R.id.image_button_3));
			imageButtons.add((ImageButton) findViewById(
					R.id.framelayout_supplt_pie)
					.findViewById(R.id.slidingDrawer_content_ll)
					.findViewById(R.id.row_linear1)
					.findViewById(R.id.image_button_4));
			imageButtons.add((ImageButton) findViewById(
					R.id.framelayout_supplt_pie)
					.findViewById(R.id.slidingDrawer_content_ll)
					.findViewById(R.id.row_linear2)
					.findViewById(R.id.image_button_5));
			imageButtons.add((ImageButton) findViewById(
					R.id.framelayout_supplt_pie)
					.findViewById(R.id.slidingDrawer_content_ll)
					.findViewById(R.id.row_linear2)
					.findViewById(R.id.image_button_6));
			imageButtons.add((ImageButton) findViewById(
					R.id.framelayout_supplt_pie)
					.findViewById(R.id.slidingDrawer_content_ll)
					.findViewById(R.id.row_linear2)
					.findViewById(R.id.image_button_7));
			imageButtons.add((ImageButton) findViewById(
					R.id.framelayout_supplt_pie)
					.findViewById(R.id.slidingDrawer_content_ll)
					.findViewById(R.id.row_linear2)
					.findViewById(R.id.image_button_8));
			if (!title.equalsIgnoreCase("需求量")) {
				imageButtons.add((ImageButton) findViewById(
						R.id.framelayout_supplt_pie)
						.findViewById(R.id.slidingDrawer_content_ll)
						.findViewById(R.id.row_linear3)
						.findViewById(R.id.image_button_9));
			}

			DIMENSIONALITY_TYPE_DES.put("projecttype", "项目类型");
			DIMENSIONALITY_TYPE_DES.put("gangchang", "钢厂");
			DIMENSIONALITY_TYPE_DES.put("daili", "代理商");
			DIMENSIONALITY_TYPE_DES.put("guixing", "轨型");
			if (!title.equalsIgnoreCase("需求量"))
				DIMENSIONALITY_TYPE_DES.put("banshichu", "办事处");
			DIMENSIONALITY_TYPE_DES.put("caizhi", "材质");
			DIMENSIONALITY_TYPE_DES.put("changdu", "长度");
			DIMENSIONALITY_TYPE_DES.put("sudu", "速度");
			DIMENSIONALITY_TYPE_DES.put("rechuli", "热处理");

			picMap.put("projecttype", R.drawable.project);
			picMap.put("daili", R.drawable.dailishang);
			picMap.put("gangchang", R.drawable.steel);
			picMap.put("banshichu", R.drawable.banshichu);
			picMap.put("guixing", R.drawable.guixing);
			picMap.put("caizhi", R.drawable.caizhi);
			picMap.put("sudu", R.drawable.speed);
			picMap.put("changdu", R.drawable.changdu);
			picMap.put("rechuli", R.drawable.rechuli);

			picPreMap.put("projecttype", R.drawable.project_pre);
			picPreMap.put("daili", R.drawable.dailishang_pre);
			picPreMap.put("gangchang", R.drawable.steel_pre);
			picPreMap.put("banshichu", R.drawable.banshichu_pre);
			picPreMap.put("guixing", R.drawable.guixing_pre);
			picPreMap.put("caizhi", R.drawable.caizhi_pre);
			picPreMap.put("sudu", R.drawable.speed_pre);
			picPreMap.put("changdu", R.drawable.changdu_pre);
			picPreMap.put("rechuli", R.drawable.rechuli_pre);

			// 设定已经限定的维度条件为不可点击
			for (int i = 0; i < imageButtons.size(); i++) {
				imageButtons.get(i).setVisibility(View.VISIBLE);
				imageButtons.get(i).setOnClickListener(this);
				imageButtons.get(i).setTag(DIMENSIONALITY_TYPE1[i]);
				String type = DIMENSIONALITY_TYPE1[i];
				for (int j = 0; j < selectDimensionality.size(); j++) {
					if (type.equals(selectDimensionality.get(j))) {
						imageButtons.get(i).setClickable(false);
						imageButtons.get(i).setEnabled(false);
						imageButtons.get(i).setBackgroundResource(
								picPreMap.get(type));
						break;
					}
				}
			}

			if (isShare) {
				currentDimensionality = getIntent().getStringExtra(
						PieChartActivity.SHARELINK_CURRENTDIMENSIONALITY);
				paramValues[14] = new String[] { currentDimensionality };
			} else {
				if (isFirst) {
					// 非配置量的、首次打开。默认项目类型维度
					currentDimensionality = "projecttype";
					paramValues[14] = new String[] { "projecttype" };
				} else {
					// 设定，默认维度。上一次添加的限定维度的最后一个
					// currentDimensionality = selectDimensionality
					// .get(selectDimensionality.size() - 1);
					// paramValues[14] = new String[] { selectDimensionality
					// .get(selectDimensionality.size() - 1) };

					// 按序排列，逻辑是这样的，可选维度是排序的。 例如： 项目类型，钢厂，办事处。 如果第一次选择的是钢厂
					// （之前没有选择过项目类型），选择一个钢厂后，默认按项目类型。之前选过项目类型的，默认按办事处。 依次类推。。
					for (int i = 0; i < DIMENSIONALITY_TYPE1.length; i++) {
						String type = DIMENSIONALITY_TYPE1[i];
						if (selectDimensionality.contains(type)) {
							continue;
						} else {
							currentDimensionality = type;
							paramValues[14] = new String[] { type };
							break;
						}

					}

				}
			}

			// 初始设定当前已经选择的维度（当前维度可自由切换）
			for (int n = 0; n < imageButtons.size(); n++) {
				if (imageButtons.get(n).getTag().toString()
						.equalsIgnoreCase(currentDimensionality)) {
					imageButtons.get(n).setSelected(true);
					imageButtons.get(n).setBackgroundResource(
							picPreMap.get(currentDimensionality));
				}
			}

		} else {

			imageButtons.add((ImageButton) findViewById(
					R.id.framelayout_supplt_pie)
					.findViewById(R.id.slidingDrawer_content_ll)
					.findViewById(R.id.row_linear1)
					.findViewById(R.id.image_button_1));
			imageButtons.add((ImageButton) findViewById(
					R.id.framelayout_supplt_pie)
					.findViewById(R.id.slidingDrawer_content_ll)
					.findViewById(R.id.row_linear1)
					.findViewById(R.id.image_button_2));
			imageButtons.add((ImageButton) findViewById(
					R.id.framelayout_supplt_pie)
					.findViewById(R.id.slidingDrawer_content_ll)
					.findViewById(R.id.row_linear1)
					.findViewById(R.id.image_button_3));
			imageButtons.add((ImageButton) findViewById(
					R.id.framelayout_supplt_pie)
					.findViewById(R.id.slidingDrawer_content_ll)
					.findViewById(R.id.row_linear1)
					.findViewById(R.id.image_button_4));
			// imageButtons.add((ImageButton) findViewById(
			// R.id.framelayout_supplt_pie)
			// .findViewById(R.id.slidingDrawer_content_ll)
			// .findViewById(R.id.row_linear2)
			// .findViewById(R.id.image_button_5));
			imageButtons.add((ImageButton) findViewById(
					R.id.framelayout_supplt_pie)
					.findViewById(R.id.slidingDrawer_content_ll)
					.findViewById(R.id.row_linear2)
					.findViewById(R.id.image_button_6));
			// imageButtons.add((ImageButton) findViewById(
			// R.id.framelayout_supplt_pie)
			// .findViewById(R.id.slidingDrawer_content_ll)
			// .findViewById(R.id.row_linear2)
			// .findViewById(R.id.image_button_7));
			imageButtons.add((ImageButton) findViewById(
					R.id.framelayout_supplt_pie)
					.findViewById(R.id.slidingDrawer_content_ll)
					.findViewById(R.id.row_linear2)
					.findViewById(R.id.image_button_8));
			imageButtons.add((ImageButton) findViewById(
					R.id.framelayout_supplt_pie)
					.findViewById(R.id.slidingDrawer_content_ll)
					.findViewById(R.id.row_linear2)
					.findViewById(R.id.image_button_9));

			DIMENSIONALITY_TYPE_DES.put("projecttype", "项目类型");
			DIMENSIONALITY_TYPE_DES.put("daili", "代理商");
			DIMENSIONALITY_TYPE_DES.put("gangchang", "钢厂");
			DIMENSIONALITY_TYPE_DES.put("banshichu", "办事处");
			DIMENSIONALITY_TYPE_DES.put("guixing", "轨型");
			// DIMENSIONALITY_TYPE_DES.put("caizhi", "材质");
			// DIMENSIONALITY_TYPE_DES.put("sudu", "速度");
			DIMENSIONALITY_TYPE_DES.put("changdu", "长度");
			DIMENSIONALITY_TYPE_DES.put("rechuli", "热处理");

			picMap.put("projecttype", R.drawable.project);
			picMap.put("daili", R.drawable.dailishang);
			picMap.put("gangchang", R.drawable.steel);
			picMap.put("banshichu", R.drawable.banshichu);
			picMap.put("guixing", R.drawable.guixing);
			// picMap.put("caizhi", R.drawable.caizhi);
			// picMap.put("sudu", R.drawable.speed);
			picMap.put("changdu", R.drawable.changdu);
			picMap.put("rechuli", R.drawable.rechuli);

			picPreMap.put("projecttype", R.drawable.project_pre);
			picPreMap.put("daili", R.drawable.dailishang_pre);
			picPreMap.put("gangchang", R.drawable.steel_pre);
			picPreMap.put("banshichu", R.drawable.banshichu_pre);
			picPreMap.put("guixing", R.drawable.guixing_pre);
			// picPreMap.put("caizhi", R.drawable.caizhi_pre);
			// picPreMap.put("sudu", R.drawable.speed_pre);
			picPreMap.put("changdu", R.drawable.changdu_pre);
			picPreMap.put("rechuli", R.drawable.rechuli_pre);

			for (int i = 0; i < imageButtons.size(); i++) {
				if (imageButtons.get(i).getVisibility() == View.VISIBLE) {
					imageButtons.get(i).setOnClickListener(this);
					imageButtons.get(i).setTag(DIMENSIONALITY_TYPE2[i]);
					String type = DIMENSIONALITY_TYPE2[i];
					for (int j = 0; j < selectDimensionality.size(); j++) {
						if (type.equals(selectDimensionality.get(j))) {
							imageButtons.get(i).setClickable(false);
							imageButtons.get(i).setEnabled(false);
							imageButtons.get(i).setBackgroundResource(
									picPreMap.get(type));
							break;
						}
					}
				}
			}

			if (isShare) {
				currentDimensionality = getIntent().getStringExtra(
						PieChartActivity.SHARELINK_CURRENTDIMENSIONALITY);
				paramValues[14] = new String[] { currentDimensionality };
			} else {
				if (isFirst) {
					// 非配置量的、首次打开。默认项目类型维度
					currentDimensionality = "projecttype";
					paramValues[14] = new String[] { "projecttype" };
				} else {
					// // 设定，默认维度。上一次添加的限定维度的最后一个
					// currentDimensionality = selectDimensionality
					// .get(selectDimensionality.size() - 1);
					// paramValues[14] = new String[] { selectDimensionality
					// .get(selectDimensionality.size() - 1) };

					// 按序排列，逻辑是这样的，可选维度是排序的。 例如： 项目类型，钢厂，办事处。 如果第一次选择的是钢厂
					// （之前没有选择过项目类型），选择一个钢厂后，默认按项目类型。之前选过项目类型的，默认按办事处。 依次类推。。
					for (int i = 0; i < DIMENSIONALITY_TYPE2.length; i++) {
						String type = DIMENSIONALITY_TYPE2[i];
						if (selectDimensionality.contains(type)) {
							continue;
						} else {
							currentDimensionality = type;
							paramValues[14] = new String[] { type };
							break;
						}

					}
				}
			}

			// 初始设定当前已经选择的维度（当前维度可自由切换）
			for (int n = 0; n < imageButtons.size(); n++) {
				if (imageButtons.get(n).getTag().toString()
						.equalsIgnoreCase(currentDimensionality)) {
					imageButtons.get(n).setSelected(true);
					imageButtons.get(n).setBackgroundResource(
							picPreMap.get(currentDimensionality));
				}
			}

		}
	}

	private void hideLoadingView() {
		mLoadingView.setVisibility(View.GONE);
		mLoadingView.stopLoading();
		
	}

	private void showLoadingView() {
		mLoadingView.setVisibility(View.VISIBLE);
		mLoadingView.startLoading();
		// mError.setVisibility(View.GONE);
		
	}

	public void postManParameter() {
		chartModel = new ChartListModel(this);
		chartModel.getDataFromServerByType(
				ChartListModel.TYPE_GETCHARTParameters, null, mReportId);
	}

	// 请求参数编辑函数
	private Object getOneChartDetailPostEntity() {
		MapActivity.OneChartDetailPostEntity oneChart = new MapActivity.OneChartDetailPostEntity();
		oneChart.reportGuid = mReportId;
		oneChart.parameterList = oneChartDetail;
		oneChart.parameterList[0].setValues(paramValues[0]);
		oneChart.parameterList[1].setValues(paramValues[1]);
		oneChart.parameterList[2].setValues(paramValues[2]);
		oneChart.parameterList[3].setValues(paramValues[3]);
		oneChart.parameterList[4].setValues(paramValues[4]);
		oneChart.parameterList[5].setValues(paramValues[5]);
		oneChart.parameterList[6].setValues(paramValues[6]);
		oneChart.parameterList[7].setValues(paramValues[7]);
		oneChart.parameterList[8].setValues(paramValues[8]);
		oneChart.parameterList[9].setValues(paramValues[9]);
		oneChart.parameterList[10].setValues(paramValues[10]);
		oneChart.parameterList[11].setValues(paramValues[11]);
		oneChart.parameterList[12].setValues(paramValues[12]);
		oneChart.parameterList[13].setValues(paramValues[13]);
		oneChart.parameterList[14].setValues(paramValues[14]);
		return oneChart;
	}

	private void initSlidingDrawerAndContent() {
		mDrawer = (SlidingDrawer) (findViewById(R.id.framelayout_supplt_pie)
				.findViewById(R.id.slidingDrawer_supplt_pie));
		mDrawer.toggle();
		rgTab = (RadioGroup) mDrawer.getContent().findViewById(
				R.id.tab_menu_pie);

		mDrawer.setEnabled(!isShare);
		if (isShare)
			mDrawer.lock();
		
		mDrawer.setOnDrawerOpenListener(new OnDrawerOpenListener() {
			@Override
			public void onDrawerOpened() {
				showLoadingView();
				// 请求数据
				chartModel.getDataFromServerByType(
						ChartListModel.TYPE_CHART_PIE,
						getOneChartDetailPostEntity());
			}
		});

		if (currentSelect == TON) {
			Drawable drawable = PieChartActivity.this.getResources()
					.getDrawable(R.drawable.btn_ton_pre);
			((RadioButton) rgTab.findViewById(R.id.rb_ton_pie))
					.setTextColor(Color.parseColor("#21BFF4"));
			((RadioButton) rgTab.findViewById(R.id.rb_ton_pie))
					.setCompoundDrawablesWithIntrinsicBounds(null, drawable,
							null, null);
		} else if (currentSelect == ROOT) {
			Drawable drawable3 = PieChartActivity.this.getResources()
					.getDrawable(R.drawable.btn_root_pre);
			((RadioButton) rgTab.findViewById(R.id.rb_root_pie))
					.setTextColor(Color.parseColor("#21BFF4"));
			((RadioButton) rgTab.findViewById(R.id.rb_root_pie))
					.setCompoundDrawablesWithIntrinsicBounds(null, drawable3,
							null, null);
		} else if (currentSelect == KM) {
			Drawable drawable6 = PieChartActivity.this.getResources()
					.getDrawable(R.drawable.btn_km_pre);
			((RadioButton) rgTab.findViewById(R.id.rb_km_pie))
					.setTextColor(Color.parseColor("#21BFF4"));
			((RadioButton) rgTab.findViewById(R.id.rb_km_pie))
					.setCompoundDrawablesWithIntrinsicBounds(null, drawable6,
							null, null);
		}

		rgTab.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.rb_ton_pie:
					// 设置当前选中 - 切换图片
					Drawable drawable = PieChartActivity.this.getResources()
							.getDrawable(R.drawable.btn_ton_pre);
					((RadioButton) group.findViewById(R.id.rb_ton_pie))
							.setTextColor(Color.parseColor("#21BFF4"));
					((RadioButton) group.findViewById(R.id.rb_ton_pie))
							.setCompoundDrawablesWithIntrinsicBounds(null,
									drawable, null, null);

					// 设置其他两项未选中图片
					Drawable drawable1 = PieChartActivity.this.getResources()
							.getDrawable(R.drawable.btn_root);
					((RadioButton) group.findViewById(R.id.rb_root_pie))
							.setTextColor(Color.parseColor("#707070"));
					((RadioButton) group.findViewById(R.id.rb_root_pie))
							.setCompoundDrawablesWithIntrinsicBounds(null,
									drawable1, null, null);

					// 设置其他两项未选中图片
					Drawable drawable2 = PieChartActivity.this.getResources()
							.getDrawable(R.drawable.btn_km);
					((RadioButton) group.findViewById(R.id.rb_km_pie))
							.setTextColor(Color.parseColor("#707070"));
					((RadioButton) group.findViewById(R.id.rb_km_pie))
							.setCompoundDrawablesWithIntrinsicBounds(null,
									drawable2, null, null);
					
					

					currentSelect = TON;

					if (title.equalsIgnoreCase("需求量")) { // 需求量
						mReportId = "97867af5-1a12-43b1-b710-dc782be7ce46"; // 吨
						// mReportId = "26535645-946a-4c5a-a9e5-b74cbd076929";
						// // 模拟

					} else if (title.equalsIgnoreCase("配置量")) {// 配置量
						mReportId = "7687ccd7-7126-4c7a-96c0-8dd37d4e1ea4"; // 吨
						// mReportId = "26535645-946a-4c5a-a9e5-b74cbd076929";
						// // 模拟
					} else if (title.equalsIgnoreCase("订货量")) { // 订货量
						mReportId = "9ef58558-bcb2-4529-8331-87755fea52c0"; // 吨
					} else if (title.equalsIgnoreCase("发货量")) { // 发货量
						mReportId = "4bdf960a-bd84-41e6-88bb-3852bf1d0e88"; // 吨
					}
					// 2.去服务端请求数据
					postManParameter();
					break;
				case R.id.rb_root_pie:
					
					// 设置当前选中 - 切换图片
					Drawable drawable3 = PieChartActivity.this.getResources()
							.getDrawable(R.drawable.btn_root_pre);
					((RadioButton) group.findViewById(R.id.rb_root_pie))
							.setTextColor(Color.parseColor("#21BFF4"));
					((RadioButton) group.findViewById(R.id.rb_root_pie))
							.setCompoundDrawablesWithIntrinsicBounds(null,
									drawable3, null, null);

					// 设置其他两项未选中图片
					Drawable drawable4 = PieChartActivity.this.getResources()
							.getDrawable(R.drawable.btn_ton);
					((RadioButton) group.findViewById(R.id.rb_ton_pie))
							.setTextColor(Color.parseColor("#707070"));
					((RadioButton) group.findViewById(R.id.rb_ton_pie))
							.setCompoundDrawablesWithIntrinsicBounds(null,
									drawable4, null, null);

					// 设置其他两项未选中图片
					Drawable drawable5 = PieChartActivity.this.getResources()
							.getDrawable(R.drawable.btn_km);
					((RadioButton) group.findViewById(R.id.rb_km_pie))
							.setTextColor(Color.parseColor("#707070"));
					((RadioButton) group.findViewById(R.id.rb_km_pie))
							.setCompoundDrawablesWithIntrinsicBounds(null,
									drawable5, null, null);

					currentSelect = ROOT;
					if (title.equalsIgnoreCase("需求量")) { // 需求量
						mReportId = "84dfbbba-afa7-4f5d-a867-faf3f256c199"; // 根
						// mReportId = "26535645-946a-4c5a-a9e5-b74cbd076929";
						// // 模拟
					} else if (title.equalsIgnoreCase("配置量")) {// 配置量
						mReportId = "91888a30-fcd7-46c6-a46d-4756ccd9a339"; // 根
						// mReportId = "26535645-946a-4c5a-a9e5-b74cbd076929";
						// // 模拟
					} else if (title.equalsIgnoreCase("订货量")) { // 订货量
						mReportId = "73e3a1a9-cace-45ea-bba3-843861fdf380"; // 根
					} else if (title.equalsIgnoreCase("发货量")) { // 发货量
						mReportId = "658d1fb6-03ba-42b4-9108-5b3d3a50bcdd"; // 根
					}
					// 2.去服务端请求数据
					postManParameter();

					break;
				case R.id.rb_km_pie:
					// 设置当前选中 - 切换图片
					Drawable drawable6 = PieChartActivity.this.getResources()
							.getDrawable(R.drawable.btn_km_pre);
					((RadioButton) group.findViewById(R.id.rb_km_pie))
							.setTextColor(Color.parseColor("#21BFF4"));
					((RadioButton) group.findViewById(R.id.rb_km_pie))
							.setCompoundDrawablesWithIntrinsicBounds(null,
									drawable6, null, null);

					// 设置其他两项未选中图片
					Drawable drawable7 = PieChartActivity.this.getResources()
							.getDrawable(R.drawable.btn_ton);
					((RadioButton) group.findViewById(R.id.rb_ton_pie))
							.setTextColor(Color.parseColor("#707070"));
					((RadioButton) group.findViewById(R.id.rb_ton_pie))
							.setCompoundDrawablesWithIntrinsicBounds(null,
									drawable7, null, null);

					// 设置其他两项未选中图片
					Drawable drawable8 = PieChartActivity.this.getResources()
							.getDrawable(R.drawable.btn_root);
					((RadioButton) group.findViewById(R.id.rb_root_pie))
							.setTextColor(Color.parseColor("#707070"));
					((RadioButton) group.findViewById(R.id.rb_root_pie))
							.setCompoundDrawablesWithIntrinsicBounds(null,
									drawable8, null, null);

					currentSelect = KM;
					if (title.equalsIgnoreCase("需求量")) { // 需求量
						mReportId = "ac0686fa-45ab-4b7b-9439-b3d5677c266b"; // 公里
					} else if (title.equalsIgnoreCase("配置量")) {// 配置量
						mReportId = "22418b03-ce90-4203-8599-ec4ed8c83c13"; // 公里
					} else if (title.equalsIgnoreCase("订货量")) { // 订货量
						mReportId = "4d67c633-1ea4-44ff-9d58-a4d5f42b9408"; // 公里
					} else if (title.equalsIgnoreCase("发货量")) { // 发货量
						mReportId = "e5fac344-2cc9-4b4a-be5d-1a36a3d3ab19"; // 公里
					}
					// 2.去服务端请求数据
					postManParameter();
					break;
				default:
					break;
				}
			}
		});
		
	}

	private void initCharMenu() {
		menuMultipleActions = (FloatingActionsMenu) pieChartLayout
				.findViewById(R.id.chart_menu_pie);

		menuMultipleActions.setVisibility(View.VISIBLE);
		

		item3 = new FloatingActionButton(this);
		item3.setTitle("明细数据");
		item3.setTag(3);
		item3.setIcon(R.drawable.btn_view);
		item3.setOnClickListener(this);
		menuMultipleActions.addButton(item3);

		if (!isShare) {

			item2 = new FloatingActionButton(this);
			item2.setTitle("查看图例详情");
			item2.setTag(2);
			item2.setIcon(R.drawable.btn_details);
			item2.setOnClickListener(this);
			menuMultipleActions.addButton(item2);

			item4 = new FloatingActionButton(this);
			item4.setTitle("分享");
			item4.setTag(4);
			item4.setIcon(R.drawable.btn_share);
			item4.setOnClickListener(this);
			menuMultipleActions.addButton(item4);
		}
		
		item = new FloatingActionButton(this);
		 item.setTitle("横竖屏切换");
		 item.setTag(1);
		 item.setIcon(R.drawable.btn_change);
		 item.setOnClickListener(this);
		 menuMultipleActions.addButton(item);
		findViewById(R.id.image_button_i).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						if (mExpanded) {
							menuMultipleActions.collapse();
							((ImageButton) v)
									.setBackgroundResource(R.drawable.baobiao_action_off);
							mExpanded = false;
						} else {
							menuMultipleActions.expand();
							((ImageButton) v)
									.setBackgroundResource(R.drawable.baobiao_action_on);
							mExpanded = true;
						}

					}
				});

	}

	// 获得整个图标的容器
	private void initPieChartRelativeLayout() {
		if (mDrawer == null)
			return;
		pieChartLayout = (RelativeLayout) mDrawer.getContent()
				.findViewById(R.id.main_content_pie)
				.findViewById(R.id.relative_layout_pie);
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

		// 返回按钮
		leftBackButton = (ImageButton) findViewById(R.id.title_left_button);
		leftBackButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finishWithAnimation();
			}
		});

		// 下拉菜单按钮
		functionPopMenu = new SystemMainTopRightPopMenu(this);
		functionPopMenu.setForTodo(); // 设计下拉菜单项
		// 右侧按钮
		rightMenuButton = (ImageButton) findViewById(R.id.title_right_image_button);
		rightMenuButton.setVisibility(View.VISIBLE);
		if (isShare)
			rightMenuButton.setVisibility(View.GONE);
		rightMenuButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				// TODO 回到首页按钮监听

				Intent intent = new Intent(PieChartActivity.this,
						CopyOfsupplyActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_left_in,
						R.anim.slide_right_out);

			}

		});
		// rightMenuButton.setImageResource(R.drawable.btn_selector_more);
		// rightMenuButton.setVisibility(View.GONE);
	}

	// public class PostEntity implements Serializable {
	// public String reportGuid;
	// public ReportResult[] parameterList;
	// }

	@Override
	public void onSuccess(int requestTypeId, Object result) {
		if (requestTypeId == ChartListModel.TYPE_GETCHARTParameters) {
			oneChartDetail = ((ReportResultInfo) result).getResult();
			showLoadingView();
			chartModel.getDataFromServerByType(ChartListModel.TYPE_CHART_PIE,
					getOneChartDetailPostEntity());
		} else if (requestTypeId == ChartListModel.TYPE_CHART_PIE) {// 返回具体报表绘图参数的处理逻辑
			pieChartResult = ((PieChartResultInfo) result).getResult();

			int item0Color = getIntent().getIntExtra("item0color", 0);

			if (item0Color != 0) {
				if (pieChartResult.getItems().length == 1) {
					pieChartResult.getItems()[0].setChartColor(item0Color);
				}
			}
			pieChart();
			hideLoadingView();
			
			//TODO yanixn
		// DecimalFormat df3  = new DecimalFormat("##.000");	
		// String s = df3.format(pieChartView.getCount());

		//tv_count.setText("合 计："+s);
		}

	}

	@Override
	public void onFail(int requestTypeId, int statusCode, String errorMsg,
			Object result) {
//		Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
	}

	private void imageButtonOnClick(View v) {
		if (!(v instanceof ImageButton))
			return;

		// 在提交的时候 把当前选择的这个 ImageButton 放到selectButtonArr里
		if (selectButtonArr == null)
			selectButtonArr = new ArrayList<ImageButton>();

		ImageButton imgBtn = (ImageButton) v;

		// 如果是当前选中的按钮，不做任何操作
		if (selectDimensionality.contains(imgBtn.getTag().toString()))
			return;
		// 遍历已经灰掉的按钮
		for (int i = 0; i < selectButtonArr.size(); i++) {
			if (imgBtn.getTag().toString() == selectButtonArr.get(i).getTag()) {
				return;
			}
		}

		// 否则选中当前按钮，把其他可选的按钮置为未选中状态
		for (int n = 0; n < imageButtons.size(); n++) {
			ImageButton btn = imageButtons.get(n);
			String tag = btn.getTag().toString();
			if (btn.isEnabled()) { // ||
									// tag.equalsIgnoreCase(currentDimensionality)
				imageButtons.get(n).setBackgroundResource(
						picMap.get(imageButtons.get(n).getTag().toString())
								.intValue());
			}
		}

		imgBtn.setBackgroundResource(picPreMap.get(imgBtn.getTag().toString())
				.intValue());
		// 把当前选中按钮的tag存住，用于之后请求数据
		currentDimensionality = imgBtn.getTag().toString();
		paramValues[14] = new String[] { currentDimensionality };
	}

	@Override
	public void onClick(View v) {
           
		if (v instanceof FloatingActionButton) {
			int tag = ((Integer) (v.getTag())).intValue();
			
			if (tag == 1) {
				if(this.getResources().getConfiguration().orientation ==Configuration.ORIENTATION_PORTRAIT){

					   setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
					 findViewById(R.id.layout_pie_timebar).setVisibility(View.GONE);
					 findViewById(R.id.tab_menu_pie).setVisibility(View.GONE);
					 findViewById(R.id.system_title_pie).setVisibility(View.GONE);
					 menuMultipleActions.removeButton(item2);
					 menuMultipleActions.removeButton(item3);
					 menuMultipleActions.removeButton(item4);
					pieChartView.get().setOnPieitemClickListener(null);
					mDrawer.lock();
					}
				if(this.getResources().getConfiguration().orientation ==Configuration.ORIENTATION_LANDSCAPE){

					   setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
					   findViewById(R.id.layout_pie_timebar).setVisibility(View.VISIBLE);
					   findViewById(R.id.tab_menu_pie).setVisibility(View.VISIBLE);
					   
					   findViewById(R.id.system_title_pie).setVisibility(View.VISIBLE);
					   menuMultipleActions.removeButton(item);
					   initCharMenu();
					   pieChartView.get().setOnPieitemClickListener(new IPieItemClickListener() {
							public void OnItemClick(int position) {

								if (pieChartResult != null
										&& pieChartResult.getItems() != null)
								// && pieChartResult.getItems().length > 1)
								{
									Intent i = new Intent(PieChartActivity.this,
											PieChartActivity.class);
									i.putExtra(PieChartActivity.PIE_BEGIN_TIME, beginTime);
									i.putExtra(PieChartActivity.PIE_END_TIME, endTime);
									i.putExtra(PieChartActivity.PIE_REPORT_ID, mReportId);
									i.putExtra(PieChartActivity.SELECT_TYPE, currentSelect);
									i.putExtra(PieChartActivity.BUILD_ID, buildID);
									i.putExtra(PieChartActivity.PROJECT_ID, projectID);

									String value = pieChartResult.getItems()[position]
											.getDataValue();

									//
									// 已经限定的维度(限定了具体维度为具体的值，该数组用来存储已经限定的维度）
									ArrayList<String> amid_selectDimensionality = new ArrayList<String>();
									// 已经限定的维度的对应值(限定了具体维度为具体的值，该数组用来存储已经限定的维度的具体值，于selectButton一一对应）
									ArrayList<String> amid_selectDimensionality_value = new ArrayList<String>();

									// 复制当前的条件，传入新的页面
									for (int d = 0; d < selectDimensionality.size(); d++) {
										amid_selectDimensionality.add(selectDimensionality
												.get(d));
										amid_selectDimensionality_value
												.add(selectDimensionality_value.get(d));
									}

									if (amid_selectDimensionality.size() == DIMENSIONALITY_TYPE_DES
											.size() - 1) {
										Toast.makeText(PieChartActivity.this, "已经到最后一级",
												Toast.LENGTH_LONG).show();
										return;
									}

									if (pieChartResult.getItems()[position].getDes() == null
											|| pieChartResult.getItems()[position].getDes()
													.trim().length() == 0) {
										Toast.makeText(
												PieChartActivity.this,
												"查询"
														+ DIMENSIONALITY_TYPE_DES
																.get(currentDimensionality)
														+ "为空，无法查询下级。", Toast.LENGTH_LONG)
												.show();
										return;
									}

									if (!amid_selectDimensionality
											.contains(currentDimensionality)) {
										amid_selectDimensionality
												.add(currentDimensionality);
										amid_selectDimensionality_value.add(value);
									} else {

										if (value != null && value.trim().length() > 0) {

											if (amid_selectDimensionality_value
													.get(amid_selectDimensionality_value
															.size() - 1).equalsIgnoreCase(
															value))
												return;
										}

										amid_selectDimensionality
												.remove(amid_selectDimensionality.size() - 1);
										amid_selectDimensionality
												.add(currentDimensionality);
										// 改变值
										amid_selectDimensionality_value
												.remove(amid_selectDimensionality_value
														.size() - 1);
										amid_selectDimensionality_value.add(value);
									}

									// if
									// (!selectDimensionality.contains(currentDimensionality))
									// {
									// selectDimensionality.add(currentDimensionality);
									// selectDimensionality_value.add(value);
									// } else {
									// selectDimensionality.remove(selectDimensionality.size()
									// - 1);
									// selectDimensionality.add(currentDimensionality);
									// // 改变值
									// selectDimensionality_value
									// .remove(selectDimensionality_value.size() - 1);
									// selectDimensionality_value.add(value);
									// }
									i.putExtra(PieChartActivity.DIMENSIONALITY_TYPE,
											amid_selectDimensionality);

									i.putExtra("title", title);
									i.putExtra(
											"datestr",
											((TextView) findViewById(R.id.supply_time_textview_pie))
													.getText().toString());

									int colorvalue = pieChartResult.getItems()[position]
											.getChartColor();
									i.putExtra("item0color", colorvalue);
									i.putExtra(PieChartActivity.DIMENSIONALITY_VALUE,
											amid_selectDimensionality_value);

									startActivityForResult(i, 0);
									// TODO
									pieChartView.get().destroyDrawingCache();
									((LinearLayout) findViewById(R.id.frame_chart_pie))
									.removeAllViews();
								}

							}
						});
					   mDrawer.unlock();

					}
				
			} else if (tag == 2) {
				
					
					
				if (pieChartResult != null && pieChartResult.getItems() != null ) {
					
					final CopyofPieDetailDialog dialog = new CopyofPieDetailDialog(this,
							R.style.mydialog, pieChartView.get());

					dialog.setViewValue("请选择图例数据", pieChartResult.getItems());
					if (!isShare) {

						dialog.setOnItemClick(new IItemCallBack() {

							@Override
							public void onItemClick(int s) {
								// TODO Auto-generated method stub

								int position = s;
								Intent i = new Intent(PieChartActivity.this,
										PieChartActivity.class);
								i.putExtra(PieChartActivity.PIE_BEGIN_TIME,
										beginTime);
								i.putExtra(PieChartActivity.PIE_END_TIME,
										endTime);
								i.putExtra(PieChartActivity.PIE_REPORT_ID,
										mReportId);
								i.putExtra(PieChartActivity.SELECT_TYPE,
										currentSelect);
								i.putExtra(PieChartActivity.BUILD_ID, buildID);
								i.putExtra(PieChartActivity.PROJECT_ID,
										projectID);

								String value = pieChartResult.getItems()[position]
										.getDataValue();

								//
								// 已经限定的维度(限定了具体维度为具体的值，该数组用来存储已经限定的维度）
								ArrayList<String> amid_selectDimensionality = new ArrayList<String>();
								// 已经限定的维度的对应值(限定了具体维度为具体的值，该数组用来存储已经限定的维度的具体值，于selectButton一一对应）
								ArrayList<String> amid_selectDimensionality_value = new ArrayList<String>();

								// 复制当前的条件，传入新的页面
								for (int d = 0; d < selectDimensionality.size(); d++) {
									amid_selectDimensionality
											.add(selectDimensionality.get(d));
									amid_selectDimensionality_value
											.add(selectDimensionality_value
													.get(d));
								}

								if (amid_selectDimensionality.size() == DIMENSIONALITY_TYPE_DES
										.size() - 1) {
									Toast.makeText(PieChartActivity.this,
											"已经到最后一级", Toast.LENGTH_LONG)
											.show();
									return;
								}

								if (pieChartResult.getItems()[position]
										.getDes() == null
										|| pieChartResult.getItems()[position]
												.getDes().trim().length() == 0) {
									Toast.makeText(
											PieChartActivity.this,
											"查询"
													+ DIMENSIONALITY_TYPE_DES
															.get(currentDimensionality)
													+ "为空，无法查询下级。",
											Toast.LENGTH_LONG).show();
									return;
								}

								if (!amid_selectDimensionality
										.contains(currentDimensionality)) {
									amid_selectDimensionality
											.add(currentDimensionality);
									amid_selectDimensionality_value.add(value);
								} else {

									if (value != null
											&& value.trim().length() > 0) {

										if (amid_selectDimensionality_value
												.get(amid_selectDimensionality_value
														.size() - 1)
												.equalsIgnoreCase(value))
											return;
									}

									amid_selectDimensionality
											.remove(selectDimensionality.size() - 1);
									amid_selectDimensionality
											.add(currentDimensionality);
									// 改变值
									amid_selectDimensionality_value
											.remove(amid_selectDimensionality_value
													.size() - 1);
									amid_selectDimensionality_value.add(value);
								}

								// if
								// (!selectDimensionality.contains(currentDimensionality))
								// {
								// selectDimensionality.add(currentDimensionality);
								// selectDimensionality_value.add(value);
								// } else {
								// selectDimensionality.remove(selectDimensionality.size()
								// - 1);
								// selectDimensionality.add(currentDimensionality);
								// // 改变值
								// selectDimensionality_value
								// .remove(selectDimensionality_value.size() -
								// 1);
								// selectDimensionality_value.add(value);
								// }
								i.putExtra(
										PieChartActivity.DIMENSIONALITY_TYPE,
										amid_selectDimensionality);

								i.putExtra("title", title);
								i.putExtra(
										"datestr",
										((TextView) findViewById(R.id.supply_time_textview_pie))
												.getText().toString());
								i.putExtra(
										PieChartActivity.DIMENSIONALITY_VALUE,
										amid_selectDimensionality_value);

								int colorvalue = pieChartResult.getItems()[position]
										.getChartColor();
								i.putExtra("item0color", colorvalue);

								startActivityForResult(i, 0);
								// TODO
								pieChartView.get().destroyDrawingCache();
								((LinearLayout) findViewById(R.id.frame_chart_pie))
								.removeAllViews();
								dialog.hide();
							}
						});
					}

					dialog.show();
				
			}
			} else if (tag == 3) {
				if (pieChartResult == null)
					return;

				tableChart();
				// if (pieOrTable == PIE) {
				// tableChart();
				// pieOrTable = TABLE;
				// } else if (pieOrTable == TABLE) {
				// pieChart();
				// pieOrTable = PIE;
				// }
			} else if (tag == 4) { // 分享

				ShareListener();
			}
			menuMultipleActions.collapse();

			((ImageButton) findViewById(R.id.image_button_i))
					.setBackgroundResource(R.drawable.baobiao_action_off);
			mExpanded = false;

		} else {
			switch (v.getId()) {
			default:
				imageButtonOnClick(v);
				break;
			}
		}

	}

	private void pieChart() {

		String strChartTitle = "";
		// 生成标题
		// 生成已经选择的维度值
		if (selectDimensionality_value != null
				&& selectDimensionality_value.size() > 0) {
			for (int i = 0; i < selectDimensionality_value.size(); i++) {
				if (i == 0)
					strChartTitle += selectDimensionality_value.get(i)
							.toString();
				else
					strChartTitle += ("-" + selectDimensionality_value.get(i)
							.toString());
			}
		}
		if (strChartTitle.trim().length() > 0) {
			if (!selectDimensionality.contains(currentDimensionality))
				strChartTitle += ("-" + DIMENSIONALITY_TYPE_DES
						.get(currentDimensionality));
		} else
			strChartTitle += DIMENSIONALITY_TYPE_DES.get(currentDimensionality);

		if (isShare) {
			strChartTitle = getIntent().getStringExtra(
					PieChartActivity.SHARE_TITLE);
		}

		int decimalCount = 3;

		DecimalFormat df  = new DecimalFormat("0.000");	
		
		if (currentSelect==ROOT){
			decimalCount =  0;

	
			df = new DecimalFormat("0");
			df.setMaximumFractionDigits(0);//显示小数位的最大字数
		}
		
			
		
		pieChartView = new WeakReference<CopyOfPieChartView>(	new CopyOfPieChartView(this, pieChartResult,
				getOneChartDetailPostEntity(), "", decimalCount));
		


		 String s = df.format(pieChartView.get().getCount());
		 tv_count.setText("合 计："+s);
		 
		 
		 CopyofPieDetailDialog lv_item = new CopyofPieDetailDialog(PieChartActivity.this,pieChartView.get());
			lv_item.setViewValue("",pieChartResult.getItems());
			  chartItems = lv_item.getViewValue();
			  lv_symbol.setAdapter(new DetailAdapter(chartItems,getApplicationContext()));
		if (!isShare){

			pieChartView.get().setOnPieitemClickListener(new IPieItemClickListener() {
				public void OnItemClick(int position) {

					if (pieChartResult != null
							&& pieChartResult.getItems() != null)
					// && pieChartResult.getItems().length > 1)
					{
						Intent i = new Intent(PieChartActivity.this,
								PieChartActivity.class);
						i.putExtra(PieChartActivity.PIE_BEGIN_TIME, beginTime);
						i.putExtra(PieChartActivity.PIE_END_TIME, endTime);
						i.putExtra(PieChartActivity.PIE_REPORT_ID, mReportId);
						i.putExtra(PieChartActivity.SELECT_TYPE, currentSelect);
						i.putExtra(PieChartActivity.BUILD_ID, buildID);
						i.putExtra(PieChartActivity.PROJECT_ID, projectID);

						String value = pieChartResult.getItems()[position]
								.getDataValue();

						//
						// 已经限定的维度(限定了具体维度为具体的值，该数组用来存储已经限定的维度）
						ArrayList<String> amid_selectDimensionality = new ArrayList<String>();
						// 已经限定的维度的对应值(限定了具体维度为具体的值，该数组用来存储已经限定的维度的具体值，于selectButton一一对应）
						ArrayList<String> amid_selectDimensionality_value = new ArrayList<String>();

						// 复制当前的条件，传入新的页面
						for (int d = 0; d < selectDimensionality.size(); d++) {
							amid_selectDimensionality.add(selectDimensionality
									.get(d));
							amid_selectDimensionality_value
									.add(selectDimensionality_value.get(d));
						}

						if (amid_selectDimensionality.size() == DIMENSIONALITY_TYPE_DES
								.size() - 1) {
							Toast.makeText(PieChartActivity.this, "已经到最后一级",
									Toast.LENGTH_LONG).show();
							return;
						}

						if (pieChartResult.getItems()[position].getDes() == null
								|| pieChartResult.getItems()[position].getDes()
										.trim().length() == 0) {
							Toast.makeText(
									PieChartActivity.this,
									"查询"
											+ DIMENSIONALITY_TYPE_DES
													.get(currentDimensionality)
											+ "为空，无法查询下级。", Toast.LENGTH_LONG)
									.show();
							return;
						}

						if (!amid_selectDimensionality
								.contains(currentDimensionality)) {
							amid_selectDimensionality
									.add(currentDimensionality);
							amid_selectDimensionality_value.add(value);
						} else {

							if (value != null && value.trim().length() > 0) {

								if (amid_selectDimensionality_value
										.get(amid_selectDimensionality_value
												.size() - 1).equalsIgnoreCase(
												value))
									return;
							}

							amid_selectDimensionality
									.remove(amid_selectDimensionality.size() - 1);
							amid_selectDimensionality
									.add(currentDimensionality);
							// 改变值
							amid_selectDimensionality_value
									.remove(amid_selectDimensionality_value
											.size() - 1);
							amid_selectDimensionality_value.add(value);
						}

						// if
						// (!selectDimensionality.contains(currentDimensionality))
						// {
						// selectDimensionality.add(currentDimensionality);
						// selectDimensionality_value.add(value);
						// } else {
						// selectDimensionality.remove(selectDimensionality.size()
						// - 1);
						// selectDimensionality.add(currentDimensionality);
						// // 改变值
						// selectDimensionality_value
						// .remove(selectDimensionality_value.size() - 1);
						// selectDimensionality_value.add(value);
						// }
						i.putExtra(PieChartActivity.DIMENSIONALITY_TYPE,
								amid_selectDimensionality);

						i.putExtra("title", title);
						i.putExtra(
								"datestr",
								((TextView) findViewById(R.id.supply_time_textview_pie))
										.getText().toString());

						int colorvalue = pieChartResult.getItems()[position]
								.getChartColor();
						i.putExtra("item0color", colorvalue);
						i.putExtra(PieChartActivity.DIMENSIONALITY_VALUE,
								amid_selectDimensionality_value);

						startActivityForResult(i, 0);
						// TODO
						pieChartView.get().destroyDrawingCache();
						((LinearLayout) findViewById(R.id.frame_chart_pie))
						.removeAllViews();
					}

				}
			});
		}

		if (pieChartView.get().isHasdata()) {
			LayoutParams params = new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

			((LinearLayout) findViewById(R.id.frame_chart_pie))
					.removeAllViews();
			((LinearLayout) findViewById(R.id.frame_chart_pie)).addView(
					pieChartView.get(), params);
			((TextView) findViewById(R.id.supply_time_textview_charttitle))
					.setText(strChartTitle);
			// pieChartLayout.addView(pieChartView,params);
		} else {
			((TextView) findViewById(R.id.supply_time_textview_charttitle))
					.setText("");
			// 无数据

			// <ImageView
			// android:id="@+id/imageview_nodataaview"
			// android:layout_width="wrap_content"
			// android:layout_height="wrap_content"
			// android:layout_gravity="center"
			// android:layout_marginBottom="10dp"
			// android:src="@drawable/nodata"
			// android:visibility="gone"/>

			ImageView nodataView = new ImageView(this);
			nodataView.setImageResource(R.drawable.nodata);

			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			params.setMargins(50,50, 50, 50);
			params.gravity = Gravity.CENTER;
			((LinearLayout) findViewById(R.id.frame_chart_pie))
					.removeAllViews();
			((LinearLayout) findViewById(R.id.frame_chart_pie)).addView(
					nodataView, params);
		}
	}

	private void tableChart() {
		// 改成展示Activity

		// LinearLayout ll = ((LinearLayout)
		// findViewById(R.id.frame_chart_pie));
		// TableChartDraw tableChart = new TableChartDraw(this);
		// tableChart.isLineColorSwap = false;
		// View v = tableChart.getTableChartWidthPieData(pieChartResult);
		//
		// LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
		// LinearLayout.LayoutParams.MATCH_PARENT,
		// LinearLayout.LayoutParams.MATCH_PARENT); // 创建 Linearlayout 布局参数
		// ll.removeAllViews();
		// ll.addView(v, params);

		String strChartTitle = "";
		// 生成标题
		// 生成已经选择的维度值
		if (selectDimensionality_value != null
				&& selectDimensionality_value.size() > 0) {
			for (int i = 0; i < selectDimensionality_value.size(); i++) {
				if (i == 0)
					strChartTitle += selectDimensionality_value.get(i)
							.toString();
				else
					strChartTitle += ("-" + selectDimensionality_value.get(i)
							.toString());
			}
		}
		if (strChartTitle.trim().length() > 0) {
			if (!selectDimensionality.contains(currentDimensionality))
				strChartTitle += ("-" + DIMENSIONALITY_TYPE_DES
						.get(currentDimensionality));
		} else
			strChartTitle += DIMENSIONALITY_TYPE_DES.get(currentDimensionality);

		if (isShare) {
			strChartTitle = getIntent().getStringExtra(
					PieChartActivity.SHARE_TITLE);
		}

		Intent i = new Intent(PieChartActivity.this, TableChartActivity.class);

		i.putExtra(PieChartActivity.PIE_DATA, pieChartResult);
		i.putExtra("title", title);
		i.putExtra("isshare", isShare);
		i.putExtra("datestr",
				((TextView) findViewById(R.id.supply_time_textview_pie))
						.getText().toString());
		i.putExtra("strChartTitle", strChartTitle);
		i.putExtra(PieChartActivity.SELECT_TYPE, currentSelect);
		startActivity(i);
		// TODO
	}

	private void ShareListener() {

		// 弹选择窗
		// 参数
		String strChartTitle = "";
		// 生成标题
		// 生成已经选择的维度值
		if (selectDimensionality_value != null
				&& selectDimensionality_value.size() > 0) {
			for (int i = 0; i < selectDimensionality_value.size(); i++) {
				if (i == 0)
					strChartTitle += selectDimensionality_value.get(i)
							.toString();
				else
					strChartTitle += ("-" + selectDimensionality_value.get(i)
							.toString());
			}
		}
		if (strChartTitle.trim().length() > 0) {
			if (!selectDimensionality.contains(currentDimensionality))
				strChartTitle += ("-" + DIMENSIONALITY_TYPE_DES
						.get(currentDimensionality));
		} else
			strChartTitle += DIMENSIONALITY_TYPE_DES.get(currentDimensionality);

		apiUrlTmp = paramValues[0][0] + "|" + paramValues[1][0] + "|"
				+ paramValues[2][0] + "|" + paramValues[3][0] + "|"
				+ paramValues[4][0] + "|" + paramValues[5][0] + "|"
				+ paramValues[6][0] + "|" + paramValues[7][0] + "|"
				+ paramValues[8][0] + "|" + paramValues[9][0] + "|"
				+ paramValues[10][0] + "|" + paramValues[11][0] + "|"
				+ paramValues[12][0] + "|" + paramValues[13][0] + "|"
				+ paramValues[14][0] + "|" + mReportId + "|" + title + "|"
				+ getIntent().getStringExtra("datestr") + "|" + strChartTitle
				+ "|" + currentSelect + "|fenxiang";
		// 设置分享参数
		shareLink = new ShareLink();
		shareLink.setTitle("分享供应动态" + title);
		shareLink.setDesc(title + ":" + strChartTitle);
		String loginUrl = PreferenceUtils.getOaLoginUrl();
		String apiDir = PreferenceUtils.getApiUrl();
		iconId = loginUrl + "/" + apiDir + "/MetroImage/sharereport.png";
		shareLink.setThumbnail(iconId);
		shareLink.setUrl(ServerUrlConstant.SERVER_BASE_URL()
				+ ServerUrlConstant.OA_GETDOCINFO_METHOD);

		AlertDialog.Builder builder = new AlertDialog.Builder(
				PieChartActivity.this);
		builder.setTitle("请选择分享位置");
		final String[] pos = { "联系人", "工作圈" };
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (curItem == 0) {
					apiUrl = "mm" + apiUrlTmp;
					shareLink.setAppUrl(apiUrl);
					MXAPI.getInstance(PieChartActivity.this).shareToChat(
							PieChartActivity.this, shareLink);
				} else {
					apiUrl = "nn" + apiUrlTmp;
					shareLink.setAppUrl(apiUrl);
					MXAPI.getInstance(PieChartActivity.this).shareToCircle(
							PieChartActivity.this, shareLink);
				}
				curItem = 0;
			}
		});
		builder.setSingleChoiceItems(pos, 0,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						curItem = which;
					}
				});
		builder.show();
	}

}
