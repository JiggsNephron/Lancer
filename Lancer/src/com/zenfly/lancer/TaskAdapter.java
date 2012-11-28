package com.zenfly.lancer;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
	    View rowView = convertView;
	    taskView taskItemView = null;
	
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
	    int ID = currentTask.getId();
	    boolean isTicked = true; //db.getTaskDone(ID);
	    //jobsItemView.checkBox.setText(currentJob.getLocation());
	    if(isTicked)
	    {
	    	//CheckBox checkBox = (CheckBox)convertView.findViewById(R.id.taskCheckBox);
	    	taskItemView.checkBox.setChecked(true);
	    }
	    
	    
	    
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
