package com.zenfly.lancer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

public class ViewTask extends Activity {
	
	// below list sets up variables to hold data that can be used anywhere in the class.
	private DatabaseHandler db;
	int TaskId;	
	int JobId; 
	Task task;
	Job job;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE); // RC: this removed the black bar at the top of activities.
		setContentView(R.layout.activity_view_task);
	
		
		db = new DatabaseHandler(this);
		TaskId = getIntent().getExtras().getInt("task"); 				// gets the task ID from the intent
		JobId = getIntent().getExtras().getInt("job_id");				// gets the Job  ID from the intent
		task  =  db.getTask(TaskId);									// gets the Task object from the database
		job = db.getJob(JobId);											// gets the Job  object form the database
		
		String taskName = task.getName();								//extracts the name from Object
		String JobName = job.getClient();								//extracts the name from Object
		String taskDeadline = task.getDeadline();						//extracts the due Date from Object
		String taskLocation = "";
		int taskLocationID = task.getLocation();
		
		TextView JobNameTitle = (TextView) findViewById(R.id.job_name);				//prepaires to access textView
		TextView TaskNameTitle = (TextView) findViewById(R.id.job_option);			//prepaires to access textView
		TextView displayName = (TextView) findViewById(R.id.thisTaskName);			//prepaires to access textView
		TextView displayDate = (TextView) findViewById(R.id.thisTaskDeadline);		//prepaires to access textView
		TextView displayLocation = (TextView) findViewById(R.id.thisTaskLocation);	//prepaires to access textView
		
		JobNameTitle.setText(JobName);									// sets the text view this data will always be set
		TaskNameTitle.setText("View Task");								// sets the text view this data will always be set
		displayName.setText(taskName);									// sets the text view this data will always be set
		
		
		if(taskLocationID == 0)											// if no location set display "No Location Set"
		{
			taskLocation = "No Location Set";
		}
		else
		{
			Location location = db.getLocation(taskLocationID);			//extracts a location object from database 
			taskLocation = location.getLocation();						//gets the first line.
			if(!location.getAdd1().equals("")) taskLocation += ",\n" + location.getAdd1();
			if(!location.getAdd2().equals("")) taskLocation += ",\n" + location.getAdd2();
			if(!location.getAdd3().equals("")) taskLocation += ",\n" + location.getAdd3();
			displayLocation.setText(taskLocation);						// sets the location in the textVew.
		}
		
		//Log.v("View Task", "taskDeadline =" + taskDeadline + "~END");
		//taskDeadline = "overDue";
		//taskDeadline = taskDeadline.trim();
		Log.v("View Task", "taskDeadline =" + taskDeadline+ "~END");
		if((taskDeadline.equals("")) || (taskDeadline == null))				// if no deadline is set display default message
		{
			taskDeadline = "No Deadline Set";
			displayDate.setText(taskDeadline);	
		}
		else displayDate.setText(taskDeadline);							// sets the deadline if applicable
		/*displayName.setText(taskName);
		displayDate.setText(taskDeadline);
		displayLocation.setText(taskLocation);*/
		
	}
	
	
	public void deleteTask(View v)
	{
		// confirms the action with the Alert Dialog
		final AlertDialog.Builder builder=new AlertDialog.Builder(ViewTask.this);
		builder.setTitle("DELETE");
		builder.setMessage("are you sure you want to delete this Task");
		
		builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface dialog, int id)
			{
				db.deleteTask(TaskId);
    			Toast.makeText(getApplicationContext(), "Deleted " + task.getName(), Toast.LENGTH_LONG).show();
    			Intent intent = new Intent(ViewTask.this, TasksList.class);
    			intent.putExtra("job_id", task.getJob());
    			startActivity(intent);
			}
		});
      
		builder.setNegativeButton("No", new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int id)
			{
 	   	      dialog.cancel();
			}
		});
      
		builder.create().show();
	}
	
	public void viewJobNotes(View v)
	{
    	Intent ViewNotes = new Intent(ViewTask.this, ViewNotes .class);
    	startActivity(ViewNotes);
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_view_task, menu);
		return true;
	}

}
