package com.zenfly.lancer;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationTimer extends BroadcastReceiver 
{
	 NotificationManager nm;

	 @Override
	 public void onReceive(Context context, Intent intent)
	 {
		 nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		 String from = "Lancer";
		 String message;
		 
		 if (intent.getIntExtra("day", 0) == 1) {
			 message = "" + intent.getStringExtra("task") + "'s deadline is in " + intent.getIntExtra("day", 0) + " day";
		 } else message = "" + intent.getStringExtra("task") + "'s deadline is in " + intent.getIntExtra("day", 0) + " days";		 
		 
		 PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, 0);
		 Notification notif = new Notification(R.drawable.app_icon, intent.getStringExtra("task") + " alert!", System.currentTimeMillis());
		 notif.setLatestEventInfo(context, from, message, contentIntent);
		 nm.notify(1, notif);
	 }
}