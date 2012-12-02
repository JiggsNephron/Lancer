package com.zenfly.lancer;

import java.util.Calendar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class startTask extends BroadcastReceiver 
{

	@Override
	public void onReceive(Context context, Intent intent)
	{
		SharedPreferences prefs = context.getSharedPreferences("com.zenfly.lancer",0);
		DatabaseHandler db = new DatabaseHandler(context);
		int jobId = intent.getIntExtra("job_id", 0);
		int taskId = intent.getIntExtra("task_id", 0);
		Calendar cal = Calendar.getInstance();
		if(db.getTaskStarted(taskId) == 0)
		{
			
		}
		else
		{
			
		}
	}
}