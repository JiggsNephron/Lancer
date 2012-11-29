package com.zenfly.lancer;

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
	        rowView.setTag(taskItemView); //for later access
	    }
	    else taskItemView = (taskView) rowView.getTag();
	    Task currentTask = (Task) taskObject.get(position); //casts as course
	    taskItemView.name.setText(currentTask.getName()); //sets the data
	    final int ID = currentTask.getId();
	    //boolean isTicked = true; //db.getTaskDone(ID);
	    //jobsItemView.checkBox.setText(currentJob.getLocation());
	    //if(isTicked)
	    //{
	    	//CheckBox checkBox = (CheckBox)convertView.findViewById(R.id.taskCheckBox);
	    	taskItemView.checkBox.setChecked(db.getTaskDone(ID)); //sets the checkbox ticked if the task is done or unticked if not done
	    	if(db.getTaskDone(ID))
	    	{
	    		taskItemView.name.setTextColor(Color.GRAY);
	    		taskItemView.name.setPaintFlags(taskItemView.name.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
	    	}
	    //}
	    	taskItemView.checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener()
	    	{
	    		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
	    		{
	    			if (taskItemView.checkBox.isChecked())
	    			{
	    				taskItemView.checkBox.setChecked(true);
	    				taskItemView.name.setTextColor(Color.GRAY);
	    				taskItemView.name.setPaintFlags(taskItemView.name.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
	    				db.setTaskDone(ID, 1);
	    	        }
	    			else
	    			{
	    				taskItemView.checkBox.setChecked(false);
	    				taskItemView.name.setTextColor(activity.getResources().getColor(R.color.lancer_blue));
	    				taskItemView.name.setPaintFlags(taskItemView.name.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
	    				db.setTaskDone(ID, 0);
	    			}
	    			
	    	    }
	    	});

	    
	    
	    return rowView;
	}
	
/*	public void onCheckedChanged (CompoundButton taskCheckBox, boolean isChecked, int position)
	{

	}*/

    protected static class taskView
    {
        protected TextView name;
        protected CheckBox checkBox;

    }
}
