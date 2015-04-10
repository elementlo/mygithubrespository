package net.wash8.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import net.wash8.R;
import net.wash8.classbean.MyOrder;
import net.wash8.customview.CustomTitle;
import net.wash8.database.MyCarDBHelper;
import net.wash8.helpers.Base64FromTo;
import net.wash8.helpers.JSON_Parser;
import net.wash8.helpers.appURLFinal;
import net.wash8.net.AsyncHttpClient;
import net.wash8.net.AsyncHttpResponseHandler;
import net.wash8.singleadaptar.MyOrderAdapter;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by ncb-user on 2015/1/9.
 */
public class MyOrderActivity extends Activity {
    private CustomTitle customTitle;
    private ListView lv_myorder;
    private ProgressBar pb_empty;

    private int id;
    private MyOrder myOrder;

    private AsyncHttpClient httpClient;
    private MyOrderAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myorder);
        initView();
        selectUserID();
        setMyOrderList();
        setListClicklistener();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (0==requestCode&&data!=null){
            setMyOrderList();
            Log.i("tag","refresh");
        }
    }

    private void setListClicklistener() {
        lv_myorder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it_orderitem=new Intent(MyOrderActivity.this,OrderItemActivity.class);
                if (myOrder!=null){
                    it_orderitem.putExtra("orderitem",myOrder.getItems().get(position));
                    startActivityForResult(it_orderitem, 0);
                }else {
                    Toast.makeText(MyOrderActivity.this,"获取订单失败,请重试",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String selectUserID() {
        MyCarDBHelper helper = new MyCarDBHelper(this);
        SQLiteDatabase database = helper.getReadableDatabase();
        Cursor cursor = database.rawQuery("select ID from userinfo", null);
        cursor.moveToFirst();
        id = cursor.getInt(cursor.getColumnIndex("ID"));
        cursor.close();
        helper.close();
        Log.i("tag", id + "");
        return id + "";
    }

    private void setMyOrderList() {
        String param = null;
        try {
            param = URLEncoder.encode("user=" + selectUserID(), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        httpClient.get(this, appURLFinal.SELECT_ORDER_LIST + param, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
                pb_empty.setVisibility(View.INVISIBLE);
                if (content != null) {
                    Log.i("tag", content);
                    myOrder = JSON_Parser.parseJSON_MyOrder(content);
                    if (myOrder != null) {
                        adapter = new MyOrderAdapter(MyOrderActivity.this, myOrder.getItems());
                        lv_myorder.setAdapter(adapter);
                    }
                }else {
                    Toast.makeText(MyOrderActivity.this,"你还没有订单",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable error, String content) {
                super.onFailure(error, content);
                Log.i("tag", content);
            }
        });
    }


    private void initView() {
        customTitle = (CustomTitle) findViewById(R.id.customTitle);
        customTitle.setIv_title_title("我的订单");
        customTitle.setIv_left_button(R.drawable.icon_back);
        customTitle.getIv_left_button().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        lv_myorder = (ListView) findViewById(R.id.lv_myorder);
        pb_empty= (ProgressBar) findViewById(R.id.pb_empty);
        httpClient = new AsyncHttpClient();
        appURLFinal.addHttpHeader(httpClient);
    }
}
