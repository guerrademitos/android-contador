package com.guerrademitos.contadorlite.apprater;

import com.guerrademitos.contadorlite.utils.Utils;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

@SuppressLint("ValidFragment")
public class RateDialogFragment2 extends DialogFragment{
	@Override
	public Dialog onCreateDialog(Bundle savedInstance){
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		
		builder.setTitle("Valora Guerra de Mitos - Contador");
		builder.setMessage("No queremos molestarte más, pero de verdad que tu apoyo nos será de mucha ayuda. ¿Nos valoras la aplicación? ¡Muchas gracias!")
			   .setPositiveButton("Venga, vale!", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       //Open store
                	   try {
                		    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+Utils.APP_NAME)));
                		} catch (android.content.ActivityNotFoundException anfe) {
                		    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id="+Utils.APP_NAME)));
                		}
                	   	AppRater.dontBotherAgain();
                   }
               })
               .setNegativeButton("No, gracias", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       // No molestar más
                	   AppRater.dontBotherAgain();
                       dialog.dismiss();
                   }
               });

		return builder.create();
	}
}
