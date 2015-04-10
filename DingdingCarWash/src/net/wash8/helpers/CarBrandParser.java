package net.wash8.helpers;

import android.content.Context;
import android.util.Xml;
import net.wash8.classbean.HomeADPic;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lijie on 2015/1/20.
 */
public class CarBrandParser {
    public List<String> hotCarBrand;
    public List<String> carBrand;
    public Map<String,String> carModel;

    private boolean isHot;
    private boolean isStart;

    private String key;

    public CarBrandParser(Context context){
        hotCarBrand = new ArrayList<String>();
        carBrand = new ArrayList<String>();
        carModel = new HashMap<String, String>();
        isHot = true;
        isStart = false;
        parser(context);
    }

    private void parser(Context context){
        XmlPullParser xmlPullParser = Xml.newPullParser();
        int eventType;
        try {
            eventType = xmlPullParser.getEventType();
            xmlPullParser.setInput(context.getAssets().open("cars.xml"),"utf-8");
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:

                        break;
                    case XmlPullParser.START_TAG:
                        if (xmlPullParser.getName().equals("dict")) {
                           isStart = true; //开始解析
                        }
                        else if (xmlPullParser.getName().equals("key")){
                            if(isStart){
                                key = xmlPullParser.nextText();
                                if(isHot){
                                    hotCarBrand.add(key);
                                }
                                else{
                                    carBrand.add(key);
                                }

                            }
                        }
                        else if (xmlPullParser.getName().equals("string")){
                            if(isStart){
                                carModel.put(key,xmlPullParser.nextText());
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (xmlPullParser.getName().equals("dict")){
                            if (isHot){
                                isHot = false;
                            }
                            isStart = false;
                        }
                        break;
                }
                eventType = xmlPullParser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
