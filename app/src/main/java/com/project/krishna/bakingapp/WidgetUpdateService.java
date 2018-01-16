package com.project.krishna.bakingapp;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

/**
 * Created by Krishna on 1/16/18.
 */

public class WidgetUpdateService extends IntentService {


    public WidgetUpdateService() {
        super("WidgetUpdateService");
    }

    public static void startBakingService(Context context, String id,String json) {
        Intent intent = new Intent(context, WidgetUpdateService.class);
        intent.putExtra(MainActivity.CLICKED_RECIPE,id);
        intent.putExtra(MainActivity.RECIPE_JSON,json);

        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        if (intent != null) {
            String recipeId=intent.getStringExtra(MainActivity.CLICKED_RECIPE);
            String recipeJson=intent.getStringExtra(MainActivity.RECIPE_JSON);
            handleActionUpdateBakingWidgets(recipeId,recipeJson);
        }
    }



    private void handleActionUpdateBakingWidgets(String id,String json) {
        Intent intent = new Intent("android.appwidget.action.APPWIDGET_UPDATE2");
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE2");
        intent.putExtra(MainActivity.CLICKED_RECIPE,id);
        intent.putExtra(MainActivity.RECIPE_JSON,json);
        Log.i("BRD","Broadcast sent");
        LocalBroadcastManager.getInstance(this).registerReceiver(new BakingAppWidget(), new IntentFilter("android.appwidget.action.APPWIDGET_UPDATE2"));
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }

}