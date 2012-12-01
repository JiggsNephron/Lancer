package com.zenfly.lancer;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
//import android.view.MenuItem;

public class JobsList extends ListActivity {
	
	public DatabaseHandler db;
	List<Job> jobs = new ArrayList<Job>();
	SharedPreferences prefs;
	int backpress = 0;
	protected Dialog mSplashDialog; // Needed for SplashScreen
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
        // this.requestWindowFeature(Window.FEATURE_NO_TITLE); // RC: this removed the black bar at the top of activities. 
        
        //Splash Code Start
        MyStateSaver data = (MyStateSaver) getLastNonConfigurationInstance(); // Method checks if app is still loading
	    if (data != null) {
	    	if (data.showSplashScreen) {
	            showSplashScreen();
	        }
	    	setContentView(R.layout.activity_jobs_list);
	    	} else {
	        showSplashScreen();
	        setContentView(R.layout.activity_jobs_list);
	        //Splash Code end
	        
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
	
	  // Below is the code that creates the menu using the right xml file
	
		@Override
	  	public boolean onOptionsItemSelected(MenuItem item) {
	      // Handle item selection
	      switch (item.getItemId()) {
	          case R.id.menu_settings:
	        	  Intent intent3 = new Intent(JobsList.this, Settings.class);
	              startActivity(intent3);
	              return true;
	          default:
	              return super.onOptionsItemSelected(item);
	      }
	  }
	
		
		//Splash Code Start
		@Override
		public Object onRetainNonConfigurationInstance() {
		    MyStateSaver data = new MyStateSaver();
		
		    if (mSplashDialog != null) {
		        data.showSplashScreen = true;
		        removeSplashScreen();
		    }
		    return data;
		}
		 
	
	 // Removes the Dialog that displays the splash screen
		 
		protected void removeSplashScreen() {
		    if (mSplashDialog != null) {
		        mSplashDialog.dismiss();
		        mSplashDialog = null;
		    }
		}
		 
		
		//Shows the splash screen over the full Activity
		 
		protected void showSplashScreen() {
		    mSplashDialog = new Dialog(this, R.style.SplashScreen); // From res/values/styles.xml
		    mSplashDialog.setContentView(R.layout.splashscreen);
		    mSplashDialog.setCancelable(false);
		    mSplashDialog.show();
		     
		    // Set Runnable to remove splash screen just in case
		    final Handler handler = new Handler();
		    handler.postDelayed(new Runnable() {
		      
		      public void run() {
		        removeSplashScreen();
		      }
		    }, 3000);
		}
		 
		
		// Simple class for storing important data across config changes
		 
		private class MyStateSaver {
		    public boolean showSplashScreen = false;
		}	
		//SplashCode End
		
}
