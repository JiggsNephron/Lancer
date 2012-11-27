package com.zenfly.lancer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddNewExpense extends Activity {
	
	final Context context = this;
	
	EditText item_choice;
	EditText item_amount;

	DatabaseHandler db;
	
	String stitem_amount;
	int inttask_amount;
	
	int job_id;
	int task_id;

	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_expense);
        
        db = new DatabaseHandler(context);
        
        item_choice  = (EditText) findViewById(R.id.button_choose_item);
        item_amount  = (EditText)findViewById(R.id.edittext_number_of_items);
        
        // get intent content 
        job_id = getIntent().getIntExtra("job_id", 0);
        task_id = getIntent().getIntExtra("task_id", 0);
        
        
        
        
    }
    
    // Lets the user choose from their list of saved items (or add a new one)
    public void chooseLocation(View v) {    	
    	
    	Intent show_items = new Intent(context, ItemsList.class);
    	
    	// Preserve the already entered options

    	stitem_amount = item_amount.getText().toString();
    	
    	if (stitem_amount.equals("")) inttask_amount = 0;
    	else inttask_amount = Integer.parseInt(stitem_amount);
    	    	
    	// Forward the saved entries to the locations list activity
    	// which then sends it back to re-populate those fields in this activity
    	show_items.putExtra("item_amount", inttask_amount);
    	show_items.putExtra("job_id", job_id);
    	
    	// show the locations list
    	startActivity(show_items);	
    }        
    
    // Saves the all the chosen entries as a new expense
    public void saveTask (View v) {
    	
    	Intent intent = new Intent(context, JobsOptions.class);
    	
    	// Get the EditText fields
    	
    	// Create a new expense using the users preferences and add it to the database
    	
    	Expense new_expense = new Expense(job_id, task_id, 0, 0);
    	
    	
    	
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

