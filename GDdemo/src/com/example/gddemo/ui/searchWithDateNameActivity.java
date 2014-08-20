package com.example.gddemo.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gddemo.db.MyDAO;
import com.example.gddemo.db.DBOpenHelper.MODE;
import com.example.gddemo.R;

public class searchWithDateNameActivity extends Activity implements
		OnClickListener {

	private Button bt_search;
	private DatePicker datePicker;
	private EditText et_typhoonName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_with_date_name);
		datePicker = (DatePicker) findViewById(R.id.datePicker);
		et_typhoonName = (EditText) findViewById(R.id.et_typhoonName);

		bt_search = (Button) findViewById(R.id.bt_search);
		bt_search.setOnClickListener(this);
		datePicker.init(1980, 0, 1, null);
		// 隐藏DatePicker的日期显示
		((ViewGroup) ((ViewGroup) datePicker.getChildAt(0)).getChildAt(0))
				.getChildAt(2).setVisibility(View.GONE);
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.bt_search:
			String typhoonName = captureName(et_typhoonName.getText()
					.toString().trim().toLowerCase());
			if (typhoonName.equals("")) {
				Toast.makeText(this, "填写名称后重试", Toast.LENGTH_SHORT).show();
			} else {
				MyDAO daoSet = null;
				MyDAO daoTY = null;
				String id = null;
				int count = -1;
				try {
					if (typhoonName.equals("Nameless")) {
						typhoonName = "(nameless)";
					}

					String month;
					int m;
					if ((m = datePicker.getMonth()) < 10) {
						month = "0" + String.valueOf(m + 1);
					} else {
						month = String.valueOf(m + 1);
					}

					String date = String.valueOf(datePicker.getYear()) + month;

					daoSet = new MyDAO(this, "typhoonSet.db",
							MODE.MODE_TYPHOONSet);
					daoTY = new MyDAO(this, "typhoon.db", MODE.MODE_TYPHOONSet);

					SQLiteDatabase set = daoSet.getReadableDB();
					Cursor cursor = null;

					if (typhoonName.equals("(nameless)")) {

						cursor = set
								.rawQuery(
										"select count(*) from typhoonSet where name=? and year = ? and (startDate like ? or startDate like ?)",
										new String[] { typhoonName,
												date.substring(0, 4),
												date.substring(4) + "%",
												date.substring(4) + "%" });
						if (cursor.moveToNext()) {
							count = cursor.getInt(0);
						}
						if (count == 0) {
							Toast.makeText(this, "该月份没有本名称的热带气旋活动", Toast.LENGTH_SHORT)
									.show();
							break;
						}
						if (count > 1) {
							List<String> list = new ArrayList<String>();
							List<String> idList = new ArrayList<String>();
							set.beginTransaction();
							{
								try {
									cursor = set
											.rawQuery(
													"select id from typhoonSet where name=? and year = ? and (startDate like ? or startDate like ?)",
													new String[] {
															typhoonName,
															date.substring(0, 4),
															date.substring(4)
																	+ "%",
															date.substring(4)
																	+ "%" });
									set.setTransactionSuccessful();
								} finally {
									set.endTransaction();
								}
							}
							while (cursor.moveToNext()) {
								id = cursor.getString(0);
								idList.add(id);
								list.add(typhoonName);
							}
							Intent intent = new Intent(this, listActivity.class);
							intent.putExtra("count", count);
							for (int i = 0; i < list.size(); i++) {
								String name = list.get(i);
								String mid = idList.get(i);
								String show = name+ "_"
										+ datePicker.getYear() + "年" + mid
										+ "号热带气旋";
								intent.putExtra("typhoonName" + (i + 1),
										name);
								intent.putExtra("date" + (i + 1),
										datePicker.getYear() + month);
								intent.putExtra("id" + (i + 1), mid);
								intent.putExtra("show" + (i + 1), show);
							}
							startActivity(intent);

							break;
						}

					}

					set.beginTransaction();
					{

						try {
							cursor = set
									.rawQuery(
											"select id from typhoonSet where name=? and year = ? and (startDate like ? or startDate like ?)",
											new String[] { typhoonName,
													date.substring(0, 4),
													date.substring(4) + "%",
													date.substring(4) + "%" });
							set.setTransactionSuccessful();
						} finally {
							set.endTransaction();
						}
					}
					if (cursor.moveToFirst() == false) {
						Toast.makeText(this, "该月份没有本名称的热带气旋活动", Toast.LENGTH_SHORT)
								.show();
					} else {
						Intent intent = new Intent(
								searchWithDateNameActivity.this,
								showTyphoonActivity.class);
						id = cursor.getString(0);
						intent.putExtra("typhoonName", typhoonName);
						intent.putExtra("date", date);
						intent.putExtra("id", id);
						startActivity(intent);

					}
				} finally {
					if (daoSet != null)
						daoSet.close();
					if (daoTY != null)
						daoTY.close();
				}
			}
			break;
		default:
			break;
		}

	}

	public static String captureName(String name) {
		if(name.equals(""))
			return "";
		char[] cs = name.toCharArray();
		if (Character.isLetter(cs[0]))
			cs[0] -= 32;
		return String.valueOf(cs);
	}
}
