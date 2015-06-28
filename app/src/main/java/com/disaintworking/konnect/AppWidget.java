package com.disaintworking.konnect;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

/**
 * Created by marcus on 28/6/15.
 */
public class AppWidget extends AppWidgetProvider{
    public static final String PICTURE = "com.marcus.chat.picture";
    public static final String NAME = "com.marcus.chat.name";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        ComponentName myWidget = new ComponentName(context, AppWidget.class);
        int[] allWidgetIds = appWidgetManager.getAppWidgetIds(myWidget);
        //appWidgetManager.updateAppWidget(myWidget, buildUpdate(context, appWidgetIds));
    }

    private RemoteViews buildUpdate(Context context, int[] appWidgetIds) {
        RemoteViews updateViews = new RemoteViews(context.getPackageName(), R.layout.widget);
        updateViews.setTextViewText(R.id.text, "Welcome!");
        updateViews.setImageViewResource(R.id.picture, R.mipmap.ic_launcher);

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        updateViews.setOnClickPendingIntent(R.id.picture, pendingIntent);

        return updateViews;
    }
}
