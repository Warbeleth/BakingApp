package com.weeturretstudio.warbeleth.android.bakingapp.utilities;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;
import android.widget.ScrollView;
import android.widget.TextView;

import com.weeturretstudio.warbeleth.android.bakingapp.R;
import com.weeturretstudio.warbeleth.android.bakingapp.model.Recipe;
import com.weeturretstudio.warbeleth.android.bakingapp.model.RecipeContent;

import java.util.List;

//TODO: Finish implementing Widget...
//ReferenceA: https://developer.android.com/reference/android/widget/ListView
//ReferenceB: https://laaptu.wordpress.com/2013/07/19/android-app-widget-with-listview/
public class RecipeWidgetProvider extends AppWidgetProvider {

    List<Recipe> recipeList = RecipeContent.recipes.getValue();

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        views.setTextViewText(R.id.widget_text_view, "Title");
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        recipeList = RecipeContent.recipes.getValue();



        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
}
