package com.zenfly.lancer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

public class ViewTask extends Activity {
	
	private DatabaseHandler db;
	int TaskId;
	Task task;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE); // RC: this removed the black bar at the top of activities.
		setContentView(R.layout.activity_view_task);
		
		db = new DatabaseHandler(this);
		TaskId = getIntent().getExtras().getInt("task");
		//Log.v(TAG, "thisTask=" + thisTask);
		task  =  db.getTask(TaskId);
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
			taskLocation = location.getLocation();
			if(!location.getAdd1().equals("")) taskLocation += ",\n" + location.getAdd1();
			if(!location.getAdd2().equals("")) taskLocation += ",\n" + location.getAdd2();
			if(!location.getAdd3().equals("")) taskLocation += ",\n" + location.getAdd3();
			displayLocation.setText(taskLocation);
		}
		
		if((taskDeadline == "") || (taskDeadline == null))
		{
			taskDeadline = "No Deadline Set";
		}
		else displayDate.setText(taskDeadline);
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
