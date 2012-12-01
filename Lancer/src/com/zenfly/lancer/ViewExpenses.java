package com.zenfly.lancer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

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
		//ItemQuantity.setText(locale_currency_format.format(Quantity));	
		TaskName.setText(linkedTask);											// sets the text view this data will always be set
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_view_expenses, menu);
		return true;
	}

	public void deleteExpense(View v)
	{
		// confirms the action with the Alert Dialog
		final AlertDialog.Builder builder=new AlertDialog.Builder(ViewExpenses.this);
		builder.setTitle("Delete " + task.getName());
		builder.setMessage("Are you sure you want to delete this Expense");
		builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface dialog, int id)
			{
				db.deleteTask(expenseId);
    			Toast.makeText(getApplicationContext(), "Deleted " + task.getName(), Toast.LENGTH_LONG).show();
    			Intent intent = new Intent(ViewExpenses.this, ExpensesList.class);
    			intent.putExtra("job_id", task.getJob());
    			startActivity(intent);
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
