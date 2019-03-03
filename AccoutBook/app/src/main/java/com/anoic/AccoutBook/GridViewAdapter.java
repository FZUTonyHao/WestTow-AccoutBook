package com.anoic.AccoutBook;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import com.anoic.AccoutBook.dao.RecordsDao;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GridViewAdapter extends BaseAdapter {

	LayoutInflater inflater;
	private TextView num = null;
	private TextView pay = null;
	private TextView income = null;
	private String[] months;
	int year = new GregorianCalendar().get(GregorianCalendar.YEAR);
	Map m = null;
	RecordsDao dao;
	
	public GridViewAdapter(Context c,int year,String [] months) {
		inflater = LayoutInflater.from(c);
		this.months = months;
		this.year = year;
		dao = RecordsDao.getInstance((Activity)c);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return months.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return months[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(null == convertView){
			convertView = inflater.inflate(R.layout.griditem,null);
			num = (TextView) convertView.findViewById(R.id.num);
			pay = (TextView) convertView.findViewById(R.id.pay);
			income = (TextView) convertView.findViewById(R.id.income);
			
			m = dao.getPerMonth(year);
			List l = (List)m.get(new Integer(position+1));
			num.setText(months[position]);
			num.setTextColor(Color.WHITE);
			num.setTypeface(Typeface.DEFAULT_BOLD);
			num.setTextSize(30);
			pay.setText("支出:"+l.get(0));
			pay.setTextColor(Color.WHITE);
			income.setText("收入:"+l.get(1));
			income.setTextColor(Color.WHITE);
		}
		return convertView;
	}

}
