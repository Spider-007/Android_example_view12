package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.example.schedulemanagerlibrary.R;

import java.util.ArrayList;


/**
 * Created by yanxin on 2017-9-20.
 */
public class SchedulePoiAdapter extends BaseAdapter {
    private final LayoutInflater inflater;
    public Context context;
    public ArrayList<PoiItem> dataList = new ArrayList<PoiItem>();

    public SchedulePoiAdapter(Context context, ArrayList<PoiItem> dataList) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        if(null != dataList)
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public PoiItem getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder = null;
        if(null == convertView){
            convertView = inflater.inflate(R.layout.activity_schedule_poi_item, null);
            mViewHolder = new ViewHolder();
            mViewHolder.tvPosition = (TextView)convertView.findViewById(R.id.tv_position);
            mViewHolder.tvAbout = (TextView)convertView.findViewById(R.id.tv_about);
            convertView.setTag(mViewHolder);
        }else{
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        mViewHolder.tvPosition.setText(dataList.get(position).toString());
        mViewHolder.tvAbout.setText(dataList.get(position).getSnippet());
        return convertView;
    }

    static class ViewHolder {
        TextView tvPosition;
        TextView tvAbout;

//        ViewHolder(View view) {
//            ButterKnife.inject(this, view);
//        }
    }
    public void setData(ArrayList<PoiItem> dataList){
        if(null == dataList){
            this.dataList.clear();
        }else{
            this.dataList = dataList;
        }

    }
}
