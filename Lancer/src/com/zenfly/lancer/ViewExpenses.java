package com.zenfly.lancer;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.Window;
import android.widget.TextView;

public class ViewExpenses extends Activity {
	
	int expenseId;
	int jobId;
	private DatabaseHandler db;
	Expense expense;
	Item item;
	Task task;
	Job job;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE); // RC: this removed the black bar at the top of activities. 
		setContentView(R.layout.activity_view_expenses);

 		db = new DatabaseHandler(this);
		expenseId = getIntent().getExtras().getInt("expense"); 				// gets the task ID from the intent
		jobId = getIntent().getExtras().getInt("job_id"); 					// gets the task ID from the intent
		expense   =  db.getExpense(expenseId);								// gets the Task object from the database
		item      =  db.getItem(expense.getItem());
		
		int TaskId    =  expense.getTask();
		job = db.getJob(jobId);	
        String JobName = job.getClient();
		String linkedTask = "";
		String ItemName = item.getName();									//extracts the name from Object
		float ItemPrice = item.getPrice();									//extracts the due Date from Object
		int Quantity = expense.getQuantity();
		
		Log.v("View Expenses", "expense.getTask() =" + expense.getTask() + "~END");
		if(TaskId != 0)
		{
			task     =  db.getTask(TaskId);  //optional 
			linkedTask = task.getName();
		}
		
		float TotalCost = Quantity * ItemPrice;
		
		String QuantityString = Integer.toString(Quantity);
		String PriceString = String.valueOf(ItemPrice);
		String TotalCostString = String.valueOf(TotalCost);
		
		

		TextView JobNameTitle = (TextView) findViewById(R.id.job_name);			//prepares to access textView
		TextView TaskNameTitle = (TextView) findViewById(R.id.job_option);		//prepares to access textView	
		JobNameTitle.setText(JobName);										// sets the text view this data will always be set
		TaskNameTitle.setText("View Expense");										// sets the text view this data will always be set
			
		TextView ExpenseItemDisplay = (TextView) findViewById(R.id.ExpenseItemDisplay);		//prepares to access textView
		TextView ExpenseCostDisplay = (TextView) findViewById(R.id.ExpenseCostDisplay);		//prepares to access textView
		TextView IndividualCost = (TextView) findViewById(R.id.IndividualCost);				//prepares to access textView
		TextView ItemQuantity = (TextView) findViewById(R.id.ItemQuantity);					//prepares to access textView
		TextView TaskName = (TextView) findViewById(R.id.TaskName);							//prepares to access textView
		
	
		ExpenseItemDisplay.setText(ItemName);									// sets the text view this data will always be set
		ExpenseCostDisplay.setText(TotalCostString);							// sets the text view this data will always be set
		IndividualCost.setText(PriceString);									// sets the text view this data will always be set
		ItemQuantity.setText(QuantityString);									// sets the text view this data will always be set
		TaskName.setText(linkedTask);											// sets the text view this data will always be set
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_view_expenses, menu);
		return true;
	}

}
