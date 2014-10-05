package org.xixixi.sweetweather.entity;
/**
 * 百度天气api中反映天气部分的实体类。包含数据如下：
 * "date": "周一","dayPictureUrl": 
 * "http://api.map.baidu.com/images/weather/day/qing.png",
 * "nightPictureUrl": "http://api.map.baidu.com/images/weather/night/duoyun.png",
 * "weather": "晴转多云",
 * "wind": "西南风3-4级",
 * "temperature": "14 ~ 4℃"
 * @author Administrator
 *
 */
public class WeatherPrediction {
	private String date;
	private String dayPictureUrl;
	private String nightPictureUrl;
	private String weather;
	private String wind;
	private String temperature;
	
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDayPictureUrl() {
		return dayPictureUrl;
	}
	public void setDayPictureUrl(String dayPictureUrl) {
		this.dayPictureUrl = dayPictureUrl;
	}
	public String getNightPictureUrl() {
		return nightPictureUrl;
	}
	public void setNightPictureUrl(String nightPictureUrl) {
		this.nightPictureUrl = nightPictureUrl;
	}
	public String getWeather() {
		return weather;
	}
	public void setWeather(String weather) {
		this.weather = weather;
	}
	public String getWind() {
		return wind;
	}
	public void setWind(String wind) {
		this.wind = wind;
	}
	public String getTemperature() {
		return temperature;
	}
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	
	
	
	

}
