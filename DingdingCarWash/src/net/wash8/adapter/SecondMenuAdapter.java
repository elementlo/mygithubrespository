package net.wash8.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import net.wash8.R;

import java.util.List;

/**
 * Created by admin1 on 2014/12/20.
 */
public class SecondMenuAdapter extends BaseAdapter {
    private String[] data;
    private Context context;

    public SecondMenuAdapter(Context context, String[] data){
        this.context = context;
        this.data = data;
    }
    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public Object getItem(int position) {
        return data[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.sencond_menu_item,null);
        TextView text = (TextView)convertView.findViewById(R.id.tv_text);
        text.setText(data[position].trim());
        return convertView;
    }
}
