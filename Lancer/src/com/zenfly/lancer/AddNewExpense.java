package com.zenfly.lancer;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddNewExpense extends Activity {
	
	final Context context = this;
	
	EditText et_item_choice;
	EditText et_item_amount;
	Spinner sp_assign_to_task;

	DatabaseHandler db;
	NumberFormat locale_currency_format;
		
	String stitem_amount;
	int intitem_amount;
	
	int job_id;
	int item_id;
	int task_id;
	int spinner_position;
	
	Task chosen_task;
	Item item;
	
	List<Task> all_tasks = new ArrayList<Task>();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_new_expense);

        db = new DatabaseHandler(context);
        locale_currency_format = NumberFormat.getCurrencyInstance();
        
        // get intent content 
        job_id = getIntent().getIntExtra("job_id", 0);					// The ID of the Job this expense is being added under
        spinner_position = getIntent().getIntExtra("task_spinner", 0);  // The spinner position (to restore it to the user's choice after choosing an item)
        intitem_amount = getIntent().getIntExtra("item_amount", 0);
              
        // Get the view elements
        et_item_choice  = (EditText) findViewById(R.id.button_choose_item);
        et_item_amount  = (EditText)findViewById(R.id.edittext_number_of_items);        
        sp_assign_to_task  = (Spinner)findViewById(R.id.expense_to_task);
        
        // get a list of all tasks to be used for populating the spinner
        all_tasks = db.getAllTasksForJob(job_id);
        // create an ArrayAdapter to fill with task names
        ArrayAdapter <CharSequence> adapter = new ArrayAdapter <CharSequence> (this, android.R.layout.simple_spinner_item );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // add one default task choice (no task chosen)
        adapter.add("Not assigned to a task");
        // go through the list of tasks, and for each one, get its name and add it to the adapter array
        for (Task task: all_tasks) {
        	adapter.add(task.getName());
        }      
        // set the spinner to use the array contents       
        sp_assign_to_task.setAdapter(adapter);
        // set the spinner to the position it was at before choosing an item
        sp_assign_to_task.setSelection(spinner_position, true);

        // set the Quantity EditText to show the intent amount
        if(getIntent().getIntExtra("item_amount", 0) != 0) {
        	intitem_amount = getIntent().getIntExtra("item_amount", 0);
        	et_item_amount.setText(Integer.toString(intitem_amount));
        } else et_item_amount.setHint("0");
        
        // get the item id (available if an item as been chosen)
        if(getIntent().getIntExtra("item_id", 0) != 0) {
        	item_id = getIntent().getIntExtra("item_id", 0);
        	item = db.getItem(item_id);
        	et_item_choice.setText(item.getName());
        }
 
    }
    
    // Lets the user choose from their list of saved items (or add a new one)
    public void chooseItems(View v) {    	
    	
    	Intent show_items = new Intent(context, ItemsList.class);
    	
    	// Preserve the already entered options
    	stitem_amount = et_item_amount.getText().toString();    	
    	if (stitem_amount.equals("")) intitem_amount = 0;
    	else intitem_amount = Integer.parseInt(stitem_amount);
    	    	
    	// Forward the saved entries to the items list activity
    	// which then sends it back to re-populate those fields in this activity
    	show_items.putExtra("item_amount", intitem_amount);
    	show_items.putExtra("job_id", job_id);
    	show_items.putExtra("task_spinner", sp_assign_to_task.getSelectedItemPosition());
    	
    	// show the locations list
    	startActivity(show_items);	
    }        
    
    // Saves the all the chosen entries as a new expense
    public void saveExpense (View v) {
    	
    	Intent intent = new Intent(context, JobsOptions.class);

    	// get the ID of the task chosen by the user in the spinner by spinner's position
    	// if no task was chosen, 0 is used
    	if ((sp_assign_to_task.getSelectedItemPosition()-1) >= 1) {
    		chosen_task = all_tasks.get(sp_assign_to_task.getSelectedItemPosition()-1);
    		task_id = chosen_task.getId();
    	} else task_id = 0;
    	
    	stitem_amount = et_item_amount.getText().toString();
    	if (stitem_amount.equals("")) intitem_amount = 0;
    	else intitem_amount = Integer.parseInt(stitem_amount);
    	
    	// if the user has chosen an item and given a quantity, the expense is added to the database
    	if (item_id != 0 && intitem_amount != 0) {
    		
    		Expense new_expense = new Expense(job_id, task_id, item_id, intitem_amount);   	   	
    	
    		db.addExpense(new_expense);    	

    		Toast.makeText(getApplicationContext(), "Added Expense: " + item.getName() + " x " + intitem_amount + ". " + "The total of this expense is " + locale_currency_format.format((intitem_amount*item.getPrice()) + ". "), Toast.LENGTH_LONG).show();
    	
    		intent.putExtra("job_id", job_id);
    		startActivity(intent);
    		
    	} else Toast.makeText(getApplicationContext(), "Please choose an item and quantity of items", Toast.LENGTH_SHORT).show();
    	
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

