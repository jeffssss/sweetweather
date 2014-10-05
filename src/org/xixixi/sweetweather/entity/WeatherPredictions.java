package org.xixixi.sweetweather.entity;

import java.util.List;

/**
 * 通过百度天气的接口http://api.map.baidu.com/telematics/v3/weather?location=%E6%8B%9C%E6%B3%89&output=json&ak=mV6uicKindXXN5RGti7eOv4r
 * 得到的完整json转化后的实体类。 包含date，city，pm25，tips，prediction。
 * @author Administrator
 *
 */
public class WeatherPredictions {
	private String error;
	private String status;
	private String date;
	private List<WeatherResults> results;
	
	
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public List<WeatherResults> getResults() {
		return results;
	}
	public void setResults(List<WeatherResults> results) {
		this.results = results;
	}
	
	

	
}
