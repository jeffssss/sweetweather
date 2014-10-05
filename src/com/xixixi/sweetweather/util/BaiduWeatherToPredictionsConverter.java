package com.xixixi.sweetweather.util;

/**
 * 将通过百度天气api得到的json数据转换成predictions实体的转换类。
 */
import org.xixixi.sweetweather.entity.WeatherPredictions;
import com.google.gson.Gson;

public class BaiduWeatherToPredictionsConverter {
	
	/**
	 * 转换方法。返回WeatherPredictions
	 * @param json 百度天气api得到的json字符串
	 * @return
	 */
	public WeatherPredictions jsonConvertToPredictions(String json){
		
		Gson gson = new Gson();
		WeatherPredictions predictions = gson.fromJson(json, WeatherPredictions.class);
		
		return predictions;
	}
	
	public String predictionsConvertToJson(WeatherPredictions predictions){
		return new Gson().toJson(predictions);
	}
	

}
