package com.guerrademitos.contador;

import com.google.ads.*;
import com.google.ads.AdRequest.ErrorCode;
import com.guerrademitos.contador.utils.Utils;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CounterActivity extends Activity implements AdListener {

	private TextView tv1, tv2, tv_timer;
	
	private boolean isTimerOn;
	protected long millisLeft;
	myCounter timer;
	
	int turn;
	ImageView iv1, iv2;
	
	private AdView adView;
	protected RelativeLayout layoutAd;
	private InterstitialAd interstitial;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Utils.setFullScreen(this);
		setContentView(R.layout.activity_counter);
		
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
