package com.guerrademitos.contador;

import com.guerrademitos.contador.apprater.AppRater;
import com.guerrademitos.contador.utils.Utils;

import android.os.Bundle;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;

public class MenuActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Utils.setFullScreen(MenuActivity.this);
		setContentView(R.layout.activity_menu);
		
		Utils.setSelectedBackground(findViewById(R.id.rl_bg_menu));
		
		AppRater.app_launched(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_menu, menu);
		return true;
	}
	
	public void startCounterApp(View v){
		Intent i = new Intent(MenuActivity.this,CounterActivity.class);
		startActivity(i);
	}
	
	public void openRules(View v){
		Intent i = new Intent(MenuActivity.this,RulesActivity.class);
		startActivity(i);		
	}
	
	public void openPrefs(View v){
		Intent i = new Intent(MenuActivity.this,SettingsActivity.class);
		startActivity(i);
	}
}
