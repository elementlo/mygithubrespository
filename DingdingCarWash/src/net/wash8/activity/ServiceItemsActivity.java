package net.wash8.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import net.wash8.R;
import net.wash8.classbean.Service;
import net.wash8.customview.CustomTitle;
import net.wash8.database.MyCarDBHelper;
import net.wash8.helpers.JSON_Parser;
import net.wash8.helpers.TextFinal;
import net.wash8.helpers.appURLFinal;
import net.wash8.net.AsyncHttpClient;
import net.wash8.net.AsyncHttpResponseHandler;
import net.wash8.singleadaptar.ServiceListAdapter;

import java.util.List;

public class ServiceItemsActivity extends Activity {
    private ListView lv_serviceitems;
    private TextView tv_sumprice;

    private String[] serv_item = new String[]{"车外蜡水快洗", "整车精洗", "SONAX特级水晶打蜡", "磨泥打蜡", "内饰深度护理", "室内臭氧消毒", "虫胶沥青去除",
            "精细洗车+内饰护理", "磨泥打蜡+内饰护理", "深度护理套餐"};
    private List<Service> service;
    private static int[] tag = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    private String[] timecost = new String[]{"20分钟", "50分钟", "60分钟", "90-120分钟", "90分钟", "15分钟", "20分钟", "120分钟", "180分钟",
            "120分钟"};
    private static float sumprice;
    private float currentfee;
    public static boolean tag_oneyuan=false;

