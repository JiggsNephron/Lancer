package com.zenfly.lancer;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;

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
	
	Calendar calendar;
	Date date_locale; 
	int year, month, day;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        
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
        task_id = getIntent().getIntExtra("task_id", 0);
        
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
        	sttask_date = getIntent().getStringExtra("task_date");
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_edit_task, menu);
        return true;
    }
}
