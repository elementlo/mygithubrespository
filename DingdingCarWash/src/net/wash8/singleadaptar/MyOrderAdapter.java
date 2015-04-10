package net.wash8.singleadaptar;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import net.wash8.R;
import net.wash8.classbean.OrderItems;
import net.wash8.database.MyCarDBHelper;
import net.wash8.helpers.HttpUtils;
import net.wash8.helpers.appURLFinal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by ncb-user on 2015/1/12.
 */
public class MyOrderAdapter extends BaseAdapter {
    private Context con;
    private List<OrderItems> items;
    public MyOrderAdapter(Context con,List<OrderItems> items){
        this.con=con;
        this.items=items;
    }
    class Holder{
        private ImageView iv_showcar_carlist;
        private TextView tv_serviceitem,tv_cost,tv_createtime,tv_weipingjia;
        private RatingBar rating_rate;
    }
    @Override
    public int getCount() {
        return items==null?0:items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder=null;
        if (convertView==null){
            holder=new Holder();
            convertView=View.inflate(con, R.layout.listview_myorder,null);
            holder.iv_showcar_carlist= (ImageView) convertView.findViewById(R.id.iv_showcar_carlist);
            holder.tv_serviceitem= (TextView) convertView.findViewById(R.id.tv_serviceitem);
            holder.tv_cost= (TextView) convertView.findViewById(R.id.tv_cost);
            holder.rating_rate= (RatingBar) convertView.findViewById(R.id.rating_rate);
            holder.tv_createtime= (TextView) convertView.findViewById(R.id.tv_createtime);
            holder.tv_weipingjia= (TextView) convertView.findViewById(R.id.tv_weipingjia);
            convertView.setTag(holder);
        }else {
            holder= (Holder) convertView.getTag();
        }
        String url= appURLFinal.BASE_URL+items.get(position).getCar().getImage();
        HttpUtils.getNetBytes(con,url,holder.iv_showcar_carlist,true);
        String servicename=formateService(items.get(position).getServices());
        String time=items.get(position).getCreatedDateTime();
        holder.tv_createtime.setText(formateTime(time));
        holder.tv_serviceitem.setText(servicename);
        holder.tv_cost.setText(items.get(position).getFee());
        holder.rating_rate.setRating(0);
        holder.tv_weipingjia.setVisibility(View.INVISIBLE);
        if(items.get(position).getRating()!=null){
        holder.rating_rate.setRating(Float.valueOf(items.get(position).getRating().getValue()));
        }else {
            holder.tv_weipingjia.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    public static String formateTime(String str) {
        String time=str.substring(6,19);
        Log.i("tag",time);
        Date date=new Date(Long.valueOf(time));
        SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd");
        String formateTime=dateformat.format(date);
        return formateTime;
    }

    public static String formateService(String str) {
        int j=Integer.parseInt(str);
        String servicename="";
        String[] serviceitem=new String[]{"车外蜡水快洗","整车清洗","SONAX特级水晶打蜡","SONAX特级水晶打蜡","内饰深度护理","室内臭氧消毒","虫胶沥青去除",
                "车外精洗+内饰深度护理 套餐","特级漆面护理"};
        String[] servicenum=new String[]{"1","2","4","8","16","32","64","128","256"};
        for (int i=0;i<servicenum.length;i++){
            int num=Integer.parseInt(servicenum[i]);
            if((j&num)>0){
                if(servicename!=""){
                    servicename=servicename+"+";
                }
                servicename=servicename+serviceitem[i];
            }
        }
        return servicename;
    }
}
