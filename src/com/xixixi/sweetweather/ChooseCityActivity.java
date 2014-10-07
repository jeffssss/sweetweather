package com.xixixi.sweetweather;

import java.util.Map;

import com.xixixi.sweetweather.adapter.CityListAdapter;
import com.xixixi.sweetweather.util.CitySqliteHelper;
import com.xixixi.sweetweather.util.SharedPreferencesHelper;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ChooseCityActivity extends Activity {
	private ListView listview;
	private CitySqliteHelper sqlitehelper;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choosecity);
		
		listview = (ListView)findViewById(R.id.city_list);
		sqlitehelper = new CitySqliteHelper(this);
		sqlitehelper.openDatabase();
		Cursor cursor = sqlitehelper.findAll();
		listview.setAdapter(new CityListAdapter(this, cursor));
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Map<String,String> map = (Map<String,String>)parent.getItemAtPosition(position);
				Intent intent = new Intent();
				intent.putExtra("name", map.get("name"));
				intent.putExtra("num", map.get("num"));
				ChooseCityActivity.this.setResult(1, intent);
				ChooseCityActivity.this.finish();
				//SharedPreferencesHelper.writeLocationPreferences(map.get("name"), map.get("num"), ChooseCityActivity.this);
				
			}
		});
		
	}

	



	@Override
	public void onBackPressed() {
		Intent intent = new Intent();
		intent.putExtra("name", SharedPreferencesHelper.readLocationNamePreferences(this));
		intent.putExtra("num", SharedPreferencesHelper.readLocationIdPreferences(this));
		ChooseCityActivity.this.setResult(1, intent);
		ChooseCityActivity.this.finish();
		super.onBackPressed();
	}





	@Override
	protected void onDestroy() {
		sqlitehelper.closeDatabase();
		super.onDestroy();
	}
	
	
	
	

}
