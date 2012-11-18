package com.example.lancer;


import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class JobsAdapter extends ArrayAdapter<Jobs>
{
	//public static final String PREFS_COUNT = "MyPrefsFile";
	  private final Activity activity;
	  private final List<Jobs> jobsObject;
	
	public JobsAdapter(Activity activity, List<Jobs> objects) 
	{
        super(activity, R.layout.jobsLayout , objects);
        this.activity = activity;
        this.jobsObject = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
	    View rowView = convertView;
	    jobsView jobsItemView = null;
	
	    if(rowView == null)
	    {
	    	//creates new instance of row layout view
	        LayoutInflater inflater = activity.getLayoutInflater();
	        rowView = inflater.inflate(R.layout.The_individual_item_in_the_listView, null);
	        jobsItemView = new jobsView(); //for holding the data
	        jobsItemView.text = (TextView) rowView.findViewById(R.id.text0);
	        jobsItemView.text2 = (TextView) rowView.findViewById(R.id.text1);
	        jobsItemView.text3 = (TextView) rowView.findViewById(R.id.text2);
	       
	        rowView.setTag(jobsItemView); //for later access
	    }
	    else jobsItemView = (jobsView) rowView.getTag();
	    {
	    	Jobs currentCourse = (Jobs) jobsObject.get(position); //casts as course
	
	    	jobsItemView.text.setText(currentCourse.getText("code")); //sets the data
	    	jobsItemView.text2.setText(currentCourse.getText("type"));
	    	jobsItemView.text3.setText(currentCourse.getText("location"));

	    }
	
	    return rowView;
	}

    protected static class jobsView
    {
        protected TextView text;
        protected TextView text2;
        protected TextView text3;

    }
}


