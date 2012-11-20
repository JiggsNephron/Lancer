/**
 * Author: Richard Cody 
 * 
 */


package com.zenfly.lancer;

import java.util.Calendar;
import java.util.List;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

public class AddNewTask extends FragmentActivity {
	
	final Context context = this;
	
	Spinner task_location_spinner;
	Button add_new_location;
	EditText add_deadline;
	
	Calendar calendar;
	int day, month, year;
	

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_task);
        
        calendar = Calendar.getInstance();
        
        task_location_spinner  = (Spinner) findViewById(R.id.spinner_location_names);
        add_new_location = (Button)findViewById(R.id.button_add_new_location);
        add_deadline = (EditText)findViewById(R.id.button_add_deadline);
        
        // FIXME RC: FOR SK > change here to show something else on the deadline EditText view when first shown
        add_deadline.setText("Choose a Deadline");
        
        //add_new_location.setOnClickListener(add_locationListener);			// TODO RC: Add New Location Dialog

        
        
        loadSpinnerData();       
        
    }
    
    // OnClick of the Deadline EditText
    public void selectDate(View v) {
    	DialogFragment newFragment = new SelectDateFragment();
    	newFragment.show(getSupportFragmentManager(), "DatePicker");
    	}
    // Used to populate the deadline EditText with the chosen date
    public void populateSetDate(int year, int month, int day) {
    		// FIXME RC: FOR SK > change here to show something else on the deadline EditText view when date chosen
    	 	add_deadline.setText(day+"/"+month+"/"+year);
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
        
    /**
     * Function to load the spinner data from SQLite database
     **/
    																			// TODO RC: Test Function to load the spinner data from SQLite database
    private void loadSpinnerData() {
    	
        // database handler
        DatabaseHandler db = new DatabaseHandler(this);
 
        // Spinner Drop down elements
        List<String> lables = db.getAllLocationStrings();
 
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lables);
 
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
 
        // attaching data adapter to spinner
        task_location_spinner.setAdapter(dataAdapter);
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
