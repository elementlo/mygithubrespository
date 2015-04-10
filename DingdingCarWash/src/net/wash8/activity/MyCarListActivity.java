package net.wash8.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import net.wash8.R;
import net.wash8.customview.CustomTitle;
import net.wash8.database.MyCarDBHelper;
import net.wash8.helpers.Base64FromTo;
import net.wash8.helpers.HttpUtils;
import net.wash8.helpers.appURLFinal;
import net.wash8.net.AsyncHttpClient;
import net.wash8.singleadaptar.CarlistonBaseAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Lawliet on 2014/12/22.
 */
public class MyCarListActivity extends Activity {
    private static ListView lv_carlist;

    private CarlistonBaseAdapter onbaseAdapter;
    private static AsyncHttpClient httpClient;

    private static List<Map<String, Object>> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycarlist);
        initView();
        setCarListOnBaseAdapter();
        setCarlistListener();
    }

    private void setCarlistListener() {
        lv_carlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it_submitcar = new Intent();
                if (list != null) {
                    it_submitcar.putExtra("UserID", list.get(position).get("UserID").toString());
                    it_submitcar.putExtra("CAR_ID", list.get(position).get("CAR_ID").toString());
                    if (list.get(position).get("Image") != null) {
                        it_submitcar.putExtra("Image", list.get(position).get("Image").toString());
                    }
                    it_submitcar.putExtra("Number", list.get(position).get("Number").toString());
                    setResult(RESULT_OK, it_submitcar);
                }
                MyCarListActivity.this.finish();

            }
        });
    }

    private void setCarListOnBaseAdapter() {
        onbaseAdapter = new CarlistonBaseAdapter(MyCarListActivity.this, setCarlistOnData(this), httpClient, lv_carlist);
        lv_carlist.setAdapter(onbaseAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (20 == resultCode) {
            setCarListOnBaseAdapter();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public static List<Map<String, Object>> setCarlistOnData(Context context) {
        list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map;
        MyCarDBHelper dbHelper = new MyCarDBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int pic_color = -1;
        Cursor cursor = dbHelper.onQuery(db, "UserID", "CAR_ID", "Number", "Brand", "Model", "Image", "Color");
        while (cursor.moveToNext()) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                String UserID = cursor.getString(0);
                String CAR_ID = cursor.getString(1);
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
                map.put("UserID", UserID);
                map.put("CAR_ID", CAR_ID);
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
        CustomTitle customTitle = (CustomTitle) findViewById(R.id.custom_title);
        customTitle.setIv_left_button(R.drawable.icon_back);
        ImageView iv_back = customTitle.getIv_left_button();
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        customTitle.setIv_title_title("车辆列表");
        customTitle.setIv_right_button(R.drawable.icon_add_right);
        ImageView iv_addmycar = customTitle.getIv_right_button();
        iv_addmycar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it_addcar = new Intent(MyCarListActivity.this, CarInfoActivity.class);
                startActivityForResult(it_addcar, 1);
            }
        });
        lv_carlist = (ListView) findViewById(R.id.lv_carlist);
/*        iv_showcar_carlist = (ImageView) findViewById(R.id.iv_showcar_carlist);
        iv_carcolor_carlist = (ImageView) findViewById(R.id.iv_carcolor_carlist);*/
        httpClient = new AsyncHttpClient();
        appURLFinal.addHttpHeader(httpClient);
    }
}
