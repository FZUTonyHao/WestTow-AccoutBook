package com.anoic.AccoutBook;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import com.anoic.AccoutBook.dao.RecordsDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MonthGridActivity extends Activity implements AbstractActivity {
	/**
	 * Called when the activity is first created.
	 */

	GridView toolbarGrid = null;
	GridView mGridView = null;
	ProgressDialog mProgressDialog;
	GridViewAdapter mAdapter;
	int year;
    int[] menu_toolbars_image = {
            R.drawable.controlbar_homepage,
			R.drawable.controlbar_refresh
	};


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.monthgrid);

		year = Integer.valueOf(getIntent().getExtras().getString("year"));
		setTitle(year + "年清单");
        initToolbarGrid();
        loadProgress();

	}

	//加载进度条
	public void loadProgress() {
		mProgressDialog = new ProgressDialog(MonthGridActivity.this);
		mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		mProgressDialog.setMessage("正在加载" + year + "年清单,请稍后...");
		mProgressDialog.setIndeterminate(false);
		mProgressDialog.show();
		new Thread(new LoadThread()).start();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		loadProgress();

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	//监听器
	class MgridViewListener implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
								long id) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(MonthGridActivity.this, ListViewActivity.class);
			Bundle b = new Bundle();
			b.putInt("mIndex", position + 1);
			b.putInt("year", year);
			intent.putExtras(b);
			startActivity(intent);
		}
	}


	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if (msg.what == 1) {
//				setContentView(R.layout.main);
				mGridView = (GridView) findViewById(R.id.mgridview);
				mGridView.setAdapter(mAdapter);
				mGridView.setOnItemClickListener(new MgridViewListener());
				mProgressDialog.cancel();
			}
		}
	};


//	加载线程类
	class LoadThread implements Runnable {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				Thread.sleep(1000);
				Message message = new Message();
				mAdapter = new GridViewAdapter(MonthGridActivity.this, year, months);
				message.what = 1;
				mHandler.sendMessage(message);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		}

	}


	//更新adatper
	public void updateView() {
		mAdapter = new GridViewAdapter(MonthGridActivity.this, year, months);
	}


	public void initToolbarGrid() {
		toolbarGrid = (GridView) findViewById(R.id.GridView_2);
		toolbarGrid.setSelector(R.drawable.toolbar_menu_item);
		toolbarGrid.setBackgroundResource(R.drawable.menu_bg2);// 设置背景
		toolbarGrid.setNumColumns(2);// 设置每行列数
		toolbarGrid.setGravity(Gravity.CENTER);// 位置居中
		toolbarGrid.setVerticalSpacing(0);// 垂直间隔
		toolbarGrid.setHorizontalSpacing(30);// 水平间隔
        toolbarGrid.setAdapter(getMenuAdapter(menu_toolbars_name2,
                menu_toolbars_image));
		toolbarGrid.setOnItemClickListener(new ToolbarListener());
	}
	//工具栏的adapter
	private SimpleAdapter getMenuAdapter(String[] menuNameArray,
										 int[] imageResourceArray) {
		ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < menuNameArray.length; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("itemImage", imageResourceArray[i]);
			map.put("itemText", menuNameArray[i]);
			data.add(map);
		}
		SimpleAdapter simperAdapter = new SimpleAdapter(this, data,
				R.layout.menuitem, new String[] { "itemImage", "itemText" },
				new int[] { R.id.item_image, R.id.item_text });

		return simperAdapter;
	}

	//菜单栏点击事件监听
	class ToolbarListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
								long id) {
			// TODO Auto-generated method stub
			switch(position){

				case 0:
					startActivity(new Intent(MonthGridActivity.this,AddRecordsActivity.class));
					break;

				case 1:
                    loadProgress();
					break;
				default:
					break;
			}
		}

	}
}