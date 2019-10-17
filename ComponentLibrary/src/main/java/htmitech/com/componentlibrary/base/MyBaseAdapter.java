package htmitech.com.componentlibrary.base;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class MyBaseAdapter<t> extends BaseAdapter {
    private List<t> datas;
    private Context context;
    public MyBaseAdapter(List<t> datas, Context context) {
        super();
        this.datas = datas;
        this.context = context;
    }
    @Override
    public int getCount() {
        return datas.size();
    }
 
    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }
 
    @Override
    public long getItemId(int position) {
        return position;
    }
 
    @Override
    public abstract View getView(int position, View arg1, ViewGroup arg2);

    public Context getContext(){
        return context;
    }
}
