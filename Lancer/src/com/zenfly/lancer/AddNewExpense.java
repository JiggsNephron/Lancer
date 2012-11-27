package com.zenfly.lancer;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class AddNewExpense extends Activity {
	
	final Context context = this;
	
	EditText et_item_choice;
	EditText et_item_amount;
	Spinner sp_assign_to_task;

	DatabaseHandler db;
	
	String stitem_amount;
	int inttask_amount;
	
	int job_id;
	int task_id;
	
	List<Task> all_tasks = new ArrayList<Task>();

	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_expense);
        
        db = new DatabaseHandler(context);
        
        et_item_choice  = (EditText) findViewById(R.id.button_choose_item);
        et_item_amount  = (EditText)findViewById(R.id.edittext_number_of_items);
        sp_assign_to_task  = (Spinner)findViewById(R.id.expense_to_task);
        
        all_tasks = db.getAllTasksForJob(job_id);

        ArrayAdapter <CharSequence> adapter = new ArrayAdapter <CharSequence> (this, android.R.layout.simple_spinner_item );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        		
        		
        // get intent content 
        job_id = getIntent().getIntExtra("job_id", 0);
        task_id = getIntent().getIntExtra("task_id", 0);
        
        
        
        
    }
    
    // Lets the user choose from their list of saved items (or add a new one)
    public void chooseItems(View v) {    	
    	
    	Intent show_items = new Intent(context, ItemsList.class);
    	
    	// Preserve the already entered options

    	stitem_amount = et_item_amount.getText().toString();
    	
    	if (stitem_amount.equals("")) inttask_amount = 0;
    	else inttask_amount = Integer.parseInt(stitem_amount);
    	    	
    	// Forward the saved entries to the items list activity
    	// which then sends it back to re-populate those fields in this activity
    	show_items.putExtra("item_amount", inttask_amount);
    	show_items.putExtra("job_id", job_id);
    	show_items.putExtra("task_id", task_id);
    	
    	// show the locations list
    	startActivity(show_items);	
    }        
    
    // Saves the all the chosen entries as a new expense
    public void saveExpense (View v) {
    	
    	Intent intent = new Intent(context, JobsOptions.class);
    	
    	// Get the EditText fields
    	
    	// Create a new expense using the users preferences and add it to the database
    	
    	//Expense new_expense = new Expense(job_id, task_id, 0, 0);
    	
    	//db.addExpense(new_expense);
    	
    	intent.putExtra("job_id", job_id);
    	intent.putExtra("task_id", task_id);
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

