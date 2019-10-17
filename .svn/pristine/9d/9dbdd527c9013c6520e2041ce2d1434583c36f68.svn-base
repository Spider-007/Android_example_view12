package widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.basewidgetlibrary.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import htmitech.com.componentlibrary.entity.AuthorInfo;

public class MyBaseAdapter extends BaseAdapter {

	Vector<AuthorInfo> vector = new Vector<AuthorInfo>();
	Context context;
	HashMap<Integer, Boolean> mcheckhm = new HashMap<Integer, Boolean>();
	
	private boolean IsMultiSelectUser;

	public MyBaseAdapter(Context context, Vector<AuthorInfo> temp, boolean isMultiSelectUser) {
		IsMultiSelectUser = isMultiSelectUser;
		this.context = context;
		vector.clear();
		vector.addAll(temp);
		this.notifyDataSetChanged();
	}

	public void setData(Vector<AuthorInfo> temp, boolean isMultiSelectUser) {
		IsMultiSelectUser = isMultiSelectUser;
		vector.clear();
		vector.addAll(temp);
		mcheckhm.clear();
		this.notifyDataSetChanged();
	}

	public int getCount() {
		return vector.size();
	}

	@Override
	public Object getItem(int arg0) {
		return vector.elementAt(arg0);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.nextperson_list_item, parent, false);
			holder.cb = (CheckBox) convertView.findViewById(R.id.nextperson_cb);
			holder.textview_name = (TextView) convertView
					.findViewById(R.id.personName);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.cb.setTag(position);
		holder.cb.setFocusable(false);
		holder.cb.setClickable(false);
//		holder.cb.setOnCheckedChangeListener(checkedChangeListener);
		holder.textview_name.setTag(position);

		AuthorInfo info = vector.elementAt(position);
		holder.cb.setChecked(mcheckhm.get(position) == null ? false : mcheckhm
				.get(position));
		holder.textview_name.setText(info.getUserName());
		return convertView;
	}

	class ViewHolder {
		private CheckBox cb;
		private TextView textview_name;
	}

	//
	// private int mrestobj;
	// private LayoutInflater mInflater;
	// private int mResource;
	// public List<? extends Map<String, ?>> mData;
	// private int[] mTo;
	// private String[] mFrom;
	// private HashMap<String, Boolean> mcheckhm;
	// private boolean misSetTag = false;
	//
	// private EditText edt;
	//
	// public UserInfo[] data;
	//
	// /**
	// *
	// */
	public Integer[] getCheckValues() {
		Integer[] mreturnValeu = null;
		if (mcheckhm != null) {
			ArrayList<Integer> mlist = new ArrayList<Integer>();
			for (int mkey : mcheckhm.keySet()) {
				if (mcheckhm.get(mkey) == true) {
					mlist.add(mkey);
				}
			}

			if (mlist.size() != 0) {
				mreturnValeu = new Integer[mlist.size()];
				mlist.toArray(mreturnValeu);
				mlist.clear();
			}
		}
		return mreturnValeu;
	}

	//
	// public void clearCheckValues() {
	//
	// }
	//
	// public RsSimpleAdapter(Context context,
	// List<? extends Map<String, ?>> data, int resource, String[] from,
	// int[] to, int restobj) {
	// super(context, data, resource, from, to);
	// mrestobj = restobj;
	// mData = data;
	// mResource = resource;
	// mFrom = from;
	// mTo = to;
	// mInflater = LayoutInflater.from(context);
	// }
	//
	// public RsSimpleAdapter(Context context,
	// List<? extends Map<String, ?>> data, int resource, String[] from,
	// int[] to, int restobj, boolean issettag, UserInfo[] dt) {
	// super(context, data, resource, from, to);
	// mrestobj = restobj;
	// mData = data;// list数据
	// mResource = resource;
	// mFrom = from;
	// mTo = to;
	// mInflater = LayoutInflater.from(context);
	// misSetTag = issettag;
	// this.data = dt;// 用户列表数据
	// }
	//
	// /**
	// */
	// public View getView(int position, View convertView, ViewGroup parent) {
	// System.out.println("position:" + position);
	// return createViewFromResource(position, convertView, parent, mResource);
	// }
	//
	// /**
	// * 设置item状态改变监听器
	// */
