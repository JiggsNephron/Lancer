package com.zenfly.lancer;

import java.util.Calendar;
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
		 Calendar calendar = Calendar.getInstance();
		 int today = calendar.get(Calendar.DAY_OF_WEEK);
		 int day = intent.getIntExtra("day", 0);
		 if(day == today)
		 {
			 nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		 	 String from = "Lancer";
		 	 String message = "It is " + intent.getIntExtra("time", 0) + " days before " + intent.getStringExtra("task") + "'s deadline";
		 	 PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, 0);
		 	 Notification notif = new Notification(R.drawable.lancer_launch_image_800x1280, intent.getStringExtra("task") + " alert!", System.currentTimeMillis());
		 	 notif.setLatestEventInfo(context, from, message, contentIntent);
		 	 nm.notify(1, notif);
		 }
	 }
}