    private AsyncHttpClient httpClient;
    private static ServiceListAdapter servieadapter;
    private DataSetObserver observer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serviceitems);
        initView();
        selectNotOrdered();
        setServiceItem();
        lvClickListner();
    }

    private boolean selectNotOrdered() {
        MyCarDBHelper helper=new MyCarDBHelper(this);
        SQLiteDatabase database=helper.getReadableDatabase();
        Cursor cursor=database.rawQuery("select NotOrdered from userinfo", null);
        cursor.moveToFirst();
        String not_ordered=cursor.getString(cursor.getColumnIndex("NotOrdered"));
        Log.i("tag","NotOrdered"+not_ordered);
        if (not_ordered!=null){
            if (not_ordered.equals("true")){
                return true;
            }else {
                return false;
            }
        }else {
            return false;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (20 == requestCode && data != null) {
            int i = data.getIntExtra("position", -1);
            tag[i] = 1;
            float temporary_price = data.getFloatExtra("float temporary_price", -1);
            if (temporary_price != -1) {
                sumprice = temporary_price + currentfee;
                tv_sumprice.setText(sumprice + "");
            }
            //servieadapter.notifyDataSetChanged();
            servieadapter = new ServiceListAdapter(ServiceItemsActivity.this, service, serv_item,
                    tv_sumprice, tag, sumprice, observer);
            lv_serviceitems.setAdapter(servieadapter);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        Log.i("tag","destory");
        tag = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        sumprice = 0;
        super.onDestroy();
    }

    public void onClick(View view) {
        int sum_item = 0;
        for (int i = 0; i < tag.length; i++) {
            Log.i("tag", tag[i] + "");
            if (tag[i] == 1 && service != null) {
                sum_item = sum_item + Integer.valueOf(service.get(i).getService());
            }
        }
        Log.i("tag", sum_item + "");
        float sum_price = Float.valueOf(tv_sumprice.getText().toString());
        Log.i("tag",sum_price+"");
        if (sum_item > 0 && sum_price > 0) {
            Intent it_retnservice = new Intent();
            it_retnservice.putExtra("sum_item", sum_item);
            it_retnservice.putExtra("sum_price",sum_price);
            it_retnservice.putExtra("sele_item",tag);
            it_retnservice.putExtra("tag_oneyuan",tag_oneyuan);
            setResult(RESULT_OK, it_retnservice);
            this.finish();
        } else {
            Toast.makeText(this, "请选择您想要的洗车服务", Toast.LENGTH_SHORT).show();
        }
    }

    private void lvClickListner() {
        lv_serviceitems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it_servie = new Intent(ServiceItemsActivity.this, ServiceDetailActivity.class);
                it_servie.putExtra("timecost", timecost[position]);
                it_servie.putExtra("OriginalFee", service.get(position).getOriginalFee());
                currentfee = Float.valueOf(service.get(position).getCurrentFee());
                it_servie.putExtra("CurrentFee", service.get(position).getCurrentFee());
                it_servie.putExtra("position", position);
                float temporary_price = Float.valueOf(tv_sumprice.getText().toString());
                it_servie.putExtra("temporary_price", temporary_price);
                int select_position = 1;
                // Log.i("tag",temporary_price+"");
                if (tag[position] == 1) {
                    it_servie.putExtra("select_position", select_position);
                }
                if (position == 0) {
                    it_servie.putExtra("TITLE", TextFinal.LASHUIROUXI_TITLE);
                    it_servie.putExtra("CONTENT", TextFinal.LASHUIROUXI);
                } else if (position == 1) {
                    it_servie.putExtra("TITLE", TextFinal.ZHENGCHEJINGXI_TITLE);
                    it_servie.putExtra("CONTENT", TextFinal.ZHENGCHEJINGXI);
                } else if (position == 2) {
                    it_servie.putExtra("TITLE", TextFinal.SONAX_TITLE);
                    it_servie.putExtra("CONTENT", TextFinal.SONAX);
                } else if (position == 3) {
                    it_servie.putExtra("TITLE", TextFinal.MONIDALA_TITLE);
                    it_servie.putExtra("CONTENT", TextFinal.MONIDALA);
                } else if (position == 4) {
                    it_servie.putExtra("TITLE", TextFinal.SHENDUHULI_TITLE);
                    it_servie.putExtra("CONTENT", TextFinal.SHENDUHULI);
                } else if (position == 5) {
                    it_servie.putExtra("TITLE", TextFinal.CHOUYANGXIAODU_TITLE);
                    it_servie.putExtra("CONTENT", TextFinal.CHOUYANGXIAODU);
                } else if (position == 6) {
                    it_servie.putExtra("TITLE", TextFinal.LIQINGQUCHU_TITLE);
                    it_servie.putExtra("CONTENT", TextFinal.LIQINGQUCHU);
                } else if (position == 7) {
                    it_servie.putExtra("CONTENT", TextFinal.JINGXI_NEISHI);
                } else if (position == 8) {
                    it_servie.putExtra("CONTENT", TextFinal.MONIDALA_NEISHI);
                } else if (position == 9) {
                    it_servie.putExtra("CONTENT", TextFinal.SHENDUHULITAOCAN);
                }
                startActivityForResult(it_servie, 20);
            }
        });
    }

    private void setServiceItem() {
        observer = new DataSetObserver() {
            @Override
            public void onChanged() {
                Log.i("tag", "nihao");
                super.onChanged();
            }
        };
        Intent it_rec_sumprice=getIntent();
        sumprice=it_rec_sumprice.getFloatExtra("sele_sumprice",0);
        if(it_rec_sumprice.getIntArrayExtra("sele_item")!=null){
            tag=it_rec_sumprice.getIntArrayExtra("sele_item");
        }
        AsyHandler handler = new AsyHandler();
        httpClient.get(appURLFinal.SERVICE_PRICE, handler);
       if (sumprice!=0){
            servieadapter = new ServiceListAdapter(ServiceItemsActivity.this, service, serv_item,
                    tv_sumprice, tag, sumprice, observer);
            servieadapter.registerDataSetObserver(observer);
           tv_sumprice.setText(sumprice+"");
        }
    }

    class AsyHandler extends AsyncHttpResponseHandler {
        @Override
        public void onSuccess(String content) {
            super.onSuccess(content);
            Log.i("tag", content);
            service = JSON_Parser.parseJSON_Servicelist(content);
            if (selectNotOrdered()){
                service.get(0).setCurrentFee("1");
            }

            servieadapter = new ServiceListAdapter(ServiceItemsActivity.this, service, serv_item,
                    tv_sumprice, tag, sumprice, observer);
            servieadapter.registerDataSetObserver(observer);
            lv_serviceitems.setAdapter(servieadapter);
        }

        @Override
        public void onFailure(Throwable error, String content) {
            super.onFailure(error, content);
            Log.i("tag", content);
        }
    }

    private void initView() {
        httpClient = new AsyncHttpClient();
        httpClient.addHeader("Client-Secret", "db266d6766a7c79f1defd856caf1d877");
        tv_sumprice = (TextView) findViewById(R.id.tv_sumprice);
        lv_serviceitems = (ListView) findViewById(R.id.lv_serviceitems);
        CustomTitle customTitle = (CustomTitle) findViewById(R.id.customTitle);
        customTitle.setIv_title_title("服务项目");
        customTitle.setIv_left_button(R.drawable.icon_back);
        customTitle.getIv_left_button().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

}
