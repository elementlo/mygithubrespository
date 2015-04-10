package net.wash8.singleadaptar;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import net.wash8.R;
import net.wash8.activity.PayPageActivity;
import net.wash8.activity.ServiceItemsActivity;
import net.wash8.classbean.Service;

import java.util.List;

/**
 * Created by ncb-user on 2014/12/29.
 */
public class ServiceListAdapter extends BaseAdapter {
    private Context con;
    private List<Service> data;
    private String[] service_item;
    private int[] tag;
    private float sumprice;

    private TextView tv_sumprice;

    private DataSetObserver observer;

    public ServiceListAdapter(Context con,List<Service> data,String[] service_item,TextView tv_sumprice,int[] tag,
                              float sumprice,DataSetObserver observer){
        this.con=con;
        this.data=data;
        this.service_item=service_item;
        this.tv_sumprice=tv_sumprice;
        this.tag=tag;
        this.sumprice=sumprice;
        this.observer=observer;
    }
    class Holder{
        TextView tv_service_price,tv_servicedetail,tv_yixuan;
        Button btn_service_select;
    }

    @Override
    public int getCount() {
        return data==null?0:data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       Holder holder=null;
        if(convertView==null){
            holder=new Holder();
            convertView=View.inflate(con, R.layout.listview_servieitem,null);
            holder.tv_service_price= (TextView) convertView.findViewById(R.id.tv_service_price);
            holder.tv_servicedetail= (TextView) convertView.findViewById(R.id.tv_servicedetail);
            holder.tv_yixuan= (TextView) convertView.findViewById(R.id.tv_yixuan);
            holder.btn_service_select= (Button) convertView.findViewById(R.id.btn_service_select);
            convertView.setTag(holder);
        }else {
            holder= (Holder) convertView.getTag();
        }
        if(tag[position]==0){
            holder.btn_service_select.setBackgroundResource(R.drawable.background_btn);
            holder.btn_service_select.setText("选择");
            holder.btn_service_select.setTextColor(Color.parseColor("#ffffff"));
            holder.tv_yixuan.setVisibility(View.INVISIBLE);
        }else {
            holder.btn_service_select.setBackgroundResource(R.drawable.btn_washtime_select1);
            holder.btn_service_select.setText("取消");
            holder.btn_service_select.setTextColor(Color.parseColor("#99D0F1"));
            holder.tv_yixuan.setVisibility(View.VISIBLE);
            observer.onChanged();
        }
/*        View v=View.inflate(con,R.layout.listview_servieitem,null);
        TextView tv_service_price= (TextView) v.findViewById(R.id.tv_service_price);
        TextView tv_servicedetail= (TextView) v.findViewById(R.id.tv_servicedetail);
        TextView tv_yixuan=(TextView) v.findViewById(R.id.tv_yixuan);
        Button btn_service_select= (Button) v.findViewById(R.id.btn_service_select);*/
        ButtonClickListner listner=new ButtonClickListner(holder.tv_yixuan,position);
        if(data!=null){
            holder.tv_service_price.setText(((int)Float.parseFloat(data.get(position).getCurrentFee()))+"元");
            holder.tv_servicedetail.setText(service_item[position]);
            holder.btn_service_select.setOnClickListener(listner);
        }
        return convertView;
    }
    class ButtonClickListner implements View.OnClickListener{
        private TextView textView;
        private int position;
        public ButtonClickListner(TextView textView,int position){
            this.textView=textView;
            this.position=position;
        }
        @Override
        public void onClick(View v) {
            Button btn= (Button) v;
            if(tag[position]==0) {
                if (position==0&&data.get(0).getCurrentFee().equals("1")){
                    ServiceItemsActivity.tag_oneyuan=true;
                }
                btn.setBackgroundResource(R.drawable.btn_washtime_select1);
                btn.setText("取消");
                btn.setTextColor(Color.parseColor("#99D0F1"));
                textView.setVisibility(View.VISIBLE);
                float i=Float.parseFloat(data.get(position).getCurrentFee());
                sumprice=sumprice+i;
                if(sumprice>=0){
                    tv_sumprice.setText(String.valueOf(sumprice));
                }else {
                    tv_sumprice.setText("出错,请退出页面重新选择");
                }
                tag[position]=1;
            }else {
                if (position==0&&data.get(0).getCurrentFee().equals("1")){
                    ServiceItemsActivity.tag_oneyuan=false;
                }
                btn.setBackgroundResource(R.drawable.background_btn);
                btn.setText("选择");
                btn.setTextColor(Color.parseColor("#ffffff"));
                textView.setVisibility(View.INVISIBLE);
                float i=Float.parseFloat(data.get(position).getCurrentFee());
                sumprice=sumprice-i;
                tv_sumprice.setText(String.valueOf(sumprice));
/*                if(sumprice>=0){
                    tv_sumprice.setText(String.valueOf(sumprice));
                }else {
                    tv_sumprice.setText("出错,请退出页面重新选择");
                }*/
                tag[position]=0;
            }
        }
    }
}
