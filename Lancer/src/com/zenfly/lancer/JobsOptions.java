package com.zenfly.lancer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
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
                
        // R.id is same for all buttons here? (SK) Button taskButton = (Button)findViewById(R.id.create_invoice);
        // --> This Stuff to be done in xml (SK) //taskButton.setVisibility(View.VISIBLE);
        // --> This Stuff to be done in xml (SK) taskButton.setBackgroundColor(Color.TRANSPARENT);
        
        // R.id is same for all buttons here? (SK) Button expensesButton = (Button)findViewById(R.id.create_invoice);
        // --> This Stuff to be done in xml (SK) expensesButton.setVisibility(View.VISIBLE);
        // --> This Stuff to be done in xml (SK) expensesButton.setBackgroundColor(Color.TRANSPARENT);
        
        // R.id is same for all buttons here? Button NotesButton = (Button)findViewById(R.id.create_invoice);
        // --> This Stuff to be done in xml (SK) NotesButton.setVisibility(View.VISIBLE);
        // --> This Stuff to be done in xml (SK) NotesButton.setBackgroundColor(Color.TRANSPARENT);
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
