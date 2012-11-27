package com.zenfly.lancer;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class AddNewExpense extends Activity {
	
	final Context context = this;

	DatabaseHandler db;
	
	int job_id;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_expense);
        
        db = new DatabaseHandler(context);
        
        
    }
    
    
    
    // Saves the all the chosen entries as a new expense
    public void saveTask (View v) {
    	
    	Intent intent = new Intent(context, JobsOptions.class);
    	
    	// Get the EditText fields
    	
    	// Create a new expense using the users preferences and add it to the database

    	
    	intent.putExtra("job_id", job_id);    	
    	startActivity(intent);
    }


    
    /** OPTIONS MENU CODE DISABLED FOR NOW
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_add_new_expense, menu);
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

