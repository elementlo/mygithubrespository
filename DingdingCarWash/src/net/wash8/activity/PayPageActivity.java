package net.wash8.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import net.wash8.R;
import net.wash8.adapter.ChooseCouponAdapter;
import net.wash8.classbean.CouponItems;
import net.wash8.classbean.Coupons;
import net.wash8.customview.CustomTitle;
import net.wash8.customview.JumpMenuUtil;
import net.wash8.database.MyCarDBHelper;
import net.wash8.helpers.Base64FromTo;
import net.wash8.helpers.JSON_Parser;
import net.wash8.helpers.appURLFinal;
import net.wash8.net.AsyncHttpClient;
import net.wash8.net.AsyncHttpResponseHandler;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class PayPageActivity extends Activity {
    private TextView tv_ordertime, tv_orderplace, tv_username, tv_usermobile, tv_carmodel, tv_sumprice, tv_couponhelp;
    private Button btn_use_coupon, btn_help, btn_confimorder;

    private SharedPreferences sp_getdata;
    private RequestQueue mQueue;
    private SQLiteDatabase database;

    private String fee;
    private String district;
    private String carlocation;
    private String neigborhood;
    private Long order_millis;
    private Long finish_millis;
    private boolean tag_oneyuan;

    private View couponLayout;
    private ListView couponList;
    private Coupons coupons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paypage);
        initView();
        setOrderDate();
        setCouponData();
    }

    private void setOrderDate() {
        sp_getdata = getSharedPreferences("OrderInfo", MODE_PRIVATE);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date order_date;
        Date finish_date;
        Intent it_oneyuan = getIntent();
        //1元优惠tag标记
        tag_oneyuan = it_oneyuan.getBooleanExtra("tag_oneyuan", false);
        Log.i("tag", "oneyuan" + tag_oneyuan);
        String order_time = sp_getdata.getString("BookedPeriodFrom", null);
        String finish_time = sp_getdata.getString("BookedPeriodTo", null);
        Log.i("tag", order_time + "tag" + finish_time);
        try {
            order_date = format.parse(order_time);
            finish_date = format.parse(finish_time);
            order_millis = order_date.getTime() - 28800000;
            finish_millis = finish_date.getTime() - 28800000;
            Log.i("tag", order_date + ":" + finish_date);
            Log.i("tag", order_millis + ":" + finish_millis);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        district = sp_getdata.getString("District", null);
        carlocation = sp_getdata.getString("CarLocation", null);
        neigborhood = sp_getdata.getString("Neigborhoods", null);
        String order_place = "深圳市"+district +neigborhood+ carlocation;
        tv_ordertime.setText(order_time);
        tv_orderplace.setText(order_place);
        MyCarDBHelper helper = new MyCarDBHelper(this);
        SQLiteDatabase database = helper.getReadableDatabase();
        Cursor cursor = helper.onQuery_UserInfo(database, "Mobile", null, "LastName", "Gender", null, null, null, null,
                null, null, null);
        Cursor cursor_car = database.rawQuery("select * from mycar where CAR_ID=" + sp_getdata.getString("CAR_ID", null),
                null);
        cursor.moveToFirst();
        cursor_car.moveToFirst();
        String lastname = "";
        String gender = "";
        String txtGender="";
        lastname = cursor.getString(cursor.getColumnIndex("LastName"));
        //判断男女
        /*if (cursor.getString(cursor.getColumnIndex("Gender"))!="null") {*/
            gender = cursor.getString(cursor.getColumnIndex("Gender"));
            if("0".equals(gender)){
                txtGender="先生";
            }else if ("1".equals(gender)){
                txtGender="女士";
            }else {
                txtGender="其他";
            }

        String username = (lastname == null ? "" : lastname) + (txtGender);
        String carinfo = cursor_car.getString(cursor_car.getColumnIndex("Brand")) + cursor_car.getString(cursor_car
                .getColumnIndex("Model")) + cursor_car.getString(cursor_car.getColumnIndex("Color"));
        tv_username.setText(username);
        String mobile = cursor.getString(cursor.getColumnIndex("Mobile"));
        if (mobile != null) {
            tv_usermobile.setText(mobile);
        }
        tv_carmodel.setText(carinfo);
        fee = sp_getdata.getString("Fee", null);
        if (fee != null) {
            tv_sumprice.setText(fee);
        }

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_confimorder:
                btn_confimorder.setClickable(false);
                String userid = sp_getdata.getString("UserID", null);
                String carid = sp_getdata.getString("CAR_ID", null);
                Log.i("tag", userid + "**********" + carid);
                String services = sp_getdata.getString("Services", null);
                final JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject();
                    jsonObject.put("UserID", userid);
                    jsonObject.put("CarID", carid);
                    jsonObject.put("Services", services);
                    jsonObject.put("Fee", fee);
                    jsonObject.put("City", "深圳");
                    jsonObject.put("District", district);
                    jsonObject.put("CarLocation", carlocation);
                    jsonObject.put("BookedPeriodFrom", "/Date(" + order_millis + "+0800)/");
                    jsonObject.put("BookedPeriodTo", "/Date(" + finish_millis + "+0800)/");
                    jsonObject.put("Neigborhoods", neigborhood);
                    Log.i("tag", jsonObject.toString());
                    JsonRequest<JSONObject> request = new JsonObjectRequest(Request.Method.POST, appURLFinal
                            .SUBMIT_ORDER, jsonObject, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.i("tag", response.toString() + "1111111");
                            btn_confimorder.setClickable(true);
                            if (tag_oneyuan) {
                                database.execSQL("update userinfo set NotOrdered="+"\'"+"false"+"\'");
                            }
                            Toast.makeText(PayPageActivity.this, "添加订单成功", Toast.LENGTH_SHORT).show();
                            Intent it_overOrdered = new Intent(PayPageActivity.this, HomeActivity.class);
                            startActivity(it_overOrdered);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("tag", error.toString()+"err");
                            btn_confimorder.setClickable(true);
                            Log.e("tag", error.getMessage()+"err", error);
                            byte[] htmlBodyBytes = error.networkResponse.data;
                            Log.e("tag", new String(htmlBodyBytes)+"err", error);
                            Toast.makeText(PayPageActivity.this, "添加订单失败请重试", Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            HashMap<String, String> headers = new HashMap<String, String>();
                            String user_name = appURLFinal.USER_NAME;
                            String pass_word = appURLFinal.PASS_WORD;
                            Log.i("tag", user_name + ":" + pass_word);
                            String key = Base64FromTo.encodeBase64(user_name + ":" + pass_word);
                            headers.put("Authorization", "Basic " + key);
                            return headers;
                        }
                    };
                    mQueue.add(request);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_use_coupon:
                initCouponList();
                break;
        }
    }

    private void initCouponList() {
        if (couponLayout == null) {
            couponLayout = LayoutInflater.from(this).inflate(R.layout.choose_coupon, null);
            couponList = (ListView) couponLayout.findViewById(R.id.lv_coupon);

            CustomTitle customTitle = (CustomTitle) couponLayout.findViewById(R.id.customTitle);
            customTitle.setIv_title_title("优惠卷");
            customTitle.setIv_left_button(R.drawable.icon_back);
            customTitle.getIv_left_button().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    JumpMenuUtil.dismiss(PayPageActivity.this);
                }
            });
        }
        if (coupons == null) {
            //setCouponData();
            ChooseCouponAdapter chooseCouponAdapter = new ChooseCouponAdapter(PayPageActivity.this, coupons);
            couponList.setAdapter(chooseCouponAdapter);
        }
        JumpMenuUtil.jumpMenu(this, couponLayout);
    }

    //处理选择优惠劵后的回调
    public void useCoupon(CouponItems couponItems) {
        JumpMenuUtil.dismiss(PayPageActivity.this);
        View useCoupon = findViewById(R.id.ll_use_coupon);
        useCoupon.setVisibility(View.VISIBLE);

        float price = 0;
        if (fee != null)
            price = Float.parseFloat(fee);
        price -= Float.parseFloat(couponItems.getValue());
        ((TextView) findViewById(R.id.tv_price)).setText(price + "元");
        ((TextView) findViewById(R.id.tv_coupon)).setText("已优惠" + couponItems.getValue() + "元");
    }

    private void setCouponData() {
        MyCarDBHelper helper = new MyCarDBHelper(this);
        SQLiteDatabase database = helper.getReadableDatabase();
        Cursor cursor = database.rawQuery("select ID from userinfo", null);
        cursor.moveToFirst();
        int userid = cursor.getInt(cursor.getColumnIndex("ID"));
        cursor.close();
        helper.close();
        AsyncHttpClient httpClient = new AsyncHttpClient();
        appURLFinal.addHttpHeader(httpClient);
        httpClient.get(this, appURLFinal.COUPON_LIST + userid, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
                Log.i("tag", "ok" + content);
                if (content != null) {
                    coupons = JSON_Parser.parseJSON_Coupons(content);
                    if (coupons.getItems().size() != 0&&Float.parseFloat(fee)>10.0) {
                        tv_couponhelp.setText("是否使用优惠卷?");
                        btn_use_coupon.setVisibility(View.VISIBLE);
                        btn_help.setVisibility(View.INVISIBLE);
                    }

                }
            }

            @Override
            public void onFailure(Throwable error, String content) {
                super.onFailure(error, content);
                Log.i("tag", content);
                Toast.makeText(PayPageActivity.this, "获取优惠卷失败,请重试", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void initView() {
        CustomTitle customTitle = (CustomTitle) findViewById(R.id.customTitle);
        customTitle.setIv_title_title("支付页");
        customTitle.setIv_left_button(R.drawable.icon_back);
        customTitle.getIv_left_button().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        tv_ordertime = (TextView) findViewById(R.id.tv_ordertime);
        tv_orderplace = (TextView) findViewById(R.id.tv_orderplace);
        tv_username = (TextView) findViewById(R.id.tv_username);
        tv_usermobile = (TextView) findViewById(R.id.tv_usermobile);
        tv_carmodel = (TextView) findViewById(R.id.tv_carmodel);
        tv_sumprice = (TextView) findViewById(R.id.tv_sumprice);
        tv_couponhelp = (TextView) findViewById(R.id.tv_couponhelp);
        btn_use_coupon = (Button) findViewById(R.id.btn_use_coupon);
        btn_help = (Button) findViewById(R.id.btn_help);
        btn_confimorder = (Button) findViewById(R.id.btn_confimorder);

        mQueue = Volley.newRequestQueue(this);
        MyCarDBHelper helper = new MyCarDBHelper(this);
        database = helper.getWritableDatabase();

    }
}
