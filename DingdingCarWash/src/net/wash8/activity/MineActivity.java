package net.wash8.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import net.wash8.R;
import net.wash8.customview.CustomDialog;
import net.wash8.customview.CustomTitle;
import net.wash8.database.MyCarDBHelper;

public class MineActivity extends Activity {
    private TextView tv_username;

    private MyCarDBHelper helper;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine);
        initView();
        setUsername();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (0 == requestCode && data != null) {
            String LastName = data.getStringExtra("LastName");
            tv_username.setText(LastName);
        }
    }

    private void setUsername() {
        helper = new MyCarDBHelper(this);
        database = helper.getReadableDatabase();
        Cursor cursor = helper.onQuery_UserInfo(database, "Mobile", null, "LastName", null, null, null, null, null, null,
                null, null);
        cursor.moveToFirst();
        String mobile = cursor.getString(cursor.getColumnIndex("Mobile"));
        String lastname = cursor.getString(cursor.getColumnIndex("LastName"));
        if (mobile != null) {
            if (lastname != null) {
                tv_username.setText(lastname);
            } else {
                tv_username.setText(mobile);
            }
            cursor.close();
            helper.close();
        }
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_aboutme:
                Intent it_aboutme = new Intent(this, AboutmeActivity.class);
                startActivityForResult(it_aboutme, 0);
                break;
            case R.id.rl_mycar:
                Intent it_mycar = new Intent(this, MyCarActivity.class);
                it_mycar.putExtra("itsmycar", true);
                startActivity(it_mycar);
                break;
            case R.id.rl_member:
                Intent it_member = new Intent(this, MemberActivity.class);
                startActivity(it_member);
                break;
            case R.id.rl_myorder:
                Intent it_myorder = new Intent(this, MyOrderActivity.class);
                startActivity(it_myorder);
                break;
            case R.id.rl_coupon:
                Intent it_coupon = new Intent(this, CouponActivity.class);
                startActivity(it_coupon);
                break;
        }
    }

    private void initView() {
        CustomTitle customTitle = (CustomTitle) findViewById(R.id.customTitle);
        customTitle.setIv_title_title("我 的");
        customTitle.setIv_left_button(R.drawable.icon_back);
        customTitle.getIv_left_button().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        customTitle.setIv_right_button(R.drawable.icon_quit);
        ImageView iv_quit = customTitle.getIv_right_button();
        iv_quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CustomDialog.Builder(MineActivity.this).setTitle(null).setMessage("确认要退出当前账号吗?").setPositiveButton
                        ("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                database=helper.getWritableDatabase();

                                helper.onDeleteAll(database);
                                helper.onDeleteAll_UserInfo(database);
                                Intent it_home = new Intent(MineActivity.this, HomeActivity.class);
                                startActivity(it_home);
                            }
                        }).setNegativeButton("取消", null).create().show();


            }
        });

        tv_username = (TextView) findViewById(R.id.tv_username);
    }
}
