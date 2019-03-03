package com.anoic.AccoutBook.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class DataBaseHelper extends SQLiteOpenHelper {

	
	//数据库字段应该定义成常量,以便重用方便
	protected static final String COLUMN_ID = "ID";
	protected static final String COLUMN_ACTION="ACTION";
	protected static final String COLUMN_YEAR="YEAR";
	protected static final String COLUMN_MONEY="MONEY";
	protected static final String COLUMN_MONTH="MONTH";
	protected static final String COLUMN_DAY="DAY";
	protected static final String COLUMN_ATIME="ATIME";
	protected static final String COLUMN_TYPE="TYPE";
	
	
	
	private static final String DATABASE_NAME = "accountBooks";
	private static final int DATABASE_VERSION = 1;
	public static final String TABLE_NAME="ACCOUNTBOOK";
	
	public DataBaseHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}
	
	public DataBaseHelper(Context context,String name,int version){
		this(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	public DataBaseHelper(Context context,String name){
		this(context, DATABASE_NAME,DATABASE_VERSION);
	}
	
	public DataBaseHelper(Context context){
		this(context, DATABASE_NAME,DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("CREATE TABLE IF NOT EXISTS "+ TABLE_NAME +" (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_ACTION + " TEXT," + COLUMN_YEAR + " INTEGER," + COLUMN_MONEY + " FLOAT," + COLUMN_TYPE + " INTEGER," + COLUMN_MONTH + " INTEGER,"+COLUMN_DAY+" INTEGER,"+COLUMN_ATIME+" TEXT)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