//	private CompoundButton.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {
//		public void onCheckedChanged(CompoundButton buttonView,
//				boolean isChecked) {
//			System.out.println("buttonView.getTag().toString():"
//					+ buttonView.getTag().toString());
//			
//			if (!IsMultiSelectUser){
//				 for(Integer p : mcheckhm.keySet()) {  
//					 mcheckhm.put(p, false);  
//	             }  
//			}
//			mcheckhm.put((Integer) buttonView.getTag(), isChecked);
//			
//			notifyDataSetChanged();
//		}
//	};

	public void setListItemCheck(int position ){
		View view = getView(position, null, null);
		CheckBox ck = (CheckBox)view.findViewById(R.id.nextperson_cb);
		
		if (IsMultiSelectUser){
			if(ck.isChecked() == true){
				 ck.setChecked(false);
			}else{
				 ck.setChecked(true);
			}
			mcheckhm.put(position, ck.isChecked());
		}else{
			if(ck.isChecked() == true){
				 for(Integer p : mcheckhm.keySet()) {  
					 mcheckhm.put(p, false);  
	             }  
				 ck.setChecked(false);
			}else{
				 for(Integer p : mcheckhm.keySet()) {  
					 mcheckhm.put(p, false);  
	             }  
				 ck.setChecked(true);
			}
			mcheckhm.put(position, ck.isChecked());
		}
		this.notifyDataSetChanged();
	}
	
	
	// private View createViewFromResource(int position, View convertView,
	// ViewGroup parent, int resource) {
	// ViewHolder holder = null;
	//
	// if (convertView == null) {
	// convertView = mInflater.inflate(resource, parent, false);
	// holder.cb = (CheckBox)convertView.findViewById(mrestobj);
	// holder.cb.setFocusable(false);
	// convertView.setTag(holder);
	// } else {
	// holder = (ViewHolder)convertView.getTag();
	// }
	// convertView.setFocusable(false);
	// bindView(position, convertView);
	//
	// if (mrestobj != 0) {
	// if (mcheckhm == null) {
	// mcheckhm = new HashMap<String, Boolean>();
	// }
	//
	// if (mv instanceof CheckBox) {
	// Object mIsChecked = mcheckhm.get((String) ((CheckBox) mv)
	// .getTag());
	// ((CheckBox) mv)
	// .setChecked((mIsChecked != null) ? (Boolean) mIsChecked
	// : false);
	// ((CheckBox) mv).setTag("" + position);
	// ((CheckBox) mv)
	// .setOnCheckedChangeListener(CheckedChangeListener);
	// }
	// }
	//
	// edt = (EditText) v.findViewById(R.id.searchEt);
	// return convertView;
	// }
	//
	// class ViewHolder {
	// private CheckBox cb;
	// }
	//
	// public EditText getEt() {
	// return edt;
	// }
	//
	// private void bindView(int position, View view) {
	// final Map<?, ?> dataSet = mData.get(position);// 填充的数据内容
	// if (dataSet == null) {
	// return;
	// }
	// final String[] from = mFrom;// map的键集
	// final int[] to = mTo;// view的ID
	// final int count = to.length;
	//
	// for (int i = 0; i < count; i++) {
	// final View v = view.findViewById(to[i]);
	// if (v != null) {
	// System.out.println("dataSet:" + dataSet);
	//
	// final Object data = dataSet.get(from[i]);
	// String text = data == null ? "" : data.toString();
	// v.setFocusable(false);
	// if (v instanceof Checkable) {
	// if (data instanceof Boolean) {
	// ((Checkable) v).setChecked((Boolean) data);
	// } else if (v instanceof TextView) {
	// if (misSetTag) {
	// ((CheckBox) v).setTag(text);
	// } else {
	// ((CheckBox) v).setTag("" + position);
	// setViewText((TextView) v, text);
	// }
	// } else {
	// throw new IllegalStateException(v.getClass().getName()
	// + " should be bound to a Boolean, not a "
	// + (data == null ? "<unknown type>"
	// : data.getClass()));
	// }
	// } else if (v instanceof TextView) {
	// setViewText((TextView) v, text);
	// } else if (v instanceof ImageView) {
	// if (data instanceof Integer) {
	// setViewImage((ImageView) v, (Integer) data);
	// } else {
	// setViewImage((ImageView) v, text);
	// }
	// } else {
	// throw new IllegalStateException(v.getClass().getName()
	// + " is not a "
	// + " view that can be bounds by this SimpleAdapter");
	// }
	// }
	// }
	// }
	//
}
