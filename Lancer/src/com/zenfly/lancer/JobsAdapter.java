package com.zenfly.lancer;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
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
	  Date date_locale;
	
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
	        jobsItemView = new jobsView(); 															//for holding the job
	        jobsItemView.name = (TextView) rowView.findViewById(R.id.JobNameDisplay); 				//prepares to access the variable
	        jobsItemView.task = (TextView) rowView.findViewById(R.id.JobLocationDisplay);			//prepares to access the variable
	        jobsItemView.date = (TextView) rowView.findViewById(R.id.JobDateDisplay);				//prepares to access the variable
	        jobsItemView.percentage = (TextView) rowView.findViewById(R.id.JobCompletionDisplay);	//prepares to access the variable
	        jobsItemView.done = (TextView) rowView.findViewById(R.id.JobDoneDisplay);				//prepares to access the variable
	        rowView.setTag(jobsItemView); 															//for later access
	    }
	    else jobsItemView = (jobsView) rowView.getTag();
	    {
	    	Job currentJob = (Job) jobsObject.get(position); //casts as job
	    	Task tempTask = null;
	    	if(db.getJobTaskCount(currentJob.getId()) != 0) tempTask = db.getNearestDeadlineTaskForJob(currentJob.getId());
	    	
	    	int percent = db.getPercentDone(currentJob.getId());
	    	
	    	if(percent == 100) 														//if all tasks are complete
	    	{
	    		//grey out all text and cross out all except for percentage and done
	    		jobsItemView.name.setTextColor(Color.GRAY);
		    	jobsItemView.name.setPaintFlags(jobsItemView.name.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
		    	jobsItemView.date.setTextColor(Color.GRAY);
				jobsItemView.date.setPaintFlags(jobsItemView.date.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
				jobsItemView.task.setTextColor(Color.GRAY);
				jobsItemView.task.setPaintFlags(jobsItemView.task.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
				jobsItemView.percentage.setTextColor(Color.GRAY);
				jobsItemView.percentage.setText(percent+"%");
				jobsItemView.done.setTextColor(Color.GRAY);
	    	}
	    	jobsItemView.percentage.setText(percent+"%");
	    	
	    	if(tempTask != null)
	    	{

			    	String taskName = tempTask.getName();									// finds the location in the data base we are looking for			   
				    jobsItemView.task.setText(taskName);									//sets the location			    	
			    	String tempDate = tempTask.getDeadline(); 								//just returns the deadline
			    	
			    	// formats the date to a locale friendly string and saves it
			    	if((tempDate != null) && (tempDate != "")) 								//if there is a deadline and it isn't blank
			    	{
			    		SimpleDateFormat date_formater = new SimpleDateFormat("yyyy/MM/dd");
				    	try {
							date_locale = date_formater.parse(tempDate);
							tempDate = DateFormat.getDateInstance().format(date_locale);
						} catch (ParseException e) {
							e.printStackTrace();
						}
				    	jobsItemView.date.setText(tempDate);								//sets the data
			    	}
			    	else{																	//if there is no deadline
			    		jobsItemView.name.setText("No task has a Deadline");	
			    	}
	    	}
	    	else{ 																			//if the job has no tasks
	    		jobsItemView.task.setText("");												// sets the location
	    		jobsItemView.date.setText("");												// sets the date
	    	}
	    jobsItemView.name.setText(currentJob.getClient()); 									//sets the Name 
	    }	
	    return rowView;
	}

    protected static class jobsView
    {
        protected TextView name;
        protected TextView task;
        protected TextView date;
        protected TextView percentage;
        protected TextView done;

    }
}