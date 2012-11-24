package com.zenfly.lancer;

import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.Menu;
import android.widget.RadioButton;

public class Settings extends Activity {

	SharedPreferences prefs;
	RadioButton none;
	RadioButton alphaAsc;
	RadioButton alphaDesc;
	RadioButton deadline;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        readSettings();
        prefs = getSharedPreferences("com.zenfly.lancer",0);
    }
    
    public void readSettings()
    {
    	String order = prefs.getString("order", "none");
    	none = (RadioButton) findViewById(R.id.order_none);
    	alphaAsc = (RadioButton) findViewById(R.id.order_alpha_asc);
    	alphaDesc = (RadioButton) findViewById(R.id.order_alpha_desc);
    	deadline = (RadioButton) findViewById(R.id.order_deadline);
        if(order.equals("none")) none.setChecked(true);
        else if(order.equals("deadline")) deadline.setChecked(true);
        else if(order.equals("alphaAsc")) alphaAsc.setChecked(true);
        else if(order.equals("alphaDesc")) alphaDesc.setChecked(true);
    }
    
    public void saveSettings()
    {
    	SharedPreferences.Editor prefEditor = prefs.edit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_settings, menu);
        return true;
    }
}
