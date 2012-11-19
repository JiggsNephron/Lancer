package com.zenfly.lancer;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class AddNewTask extends Activity {
	
	Spinner task_location_spinner;
	Button add_new_location;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_task);
        
        
        task_location_spinner  = (Spinner) findViewById(R.id.spinner_location_names);
        add_new_location = (Button)findViewById(R.id.button_add_deadline);
        
        
        loadSpinnerData();       
        
    }
    
    
    /**
     * Function to load the spinner data from SQLite database
     **/    
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
