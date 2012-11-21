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

public class JobsList extends ListActivity {
	
	public DatabaseHandler db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs_list);
        
        db = new DatabaseHandler(this.getApplicationContext());
        db.getWritableDatabase();
        
        
        List<Job> jobs = new ArrayList<Job>(); //makes a list of jobs to send to the list View
        
        setListAdapter(new JobsAdapter(this, jobs)); //starts the list View
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_jobs_list, menu);
        return true;
    }
    
    public void addNewJob(View v) {
    	
    	// FIXME RC: FOR SB > Adding this so I can test my view via this button
    	Intent intent = new Intent(JobsList.this, AddNewJob.class);
    	
    	startActivity(intent);
    	
    }
    
    
	@Override
	  public void onListItemClick(ListView parent, View v, int position, long id)
	  {	 
	  	Intent intent = new Intent(JobsList.this, JobsOptions.class);
	  	intent.putExtra("course", position); //sends the job name

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
