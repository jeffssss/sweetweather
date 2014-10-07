package com.xixixi.sweetweather.util;

import com.xixixi.sweetweather.service.RealtimeWeatherService;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.text.format.DateUtils;
import android.util.Log;

public class AlarmManagerUtil {
	
	private static AlarmManager am;  
	private static PendingIntent pendingIntent;  
	
	public AlarmManagerUtil(Context ctx){
		this.am = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
	}
	
    public AlarmManager getAlarmManager(Context ctx){
		return this.am;
	}
        
        
    public void sendUpdateBroadcastRepeat(Context ctx){
    		Log.e("weather", "enter alarmmanager repeat function");
            Intent intent =new Intent(ctx, RealtimeWeatherService.class);
            pendingIntent = PendingIntent.getService(ctx, 0, intent, 0);
            long interval = DateUtils.MINUTE_IN_MILLIS * 5 ;// 5分钟一次 
            long firstWake = System.currentTimeMillis() + 1000;           
            //60秒一个周期，不停的service;
            am.setRepeating(AlarmManager.RTC_WAKEUP, firstWake, interval, pendingIntent);
    }

}
