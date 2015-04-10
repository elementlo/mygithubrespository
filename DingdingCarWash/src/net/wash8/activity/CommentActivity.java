package net.wash8.activity;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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

/**
 * Created by lijie on 2015/1/13.
 */
public class CommentActivity extends Activity {

    private CustomTitle customTitle;

    private EditText title,content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        initView();
    }

    private void initView(){
        customTitle = (CustomTitle) findViewById(R.id.customTitle);
        customTitle.setIv_title_title("投诉建议");
        customTitle.setIv_left_button(R.drawable.icon_back);
        customTitle.getIv_left_button().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        title = (EditText)findViewById(R.id.et_title);
        content = (EditText)findViewById(R.id.et_content);
    }

    public void onClick(View view){
        String strTitle = title.getText().toString();
        String strContent = content.getText().toString();

        if(strContent == null || "".equals(strContent)){
            Toast.makeText(this,"说两句呗",Toast.LENGTH_LONG).show();
            return;
        }

        if(strTitle == null || "".equals(strTitle)){
            strTitle = "我对爱洗吧客户端Android版的建议";
        }

        MyCarDBHelper helper=new MyCarDBHelper(this);
        SQLiteDatabase database=helper.getReadableDatabase();
        Cursor cursor=database.rawQuery("select ID from userinfo", null);
        cursor.moveToFirst();
        int userid=cursor.getInt(cursor.getColumnIndex("ID"));

        RequestParams params = new RequestParams();
        params.put("Title", strTitle);
        params.put("Content", strContent);
        params.put("AppVersion","2");
        params.put("UserID",""+userid);
        AsyncHttpClient asyncHttpclient = new AsyncHttpClient();
        appURLFinal.addHttpHeader(asyncHttpclient);
        asyncHttpclient.post(appURLFinal.SUBMIT_COMMENT, params, new AsyncHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, String content) {
                super.onSuccess(statusCode, content);
                Toast.makeText(CommentActivity.this,"提交成功",Toast.LENGTH_SHORT).show();
                finish();
                Log.i("tag", content+"");
            }

            @Override
            public void onFailure(Throwable error, String content) {
                super.onFailure(error, content);
                Toast.makeText(CommentActivity.this,"提交失败",Toast.LENGTH_SHORT).show();
                Log.i("tag", content+"");

            }

        });
    }
}
