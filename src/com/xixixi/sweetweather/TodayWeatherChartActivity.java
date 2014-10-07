package com.xixixi.sweetweather;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import com.xixixi.sweetweather.util.SharedPreferencesHelper;
import com.xixixi.sweetweather.util.WeatherSqliteHelper;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

public class TodayWeatherChartActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_graphic);
		
		WeatherSqliteHelper sqlite = WeatherSqliteHelper.getInstance(this);
		//查找出符合条件的记录
		sqlite.open();
/*		Calendar calendar = Calendar.getInstance();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
    	String date = sdf.format(calendar.getTime());*/
		String cityname = SharedPreferencesHelper.readLocationNamePreferences(this);
		Cursor cursor = sqlite.findOnedayWeather(cityname);

		//将记录放进各个数组里
		int size = cursor.getCount();
		Log.e("weather", " cursor size = "+size);
		double[] _y = new double[size];
		double[] _x = new double[size];
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm",Locale.getDefault());
		Date[] time = new Date[size];
		int position = 0;
		for(position = 0;position<size; position++){
			//_x[position] = position+1;
			cursor.moveToPosition(position);
			//y轴天气值
			_y[position] = Double.parseDouble(cursor.getString(cursor.getColumnIndex(WeatherSqliteHelper.COLUMN_TEMP)));
			try {
				time[position] = sdf2.parse(cursor.getString(cursor.getColumnIndex(WeatherSqliteHelper.COLUMN_TIME)));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		sqlite.close();
		
		// Creating TimeSeries for time
        TimeSeries timeSeries = new TimeSeries("time");

        // Adding data to time Series
        for(int i=0;i<size;i++){
            timeSeries.add(time[i], _y[i]);
        }
        
        // Creating a dataset to hold each series
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
 
        // Adding Visits Series to the dataset
        dataset.addSeries(timeSeries);
        
        // Creating XYSeriesRenderer to customize timeSeries
        XYSeriesRenderer timeRenderer = new XYSeriesRenderer();
        timeRenderer.setColor(Color.WHITE);
        timeRenderer.setPointStyle(PointStyle.CIRCLE);
        timeRenderer.setFillPoints(true);
        timeRenderer.setLineWidth(2);
        timeRenderer.setChartValuesTextSize(20);
        timeRenderer.setDisplayChartValues(true);   
        
        // Creating a XYMultipleSeriesRenderer to customize the whole chart
        XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
 
        multiRenderer.setChartTitle("历史天气气温情况");
        multiRenderer.setAxisTitleTextSize(35);
        multiRenderer.setXTitle(cityname+"气温");
        multiRenderer.setLabelsTextSize(25);
        multiRenderer.setChartTitleTextSize(45);
        multiRenderer.setYTitle("时间");
        multiRenderer.setZoomButtonsVisible(true);
        multiRenderer.setLegendTextSize(30);
        
        multiRenderer.addSeriesRenderer(timeRenderer);
        
	    LinearLayout layout = (LinearLayout)findViewById(R.id.graphic_content);
	    View mChartView = ChartFactory.getTimeChartView(this, dataset, multiRenderer, "MM-dd HH:mm");
	    
	    
 
	    layout.addView(mChartView);  
	      
	    
	    
	    
	}

	  
}
