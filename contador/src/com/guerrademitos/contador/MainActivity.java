package com.guerrademitos.contador;

import com.bugsense.trace.BugSenseHandler;

import com.guerrademitos.contador.utils.Utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


public class MainActivity extends Activity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		BugSenseHandler.initAndStartSession(getApplicationContext(), "8f2dfd22");
		
		Utils.setContext(getApplicationContext());
		Utils.setFullScreen(MainActivity.this);

		setContentView(R.layout.activity_main);
		
		Utils.setSelectedBackground(findViewById(R.id.bgsplash));
		
		final int millis = 3000;
		Thread splash = new Thread(){
			int wait = 0;
			
			@Override
			public void run(){
				try{
					while(wait < millis){
						sleep(100);
						wait+=100;
					}
				}catch(Exception e){
					System.err.println(e.getMessage());
				}finally{
					Intent i = new Intent(MainActivity.this,MenuActivity.class);
					startActivity(i);
					finish();
				}
			}
		};
		splash.start();
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
	}
}
