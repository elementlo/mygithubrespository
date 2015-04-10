package net.wash8.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import net.wash8.R;
import net.wash8.classbean.OrderItems;
import net.wash8.customview.CustomTitle;
import net.wash8.helpers.Base64FromTo;
import net.wash8.helpers.appURLFinal;
import net.wash8.net.AsyncHttpClient;
import net.wash8.net.AsyncHttpResponseHandler;
import net.wash8.singleadaptar.MyOrderAdapter;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ncb-user on 2015/1/12.
 */
public class OrderItemActivity extends Activity {
    private CustomTitle customTitle;
    private NetworkImageView iv_carimage, iv_before0, iv_before1, iv_before2, iv_before3, iv_after0, iv_after1, iv_after2,
            iv_after3;
    private TextView tv_carnumber, tv_washprogram, tv_date, tv_price, tv_advice;
    private RelativeLayout rl_submitbtn;
    private LinearLayout ll_before, ll_after;
    private EditText et_advice;
    private RatingBar rating_rate;
    private ProgressBar pb_orderitem;

    private OrderItems orderItems;
    private String url_before0, url_before1, url_before2, url_before3, url_after0, url_after1, url_after2, url_after3;
    private ArrayList<String> urllist;
    private String param;

    private RequestQueue mQueue;
    private ImageLoader imageLoader;
    private AsyncHttpClient httpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderitem);
        initView();
        setData();
    }

    public void onClick(View view) {
        int id = view.getId();
        Intent it_comparison = new Intent(this, ComparisonActivity.class);
        it_comparison.putStringArrayListExtra("comparison", urllist);

            if (id == R.id.iv_before0 || id == R.id.iv_after0) {
                it_comparison.putExtra("tag", 0);
                startActivity(it_comparison);
            } else if (id == R.id.iv_before1 || id == R.id.iv_after1) {
                it_comparison.putExtra("tag", 1);
                startActivity(it_comparison);
            } else if (id == R.id.iv_before2 || id == R.id.iv_after2) {
                it_comparison.putExtra("tag", 2);
                startActivity(it_comparison);
            } else if (id == R.id.iv_before3 || id == R.id.iv_after3) {
                it_comparison.putExtra("tag", 3);
                startActivity(it_comparison);
            }
        if (id == R.id.btn_submit_evaluation) {
            if (!TextUtils.isEmpty(et_advice.getText())) {
                pb_orderitem.setVisibility(View.VISIBLE);
                String orderid = orderItems.getID();
                String advice = et_advice.getText().toString();
                float value = rating_rate.getRating();
                JSONObject jsonObject = new JSONObject();
                JSONObject jsonObject_outer=new JSONObject();
                try {
                    jsonObject.put("OrderID", orderid);
                    jsonObject.put("Comment", advice);
                    jsonObject.put("Value", ((int)value) + "");
                    jsonObject_outer.put("Rating",jsonObject);
                    jsonObject_outer.put("ID",orderid);
                    param = jsonObject_outer.toString();
                    Log.i("tag",param);
                    submitAdvice(param);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(this, "请填写您对我们服务的评价", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void submitAdvice(String param) {
        try {
            StringEntity entity = new StringEntity(param, "utf-8");
            httpClient.put(this, appURLFinal.SUBMIT_ADVICE, entity, "application/json; charset=utf-8", new
                    AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(String content) {
                            super.onSuccess(content);
                            Log.i("tag", "ok" + content);
                            Intent it_back=new Intent();
                            it_back.putExtra("refresh",true);
                            setResult(RESULT_OK, it_back);
                            pb_orderitem.setVisibility(View.INVISIBLE);
                            onBackPressed();
                        }

                        @Override
                        public void onFailure(Throwable error, String content) {
                            super.onFailure(error, content);
                            Log.i("tag", content);
                            pb_orderitem.setVisibility(View.INVISIBLE);
                            Toast.makeText(OrderItemActivity.this,"提交失败,请重试",Toast.LENGTH_SHORT).show();
                        }
                    });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void setData() {
        Intent it_recmyorder = getIntent();
        orderItems = (OrderItems) it_recmyorder.getSerializableExtra("orderitem");
        imageLoader = new ImageLoader(mQueue, new ImageLoader.ImageCache() {
            @Override
            public Bitmap getBitmap(String url) {
                return null;
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {

            }
        });
        Log.i("tag", orderItems.getStatus());
        String base_url = appURLFinal.BASE_URL;
        Log.i("tag", base_url + orderItems.getCar().getImage());
        iv_carimage.setImageUrl(base_url + orderItems.getCar().getImage(), imageLoader);
        Log.i("tag", orderItems.getServices());
        String servicename = MyOrderAdapter.formateService(orderItems.getServices());
        String status = orderItems.getStatus();
        tv_washprogram.setText(servicename);
        tv_date.setText(formateTime(orderItems.getBookedPeriodFrom(), 1) + "-" + formateTime(orderItems
                .getBookedPeriodTo(), 3));
        tv_price.setText("¥ " + orderItems.getFee() + " /次");
        if ("4".equals(status) || "5".equals(status)) {
            urllist = new ArrayList<String>();
            url_before0 = base_url + orderItems.getCarImages().get(0).getSource();
            url_before1 = base_url + orderItems.getCarImages().get(1).getSource();
            url_before2 = base_url + orderItems.getCarImages().get(2).getSource();
            url_before3 = base_url + orderItems.getCarImages().get(3).getSource();
            url_after0 = base_url + orderItems.getCarImages().get(4).getSource();
            url_after1 = base_url + orderItems.getCarImages().get(5).getSource();
            url_after2 = base_url + orderItems.getCarImages().get(6).getSource();
            url_after3 = base_url + orderItems.getCarImages().get(7).getSource();
            urllist.add(url_before0);
            urllist.add(url_before1);
            urllist.add(url_before2);
            urllist.add(url_before3);
            urllist.add(url_after0);
            urllist.add(url_after1);
            urllist.add(url_after2);
            urllist.add(url_after3);
            iv_before0.setImageUrl(url_before0, imageLoader);
            iv_before1.setImageUrl(url_before1, imageLoader);
            iv_before2.setImageUrl(url_before2, imageLoader);
            iv_before3.setImageUrl(url_before3, imageLoader);
            iv_after0.setImageUrl(url_after0, imageLoader);
            iv_after1.setImageUrl(url_after1, imageLoader);
            iv_after2.setImageUrl(url_after2, imageLoader);
            iv_after3.setImageUrl(url_after3, imageLoader);
            if ("5".equals(status)) {
                rating_rate.setRating(Float.valueOf(orderItems.getRating().getValue()));
                rating_rate.setIsIndicator(true);
                et_advice.setVisibility(View.INVISIBLE);
                tv_advice.setText(orderItems.getRating().getComment());
                rl_submitbtn.setVisibility(View.GONE);
            }
        } else {
            ll_before.setVisibility(View.GONE);
            ll_after.setVisibility(View.GONE);
            et_advice.setVisibility(View.GONE);
            rl_submitbtn.setVisibility(View.GONE);
            rating_rate.setIsIndicator(true);
        }
        tv_carnumber.setText(orderItems.getCar().getNumber());
    }

    public static String formateTime(String str, int id) {
        String time = str.substring(6, 19);
        String formateTime = null;
        Date date = new Date(Long.valueOf(time));
        switch (id) {
            case 1:
                Log.i("tag", time);
                SimpleDateFormat dateformat1 = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                formateTime = dateformat1.format(date);
                break;
            case 2:
                Log.i("tag", time);
                SimpleDateFormat dateformat2 = new SimpleDateFormat("yyyy-MM-dd");
                formateTime = dateformat2.format(date);
                break;
            case 3:
                Log.i("tag", time);
                SimpleDateFormat dateformat3 = new SimpleDateFormat("hh:mm");
                formateTime = dateformat3.format(date);
                break;
        }
        return formateTime;
    }

    private void initView() {
        customTitle = (CustomTitle) findViewById(R.id.customTitle);
        customTitle.setIv_title_title("订单详情");
        customTitle.setIv_left_button(R.drawable.icon_back);
        customTitle.getIv_left_button().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        iv_carimage = (NetworkImageView) findViewById(R.id.iv_carimage);
        iv_before0 = (NetworkImageView) findViewById(R.id.iv_before0);
        iv_before1 = (NetworkImageView) findViewById(R.id.iv_before1);
        iv_before2 = (NetworkImageView) findViewById(R.id.iv_before2);
        iv_before3 = (NetworkImageView) findViewById(R.id.iv_before3);
        iv_after0 = (NetworkImageView) findViewById(R.id.iv_after0);
        iv_after1 = (NetworkImageView) findViewById(R.id.iv_after1);
        iv_after2 = (NetworkImageView) findViewById(R.id.iv_after2);
        iv_after3 = (NetworkImageView) findViewById(R.id.iv_after3);
        tv_carnumber = (TextView) findViewById(R.id.tv_carnumber);
        ll_before = (LinearLayout) findViewById(R.id.ll_before);
        ll_after = (LinearLayout) findViewById(R.id.ll_after);
        rl_submitbtn = (RelativeLayout) findViewById(R.id.rl_submitbtn);
        et_advice = (EditText) findViewById(R.id.et_advice);
        rating_rate = (RatingBar) findViewById(R.id.rating_rate);
        tv_washprogram = (TextView) findViewById(R.id.tv_washprogram);
        tv_date = (TextView) findViewById(R.id.tv_date);
        tv_price = (TextView) findViewById(R.id.tv_price);
        tv_advice = (TextView) findViewById(R.id.tv_advice);
        pb_orderitem= (ProgressBar) findViewById(R.id.pb_orderitem);

        mQueue = Volley.newRequestQueue(this);
        httpClient = new AsyncHttpClient();
        appURLFinal.addHttpHeader(httpClient);
    }
}
