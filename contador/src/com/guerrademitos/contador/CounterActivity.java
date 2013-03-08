package com.guerrademitos.contador;

import com.google.ads.*;
import com.google.ads.AdRequest.ErrorCode;
import com.guerrademitos.contador.utils.Utils;

import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CounterActivity extends FragmentActivity implements AdListener {

	private TextView tv1, tv2, tv_timer;
	
	private boolean isTimerOn;
	protected long millisLeft;
	myCounter timer;
	
	int turn;
	ImageView iv1, iv2;

	private AdView adView;
	protected RelativeLayout layoutAd;
	private InterstitialAd interstitial;
	
	RelativeLayout rl = null;
	TextView rltv = null;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Utils.setFullScreen(this);
		setContentView(R.layout.activity_counter);
		
		//Que no se apague la pantalla
		Utils.keepAlive(this);
		
		Utils.setSelectedBackground(findViewById(R.id.rl_bg_counter));
		
		tv1 = (TextView)findViewById(R.id.tv_player1);
		tv2 = (TextView)findViewById(R.id.tv_player2);
		tv1.setText("20");
		tv2.setText("20");
		
		tv_timer = (TextView)findViewById(R.id.tv_timer);
		isTimerOn = false;
		millisLeft = timeToMillis(40, 00);
		
		turn = 1;
		iv1 = (ImageView)findViewById(R.id.iv_player1);
		iv2 = (ImageView)findViewById(R.id.iv_player2);
		
		iv1.setVisibility(View.VISIBLE);
		iv2.setVisibility(View.GONE);
		
		interstitial = new InterstitialAd(this,Utils.ADMOB_ID);
		AdRequest adRequest = new AdRequest();
		if (Utils.TEST_MODE){
			adRequest.addTestDevice(AdRequest.TEST_EMULATOR);
			adRequest.addTestDevice("TEST_DEVICE_ID");
		}
		
		interstitial.loadAd(adRequest);
		interstitial.setAdListener(this);
		
		
	}
	
	@Override
	public void onDestroy() {
	    if (adView != null) {
	      adView.destroy();
	    }
	    super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_counter, menu);
		return true;
	}
	
	public long timeToMillis(int min, int sec){
		long time = 0;
		
		time += min*60000;
		time += sec*1000;
		
		return time;
	}
	
	public String millisToTime(long millis){
		String time = "";
		
		int min = (int) ((millis / 1000) / 60);
		int sec = (int) ((millis / 1000) % 60);
		
		time += String.format("%02d",min);
		time += ":";
		time += String.format("%02d",sec);
		
		return time;
	}
	
	public void player1Up(View v){
		int score = Integer.parseInt(tv1.getText().toString());
		tv1.setText( String.valueOf(++score) );
	}
	
	public void player1Down(View v){
		int score = Integer.parseInt(tv1.getText().toString());
		tv1.setText( String.valueOf(--score) );
	}
	
	public void player2Up(View v){
		int score = Integer.parseInt(tv2.getText().toString());
		tv2.setText( String.valueOf(++score) );
	}
	
	public void player2Down(View v){
		int score = Integer.parseInt(tv2.getText().toString());
		tv2.setText( String.valueOf(--score) );
	}
	
	public void changeMainPlayer(View v){
		if(turn == 1){
			iv1.setVisibility(View.GONE);
			iv2.setVisibility(View.VISIBLE);
			turn = 2;
		}else{
			iv1.setVisibility(View.VISIBLE);
			iv2.setVisibility(View.GONE);
			turn = 1;
		}
	}
	
	public void timerClick(View v){
		if(!isTimerOn){
			isTimerOn = true;
			timer = new myCounter(millisLeft,1000);
			timer.start();
		}else{
			isTimerOn=false;
			timer.cancel();
		}
	}
	
	public class myCounter extends CountDownTimer{

		public myCounter(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onFinish() {
			timer.cancel();
			millisLeft = timeToMillis(40, 00);
			tv_timer.setText("40:00");
		}

		@Override
		public void onTick(long millisUntilFinished) {
			tv_timer.setText(millisToTime(millisUntilFinished));
			millisLeft = millisUntilFinished;
		}	
	}
	
	
	// Background
	public void changeBgPlayer(View v){
		
		if(v.getId() == R.id.rl_prefs_player1){
			rl = (RelativeLayout)findViewById(R.id.c_bg_player1);
			rltv = (TextView)findViewById(R.id.tv_player1);
		}
		else if(v.getId() == R.id.rl_prefs_player2){
			rl = (RelativeLayout)findViewById(R.id.c_bg_player2);
			rltv = (TextView)findViewById(R.id.tv_player2);
		}
		else return;
		
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

	@Override
	public void onDismissScreen(Ad ad) {}

	@Override
	public void onFailedToReceiveAd(Ad ad, ErrorCode ecode) {
		adView = new AdView(this, AdSize.SMART_BANNER, Utils.ADMOB_ID);
		layoutAd = (RelativeLayout)findViewById(R.id.adView);
		
		adView.setAdListener(new AdListener() {
			public void onReceiveAd(Ad ad){
				layoutAd.setVisibility(View.VISIBLE);
			}
			public void onFailedToReceiveAd(Ad ad, AdRequest.ErrorCode error){
				layoutAd.setVisibility(View.GONE);
			}
			public void onPresentScreen(Ad ad){}
			public void onDismissScreen(Ad ad){}
			public void onLeaveApplication(Ad ad){}
		});

		layoutAd.addView(adView);
		AdRequest adRequest = new AdRequest();
		if (Utils.TEST_MODE){
			adRequest.addTestDevice(AdRequest.TEST_EMULATOR);
			adRequest.addTestDevice(Utils.ADMOB_DEVICE_ID);

		}
		adView.loadAd(adRequest);
	}

	@Override
	public void onLeaveApplication(Ad ad) {}

	@Override
	public void onPresentScreen(Ad ad) {}

	@Override
	public void onReceiveAd(Ad ad) {
		Log.d("OK", "Received ad");
	    if (ad == interstitial) {
	      interstitial.show();
	    }
	}
}
