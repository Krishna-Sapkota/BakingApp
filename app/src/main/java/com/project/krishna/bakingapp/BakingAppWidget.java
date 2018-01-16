package com.project.krishna.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.project.krishna.bakingapp.data.RecipeDetails;
import com.project.krishna.bakingapp.util.ParseUtility;

import org.json.JSONException;

/**
 * Implementation of App Widget functionality.
 */
public class BakingAppWidget extends AppWidgetProvider {

    String recipeId;
    String recipeJson;
    public static RecipeDetails recipeDetails;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget);

        //call activity when widget is clicked, but resume activity from stack so you do not pass intent.extras afresh
        Intent appIntent = new Intent(context, DetailsActivity.class);
        appIntent.addCategory(Intent.ACTION_MAIN);
        appIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        appIntent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.widget_grid_view, appPendingIntent);

        // Set the GridWidgetService intent to act as the adapter for the GridView
        Log.i("BRD","Broadcast 123");

        Intent intent = new Intent(context, GridWidgetService.class);
        views.setRemoteAdapter(R.id.widget_grid_view, intent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
      //  for (int appWidgetId : appWidgetIds) {
       //     updateAppWidget(context, appWidgetManager, appWidgetId);
        //}
    }
    public static void updateBakingWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, BakingAppWidget.class));

        final String action = intent.getAction();
        Log.i("BRD","Broadcast");

        if (action.equals("android.appwidget.action.APPWIDGET_UPDATE2")) {
            recipeId=intent.getStringExtra(MainActivity.CLICKED_RECIPE);
            recipeJson=intent.getStringExtra(MainActivity.RECIPE_JSON);


            try {
                recipeDetails= ParseUtility.getRecipeDetails(recipeJson,recipeId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_grid_view);
            //Now update all widgets

            BakingAppWidget.updateBakingWidgets(context, appWidgetManager, appWidgetIds);
            super.onReceive(context, intent);
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
}

