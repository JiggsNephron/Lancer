package com.zenfly.lancer;

import java.util.ArrayList;
import java.util.List;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

public class ExpensesList extends ListActivity {
	
	public DatabaseHandler db;
	Job job;
	List<Expense> expense = new ArrayList<Expense>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE); // RC: this removed the black bar at the top of activities.
        setContentView(R.layout.activity_expenses_list);
       // Log.v("Expenses list", "test 1  ~END");
        db = new DatabaseHandler(this.getApplicationContext());
        
        //SMcD: just adding this to see if it grabs jobs from the DB. And it does. Happy days
        int jobId = getIntent().getIntExtra("job_id", 0);
        expense = db.getAllExpensesForJob(jobId); //makes a list of jobs to send to the list View
        job = db.getJob(jobId);	
        String JobName = job.getClient();
        TextView JobNameTitle = (TextView) findViewById(R.id.job_name);				//prepaires to access textView
		JobNameTitle.setText(JobName);									// sets the text view this data will always be set
        
        setListAdapter(new ExpensesAdapter(this, expense)); //starts the list View
    }

    
    public void addNewExpense(View v) {
       	Intent intent = new Intent(ExpensesList.this, AddNewExpense.class);
       	intent.putExtra("job_id", getIntent().getIntExtra("job_id", 0));
    	startActivity(intent);	
   }
    
    @Override
    public void onBackPressed() {
    	Intent intent = new Intent(ExpensesList.this, JobsOptions.class);
    	intent.putExtra("job_id", getIntent().getIntExtra("job_id", 0));
    	startActivity(intent);
    	return;
    }       
        
    
	@Override
	  public void onListItemClick(ListView parent, View v, int position, long id)
	  {	 
	  	Intent intent = new Intent(ExpensesList.this, ViewExpenses.class);
	  	int expenseId = expense.get(position).getId();
	  	intent.putExtra("expense", expenseId); //sends the job name
	    startActivity(intent);
	  }

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_expences_list, menu);
        return true;
    }
}
