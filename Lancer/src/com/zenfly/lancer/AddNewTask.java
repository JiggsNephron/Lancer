/**
 * Allows the user to add new tasks to their job
 * 
 * Authors: Richard Cody, Simon McDonnell (small things),
 * 
 */

package com.zenfly.lancer;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

public class AddNewTask extends FragmentActivity {
	
	final Context context = this;
	
	EditText task_location_box;
	EditText task_name;
	EditText add_deadline;
	EditText task_hourly_wage;
	EditText task_email_address;
	EditText task_phone_number;
	
	Button add_new_location;	
	
	DatabaseHandler db;
	
	String sttask_name;
	
	String sttask_date = "";
	String stformatted_task_date = "";
	
	String sttask_email_address = "";
	String sttask_phone_number = "";
	
	int task_location_id;
	
	int job_id;
	
	float hourlyWage = 0;
	String sthourly_wage = "";
	int hoursWorked = 0;
	
	int done = 0;
	
	Calendar calendar;
	Date date_locale; 
	int year, month, day;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_new_task);
        
        calendar = Calendar.getInstance(); 
        db = new DatabaseHandler(context);
        
        // define the layout elements
        task_name  = (EditText) findViewById(R.id.task_name);
        task_location_box  = (EditText)findViewById(R.id.button_location_names);
        add_deadline = (EditText)findViewById(R.id.button_add_deadline);
        task_hourly_wage = (EditText)findViewById(R.id.task_hourly_wage);        
        task_email_address = (EditText)findViewById(R.id.task_email_address);
        task_phone_number = (EditText)findViewById(R.id.task_phone_number);
        
        // get intent content      
        job_id = getIntent().getIntExtra("job_id", 0);        
        
        // check if there is a task_name in the received intent and put it into the EditText
        if(getIntent().getStringExtra("task_name") != null) {
        	sttask_name = getIntent().getStringExtra("task_name");
        	task_name.setText(sttask_name);
        }
        
        // check if there is a location in the received intent and put it into the EditText       
        if(getIntent().getIntExtra("location", 0) != 0) {
        	task_location_id = getIntent().getIntExtra("location", 0);
        	Location location = db.getLocation(task_location_id);
        	task_location_box.setText(location.getLocation());
        }  
        
        // check if there is a task_date in the received intent and put it into the EditText
        if(getIntent().getStringExtra("task_date") != null) {
        	stformatted_task_date = getIntent().getStringExtra("task_date");
        	add_deadline.setText(stformatted_task_date);        
        }
        else {
        	add_deadline.setText(""); // FIXME RC: FOR SK > change here to show something else on the deadline EditText view when first shown
        }
        
        // check if there is a hourly wage in the received intent and put it into the EditText
        if(getIntent().getStringExtra("hourly_wage") != null) {
        	sthourly_wage = getIntent().getStringExtra("hourly_wage");
        	task_hourly_wage.setText(sthourly_wage);        
        }        
    }
    
    // OnClick of the Deadline EditText
    public void selectDate(View v) {
    	DialogFragment newFragment = new SelectDateFragment();
    	newFragment.show(getSupportFragmentManager(), "DatePicker");
    }
    
    // Extends DialogFragment to show a date picker dialog to the user
    @SuppressLint("ValidFragment")
	public class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    	// show the dialog with the current date
    	@Override
    	public Dialog onCreateDialog(Bundle savedInstanceState) {
    		year = calendar.get(Calendar.YEAR);
    		month = calendar.get(Calendar.MONTH);
    		day = calendar.get(Calendar.DAY_OF_MONTH);
    		return new DatePickerDialog(getActivity(), this, year, month, day);
    	}
    	// once the user chooses a date and clicks set, populateSetDate() is called
    	public void onDateSet(DatePicker view, int yy, int mm, int dd) {
    		populateSetDate(yy, mm+1, dd);
    	}
    }    
    
    // Used to populate the deadline EditText with the chosen date
    public void populateSetDate(int year, int month, int day) {
    	
    	// stores the user chosen deadline date into a string
    	sttask_date = year+"/"+month+"/"+day;
    	
    	// creates a SimpleDateFormat object with the same template as the user chosen deadline date string
    	SimpleDateFormat date_formater = new SimpleDateFormat("yyyy/MM/dd");

    	try {
    		// creates a date object based on the SimpleDateFormat object
			date_locale = date_formater.parse(sttask_date);
			// formats the date to a locale friendly string and saves it
			stformatted_task_date = DateFormat.getDateInstance().format(date_locale);
		} catch (ParseException e) {
			stformatted_task_date = "";
		}   	
    	
    	// sets the text of the deadline EditText box to the locale formatted date string
    	add_deadline.setText(stformatted_task_date);
    }     

    // Saves the all the chosen entries as a new task
    public void saveTask (View v) {
    	
    	Intent back_to_tasksList = new Intent(context, TasksList.class);
    	
    	// get the EditText fields and convert the wage to an integer
    	sttask_name = task_name.getText().toString();
    	sthourly_wage = task_hourly_wage.getText().toString();    	
    	sttask_email_address = task_email_address.getText().toString();
    	sttask_phone_number = task_phone_number.getText().toString();    	
    	
    	if (sthourly_wage.equals("")) hourlyWage = 0;
    	else hourlyWage = Float.parseFloat(sthourly_wage);
    	
    	// create a new task using the users preferences and add it to the database
    	if (!sttask_email_address.equals("")) {
    		if (checkEmailValid(sttask_email_address)) {
    			if(!sttask_name.equals(""))	{
    	    		Task new_task = new Task(sttask_name, job_id, sttask_date, task_location_id, hourlyWage, sttask_phone_number, sttask_email_address, hoursWorked, done);
    	        	db.addTask(new_task);
    	        	back_to_tasksList.putExtra("job_id", job_id);
    		    	startActivity(back_to_tasksList);
    	    	}
    	    	else {
    	    		Toast.makeText(getApplicationContext(), " You must provide a name for your task ", Toast.LENGTH_LONG).show();
    	    	}
    			
    		} else if (!checkEmailValid(sttask_email_address)) {
    			Toast.makeText(getApplicationContext(), "Please enter a valid email address", Toast.LENGTH_LONG).show();
    		}    		
    	} else {    		
        	if(!sttask_name.equals(""))
        	{
        		Task new_task = new Task(sttask_name, job_id, sttask_date, task_location_id, hourlyWage, sttask_phone_number, sttask_email_address, hoursWorked, done);
            	db.addTask(new_task);
            	back_to_tasksList.putExtra("job_id", job_id);
    	    	startActivity(back_to_tasksList);
        	}
        	else {
        		Toast.makeText(getApplicationContext(), " You must provide a name for your task ", Toast.LENGTH_LONG).show();
        	}
    	}
    }
    
    // method to check if user entered email address is correct format
    public static boolean checkEmailValid(String email) {
        
       	boolean isEmailValid = false;

        String emailcheckstring = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(emailcheckstring, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        
        if (matcher.matches()) {
        	isEmailValid = true;
        }
        
        return isEmailValid;
    }
    
    // Lets the user choose from their list of saved locations (or add a new one)
    public void chooseLocation(View v) {    	
    	
    	Intent show_locations = new Intent(context, LocationsList.class);
    	
    	// Preserve the already entered options
    	sttask_name = task_name.getText().toString();
    	sthourly_wage = task_hourly_wage.getText().toString();
    	sttask_email_address = task_email_address.getText().toString();
    	sttask_phone_number = task_phone_number.getText().toString();
    	
    	// Forward the saved entries to the locations list activity
    	// which then sends it back to re-populate those fields in this activity
    	show_locations.putExtra("task_name", sttask_name);
    	show_locations.putExtra("task_date", stformatted_task_date);
    	show_locations.putExtra("hourly_wage", sthourly_wage);
    	show_locations.putExtra("email_address", sttask_email_address);
    	show_locations.putExtra("phone_number", sttask_phone_number);
    	show_locations.putExtra("job_id", job_id);
    	
    	// show the locations list
    	show_locations.putExtra("job_id", job_id);
    	startActivity(show_locations);	
    }

    // Override back button to create a more consistent experience
    @Override
    public void onBackPressed() {
    	Intent intent = new Intent(AddNewTask.this, TasksList.class);
    	intent.putExtra("job_id", job_id);
    	startActivity(intent);
    	return;
    }      
}
