/**
 * Allows the user to add a new Job
 * 
 * Authors: Richard Cody,
 * 
 */

package com.zenfly.lancer;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

public class AddNewJob extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_new_job);    	
    	 
    }
    
    public void saveJob(View v) {
    	
    	DatabaseHandler db = new DatabaseHandler(this);
    	Intent backToJobsList = new Intent(this, JobsList.class);
    	
        // Get the EditText
        EditText jobName = (EditText) findViewById(R.id.job_name);
        
        // Get the data from the EditText and store it
    	String stjobName = jobName.getText().toString();
    	stjobName = stjobName.trim(); // removes spaces on the ends of the string
    	
    	if(!stjobName.equals(""))
    	{
	    	// add new job based on user entered data    		
	    	db.addJob(new Job(stjobName));
	    	Toast.makeText(getApplicationContext(), "Job: " + stjobName + " added.", Toast.LENGTH_SHORT).show();
	    	Log.v("Name:", stjobName);
	    	startActivity(backToJobsList);
    	}
    	else {
    		Toast.makeText(getApplicationContext(), "You must provide a name for your job", Toast.LENGTH_LONG).show();
    	}
    	
    }
    
    // Override back button to create a more consistent experience
    @Override
    public void onBackPressed() {
    	Intent intent = new Intent(AddNewJob.this, JobsList.class);
    	startActivity(intent);
    	return;
    }      
}
