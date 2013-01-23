package com.guerrademitos.contador.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class Utils {
	
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

}