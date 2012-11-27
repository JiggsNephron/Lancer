package com.zenfly.lancer;


import java.util.List;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class JobsAdapter extends ArrayAdapter<Job>
{
	  private final Activity activity;
	  private final List<Job> jobsObject;
	  public DatabaseHandler db;
	
	public JobsAdapter(Activity activity, List<Job> objects) 
	{
        super(activity, R.layout.activity_jobs_list , objects);
        this.activity = activity;
        this.jobsObject = objects;
        db = new DatabaseHandler(this.getContext());
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
	    	
	    	
	    	if(db.getJobTaskCount(currentJob.getId()) != 0)
	    	{
		    	Task tempTask = db.getNearestDeadlineTask();
		    	//private static final String TAG = "MainActivity";

		    	Log.v("JobsAdapter", "tempTask  . . . . . =" + tempTask);				//testing staement
		    	

		    	int tempLocation = tempTask.getLocation();								// finds the location in the data base we are looking for
		    	if(tempLocation != 0){
		    	
			    	Log.v("JobsAdapter", "tempLocation  . . . . . =" + tempLocation);
			    	Location a  = db.getLocation(tempLocation); 						// extracts the location from the database
			    	//String b = "temp";//a.getLocation(); 								// puts the location into a string
			    	String b = a.getLocation(); 										// puts the location into a string
			    	
			    	jobsItemView.location.setText(b);								   //sets the data
		    	}
		    	
		    	//String tempDate = "temp";//tempTask.getDeadline();// just returns the raw date string
		    	String tempDate = tempTask.getDeadline();// just returns the raw date string
		    	tempDate = tempDate.trim();
		    	
		    	if((tempDate != null) && (tempDate != ""))
		    	{
		    		Log.v("JobsAdapter", "tempDate  . . . . . =" + tempDate);
			    	
			    	jobsItemView.date.setText(tempDate);								//sets the data
		    	}
		    	else
		    	{
		    		jobsItemView.name.setText("No task has a Deadline");
		    	}
		    	jobsItemView.name.setText(currentJob.getClient());//currentJob.getClient()); //sets the data
		    	return rowView;
	    	}
	    	else																		// if there is no job run the code here
	    	{
	    		jobsItemView.location.setText("");										// sets the locaition
	    		jobsItemView.date.setText("");											// sets the date
	    	}
	    	jobsItemView.name.setText(currentJob.getClient()); 							//sets the Name 
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


