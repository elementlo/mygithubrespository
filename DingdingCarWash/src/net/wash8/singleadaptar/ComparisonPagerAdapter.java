package net.wash8.singleadaptar;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by ncb-user on 2015/1/13.
 */
public class ComparisonPagerAdapter extends PagerAdapter {
    private List<View> data;
    public ComparisonPagerAdapter(List<View> data){
        this.data=data;
    }
    @Override
    public int getCount() {
        return data==null?0:data.size();
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(data.get(position));
        return data.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(data.get(position));
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return  view==o;
    }
}
