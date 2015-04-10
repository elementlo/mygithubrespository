package net.wash8.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import net.wash8.R;
import net.wash8.customview.CustomTitle;

/**
 * Created by ncb-user on 2015/1/22.
 */
public class ShopActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        initView();
    }

    private void initView() {
        CustomTitle customTitle = (CustomTitle) findViewById(R.id.customTitle);
        customTitle.setIv_title_title("暂未上线");
        customTitle.setIv_left_button(R.drawable.icon_back);
        customTitle.getIv_left_button().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
