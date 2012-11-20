/**
 * Author: Richard Cody 
 * 
 */


package com.zenfly.lancer;

import java.util.Calendar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

public class AddNewTask extends FragmentActivity {
	
	final Context context = this;
	
	EditText task_location_box;
	Button add_new_location;
	EditText add_deadline;
	
	Calendar calendar;
	int day, month, year;
	

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_task);
        
        calendar = Calendar.getInstance();
        
        task_location_box  = (EditText)findViewById(R.id.button_add_new_location);
        add_deadline = (EditText)findViewById(R.id.button_add_deadline);
        
        // FIXME RC: FOR SK > change here to show something else on the deadline EditText view when first shown
        add_deadline.setText("Choose a Deadline");
        
        //add_new_location.setOnClickListener(add_locationListener);			// TODO RC: Add New Location Dialog
    
        
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
    
    public void chooseLocation(View v) {
    	
    	// TODO RC: chooseLocation dialog actions
    	LayoutInflater li = LayoutInflater.from(context);
		View promptsView = li.inflate(R.layout.activity_locations_list, null);
		
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
		
		alertDialogBuilder.setView(promptsView);
		
		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();
		
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
