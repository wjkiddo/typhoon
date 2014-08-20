package com.example.gddemo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.gddemo.db.DBOpenHelper.MODE;

public class MyDAO {
	private SQLiteDatabase db_write;
	private MODE m;
	private DBOpenHelper helper;

	// private

	public MyDAO(Context context, String name, MODE m) {
		helper = new DBOpenHelper(context, name, 1, m);
		
		this.m = m;
	}

	public void insert(String name, int year, String id,int length,
			String startDate, String endDate,int subCenterCount) {
		db_write = helper.getWritableDatabase();
		if (m == MODE.MODE_TYPHOONSet) {
			db_write.beginTransaction();
			try {
				db_write.execSQL("INSERT INTO typhoonSet(name,year,id,length,startDate,endDate,subCenterCount) VALUES(?,?,?,?,?,?,?)",
						new Object[] {name, year, id,length, startDate,
								endDate,subCenterCount});
				db_write.setTransactionSuccessful();
			} finally {
				db_write.endTransaction();
			}
		}
		db_write.close();

	}

	public void insert(String name,int year ,String id, String time,int level, int lat, int lon,
			int pres, int wnd) {
		if (m == MODE.MODE_TYPHOON) {
			db_write = helper.getWritableDatabase();
			db_write.beginTransaction();
			try {
				db_write.execSQL("INSERT INTO typhoon(name,year,id,time,level,lat,lon,pres,wnd) VALUES(?,?,?,?,?,?,?,?,?)",
						new Object[] {name,year,id,time,level,lat,lon,pres,wnd });
				db_write.setTransactionSuccessful();
			} finally {
				db_write.endTransaction();
			}
			db_write.close();
		}
	}
	
	public SQLiteDatabase getReadableDB() {
		 return helper.getReadableDatabase();
	}

	public SQLiteDatabase getWritableDB() {
		return helper.getWritableDatabase();
	}
	
	public void close() {
		helper.close();
	}
}
