package com.xixixi.sweetweather.util;





import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import org.xixixi.sweetweather.entity.RealtimeWeather;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class WeatherSqliteHelper {
	//数据库、表、字段名
	private static final String DB_NAME = "sweetweather.db";  
	private static final String TABLE_NAME="weather";
	public static final String COLUMN_id = "id";
	public static final String COLUMN_TEMP = "temp";
	public static final String COLUMN_WD = "wd";
	public static final String COLUMN_WS = "ws";
	public static final String COLUMN_SD = "sd";
	public static final String COLUMN_TIME = "time";
	public static final String COLUMN_CITY = "city";
	//版本号
	private static final int DB_VERSION = 1; 
	
	// 执行open()打开数据库时，保存返回的数据库对象  
    private SQLiteDatabase mSQLiteDatabase = null; 
	
    // 查询游标对象  
    private Cursor cursor; 
    
    // 本地Context对象  
    private Context mContext = null;
    
    // 由SQLiteOpenHelper继承过来  
    private DatabaseHelper mDatabaseHelper = null;  
    
    private static WeatherSqliteHelper dbConn= null;
    
    
    
    /** 
     * SQLiteOpenHelper内部类 
     */  
    private static class DatabaseHelper extends SQLiteOpenHelper{
    	
        DatabaseHelper(Context context) {  
            // 当调用getWritableDatabase()或 getReadableDatabase()方法时,创建一个数据库  
            super(context, DB_NAME, null, DB_VERSION);  
        }

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL("CREATE TABLE "+TABLE_NAME+"("+COLUMN_id+" INTEGER PRIMARY KEY NOT NULL,"
						+COLUMN_CITY+" TEXT, "+COLUMN_TEMP+" TEXT, "
						+COLUMN_WD+" TEXT, "+COLUMN_WS+" TEXT, "+COLUMN_SD+" TEXT, "
					+COLUMN_TIME+" TEXT);");  
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
            onCreate(db); 			
		}
    }
    
    public WeatherSqliteHelper(Context mContext){
    	super();
    	this.mContext = mContext;
    }
    public static WeatherSqliteHelper getInstance(Context mContext){
        if (null == dbConn) {  
            dbConn = new WeatherSqliteHelper(mContext);  
        }  
        return dbConn;  
    }
    
    /** 
     * 打开数据库 
     */  
    public void open() {  
        mDatabaseHelper = new DatabaseHelper(mContext);  
        mSQLiteDatabase = mDatabaseHelper.getWritableDatabase();  
    }
    
    /** 
     * 关闭数据库 
     */  
    public void close() {  
        if (null != mDatabaseHelper) {  
            mDatabaseHelper.close();  
        }  
        if (null != cursor) {  
            cursor.close();  
        }  
    }  
    
    
    /**
     * 
     * @param realtime
     * @return -1 记录相同  -2 插入失败
     */
    public long insertRealtimeWeather(RealtimeWeather realtime){
    	
    	String temp = realtime.getWeatherinfo().getTemp();
    	String wd = realtime.getWeatherinfo().getWD();
    	String ws = realtime.getWeatherinfo().getWS();
    	String sd = realtime.getWeatherinfo().getSD();
    	String time = realtime.getWeatherinfo().getTime();
    	String city = realtime.getWeatherinfo().getCity();
    	
//    	String latestTime = findLatestWeatherTime();
/*    	if( !(latestTime == null|| latestTime.equals(null)) ){
        	//Log.e("weather", "latest record time = "+latestTime+" time = "+time);
        	if(latestTime.equals(time)||latestTime == time){
        		//Log.e("weather", "time equals");
        		return -1;
        	} 		
    	}
*/
    	String sql = "select * from "+TABLE_NAME+" where "+COLUMN_TIME+"=? and "+COLUMN_CITY+"=?";
    	Cursor result = mSQLiteDatabase.rawQuery(sql, new String[]{time,city});
    	Log.e("weather", "time = " + time + " city= "+ city + "cursor size = "+result.getCount());
    	if(result.getCount()>0){
    		return -1;
    	}
    	
    	ContentValues contentValues = new ContentValues();
    	contentValues.put(COLUMN_TEMP, temp);
    	contentValues.put(COLUMN_WD, wd);
    	contentValues.put(COLUMN_WS, ws);
    	contentValues.put(COLUMN_SD, sd);
    	contentValues.put(COLUMN_TIME, time);
    	contentValues.put(COLUMN_CITY, city);
    	
    	
    	
    	try {
			return insert(TABLE_NAME, null, contentValues);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return -2;
    	
    }
    
    
    public String findLatestWeatherTime() {
		try {
			Cursor cursor = findAll();
			cursor.moveToFirst();
			return cursor.getString(cursor.getColumnIndex(COLUMN_TIME));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/** 
     * 插入数据 
     * @param tableName 表名 
     * @param nullColumn null 
     * @param contentValues 名值对 
     * @return 新插入数据的ID，错误返回-1 
     * @throws Exception 
     */  
    public long insert(String tableName, String nullColumn,  
            ContentValues contentValues) throws Exception {  
        try {  
            return mSQLiteDatabase.insert(tableName, nullColumn, contentValues);  
        } catch (Exception e) {  
            throw e;  
        }  
    }  
    
    public Cursor findAll() throws Exception{  
        try {  
            cursor = mSQLiteDatabase.query(TABLE_NAME, null, null, null, null, null, " id desc");  
            cursor.moveToFirst();  
            return cursor;  
        } catch (Exception e) {  
            throw e;  
        }  
    }  
    
    public Cursor findOnedayWeather(String city){

    	String sql = "select * from "+TABLE_NAME+" where "+COLUMN_CITY+" =?";
    	Cursor cursor = mSQLiteDatabase.rawQuery(sql, new String[]{city});
    	cursor.moveToFirst();
    	return cursor;
    }
    
    public Cursor findWeatherByLocation(){
    	//TODO: heheheheheh;
    	return null;
    }
    
}

