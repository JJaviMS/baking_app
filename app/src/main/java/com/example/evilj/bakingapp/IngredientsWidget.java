package com.example.evilj.bakingapp;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import java.util.ArrayList;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientsWidget extends AppWidgetProvider {

    public static ArrayList<String> ingredientes;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = getRemoteViews(context);


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);

    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int[] appWidgetIds, ArrayList<String> strings) {
        ingredientes = strings;
        for (int widget : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, widget);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    private static RemoteViews getRemoteViews (Context context){
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.ingredients_widget);
        Intent intent = new Intent(context,ListWidgetService.class);
        intent.putExtra(BakingService.INGREDIENTS_EXTRA,ingredientes);
        remoteViews.setRemoteAdapter(R.id.list_widget,intent);
        remoteViews.setEmptyView(R.id.list_widget,R.id.empty_widget);
        return remoteViews;
    }

}

