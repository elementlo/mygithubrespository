package net.wash8.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import net.wash8.R;
import net.wash8.customview.CustomTitle;
import net.wash8.database.MyCarDBHelper;
import net.wash8.helpers.HttpUtils;
import net.wash8.helpers.appURLFinal;
import net.wash8.net.AsyncHttpClient;
import net.wash8.net.AsyncHttpResponseHandler;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class OrderActitity extends Activity {
    private CustomTitle customTitle;
    private ImageView iv_back, iv_car;
    private RelativeLayout rl_addcar;
    private ImageView iv_icon_check, iv_icon_check1, iv_icon_check2;
    private TextView tv_carnumber;

    private String AdName = null;
    private String searchResult, begin_time, finish_time, Neigborhoods, CarLocation, User_ID, Car_ID, Number;
    private int sum_item, date_position = -1, position = -1;
    private float sum_price;
    private int[] tag;
    private boolean tag_oneyuan;

    private AsyncHttpClient asyncHttpClient;
    private SharedPreferences sp_userorder;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        initView();
        //receiveCarInfo();
        getSearchResult();
        getCarInfoFromSharepreference();
    }

    //将汽车信息存入sharepreference
    private void setCarInfoIntoSharepreference(String userID, String carID, String number, String imagePath) {
        editor.putString("car_info_user_id", userID);
        editor.putString("car_info_car_id", carID);
        editor.putString("car_info_number", number);
        editor.putString("car_info_image", imagePath);
        editor.commit();
    }

    //从shareprefrence取出汽车信息
    private void getCarInfoFromSharepreference() {
        User_ID = sp_userorder.getString("car_info_user_id", null);
        Car_ID = sp_userorder.getString("car_info_car_id", null);
        Number = sp_userorder.getString("car_info_number", null);
        String pic_path = sp_userorder.getString("car_info_image", null);

        if (User_ID != null && Car_ID != null && Number != null && pic_path != null) {
            tv_carnumber.setText(Number);
            HttpUtils.getNetBytes(this, appURLFinal.BASE_URL + pic_path, iv_car, false);
        }
    }


    //获得驻点楼盘
    private void getSearchResult() {
        String param = "深圳市";
        try {
            String url = URLEncoder.encode(param, "UTF-8");
            asyncHttpClient.get(appURLFinal.SEARCH_RESULT + url, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(String content) {
                    super.onSuccess(content);
                    if (!content.equals("null")) {
                        searchResult = content;
                    }
                }

                @Override
                public void onFailure(Throwable error, String content) {
                    super.onFailure(error, content);
                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void receiveCarInfo(Intent data) {
        String pic_path = data.getStringExtra("Image");
        HttpUtils.getNetBytes(this, appURLFinal.BASE_URL + pic_path, iv_car, false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (4 == requestCode && data != null) {
            Log.i("tag","UserID"+data.getStringExtra("UserID"));
            receiveCarInfo(data);
            User_ID = data.getStringExtra("UserID");
            Car_ID = data.getStringExtra("CAR_ID");
            Number = data.getStringExtra("Number");
            tv_carnumber.setText(Number);
            setCarInfoIntoSharepreference(User_ID, Car_ID, Number, data.getStringExtra("Image"));
        }
        if (3 == requestCode && data != null) {
            iv_icon_check2.setBackgroundResource(R.drawable.icon_checked);
            sum_item = data.getIntExtra("sum_item", -1);
            sum_price = data.getFloatExtra("sum_price", -1);
            tag = data.getIntArrayExtra("sele_item");
            tag_oneyuan = data.getBooleanExtra("tag_oneyuan", true);
            Log.i("tag", tag_oneyuan + "");
        }
        if (2 == requestCode && data != null) {
            AdName = data.getStringExtra("AdName");
            Neigborhoods = data.getStringExtra("Neigborhoods");
            CarLocation = data.getStringExtra("CarLocation");
            Log.i("tag", CarLocation);
            iv_icon_check.setBackgroundResource(R.drawable.icon_checked);
        }
        if (1 == requestCode && data != null) {
            begin_time = data.getStringExtra("begin_time");
            finish_time = data.getStringExtra("finish_time");
            date_position = data.getIntExtra("date_position", -1);
            position = data.getIntExtra("position", -1);
            iv_icon_check1.setBackgroundResource(R.drawable.icon_checked);
        }
        if (0 == requestCode && data != null) {
            receiveCarInfo(data);
            User_ID = data.getStringExtra("UserID");
            Car_ID = data.getStringExtra("CAR_ID");
            Number = data.getStringExtra("Number");
            tv_carnumber.setText(Number);
            setCarInfoIntoSharepreference(User_ID, Car_ID, Number, data.getStringExtra("Image"));
        }
    }

    private void initView() {
        customTitle = (CustomTitle) findViewById(R.id.custom_title);
        rl_addcar = (RelativeLayout) findViewById(R.id.rl_addcar);
        //增加车辆
        rl_addcar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyCarDBHelper helper = new MyCarDBHelper(OrderActitity.this);
                SQLiteDatabase database = helper.getReadableDatabase();
                Cursor cursor = database.rawQuery("select * from mycar", null);
                if (cursor.getCount() < 1) {
                    Intent itAddcar=new Intent(OrderActitity.this,CarInfoActivity.class);
                    itAddcar.putExtra("emptylist",true);
                    startActivityForResult(itAddcar,4);
                } else {
                    Intent it_addcar = new Intent(OrderActitity.this, MyCarListActivity.class);
                    startActivityForResult(it_addcar, 0);
                }
            }
        });
        customTitle.setIv_left_button(R.drawable.icon_back);
        customTitle.setIv_title_title("预 约");
        iv_back = customTitle.getIv_left_button();
        iv_back.setOnClickListener(new Backlistener());
        iv_icon_check = (ImageView) findViewById(R.id.iv_icon_check);
        iv_icon_check1 = (ImageView) findViewById(R.id.iv_icon_check1);
        iv_icon_check2 = (ImageView) findViewById(R.id.iv_icon_check2);
        iv_car = (ImageView) findViewById(R.id.iv_car);
        tv_carnumber = (TextView) findViewById(R.id.tv_carnumber);

        asyncHttpClient = new AsyncHttpClient();
        sp_userorder = getSharedPreferences("OrderInfo", MODE_PRIVATE);
        editor = sp_userorder.edit();
    }


    class Backlistener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            /*洗车时间*/
            case R.id.rl_washtime_in:
                if (AdName == null) {
                    Toast.makeText(this, "请先选择您的洗车地点", Toast.LENGTH_SHORT).show();
                } else {
                    Intent it_washtime = new Intent(this, WashTimeActivity.class);
                    if (date_position != -1 && position != -1) {
                        Log.i("tag", date_position + position + "*****");
                        it_washtime.putExtra("date_position", date_position);
                        it_washtime.putExtra("position", position);
                    }
                    it_washtime.putExtra("AdName", AdName);
                    startActivityForResult(it_washtime, 1);
                }
                break;
            /*洗车地点*/
            case R.id.rl_washplace_in:
                Intent it_washplace = new Intent(this, PoiSearchActivity.class);
                if (searchResult != null) {
                    Log.i("tag", searchResult);
                    if (null == Car_ID) {
                        Toast.makeText(this, "请先添加您的车辆", Toast.LENGTH_SHORT).show();
                    } else {
                        it_washplace.putExtra("searchResult", searchResult);
                        startActivityForResult(it_washplace, 2);
                    }
                } else {
                    Toast.makeText(this, "请稍候~网络不稳定", Toast.LENGTH_SHORT).show();
                }
                break;
            /*选择服务*/
            case R.id.rl_serviceitem_in:
                if (null == Car_ID) {
                    Toast.makeText(this, "请先添加您的车辆", Toast.LENGTH_SHORT).show();
                } else {
                    Intent it_service = new Intent(this, ServiceItemsActivity.class);
                    if (sum_price != 0.0 && sum_item != 0) {
                        Log.i("tag", sum_price + "****" + sum_item);
                        it_service.putExtra("sele_sumprice", sum_price);
                        it_service.putExtra("sele_item", tag);
                    }
                    startActivityForResult(it_service, 3);
                }
                break;
            /*提交*/
            case R.id.btn_submit_place:
                if (begin_time != null && finish_time != null && User_ID != null && Car_ID != null && sum_item != -1 && sum_price != -1
                        && AdName != null && CarLocation != null) {
                    editor.clear();
                    editor.putString("BookedPeriodFrom", begin_time);
                    editor.putString("BookedPeriodTo", finish_time);
                    editor.putString("UserID", User_ID);
                    editor.putString("CAR_ID", Car_ID);
                    editor.putString("Services", sum_item + "");
                    editor.putString("Fee", sum_price + "");
                    editor.putString("District", AdName);
                    editor.putString("CarLocation", CarLocation);
                    editor.putString("Neigborhoods", Neigborhoods);
                    editor.commit();
                    Intent it_paypage = new Intent(this, PayPageActivity.class);
                    it_paypage.putExtra("tag_oneyuan", tag_oneyuan);
                    startActivityForResult(it_paypage, 4);
                } else {
                    Toast.makeText(this, "请补全您的订单信息", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

}
