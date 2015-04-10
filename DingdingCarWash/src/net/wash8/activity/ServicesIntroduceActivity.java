package net.wash8.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import net.wash8.R;
import net.wash8.adapter.ServiceIntroduceAdapter;
import net.wash8.customview.CustomTitle;

/**
 * Created by lijie on 2015/1/12.
 */
public class ServicesIntroduceActivity extends Activity{

    private CustomTitle customTitle;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_introduce);
        initView();
    }

    private void initView(){
        customTitle = (CustomTitle) findViewById(R.id.customTitle);
        customTitle.setIv_title_title("服务介绍");
        customTitle.setIv_left_button(R.drawable.icon_back);
        customTitle.getIv_left_button().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        viewPager = (ViewPager)findViewById(R.id.viewpager);
        viewPager.setAdapter(new ServiceIntroduceAdapter(this));
    }
}
