package com.zenfly.lancer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class JobsOptions extends Activity  {

	DatabaseHandler db;
	private int id;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs_options);
        
        db = new DatabaseHandler(this.getApplicationContext());
        id = getIntent().getExtras().getInt("job");
        Job job = db.getJob(id);
        TextView jobName = (TextView) findViewById(R.id.job_name);
        jobName.setText(job.getClient());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_jobs_options, menu);
        return true;
    }
      	
	public void createInvoice(View v)
    {
		// FIXME RC: FOR SB > Adding this so I can test my view via this button
    	Intent createIncoice = new Intent(JobsOptions.this, AddNewTask.class);
    	startActivity(createIncoice);
    }
    	
    public void viewTasks(View v)
    {
    	Intent viewTasks = new Intent(JobsOptions.this, TasksList.class);
    	viewTasks.putExtra("job", id); //sends the job id
    	startActivity(viewTasks); 
    }
    
    public void viewExpences(View v)
    {
    	Intent viewExpences = new Intent(JobsOptions.this, ExpensesList .class);
    	startActivity(viewExpences);
    }
           
}
