package com.xixixi.sweetweather.adapter;

import com.xixixi.sweetweather.R;
import com.xixixi.sweetweather.util.WeatherSqliteHelper;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class HistoryAdapter extends BaseAdapter {
	
	private Context context;
	private LayoutInflater inflater;
	private Cursor cursor;
	
	public HistoryAdapter(Context context, Cursor cursor){
		this.context = context;
		this.cursor = cursor;
		this.inflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		return cursor.getCount();
	}

	@Override
	public Object getItem(int position) {
		//TODO: hehe
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(cursor==null){
			return null;
		}
		if(convertView == null){
			convertView = inflater.inflate(R.layout.history_list_item, null);
		}
		cursor.moveToPosition(position);
		TextView temp = (TextView)convertView.findViewById(R.id.history_temp);
		TextView datetime = (TextView)convertView.findViewById(R.id.history_datetime);
		TextView location = (TextView)convertView.findViewById(R.id.history_location);
		
		datetime.setText(cursor.getString(cursor.getColumnIndex(WeatherSqliteHelper.COLUMN_TIME)));
		location.setText(cursor.getString(cursor.getColumnIndex(WeatherSqliteHelper.COLUMN_CITY)));	
		temp.setText(cursor.getString(cursor.getColumnIndex(WeatherSqliteHelper.COLUMN_TEMP)));
		return convertView;
	}

}
