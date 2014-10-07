package com.xixixi.sweetweather;

import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.xixixi.sweetweather.entity.RealtimeWeather;
import org.xixixi.sweetweather.entity.WeatherPredictions;

import com.xixixi.sweetweather.adapter.WeatherPredictionAdapter;
import com.xixixi.sweetweather.util.AlarmManagerUtil;
import com.xixixi.sweetweather.util.BaiduWeatherToPredictionsConverter;
import com.xixixi.sweetweather.util.CitySqliteHelper;
import com.xixixi.sweetweather.util.RealtimeWeatherConverter;
import com.xixixi.sweetweather.util.SharedPreferencesHelper;
import com.xixixi.sweetweather.util.WeatherSqliteHelper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private TextView local_name = null;
	private TextView local_id = null;
	private Boolean ifpref = false;
	private ListView weatherList = null;
	private TextView main_state = null;
	private Button refreshBtn = null;
	
	private TextView realtime_state = null;
	private TextView realtime_temp = null;
	private TextView realtime_wd = null;
	private TextView realtime_ws = null;
	private TextView realtime_sd = null;
	private TextView realtime_time = null;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		new AlarmManagerUtil(this).sendUpdateBroadcastRepeat(this);
		
		//绑定控件
		local_name = (TextView)findViewById(R.id.location_name);
		local_id = (TextView)findViewById(R.id.location_id);
		weatherList = (ListView)findViewById(R.id.main_weather_list);
		main_state = (TextView)findViewById(R.id.main_state);
		refreshBtn = (Button)findViewById(R.id.main_refresh_weather);
		
		//绑定实时气温的控件
		realtime_state = (TextView)findViewById(R.id.realtime_state);
		realtime_temp = (TextView)findViewById(R.id.realtime_temp);
		realtime_wd = (TextView)findViewById(R.id.realtime_wd);
		realtime_ws = (TextView)findViewById(R.id.realtime_ws);
		realtime_sd = (TextView)findViewById(R.id.realtime_sd);
		realtime_time = (TextView)findViewById(R.id.realtime_time);
		
		
		
		main_state.setText("未加载数据");
		realtime_state.setText("未加载数据");
		
		refreshBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				displayWeather();
				
			}
		});
		displayNameAndId();
		
		//如果有pref则执行请求天气预测。
		if(ifpref){
			displayWeather();
			
		}
		
		
}
	private void displayWeather() {
		new HttpGetWeatherPredictionsAsyncTask(MainActivity.this).execute();
		new HttpGetRealtimeWeatherAsyncTask(MainActivity.this).execute();
	}
	/**
	 * 通过http 的 get请求获取当前ip对应的城市id。并从sqlite里获取城市名字。结果存到pref里。
	 */
	private void displayNameAndId(){
		String name = SharedPreferencesHelper.readLocationNamePreferences(MainActivity.this);
		String id = SharedPreferencesHelper.readLocationIdPreferences(MainActivity.this);
		if(name.equals("")||name==""){
			//没有preference，执行http请求
			ifpref = false;
			new HttpGetCityIdAsyncTask(MainActivity.this).execute();			
		}
		else{
			ifpref = true;
			local_name.setText(name);
			local_id.setText(id);
			
		}
	}
	
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch(id){
		case R.id.action_settings: return true;
		case R.id.action_history: {
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, HistoryActivity.class);
			startActivity(intent);
			break;
		}
		case R.id.action_choose_city:{
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, ChooseCityActivity.class);
			startActivityForResult(intent, 1);
			break;
		}
		case R.id.action_graphic:{
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, TodayWeatherChartActivity.class);
			startActivity(intent);
			break;
		}
		default: return super.onOptionsItemSelected(item); 
		}
		return super.onOptionsItemSelected(item); 
		
		
	}
	
	
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		SharedPreferencesHelper.writeLocationPreferences(data.getStringExtra("name"), data.getStringExtra("num"), this);
		displayNameAndId();
		displayWeather();
	}




	class HttpGetCityIdAsyncTask extends AsyncTask<Void, Integer, String> {
		private final static String url = "http://61.4.185.48:81/g/";
		private Context context; 
		private ProgressDialog pd;
		private String location_id;
		private String location_name;
		public HttpGetCityIdAsyncTask(Context context){
			this.context = context;
			
		}

		@Override
		protected void onPreExecute() {
	        pd = new ProgressDialog(context);  
	        pd.setTitle("SweetWeather");
	        pd.setMessage("正在获取当前城市信息"); 
	        pd.setCancelable(false);  
	        pd.setIcon(R.drawable.progress_dialog);
	        pd.show();  
		}



		@Override
		protected String doInBackground(Void... params) {
			HttpGet httpRequest = new HttpGet(url);
			try {
				HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest);
				 if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){						 
					 String strResult = EntityUtils.toString(httpResponse.getEntity());
					 int index = strResult.indexOf("id=");
					 if(index>0){
						 CitySqliteHelper sqliteHelper = new CitySqliteHelper(MainActivity.this);
						 sqliteHelper.openDatabase();
						 location_id = strResult.substring(index+3, index+12);
						 location_name = sqliteHelper.findNameByCityId(location_id);
						 sqliteHelper.closeDatabase();
						 SharedPreferencesHelper.writeLocationPreferences(location_name, location_id, context);
						 return "succeed";
					 }
					 else{
						 Toast.makeText(context, "未能获取当前城市信息", Toast.LENGTH_SHORT).show();
						 return "fail";
					 }

				 }
				 else{
					 Toast.makeText(context, "未能获取当前城市信息", Toast.LENGTH_SHORT).show();
					 return "fail";
				 }
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			return "error";
			
			
		}

		@Override
		protected void onPostExecute(String result) {
			if(result.equals("succeed")||result =="succeed"){
				local_name.setText(location_name);
				local_id.setText(location_id);
			}
			else{
				local_name.setText("error");
			}

			pd.dismiss();
			displayWeather();
		}



	}

	class HttpGetWeatherPredictionsAsyncTask extends AsyncTask<Void, Integer,String>{
		private final static String url_1 = "http://api.map.baidu.com/telematics/v3/weather?location=";
		private final static String url_2 = "&output=json&ak=mV6uicKindXXN5RGti7eOv4r";
		private Context context; 
		
		public HttpGetWeatherPredictionsAsyncTask(Context context){
			this.context = context;
			
		}

		
		
		@Override
		protected void onPreExecute() {
			main_state.setVisibility(View.VISIBLE);
			main_state.setText("正在加载天气数据...");
		}



		@Override
		protected void onPostExecute(String result) {
			if(result!=null){
				WeatherPredictions weather = new BaiduWeatherToPredictionsConverter().jsonConvertToPredictions(result);
				WeatherPredictionAdapter predictionAdapter = new WeatherPredictionAdapter(context, weather);
				//Log.e("weather", "begin to set adapter");
				weatherList.setAdapter(predictionAdapter);
				//Log.e("weather", "finish adapter");
				main_state.setVisibility(View.GONE);
				//Log.e("weather", "main_state invisible");
			}
			else{
				main_state.setText("加载失败");
			}
		}



		@Override
		protected String doInBackground(Void... params) {
			String cityName = SharedPreferencesHelper.readLocationNamePreferences(context);
			//String cityName = "武汉";
			//Log.e("weather", "cityname = "+cityName);
			if(cityName.contains(".")){
				cityName=cityName.substring(cityName.indexOf(".")+1, cityName.length());
			}
			//Log.e("weather", "cityname = "+cityName);
			try {
				cityName = java.net.URLEncoder.encode(cityName, "utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String url = url_1+cityName+url_2;
			//Log.e("weather", url);
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
	
	class HttpGetRealtimeWeatherAsyncTask extends AsyncTask<Void,Integer,String>{
		private final static String url_1 = "http://www.weather.com.cn/data/sk/";
		private final static String url_2 = ".html";
		
		private Context context; 
		
		public HttpGetRealtimeWeatherAsyncTask(Context context){
			this.context = context;
			
		}
		
		
		@Override
		protected void onPreExecute() {
			realtime_state.setVisibility(View.VISIBLE);
			realtime_state.setText("正在加载实时天气数据...");
		}


		@Override
		protected void onPostExecute(String result) {
			if(result!=null){
				RealtimeWeather realtimeWeather = new RealtimeWeatherConverter().JsonToRealtimeWeather(result);
				WeatherSqliteHelper sqlitehelper = WeatherSqliteHelper.getInstance(context);
				sqlitehelper.open();
				sqlitehelper.insertRealtimeWeather(realtimeWeather);
				//Log.e("weather", "insert id = "+id);
				sqlitehelper.close();
				realtime_state.setVisibility(View.GONE);
				realtime_sd.setText(realtimeWeather.getWeatherinfo().getSD());
				realtime_temp.setText(realtimeWeather.getWeatherinfo().getTemp());
				realtime_time.setText(realtimeWeather.getWeatherinfo().getTime());
				realtime_wd.setText(realtimeWeather.getWeatherinfo().getWD());
				realtime_ws.setText(realtimeWeather.getWeatherinfo().getWS());				
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
