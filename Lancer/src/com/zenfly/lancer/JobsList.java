package com.zenfly.lancer;

import java.util.ArrayList;
import java.util.List;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.Toast;
//import android.view.MenuItem;

public class JobsList extends ListActivity {
	
	public DatabaseHandler db;
	List<Job> jobs = new ArrayList<Job>();
	SharedPreferences prefs;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE); // RC: this removed the black bar at the top of activities. 
        setContentView(R.layout.activity_jobs_list);
        String order;
        prefs = getSharedPreferences("com.zenfly.lancer",0);
        db = new DatabaseHandler(this.getApplicationContext());;
        if(db.getJobCount() == 0)
        {
        	Intent intent = new Intent(JobsList.this, AddNewJob.class);
        	Toast.makeText(getApplicationContext(), "You have no current jobs. Please add one", Toast.LENGTH_LONG).show();
        	startActivity(intent);
        }
        order = prefs.getString("order", "none");
        if(order.equals("none"))jobs = db.getAllJobs();
        else if(order.equals("deadline"))jobs = db.getAllJobsByDeadline();
        else if(order.equals("alphaAsc"))jobs = db.getAllJobsByAlphaAsc();
        else if(order.equals("alphaDesc"))jobs = db.getAllJobsByAlphaDesc();
        
        setListAdapter(new JobsAdapter(this, jobs)); //starts the list View
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_jobs_list, menu);
        return true;
    }
    
    public void addNewJob(View v) {
    	

    	Intent intent = new Intent(JobsList.this, AddNewJob.class); 	
    	startActivity(intent);
    	
   }
    
	@Override
	  public void onListItemClick(ListView parent, View v, int position, long id)
	  {	 
	  	Intent intent = new Intent(JobsList.this, JobsOptions.class);
	  	int jobId = jobs.get(position).getId();
	  	intent.putExtra("job_id", jobId); //sends the job id
	    startActivity(intent);
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

// a test comment
