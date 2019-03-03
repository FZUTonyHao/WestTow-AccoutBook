package com.anoic.AccoutBook;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.anoic.AccoutBook.Bean.Records;
import com.anoic.AccoutBook.dao.RecordsDao;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class AddRecordsActivity extends Activity {

	private ImageButton addRec,addCancel;
	private EditText maction,mtime,mmoney;
	private RadioGroup type;
	private RadioButton mincome,mpay;
	private RecordsDao dao = RecordsDao.getInstance(this);;
	private Records mrecords;
	private int ttype = -1;
	ListViewAdapter mListAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addrecords);
		setTitle(R.string.addrec);
		
		addRec = (ImageButton) findViewById(R.id.addsure);
		addCancel = (ImageButton) findViewById(R.id.addcancel);
		
		maction = (EditText) findViewById(R.id.maction);
		mtime = (EditText) findViewById(R.id.mtime);
		mtime.setText(getDateString());
		mtime.setEnabled(false);
		
		mmoney = (EditText) findViewById(R.id.mmoney);
		type = (RadioGroup) findViewById(R.id.mtypegroup);
		mincome = (RadioButton) findViewById(R.id.mincome); 
		mpay = (RadioButton) findViewById(R.id.mpay);
		
		
		type.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				if(mincome.getId() ==  checkedId)
					ttype = 1;
				else 
					ttype = 0;
			}
		});
		
		
		
		
		//添加记录监听器
		addRec.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				final String actionStr = maction.getText().toString();
				final String moneyStr = mmoney.getText().toString();
				if(null == actionStr.trim() || "".equals(actionStr.trim())){
					AlertDialog dialog = new AlertDialog.Builder(AddRecordsActivity.this).setTitle(R.string.notice).setMessage("事件不能为空!").setPositiveButton("确定",new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							
						}
					}).create();
					dialog.show();
					return ;
				}
				if(null == moneyStr.trim() || "".equals(moneyStr.trim())){
					AlertDialog dialog = new AlertDialog.Builder(AddRecordsActivity.this).setTitle(R.string.notice).setMessage("金额不能为空!").setPositiveButton("确定",new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							
						}
					}).create();
					dialog.show();
					return ;
				}
				if(ttype == -1){
					AlertDialog dialog = new AlertDialog.Builder(AddRecordsActivity.this).setTitle(R.string.notice).setMessage("请选择类型!").setPositiveButton("确定",new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							
						}
					}).create();
					dialog.show();	
					return ;
				}
				
				
				
				mrecords = new Records();
				mrecords.setAction(maction.getText().toString());
				mrecords.setMoney(Float.valueOf(mmoney.getText().toString()));
				mrecords.setType(Integer.valueOf(ttype));				
				dao.InsertRecord(mrecords);
				finish();
				System.gc();
			}
		});
		
		//取消监听器
		addCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog dialog = new AlertDialog.Builder(AddRecordsActivity.this).setTitle(R.string.notice).setMessage(R.string.addcancel_message).setPositiveButton("确定",new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Toast.makeText(AddRecordsActivity.this, R.string.addcancel_nmessage, Toast.LENGTH_LONG).show();
						dialog.dismiss();
						finish();						
					}
					
					
				}).setNegativeButton(R.string.addcancel,new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.cancel();
						Toast.makeText(AddRecordsActivity.this, R.string.addcancel_pmessage, Toast.LENGTH_LONG).show();
					}
				}).create();
				dialog.show();
			}
		});
		
	}
	
	/**
	 * 得到时间字符串
	 * @return
	 */
	public String getDateString(){
		SimpleDateFormat   dateFormat   =  new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(new Date());
	}
}
