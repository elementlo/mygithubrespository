package net.wash8.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import net.wash8.R;
import net.wash8.customview.CustomTitle;
import net.wash8.helpers.HttpUtils;
import net.wash8.singleadaptar.ComparisonPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ComparisonActivity extends Activity {
    private CustomTitle customTitle;
    private ViewPager vp_comparison;

    private List<View> data;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comparison);
        getUrlData();
        initView();
        setData();
    }

    private List<String> getUrlData() {
        Intent it_recurl=getIntent();
        ArrayList<String> urllist = it_recurl.getStringArrayListExtra("comparison");
        id=it_recurl.getIntExtra("tag",-1);
        return urllist;
    }


    private void setData() {
        View view;
        for (int i = 0; i < 4; i++) {
            view = View.inflate(this, R.layout.viewpager_comparison, null);
            ImageView iv_before = (ImageView) view.findViewById(R.id.iv_before);
            ImageView iv_after = (ImageView) view.findViewById(R.id.iv_after);
            HttpUtils.getNetBytes(this, getUrlData().get(i),iv_before,false);
            HttpUtils.getNetBytes(this, getUrlData().get(i+4),iv_after,false);
            data.add(view);
        }
        ComparisonPagerAdapter pagerAdapter=new ComparisonPagerAdapter(data);
        vp_comparison.setAdapter(pagerAdapter);
        vp_comparison.setCurrentItem(id);
        vp_comparison.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                int No=i+1;
                customTitle.setIv_title_title("洗车前后对比 "+No+"/4");
            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void initView() {
        customTitle = (CustomTitle) findViewById(R.id.customTitle);
        vp_comparison = (ViewPager) findViewById(R.id.vp_comparison);
        customTitle.setIv_left_button(R.drawable.icon_back);
        customTitle.getIv_left_button().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        int No=id+1;
        customTitle.setIv_title_title("洗车前后对比 "+No+"/4");
        data = new ArrayList<View>();
    }

}
