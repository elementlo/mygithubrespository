package net.wash8.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import net.wash8.R;
import net.wash8.customview.CustomTitle;

/**
 * Created by ncb-user on 2015/1/7.
 */
public class SexSelectActivity extends Activity {
    private CustomTitle customTitle;
    private Intent it_backsexsel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sexselet);
        initView();
    }
    public void onClick(View view){
        switch (view.getId()){
            case R.id.rl_nan:
                it_backsexsel.putExtra("sex","男");
                setResult(RESULT_OK,it_backsexsel);
                finish();
                break;
            case R.id.rl_nv:
                it_backsexsel.putExtra("sex","女");
                setResult(RESULT_OK,it_backsexsel);
                finish();
                break;
            case R.id.rl_qita:
                it_backsexsel.putExtra("sex","其他");
                setResult(RESULT_OK,it_backsexsel);
                finish();
                break;
        }
    }

    private void initView() {
        customTitle= (CustomTitle) findViewById(R.id.customTitle);
        customTitle.setIv_title_title("选择性别");
        customTitle.setIv_left_button(R.drawable.icon_back);
        customTitle.getIv_left_button().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        it_backsexsel=new Intent();
    }
}
