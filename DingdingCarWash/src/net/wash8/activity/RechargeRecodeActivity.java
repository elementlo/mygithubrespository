package net.wash8.activity;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import net.wash8.R;
import net.wash8.customview.CustomTitle;
import net.wash8.database.MyCarDBHelper;
import net.wash8.helpers.Base64FromTo;
import net.wash8.helpers.appURLFinal;
import net.wash8.net.AsyncHttpClient;
import net.wash8.net.AsyncHttpResponseHandler;
import net.wash8.net.RequestParams;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by lijie on 2015/1/15.
 */
public class RechargeRecodeActivity extends Activity{
    private CustomTitle customTitle;

    private JSONArray data;

    private ListView recharges;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_recode);
        initView();

        getRecodeList();
    }

    private void initView(){
        customTitle = (CustomTitle) findViewById(R.id.customTitle);
        customTitle.setIv_title_title("充值记录");
        customTitle.setIv_left_button(R.drawable.icon_back);
        customTitle.getIv_left_button().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        recharges = (ListView)findViewById(R.id.lv_recharge);
    }

    private void getRecodeList(){

        MyCarDBHelper helper=new MyCarDBHelper(this);
        SQLiteDatabase database=helper.getReadableDatabase();
        Cursor cursor=database.rawQuery("select ID from userinfo", null);
        cursor.moveToFirst();
        int userid=cursor.getInt(cursor.getColumnIndex("ID"));

        AsyncHttpClient asyncHttpclient = new AsyncHttpClient();

        appURLFinal.addHttpHeader(asyncHttpclient);

        asyncHttpclient.get(appURLFinal.USER_DEPOSIT+userid, null, new AsyncHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, String content) {
                super.onSuccess(statusCode, content);
                if (content!=null){
                Log.i("tag",content+"");
                try {
                    JSONObject jsonContent = new JSONObject(content);
                    data = jsonContent.getJSONArray("Items");
                }catch (Exception e){
                    e.printStackTrace();
                }

                SimpleAdapter adapter=new SimpleAdapter(RechargeRecodeActivity.this,pacageData(),R.layout
                        .list_recharge_recode_item,new String[]{"Amount","CreatedDateTime"},new int[]{R.id.tv_money,R
                        .id.tv_time,R.id.tv_value});

                recharges.setAdapter(adapter);}
                else {
                    Toast.makeText(RechargeRecodeActivity.this,"还没有记录",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable error, String content) {
                super.onFailure(error, content);

                Log.i("tag",content+"");
            }

        });
    }

    private List<Map<String, String>> pacageData(){
        List<Map<String,String>> list = new ArrayList<Map<String, String>>();
        for(int i = 0;i < data.length();i++){
            Map<String,String> map = new HashMap<String, String>();
            try{
                JSONObject jsonObject = data.getJSONObject(i);
                map.put("Amount",jsonObject.getString("Amount"));
                map.put("CreatedDateTime",formatDate(getTimestemp(jsonObject.getString("CreatedDateTime"))));
                list.add(map);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return list;
    }

    private String formatDate(long timestemp){
        Date date=new Date(timestemp);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String strTime = format.format(date);
        return strTime;
    }

    private Long getTimestemp(String date){
        try {
            int start = date.indexOf("(")+1;
            int end = date.indexOf("+");
            String strs = date.substring(start, end);
            return Long.decode(strs);
        } catch (Exception e) {
        }
        return 0l;
    }

}
