package com.guerrademitos.contador.utils;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;

public class AppRater {
    
    private final static int DAYS_UNTIL_PROMPT = 3;
    private final static int LAUNCHES_UNTIL_PROMPT = 7;
    
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
        
        // Wait at least n days before opening
        if (launch_count >= LAUNCHES_UNTIL_PROMPT) {
            if (System.currentTimeMillis() >= date_firstLaunch + 
                    (DAYS_UNTIL_PROMPT * 24 * 60 * 60 * 1000)) {
            	DialogFragment dialog = new RateDialogFragment1();
    			dialog.show(((FragmentActivity) mContext).getSupportFragmentManager(), "Select Background");
            }
        }
        
        editor.commit();
    }
    
    public static void dontBotherAgain(){
    	
 	   if (editor != null) {
            editor.putBoolean("dontshowagain", true);
            editor.commit();
        }
    }
    
    @SuppressLint("ValidFragment")
	public class RateDialogFragment1 extends DialogFragment{
		@Override
		public Dialog onCreateDialog(Bundle savedInstance){
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

			builder.setMessage("If you enjoy using " + Utils.APP_TITLE + ", please take a moment to rate it. Thanks for your support!")
				   .setPositiveButton("Valorar!", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                       //Open store
	                	   try {
	                		    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+Utils.APP_NAME)));
	                		} catch (android.content.ActivityNotFoundException anfe) {
	                		    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id="+Utils.APP_NAME)));
	                		}

	                   }
	               })
				   .setNeutralButton("Quiz‡ luego", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                       // User cancelled the dialog
	                	   dialog.dismiss();
	                   }
	               })
	               .setNegativeButton("Nunca", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                       // No molestar m‡s
	                	   AppRater.dontBotherAgain();
	                       dialog.dismiss();
	                   }
	               });

			return builder.create();
		}
	}
}

