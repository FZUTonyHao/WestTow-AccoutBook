package com.anoic.AccoutBook;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class SetPassActivity extends Activity {

	private ImageButton setpasssureBtn,setpasscancelBtn; 
	private TextView mtvOldpass,mtvAccesspass,mtvConfirmpass;
	private EditText metOldpass,metAccesspass,metConfirmpass;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setpass);
		init();
	}
	
	
	
	
	private void init(){
		setpasscancelBtn = (ImageButton) findViewById(R.id.setpasscancel);
		setpasssureBtn = (ImageButton) findViewById(R.id.setpasssure);
		
		//mtvOldpass = (TextView) findViewById(R.id.tvoldpass);
		mtvAccesspass = (TextView) findViewById(R.id.tvaccesspass);
		mtvConfirmpass = (TextView) findViewById(R.id.tvconfirmpass);
		
		//metOldpass = (EditText) findViewById(R.id.etoldspass);
		metAccesspass = (EditText) findViewById(R.id.etaccesspass);
		metConfirmpass = (EditText) findViewById(R.id.etconfirmpass);
		
		
		//设置按钮监听
		
		setpasssureBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if("".equals(metAccesspass.getText().toString())){
					Toast.makeText(SetPassActivity.this, "密码不能为空!",Toast.LENGTH_LONG).show();
					return ;
				}
				if("".equals(metConfirmpass.getText().toString())){
					Toast.makeText(SetPassActivity.this, "确认密码不能为空!",Toast.LENGTH_LONG).show();
					return;
				}
				if(!(metAccesspass.getText().toString().equals(metConfirmpass.getText().toString()))){
					Toast.makeText(SetPassActivity.this, "两次输入不一致!",Toast.LENGTH_LONG).show();
					return ;
				}
				
				SharedPreferences mPreferences = getSharedPreferences(AccountsBook.CONFIG_NAME, AccountsBook.MODE_WORLD_WRITEABLE);
				Editor editor = mPreferences.edit();
				editor.putString("password",metConfirmpass.getText().toString());
				editor.putBoolean("issetpass",true);
				editor.commit();
				finish();
				System.gc();
			}
		});
		
		
		//取消按钮监听
		setpasscancelBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				System.gc();
			}
		});
		
	}
}
