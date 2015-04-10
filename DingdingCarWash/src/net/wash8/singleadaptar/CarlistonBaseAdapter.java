package net.wash8.singleadaptar;

import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import net.wash8.R;
import net.wash8.activity.MyCarListActivity;
import net.wash8.customview.CustomDialog;
import net.wash8.database.MyCarDBHelper;
import net.wash8.helpers.HttpUtils;
import net.wash8.helpers.appURLFinal;
import net.wash8.net.AsyncHttpClient;
import net.wash8.net.AsyncHttpResponseHandler;

import java.util.List;
import java.util.Map;

public class CarlistonBaseAdapter extends BaseAdapter {
    private ListView lv_carlist;

    private List<Map<String, Object>> data;
    private String number, model, brand, color;

    private Context con;
    private AsyncHttpClient httpClient;
    private Handler handler;
    private CarlistonBaseAdapter onbaseAdapter;

    public CarlistonBaseAdapter(Context con, List<Map<String, Object>> data,AsyncHttpClient httpClient,ListView lv_carlist) {
        this.data = data;
        this.con = con;
        this.httpClient=httpClient;
        this.lv_carlist=lv_carlist;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        view = View.inflate(con, R.layout.listview_carlist, null);
        final ImageView iv_showcar_carlist = (ImageView) view.findViewById(R.id.iv_showcar_carlist);
        final TextView tv_carnumber_carlist = (TextView) view.findViewById(R.id.tv_carnumber_carlist);
        final TextView tv_carbrand_carlist = (TextView) view.findViewById(R.id.tv_carbrand_carlist);
        final TextView tv_carmodel_carlist = (TextView) view.findViewById(R.id.tv_carmodel_carlist);
        final TextView tv_carcolor_carlist = (TextView) view.findViewById(R.id.tv_carcolor_carlist);
        ImageView iv_carcolor_carlist = (ImageView) view.findViewById(R.id.iv_carcolor_carlist);
        Button btn_select_carlist = (Button) view.findViewById(R.id.btn_select_carlist);
        Button btn_delet_carlist = (Button) view.findViewById(R.id.btn_delet_carlist);

        if ((data.get(i).get("Image")) != null) {
            Log.i("img", appURLFinal.BASE_URL + data.get(i).get("Image").toString());
            String url = appURLFinal.BASE_URL + data.get(i).get("Image").toString();
            HttpUtils.getNetBytes(con, url, iv_showcar_carlist, true);
        }
        if ((data.get(i).get("Number")) != null) {
            number = data.get(i).get("Number").toString();
            tv_carnumber_carlist.setText(number);
        }
        if ((data.get(i).get("Brand")) != null) {
            brand = data.get(i).get("Brand").toString();
            tv_carbrand_carlist.setText(brand);
        }
        if ((data.get(i).get("Model")) != null) {
            model = data.get(i).get("Model").toString();
            tv_carmodel_carlist.setText(model);
        }
        if ((data.get(i).get("Color")) != null) {
            color = data.get(i).get("Color").toString();
            String str_color = "-1";
            if ("0".equals(color)) {
                str_color = "白色";
            } else if ("1".equals(color)) {
                str_color = "黑色";
            } else if ("2".equals(color)) {
                str_color = "红色";
            } else if ("3".equals(color)) {
                str_color = "蓝色";
            } else if ("4".equals(color)) {
                str_color = "银色";
            } else if ("5".equals(color)) {
                str_color = "深灰色";
            } else if ("6".equals(color)) {
                str_color = "黄色";
            } else if ("7".equals(color)) {
                str_color = "绿色";
            }else if ("8".equals(color)) {
                str_color = "香槟色";
            }else if ("9".equals(color)) {
                str_color = "咖啡色";
            }else if ("10".equals(color)) {
                str_color = "紫色";
            }else if ("11".equals(color)) {
                str_color = "橙色";
            }
            tv_carcolor_carlist.setText(str_color);

        }if (data.get(i).get("pic_color")!=null){
        iv_carcolor_carlist.setBackgroundResource(Integer.valueOf(data.get(i).get("pic_color").toString()));
            //iv_carcolor_carlist.setBackgroundColor(Integer.valueOf(data.get(i).get("pic_color").toString()));
        }
/*        btn_select_carlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CustomDialog.Builder(con).setTitle(null).setMessage
                        ("确认选择" + tv_carnumber_carlist
                                .getText() + tv_carbrand_carlist.getText() + tv_carmodel_carlist
                                .getText() + tv_carcolor_carlist.getText() + "洗车吗?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
*//*                                SharedPreferences sp_carInfo = con.getSharedPreferences("carInfo", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sp_carInfo.edit();
                                editor.putString("UserID",data.get(i).get("UserID").toString());
                                editor.putString("CAR_ID",data.get(i).get("CAR_ID").toString());
                                editor.commit();*//*

                                Intent it_submitcar=new Intent();
                                it_submitcar.putExtra("UserID",data.get(i).get("UserID").toString());
                                it_submitcar.putExtra("CAR_ID",data.get(i).get("CAR_ID").toString());
                                it_submitcar.putExtra("Image",data.get(i).get("Image").toString());
                                it_submitcar.putExtra("Number",data.get(i).get("Number").toString());
                                ((Activity)con).setResult(Activity.RESULT_OK, it_submitcar);
                                ((Activity)con).finish();
                            }
                        }).setNegativeButton("取消", null).create().show();


            }
        });*/
        btn_delet_carlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CustomDialog.Builder(con).setTitle(null).setMessage("确认删除吗?").setPositiveButton("确定", new DialogInterface
                        .OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {
                        dialog.dismiss();
                        final String carid = data.get(i).get("CAR_ID").toString();
                        String url = appURLFinal.deletCar(carid);
                        AsyHandler asyHandler = new AsyHandler(carid);
                        httpClient.delete(con, url, asyHandler);
                        handler = new Handler() {
                            @Override
                            public void handleMessage(Message msg) {
                                if (msg.what == 0) {
                                    onbaseAdapter = new CarlistonBaseAdapter(con, MyCarListActivity.setCarlistOnData
                                            (con), httpClient, lv_carlist);
                                    lv_carlist.setAdapter(onbaseAdapter);
                                    Toast.makeText(con, "删除成功", Toast.LENGTH_SHORT).show();
                                }
                                super.handleMessage(msg);
                            }
                        };
                    }

                }).setNegativeButton("取消", null).create().show();

            }
        });
        return view;
    }
    class AsyHandler extends AsyncHttpResponseHandler{
        private String carid;
        public AsyHandler(String  carid){
            this.carid=carid;
        }
        @Override
        public void onSuccess(String content) {
            final MyCarDBHelper helper=new MyCarDBHelper(con);
            final SQLiteDatabase database=helper.getWritableDatabase();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    helper.onDelete(database,Integer.valueOf(carid));
                    handler.sendEmptyMessage(0);
                }
            }).start();

            super.onSuccess(content);
        }

        @Override
        public void onFailure(Throwable error, String content) {
            super.onFailure(error, content);
            Log.i("tag",content+"fail");
            Toast.makeText(con,"删除失败,请重试~",Toast.LENGTH_SHORT).show();
        }
    }

}
