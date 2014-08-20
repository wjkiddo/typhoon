package com.example.gddemo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {
	private MODE m;
	public enum MODE {
		MODE_TYPHOONSet,MODE_TYPHOON;
	}
	public DBOpenHelper(Context context,String name,int version,MODE m) {
		super(context, name, null, version);
		this.m = m;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		if(m==MODE.MODE_TYPHOONSet)
			db.execSQL("create table typhoonSet(sid integer primary key autoincrement,name VARCHAR(15),year INTEGER,id VARCHAR(4),length INTEGER,startDate VARCHAR(4),endDate VARCHAR(4),subCenterCount INTEGER)");
		if(m==MODE.MODE_TYPHOON)
			db.execSQL("create table typhoon(tid integer primary key autoincrement,name VARCHAR(15),year INTEGER,id VARCHAR(4),time VARCHAR(10),level INTEGER,lat INTEGER,lon INTEGER,pres INTEGER,wnd INTEGER)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}

