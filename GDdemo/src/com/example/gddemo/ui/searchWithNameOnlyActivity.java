package com.example.gddemo.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gddemo.db.MyDAO;
import com.example.gddemo.db.DBOpenHelper.MODE;
import com.example.gddemo.R;

public class searchWithNameOnlyActivity extends Activity {
	private EditText et;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_with_name_only);

		et = (EditText) findViewById(R.id.editText1);

	}

	public void onClick(View v) {
		
		searchByName(et.getText().toString(),this);
		
	}

	public static String captureName(String name) {
		if(name.equals(""))
			return "";
		char[] cs = name.toCharArray();
		if (Character.isLetter(cs[0]))
			cs[0] -= 32;
		return String.valueOf(cs);
	}
	
	public static void searchByName(String name,Activity a) {
		
		String typhoonName = captureName(name.trim()
				.toLowerCase());
	
		if (typhoonName.equals("")) {
			Toast.makeText(a, "填写名称后重试", Toast.LENGTH_SHORT).show();
		} else {
			List<String> list = new ArrayList<String>();
			List<String> idList = new ArrayList<String>();
			SQLiteDatabase dbSet = new MyDAO(a, "typhoonSet.db",
					MODE.MODE_TYPHOONSet).getReadableDB();
			if (typhoonName.equals("Nameless")) {
				typhoonName = "(nameless)";
			}
			Cursor cursor = dbSet.rawQuery(
					"select year,startDate,id from typhoonSet where name=?",
					new String[] { typhoonName });
			while (cursor.moveToNext()) {
				String year = cursor.getInt(0) + "";
				String startDate = cursor.getString(1);
				String id = cursor.getString(2);
				String date = year + startDate.substring(0, 2);
				list.add(date.substring(0, 4) + "年" + date.substring(4) + "月");
				idList.add(id);
			}

			if (list.size() == 0) {
				Toast.makeText(a, "该气旋名称不存在", Toast.LENGTH_SHORT).show();
			} else {
				Intent intent = new Intent(a, listActivity.class);
				intent.putExtra("count", list.size());
				for (int i = 0; i < list.size(); i++) {
					String id = idList.get(i);
					String date = list.get(i);
					String show = date+ "_" + date.substring(0, 4) + "年" + id + "号热带气旋";
					intent.putExtra("typhoonName" + (i + 1), typhoonName);
					intent.putExtra("date" + (i + 1), date);
					intent.putExtra("id" + (i + 1), id);
					intent.putExtra("show" + (i + 1), show);

				}
				a.startActivity(intent);
			}
			dbSet.close();
		}
	}
}
