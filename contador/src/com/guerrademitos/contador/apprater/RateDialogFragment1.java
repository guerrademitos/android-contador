package com.guerrademitos.contador.apprater;

import com.guerrademitos.contador.utils.Utils;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

@SuppressLint("ValidFragment")
public class RateDialogFragment1 extends DialogFragment{
	@Override
	public Dialog onCreateDialog(Bundle savedInstance){
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		
		builder.setTitle("Valora Guerra de Mitos - Contador");
		builder.setMessage("¿Te gusta Guerra de Mitos? Ayúdanos valorando la aplicación. No te olvides de opinar :) ¡Gracias por tu apoyo!")
			   .setPositiveButton("Valorar!", new DialogInterface.OnClickListener() {
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
			   .setNeutralButton("Quizá luego", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       // User cancelled the dialog
                	   dialog.dismiss();
                   }
               })
               .setNegativeButton("Nunca", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       // No molestar más
                	   AppRater.dontBotherAgain();
                       dialog.dismiss();
                   }
               });

		return builder.create();
	}
}
