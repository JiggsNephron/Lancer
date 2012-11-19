package com.zenfly.lancer;


import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class JobsAdapter extends ArrayAdapter<Job>
{
	//public static final String PREFS_COUNT = "MyPrefsFile";
	  private final Activity activity;
	  private final List<Job> jobsObject;
	
	public JobsAdapter(Activity activity, List<Job> objects) 
	{
        super(activity, R.layout.activity_jobs_list , objects);
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
	        rowView = inflater.inflate(R.layout.job_item, null);
	        jobsItemView = new jobsView(); //for holding the data
	        jobsItemView.name = (TextView) rowView.findViewById(R.id.JobNameDisplay); 
	        jobsItemView.location = (TextView) rowView.findViewById(R.id.JobLocationDisplay);
	        jobsItemView.date = (TextView) rowView.findViewById(R.id.JobDateDisplay);
	       
	        rowView.setTag(jobsItemView); //for later access
	    }
	    else jobsItemView = (jobsView) rowView.getTag();
	    {
	    	Job currentJob = (Job) jobsObject.get(position); //casts as course
	
	    	jobsItemView.name.setText(currentJob.getClient()); //sets the data
	    	//jobsItemView.location.setText(currentJob.getLocation());
	    	//jobsItemView.date.setText(currentJob.getText("date"));

	    }
	
	    return rowView;
	}

    protected static class jobsView
    {
        protected TextView name;
        protected TextView location;
        protected TextView date;

    }
}


