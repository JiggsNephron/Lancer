/**
 * Shows the list of locations and allows the user to add new ones
 * 
 * Authors: Richard Cody,
 * 
 */

package com.zenfly.lancer;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class LocationsList extends ListActivity {
	
	List<Location> locations = new ArrayList<Location>();

	final Context context = this;
	
	AdapterContextMenuInfo info;
	
	DatabaseHandler db;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_locations_list);
        
		db = new DatabaseHandler(this);
		locations = db.getAllLocations();
   
		setListAdapter(new LocationsAdapter(this, locations));
		
		if (getIntent().getIntExtra("task_id", 0) == 0) {
			registerForContextMenu(getListView());
		}
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
		    	
		    	if(!stlocation_nickname.equals("") && !stlocation_address1.equals(""))
		    	{
			    	// add new location based on user entered data
		    		Intent added_a_task_done = new Intent(LocationsList.this, LocationsList.class);
		    		added_a_task_done.putExtra("task_name", getIntent().getStringExtra("task_name"));
		    	  	added_a_task_done.putExtra("task_date", getIntent().getStringExtra("task_date"));
		    	  	added_a_task_done.putExtra("hourly_wage", getIntent().getStringExtra("hourly_wage"));
		    	  	added_a_task_done.putExtra("email_address", getIntent().getStringExtra("email_address"));
		    	  	added_a_task_done.putExtra("phone_number", getIntent().getStringExtra("phone_number"));
		    	  	added_a_task_done.putExtra("job_id", getIntent().getIntExtra("job_id", 0));
		    	  	added_a_task_done.putExtra("task_id", getIntent().getIntExtra("task_id", 0));
		    		db.addLocation(new Location(stlocation_nickname, stlocation_address1, stlocation_address2, stlocation_address3));
		    		Toast.makeText(getApplicationContext(), "Saved Location: " + stlocation_nickname, Toast.LENGTH_LONG).show();
		    		startActivity(added_a_task_done);
		    	} else {
		    		Toast.makeText(getApplicationContext(), "Location Nickname and Address Line 1 cannot be empty", Toast.LENGTH_LONG).show();		    		
		    	}		        
		     }
		 });	
		
		// show the dialog
		dialog.show();		
    }  
    
	@Override
	public void onListItemClick(ListView parent, View v, int position, long id) {
		
		if (getIntent().getIntExtra("task_id", 0) != 0) {
			Intent intent = new Intent(LocationsList.this, EditTask.class);
			int locationId = locations.get(position).getId();
			intent.putExtra("task_id", getIntent().getIntExtra("task_id", 0));
			intent.putExtra("job_id", getIntent().getIntExtra("job_id", 0));
			intent.putExtra("location", locationId);
			startActivity(intent);
		} else {	
	  	Intent intent = new Intent(LocationsList.this, AddNewTask.class);
	  	int locationId = locations.get(position).getId();
	  	intent.putExtra("location", locationId);
	  	intent.putExtra("task_name", getIntent().getStringExtra("task_name"));
	  	intent.putExtra("task_date", getIntent().getStringExtra("task_date"));
	  	intent.putExtra("hourly_wage", getIntent().getStringExtra("hourly_wage"));
	  	intent.putExtra("email_address", getIntent().getStringExtra("email_address"));
	  	intent.putExtra("phone_number", getIntent().getStringExtra("phone_number"));
	  	intent.putExtra("job_id", getIntent().getIntExtra("job_id", 0));
	  	intent.putExtra("task_id", getIntent().getIntExtra("task_id", 0));
	    startActivity(intent);
		}
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_locations_list, menu);
        return true;
    }
    
    @Override
	public void onCreateContextMenu(ContextMenu menu, View v,
	        ContextMenuInfo menuInfo) {
	    super.onCreateContextMenu(menu, v, menuInfo);
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.item_long_press, menu);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		
	    info = (AdapterContextMenuInfo) item.getMenuInfo();
	 
	    switch (item.getItemId()) {
	    
	    case R.id.remove_item:	    	
	    	// confirms the action with the Alert Dialog
	    	final AlertDialog.Builder builder=new AlertDialog.Builder(LocationsList.this);
	    	builder.setTitle("Delete " + locations.get(info.position).getLocation());
	    	builder.setMessage("Are you sure you want to delete this location?\nThis will remove this location from any existing tasks that use it.");
	    			
	    	builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	    		
	    		public void onClick(DialogInterface dialog, int id) {
	    			db.deleteLocation(locations.get(info.position).getId());
	    			Toast.makeText(getApplicationContext(), "Deleted " + locations.get(info.position).getLocation(), Toast.LENGTH_LONG).show();
	    			Intent back_to_locationsList = new Intent(context, LocationsList.class);
	    			
	    			
	    			back_to_locationsList.putExtra("task_name", getIntent().getStringExtra("task_name"));
	    			back_to_locationsList.putExtra("task_date", getIntent().getStringExtra("task_date"));
	    	    	back_to_locationsList.putExtra("hourly_wage", getIntent().getStringExtra("hourly_wage"));
	    	    	back_to_locationsList.putExtra("email_address", getIntent().getStringExtra("email_address"));
	    	    	back_to_locationsList.putExtra("phone_number", getIntent().getStringExtra("phone_number"));
	    	    	back_to_locationsList.putExtra("job_id", getIntent().getIntExtra("job_id", 0));
	    	    			    	    			
	    	    			
	    			startActivity(back_to_locationsList);
	    		}
	    	});
	    	builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
	    		public void onClick(DialogInterface dialog, int id) {
	    			dialog.cancel();
	    		}
	    	});    	      
	    	builder.create().show();
	    	return true;
	    }
	    return false;
	}    
}
