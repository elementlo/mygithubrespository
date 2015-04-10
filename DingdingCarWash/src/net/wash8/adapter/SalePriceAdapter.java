package net.wash8.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import net.wash8.R;

/**
 * Created by lijie on 2015/1/12.
 */
public class SalePriceAdapter extends BaseAdapter {
    private Context context;
    private String[] service;
    private String[] price;
    private String[] original_price;

    public SalePriceAdapter(Context context,String [] service,String[] price,String[] original_price){
        this.context = context;
        this.service = service;
        this.price = price;
        this.original_price=original_price;
    }

    @Override
    public int getCount() {
        return service.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null)
            view = LayoutInflater.from(context).inflate(R.layout.adapter_sale_price_item,null);
        TextView content = (TextView)view.findViewById(R.id.tv_content);
        TextView priceView = (TextView)view.findViewById(R.id.tv_price);
        TextView tv_original_price= (TextView) view.findViewById(R.id.tv_original_price);

        content.setText(service[i]);
        priceView.setText(((int)Float.parseFloat(price[i]))+"元");
        tv_original_price.setText(((int)Float.parseFloat(original_price[i]))+"元");
        tv_original_price.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG);
        return view;
    }
}
