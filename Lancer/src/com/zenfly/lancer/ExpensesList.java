package com.zenfly.lancer;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

public class ExpensesList extends ListActivity {
	
	public DatabaseHandler db;
	Job job;
	int taskId;
	List<Expense> expense = new ArrayList<Expense>();
	private NumberFormat locale_currency_format_totalCost;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE); // RC: this removed the black bar at the top of activities. 
        
        setContentView(R.layout.activity_expenses_list);
        locale_currency_format_totalCost= NumberFormat.getCurrencyInstance();
        db = new DatabaseHandler(this.getApplicationContext());
        float totalCostValue;
        int jobId = getIntent().getIntExtra("job_id", 0);
        taskId = getIntent().getIntExtra("task_id", 0);
        Log.v("Task is ", ""+taskId);
        if(taskId != 0){
        	expense = db.getAllExpensesForTask(taskId);
        	totalCostValue = db.getTotalCostForTask(taskId);
        }
        else {
        	totalCostValue = db.getTotalCostForJob(jobId);					//gets the total of all expenses for the job
        	expense = db.getAllExpensesForJob(jobId);								//makes a list of jobs to send to the list View
        }
        job = db.getJob(jobId);	
        setListAdapter(new ExpensesAdapter(this, expense)); 						//starts the list View
        TextView totalCost = (TextView) findViewById(R.id.TotalCost);				//prepares to access textView
        totalCost.setText(locale_currency_format_totalCost.format(totalCostValue));							// sets the text view this data
    }
  
    public void addNewExpense(View v) {
       	Intent intent = new Intent(ExpensesList.this, AddNewExpense.class);
       	intent.putExtra("job_id", getIntent().getIntExtra("job_id", 0));
       	intent.putExtra("task_id", getIntent().getIntExtra("task_id", 0));
    	startActivity(intent);	
   }
    
    @Override
    public void onBackPressed() {
    	Intent intent;
    	
    	if (getIntent().getIntExtra("from_invoice", 0) == 1) {
    		intent = new Intent(ExpensesList.this, ViewJobInvoice.class);
    	} else if (getIntent().getIntExtra("task_id", 0) != 0) {    		
    		intent = new Intent(ExpensesList.this, ViewTask.class);
    		intent.putExtra("task", getIntent().getIntExtra("task_id", 0));    		
    	} else {
    		intent = new Intent(ExpensesList.this, JobsOptions.class);
    	}
    	
    	intent.putExtra("job_id", getIntent().getIntExtra("job_id", 0));
		startActivity(intent);
    }       
        
	@Override
	  public void onListItemClick(ListView parent, View v, int position, long id)
	  {	 
	  	Intent intent = new Intent(ExpensesList.this, ViewExpenses.class);
	  	int expenseId = expense.get(position).getId();
	  	intent.putExtra("expense", expenseId); 											//sends the expense name
	  	intent.putExtra("job_id", getIntent().getIntExtra("job_id", 0)); 				//sends the job name
	  	intent.putExtra("task_id", getIntent().getIntExtra("task_id", 0));
	    startActivity(intent);
	  }
}
