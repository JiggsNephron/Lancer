package com.example.lancer;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class JobOptions extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_options);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_job_options, menu);
        return true;
    }
      	
    public void createInvoice()
    {
    	Intent createIncoice = new Intent(JobOptions.this, CreateInvoice.class);
    	startActivity(createIncoice);
    }
    	
    public void viewTasks()
    {
    	Intent viewTasks = new Intent(JobOptions.this, TasksList.class);
    	startActivity(viewTasks); 
    }
    
    public void viewExpences()
    {
    	Intent viewExpences = new Intent(JobOptions.this, ExpensesList .class);
    	startActivity(viewExpences);
    }
    
    public void deleteJob()
    {
    	Intent deleteJob = new Intent(JobOptions.this, DeleteJob.class);
    	startActivity(deleteJob);
    }
       
}
