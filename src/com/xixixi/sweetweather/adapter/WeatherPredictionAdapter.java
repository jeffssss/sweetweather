package com.xixixi.sweetweather.adapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.xixixi.sweetweather.entity.WeatherPrediction;
import org.xixixi.sweetweather.entity.WeatherPredictions;

import com.xixixi.sweetweather.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class WeatherPredictionAdapter extends BaseAdapter {
	private Context context;
	private WeatherPredictions weatherPredictions;
	private Date date;
	private LayoutInflater inflater;
	public WeatherPredictionAdapter(Context context,WeatherPredictions weatherPredictions){
		this.context = context;
		this.weatherPredictions = weatherPredictions;
		this.inflater = LayoutInflater.from(context);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
		try {
			this.date = sdf.parse(weatherPredictions.getDate());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public int getCount() {
		if(weatherPredictions.getStatus().equals("success")){
			//Log.e("weather", "get count !=0");
			return weatherPredictions.getResults().get(0).getWeather_data().size();
		}
		else{
			//Log.e("weather", "get count 0");
			return 0;
		}
			
	}

	@Override
	public Object getItem(int position) {
		if(weatherPredictions.getStatus().equals("success")){
			return weatherPredictions.getResults().get(0).getWeather_data().get(position);
		}
		else
			return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(!weatherPredictions.getStatus().equals("success")){
			//Log.e("weather", "not success!!!");
			return null;
		}
        if(convertView == null)
        {
            convertView = inflater.inflate(R.layout.prediction_list_item, null);
        }
		
        //Log.e("weather", "getView了呵呵呵");
		
		TextView prediction_date = (TextView)convertView.findViewById(R.id.prediction_date);
		TextView prediction_weekday = (TextView)convertView.findViewById(R.id.prediction_weekday);
		TextView prediction_weather = (TextView)convertView.findViewById(R.id.prediction_weather);
		TextView prediction_temp = (TextView)convertView.findViewById(R.id.prediction_temp);
		TextView prediction_wind = (TextView)convertView.findViewById(R.id.prediction_wind);
		
		WeatherPrediction prediction = weatherPredictions.getResults().get(0).getWeather_data().get(position);
		prediction_weekday.setText(prediction.getDate());
		prediction_weather.setText(prediction.getWeather());
		prediction_temp.setText(prediction.getTemperature());
		prediction_wind.setText(prediction.getWind());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, position);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
		prediction_date.setText(sdf.format(calendar.getTime()));
		//Log.e("weather", sdf.format(calendar.getTime())+" "+prediction.getTemperature());
		return convertView;
	}


}
