package com.zenfly.lancer;

import java.util.Calendar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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
	String taskLocation;

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
		taskLocation = "";
		int taskLocationID = task.getLocation();
		
		TextView JobNameTitle = (TextView) findViewById(R.id.job_name);				//prepares to access textView
		TextView TaskNameTitle = (TextView) findViewById(R.id.job_option);			//prepares to access textView
		TextView displayName = (TextView) findViewById(R.id.thisTaskName);			//prepares to access textView
		TextView displayDate = (TextView) findViewById(R.id.thisTaskDeadline);		//prepares to access textView
		TextView displayLocation = (TextView) findViewById(R.id.thisTaskLocation);	//prepares to access textView
		
		JobNameTitle.setText(JobName);									// sets the text view this data will always be set
		TaskNameTitle.setText("View Task");								// sets the text view this data will always be set
		displayName.setText(taskName);									// sets the text view this data will always be set
		
		
		if(taskLocationID == 0)											// if no location set display "No Location Set"
		{
			taskLocation = "None Set";
		}
		else
		{
			Location location = db.getLocation(taskLocationID);			//extracts a location object from database 
			taskLocation = location.getLocation();						//gets the first line.
			if(!location.getAdd1().equals("")) taskLocation += ",\n" + location.getAdd1();
			if(!location.getAdd2().equals("")) taskLocation += ",\n" + location.getAdd2();
			if(!location.getAdd3().equals("")) taskLocation += ",\n" + location.getAdd3();
		}
		displayLocation.setText(taskLocation); // sets the location in the textVew
		Log.v("View Task", "taskDeadline =" + taskDeadline+ "~END");
		if((taskDeadline.equals("")) || (taskDeadline == null))	taskDeadline = "None Set"; // if no deadline, sets a default message
		displayDate.setText(taskDeadline); //displays the deadline
	}
	
	public void deleteTask(View v)
	{
		// confirms the action with the Alert Dialog
		final AlertDialog.Builder builder=new AlertDialog.Builder(ViewTask.this);
		builder.setTitle("Delete " + task.getName());
		builder.setMessage("Are you sure you want to delete this Task");
		
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
	
	public void emailPerson (View v)
	{
		if(!task.getEmail().equals(""))
		{
			Intent intent = new Intent(Intent.ACTION_SEND);
			intent.setType("message/rfc822");
			intent.putExtra(Intent.EXTRA_EMAIL, task.getEmail());
			startActivity(Intent.createChooser(intent, "Send Email"));
		}
		else Toast.makeText(getApplicationContext(), "You have not set a contact email for this task", Toast.LENGTH_LONG).show();
	}
	
	public void callPerson (View v)
	{
		if(task.getPhone() != 0)
		{
			Intent intent = new Intent(Intent.ACTION_CALL);
			intent.setData(Uri.parse("tel:" + task.getPhone()));
			startActivity(intent);
		}
		else Toast.makeText(getApplicationContext(), "You have not set a contact number for this task", Toast.LENGTH_LONG).show();
	}
	
	// method used to view the Task Address on Google Maps
	public void viewOnMap (View v) {

		try {
			String uri = String.format("geo:0,0?q=%s", taskLocation);
			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
			startActivity(intent);
			
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), " No map app found. ", Toast.LENGTH_LONG).show();
		    }
	}
	
	public void setNotification(View v)
	{
		AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		Calendar cal = Calendar.getInstance();
		
		Intent intent = new Intent(ViewTask.this, NotificationTimer.class);
		intent.putExtra("task", task.getName());
		//intent.putExtra("time", time);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(this, task.getId(), intent, PendingIntent.FLAG_ONE_SHOT);
		if(task.hasAlarm())
		{
			am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
			Toast.makeText(getApplicationContext(), "Alarm Set", Toast.LENGTH_LONG).show();
			db.setTaskAlarm(task.getId(), 1);
			task.setAlarm(true);
		}
		else
		{
			am.cancel(pendingIntent);
			Toast.makeText(getApplicationContext(), "Notification cancelled for " + task.getName(), Toast.LENGTH_LONG).show();
			db.setTaskAlarm(task.getId(), 0);
			task.setAlarm(false);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_view_task, menu);
		return true;
	}

}
