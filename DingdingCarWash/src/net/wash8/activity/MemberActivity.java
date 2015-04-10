package net.wash8.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import net.wash8.R;
import net.wash8.customview.CustomTitle;

/**
 * Created by ncb-user on 2015/1/9.
 */
public class MemberActivity extends Activity {
    private CustomTitle customTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);
        initView();
    }

    public void onClick(View view){

        Intent intent = new Intent(this, RechargeRecodeActivity.class);

        startActivity(intent);
    }

    private void initView() {
        customTitle= (CustomTitle) findViewById(R.id.customTitle);
        customTitle.setIv_title_title("会员");
        customTitle.setIv_left_button(R.drawable.icon_back);
        customTitle.getIv_left_button().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
