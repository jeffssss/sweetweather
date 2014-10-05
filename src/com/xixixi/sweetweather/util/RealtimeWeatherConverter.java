package com.xixixi.sweetweather.util;

import org.xixixi.sweetweather.entity.RealtimeWeather;

import com.google.gson.Gson;

/**接口网址
 * http://www.weather.com.cn/data/sk/101110101.html
 * 该转换器将json抓换成RealtimeWeather实体
 * @author Administrator
 *
 */
public class RealtimeWeatherConverter {
	
	
	public RealtimeWeather JsonToRealtimeWeather(String json){
		return new Gson().fromJson(json, RealtimeWeather.class);
		
	}
	
	public String RealtimeWeatherToJson(RealtimeWeather realtimeWeather){
		return new Gson().toJson(realtimeWeather);
	}

}
