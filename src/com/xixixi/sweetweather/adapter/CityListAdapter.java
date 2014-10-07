package com.xixixi.sweetweather.adapter;

import java.util.HashMap;
import java.util.Map;

import com.xixixi.sweetweather.R;
import com.xixixi.sweetweather.util.CitySqliteHelper;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CityListAdapter extends BaseAdapter {
	
	private Context context;
	private LayoutInflater inflater;
	private Cursor cursor;
	public CityListAdapter(Context context, Cursor cursor){
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
		cursor.moveToPosition(position);
		Map<String,String> map = new HashMap<String,String>();
		map.put("name", cursor.getString(cursor.getColumnIndex(CitySqliteHelper.COLUMN_NAME)));
		map.put("num", cursor.getString(cursor.getColumnIndex(CitySqliteHelper.COLUMN_CITY_NUM)));
		return map;
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
			convertView = inflater.inflate(R.layout.choose_city_item, null);
		}
		cursor.moveToPosition(position);
		TextView city = (TextView)convertView.findViewById(R.id.city_name);
		city.setText(cursor.getString(cursor.getColumnIndex(CitySqliteHelper.COLUMN_NAME)));
		
		return convertView;
	}

}
