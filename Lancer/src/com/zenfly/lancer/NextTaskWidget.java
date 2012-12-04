package com.zenfly.lancer;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class NextTaskWidget extends AppWidgetProvider {
		
	DatabaseHandler db;

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
	{	
		ComponentName thisWidget = new ComponentName(context, NextTaskWidget.class);
		db = new DatabaseHandler(context);
		String formatDate;
		RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.activity_next_task_widget);
		int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
		if(db.getJobCount() != 0)
		{
			Task task = db.getNearestDeadlineTask();
			for (int widgetId : allWidgetIds)
		    {
			    if(task == null)
			    {
			    	remoteViews.setTextViewText(R.id.job_name, "No upcoming tasks");
			    	remoteViews.setTextViewText(R.id.next_task_name, "");
			    	remoteViews.setTextViewText(R.id.next_task_deadline, "");
			    }
			    else
			    {
			    	Intent intent = new Intent(context, JobsList.class);
			    	intent.putExtra("task", task.getId());
			    	intent.putExtra("job_id", task.getJob());
			    	PendingIntent pending = PendingIntent.getActivity(context, 0, intent, 0);
			    	remoteViews.setOnClickPendingIntent(R.id.layout, pending);
			    	Job job = db.getJob(task.getJob());
			    	remoteViews.setTextViewText(R.id.job_name, job.getClient());
			    	remoteViews.setTextViewText(R.id.next_task_name, task.getName());
			    	if(!task.getDeadline().equals(""))
			    	{
			    		SimpleDateFormat date_formater = new SimpleDateFormat("yyyy/MM/dd");			
			    		try 
			    		{
			    			// creates a date object based on the SimpleDateFormat object
			    			Date date_locale = date_formater.parse(task.getDeadline());
					    	// formats the date to a locale friendly string and saves it
							formatDate = DateFormat.getDateInstance().format(date_locale);	    		
				    	  } catch (ParseException e)
				    	  {
				    		  formatDate = "No Location Set";
						  }	    				
						remoteViews.setTextViewText(R.id.next_task_deadline, formatDate); //displays the locale formatted deadline
					}
			    	else 
					{
						remoteViews.setTextViewText(R.id.next_task_deadline, "None Set");
			        }
			      appWidgetManager.updateAppWidget(widgetId, remoteViews);
			    }
		    }
		}
		else
		{
			remoteViews.setTextViewText(R.id.job_name, "No jobs in database");
	    	remoteViews.setTextViewText(R.id.next_task_name, "");
	    	remoteViews.setTextViewText(R.id.next_task_deadline, "");
		}
	}
}


