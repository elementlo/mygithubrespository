package net.wash8.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import net.wash8.R;
import net.wash8.customview.CustomTitle;
import net.wash8.helpers.TextFinal;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by lijie on 2015/1/12.
 */
public class SalePriceDetailActivity extends Activity {
    private String[] timeCosts = new String[]{"20分钟", "50分钟", "60分钟", "90-120分钟", "90分钟", "15分钟", "20分钟", "120分钟",
            "180分钟",
            "120分钟"};

    private CustomTitle customTitle;

    private TextView timeCost;
    private TextView originalFee;
    private TextView currentFee;
    private TextView title;
    private TextView content;
    private ImageView iv_ceshitupian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_detial);
        initView();
    }

    private void initView(){
        customTitle = (CustomTitle) findViewById(R.id.customTitle);
        customTitle.setIv_title_title("套餐详情");
        customTitle.setIv_left_button(R.drawable.icon_back);
        customTitle.getIv_left_button().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        timeCost = (TextView)findViewById(R.id.tv_timecost);
        originalFee = (TextView)findViewById(R.id.tv_originalFee);
        currentFee = (TextView)findViewById(R.id.tv_currentFee);
        title = (TextView)findViewById(R.id.tv_title);
        content = (TextView)findViewById(R.id.tv_content);
        iv_ceshitupian= (ImageView) findViewById(R.id.iv_ceshitupian);

        Intent intent = getIntent();
        try {
            JSONObject carservice = new JSONObject(intent.getStringExtra("CarService"));
            int index = intent.getIntExtra("index",0);
            timeCost.setText(timeCosts[index]);
            originalFee.setText(carservice.getString("OriginalFee"));
            currentFee.setText(carservice.getString("CurrentFee"));
            Map<String,String> serviceInfo = TextFinal.getSeviceInfomationByIndex(index);
            switch (index){
                case 0:
                    iv_ceshitupian.setBackgroundResource(R.drawable.lashuikuaixi);
                    break;
                case 1:
                    iv_ceshitupian.setBackgroundResource(R.drawable.zhengchejingxi);
                    break;
                case 2:
                    iv_ceshitupian.setBackgroundResource(R.drawable.dala);
                    break;
                case 3:
                    iv_ceshitupian.setBackgroundResource(R.drawable.monidala);
                    break;
                case 4:
                    iv_ceshitupian.setBackgroundResource(R.drawable.neishihuli);
                    break;
                case 5:
                    iv_ceshitupian.setBackgroundResource(R.drawable.chouyangxiaodu);
                    break;
                case 6:
                    iv_ceshitupian.setBackgroundResource(R.drawable.liqingquchu);
                    break;
                case 7:
                    iv_ceshitupian.setBackgroundResource(R.drawable.xichehuli);
                    break;
                case 8:
                    iv_ceshitupian.setBackgroundResource(R.drawable.dalahuli);
                    break;
                case 9:
                    iv_ceshitupian.setBackgroundResource(R.drawable.shenduhulitaocan);
                    break;
            }
            if(serviceInfo.containsKey("TITLE"))
                title.setText(serviceInfo.get("TITLE"));
            if(serviceInfo.containsKey("CONTENT"))
                content.setText(serviceInfo.get("CONTENT"));
        }catch (Exception e){

        }


    }
}
