/**
 * Shows the list of items and allows the user to add new ones
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

public class ItemsList extends ListActivity {
	
	List<Item> items = new ArrayList<Item>();

	final Context context = this;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_list);
        
		DatabaseHandler db = new DatabaseHandler(this);
		items = db.getAllItems();
   
		setListAdapter(new ItemsAdapter(this, items));
        
    }
    
    // TODO RC: addItem
    public void addItem(View v) {   	
    	
    	// create new dialog object
    	final Dialog dialog = new Dialog(ItemsList.this);
    	
    	// set the correct layout for this dialog
    	dialog.setContentView(R.layout.activity_add_new_location);
    	dialog.setTitle("Add a new location");    	

    	// find the save button
		Button button = (Button) dialog.findViewById(R.id.saveLocation);
		
		
		
		// what to do when the button is clicked
		button.setOnClickListener(new OnClickListener() {
			// get each EditText field, put the contents to string, and then save these to the locations database
			public void onClick(View v) {
				
				DatabaseHandler db = new DatabaseHandler(ItemsList.this); 
				
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
		    		Intent added_an_item_done = new Intent(ItemsList.this, ItemsList.class);
		    		added_an_item_done.putExtra("item_amount", getIntent().getStringExtra("item_amount"));
		    		added_an_item_done.putExtra("task_spinner", getIntent().getStringExtra("task_spinner"));
		    		added_an_item_done.putExtra("job_id", getIntent().getStringExtra("job_id"));

		    		db.addItem(new Item("ha", 0));
		    		Toast.makeText(getApplicationContext(), "Saved Location: " + stlocation_nickname, Toast.LENGTH_LONG).show();
		    		startActivity(added_an_item_done);
		    		
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
	  	Intent intent = new Intent(ItemsList.this, AddNewTask.class);
	  	int itemId = items.get(position).getId();
	  	intent.putExtra("item_amount", getIntent().getStringExtra("item_amount"));
	  	intent.putExtra("task_spinner", getIntent().getStringExtra("task_spinner"));
	  	intent.putExtra("job_id", getIntent().getStringExtra("job_id"));
	  	intent.putExtra("item_id", itemId);
	  	
	    startActivity(intent);
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_locations_list, menu);
        return true;
    }
}
