package com.zenfly.lancer;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ListView;
//import android.view.MenuItem;

public class TasksList extends ListActivity {
	
	public DatabaseHandler db;
	List<Task> task;// = //new ArrayList<Task>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks_list);
        
        db = new DatabaseHandler(this.getApplicationContext());

        
        //SMcD: just adding this to see if it grabs jobs from the DB. And it does. Happy days
        int thisJob = getIntent().getIntExtra("job_id", 0);
        task = db.getAllTasksForJob(thisJob); //makes a list of jobs to send to the list View
        
        setListAdapter(new TaskAdapter(this, task)); //starts the list View
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_tasks_list, menu);
        return true;
    }
    
    public void addNewTask(View v) {
       	Intent intent = new Intent(TasksList.this, AddNewTask.class);
       	intent.putExtra("job_id", getIntent().getIntExtra("job_id", 0));
    	startActivity(intent);	
   }
    
    
	@Override
	  public void onListItemClick(ListView parent, View v, int position, long id)
	  {	 
	  	Intent intent = new Intent(TasksList.this, ViewTask.class);
	  	int taskId = task.get(position).getId();
	  	Log.v("Id is: ", taskId+"");
	  	intent.putExtra("task", taskId); //sends the job name
	    startActivity(intent);
	  }
	
	public void onCheckedChanged (CompoundButton taskCheckBox, boolean isChecked)
	{
		if(isChecked ==  true)
		{
			
		}
		else if(isChecked ==  false)
		{
			Log.v("task list  . . . . . ", "thisTask=" + isChecked);
		}
	}
	
	  //below is the code that creates the menu using the right xml file
	 /*
		@Override
	  	public boolean onOptionsItemSelected(MenuItem item) {
	      // Handle item selection
	      switch (item.getItemId()) {
	          case R.id.addCourse:
	        	  Intent intent = new Intent(JobsList.this, AddJob.class);
	              startActivity(intent);
	              return true;
	          case R.id.removeCourse:
	        	  Intent intent2 = new Intent(JobsList.this, DeleteJob.class);
	              startActivity(intent2);
	              return true;
	          case R.id.menu_settings:
	        	  Intent intent3 = new Intent(JobsList.this, Settings.class);
	              startActivity(intent3);
	              return true;
	          default:
	              return super.onOptionsItemSelected(item);
	      }
	  }//*/
	
}
