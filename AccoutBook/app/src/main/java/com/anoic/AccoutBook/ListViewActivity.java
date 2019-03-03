package com.anoic.AccoutBook;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import com.anoic.AccoutBook.Bean.Records;
import com.anoic.AccoutBook.dao.RecordsDao;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
public class ListViewActivity extends Activity implements AbstractActivity { 

	ListView mListView = null;
	GridView toolbarGrid = null;
	ListViewAdapter mListAdapter;
	int year,month = 0;
	TextView tv = null;
	String ids = "";
	List<Integer>  l = null;
	RecordsDao dao = RecordsDao.getInstance(this);
	int[] menu_toolbars_image = { R.drawable.controlbar_homepage,
			   R.drawable.controlbar_window,
			R.drawable.menu_delete,
			 };
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview);
		Intent intent = getIntent();
		Bundle b = intent.getExtras();
		month = b.getInt("mIndex");
		year = b.getInt("year");
		setTitle(year+"年"+month+"月份清单");
		
		
		mListView = (ListView) findViewById(R.id.mlistview);		
		tv = (TextView) findViewById(R.id.totalmoney);
		updateAdapter();
		initToolbarGrid();
	}
	
	
	//初始化工具栏按钮
	public void initToolbarGrid(){
		toolbarGrid = (GridView) findViewById(R.id.GridView_toolbar);
		toolbarGrid.setSelector(R.drawable.toolbar_menu_item);
		toolbarGrid.setBackgroundResource(R.drawable.menu_bg2);// 设置背景
		toolbarGrid.setNumColumns(3);// 设置每行列数
		toolbarGrid.setGravity(Gravity.CENTER);// 位置居中
		toolbarGrid.setVerticalSpacing(10);// 垂直间隔
		toolbarGrid.setHorizontalSpacing(30);// 水平间隔
		toolbarGrid.setAdapter(getMenuAdapter(menu_toolbars_name,
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
				case 0 :
					Intent t = new Intent();
					t.setAction(Intent.ACTION_MAIN);
					t.addCategory(Intent.CATEGORY_HOME);
					startActivity(t);
					finish();
					System.gc();
					break;
				case 1:
					startActivity(new Intent(ListViewActivity.this,AddRecordsActivity.class));
					break;
				case 2:
					if(null == mListAdapter.ids || "".equals(mListAdapter.ids) || mListAdapter.ids.length()<=0){
						Toast.makeText(ListViewActivity.this, "请选择记录",Toast.LENGTH_SHORT).show();
					}else{
						
						AlertDialog dialog = new AlertDialog.Builder(ListViewActivity.this).setTitle(R.string.notice).setMessage("您确定要删除这些记录吗？").setPositiveButton("确定",new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								
								String ids = mListAdapter.ids;
								ids = ids.substring(0,ids.length()-1);
								dao.delRecord(ids);
								updateAdapter();
								mListAdapter.ids = "";
								Toast.makeText(ListViewActivity.this, "删除成功",Toast.LENGTH_SHORT).show();
								dialog.cancel();
							}													
						}).setNegativeButton(R.string.addcancel,new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
							}
						}).create();						
						dialog.show();
					}
					break;
				case 3:
					break;
				default:
					break;
			}
		}
		
	}
	
	private void updateAdapter(){
		mListAdapter = new ListViewAdapter(ListViewActivity.this,dao.getAll(year,month));
		mListView.setAdapter(mListAdapter);
		l = dao.getTotal(year,month);
		tv.setText(" 支出:"+l.get(0)+"元    收入:"+l.get(1)+"元");
	}
	
	
	@Override
	protected void onResume() {
		super.onResume();
		updateAdapter();
	}
	
	
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	//	mListAdapter.notifyDataSetChanged();
	}

}
