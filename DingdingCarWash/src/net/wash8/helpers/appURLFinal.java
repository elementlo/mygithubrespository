package net.wash8.helpers;

import android.util.Log;
import net.wash8.net.AsyncHttpClient;

public class appURLFinal {
    public static final String HOME_ADV="http://wash8.com.cn/api/home-adv";
    public static final String BASE_URL="http://wash8.com.cn";
    public static final String GET_DATE_ORDER="http://wash8.com.cn/api/order/count?f=";
    public static final String LOG_IN="http://wash8.com.cn/api/user";
    public static final String ADDCAR="http://wash8.com.cn/api/user?action=addCar";
    public static final String SERVICE_PRICE="http://wash8.com.cn/api/value/service-fee";
    public static final String SEARCH_RESULT="http://wash8.com.cn/api/stationary/";
    public static final String SUBMIT_ORDER="http://wash8.com.cn/api/order";
    public static final String UPDATE_USERINFO="http://wash8.com.cn/api/user";
    public static final String SELECT_ORDER_LIST="http://wash8.com.cn/api/order?f=";
    public static final String COUPON_LIST="http://wash8.com.cn/api/coupon/";
    public static final String SUBMIT_ADVICE="http://wash8.com.cn/api/order?action=rating";

    public static final String SUBMIT_COMMENT="http://wash8.com.cn/api/suggestion";
    public static final String USER_DEPOSIT = "http://wash8.com.cn/api/user-deposit/";
    public static final String AUTH_CODE = "http://wash8.com.cn/api/user?action=auth";
    public static final String REGISTER = "http://wash8.com.cn/api/user?action=reg";

    public static String USER_NAME=null;
    public static String PASS_WORD=null;
    public static String deletCar(String carid){
        String url="http://wash8.com.cn/api/user/"+carid+"?action=delCar";
        return url;
    }
    public static void addHttpHeader(AsyncHttpClient httpClient){
        httpClient.addHeader("Client-Secret", "db266d6766a7c79f1defd856caf1d877");
        Log.i("tag", appURLFinal.USER_NAME + ":" + appURLFinal.PASS_WORD);
        String user_name=appURLFinal.USER_NAME;
        String pass_word=appURLFinal.PASS_WORD;
        String key = Base64FromTo.encodeBase64(user_name + ":" + pass_word);
        httpClient.addHeader("Authorization", "Basic " + key);
    }
}
