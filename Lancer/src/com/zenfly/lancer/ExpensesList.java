package com.zenfly.lancer;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;

public class ExpensesList extends ListActivity {
	
	public DatabaseHandler db;
	List<Expense> expense = new ArrayList<Expense>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses_list);
        
        db = new DatabaseHandler(this.getApplicationContext());
        
        //SMcD: just adding this to see if it grabs jobs from the DB. And it does. Happy days
        int thisJob = getIntent().getIntExtra("job_id", 0);
       // expense = db.getAllExpensesForJob(thisJob); //makes a list of jobs to send to the list View
        
        setListAdapter(new ExpensesAdapter(this, expense)); //starts the list View
    }

    
    public void addNewExpense(View v) {
       	Intent intent = new Intent(ExpensesList.this, AddNewTask.class);
       	intent.putExtra("job_id", getIntent().getIntExtra("job_id", 0));
    	startActivity(intent);	
   }
    
    
	@Override
	  public void onListItemClick(ListView parent, View v, int position, long id)
	  {	 
	  	Intent intent = new Intent(ExpensesList.this, ViewExpenses.class);
	  	int expenseId = expense.get(position).getId();
	  	intent.putExtra("task", expenseId); //sends the job name
	    startActivity(intent);
	  }

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_expences_list, menu);
        return true;
    }
}
