package com.weeturretstudio.warbeleth.android.bakingapp.utilities;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;
import com.weeturretstudio.warbeleth.android.bakingapp.R;

//TODO: Finish implementing Widget...
//ReferenceA: https://developer.android.com/reference/android/widget/ListView
//ReferenceB: https://laaptu.wordpress.com/2013/07/19/android-app-widget-with-listview/
public class RecipeWidgetProvider extends AppWidgetProvider {
    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        /*

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        views.setTextViewText(R.id.widget_text_view, "Title");

        */

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        //views.setTextViewText(R.id.widget_text_view_title, "Ingredients");
        //views.setTextViewText(R.id.widget_text_view_body, "Eggs\nPandas");


        //CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        //RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.network_widget);
        //views.setTextViewText(R.id.appwidget_text, widgetText);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
}
