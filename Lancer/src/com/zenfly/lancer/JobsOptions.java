package com.zenfly.lancer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class JobsOptions extends Activity  {

	DatabaseHandler db;
	private int id;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE); // RC: this removed the black bar at the top of activities. 
        setContentView(R.layout.activity_jobs_options);
        db = new DatabaseHandler(this.getApplicationContext());
        id = getIntent().getIntExtra("job_id", 0);
        Job job = db.getJob(id);
        TextView jobName = (TextView) findViewById(R.id.job_name);
        jobName.setText(job.getClient());
    }

    @Override
    public void onBackPressed() {
    	Intent intent = new Intent(JobsOptions.this, JobsList.class);
    	intent.putExtra("splash_screen", 1);
    	startActivity(intent);
    	return;
    }  
      	
	public void createInvoice(View v)
    {
		Intent ViewInvoice = new Intent(JobsOptions.this, ViewJobInvoice.class);
    	ViewInvoice.putExtra("job_id", id); //sends the job id
    	startActivity(ViewInvoice);
    }
    	
    public void viewTasks(View v)
    {
    	Intent viewTasks = new Intent(JobsOptions.this, TasksList.class);
    	viewTasks.putExtra("job_id", id); //sends the job id
    	startActivity(viewTasks); 
    }
    
    public void viewExpences(View v)
    {
    	Intent viewExpences = new Intent(JobsOptions.this, ExpensesList.class);
    	viewExpences.putExtra("job_id", id); //sends the job id
    	startActivity(viewExpences);
    }
    
    public void viewNotes(View v)
    {
    	Intent ViewNotes = new Intent(JobsOptions.this, NotesList.class);
    	ViewNotes.putExtra("job_id", id); //sends the job id
    	startActivity(ViewNotes);
    }
}
