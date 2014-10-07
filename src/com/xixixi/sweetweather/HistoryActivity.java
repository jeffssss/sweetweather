package com.xixixi.sweetweather;

import com.xixixi.sweetweather.adapter.HistoryAdapter;
import com.xixixi.sweetweather.util.WeatherSqliteHelper;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ListView;

public class HistoryActivity extends Activity {
	private ListView listview = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history);
		listview = (ListView)findViewById(R.id.history_listview);
		bulidListView();
		
	}

	private void bulidListView() {
		WeatherSqliteHelper sqlitehelper = WeatherSqliteHelper.getInstance(HistoryActivity.this);
		
		try {
			sqlitehelper.open();
			Cursor cursor = sqlitehelper.findAll();
			if(cursor.getCount()==0){
				
			}
			else{
				HistoryAdapter adapter = new HistoryAdapter(HistoryActivity.this, cursor);
				listview.setAdapter(adapter);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void onDestroy() {
		WeatherSqliteHelper.getInstance(HistoryActivity.this).close();
		super.onDestroy();
	}
	
	

}
