/**
 * Authors: Richard Cody,
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
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class AddNewTask extends FragmentActivity {
	
	final Context context = this;
	
	EditText task_location_box;
	Button add_new_location;
	EditText add_deadline;
	
	Calendar calendar;
	int year, month, day;
	

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_task);
        
        calendar = Calendar.getInstance();
        
        task_location_box  = (EditText)findViewById(R.id.button_add_new_location);
        add_deadline = (EditText)findViewById(R.id.button_add_deadline);
        
        // FIXME RC: FOR SK > change here to show something else on the deadline EditText view when first shown
        add_deadline.setText("Choose a Deadline");
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
    
    // TODO RC: chooseLocation dialog action (this needs to act as a locations listview)
    public void chooseLocation(View v) {    	
    	
    	LayoutInflater li = LayoutInflater.from(context);
		View promptsView = li.inflate(R.layout.activity_locations_list, null);
		
		
		
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
		
		alertDialogBuilder.setView(promptsView);
		
		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();
		
		ListView listView1 = (ListView) alertDialog.findViewById(R.id.locations_list);
		
		List<Location> locations = new ArrayList<Location>();
		
		DatabaseHandler db = new DatabaseHandler(AddNewTask.this);
		locations = db.getAllLocations();
   
		setListAdapter(new LocationsAdapter(AddNewTask.this, locations));

		// show it
		alertDialog.show();
		
    }
    
    // TODO RC: addLocation needs to save to database
    public void addLocation(View v) {   	
    	
    	// create new dialog object
    	final Dialog dialog = new Dialog(AddNewTask.this);
    	
    	// set the correct layout for this dialog
    	dialog.setContentView(R.layout.activity_add_new_location);
    	dialog.setTitle("Add a new location");    	

    	// find the save button
		Button button = (Button) dialog.findViewById(R.id.saveLocation);
		
		
		
		// what to do when the button is clicked
		button.setOnClickListener(new OnClickListener() {
			// get each EditText field, put the contents to string, and then save these to the locations database
			public void onClick(View v) {
				
				DatabaseHandler db = new DatabaseHandler(AddNewTask.this); 
				
		    	EditText location_nickname  = (EditText)dialog.findViewById(R.id.location_name);
		    	EditText location_address1  = (EditText)dialog.findViewById(R.id.address_line1);
		    	EditText location_address2  = (EditText)dialog.findViewById(R.id.address_line2);
		    	EditText location_address3  = (EditText)dialog.findViewById(R.id.address_line3);
		    	
		    	String stlocation_nickname = location_nickname.getText().toString();
		    	String stlocation_address1 = location_address1.getText().toString();
		    	String stlocation_address2 = location_address2.getText().toString();
		    	String stlocation_address3 = location_address3.getText().toString();
		    	
		    	
		    	if(!stlocation_nickname.equals("") && stlocation_address1.equals("") && stlocation_address2.equals("") && stlocation_address3.equals(""))
		    	{
			    	// add new location based on user entered data
		    		db.addLocation(new Location(stlocation_nickname));
		    		Toast.makeText(getApplicationContext(), "Saved Location: " + stlocation_nickname, Toast.LENGTH_LONG).show();
		    		dialog.dismiss();
		    	}
		    	if(!stlocation_nickname.equals("") && !stlocation_address1.equals("") && stlocation_address2.equals("") && stlocation_address3.equals(""))
		    	{
			    	// add new location based on user entered data
		    		db.addLocation(new Location(stlocation_nickname, stlocation_address1));
		    		Toast.makeText(getApplicationContext(), "Saved Location: " + stlocation_nickname, Toast.LENGTH_LONG).show();
		    		dialog.dismiss();
		    	}  
		    	if(!stlocation_nickname.equals("") && !stlocation_address1.equals("") && !stlocation_address2.equals("") && stlocation_address3.equals(""))
		    	{
			    	// add new location based on user entered data
		    		db.addLocation(new Location(stlocation_nickname, stlocation_address1, stlocation_address2));
		    		Toast.makeText(getApplicationContext(), "Saved Location: " + stlocation_nickname, Toast.LENGTH_LONG).show();
		    		dialog.dismiss();
		    	}  
		    	if(!stlocation_nickname.equals("") && !stlocation_address1.equals("") && !stlocation_address2.equals("") && !stlocation_address3.equals(""))
		    	{
			    	// add new location based on user entered data
		    		db.addLocation(new Location(stlocation_nickname, stlocation_address1, stlocation_address2, stlocation_address3));
		    		Toast.makeText(getApplicationContext(), "Saved Location: " + stlocation_nickname, Toast.LENGTH_LONG).show();
		    		dialog.dismiss();
		    	} else if (stlocation_nickname.equals("")) {
		    		Toast.makeText(getApplicationContext(), "Location Nickname cannot be empty", Toast.LENGTH_LONG).show();		    		
		    	}		        
		     }
		 });	
		
		// show the dialog
		dialog.show();		
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
