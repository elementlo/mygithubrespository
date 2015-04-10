package net.wash8.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import net.wash8.R;
import net.wash8.classbean.HomeADPic;
import net.wash8.classbean.UserInfo;
import net.wash8.customview.CustomTitle;
import net.wash8.database.MyCarDBHelper;
import net.wash8.helpers.FontsOverride;
import net.wash8.helpers.HttpUtils;
import net.wash8.helpers.JSON_Parser;
import net.wash8.helpers.appURLFinal;
import net.wash8.net.AsyncHttpClient;
import net.wash8.net.AsyncHttpResponseHandler;
import net.wash8.net.RequestParams;
import net.wash8.singleadaptar.ListviewAdaptar;
import net.wash8.singleadaptar.MyPagerAdaptar;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomeActivity extends SlidingFragmentActivity {
    private ViewPager vp_pattern;
    private ImageView iv1;
    private ImageView iv2;
    private ImageView iv3;
    private ImageView iv_leftmenu;
    private ListView lv_indexlist;

    private int item, width;
    private HomeADPic homeADPic;
    private int[] data = {R.drawable.middle_pattern, R.drawable.middle_pattern, R.drawable.middle_pattern};
    private List<ImageView> iv_data;

    private AsyncHttpClient asyncHttpClient;
    private RotateAnimation rateanimation;
    private Timer timer;
    private ListviewAdaptar lvadaptar;
    private MyPagerAdaptar mpagerdaptar;
    private MyCarDBHelper dbHelper;
    private SQLiteDatabase database;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //全局自定义字体
        FontsOverride.setDefaultFont(this, "MONOSPACE", "fonts/heijianti.TTF");
        initView();
        setlistview();

        vplistenner();
        initRightMenu();

        loadHomeADPIC();

/*        if (dbHelper.hasRegister()) {
            logIn();
        }else {
            Toast.makeText(this,"如需体验服务,请您先注册~",Toast.LENGTH_SHORT).show();
        }*/
    }
/*
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
                Toast.makeText(HomeActivity.this, "登录失败,请重新注册", Toast.LENGTH_SHORT).show();
            }

        }
    }*/

    private void loadHomeADPIC() {
        HttpUtils.getGetResult(appURLFinal.HOME_ADV, new HttpUtils.getResultCallback() {
            @Override
            public void getMessage(String message) {
                Log.i("tag", message);
                homeADPic = JSON_Parser.parseJSON_AD(message);
                setMidViewPager(homeADPic);
            }
        });
    }

    public void setMidViewPager(HomeADPic homeADPic) {
        for (int i = 0; i < homeADPic.getItems().size(); i++) {
            ImageView iv_vp_pattern = new ImageView(HomeActivity.this);
            String picURL = appURLFinal.BASE_URL + "" + homeADPic.getItems().get(i).getImage();
            //Log.i("tag", picURL);
            HttpUtils.getNetBytes(this, picURL, iv_vp_pattern, false);
            iv_data.add(iv_vp_pattern);
        }
        vp_pattern.setAdapter(mpagerdaptar);

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_order:
                //判断是否注册
                if (dbHelper.hasRegister()) {
                    Intent intent_order = new Intent(this, OrderActitity.class);
                    startActivity(intent_order);
                } else {
                    Intent intent_register = new Intent(this, RegisterIndexActivity.class);
                    startActivity(intent_register);
                }

                break;
            case R.id.iv_mine:
                if (dbHelper.hasRegister()) {
                    Intent intent_mine = new Intent(this, MineActivity.class);
                    startActivity(intent_mine);
                } else {
                    Intent intent_register = new Intent(this, RegisterIndexActivity.class);
                    startActivity(intent_register);
                }
                break;
/*            case R.id.iv_shop:
                Intent it_coming=new Intent(this,ShopActivity.class);
                startActivity(it_coming);
                break;
            case R.id.iv_sign:
*//*                Intent it_sign=new Intent(this,MipcaActivityCapture.class);
                startActivity(it_sign);*//*
                Intent it_coming1=new Intent(this,ShopActivity.class);
                startActivity(it_coming1);
                break;*/
        }
    }

    private void initRightMenu() {
        //Fragment left_menu=new Fragment();
        setBehindContentView(R.layout.left_menu);
        //getSupportFragmentManager().beginTransaction().replace(R.layout.left_menu,left_menu).commit();
        SlidingMenu menu = getSlidingMenu();
        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        //设置滑动阴影特效
        //menu.setShadowWidthRes(R.dimen.shadow_width);
        //menu.setShadowDrawable(R.drawable.shadow);
        menu.setOnOpenListener(new SlidingMenu.OnOpenListener() {
            @Override
            public void onOpen() {
                iv_leftmenu.startAnimation(rateanimation);
            }
        });
        menu.setOnCloseListener(new SlidingMenu.OnCloseListener() {

            @Override
            public void onClose() {
                Animation rateanimation = new RotateAnimation(90f, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation
                        .RELATIVE_TO_SELF, 0.5f);
                rateanimation.setDuration(300);
                rateanimation.setFillAfter(true);
                iv_leftmenu.startAnimation(rateanimation);
            }
        });


        menu.setBehindWidth(width / 2);
        //menu.setBehindOffset(densityDpi);
        menu.setFadeDegree(0.35f);
        menu.findViewById(R.id.about).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, AboutActivity.class);
                startActivity(intent);

            }
        });
        menu.findViewById(R.id.update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this,"已是最新版本",Toast.LENGTH_SHORT).show();

            }
        });
        menu.findViewById(R.id.help).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it_help=new Intent(HomeActivity.this,HelpActivity.class);
                startActivity(it_help);
            }
        });
