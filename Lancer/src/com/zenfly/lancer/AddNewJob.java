package com.zenfly.lancer;

import com.example.lancer.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.support.v4.app.NavUtils;

public class AddNewJob extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_job);    	
    	
    }
    
    // on pressing Back, go back to main
    public void saveJob(View v) {
    	
    	DatabaseHandler db = new DatabaseHandler(this);
    	Intent backToJobsList = new Intent(this, JobsList.class);
    	
        // Getting each EditText
        EditText jobName = (EditText) findViewById(R.id.job_name);
        EditText jobLocation = (EditText) findViewById(R.id.job_location);
        EditText jobDeadline = (EditText) findViewById(R.id.job_deadline); 	// not yet used
        
        // get the data from each element and store it
    	String stjobName = jobName.getText().toString();
    	String stjobLocation = jobLocation.getText().toString();
    	String stjobDeadline = jobDeadline.getText().toString(); 			// not yet used
    	
    	// add new job based on user entered data    		
    	db.addJob(new Job(stjobName, stjobLocation)); 						// TODO: need to be able to add deadline
    	
    	startActivity(backToJobsList);
    	
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
