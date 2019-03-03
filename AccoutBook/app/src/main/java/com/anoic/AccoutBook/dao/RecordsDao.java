package com.anoic.AccoutBook.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.anoic.AccoutBook.Bean.Records;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class RecordsDao {
	
	
	private static final String TAG ="DAO_TAG";
	
	private DataBaseHelper mdbhelper;
	private static RecordsDao mrecordsDao; 
	private static Activity activity;
	private SQLiteDatabase db;
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	//支持多线程的单例模式
	private RecordsDao(Activity activity){
		mdbhelper = new DataBaseHelper(activity);
	}
	public static RecordsDao getInstance(Activity a){
		if(null == mrecordsDao){
			synchronized(RecordsDao.class){
				if(null == mrecordsDao){
					mrecordsDao = new RecordsDao(a);
				}
			}
		}
		activity = a;
		return mrecordsDao;
	}
	
	public void execSQL(String sql){
		try{
			db = mdbhelper.getWritableDatabase();
			db.execSQL(sql);
		}catch(Exception e) {
			
		}finally{
			db.close();
			mdbhelper.close();
		}
	}
	
	
	//得到月份的收入和支出
	public List<Integer> getTotal(int year,int month){
		List<Integer> l = new ArrayList<Integer>();
		Cursor cursor = null;
		String sql = "select sum("+DataBaseHelper.COLUMN_MONEY+") AS TOTAL,"+DataBaseHelper.COLUMN_TYPE+" FROM " + DataBaseHelper.TABLE_NAME + " WHERE " +
					 DataBaseHelper.COLUMN_MONTH+"='"+month+"' and "+ DataBaseHelper.COLUMN_YEAR+"='"+ year +"' group by "+DataBaseHelper.COLUMN_TYPE+" order by "+DataBaseHelper.COLUMN_TYPE+
					 " asc";
		Log.d(TAG, sql);
		
		db = mdbhelper.getReadableDatabase();
		cursor = db.rawQuery(sql,null);
		activity.startManagingCursor(cursor);
		int nums = cursor.getCount();
		if(nums == 0){
			l.add(0);
			l.add(0);
		}
		else if(nums == 1){
			int total = 0;
			for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()) {
				total = cursor.getInt(cursor.getColumnIndexOrThrow("TOTAL"));
				if(cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelper.COLUMN_TYPE)) == 0){
					l.add(total);
					l.add(0);
				}else{
					l.add(0);
					l.add(total);
				}
			}

		}else{
			for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()) {
				l.add(cursor.getInt(cursor.getColumnIndexOrThrow("TOTAL")));
			}
		}
		db.close();
		mdbhelper.close();
		return l;
	}
	
	
	
	
	//删除记录
	public void delRecord(String idstr){
		String sql = "delete from "+ DataBaseHelper.TABLE_NAME +" where id in ("+idstr +")";
		try{
			db = mdbhelper.getWritableDatabase();
			db.execSQL(sql);
		}catch(Exception e) {
			
		}finally{
			db.close();
			mdbhelper.close();
		}
	}
	
	//得到一个Records的list
	public List<Records> getAll(int year,int month){
		String sql="";
		if(year == 0 && month == 0){
			sql = "SELECT * FROM "+DataBaseHelper.TABLE_NAME +" order by ID DESC";
		}else{
			sql = "SELECT * FROM "+DataBaseHelper.TABLE_NAME + " where " +DataBaseHelper.COLUMN_YEAR + "='"+year+"' and "+DataBaseHelper.COLUMN_MONTH+"='"+month+"' order by ID DESC ";
		}
		
		Cursor cursor = null;
		List<Records> ls = new ArrayList<Records>();
		db = mdbhelper.getReadableDatabase();
		cursor = db.rawQuery(sql,null);
		activity.startManagingCursor(cursor);
        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext())  
        {
        	Records r = new Records();
            r.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelper.COLUMN_ID)));
            r.setAction(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelper.COLUMN_ACTION)));
            r.setDatetime(cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_ATIME)));
            r.setMoney(cursor.getFloat(cursor.getColumnIndexOrThrow(DataBaseHelper.COLUMN_MONEY)));
            r.setDay(cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelper.COLUMN_DAY)));
            r.setMonth(cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelper.COLUMN_MONTH)));
            r.setYear(cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelper.COLUMN_YEAR)));
            r.setType(cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelper.COLUMN_TYPE)));
            ls.add(r);
        }   
		db.close();
		mdbhelper.close();
		return ls;
	}
	
	
	//插入记录
	public long InsertRecord(Records r){
		long rows = 0;
		db = mdbhelper.getWritableDatabase();
		Calendar c =Calendar.getInstance(Locale.CHINA);
		ContentValues cv=new ContentValues();
		cv.put("ACTION", r.getAction());
		cv.put("MONEY",r.getMoney());
		cv.put("YEAR",c.get(Calendar.YEAR));
		cv.put("MONTH",(c.get(Calendar.MONTH)+1));
		cv.put("DAY",c.get(Calendar.DATE));
		cv.put("TYPE",r.getType());
		cv.put("ATIME",dateFormat.format(new Date()));
		try{
			rows = db.insert(DataBaseHelper.TABLE_NAME, null, cv);
		}catch(Exception e){
			Log.e(TAG, e.getMessage());
		}
		db.close();
		mdbhelper.close();
		return rows;
	}
	
	
	public Map<Integer,List> getPerMonth(int year){
		Map<Integer,List> m = new HashMap<Integer,List>();
		List l = null;
		int [] months = new int[]{1,2,3,4,5,6,7,8,9,10,11,12};
		for(int i=0;i<months.length;i++){
			l = getTotal(year,months[i]);
			m.put(i+1, l);
		}
		return m;
	}
	
}
