package net.wash8.activity;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import net.wash8.R;
import net.wash8.adapter.SecondMenuAdapter;
import net.wash8.customview.CustomTitle;
import net.wash8.customview.JumpMenuUtil;
import net.wash8.customview.AsortList.AssortView;
import net.wash8.customview.AsortList.AssortView.OnTouchAssortListener;
import net.wash8.customview.AsortList.PinyinAdapter;
import net.wash8.database.MyCarDBHelper;
import net.wash8.helpers.CarBrandParser;
import net.wash8.helpers.appURLFinal;
import net.wash8.net.AsyncHttpClient;
import net.wash8.net.AsyncHttpResponseHandler;

import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView.OnItemClickListener;

/**
 * Created by admin1 on 2014/12/19.
 */
public class CarInfoActivity extends Activity implements View.OnClickListener {
	private String[] provinces = {"京","沪","津","渝","黑","吉","辽","蒙","冀","新","甘","青","陕","宁","豫","鲁","晋","皖"
			,"鄂","湘","苏","川","黔","滇","桂","藏","浙","赣","粤","闽","台","琼","港","澳"};
	
    private View iv_chooseColor, iv_chooseBand, iv_chooseImage;
    private View carBrandLayout;
    private View carColorLayout;
    private View secondMenu;
    private CustomTitle customTitle;
    private TextView tv_carbrand, tv_mycarbrand, tv_mycarcolor,tv_province;
    private ImageView iv_showcar;
    private EditText et_carnumber;
    private ProgressBar pb_carinfo;
    private LinearLayout ll_black, ll_white, ll_red, ll_blue, ll_silvergray, ll_darkgray, ll_yellow, ll_green,ll_champagne,ll_coffee,ll_purple,ll_orange;

    private PinyinAdapter adapter;
    private ExpandableListView eListView;
    private AssortView assortView;
//    private List<String> names;
//    private List<String> hotCars;
    private CarBrandParser carBrandParser;
    private List<LinearLayout> list_carcolor;
    private String base64, carbrand, carmodel;
    private SQLiteDatabase sqldb;
    private String[] color = {"黑色", "白色", "红色", "蓝色", "银色", "深灰色", "黄色", "绿色","香槟色","咖啡色","紫色","橙色"};
    private byte[] bytes;

    private MyCarDBHelper database;
    private AsyncHttpClient asyncHttpClient;
    private android.os.Handler handler;


