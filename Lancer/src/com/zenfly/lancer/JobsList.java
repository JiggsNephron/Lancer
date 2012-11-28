package com.zenfly.lancer;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
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
	int backpress = 0;
	
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
    
    // Done by RC: Makes sure that back always exits, but only on 2nd press in case user didnt mean to exit
    @Override
    public void onBackPressed() {
    	
    	if (backpress == 0){    		
    		Toast.makeText(getApplicationContext(), " Press Back again to Exit ", Toast.LENGTH_SHORT).show();
    	}
    	
    	backpress = (backpress + 1);
        
    	// after 5 seconds, the backpress counter is reset to 0
        new CountDownTimer(5000, 50) {
            public void onTick(long millisUntilFinished) {
            	if (backpress > 1) {
                	Intent intent = new Intent(Intent.ACTION_MAIN);
                	intent.addCategory(Intent.CATEGORY_HOME);
                	intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                	backpress = 0;
                	startActivity(intent);
                }
            }
            public void onFinish() {
            	backpress = 0;
            }
         }.start();   
    } 
    
    public void addNewJob(View v) {
    	

    	Intent intent = new Intent(JobsList.this, AddNewJob.class); 	
    	startActivity(intent);
    	this.finish();
    	
   }
    
	@Override
	  public void onListItemClick(ListView parent, View v, int position, long id)
	  {	 
	  	Intent intent = new Intent(JobsList.this, JobsOptions.class);
	  	int jobId = jobs.get(position).getId();
	  	intent.putExtra("job_id", jobId); //sends the job id
	    startActivity(intent);
	    this.finish();
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
