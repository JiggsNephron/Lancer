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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class TaskAdapter extends ArrayAdapter<Task> {

	  private final Activity activity;
	  private final List<Task> taskObject;
	  public DatabaseHandler db;
	  Date date_locale;
	  String stformatted_task_date = "";
	
	public TaskAdapter(Activity activity, List<Task> objects) 
	{
      super(activity, R.layout.activity_tasks_list , objects);
      this.activity = activity;
      this.taskObject = objects;
      db = new DatabaseHandler(this.getContext());      
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent)
	{
	    View rowView = convertView;
	    final taskView taskItemView;
	
	    if(rowView == null)
	    {
	    	//creates new instance of row layout view
	        LayoutInflater inflater = activity.getLayoutInflater();
	        rowView = inflater.inflate(R.layout.task_item, null);
	        taskItemView = new taskView(); //for holding the data
	        taskItemView.name = (TextView) rowView.findViewById(R.id.taskNameDisplay);
	        taskItemView.checkBox = (CheckBox) rowView.findViewById(R.id.taskCheckBox);
	        taskItemView.deadline = (TextView) rowView.findViewById(R.id.taskDateDisplay);
	        taskItemView.location = (TextView) rowView.findViewById(R.id.taskLocationDisplay);
	        rowView.setTag(taskItemView); //for later access
	    }
	    else taskItemView = (taskView) rowView.getTag();
	    String curLocation = "";
	    Task currentTask = (Task) taskObject.get(position); //casts as course
	    taskItemView.name.setText(currentTask.getName()); //sets the data
	    
	    String curDeadline = currentTask.getDeadline();	  
	    if(!curDeadline.equals("")) {	    	
	    	// creates a SimpleDateFormat object with the same template as the database deadline date string
	    	SimpleDateFormat date_formater = new SimpleDateFormat("yyyy/MM/dd");	    	
	    	try {
	    		// creates a date object based on the SimpleDateFormat object
	    		date_locale = date_formater.parse(curDeadline);
		    	// formats the date to a locale friendly string and saves it
				stformatted_task_date = DateFormat.getDateInstance().format(date_locale);	    		
	    	} catch (ParseException e) {
				stformatted_task_date = "No Deadline Found";
			}	    				
	    	taskItemView.deadline.setText(stformatted_task_date);
	    } else {
	    	taskItemView.deadline.setText("No Deadline Set");
	    }
	    
	    if(currentTask.getLocation() != 0) curLocation = db.getLocation(currentTask.getLocation()).getLocation();
	    if(!curLocation.equals(""))taskItemView.location.setText(curLocation);
	    else taskItemView.location.setText("No Location Set");
	    final int ID = currentTask.getId();
	    taskItemView.checkBox.setChecked(db.getTaskDone(ID)); //sets the checkbox ticked if the task is done or unticked if not done
	    if(db.getTaskDone(ID))
	    {
	    	taskItemView.name.setTextColor(Color.GRAY);
	    	taskItemView.name.setPaintFlags(taskItemView.name.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
	    	taskItemView.deadline.setTextColor(Color.GRAY);
			taskItemView.deadline.setPaintFlags(taskItemView.deadline.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
			taskItemView.location.setTextColor(Color.GRAY);
			taskItemView.location.setPaintFlags(taskItemView.location.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
	    }
	    taskItemView.checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener()
	    {
	    	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
	    	{
	    		if (taskItemView.checkBox.isChecked())
	    		{
	    			taskItemView.checkBox.setChecked(true);
	    			taskItemView.name.setTextColor(Color.GRAY);
	    			taskItemView.name.setPaintFlags(taskItemView.name.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
	    			taskItemView.deadline.setTextColor(Color.GRAY);
	    			taskItemView.deadline.setPaintFlags(taskItemView.deadline.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
	    			taskItemView.location.setTextColor(Color.GRAY);
	    			taskItemView.location.setPaintFlags(taskItemView.location.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
	    			db.setTaskDone(ID, 1);
	            }
	    		else
	    		{
	    			taskItemView.checkBox.setChecked(false);
	    			taskItemView.name.setTextColor(activity.getResources().getColor(R.color.lancer_blue));
	    			taskItemView.name.setPaintFlags(taskItemView.name.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
	    			taskItemView.deadline.setTextColor(activity.getResources().getColor(R.color.lancer_blue));
	    			taskItemView.deadline.setPaintFlags(taskItemView.deadline.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
	    			taskItemView.location.setTextColor(activity.getResources().getColor(R.color.lancer_blue));
	    			taskItemView.location.setPaintFlags(taskItemView.location.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
	    			db.setTaskDone(ID, 0);
	    		}
	        }
	    });
	    return rowView;
	}
	
    protected static class taskView
    {
        protected TextView name;
        protected TextView deadline;
        protected TextView location;
        protected CheckBox checkBox;

    }
}
