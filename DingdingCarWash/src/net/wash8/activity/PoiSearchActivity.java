package net.wash8.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiItemDetail;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.amap.api.services.poisearch.PoiSearch.OnPoiSearchListener;
import net.wash8.R;
import net.wash8.classbean.SearchResult;
import net.wash8.classbean.Stationaries;
import net.wash8.customview.CustomDialog;
import net.wash8.customview.CustomTitle;
import net.wash8.helpers.JSON_Parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PoiSearchActivity extends Activity implements OnPoiSearchListener {


    private EditText mPoiSearchText;
    private ImageView mPoiSearchClear;
    private EditText edittext_poi_search_text, et_carlocation;
    private RelativeLayout rl_detail_location;

    private ListView mPoiSearchList, lv_userpoi, lv_carlocation;
    private PoiSearch mPoiSearch;
    private PoiSearch.Query mQuery;
    private ArrayList<HashMap<String, String>> mPoiSearchData = new ArrayList<HashMap<String, String>>();
    private List<Stationaries> list;
    private List<Stationaries> list_new = new ArrayList<Stationaries>();
    private static int j = 0, k = 0;

    private boolean tag;
    private Intent it_submitplace;
    private SharedPreferences.Editor editor, editor_detail;
    private SharedPreferences sp_commonpoi, sp_carlocation;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poi_search);
        initView();
        setUserPoiList();
        getSearchResult();
    }

    private void setUserPoiList() {
        final SharedPreferences sp_common = getSharedPreferences("commonpoi", MODE_PRIVATE);
        if (sp_common != null) {
            List<HashMap<String, String>> userpoilist = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> map;
            int position = sp_common.getInt("sum", -1);
            for (int i = 0; i < (position + 1); i++) {
                map = new HashMap<String, String>();
                String userpoi = sp_common.getString("userpoi" + i, null);
                map.put("userpoi", userpoi);
                userpoilist.add(map);
            }
            final List<HashMap<String, String>> carlocationlist = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> map_carlocation;
            int position_carlocation = sp_common.getInt("sum_car", -1);
            for (int i = 0; i < (position_carlocation + 1); i++) {
                map_carlocation = new HashMap<String, String>();
                String carlocation = sp_common.getString("carlocation" + i, null);
                map_carlocation.put("carlocation", carlocation);
                carlocationlist.add(map_carlocation);
            }
            SimpleAdapter adapter = new SimpleAdapter(this, userpoilist, R.layout.item_poi_search, new
                    String[]{"userpoi"}, new int[]{R.id.textview_poi_search_title});
            lv_userpoi.setAdapter(adapter);
            lv_userpoi.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String AdName = sp_common.getString("AdName" + position, null);
                    it_submitplace.putExtra("AdName", AdName);
                    lv_userpoi.setVisibility(View.INVISIBLE);
                    mPoiSearchList.setVisibility(View.INVISIBLE);
                    edittext_poi_search_text.setText(sp_common.getString("userpoi" + position, null));
                    rl_detail_location.setVisibility(View.VISIBLE);
                    rl_detail_location.requestFocus();
                    lv_carlocation.setVisibility(View.VISIBLE);
                    SimpleAdapter carlocadapter = new SimpleAdapter(PoiSearchActivity.this, carlocationlist, R.layout
                            .item_poi_search, new String[]{"carlocation"}, new int[]{R.id.textview_poi_search_title});
                    lv_carlocation.setAdapter(carlocadapter);
                    lv_carlocation.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            lv_carlocation.setVisibility(View.INVISIBLE);
                            et_carlocation.setText(sp_common.getString("carlocation" + position, null));
                        }
                    });
                    tag = true;
                }
            });

        }
    }

    private void getSearchResult() {
        Intent it_getresult = getIntent();
        String result = it_getresult.getStringExtra("searchResult");
        Log.i("tag", result);
        SearchResult searchResult = JSON_Parser.parseJSON_SearchResult(result);
        list = searchResult.getStationaries();
        Log.i("tag", list.get(0).getNeigborhoods() + "");
    }
