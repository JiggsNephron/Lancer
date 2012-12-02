/**
 * Allows the user to edit an expense
 * 
 * Authors: Richard Cody, 
 * 
 */

package com.zenfly.lancer;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class EditExpense extends Activity {
	
	final Context context = this;
	
	EditText et_item_choice;
	EditText et_item_amount;
	EditText et_item_total;
	Spinner sp_assign_to_task;

	DatabaseHandler db;
	NumberFormat locale_currency_format;
		
	String stitem_amount;
	int intitem_amount;
	float total;
	float item_cost;
	
	int job_id;
	int item_id;
	int task_id;
	int expense_id;
	
	int counter = 0;
	int set_spinner_to = 0;
	SparseIntArray spinnerandtaskMap;
	
	Task chosen_task;
	Item item;
	Expense current_expense;
	
	List<Task> all_tasks = new ArrayList<Task>();	

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_expense);
        
        db = new DatabaseHandler(context);
        locale_currency_format = NumberFormat.getCurrencyInstance();
        
        // get intent content      
        expense_id = getIntent().getIntExtra("expense_id", 0);        
        
        current_expense = db.getExpense(expense_id);
        
        task_id = current_expense.getTask();
        job_id = current_expense.getJob();
        
        
        // get the item id (available if an item as been chosen)
        if(getIntent().getIntExtra("item_id", 0) != 0) {
        	item_id = getIntent().getIntExtra("item_id", 0);
        	item = db.getItem(item_id);
        	et_item_choice.setText(item.getName() + ": " + locale_currency_format.format(item.getPrice()));
        } else {
        	item = db.getItem(current_expense.getItem());
        	item_id = item.getId();
        }
        
        // Get the view elements
        et_item_choice  = (EditText) findViewById(R.id.button_choose_item);
        et_item_amount  = (EditText)findViewById(R.id.edittext_number_of_items);
        et_item_total = (EditText)findViewById(R.id.edittext_total_cost_of_items);
        sp_assign_to_task  = (Spinner)findViewById(R.id.expense_to_task);
        
        et_item_choice.setText(item.getName() + ": " + locale_currency_format.format(item.getPrice()));
        
        intitem_amount = current_expense.getQuantity();        
        
        // set the Quantity EditText to show the intent amount
        if(getIntent().getIntExtra("item_amount", 0) != 0) {
        	intitem_amount = getIntent().getIntExtra("item_amount", 0);
        	et_item_amount.setText(Integer.toString(intitem_amount));
        } else et_item_amount.setText(Integer.toString(intitem_amount));
        
        spinnerandtaskMap = new SparseIntArray();
        
        // get a list of all tasks to be used for populating the spinner
        all_tasks = db.getAllTasksForJob(job_id);
        // create an ArrayAdapter to fill with task names
        ArrayAdapter <CharSequence> adapter = new ArrayAdapter <CharSequence> (this, android.R.layout.simple_spinner_item );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // add one default task choice (no task chosen)
        adapter.add("Not assigned to a task");
        spinnerandtaskMap.put(counter, 0);
        // go through the list of tasks, and for each one, get its name and add it to the adapter array
        for (Task task: all_tasks) {
        	counter++;
        	spinnerandtaskMap.put(counter, task.getId());
        	adapter.add(task.getName());
        	if (task.getId() == task_id) {
        		set_spinner_to = counter;
        	}
        }        
        
        if(getIntent().getIntExtra("task_spinner", 0) != 0) {
        	set_spinner_to = getIntent().getIntExtra("task_spinner", 0);
        }        
        // set the spinner to use the array contents       
        sp_assign_to_task.setAdapter(adapter);
        // set the spinner to the position of the already set task
        sp_assign_to_task.setSelection(set_spinner_to);
        
        
        // if an item has already been chosen, run this block
        if (item != null) {
        	
        	// get the user entered quantity choice and convert to integer
        	stitem_amount = et_item_amount.getText().toString();
	    	if (stitem_amount.equals("")) intitem_amount = 0;
	    	else intitem_amount = Integer.parseInt(stitem_amount);
        	
	    	// get the cost of one item
	    	item_cost = item.getPrice();
	    	
	    	// if the user has entered a quantity, use it to calculate total expense
	    	if (intitem_amount != 0){	
	    		total = intitem_amount * item_cost;
	    		et_item_total.setText(locale_currency_format.format(total));
	    	}
	    	
	    	// also calculate the total expense in real time if user changes quantity
            et_item_amount.addTextChangedListener(new TextWatcher() {

    			public void afterTextChanged(Editable s) {
    				    				
    			}
    			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    				
    			}
    			public void onTextChanged(CharSequence s, int start, int before,
    					int count) {
    				// get the user entered quantity choice in real time and convert to integer
    				stitem_amount = et_item_amount.getText().toString();
    		    	if (stitem_amount.equals("")) intitem_amount = 0;
    		    	else intitem_amount = Integer.parseInt(stitem_amount);
    		    	// get the cost of one item
    		    	item_cost = item.getPrice();
    		    	// if the user has entered a quantity, use it to calculate total expense
    		    	if (intitem_amount != 0){	
    		    		total = intitem_amount * item_cost;
    		    		et_item_total.setText(locale_currency_format.format(total));
    		    	}
    			}            	
            }); 
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
    	show_items.putExtra("task_id", getIntent().getIntExtra("task_id", 0));
    	show_items.putExtra("expense_id", getIntent().getIntExtra("expense_id", 0));    	
    	show_items.putExtra("task_spinner", set_spinner_to);
    	
    	// show the locations list
    	startActivity(show_items);	
    }      
    
    public void editExpense (View v) {
    	
    	Intent back_to_expensesList = new Intent(context, ViewExpenses.class);

    	// get the ID of the task chosen by the user in the spinner by spinner's position
    	task_id = spinnerandtaskMap.get(sp_assign_to_task.getSelectedItemPosition());
    	
    	// get the quantity of items as a string and convert it to an integer
    	stitem_amount = et_item_amount.getText().toString();
    	if (stitem_amount.equals("")) intitem_amount = 0;
    	else intitem_amount = Integer.parseInt(stitem_amount);
    	
    	// if the user has chosen an item and given a quantity, the expense is added to the database
    	if (item_id != 0 && intitem_amount != 0) {
    		
    		current_expense.setItem(item_id);
    		current_expense.setQuantity(intitem_amount);
    		current_expense.setTask(task_id);
    	
    		db.updateExpense(current_expense);    	
    		
    		float total_cost = intitem_amount*item.getPrice();
    		
    		Toast.makeText(getApplicationContext(), 
    				"Added Expense: " 
    						+ item.getName() 
    						+ " x " 
    						+ intitem_amount 
    						+ ". " 
    						+ "The total of this expense is " 
    						+ locale_currency_format.format(total_cost) 
    						+ ". ", 
    				Toast.LENGTH_LONG).show();
    	
    		back_to_expensesList.putExtra("job_id", job_id);
    		back_to_expensesList.putExtra("expense", expense_id);
    		startActivity(back_to_expensesList);
    		
    	} else Toast.makeText(getApplicationContext(), "Please choose an item and quantity of items", Toast.LENGTH_SHORT).show();
    	
    }
    
    public void deleteExpense (View v) {
    	
    	// confirms the action with the Alert Dialog
    	final AlertDialog.Builder builder=new AlertDialog.Builder(EditExpense.this);
    	builder.setTitle("Delete " + current_expense.getItem() + " x " + current_expense.getQuantity());
    	builder.setMessage("Are you sure you want to delete this Expense?");
    			
    	builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
    		
    		public void onClick(DialogInterface dialog, int id)
    			{
    					db.deleteExpense(expense_id);
    	    			Toast.makeText(getApplicationContext(), "Deleted Expense", Toast.LENGTH_LONG).show();
    	    			Intent back_to_expenses = new Intent(context, ExpensesList.class);
    	    			back_to_expenses.putExtra("task_id", getIntent().getIntExtra("task_id", 0));
    	    			back_to_expenses.putExtra("job_id", job_id);
    	    	    	startActivity(back_to_expenses);
    			}
    	});
    	      
    	builder.setNegativeButton("No", new DialogInterface.OnClickListener()
    		{
    			public void onClick(DialogInterface dialog, int id)
    			{
    	 	   	     dialog.cancel();
    			}
    	});    	      
    	builder.create().show();
    }        
}
