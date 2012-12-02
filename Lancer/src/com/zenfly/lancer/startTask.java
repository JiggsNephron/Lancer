package com.zenfly.lancer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class startTask extends BroadcastReceiver 
{

	@Override
	public void onReceive(Context context, Intent intent)
	{
		DatabaseHandler db = new DatabaseHandler(context);
		int jobId = intent.getIntExtra("job_id", 0);
		int taskId = intent.getIntExtra("task_id", 0);
		
	}
}
