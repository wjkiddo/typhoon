package com.example.gddemo.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.gddemo.db.MyDAO;
import com.example.gddemo.db.DBOpenHelper.MODE;
import com.example.gddemo.R;

public class searchWithDateOnlyActivity extends Activity {
	private DatePicker datePicker;
	private Button search;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_with_date_only);
		
		datePicker = (DatePicker) findViewById(R.id.datePicker);
		datePicker.init(1980, 0, 1, null);

		
		// 隐藏DatePicker的日期显示
		((ViewGroup) ((ViewGroup) datePicker.getChildAt(0)).getChildAt(0))
				.getChildAt(2).setVisibility(View.GONE);
	}
	
	public void onClick(View v) {
		List<String> list = new ArrayList<String>();
		List<String> idList = new ArrayList<String>();
		String month;
		int m;
		if ((m = datePicker.getMonth()) < 10) {
			month = "0" + String.valueOf(m + 1);
		} else {
			month = String.valueOf(m + 1);
		}
		
		SQLiteDatabase dbSet = new MyDAO(this, "typhoonSet.db", MODE.MODE_TYPHOONSet).getReadableDB();
		Cursor cursor =dbSet.rawQuery("select name,id from typhoonSet where  year = ? and (startDate like ? or startDate like ?)",new String[] {datePicker.getYear()+"",month+"%",month+"%"});
		while(cursor.moveToNext()) {
			String name = cursor.getString(0);
			String id  = cursor.getString(1);
			idList.add(id);
			list.add(name);
		}
		
		if(list.size()==0) {
			Toast.makeText(this, "此月份没有热带气旋活动", Toast.LENGTH_SHORT).show();
		} else {
			Intent intent  = new Intent(this,listActivity.class);
			intent.putExtra("count", list.size());
			for(int i=0; i<list.size(); i++) {
				String name = list.get(i);
				String id = idList.get(i);
				String show = name+"_"+datePicker.getYear()+"年"+id+"号热带气旋";
				intent.putExtra("typhoonName"+(i+1), name);
				intent.putExtra("date"+(i+1), datePicker.getYear()+month);
				intent.putExtra("id"+(i+1),id);
				intent.putExtra("show"+(i+1), show);
			}
			startActivity(intent);
		}
		dbSet.close();
	}
}
