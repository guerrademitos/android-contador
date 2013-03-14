package com.guerrademitos.contadorlite.apprater;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;

public class AppRater {
    
    private final static int LAUNCHES_UNTIL_FIRST_PROMPT = 3;
    private final static int LAUNCHES_UNTIL_LAST_PROMPT = 7;
    
    static SharedPreferences prefs;
    static SharedPreferences.Editor editor;
    
    public static void app_launched(Context mContext) {
        prefs = mContext.getSharedPreferences("apprater", 0);
        if (prefs.getBoolean("dontshowagain", false)) { return ; }
        
        editor = prefs.edit();
        
        // Increment launch counter
        long launch_count = prefs.getLong("launch_count", 0) + 1;
        editor.putLong("launch_count", launch_count);

        // Get date of first launch
        Long date_firstLaunch = prefs.getLong("date_firstlaunch", 0);
        if (date_firstLaunch == 0) {
            date_firstLaunch = System.currentTimeMillis();
            editor.putLong("date_firstlaunch", date_firstLaunch);
        }
        
        if (launch_count == LAUNCHES_UNTIL_FIRST_PROMPT) {
            	DialogFragment dialog = new RateDialogFragment1();
    			dialog.show(((FragmentActivity) mContext).getSupportFragmentManager(), "Rate me");
        }else if (launch_count >= LAUNCHES_UNTIL_LAST_PROMPT){
        	DialogFragment dialog = new RateDialogFragment2();
			dialog.show(((FragmentActivity) mContext).getSupportFragmentManager(), "Rate me");
        }
        
        editor.commit();
    }
    
    public static void dontBotherAgain(){
    	
 	   if (editor != null) {
            editor.putBoolean("dontshowagain", true);
            editor.commit();
        }
    }
    
}
