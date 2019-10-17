package htmitech.com.componentlibrary.datepicker;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;


import java.util.Calendar;
import java.util.Date;

import htmitech.com.componentlibrary.R;
import htmitech.com.componentlibrary.datepicker.wheelview.OnWheelScrollListener;
import htmitech.com.componentlibrary.datepicker.wheelview.WheelView;
import htmitech.com.componentlibrary.datepicker.wheelview.adapter.ArrayWheelAdapter;
import htmitech.com.componentlibrary.datepicker.wheelview.adapter.NumericWheelAdapter;
import htmitech.com.componentlibrary.myEnum.TimeEnum;


/**
 * tony
 */
public class PopChooseTimeHelper {

    private Context context;
    private PopupWindow pop;
    private View view;
    private OnClickOkListener onClickOkListener;
    
    
//    private int mYear=2016;
	private int mMonth=6;
	private int mDay=13;
	
	private WheelView year;
	private WheelView month;
	private WheelView day;
	private WheelView time;
	private WheelView min;
	private WheelView sec;
	private WheelView seconds;
	private WheelView ampm;
	private WheelView week;
	private WheelView y_week;
	private String[] am_pm = new String[]{"上午","下午"} ;;
	private String[] weeks = new String []{"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};

	private TextView tv_show_time;
	
	public TimeEnum[] mTimeEnums;
	
	private Button btnOK,btnCancel;
	
	public void setTimeEnums(TimeEnum...enums){
		this.mTimeEnums = enums;
		setAttribute();
	}
	
    public PopChooseTimeHelper(Context context) {
        this.context = context;
        view = LayoutInflater.from(context).inflate(R.layout.wheel_date_picker, null);
        pop = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        initView();
        initPop();
        initData();
    }
	public PopupWindow  getPop(){
		return this.pop;
	}

    public void initView(){
    	btnOK = (Button) view.findViewById(R.id.btnOK);
    	btnCancel = (Button) view.findViewById(R.id.btnCancel);
    }

    private void initPop() {
        pop.setAnimationStyle(R.style.AnimBottom);
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
		//实例化一个ColorDrawable颜色为半透明
		//设置SelectPicPopupWindow弹出窗体动画效果
		pop.setAnimationStyle(R.style.AnimBottom);
//		//实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0x00000000);
		dw.setAlpha(100);
		pop.setBackgroundDrawable(dw);
        pop.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		WindowManager.LayoutParams lp = ((Activity)context).getWindow()
				.getAttributes();
		lp.alpha = 0.7f;
		((Activity)context).getWindow().setAttributes(lp);
    }
    
    public void setAttribute(){
    	if(mTimeEnums == null){
    		return;
    	}
    	for(TimeEnum mTimeEnum : mTimeEnums){
    		switch(mTimeEnum){
	    		case YEAR:
	    			year.setVisibility(View.VISIBLE);
	    			break;
	    		case MONTH:
	    			month.setVisibility(View.VISIBLE);
	    			break;
	    		case DAY:
	    			day.setVisibility(View.VISIBLE);
	    			break;
	    		case HOUR:
	    			min.setVisibility(View.VISIBLE);
	    			break;
	    		case SEX:
	    			sec.setVisibility(View.VISIBLE);
	    			break;
				case SECONDS:
					seconds.setVisibility(View.VISIBLE);
					break;
				case TIME:
					time.setVisibility(View.VISIBLE);
					tv_show_time.setVisibility(View.GONE);
					break;
				case AMPM:
					ampm.setVisibility(View.VISIBLE);
					break;
				case WEEK:
					week.setVisibility(View.VISIBLE);
					break;
				case Y_WEEK:
					y_week.setVisibility(View.VISIBLE);
					break;
    		}
    			
    	}
    	
    	int n_year = year.getCurrentItem() + 1990;//年
		int n_month = month.getCurrentItem() + 1;//月
		int n_data = day.getCurrentItem() + 1; // 日
		int n_min = min.getCurrentItem()+1; // 时
		int n_sec = sec.getCurrentItem()+1; //分
		int n_seconds = seconds.getCurrentItem()+1; //秒
		int n_y_week = y_week.getCurrentItem()+1;//周
		String n_ap_pm = am_pm[ampm.getCurrentItem()];
		String n_week= weeks[week.getCurrentItem()];
    	String showText = setText(n_year,n_month,n_data,n_min,n_sec,n_ap_pm,n_week,n_y_week,n_seconds);
		if(showText.substring(showText.length()-1).equals("-")){
			tv_show_time.setText(""+showText.substring(0, showText.length() -1));
		}else {
			tv_show_time.setText(""+showText);
		}
    }
    public void initData(){

		Calendar c = Calendar.getInstance();
		int norYear = c.get(Calendar.YEAR);
		int curMonth = c.get(Calendar.MONTH) + 1;//通过Calendar算出的月数要+1
		int curDate = c.get(Calendar.DATE);
		int apm = c.get(Calendar.AM_PM);
		int curWeek = c.get(Calendar.DAY_OF_WEEK);
		int curMin = c.get(Calendar.HOUR_OF_DAY);
		int curSec = c.get(Calendar.MINUTE);
		int curSeconds = c.get(Calendar.SECOND);

		Calendar cal = Calendar.getInstance();//这一句必须要设置，否则美国认为第一天是周日，而我国认为是周一，对计算当期日期是第几周会有错误
		cal.setFirstDayOfWeek(Calendar.MONDAY); // 设置每周的第一天为星期一
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);// 每周从周一开始
		cal.setMinimalDaysInFirstWeek(7); // 设置每周最少为7天
		cal.setTime(new Date());
		int curYWeeks=cal.get(Calendar.WEEK_OF_YEAR);
		int curYear = norYear;
//		int curMonth =mMonth+1;
//		int curDate = mDay;
		
		year = (WheelView) view.findViewById(R.id.year);
		NumericWheelAdapter numericWheelAdapter1=new NumericWheelAdapter(context,1990, norYear+50);
		numericWheelAdapter1.setLabel("年");
		year.setViewAdapter(numericWheelAdapter1);
		year.setCyclic(false);//是否可循环滑
		year.addScrollingListener(scrollListener);
		
		month = (WheelView) view.findViewById(R.id.month);
		NumericWheelAdapter numericWheelAdapter2=new NumericWheelAdapter(context,1, 12, "%02d"); 
		numericWheelAdapter2.setLabel("月");
		month.setViewAdapter(numericWheelAdapter2);
		month.setCyclic(false);
		month.addScrollingListener(scrollListener);
		
		day = (WheelView) view.findViewById(R.id.day);
		initDay(curYear,curMonth);
		day.setCyclic(false);
		day.addScrollingListener(scrollListener);

		week = (WheelView) view.findViewById(R.id.week);
		ArrayWheelAdapter<String> arrayWheelAdapter2=new ArrayWheelAdapter(context,weeks);
		week.setViewAdapter(arrayWheelAdapter2);
		week.setCyclic(false);//是否可循环滑
		week.addScrollingListener(scrollListener);
		
		time= (WheelView) view.findViewById(R.id.time);
		String[] times = {"未知","男","女"} ;
		ArrayWheelAdapter<String> arrayWheelAdapter=new ArrayWheelAdapter<String>(context,times );
		time.setViewAdapter(arrayWheelAdapter);
		time.setCyclic(false);
		time.addScrollingListener(scrollListener);

		ampm= (WheelView) view.findViewById(R.id.ampm);
		ArrayWheelAdapter<String> arrayWheelAdapter1=new ArrayWheelAdapter<String>(context,am_pm );
		ampm.setViewAdapter(arrayWheelAdapter1);
		ampm.setCyclic(false);
		ampm.addScrollingListener(scrollListener);
		
		min = (WheelView) view.findViewById(R.id.min);
		NumericWheelAdapter numericWheelAdapter3=new NumericWheelAdapter(context,1, 23, "%02d"); 
		numericWheelAdapter3.setLabel("时");
		min.setViewAdapter(numericWheelAdapter3);
		min.setCyclic(false);
		min.addScrollingListener(scrollListener);

		sec = (WheelView) view.findViewById(R.id.sec);
		NumericWheelAdapter numericWheelAdapter4=new NumericWheelAdapter(context,0, 59, "%02d");
		numericWheelAdapter4.setLabel("分");
		sec.setViewAdapter(numericWheelAdapter4);
		sec.setCyclic(false);
		sec.addScrollingListener(scrollListener);

		seconds = (WheelView) view.findViewById(R.id.seconds);
		NumericWheelAdapter numericWheelAdapter6=new NumericWheelAdapter(context,1, 59, "%02d");
		numericWheelAdapter6.setLabel("秒");
		seconds.setViewAdapter(numericWheelAdapter6);
		seconds.setCyclic(false);
		seconds.addScrollingListener(scrollListener);

		y_week = (WheelView) view.findViewById(R.id.y_week);
		NumericWheelAdapter numericWheelAdapter5=new NumericWheelAdapter(context,1, 53, "%02d");
		numericWheelAdapter5.setLabel("周");
		y_week.setViewAdapter(numericWheelAdapter5);
		y_week.setCyclic(false);
		y_week.addScrollingListener(scrollListener);
		


		year.setVisibleItems(6);//设置显示行数
		month.setVisibleItems(6);
		day.setVisibleItems(6);
//		time.setVisibleItems(7);
		min.setVisibleItems(6);
		sec.setVisibleItems(6);
		seconds.setVisibleItems(6);
		week.setVisibleItems(6);
		y_week.setVisibleItems(6);

		year.setCurrentItem(curYear - 1990);
		month.setCurrentItem(curMonth - 1);
		day.setCurrentItem(curDate - 1);
		min.setCurrentItem(curMin-1);
		sec.setCurrentItem(curSec-1);
		seconds.setCurrentItem(curSeconds-1);
		ampm.setCurrentItem(apm);
		week.setCurrentItem(curWeek-1);
		y_week.setCurrentItem(curYWeeks-1);

		
		tv_show_time = (TextView) view.findViewById(R.id.tv_show_time);
		
		btnOK.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(onClickOkListener != null){
					if(tv_show_time.getText().toString().trim().substring(tv_show_time.getText().toString().trim().length()-1).equals("-")){
						onClickOkListener.onClickOk(""+tv_show_time.getText().toString().trim().substring(0, tv_show_time.getText().toString().trim().length() -1));
					}else {
						onClickOkListener.onClickOk(""+tv_show_time.getText().toString());
					}

				}
				pop.dismiss();
				WindowManager.LayoutParams lp = ( (Activity)context).getWindow().getAttributes();
				lp.alpha = 1f;
				((Activity)context).getWindow().setAttributes(lp);
			}
		});
		btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(onClickOkListener != null){
					onClickOkListener.onClickOk("");
				}
				WindowManager.LayoutParams lp = ( (Activity)context).getWindow().getAttributes();
				lp.alpha = 1f;
				((Activity)context).getWindow().setAttributes(lp);
				pop.dismiss();
			}
		});

		pop.setOnDismissListener(new PopupWindow.OnDismissListener() {

			@Override
			public void onDismiss() {
				WindowManager.LayoutParams lp =( (Activity)context).getWindow().getAttributes();
				lp.alpha = 1f;
				( (Activity)context).getWindow().setAttributes(lp);
			}
		});


