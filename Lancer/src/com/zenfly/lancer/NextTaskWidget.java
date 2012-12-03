package com.zenfly.lancer;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.widget.RemoteViews;

public class NextTaskWidget extends AppWidgetProvider {
		
	DatabaseHandler db;

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		
		ComponentName thisWidget = new ComponentName(context, NextTaskWidget.class);
		
		int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
		
		RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.activity_next_task_widget);
		
		remoteViews.setTextViewText(R.id.job_name, "A test");
		
	}
}


