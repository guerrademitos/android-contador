package com.guerrademitos.contador;

import com.guerrademitos.contador.utils.Utils;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CounterActivity extends FragmentActivity {

	private TextView tv1, tv2, tv_timer;

	private boolean isTimerOn, wasTimerOn;
	protected long millisLeft;
	myCounter timer;

	int turn;
	ImageView iv1, iv2;

	RelativeLayout rl = null;
	TextView rltv = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Utils.setFullScreen(this);
		setContentView(R.layout.activity_counter);

		// Que no se apague la pantalla
		Utils.keepAlive(this);

		Utils.setSelectedBackground(findViewById(R.id.rl_bg_counter));

		tv1 = (TextView) findViewById(R.id.tv_player1);
		tv2 = (TextView) findViewById(R.id.tv_player2);
		tv1.setText("20");
		tv2.setText("20");

		tv_timer = (TextView) findViewById(R.id.tv_timer);
		isTimerOn = false;
		wasTimerOn = false;
		millisLeft = timeToMillis(40, 00);
		timer = new myCounter(millisLeft, 1000);

		turn = 1;
		iv1 = (ImageView) findViewById(R.id.iv_player1);
		iv2 = (ImageView) findViewById(R.id.iv_player2);

		iv1.setVisibility(View.VISIBLE);
		iv2.setVisibility(View.GONE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_counter, menu);
		return true;
	}

	public long timeToMillis(int min, int sec) {
		long time = 0;

		time += min * 60000;
		time += sec * 1000;

		return time;
	}

	public String millisToTime(long millis) {
		String time = "";

		int min = (int) ((millis / 1000) / 60);
		int sec = (int) ((millis / 1000) % 60);

		time += String.format("%02d", min);
		time += ":";
		time += String.format("%02d", sec);

		return time;
	}

	public void player1Up(View v) {
		int score = Integer.parseInt(tv1.getText().toString());
		tv1.setText(String.valueOf(++score));
	}

	public void player1Down(View v) {
		int score = Integer.parseInt(tv1.getText().toString());
		if (score > 0)
			tv1.setText(String.valueOf(--score));
	}

	public void player2Up(View v) {
		int score = Integer.parseInt(tv2.getText().toString());
		tv2.setText(String.valueOf(++score));
	}

	public void player2Down(View v) {
		int score = Integer.parseInt(tv2.getText().toString());
		if (score > 0)
			tv2.setText(String.valueOf(--score));
	}

	public void changeMainPlayer(View v) {
		if (turn == 1) {
			iv1.setVisibility(View.GONE);
			iv2.setVisibility(View.VISIBLE);
			turn = 2;
		} else {
			iv1.setVisibility(View.VISIBLE);
			iv2.setVisibility(View.GONE);
			turn = 1;
		}
	}

	public void timerClick(View v) {
		if (!isTimerOn) {
			isTimerOn = true;
			timer = new myCounter(millisLeft, 1000);
			timer.start();
		} else {
			isTimerOn = false;
			timer.cancel();
		}
	}

	public void reset(View v) {
		iv1.setVisibility(View.VISIBLE);
		iv2.setVisibility(View.GONE);
		tv1.setText("20");
		tv2.setText("20");
		RelativeLayout rl1 = (RelativeLayout) findViewById(R.id.c_bg_player1);
		RelativeLayout rl2 = (RelativeLayout) findViewById(R.id.c_bg_player2);

		rl1.setBackgroundResource(R.color.white_overlay);
		rl2.setBackgroundResource(R.color.white_overlay);

		timer.cancel();
		millisLeft = timeToMillis(40, 00);
		tv_timer.setText("40:00");
		isTimerOn = false;
		wasTimerOn = false;

		turn = 1;
	}

	public void showInfoOverlay(View v) {
		wasTimerOn = isTimerOn;
		if (isTimerOn)
			timerClick(v);
		findViewById(R.id.rules_overlay).setVisibility(View.VISIBLE);
	}

	public void hideInfoOverlay(View v) {
		if (wasTimerOn) {
			timerClick(v);
		}
		findViewById(R.id.rules_overlay).setVisibility(View.GONE);
	}

	public class myCounter extends CountDownTimer {

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
	public void changeBgPlayer(View v) {

		if (v.getId() == R.id.rl_prefs_player1) {
			rl = (RelativeLayout) findViewById(R.id.c_bg_player1);
			rltv = (TextView) findViewById(R.id.tv_player1);
		} else if (v.getId() == R.id.rl_prefs_player2) {
			rl = (RelativeLayout) findViewById(R.id.c_bg_player2);
			rltv = (TextView) findViewById(R.id.tv_player2);
		} else
			return;

		DialogFragment dialog = new SelectBackgroundDialogFragment();
		dialog.show(getSupportFragmentManager(), "Select Background");
	}

	@SuppressLint("ValidFragment")
	public class SelectBackgroundDialogFragment extends DialogFragment {
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the Builder class for convenient dialog construction
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setTitle("Selecciona tu panteón.").setItems(
					R.array.pantheons, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							// The 'which' argument contains the index position
							// of the selected item
							switch (which) {
							case 0: // Takanamahara
								rl.setBackgroundResource(R.drawable.c_bg_takamagahara);
								rltv.setText("18");
								break;
							case 1: // Yomi
								rl.setBackgroundResource(R.drawable.c_bg_yomi);
								rltv.setText("20");
								break;
							case 2: // Niflheim
								rl.setBackgroundResource(R.drawable.c_bg_niflheim);
								rltv.setText("19");
								break;
							case 3: // Asgard
								rl.setBackgroundResource(R.drawable.c_bg_asgard);
								rltv.setText("18");
								break;
							}
						}
					});
			// Create the AlertDialog object and return it
			return builder.create();
		}
	}

}
