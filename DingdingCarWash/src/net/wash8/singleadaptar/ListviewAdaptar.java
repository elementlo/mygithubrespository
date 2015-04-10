package net.wash8.singleadaptar;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import net.wash8.activity.HomeActivity;
import net.wash8.R;

/**
 * Created by ncb-user on 2014/12/12.
 */
public class ListviewAdaptar extends BaseAdapter {
    private Context con;
    private ImageView iv_lefticon;
    private TextView iv_itemtitle,iv_itemdetail;
    private TextView tv_unread;
    public ListviewAdaptar(HomeActivity con){
        this.con=con;
    }


    @Override
    public int getCount() {
        return 2;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View v=View.inflate(con, R.layout.listview_view_1th,null);
        iv_lefticon = (ImageView) v.findViewById(R.id.iv_lefticon);
        iv_itemdetail= (TextView) v.findViewById(R.id.iv_itemdetail);
        iv_itemtitle= (TextView) v.findViewById(R.id.iv_itemtitle);
        tv_unread= (TextView) v.findViewById(R.id.tv_unread);
        Typeface typeFace  = Typeface.createFromAsset(con.getAssets(),"fonts/heijianti.TTF");
        tv_unread.setTypeface(typeFace);
        if(position==0) {
            iv_lefticon.setBackgroundResource(R.drawable.eye_listitem);
            iv_itemdetail.setText("了解我们的洗车工具、流程");
            iv_itemtitle.setText("服务介绍");
        }
        else if (position==1){
            iv_lefticon.setBackgroundResource(R.drawable.arrow_left);
            iv_itemdetail.setText("限量特价洗、护套餐");
            iv_itemtitle.setText("特价套餐");
        }

        return v;
    }
}
