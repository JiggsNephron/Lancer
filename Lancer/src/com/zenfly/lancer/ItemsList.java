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
    	dialog.setContentView(R.layout.activity_add_new_item);
    	dialog.setTitle("Add a new item");    	

    	// find the save button
		Button button = (Button) dialog.findViewById(R.id.saveNewItem);		
		
		// what to do when the button is clicked
		button.setOnClickListener(new OnClickListener() {
			// get each EditText field, put the contents to string, and then save these to the items database
			public void onClick(View v) {
				
				DatabaseHandler db = new DatabaseHandler(ItemsList.this); 
				
		    	EditText item_name  = (EditText)dialog.findViewById(R.id.item_name);
		    	EditText item_cost = (EditText)dialog.findViewById(R.id.item_cost);

		    	
		    	String stitem_name = item_name.getText().toString();
		    	String stitem_cost = item_cost.getText().toString();
		    	
		    	int intitem_cost;
		    	if (stitem_cost.equals("")) intitem_cost = 0;
		    	else intitem_cost = Integer.parseInt(stitem_cost);

		    	
		    	if(!stitem_name.equals(""))
		    	{
			    	// add new location based on user entered data
		    		Intent added_an_item_done = new Intent(ItemsList.this, ItemsList.class);
		    		added_an_item_done.putExtra("item_amount", getIntent().getStringExtra("item_amount"));
		    		added_an_item_done.putExtra("task_spinner", getIntent().getStringExtra("task_spinner"));
		    		added_an_item_done.putExtra("job_id", getIntent().getStringExtra("job_id"));

		    		db.addItem(new Item(stitem_name, intitem_cost));
		    		Toast.makeText(getApplicationContext(), "Saved Item: " + stitem_name, Toast.LENGTH_LONG).show();
		    		startActivity(added_an_item_done);
		    		
		    	} else if (stitem_name.equals("")) {
		    		Toast.makeText(getApplicationContext(), "Item Name cannot be empty", Toast.LENGTH_LONG).show();		    		
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

}
