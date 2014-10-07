package com.xixixi.sweetweather.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import org.xixixi.sweetweather.entity.RealtimeWeather;

import com.google.gson.Gson;

/**接口网址
 * http://www.weather.com.cn/data/sk/101110101.html
 * 该转换器将json抓换成RealtimeWeather实体
 * @author Administrator
 *
 */
public class RealtimeWeatherConverter {
	
	/**
	 * 将得到的time加上日期并转化成RealtimeWeather实体类
	 * @param json
	 * @return
	 */
	public RealtimeWeather JsonToRealtimeWeather(String json){
		RealtimeWeather realtimeweather = new Gson().fromJson(json, RealtimeWeather.class);
		String time = realtimeweather.getWeatherinfo().getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
		//逻辑可能有点错误
		Calendar calendar = Calendar.getInstance();
		realtimeweather.getWeatherinfo().setTime(sdf.format(calendar.getTime())+" "+time);
		return realtimeweather;
		
	}
	
	public String RealtimeWeatherToJson(RealtimeWeather realtimeWeather){
		return new Gson().toJson(realtimeWeather);
	}

}
