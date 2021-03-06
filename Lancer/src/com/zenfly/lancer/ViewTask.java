package com.zenfly.lancer;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ViewTask extends Activity {
	
	// below list sets up variables to hold data that can be used anywhere in the class.
	private DatabaseHandler db;
	private int TaskId;	
	private int JobId;
	private int day = 0;
	long total_minutes;
	long total_millis;
	int start_day;
	int start_hour;
	int start_minute;
	long started_time;
	static final int TIME_DIALOG_ID = 0;
	int stop_day;
	int stop_hour;
	int stop_minute;
	
	
	Task task;
	Job job;
	Location location;
	Date date_locale;
	String stformatted_task_date = "";
	Button start_task;
	SharedPreferences prefs;
	
	NumberFormat locale_currency_format;
	
	Calendar calendar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE); // RC: this removed the black bar at the top of activities.
		setContentView(R.layout.activity_view_task);
		
		db = new DatabaseHandler(this);
		locale_currency_format = NumberFormat.getCurrencyInstance();
		prefs = this.getSharedPreferences("com.zenfly.lancer",0);
		TaskId = getIntent().getExtras().getInt("task"); 				// gets the task ID from the intent
		JobId = getIntent().getExtras().getInt("job_id");				// gets the Job  ID from the intent
		task =  db.getTask(TaskId);									// gets the Task object from the database
		job = db.getJob(JobId);											// gets the Job  object form the database
		
		String taskName = task.getName();								//extracts the name from Object
		String taskLocation = "";
		String taskDeadline = task.getDeadline();						//extracts the due Date from Object		
		if((taskDeadline.equals("")) || (taskDeadline == null))	taskDeadline = ""; 											// if no deadline, sets as empty string	
		
		int taskLocationID = task.getLocation();
		TextView TaskNameTitle = (TextView) findViewById(R.id.job_option);			//prepares to access textView
		TextView displayName = (TextView) findViewById(R.id.thisTaskName);			//prepares to access textView
		TextView displayDate = (TextView) findViewById(R.id.thisTaskDeadline);		//prepares to access textView
		TextView displayLocation = (TextView) findViewById(R.id.view_on_map);	//prepares to access textView
		Button start_task = (Button) findViewById(R.id.start_task);
		TaskNameTitle.setText("Tasks");								// sets the text view this data will always be set
		displayName.setText(taskName);									// sets the text view this data will always be set
		
		if(taskLocationID == 0)											// if no location set display "No Location Set"
		{
			taskLocation = "No Location Set";
		}
		else
		{
			location = db.getLocation(taskLocationID);			//extracts a location object from database 
			taskLocation = "View " + location.getLocation() + " on map";						//gets the first line.
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
		
		if (db.getTaskStarted(task.getId()) == 0) {
			start_task.setBackgroundResource(R.color.lancer_green);
			if (task.getMinutesWorked() > 0 && task.getWage() > 0) {
				start_task.setText("Start Task" + "\nEarned " + locale_currency_format.format(((task.getMinutesWorked())/60.00)*(task.getWage())) + " so far");
			} else start_task.setText("Start Task");			
		} else if (db.getTaskStarted(task.getId()) == 1) {
			start_task.setBackgroundResource(R.color.lancer_red);
			start_task.setText("Stop Task");
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
	
	// RC: starts the timer for task wages
	public void startTask (View v)
	{		
		// new notification manager to set the notifications
		NotificationManager nm = (NotificationManager) ViewTask.this.getSystemService(Context.NOTIFICATION_SERVICE);
		
		// if this task has a wage run this block
		if (task.getWage() > 0) {
			
			// creates a shared preference editor for adding the started time later
			SharedPreferences.Editor prefEditor = prefs.edit();
			calendar = Calendar.getInstance(); 
			
			// if the task timer has not already been started, run this block
			// else stop the task timer
			if (db.getTaskStarted(task.getId()) == 0) {
				db.setTaskStarted(task.getId(), 1);
				
				// put the time that the start button was clicked into shared preferences
				prefEditor.putLong("started_time", calendar.getTimeInMillis());
				prefEditor.commit();
				
				// create an intent for coming back to this task from the notifitcation
				// and put the needed intent extras
				Intent intent = new Intent(ViewTask.this, ViewTask.class);
				intent.putExtra("job_id", getIntent().getIntExtra("job_id", 0));
				intent.putExtra("task", getIntent().getIntExtra("task", 0));
				
				// create a new pending intent with the above intent and an ID matching the task_id
		        PendingIntent pendingIntent = PendingIntent.getActivity(ViewTask.this, task.getId(), intent, 0);
		        // create a notification builder
		        NotificationCompat.Builder builder = new NotificationCompat.Builder(ViewTask.this);
		        // add the required fields to the notification via builder
		        builder.setContentIntent(pendingIntent)
	            .setSmallIcon(R.drawable.app_icon)
	            .setTicker(task.getName() + " started")
	            .setContentTitle(task.getName())
	            .setContentText("started at " + DateUtils.formatDateTime(this, calendar.getTimeInMillis(), DateUtils.FORMAT_SHOW_TIME))
	            .setContentInfo(locale_currency_format.format(task.getWage()))
	            .setOngoing(true);
		        // build the notification and show it
		        Notification notification = builder.build(); 
		        nm.notify(task.getId(), notification);
				
			} else if (db.getTaskStarted(task.getId()) == 1) {
				db.setTaskStarted(task.getId(), 0);
				
				// get the started time from the shared preferences
				started_time = prefs.getLong("started_time", 0);
				// calculate the total milliseconds that have been worked 
				// by subtracting the started time from the end task timer time
				total_millis = calendar.getTimeInMillis() - started_time;
				// convert the total milliseconds worked to minutes
				total_minutes = ((total_millis / 1000) / 60);
				// set the worked minutes into the database for the task
				db.setTaskMinutes(task.getId(), total_minutes);
				// cancel the notification
				nm.cancel(task.getId());
				// inform the user via a toast how long they worked
				Toast.makeText(getApplicationContext(), "You worked " + total_minutes + " minutes.", Toast.LENGTH_SHORT).show();
			}
			
			// refresh the ViewTask activity to show the changes
			Intent refreshTaskView = new Intent(ViewTask.this, ViewTask.class);
			refreshTaskView.putExtra("job_id", getIntent().getIntExtra("job_id", 0));
			refreshTaskView.putExtra("task", getIntent().getIntExtra("task", 0));
			startActivity(refreshTaskView);
			
		} else Toast.makeText(getApplicationContext(), "You have not set an hourly wage for this task", Toast.LENGTH_LONG).show();
	}	
	
	public void callPerson (View v)
	{
		if(!task.getPhone().equals(""))
		{
			Intent intent = new Intent(Intent.ACTION_DIAL);
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
		if(!task.getDeadline().equals("")) //makes sure there is a deadline
		{	 
			if(task.getAlarm() == 0) //checks if a notification is already set
			{
				LayoutInflater factory = LayoutInflater.from(this);            
		        final View notifyDateSet = factory.inflate(R.layout.notify_options, null); //sets the dialog layout
		        AlertDialog.Builder notifyDialog = new AlertDialog.Builder(this);
		    	notifyDialog.setView(notifyDateSet);
		    	final EditText userDay = (EditText) notifyDateSet.findViewById(R.id.notify_day); //the way the user picks the day
		    	notifyDialog.setPositiveButton("Set Day", new DialogInterface.OnClickListener() //if the user chooses Set Time
		    	{
		            public void onClick(DialogInterface dialog, int whichButton)
		            {            	
		                if(!userDay.getText().toString().equals(""))day = Integer.parseInt(userDay.getText().toString()); //makes sure the user has entered a value for day
		                else day = 0;
		                setNotification();
		            }
		        });
		        notifyDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		            public void onClick(DialogInterface dialog, int whichButton) {
		                dialog.cancel();
		            }
		        });
		        notifyDialog.show();
			}
			else //if an alarm is already set
			{
				AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
				Intent intent = new Intent(ViewTask.this, NotificationTimer.class);
				PendingIntent pendingIntent = PendingIntent.getBroadcast(this, task.getId(), intent, PendingIntent.FLAG_ONE_SHOT);
				am.cancel(pendingIntent); //cancels the notification
				Toast.makeText(getApplicationContext(), "Notification cancelled for " + task.getName(), Toast.LENGTH_LONG).show();
				task.setAlarm(0);
				db.setTaskAlarm(task.getId(), 0); //tells the database that a notification is not set for this task
			}
		}
		else Toast.makeText(getApplicationContext(), "You have not set a deadline for this task", Toast.LENGTH_LONG).show();
	}
	
	public void setNotification()
	{
		AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		String delim = "[/]"; //sets the value for splitting Strings
		String[] dates = task.getDeadline().split(delim); //splits the deadline into three parts, year month day
		//sets the calendar to the user specified day/month/year
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, Integer.parseInt(dates[0]));
		cal.set(Calendar.MONTH, Integer.parseInt(dates[1])-1);
		cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dates[2]) - (day+1));
		cal.set(Calendar.HOUR, 23);//chosenHour);
		cal.set(Calendar.MINUTE, 0);//chosenMinute);
		cal.set(Calendar.MILLISECOND, 0);
		Intent intent = new Intent(ViewTask.this, NotificationTimer.class); //an intent which will launch on the correct day
		intent.putExtra("task", task.getName()); //sends the task name to the notification
		intent.putExtra("day", day); //sends the day to the notification
		PendingIntent pendingIntent = PendingIntent.getBroadcast(this, task.getId(), intent, PendingIntent.FLAG_ONE_SHOT);
		am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent); //sets the alarm
		
		String unformatted_date = cal.get(Calendar.YEAR) + "/" + (cal.get(Calendar.MONTH)+1) + "/" + cal.get(Calendar.DAY_OF_MONTH);
		Date tempdate;
		String formatted_date;
		SimpleDateFormat date_formater = new SimpleDateFormat("yyyy/MM/dd");			
		try {
    		// creates a date object based on the SimpleDateFormat object
			tempdate = date_formater.parse(unformatted_date);
	    	// formats the date to a locale friendly string and saves it
			formatted_date = DateFormat.getDateInstance().format(tempdate);	    		
    	} catch (ParseException e) {
    		formatted_date = Integer.toString(day) + "days ahead";
		}	    						
		
		Toast.makeText(getApplicationContext(), "Notification Set for " + formatted_date, Toast.LENGTH_LONG).show();
		db.setTaskAlarm(task.getId(), 1); //changes the database to show this task has an alarm set
		task.setAlarm(1);
	}
		
    @Override
    public void onBackPressed() {
    	
    	Intent intent = new Intent(ViewTask.this, TasksList.class);
    	intent.putExtra("job_id", getIntent().getIntExtra("job_id", 0));
    	intent.putExtra("from_invoice", getIntent().getIntExtra("from_invoice", 0));
    	startActivity(intent);    	
    	return;  	
    }    
}
