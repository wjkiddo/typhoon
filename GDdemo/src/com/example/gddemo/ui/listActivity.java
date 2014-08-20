package com.example.gddemo.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.gddemo.R;
import com.example.gddemo.R.id;
import com.example.gddemo.R.layout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class listActivity extends Activity implements OnItemClickListener {
	private ListView lv;
	private String[] shows = null;                   //存放ListView子项的显示内容
	private List<String> list = new ArrayList<String>();
	private int count, showCount;
	private List<? extends Map<String, ?>> data;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		lv = (ListView) findViewById(R.id.lv1);
		Intent intent = getIntent();
		int count = intent.getIntExtra("count", 0);
		this.count = count;
		/*showCount = count < 10 ? 10 : count;
		shows = new String[showCount];*/
		shows = new String[count];
	
		for (int i = 1; i <= count; i++) {
			String name = intent.getStringExtra("typhoonName" + i);
			String date = intent.getStringExtra("date" + i);
			String id = intent.getStringExtra("id" + i);
			String show = intent.getStringExtra("show" + i);
			if (show.contains("(nameless)")) {
				show = show.replace("(nameless)", "nameless");
			}
			shows[i - 1] = show;
			list.add(name + "_" + date+"_"+id);

		}

		lv.setOnItemClickListener(this);
		lv.setAdapter(new MyAdapter(this));

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		if (position < count) {
			String[] ss = list.get(position).split("_");
			Intent intent = new Intent(listActivity.this,
					showTyphoonActivity.class);
			intent.putExtra("typhoonName", ss[0]);
			intent.putExtra("date", ss[1]);
			intent.putExtra("id", ss[2]);
			startActivity(intent);
		}
	}

	private class MyAdapter extends BaseAdapter {

		private LayoutInflater mInflater;

		public MyAdapter(Context context) {
			this.mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return shows.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			//根据layout文件实例化listview的子项
			if(position==0) 
				convertView = mInflater.inflate(R.layout.vlist1, null);
			else 
				convertView = mInflater.inflate(R.layout.vlist2, null);
			TextView tv1 = (TextView) convertView.findViewById(R.id.textView1);
			tv1.setTextSize(22);
			tv1.setTextColor(0xFF383838);
			if (position < count) {
				String[] ss = shows[position].split("_");
				tv1.setText(ss[0]);
			} else {
				tv1.setText("");
			}

			TextView tv2 = (TextView) convertView.findViewById(R.id.textView2);
			tv2.setTextSize(22);
			tv2.setTextColor(0xFF383838);
			if (position < count) {
				String[] ss = shows[position].split("_");
				tv2.setText(ss[1]);
			} else {
				tv2.setText("");
			}

			return convertView;
		}

	}

}
