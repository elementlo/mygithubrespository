package net.wash8.activity;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import net.wash8.R;
import net.wash8.classbean.UserInfo;
import net.wash8.customview.CustomTitle;
import net.wash8.database.MyCarDBHelper;
import net.wash8.helpers.JSON_Parser;
import net.wash8.helpers.appURLFinal;
import net.wash8.net.AsyncHttpClient;
import net.wash8.net.AsyncHttpResponseHandler;
import net.wash8.net.RequestParams;
import org.json.JSONObject;

/**
 * Created by admin1 on 2015/1/16.
 */
public class RegisterActivity extends Activity {
    private CustomTitle customTitle;

    private EditText phone, authCode;
    private Button btn_auth_code;

    private Button getAuthCode;

    private int count = 60;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView() {
        customTitle = (CustomTitle) findViewById(R.id.customTitle);
        customTitle.setIv_title_title("快速注册");
        customTitle.setIv_left_button(R.drawable.icon_back);
        customTitle.getIv_left_button().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btn_auth_code= (Button) findViewById(R.id.btn_auth_code);
        phone = (EditText) findViewById(R.id.et_phone);
        authCode = (EditText) findViewById(R.id.et_auth_code);
        getAuthCode = (Button) findViewById(R.id.btn_auth_code);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_auth_code:
                getAuthCode();
                break;
            case R.id.btn_submit:
                submit();
                break;
        }
    }

    private void count() {
        count--;
        btn_auth_code.setBackgroundResource(R.color.gray);
        getAuthCode.setText(count + "后重新发送");


        if (count > 0) {
            getAuthCode.postDelayed(new Runnable() {
                @Override
                public void run() {
                    count();
                }
            }, 1000);
        } else {
            getAuthCode.setText("获取短信验证码");
            btn_auth_code.setBackgroundResource(R.drawable.blue_shape);
            count = 60;
        }
    }

    private void getAuthCode() {
        if (count < 60) {
            Toast.makeText(this, "正在获取验证码", Toast.LENGTH_LONG).show();
            return;
        }

        String strPhone = phone.getText().toString();

        if (strPhone == null || "".equals(strPhone)) {
            Toast.makeText(this, "请输入手机号", Toast.LENGTH_LONG).show();
            return;
        }

        count();

        RequestParams params = new RequestParams();
        params.put("Mobile", strPhone);
        AsyncHttpClient asyncHttpclient = new AsyncHttpClient();
        asyncHttpclient.post(appURLFinal.AUTH_CODE, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, String content) {
                super.onSuccess(statusCode, content);
                Log.i("tag", content + "suc");
                try {
                    JSONObject json = new JSONObject(content);
                    authCode.setText(json.getString("AuthCode"));
                    count = 0;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable error, String content) {
                super.onFailure(error, content);
                Log.i("tag", content + "err");

            }

        });
    }

    private void submit() {
        final String strPhone = phone.getText().toString();

        if (strPhone == null || "".equals(strPhone)) {
            Toast.makeText(this, "请输入手机号", Toast.LENGTH_LONG).show();
            return;
        }

        final String strAuthCode = authCode.getText().toString();

        if (strAuthCode == null || "".equals(strAuthCode)) {
            Toast.makeText(this, "请输入验证码", Toast.LENGTH_LONG).show();
            return;
        }

        RequestParams params = new RequestParams();
        params.put("Mobile", strPhone);
        params.put("AuthCode", strAuthCode);

        AsyncHttpClient asyncHttpclient = new AsyncHttpClient();
        asyncHttpclient.post(appURLFinal.REGISTER, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, String content) {
                super.onSuccess(statusCode, content);
                Log.i("tag", content + "suc");
                appURLFinal.USER_NAME=strPhone;
                appURLFinal.PASS_WORD=strAuthCode;
                if (!content.equals("null")) {
                    UserInfo userInfo = JSON_Parser.parseJSON_Info(content);
                    MyCarDBHelper dbHelper = new MyCarDBHelper(RegisterActivity.this);
                    SQLiteDatabase database = dbHelper.getWritableDatabase();
                    dbHelper.onDeleteAll(database);
                    dbHelper.onDeleteAll_UserInfo(database);
                    if (userInfo != null) {
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
                        dbHelper.onInsertUserInfo(database, userInfo.getMobile(), userInfo.getAuthCode(), userInfo.getLastName(),
                                userInfo.getGender(), userInfo.getJob(), userInfo.getWeixin(), userInfo.getBalance(), userInfo
                                        .getAppVersion(), userInfo.getCreatedDateTime(), userInfo.getID(),userInfo.getNotOrdered());
                        System.out.println(userInfo);
                        finish();
                    }
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisterActivity.this, "登陆失败,请检查您的验证码", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable error, String content) {
                super.onFailure(error, content);
                Log.i("tag", content + "err");

            }

        });
    }
}
