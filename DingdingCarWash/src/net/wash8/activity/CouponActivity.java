package net.wash8.activity;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import net.wash8.R;
import net.wash8.classbean.Coupon;
import net.wash8.classbean.CouponItems;
import net.wash8.classbean.Coupons;
import net.wash8.customview.CustomTitle;
import net.wash8.database.MyCarDBHelper;
import net.wash8.helpers.Base64FromTo;
import net.wash8.helpers.JSON_Parser;
import net.wash8.helpers.appURLFinal;
import net.wash8.net.AsyncHttpClient;
import net.wash8.net.AsyncHttpResponseHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ncb-user on 2015/1/14.
 */
public class CouponActivity extends Activity {
    private CustomTitle customTitle;
    private ListView lv_coupon;

    private Coupons coupons;

    private AsyncHttpClient httpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon);
        initView();
        setCouponData();
    }

    private void setCouponData() {
        MyCarDBHelper helper=new MyCarDBHelper(this);
        SQLiteDatabase database=helper.getReadableDatabase();
        Cursor cursor=database.rawQuery("select ID from userinfo", null);
        cursor.moveToFirst();
        int userid=cursor.getInt(cursor.getColumnIndex("ID"));
        cursor.close();
        helper.close();
        httpClient.get(this,appURLFinal.COUPON_LIST+userid,new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
                Log.i("tag","ok"+content);
                if (content!=null){
                    coupons=JSON_Parser.parseJSON_Coupons(content);
                    SimpleAdapter adapter=new SimpleAdapter(CouponActivity.this,getData(coupons),R.layout
                            .listview_coupon,new String[]{"SerialNumber","ExpirationDate","Value"},new int[]{R.id.tv_coupon_no,R
                            .id.tv_expirationdate,R.id.tv_value});
                    lv_coupon.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Throwable error, String content) {
                super.onFailure(error, content);
                Log.i("tag",content);
                Toast.makeText(CouponActivity.this,"获取优惠卷失败,请重试",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<Map<String, String>> getData(Coupons coupons) {
        List<CouponItems> list=coupons.getItems();
        List<Map<String, String>> mapList= new ArrayList<Map<String, String>>();
        HashMap<String,String> hashMap;
        if (list!=null){
        for (int i=0;i<list.size();i++){
            hashMap=new HashMap<String, String>();
            hashMap.put("SerialNumber",list.get(i).getSerialNumber());
            String expirationDate =OrderItemActivity.formateTime(list.get(i).getExpirationDate(),2);
            hashMap.put("ExpirationDate",expirationDate);
            hashMap.put("Value", list.get(i).getValue() + "元");
            mapList.add(hashMap);
        }
        }
        return mapList;
    }

    private void initView() {
        customTitle = (CustomTitle) findViewById(R.id.customTitle);
        customTitle.setIv_title_title("优惠卷");
        customTitle.setIv_left_button(R.drawable.icon_back);
        customTitle.getIv_left_button().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        lv_coupon= (ListView) findViewById(R.id.lv_coupon);

        httpClient = new AsyncHttpClient();
        appURLFinal.addHttpHeader(httpClient);
    }
}
