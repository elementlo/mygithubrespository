package net.wash8.helpers;

import android.util.Xml;
import net.wash8.classbean.HomeADPic;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ncb-user on 2014/12/15.
 */
public class XMLParser {
    public static List<HomeADPic> homeADVParser(String url) {
        List<HomeADPic> list_homepic = null;
        HomeADPic homeADVpic = null;
        XmlPullParser xmlPullParser = Xml.newPullParser();
        int eventType;
        try {
            eventType = xmlPullParser.getEventType();
            xmlPullParser.setInput(new StringReader(url));
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        list_homepic = new ArrayList<HomeADPic>();
                        break;
                    case XmlPullParser.START_TAG:
                        if (xmlPullParser.getName().equals("HomeAdv")) {
                            homeADVpic = new HomeADPic();
                        }
                        else if (xmlPullParser.getName().equals("ID")){
                            //homeADVpic.setID(xmlPullParser.nextText());
                        }
                        else if (xmlPullParser.getName().equals("Image")){
                            //homeADVpic.setPIC_URL(xmlPullParser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (xmlPullParser.getName().equals("HomeAdv")){
                            list_homepic.add(homeADVpic);
                            homeADVpic=null;
                        }
                        break;
                }
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return list_homepic;

    }

    }