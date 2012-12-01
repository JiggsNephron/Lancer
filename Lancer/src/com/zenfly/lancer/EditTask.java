package com.zenfly.lancer;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditTask extends Activity {
	
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
	int alarm = 0;
	int task_location_id;
	
	int job_id;
	int task_id;
	
	float hourlyWage = 0;
	String sthourly_wage = "";
	int hoursWorked = 0;
	
	int done = 0;
	
	Task current_task;
	
	NumberFormat locale_currency_format;
	
	Calendar calendar;
	Date date_locale; 
	int year, month, day;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        
        calendar = Calendar.getInstance(); 
        db = new DatabaseHandler(context);
        locale_currency_format = NumberFormat.getCurrencyInstance();
        
        
        // define the layout elements
        task_name  = (EditText) findViewById(R.id.task_name);
        task_location_box  = (EditText)findViewById(R.id.button_location_names);
        add_deadline = (EditText)findViewById(R.id.button_add_deadline);
        task_hourly_wage = (EditText)findViewById(R.id.task_hourly_wage);        
        task_email_address = (EditText)findViewById(R.id.task_email_address);
        task_phone_number = (EditText)findViewById(R.id.task_phone_number);
        
        // get intent content      
        job_id = getIntent().getIntExtra("job_id", 0);
        task_id = getIntent().getIntExtra("task_id", 0);
        
        current_task = db.getTask(task_id);

        task_name.setText(current_task.getName());
        task_location_box.setText((db.getLocation(current_task.getLocation())).getLocation());
        
        sttask_date = getIntent().getStringExtra(current_task.getDeadline());
        
        SimpleDateFormat date_formater = new SimpleDateFormat("yyyy/MM/dd");
        date_locale = date_formater.parse(sttask_date);
        stformatted_task_date = DateFormat.getDateInstance().format(date_locale);
        
        add_deadline.setText(stformatted_task_date);
        
        task_hourly_wage.setText(locale_currency_format.format(current_task.getWage()));
        
        task_email_address.setText(current_task.getEmail());
        task_phone_number.setText(current_task.getPhone());
        
    }
    
    // Saves the all the chosen entries as a new task
    public void editTask (View v) {
    	
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
    	    		
    				
    	    		
    	        	db.updateTask(current_task);
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
        		
        		
        		
        		db.updateTask(current_task);
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
}
