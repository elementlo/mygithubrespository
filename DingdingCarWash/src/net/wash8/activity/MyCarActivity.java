package net.wash8.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import net.wash8.R;
import net.wash8.customview.CustomTitle;
import net.wash8.database.MyCarDBHelper;
import net.wash8.helpers.Base64FromTo;
import net.wash8.helpers.appURLFinal;
import net.wash8.net.AsyncHttpClient;
import net.wash8.singleadaptar.CarlistonBaseAdapter;
import net.wash8.singleadaptar.MyCarListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ncb-user on 2015/1/9.
 */
public class MyCarActivity extends Activity {
    private CustomTitle customTitle;
    private ImageView iv_back;
    private static ListView lv_carlist;

    private MyCarListAdapter carAdapter;
    private static AsyncHttpClient httpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycarlist);
        initView();
        setCarListOnBaseAdapter();
    }

    private void setCarListOnBaseAdapter(){
        carAdapter=new MyCarListAdapter(MyCarActivity.this,setCarlistOnData(this),httpClient,lv_carlist);
        if(carAdapter!=null){
            lv_carlist.setAdapter(carAdapter);
        }
    }

    public static List<Map<String, Object>> setCarlistOnData(Context context) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map;
        MyCarDBHelper dbHelper = new MyCarDBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int pic_color = -1;
        Cursor cursor = dbHelper.onQuery(db, "UserID","CAR_ID","Number", "Brand", "Model", "Image", "Color");
        while (cursor.moveToNext()) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                String UserID=cursor.getString(0);
                String CAR_ID=cursor.getString(1);
                String number = cursor.getString(2);
                String brand = cursor.getString(3);
                String model = cursor.getString(4);
                String image = cursor.getString(5);
                String color = cursor.getString(6);

                if ("0".equals(color)) {
                    pic_color = R.drawable.white_circle;
                } else if ("1".equals(color)) {
                    pic_color = R.drawable.black_circle;
                } else if ("2".equals(color)) {
                    pic_color = R.drawable.red_circle;
                } else if ("3".equals(color)) {
                    pic_color = R.drawable.blue_circle;
                } else if ("4".equals(color)) {
                    pic_color = R.drawable.sliver_gray_circle;
                } else if ("5".equals(color)) {
                    pic_color = R.drawable.dark_gray_circle;
                } else if ("6".equals(color)) {
                    pic_color = R.drawable.yellow_circle;
                } else if ("7".equals(color)) {
                    pic_color = R.drawable.green_circle;
                }else if ("8".equals(color)) {
                    pic_color = R.drawable.champagne_circle;
                }else if ("9".equals(color)) {
                    pic_color = R.drawable.coffee_circle;
                }else if ("10".equals(color)) {
                    pic_color = R.drawable.purple_circle;
                }else if ("11".equals(color)) {
                    pic_color = R.drawable.orange_circle;
                }
                map = new HashMap<String, Object>();
                map.put("UserID",UserID);
                map.put("CAR_ID",CAR_ID);
                map.put("Number", number);
                map.put("Brand", brand);
                map.put("Model", model);
                map.put("Image", image);
                map.put("Color", color);
                map.put("pic_color", pic_color);
                list.add(map);
            }
        }
        return list;
    }

    private void initView() {
        customTitle = (CustomTitle) findViewById(R.id.custom_title);
        customTitle.setIv_left_button(R.drawable.icon_back);
        iv_back = customTitle.getIv_left_button();
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        customTitle.setIv_title_title("我的车");
        lv_carlist = (ListView) findViewById(R.id.lv_carlist);
        httpClient=new AsyncHttpClient();
        appURLFinal.addHttpHeader(httpClient);
    }
}
