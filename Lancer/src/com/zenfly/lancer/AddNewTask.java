/**
 * Allows the user to add new tasks to their job
 * 
 * Authors: Richard Cody, Simon McDonnell,
 * 
 */

package com.zenfly.lancer;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

//TODO SMcD: Add hourly wage (should we do this like notes? let the user add it after the task is added?) 

public class AddNewTask extends FragmentActivity {
	
	final Context context = this;
	
	EditText task_location_box;
	EditText task_name;
	EditText add_deadline;
	Button add_new_location;	
	
	DatabaseHandler db;
	
	String sttask_name;
	String sttask_date = "";
	String stformatted_task_date = "";
	int task_location_id;
	int job_id;
	int hourlyWage = 0;
	int hoursWorked = 0;
	
	Calendar calendar;
	Date date_locale; 
	int year, month, day;
	

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_task);
        
        calendar = Calendar.getInstance(); 
        db = new DatabaseHandler(this.getApplicationContext());
        
        task_name  = (EditText) findViewById(R.id.task_name);
        task_location_box  = (EditText)findViewById(R.id.button_location_names);
        add_deadline = (EditText)findViewById(R.id.button_add_deadline);
        
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
        // otherwise just add an instruction
        if(getIntent().getStringExtra("task_date") != null) {
        	stformatted_task_date = getIntent().getStringExtra("task_date");
        	add_deadline.setText(stformatted_task_date);        
        }
        else {
        	add_deadline.setText("Choose a Deadline"); // FIXME RC: FOR SK > change here to show something else on the deadline EditText view when first shown
        }
    }
    
    // OnClick of the Deadline EditText
    public void selectDate(View v) {
    	DialogFragment newFragment = new SelectDateFragment();
    	newFragment.show(getSupportFragmentManager(), "DatePicker");
    }
    
    // Used to populate the deadline EditText with the chosen date
    public void populateSetDate(int year, int month, int day) {
    	
    	// stores the user chosen deadline date into a string
    	sttask_date = year+"/"+month+"/"+day;
    	
    	// creates a SimpleDateFormat object with the same template as the user chosen deadline date string
    	SimpleDateFormat date_formater = new SimpleDateFormat("yyyy/MM/dd");

    	// creates a date object based on the SimpleDateFormat object
    	try {
			date_locale = date_formater.parse(sttask_date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	// formats the date to a locale friendly string and saves it
    	stformatted_task_date = DateFormat.getDateInstance().format(date_locale);
    	// sets the text of the deadline EditText box to the date string
    	add_deadline.setText(stformatted_task_date);
    } 
    
    // Extends DialogFragment to show a date picker dialog to the user
    public class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    	@Override
    	public Dialog onCreateDialog(Bundle savedInstanceState) {
    		year = calendar.get(Calendar.YEAR);
    		month = calendar.get(Calendar.MONTH);
    		day = calendar.get(Calendar.DAY_OF_MONTH);
    		return new DatePickerDialog(getActivity(), this, year, month, day);
    	}
    	 
    	public void onDateSet(DatePicker view, int yy, int mm, int dd) {
    		populateSetDate(yy, mm+1, dd);
    	}
    }

    // Saves the all the chosen entries as a new task
    public void saveTask (View v) {
    	
    	Intent intent = new Intent(AddNewTask.this, JobsOptions.class);
    	
    	sttask_name = task_name.getText().toString();
    	
    	Task task = new Task(sttask_name, job_id, sttask_date, task_location_id, hourlyWage, hoursWorked);
    	db.addTask(task);   	
    	
    	intent.putExtra("job", job_id);
    	
    	startActivity(intent);
    }
    
    // Lets the user choose from their list of saved locations (or add a new one)
    public void chooseLocation(View v) {    	
    	
    	Intent show_locations = new Intent(this, LocationsList.class);
    	
    	// Preserve the already entered options
    	sttask_name = task_name.getText().toString();
    	    	
    	// Forward the saved entries to the locations list activity
    	// which then sends it back to re-populate those fields in this activity
    	show_locations.putExtra("task_name", sttask_name);
    	show_locations.putExtra("task_date", stformatted_task_date);
    	show_locations.putExtra("job_id", job_id);
    	
    	// show the locations list
    	startActivity(show_locations);
	
    }
 
        
    /** OPTIONS MENU CODE DISABLED FOR NOW
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_add_new_job, menu);
        return true;
    }

    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
	**/    
    
    
}
