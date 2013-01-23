package com.guerrademitos.contador;

import com.guerrademitos.contador.utils.Utils;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class CounterActivity extends Activity {

	private TextView tv1, tv2, tv_timer;
	
	private boolean isTimerOn;
	protected long millisLeft;
	myCounter timer;
	
	int turn;
	ImageView iv1, iv2;
	int VISIBLE = 0, GONE = 8;
		
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
		
		iv1.setVisibility(VISIBLE);
		iv2.setVisibility(GONE);
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
			iv1.setVisibility(GONE);
			iv2.setVisibility(VISIBLE);
			turn = 2;
		}else{
			iv1.setVisibility(VISIBLE);
			iv2.setVisibility(GONE);
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


}