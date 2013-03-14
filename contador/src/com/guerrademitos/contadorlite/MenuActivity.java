package com.guerrademitos.contadorlite;

import com.guerrademitos.contadorlite.apprater.AppRater;
import com.guerrademitos.contadorlite.utils.Utils;

import android.net.Uri;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;

public class MenuActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Utils.setFullScreen(MenuActivity.this);
		setContentView(R.layout.activity_menu);
		
		overridePendingTransition(R.anim.rotation, R.anim.rotation);
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
		DialogFragment dialog = new BuyItDialogFragment();
		dialog.show(getSupportFragmentManager(), "Select Background");
	}
	
	@SuppressLint("ValidFragment")
	public class BuyItDialogFragment extends DialogFragment{
		@Override
		public Dialog onCreateDialog(Bundle savedInstance){
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			
			builder.setMessage(R.string.buyit)
				   .setPositiveButton(R.string.buy, new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                       //Open store
	                	   
	                	   try {
	                		    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+Utils.APP_NAME)));
	                		} catch (android.content.ActivityNotFoundException anfe) {
	                		    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id="+Utils.APP_NAME)));
	                		}
	                	   
	                   }
	               })
				   .setNegativeButton(R.string.later, new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                       // User cancelled the dialog
	                   }
	               });
			
			return builder.create();
		}
	}
}
