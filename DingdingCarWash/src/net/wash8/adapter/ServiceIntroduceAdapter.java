package net.wash8.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ScrollView;
import net.wash8.R;

/**
 * Created by lijie on 2015/1/12.
 */
public class ServiceIntroduceAdapter extends PagerAdapter {
    private int[] images = {R.drawable.server_overview_01,
            R.drawable.server_overview_02,
            R.drawable.server_overview_03,
            R.drawable.server_overview_04,
            R.drawable.server_overview_05,
            R.drawable.server_overview_06,
            R.drawable.server_overview_07,
            R.drawable.server_overview_08,};

    private Context context;
    public ServiceIntroduceAdapter(Context context){
        this.context = context;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        // TODO Auto-generated method stub
        return arg0 == arg1;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View layout= LayoutInflater.from(context).inflate(R.layout.adapter_service_introduce_item,
                null);
        ImageView imageView = (ImageView) layout.findViewById(R.id.iv_image);
        imageView.setImageResource(images[position]);
        container.addView(layout);
        return layout;
    }



    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
    }
}
