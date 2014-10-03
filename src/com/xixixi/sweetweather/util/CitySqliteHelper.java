package com.xixixi.sweetweather.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.xixixi.sweetweather.R;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

public class CitySqliteHelper {
	//数据库、表、字段名
	private static final String DB_NAME = "db_weather.db";  
	private static final String TABLE_NAME="citys";
	private static final String COLUMN_PROVINCE = "province_id";
	private static final String COLUMN_NAME = "name";
	private static final String COLUMN_CITY_NUM = "city_num";
	//版本号
	private static final int DB_VERSION = 1; 
	//路径
	public static final String PACKAGE_NAME = "com.xixixi.sweetweather";
	public static final String DB_PATH = "/data"
            + Environment.getDataDirectory().getAbsolutePath() + "/"
            + PACKAGE_NAME;  //在手机里存放数据库的位置
	// 执行open()打开数据库时，保存返回的数据库对象  
    private SQLiteDatabase mSQLiteDatabase = null; 
    
    // 由SQLiteOpenHelper继承过来  
    //private DatabaseHelper mDatabaseHelper = null;  
	
    // 查询游标对象  
    private Cursor cursor; 
    
    // 本地Context对象  
    private Context mContext = null;
    
    private static CitySqliteHelper dbConn= null;
    
    private final int BUFFER_SIZE = 400000;
    
    
    public CitySqliteHelper(Context mContext){
    	this.mContext = mContext;
    }
    
    public void openDatabase(){
    	this.mSQLiteDatabase = this.openDatabase(DB_PATH + "/" + DB_NAME);
    }
    
    private SQLiteDatabase openDatabase(String dbfile){
    	try {
            if (!(new File(dbfile).exists())) {
            	//判断数据库文件是否存在，若不存在则执行导入，否则直接打开数据库
                InputStream is = this.mContext.getResources().openRawResource(
                        R.raw.db_weather); //欲导入的数据库
                FileOutputStream fos = new FileOutputStream(dbfile);
                byte[] buffer = new byte[BUFFER_SIZE];
                int count = 0;
                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }
                fos.close();
                is.close();
            }
            SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dbfile,
                    null);
            return db;
        } catch (FileNotFoundException e) {
            Log.e("Database", "File not found");
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("Database", "IO exception");
            e.printStackTrace();
        }
        return null;
    }
    
    public void closeDatabase() {
        this.mSQLiteDatabase.close();
    }
    
    
    public String findNameByCityId(String id){
    	
    	try {
    		Cursor cursor = mSQLiteDatabase.query(TABLE_NAME, null, COLUMN_CITY_NUM+"=?", new String[]{id}, null, null, null);
    		//Log.e("sqliteheheheheh", cursor.getCount()+" "+cursor.getColumnCount());
    		if(cursor.getCount()>0){
    			cursor.moveToFirst();
    			return cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	//Log.e("sqliteheheheheh", "count = 0!!!!!!!!!!!");
    	return null;
    	
    }
    
    
}
