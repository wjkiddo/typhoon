package com.example.gddemo.ui;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.example.gddemo.R;
import com.example.gddemo.R.id;
import com.example.gddemo.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;

public class searchModeActivity extends Activity implements OnClickListener,
		OnCheckedChangeListener {
	private CheckBox searchByDate, searchByName, selectAll;
	private Button ok,buttonName;
	private boolean canelByCBAll;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		sdfsdfsdfsdfsd

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.button_ok:
			if (searchByDate.isChecked()) {
				if (searchByName.isChecked()) {
					Intent intent = new Intent(searchModeActivity.this,
							searchWithDateNameActivity.class);
					startActivity(intent);
				} else {
					Intent intent = new Intent(searchModeActivity.this,
							searchWithDateOnlyActivity.class);
					startActivity(intent);
				}
			} else {
				if (searchByName.isChecked()) {
					Intent intent = new Intent(searchModeActivity.this,
							searchWithNameOnlyActivity.class);
					startActivity(intent);
				} else {
					Toast.makeText(getApplicationContext(), "nothing",
							Toast.LENGTH_SHORT).show();

				}
			}
			break;
		case R.id.buttonName:
			Intent intent = new Intent(searchModeActivity.this,
					typhoonNameListActivity.class);
			startActivity(intent);
			
			
		default:
			break;
		}

	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		switch (buttonView.getId()) {
		case R.id.checkBox_selectAll:
			if (selectAll.isChecked()) {
				searchByDate.setChecked(true);
				searchByName.setChecked(true);
			} else {
				if (canelByCBAll) {
					searchByDate.setChecked(false);
					searchByName.setChecked(false);
				}
			}
			break;

		case R.id.checkBox_searchByDate:
			if (searchByDate.isChecked()) {
				if (searchByName.isChecked()) {
					selectAll.setChecked(true);
					canelByCBAll = true;
				}
			} else {
				canelByCBAll = false;
				selectAll.setChecked(false);
			}
			break;

		case R.id.checkBox_searchByName:
			if (searchByName.isChecked()) {
				if (searchByDate.isChecked()) {
					selectAll.setChecked(true);
					canelByCBAll = true;
				}
			} else {
				canelByCBAll = false;
				selectAll.setChecked(false);
			}
			break;
		default:
			break;
		}

	}

	public void putFileFromAssets(String name, String dirname)
			throws IOException {
		InputStream in = this.getResources().getAssets().open(name);
		BufferedInputStream bin = new BufferedInputStream(in);
		File dir = new File("/data/data/" + this.getPackageName() + "/"
				+ dirname);
		File file = new File(dir, name);
		if (dir.exists() && file.exists()) {
			return;
		}

		if (!dir.exists()) {
			dir.mkdir();
		}
		int n = -1;
		if (!file.exists()) {
			FileOutputStream out = new FileOutputStream(file);
			byte[] b = new byte[1024];
			while ((n = bin.read(b)) != -1) {
				out.write(b, 0, n);
			}
			out.close();
			bin.close();

		}

	}

	public void putDBFromAssets(String name) throws IOException {
		putFileFromAssets(name, "databases");
	}

}