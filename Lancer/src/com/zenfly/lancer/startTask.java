package com.zenfly.lancer;

import java.util.Calendar;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
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
		int taskId = intent.getIntExtra("task_id", 0);
		Task task = db.getTask(taskId);
		Job job = db.getJob(task.getJob());
		NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		String from = "Lancer";
		String message = "You have started working on " + task.getName();
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, 0);
		Notification notif = new Notification(R.drawable.lancer_launch_image_800x1280, "Working on " + task.getName() + " for " + job.getClient(), System.currentTimeMillis());
		notif.setLatestEventInfo(context, from, message, contentIntent);
		nm.notify(1, notif);
	}
}