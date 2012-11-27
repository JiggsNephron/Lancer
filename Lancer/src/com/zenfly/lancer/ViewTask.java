package com.zenfly.lancer;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class ViewTask extends Activity {
	
	private DatabaseHandler db;
	private static final String TAG = "ViewTasks";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_task);
		
		
		int thisTask = getIntent().getExtras().getInt("task");
		Log.v(TAG, "thisTask=" + thisTask);
		Task task  =  db.getTask(thisTask);
		
		String taskName = task.getName();
		String taskLocation = task.getDeadline();
		int taskDeadline = task.getLocation();
		
		TextView displayName = (TextView) findViewById(R.id.thisTaskName);
		TextView displayLocation = (TextView) findViewById(R.id.thisTaskName);
		
		displayName.setText(taskName);
		displayLocation.setText(taskLocation);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_view_task, menu);
		return true;
	}

}
