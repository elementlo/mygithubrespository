package net.wash8.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.android.volley.*;
import com.android.volley.toolbox.Volley;
import net.wash8.R;
import net.wash8.customview.CustomTitle;
import net.wash8.database.MyCarDBHelper;
import net.wash8.helpers.Base64FromTo;
import net.wash8.helpers.appURLFinal;
import net.wash8.net.AsyncHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AboutmeActivity extends Activity {
    private TextView tv_sex, et_userphone;
    private ProgressBar pb_aboutme;
    private EditText et_username, et_userjob, et_userwechat;
    private Button btn_submit_userinfo;

    private String LastName, Job, Weixin;
    private int sex_num;

    private SQLiteDatabase database;
    private MyCarDBHelper helper;
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutme);
        initView();
        setUserPhone();
    }

    private void setUserPhone() {
        helper = new MyCarDBHelper(this);
        database = helper.getWritableDatabase();
        Cursor cursor = helper.onQuery_UserInfo(database, "Mobile", null, "LastName", "Gender", null, null, null, null,
                null, null,null);

        cursor.moveToFirst();
        String mobile = cursor.getString(cursor.getColumnIndex("Mobile"));
        String lastname = cursor.getString(cursor.getColumnIndex("LastName"));
        String gender = cursor.getString(cursor.getColumnIndex("Gender"));
        if (lastname != null) {
            et_username.setText(lastname);
        }
            if (gender != null) {
                if ("0".equals(gender)) {
                    tv_sex.setText("男");
                } else if ("1".equals(gender)) {
                    tv_sex.setText("女");
                }else if ("2".equals(gender)) {
                    tv_sex.setText("其他");
                }
            }
        et_userphone.setText(mobile);
        cursor.close();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (0 == requestCode && data != null) {
            tv_sex.setText(data.getStringExtra("sex"));
        }

    }

    private void initView() {
        CustomTitle customTitle = (CustomTitle) findViewById(R.id.customTitle);
        customTitle.setIv_title_title("账号信息");
        customTitle.setIv_left_button(R.drawable.icon_back);
        customTitle.getIv_left_button().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        tv_sex = (TextView) findViewById(R.id.tv_sex);
        et_userphone = (TextView) findViewById(R.id.et_userphone);
        pb_aboutme = (ProgressBar) findViewById(R.id.pb_aboutme);
        et_username = (EditText) findViewById(R.id.et_username);
        et_userjob = (EditText) findViewById(R.id.et_userjob);
        et_userwechat = (EditText) findViewById(R.id.et_userwechat);
        btn_submit_userinfo = (Button) findViewById(R.id.btn_submit_userinfo);

        AsyncHttpClient httpClient = new AsyncHttpClient();
        String key = Base64FromTo.encodeBase64(appURLFinal.USER_NAME + ":" + appURLFinal.PASS_WORD);
        httpClient.addHeader("Client-Secret", "db266d6766a7c79f1defd856caf1d877");
        httpClient.addHeader("Authorization", "Basic " + key);
        mQueue= Volley.newRequestQueue(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_sexselect:
                Intent it_sexselect = new Intent(this, SexSelectActivity.class);
                startActivityForResult(it_sexselect, 0);
                break;
            case R.id.btn_submit_userinfo:
                pb_aboutme.setVisibility(View.VISIBLE);
                pb_aboutme.requestFocus();
                btn_submit_userinfo.setClickable(false);
                Cursor cursor = database.rawQuery("select ID from userinfo", null);
                cursor.moveToFirst();
                int id = cursor.getInt(cursor.getColumnIndex("ID"));
                cursor.close();
                if (id != 0) {
                    submitToServer(id);
                }
/*                if (!TextUtils.isEmpty(et_username.getText()) || !TextUtils.isEmpty(tv_sex.getText()) || !TextUtils
                        .isEmpty(et_userjob.getText()) || !TextUtils.isEmpty(et_userwechat.getText())) {
                } else {
                    Toast.makeText(this, "无信息更改", Toast.LENGTH_SHORT).show();
                }*/

                break;

        }
    }

    private void submitToServer(int id) {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject();
            String ID = String.valueOf(id);
            jsonObject.put("ID", ID);
            if (!TextUtils.isEmpty(tv_sex.getText())) {
                String sex = tv_sex.getText().toString();
                if ("男".equals(sex)) {
                    sex_num = 0;
                } else if ("女".equals(sex)) {
                    sex_num = 1;
                } else if ("其他".equals(sex)) {
                    sex_num = 2;
                }
            }
            jsonObject.put("Gender",sex_num);
            if (!TextUtils.isEmpty(et_username.getText())) {
                LastName = et_username.getText().toString();
            } else {
                LastName = null;
            }
            jsonObject.put("LastName", LastName);
            if (!TextUtils.isEmpty(et_userjob.getText())) {
                Job = et_userjob.getText().toString();
            } else {
                Job = "null";
            }
            jsonObject.put("Job", Job);
            if (!TextUtils.isEmpty(et_userwechat.getText())) {
                Weixin = et_userwechat.getText().toString();
            } else {
                Weixin = "null";
                jsonObject.put("Weixin", Weixin);
            }
            jsonObject.put("Weixin", Weixin);
            String param = jsonObject.toString();
            Log.i("tag",param);
            net.wash8.helpers.JsonRequest request=new net.wash8.helpers.JsonRequest(Request.Method.PUT, appURLFinal
                    .UPDATE_USERINFO, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.i("tag",response.toString()+"1111111");
                    intsertIntoDB(LastName, sex_num + "", Job, Weixin);
                    if (LastName != null) {
                        Intent it_backtomine = new Intent();
                        it_backtomine.putExtra("LastName", LastName);
                        setResult(RESULT_OK, it_backtomine);
                    }
                    btn_submit_userinfo.setClickable(true);
                    pb_aboutme.setVisibility(View.INVISIBLE);
                    AboutmeActivity.this.finish();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("tag",error.toString()+"");
                    pb_aboutme.setVisibility(View.INVISIBLE);
                    btn_submit_userinfo.setClickable(true);
                    Toast.makeText(AboutmeActivity.this, "网络请求出错,请重新尝试", Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String ,String >headers=new HashMap<String, String>();
                    String user_name=appURLFinal.USER_NAME;
                    String pass_word=appURLFinal.PASS_WORD;
                    String key = Base64FromTo.encodeBase64(user_name + ":" + pass_word);
                    headers.put("Authorization", "Basic " + key);
                    return  headers;
                }
            };
            mQueue.add(request);
           /* JsonRequest<JSONObject> request= new JsonObjectRequest(Request.Method.PUT, appURLFinal
                    .UPDATE_USERINFO, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.i("tag",response.toString()+"1111111");
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("tag",error.toString());
                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String ,String > headers=new HashMap<String, String>();
                    String key = Base64FromTo.encodeBase64(appURLFinal.USER_NAME + ":" + appURLFinal.PASS_WORD);
                    headers.put("Authorization", "Basic " + key);
                    return  headers;
                }
            };
            mQueue.add(request);*/

           /* StringEntity entity = new StringEntity(param,"UTF-8");
            Log.i("tag", param);
            httpClient.put(this, appURLFinal.UPDATE_USERINFO, entity, "application/json;charset=UTF-8", new
                    AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(String content) {
                            super.onSuccess(content);
                            Log.i("tag", "11111" + content);
                            intsertIntoDB(LastName, sex_num + "", Job, Weixin);
                            if (LastName != null) {
                                Intent it_backtomine = new Intent();
                                it_backtomine.putExtra("LastName", LastName);
                                setResult(RESULT_OK, it_backtomine);
                            }
                            btn_submit_userinfo.setClickable(false);
                            pb_aboutme.setVisibility(View.INVISIBLE);
                            AboutmeActivity.this.finish();
                        }

                        @Override
                        public void onFailure(Throwable error, String content) {
                            super.onFailure(error, content);
                            if (content != null) {
                                Log.i("tag", content);
                                pb_aboutme.setVisibility(View.INVISIBLE);
                                Toast.makeText(AboutmeActivity.this, "网络请求出错,请重新尝试", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });*/
        } catch (JSONException e) {
            e.printStackTrace();
        } /*catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*/
    }

    private void intsertIntoDB(String LastName, String Gender, String Job, String Weixin) {
        database.execSQL("update userinfo set LastName=" +"\'"+ LastName+ "\'"+ ",Gender=" + Gender + ",Job=" +
                "\'" + Job + "\'" + "," +
                "Weixin=" + "\'"+Weixin+"\'");
        helper.close();
    }
}
