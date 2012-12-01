package com.zenfly.lancer;


import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
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
	        jobsItemView = new jobsView(); //for holding the job
	        jobsItemView.name = (TextView) rowView.findViewById(R.id.JobNameDisplay); 
	        jobsItemView.location = (TextView) rowView.findViewById(R.id.JobLocationDisplay);
	        jobsItemView.date = (TextView) rowView.findViewById(R.id.JobDateDisplay);
	        jobsItemView.percentage = (TextView) rowView.findViewById(R.id.JobCompletionDisplay);
	        jobsItemView.done = (TextView) rowView.findViewById(R.id.JobDoneDisplay);
	        rowView.setTag(jobsItemView); //for later access
	    }
	    else jobsItemView = (jobsView) rowView.getTag();
	    {
	    	Job currentJob = (Job) jobsObject.get(position); //casts as job
	    	if(db.getJobTaskCount(currentJob.getId()) != 0)
	    	{
		    	Task tempTask = db.getNearestDeadlineTask();
		    	int percent = db.getPercentDone(currentJob.getId());
		    	jobsItemView.percentage.setText(percent+"%");
		    	int tempLocation = tempTask.getLocation();								// finds the location in the data base we are looking for
		    	if(tempLocation != 0) //if there is a location
		    	{
			    	Location a  = db.getLocation(tempLocation); 						// extracts the location from the database
			    	String b = a.getLocation(); 										// puts the location into a string	
			    	jobsItemView.location.setText(b);								   //sets the location
		    	}
		    	String tempDate = tempTask.getDeadline(); //just returns the deadline
		    	tempDate = tempDate.trim();
		    	
		    	if((tempDate != null) && (tempDate != "")) //if there is a deadline and it isn't blank
		    	{
			    	jobsItemView.date.setText(tempDate);								//sets the data
		    	}
		    	else //if there is no deadline
		    	{
		    		jobsItemView.name.setText("No task has a Deadline");
		    		
		    	}
		    	if(percent == 100) //if all tasks are complete
		    	{
		    		//grey out all text and cross out all except for percentage and done
		    		jobsItemView.name.setTextColor(Color.GRAY);
			    	jobsItemView.name.setPaintFlags(jobsItemView.name.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
			    	jobsItemView.date.setTextColor(Color.GRAY);
					jobsItemView.date.setPaintFlags(jobsItemView.date.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
					jobsItemView.location.setTextColor(Color.GRAY);
					jobsItemView.location.setPaintFlags(jobsItemView.location.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
					jobsItemView.percentage.setTextColor(Color.GRAY);
					jobsItemView.done.setTextColor(Color.GRAY);
		    	}
	    	}
	    	else //if the job has no tasks
	    	{
	    		jobsItemView.location.setText("");										// sets the location
	    		jobsItemView.date.setText("");											// sets the date
	    		jobsItemView.percentage.setText("");
	    		jobsItemView.done.setText("");
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
        protected TextView percentage;
        protected TextView done;

    }
}


