package com.zenfly.lancer;

import java.util.ArrayList;
import java.util.List;

import com.zenfly.lancer.DatabaseHandler;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
//import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class JobsList extends ListActivity {
	
	public DatabaseHandler db;
	List<Job> jobs = new ArrayList<Job>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs_list);
        
        db = new DatabaseHandler(this.getApplicationContext());
        //SMcD: this line isn't actually needed
        //db.getWritableDatabase();
        if(db.getJobCount() == 0)
        {
        	Intent intent = new Intent(JobsList.this, AddNewJob.class);
        	Toast.makeText(getApplicationContext(), "You have no current jobs. Please add one", Toast.LENGTH_LONG).show();
        	startActivity(intent);
        }
        //SMcD: just adding this to see if it grabs jobs from the DB. And it does. Happy days
        jobs = db.getAllJobs();//= new ArrayList<Job>(); //makes a list of jobs to send to the list View
        
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
	  	intent.putExtra("job", jobId); //sends the job name
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