//搜索驻点楼盘匹配关键字
/*    private List<Stationaries> getNewData(String input_info) {
        for (int i = 0; i < list.size(); i++) {
            Stationaries items = list.get(i);
            if (items.getNeigborhoods().contains(input_info)) {
                Stationaries items_new = new Stationaries();
                items_new.setNeigborhoods(items.getNeigborhoods());
                items_new.setDistrict(items.getDistrict());
                list_new.add(items_new);
            }
        }
        return list_new;
    }*/

    private void initView() {
        it_submitplace = new Intent();
        sp_commonpoi = getSharedPreferences("commonpoi", MODE_PRIVATE);
        editor = sp_commonpoi.edit();
        sp_carlocation = getSharedPreferences("carlocation", MODE_PRIVATE);
        editor_detail = sp_carlocation.edit();

        et_carlocation = (EditText) findViewById(R.id.et_carlocation);
        rl_detail_location = (RelativeLayout) findViewById(R.id.rl_detail_location);
        edittext_poi_search_text = (EditText) findViewById(R.id.edittext_poi_search_text);
        CustomTitle customTitle = (CustomTitle) findViewById(R.id.customTitle);
        customTitle.setIv_title_title("选择车辆位置");
        customTitle.setIv_left_button(R.drawable.icon_back);
        customTitle.getIv_left_button().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        lv_userpoi = (ListView) findViewById(R.id.lv_userpoi);
        lv_carlocation = (ListView) findViewById(R.id.lv_carlocation);
        mPoiSearchText = (EditText) findViewById(R.id.edittext_poi_search_text);

        mPoiSearchText.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                list_new.clear();
                if (s.toString().length() != 0) {
                    lv_userpoi.setVisibility(View.INVISIBLE);
                    mPoiSearchClear.setVisibility(View.VISIBLE);
                } else {
                    mPoiSearchClear.setVisibility(View.INVISIBLE);
                }
                if (!tag) {
                    mQuery = new PoiSearch.Query("深圳" + s.toString(), "", "");
                    mQuery.setPageSize(10);

                    mQuery.setPageNum(1);
                    mPoiSearch = new PoiSearch(PoiSearchActivity.this, mQuery);
                    mPoiSearch.setOnPoiSearchListener(PoiSearchActivity.this);
                    mPoiSearch.searchPOIAsyn();
                } else {
                    mPoiSearchList.setVisibility(View.INVISIBLE);
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }

        });

        mPoiSearchClear = (ImageView) findViewById(R.id.imageview_poi_search_clear);
        mPoiSearchClear.setOnClickListener(new OnClickListener() {

            public void onClick(View view) {
                mPoiSearchText.setText("");
                mPoiSearchClear.setVisibility(View.GONE);

            }

        });
        mPoiSearchList = (ListView) findViewById(R.id.listview_poi_search_list);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit_place:
                if (TextUtils.isEmpty(et_carlocation.getText())) {
                    Toast.makeText(this, "请填写为您洗车的详细位置", Toast.LENGTH_SHORT).show();
                } else {
                    String neigborhoods = edittext_poi_search_text.getText().toString();
                    String user_carlocation = et_carlocation.getText().toString();
                    it_submitplace.putExtra("Neigborhoods", neigborhoods);
                    it_submitplace.putExtra("CarLocation", user_carlocation);
                    setResult(RESULT_OK, it_submitplace);
                    //保存常用地址

                    boolean tag_exsit_poi = false;
                    boolean tag_exsit_carloc = false;
                    for (int i = 0; i < (j + 1); i++) {
                        String userpoi = sp_commonpoi.getString("userpoi" + i, null);
                        if (userpoi != null) {
                            if (userpoi.equals(neigborhoods)) {
                                tag_exsit_poi = true;
                            }
                        }
                    }
                    if (!tag_exsit_poi) {
                        editor.putString("userpoi" + j, neigborhoods);
                        editor.putInt("sum", j);
                        j++;
                    }
                    for (int i = 0; i < (k + 1); i++) {
                        String carlocation = sp_commonpoi.getString("carlocation" + i, null);
                        if (carlocation != null) {
                            if (user_carlocation.equals(carlocation)) {
                                tag_exsit_carloc = true;
                            }
                        }
                    }
                    if (!tag_exsit_carloc) {
                        editor.putString("carlocation" + k, user_carlocation);
                        editor.putInt("sum_car", k);
                        k++;
                    }
                    if (!tag_exsit_poi || !tag_exsit_carloc) {
                        editor.commit();
                    }
                    this.finish();
                }
                break;
        }
    }


    public void onPoiItemDetailSearched(PoiItemDetail arg0, int rCode) {

    }

    public void onPoiSearched(PoiResult result, int rCode) {
        if (result != null && result.getQuery() != null) {

            if (result.getQuery().equals(mQuery)) {
                PoiResult mPoiResult = result;
                final List<PoiItem> poiItems = mPoiResult.getPois();
                if (poiItems != null) {
                    mPoiSearchData.clear();
                    HashMap<String, String> map;
                    for (PoiItem poiItem : poiItems) {
                        map = new HashMap<String, String>();
                        PoiItem item = poiItem;
                        map.put("Title", item.getTitle());
                        map.put("Snippet", item.getSnippet());
                        map.put("AdName", item.getAdName());
                        mPoiSearchData.add(map);
                    }
                    if (!tag) {
                        mPoiSearchList.setAdapter(new Adapter());
                        mPoiSearchList.setVisibility(View.VISIBLE);
                    }
                    mPoiSearchList.setOnItemClickListener(new OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            //mPoiSearchList.setVisibility(View.INVISIBLE);
                            if (mPoiSearchData != null) {
                                tag = true;
                                if (position < list.size()) {
                                    mPoiSearchList.setVisibility(View.INVISIBLE);
                                    String district = list.get(position).getDistrict();
                                    String neighborhoods = list.get(position).getNeigborhoods();
                                    edittext_poi_search_text.setText("");
                                    edittext_poi_search_text.setText(district + neighborhoods);
                                    it_submitplace.putExtra("AdName", district);
                                    editor.putString("AdName" + j, district);
                                    rl_detail_location.setVisibility(View.VISIBLE);
                                    rl_detail_location.requestFocus();
                                    tag = false;
                                } else {
/*                                    edittext_poi_search_text.setText("");
                                    edittext_poi_search_text.setText(mPoiSearchData.get(position).get("Title")
                                            + mPoiSearchData.get(position).get("Snippet"));*/
                                    String mdistrict=mPoiSearchData.get(position).get("AdName");
                                    if (mdistrict == null||!"南山区".equals(mdistrict)) {
                                        new CustomDialog.Builder(PoiSearchActivity.this).setTitle(null).setMessage
                                                (mdistrict+"不在服务区,请重新选择").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        })
                                                .setNegativeButton(null,
                                                        null).create().show();
                                    } else {
                                        mPoiSearchList.setVisibility(View.INVISIBLE);
                                        edittext_poi_search_text.setText("");
                                        edittext_poi_search_text.setText(mPoiSearchData.get(position).get("Title")
                                                + mPoiSearchData.get(position).get("Snippet"));
                                        it_submitplace.putExtra("AdName", mPoiSearchData.get(position).get("AdName"));
                                        editor.putString("AdName" + j, mPoiSearchData.get(position).get("AdName"));
                                        rl_detail_location.setVisibility(View.VISIBLE);
                                        rl_detail_location.requestFocus();
                                        tag = false;
                                    }
                                }
                                //tag = false;
  /*                              rl_detail_location.setVisibility(View.VISIBLE);
                                rl_detail_location.requestFocus();*/
                            }
                        }

                    });
                }
            }
        }
    }

    class Adapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mPoiSearchData.size();
        }

        @Override
        public Object getItem(int position) {
            return mPoiSearchData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            int i = list.size();
            convertView = View.inflate(PoiSearchActivity.this, R.layout.item_poi_search, null);
            TextView textview_poi_search_title = (TextView) convertView.findViewById(R.id.textview_poi_search_title);
            TextView textview_poi_search_snippet = (TextView) convertView.findViewById(R.id
                    .textview_poi_search_snippet);
            if (position < i) {
                textview_poi_search_title.setText(list.get(position).getNeigborhoods());
                textview_poi_search_snippet.setText(list.get(position).getDistrict());
            } else {
                textview_poi_search_title.setText(mPoiSearchData.get(position).get("Title"));
                textview_poi_search_snippet.setText(mPoiSearchData
                        .get(position).get("Snippet"));
            }
            return convertView;
        }

    }

}
