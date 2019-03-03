package com.anoic.AccoutBook;

import java.util.List;

import com.anoic.AccoutBook.Bean.Records;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class ListViewAdapter extends BaseAdapter {

	private List<Records> mList;
	private LayoutInflater inflater = null;
	private CheckBox mcheckbox = null;
	private TextView mtime = null;
	private TextView maction = null;
	private Context context;
	public  String ids=""; 
	
	public ListViewAdapter(Context c,List<Records> list) {
		this.mList = list;
		this.context= c;
		inflater = LayoutInflater.from(c);
	}
	
	public ListViewAdapter(Context c){
		this.context= c;
		inflater = LayoutInflater.from(c);
	}

	@Override
	public int getCount() {
		if( null != mList)
			return mList.size();
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		if(null != mList){
			return mList.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		// TODO Auto-generated method stub
//		if(null == convertView){ 此处虽然可以优化速度,但是会产生重复行
			Records mRecords = (Records) mList.get(position);			
			convertView = inflater.inflate(R.layout.listitem,null);
			mtime = (TextView) convertView.findViewById(R.id.mtime);
			maction = (TextView) convertView.findViewById(R.id.maction);
			mcheckbox = (CheckBox) convertView.findViewById(R.id.tv_account_checked);
			mcheckbox.setId(mRecords.getId());
			mcheckbox.setFocusable(false);
			mcheckbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {
					// TODO Auto-generated method stub
					if(isChecked){
						ids += buttonView.getId()+",";
					}else{
						ids = ids.replace(buttonView.getId()+",","");
					}
				}
			});
			mtime.setText(mRecords.getDatetime());
			
			String moneys = mRecords.getType()== 0?"-":"+";
			
			maction.setText(mRecords.getAction()+" ￥"+ moneys + mRecords.getMoney() );
		//}
		return convertView;
	}
	
	
	
	//class Ls implements on

}
