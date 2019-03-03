package com.anoic.AccoutBook;

import java.util.Calendar;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import android.widget.Toast;


public class AccountsBook extends Activity implements AbstractActivity {
    /** Called when the activity is first created. */
	private static Boolean isExit= false;
	private static Boolean hasTask  = false;
    Timer tExit = new Timer();
    private EditText meditText,mpassword;
    private TextView mtvyear,mtvpass;
    private Button mEnterButton,mSetPassButton,mClearButton;
    private CheckBox mCheckBox;
    public static final String CONFIG_NAME = "accountsbook";
    Calendar c =  Calendar.getInstance(Locale.CHINA);
    private SharedPreferences mPreferences;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);   
        setContentView(R.layout.main);	
		initUI();
    }
    
    //初始化
    public void initUI(){
    	
    	mPreferences = getSharedPreferences(CONFIG_NAME, AccountsBook.MODE_WORLD_READABLE);
    	
    	mtvyear = (TextView) findViewById(R.id.tvyear);
    	mtvpass = (TextView) findViewById(R.id.tvpass);
    	mSetPassButton = (Button)findViewById(R.id.setpass);
    	mClearButton = (Button)findViewById(R.id.clearall);
    	meditText = (EditText) findViewById(R.id.setmonth);
		mpassword = (EditText) findViewById(R.id.password);
		mEnterButton = (Button) findViewById(R.id.enter);		
		mCheckBox = (CheckBox) findViewById(R.id.isRempass);
		
		
		
    	mtvyear.setText("年份:");
    	mtvpass.setText("密码:");
    	mtvyear.setTypeface(Typeface.DEFAULT_BOLD);  
    	mtvpass.setTypeface(Typeface.DEFAULT_BOLD);
    	mtvyear.setTextColor(Color.WHITE);
    	mtvpass.setTextColor(Color.WHITE);
		meditText.setText(c.get(Calendar.YEAR)+"");
		
		
		//设置密码监听
		mSetPassButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				Bundle b = new Bundle();
				b.putString("year",meditText.getText().toString());
				intent.putExtras(b);
				intent.setClass(AccountsBook.this,SetPassActivity.class);
				startActivity(intent);				
			}
		});
		
		//进入按钮监听器
		mEnterButton.setOnClickListener(new OnClickListener() {
			boolean isenter = false;
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if("".equals(meditText.getText().toString())){
					Toast.makeText(AccountsBook.this, "请选择年份",Toast.LENGTH_LONG).show();
					return;
				}
				if(mPreferences.getBoolean("issetpass", false)){
					if("".equals(mpassword.getText().toString())){
						Toast.makeText(AccountsBook.this, "请输入密码",Toast.LENGTH_LONG).show();
						return ;
					}else{
					//	Log.e("",)
						isenter = (mPreferences.getString("password","").equals(mpassword.getText().toString()))?true:false;
						if(!isenter){
							Toast.makeText(AccountsBook.this, "密码不正确",Toast.LENGTH_LONG).show();
							return ;
						}
					}
				}
				
				if(isenter){
					Intent intent = new Intent();
					Bundle b = new Bundle();
					b.putString("year",meditText.getText().toString());
					intent.putExtras(b);
					intent.setClass(AccountsBook.this,MonthGridActivity.class);
					startActivity(intent);
				}else{
					return ;
				}
			}
		});
		
		//个性化设置操作记录
    	
    	if(mPreferences.getBoolean("isfirst",true)){
    		Toast.makeText(AccountsBook.this,"您第一次登录本软件,为了您的隐私安全,请设置访问密码!",Toast.LENGTH_LONG).show();
    	}
    	if(mPreferences.getBoolean("isRempass",false)){
    		mpassword.setText(mPreferences.getString("password",""));
    		mCheckBox.setChecked(true);
    	}
    }
    
    
   
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
    		ContextMenuInfo menuInfo) {
    	// TODO Auto-generated method stub
    	super.onCreateContextMenu(menu, v, menuInfo);
    }

  	
    @Override
    protected void onStop(){
    	mPreferences = getSharedPreferences(CONFIG_NAME, AccountsBook.MODE_PRIVATE);
    	SharedPreferences.Editor mEditor = mPreferences.edit();
    	mEditor.putBoolean("isfirst",false);
    	mEditor.putBoolean("isRempass", mCheckBox.isChecked());
    	mEditor.commit();
    	super.onStop();
  	}
    @Override
    protected void onResume() {
    	mPreferences = getSharedPreferences(CONFIG_NAME, AccountsBook.MODE_PRIVATE);
    	super.onResume();
    };
  	
    
  	TimerTask task = new TimerTask() {
  	  	 @Override
  	  		 public void run() {
  	  		 	isExit = false;
  	  		 	hasTask = true;
  	  	 	}
  	  	};
    
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		//双击返回退出程序
		if(keyCode == KeyEvent.KEYCODE_BACK){
			if(isExit == false ) {
				isExit = true;
				Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
				if(!hasTask) {
					tExit.schedule(task, 2000);
				}} else {
				finish();
				System.exit(0);
			}
		}			
		return false;
	}
    
}