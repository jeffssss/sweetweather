package com.xixixi.sweetweather.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/*
 * 专用于操作sharedpreferences的操作类
 * 实现对preference的增加 删除 
 */
public class SharedPreferencesHelper {
	
	private static final String PREFERENCES_NAME = "sweet_weather_location";
	private static final String KEY_LOCATION_NAME = "location_name";
	private static final String KEY_LOCATION_ID = "location_id";
	
	/**
	 * 用于写入关于地名的信息
	 * 
	 * @param locationName 地名
	 * @param locationID	地名对应id
	 * @param context
	 */
	public static void writeLocationPreferences(String locationName , String locationID , Context context){
		SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);	
		Editor editor = pref.edit();
		editor.putString(KEY_LOCATION_NAME, locationName);
		editor.putString(KEY_LOCATION_ID, locationID);
		editor.commit();
	}
	
	/**
	 * 读取当前地名
	 * 
	 * @param context
	 * @return
	 */
	
	public static String readLocationNamePreferences(Context context){
		SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);		
		return pref.getString(KEY_LOCATION_NAME, "");
	}

	/**
	 * 读取当面地名所对应的id.
	 * 
	 * @param context
	 * @return
	 */
	public static String readLocationIdPreferences(Context context){
		SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);		
		return pref.getString(KEY_LOCATION_ID, "");
	}
}
