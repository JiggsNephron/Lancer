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
	

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_task);
        
        
        task_location_spinner  = (Spinner) findViewById(R.id.spinner_location_names);
        add_new_location = (Button)findViewById(R.id.button_add_new_location);
        add_deadline = (EditText)findViewById(R.id.button_add_deadline);
        
        
        //add_new_location.setOnClickListener(add_locationListener);			// TODO Add New Location Dialog

        
        
        loadSpinnerData();       
        
    }
    
    public void selectDate(View v) {
    	DialogFragment newFragment = new SelectDateFragment();
    	newFragment.show(getSupportFragmentManager(), "DatePicker");
    	}
    
    public void populateSetDate(int year, int month, int day) {
    	 	add_deadline.setText(day+"/"+month+"/"+year);
    	}
    
    
    public class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    	@Override
    	public Dialog onCreateDialog(Bundle savedInstanceState) {
    	final Calendar calendar = Calendar.getInstance();
    	int yy = calendar.get(Calendar.YEAR);
    	int mm = calendar.get(Calendar.MONTH);
    	int dd = calendar.get(Calendar.DAY_OF_MONTH);
    	return new DatePickerDialog(getActivity(), this, yy, mm, dd);
    	}
    	 
    	public void onDateSet(DatePicker view, int yy, int mm, int dd) {
    	populateSetDate(yy, mm+1, dd);
    	}
    }
        
    /**
     * Function to load the spinner data from SQLite database
     **/
    																			// TODO Test Function to load the spinner data from SQLite database
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
