//Authors: Simon McDonnell

package com.zenfly.lancer;

import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.RadioButton;

public class Settings extends Activity {

	SharedPreferences prefs; //for accessing and modifying the settings
	RadioButton none; //radio button for choosing no ordering system
	RadioButton alphaAsc; //radio button for choosing an A-Z ordering system
	RadioButton alphaDesc; //radio button for choosing a Z-A ordering system
	RadioButton deadline; //radio button for choosing a deadline based ordering system
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        prefs = getSharedPreferences("com.zenfly.lancer", 0); //sets the SharedPreferences
        readSettings(); //sets the radio buttons to the last chosen or default values
    }
    
    public void readSettings()
    {
    	String order = prefs.getString("order", "none"); //holds the user's last used ordering system or the default if this is the first time used
    	none = (RadioButton) findViewById(R.id.order_none);
    	alphaAsc = (RadioButton) findViewById(R.id.order_alpha_asc);
    	alphaDesc = (RadioButton) findViewById(R.id.order_alpha_desc);
    	deadline = (RadioButton) findViewById(R.id.order_deadline);
        //sets the correct radio buttons for the order as per either the last chosen setting or the default
    	if(order.equals("none")) none.setChecked(true);
        else if(order.equals("deadline")) deadline.setChecked(true);
        else if(order.equals("alphaAsc")) alphaAsc.setChecked(true);
        else if(order.equals("alphaDesc")) alphaDesc.setChecked(true);
    }
    
    public void saveSettings(View v)
    {
    	SharedPreferences.Editor prefEditor = prefs.edit(); //sets up the editor
    	//saves the user's order settings
    	if(none.isChecked())prefEditor.putString("order", "none");
    	if(alphaAsc.isChecked())prefEditor.putString("order", "alphaAsc");
    	if(alphaDesc.isChecked())prefEditor.putString("order", "alphaDesc");
    	if(deadline.isChecked())prefEditor.putString("order", "deadline");
    	prefEditor.commit(); //save the settings
    	finish(); //return to the previous page
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_settings, menu);
        return true;
    }
}
