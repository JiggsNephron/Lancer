package com.zenfly.lancer;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class ExpensesList extends Activity {
	
	public DatabaseHandler db;
	List<Expense> expense = new ArrayList<Expense>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses_list);
        
        db = new DatabaseHandler(this.getApplicationContext());
        
        //SMcD: just adding this to see if it grabs jobs from the DB. And it does. Happy days
        int thisJob = getIntent().getIntExtra("job_id", 0);
        expense = db.getAllExpensesForJob(thisJob); //makes a list of jobs to send to the list View
        
        setListAdapter(new ExpensesAdapter(this, expense)); //starts the list View
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_expences_list, menu);
        return true;
    }
}
