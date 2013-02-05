package com.guerrademitos.contador;

import com.guerrademitos.contador.gestures.*;
import com.guerrademitos.contador.utils.Utils;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class RulesActivity extends Activity implements SwipeInterface {
	
	private int current_page = 1;
	private int max_page = 16;
	private ImageView image;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Utils.setFullScreen(this);
		setContentView(R.layout.activity_rules);
		
		image = (ImageView) findViewById(R.id.iv_rules);
		
		ActivitySwipeDetector swipe = new ActivitySwipeDetector(this);
		RelativeLayout swipe_layout = (RelativeLayout) findViewById(R.id.rl_rules);
		swipe_layout.setOnTouchListener(swipe);
			
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_rules, menu);
		return true;
	}


	@Override
	public void bottom2top(View v) {
		nextPage();
	}


	@Override
	public void left2right(View v) {
		prevPage();
	}

	@Override
	public void right2left(View v) {	
		nextPage();
	}


	@Override
	public void top2bottom(View v) {
		prevPage();
	}
	
	private void prevPage() {
		
		current_page--;
		
		if(current_page < 1)
			current_page = max_page;
		
		updateImage();
	}

	private void nextPage() {
		
		current_page++;
		
		if(current_page > max_page)
			current_page = 1;
		
		updateImage();
	}
	
	private void updateImage(){
		
		String uri = "@drawable/reglas"+current_page;
		int imageResource = getResources().getIdentifier(uri, null, getPackageName());	
		Drawable res = getResources().getDrawable(imageResource);
		image.setImageDrawable(res);
		
	}
	

}
