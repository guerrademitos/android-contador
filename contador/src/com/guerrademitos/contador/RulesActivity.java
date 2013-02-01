package com.guerrademitos.contador;

import com.guerrademitos.contador.utils.Utils;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.Menu;
import android.webkit.WebView;
import android.webkit.WebViewClient;

@SuppressLint("SetJavaScriptEnabled")
public class RulesActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Utils.setFullScreen(this);
		setContentView(R.layout.activity_rules);
		
		WebView wv = (WebView)findViewById(R.id.wv_rules); 
        wv.getSettings().setJavaScriptEnabled(true);
        
        wv.setWebViewClient(new WebViewClient(){
        	@Override
        	public boolean shouldOverrideUrlLoading(WebView view, String url){
        		return false;
        	}
        });
        
        String pdf = "http://joc.gegebe.com/wp-content/uploads/2013/01/REGLAS1.pdf";
        wv.loadUrl("http://docs.google.com/gview?embedded=true&url=" + pdf);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_rules, menu);
		return true;
	}

}
