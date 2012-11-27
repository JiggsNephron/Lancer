/**
 * Used for showing the list of items
 * 
 * Authors: Richard Cody,
 * 
 */

package com.zenfly.lancer;


import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class ItemsAdapter extends ArrayAdapter<Item>
{
	//public static final String PREFS_COUNT = "MyPrefsFile";
	  private final Activity activity;
	  private final List<Item> itemsObject;
	  public DatabaseHandler db;
	
	public ItemsAdapter(Activity activity, List<Item> objects) 
	{
        super(activity, R.layout.activity_items_list , objects);
        this.activity = activity;
        this.itemsObject = objects;
        db = new DatabaseHandler(this.getContext());
	} 

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
	    View rowView = convertView;
	    itemsView itemItemView = null;
	
	    if(rowView == null)
	    {
	    	//creates new instance of row layout view
	        LayoutInflater inflater = activity.getLayoutInflater();
	        rowView = inflater.inflate(R.layout.item_item, null);
	        itemItemView = new itemsView(); //for holding the data
	        itemItemView.name = (TextView) rowView.findViewById(R.id.ItemNameDisplay); 
	       
	        rowView.setTag(itemItemView); //for later access
	    }
	    else itemItemView = (itemsView) rowView.getTag();
	    {
	    	Item currentItem = (Item) itemsObject.get(position); //casts as course		    	
	    
	    	itemItemView.name.setText(currentItem.getName()); //sets the data
	    }
	    return rowView;
	}

    protected static class itemsView
    {
        protected TextView name;

    }
}


