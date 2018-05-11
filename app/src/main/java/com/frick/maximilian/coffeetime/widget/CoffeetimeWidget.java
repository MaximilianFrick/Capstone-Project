package com.frick.maximilian.coffeetime.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;

public class CoffeetimeWidget extends AppWidgetProvider {

   @Override
   public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
      Intent intent = new Intent(context.getApplicationContext(), UpdateWidgetService.class);
      intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

      // Update the widgets via the service
      context.startService(intent);
   }
}

