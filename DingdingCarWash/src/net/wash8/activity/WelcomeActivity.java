package net.wash8.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;
import net.wash8.R;
import net.wash8.classbean.UserInfo;
import net.wash8.database.MyCarDBHelper;
import net.wash8.helpers.JSON_Parser;
import net.wash8.helpers.appURLFinal;
import net.wash8.net.AsyncHttpClient;
import net.wash8.net.AsyncHttpResponseHandler;
import net.wash8.net.RequestParams;

public class WelcomeActivity extends Activity {
    private MyCarDBHelper dbHelper;
    private SQLiteDatabase database;
    private AsyncHttpClient asyncHttpClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        initView();
        if (dbHelper.hasRegister()) {
            logIn();
        }else {
            Toast.makeText(this, "如需体验服务,请您先注册~", Toast.LENGTH_SHORT).show();
            Handler handler=new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent itHomeActivity=new Intent(WelcomeActivity.this,HomeActivity.class);
                    startActivity(itHomeActivity);
                    WelcomeActivity.this.finish();
                }
            },2000);

        }
    }

    private void initView() {
        asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.addHeader("Client-Secret", "db266d6766a7c79f1defd856caf1d877");
        dbHelper = new MyCarDBHelper(WelcomeActivity.this);
        database = dbHelper.getWritableDatabase();

    }

    private void logIn() {

        Asyhandler asyhandler = new Asyhandler();
        RequestParams params = new RequestParams();
        Cursor cursor=dbHelper.onQuery_UserInfo(database, "Mobile", "AuthCode", null, null, null, null, null, null,
                null, null,null);
        cursor.moveToFirst();
        appURLFinal.USER_NAME=cursor.getString(cursor.getColumnIndex("Mobile"));
        appURLFinal.PASS_WORD=cursor.getString(cursor.getColumnIndex("AuthCode"));
        cursor.close();
        if (appURLFinal.USER_NAME==null||appURLFinal.PASS_WORD==null)
        {
            Toast.makeText(this,"登录失败,你重试",Toast.LENGTH_SHORT).show();
        }else {
            params.put("Mobile", appURLFinal.USER_NAME);
            params.put("AuthCode", appURLFinal.PASS_WORD);
            asyncHttpClient.post(appURLFinal.LOG_IN, params, asyhandler);
        }
    }

    class Asyhandler extends AsyncHttpResponseHandler {
        public void onFailure(Throwable error, String content) {
            Log.i("tag","error"+content);
            Handler handler=new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent itHomeActivity = new Intent(WelcomeActivity.this, HomeActivity.class);
                    startActivity(itHomeActivity);
                    WelcomeActivity.this.finish();
                }
            }, 2000);
            dbHelper.onDeleteAll(database);
            dbHelper.onDeleteAll_UserInfo(database);
            Toast.makeText(WelcomeActivity.this, "登录失败,请重新注册", Toast.LENGTH_SHORT).show();
            super.onFailure(error, content);
        }

        @Override
        public void onSuccess(String content) {
            super.onSuccess(content);
            if (!content.equals("null")) {
                Log.i("tag", content);
                UserInfo userInfo = JSON_Parser.parseJSON_Info(content);

                dbHelper.onDeleteAll(database);
                dbHelper.onDeleteAll_UserInfo(database);
                if (userInfo.getCars()!=null) {
                    for (int i = 0; i < userInfo.getCars().size(); i++) {
                        if (userInfo.getCars().get(i).getEnabled().equals("true")) {
                            dbHelper.onInsert(database, userInfo.getID(), userInfo.getCars().get(i).getID(), userInfo.getCars().get(i)
                                            .getNumber
                                                    (), userInfo.getCars().get
                                            (i).getBrand(),
                                    userInfo.getCars().get(i).getModel(), userInfo.getCars().get(i).getImage(), userInfo.getCars().get
                                            (i).getColor());
                        }
                    }
                }
                dbHelper.onInsertUserInfo(database, userInfo.getMobile(), userInfo.getAuthCode(), userInfo.getLastName(),
                        userInfo.getGender(), userInfo.getJob(), userInfo.getWeixin(), userInfo.getBalance(), userInfo
                                .getAppVersion(), userInfo.getCreatedDateTime(), userInfo.getID(),userInfo.getNotOrdered());
                Log.i("tag", content);
            } else {
                dbHelper.onDeleteAll(database);
                dbHelper.onDeleteAll_UserInfo(database);
                Toast.makeText(WelcomeActivity.this, "登录失败,请重新注册", Toast.LENGTH_SHORT).show();
            }
            Handler handler=new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent itHomeActivity = new Intent(WelcomeActivity.this, HomeActivity.class);
                    startActivity(itHomeActivity);
                    WelcomeActivity.this.finish();
                }
            }, 2000);
        }
    }
}
