package com.zenfly.lancer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class JobsOptions extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs_options);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_jobs_options, menu);
        return true;
    }
    
public void addNewTask(View v) {
    	
    	// FIXME RC: FOR SB > Adding this so I can test my view via this button
    	Intent intent = new Intent(JobsOptions.this, AddNewTask.class);
    	
    	startActivity(intent);
    	
    }
      	
/*    public void createInvoice()
    {
    	Intent createIncoice = new Intent(JobsOptions.this, CreateInvoice.class);
    	startActivity(createIncoice);
    }//*/
    	
    public void viewTasks()
    {
    	Intent viewTasks = new Intent(JobsOptions.this, TasksList.class);
    	startActivity(viewTasks); 
    }
    
    public void viewExpences()
    {
    	Intent viewExpences = new Intent(JobsOptions.this, ExpensesList .class);
    	startActivity(viewExpences);
    }
           
}
