/**
 * Shows the list of items and allows the user to add new ones
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

public class ItemsList extends ListActivity {
	
	List<Item> items = new ArrayList<Item>();

	final Context context = this;
	
	DatabaseHandler db;
	
	AdapterContextMenuInfo info;
	
		
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_items_list);
        
		db = new DatabaseHandler(this);
		items = db.getAllItems();
		
   
		setListAdapter(new ItemsAdapter(this, items));
		
		registerForContextMenu(getListView());
        
    }
    
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
		    	
		    	float intitem_cost;
		    	if (stitem_cost.equals("")) intitem_cost = 0;
		    	else intitem_cost = Float.parseFloat(stitem_cost);

		    	
		    	if(!stitem_name.equals(""))
		    	{
			    	// add new location based on user entered data
		    		Intent added_an_item_done = new Intent(ItemsList.this, ItemsList.class);
		    		added_an_item_done.putExtra("item_amount", getIntent().getIntExtra("item_amount", 0));
		    		added_an_item_done.putExtra("task_spinner", getIntent().getIntExtra("task_spinner", 0));
		    		added_an_item_done.putExtra("job_id", getIntent().getIntExtra("job_id", 0));
		    		added_an_item_done.putExtra("task_id", getIntent().getIntExtra("task_id", 0));
		    		added_an_item_done.putExtra("expense_id", getIntent().getIntExtra("expense_id", 0)); 

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
	public void onListItemClick(ListView parent, View v, int position, long id) {	 
				
		if (getIntent().getIntExtra("expense_id", 0) != 0) {
			Intent intent = new Intent(ItemsList.this, EditExpense.class);
			int itemId = items.get(position).getId();
			intent.putExtra("job_id", getIntent().getIntExtra("job_id", 0));
		  	intent.putExtra("task_id", getIntent().getIntExtra("task_id", 0));
		  	intent.putExtra("item_id", itemId);
			intent.putExtra("expense_id", getIntent().getIntExtra("expense_id", 0));
			startActivity(intent);
			
		} else {
			Intent intent = new Intent(ItemsList.this, AddNewExpense.class);
			int itemId = items.get(position).getId();
		  	intent.putExtra("item_amount", getIntent().getIntExtra("item_amount", 0));
		  	intent.putExtra("task_spinner", getIntent().getIntExtra("task_spinner", 0));
		  	intent.putExtra("job_id", getIntent().getIntExtra("job_id", 0));
		  	intent.putExtra("task_id", getIntent().getIntExtra("task_id", 0));
		  	intent.putExtra("item_id", itemId);
		  	intent.putExtra("expense_id", getIntent().getIntExtra("expense_id", 0)); 	  	
		    startActivity(intent);
		}	
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
	    	final AlertDialog.Builder builder=new AlertDialog.Builder(ItemsList.this);
	    	builder.setTitle("Delete " + items.get(info.position).getName());
	    	builder.setMessage("Are you sure you want to delete this item?\nThis will also delete any expenses that use this item.");
	    			
	    	builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	    		
	    		public void onClick(DialogInterface dialog, int id) {
	    			db.deleteItem(items.get(info.position).getId());
	    			Toast.makeText(getApplicationContext(), "Deleted " + items.get(info.position).getName(), Toast.LENGTH_LONG).show();
	    			Intent back_to_itemsList = new Intent(context, ItemsList.class);
	    	    			
	    			back_to_itemsList.putExtra("item_amount", getIntent().getIntExtra("item_amount", 0));
	    			back_to_itemsList.putExtra("job_id", getIntent().getIntExtra("job_id", 0));
	    			back_to_itemsList.putExtra("task_id", getIntent().getIntExtra("task_id", 0));
	    			back_to_itemsList.putExtra("task_spinner", getIntent().getIntExtra("task_spinner", 0));	    	    			
	    	    			
	    			startActivity(back_to_itemsList);
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
