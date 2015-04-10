package net.wash8.helpers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.wash8.classbean.*;

import java.util.List;

public class JSON_Parser {
	public static HomeADPic parseJSON_AD(String str) {
		HomeADPic homeADPic = null;
		Gson gson=new Gson();
		try{
		homeADPic=gson.fromJson(str, HomeADPic.class);}
		catch (Exception e){

		}
		return homeADPic;
	}
	public static UserInfo parseJSON_Info(String str) {
		UserInfo userInfo;
		Gson gson=new Gson();
		userInfo=gson.fromJson(str, UserInfo.class);
		return userInfo;
	}
	public static List<Service> parseJSON_Servicelist(String str){
		List<Service> data;
		Gson gson=new Gson();
		data=gson.fromJson(str,new TypeToken<List<Service>>() {
		}.getType());
		return data;
	}
	public static SearchResult parseJSON_SearchResult(String str){
		SearchResult searchResult;
		Gson gson=new Gson();
		searchResult=gson.fromJson(str,SearchResult.class);
		return searchResult;
	}
	public static MyOrder parseJSON_MyOrder(String str){
		MyOrder myOrder;
		Gson gson=new Gson();
		myOrder=gson.fromJson(str,MyOrder.class);
		return myOrder;
	}
	public static Coupons parseJSON_Coupons(String str){
		Coupons coupons;
		Gson gson=new Gson();
		coupons=gson.fromJson(str,Coupons.class);
		return coupons;
	}
	}

