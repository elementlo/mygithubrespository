package net.wash8.helpers;

import android.util.Base64;

/**
 * Created by ncb-user on 2014/12/25.
 */
public class Base64FromTo {
    public static String encodeBase64(String str){

        String enToStr = Base64.encodeToString(str.getBytes(), Base64.DEFAULT);

        return enToStr;
    }
    public static String decodeBase64(String str){
        return null;
    }
}
