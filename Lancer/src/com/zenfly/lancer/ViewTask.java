package com.zenfly.lancer;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
	Location location;
	Date date_locale;
	String stformatted_task_date = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE); // RC: this removed the black bar at the top of activities.
		setContentView(R.layout.activity_view_task);
		
		db = new DatabaseHandler(this);
		TaskId = getIntent().getExtras().getInt("task"); 				// gets the task ID from the intent
		JobId = getIntent().getExtras().getInt("job_id");				// gets the Job  ID from the intent
		task =  db.getTask(TaskId);									// gets the Task object from the database
		job = db.getJob(JobId);											// gets the Job  object form the database
		
		String taskName = task.getName();								//extracts the name from Object
		String JobName = job.getClient();								//extracts the name from Object
		String taskLocation = "";
		String taskDeadline = task.getDeadline();						//extracts the due Date from Object		
		if((taskDeadline.equals("")) || (taskDeadline == null))	taskDeadline = ""; 											// if no deadline, sets as empty string	
		
		int taskLocationID = task.getLocation();
		
		TextView JobNameTitle = (TextView) findViewById(R.id.job_name);				//prepares to access textView
		TextView TaskNameTitle = (TextView) findViewById(R.id.job_option);			//prepares to access textView
		TextView displayName = (TextView) findViewById(R.id.thisTaskName);			//prepares to access textView
		TextView displayDate = (TextView) findViewById(R.id.thisTaskDeadline);		//prepares to access textView
		TextView displayLocation = (TextView) findViewById(R.id.view_on_map);	//prepares to access textView
		
		JobNameTitle.setText(JobName);									// sets the text view this data will always be set
		TaskNameTitle.setText("View Task");								// sets the text view this data will always be set
		displayName.setText(taskName);									// sets the text view this data will always be set
		
		if(taskLocationID == 0)											// if no location set display "No Location Set"
		{
			taskLocation = "None Set";
		}
		else
		{
			location = db.getLocation(taskLocationID);			//extracts a location object from database 
			taskLocation = location.getLocation();						//gets the first line.
//			if(!location.getAdd1().equals("")) taskLocation += ",\n" + location.getAdd1();
//			if(!location.getAdd2().equals("")) taskLocation += ",\n" + location.getAdd2();
//			if(!location.getAdd3().equals("")) taskLocation += ",\n" + location.getAdd3();
		}
		displayLocation.setText(taskLocation); // sets the location in the textVew
		Log.v("View Task", "taskDeadline =" + taskDeadline+ "~END");		
		
		// RC: makes the due date show as locale formatted string
		if((!taskDeadline.equals(""))) {
	    	// creates a SimpleDateFormat object with the same template as the database deadline date string			
			SimpleDateFormat date_formater = new SimpleDateFormat("yyyy/MM/dd");			
			try {
	    		// creates a date object based on the SimpleDateFormat object
	    		date_locale = date_formater.parse(taskDeadline);
		    	// formats the date to a locale friendly string and saves it
				stformatted_task_date = DateFormat.getDateInstance().format(date_locale);	    		
	    	} catch (ParseException e) {
				stformatted_task_date = "None Set";
			}	    				
			displayDate.setText(stformatted_task_date); //displays the locale formatted deadline
		} else {
			taskDeadline = "None Set"; // if no deadline, sets a default message
			displayDate.setText(taskDeadline);
		}		
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
	
	public void editTask(View v) {
		Intent edittask = new Intent(ViewTask.this, EditTask.class);
		edittask.putExtra("job_id", JobId);
		edittask.putExtra("task_id", TaskId); //sends the task id
    	startActivity(edittask);
	}
	
	
	public void viewTaskNotes(View v)
	{
    	Intent ViewNotes = new Intent(ViewTask.this, NotesList.class);
    	ViewNotes.putExtra("job_id", JobId);
    	ViewNotes.putExtra("task_id", TaskId); //sends the task id
    	startActivity(ViewNotes);
	}
	
	
	public void viewTaskExpenses(View v)
	{
		Intent ViewExpenses = new Intent(ViewTask.this, ExpensesList.class);
		ViewExpenses.putExtra("job_id", JobId);
		ViewExpenses.putExtra("task_id", TaskId); //sends the task id
    	startActivity(ViewExpenses);
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
		if(!task.getPhone().equals(""))
		{
			Intent intent = new Intent(Intent.ACTION_CALL);
			intent.setData(Uri.parse("tel:" + task.getPhone()));
			startActivity(intent);
		}
		else Toast.makeText(getApplicationContext(), "You have not set a contact number for this task", Toast.LENGTH_LONG).show();
	}
	
	// method used to view the Task Address on Google Maps
	public void viewOnMap (View v) {
		
		// if there is a location object, 
		// the button attempts to open the address set by the user on google maps
		if (location != null) {
			
			// set the location string into a string variable
			String stlocation = location.getLocation();			
			if(!location.getAdd1().equals("")) stlocation += " " + location.getAdd1();
			if(!location.getAdd2().equals("")) stlocation += " " + location.getAdd2();
			if(!location.getAdd3().equals("")) stlocation += " " + location.getAdd3();
			
			// try to open the address in google maps
			try {
				String uri = String.format("geo:0,0?q=%s", stlocation);
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
				startActivity(intent);
				
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(), " No map app found. ", Toast.LENGTH_SHORT).show();
			}			
		} else {
			Toast.makeText(getApplicationContext(), " No location has been set. ", Toast.LENGTH_SHORT).show();
		}		
	}
	
	public void notifyMe(View v)
	{
		AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		String delim = "[/]";
		String[] dates = task.getDeadline().split(delim);
		Calendar cal = Calendar.getInstance();
		//cal.set(Calendar.YEAR, 2012);//Integer.parseInt(dates[0]));
		//cal.set(Calendar.MONTH, 11);//Integer.parseInt(dates[1])-1);
		//cal.set(Calendar.DAY_OF_MONTH, 2);//Integer.parseInt(dates[2]));
		//cal.set(Calendar.HOUR, 9);
		cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE+1));
		//cal.set(Calendar.MILLISECOND, 0);
		//cal.setTimeInMillis(System.currentTimeMillis());
        //cal.clear();
        //cal.set(Integer.parseInt(dates[0]),Integer.parseInt(dates[1])-1,Integer.parseInt(dates[2]),0,0);
		Intent intent = new Intent(ViewTask.this, NotificationTimer.class);
		intent.putExtra("task", task.getName());
		//intent.putExtra("time", time);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(this, task.getId(), intent, PendingIntent.FLAG_ONE_SHOT);
		//if(!task.hasAlarm())
		//{
			am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
			Toast.makeText(getApplicationContext(), "Alarm Set for " + cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.MONTH) + "/" + cal.get(Calendar.YEAR) + " at " + cal.get(Calendar.HOUR) + ":" + cal.get(Calendar.MINUTE)+1, Toast.LENGTH_LONG).show();
			//db.setTaskAlarm(task.getId(), 1);
			task.setAlarm(1);
		//}
		//else
		//{
		//	am.cancel(pendingIntent);
			//Toast.makeText(getApplicationContext(), "Notification cancelled for " + task.getName(), Toast.LENGTH_LONG).show();
			//db.setTaskAlarm(task.getId(), 0);
			//task.setAlarm(false);
		//}
	}
	
    @Override
    public void onBackPressed() {
    	
    	Intent intent = new Intent(ViewTask.this, TasksList.class);
    	intent.putExtra("job_id", getIntent().getIntExtra("job_id", 0));
    	startActivity(intent);    	
    	return;
    	
    }    
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_view_task, menu);
		return true;
	}

}
