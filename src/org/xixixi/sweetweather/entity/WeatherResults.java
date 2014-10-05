package org.xixixi.sweetweather.entity;

import java.util.List;

public class WeatherResults{
	private String currentCity;
	private String pm25;
	private List<WeatherTip> index;
	private List<WeatherPrediction> weather_data;
	public String getCurrentCity() {
		return currentCity;
	}
	public void setCurrentCity(String currentCity) {
		this.currentCity = currentCity;
	}
	public String getPm25() {
		return pm25;
	}
	public void setPm25(String pm25) {
		this.pm25 = pm25;
	}
	public List<WeatherTip> getIndex() {
		return index;
	}
	public void setIndex(List<WeatherTip> index) {
		this.index = index;
	}
	public List<WeatherPrediction> getWeather_data() {
		return weather_data;
	}
	public void setWeather_data(List<WeatherPrediction> weather_data) {
		this.weather_data = weather_data;
	}
}
