package com.example.evilj.bakingapp;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import java.util.ArrayList;

/**
 * Created by JjaviMS on 15/05/2018.
 *
 * @author JJaviMS
 */
public class BakingService extends IntentService {

    public static final String UPDATE_INGREDIENTS = "com.example.evilj.bakingapp";
    public static final String INGREDIENTS_EXTRA = "ingredients";

    public BakingService() {
        super("BakingService");
    }

    public static void startBakingService (Context context, ArrayList<String> ingredients){
        Intent intent = new Intent(context,BakingService.class);
        intent.putExtra(INGREDIENTS_EXTRA,ingredients);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent==null) return;
        final String action = intent.getAction();
        if (action==null) return;
        if (action.equals(UPDATE_INGREDIENTS)){
            handleUpdate(intent.getStringArrayListExtra(UPDATE_INGREDIENTS));
        }
    }

    private void handleUpdate (ArrayList<String> ingredients){
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int [] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this,IngredientsWidget.class));

        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds,R.id.list_widget);
        IngredientsWidget.updateAppWidget(this,appWidgetManager,appWidgetIds,ingredients);
    }


}
