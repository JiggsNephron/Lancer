package com.zenfly.lancer;

import java.util.Calendar;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

public class NotificationTimer extends BroadcastReceiver 
{
	 NotificationManager nm;

	 @Override
	 public void onReceive(Context context, Intent intent)
	 {
		 nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		 String from = "Lancer";
		 String message = "It is " + intent.getIntExtra("day", 0) + " days before " + intent.getStringExtra("task") + "'s deadline";
		 PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, 0);
		 Notification notif = new Notification(R.drawable.app_icon, intent.getStringExtra("task") + " alert!", System.currentTimeMillis());
		 notif.setLatestEventInfo(context, from, message, contentIntent);
		 nm.notify(1, notif);

		 /*intent.putExtra("job_id", getIntent().getIntExtra("job_id", 0));
		 intent.putExtra("task", getIntent().getIntExtra("task", 0));

		 PendingIntent pendingIntent = PendingIntent.getActivity(ViewTask.this, 01, intent, 0);

		 NotificationCompat.Builder builder = new NotificationCompat.Builder(ViewTask.this);

		 builder.setContentIntent(pendingIntent)
		 .setSmallIcon(R.drawable.app_icon)
		 .setTicker(task.getName() + "started")
		 .setContentTitle(task.getName() + "running")
		 .setContentText(task.getName() + " at " + locale_currency_format.format(task.getWage()))
		 .setContentInfo("Lancer")
		 .setOngoing(true);

		 Notification notification = builder.build();
		 Toast.makeText(getApplicationContext(), "Starting notification", Toast.LENGTH_SHORT).show();  
		 nm.notify(01, notification);*/
	 }
}