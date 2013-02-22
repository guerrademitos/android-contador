package com.guerrademitos.contador.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class Utils {
	
	public static String APP_TITLE = "Guerra de Mitos - Contador";
	//public static String APP_NAME = "com.guerrademitos.contador";
	public static String APP_NAME = "com.rovio.angrybirds";
	
	private static Context context;
	
	public static void setContext(Context applicationContext) {
		context = applicationContext;
	}
	
	public static boolean isConnected() 
	{
	    ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
	    android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

	    if (wifi.isConnected()) {
	        return true;
	    } else if (mobile.isConnected()) {
	        return true;
	    }
	    return false;
	}
	
	public static void toast(String msg){
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}
	
	public static void setFullScreen(Context currContext) { 
		((Activity) currContext).requestWindowFeature(Window.FEATURE_NO_TITLE); 
		((Activity) currContext).getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
													  WindowManager.LayoutParams.FLAG_FULLSCREEN); 
	}
	
	public static void keepAlive(Context currContext){
		((Activity) currContext).getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}
	
	public static void restart(){
		Intent i = context.getPackageManager().getLaunchIntentForPackage( context.getPackageName() );
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		context.startActivity(i);
	}
	
	@SuppressWarnings("deprecation")
	public static void setSelectedBackground(View v){
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
		String bg_resource = sharedPref.getString("app_background","bg_fuego");
		
		String uri = "drawable/"+bg_resource;
	    int imageResource = context.getResources().getIdentifier(uri, null, context.getPackageName());
	    Drawable image = context.getResources().getDrawable(imageResource);
		
		v.setBackgroundDrawable(image);																								
	}

}
