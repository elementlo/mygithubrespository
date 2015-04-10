package net.wash8.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import net.wash8.R;
import net.wash8.adapter.SalePriceAdapter;
import net.wash8.adapter.ServiceIntroduceAdapter;
import net.wash8.classbean.Service;
import net.wash8.customview.CustomTitle;
import net.wash8.helpers.appURLFinal;
import net.wash8.net.AsyncHttpClient;
import net.wash8.net.AsyncHttpResponseHandler;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by lijie on 2015/1/12.
 */
public class SalePriceActivity extends Activity{
    private String[] servItem = new String[]{"车外蜡水快洗", "整车精洗", "SONAX特级水晶打蜡", "磨泥打蜡", "内饰深度护理", "室内臭氧消毒", "虫胶沥青去除",
            "精细洗车+内饰护理", "磨泥打蜡+内饰护理", "深度护理套餐"};

    private String[] price = new String[]{"0","0","0","0","0","0","0","0","0","0"};
    private String[] original_price=new String[]{"0","0","0","0","0","0","0","0","0","0"};

    private CustomTitle customTitle;

    private ListView serviceList;
    private SalePriceAdapter adapter;

    private JSONArray jsonPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_price);
        initView();

        getServicePrice();
    }

    private void initView(){
        customTitle = (CustomTitle) findViewById(R.id.customTitle);
        customTitle.setIv_title_title("特价套餐");
        customTitle.setIv_left_button(R.drawable.icon_back);
        customTitle.getIv_left_button().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        serviceList = (ListView)findViewById(R.id.lv_service_list);
        serviceList.setAdapter((adapter = new SalePriceAdapter(SalePriceActivity.this,servItem,price,original_price)));

        serviceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(jsonPrice == null){
                    Toast.makeText(SalePriceActivity.this,"正在获取价格信息",Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(SalePriceActivity.this,SalePriceDetailActivity.class);
                try {
                    intent.putExtra("CarService",jsonPrice.get(i).toString());
                    intent.putExtra("index",i);
                }catch (Exception e){
                    e.printStackTrace();
                }

                startActivity(intent);
            }
        });
    }

    private void getServicePrice(){
        AsyncHttpClient httpClient = new AsyncHttpClient();
        httpClient.get(appURLFinal.SERVICE_PRICE, new AsyncHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, String content) {
                super.onSuccess(statusCode, content);
                Log.i("tag",content+"------");
                try{
                    jsonPrice = new JSONArray(content);
                    for(int i = 0;i < jsonPrice.length();i++){
                        JSONObject jsonObject = jsonPrice.getJSONObject(i);
                        price[i] = jsonObject.getString("CurrentFee");
                        original_price[i]=jsonObject.getString("OriginalFee");
                    }
                    adapter.notifyDataSetInvalidated();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Throwable error, String content) {
                super.onFailure(error, content);

            }

        });
    }
}
