package com.zenfly.lancer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class ViewTask extends Activity {
	
	private DatabaseHandler db;
	int TaskId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE); // RC: this removed the black bar at the top of activities.
		setContentView(R.layout.activity_view_task);
		
		db = new DatabaseHandler(this);
		TaskId = getIntent().getExtras().getInt("task");
		//Log.v(TAG, "thisTask=" + thisTask);
		Task task  =  db.getTask(TaskId);
		//Log.v(TAG, "thisTask=" + task);
		String taskName = task.getName();
		String taskDeadline = task.getDeadline();
		String taskLocation = "";
		int taskLocationID = task.getLocation();
		TextView displayName = (TextView) findViewById(R.id.thisTaskName);
		TextView displayDate = (TextView) findViewById(R.id.thisTaskDeadline);
		TextView displayLocation = (TextView) findViewById(R.id.thisTaskLocation);

		
		if(taskLocationID == 0)
		{
			taskLocation = "No Location Set";
		}
		else
		{
			Location location = db.getLocation(taskLocationID);
			taskLocation = location.getAdd1();
		}
		
		if((taskDeadline == "") || (taskDeadline == null))
		{
			taskDeadline = "No Deadline Set";
		}
		
		displayName.setText(taskName);
		displayDate.setText(taskDeadline);
		displayLocation.setText(taskLocation);
		
	}
	
	
	public void deleteTask(View v)
	{
		db.deleteTask(TaskId);
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
