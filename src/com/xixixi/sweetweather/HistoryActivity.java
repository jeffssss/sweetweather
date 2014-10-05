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
		
		bulidListView();
		
	}

	private void bulidListView() {
		WeatherSqliteHelper sqlitehelper = WeatherSqliteHelper.getInstance(HistoryActivity.this);
		
		try {
			sqlitehelper.open();
			Cursor cursor = sqlitehelper.findAll();
			if(cursor.getCount()==0){
				
				listview.addView(LayoutInflater.from(HistoryActivity.this).
						inflate(R.layout.history_list_head_none, null));
			}
			else{
				HistoryAdapter adapter = new HistoryAdapter(HistoryActivity.this, cursor);
				listview.setAdapter(adapter);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			sqlitehelper.close();
		}
		
	}
	
	

}
