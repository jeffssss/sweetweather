package com.xixixi.sweetweather.service;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.xixixi.sweetweather.entity.RealtimeWeather;

import com.xixixi.sweetweather.util.RealtimeWeatherConverter;
import com.xixixi.sweetweather.util.SharedPreferencesHelper;
import com.xixixi.sweetweather.util.WeatherSqliteHelper;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.view.View;

public class RealtimeWeatherService extends Service {
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		//Log.e("weather", "service oncreate!!!!!");
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		//Log.e("weather", "service onstartCommand!!!!!"+startId);
		//进行http请求。代码重复，有待优化
		new HttpGetRealtimeWeatherAsyncTask(getApplicationContext()).execute();
		return super.onStartCommand(intent, flags, startId);
	}
	
	
	class HttpGetRealtimeWeatherAsyncTask extends AsyncTask<Void,Integer,String>{
		private final static String url_1 = "http://www.weather.com.cn/data/sk/";
		private final static String url_2 = ".html";
		
		private Context context; 
		
		public HttpGetRealtimeWeatherAsyncTask(Context context){
			this.context = context;
			
		}
		
		
		@Override
		protected void onPostExecute(String result) {
			if(result!=null){
				RealtimeWeather realtimeWeather = new RealtimeWeatherConverter().JsonToRealtimeWeather(result);
				WeatherSqliteHelper sqlitehelper = WeatherSqliteHelper.getInstance(context);
				sqlitehelper.open();
				sqlitehelper.insertRealtimeWeather(realtimeWeather);
				//long id = sqlitehelper.insertRealtimeWeather(realtimeWeather);
				//Log.e("weather", "insert id = "+id);
				sqlitehelper.close();
			
			}
		}


		@Override
		protected String doInBackground(Void... params) {
			String cityid = SharedPreferencesHelper.readLocationIdPreferences(context);
			String url = url_1 + cityid + url_2;
			try {
				HttpGet httpRequest = new HttpGet(url);
				HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest);
				if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){	
					String json = EntityUtils.toString(httpResponse.getEntity());
					//Log.e("weather", json);
					return json;
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			return null;
		}
	}
}
