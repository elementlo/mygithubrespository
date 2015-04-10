package net.wash8.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import net.wash8.R;
import net.wash8.customview.CustomTitle;
import net.wash8.helpers.appURLFinal;
import net.wash8.net.AsyncHttpClient;
import net.wash8.net.AsyncHttpResponseHandler;
import net.wash8.singleadaptar.WashTimeAdaptar;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by ncb-user on 2014/12/18.
 */
public class WashTimeActivity extends Activity {
    private int mMonth, day_month, date_position = -1;
    public static int time_position=-1;
    private String mWDay, mDay, select_date, begin_time, finish_time;
    private List<String> data_list;

    private ImageView divid1_date, divid2_date, divid3_date, divid4_date, divid5_date, divid6_date, divid7_date;
    private TextView tv_month1, tv_month2, tv_month3, tv_month4, tv_month5, tv_month6, tv_month7, tv1_week, tv2_week, tv3_week, tv4_week, tv5_week, tv6_week, tv7_week, tv1_date,
            tv2_date, tv3_date, tv4_date, tv5_date, tv6_date, tv7_date;
    private ListView lv_washtime;

    private AsyncHttpClient httpClient;
    private String[] str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordertime);
        initView();
        setDate();
        setListData();
    }

    private void setListData() {
        WashTimeAdaptar wtadaptar = new WashTimeAdaptar(WashTimeActivity.this, str,-1);
        lv_washtime.setAdapter(wtadaptar);
    }

    private void initView() {
        httpClient = new AsyncHttpClient();
        httpClient.addHeader("Client-Secret", "db266d6766a7c79f1defd856caf1d877");
        CustomTitle customTitle = (CustomTitle) findViewById(R.id.customTitle);
        customTitle.setIv_left_button(R.drawable.icon_back);
        customTitle.setIv_title_title("选择预约时间");
        customTitle.getIv_left_button().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        tv_month1 = (TextView) findViewById(R.id.tv_month1);
        tv_month2 = (TextView) findViewById(R.id.tv_month2);
        tv_month3 = (TextView) findViewById(R.id.tv_month3);
        tv_month4 = (TextView) findViewById(R.id.tv_month4);
        tv_month5 = (TextView) findViewById(R.id.tv_month5);
        tv_month6 = (TextView) findViewById(R.id.tv_month6);
        tv_month7 = (TextView) findViewById(R.id.tv_month7);
        tv1_week = (TextView) findViewById(R.id.tv1_week);
        tv2_week = (TextView) findViewById(R.id.tv2_week);
        tv3_week = (TextView) findViewById(R.id.tv3_week);
        tv4_week = (TextView) findViewById(R.id.tv4_week);
        tv5_week = (TextView) findViewById(R.id.tv5_week);
        tv6_week = (TextView) findViewById(R.id.tv6_week);
        tv7_week = (TextView) findViewById(R.id.tv7_week);
        tv1_date = (TextView) findViewById(R.id.tv1_date);
        tv2_date = (TextView) findViewById(R.id.tv2_date);
        tv3_date = (TextView) findViewById(R.id.tv3_date);
        tv4_date = (TextView) findViewById(R.id.tv4_date);
        tv5_date = (TextView) findViewById(R.id.tv5_date);
        tv6_date = (TextView) findViewById(R.id.tv6_date);
        tv7_date = (TextView) findViewById(R.id.tv7_date);
        divid1_date = (ImageView) findViewById(R.id.divid1_date);
        divid2_date = (ImageView) findViewById(R.id.divid2_date);
        divid3_date = (ImageView) findViewById(R.id.divid3_date);
        divid4_date = (ImageView) findViewById(R.id.divid4_date);
        divid5_date = (ImageView) findViewById(R.id.divid5_date);
        divid6_date = (ImageView) findViewById(R.id.divid6_date);
        divid7_date = (ImageView) findViewById(R.id.divid7_date);
        lv_washtime = (ListView) findViewById(R.id.lv_washtime);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setDate() {
        Intent rec_position = getIntent();
        int rec_date_position = rec_position.getIntExtra("date_position", -1);
        int rec_time_position = rec_position.getIntExtra("position", -1);
        Log.i("tag","rec_date_position"+rec_date_position+"rec_time_position"+rec_time_position);
        Calendar c = Calendar.getInstance();
        Date date = c.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String today = format.format(date);
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        mMonth = c.get(Calendar.MONTH) + 1;//获取当前月份
        day_month = c.get(Calendar.DAY_OF_MONTH);
        mDay = String.valueOf(day_month);//获取当前月份的日期号码
        mWDay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
        tv_month1.setText(mMonth + "月");
        if ("1".equals(mWDay)) {
            mWDay = "天";
            tv1_week.setText("周" + mWDay);
            tv2_week.setText("周一");
            tv3_week.setText("周二");
            tv4_week.setText("周三");
            tv5_week.setText("周四");
            tv6_week.setText("周五");
            tv7_week.setText("周六");
        } else if ("2".equals(mWDay)) {
            mWDay = "一";
            tv1_week.setText("周" + mWDay);
            tv2_week.setText("周二");
            tv3_week.setText("周三");
            tv4_week.setText("周四");
            tv5_week.setText("周五");
            tv6_week.setText("周六");
            tv7_week.setText("周天");
        } else if ("3".equals(mWDay)) {
            mWDay = "二";
            tv1_week.setText("周" + mWDay);
            tv2_week.setText("周三");
            tv3_week.setText("周四");
            tv4_week.setText("周五");
            tv5_week.setText("周六");
            tv6_week.setText("周天");
            tv7_week.setText("周一");
        } else if ("4".equals(mWDay)) {
            mWDay = "三";
            tv1_week.setText("周" + mWDay);
            tv2_week.setText("周四");
            tv3_week.setText("周五");
            tv4_week.setText("周六");
            tv5_week.setText("周天");
            tv6_week.setText("周一");
            tv7_week.setText("周二");
        } else if ("5".equals(mWDay)) {
            mWDay = "四";
            tv1_week.setText("周" + mWDay);
            tv2_week.setText("周五");
            tv3_week.setText("周六");
            tv4_week.setText("周日");
            tv5_week.setText("周一");
            tv6_week.setText("周二");
            tv7_week.setText("周三");
        } else if ("6".equals(mWDay)) {
            mWDay = "五";
            tv1_week.setText("周" + mWDay);
            tv2_week.setText("周六");
            tv3_week.setText("周天");
            tv4_week.setText("周一");
            tv5_week.setText("周二");
            tv6_week.setText("周三");
            tv7_week.setText("周四");
        } else if ("7".equals(mWDay)) {
            mWDay = "六";
            tv1_week.setText("周" + mWDay);
            tv2_week.setText("周天");
            tv3_week.setText("周一");
            tv4_week.setText("周二");
            tv5_week.setText("周三");
            tv6_week.setText("周四");
            tv7_week.setText("周五");
        }
        tv1_date.setText(mDay);
        List<TextView> list = new ArrayList<TextView>();
        list.add(tv1_date);
        list.add(tv2_date);
        list.add(tv3_date);
        list.add(tv4_date);
        list.add(tv5_date);
        list.add(tv6_date);
        list.add(tv7_date);
        List<ImageView> dvd_list = new ArrayList<ImageView>();
        dvd_list.add(divid1_date);
        dvd_list.add(divid2_date);
        dvd_list.add(divid3_date);
        dvd_list.add(divid4_date);
        dvd_list.add(divid5_date);
        dvd_list.add(divid6_date);
        dvd_list.add(divid7_date);
        List<TextView> month_list = new ArrayList<TextView>();
        month_list.add(tv_month1);
        month_list.add(tv_month2);
        month_list.add(tv_month3);
        month_list.add(tv_month4);
        month_list.add(tv_month5);
        month_list.add(tv_month6);
        month_list.add(tv_month7);
        int i, tag = 0;
        String params;
        Date_Click_Listener dclistener;
        Intent it_getAdN = getIntent();
        //获得行政区
        String AdName = it_getAdN.getStringExtra("AdName");
        data_list = new ArrayList<String>();
        String date_fom;
        for (i = 0; i < 7; i++) {
            if (i == 0) {
                select_date = today;
                date_position = 0;
                list.get(i).setText(mDay);
                list.get(i).setBackground(getResources().getDrawable(R.drawable.selected_date));
                list.get(i).setTextColor(Color.parseColor("#ffffff"));
                date_fom = format.format(date);
                //String date=c.get(Calendar.YEAR)+"-0"+(c.get(Calendar.MONTH)+1)+"-0"+c.get(Calendar.DAY_OF_MONTH);
                params = "dis=" + AdName + "&" + "bpf=" + date_fom + " 12:00:00&bpt=" + date_fom + " 23:59:59";
                Log.i("tag", params);
                if (rec_date_position==-1){
                requestOrderDate(params, new AsyHandler(-1));
                }
                data_list.add(date_fom);
            }
            else {
                c.add(Calendar.DAY_OF_YEAR, 1);//时间加1天往后推
                tag = c.get(Calendar.DAY_OF_MONTH);
                Date date_next = c.getTime();
                list.get(i).setText(tag + "");
                date_fom = format.format(date_next);
                params = "dis=" + AdName + "&" + "bpf=" + date_fom + " 12:00:00&bpt=" + date_fom + " 23:59:59";
                //Log.i("tag", params);
                data_list.add(date_fom);
            }
            dclistener = new Date_Click_Listener(params);
            list.get(i).setOnClickListener(dclistener);
            if (tag == 1) {
                if (i > 0) {
                    dvd_list.get(i - 1).setVisibility(View.VISIBLE);
                }
                int tag_month = c.get(Calendar.MONTH);
                month_list.get(i).setText(tag_month + 1 + "月");
                month_list.get(i).setVisibility(View.VISIBLE);
            }
        }
        if (rec_date_position!=-1){
            date_position=rec_date_position;
            select_date=data_list.get(rec_date_position);
            list.get(rec_date_position).setBackground(getResources().getDrawable(R.drawable.selected_date));
            list.get(rec_date_position).setTextColor(Color.parseColor("#ffffff"));
            if (rec_date_position!=0){
            list.get(0).setBackground(getResources().getDrawable(R.drawable.bg_date));
            list.get(0).setTextColor(Color.parseColor("#000000"));}
            params="dis="+AdName+"&"+"bpf="+select_date+" 12:00:00&bpt="+select_date+" 23:59:59";
            Log.i("tag", params);
            requestOrderDate(params, new AsyHandler(rec_time_position));
        }

    }

    class Date_Click_Listener implements View.OnClickListener {
        String params;
        AsyHandler handler = new AsyHandler(-1);

        public Date_Click_Listener(String params) {
            this.params = params;
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onClick(View view) {
            TextView tv_click = (TextView) view;
            switch (view.getId()) {
                case R.id.tv1_date:
                    requestOrderDate(params, handler);
                    date_position = 0;
                    select_date = data_list.get(0);
                    tv_click.setBackground(getResources().getDrawable(R.drawable.selected_date));
                    tv_click.setTextColor(Color.parseColor("#ffffff"));
                    tv2_date.setBackground(getResources().getDrawable(R.drawable.bg_date));
                    tv2_date.setTextColor(Color.parseColor("#000000"));
                    tv3_date.setBackground(getResources().getDrawable(R.drawable.bg_date));
                    tv3_date.setTextColor(Color.parseColor("#000000"));
                    tv4_date.setBackground(getResources().getDrawable(R.drawable.bg_date));
                    tv4_date.setTextColor(Color.parseColor("#000000"));
                    tv5_date.setBackground(getResources().getDrawable(R.drawable.bg_date));
                    tv5_date.setTextColor(Color.parseColor("#000000"));
                    tv6_date.setBackground(getResources().getDrawable(R.drawable.bg_date));
                    tv6_date.setTextColor(Color.parseColor("#000000"));
                    tv7_date.setBackground(getResources().getDrawable(R.drawable.bg_date));
                    tv7_date.setTextColor(Color.parseColor("#000000"));
                    break;
                case R.id.tv2_date:
                    requestOrderDate(params, handler);
                    date_position = 1;
                    select_date = data_list.get(1);
                    tv_click.setBackground(getResources().getDrawable(R.drawable.selected_date));
                    tv_click.setTextColor(Color.parseColor("#ffffff"));
                    tv1_date.setBackground(getResources().getDrawable(R.drawable.bg_date));
                    tv1_date.setTextColor(Color.parseColor("#000000"));
                    tv3_date.setBackground(getResources().getDrawable(R.drawable.bg_date));
                    tv3_date.setTextColor(Color.parseColor("#000000"));
                    tv4_date.setBackground(getResources().getDrawable(R.drawable.bg_date));
                    tv4_date.setTextColor(Color.parseColor("#000000"));
                    tv5_date.setBackground(getResources().getDrawable(R.drawable.bg_date));
                    tv5_date.setTextColor(Color.parseColor("#000000"));
                    tv6_date.setBackground(getResources().getDrawable(R.drawable.bg_date));
                    tv6_date.setTextColor(Color.parseColor("#000000"));
                    tv7_date.setBackground(getResources().getDrawable(R.drawable.bg_date));
                    tv7_date.setTextColor(Color.parseColor("#000000"));
                    break;
                case R.id.tv3_date:
                    requestOrderDate(params, handler);
                    date_position = 2;
                    select_date = data_list.get(2);
                    Log.i("tag", select_date);
                    tv_click.setBackground(getResources().getDrawable(R.drawable.selected_date));
                    tv_click.setTextColor(Color.parseColor("#ffffff"));
                    tv2_date.setBackground(getResources().getDrawable(R.drawable.bg_date));
                    tv2_date.setTextColor(Color.parseColor("#000000"));
                    tv1_date.setBackground(getResources().getDrawable(R.drawable.bg_date));
                    tv1_date.setTextColor(Color.parseColor("#000000"));
                    tv4_date.setBackground(getResources().getDrawable(R.drawable.bg_date));
                    tv4_date.setTextColor(Color.parseColor("#000000"));
                    tv5_date.setBackground(getResources().getDrawable(R.drawable.bg_date));
                    tv5_date.setTextColor(Color.parseColor("#000000"));
                    tv6_date.setBackground(getResources().getDrawable(R.drawable.bg_date));
                    tv6_date.setTextColor(Color.parseColor("#000000"));
                    tv7_date.setBackground(getResources().getDrawable(R.drawable.bg_date));
                    tv7_date.setTextColor(Color.parseColor("#000000"));
                    break;
                case R.id.tv4_date:
                    requestOrderDate(params, handler);
                    date_position = 3;
                    select_date = data_list.get(3);
                    tv_click.setBackground(getResources().getDrawable(R.drawable.selected_date));
                    tv_click.setTextColor(Color.parseColor("#ffffff"));
                    tv2_date.setBackground(getResources().getDrawable(R.drawable.bg_date));
                    tv2_date.setTextColor(Color.parseColor("#000000"));
                    tv3_date.setBackground(getResources().getDrawable(R.drawable.bg_date));
                    tv3_date.setTextColor(Color.parseColor("#000000"));
                    tv1_date.setBackground(getResources().getDrawable(R.drawable.bg_date));
                    tv1_date.setTextColor(Color.parseColor("#000000"));
                    tv5_date.setBackground(getResources().getDrawable(R.drawable.bg_date));
                    tv5_date.setTextColor(Color.parseColor("#000000"));
                    tv6_date.setBackground(getResources().getDrawable(R.drawable.bg_date));
                    tv6_date.setTextColor(Color.parseColor("#000000"));
                    tv7_date.setBackground(getResources().getDrawable(R.drawable.bg_date));
                    tv7_date.setTextColor(Color.parseColor("#000000"));
                    break;
                case R.id.tv5_date:
                    requestOrderDate(params, handler);
                    date_position = 4;
                    select_date = data_list.get(4);
                    tv_click.setBackground(getResources().getDrawable(R.drawable.selected_date));
                    tv_click.setTextColor(Color.parseColor("#ffffff"));
                    tv2_date.setBackground(getResources().getDrawable(R.drawable.bg_date));
                    tv2_date.setTextColor(Color.parseColor("#000000"));
                    tv3_date.setBackground(getResources().getDrawable(R.drawable.bg_date));
                    tv3_date.setTextColor(Color.parseColor("#000000"));
                    tv4_date.setBackground(getResources().getDrawable(R.drawable.bg_date));
                    tv4_date.setTextColor(Color.parseColor("#000000"));
                    tv1_date.setBackground(getResources().getDrawable(R.drawable.bg_date));
                    tv1_date.setTextColor(Color.parseColor("#000000"));
                    tv6_date.setBackground(getResources().getDrawable(R.drawable.bg_date));
                    tv6_date.setTextColor(Color.parseColor("#000000"));
                    tv7_date.setBackground(getResources().getDrawable(R.drawable.bg_date));
                    tv7_date.setTextColor(Color.parseColor("#000000"));
                    break;
                case R.id.tv6_date:
                    requestOrderDate(params, handler);
                    date_position = 5;
                    select_date = data_list.get(5);
                    tv_click.setBackground(getResources().getDrawable(R.drawable.selected_date));
                    tv_click.setTextColor(Color.parseColor("#ffffff"));
                    tv2_date.setBackground(getResources().getDrawable(R.drawable.bg_date));
                    tv2_date.setTextColor(Color.parseColor("#000000"));
                    tv3_date.setBackground(getResources().getDrawable(R.drawable.bg_date));
                    tv3_date.setTextColor(Color.parseColor("#000000"));
                    tv4_date.setBackground(getResources().getDrawable(R.drawable.bg_date));
                    tv4_date.setTextColor(Color.parseColor("#000000"));
                    tv5_date.setBackground(getResources().getDrawable(R.drawable.bg_date));
                    tv5_date.setTextColor(Color.parseColor("#000000"));
                    tv1_date.setBackground(getResources().getDrawable(R.drawable.bg_date));
                    tv1_date.setTextColor(Color.parseColor("#000000"));
                    tv7_date.setBackground(getResources().getDrawable(R.drawable.bg_date));
                    tv7_date.setTextColor(Color.parseColor("#000000"));
                    break;
                case R.id.tv7_date:
                    requestOrderDate(params, handler);
                    date_position = 6;
                    select_date = data_list.get(6);
                    tv_click.setBackground(getResources().getDrawable(R.drawable.selected_date));
                    tv_click.setTextColor(Color.parseColor("#ffffff"));
                    tv2_date.setBackground(getResources().getDrawable(R.drawable.bg_date));
                    tv2_date.setTextColor(Color.parseColor("#000000"));
                    tv3_date.setBackground(getResources().getDrawable(R.drawable.bg_date));
                    tv3_date.setTextColor(Color.parseColor("#000000"));
                    tv4_date.setBackground(getResources().getDrawable(R.drawable.bg_date));
                    tv4_date.setTextColor(Color.parseColor("#000000"));
                    tv5_date.setBackground(getResources().getDrawable(R.drawable.bg_date));
                    tv5_date.setTextColor(Color.parseColor("#000000"));
                    tv6_date.setBackground(getResources().getDrawable(R.drawable.bg_date));
                    tv6_date.setTextColor(Color.parseColor("#000000"));
                    tv1_date.setBackground(getResources().getDrawable(R.drawable.bg_date));
                    tv1_date.setTextColor(Color.parseColor("#000000"));
                    break;
            }
        }
    }

    private void requestOrderDate(String params, AsyHandler handler) {
        try {
            String url = URLEncoder.encode(params, "UTF-8");
            Log.i("tag", url);
            httpClient.get(appURLFinal.GET_DATE_ORDER + url, handler);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    class AsyHandler extends AsyncHttpResponseHandler {
        private int position;
        public AsyHandler(int position){
            this.position=position;
        }
        @Override
        public void onFailure(Throwable error, String content) {
            super.onFailure(error, content);
        }

        @Override
        public void onSuccess(int statusCode, String content) {
            super.onSuccess(statusCode, content);
            String s = content.substring(1, 24);
            Log.i("tag", s);
            str = s.split(",");
            lv_washtime.setAdapter(new WashTimeAdaptar(WashTimeActivity.this, str,position));
        }
    }

    public void onClick(View view) {
        SharedPreferences sharetime = getSharedPreferences("washtime", WashTimeActivity.MODE_PRIVATE);
        int position;
        if (time_position!=-1){
            position=time_position;
        }else {
        position = sharetime.getInt("time", -1);
        }

        if (position == 0) {
            begin_time = select_date + " 12:00";
            finish_time = select_date + " 12:59";
        } else if (position > 0 && position < 11) {
            int time=position+12;
            begin_time = select_date  + " "+time + ":00";
            finish_time = select_date + " "+time + ":59";
        } else if (position == 11) {
            begin_time = select_date + " 23:00";
            finish_time = select_date + " 23:59";
        }
        if (begin_time==null||finish_time==null) {
            Toast.makeText(WashTimeActivity.this, "请选择时间", Toast.LENGTH_SHORT).show();
        } else {
            sharetime.edit().clear().commit();
            Intent it_submit = new Intent();
            Log.i("tag",begin_time+"*****"+finish_time);
            Log.i("tag",date_position+"******"+position);
            it_submit.putExtra("date_position", date_position);
            it_submit.putExtra("position", position);
            it_submit.putExtra("begin_time", begin_time);
            it_submit.putExtra("finish_time", finish_time);
            setResult(RESULT_OK, it_submit);
            this.finish();
        }
    }

}
