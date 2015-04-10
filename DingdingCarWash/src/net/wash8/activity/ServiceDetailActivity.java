package net.wash8.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import net.wash8.R;
import net.wash8.customview.CustomTitle;

/**
 * Created by ncb-user on 2014/12/30.
 */
public class ServiceDetailActivity extends Activity {
    private TextView tv_OriginalFee,tv_CurrentFee,tv_timecost,tv_serviceintroduct,tv_title_serviceintroduct;
    private CustomTitle customTitle;
    private Button btn_selectservice;
    private ImageView iv_ceshitupian;

    private String CurrentFee;

    private int position;
    private float temporary_price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicedetail);
        initView();
        setServiceData();
    }
    public void onClick(View view){
        Intent it_resultservice=new Intent();
        it_resultservice.putExtra("position",position);
        if (position==0&& CurrentFee.equals("1")){
            ServiceItemsActivity.tag_oneyuan=true;
        }
        if (temporary_price!=-1){
        it_resultservice.putExtra("float temporary_price",temporary_price);
        }
        setResult(RESULT_OK,it_resultservice);
        this.finish();
    }

    private void setServiceData() {
        Intent it_service=getIntent();
        String timecost=it_service.getStringExtra("timecost");
        String OriginalFee=it_service.getStringExtra("OriginalFee");
        CurrentFee=it_service.getStringExtra("CurrentFee");
        String title=it_service.getStringExtra("TITLE");
        String content=it_service.getStringExtra("CONTENT");
        temporary_price=it_service.getFloatExtra("temporary_price",-1);
        position=it_service.getIntExtra("position",-1);
        int select_position=it_service.getIntExtra("select_position",-1);
        if (select_position==1){
            btn_selectservice.setClickable(false);
            btn_selectservice.setText("已选择");
            btn_selectservice.setBackgroundResource(R.drawable.btn_washtime_select1);
            btn_selectservice.setTextColor(Color.parseColor("#99D0F1"));
        }
        if (timecost!=null){
            tv_timecost.setText(timecost);
        }
        if (OriginalFee!=null){
            tv_OriginalFee.setText("¥"+OriginalFee+"元");
        }
        if (CurrentFee!=null){
            tv_CurrentFee.setText("¥"+CurrentFee+"元");
        }
        if (title!=null){
            tv_title_serviceintroduct.setText(title);
        }
        if (content!=null){
            tv_serviceintroduct.setText(content);
        }
        switch (position){
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
    }

    private void initView() {
        customTitle= (CustomTitle) findViewById(R.id.customTitle);
        customTitle.setIv_title_title("详情");
        customTitle.setIv_left_button(R.drawable.icon_back);
        customTitle.getIv_left_button().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btn_selectservice= (Button) findViewById(R.id.btn_selectservice);
        tv_OriginalFee= (TextView) findViewById(R.id.tv_OriginalFee);
        tv_CurrentFee= (TextView) findViewById(R.id.tv_CurrentFee);
        tv_timecost= (TextView) findViewById(R.id.tv_timecost);
        tv_serviceintroduct= (TextView) findViewById(R.id.tv_serviceintroduct);
        tv_title_serviceintroduct= (TextView) findViewById(R.id.tv_title_serviceintroduct);
        iv_ceshitupian= (ImageView) findViewById(R.id.iv_ceshitupian);
    }
}
