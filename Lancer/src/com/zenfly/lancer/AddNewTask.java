/**
 * Author: Richard Cody 
 * 
 */


package com.zenfly.lancer;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

public class AddNewTask extends Activity {
	
	final Context context = this;
	
	Spinner task_location_spinner;
	Button add_new_location;
	EditText add_deadline;
	
	static final int ID_DEADLINE = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_task);
        
        
        task_location_spinner  = (Spinner) findViewById(R.id.spinner_location_names);
        add_new_location = (Button)findViewById(R.id.button_add_new_location);
        add_deadline = (EditText)findViewById(R.id.button_add_deadline);
        
        
        //add_new_location.setOnClickListener(add_locationListener);			// TODO Add New Location Dialog
        add_deadline.setOnClickListener(add_deadlineListener); 				// TODO Date Picker Dialog
        
        
        
        loadSpinnerData();       
        
    }
    
    View.OnClickListener add_deadlineListener = new View.OnClickListener() {
		
		public void onClick(View v) {
			
			// getting the dialog_task_deadline.xml view as pop up
			LayoutInflater li = LayoutInflater.from(context);
			View promptsView = li.inflate(R.layout.dialog_task_deadline, null);
			
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
			
			alertDialogBuilder.setView(promptsView);
			
			final DatePicker datePicker = (DatePicker) findViewById( R.id.picker_task_deadline );
			
			alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog,int id) {
			    	// get user input and set it to result
			    	// edit text
			    	add_deadline.setText(Integer.toString(datePicker.getDayOfMonth()) + "/" + Integer.toString(datePicker.getMonth()) + "/" + Integer.toString(datePicker.getYear()));
			    }
			  })
			.setNegativeButton("Cancel",
			  new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog,int id) {
				dialog.cancel();
			    }
			  });
			
			
			// create alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();

			// show it
			alertDialog.show();			
			
		}
	};
        
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
