package net.wash8.singleadaptar;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import net.wash8.R;
import net.wash8.activity.WashTimeActivity;

import java.util.ArrayList;
import java.util.List;


public class WashTimeAdaptar extends BaseAdapter {
    private Context con;
    private TextView tv_zhongwu, tv_shijian;
    private String[] str;
    private Button btn_time_select;
    private int item_Click=-1;


    public WashTimeAdaptar(Context con, String[] str,int position) {
        this.con = con;
        this.str = str;
        if (position!=-1){
            item_Click=position;
        }
    }

    public int getCount() {
        return 12;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(con, R.layout.listview_view_washtime, null);
        initView(v);
        BtnClickListenner btnClickListenner=new BtnClickListenner(position,this);
        btn_time_select.setClickable(true);
        btn_time_select.setOnClickListener(btnClickListenner);
        if(position!=item_Click){
            btn_time_select.setBackgroundResource(R.drawable.background_btn);
            btn_time_select.setText("选择");
        }
        else{
            btn_time_select.setBackgroundResource(R.drawable.btn_washtime_select1);
            btn_time_select.setText("已选择");
            btn_time_select.setTextColor(Color.parseColor("#70CDE5"));
            WashTimeActivity.time_position=position;
        }
        onRefreshBtn(position);
        setListText(position);
        return v;
    }

    class BtnClickListenner implements View.OnClickListener {
        private int position;
        private WashTimeAdaptar washTimeAdaptar;
        public BtnClickListenner(int position,WashTimeAdaptar washTimeAdaptar){
            this.position=position;
            this.washTimeAdaptar=washTimeAdaptar;
        }
        @Override
        public void onClick(View view) {
            item_Click=position;
            washTimeAdaptar.notifyDataSetChanged();
            SharedPreferences sharetime=con.getSharedPreferences("washtime", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=sharetime.edit();
            editor.clear();
            editor.putInt("time",position);
            editor.commit();
        }

    }

    private void initView(View v) {
        tv_shijian = (TextView) v.findViewById(R.id.tv_shijian);
        tv_zhongwu = (TextView) v.findViewById(R.id.tv_zhongwu);
        btn_time_select = (Button) v.findViewById(R.id.btn_time_select);
    }

    private void setListText(int position) {
        if (position >= 1 && position < 9) {
            tv_shijian.setText("0" + position + ":00--0" + (position + 1) + ":00");

        } else if (position == 9) {
            tv_shijian.setText("09:00--10:00");
        } else if (position == 10) {
            tv_shijian.setText("10:00--11:00");
        } else if (position == 11) {
            tv_shijian.setText("11:00--00:00");
        }
        if (position > 5) {
            tv_zhongwu.setText("晚上");
        }
    }

    private void onRefreshBtn(int position) {
        int i;
        if (str != null) {
            for (i = 0; i < str.length; i++) {
                //Log.i("str", str[i]);
                if (Integer.parseInt(str[i])>=4) {
                    if (position == i) {
                        btn_time_select.setBackgroundResource(R.drawable.btn_washtime_select);
                        btn_time_select.setTextColor(Color.parseColor("#F3911F"));
                        btn_time_select.setText("已满");
                        btn_time_select.setClickable(false);
                    }
                }
            }
        }
    }
}
