/**
 * Shows the list of locations and allows the user to add new ones
 * 
 * Authors: Richard Cody,
 * 
 */

package com.zenfly.lancer;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class LocationsList extends ListActivity {
	
	List<Location> locations = new ArrayList<Location>();

	final Context context = this;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations_list);
        
		DatabaseHandler db = new DatabaseHandler(this);
		locations = db.getAllLocations();
   
		setListAdapter(new LocationsAdapter(this, locations));
        
    }
    
    public void addLocation(View v) {   	
    	
    	// create new dialog object
    	final Dialog dialog = new Dialog(LocationsList.this);
    	
    	// set the correct layout for this dialog
    	dialog.setContentView(R.layout.activity_add_new_location);
    	dialog.setTitle("Add a new location");    	

    	// find the save button
		Button button = (Button) dialog.findViewById(R.id.saveLocation);
		
		
		
		// what to do when the button is clicked
		button.setOnClickListener(new OnClickListener() {
			// get each EditText field, put the contents to string, and then save these to the locations database
			public void onClick(View v) {
				
				DatabaseHandler db = new DatabaseHandler(LocationsList.this); 
				
		    	EditText location_nickname  = (EditText)dialog.findViewById(R.id.location_name);
		    	EditText location_address1  = (EditText)dialog.findViewById(R.id.address_line1);
		    	EditText location_address2  = (EditText)dialog.findViewById(R.id.address_line2);
		    	EditText location_address3  = (EditText)dialog.findViewById(R.id.address_line3);
		    	
		    	String stlocation_nickname = location_nickname.getText().toString();
		    	String stlocation_address1 = location_address1.getText().toString();
		    	String stlocation_address2 = location_address2.getText().toString();
		    	String stlocation_address3 = location_address3.getText().toString();
		    	
		    	if(!stlocation_nickname.equals(""))
		    	{
			    	// add new location based on user entered data
		    		Intent added_a_task_done = new Intent(LocationsList.this, LocationsList.class);
		    		added_a_task_done.putExtra("task_name", getIntent().getStringExtra("task_name"));
		    	  	added_a_task_done.putExtra("task_date", getIntent().getStringExtra("task_date"));
		    	  	added_a_task_done.putExtra("hourly_wage", getIntent().getStringExtra("hourly_wage"));
		    		db.addLocation(new Location(stlocation_nickname, stlocation_address1, stlocation_address2, stlocation_address3));
		    		Toast.makeText(getApplicationContext(), "Saved Location: " + stlocation_nickname, Toast.LENGTH_LONG).show();
		    		startActivity(added_a_task_done);
		    	} else if (stlocation_nickname.equals("")) {
		    		Toast.makeText(getApplicationContext(), "Location Nickname cannot be empty", Toast.LENGTH_LONG).show();		    		
		    	}		        
		     }
		 });	
		
		// show the dialog
		dialog.show();		
    }  
    
	@Override
	public void onListItemClick(ListView parent, View v, int position, long id)
	{	 
	  	Intent intent = new Intent(LocationsList.this, AddNewTask.class);
	  	int locationId = locations.get(position).getId();
	  	intent.putExtra("location", locationId);
	  	intent.putExtra("task_name", getIntent().getStringExtra("task_name"));
	  	intent.putExtra("task_date", getIntent().getStringExtra("task_date"));
	  	intent.putExtra("hourly_wage", getIntent().getStringExtra("hourly_wage"));
	  	intent.putExtra("job_id", getIntent().getIntExtra("job_id", 0));
	    startActivity(intent);
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_locations_list, menu);
        return true;
    }
}
