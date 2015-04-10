package net.wash8.singleadaptar;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by ncb-user on 2014/12/12.
 */
public class MyPagerAdaptar extends PagerAdapter{
    private List<ImageView> iv_data;
    public MyPagerAdaptar(List<ImageView> iv_data){
        this.iv_data=iv_data;
    }
    @Override
    public int getCount() {
        return iv_data==null?0:iv_data.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(iv_data.get(position));
        return iv_data.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(iv_data.get(position));
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view==o;
    }
}
