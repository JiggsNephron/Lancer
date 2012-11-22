/**
 * Authors: Richard Cody, Simon McDonnell,
 * 
 */


package com.zenfly.lancer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

//TODO SMcD: Add hourly wage 

public class AddNewTask extends FragmentActivity {
	
	final Context context = this;
	
	EditText task_location_box;
	EditText task_name;
	Button add_new_location;
	EditText add_deadline;
	DatabaseHandler db;
	
	Calendar calendar;
	int year, month, day;
	

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_task);
        task_name  = (EditText) findViewById(R.id.task_name);
        task_location_box  = (EditText)findViewById(R.id.button_location_names);//button_add_new_location);
        add_deadline = (EditText)findViewById(R.id.button_add_deadline);
        db = new DatabaseHandler(this.getApplicationContext());
        if(getIntent().getStringExtra("task_name") != null) task_name.setText(getIntent().getStringExtra("task_name"));
        if(getIntent().getStringExtra("task_date") != null) add_deadline.setText(getIntent().getStringExtra("task_date"));
        if(getIntent().getIntExtra("location", 0) != 0) 
        {
        	Location location = db.getLocation(getIntent().getIntExtra("location", 0));
        	task_location_box.setText(location.getLocation());
        }
        calendar = Calendar.getInstance();
        
        
        if(getIntent().getStringExtra("task_date") != null) add_deadline.setText(getIntent().getStringExtra("task_date"));
        // FIXME RC: FOR SK > change here to show something else on the deadline EditText view when first shown
        else add_deadline.setText("Choose a Deadline");
    }
    
    // OnClick of the Deadline EditText
    public void selectDate(View v) {
    	DialogFragment newFragment = new SelectDateFragment();
    	newFragment.show(getSupportFragmentManager(), "DatePicker");
    }
    
    // Used to populate the deadline EditText with the chosen date
    public void populateSetDate(int year, int month, int day) {
    	add_deadline.setText(year+"/"+month+"/"+day);
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
    
    public void saveTask (View v) {
    	Task task = new Task(task_name.getText().toString(), getIntent().getIntExtra("job", 0), add_deadline.getText().toString(), getIntent().getIntExtra("location", 0), 0, 0);
    	db.addTask(task); 
    	Intent intent = new Intent(AddNewTask.this, JobsOptions.class);
    	intent.putExtra("job", getIntent().getIntExtra("job", 0));
    	startActivity(intent);
    }
    
    // TODO RC: ???move chooseLocation to its own class???
    public void chooseLocation(View v) {    	
    	
    	Intent show_locations = new Intent(this, LocationsList.class);
    	
    	String sttask_name = task_name.getText().toString();
    	String sttask_date = add_deadline.getText().toString();
    	show_locations.putExtra("task_name", sttask_name);
    	show_locations.putExtra("task_date", sttask_date);
    	show_locations.putExtra("job", getIntent().getIntExtra("job", 0));
    	
    	startActivity(show_locations);
    	
    	// TODO RC: send the already filled data via addExtra and then receive the location thats been chosen
    	
//    	LayoutInflater li = LayoutInflater.from(context);
//		View promptsView = li.inflate(R.layout.activity_locations_list, null);		
//		
//		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
//		
//		alertDialogBuilder.setView(promptsView);
//		
//		// create alert dialog
//		AlertDialog alertDialog = alertDialogBuilder.create();
//		
//		List<Location> locations = new ArrayList<Location>();
//		
//		DatabaseHandler db = new DatabaseHandler(AddNewTask.this);
//		locations = db.getAllLocations();
//   
//		//setListAdapter(new LocationsAdapter(AddNewTask.this, locations));
//
//		// show it
//		alertDialog.show();
		
    }
    
    
//    public void addLocation(View v) {   	
//    	
//    	// create new dialog object
//    	final Dialog dialog = new Dialog(AddNewTask.this);
//    	
//    	// set the correct layout for this dialog
//    	dialog.setContentView(R.layout.activity_add_new_location);
//    	dialog.setTitle("Add a new location");    	
//
//    	// find the save button
//		Button button = (Button) dialog.findViewById(R.id.saveLocation);
//		
//		
//		
//		// what to do when the button is clicked
//		button.setOnClickListener(new OnClickListener() {
//			// get each EditText field, put the contents to string, and then save these to the locations database
//			public void onClick(View v) {
//				
//				DatabaseHandler db = new DatabaseHandler(AddNewTask.this); 
//				
//		    	EditText location_nickname  = (EditText)dialog.findViewById(R.id.location_name);
//		    	EditText location_address1  = (EditText)dialog.findViewById(R.id.address_line1);
//		    	EditText location_address2  = (EditText)dialog.findViewById(R.id.address_line2);
//		    	EditText location_address3  = (EditText)dialog.findViewById(R.id.address_line3);
//		    	
//		    	String stlocation_nickname = location_nickname.getText().toString();
//		    	String stlocation_address1 = location_address1.getText().toString();
//		    	String stlocation_address2 = location_address2.getText().toString();
//		    	String stlocation_address3 = location_address3.getText().toString();
//		    	
//		    	if(!stlocation_nickname.equals(""))
//		    	{
//			    	// add new location based on user entered data
//		    		db.addLocation(new Location(stlocation_nickname, stlocation_address1, stlocation_address2, stlocation_address3));
//		    		Toast.makeText(getApplicationContext(), "Saved Location: " + stlocation_nickname, Toast.LENGTH_LONG).show();
//		    		dialog.dismiss();
//		    	} else if (stlocation_nickname.equals("")) {
//		    		Toast.makeText(getApplicationContext(), "Location Nickname cannot be empty", Toast.LENGTH_LONG).show();		    		
//		    	}		        
//		     }
//		 });	
//		
//		// show the dialog
//		dialog.show();		
//    }   
        
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
