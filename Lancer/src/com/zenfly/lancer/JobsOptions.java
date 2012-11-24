package com.zenfly.lancer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class JobsOptions extends Activity  {

	DatabaseHandler db;
	private int id;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs_options);
        
        db = new DatabaseHandler(this.getApplicationContext());
        id = getIntent().getIntExtra("job_id", 0);
        Job job = db.getJob(id);
        TextView jobName = (TextView) findViewById(R.id.job_name);
        jobName.setText(job.getClient());
        
        Button taskButton = (Button)findViewById(R.id.create_invoice);
        taskButton.setVisibility(View.VISIBLE);
        taskButton.setBackgroundColor(Color.TRANSPARENT);
        
        Button expensesButton = (Button)findViewById(R.id.create_invoice);
        expensesButton.setVisibility(View.VISIBLE);
        expensesButton.setBackgroundColor(Color.TRANSPARENT);
        
        Button NotesButton = (Button)findViewById(R.id.create_invoice);
        NotesButton.setVisibility(View.VISIBLE);
        NotesButton.setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_jobs_options, menu);
        return true;
    }
      	
	public void createInvoice(View v)
    {
		// FIXME RC: FOR SB > Adding this so I can test my view via this button
    	Intent createInvoice = new Intent(JobsOptions.this, AddNewTask.class);
    	createInvoice.putExtra("job_id", id);
    	startActivity(createInvoice);
    }
    	
    public void viewTasks(View v)
    {
    	Intent viewTasks = new Intent(JobsOptions.this, TasksList.class);
    	viewTasks.putExtra("job_id", id); //sends the job id
    	startActivity(viewTasks); 
    }
    
    public void viewExpences(View v)
    {
    	Intent viewExpences = new Intent(JobsOptions.this, ExpensesList .class);
    	startActivity(viewExpences);
    }
    
    public void viewNotes(View v)
    {
    	Intent viewExpences = new Intent(JobsOptions.this, ExpensesList .class);
    	startActivity(viewExpences);
    }
           
}
