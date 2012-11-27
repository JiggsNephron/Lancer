/**
 * Used for showing the list of locations
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


public class ItemsAdapter extends ArrayAdapter<Location>
{
	//public static final String PREFS_COUNT = "MyPrefsFile";
	  private final Activity activity;
	  private final List<Location> locationsObject;
	  public DatabaseHandler db;
	
	public ItemsAdapter(Activity activity, List<Location> objects) 
	{
        super(activity, R.layout.activity_locations_list , objects);
        this.activity = activity;
        this.locationsObject = objects;
        db = new DatabaseHandler(this.getContext());
	} 

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
	    View rowView = convertView;
	    locationsView locationItemView = null;
	
	    if(rowView == null)
	    {
	    	//creates new instance of row layout view
	        LayoutInflater inflater = activity.getLayoutInflater();
	        rowView = inflater.inflate(R.layout.location_item, null);
	        locationItemView = new locationsView(); //for holding the data
	        locationItemView.name = (TextView) rowView.findViewById(R.id.LocationNameDisplay); 
	       
	        rowView.setTag(locationItemView); //for later access
	    }
	    else locationItemView = (locationsView) rowView.getTag();
	    {
	    	Location currentLocation = (Location) locationsObject.get(position); //casts as course		    	
	    
	    	locationItemView.name.setText(currentLocation.getLocation()); //sets the data
	    }
	    return rowView;
	}

    protected static class locationsView
    {
        protected TextView name;

    }
}


