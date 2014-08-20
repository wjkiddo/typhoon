package com.example.gddemo.ui;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.example.gddemo.R;
import com.example.gddemo.R.id;
import com.example.gddemo.R.layout;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class typhoonNameListActivity extends Activity {
	private ListView listview;
	private List<String> list;
	private static int COUNT = 191;
	private Activity a;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_typhoon_name);
		a = this;
		listview = (ListView) findViewById(R.id.listView1);
		list = new ArrayList<String>(COUNT/2+COUNT%2);
		StringBuilder sb = new StringBuilder();
		int n = 0;
		BufferedReader br = null;
		try {
			InputStream in = a.getResources().getAssets()
					.open("typhoonName.txt");
			br = new BufferedReader(new InputStreamReader(in, "GBK"));
			String s = null;
			while ((s = br.readLine()) != null) {
				n++;
				sb.append(s);
				if (n % 2 == 1)
					sb.append("|");

				if (n % 2 == 0) {
					list.add(sb.toString());
					sb.setLength(0);
				}
			}
			if (n % 2 == 1) {
				list.add(sb.toString());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		listview.setAdapter(new MyAdapter(this));
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

			}
		});
	}

	private class MyAdapter extends BaseAdapter {
		private LayoutInflater mInflater;
		private OnClickListener left, right;

		public MyAdapter(Context context) {
			mInflater = LayoutInflater.from(context);
			left = new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String s = ((TextView) v.findViewById(R.id.tv_name1))
							.getText().toString();
					searchWithNameOnlyActivity.searchByName(s, a);
				}
			};

			right = new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String s = ((TextView) v.findViewById(R.id.tv_name3))
							.getText().toString();
					searchWithNameOnlyActivity.searchByName(s, a);
				}
			};
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return COUNT / 2 + COUNT % 2;
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
			if (position == 0)
				convertView = mInflater.inflate(R.layout.namelist1, null);
			else
				convertView = mInflater.inflate(R.layout.namelist2, null);
			TextView english1, chinese1, english2, chinese2;
			english1 = (TextView) convertView.findViewById(R.id.tv_name1);
			chinese1 = (TextView) convertView.findViewById(R.id.tv_name2);
			english2 = (TextView) convertView.findViewById(R.id.tv_name3);
			chinese2 = (TextView) convertView.findViewById(R.id.tv_name4);
			View view1 = convertView.findViewById(R.id.liner1);
			View view2 = convertView.findViewById(R.id.liner2);
			tvInit(english1, 19);
			tvInit(chinese1, 22);
			tvInit(english2, 19);
			tvInit(chinese2, 22);

			String s = list.get(position);
			String[] ss = s.split("\\|");
			int n = ss.length;

			String[] k = ss[0].split("_");
			english1.setText(k[0]);
			chinese1.setText(k[1]);

			if (ss.length == 2) {

				k = ss[1].split("_");
				english2.setText(k[0]);
				chinese2.setText(k[1]);

			}
			view1.setOnClickListener(left);
			view2.setOnClickListener(right);
			return convertView;
		}

		private void tvInit(TextView v, int size) {
			v.setTextSize(size);
			v.setTextColor(0xFF383838);
		}

	}
}
