package com.guerrademitos.contador;

import com.devspark.sidenavigation.ISideNavigationCallback;
import com.devspark.sidenavigation.SideNavigationView;
import com.guerrademitos.contador.gestures.*;
import com.guerrademitos.contador.utils.Utils;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ViewFlipper;

public class RulesActivity extends Activity implements SwipeInterface,ISideNavigationCallback {
	
	private ViewFlipper flipper;
	private SideNavigationView sideNavigationView;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Utils.setFullScreen(this);
		setContentView(R.layout.activity_rules);
		
		//Visor
		flipper = (ViewFlipper) findViewById(R.id.flipper);
		flipper.setInAnimation(getApplicationContext(),R.anim.push_left_in);
		flipper.setOutAnimation(getApplicationContext(),R.anim.push_left_out);
		
		ActivitySwipeDetector swipe = new ActivitySwipeDetector(this);
		RelativeLayout swipe_layout = (RelativeLayout) findViewById(R.id.rl_rules);
		swipe_layout.setOnTouchListener(swipe);		
		
		//Menu lateral
		sideNavigationView = (SideNavigationView) findViewById(R.id.side_navigation_rules);
	    sideNavigationView.setMenuItems(R.menu.menu_rules);
	    sideNavigationView.setMenuClickCallback(this);
	    sideNavigationView.setMode(SideNavigationView.Mode.LEFT);
	    
	    //Abrirlo con slide
	    RelativeLayout swipe_menu = (RelativeLayout)findViewById(R.id.rl_slide_index);
	    swipe_menu.setOnTouchListener(new ActivitySwipeDetector(new SwipeInterface() {
			@Override
			public void left2right(View v) {
				sideNavigationView.showMenu();
			}
			@Override
			public void bottom2top(View v) {}
			@Override
			public void right2left(View v) {}
			@Override
			public void top2bottom(View v) {}
		}){});
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_rules, menu);
		return true;
	}


	@Override
	public void bottom2top(View v) {
	}


	@Override
	public void left2right(View v) {
		flipper.setInAnimation(getApplicationContext(),R.anim.push_right_in);
		flipper.setOutAnimation(getApplicationContext(),R.anim.push_right_out);
		flipper.showPrevious();
	}

	@Override
	public void right2left(View v) {	
		flipper.setInAnimation(getApplicationContext(),R.anim.push_left_in);
		flipper.setOutAnimation(getApplicationContext(),R.anim.push_left_out);
		flipper.showNext();
	}


	@Override
	public void top2bottom(View v) {
	}


	@Override
	public void onSideNavigationItemClick(int itemId) {
		switch (itemId) {
		case R.id.side_intro:
			flipper.setDisplayedChild(0);
			break;
		case R.id.side_reglasbasicas:
			flipper.setDisplayedChild(1);
			break;

		default:
			sideNavigationView.hideMenu();
			break;
		}
		
	}
	
	@Override
	public void onBackPressed(){
		if(sideNavigationView.isShown())
			sideNavigationView.hideMenu();
		else
			super.onBackPressed();
		
	}
}