//		btnCancel.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				pop.dismiss();
//			}
//		});
    }
    OnWheelScrollListener scrollListener = new OnWheelScrollListener() {
		@Override
		public void onScrollingStarted(WheelView wheel) {
		}

		@Override
		public void onScrollingFinished(WheelView wheel) {
			if(!tv_show_time.isShown()){
				if(wheel.getViewAdapter() instanceof ArrayWheelAdapter){
					tv_show_time.setText(""+((ArrayWheelAdapter) wheel.getViewAdapter()).getItemText(wheel.getCurrentItem()));
				}else {
					tv_show_time.setText(""+((NumericWheelAdapter) wheel.getViewAdapter()).getItemText(wheel.getCurrentItem()));
				}
				return;
			}
			int n_year = year.getCurrentItem() + 1990;//年
			int n_month = month.getCurrentItem() + 1;//月
			int days = initDay(n_year, n_month);
			int n_data = day.getCurrentItem() + 1; // 日
			int n_min = min.getCurrentItem()+1 ; // 时
			int n_sec = sec.getCurrentItem() ; //分
			int n_seconds = seconds.getCurrentItem()+1 ; //秒
			int n_y_week = y_week.getCurrentItem() + 1; //分
			String n_ap_pm = am_pm[ampm.getCurrentItem()];
			String n_week = weeks[week.getCurrentItem()];
			String showText = setText(n_year,n_month,n_data > days ? days : n_data,n_min,n_sec,n_ap_pm,n_week,n_y_week,n_seconds);
			tv_show_time.setText(""+showText);


		}
	};
	
	/**
	 * 设置显示时间
	 * @param year
	 * @param month
	 * @param data
	 * @param min
	 * @param sec
	 * @return
	 */
	public String setText(int year,int month,int data,int min,int sec,String am_pm,String week,int y_week,int seconds){
		StringBuffer sb = new StringBuffer();
    	if(mTimeEnums == null){
    		return "";
    	}
    	for(TimeEnum mTimeEnum : mTimeEnums){
    		switch(mTimeEnum){
	    		case YEAR:
					if(mTimeEnums.length<=1){
						sb.append(year+"年");
					}else {
						sb.append(year+"-");
					}
	    			break;
	    		case MONTH:
					if(mTimeEnums.length<=2){
						sb.append(month<10?"0"+month+"":month+"");
					}else {
						sb.append(month<10?"0"+month+"-":month+"-");
					}
	    			break;
	    		case DAY:
	    			sb.append(data<10?"0"+data:data);
	    			break;
	    		case HOUR:
	    			sb.append(" "+((min+"").length() == 1 ? ("0"+min) : min)+":");
	    			break;
	    		case SEX:
					if(mTimeEnums.length<=5){
						sb.append(((sec+"").length() == 1 ? ("0"+sec) : sec));
					}else {
						sb.append(((sec+"").length() == 1 ? ("0"+sec) : sec)+":");
					}

	    			break;
				case SECONDS:
					sb.append(((seconds+"").length() == 1 ? ("0"+seconds) : seconds));
					break;
				case AMPM:
					sb.append("  "+am_pm);
					break;
				case WEEK:
					sb.append("  "+week);
					break;
				case Y_WEEK:
					sb.append("  "+y_week+"周");
					break;
    		}
    			
    	}
    	return sb.toString();
    }
	private int initDay(int arg1, int arg2) {
		int days = getDay(arg1, arg2);
		NumericWheelAdapter numericWheelAdapter=new NumericWheelAdapter(context,1, days, "%02d");
		numericWheelAdapter.setLabel("日");
		day.setViewAdapter(numericWheelAdapter);
		return days;
	}
	/**
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	private int getDay(int year, int month) {
		int day = 30;
		boolean flag = false;
		switch (year % 4) {
		case 0:
			flag = true;
			break;
		default:
			flag = false;
			break;
		}
		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			day = 31;
			break;
		case 2:
			day = flag ? 29 : 28;
			break;
		default:
			day = 30;
			break;
		}
		return day;
	}
    /**
     * 显示
     *
     * @param view
     */
    public void show(View view) {
        pop.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

	public boolean isShowing(){
		return pop.isShowing();
	}

	public void dismiss(){
		if(pop != null && isShowing() )
			pop.dismiss();
	}
    /**
     * 隐藏监听
     *
     * @param onDismissListener
     */
    public void setOnDismissListener(PopupWindow.OnDismissListener onDismissListener) {
        pop.setOnDismissListener(onDismissListener);
    }

    public void setOnClickOkListener(OnClickOkListener onClickOkListener) {
        this.onClickOkListener = onClickOkListener;
    }

    public interface OnClickOkListener {
        public void onClickOk(String birthday);
    }
	public interface OnClickCancelListener {
		public void onClickCancel();
	}

//	public void setYear(int year){
//		this.mYear = year;
////	}
	public void setMonth(int mMonth){
		this.mMonth= mMonth;
	}
	public void setDay(int mDay){
		this.mDay = mDay;
	}

}
