package net.wash8.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import net.wash8.R;
import net.wash8.activity.OrderItemActivity;
import net.wash8.activity.PayPageActivity;
import net.wash8.classbean.CouponItems;
import net.wash8.classbean.Coupons;
import net.wash8.entities.Coupon;

/**
 * Created by admin1 on 2015/1/15.
 */
public class ChooseCouponAdapter extends BaseAdapter{
    private PayPageActivity payPageActivity;
    private Coupons coupons;
    public ChooseCouponAdapter(PayPageActivity payPageActivity,Coupons coupons){
        this.payPageActivity = payPageActivity;
        this.coupons = coupons;
    }

    @Override
    public int getCount() {
        return coupons.getItems().size();
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
            view = LayoutInflater.from(payPageActivity).inflate(R.layout.listview_coupon_choose,null);
        final CouponItems couponItems = coupons.getItems().get(i);

        ((TextView) view.findViewById(R.id.tv_coupon_no)).setText(couponItems.getID());
        ((TextView) view.findViewById(R.id.tv_expirationdate)).setText(OrderItemActivity.formateTime(couponItems.getExpirationDate(), 2));
        ((TextView) view.findViewById(R.id.tv_value)).setText(couponItems.getValue()+"元");

        view.findViewById(R.id.btn_use_coupon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                payPageActivity.useCoupon(couponItems);
            }
        });
        return view;
    }
}
