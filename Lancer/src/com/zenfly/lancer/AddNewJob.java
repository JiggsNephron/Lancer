/**
 * Allows the user to add a new Job
 * 
 * Authors: Richard Cody, Simon McDonnell,
 * 
 */

package com.zenfly.lancer;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddNewJob extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_job);    	
    	 
    }
    
    public void saveJob(View v) {
    	
    	DatabaseHandler db = new DatabaseHandler(this);
    	Intent backToJobsList = new Intent(this, JobsList.class);
    	
        // Get the EditText
        EditText jobName = (EditText) findViewById(R.id.job_name);
        
        // get the data from each element and store it
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
    
    /** OPTIONS MENU CODE DISABLED FOR NOW
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_add_new_job, menu);
        return true;
    }

    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
	**/
}
