package com.xixixi.sweetweather;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.xixixi.sweetweather.util.CitySqliteHelper;
import com.xixixi.sweetweather.util.SharedPreferencesHelper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private TextView local_name = null;
	private TextView local_id = null;
	private ProgressDialog mypDialog= null;
	private String locationId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//绑定控件
		local_name = (TextView)findViewById(R.id.location_name);
		local_id = (TextView)findViewById(R.id.location_id);
		
		displayNameAndId();
		
		
}
	
	private void displayNameAndId(){
		String name = SharedPreferencesHelper.readLocationNamePreferences(MainActivity.this);
		String id = SharedPreferencesHelper.readLocationIdPreferences(MainActivity.this);
		if(name.equals("")||name==""){
			//没有preference，执行http请求
			new HttpGetAsyncTask(MainActivity.this).execute();			
		}
		else{
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
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	class HttpGetAsyncTask extends AsyncTask<Void, Integer, String> {
		private final static String url = "http://61.4.185.48:81/g/";
		private Context context; 
		private ProgressDialog pd;
		private String location_id;
		private String location_name;
		public HttpGetAsyncTask(Context context){
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
		}



	}

	
	
}
