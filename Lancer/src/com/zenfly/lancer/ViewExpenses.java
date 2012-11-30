package com.zenfly.lancer;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ViewExpenses extends Activity {
	
	int expenseId;
	private DatabaseHandler db;
	Expense expense;
	//List<Expense> expense;
	Item item;
	Task task;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_expenses);
		//expense =db.getAllExpensesForTask(TaskId);
	
///*	
 		db = new DatabaseHandler(this);
		expenseId = getIntent().getExtras().getInt("expense"); 				// gets the task ID from the intent
		expense  =  db.getExpense(expenseId);									// gets the Task object from the database
		item     =  db.getItem(expense.getItem());
		task     =  db.getTask(expense.getTask());
		
		String ItemName = item.getName();								//extracts the name from Object
		float ItemPrice = item.getPrice();						//extracts the due Date from Object
		String linkedTask = task.getName();
		int Quantity = expense.getQuantity();
		//*/
		
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_view_expenses, menu);
		return true;
	}

}