    private View provinceLayout;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_info);
        initView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            ContentResolver cr_carpic = this.getContentResolver();
            try {
                //将图片压缩以便存入数据库
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                Bitmap bitmap;
                BitmapFactory.decodeStream(cr_carpic.openInputStream(uri), null, options);
                int srcWidth = options.outWidth;
                int srcHeight = options.outHeight;
                int desWidth = 0;
                int desHeight = 0;
                double ratio = 0.0;
                if (srcWidth > srcHeight) {
                    ratio = srcWidth / 600;
                    desWidth = 600;
                    desHeight = (int) (srcHeight / ratio);
                } else {
                    ratio = srcHeight / 360;
                    desHeight = 360;
                    desWidth = (int) (srcWidth / ratio);
                }
                BitmapFactory.Options newOpts = new BitmapFactory.Options();
                newOpts.inSampleSize = (int) (ratio + 0.9);
                newOpts.inJustDecodeBounds = false;
                newOpts.outWidth = desWidth;
                newOpts.outHeight = desHeight;
                bitmap= BitmapFactory.decodeStream(cr_carpic.openInputStream(uri), null, newOpts);
                ByteArrayOutputStream bstream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 50, bstream);
                BufferedInputStream in=new BufferedInputStream(cr_carpic.openInputStream(uri));
                byte[] temp=new byte[1024];
                int size=0;
                while ((size=in.read(temp))!=-1){
                    bstream.write(temp,0,size);
                }
                in.close();
                bytes = bstream.toByteArray();
                //base64 = Base64.encodeToString(bytes, Base64.DEFAULT);
                iv_showcar.setImageBitmap(bitmap);
                bstream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == 2) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                final Bitmap bitmap = bundle.getParcelable("data");
                ByteArrayOutputStream bstream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 50, bstream);
                bytes = bstream.toByteArray();
                iv_showcar.setImageBitmap(bitmap);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }



    private void initView() {
        asyncHttpClient = new AsyncHttpClient();
        /*asyncHttpClient.addHeader("Client-Secret", "db266d6766a7c79f1defd856caf1d877");
        Log.i("tag",appURLFinal.USER_NAME + ":" + appURLFinal.PASS_WORD);
        String user_name=appURLFinal.USER_NAME;
        String pass_word=appURLFinal.PASS_WORD;
        String key = Base64FromTo.encodeBase64(user_name + ":" + pass_word);
        asyncHttpClient.addHeader("Authorization", "Basic " + key);*/
        appURLFinal.addHttpHeader(asyncHttpClient);

        database = new MyCarDBHelper(this);
        sqldb = database.getWritableDatabase();

        list_carcolor = new ArrayList<LinearLayout>();

        et_carnumber = (EditText) findViewById(R.id.et_carnumber);
        et_carnumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    et_carnumber.setTextColor(getResources().getColor(R.color.black));
                    et_carnumber.setText("B");
                    et_carnumber.setSelection(1);
                }else {
                    et_carnumber.setTextColor(getResources().getColor(R.color.gray));
                }
            }
        });
        et_carnumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                et_carnumber.removeTextChangedListener(this);//解除文字改变事件
                et_carnumber.setText(s.toString().toUpperCase());//转换
                et_carnumber.setSelection(s.toString().length());//重新设置光标位置
                et_carnumber.addTextChangedListener(this);//重新绑
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        iv_showcar = (ImageView) findViewById(R.id.iv_showcar);
        tv_mycarbrand = (TextView) findViewById(R.id.tv_mycarbrand);
        tv_mycarcolor = (TextView) findViewById(R.id.tv_mycarcolor);
        iv_chooseColor = findViewById(R.id.iv_chooseColor);
        iv_chooseBand = findViewById(R.id.iv_chooseBrand);
        iv_chooseImage = findViewById(R.id.iv_chooseImage);
        customTitle = (CustomTitle) findViewById(R.id.custom_title);
        customTitle.setIv_title_title("车辆信息");
        customTitle.setIv_left_button(R.drawable.icon_back);
        customTitle.getIv_left_button().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        pb_carinfo = (ProgressBar) findViewById(R.id.pb_carinfo);
        tv_province= (TextView) findViewById(R.id.tv_province);

        iv_chooseColor.setOnClickListener(this);
        iv_chooseImage.setOnClickListener(this);
        iv_chooseBand.setOnClickListener(this);
    }

    private boolean isSecondMenuShow = false;
    private String curSelectedPosition = "";

    //弹出二级菜单
    private void popSecondMenu(final String[] data, final String car_brand) {
        if (secondMenu == null)
            secondMenu = carBrandLayout.findViewById(R.id.lv_secondMenu);

        SecondMenuAdapter adapter = new SecondMenuAdapter(CarInfoActivity.this, data);
        ListView secondMenuList = (ListView)secondMenu.findViewById(R.id.lv_secondMenuList);
        secondMenuList.setAdapter(adapter);
        secondMenu.setVisibility(View.VISIBLE);
        playAnimation(secondMenu, true, null);
        isSecondMenuShow = true;
        secondMenuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (data[i] != null && car_brand != null) {
                    carbrand = car_brand;
                    carmodel = data[i];
                    tv_mycarbrand.setText(car_brand + " " + data[i]);
                    JumpMenuUtil.dismiss(CarInfoActivity.this);
                }
            }
        });
    }

    //收回二级菜单
    private void dismissSecondMenu() {
        playAnimation(secondMenu, false, new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                secondMenu.setVisibility(View.GONE);
                isSecondMenuShow = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    //播放二级菜单弹出动画
    private static void playAnimation(View v, boolean isPop, Animation.AnimationListener animationListener) {
        //移动动画
        TranslateAnimation translateAnimation =
                new TranslateAnimation(
                        Animation.RELATIVE_TO_SELF, isPop ? 1 : 0,
                        Animation.RELATIVE_TO_SELF, isPop ? 0 : 1,
                        Animation.RELATIVE_TO_SELF, 0f,
                        Animation.RELATIVE_TO_SELF, 0f);
        translateAnimation.setDuration(300);
        v.clearAnimation();
        v.startAnimation(translateAnimation);
        v.setAnimation(translateAnimation);
        if (animationListener != null)
            translateAnimation.setAnimationListener(animationListener);
    }

    //初始化汽车品牌列表
    private void initSortList() {

        carBrandLayout = LayoutInflater.from(this).inflate(R.layout.choose_car_brand_menu, null);
        eListView = (ExpandableListView) carBrandLayout.findViewById(R.id.elist);
        assortView = (AssortView) carBrandLayout.findViewById(R.id.assort);

        if(carBrandParser == null)
            carBrandParser = new CarBrandParser(this);

        adapter = new PinyinAdapter(this, carBrandParser.carBrand,carBrandParser.hotCarBrand);
        eListView.setAdapter(adapter);
        //展开所有
        for (int i = 0, length = adapter.getGroupCount(); i < length; i++) {
            eListView.expandGroup(i);
        }


        eListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                tv_carbrand = (TextView) v.findViewById(R.id.tv_brand);
                String selectedPosition = groupPosition + "_" + childPosition;
                if (curSelectedPosition.equals(selectedPosition)) {
                    if (isSecondMenuShow) {
                        dismissSecondMenu();
                        curSelectedPosition = "";
                    } else {

                        TextView carBrand = (TextView)v.findViewById(R.id.tv_brand);
                        String strModel = carBrandParser.carModel.get(carBrand.getText().toString());
                        strModel = strModel.replaceAll("\\s*", "");
                        String[] data = strModel.split("\\|");
                        popSecondMenu(data, tv_carbrand.getText().toString());
                    }
                } else {
                    TextView carBrand = (TextView)v.findViewById(R.id.tv_brand);
                    String strModel = carBrandParser.carModel.get(carBrand.getText().toString());
                    strModel = strModel.replaceAll("\\s*", "");
                    String[] data = strModel.split("\\|");
                    popSecondMenu(data, tv_carbrand.getText().toString());
                    curSelectedPosition = selectedPosition;
                }
                return false;
            }
        });

        //字母按键回调
        assortView.setOnTouchAssortListener(new OnTouchAssortListener() {

            View layoutView = LayoutInflater.from(CarInfoActivity.this)
                    .inflate(R.layout.alert_dialog_menu_layout, null);
            TextView text = (TextView) layoutView.findViewById(R.id.content);
            PopupWindow popupWindow;

            public void onTouchAssortListener(String str) {
                int index;
                if("热".equals(str)){
                   index = 0;
                }
                else
                    index = adapter.getAssort().getHashList().indexOfKey(str)+1;
                if (index != -1) {
                    eListView.setSelectedGroup(index);
                }
                if (popupWindow != null) {
                    text.setText(str);
                } else {
                    popupWindow = new PopupWindow(layoutView,
                            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,
                            false);
                    // 显示在Activity的根视图中心
                    popupWindow.showAtLocation(getWindow().getDecorView(),
                            Gravity.CENTER, 0, 0);
                }
                text.setText(str);
            }

            public void onTouchAssortUP() {
                if (popupWindow != null)
                    popupWindow.dismiss();
                popupWindow = null;
            }
        });

    }

    //设置title属性
    private void initCustomTitle(View view) {
        customTitle = (CustomTitle) view.findViewById(R.id.custom_title);
        customTitle.setIv_left_button(R.drawable.icon_back);

        customTitle.getIv_left_button().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSecondMenuShow) {
                    dismissSecondMenu();
                    curSelectedPosition = "";
                }
                JumpMenuUtil.dismiss(CarInfoActivity.this);
            }
        });
    }
    
    private void initProvince(){
    	GridView carProvinceView = (GridView) provinceLayout.findViewById(R.id.gv_car_province);
    	List<Map<String,String>> data = new ArrayList<Map<String,String>>();
    	for(String s : provinces){
    		Map<String,String> map = new HashMap<String, String>();
    		map.put("text", s);
    		data.add(map);
    	}
    	carProvinceView.setAdapter(new SimpleAdapter(getApplicationContext(), data, R.layout.adapter_car_province, 
    			new String[]{"text"}, new int[]{R.id.tv_province}));
        carProvinceView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                TextView provinceView = (TextView) findViewById(R.id.tv_province);
                provinceView.setText(provinces[arg2]);
                JumpMenuUtil.dismiss(CarInfoActivity.this);
			}
		});
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(isSecondMenuShow){
                dismissSecondMenu();
                curSelectedPosition = "";
                return true;
            }
            if(JumpMenuUtil.isShow){
                JumpMenuUtil.dismiss(CarInfoActivity.this);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public void onClick(View v) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        switch (v.getId()) {
        	case R.id.iv_choose_province:
        	case R.id.tv_province:   //选择省份
                if (imm != null) {
                    imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(),
                            0);
                }
        		if(provinceLayout == null){
        			provinceLayout = LayoutInflater.from(CarInfoActivity.this).inflate(R.layout.choose_car_province, null);
        			initCustomTitle(provinceLayout);
        			initProvince();
        		}
        		JumpMenuUtil.jumpMenu(CarInfoActivity.this, provinceLayout);	
        		break;
            case R.id.ll_carColor:
            case R.id.iv_chooseColor:   //选择颜色
                if (imm != null) {
                    tv_mycarbrand.requestFocus();
                    imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(),
                            0);
                }
                if (carColorLayout == null) {
                    carColorLayout = LayoutInflater.from(CarInfoActivity.this).inflate(R.layout.choose_car_color_menu, null);
                    initCustomTitle(carColorLayout);
                    initColorView();
                    setCarColorListener();
                }
                JumpMenuUtil.jumpMenu(CarInfoActivity.this, carColorLayout);
                break;
            case R.id.ll_car:
            case R.id.iv_chooseBrand:   //选择品牌
                if (imm != null) {
                    tv_mycarbrand.requestFocus();
                    imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(),
                            0);
                }
                if (carBrandLayout == null) {
                    initSortList();
                    initCustomTitle(carBrandLayout);
                }
                JumpMenuUtil.jumpMenu(CarInfoActivity.this, carBrandLayout);
                break;
            case R.id.iv_showcar:
            case R.id.ll_chooseImage:   //选择图片
                if (imm != null) {
                    tv_mycarbrand.requestFocus();
                    imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(),
                            0);
                }
                final String[] alter = {"拍照", "图库"};
                new AlertDialog.Builder(CarInfoActivity.this).setTitle("选择车辆照片").setItems(alter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                dialog.dismiss();
                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(intent, 2);
                                break;
                            case 1:
                                dialog.dismiss();
                                Intent it_selectphoto = new Intent();
                                it_selectphoto.setType("image/*");
                                it_selectphoto.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(it_selectphoto, 1);
                                break;
                        }
                    }
                }).show();
                break;
            case R.id.ib_submit_mycar:

                addDBdata();
                handler = new android.os.Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        Intent intent = new Intent();
                        setResult(20, intent);
                        Toast.makeText(CarInfoActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                        pb_carinfo.setVisibility(View.INVISIBLE);
                        CarInfoActivity.this.finish();
                        super.handleMessage(msg);
                    }
                };
                break;

        }
    }

    private void submitToService(JSONObject param) {
        //StringEntity stringEntity = null;
        AsyHandler handler = new AsyHandler();
   /*     try {
            stringEntity = new StringEntity(param, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*/
        //asyncHttpClient.put(this, appURLFinal.ADDCAR, stringEntity, "application/json;charset=utf-8", handler);
        asyncHttpClient.post(this,appURLFinal.ADDCAR,param,"application/json;charset=utf-8",handler);
    }

    class AsyHandler extends AsyncHttpResponseHandler {
        @Override
        public void onSuccess(String content) {
            super.onSuccess(content);
            Log.i("tag", "111111111" + content);
            if (!content.equals("null")) {
                Log.i("tag", "111111111" + content);
                JSONObject jsonObject;
                String UserID, Number, Brand, Model, Image = null, Enabled = null, ID, Color;
                try {
                    jsonObject = new JSONObject(content);
                    UserID = jsonObject.getString("UserID");
                    Number = jsonObject.getString("Number");
                    Brand = jsonObject.getString("Brand");
                    Model = jsonObject.getString("Model");
                    Image = jsonObject.getString("Image");
                    Color = jsonObject.getString("Color");
                    ID = jsonObject.getString("ID");
                    database.onInsert(sqldb, UserID, ID, Number, Brand, Model, Image, Color);
                    //车辆列表为空时传值
                    setEmptyList(UserID,ID,Image,Number);
                    handler.sendEmptyMessage(0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.i("tag", Image + "11111111111");
            }
        }

        @Override
        public void onFailure(Throwable error, String content) {
            super.onFailure(error, content);
            Log.i("tag", content);
            Toast.makeText(CarInfoActivity.this,"添加车辆失败,请重试",Toast.LENGTH_SHORT).show();
            pb_carinfo.setVisibility(View.INVISIBLE);
        }
    }

    private void setEmptyList(String UserID,String ID,String Image,String Number) {
        Intent intent=getIntent();
        boolean flag=intent.getBooleanExtra("emptylist",false);
        if(flag){
            Log.i("tag","info"+UserID+ID+Image+Number);
            Intent it_submitcar=new Intent();
            it_submitcar.putExtra("UserID",UserID);
            it_submitcar.putExtra("CAR_ID",ID);
            it_submitcar.putExtra("Image",Image);
            it_submitcar.putExtra("Number",Number);
            setResult(RESULT_OK,it_submitcar);
            CarInfoActivity.this.finish();
        }
    }

    /*class runnable implements Runnable {
        String UserID, Number, Brand, Model, Image, Color;

        public runnable(String UserID, String Number, String Brand, String Model, String Image, String Color) {
            this.UserID = UserID;
            this.Number = Number;
            this.Brand = Brand;
            this.Image = Image;
            this.Color = Color;
            this.Model = Model;
        }

        @Override
        public void run() {
            database.onInsert(sqldb, UserID, Number, Brand, Model, Image, Color);
            Handler handler=new Handler() {
            }
            Intent intent = new Intent();
            intent.putExtra("addDB", true);
            setResult(20, intent); n nbn
        }
    }*/

    //将用户信息加入数据库
    private void addDBdata() {
        if (bytes!=null){
        base64 = Base64.encodeToString(bytes, Base64.DEFAULT);
        }
        if (TextUtils.isEmpty(et_carnumber.getText()) || "请选择您车辆的品牌".equals(tv_mycarbrand.getText().toString()) ||
                "请选择您车辆的颜色".equals(tv_mycarcolor.getText().toString()) || base64 == null) {
            Toast.makeText(CarInfoActivity.this, "请补全您的信息", Toast.LENGTH_SHORT).show();
        } else {
            if (!et_carnumber.getText().toString().matches("^[A-Za-z]{1}[A-Za-z_0-9]{5}$")) {
                Toast.makeText(CarInfoActivity.this, "请填写正确的车牌号码", Toast.LENGTH_SHORT).show();
            } else {
                pb_carinfo.setVisibility(View.VISIBLE);
                String str_color = tv_mycarcolor.getText().toString();
                String number = tv_province.getText().toString() + et_carnumber.getText().toString().substring(0,1)
                        +" "+et_carnumber.getText().toString().substring(1);
                Log.i("tag",number+"******");
                String color = "-1";

                if ("白色".equals(str_color)) {
                    color = "0";
                } else if ("黑色".equals(str_color)) {
                    color = "1";
                } else if ("红色".equals(str_color)) {
                    color = "2";
                } else if ("蓝色".equals(str_color)) {
                    color = "3";
                } else if ("银色".equals(str_color)) {
                    color = "4";
                } else if ("深灰色".equals(str_color)) {
                    color = "5";
                } else if ("黄色".equals(str_color)) {
                    color = "6";
                } else if ("绿色".equals(str_color)) {
                    color = "7";
                } else if("香槟色".equals(str_color)){
                    color = "8";
                } else if("咖啡色".equals(str_color)){
                    color = "9";
                } else if("紫色".equals(str_color)){
                    color = "10";
                } else if("橙色".equals(str_color)){
                    color = "11";
                }
                JSONObject param = toJsonString(number, carbrand, carmodel, color, base64);
                if (param != null) {
                    Log.i("tag", param.toString());
                    submitToService(param);
                } else {
                    Toast.makeText(this, "网络错误导致登录失败,请重新登陆", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    private JSONObject toJsonString(String Number, String Brand, String Model, String Color, String Image) {
        //SharedPreferences sp = getSharedPreferences("UserID", MODE_PRIVATE);
        MyCarDBHelper helper = new MyCarDBHelper(this);
        SQLiteDatabase database = helper.getReadableDatabase();
        Cursor cursor = database.rawQuery("select ID from userinfo", null);
        cursor.moveToFirst();
        if (cursor != null) {
            int UserID = cursor.getInt(cursor.getColumnIndex("ID"));
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject = new JSONObject();
            JSONObject jsonObject_outer = new JSONObject();
            String params = null;
            try {
                jsonObject.put("UserID", UserID);
                jsonObject.put("Number", Number);
                jsonObject.put("Brand", Brand);
                jsonObject.put("Model", Model);
                jsonObject.put("Color", Color);
                jsonObject.put("Image", Image);
                jsonArray.put(jsonObject);
                jsonObject_outer.put("ID", UserID);
                jsonObject_outer.put("Cars", jsonArray);
                //params = jsonObject_outer.toString();
                return jsonObject_outer;
            } catch (JSONException e) {

            }
            return jsonObject_outer;
        }
        return null;
    }


    private void initColorView() {
        ll_black = (LinearLayout) carColorLayout.findViewById(R.id.ll_black);
        ll_white = (LinearLayout) carColorLayout.findViewById(R.id.ll_white);
        ll_red = (LinearLayout) carColorLayout.findViewById(R.id.ll_red);
        ll_blue = (LinearLayout) carColorLayout.findViewById(R.id.ll_blue);
        ll_silvergray = (LinearLayout) carColorLayout.findViewById(R.id.ll_silvergray);
        ll_darkgray = (LinearLayout) carColorLayout.findViewById(R.id.ll_darkgray);
        ll_yellow = (LinearLayout) carColorLayout.findViewById(R.id.ll_yellow);
        ll_green = (LinearLayout) carColorLayout.findViewById(R.id.ll_green);
        ll_champagne = (LinearLayout) carColorLayout.findViewById(R.id.ll_champagne);
        ll_coffee = (LinearLayout) carColorLayout.findViewById(R.id.ll_coffee);
        ll_purple = (LinearLayout) carColorLayout.findViewById(R.id.ll_purple);
        ll_orange = (LinearLayout) carColorLayout.findViewById(R.id.ll_orange);
        list_carcolor.add(ll_black);
        list_carcolor.add(ll_white);
        list_carcolor.add(ll_red);
        list_carcolor.add(ll_blue);
        list_carcolor.add(ll_silvergray);
        list_carcolor.add(ll_darkgray);
        list_carcolor.add(ll_yellow);
        list_carcolor.add(ll_green);
        list_carcolor.add(ll_champagne);
        list_carcolor.add(ll_coffee);
        list_carcolor.add(ll_purple);
        list_carcolor.add(ll_orange);
    }

    //选择颜色监听
    private void setCarColorListener() {
        int i;
        CarColorListener colorListener;
        Log.i("tag", list_carcolor.size() + "");
        for (i = 0; i < list_carcolor.size(); i++) {
            colorListener = new CarColorListener(i);
            list_carcolor.get(i).setOnClickListener(colorListener);
        }
    }

    class CarColorListener implements View.OnClickListener {
        private int i;
        private List<LinearLayout> list;

        public CarColorListener(int i) {
            this.i = i;
        }

        @Override
        public void onClick(View view) {
            String txt_color = color[i];
            tv_mycarcolor.setText(txt_color);
            JumpMenuUtil.dismiss(CarInfoActivity.this);
        }
    }
}