/*        menu.findViewById(R.id.help).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it_help=new Intent(HomeActivity.this,)
            }
        });*/

        menu.findViewById(R.id.comment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyCarDBHelper helper = new MyCarDBHelper(HomeActivity.this);
                if (helper.hasRegister()) {
                    Intent intent = new Intent(HomeActivity.this, CommentActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent_register = new Intent(HomeActivity.this, RegisterIndexActivity.class);
                    startActivity(intent_register);
                }
            }
        });


    }

    private void vplistenner() {
        vp_pattern.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i) {
                    case 0:
                        iv1.setBackgroundResource(R.drawable.dot_select);
                        iv2.setBackgroundResource(R.drawable.dot_empty);
                        iv3.setBackgroundResource(R.drawable.dot_empty);
                        break;
                    case 1:
                        iv2.setBackgroundResource(R.drawable.dot_select);
                        iv1.setBackgroundResource(R.drawable.dot_empty);
                        iv3.setBackgroundResource(R.drawable.dot_empty);
                        break;
                    case 2:
                        iv3.setBackgroundResource(R.drawable.dot_select);
                        iv2.setBackgroundResource(R.drawable.dot_empty);
                        iv1.setBackgroundResource(R.drawable.dot_empty);
                        break;
                }
                item = i;
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void setlistview() {
        lv_indexlist.setAdapter(lvadaptar);
        lv_indexlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:         //服务介绍
                        Intent intent = new Intent(HomeActivity.this, ServicesIntroduceActivity.class);
                        startActivity(intent);
                        break;
                    case 1:         //特价套餐
                        Intent intent1 = new Intent(HomeActivity.this, SalePriceActivity.class);
                        startActivity(intent1);
                        break;
                }
            }
        });
    }

    private void initView() {
        asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.addHeader("Client-Secret", "db266d6766a7c79f1defd856caf1d877");
        dbHelper = new MyCarDBHelper(HomeActivity.this);
        database = dbHelper.getWritableDatabase();

        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        width = metric.widthPixels;     // 屏幕宽度（像素）
        //int height = metric.heightPixels;   // 屏幕高度（像素）
        //float density = metric.density;      // 屏幕密度（0.75 / 1.0 / 1.5）
        //densityDpi = metric.densityDpi;  // 屏幕密度DPI（120 / 160 / 240）
        /*
      Called when the activity is first created.
     */
        CustomTitle customTitle = (CustomTitle) findViewById(R.id.custom_title);
        customTitle.setIv_left_button(R.drawable.left_menu);
        vp_pattern = (ViewPager) findViewById(R.id.vp_pattern);
        lv_indexlist = (ListView) findViewById(R.id.lv_indexlist);
        iv_leftmenu = customTitle.getIv_left_button();
        iv_leftmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_leftmenu.startAnimation(rateanimation);
                getSlidingMenu().showMenu();
            }
        });
        iv1 = (ImageView) findViewById(R.id.iv1);
        iv2 = (ImageView) findViewById(R.id.iv2);
        iv3 = (ImageView) findViewById(R.id.iv3);
        iv_data = new ArrayList<ImageView>();
        mpagerdaptar = new MyPagerAdaptar(iv_data);
        lvadaptar = new ListviewAdaptar(this);
        iv1.setBackgroundResource(R.drawable.dot_select);
        setOpenAnimation();
        autoScroll();


    }

    private Handler autoScrollHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            vp_pattern.setCurrentItem(item);
            if (item == data.length - 1) {
                item = 0;
            } else {
                item++;
            }

        }
    };

    private void autoScroll() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                autoScrollHandler.sendEmptyMessage(0);
            }
        }, 3000, 3000);
    }

    private void setOpenAnimation() {
        //设置动画集合
        rateanimation = new RotateAnimation(0f, 90f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rateanimation.setDuration(300);
        rateanimation.setFillAfter(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }
}
