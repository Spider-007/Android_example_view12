package com.htmitech.ztcustom.zt.view.zgtw;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleAdapter;


import com.example.ztcustom.R;

import java.util.ArrayList;
import java.util.HashMap;


@SuppressLint("NewApi")
public class MenuFragment extends Fragment {
	private MyGridView gridViewType;

	private SimpleAdapter adater1;

	private ArrayList<HashMap<String, Object>> mList1;

	private static final int listContent1[] = { R.string.hello_world,
			R.string.hello_world, R.string.hello_world, R.string.hello_world,
			R.string.hello_world, R.string.hello_world, R.string.hello_world,
			R.string.hello_world, R.string.hello_world, R.string.hello_world,
			R.string.hello_world, R.string.hello_world, R.string.hello_world };
	private static final int TYPE_NUM = 13;

	private int typeLastItem = 0;

	public MenuFragment() {
		// TODO Auto-generated constructor stub

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_menu_content, null);
		findView(rootView);
		return rootView;
	}

	private void findView(View rootView) {
		// TODO Auto-generated method stub
		initGridViewOne(rootView);

	}

	private void initGridViewOne(View rootView) {
		gridViewType = (MyGridView) rootView
				.findViewById(R.id.search_type);
		mList1 = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < TYPE_NUM; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("radioIcon", R.drawable.btn_off);
			map.put("radioText", getString(listContent1[i]));
			mList1.add(map);
		}
		adater1 = new SimpleAdapter(getActivity(), mList1,
				R.layout.search_grid_item, new String[] { "radioIcon",
						"radioText" }, new int[] { R.id.item_RadioImg,
						R.id.item_RadioText });
		if (gridViewType != null) {
			gridViewType.setAdapter(adater1);
			gridViewType.requestFocus();
			gridViewType.setOnItemClickListener(new MyTypeOnItemClick());
		}
	}

	private class MyTypeOnItemClick implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated methfod stub
			System.out.println("search_type");
			if (typeLastItem != position) {
				if (typeLastItem >= 0) {
					changeItemImg(adater1, typeLastItem, false);
				}
			}
			typeLastItem = position;
			changeItemImg(adater1, position, true);
			HashMap<String, Object> map = (HashMap<String, Object>) adater1
					.getItem(position);
			String typeStr = (String) map.get("radioText");
			System.out.println("Tyepe string:" + typeStr);
		}
	}

	private void changeItemImg(SimpleAdapter sa, int selectedItem, boolean isOn) {
		HashMap<String, Object> map = (HashMap<String, Object>) sa
				.getItem(selectedItem);
		if (isOn) {
			map.put("radioIcon", R.drawable.btn_on);
		} else {
			map.put("radioIcon", R.drawable.btn_off);
		}
		sa.notifyDataSetChanged();
	}

}
