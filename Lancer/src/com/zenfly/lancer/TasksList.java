package com.zenfly.lancer;

import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.ListView;
//import android.view.MenuItem;
import android.widget.TextView;

public class TasksList extends ListActivity {
	
	private DatabaseHandler db;
	List<Task> task;
	int thisJob;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE); // RC: this removed the black bar at the top of activities.
        setContentView(R.layout.activity_tasks_list);
        
        db = new DatabaseHandler(this.getApplicationContext());
        thisJob = getIntent().getIntExtra("job_id", 0);
        Job job = db.getJob(thisJob);
        task = db.getAllTasksForJob(thisJob); //makes a list of tasks to send to the list View
       // Log.v("Expenses list", "task="+ task +"  ~END");
        
        setListAdapter(new TaskAdapter(this, task)); //starts the list View
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_tasks_list, menu);
        return true;
    }
    
    @Override
    public void onBackPressed() {
    	Intent intent = new Intent(TasksList.this, JobsOptions.class);
    	intent.putExtra("job_id", thisJob);
    	startActivity(intent);
    	return;
    }     
    
    public void addNewTask(View v) {
       	Intent intent = new Intent(TasksList.this, AddNewTask.class);
       	intent.putExtra("job_id", thisJob);
    	startActivity(intent);	
   }
    
    
	@Override
	  public void onListItemClick(ListView parent, View v, int position, long id)
	  {	 
	  	Intent intent = new Intent(TasksList.this, ViewTask.class);
	  	int taskId = task.get(position).getId();
	  //	Log.v("Id is: ", taskId+"");
	  	intent.putExtra("task", taskId); //sends the task id
	  	intent.putExtra("job_id", thisJob); //sends the Job id
	    startActivity(intent);
	  }
	
	public void onCheckedChanged (CompoundButton taskCheckBox, boolean isChecked)
	{
		if(taskCheckBox.isChecked() ==  true)
		{
			
		}
		else if(!taskCheckBox.isChecked())
		{
			Log.v("task list  . . . . . ", "thisTask=no");
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
	
	
	// add urgent tasks  to home screen
	
	
	
	
	
}
